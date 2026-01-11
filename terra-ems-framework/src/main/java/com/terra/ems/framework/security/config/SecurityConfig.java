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

package com.terra.ems.framework.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.terra.ems.common.constant.ErrorCodes;
import com.terra.ems.common.domain.Result;
import com.terra.ems.framework.security.filter.JsonAuthenticationFilter;
import com.terra.ems.framework.security.session.HeaderSessionIdResolver;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.DelegatingSecurityContextRepository;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisIndexedHttpSession;
import org.springframework.session.web.http.HttpSessionIdResolver;

import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * Security配置
 *
 * @author dengxueping
 * @since 2026-01-11
 */
@Configuration
@EnableWebSecurity
@EnableRedisIndexedHttpSession(redisNamespace = "terra:ems:session")
public class SecurityConfig {

    private final ObjectMapper objectMapper;
    private final StringRedisTemplate redisTemplate;

    public SecurityConfig(ObjectMapper objectMapper, StringRedisTemplate redisTemplate) {
        this.objectMapper = objectMapper;
        this.redisTemplate = redisTemplate;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http,
            AuthenticationConfiguration authConfig,
            DaoAuthenticationProvider daoAuthenticationProvider) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/error", "/captcha/**", "/system/constant/**",
                                "/sms/**")
                        .permitAll()
                        .anyRequest().authenticated())
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint((request, response, authException) -> writeJsonResponse(response,
                                Result.failure(ErrorCodes.UNAUTHORIZED)))
                        .accessDeniedHandler((request, response, accessDeniedException) -> writeJsonResponse(response,
                                Result.failure(ErrorCodes.FORBIDDEN))))
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessHandler((request, response, authentication) -> writeJsonResponse(response,
                                Result.success("登出成功"))))
                .authenticationProvider(daoAuthenticationProvider)
                .securityContext(context -> context.securityContextRepository(securityContextRepository()))
                .addFilterAt(jsonAuthenticationFilter(authConfig, securityContextRepository()),
                        UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public SecurityContextRepository securityContextRepository() {
        return new DelegatingSecurityContextRepository(
                new HttpSessionSecurityContextRepository(),
                new RequestAttributeSecurityContextRepository());
    }

    @Bean
    public JsonAuthenticationFilter jsonAuthenticationFilter(AuthenticationConfiguration authConfig,
            SecurityContextRepository securityContextRepository) throws Exception {
        JsonAuthenticationFilter filter = new JsonAuthenticationFilter();
        filter.setFilterProcessesUrl("/login");
        filter.setAuthenticationManager(authConfig.getAuthenticationManager());
        filter.setRedisTemplate(redisTemplate);
        filter.setSecurityContextRepository(securityContextRepository);

        // 登录成功处理
        filter.setAuthenticationSuccessHandler((request, response, authentication) -> {
            HttpSession session = request.getSession();
            String token = session.getId();

            Map<String, Object> data = new HashMap<>();
            data.put("token", token);
            data.put("username", authentication.getName());
            writeJsonResponse(response, Result.success("登录成功", data));
        });

        // 登录失败处理 - 使用实际的异常信息
        filter.setAuthenticationFailureHandler((request, response, authException) -> {
            String message = authException.getMessage();
            // 如果是 Spring Security 默认的 Bad credentials 消息，转换为中文
            if ("Bad credentials".equals(message)) {
                message = "用户名或密码错误";
            }
            writeJsonResponse(response, Result.failure(message));
        });

        return filter;
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(UserDetailsService userDetailsService) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public HttpSessionIdResolver httpSessionIdResolver() {
        return HeaderSessionIdResolver.authToken();
    }

    /**
     * 写入JSON响应
     */
    private void writeJsonResponse(HttpServletResponse response, Result<?> result) {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setStatus(result.getStatus() > 0 ? result.getStatus() : HttpServletResponse.SC_OK);

        try (PrintWriter writer = response.getWriter()) {
            writer.write(objectMapper.writeValueAsString(result));
            writer.flush();
        } catch (Exception e) {
            throw new RuntimeException("Failed to write JSON response", e);
        }
    }
}
