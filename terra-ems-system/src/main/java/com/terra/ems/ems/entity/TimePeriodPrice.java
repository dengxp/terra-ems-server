/*
 * Copyright (c) 2024 泰若科技（广州）有限公司. All rights reserved.
 * Copyright (c) 2024-2026 Terra EMS
 */
package com.terra.ems.ems.entity;

import com.terra.ems.ems.enums.TimePeriodType;
import com.terra.ems.framework.enums.DataItemStatus;
import com.terra.ems.framework.jpa.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalTime;

/**
 * Name: TimePeriodPrice.java
 * Email: dengxueping@gmail.com
 * Date: 2026-01-10
 * Description:
 * 分时电价配置实体
 *
 * @author dengxueping
 */
@Data
@Entity
@Table(name = "ems_time_period_price")
@EqualsAndHashCode(callSuper = true)
public class TimePeriodPrice extends BaseEntity {

    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 关联电价政策ID（可选）
     */
    @Column(name = "price_policy_id")
    private Long pricePolicyId;

    /**
     * 时段类型
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "period_type", nullable = false, length = 20)
    private TimePeriodType periodType;

    /**
     * 时段名称
     */
    @Column(name = "period_name", nullable = false, length = 50)
    private String periodName;

    /**
     * 开始时间
     */
    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;

    /**
     * 结束时间
     */
    @Column(name = "end_time", nullable = false)
    private LocalTime endTime;

    /**
     * 电价 (元/kWh)
     */
    @Column(name = "price", nullable = false, precision = 10, scale = 4)
    private BigDecimal price;

    /**
     * 排序
     */
    @Column(name = "sort_order")
    private Integer sortOrder = 0;

    /**
     * 状态
     */
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "status")
    private DataItemStatus status = DataItemStatus.ENABLE;

    /**
     * 备注
     */
    @Column(name = "remark", length = 500)
    private String remark;

    /**
     * 关联的电价政策
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "price_policy_id", insertable = false, updatable = false)
    private PricePolicy pricePolicy;
}
