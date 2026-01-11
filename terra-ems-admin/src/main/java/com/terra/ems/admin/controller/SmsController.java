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

package com.terra.ems.admin.controller;

import com.terra.ems.common.domain.Result;
import com.terra.ems.framework.controller.Controller;
import com.terra.ems.framework.domain.dto.SmsCodeRequest;
import com.terra.ems.framework.domain.dto.SmsLoginRequest;
import com.terra.ems.framework.service.SmsService;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.terra.ems.system.entity.SysUser;
import com.terra.ems.system.service.SysUserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 短信相关接口控制器
 *
 * @author dengxueping
 * @since 2026-01-11
 */
@Tag(name = "短信服务")
@RestController
@RequestMapping("/sms")
public class SmsController extends Controller {

    private static final Logger log = LoggerFactory.getLogger(SmsController.class);

    private final SmsService smsService;
    private final SysUserService userService;

    public SmsController(SmsService smsService, SysUserService userService) {
        this.smsService = smsService;
        this.userService = userService;
    }

    /**
     * 发送短信验证码
     *
     * @param request 短信验证码请求对象
     * @return 操作结果
     */
    @PostMapping("/send")
    public Result<Map<String, Object>> sendCode(@Valid @RequestBody SmsCodeRequest request) {
        boolean success = smsService.sendCode(request.phoneNumber());

        Map<String, Object> data = new HashMap<>();
        data.put("message", "验证码已发送");

        return success ? Result.content(data) : Result.failure("发送验证码失败");
    }

    /**
     * 手机号登录
     *
     * @param request     短信登录请求对象
     * @param httpRequest HTTP 请求对象
     * @return 包含 Token 和用户信息的结果
     */
    @PostMapping("/login")
    public Result<Map<String, Object>> loginBySms(@Valid @RequestBody SmsLoginRequest request,
            HttpServletRequest httpRequest) {
        // 验证验证码
        boolean isValidCode = smsService.verifyCode(request.phone(), request.code());
        if (!isValidCode) {
            return Result.failure("验证码错误或已过期");
        }

        // 根据手机号查找用户
        SysUser user = userService.findByPhone(request.phone());
        if (user == null) {
            return Result.failure("该手机号未绑定账号，请联系管理员");
        }

        if (!user.isEnabled()) {
            return Result.failure("账号已被禁用");
        }

        // 清除已使用的验证码
        smsService.clearCode(request.phone());

        // 设置认证信息
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                user.getUsername(),
                null,
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);

        // 将Context保存到Session
        HttpSession session = httpRequest.getSession(true);
        session.setAttribute("SPRING_SECURITY_CONTEXT", context);

        // 获取Token (Session ID)
        String token = session.getId();

        // 构造返回数据
        Map<String, Object> data = new HashMap<>();
        data.put("token", token);
        data.put("username", user.getUsername());
        data.put("phone", user.getPhone());

        log.info("用户 {} 通过手机号 {} 登录成功", user.getUsername(), request.phone());

        return Result.content(data);
    }
}
