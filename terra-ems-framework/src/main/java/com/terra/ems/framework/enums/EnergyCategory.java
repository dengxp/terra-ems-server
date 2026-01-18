/*
 * Copyright (c) 2024 泰若科技（广州）有限公司. All rights reserved.
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

package com.terra.ems.framework.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.*;

/**
 * 能源类别
 *
 * @author dengxueping
 * @since 2026-01-18
 */

@Schema(title = "能源类别")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum EnergyCategory implements BaseUiEnum<String> {

    /**
     * 电力
     */
    ELECTRIC("ELECTRIC", "电力"),
    /**
     * 燃气
     */
    GAS("GAS", "燃气"),
    /**
     * 蒸汽
     */
    STEAM("STEAM", "蒸汽"),
    /**
     * 水
     */
    WATER("WATER", "水"),
    /**
     * 其他
     */
    OTHER("OTHER", "其他");

    private static final Map<String, EnergyCategory> INDEX_MAP = new HashMap<>();
    private static final List<Map<String, Object>> JSON_STRUCTURE = new ArrayList<>();

    static {
        for (EnergyCategory category : EnergyCategory.values()) {
            INDEX_MAP.put(category.getValue(), category);

            Map<String, Object> item = new HashMap<>();
            item.put("value", category.getValue());
            item.put("key", category.name());
            item.put("text", category.getDescription());
            JSON_STRUCTURE.add(item);
        }
    }

    @Schema(title = "枚举值")
    private final String value;
    @Schema(title = "文字")
    private final String description;

    EnergyCategory(String value, String description) {
        this.value = value;
        this.description = description;
    }

    public static EnergyCategory get(String index) {
        return INDEX_MAP.get(index);
    }

    public static List<Map<String, Object>> getPreprocessedJsonStructure() {
        return JSON_STRUCTURE;
    }

    @JsonCreator
    public static EnergyCategory fromValue(String value) {
        if (value == null) {
            return null;
        }
        for (EnergyCategory category : values()) {
            if (category.getValue().equals(value)) {
                return category;
            }
        }
        throw new IllegalArgumentException("Invalid EnergyCategory value: " + value);
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
    public static class EnergyCategoryConverter implements AttributeConverter<EnergyCategory, String> {

        @Override
        public String convertToDatabaseColumn(EnergyCategory attribute) {
            return attribute != null ? attribute.getValue() : null;
        }

        @Override
        public EnergyCategory convertToEntityAttribute(String dbData) {
            return dbData != null ? EnergyCategory.fromValue(dbData) : null;
        }
    }
}
