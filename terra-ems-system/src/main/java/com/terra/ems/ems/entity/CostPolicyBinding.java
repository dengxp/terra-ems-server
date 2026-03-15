/*
 * Copyright (c) 2025-2026 Terra Technology (Guangzhou) Co., Ltd.
 * Copyright (c) 2025-2026 泰若科技（广州）有限公司.
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
import com.fasterxml.jackson.annotation.JsonProperty;
import com.terra.ems.framework.enums.DataItemStatus;
import com.terra.ems.framework.jpa.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

/**
 * 成本策略绑定
 * 将电价策略绑定到用能单元
 *
 * @author dengxueping
 * @since 2026-01-11
 */

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "ems_cost_policy_binding", indexes = {
        @Index(name = "idx_cpb_energy_unit", columnList = "energy_unit_id"),
        @Index(name = "idx_cpb_price_policy", columnList = "price_policy_id")
})
@Schema(description = "成本策略绑定")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class CostPolicyBinding extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "主键ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "energy_unit_id", nullable = false)
    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
    @Schema(description = "用能单元")
    private EnergyUnit energyUnit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "price_policy_id", nullable = false)
    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler", "items" })
    @Schema(description = "电价策略")
    private PricePolicy pricePolicy;

    @Column(name = "effective_start_date", nullable = false)
    @Schema(description = "生效开始日期")
    private LocalDate effectiveStartDate;

    @Column(name = "effective_end_date")
    @Schema(description = "生效结束日期（为空表示长期有效）")
    private LocalDate effectiveEndDate;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "status")
    @Schema(description = "状态")
    private DataItemStatus status = DataItemStatus.ENABLE;

    @Column(name = "remark", length = 500)
    @Schema(description = "备注")
    private String remark;

    // ========== @JsonProperty 桥接方法 ==========

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public void setEnergyUnitId(Long energyUnitId) {
        if (energyUnitId != null) {
            this.energyUnit = new EnergyUnit();
            this.energyUnit.setId(energyUnitId);
        }
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public void setPricePolicyId(Long pricePolicyId) {
        if (pricePolicyId != null) {
            this.pricePolicy = new PricePolicy();
            this.pricePolicy.setId(pricePolicyId);
        }
    }
}
