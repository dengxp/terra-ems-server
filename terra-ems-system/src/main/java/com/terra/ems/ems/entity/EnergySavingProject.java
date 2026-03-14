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

import com.terra.ems.ems.enums.ProjectStatus;
import com.terra.ems.framework.jpa.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 节能项目
 *
 * @author dengxueping
 * @since 2026-01-11
 */

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "ems_energy_saving_project", indexes = {
        @Index(name = "idx_saving_project_status", columnList = "status"),
        @Index(name = "idx_saving_project_liable", columnList = "liable_person")
})
@Schema(description = "节能项目")
public class EnergySavingProject extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "主键ID")
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    @Schema(description = "项目名称")
    private String name;

    @Column(name = "plan", columnDefinition = "TEXT")
    @Schema(description = "总体计划")
    private String plan;

    @Column(name = "implementation_plan", columnDefinition = "TEXT")
    @Schema(description = "实施计划")
    private String implementationPlan;

    @Column(name = "current_work", length = 500)
    @Schema(description = "当前工作")
    private String currentWork;

    @Column(name = "liable_person", length = 50)
    @Schema(description = "项目负责人")
    private String liablePerson;

    @Column(name = "saving_amount", precision = 12, scale = 2)
    @Schema(description = "节约量（单位：吨标准煤或元）")
    private BigDecimal savingAmount;

    @Column(name = "completion_time")
    @Schema(description = "计划完成时间")
    private LocalDate completionTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20)
    @Schema(description = "项目状态")
    private ProjectStatus status = ProjectStatus.PLANNING;

    @Column(name = "remark", length = 500)
    @Schema(description = "备注")
    private String remark;
}
