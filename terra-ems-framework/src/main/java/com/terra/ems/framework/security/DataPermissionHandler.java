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

package com.terra.ems.framework.security;

import com.terra.ems.common.domain.CurrentUser;
import com.terra.ems.framework.enums.DataScope;
import jakarta.persistence.criteria.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 数据权限处理器
 *
 * @author dengxueping
 * @since 2026-01-11
 */
@Slf4j
@Component
public class DataPermissionHandler {

    /**
     * 获取数据权限过滤条件
     */
    public <T> Predicate getDataScopePredicate(
            Root<T> root,
            CriteriaQuery<?> query,
            CriteriaBuilder cb) {

        CurrentUser currentUser = getCurrentUser();
        if (currentUser == null) {
            log.warn("未登录用户尝试访问数据，拒绝所有");
            return cb.disjunction(); // 未登录拒绝所有
        }

        DataScope dataScope = getUserDataScope(currentUser);
        log.debug("用户 {} 的数据权限范围: {}", currentUser.getUsername(), dataScope);

        return switch (dataScope) {
            case ALL -> cb.conjunction(); // 允许所有
            case DEPT -> getDeptPredicate(root, cb, currentUser);
            case DEPT_AND_CHILD -> getDeptAndChildPredicate(root, cb, currentUser);
            case SELF -> getSelfPredicate(root, cb, currentUser);
            case CUSTOM -> getCustomPredicate(root, cb, currentUser);
        };
    }

    /**
     * 获取当前登录用户
     */
    private CurrentUser getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof CurrentUser) {
            return (CurrentUser) authentication.getPrincipal();
        }
        return null;
    }

    /**
     * 获取用户的数据权限范围（从角色中获取最大权限）
     */
    private DataScope getUserDataScope(CurrentUser user) {
        // TODO: 从用户的所有角色中获取最大数据权限
        // 临时返回 SELF，后续需要从数据库查询
        return DataScope.SELF;
    }

    /**
     * 本部门数据权限
     */
    private <T> Predicate getDeptPredicate(Root<T> root, CriteriaBuilder cb, CurrentUser user) {
        try {
            // TODO: CurrentUser需要添加departmentId字段
            // return cb.equal(root.get("departmentId"), user.getDepartmentId());
            log.warn("部门数据权限功能待实现（CurrentUser缺少departmentId字段），降级为 SELF 权限");
            return getSelfPredicate(root, cb, user);
        } catch (IllegalArgumentException e) {
            log.warn("实体没有 departmentId 字段，降级为 SELF 权限");
            return getSelfPredicate(root, cb, user);
        }
    }

    /**
     * 本部门及子部门数据权限
     */
    private <T> Predicate getDeptAndChildPredicate(Root<T> root, CriteriaBuilder cb, CurrentUser user) {
        try {
            // TODO: CurrentUser需要添加departmentId字段
            // Set<Long> deptIds = getAllChildDeptIds(user.getDepartmentId());
            // deptIds.add(user.getDepartmentId());
            // return root.get("departmentId").in(deptIds);
            log.warn("部门及子部门数据权限功能待实现（CurrentUser缺少departmentId字段），降级为 SELF 权限");
            return getSelfPredicate(root, cb, user);
        } catch (IllegalArgumentException e) {
            log.warn("实体没有 departmentId 字段，降级为 SELF 权限");
            return getSelfPredicate(root, cb, user);
        }
    }

    /**
     * 仅本人数据权限
     */
    private <T> Predicate getSelfPredicate(Root<T> root, CriteriaBuilder cb, CurrentUser user) {
        try {
            // 优先使用 createdBy，其次使用 userId
            if (hasField(root, "createdBy")) {
                return cb.equal(root.get("createdBy"), user.getUserId());
            } else if (hasField(root, "userId")) {
                return cb.equal(root.get("userId"), user.getUserId());
            } else if (hasField(root, "id")) {
                return cb.equal(root.get("id"), Long.parseLong(user.getUserId()));
            }
        } catch (Exception e) {
            log.error("应用 SELF 数据权限失败", e);
        }
        return cb.disjunction(); // 找不到合适字段，拒绝所有
    }

    /**
     * 自定义数据权限
     */
    private <T> Predicate getCustomPredicate(Root<T> root, CriteriaBuilder cb, CurrentUser user) {
        // TODO: 从用户的角色中获取自定义部门ID列表
        Set<Long> customDeptIds = getCustomDeptIds(user);
        if (customDeptIds.isEmpty()) {
            return getSelfPredicate(root, cb, user);
        }
        try {
            return root.get("departmentId").in(customDeptIds);
        } catch (IllegalArgumentException e) {
            return getSelfPredicate(root, cb, user);
        }
    }

    /**
     * 检查实体是否有指定字段
     */
    private <T> boolean hasField(Root<T> root, String fieldName) {
        try {
            root.get(fieldName);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * 获取所有子部门ID（递归）
     */
    private Set<Long> getAllChildDeptIds(Long deptId) {
        // TODO: 实现部门树的递归查询
        return Set.of();
    }

    /**
     * 获取自定义部门ID列表
     */
    private Set<Long> getCustomDeptIds(CurrentUser user) {
        // TODO: 从用户角色中获取
        return Set.of();
    }
}
