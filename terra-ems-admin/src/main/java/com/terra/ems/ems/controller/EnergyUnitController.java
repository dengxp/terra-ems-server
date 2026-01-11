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

package com.terra.ems.ems.controller;

import com.terra.ems.common.domain.Result;
import com.terra.ems.ems.entity.EnergyUnit;
import com.terra.ems.ems.service.EnergyUnitService;
import com.terra.ems.framework.enums.DataItemStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用能单元控制器
 *
 * @author dengxueping
 */
@Tag(name = "用能单元管理")
@RestController
@RequestMapping("/energy-units")
@RequiredArgsConstructor
public class EnergyUnitController {

    private final EnergyUnitService energyUnitService;

    @Operation(summary = "获取完整树形结构")
    @GetMapping("/tree")
    public Result<List<EnergyUnit>> getTree() {
        return Result.content(energyUnitService.getTree());
    }

    @Operation(summary = "获取启用状态的树形结构")
    @GetMapping("/tree/enabled")
    public Result<List<EnergyUnit>> getEnabledTree() {
        return Result.content(energyUnitService.getEnabledTree());
    }

    @Operation(summary = "获取启用的用能单元列表")
    @GetMapping("/enabled")
    public Result<List<EnergyUnit>> getEnabledList() {
        return Result.content(energyUnitService.findEnabled());
    }

    @Operation(summary = "获取子节点（懒加载）")
    @GetMapping("/{parentId}/children")
    public Result<List<EnergyUnit>> getChildren(@PathVariable Long parentId) {
        return Result.content(energyUnitService.getChildren(parentId));
    }

    @Operation(summary = "根据ID查询用能单元")
    @GetMapping("/{id}")
    public Result<EnergyUnit> getById(@PathVariable Long id) {
        return java.util.Optional.ofNullable(energyUnitService.findById(id))
                .map(Result::content)
                .orElse(Result.failure("用能单元不存在"));
    }

    @Operation(summary = "根据编码查询用能单元")
    @GetMapping("/code/{code}")
    public Result<EnergyUnit> getByCode(@PathVariable String code) {
        return energyUnitService.findByCode(code)
                .map(Result::content)
                .orElse(Result.failure("用能单元不存在"));
    }

    @Operation(summary = "创建用能单元")
    @PostMapping
    public Result<EnergyUnit> create(
            @RequestBody EnergyUnit energyUnit,
            @Parameter(description = "父节点ID，不传表示创建根节点") @RequestParam(required = false) Long parentId) {
        try {
            EnergyUnit created = energyUnitService.create(energyUnit, parentId);
            return Result.content(created);
        } catch (IllegalArgumentException e) {
            return Result.failure(e.getMessage());
        }
    }

    @Operation(summary = "更新用能单元")
    @PutMapping("/{id}")
    public Result<EnergyUnit> update(@PathVariable Long id, @RequestBody EnergyUnit energyUnit) {
        try {
            EnergyUnit updated = energyUnitService.update(id, energyUnit);
            return Result.content(updated);
        } catch (IllegalArgumentException e) {
            return Result.failure(e.getMessage());
        }
    }

    @Operation(summary = "移动节点（更改父节点）")
    @PatchMapping("/{id}/move")
    public Result<EnergyUnit> move(
            @PathVariable Long id,
            @Parameter(description = "新父节点ID，不传表示移动到根级别") @RequestParam(required = false) Long newParentId) {
        try {
            EnergyUnit moved = energyUnitService.move(id, newParentId);
            return Result.content(moved);
        } catch (IllegalArgumentException e) {
            return Result.failure(e.getMessage());
        }
    }

    @Operation(summary = "删除用能单元")
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        try {
            energyUnitService.delete(id);
            return Result.success();
        } catch (IllegalArgumentException e) {
            return Result.failure(e.getMessage());
        }
    }

    @Operation(summary = "修改用能单元状态")
    @PatchMapping("/{id}/status")
    public Result<EnergyUnit> updateStatus(
            @PathVariable Long id,
            @RequestParam DataItemStatus status) {
        try {
            EnergyUnit updated = energyUnitService.updateStatus(id, status);
            return Result.content(updated);
        } catch (IllegalArgumentException e) {
            return Result.failure(e.getMessage());
        }
    }
}
