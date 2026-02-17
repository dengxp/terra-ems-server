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

@Tag(name = "角色管理")
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
    @Operation(summary = "分页查询")
    @GetMapping
    public Result<Map<String, Object>> findByPage(
            Pager pager,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String code) {
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

    /**
     * 保存或更新角色
     */
    @Log(title = "角色管理", businessType = BusinessType.UPDATE)
    @Operation(summary = "保存或更新角色")
    @PostMapping
    @PutMapping
    @Override
    @PreAuthorize("hasAnyAuthority('system:role:add', 'system:role:edit')")
    public Result<SysRole> saveOrUpdate(@Validated @RequestBody SysRole role) {
        return super.saveOrUpdate(role);
    }

    /**
     * 获取角色选项列表
     */
    @Operation(summary = "查询角色选项列表")
    @GetMapping("/options")
    public Result<List<Option<Long>>> findOptions() {
        return Result.content(roleService.findOptions());
    }
}
