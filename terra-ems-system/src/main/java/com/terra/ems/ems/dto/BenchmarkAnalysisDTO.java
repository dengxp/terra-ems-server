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

/**
 * 对标分析 DTO
 *
 * @author dengxueping
 * @since 2026-01-11
 */

@Data
@Schema(description = "对标分析")
public class BenchmarkAnalysisDTO {

    @Schema(description = "用能单元ID")
    private Long energyUnitId;

    @Schema(description = "用能单元名称")
    private String energyUnitName;

    @Schema(description = "实际能耗")
    private BigDecimal actualConsumption;

    @Schema(description = "标杆值")
    private BigDecimal benchmarkValue;

    @Schema(description = "标杆等级")
    private String benchmarkGrade;

    @Schema(description = "标杆编码")
    private String benchmarkCode;

    @Schema(description = "差异值（实际-标杆）")
    private BigDecimal difference;

    @Schema(description = "达标率(%)")
    private BigDecimal complianceRate;

    @Schema(description = "是否达标")
    private Boolean isCompliant;

    @Schema(description = "单位")
    private String unit;

    @Schema(description = "评价（优/良/中/差）")
    private String evaluation;
}
