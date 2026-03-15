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
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 能耗数据
 *
 * @author dengxueping
 * @since 2026-01-11
 */

@Data
@Entity
@Table(name = "ems_energy_data", indexes = {
        @Index(name = "idx_energy_data_point", columnList = "meter_point_id"),
        @Index(name = "idx_energy_data_type", columnList = "energy_type_id"),
        @Index(name = "idx_energy_data_time", columnList = "data_time, time_type"),
        @Index(name = "idx_energy_data_composite", columnList = "meter_point_id, data_time, time_type")
})
@Schema(description = "能耗数据")
public class EnergyData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "主键ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "meter_point_id", nullable = false)
    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
    @Schema(description = "采集点位")
    private MeterPoint meterPoint;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "energy_type_id", nullable = false)
    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
    @Schema(description = "能源类型")
    private EnergyType energyType;

    @Column(name = "data_time", nullable = false)
    @Schema(description = "数据时间")
    private LocalDateTime dataTime;

    @Column(name = "time_type", length = 10, nullable = false)
    @Schema(description = "时间类型：HOUR/DAY/MONTH/YEAR")
    private String timeType;

    @Column(name = "value", precision = 18, scale = 4)
    @Schema(description = "能耗值")
    private BigDecimal value;

    @Column(name = "created_at")
    @Schema(description = "创建时间")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
