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
