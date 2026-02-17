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

package com.terra.ems.system.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.terra.ems.common.annotation.Excel;
import com.terra.ems.framework.enums.DataItemStatus;
import com.terra.ems.framework.jpa.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统岗位实体
 *
 * @author dengxueping
 * @since 2026-01-11
 */

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "sys_post", uniqueConstraints = {
                @UniqueConstraint(columnNames = { "code" })
}, indexes = {
                @Index(name = "idx_sys_post_code", columnList = "code")
})
@Schema(title = "系统岗位")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class SysPost extends BaseEntity {

        @Schema(title = "岗位ID")
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Schema(title = "岗位编码")
        @Excel(name = "岗位编码")
        @Column(name = "code", length = 64, unique = true, nullable = false)
        private String code;

        @Schema(title = "岗位名称")
        @Excel(name = "岗位名称")
        @Column(name = "name", length = 64, nullable = false)
        private String name;

        @Schema(title = "显示顺序")
        @Excel(name = "岗位排序")
        @Column(name = "sort_order")
        private Integer sortOrder = 0;

        @Schema(title = "状态")
        @Excel(name = "状态", readConverterExp = "ENABLE=正常,DISABLE=停用")
        @Column(name = "status", nullable = false)
        private DataItemStatus status = DataItemStatus.ENABLE;

        @Schema(title = "备注")
        @Excel(name = "备注")
        @Column(name = "remark", length = 500)
        private String remark;
}
