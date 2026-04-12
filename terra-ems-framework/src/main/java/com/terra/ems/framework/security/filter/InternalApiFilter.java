package com.terra.ems.framework.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * 内部服务 API 认证过滤器
 * 
 * 专门用于校验 Processor 或 Gateway 发起的内部请求。
 * 校验 Header 中的 X-Internal-Token 是否匹配配置的密钥。
 */
@Component
public class InternalApiFilter extends OncePerRequestFilter {

    private static final String INTERNAL_TOKEN_HEADER = "X-Internal-Token";

    @Value("${terra.api.secret}")
    private String internalSecret;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        String path = request.getServletPath();
        
        // 只拦截以 /processor/ 或 /collector/ 开头的路径
        if (path.startsWith("/processor/") || path.startsWith("/collector/")) {
            String token = request.getHeader(INTERNAL_TOKEN_HEADER);
            
            if (StringUtils.hasText(internalSecret) && internalSecret.equals(token)) {
                // 校验通过，直接放行
                filterChain.doFilter(request, response);
                return;
            }
            
            // 校验失败，返回 401
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("{\"code\": 401, \"msg\": \"Internal API Unauthorized: Missing or invalid token\"}");
            return;
        }

        // 非内部接口，交给下一个过滤器（Spring Security）处理
        filterChain.doFilter(request, response);
    }
}
