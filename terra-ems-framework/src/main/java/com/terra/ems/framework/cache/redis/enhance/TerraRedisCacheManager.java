/*
 * Copyright (c) 2025-2026 Terra Technology (Guangzhou) Co., Ltd.
 * Copyright (c) 2025-2026 泰若科技（广州）有限公司.
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

package com.terra.ems.framework.cache.redis.enhance;

import com.terra.ems.framework.cache.core.properties.CacheProperties;
import com.terra.ems.framework.cache.core.properties.Expire;
import com.terra.ems.common.constant.SymbolConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;

import java.util.Map;

@Slf4j
public class TerraRedisCacheManager extends RedisCacheManager {

    private CacheProperties cacheProperties;

    public TerraRedisCacheManager(RedisCacheWriter cacheWriter, RedisCacheConfiguration defaultCacheConfiguration,
            CacheProperties cacheProperties) {
        super(cacheWriter, defaultCacheConfiguration);
        this.cacheProperties = cacheProperties;
    }

    public TerraRedisCacheManager(RedisCacheWriter cacheWriter, RedisCacheConfiguration defaultCacheConfiguration,
            CacheProperties cacheProperties, String... initialCacheNames) {
        super(cacheWriter, defaultCacheConfiguration, initialCacheNames);
        this.cacheProperties = cacheProperties;
    }

    public TerraRedisCacheManager(RedisCacheWriter cacheWriter, RedisCacheConfiguration defaultCacheConfiguration,
            boolean allowInFlightCacheCreation, CacheProperties cacheProperties, String... initialCacheNames) {
        super(cacheWriter, defaultCacheConfiguration, allowInFlightCacheCreation, initialCacheNames);
        this.cacheProperties = cacheProperties;
    }

    public TerraRedisCacheManager(RedisCacheWriter cacheWriter, RedisCacheConfiguration defaultCacheConfiguration,
            Map<String, RedisCacheConfiguration> initialCacheConfigurations, CacheProperties cacheProperties) {
        super(cacheWriter, defaultCacheConfiguration, initialCacheConfigurations);
        this.cacheProperties = cacheProperties;
    }

    public TerraRedisCacheManager(RedisCacheWriter cacheWriter, RedisCacheConfiguration defaultCacheConfiguration,
            Map<String, RedisCacheConfiguration> initialCacheConfigurations, boolean allowInFlightCacheCreation,
            CacheProperties cacheProperties) {
        super(cacheWriter, defaultCacheConfiguration, initialCacheConfigurations, allowInFlightCacheCreation);
        this.cacheProperties = cacheProperties;
    }

    @Override
    protected RedisCache createRedisCache(String name, RedisCacheConfiguration cacheConfig) {
        Map<String, Expire> expires = cacheProperties.getExpires();
        if (MapUtils.isNotEmpty(expires)) {
            String key = StringUtils.replace(name, SymbolConstants.COLON, cacheProperties.getSeparator());
            if (expires.containsKey(key)) {
                Expire expire = expires.get(key);
                log.debug("[Terra] |- CACHE - Redis cache [{}] is set to use CUSTOM expire.", name);
                cacheConfig = cacheConfig.entryTtl(expire.getTtl());
            }
        }
        return super.createRedisCache(name, cacheConfig);
    }
}
