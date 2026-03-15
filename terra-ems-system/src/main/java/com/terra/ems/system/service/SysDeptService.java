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

package com.terra.ems.system.service;

import com.terra.ems.framework.enums.DataScope;
import com.terra.ems.framework.jpa.repository.BaseRepository;
import com.terra.ems.framework.service.BaseService;
import com.terra.ems.system.entity.SysDept;
import com.terra.ems.system.entity.SysRole;
import com.terra.ems.system.entity.SysUser;
import com.terra.ems.system.repository.SysDeptRepository;
import com.terra.ems.system.repository.SysUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    private final SysUserRepository userRepository;

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
     * 获取所有状态为启用的部门列表
     *
     * @return 启用的部门列表
     */
    public List<SysDept> findAllEnabled() {
        return deptRepository.findAllEnabledWithDetails();
    }

    /**
     * 获取所有部门列表（包含禁用）
     *
     * @return 部门列表
     */
    public List<SysDept> findAllWithDetails() {
        return deptRepository.findAllWithDetails();
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

    /**
     * 根据部门名称查询
     */
    public com.terra.ems.system.entity.SysDept findByName(String name) {
        return deptRepository.findByName(name);
    }

    /**
     * 添加部门成员（将用户转移到该部门）
     *
     * @param deptId  部门ID
     * @param userIds 用户ID列表
     */
    @Transactional(rollbackFor = Exception.class)
    public void addMembers(Long deptId, List<Long> userIds) {
        SysDept dept = deptRepository.findById(deptId)
                .orElseThrow(() -> new RuntimeException("部门不存在"));

        List<SysUser> users = userRepository.findAllById(userIds);
        for (SysUser user : users) {
            user.setDept(dept);
        }
        userRepository.saveAll(users);
    }

    /**
     * 移除部门成员
     *
     * @param deptId 部门ID
     * @param userId 用户ID
     */
    @Transactional(rollbackFor = Exception.class)
    public void removeMember(Long deptId, Long userId) {
        SysUser user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("用户不存在"));

        if (user.getDept() != null && user.getDept().getId().equals(deptId)) {
            user.setDept(null);
            userRepository.save(user);
        } else {
            throw new RuntimeException("该用户不属于此部门");
        }
    }

    /**
     * 批量移除部门成员
     *
     * @param deptId  部门ID
     * @param userIds 用户ID列表
     */
    @Transactional(rollbackFor = Exception.class)
    public void removeMembers(Long deptId, List<Long> userIds) {
        List<SysUser> users = userRepository.findAllById(userIds);
        for (SysUser user : users) {
            if (user.getDept() != null && user.getDept().getId().equals(deptId)) {
                user.setDept(null);
            }
        }
        userRepository.saveAll(users);
    }

    /**
     * 保存或更新部门
     * 重写以支持部分更新，避免 orphanRemoval 误删除子部门，并维护 ancestors
     *
     * @param entity 部门实体
     * @return 保存后的实体
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysDept saveOrUpdate(SysDept entity) {
        // 新增逻辑
        if (entity.getId() == null) {
            String ancestors = "0";
            if (entity.getParent() != null || entity.getParentId() != null) {
                Long parentId = entity.getParent() != null ? entity.getParent().getId() : entity.getParentId();
                if (parentId != null && parentId != 0) {
                    SysDept parent = deptRepository.findById(parentId).orElse(null);
                    if (parent != null) {
                        entity.setParent(parent);
                        ancestors = parent.getAncestors() + "," + parent.getId();
                    } else {
                        entity.setAncestors("0");
                    }
                } else {
                    entity.setAncestors("0");
                }
            } else {
                entity.setAncestors("0");
            }
            entity.setAncestors(ancestors);
            return super.saveOrUpdate(entity);
        }

        // 更新逻辑
        SysDept existing = deptRepository.findById(entity.getId())
                .orElseThrow(() -> new RuntimeException("部门不存在"));

        // 复制属性
        existing.setName(entity.getName());
        existing.setSortOrder(entity.getSortOrder());
        existing.setStatus(entity.getStatus());
        existing.setLeader(entity.getLeader());
        existing.setPhone(entity.getPhone());
        existing.setEmail(entity.getEmail());
        existing.setDescription(entity.getDescription());
        existing.setCode(entity.getCode());

        // 处理父节点变化
        Long oldParentId = existing.getParent() != null ? existing.getParent().getId() : 0L;
        Long newParentId = null;
        SysDept newParent = null;

        if (entity.getParent() != null) {
            newParentId = entity.getParent().getId();
            newParent = entity.getParent();
        } else if (entity.getParentId() != null) {
            newParentId = entity.getParentId();
        }

        // 允许前端传 parentId=0 表示根节点
        if (newParentId != null && newParentId == 0) {
            newParent = null;
        } else if (newParentId != null && newParent == null) {
            // 只有ID，需查询实体
            newParent = deptRepository.findById(newParentId).orElse(null);
        }

        // 如果 newParentId 不为null (前端传了参数)，则处理移动逻辑
        if (newParentId != null) {
            Long effectiveNewId = (newParent != null) ? newParent.getId() : 0L;

            if (effectiveNewId.equals(existing.getId())) {
                throw new RuntimeException("上级部门不能是自己");
            }

            // 检查是否真正发生变化
            boolean changed = !effectiveNewId.equals(oldParentId);

            if (changed) {
                String newAncestors;
                if (newParent != null) {
                    String ancestors = newParent.getAncestors();
                    // 校验循环引用
                    if (ancestors != null && ("," + ancestors + ",").contains("," + existing.getId() + ",")) {
                        throw new RuntimeException("上级部门不能是自己的子部门");
                    }
                    newAncestors = ancestors + "," + newParent.getId();
                    existing.setParent(newParent);
                } else {
                    newAncestors = "0";
                    existing.setParent(null);
                }

                String oldAncestors = existing.getAncestors();
                existing.setAncestors(newAncestors);
                updateChildrenAncestors(existing.getId(), oldAncestors, newAncestors);
            }
        }

        // 更新负责人
        if (entity.getManager() != null) {
            existing.setManager(entity.getManager());
        } else {
            existing.setManager(null);
        }

        return deptRepository.save(existing);
    }

    private void updateChildrenAncestors(Long currentId, String oldAncestors, String newAncestors) {
        List<SysDept> children = deptRepository.findByAncestorsLike(oldAncestors + "," + currentId + "%");
        for (SysDept child : children) {
            String ancestors = child.getAncestors();
            if (ancestors != null) {
                String target = oldAncestors + "," + currentId;
                String replacement = newAncestors + "," + currentId;
                if (ancestors.startsWith(target)) {
                    child.setAncestors(ancestors.replaceFirst(target, replacement));
                    deptRepository.save(child);
                }
            }
        }
    }
}
