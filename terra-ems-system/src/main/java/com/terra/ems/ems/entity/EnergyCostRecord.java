/*
 * Copyright (c) 2024-2026 Terra Technology (Guangzhou) Co., Ltd.
 * Copyright (c) 2024-2026 泰若科技（广州）有限公司.
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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.terra.ems.ems.enums.RecordPeriodType;
import com.terra.ems.framework.jpa.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 能源成本记录
 *
 * @author dengxueping
 * @since 2026-01-11
 */

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "ems_energy_cost_record", indexes = {
        @Index(name = "idx_ecr_energy_unit", columnList = "energy_unit_id"),
        @Index(name = "idx_ecr_record_date", columnList = "record_date"),
        @Index(name = "idx_ecr_period_type", columnList = "period_type")
})
@Schema(description = "能源成本记录")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class EnergyCostRecord extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "主键ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "energy_unit_id")
    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
    @Schema(description = "用能单元")
    private EnergyUnit energyUnit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "energy_type_id")
    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
    @Schema(description = "能源类型")
    private EnergyType energyType;

    @Column(name = "record_date", nullable = false)
    @Schema(description = "记录日期")
    private LocalDate recordDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "period_type", length = 10)
    @Schema(description = "周期类型")
    private RecordPeriodType periodType = RecordPeriodType.DAY;

    @Column(name = "consumption", precision = 14, scale = 4)
    @Schema(description = "用量")
    private BigDecimal consumption;

    @Column(name = "cost", precision = 14, scale = 2)
    @Schema(description = "成本金额")
    private BigDecimal cost;

    @Column(name = "sharp_consumption", precision = 14, scale = 4)
    @Schema(description = "尖时段用量")
    private BigDecimal sharpConsumption;

    @Column(name = "peak_consumption", precision = 14, scale = 4)
    @Schema(description = "峰时段用量")
    private BigDecimal peakConsumption;

    @Column(name = "flat_consumption", precision = 14, scale = 4)
    @Schema(description = "平时段用量")
    private BigDecimal flatConsumption;

    @Column(name = "valley_consumption", precision = 14, scale = 4)
    @Schema(description = "谷时段用量")
    private BigDecimal valleyConsumption;

    @Column(name = "power_factor", precision = 5, scale = 2)
    @Schema(description = "功率因数")
    private BigDecimal powerFactor;

    @Column(name = "remark", length = 500)
    @Schema(description = "备注")
    private String remark;
}
