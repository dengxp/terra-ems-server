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

package com.terra.ems.framework.cache.core.properties;

import org.apache.commons.lang3.ObjectUtils;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * Description: 自定义二级缓存过期时间通用属性
 * </p>
 */
public class Expire {

    // 统一缓存时间，默认：1
    private Long duration = 1L;

    // 统一缓存时长单位：默认：小时
    private TimeUnit unit = TimeUnit.HOURS;

    /**
     * Redis缓存TTL设置，默认：1小时，单位小时
     * <p>
     * 使用Duration类型，配置参数形式如下：
     * ”？ns" // 纳秒
     * "?us" // 微秒
     * “?ms” // 毫秒
     * "?s" // 秒
     * "?m" // 分
     * "?h" // 小时
     * "?d" // 天
     * </p>
     */
    private Duration ttl;

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public TimeUnit getUnit() {
        return unit;
    }

    public void setUnit(TimeUnit unit) {
        this.unit = unit;
    }

    public Duration getTtl() {
        if (ObjectUtils.isEmpty(this.ttl)) {
            this.ttl = convertToDuration(this.duration, this.unit);
        }
        return ttl;
    }

    private Duration convertToDuration(Long duration, TimeUnit timeUnit) {
        switch (timeUnit) {
            case DAYS:
                return Duration.ofDays(duration);
            case HOURS:
                return Duration.ofHours(duration);
            case SECONDS:
                return Duration.ofSeconds(duration);
            default:
                return Duration.ofMinutes(duration);
        }
    }
}
