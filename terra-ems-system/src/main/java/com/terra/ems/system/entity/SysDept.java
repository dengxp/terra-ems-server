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

package com.terra.ems.system.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.terra.ems.framework.enums.DataItemStatus;
import com.terra.ems.framework.jpa.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.Formula;

import java.util.ArrayList;
import java.util.List;

/**
 * 系统部门实体（组织架构）
 *
 * @author dengxueping
 * @since 2026-01-11
 */

@Data
@EqualsAndHashCode(callSuper = true, exclude = { "parent", "children", "manager" })
@Entity
@Table(name = "sys_dept", indexes = {
        @Index(name = "idx_sys_dept_parent", columnList = "parent_id"),
        @Index(name = "idx_sys_dept_status", columnList = "status")
})
@Schema(title = "部门(组织架构)")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class SysDept extends BaseEntity {

    @Schema(title = "部门ID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(title = "父部门ID")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler", "children", "parent" })
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
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

    @Schema(title = "部门负责人")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id")
    @ToString.Exclude
    @JsonIgnoreProperties({ "password", "dept", "roles", "hibernateLazyInitializer", "handler" })
    private SysUser manager;

    @Schema(title = "部门描述")
    @Column(name = "description", length = 512)
    private String description;

    @Schema(title = "成员数量")
    @Formula("(select count(1) from sys_user u where u.dept_id = id)")
    private Integer memberCount;

    /**
     * 获取父部门ID（用于前端展示和反序列化桥接）
     *
     * @return 父部门ID
     */
    @JsonProperty("parentId")
    public Long getParentId() {
        return parent != null ? parent.getId() : null;
    }

    /**
     * 获取父部门名称
     *
     * @return 父部门名称
     */
    @JsonProperty("parentName")
    public String getParentName() {
        return parent != null ? parent.getName() : null;
    }

    /**
     * 设置父部门ID（用于接收前端扁平数据并自动转为对象存根）
     *
     * @param parentId 父部门ID
     */
    @JsonProperty("parentId")
    public void setParentId(Long parentId) {
        if (parentId != null) {
            this.parent = new SysDept();
            this.parent.setId(parentId);
        } else {
            this.parent = null;
        }
    }

    /**
     * 获取负责人ID（用于前端展示和反序列化桥接）
     *
     * @return 负责人ID
     */
    @JsonProperty("managerId")
    public Long getManagerId() {
        return manager != null ? manager.getId() : null;
    }

    /**
     * 获取负责人姓名
     *
     * @return 负责人姓名
     */
    @JsonProperty("managerName")
    public String getManagerName() {
        return manager != null ? manager.getRealName() : null;
    }

    /**
     * 设置负责人ID（用于接收前端扁平数据并自动转为对象存根）
     *
     * @param managerId 负责人ID
     */
    @JsonProperty("managerId")
    public void setManagerId(Long managerId) {
        if (managerId != null) {
            this.manager = new SysUser();
            this.manager.setId(managerId);
        } else {
            this.manager = null;
        }
    }
}
