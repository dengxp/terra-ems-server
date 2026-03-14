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

package com.terra.ems.admin.controller;

import com.terra.ems.common.annotation.Log;
import com.terra.ems.common.constant.CacheConstants;
import com.terra.ems.common.domain.Result;
import com.terra.ems.common.enums.BusinessType;
import com.terra.ems.common.utils.StringUtils;
import com.terra.ems.system.domain.SysCache;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "系统监控-缓存监控")
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

    @Operation(summary = "查询缓存状态")
    @PreAuthorize("hasPerm('monitor:cache:list')")
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

    @Operation(summary = "查询缓存列表")
    @PreAuthorize("hasPerm('monitor:cache:list')")
    @GetMapping("/getNames")
    public Result<List<SysCache>> cache() {
        return Result.content(caches);
    }

    @Operation(summary = "查询缓存键名列表")
    @PreAuthorize("hasPerm('monitor:cache:list')")
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

    @Operation(summary = "获取缓存指定键值")
    @PreAuthorize("hasPerm('monitor:cache:list')")
    @GetMapping("/getValue/{cacheName}/{cacheKey}")
    public Result<SysCache> getCacheValue(@PathVariable String cacheName, @PathVariable String cacheKey) {
        Object cacheValue = redisTemplate.opsForValue().get(cacheKey);
        SysCache sysCache = new SysCache(cacheName, cacheKey, String.valueOf(cacheValue));
        return Result.content(sysCache);
    }

    @Operation(summary = "清理指定缓存")
    @PreAuthorize("hasPerm('monitor:cache:remove')")
    @Log(title = "缓存监控", businessType = BusinessType.CLEAN)
    @DeleteMapping("/clearCacheName/{cacheName}")
    public Result<Void> clearCacheName(@PathVariable String cacheName) {
        Set<Object> cacheKeys = redisTemplate.keys(cacheName + "*");
        if (cacheKeys != null && !cacheKeys.isEmpty()) {
            redisTemplate.delete(cacheKeys);
        }
        return Result.success();
    }

    @Operation(summary = "清理缓存键")
    @PreAuthorize("hasPerm('monitor:cache:remove')")
    @Log(title = "缓存监控", businessType = BusinessType.CLEAN)
    @DeleteMapping("/clearCacheKey/{cacheKey}")
    public Result<Void> clearCacheKey(@PathVariable String cacheKey) {
        redisTemplate.delete(cacheKey);
        return Result.success();
    }

    @Operation(summary = "清空所有缓存")
    @PreAuthorize("hasPerm('monitor:cache:remove')")
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
