/*
 * Copyright (c) 2024 泰若科技（广州）有限公司. All rights reserved.
 */

package com.terra.ems.ems.param;

import com.terra.ems.ems.enums.RecordPeriodType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

/**
 * 能源成本记录查询参数
 *
 * @author dengxueping
 * @since 2026-02-08
 */
@Data
@Schema(description = "能源成本记录查询参数")
public class EnergyCostRecordQueryParam {

    @Schema(description = "用能单元ID")
    private Long energyUnitId;

    @Schema(description = "能源类型ID")
    private Long energyTypeId;

    @Schema(description = "周期类型")
    private RecordPeriodType periodType;

    @Schema(description = "开始日期")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate startDate;

    @Schema(description = "结束日期")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate endDate;
}
