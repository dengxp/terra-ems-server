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

package com.terra.ems.system.service;

import com.terra.ems.framework.enums.DataScope;
import com.terra.ems.framework.jpa.repository.BaseRepository;
import com.terra.ems.framework.service.BaseService;
import com.terra.ems.system.entity.SysDept;
import com.terra.ems.system.entity.SysRole;
import com.terra.ems.system.entity.SysUser;
import com.terra.ems.system.repository.SysDeptRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.List;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * 系统部门服务
 *
 * @author dengxueping
 * @since 2026-01-11
 */

@Service
@RequiredArgsConstructor
public class SysDeptService extends BaseService<SysDept, Long> {

    private final SysDeptRepository deptRepository;

    /**
     * 获取数据访问仓库
     *
     * @return 部门仓库
     */
    @Override
    public BaseRepository<SysDept, Long> getRepository() {
        return deptRepository;
    }

    /**
     * 查询指定部门及其所有子部门构成的树形结构
     *
     * @param parentId 父部门ID，为 null 时从顶级部门开始查询
     * @return 部门树列表
     */
    public List<SysDept> findDeptTree(Long parentId) {
        if (parentId == null) {
            // 返回顶级部门树
            return deptRepository.findByParentIdOrderBySortOrderAsc(null);
        } else {
            // 返回指定部门及其子树
            return deptRepository.findByParentIdOrderBySortOrderAsc(parentId);
        }
    }

    /**
     * 获取所有状态为启用的部门列表
     *
     * @return 启用的部门列表
     */
    public List<SysDept> findAllEnabled() {
        return deptRepository.findAllEnabled();
    }

    /**
     * 根据用户及其绑定的角色权限，计算可访问的部门ID集合
     * 用于数据权限过滤
     *
     * @param user 当前用户实体
     * @return 可访问部门ID集合，返回 null 时表示拥有全部权限
     */
    public Set<Long> getAccessibleDeptIds(SysUser user) {
        Set<Long> deptIds = new HashSet<>();
        if (user.getRoles() == null || user.getRoles().isEmpty()) {
            return deptIds;
        }

        for (SysRole role : user.getRoles()) {
            DataScope dataScope = role.getDataScope();
            if (dataScope == null)
                continue;

            switch (dataScope) {
                case ALL:
                    // 全部权限，标志由 Aspect 或业务逻辑处理，此处返回空代表不限制或通过全表
                    return null;
                case DEPT:
                    if (user.getDept() != null) {
                        deptIds.add(user.getDept().getId());
                    }
                    break;
                case DEPT_AND_CHILD:
                    if (user.getDept() != null) {
                        deptIds.add(user.getDept().getId());
                        List<SysDept> children = deptRepository.findByAncestorsLike("%" + user.getDept().getId() + "%");
                        deptIds.addAll(children.stream().map(SysDept::getId).collect(Collectors.toSet()));
                    }
                    break;
                case CUSTOM:
                    if (!CollectionUtils.isEmpty(role.getDataScopeDeptIds())) {
                        deptIds.addAll(role.getDataScopeDeptIds());
                    }
                    break;
                case SELF:
                    // 仅本人权限，不涉及部门ID过滤，标记由业务逻辑处理
                    break;
            }
        }
        return deptIds;
    }
}
