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

package com.terra.ems.framework.security.filter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationServiceException;

import com.terra.ems.common.constant.CacheConstants;

/**
 * 支持JSON格式的登录过滤器
 *
 * @author dengxueping
 * @since 2026-01-11
 */
public class JsonAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private StringRedisTemplate redisTemplate;

    public void setRedisTemplate(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        String username;
        String password;
        String captcha;
        String uuid;

        // 支持JSON格式登录
        if (request.getContentType() != null && request.getContentType().contains(MediaType.APPLICATION_JSON_VALUE)) {
            try {
                Map<String, String> loginData = objectMapper.readValue(request.getInputStream(),
                        new TypeReference<>() {
                        });
                username = loginData.getOrDefault("username", "");
                password = loginData.getOrDefault("password", "");
                captcha = loginData.getOrDefault("captcha", "");
                uuid = loginData.getOrDefault("uuid", "");
            } catch (IOException e) {
                username = "";
                password = "";
                captcha = "";
                uuid = "";
            }
        } else {
            // 兼容表单提交
            username = obtainUsername(request);
            password = obtainPassword(request);
            captcha = request.getParameter("captcha");
            uuid = request.getParameter("uuid");

            username = (username != null) ? username : "";
            password = (password != null) ? password : "";
            captcha = (captcha != null) ? captcha : "";
            uuid = (uuid != null) ? uuid : "";
        }

        // 验证码校验
        validateCaptcha(captcha, uuid);

        username = username.trim();

        UsernamePasswordAuthenticationToken authRequest = UsernamePasswordAuthenticationToken.unauthenticated(username,
                password);

        setDetails(request, authRequest);

        return this.getAuthenticationManager().authenticate(authRequest);
    }

    /**
     * 验证码校验
     *
     * @param code 用户输入的验证码
     * @param uuid 验证码标识
     */
    private void validateCaptcha(String code, String uuid) {
        // 验证码为空，抛出异常
        if (StringUtils.isEmpty(code)) {
            throw new AuthenticationServiceException("验证码不能为空");
        }

        // 验证码标识为空，说明验证码已失效
        if (StringUtils.isEmpty(uuid)) {
            throw new AuthenticationServiceException("验证码已失效");
        }

        // 从 Redis 获取验证码
        String verifyKey = CacheConstants.CAPTCHA_CODE_KEY + uuid;
        String captcha = redisTemplate.opsForValue().get(verifyKey);

        // 无论验证成功与否，都删除验证码（防止暴力破解）
        redisTemplate.delete(verifyKey);

        if (captcha == null) {
            throw new AuthenticationServiceException("验证码已失效");
        }

        if (!code.equalsIgnoreCase(captcha)) {
            throw new AuthenticationServiceException("验证码错误");
        }
    }
}
