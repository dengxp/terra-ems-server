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

package com.terra.ems.framework.security.permission;

import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;

/**
 * 自定义权限表达式根对象
 * 扩展原生 hasAuthority 逻辑，支持 *:*:* 全局通配符
 *
 * @author antigravity
 * @since 2026-02-19
 */
public class TerraSecurityExpressionRoot extends SecurityExpressionRoot implements MethodSecurityExpressionOperations {

    private Object filterObject;
    private Object returnObject;
    private Object target;

    public TerraSecurityExpressionRoot(Authentication authentication) {
        super(authentication);
    }

    /**
     * 自定义权限检查逻辑
     * 如果用户拥有 *:*:* 权限，则直接返回 true，实现上帝模式
     */
    public boolean hasPerm(String permission) {
        // 先检查是否拥有上帝码
        if (hasAuthority("*:*:*")) {
            return true;
        }
        // 否则走标准匹配逻辑
        return hasAuthority(permission);
    }

    /**
     * 自定义多权限检查逻辑
     */
    public boolean hasAnyPerm(String... permissions) {
        if (hasAuthority("*:*:*")) {
            return true;
        }
        for (String permission : permissions) {
            if (hasAuthority(permission)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void setFilterObject(Object filterObject) {
        this.filterObject = filterObject;
    }

    @Override
    public Object getFilterObject() {
        return filterObject;
    }

    @Override
    public void setReturnObject(Object returnObject) {
        this.returnObject = returnObject;
    }

    @Override
    public Object getReturnObject() {
        return returnObject;
    }

    /**
     * Sets the "this" person for the method invocation.
     *
     * @param target the target object (this)
     */
    public void setThis(Object target) {
        this.target = target;
    }

    @Override
    public Object getThis() {
        return target;
    }
}
