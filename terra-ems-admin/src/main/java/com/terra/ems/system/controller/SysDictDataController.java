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

package com.terra.ems.system.controller;

import com.terra.ems.common.domain.Result;
import com.terra.ems.framework.controller.BaseController;
import com.terra.ems.framework.definition.dto.Pager;
import com.terra.ems.framework.service.BaseService;
import com.terra.ems.system.entity.SysDictData;
import com.terra.ems.system.service.SysDictDataService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import com.terra.ems.common.annotation.Log;
import com.terra.ems.common.enums.BusinessType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 字典数据控制器
 *
 * @author dengxueping
 * @since 2026-01-16
 */
@Tag(name = "系统管理-字典数据", description = "系统字典数据管理")
@RestController
@RequestMapping("/system/dict/data")
@RequiredArgsConstructor
public class SysDictDataController extends BaseController<SysDictData, Long> {

    private final SysDictDataService dictDataService;

    @Override
    protected BaseService<SysDictData, Long> getService() {
        return dictDataService;
    }

    /**
     * 分页查询字典数据
     */
    @Operation(summary = "分页查询")
    @GetMapping
    public Result<Map<String, Object>> findByPage(
            Pager pager,
            @RequestParam(required = false) String label,
            @RequestParam(required = false) String value,
            @RequestParam(required = false) String typeCode,
            @RequestParam(required = false) String status) {
        Specification<SysDictData> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.hasText(label)) {
                predicates.add(cb.like(root.get("label"), "%" + label + "%"));
            }
            if (StringUtils.hasText(value)) {
                predicates.add(cb.equal(root.get("value"), value));
            }
            if (StringUtils.hasText(typeCode)) {
                predicates.add(cb.equal(root.get("typeCode"), typeCode));
            }
            if (StringUtils.hasText(status)) {
                predicates.add(cb.equal(root.get("status"), status));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
        return findByPage(pager, spec);
    }

    /**
     * 保存或更新字典数据
     */
    @Operation(summary = "提交")
    @PostMapping
    @PutMapping
    @Override
    @Log(title = "字典数据", businessType = BusinessType.UPDATE)
    @PreAuthorize("hasAnyPerm('system:dict:add', 'system:dict:edit')")
    public Result<SysDictData> saveOrUpdate(@Validated @RequestBody SysDictData dictData) {
        return super.saveOrUpdate(dictData);
    }

    /**
     * 删除字典数据
     */
    @Operation(summary = "删除")
    @PreAuthorize("hasPerm('system:dict:remove')")
    @Log(title = "字典数据", businessType = BusinessType.DELETE)
    @DeleteMapping("/{id}")
    @Override
    public Result<String> delete(@PathVariable Long id) {
        return super.delete(id);
    }

    /**
     * 批量删除字典数据
     */
    @Operation(summary = "批量删除")
    @PreAuthorize("hasPerm('system:dict:remove')")
    @Log(title = "字典数据", businessType = BusinessType.DELETE)
    @DeleteMapping
    @Override
    public Result<String> deleteBatch(@RequestBody List<Long> ids) {
        return super.deleteBatch(ids);
    }

    @Operation(summary = "按类型查询")
    @GetMapping(value = "/type/{dictType}")
    public Result<List<SysDictData>> dictType(@Parameter(description = "字典类型") @PathVariable String dictType) {
        return Result.content(dictDataService.findByType(dictType));
    }
}
