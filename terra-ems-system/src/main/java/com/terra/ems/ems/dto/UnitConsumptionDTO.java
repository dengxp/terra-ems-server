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
 * Name: UnitConsumptionDTO.java
 * Email: dengxueping@gmail.com
 * Date: 2026-01-10
 * Description:
 * 单耗分析DTO
 *
 * @author dengxueping
 */
@Data
@Schema(title = "单耗分析")
public class UnitConsumptionDTO {

    @Schema(title = "用能单元ID")
    private Long energyUnitId;

    @Schema(title = "用能单元名称")
    private String energyUnitName;

    @Schema(title = "产量")
    private BigDecimal production;

    @Schema(title = "产量单位")
    private String productionUnit;

    @Schema(title = "能耗（折标煤）")
    private BigDecimal energyConsumption;

    @Schema(title = "能耗单位")
    private String energyUnit = "tce";

    @Schema(title = "单耗（能耗/产量）")
    private BigDecimal unitConsumption;

    @Schema(title = "同比单耗")
    private BigDecimal lastYearUnitConsumption;

    @Schema(title = "同比变化率（%）")
    private BigDecimal yoyRate;

    @Schema(title = "环比单耗")
    private BigDecimal lastPeriodUnitConsumption;

    @Schema(title = "环比变化率（%）")
    private BigDecimal momRate;

    @Schema(title = "趋势数据")
    private List<TrendItem> trendData;

    @Data
    @Schema(title = "单耗趋势项")
    public static class TrendItem {
        @Schema(title = "时间标签")
        private String label;

        @Schema(title = "单耗值")
        private BigDecimal unitConsumption;

        @Schema(title = "产能量")
        private BigDecimal production;

        @Schema(title = "能耗量")
        private BigDecimal energyConsumption;
    }
}
