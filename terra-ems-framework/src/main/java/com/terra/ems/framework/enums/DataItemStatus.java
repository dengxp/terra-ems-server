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

package com.terra.ems.framework.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.*;

/**
 * 数据状态
 *
 * @author dengxueping
 * @since 2026-01-11
 */

@Schema(title = "数据状态")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum DataItemStatus implements BaseUiEnum<Integer> {

    /**
     * 数据条目已启用
     */
    ENABLE(0, "启用"),
    /**
     * 数据条目被禁用
     */
    FORBIDDEN(1, "禁用");

    private static final Map<Integer, DataItemStatus> INDEX_MAP = new HashMap<>();
    private static final List<Map<String, Object>> JSON_STRUCTURE = new ArrayList<>();

    static {
        for (DataItemStatus dataItemStatus : DataItemStatus.values()) {
            INDEX_MAP.put(dataItemStatus.getValue(), dataItemStatus);

            Map<String, Object> item = new HashMap<>();
            item.put("value", dataItemStatus.getValue());
            item.put("key", dataItemStatus.name());
            item.put("text", dataItemStatus.getDescription());
            item.put("label", dataItemStatus.getDescription());
            JSON_STRUCTURE.add(item);
        }
    }

    @Schema(title = "枚举值")
    private final Integer value;
    @Schema(title = "文字")
    private final String description;

    DataItemStatus(Integer value, String description) {
        this.value = value;
        this.description = description;
    }

    public static DataItemStatus get(Integer index) {
        return INDEX_MAP.get(index);
    }

    public static List<Map<String, Object>> getPreprocessedJsonStructure() {
        return JSON_STRUCTURE;
    }

    @JsonCreator
    public static DataItemStatus fromValue(Integer value) {
        for (DataItemStatus status : values()) {
            if (status.getValue().equals(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid status value: " + value);
    }

    /**
     * 不加@JsonValue，转换的时候转换出完整的对象。
     * 加了@JsonValue，只会显示相应的属性的值
     * <p>
     * 不使用@JsonValue @JsonDeserializer类里面要做相应的处理
     *
     * @return Enum枚举值
     */
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
    public static class DataItemStatusConverter implements AttributeConverter<DataItemStatus, Integer> {

        @Override
        public Integer convertToDatabaseColumn(DataItemStatus attribute) {
            return attribute != null ? attribute.getValue() : null;
        }

        @Override
        public DataItemStatus convertToEntityAttribute(Integer dbData) {
            return dbData != null ? DataItemStatus.fromValue(dbData) : null;
        }
    }
}
