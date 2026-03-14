/*
 * Copyright (c) 2024-2026 Terra Technology (Guangzhou) Co., Ltd.
 * Copyright (c) 2024-2026 泰若科技（广州）有限公司.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 *
 */

package com.terra.ems.system.aspect;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.terra.ems.common.utils.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.NamedThreadLocal;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.terra.ems.common.annotation.Log;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import com.terra.ems.common.enums.BusinessStatus;
import com.terra.ems.common.utils.ServletUtils;
import com.terra.ems.common.utils.ip.IpUtils;
import com.terra.ems.framework.manager.AsyncManager;
import com.terra.ems.system.manager.factory.AsyncFactory;
import com.terra.ems.system.entity.SysOperationLog;
import jakarta.annotation.PostConstruct;

/**
 * 操作日志记录处理
 * 
 * @author dengxueping
 */
@Aspect
@Component
public class LogAspect {
    private static final Logger log = LoggerFactory.getLogger(LogAspect.class);

    private static final ObjectMapper objectMapper = new ObjectMapper();

    /** 排除敏感属性字段 */
    public static final String[] EXCLUDE_PROPERTIES = { "password", "oldPassword", "newPassword", "confirmPassword" };

    /** 计算操作消耗时间 */
    private static final ThreadLocal<Long> TIME_THREADLOCAL = new NamedThreadLocal<Long>("Cost Time");

    @PostConstruct
    public void init() {
        log.info("========================================");
        log.info("[LogAspect] 操作日志切面已初始化！AOP 已生效");
        log.info("========================================");
    }

    /**
     * 处理请求前执行
     */
    @Before(value = "@annotation(controllerLog)")
    public void boBefore(JoinPoint joinPoint, Log controllerLog) {
        TIME_THREADLOCAL.set(System.currentTimeMillis());
    }

    /**
     * 处理完请求后执行
     *
     * @param joinPoint 切点
     */
    @AfterReturning(pointcut = "@annotation(controllerLog)", returning = "jsonResult")
    public void doAfterReturning(JoinPoint joinPoint, Log controllerLog, Object jsonResult) {
        handleLog(joinPoint, controllerLog, null, jsonResult);
    }

    /**
     * 拦截异常操作
     * 
     * @param joinPoint 切点
     * @param e         异常
     */
    @AfterThrowing(value = "@annotation(controllerLog)", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Log controllerLog, Exception e) {
        handleLog(joinPoint, controllerLog, e, null);
    }

    protected void handleLog(final JoinPoint joinPoint, Log controllerLog, final Exception e, Object jsonResult) {
        try {
            log.info("[LogAspect] 拦截到操作日志: title={}, businessType={}, method={}",
                    controllerLog.title(), controllerLog.businessType(),
                    joinPoint.getSignature().toShortString());
            // 获取当前的用户
            String username = "unknown"; // 暂时无法获取SecurityContext，需要结合SpringSecurity获取当前用户
            try {
                // 这里简单尝试获取用户名，实际应该从SecurityContextHolder获取
                // 由于Aspect是在Framework中，我们假设SecurityContextHolder可用
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                if (authentication != null) {
                    username = authentication.getName();
                }
            } catch (Exception ex) {
                // ignore
            }

            // *========数据库日志=========*//
            SysOperationLog operationLog = new SysOperationLog();
            operationLog.setStatus(BusinessStatus.SUCCESS.ordinal());
            // 请求的地址
            String ip = IpUtils.getIpAddr(ServletUtils.getRequest());
            operationLog.setOperationIp(ip);
            operationLog.setOperationUrl(StringUtils.substring(ServletUtils.getRequest().getRequestURI(), 0, 255));
            operationLog.setOperationName(username);

            if (e != null) {
                operationLog.setStatus(BusinessStatus.FAIL.ordinal());
                operationLog.setErrorMsg(StringUtils.substring(e.getMessage(), 0, 2000));
            }
            // 设置方法名称
            String className = joinPoint.getTarget().getClass().getName();
            String methodName = joinPoint.getSignature().getName();
            operationLog.setMethod(className + "." + methodName + "()");
            // 设置请求方式
            operationLog.setRequestMethod(ServletUtils.getRequest().getMethod());
            // 处理设置注解上的参数
            getControllerMethodDescription(joinPoint, controllerLog, operationLog, jsonResult);
            // 设置消耗时间
            operationLog.setCostTime(System.currentTimeMillis() - TIME_THREADLOCAL.get());
            operationLog.setOperationTime(LocalDateTime.now());
            // 保存数据库
            log.info("[LogAspect] 准备异步保存操作日志: title={}, operationName={}, operationUrl={}",
                    operationLog.getTitle(), operationLog.getOperationName(), operationLog.getOperationUrl());
            AsyncManager.me().execute(AsyncFactory.recordOper(operationLog));
        } catch (Exception exp) {
            // 记录本地异常日志
            log.error("==前置通知异常==");
            log.error("异常信息:{}", exp.getMessage());
            exp.printStackTrace();
        } finally {
            TIME_THREADLOCAL.remove();
        }
    }

    /**
     * 获取注解中对方法的描述信息 用于Controller层注解
     * 
     * @param log     日志
     * @param operationLog 操作日志
     * @throws Exception
     */
    public void getControllerMethodDescription(JoinPoint joinPoint, Log log, SysOperationLog operationLog, Object jsonResult)
            throws Exception {
        // 设置action动作
        operationLog.setBusinessType(log.businessType().ordinal());
        // 设置标题
        String title = log.title();
        if (StringUtils.isEmpty(title)) {
            Tag tag = joinPoint.getTarget().getClass().getAnnotation(Tag.class);
            if (tag != null && StringUtils.isNotEmpty(tag.name())) {
                title = tag.name();
            }
        }
        operationLog.setTitle(title);
        // 设置操作人类别
        operationLog.setOperatorType(log.operatorType().ordinal());
        // 是否需要保存request，参数和值
        if (log.isSaveRequestData()) {
            // 获取参数的信息，传入到数据库中。
            setRequestValue(joinPoint, operationLog);
        }
        // 是否需要保存response，参数和值
        if (log.isSaveResponseData() && StringUtils.isNotNull(jsonResult)) {
            operationLog.setJsonResult(StringUtils.substring(objectMapper.writeValueAsString(jsonResult), 0, 2000));
        }
    }

    /**
     * 获取请求的参数，放到log中
     * 
     * @param operationLog 操作日志
     * @throws Exception 异常
     */
    private void setRequestValue(JoinPoint joinPoint, SysOperationLog operationLog) throws Exception {
        String requestMethod = operationLog.getRequestMethod();
        if ("PUT".equals(requestMethod) || "POST".equals(requestMethod)) {
            String params = argsArrayToString(joinPoint.getArgs());
            operationLog.setOperationParam(StringUtils.substring(params, 0, 2000));
        } else {
            Map<?, ?> paramsMap = ServletUtils.getRequest().getParameterMap();
            operationLog.setOperationParam(StringUtils.substring(objectMapper.writeValueAsString(paramsMap), 0, 2000));
        }
    }

    /**
     * 参数拼装
     */
    private String argsArrayToString(Object[] paramsArray) {
        StringBuilder params = new StringBuilder();
        if (paramsArray != null && paramsArray.length > 0) {
            for (Object o : paramsArray) {
                if (StringUtils.isNotNull(o) && !isFilterObject(o)) {
                    try {
                        String jsonObj = objectMapper.writeValueAsString(o);
                        params.append(jsonObj.toString() + " ");
                    } catch (Exception e) {
                    }
                }
            }
        }
        return params.toString().trim();
    }

    /**
     * 判断是否需要过滤的对象。
     * 
     * @param o 对象信息。
     * @return 如果是需要过滤的对象，则返回true；否则返回false。
     */
    @SuppressWarnings("rawtypes")
    public boolean isFilterObject(final Object o) {
        Class<?> clazz = o.getClass();
        if (clazz.isArray()) {
            return clazz.getComponentType().isAssignableFrom(MultipartFile.class);
        } else if (Collection.class.isAssignableFrom(clazz)) {
            Collection collection = (Collection) o;
            for (Object value : collection) {
                return value instanceof MultipartFile;
            }
        } else if (Map.class.isAssignableFrom(clazz)) {
            Map map = (Map) o;
            for (Object value : map.entrySet()) {
                Map.Entry entry = (Map.Entry) value;
                return entry.getValue() instanceof MultipartFile;
            }
        }
        return o instanceof MultipartFile || o instanceof HttpServletRequest || o instanceof HttpServletResponse
                || o instanceof BindingResult;
    }
}
