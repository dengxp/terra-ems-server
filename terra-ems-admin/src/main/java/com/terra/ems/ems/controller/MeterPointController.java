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

import com.terra.ems.framework.controller.BaseController;
import com.terra.ems.framework.service.BaseService;
import com.terra.ems.common.domain.Result;
import com.terra.ems.ems.entity.MeterPoint;
import com.terra.ems.ems.service.MeterPointService;
import com.terra.ems.framework.enums.DataItemStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
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
 * @since 2026-01-11
 */
@Tag(name = "采集点位管理")
@RestController
@RequestMapping("/meter-points")
public class MeterPointController extends BaseController<MeterPoint, Long> {

    private final MeterPointService meterPointService;

    public MeterPointController(MeterPointService meterPointService) {
        this.meterPointService = meterPointService;
    }

    @Override
    protected BaseService<MeterPoint, Long> getService() {
        return meterPointService;
    }

    /**
     * 分页查询采集点位
     */
    @Operation(summary = "分页查询")
    @GetMapping("/search")
    public Result<Page<MeterPoint>> search(
            @Parameter(description = "页码") @RequestParam(defaultValue = "1") int current,
            @Parameter(description = "每页数量") @RequestParam(defaultValue = "10") int pageSize,
            @Parameter(description = "排序字段") @RequestParam(defaultValue = "sortOrder") String sortField,
            @Parameter(description = "排序方向") @RequestParam(defaultValue = "asc") String sortOrder) {
        Sort sort = "desc".equalsIgnoreCase(sortOrder)
                ? Sort.by(sortField).descending()
                : Sort.by(sortField).ascending();
        Pageable pageable = PageRequest.of(current - 1, pageSize, sort);
        return Result.content(meterPointService.findAll(pageable));
    }

    /**
     * 根据编码查询采集点位
     */
    @Operation(summary = "根据编码查询采集点位")
    @GetMapping("/code/{code}")
    public Result<MeterPoint> findByCode(@PathVariable String code) {
        return meterPointService.findByCode(code)
                .map(Result::content)
                .orElse(Result.failure("采集点位不存在"));
    }

    /**
     * 根据计量器具ID查询采集点位
     */
    @Operation(summary = "根据计量器具ID查询采集点位")
    @GetMapping("/meter/{meterId}")
    public Result<List<MeterPoint>> findByMeterId(@PathVariable Long meterId) {
        return Result.content(meterPointService.findByMeterId(meterId));
    }

    /**
     * 根据能源类型ID查询采集点位
     */
    @Operation(summary = "根据能源类型ID查询采集点位")
    @GetMapping("/energy-type/{energyTypeId}")
    public Result<List<MeterPoint>> findByEnergyTypeId(@PathVariable Long energyTypeId) {
        return Result.content(meterPointService.findByEnergyTypeId(energyTypeId));
    }

    /**
     * 根据用能单元ID查询关联的采集点位
     */
    @Operation(summary = "根据用能单元ID查询关联的采集点位")
    @GetMapping("/energy-unit/{energyUnitId}")
    public Result<List<MeterPoint>> findByEnergyUnitId(@PathVariable Long energyUnitId) {
        return Result.content(meterPointService.findByEnergyUnitId(energyUnitId));
    }

    /**
     * 创建采集点位
     */
    @Operation(summary = "创建采集点位")
    @PostMapping("/create")
    public Result<MeterPoint> create(
            @RequestBody MeterPoint meterPoint,
            @Parameter(description = "计量器具ID") @RequestParam(required = false) Long meterId,
            @Parameter(description = "能源类型ID") @RequestParam(required = false) Long energyTypeId) {
        return Result.content(meterPointService.create(meterPoint, meterId, energyTypeId));
    }

    /**
     * 更新采集点位
     */
    @Operation(summary = "更新采集点位")
    @PutMapping("/{id}")
    public Result<MeterPoint> update(
            @PathVariable Long id,
            @RequestBody MeterPoint meterPoint,
            @Parameter(description = "计量器具ID") @RequestParam(required = false) Long meterId,
            @Parameter(description = "能源类型ID") @RequestParam(required = false) Long energyTypeId) {
        return Result.content(meterPointService.update(id, meterPoint, meterId, energyTypeId));
    }

    // 删除 (delete) 使用 BaseController 默认实现，但需注意 meterPointService.delete 是否有重写逻辑。
    // 原代码 meterPointService.delete(id) 存在。BaseController 调用
    // getService().delete(id)。
    // 如果 Service 逻辑一致，则无需重写。原 Controller 是 delete(@PathVariable Long id)。
    // BaseController 也是，所以可以直接复用。

    /**
     * 修改采集点位状态
     */
    @Operation(summary = "修改采集点位状态")
    @PatchMapping("/{id}/status")
    public Result<MeterPoint> updateStatus(
            @PathVariable Long id,
            @RequestParam DataItemStatus status) {
        return Result.content(meterPointService.updateStatus(id, status));
    }

    /**
     * 关联用能单元
     */
    @Operation(summary = "关联用能单元")
    @PostMapping("/{id}/energy-units")
    public Result<MeterPoint> assignEnergyUnits(
            @PathVariable Long id,
            @RequestBody Set<Long> energyUnitIds) {
        return Result.content(meterPointService.assignEnergyUnits(id, energyUnitIds));
    }
}
