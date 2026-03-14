/*
 * Copyright (c) 2024-2026 Terra Technology (Guangzhou) Co., Ltd.
 * Copyright (c) 2024-2026 泰若科技（广州）有限公司.
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
import com.terra.ems.ems.dto.ComparisonAnalysisDTO;
import com.terra.ems.ems.dto.EnergyStatisticsSummaryDTO;
import com.terra.ems.ems.service.CarbonEmissionService;
import com.terra.ems.framework.controller.Controller;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 碳排放管理控制器
 *
 * @author dengxueping
 * @since 2026-01-11
 */
@RestController
@RequestMapping("/carbon")
@Tag(name = "碳排放管理")
@RequiredArgsConstructor
public class CarbonEmissionController extends Controller {

    private final CarbonEmissionService carbonEmissionService;

    /**
     * 获取碳排放汇总
     *
     * @param energyUnitId 用能单元ID
     * @param timeType     时间类型：DAY/MONTH/YEAR
     * @param dataTime     数据时间
     * @return 碳排放汇总数据
     */
    @Operation(summary = "获取碳排放汇总")
    @GetMapping("/summary")
    public Result<EnergyStatisticsSummaryDTO> findSummary(
            @Parameter(description = "用能单元ID") @RequestParam Long energyUnitId,
            @Parameter(description = "时间类型：DAY/MONTH/YEAR") @RequestParam(defaultValue = "MONTH") String timeType,
            @Parameter(description = "数据时间") @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime dataTime) {
        return Result.content(carbonEmissionService.getSummary(energyUnitId, timeType, dataTime));
    }

    /**
     * 获取单元碳排放排名
     *
     * @param parentUnitId 父用能单元ID
     * @param timeType     时间类型
     * @param dataTime     数据时间
     * @return 碳排放排名分析列表
     */
    @Operation(summary = "获取单元碳排放排名")
    @GetMapping("/ranking")
    public Result<List<ComparisonAnalysisDTO>> findRanking(
            @Parameter(description = "父用能单元ID") @RequestParam Long parentUnitId,
            @Parameter(description = "时间类型：DAY/MONTH/YEAR") @RequestParam(defaultValue = "MONTH") String timeType,
            @Parameter(description = "数据时间") @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime dataTime) {
        return Result.content(carbonEmissionService.getRanking(parentUnitId, timeType, dataTime));
    }
}
