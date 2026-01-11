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

package com.terra.ems.ems.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * Name: CostTrendDTO.java
 * Email: dengxueping@gmail.com
 * Date: 2026-01-10
 * Description:
 * 成本趋势分析 DTO
 *
 * @author dengxueping
 */
@Data
@Schema(description = "成本趋势分析结果")
public class CostTrendDTO {

    @Schema(description = "汇总表格数据")
    private List<TrendTableItem> tableItems;

    @Schema(description = "趋势图表数据")
    private List<TrendChartData> chartData;

    /**
     * 汇总表格项
     */
    @Data
    @Schema(description = "趋势表格项")
    public static class TrendTableItem {
        @Schema(description = "时间标签")
        private String dateLabel;

        @Schema(description = "总费用(元)")
        private BigDecimal totalCost;

        @Schema(description = "能源明细列表")
        private List<EnergyItem> energyItems;
    }

    /**
     * 能源明细
     */
    @Data
    @Schema(description = "能源明细")
    public static class EnergyItem {
        @Schema(description = "能源类型ID")
        private Long energyTypeId;

        @Schema(description = "能源类型名称")
        private String energyTypeName;

        @Schema(description = "能源类型编码")
        private String energyTypeCode;

        @Schema(description = "消耗量")
        private BigDecimal consumption;

        @Schema(description = "费用(元)")
        private BigDecimal cost;

        @Schema(description = "单位")
        private String unit;
    }

    /**
     * 趋势图表数据
     */
    @Data
    @Schema(description = "趋势图表数据")
    public static class TrendChartData {
        @Schema(description = "能源类型ID")
        private Long energyTypeId;

        @Schema(description = "能源类型名称")
        private String energyTypeName;

        @Schema(description = "能源单位")
        private String energyUnit;

        @Schema(description = "费用标签")
        private String costLabel;

        @Schema(description = "消耗量标签")
        private String consumptionLabel;

        @Schema(description = "时间轴标签列表")
        private List<String> timeLabels;

        @Schema(description = "费用数据列表")
        private List<BigDecimal> costValues;

        @Schema(description = "消耗量数据列表")
        private List<BigDecimal> consumptionValues;
    }
}
