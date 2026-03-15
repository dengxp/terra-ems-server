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

package com.terra.ems.ems.enums;

import lombok.Getter;

/**
 * 电价时段类型枚举
 * 用于分时电价（尖峰平谷）的时段分类
 *
 * @author dengxueping
 * @since 2026-01-11
 */

@Getter
public enum PeriodType {

    /**
     * 尖时段 - 最高电价
     */
    SHARP("尖", 1),

    /**
     * 峰时段 - 高电价
     */
    PEAK("峰", 2),

    /**
     * 平时段 - 正常电价
     */
    FLAT("平", 3),

    /**
     * 谷时段 - 低电价
     */
    VALLEY("谷", 4),

    /**
     * 深谷时段 - 最低电价
     */
    DEEP("深谷", 5);

    private final String label;
    private final int order;

    PeriodType(String label, int order) {
        this.label = label;
        this.order = order;
    }
}
