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

package com.terra.ems.admin.controller;

import com.terra.ems.common.annotation.Log;
import com.terra.ems.common.annotation.SuperPermission;
import com.terra.ems.common.domain.Option;
import com.terra.ems.common.domain.Result;
import com.terra.ems.common.enums.BusinessType;
import com.terra.ems.framework.controller.BaseController;
import com.terra.ems.framework.definition.dto.Pager;
import com.terra.ems.framework.service.BaseService;
import com.terra.ems.system.entity.SysRole;
import com.terra.ems.system.service.SysRoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 角色管理控制器
 *
 * @author dengxueping
 * @since 2026-02-16
 */

@Tag(name = "系统管理-角色管理")
@RestController
@RequestMapping("/system/role")
public class SysRoleController extends BaseController<SysRole, Long> {

    private final SysRoleService roleService;

    @Autowired
    public SysRoleController(SysRoleService roleService) {
        this.roleService = roleService;
    }

    @Override
    protected BaseService<SysRole, Long> getService() {
        return roleService;
    }

    /**
     * 分页查询角色
     */
    @Operation(summary = "查询角色列表")
    @PreAuthorize("hasPerm('system:role:list')")
    @GetMapping
    public Result<Map<String, Object>> findByPage(
            Pager pager,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String code) {

        // 如果没有指定排序，则默认按创建时间倒序
        if (pager.getSortOrders().isEmpty()) {
            pager.addSortOrder("createdAt", "DESC");
        }

        Specification<SysRole> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.hasText(name)) {
                predicates.add(cb.like(root.get("name"), "%" + name + "%"));
            }
            if (StringUtils.hasText(code)) {
                predicates.add(cb.like(root.get("code"), "%" + code + "%"));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
        return findByPage(pager, spec);
    }

    @Operation(summary = "查询角色详情")
    @PreAuthorize("hasPerm('system:role:query')")
    @GetMapping("/{id}")
    @Override
    public Result<SysRole> findById(@PathVariable Long id) {
        return super.findById(id);
    }

    /**
     * 查询所有角色列表(不分页)
     */
    @Operation(summary = "查询角色列表(不分页)")
    @GetMapping("/list")
    public Result<List<SysRole>> findList() {
        return Result.content(roleService.findAll());
    }

    /**
     * 保存或更新角色
     */
    @Log(title = "角色管理", businessType = BusinessType.UPDATE)
    @Operation(summary = "保存角色")
    @SuperPermission
    @PostMapping
    @Override
    public Result<SysRole> saveOrUpdate(@Validated @RequestBody SysRole role) {
        return super.saveOrUpdate(role);
    }

    /**
     * 通过ID更新角色
     */
    @Operation(summary = "修改角色")
    @SuperPermission
    @PutMapping("/{id}")
    @Override
    public Result<SysRole> update(@PathVariable Long id, @RequestBody @Validated SysRole domain) {
        return super.update(id, domain);
    }

    /**
     * 删除角色
     */
    @Operation(summary = "删除角色")
    @SuperPermission
    @Log(title = "角色管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{id}")
    @Override
    public Result<String> delete(@PathVariable Long id) {
        return super.delete(id);
    }

    /**
     * 批量删除角色
     */
    @Operation(summary = "批量删除角色")
    @SuperPermission
    @Log(title = "角色管理", businessType = BusinessType.DELETE)
    @DeleteMapping
    @Override
    public Result<String> deleteBatch(@RequestBody List<Long> ids) {
        return super.deleteBatch(ids);
    }

    /**
     * 查询所有角色
     */
    @Operation(summary = "查询所有数据")
    @PreAuthorize("hasPerm('system:role:list')")
    @GetMapping("/all")
    public Result<List<SysRole>> findAll() {
        return super.findAll();
    }

    /**
     * 获取角色权限ID列表
     */
    @Operation(summary = "查询角色权限")
    @PreAuthorize("hasPerm('system:role:query')")
    @GetMapping("/{id}/permissions")
    public Result<List<Long>> getPermissions(@PathVariable Long id) {
        return Result.content(roleService.findPermissionIds(id));
    }

    /**
     * 更新角色权限
     */
    @Operation(summary = "更新角色权限")
    @SuperPermission
    @Log(title = "角色管理", businessType = BusinessType.UPDATE)
    @PostMapping("/{id}/permissions")
    public Result<String> updatePermissions(@PathVariable Long id, @RequestBody Map<String, List<Long>> data) {
        List<Long> permissionIds = data.get("permissionIds");
        roleService.updatePermissions(id, permissionIds);
        return Result.success("更新权限成功");
    }

    /**
     * 添加角色成员
     */
    @Operation(summary = "添加角色成员")
    @SuperPermission
    @Log(title = "角色管理", businessType = BusinessType.UPDATE)
    @PostMapping("/{id}/members")
    public Result<String> addMembers(@PathVariable Long id, @RequestBody List<Long> memberIds) {
        roleService.addMembers(id, memberIds);
        return Result.success("添加成员成功");
    }

    /**
     * 移除角色成员
     */
    @Operation(summary = "移除角色成员")
    @SuperPermission
    @Log(title = "角色管理", businessType = BusinessType.UPDATE)
    @DeleteMapping("/{id}/members")
    public Result<String> removeMembers(@PathVariable Long id, @RequestBody List<Long> memberIds) {
        roleService.removeMembers(id, memberIds);
        return Result.success("移除成员成功");
    }

    /**
     * 获取角色选项列表
     */
    @Operation(summary = "查询角色选项")
    @GetMapping("/options")
    public Result<List<Option<Long>>> findOptions() {
        return Result.content(roleService.findOptions());
    }
}
