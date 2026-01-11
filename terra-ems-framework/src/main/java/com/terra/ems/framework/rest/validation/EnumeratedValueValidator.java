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

package com.terra.ems.framework.rest.validation;

import com.terra.ems.framework.rest.annotation.EnumeratedValue;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.ArrayUtils;

import java.util.Arrays;

/**
 * 枚举值校验器
 *
 * @author dengxueping
 * @since 2026-01-11
 */

public class EnumeratedValueValidator implements ConstraintValidator<EnumeratedValue, Object> {

    private String[] names;
    private boolean unique;

    @Override
    public void initialize(EnumeratedValue constraintAnnotation) {
        this.names = constraintAnnotation.names();
        this.unique = constraintAnnotation.unique();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        if (ArrayUtils.isEmpty(names)) {
            return true;
        }

        String strValue = String.valueOf(value);
        if (unique) {
            return Arrays.asList(names).contains(strValue);
        } else {
            // 如果不要求唯一，这里暂时简单实现，可根据需求扩展
            return Arrays.asList(names).contains(strValue);
        }
    }
}
