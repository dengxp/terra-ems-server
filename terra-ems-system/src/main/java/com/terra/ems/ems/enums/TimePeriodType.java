/*
 * Copyright (c) 2024 泰若科技（广州）有限公司. All rights reserved.
 * Copyright (c) 2024-2026 Terra EMS
 */
package com.terra.ems.ems.enums;

import lombok.Getter;

/**
 * Name: TimePeriodType.java
 * Email: dengxueping@gmail.com
 * Date: 2026-01-10
 * Description:
 * 分时电价时段类型枚举
 *
 * @author dengxueping
 */
@Getter
public enum TimePeriodType {

    SHARP("尖峰", 1.5),
    PEAK("高峰", 1.2),
    FLAT("平段", 1.0),
    VALLEY("低谷", 0.6),
    DEEP_VALLEY("深谷", 0.3);

    private final String label;
    private final double defaultMultiplier;

    TimePeriodType(String label, double defaultMultiplier) {
        this.label = label;
        this.defaultMultiplier = defaultMultiplier;
    }

    /**
     * 根据名称获取枚举
     */
    public static TimePeriodType fromName(String name) {
        for (TimePeriodType type : values()) {
            if (type.name().equalsIgnoreCase(name)) {
                return type;
            }
        }
        return FLAT;
    }
}
