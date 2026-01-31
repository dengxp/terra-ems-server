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

package com.terra.ems.ems.entity;

import com.terra.ems.ems.enums.TimeGranularity;
import com.terra.ems.framework.jpa.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 产品产量记录
 * 用于记录各用能单元的产品产量数据，支持单耗分析计算
 *
 * @author dengxueping
 * @since 2026-01-11
 */

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "ems_production_record", indexes = {
        @Index(name = "idx_production_energy_unit", columnList = "energy_unit_id"),
        @Index(name = "idx_production_date", columnList = "record_date"),
        @Index(name = "idx_production_product", columnList = "product_name")
})
@Schema(title = "产品产量记录")
public class ProductionRecord extends BaseEntity {

    @Schema(title = "ID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(title = "用能单元ID", description = "关联ems_energy_unit表")
    @Column(name = "energy_unit_id", nullable = false)
    private Long energyUnitId;

    @Schema(title = "记录时间")
    @Column(name = "record_date", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime recordDate;

    @Schema(title = "产品名称")
    @Column(name = "product_name", length = 100, nullable = false)
    private String productName;

    @Schema(title = "产量")
    @Column(name = "quantity", precision = 18, scale = 4, nullable = false)
    private BigDecimal quantity;

    @Schema(title = "计量单位", description = "如：吨、件、立方米等")
    @Column(name = "unit", length = 20)
    private String unit;

    @Schema(title = "时间粒度")
    @Enumerated(EnumType.STRING)
    @Column(name = "granularity", length = 20, nullable = false)
    private TimeGranularity granularity = TimeGranularity.DAY;

    @Schema(title = "数据类型", description = "1: 产量, 2: 仪表, 3: 指标")
    @Column(name = "data_type", length = 10, nullable = false)
    private String dataType = "1";

    @Schema(title = "产品类型", description = "关联字典或名称")
    @Column(name = "product_type", length = 50)
    private String productType;

    @Schema(title = "备注")
    @Column(name = "remark", length = 500)
    private String remark;
}
