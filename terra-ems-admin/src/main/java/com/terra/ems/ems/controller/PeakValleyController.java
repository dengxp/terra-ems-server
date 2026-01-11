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
import com.terra.ems.ems.entity.TimePeriodPrice;
import com.terra.ems.ems.service.PeakValleyAnalysisService;
import com.terra.ems.ems.service.PeakValleyAnalysisService.DailyPeakValleyData;
import com.terra.ems.ems.service.PeakValleyAnalysisService.PeakValleyAnalysisResult;
import com.terra.ems.framework.controller.Controller;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

/**
 * Name: PeakValleyController.java
 * Email: dengxueping@gmail.com
 * Date: 2026-01-11
 * Description: 尖峰平谷分析控制器
 *
 * @author dengxueping
 */
@Tag(name = "尖峰平谷分析")
@RestController
@RequestMapping("/peak-valley")
@RequiredArgsConstructor
public class PeakValleyController {

    private final PeakValleyAnalysisService peakValleyAnalysisService;

    @Operation(summary = "按日分析")
    @GetMapping("/daily")
    public Result<PeakValleyAnalysisResult> getDailyAnalysis(
            @Parameter(description = "用能单元ID") @RequestParam Long energyUnitId,
            @Parameter(description = "日期，格式 yyyy-MM-dd") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        PeakValleyAnalysisResult result = peakValleyAnalysisService.getDailyAnalysis(energyUnitId, date);
        return Result.content(result);
    }

    @Operation(summary = "按月分析")
    @GetMapping("/monthly")
    public Result<PeakValleyAnalysisResult> getMonthlyAnalysis(
            @Parameter(description = "用能单元ID") @RequestParam Long energyUnitId,
            @Parameter(description = "年月，格式 yyyy-MM") @RequestParam String yearMonth) {
        YearMonth ym = YearMonth.parse(yearMonth);
        PeakValleyAnalysisResult result = peakValleyAnalysisService.getMonthlyAnalysis(energyUnitId, ym);
        return Result.content(result);
    }

    @Operation(summary = "按年分析")
    @GetMapping("/yearly")
    public Result<PeakValleyAnalysisResult> getYearlyAnalysis(
            @Parameter(description = "用能单元ID") @RequestParam Long energyUnitId,
            @Parameter(description = "年份") @RequestParam int year) {
        PeakValleyAnalysisResult result = peakValleyAnalysisService.getYearlyAnalysis(energyUnitId, year);
        return Result.content(result);
    }

    @Operation(summary = "时段汇总")
    @GetMapping("/summary")
    public Result<PeakValleyAnalysisResult> getPeriodSummary(
            @Parameter(description = "用能单元ID") @RequestParam Long energyUnitId,
            @Parameter(description = "开始日期，格式 yyyy-MM-dd") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @Parameter(description = "结束日期，格式 yyyy-MM-dd") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        PeakValleyAnalysisResult result = peakValleyAnalysisService.getPeriodSummary(energyUnitId, startDate, endDate);
        return Result.content(result);
    }

    @Operation(summary = "获取每日详细数据")
    @GetMapping("/daily-details")
    public Result<List<DailyPeakValleyData>> getDailyDetailedData(
            @Parameter(description = "用能单元ID") @RequestParam Long energyUnitId,
            @Parameter(description = "开始日期，格式 yyyy-MM-dd") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @Parameter(description = "结束日期，格式 yyyy-MM-dd") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<DailyPeakValleyData> result = peakValleyAnalysisService.getDailyDetailedData(energyUnitId, startDate,
                endDate);
        return Result.content(result);
    }

    @Operation(summary = "获取电价配置列表")
    @GetMapping("/price-configs")
    public Result<List<TimePeriodPrice>> getPriceConfigs() {
        return Result.content(peakValleyAnalysisService.getPriceConfigs());
    }
}
