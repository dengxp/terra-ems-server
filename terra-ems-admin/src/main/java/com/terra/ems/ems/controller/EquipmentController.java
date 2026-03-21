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

package com.terra.ems.ems.controller;

import com.terra.ems.common.annotation.Log;
import com.terra.ems.common.domain.Result;
import com.terra.ems.common.enums.BusinessType;
import com.terra.ems.ems.entity.Equipment;
import com.terra.ems.ems.service.EquipmentService;
import com.terra.ems.framework.controller.BaseController;
import com.terra.ems.framework.definition.dto.Pager;
import com.terra.ems.framework.service.BaseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 用能设备管理控制器
 *
 * @author dengxueping
 * @since 2026-03-21
 */
@Tag(name = "用能设备管理")
@RestController
@RequestMapping("/equipments")
public class EquipmentController extends BaseController<Equipment, Long> {

    private final EquipmentService equipmentService;

    public EquipmentController(EquipmentService equipmentService) {
        this.equipmentService = equipmentService;
    }

    @Override
    protected BaseService<Equipment, Long> getService() {
        return equipmentService;
    }

    @Operation(summary = "保存或更新用能设备")
    @PostMapping
    @Override
    @Log(title = "用能设备", businessType = BusinessType.UPDATE)
    @PreAuthorize("hasAnyPerm('ems:equipment:add', 'ems:equipment:edit')")
    public Result<Equipment> saveOrUpdate(@Validated @RequestBody Equipment equipment) {
        return super.saveOrUpdate(equipment);
    }

    @Operation(summary = "删除用能设备")
    @DeleteMapping("/{id}")
    @Override
    @Log(title = "用能设备", businessType = BusinessType.DELETE)
    @PreAuthorize("hasPerm('ems:equipment:remove')")
    public Result<String> delete(@PathVariable Long id) {
        return super.delete(id);
    }

    @Operation(summary = "批量删除用能设备")
    @DeleteMapping
    @Override
    @Log(title = "用能设备", businessType = BusinessType.DELETE)
    @PreAuthorize("hasPerm('ems:equipment:remove')")
    public Result<String> deleteBatch(@RequestBody List<Long> ids) {
        return super.deleteBatch(ids);
    }

    @Operation(summary = "分页查询")
    @PreAuthorize("hasPerm('ems:equipment:list')")
    @GetMapping
    public Result<Map<String, Object>> findByPage(Pager pager,
            @RequestParam(required = false) String code,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String type) {
        Specification<Equipment> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.hasText(code)) {
                predicates.add(cb.like(root.get("code"), "%" + code + "%"));
            }
            if (StringUtils.hasText(name)) {
                predicates.add(cb.like(root.get("name"), "%" + name + "%"));
            }
            if (StringUtils.hasText(type)) {
                predicates.add(cb.equal(root.get("type"), type));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
        return findByPage(pager, spec);
    }

    @Operation(summary = "按ID查询")
    @PreAuthorize("hasPerm('ems:equipment:query')")
    @GetMapping("/{id}")
    @Override
    public Result<Equipment> findById(@PathVariable Long id) {
        return super.findById(id);
    }

    @Operation(summary = "查询所有用能设备")
    @PreAuthorize("hasPerm('ems:equipment:list')")
    @GetMapping("/all")
    public Result<List<Equipment>> findAll() {
        return super.findAll();
    }

    @Operation(summary = "根据用能单元查询设备")
    @GetMapping("/energy-unit/{energyUnitId}")
    public Result<List<Equipment>> findByEnergyUnitId(@PathVariable Long energyUnitId) {
        return Result.content(equipmentService.findByEnergyUnitId(energyUnitId));
    }
}
