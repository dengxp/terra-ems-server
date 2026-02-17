package com.terra.ems.system.param;

import com.terra.ems.framework.enums.DataItemStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 部门查询参数
 *
 * @author dengxueping
 * @since 2026-02-16
 */
@Data
@Schema(title = "部门查询参数")
public class SysDeptQueryParam {

    @Schema(title = "部门名称")
    private String name;

    @Schema(title = "部门状态")
    private DataItemStatus status;

    @Schema(title = "父部门ID")
    private Long parentId;
}
