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
import com.terra.ems.ems.entity.EnergyCostRecord;
import com.terra.ems.ems.enums.RecordPeriodType;
import com.terra.ems.ems.service.EnergyCostRecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.terra.ems.ems.dto.CostDeviationDTO;
import com.terra.ems.ems.dto.CostTrendDTO;

/**
 * 能源成本记录控制器
 *
 * @author dengxueping
 * @since 2026-01-11
 */
@RestController
@RequestMapping("/ems/energy-cost-records")
@Tag(name = "能源成本记录管理")
@RequiredArgsConstructor
public class EnergyCostRecordController {

    private final EnergyCostRecordService service;

    /**
     * 分页条件查询成本记录
     *
     * @param energyUnitId 用能单元ID
     * @param energyTypeId 能源类型ID
     * @param periodType   周期类型
     * @param startDate    开始日期
     * @param endDate      结束日期
     * @param page         页码
     * @param size         每页大小
     * @return 分页结果
     */
    @GetMapping
    @Operation(summary = "分页条件查询")
    public Result<Page<EnergyCostRecord>> search(
            @RequestParam(required = false) @Parameter(description = "用能单元ID") Long energyUnitId,
            @RequestParam(required = false) @Parameter(description = "能源类型ID") Long energyTypeId,
            @RequestParam(required = false) @Parameter(description = "周期类型") RecordPeriodType periodType,
            @RequestParam(required = false) @Parameter(description = "开始日期") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @Parameter(description = "结束日期") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return Result.content(service.findByConditions(energyUnitId, energyTypeId, periodType, startDate, endDate,
                PageRequest.of(page, size)));
    }

    /**
     * 根据ID查询成本记录详情
     *
     * @param id 记录ID
     * @return 成本记录详情
     */
    @GetMapping("/{id}")
    @Operation(summary = "根据ID查询")
    public Result<EnergyCostRecord> findById(@PathVariable Long id) {
        return java.util.Optional.ofNullable(service.findById(id))
                .map(Result::content)
                .orElse(Result.failure("成本记录不存在"));
    }

    /**
     * 按用能单元和日期范围查询成本记录列表
     *
     * @param energyUnitId 用能单元ID
     * @param startDate    开始日期
     * @param endDate      结束日期
     * @return 成本记录列表
     */
    @GetMapping("/energy-unit/{energyUnitId}")
    @Operation(summary = "按用能单元和日期范围查询")
    public Result<List<EnergyCostRecord>> findByEnergyUnitAndDateRange(
            @PathVariable @Parameter(description = "用能单元ID") Long energyUnitId,
            @RequestParam @Parameter(description = "开始日期") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @Parameter(description = "结束日期") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return Result.content(service.findByEnergyUnitAndDateRange(energyUnitId, startDate, endDate));
    }

    /**
     * 统计指定范围内的费用和用量
     *
     * @param energyUnitId 用能单元ID
     * @param startDate    开始日期
     * @param endDate      结束日期
     * @return 统计数据映射 (totalCost, totalConsumption)
     */
    @GetMapping("/statistics")
    @Operation(summary = "统计成本和用量")
    public Result<Map<String, BigDecimal>> getStatistics(
            @RequestParam(required = false) @Parameter(description = "用能单元ID") Long energyUnitId,
            @RequestParam @Parameter(description = "开始日期") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @Parameter(description = "结束日期") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        Map<String, BigDecimal> stats = new HashMap<>();
        stats.put("totalCost", service.sumCostByDateRange(energyUnitId, startDate, endDate));
        stats.put("totalConsumption", service.sumConsumptionByDateRange(energyUnitId, startDate, endDate));
        return Result.content(stats);
    }

    /**
     * 手动创建成本记录
     *
     * @param record 成本记录实体
     * @return 创建后的实体
     */
    @PostMapping
    @Operation(summary = "创建成本记录")
    public Result<EnergyCostRecord> create(@RequestBody EnergyCostRecord record) {
        try {
            return Result.content(service.create(record));
        } catch (Exception e) {
            return Result.failure(e.getMessage());
        }
    }

    /**
     * 更新成本记录详情
     *
     * @param id     记录ID
     * @param record 成本记录实体
     * @return 更新后的实体
     */
    @PutMapping("/{id}")
    @Operation(summary = "更新成本记录")
    public Result<EnergyCostRecord> update(@PathVariable Long id, @RequestBody EnergyCostRecord record) {
        try {
            return Result.content(service.update(id, record));
        } catch (IllegalArgumentException e) {
            return Result.failure(e.getMessage());
        }
    }

    /**
     * 删除指定的成本记录
     *
     * @param id 记录ID
     * @return 操作结果
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "删除成本记录")
    public Result<Void> delete(@PathVariable Long id) {
        try {
            service.deleteById(id);
            return Result.success();
        } catch (Exception e) {
            return Result.failure(e.getMessage());
        }
    }

    /**
     * 获取成本偏差分析数据
     *
     * @param energyUnitId 用能单元ID
     * @param timeType     时间类型 (MONTH/YEAR)
     * @param dataTime     查询日期
     * @return 偏差分析结果
     */
    @GetMapping("/deviation")
    @Operation(summary = "偏差分析")
    public Result<CostDeviationDTO> getDeviationAnalysis(
            @RequestParam(required = false) @Parameter(description = "用能单元ID") Long energyUnitId,
            @RequestParam @Parameter(description = "时间类型 (MONTH/YEAR)") String timeType,
            @RequestParam @Parameter(description = "查询日期") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataTime) {
        return Result.content(service.getDeviationAnalysis(energyUnitId, timeType, dataTime));
    }

    /**
     * 获取成本趋势分析数据
     *
     * @param energyUnitId 用能单元ID
     * @param timeType     时间类型 (DAY/MONTH/YEAR)
     * @param dataTime     查询日期
     * @return 趋势分析结果
     */
    @GetMapping("/trend")
    @Operation(summary = "成本趋势分析")
    public Result<CostTrendDTO> getCostTrendAnalysis(
            @RequestParam(required = false) @Parameter(description = "用能单元ID") Long energyUnitId,
            @RequestParam @Parameter(description = "时间类型 (DAY/MONTH/YEAR)") String timeType,
            @RequestParam @Parameter(description = "查询日期") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataTime) {
        return Result.content(service.getCostTrendAnalysis(energyUnitId, timeType, dataTime));
    }
}
