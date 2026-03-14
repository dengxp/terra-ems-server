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

package com.terra.ems.framework.security.session;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.session.web.http.HttpSessionIdResolver;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.List;

/**
 * 从HTTP Header中解析Session ID
 *
 * @author dengxueping
 * @since 2026-01-11
 */
public class HeaderSessionIdResolver implements HttpSessionIdResolver {

    private static final Logger log = LoggerFactory.getLogger(HeaderSessionIdResolver.class);
    private static final String DEFAULT_HEADER_NAME = "X-Auth-Token";

    private final String headerName;

    public HeaderSessionIdResolver(String headerName) {
        Assert.notNull(headerName, "headerName cannot be null");
        this.headerName = headerName;
    }

    public static HeaderSessionIdResolver authToken() {
        return new HeaderSessionIdResolver(DEFAULT_HEADER_NAME);
    }

    @Override
    public List<String> resolveSessionIds(HttpServletRequest request) {
        String headerValue = request.getHeader(this.headerName);
        log.debug(">>> HeaderSessionIdResolver: URI={}, Header[{}]={}",
                request.getRequestURI(), this.headerName, headerValue);
        return (headerValue != null) ? Collections.singletonList(headerValue) : Collections.emptyList();
    }

    @Override
    public void setSessionId(HttpServletRequest request, HttpServletResponse response, String sessionId) {
        response.setHeader(this.headerName, sessionId);
    }

    @Override
    public void expireSession(HttpServletRequest request, HttpServletResponse response) {
        response.setHeader(this.headerName, "");
    }
}
