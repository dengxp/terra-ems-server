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
import com.terra.ems.ems.enums.BenchmarkType;
import com.terra.ems.framework.enums.DataItemStatus;
import com.terra.ems.framework.jpa.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * 对标值管理
 *
 * @author dengxueping
 * @since 2026-01-11
 */

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "ems_benchmark", indexes = {
        @Index(name = "idx_benchmark_code", columnList = "code"),
        @Index(name = "idx_benchmark_type", columnList = "type")
})
@Schema(description = "对标值管理")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Benchmark extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "主键ID")
    private Long id;

    @Column(name = "code", nullable = false, length = 50, unique = true)
    @Schema(description = "标杆编码")
    private String code;

    @Column(name = "name", nullable = false, length = 100)
    @Schema(description = "标杆名称")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", length = 20)
    @Schema(description = "标杆类型")
    private BenchmarkType type = BenchmarkType.NATIONAL;

    @Column(name = "grade", length = 20)
    @Schema(description = "标杆等级")
    private String grade;

    @Column(name = "value", precision = 12, scale = 4)
    @Schema(description = "标杆值")
    private BigDecimal value;

    @Column(name = "unit", length = 20)
    @Schema(description = "单位")
    private String unit;

    @Column(name = "national_num", length = 50)
    @Schema(description = "国标编号")
    private String nationalNum;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "energy_type_id")
    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
    @Schema(description = "关联能源类型")
    private EnergyType energyType;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "status")
    @Schema(description = "状态")
    private DataItemStatus status = DataItemStatus.ENABLE;

    @Column(name = "remark", length = 500)
    @Schema(description = "备注")
    private String remark;
}
