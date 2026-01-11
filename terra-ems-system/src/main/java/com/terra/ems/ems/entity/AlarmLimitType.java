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

import com.terra.ems.framework.jpa.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Name: AlarmLimitType.java
 * Email: dengxueping@gmail.com
 * Date: 2026-01-10
 * Description:
 * 报警限值类型维护对象
 *
 * @author dengxueping
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "ems_alarm_limit_type", indexes = {
        @Index(name = "idx_alarm_limit_type_code", columnList = "limit_code")
})
@Schema(description = "报警限值类型")
public class AlarmLimitType extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "主键id")
    private Long id;

    @Column(name = "limit_name", length = 100, nullable = false)
    @Schema(description = "限值类型名称")
    private String limitName;

    @Column(name = "limit_code", length = 50, nullable = false, unique = true)
    @Schema(description = "限值类型编码")
    private String limitCode;

    @Column(name = "color_number", length = 20)
    @Schema(description = "色号")
    private String colorNumber;

    @Column(name = "comparator_operator", length = 10)
    @Schema(description = "比较运算符")
    private String comparatorOperator;

    @Column(name = "alarm_type", length = 20)
    @Schema(description = "预警类型")
    private String alarmType;
}
