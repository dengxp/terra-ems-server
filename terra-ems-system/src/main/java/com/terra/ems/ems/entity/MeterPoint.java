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
import com.terra.ems.framework.enums.DataItemStatus;
import com.terra.ems.framework.jpa.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * 采集点位实体
 * 
 * 定义计量器具的具体采集指标（如有功电量、无功电量、瞬时功率等）
 *
 * @author dengxueping
 * @since 2026-01-11
 */

@Data
@EqualsAndHashCode(callSuper = true, exclude = { "meter", "energyType", "energyUnits" })
@Entity
@Table(name = "ems_meter_point", uniqueConstraints = {
                @UniqueConstraint(columnNames = { "code" })
}, indexes = {
                @Index(name = "idx_meter_point_code", columnList = "code"),
                @Index(name = "idx_meter_point_meter", columnList = "meter_id"),
                @Index(name = "idx_meter_point_energy_type", columnList = "energy_type_id")
})
@Schema(title = "采集点位")
public class MeterPoint extends BaseEntity {

        @Schema(title = "ID")
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Schema(title = "点位编码", description = "唯一标识")
        @Column(name = "code", length = 50, unique = true, nullable = false)
        private String code;

        @Schema(title = "点位名称")
        @Column(name = "name", length = 100, nullable = false)
        private String name;

        @Schema(title = "点位类型", description = "COLLECT=采集类, CALC=计算类")
        @Column(name = "point_type", length = 20, nullable = false)
        private String pointType;

        @Schema(title = "分类", description = "ENERGY=能源类, PRODUCT=产品类, EFFICIENCY=能效类, OPERATION=经营类, OTHER=其他")
        @Column(name = "category", length = 20)
        private String category;

        @Schema(title = "关联计量器具")
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "meter_id")
        @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
        private Meter meter;

        @Schema(title = "关联能源类型")
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "energy_type_id")
        @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
        private EnergyType energyType;

        @Schema(title = "计量单位")
        @Column(name = "unit", length = 20)
        private String unit;

        @Schema(title = "初始表底值")
        @Column(name = "initial_value", precision = 15, scale = 4)
        private BigDecimal initialValue;

        @Schema(title = "最小值限制")
        @Column(name = "min_value", precision = 15, scale = 4)
        private BigDecimal minValue;

        @Schema(title = "最大值限制")
        @Column(name = "max_value", precision = 15, scale = 4)
        private BigDecimal maxValue;

        @Schema(title = "步长最小值")
        @Column(name = "step_min", precision = 15, scale = 4)
        private BigDecimal stepMin;

        @Schema(title = "步长最大值")
        @Column(name = "step_max", precision = 15, scale = 4)
        private BigDecimal stepMax;

        @Schema(title = "排序")
        @Column(name = "sort_order")
        private Integer sortOrder = 0;

        @Schema(title = "状态")
        @Column(name = "status", nullable = false)
        private DataItemStatus status = DataItemStatus.ENABLE;

        @Schema(title = "备注")
        @Column(name = "remark", length = 500)
        private String remark;

        @Schema(title = "关联用能单元", description = "多对多关系")
        @ManyToMany
        @JoinTable(name = "ems_energy_unit_point", joinColumns = @JoinColumn(name = "meter_point_id"), inverseJoinColumns = @JoinColumn(name = "energy_unit_id"))
        @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler", "children", "parent", "meterPoints" })
        private Set<EnergyUnit> energyUnits = new HashSet<>();

        // ============================================================================
        // @JsonProperty 桥接模式 - 用于前端扁平数据与后端实体对象的无缝转换
        // ============================================================================

        /**
         * 获取计量器具ID（用于前端展示和反序列化桥接）
         */
        @com.fasterxml.jackson.annotation.JsonProperty("meterId")
        public Long getMeterId() {
                return meter != null ? meter.getId() : null;
        }

        /**
         * 设置计量器具ID（用于接收前端扁平数据并自动转为对象存根）
         */
        @com.fasterxml.jackson.annotation.JsonProperty("meterId")
        public void setMeterId(Long meterId) {
                if (meterId != null) {
                        this.meter = new Meter();
                        this.meter.setId(meterId);
                } else {
                        this.meter = null;
                }
        }

        /**
         * 获取能源类型ID（用于前端展示和反序列化桥接）
         */
        @com.fasterxml.jackson.annotation.JsonProperty("energyTypeId")
        public Long getEnergyTypeId() {
                return energyType != null ? energyType.getId() : null;
        }

        /**
         * 设置能源类型ID（用于接收前端扁平数据并自动转为对象存根）
         */
        @com.fasterxml.jackson.annotation.JsonProperty("energyTypeId")
        public void setEnergyTypeId(Long energyTypeId) {
                if (energyTypeId != null) {
                        this.energyType = new EnergyType();
                        this.energyType.setId(energyTypeId);
                } else {
                        this.energyType = null;
                }
        }
}
