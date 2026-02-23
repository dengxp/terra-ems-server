/*
 * Copyright (c) 2024 泰若科技（广州）有限公司. All rights reserved.
 */

package com.terra.ems.common.annotation;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.*;

/**
 * 超级权限注解（Spring Security 元注解）
 *
 * <p>
 * 标注在 Controller 方法上，表示该接口仅超级管理员可调用。
 * </p>
 * <p>
 * 运行时等价于 {@code @PreAuthorize("hasAuthority('*:*:*')")}，
 * 由 Spring Security 统一拦截。
 * </p>
 * <p>
 * 同时被 {@code PermissionInitializer} 扫描，自动在数据库中
 * 标记对应权限为超级权限，供前端权限树标红展示。
 * </p>
 *
 * @author dengxueping
 * @since 2026-02-22
 */
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@PreAuthorize("hasAuthority('*:*:*')")
public @interface SuperPermission {
}
