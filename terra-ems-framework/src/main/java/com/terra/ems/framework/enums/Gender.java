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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 性别枚举
 *
 * @author dengxueping
 * @since 2026-01-11
 */

@Schema(title = "性别")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Gender implements BaseUiEnum<Integer> {

    /**
     * 男
     */
    MAN(0, "男"),
    /**
     * 女
     */
    WOMAN(1, "女"),
    /**
     * 未知
     */
    UNKNOWN(2, "未知");

    private static final Map<Integer, Gender> INDEX_MAP = new HashMap<>();
    private static final List<Map<String, Object>> JSON_STRUCTURE = new ArrayList<>();

    static {
        for (Gender gender : Gender.values()) {
            INDEX_MAP.put(gender.getValue(), gender);

            Map<String, Object> item = new HashMap<>();
            item.put("value", gender.getValue());
            item.put("key", gender.name());
            item.put("text", gender.getDescription());
            item.put("label", gender.getDescription());
            JSON_STRUCTURE.add(item);
        }
    }

    @Schema(title = "枚举值")
    private final Integer value;
    @Schema(title = "文字")
    private final String description;

    Gender(Integer value, String description) {
        this.value = value;
        this.description = description;
    }

    public static Gender get(Integer index) {
        return INDEX_MAP.get(index);
    }

    public static List<Map<String, Object>> getPreprocessedJsonStructure() {
        return JSON_STRUCTURE;
    }

    @JsonCreator
    public static Gender fromValue(Integer value) {
        for (Gender gender : values()) {
            if (gender.getValue().equals(value)) {
                return gender;
            }
        }
        throw new IllegalArgumentException("Invalid gender value: " + value);
    }

    @JsonValue
    @Override
    public Integer getValue() {
        return value;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Converter(autoApply = true)
    public static class GenderConverter implements AttributeConverter<Gender, Integer> {

        @Override
        public Integer convertToDatabaseColumn(Gender attribute) {
            return attribute != null ? attribute.getValue() : null;
        }

        @Override
        public Gender convertToEntityAttribute(Integer dbData) {
            return dbData != null ? Gender.fromValue(dbData) : null;
        }
    }
}
