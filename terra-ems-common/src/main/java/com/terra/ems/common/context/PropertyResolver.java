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

package com.terra.ems.common.context;

import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;

public class PropertyResolver {

    /**
     * 从环境信息中获取配置信息
     * 
     * @param environment SpringBoot Enviroment {@link Environment}
     * @param property    属性名称
     * @return 属性值
     */
    public static String getProperty(Environment environment, String property) {
        return environment.getProperty(property);
    }

    /**
     * 从环境中获取配置信息
     * 
     * @param environment  SpringBoot Enviroment {@link Environment}
     * @param property     属性名称
     * @param defaultValue 默认值
     * @return 环境中的属性值，若未获取到，则返回默认值
     */
    public static String getProperty(Environment environment, String property, String defaultValue) {
        return environment.getProperty(property, defaultValue);
    }

    /**
     * 从条件上下文中获取配置信息
     * 
     * @param conditionContext SpringBoot ConditionContext
     * @param property         配置名称
     * @return 配置属性值
     */
    public static String getProperty(ConditionContext conditionContext, String property) {
        return getProperty(conditionContext.getEnvironment(), property);
    }

    public static String getProperty(ConditionContext conditionContext, String property, String defaultValue) {
        return getProperty(conditionContext.getEnvironment(), property, defaultValue);
    }

    public static <T> T getProperty(Environment environment, String property, Class<T> targetType) {
        return environment.getProperty(property, targetType);
    }

    public static <T> T getProperty(Environment environment, String property, Class<T> targetType, T defaultValue) {
        return environment.getProperty(property, targetType, defaultValue);
    }

    public static <T> T getProperty(ConditionContext conditionContext, String property, Class<T> targetType) {
        return getProperty(conditionContext.getEnvironment(), property, targetType);
    }

    public static <T> T getProperty(ConditionContext conditionContext, String property, Class<T> targetType,
            T defaultValue) {
        return getProperty(conditionContext.getEnvironment(), property, targetType, defaultValue);
    }

    public static boolean contains(Environment environment, String property) {
        return environment.containsProperty(property);
    }

    public static boolean contains(ConditionContext conditionContext, String property) {
        return contains(conditionContext.getEnvironment(), property);
    }

    /**
     * 从条件上下文中获取Boolean类型值配置信息
     *
     * @param environment  Spring Boot ConditionContext {@link ConditionContext}
     * @param property     配置名称
     * @param defaultValue 默认值
     * @return 配置属性值
     */
    public static boolean getBoolean(Environment environment, String property, boolean defaultValue) {
        return getProperty(environment, property, Boolean.class, defaultValue);
    }

    /**
     * 从条件上下文中获取Boolean类型值配置信息
     *
     * @param environment Spring Boot ConditionContext {@link ConditionContext}
     * @param property    配置名称
     * @return 配置属性值
     */
    public static boolean getBoolean(Environment environment, String property) {
        return getProperty(environment, property, Boolean.class, false);
    }

    /**
     * 从条件上下文中获取Boolean类型值配置信息
     *
     * @param conditionContext Spring Boot ConditionContext {@link ConditionContext}
     * @param property         配置名称
     * @return 配置属性值
     */
    public static boolean getBoolean(ConditionContext conditionContext, String property) {
        return getProperty(conditionContext, property, Boolean.class, false);
    }

    /**
     * 从条件上下文中获取Boolean类型值配置信息
     *
     * @param conditionContext Spring Boot ConditionContext {@link ConditionContext}
     * @param property         配置名称
     * @param defaultValue     默认值
     * @return 配置属性值
     */
    public static boolean getBoolean(ConditionContext conditionContext, String property, boolean defaultValue) {
        return getProperty(conditionContext, property, Boolean.class, defaultValue);
    }
}
