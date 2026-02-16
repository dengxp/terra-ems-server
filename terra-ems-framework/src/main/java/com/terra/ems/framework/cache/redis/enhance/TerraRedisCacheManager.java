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
