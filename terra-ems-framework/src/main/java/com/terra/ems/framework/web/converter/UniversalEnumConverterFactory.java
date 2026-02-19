package com.terra.ems.framework.web.converter;

import com.terra.ems.framework.enums.EnumValue;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * 通用枚举转换工厂
 *
 * @author dengxueping
 * @since 2026-02-19
 */
public class UniversalEnumConverterFactory implements ConverterFactory<String, EnumValue<?>> {

    @Override
    public <T extends EnumValue<?>> Converter<String, T> getConverter(Class<T> targetType) {
        return new StringToEnumConverter<>(targetType);
    }

    private static class StringToEnumConverter<T extends EnumValue<?>> implements Converter<String, T> {
        private final Class<T> enumType;
        private final Map<String, T> constantMap = new HashMap<>();

        public StringToEnumConverter(Class<T> enumType) {
            this.enumType = enumType;
            T[] constants = enumType.getEnumConstants();
            for (T constant : constants) {
                constantMap.put(String.valueOf(constant.getValue()), constant);
            }
        }

        @Override
        public T convert(String source) {
            if (source == null || source.isEmpty()) {
                return null;
            }

            T t = constantMap.get(source);
            if (t == null) {
                // 尝试通过名称匹配 (兼容依然传名称的情况)
                try {
                    @SuppressWarnings({ "unchecked", "rawtypes" })
                    Enum<?> enumValue = Enum.valueOf((Class<Enum>) (Class<?>) enumType, source);
                    t = (T) enumValue;
                } catch (IllegalArgumentException e) {
                    // ignore
                }
            }
            if (t == null) {
                throw new IllegalArgumentException("No enum constant " + enumType.getCanonicalName() + "." + source);
            }
            return t;
        }
    }
}
