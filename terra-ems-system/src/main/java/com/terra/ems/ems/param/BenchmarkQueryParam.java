/*
 * Copyright (c) 2024 泰若科技（广州）有限公司. All rights reserved.
 */

package com.terra.ems.ems.param;

import com.terra.ems.ems.enums.BenchmarkType;
import com.terra.ems.framework.enums.DataItemStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 对标值查询参数
 *
 * @author dengxueping
 * @since 2026-02-08
 */
@Data
@Schema(description = "对标值查询参数")
public class BenchmarkQueryParam {

    @Schema(description = "关键词 (名称/备注)")
    private String keyword;

    @Schema(description = "对标类型")
    private BenchmarkType type;

    @Schema(description = "状态")
    private DataItemStatus status;
}
