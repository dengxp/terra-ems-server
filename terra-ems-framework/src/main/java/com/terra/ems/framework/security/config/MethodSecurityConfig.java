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

package com.terra.ems.framework.security.config;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.authorization.method.PreAuthorizeAuthorizationManager;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.context.annotation.Role;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.beans.factory.config.BeanDefinition;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import com.terra.ems.framework.security.permission.TerraMethodSecurityExpressionHandler;

/**
 * 方法安全独立配置
 * 单独提取是为了确保 MethodSecurityExpressionHandler 在 EnableMethodSecurity 启动前已就绪
 */
@Configuration
@EnableMethodSecurity
public class MethodSecurityConfig {

    /**
     * 自定义方法安全表达式处理器
     */
    @Bean
    @Primary
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public static MethodSecurityExpressionHandler methodSecurityExpressionHandler() {
        return new TerraMethodSecurityExpressionHandler();
    }

    /**
     * 显式注册 PreAuthorizeAuthorizationManager，强制使用我们的 Handler
     * 解决 Spring Security 6 默认实例忽略全局 Bean 的问题
     */
    @Bean
    @Primary
    public AuthorizationManager<MethodInvocation> preAuthorizeAuthorizationManager(
            MethodSecurityExpressionHandler handler) {
        PreAuthorizeAuthorizationManager manager = new PreAuthorizeAuthorizationManager();
        manager.setExpressionHandler(handler);
        return manager;
    }

    @Bean
    public static org.springframework.beans.factory.config.BeanPostProcessor forceHandlerBPP(
            org.springframework.beans.factory.ObjectProvider<MethodSecurityExpressionHandler> handlerProvider) {
        return new org.springframework.beans.factory.config.BeanPostProcessor() {
            @Override
            public Object postProcessAfterInitialization(Object bean, String beanName) {
                // 1. 劫持 Manager
                if (bean.getClass().getName().contains("PreAuthorizeAuthorizationManager")) {
                    injectToManager(bean, handlerProvider.getIfAvailable(), beanName);
                }
                // 2. 劫持 Interceptor
                if (bean instanceof MethodInterceptor) {
                    processInterceptor(bean, handlerProvider.getIfAvailable(), beanName);
                }
                return bean;
            }

            private void processInterceptor(Object bean, MethodSecurityExpressionHandler handler, String name) {
                try {
                    // 处理 DeferringMethodInterceptor
                    if (bean.getClass().getName().contains("DeferringMethodInterceptor")) {
                        java.lang.reflect.Field delegateField = findField(bean.getClass(), "delegate");
                        if (delegateField != null) {
                            delegateField.setAccessible(true);
                            Object delegate = delegateField.get(bean);
                            if (delegate != null) {
                                // 如果是 Supplier，尝试获取真实对象并劫持
                                if (delegate instanceof java.util.function.Supplier) {
                                    try {
                                        Object target = ((java.util.function.Supplier<?>) delegate).get();
                                        if (target != null) {
                                            processInterceptor(target, handler, name + "->supplied");
                                        }
                                    } catch (Exception e) {
                                        // Ignore
                                    }
                                } else {
                                    processInterceptor(delegate, handler, name + "->delegate");
                                }
                            }
                        }
                    }

                    // 寻找 authorizationManager 字段
                    java.lang.reflect.Field managerField = findField(bean.getClass(), "authorizationManager");
                    if (managerField != null) {
                        managerField.setAccessible(true);
                        Object manager = managerField.get(bean);
                        if (manager != null) {
                            injectToManager(manager, handler, name + "->manager");
                        }
                    }
                } catch (Exception e) {
                    // Ignore
                }
            }

            private void injectToManager(Object manager, MethodSecurityExpressionHandler handler, String name) {
                if (manager.getClass().getName().contains("PreAuthorizeAuthorizationManager")) {
                    try {
                        java.lang.reflect.Method setHandler = manager.getClass().getMethod("setExpressionHandler",
                                MethodSecurityExpressionHandler.class);
                        setHandler.setAccessible(true);
                        setHandler.invoke(manager, handler);
                    } catch (Exception e) {
                        try {
                            java.lang.reflect.Field registryField = findField(manager.getClass(), "registry");
                            if (registryField != null) {
                                registryField.setAccessible(true);
                                Object registry = registryField.get(manager);
                                java.lang.reflect.Field handlerField = findField(registry.getClass(),
                                        "expressionHandler");
                                if (handlerField != null) {
                                    handlerField.setAccessible(true);
                                    handlerField.set(registry, handler);
                                }
                            }
                        } catch (Exception ex) {
                        }
                    }
                }
            }

            private java.lang.reflect.Field findField(Class<?> clazz, String fieldName) {
                Class<?> current = clazz;
                while (current != null && current != Object.class) {
                    try {
                        return current.getDeclaredField(fieldName);
                    } catch (NoSuchFieldException e) {
                        current = current.getSuperclass();
                    }
                }
                return null;
            }
        };
    }
}
