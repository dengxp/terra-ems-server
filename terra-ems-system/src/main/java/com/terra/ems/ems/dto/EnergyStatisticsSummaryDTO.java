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
import java.time.LocalDateTime;
import java.util.List;

/**
 * Name: EnergyStatisticsSummaryDTO.java
 * Email: dengxueping@gmail.com
 * Date: 2026-01-10
 * Description:
 * 能耗统计汇总 DTO
 *
 * @author dengxueping
 */
@Data
@Schema(description = "能耗统计汇总")
public class EnergyStatisticsSummaryDTO {

    @Schema(description = "当期总能耗")
    private BigDecimal currentTotal;

    @Schema(description = "同期总能耗")
    private BigDecimal lastYearTotal;

    @Schema(description = "上期总能耗")
    private BigDecimal lastPeriodTotal;

    @Schema(description = "同比增长率 (%)")
    private BigDecimal yoyRate;

    @Schema(description = "环比增长率 (%)")
    private BigDecimal momRate;

    @Schema(description = "能源类型分布")
    private List<EnergyTypeDistribution> energyTypeDistribution;

    @Schema(description = "趋势数据")
    private List<TrendDataItem> trendData;

    @Data
    @Schema(description = "能源类型分布项")
    public static class EnergyTypeDistribution {
        @Schema(description = "能源类型ID")
        private Long energyTypeId;

        @Schema(description = "能源类型名称")
        private String energyTypeName;

        @Schema(description = "能耗值")
        private BigDecimal value;

        @Schema(description = "占比 (%)")
        private BigDecimal percentage;
    }

    @Data
    @Schema(description = "趋势数据项")
    public static class TrendDataItem {
        @Schema(description = "时间")
        private LocalDateTime dataTime;

        @Schema(description = "时间标签")
        private String label;

        @Schema(description = "能耗值")
        private BigDecimal value;
    }
}
