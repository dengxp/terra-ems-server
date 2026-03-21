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

import java.math.BigDecimal;

/**
 * 用能设备实体
 *
 * 工厂中实际消耗能源的设备（如空调主机、注塑机、空压机等）。
 * 一台用能设备可以关联多个计量器具（如电表、水表、气表），
 * 以衡量该设备在不同能源品种上的消耗。
 *
 * @author dengxueping
 * @since 2026-03-21
 */

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "ems_equipment", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "code" })
}, indexes = {
        @Index(name = "idx_equipment_code", columnList = "code"),
        @Index(name = "idx_equipment_energy_unit", columnList = "energy_unit_id")
})
@Schema(title = "用能设备")
public class Equipment extends BaseEntity {

    @Schema(title = "ID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(title = "设备编码", description = "唯一标识")
    @Column(name = "code", length = 50, unique = true, nullable = false)
    private String code;

    @Schema(title = "设备名称")
    @Column(name = "name", length = 150, nullable = false)
    private String name;

    @Schema(title = "设备类型", description = "字典：ems_equipment_type")
    @Column(name = "type", length = 50)
    private String type;

    @Schema(title = "规格型号")
    @Column(name = "model_number", length = 150)
    private String modelNumber;

    @Schema(title = "生产厂商")
    @Column(name = "manufacturer", length = 255)
    private String manufacturer;

    @Schema(title = "额定功率(kW)")
    @Column(name = "rated_power", precision = 12, scale = 2)
    private BigDecimal ratedPower;

    @Schema(title = "安装位置")
    @Column(name = "location", length = 300)
    private String location;

    @Schema(title = "所属用能单元", description = "设备位于哪个用能单元")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "energy_unit_id")
    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler", "children", "parent" })
    private EnergyUnit energyUnit;

    @Schema(title = "排序")
    @Column(name = "sort_order")
    private Integer sortOrder = 0;

    @Schema(title = "状态")
    @Column(name = "status", nullable = false)
    private DataItemStatus status = DataItemStatus.ENABLE;

    @Schema(title = "备注")
    @Column(name = "remark", length = 500)
    private String remark;

    // ============================================================================
    // @JsonProperty 桥接模式
    // ============================================================================

    @JsonProperty("energyUnitId")
    public Long getEnergyUnitId() {
        return energyUnit != null ? energyUnit.getId() : null;
    }

    @JsonProperty("energyUnitId")
    public void setEnergyUnitId(Long energyUnitId) {
        if (energyUnitId != null) {
            this.energyUnit = new EnergyUnit();
            this.energyUnit.setId(energyUnitId);
        } else {
            this.energyUnit = null;
        }
    }
}
