package com.terra.ems.framework.cache.redis.config;

import com.terra.ems.framework.cache.redis.annotation.ConditionalOnRedisSessionSharing;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.FlushMode;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisIndexedHttpSession;
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
    @EnableRedisIndexedHttpSession(flushMode = FlushMode.IMMEDIATE)
    static class TerraRedisHttpSessionConfiguration {
        @Bean
        public CookieSerializer cookieSerializer() {
            DefaultCookieSerializer cookieSerializer = new DefaultCookieSerializer();
            cookieSerializer.setUseHttpOnlyCookie(false);
            cookieSerializer.setSameSite(null);
            log.trace("[Terra] |- Bean [Cookie Serializer] Auto Configure.");

            return cookieSerializer;
        }
    }
}
