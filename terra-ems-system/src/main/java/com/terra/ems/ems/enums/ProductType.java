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
 * 产品类型
 *
 * @author dengxueping
 * @since 2026-01-26
 */
@Schema(title = "产品类型")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ProductType implements BaseUiEnum<String> {

    /**
     * 成品
     */
    FINISHED("1", "成品"),
    /**
     * 半成品
     */
    SEMI_FINISHED("2", "半成品"),
    /**
     * 原材料
     */
    RAW_MATERIAL("3", "原材料");

    private static final Map<String, ProductType> INDEX_MAP = new HashMap<>();
    private static final List<Map<String, Object>> JSON_STRUCTURE = new ArrayList<>();

    static {
        for (ProductType type : ProductType.values()) {
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

    ProductType(String value, String description) {
        this.value = value;
        this.description = description;
    }

    public static ProductType get(String index) {
        return INDEX_MAP.get(index);
    }

    public static List<Map<String, Object>> getPreprocessedJsonStructure() {
        return JSON_STRUCTURE;
    }

    @JsonCreator
    public static ProductType fromValue(String value) {
        if (value == null) {
            return null;
        }
        for (ProductType type : values()) {
            if (type.getValue().equals(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid ProductType value: " + value);
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
    public static class ProductTypeConverter implements AttributeConverter<ProductType, String> {

        @Override
        public String convertToDatabaseColumn(ProductType attribute) {
            return attribute != null ? attribute.getValue() : null;
        }

        @Override
        public ProductType convertToEntityAttribute(String dbData) {
            return dbData != null ? ProductType.fromValue(dbData) : null;
        }
    }
}
