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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.terra.ems.ems.enums.PeriodType;
import com.terra.ems.framework.jpa.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * 电价策略明细（时段价格）
 *
 * @author dengxueping
 * @since 2026-01-11
 */

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "ems_price_policy_item", indexes = {
        @Index(name = "idx_price_policy_item_policy", columnList = "policy_id")
})
@Schema(description = "电价策略时段明细")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class PricePolicyItem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "主键ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "policy_id", nullable = false)
    @JsonIgnore
    @Schema(description = "关联策略")
    private PricePolicy pricePolicy;

    @Enumerated(EnumType.STRING)
    @Column(name = "period_type", length = 20, nullable = false)
    @Schema(description = "时段类型：SHARP(尖)/PEAK(峰)/FLAT(平)/VALLEY(谷)/DEEP(深谷)")
    private PeriodType periodType;

    @Column(name = "price", precision = 10, scale = 4, nullable = false)
    @Schema(description = "单价（元/单位）")
    private BigDecimal price;

    @Column(name = "sort_order")
    @Schema(description = "排序号")
    private Integer sortOrder = 0;

    @Column(name = "start_time", length = 10)
    @Schema(description = "开始时间（HH:mm格式，如 08:00）")
    private String startTime;

    @Column(name = "end_time", length = 10)
    @Schema(description = "结束时间（HH:mm格式，如 12:00）")
    private String endTime;

    @Column(name = "remark", length = 200)
    @Schema(description = "备注")
    private String remark;
}
