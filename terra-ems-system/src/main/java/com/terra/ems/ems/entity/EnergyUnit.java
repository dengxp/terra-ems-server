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
import com.terra.ems.ems.enums.EnergyUnitType;
import com.terra.ems.framework.enums.DataItemStatus;
import com.terra.ems.framework.jpa.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 用能单元实体
 * 
 * 用于构建企业能源消耗的层级结构（总厂 → 车间 → 产线 → 设备）
 * 支持电力支路、工序等多种类型
 *
 * @author dengxueping
 * @since 2026-01-11
 */

@Data
@EqualsAndHashCode(callSuper = true, exclude = { "parent", "children" })
@Entity
@Table(name = "ems_energy_unit", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "code" })
}, indexes = {
        @Index(name = "idx_energy_unit_code", columnList = "code"),
        @Index(name = "idx_energy_unit_parent", columnList = "parent_id"),
        @Index(name = "idx_energy_unit_type", columnList = "unit_type")
})
@Schema(title = "用能单元")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class EnergyUnit extends BaseEntity {

    @Schema(title = "ID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(title = "编码", description = "唯一标识")
    @Column(name = "code", length = 50, unique = true, nullable = false)
    private String code;

    @Schema(title = "名称")
    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Schema(title = "单元类型", description = "GENERAL=普通, BRANCH=支路, PROCESS=工序, EQUIPMENT=设备")
    @Enumerated(EnumType.STRING)
    @Column(name = "unit_type", length = 20)
    private EnergyUnitType unitType = EnergyUnitType.GENERAL;

    @Schema(title = "父节点")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler", "children", "parent" })
    private EnergyUnit parent;

    @Schema(title = "子节点")
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("sortOrder ASC")
    @JsonIgnoreProperties({ "parent" })
    private List<EnergyUnit> children = new ArrayList<>();

    @Schema(title = "层级", description = "0=根节点")
    @Column(name = "level", nullable = false)
    private Integer level = 0;

    @Schema(title = "排序")
    @Column(name = "sort_order")
    private Integer sortOrder = 0;

    @Schema(title = "状态")
    @Column(name = "status", nullable = false)
    private DataItemStatus status = DataItemStatus.ENABLE;

    @Schema(title = "备注")
    @Column(name = "remark", length = 500)
    private String remark;

    // ==================== 电气参数（支路类型专用） ====================

    @Schema(title = "电压等级", description = "如：380V, 10kV, 35kV")
    @Column(name = "voltage_level", length = 20)
    private String voltageLevel;

    @Schema(title = "额定电流", description = "单位：A")
    @Column(name = "rated_current", precision = 10, scale = 2)
    private BigDecimal ratedCurrent;

    @Schema(title = "额定功率", description = "单位：kW")
    @Column(name = "rated_power", precision = 12, scale = 2)
    private BigDecimal ratedPower;

    /**
     * 获取父节点ID（用于前端展示和反序列化桥接）
     */
    @JsonProperty("parentId")
    public Long getParentId() {
        return parent != null ? parent.getId() : null;
    }

    /**
     * 设置父节点ID（用于接收前端扁平数据并自动转为对象存根）
     */
    @JsonProperty("parentId")
    public void setParentId(Long parentId) {
        if (parentId != null) {
            this.parent = new EnergyUnit();
            this.parent.setId(parentId);
        } else {
            this.parent = null;
        }
    }

    /**
     * 判断是否为支路类型
     */
    @Transient
    public boolean isBranch() {
        return EnergyUnitType.BRANCH.equals(this.unitType);
    }
}
