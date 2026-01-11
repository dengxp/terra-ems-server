/*
 * Copyright (c) 2024 泰若科技（广州）有限公司. All rights reserved.
 * Copyright (c) 2024-2026 Terra EMS
 */
package com.terra.ems.ems.entity;

import com.terra.ems.ems.enums.TimePeriodType;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Name: PeakValleyData.java
 * Email: dengxueping@gmail.com
 * Date: 2026-01-10
 * Description:
 * 分时用电数据实体
 *
 * @author dengxueping
 */
@Data
@Entity
@Table(name = "ems_peak_valley_data", indexes = {
        @Index(name = "idx_pvd_meter_point", columnList = "meter_point_id"),
        @Index(name = "idx_pvd_energy_unit", columnList = "energy_unit_id"),
        @Index(name = "idx_pvd_data_time", columnList = "data_time, time_type")
})
public class PeakValleyData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 采集点位ID
     */
    @Column(name = "meter_point_id", nullable = false)
    private Long meterPointId;

    /**
     * 用能单元ID
     */
    @Column(name = "energy_unit_id")
    private Long energyUnitId;

    /**
     * 数据日期
     */
    @Column(name = "data_time", nullable = false)
    private LocalDate dataTime;

    /**
     * 时间类型 HOUR/DAY/MONTH
     */
    @Column(name = "time_type", nullable = false, length = 10)
    private String timeType;

    /**
     * 时段类型
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "period_type", nullable = false, length = 20)
    private TimePeriodType periodType;

    /**
     * 用电量 kWh
     */
    @Column(name = "electricity", precision = 18, scale = 4)
    private BigDecimal electricity;

    /**
     * 单价
     */
    @Column(name = "price", precision = 10, scale = 4)
    private BigDecimal price;

    /**
     * 电费
     */
    @Column(name = "cost", precision = 18, scale = 4)
    private BigDecimal cost;

    /**
     * 创建时间
     */
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    /**
     * 关联的采集点位
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "meter_point_id", insertable = false, updatable = false)
    private MeterPoint meterPoint;

    /**
     * 关联的用能单元
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "energy_unit_id", insertable = false, updatable = false)
    private EnergyUnit energyUnit;

    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }
}
