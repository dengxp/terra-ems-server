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
 * Name: BranchAnalysisDTO.java
 * Email: dengxueping@gmail.com
 * Date: 2026-01-10
 * Description:
 * 支路能耗分析DTO
 *
 * @author dengxueping
 */
@Data
@Schema(description = "支路能耗分析")
public class BranchAnalysisDTO {

    @Schema(description = "支路ID")
    private Long branchId;

    @Schema(description = "支路名称")
    private String branchName;

    @Schema(description = "电压等级")
    private String voltageLevel;

    @Schema(description = "额定电流(A)")
    private BigDecimal ratedCurrent;

    @Schema(description = "额定功率(kW)")
    private BigDecimal ratedPower;

    @Schema(description = "总能耗")
    private BigDecimal totalConsumption;

    @Schema(description = "单位")
    private String unit;

    @Schema(description = "占比(%)")
    private BigDecimal percentage;

    @Schema(description = "时段能耗数据")
    private List<TimeSlotData> timeSlotData;

    @Data
    @Schema(description = "时段数据")
    public static class TimeSlotData {
        @Schema(description = "时段标签")
        private String label;

        @Schema(description = "能耗值")
        private BigDecimal value;
    }
}
