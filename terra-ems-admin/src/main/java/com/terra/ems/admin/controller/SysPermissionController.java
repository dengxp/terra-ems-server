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

package com.terra.ems.admin.controller;

import com.terra.ems.common.domain.Option;
import com.terra.ems.common.domain.Result;
import com.terra.ems.framework.controller.BaseController;
import com.terra.ems.framework.definition.dto.Pager;
import com.terra.ems.framework.service.BaseService;
import com.terra.ems.system.entity.SysPermission;
import com.terra.ems.system.service.SysPermissionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import com.terra.ems.common.annotation.Log;
import com.terra.ems.common.enums.BusinessType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import com.terra.ems.common.annotation.SuperPermission;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 系统权限控制器
 *
 * @author antigravity
 * @since 2026-02-19
 */
@Tag(name = "系统管理-权限管理", description = "系统权限的CRUD接口")
@RestController
@RequestMapping("/system/permission")
@RequiredArgsConstructor
public class SysPermissionController extends BaseController<SysPermission, Long> {

    private final SysPermissionService permissionService;
    private final com.terra.ems.system.init.PermissionInitializer permissionInitializer;

    @Override
    public SysPermissionService getService() {
        return permissionService;
    }

    /**
     * 获取权限选项列表
     */
    @Operation(summary = "查询权限选项")
    @GetMapping("/options")
    public Result<List<Option<Long>>> findOptions() {
        return Result.content(permissionService.findOptions());
    }

    @Operation(summary = "查询权限详情")
    @PreAuthorize("hasPerm('system:permission:query')")
    @GetMapping("/{id:\\d+}")
    @Override
    public Result<SysPermission> findById(@PathVariable Long id) {
        return super.findById(id);
    }

    /**
     * 保存或更新权限
     */
    @Operation(summary = "保存权限")
    @SuperPermission
    @PostMapping
    @PutMapping
    @Override
    @Log(title = "权限管理", businessType = BusinessType.UPDATE)
    public Result<SysPermission> saveOrUpdate(@Validated @RequestBody SysPermission permission) {
        return super.saveOrUpdate(permission);
    }

    /**
     * 更新权限
     */
    @Operation(summary = "修改权限")
    @SuperPermission
    @PutMapping("/{id:\\d+}")
    @Override
    public Result<SysPermission> update(@PathVariable Long id, @RequestBody @Validated SysPermission domain) {
        return super.update(id, domain);
    }

    /**
     * 删除权限
     */
    @Operation(summary = "删除权限")
    @SuperPermission
    @Log(title = "权限管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{id:\\d+}")
    @Override
    public Result<String> delete(@PathVariable Long id) {
        return super.delete(id);
    }

    /**
     * 批量删除权限
     */
    @Operation(summary = "批量删除权限")
    @SuperPermission
    @Log(title = "权限管理", businessType = BusinessType.DELETE)
    @DeleteMapping
    @Override
    public Result<String> deleteBatch(@RequestBody List<Long> ids) {
        return super.deleteBatch(ids);
    }

    /**
     * 分页查询权限
     *
     * @param pager  分页参数
     * @param params 查询参数
     * @return 分页结果
     */
    @Operation(summary = "查询权限列表")
    @PreAuthorize("hasPerm('system:permission:list')")
    @GetMapping
    public Result<Map<String, Object>> findByPage(Pager pager,
            @io.swagger.v3.oas.annotations.Parameter(hidden = true) @org.springframework.web.bind.annotation.RequestParam Map<String, Object> params) {
        // 默认按模块和代码排序，保证自然展示顺序
        if (CollectionUtils.isEmpty(pager.getSortOrders())) {
            pager.addSortOrder("module.id", "ASC");
            pager.addSortOrder("code", "ASC");
        }
        Specification<SysPermission> spec = buildSpecification(params);
        return super.findByPage(pager, spec);
    }

    /**
     * 构建查询条件
     */
    protected Specification<SysPermission> buildSpecification(Map<String, Object> params) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            String name = (String) params.get("name");
            String code = (String) params.get("code");
            Object moduleId = params.get("moduleId");

            if (StringUtils.hasText(name)) {
                predicates.add(cb.like(root.get("name"), "%" + name + "%"));
            }
            if (StringUtils.hasText(code)) {
                predicates.add(cb.like(root.get("code"), "%" + code + "%"));
            }
            if (moduleId != null) {
                Long mId;
                if (moduleId instanceof Integer) {
                    mId = ((Integer) moduleId).longValue();
                } else {
                    mId = Long.parseLong(moduleId.toString());
                }
                predicates.add(cb.equal(root.get("module").get("id"), mId));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    /**
     * 同步权限 (增量)
     */
    @Operation(summary = "同步权限")
    @SuperPermission
    @PostMapping("/sync")
    public Result<Void> sync() {
        permissionInitializer.scanAndSync(false);
        return Result.success();
    }

    /**
     * 全量同步权限 (清空后重新扫描)
     */
    @Operation(summary = "全量同步权限")
    @SuperPermission
    @PostMapping("/sync/full")
    public Result<Void> fullSync() {
        permissionInitializer.fullSync();
        return Result.success();
    }
}
