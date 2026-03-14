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
import com.fasterxml.jackson.annotation.JsonProperty;
import com.terra.ems.framework.jpa.entity.BaseEntity;
import com.terra.ems.framework.enums.DataItemStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

/**
 * 电价策略
 *
 * @author dengxueping
 * @since 2026-01-11
 */

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "ems_price_policy", indexes = {
        @Index(name = "idx_price_policy_code", columnList = "code"),
        @Index(name = "idx_price_policy_energy_type", columnList = "energy_type_id")
})
@Schema(description = "电价策略")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class PricePolicy extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "主键ID")
    private Long id;

    @Column(name = "code", length = 50, nullable = false, unique = true)
    @Schema(description = "策略编码")
    private String code;

    @Column(name = "name", length = 100, nullable = false)
    @Schema(description = "策略名称")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "energy_type_id")
    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
    @Schema(description = "关联能源类型")
    private EnergyType energyType;

    /**
     * 设置能源类型ID（用于 JSON 反序列化）
     */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public void setEnergyTypeId(Long energyTypeId) {
        if (energyTypeId != null) {
            this.energyType = new EnergyType();
            this.energyType.setId(energyTypeId);
        }
    }

    @Column(name = "is_multi_rate")
    @Schema(description = "是否复费率（分时电价）")
    private Boolean isMultiRate = true;

    @OneToMany(mappedBy = "pricePolicy", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @OrderBy("sortOrder ASC")
    @Schema(description = "时段价格明细")
    private List<PricePolicyItem> items = new ArrayList<>();

    @Column(name = "sort_order")
    @Schema(description = "排序号")
    private Integer sortOrder = 0;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "status")
    @Schema(description = "状态")
    private DataItemStatus status = DataItemStatus.ENABLE;

    @Column(name = "effective_start_date")
    @Schema(description = "生效开始日期")
    private java.time.LocalDate effectiveStartDate;

    @Column(name = "effective_end_date")
    @Schema(description = "生效结束日期")
    private java.time.LocalDate effectiveEndDate;

    @Column(name = "remark", length = 500)
    @Schema(description = "备注")
    private String remark;

    /**
     * 添加时段价格明细
     */
    public void addItem(PricePolicyItem item) {
        items.add(item);
        item.setPricePolicy(this);
    }

    /**
     * 移除时段价格明细
     */
    public void removeItem(PricePolicyItem item) {
        items.remove(item);
        item.setPricePolicy(null);
    }

    /**
     * 清空并重新设置明细
     */
    public void setItems(List<PricePolicyItem> newItems) {
        this.items.clear();
        if (newItems != null) {
            for (PricePolicyItem item : newItems) {
                addItem(item);
            }
        }
    }
}
