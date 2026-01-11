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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.terra.ems.framework.enums.DataItemStatus;
import com.terra.ems.framework.jpa.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * 能源类型实体
 *
 * @author dengxueping
 * @since 2026-01-11
 */

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "ems_energy_type", uniqueConstraints = {
                @UniqueConstraint(columnNames = { "code" })
}, indexes = {
                @Index(name = "idx_energy_type_code", columnList = "code"),
                @Index(name = "idx_energy_type_category", columnList = "category")
})
@Schema(title = "能源类型")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class EnergyType extends BaseEntity {

        @Schema(title = "ID")
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Schema(title = "编码", description = "唯一标识，如 ELECTRIC, NATURAL_GAS")
        @Column(name = "code", length = 50, unique = true, nullable = false)
        private String code;

        @Schema(title = "名称", description = "如：电力、天然气")
        @Column(name = "name", length = 100, nullable = false)
        private String name;

        @Schema(title = "计量单位", description = "如：kWh, m³, t")
        @Column(name = "unit", length = 20, nullable = false)
        private String unit;

        @Schema(title = "类别", description = "ELECTRIC/GAS/STEAM/WATER/OTHER")
        @Column(name = "category", length = 20)
        private String category;

        @Schema(title = "是否可存储")
        @Column(name = "storable", nullable = false)
        private Boolean storable = false;

        @Schema(title = "标煤折算系数", description = "kgce/单位")
        @Column(name = "coefficient", precision = 10, scale = 4)
        private BigDecimal coefficient;

        @Schema(title = "碳排放因子", description = "kgCO2/单位")
        @Column(name = "emission_factor", precision = 10, scale = 4)
        private BigDecimal emissionFactor;

        @Schema(title = "默认单价")
        @Column(name = "default_price", precision = 10, scale = 4)
        private BigDecimal defaultPrice;

        @Schema(title = "图标", description = "前端展示用图标")
        @Column(name = "icon", length = 50)
        private String icon;

        @Schema(title = "颜色", description = "前端展示用颜色")
        @Column(name = "color", length = 20)
        private String color;

        @Schema(title = "排序")
        @Column(name = "sort_order")
        private Integer sortOrder = 0;

        @Schema(title = "状态")
        @Column(name = "status", nullable = false)
        private DataItemStatus status = DataItemStatus.ENABLE;

        @Schema(title = "备注")
        @Column(name = "remark", length = 500)
        private String remark;
}
