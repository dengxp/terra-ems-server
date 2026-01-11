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
import com.terra.ems.ems.entity.MeterPoint;
import com.terra.ems.ems.service.MeterPointService;
import com.terra.ems.framework.enums.DataItemStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * 采集点位控制器
 *
 * @author dengxueping
 */
@Tag(name = "采集点位管理")
@RestController
@RequestMapping("/meter-points")
@RequiredArgsConstructor
public class MeterPointController {

    private final MeterPointService meterPointService;

    @Operation(summary = "分页查询采集点位")
    @GetMapping
    public Result<Page<MeterPoint>> list(
            @Parameter(description = "页码") @RequestParam(defaultValue = "1") int current,
            @Parameter(description = "每页数量") @RequestParam(defaultValue = "10") int pageSize,
            @Parameter(description = "排序字段") @RequestParam(defaultValue = "sortOrder") String sortField,
            @Parameter(description = "排序方向") @RequestParam(defaultValue = "asc") String sortOrder) {
        Sort sort = sortOrder.equalsIgnoreCase("desc")
                ? Sort.by(sortField).descending()
                : Sort.by(sortField).ascending();
        Pageable pageable = PageRequest.of(current - 1, pageSize, sort);
        return Result.content(meterPointService.findAll(pageable));
    }

    @Operation(summary = "根据ID查询采集点位")
    @GetMapping("/{id}")
    public Result<MeterPoint> getById(@PathVariable Long id) {
        return java.util.Optional.ofNullable(meterPointService.findById(id))
                .map(Result::content)
                .orElse(Result.failure("采集点位不存在"));
    }

    @Operation(summary = "根据编码查询采集点位")
    @GetMapping("/code/{code}")
    public Result<MeterPoint> getByCode(@PathVariable String code) {
        return meterPointService.findByCode(code)
                .map(Result::content)
                .orElse(Result.failure("采集点位不存在"));
    }

    @Operation(summary = "根据计量器具ID查询采集点位")
    @GetMapping("/meter/{meterId}")
    public Result<List<MeterPoint>> getByMeterId(@PathVariable Long meterId) {
        return Result.content(meterPointService.findByMeterId(meterId));
    }

    @Operation(summary = "根据能源类型ID查询采集点位")
    @GetMapping("/energy-type/{energyTypeId}")
    public Result<List<MeterPoint>> getByEnergyTypeId(@PathVariable Long energyTypeId) {
        return Result.content(meterPointService.findByEnergyTypeId(energyTypeId));
    }

    @Operation(summary = "根据用能单元ID查询关联的采集点位")
    @GetMapping("/energy-unit/{energyUnitId}")
    public Result<List<MeterPoint>> getByEnergyUnitId(@PathVariable Long energyUnitId) {
        return Result.content(meterPointService.findByEnergyUnitId(energyUnitId));
    }

    @Operation(summary = "创建采集点位")
    @PostMapping
    public Result<MeterPoint> create(
            @RequestBody MeterPoint meterPoint,
            @Parameter(description = "计量器具ID") @RequestParam(required = false) Long meterId,
            @Parameter(description = "能源类型ID") @RequestParam(required = false) Long energyTypeId) {
        try {
            MeterPoint created = meterPointService.create(meterPoint, meterId, energyTypeId);
            return Result.content(created);
        } catch (IllegalArgumentException e) {
            return Result.failure(e.getMessage());
        }
    }

    @Operation(summary = "更新采集点位")
    @PutMapping("/{id}")
    public Result<MeterPoint> update(
            @PathVariable Long id,
            @RequestBody MeterPoint meterPoint,
            @Parameter(description = "计量器具ID") @RequestParam(required = false) Long meterId,
            @Parameter(description = "能源类型ID") @RequestParam(required = false) Long energyTypeId) {
        try {
            MeterPoint updated = meterPointService.update(id, meterPoint, meterId, energyTypeId);
            return Result.content(updated);
        } catch (IllegalArgumentException e) {
            return Result.failure(e.getMessage());
        }
    }

    @Operation(summary = "删除采集点位")
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        try {
            meterPointService.delete(id);
            return Result.success();
        } catch (IllegalArgumentException e) {
            return Result.failure(e.getMessage());
        }
    }

    @Operation(summary = "修改采集点位状态")
    @PatchMapping("/{id}/status")
    public Result<MeterPoint> updateStatus(
            @PathVariable Long id,
            @RequestParam DataItemStatus status) {
        try {
            MeterPoint updated = meterPointService.updateStatus(id, status);
            return Result.content(updated);
        } catch (IllegalArgumentException e) {
            return Result.failure(e.getMessage());
        }
    }

    @Operation(summary = "关联用能单元")
    @PostMapping("/{id}/energy-units")
    public Result<MeterPoint> assignEnergyUnits(
            @PathVariable Long id,
            @RequestBody Set<Long> energyUnitIds) {
        try {
            MeterPoint updated = meterPointService.assignEnergyUnits(id, energyUnitIds);
            return Result.content(updated);
        } catch (IllegalArgumentException e) {
            return Result.failure(e.getMessage());
        }
    }
}
