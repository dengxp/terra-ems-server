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

package com.terra.ems.framework.jpa.util;

import com.terra.ems.common.domain.CurrentUser;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 数据权限工具类
 *
 * @author dengxueping
 * @since 2026-01-11
 */

public class DataPermissionUtils {

    /**
     * 获取当前登录用户
     */
    public static CurrentUser getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof CurrentUser) {
            return (CurrentUser) authentication.getPrincipal();
        }
        return null;
    }

    /**
     * 构建数据权限 Specification
     *
     * @param deptField 部门字段名
     * @param userField 用户字段名
     * @return Specification
     */
    public static <T> Specification<T> buildSpecification(String deptField, String userField) {
        CurrentUser user = getCurrentUser();
        if (user == null) {
            return (root, query, cb) -> cb.disjunction(); // 拒绝所有
        }

        // 管理员或拥有全部权限标记
        if (user.getRoles().contains("admin") || DataPermissionContext.getIsAll()) {
            return null;
        }

        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            try {
                // 部门过滤
                Set<Long> deptIds = user.getAccessibleDeptIds();
                if (!CollectionUtils.isEmpty(deptIds)) {
                    Path<Long> deptPath = root.get(deptField);
                    predicates.add(deptPath.in(deptIds));
                }

                // 本人数据过滤
                // 逻辑：如果同时设置了部门过滤，通常是 OR 关系，或者是取并集
                // 这里我们优先判断 accessibleDeptIds，如果为空且角色配置为仅本人，则进行 userField 过滤
                if (DataPermissionContext.getIsSelf() || CollectionUtils.isEmpty(deptIds)) {
                    Path<String> userPath = root.get(userField);
                    predicates.add(cb.equal(userPath, user.getUserId()));
                }
            } catch (IllegalArgumentException e) {
                // 实体不包含指定的数据权限字段，跳过数据权限过滤
                return cb.conjunction();
            }

            if (predicates.isEmpty()) {
                return cb.conjunction();
            }

            return predicates.size() == 1 ? predicates.get(0) : cb.or(predicates.toArray(new Predicate[0]));
        };
    }
}
