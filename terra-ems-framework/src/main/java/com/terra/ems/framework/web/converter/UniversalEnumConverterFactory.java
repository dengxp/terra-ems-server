/*
 * Copyright (c) 2024-2026 Terra Technology (Guangzhou) Co., Ltd.
 * Copyright (c) 2024-2026 泰若科技（广州）有限公司.
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
