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

import com.terra.ems.common.domain.Option;
import com.terra.ems.common.domain.Result;
import com.terra.ems.framework.controller.BaseController;
import com.terra.ems.framework.definition.dto.Pager;
import com.terra.ems.framework.service.BaseService;
import com.terra.ems.system.entity.SysModule;
import com.terra.ems.system.service.SysModuleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import com.terra.ems.common.annotation.Log;
import com.terra.ems.common.enums.BusinessType;

/**
 * 业务模块控制器
 *
 * @author antigravity
 * @since 2026-02-19
 */
@Tag(name = "系统管理-业务模块管理", description = "系统业务模块的CRUD接口")
@RestController
@RequestMapping("/system/module")
public class SysModuleController extends BaseController<SysModule, Long> {

    private final SysModuleService moduleService;

    public SysModuleController(SysModuleService moduleService) {
        this.moduleService = moduleService;
    }

    @Override
    protected BaseService<SysModule, Long> getService() {
        return moduleService;
    }

    /**
     * 分页查询业务模块
     *
     * @param pager  分页参数
     * @param params 查询参数
     * @return 分页结果
     */
    @Operation(summary = "查询模块详情")
    @PreAuthorize("hasPerm('system:module:query')")
    @GetMapping("/{id:\\d+}")
    @Override
    public Result<SysModule> findById(@PathVariable Long id) {
        return super.findById(id);
    }

    /**
     * 获取业务模块选项列表
     */
    @Operation(summary = "查询模块选项")
    @GetMapping("/options")
    public Result<List<Option<Long>>> findOptions() {
        return Result.content(moduleService.findOptions());
    }

    /**
     * 获取业务模块树（含权限）
     */
    @Operation(summary = "查询模块权限树")
    @GetMapping("/tree")
    public Result<List<SysModule>> findTree() {
        return Result.content(moduleService.findTree());
    }

    /**
     * 保存或更新业务模块
     */
    @Operation(summary = "保存模块")
    @PostMapping
    @PutMapping
    @Override
    @Log(title = "业务模块", businessType = BusinessType.UPDATE)
    @PreAuthorize("hasAnyPerm('system:module:add', 'system:module:edit')")
    public Result<SysModule> saveOrUpdate(@Validated @RequestBody SysModule module) {
        return super.saveOrUpdate(module);
    }

    /**
     * 更新业务模块
     */
    @Operation(summary = "修改模块")
    @PreAuthorize("hasPerm('system:module:edit')")
    @PutMapping("/{id:\\d+}")
    @Override
    public Result<SysModule> update(@PathVariable Long id, @RequestBody @Validated SysModule domain) {
        return super.update(id, domain);
    }

    /**
     * 删除业务模块
     */
    @Operation(summary = "删除模块")
    @PreAuthorize("hasPerm('system:module:remove')")
    @Log(title = "业务模块", businessType = BusinessType.DELETE)
    @DeleteMapping("/{id:\\d+}")
    @Override
    public Result<String> delete(@PathVariable Long id) {
        return super.delete(id);
    }

    /**
     * 批量删除业务模块
     */
    @Operation(summary = "批量删除模块")
    @PreAuthorize("hasPerm('system:module:remove')")
    @Log(title = "业务模块", businessType = BusinessType.DELETE)
    @DeleteMapping
    @Override
    public Result<String> deleteBatch(@RequestBody List<Long> ids) {
        return super.deleteBatch(ids);
    }

    /**
     * 分页查询业务模块
     *
     * @param pager  分页参数
     * @param params 查询参数
     * @return 分页结果
     */
    @Operation(summary = "查询模块列表")
    @PreAuthorize("hasPerm('system:module:list')")
    @GetMapping
    public Result<Map<String, Object>> findByPage(Pager pager,
            @io.swagger.v3.oas.annotations.Parameter(hidden = true) @org.springframework.web.bind.annotation.RequestParam Map<String, Object> params) {
        Specification<SysModule> spec = buildSpecification(params);
        return super.findByPage(pager, spec);
    }

    /**
     * 构建查询条件
     */
    protected Specification<SysModule> buildSpecification(Map<String, Object> params) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            String name = (String) params.get("name");
            String code = (String) params.get("code");

            if (StringUtils.hasText(name)) {
                predicates.add(cb.like(root.get("name"), "%" + name + "%"));
            }
            if (StringUtils.hasText(code)) {
                predicates.add(cb.like(root.get("code"), "%" + code + "%"));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
