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

package com.terra.ems.framework.cache.redis.config;

import com.terra.ems.framework.cache.redis.annotation.ConditionalOnRedisSessionSharing;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.FlushMode;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisIndexedHttpSession;
import org.springframework.session.data.redis.config.ConfigureRedisAction;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;

@Configuration(proxyBeanMethods = false)
@ConditionalOnRedisSessionSharing
@Slf4j
public class RedisSessionSharingConfiguration {

    @PostConstruct
    public void postConstruct() {
        log.debug("[Terra] |- SDK [Cache Redis Session Sharing] Auto Configure.");
    }

    @Configuration(proxyBeanMethods = false)
    @ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
    @EnableRedisIndexedHttpSession(flushMode = FlushMode.IMMEDIATE, redisNamespace = "terra:ems:session")
    static class TerraRedisHttpSessionConfiguration {
        @Bean
        public CookieSerializer cookieSerializer() {
            DefaultCookieSerializer cookieSerializer = new DefaultCookieSerializer();
            cookieSerializer.setUseHttpOnlyCookie(false);
            cookieSerializer.setSameSite(null);
            log.trace("[Terra] |- Bean [Cookie Serializer] Auto Configure.");

            return cookieSerializer;
        }

        @Bean
        public static ConfigureRedisAction configureRedisAction() {
            return ConfigureRedisAction.NO_OP;
        }
    }
}
