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

import com.terra.ems.ems.enums.PolicyType;
import com.terra.ems.framework.enums.DataItemStatus;
import com.terra.ems.framework.jpa.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

/**
 * 政策法规
 *
 * @author dengxueping
 * @since 2026-01-11
 */

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "ems_policy", indexes = {
        @Index(name = "idx_policy_type", columnList = "type"),
        @Index(name = "idx_policy_issuing_date", columnList = "issuing_date")
})
@Schema(description = "政策法规")
public class Policy extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "主键ID")
    private Long id;

    @Column(name = "title", nullable = false, length = 200)
    @Schema(description = "政策标题")
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", length = 20)
    @Schema(description = "政策类型")
    private PolicyType type = PolicyType.NATIONAL;

    @Column(name = "department", length = 100)
    @Schema(description = "印发部门")
    private String department;

    @Column(name = "issuing_date")
    @Schema(description = "印发日期")
    private LocalDate issuingDate;

    @Column(name = "file_url", length = 500)
    @Schema(description = "文件地址")
    private String fileUrl;

    @Column(name = "summary", columnDefinition = "TEXT")
    @Schema(description = "政策摘要")
    private String summary;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "status")
    @Schema(description = "状态")
    private DataItemStatus status = DataItemStatus.ENABLE;

    @Column(name = "remark", length = 500)
    @Schema(description = "备注")
    private String remark;
}
