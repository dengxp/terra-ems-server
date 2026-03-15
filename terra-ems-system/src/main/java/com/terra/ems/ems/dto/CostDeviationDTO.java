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

package com.terra.ems.ems.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 成本偏差分析 DTO
 *
 * @author dengxueping
 * @since 2026-01-11
 */

@Data
@Schema(description = "成本偏差分析结果")
public class CostDeviationDTO {

    @Schema(description = "电力数据")
    private ElectricityData electricityData;

    @Schema(description = "统计数据")
    private StatisticsData statisticsData;

    @Schema(description = "耗电明细列表")
    private List<ConsumptionDetail> consumptionDetails;

    /**
     * 电力数据
     */
    @Data
    @Schema(description = "电力数据")
    public static class ElectricityData {
        @Schema(description = "总电量(千瓦时)")
        private BigDecimal totalConsumption;

        @Schema(description = "总电量同比(%)")
        private BigDecimal totalConsumptionYoy;

        @Schema(description = "总电量环比(%)")
        private BigDecimal totalConsumptionMom;

        @Schema(description = "总电费(元)")
        private BigDecimal totalCost;

        @Schema(description = "总电费同比(%)")
        private BigDecimal totalCostYoy;

        @Schema(description = "总电费环比(%)")
        private BigDecimal totalCostMom;

        @Schema(description = "功率因数")
        private BigDecimal powerFactor;

        @Schema(description = "功率因数同比(%)")
        private BigDecimal powerFactorYoy;

        @Schema(description = "功率因数环比(%)")
        private BigDecimal powerFactorMom;

        @Schema(description = "尖电量(千瓦时)")
        private BigDecimal sharpConsumption;

        @Schema(description = "峰电量(千瓦时)")
        private BigDecimal peakConsumption;

        @Schema(description = "平电量(千瓦时)")
        private BigDecimal flatConsumption;

        @Schema(description = "谷电量(千瓦时)")
        private BigDecimal valleyConsumption;
    }

    /**
     * 统计数据（含差值）
     */
    @Data
    @Schema(description = "统计数据")
    public static class StatisticsData {
        @Schema(description = "总电量(千瓦时)")
        private BigDecimal totalConsumption;

        @Schema(description = "总电量同比(%)")
        private BigDecimal totalConsumptionYoy;

        @Schema(description = "总电量环比(%)")
        private BigDecimal totalConsumptionMom;

        @Schema(description = "总电量差值(千瓦时)")
        private BigDecimal totalConsumptionDiff;

        @Schema(description = "总电费(元)")
        private BigDecimal totalCost;

        @Schema(description = "总电费同比(%)")
        private BigDecimal totalCostYoy;

        @Schema(description = "总电费环比(%)")
        private BigDecimal totalCostMom;

        @Schema(description = "总电费差值(元)")
        private BigDecimal totalCostDiff;

        @Schema(description = "功率因数")
        private BigDecimal powerFactor;

        @Schema(description = "功率因数同比(%)")
        private BigDecimal powerFactorYoy;

        @Schema(description = "功率因数环比(%)")
        private BigDecimal powerFactorMom;

        @Schema(description = "功率因数差值")
        private BigDecimal powerFactorDiff;

        @Schema(description = "尖电量(千瓦时)")
        private BigDecimal sharpConsumption;

        @Schema(description = "峰电量(千瓦时)")
        private BigDecimal peakConsumption;

        @Schema(description = "平电量(千瓦时)")
        private BigDecimal flatConsumption;

        @Schema(description = "谷电量(千瓦时)")
        private BigDecimal valleyConsumption;

        @Schema(description = "尖电量差值(千瓦时)")
        private BigDecimal sharpConsumptionDiff;

        @Schema(description = "峰电量差值(千瓦时)")
        private BigDecimal peakConsumptionDiff;

        @Schema(description = "平电量差值(千瓦时)")
        private BigDecimal flatConsumptionDiff;

        @Schema(description = "谷电量差值(千瓦时)")
        private BigDecimal valleyConsumptionDiff;
    }

    /**
     * 耗电明细
     */
    @Data
    @Schema(description = "耗电明细")
    public static class ConsumptionDetail {
        @Schema(description = "用能单元ID")
        private Long energyUnitId;

        @Schema(description = "用能单元名称")
        private String energyUnitName;

        @Schema(description = "总耗电量(千瓦时)")
        private BigDecimal totalConsumption;

        @Schema(description = "尖电量(千瓦时)")
        private BigDecimal sharpConsumption;

        @Schema(description = "峰电量(千瓦时)")
        private BigDecimal peakConsumption;

        @Schema(description = "平电量(千瓦时)")
        private BigDecimal flatConsumption;

        @Schema(description = "谷电量(千瓦时)")
        private BigDecimal valleyConsumption;

        @Schema(description = "总电费(元)")
        private BigDecimal totalCost;

        @Schema(description = "同比(%)")
        private BigDecimal yoy;

        @Schema(description = "环比(%)")
        private BigDecimal mom;

        @Schema(description = "占比(%)")
        private BigDecimal percentage;
    }
}
