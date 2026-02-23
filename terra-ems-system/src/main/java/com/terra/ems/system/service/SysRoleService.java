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

import com.terra.ems.common.domain.Option;
import com.terra.ems.framework.jpa.repository.BaseRepository;
import com.terra.ems.framework.service.BaseService;
import com.terra.ems.system.entity.SysPermission;
import com.terra.ems.system.entity.SysRole;
import com.terra.ems.system.repository.SysRoleRepository;
import com.terra.ems.system.repository.SysPermissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 系统角色服务
 *
 * @author dengxueping
 * @since 2026-02-16
 */

@Service
@RequiredArgsConstructor
public class SysRoleService extends BaseService<SysRole, Long> {

    private final SysRoleRepository roleRepository;
    private final SysPermissionRepository permissionRepository;
    private final SysUserService userService;

    @Override
    public BaseRepository<SysRole, Long> getRepository() {
        return roleRepository;
    }

    /**
     * 查询角色权限ID列表
     *
     * @param roleId 角色ID
     * @return 权限ID列表
     */
    public List<Long> findPermissionIds(Long roleId) {
        SysRole role = roleRepository.findById(roleId).orElse(null);
        if (role != null) {
            return role.getPermissions().stream()
                    .map(SysPermission::getId)
                    .collect(java.util.stream.Collectors.toList());
        }
        return java.util.List.of();
    }

    /**
     * 更新角色权限
     *
     * @param roleId        角色ID
     * @param permissionIds 权限ID列表
     */
    @Transactional
    public void updatePermissions(Long roleId, List<Long> permissionIds) {
        SysRole role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("角色不存在"));

        role.setPermissionIds(new HashSet<>(permissionIds));
        role.setPermissionCount(permissionIds.size());

        roleRepository.save(role);
    }

    /**
     * 添加角色成员
     *
     * @param roleId    角色ID
     * @param memberIds 成员ID列表
     */
    @Transactional
    public void addMembers(Long roleId, List<Long> memberIds) {
        userService.addRoleToUsers(roleId, memberIds);
        updateMemberCount(roleId);
    }

    /**
     * 移除角色成员
     *
     * @param roleId    角色ID
     * @param memberIds 成员ID列表
     */
    @Transactional
    public void removeMembers(Long roleId, List<Long> memberIds) {
        userService.removeRoleFromUsers(roleId, memberIds);
        updateMemberCount(roleId);
    }

    /**
     * 更新角色成员数量
     *
     * @param roleId 角色ID
     */
    public void updateMemberCount(Long roleId) {
        SysRole role = roleRepository.findById(roleId).orElse(null);
        if (role != null) {
            Integer count = userService.countByRoleId(roleId);
            role.setMemberCount(count);
            roleRepository.save(role);
        }
    }

    /**
     * 查询角色选项列表
     *
     * @return 角色选项列表
     */
    public List<Option<Long>> findOptions() {
        return roleRepository.findOptions();
    }
}
