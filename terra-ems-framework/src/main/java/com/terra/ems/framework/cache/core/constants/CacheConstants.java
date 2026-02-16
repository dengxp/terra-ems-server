package com.terra.ems.framework.cache.core.constants;

import com.terra.ems.common.constant.BaseConstants;

public interface CacheConstants extends BaseConstants {

    /* ---------- 通用缓存常量 ---------- */

    String CACHE_PREFIX = "cache:";

    String CACHE_SIMPLE_BASE_PREFIX = CACHE_PREFIX + "simple:";
    String CACHE_TOKEN_BASE_PREFIX = CACHE_PREFIX + "token:";

    String AREA_PREFIX = "data:";

    /* ---------- Terra Cache配置属性 ---------- */
    String PROPERTY_PREFIX_CACHE = PROPERTY_PREFIX_TERRA + ".cache";

    String PROPERTY_REDIS_REDISSON = PROPERTY_SPRING_DATA + ".redisson";

    String ITEM_REDISSON_ENABLED = PROPERTY_REDIS_REDISSON + PROPERTY_ENABLED;
}
