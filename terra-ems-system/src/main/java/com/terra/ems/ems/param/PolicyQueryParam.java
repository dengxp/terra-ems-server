/*
 * Copyright (c) 2024 泰若科技（广州）有限公司. All rights reserved.
 */

package com.terra.ems.ems.param;

import com.terra.ems.ems.enums.PolicyType;
import com.terra.ems.framework.enums.DataItemStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 政策法规查询参数
 *
 * @author dengxueping
 * @since 2026-02-08
 */
@Data
@Schema(description = "政策法规查询参数")
public class PolicyQueryParam {

    @Schema(description = "关键词 (标题/摘要/备注)")
    private String keyword;

    @Schema(description = "标题")
    private String title;

    @Schema(description = "政策类型")
    private PolicyType type;

    @Schema(description = "状态")
    private DataItemStatus status;
}
