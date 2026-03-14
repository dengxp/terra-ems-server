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

import com.terra.ems.framework.cache.core.properties.CacheProperties;
import com.terra.ems.framework.cache.redis.enhance.TerraRedisCacheManager;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(CacheProperties.class)
@Import(RedisSessionSharingConfiguration.class)
@Slf4j
public class CacheRedisConfiguration {

    @PostConstruct
    public void postConstruct() {
        log.debug("[Terra] |- SDK [Cache Redis] Auto Configure.");
    }

    private RedisSerializer<String> keySerializer() {
        return new StringRedisSerializer();
    }

    /**
     * 命名为 springSessionDefaultRedisSerializer 是因为 Spring Session 会用到。
     * {@link org.springframework.session.data.redis.config.annotation.web.http.AbstractRedisHttpSessionConfiguration}
     * {@link org.springframework.session.data.redis.config.annotation.web.server.RedisWebSessionConfiguration}
     *
     * @return {@link RedisSerializer}
     */

    // @Bean(name = "springSessionDefaultRedisSerializer")
    private RedisSerializer<Object> valueSerializer() {
        RedisSerializer<Object> redisSerializer = new Jackson2JsonRedisSerializer<Object>(Object.class);
        log.trace("[Terra] |- Bean [Jackson2Json Redis Serializer] Auto Configure.");
        return redisSerializer;
    }

    /**
     * 重新配置一个RedisTemplate
     *
     * @return {@link RedisTemplate}
     */
    @Bean(name = "redisTemplate")
    @ConditionalOnMissingBean
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(keySerializer());
        redisTemplate.setHashKeySerializer(keySerializer());
        redisTemplate.setValueSerializer(valueSerializer());
        redisTemplate.setValueSerializer(valueSerializer());
        redisTemplate.setDefaultSerializer(valueSerializer());
        redisTemplate.afterPropertiesSet();

        log.trace("[Terra] |- Bean [Redis Template] Auto Configure.");

        return redisTemplate;
    }

    @Bean(name = "stringRedisTemplate")
    @ConditionalOnMissingBean
    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
        stringRedisTemplate.setConnectionFactory(redisConnectionFactory);
        stringRedisTemplate.afterPropertiesSet();
        log.trace("[Terra] |- Bean [String Redis Template] Auto Configure.");
        return stringRedisTemplate;
    }

    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(RedisConnectionFactory redisConnectionFactory) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory);
        log.trace("[Terra] |- Bean [Redis Message Listener Container] Auto Configure.");
        return container;
    }

    @Bean
    public RedisCacheManager redisCacheManager(RedisConnectionFactory redisConnectionFactory,
            CacheProperties cacheProperties) {
        RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory);

        // 注意：这里 RedisCacheConfiguration 每一个方法调用之后，都会返回一个新的 RedisCacheConfiguration
        // 对象，所以要注意对象的引用关系。
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(cacheProperties.getTtl());

        boolean allowNullValues = cacheProperties.getAllowNullValues();
        if (!allowNullValues) {
            // 注意：这里 RedisCacheConfiguration 每一个方法调用之后，都会返回一个新的 RedisCacheConfiguration
            // 对象，所以要注意对象的引用关系。
            redisCacheConfiguration = redisCacheConfiguration.disableCachingNullValues();
        }

        TerraRedisCacheManager terraRedisCacheManager = new TerraRedisCacheManager(redisCacheWriter,
                redisCacheConfiguration, cacheProperties);
        terraRedisCacheManager.setTransactionAware(false);
        terraRedisCacheManager.afterPropertiesSet();
        log.trace("[Terra] |- Bean [Redis Cache Manager] Auto Configure.");
        return terraRedisCacheManager;
    }

    @Configuration(proxyBeanMethods = false)
    @ComponentScan({
            "com.terra.ems.framework.cache.redis.utils"
    })
    static class RedisUtilConfiguration {

        @PostConstruct
        public void postConstruct() {
            log.debug("[Terra] |- SDK [Cache Redis Utils] Auto Configure.");
        }
    }
}
