/*
 * Copyright (c) 2024 泰若科技（广州）有限公司. All rights reserved.
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

package com.terra.ems.framework.web;

import com.terra.ems.common.constant.ErrorCodes;
import com.terra.ems.common.domain.Result;
import com.terra.ems.common.exception.TerraException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 全局异常解析器
 *
 * @author dengxueping
 * @since 2026-01-11
 */
@Slf4j
public class GlobalExceptionResolver {

    private static final Map<String, ErrorCodes> EXCEPTION_DICTIONARY = new HashMap<>();

    static {
        // 安全相关
        EXCEPTION_DICTIONARY.put("AccessDeniedException", ErrorCodes.ACCESS_DENIED);
        EXCEPTION_DICTIONARY.put("InsufficientAuthenticationException", ErrorCodes.UNAUTHORIZED);

        // 数据库相关
        EXCEPTION_DICTIONARY.put("DataIntegrityViolationException", ErrorCodes.INTERNAL_SERVER_ERROR);
        EXCEPTION_DICTIONARY.put("BadSqlGrammarException", ErrorCodes.INTERNAL_SERVER_ERROR);

        // 参数与请求相关
        EXCEPTION_DICTIONARY.put("MethodArgumentNotValidException", ErrorCodes.INTERNAL_SERVER_ERROR); // 通常在 Handler
                                                                                                       // 中特殊处理
        EXCEPTION_DICTIONARY.put("BindException", ErrorCodes.INTERNAL_SERVER_ERROR);
        EXCEPTION_DICTIONARY.put("HttpRequestMethodNotSupportedException", ErrorCodes.INTERNAL_SERVER_ERROR);
        EXCEPTION_DICTIONARY.put("HttpMediaTypeNotSupportedException", ErrorCodes.INTERNAL_SERVER_ERROR);
        EXCEPTION_DICTIONARY.put("HttpMessageNotReadableException", ErrorCodes.INTERNAL_SERVER_ERROR);
        EXCEPTION_DICTIONARY.put("MissingServletRequestParameterException", ErrorCodes.INTERNAL_SERVER_ERROR);

        // 业务/逻辑相关
        EXCEPTION_DICTIONARY.put("IllegalArgumentException", ErrorCodes.BAD_REQUEST);
        EXCEPTION_DICTIONARY.put("NullPointerException", ErrorCodes.INTERNAL_SERVER_ERROR);
    }

    /**
     * 解析异常并返回统一响应对象
     *
     * @param ex   异常
     * @param path 请求路径
     * @return Result
     */
    public static Result<String> resolveException(Exception ex, String path) {
        log.trace("[Terra] |- 全局解析器捕获异常, 路径: [{}], 异常信息: ", path, ex);

        if (ex instanceof TerraException terraException) {
            Result<String> result = terraException.getResult();
            result.path(path);
            log.error("[Terra] |- 业务异常: {}", result.getMessage());
            return result;
        } else {
            Result<String> result = Result.failure();
            String exceptionName = ex.getClass().getSimpleName();

            // 特殊处理数据完整性违规异常（如唯一约束冲突）
            if ("DataIntegrityViolationException".equals(exceptionName)) {
                log.error("[Terra] |- 数据完整性异常详情: ", ex);
                String message = extractConstraintViolationMessage(ex);
                result = Result.failure(ErrorCodes.INTERNAL_SERVER_ERROR);
                result.message(message);
                result.path(path);
                result.detail(ex.getMessage());
                log.error("[Terra] |- 数据完整性异常: {}", message);
                return result;
            }

            if (StringUtils.isNotEmpty(exceptionName) && EXCEPTION_DICTIONARY.containsKey(exceptionName)) {
                ErrorCodes errorCode = EXCEPTION_DICTIONARY.get(exceptionName);
                result = Result.failure(errorCode);
            } else {
                log.warn("[Terra] |- 字典中未找到异常名 [{}], 使用默认错误响应", exceptionName);
                // 对于未定义的异常，如果存在 Message，则透传给 Result
                if (StringUtils.isNotBlank(ex.getMessage())) {
                    result.message(ex.getMessage());
                }
            }

            result.path(path);
            // 对于业务异常（如 IllegalArgumentException），始终透传异常消息
            if (StringUtils.isNotBlank(ex.getMessage())) {
                result.message(ex.getMessage());
            }
            result.detail(ex.getMessage());

            log.error("[Terra] |- 系统异常 [{}]: {}", exceptionName, ex.getMessage());
            return result;
        }
    }

    /**
     * 从数据完整性违规异常中提取用户友好的错误信息
     *
     * @param ex 异常
     * @return 用户友好的错误信息
     */
    private static String extractConstraintViolationMessage(Exception ex) {
        String message = ex.getMessage();
        Throwable cause = ex.getCause();

        // 遍历异常链查找 ConstraintViolationException
        while (cause != null) {
            String causeName = cause.getClass().getSimpleName();
            String causeMessage = cause.getMessage();

            // PostgreSQL/Hibernate 唯一约束冲突
            if ("ConstraintViolationException".equals(causeName) ||
                    "PSQLException".equals(causeName)) {

                if (causeMessage != null) {
                    // 匹配常见的唯一约束违规模式
                    if (causeMessage.contains("duplicate key") ||
                            causeMessage.contains("unique constraint") ||
                            causeMessage.contains("重复键违反唯一约束")) {
                        return "数据重复，已存在相同的记录";
                    }
                    if (causeMessage.contains("foreign key constraint") ||
                            causeMessage.contains("违反外键约束")) {
                        return "操作失败，该数据正在被其他记录引用";
                    }
                }
            }
            cause = cause.getCause();
        }

        // 默认返回通用消息
        return "数据保存失败，请检查输入数据是否有效";
    }
}
