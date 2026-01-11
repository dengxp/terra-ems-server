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
import com.terra.ems.framework.controller.BaseController;
import com.terra.ems.framework.enums.DataItemStatus;
import com.terra.ems.framework.service.BaseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用能单元控制器
 *
 * @author dengxueping
 * @since 2026-01-11
 */
@Tag(name = "用能单元管理")
@RestController
@RequestMapping("/energy-units")
public class EnergyUnitController extends BaseController<EnergyUnit, Long> {

    private final EnergyUnitService energyUnitService;

    public EnergyUnitController(EnergyUnitService energyUnitService) {
        this.energyUnitService = energyUnitService;
    }

    @Override
    protected BaseService<EnergyUnit, Long> getService() {
        return energyUnitService;
    }

    /**
     * 隐藏基类的 saveOrUpdate 方法，使用 create 替代（因需 parentId 参数）
     */
    @Override
    public Result<EnergyUnit> saveOrUpdate(EnergyUnit entity) {
        throw new UnsupportedOperationException("请使用 POST /energy-units?parentId= 的 create 方法");
    }

    /**
     * 获取完整树形结构
     */
    @Operation(summary = "获取完整树形结构")
    @GetMapping("/tree")
    public Result<List<EnergyUnit>> findTree() {
        return Result.content(energyUnitService.getTree());
    }

    /**
     * 获取启用状态的树形结构
     */
    @Operation(summary = "获取启用状态的树形结构")
    @GetMapping("/tree/enabled")
    public Result<List<EnergyUnit>> findEnabledTree() {
        return Result.content(energyUnitService.getEnabledTree());
    }

    /**
     * 获取启用的用能单元列表
     */
    @Operation(summary = "获取启用的用能单元列表")
    @GetMapping("/enabled")
    public Result<List<EnergyUnit>> findEnabledList() {
        return Result.content(energyUnitService.findEnabled());
    }

    /**
     * 获取子节点（懒加载）
     */
    @Operation(summary = "获取子节点（懒加载）")
    @GetMapping("/{parentId}/children")
    public Result<List<EnergyUnit>> findChildren(@PathVariable Long parentId) {
        return Result.content(energyUnitService.getChildren(parentId));
    }

    /**
     * 根据编码查询用能单元
     */
    @Operation(summary = "根据编码查询用能单元")
    @GetMapping("/code/{code}")
    public Result<EnergyUnit> findByCode(@PathVariable String code) {
        return energyUnitService.findByCode(code)
                .map(Result::content)
                .orElse(Result.failure("用能单元不存在"));
    }

    /**
     * 创建用能单元
     */
    @Operation(summary = "创建用能单元")
    @PostMapping("/create")
    public Result<EnergyUnit> create(
            @RequestBody EnergyUnit energyUnit,
            @Parameter(description = "父节点ID，不传表示创建根节点") @RequestParam(required = false) Long parentId) {
        return Result.content(energyUnitService.create(energyUnit, parentId));
    }

    /**
     * 移动节点（更改父节点）
     */
    @Operation(summary = "移动节点（更改父节点）")
    @PatchMapping("/{id}/move")
    public Result<EnergyUnit> move(
            @PathVariable Long id,
            @Parameter(description = "新父节点ID，不传表示移动到根级别") @RequestParam(required = false) Long newParentId) {
        return Result.content(energyUnitService.move(id, newParentId));
    }

    /**
     * 修改用能单元状态
     */
    @Operation(summary = "修改用能单元状态")
    @PatchMapping("/{id}/status")
    public Result<EnergyUnit> updateStatus(
            @PathVariable Long id,
            @RequestParam DataItemStatus status) {
        return Result.content(energyUnitService.updateStatus(id, status));
    }

    // BaseController 已提供标准 update(via saveOrUpdate), getById, delete
}
