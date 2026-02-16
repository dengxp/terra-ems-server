package com.terra.ems.admin.controller;

import com.terra.ems.common.annotation.Log;
import com.terra.ems.common.constant.CacheConstants;
import com.terra.ems.common.domain.Result;
import com.terra.ems.common.enums.BusinessType;
import com.terra.ems.common.utils.StringUtils;
import com.terra.ems.system.domain.SysCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * 缓存监控
 * 
 * @author terra
 */
@RestController
@RequestMapping("/monitor/cache")
public class CacheController {

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    private final static List<SysCache> caches = new ArrayList<SysCache>();
    static {
        caches.add(new SysCache(CacheConstants.LOGIN_TOKEN_KEY, "用户信息"));
        caches.add(new SysCache(CacheConstants.SYS_CONFIG_KEY, "配置信息"));
        caches.add(new SysCache(CacheConstants.SYS_DICT_KEY, "数据字典"));
        caches.add(new SysCache(CacheConstants.CAPTCHA_CODE_KEY, "验证码"));
        caches.add(new SysCache(CacheConstants.REPEAT_SUBMIT_KEY, "防重提交"));
        caches.add(new SysCache(CacheConstants.RATE_LIMIT_KEY, "限流处理"));
        caches.add(new SysCache(CacheConstants.PWD_ERR_CNT_KEY, "密码错误次数"));
    }

    @PreAuthorize("@ss.hasPermi('monitor:cache:list')")
    @GetMapping()
    public Result<Map<String, Object>> getInfo() throws Exception {
        Properties info = (Properties) redisTemplate.execute((RedisCallback<Object>) RedisConnection::info);
        Properties commandStats = (Properties) redisTemplate
                .execute((RedisCallback<Object>) connection -> connection.info("commandstats"));
        Object dbSize = redisTemplate.execute((RedisCallback<Object>) RedisConnection::dbSize);

        Map<String, String> infoMap = new HashMap<>();
        if (info != null) {
            info.stringPropertyNames().forEach(name -> {
                infoMap.put(name, info.getProperty(name));
            });
        }

        Map<String, Object> result = new HashMap<>(3);
        result.put("info", infoMap);
        result.put("dbSize", String.valueOf(dbSize));

        List<Map<String, String>> pieList = new ArrayList<>();
        if (commandStats != null) {
            commandStats.stringPropertyNames().forEach(key -> {
                Map<String, String> data = new HashMap<>(2);
                String property = commandStats.getProperty(key);
                data.put("name", StringUtils.removeStart(key, "cmdstat_"));
                data.put("value", StringUtils.substringBetween(property, "calls=", ",usec"));
                pieList.add(data);
            });
        }
        result.put("commandStats", pieList);
        return Result.content(result);
    }

    @PreAuthorize("@ss.hasPermi('monitor:cache:list')")
    @GetMapping("/getNames")
    public Result<List<SysCache>> cache() {
        return Result.content(caches);
    }

    @PreAuthorize("@ss.hasPermi('monitor:cache:list')")
    @GetMapping("/getKeys/{cacheName}")
    public Result<Set<String>> getCacheKeys(@PathVariable String cacheName) {
        Set<Object> cacheKeys = redisTemplate.keys(cacheName + "*");
        Set<String> keys = new TreeSet<>();
        if (cacheKeys != null) {
            for (Object key : cacheKeys) {
                keys.add(String.valueOf(key));
            }
        }
        return Result.content(keys);
    }

    @PreAuthorize("@ss.hasPermi('monitor:cache:list')")
    @GetMapping("/getValue/{cacheName}/{cacheKey}")
    public Result<SysCache> getCacheValue(@PathVariable String cacheName, @PathVariable String cacheKey) {
        Object cacheValue = redisTemplate.opsForValue().get(cacheKey);
        SysCache sysCache = new SysCache(cacheName, cacheKey, String.valueOf(cacheValue));
        return Result.content(sysCache);
    }

    @PreAuthorize("@ss.hasPermi('monitor:cache:list')")
    @Log(title = "缓存监控", businessType = BusinessType.CLEAN)
    @DeleteMapping("/clearCacheName/{cacheName}")
    public Result<Void> clearCacheName(@PathVariable String cacheName) {
        Set<Object> cacheKeys = redisTemplate.keys(cacheName + "*");
        if (cacheKeys != null && !cacheKeys.isEmpty()) {
            redisTemplate.delete(cacheKeys);
        }
        return Result.success();
    }

    @PreAuthorize("@ss.hasPermi('monitor:cache:list')")
    @Log(title = "缓存监控", businessType = BusinessType.CLEAN)
    @DeleteMapping("/clearCacheKey/{cacheKey}")
    public Result<Void> clearCacheKey(@PathVariable String cacheKey) {
        redisTemplate.delete(cacheKey);
        return Result.success();
    }

    @PreAuthorize("@ss.hasPermi('monitor:cache:list')")
    @Log(title = "缓存监控", businessType = BusinessType.CLEAN)
    @DeleteMapping("/clearCacheAll")
    public Result<Void> clearCacheAll() {
        for (SysCache cache : caches) {
            Set<Object> cacheKeys = redisTemplate.keys(cache.getCacheName() + "*");
            if (cacheKeys != null && !cacheKeys.isEmpty()) {
                redisTemplate.delete(cacheKeys);
            }
        }
        return Result.success();
    }
}
