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
 * 工序能耗分析 DTO
 *
 * @author dengxueping
 * @since 2026-01-11
 */

@Data
@Schema(title = "工序能耗分析")
public class ProcessEnergyAnalysisDTO {

    @Schema(title = "用能单元ID")
    private Long energyUnitId;

    @Schema(title = "用能单元名称")
    private String energyUnitName;

    @Schema(title = "总能耗（折标煤）")
    private BigDecimal totalConsumption;

    @Schema(title = "占比（%）")
    private BigDecimal percentage;

    @Schema(title = "单位")
    private String unit = "tce";

    @Schema(title = "时段能耗明细")
    private List<TimeSlotEnergy> timeSlotData;

    /**
     * 时段能耗
     */
    @Data
    public static class TimeSlotEnergy {
        @Schema(title = "时段标签")
        private String label;

        @Schema(title = "能耗值")
        private BigDecimal value;
    }
}
