package com.terra.ems.ems.param;

import com.terra.ems.framework.enums.DataItemStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 产品查询参数
 *
 * @author dengxueping
 * @since 2026-01-26
 */
@Data
@Schema(title = "产品查询参数")
public class ProductQueryParam {

    @Schema(title = "产品编码")
    private String code;

    @Schema(title = "产品名称")
    private String name;

    @Schema(title = "产品类型")
    private String type;

    @Schema(description = "状态 (0:启用, 1:停用)")
    private Integer status;
}
