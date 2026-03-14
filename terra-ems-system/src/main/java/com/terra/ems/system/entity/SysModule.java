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
import com.terra.ems.framework.jpa.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * 系统模块实体
 * 用于对权限进行分类管理
 *
 * @author antigravity
 * @since 2026-02-19
 */

@Data
@EqualsAndHashCode(callSuper = true, exclude = { "permissions" })
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "系统模块")
@Entity
@Table(name = "sys_module", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "code" })
})
public class SysModule extends BaseEntity {

    @Schema(title = "模块ID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(title = "模块名称")
    @Column(name = "name", length = 128, nullable = false)
    private String name;

    @Schema(title = "模块代码")
    @Column(name = "code", length = 64, unique = true, nullable = false)
    private String code;

    @Schema(title = "显示顺序")
    @Column(name = "sort_order")
    private Integer sortOrder = 0;

    @Schema(title = "权限列表")
    @OneToMany(mappedBy = "module", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties({ "module" })
    private List<SysPermission> permissions = new ArrayList<>();

    public SysModule(String name, String code) {
        this.name = name;
        this.code = code;
    }
}
