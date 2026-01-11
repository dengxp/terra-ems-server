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
import com.terra.ems.ems.dto.BenchmarkAnalysisDTO;
import com.terra.ems.ems.dto.BranchAnalysisDTO;
import com.terra.ems.ems.dto.ComparisonAnalysisDTO;
import com.terra.ems.ems.dto.EnergyStatisticsSummaryDTO;
import com.terra.ems.ems.dto.ProcessEnergyAnalysisDTO;
import com.terra.ems.ems.dto.UnitConsumptionDTO;
import com.terra.ems.ems.service.EnergyStatisticsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 能耗统计分析控制器
 *
 * @author dengxueping
 */
@RestController
@RequestMapping("/statistics")
@Tag(name = "能耗统计分析")
@RequiredArgsConstructor
public class EnergyStatisticsController {

    private final EnergyStatisticsService energyStatisticsService;

    @Operation(summary = "获取统计汇总")
    @GetMapping("/summary")
    public Result<EnergyStatisticsSummaryDTO> getSummary(
            @Parameter(description = "用能单元ID") @RequestParam Long energyUnitId,
            @Parameter(description = "时间类型：DAY/MONTH/YEAR") @RequestParam(defaultValue = "DAY") String timeType,
            @Parameter(description = "数据时间") @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime dataTime) {
        return Result.content(energyStatisticsService.getSummary(energyUnitId, timeType, dataTime));
    }

    @Operation(summary = "获取同比分析")
    @GetMapping("/yoy")
    public Result<List<ComparisonAnalysisDTO>> getYoYAnalysis(
            @Parameter(description = "父用能单元ID") @RequestParam Long parentUnitId,
            @Parameter(description = "时间类型：DAY/MONTH/YEAR") @RequestParam(defaultValue = "MONTH") String timeType,
            @Parameter(description = "数据时间") @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime dataTime) {
        return Result.content(energyStatisticsService.getYoYAnalysis(parentUnitId, timeType, dataTime));
    }

    @Operation(summary = "获取环比分析")
    @GetMapping("/mom")
    public Result<List<ComparisonAnalysisDTO>> getMoMAnalysis(
            @Parameter(description = "父用能单元ID") @RequestParam Long parentUnitId,
            @Parameter(description = "时间类型：DAY/MONTH/YEAR") @RequestParam(defaultValue = "MONTH") String timeType,
            @Parameter(description = "数据时间") @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime dataTime) {
        return Result.content(energyStatisticsService.getMoMAnalysis(parentUnitId, timeType, dataTime));
    }

    @Operation(summary = "获取综合能耗汇总")
    @GetMapping("/comprehensive/summary")
    public Result<EnergyStatisticsSummaryDTO> getComprehensiveSummary(
            @Parameter(description = "用能单元ID") @RequestParam Long energyUnitId,
            @Parameter(description = "时间类型：DAY/MONTH/YEAR") @RequestParam(defaultValue = "MONTH") String timeType,
            @Parameter(description = "数据时间") @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime dataTime) {
        return Result.content(energyStatisticsService.getComprehensiveSummary(energyUnitId, timeType, dataTime));
    }

    @Operation(summary = "获取单元能耗排名")
    @GetMapping("/ranking")
    public Result<List<ComparisonAnalysisDTO>> getRankingAnalysis(
            @Parameter(description = "父用能单元ID") @RequestParam Long parentUnitId,
            @Parameter(description = "时间类型：DAY/MONTH/YEAR") @RequestParam(defaultValue = "MONTH") String timeType,
            @Parameter(description = "数据时间") @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime dataTime) {
        return Result.content(energyStatisticsService.getRankingAnalysis(parentUnitId, timeType, dataTime));
    }

    @Operation(summary = "获取工序能耗分析")
    @GetMapping("/process-energy")
    public Result<List<ProcessEnergyAnalysisDTO>> getProcessEnergyAnalysis(
            @Parameter(description = "父用能单元ID") @RequestParam Long parentUnitId,
            @Parameter(description = "时间类型：DAY/MONTH/YEAR") @RequestParam(defaultValue = "DAY") String timeType,
            @Parameter(description = "数据时间") @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime dataTime,
            @Parameter(description = "能源类型ID") @RequestParam(required = false) Long energyTypeId) {
        return Result.content(
                energyStatisticsService.getProcessEnergyAnalysis(parentUnitId, timeType, dataTime, energyTypeId));
    }

    @Operation(summary = "获取单耗分析")
    @GetMapping("/unit-consumption")
    public Result<UnitConsumptionDTO> getUnitConsumptionAnalysis(
            @Parameter(description = "用能单元ID") @RequestParam Long energyUnitId,
            @Parameter(description = "时间类型：DAY/MONTH/YEAR") @RequestParam(defaultValue = "MONTH") String timeType,
            @Parameter(description = "数据时间") @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime dataTime,
            @Parameter(description = "能源类型ID") @RequestParam(required = false) Long energyTypeId,
            @Parameter(description = "产品名称") @RequestParam(required = false) String productName) {
        return Result.content(energyStatisticsService.getUnitConsumptionAnalysis(energyUnitId, timeType, dataTime,
                energyTypeId, productName));
    }

    // ==================== 支路分析 ====================

    @Operation(summary = "获取支路能耗分析")
    @GetMapping("/branch")
    public Result<List<BranchAnalysisDTO>> getBranchAnalysis(
            @Parameter(description = "父用能单元ID") @RequestParam Long parentUnitId,
            @Parameter(description = "时间类型：DAY/MONTH/YEAR") @RequestParam(defaultValue = "DAY") String timeType,
            @Parameter(description = "数据时间") @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime dataTime,
            @Parameter(description = "能源类型ID") @RequestParam(required = false) Long energyTypeId) {
        return Result.content(
                energyStatisticsService.getBranchAnalysis(parentUnitId, timeType, dataTime, energyTypeId));
    }

    // ==================== 对标分析 ====================

    @Operation(summary = "获取对标分析")
    @GetMapping("/benchmark")
    public Result<List<BenchmarkAnalysisDTO>> getBenchmarkAnalysis(
            @Parameter(description = "父用能单元ID") @RequestParam Long parentUnitId,
            @Parameter(description = "时间类型：DAY/MONTH/YEAR") @RequestParam(defaultValue = "MONTH") String timeType,
            @Parameter(description = "数据时间") @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime dataTime,
            @Parameter(description = "标杆类型") @RequestParam(required = false) String benchmarkType) {
        return Result.content(
                energyStatisticsService.getBenchmarkAnalysis(parentUnitId, timeType, dataTime, benchmarkType));
    }
}
