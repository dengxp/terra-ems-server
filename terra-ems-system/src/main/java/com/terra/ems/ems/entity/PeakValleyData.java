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

import com.terra.ems.ems.enums.TimePeriodType;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 分时用电数据
 *
 * @author dengxueping
 * @since 2026-01-11
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
