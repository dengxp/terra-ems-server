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

    @Schema(title = "产品名称")
    private String name;

    @Schema(title = "产品类型")
    private String type;

    @Schema(title = "状态")
    private DataItemStatus status;
}
