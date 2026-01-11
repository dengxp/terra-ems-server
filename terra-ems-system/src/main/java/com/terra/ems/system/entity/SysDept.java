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
import com.terra.ems.framework.enums.DataItemStatus;
import com.terra.ems.framework.jpa.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

/**
 * Name: SysDept
 * Email: dengxueping@gmail.com
 * Date: 2024-01-09
 * Description: 系统部门实体（组织架构）
 *
 * @author dengxueping
 */
@Data
@EqualsAndHashCode(callSuper = true, exclude = { "parent", "children" })
@Entity
@Table(name = "sys_dept", indexes = {
        @Index(name = "idx_sys_dept_parent", columnList = "parent_id"),
        @Index(name = "idx_sys_dept_status", columnList = "status")
})
@Schema(title = "部门(组织架构)")
public class SysDept extends BaseEntity {

    @Schema(title = "部门ID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(title = "父部门ID")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler", "children", "parent" })
    private SysDept parent;

    @Schema(title = "部门名称")
    @Column(name = "name", length = 64, nullable = false)
    private String name;

    @Schema(title = "部门编码")
    @Column(name = "code", length = 32)
    private String code;

    @Schema(title = "显示顺序")
    @Column(name = "sort_order")
    private Integer sortOrder = 0;

    @Schema(title = "负责人")
    @Column(name = "leader", length = 32)
    private String leader;

    @Schema(title = "联系电话")
    @Column(name = "phone", length = 11)
    private String phone;

    @Schema(title = "邮箱")
    @Column(name = "email", length = 64)
    private String email;

    @Schema(title = "祖级列表", description = "用于快速查询子部门")
    @Column(name = "ancestors", length = 255)
    private String ancestors;

    @Schema(title = "部门状态")
    @Column(name = "status", nullable = false)
    private DataItemStatus status = DataItemStatus.ENABLE;

    @Schema(title = "子部门")
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("sortOrder ASC")
    @JsonIgnoreProperties({ "parent" })
    private List<SysDept> children = new ArrayList<>();

    /**
     * 获取父部门ID
     */
    @Transient
    public Long getParentId() {
        return parent != null ? parent.getId() : null;
    }
}
