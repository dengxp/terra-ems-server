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

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;
import com.terra.ems.framework.enums.BaseUiEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用能单元类型
 *
 * @author dengxueping
 * @since 2026-01-11
 */

@Schema(title = "用能单元类型")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum EnergyUnitType implements BaseUiEnum<String> {

    /**
     * 普通单元
     */
    GENERAL("GENERAL", "普通单元"),
    /**
     * 电力支路
     */
    BRANCH("BRANCH", "电力支路"),
    /**
     * 工序
     */
    PROCESS("PROCESS", "工序"),
    /**
     * 设备
     */
    EQUIPMENT("EQUIPMENT", "设备");

    private static final Map<String, EnergyUnitType> INDEX_MAP = new HashMap<>();
    private static final List<Map<String, Object>> JSON_STRUCTURE = new ArrayList<>();

    static {
        for (EnergyUnitType type : EnergyUnitType.values()) {
            INDEX_MAP.put(type.getValue(), type);

            Map<String, Object> item = new HashMap<>();
            item.put("value", type.getValue());
            item.put("key", type.name());
            item.put("text", type.getDescription());
            item.put("label", type.getDescription());
            JSON_STRUCTURE.add(item);
        }
    }

    @Schema(title = "枚举值")
    private final String value;
    @Schema(title = "文字")
    private final String description;

    EnergyUnitType(String value, String description) {
        this.value = value;
        this.description = description;
    }

    public static EnergyUnitType get(String index) {
        return INDEX_MAP.get(index);
    }

    public static List<Map<String, Object>> getPreprocessedJsonStructure() {
        return JSON_STRUCTURE;
    }

    @JsonCreator
    public static EnergyUnitType fromValue(String value) {
        if (value == null) {
            return null;
        }
        for (EnergyUnitType type : values()) {
            if (type.getValue().equals(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid EnergyUnitType value: " + value);
    }

    @JsonValue
    @Override
    public String getValue() {
        return value;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Converter(autoApply = true)
    public static class EnergyUnitTypeConverter implements AttributeConverter<EnergyUnitType, String> {

        @Override
        public String convertToDatabaseColumn(EnergyUnitType attribute) {
            return attribute != null ? attribute.getValue() : null;
        }

        @Override
        public EnergyUnitType convertToEntityAttribute(String dbData) {
            return dbData != null ? EnergyUnitType.fromValue(dbData) : null;
        }
    }
}
