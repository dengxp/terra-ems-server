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
import com.terra.ems.framework.enums.DataItemStatus;
import com.terra.ems.framework.jpa.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

/**
 * 系统菜单/权限实体
 *
 * @author dengxueping
 * @since 2026-01-11
 */

@Data
@EqualsAndHashCode(callSuper = true, exclude = { "parent", "children" })
@Entity
@Table(name = "sys_menu", indexes = {
        @Index(name = "idx_sys_menu_parent", columnList = "parent_id")
})
@Schema(title = "系统菜单/权限")
public class SysMenu extends BaseEntity {

    @Schema(title = "菜单ID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(title = "菜单名称")
    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @Schema(title = "父菜单ID")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler", "children", "parent" })
    private SysMenu parent;

    @Schema(title = "显示顺序")
    @Column(name = "sort_order")
    private Integer sortOrder = 0;

    @Schema(title = "路由地址")
    @Column(name = "path", length = 200)
    private String path;

    @Schema(title = "组件路径")
    @Column(name = "component", length = 255)
    private String component;

    @Schema(title = "权限标识")
    @Column(name = "permission_code", length = 100)
    private String permissionCode;

    @Schema(title = "菜单类型 (M目录 C菜单 F按钮)")
    @Column(name = "menu_type", length = 1, nullable = false)
    private String menuType;

    @Schema(title = "菜单图标")
    @Column(name = "icon", length = 100)
    private String icon;

    @Schema(title = "状态")
    @Column(name = "status", nullable = false)
    private DataItemStatus status = DataItemStatus.ENABLE;

    @Schema(title = "显示状态 (0显示 1隐藏)")
    @Column(name = "visible", length = 1)
    private String visible = "0";

    @Schema(title = "子菜单")
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("sortOrder ASC")
    @JsonIgnoreProperties({ "parent" })
    private List<SysMenu> children = new ArrayList<>();

    /**
     * 获取父菜单ID
     *
     * @return 父菜单ID
     */
    @Transient
    public Long getParentId() {
        return parent != null ? parent.getId() : null;
    }
}
