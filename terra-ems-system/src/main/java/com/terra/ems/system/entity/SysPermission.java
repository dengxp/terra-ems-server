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

/**
 * 系统权限实体
 *
 * @author dengxueping
 * @since 2026-01-11
 */

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "权限表")
@Entity
@Table(name = "sys_permission", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "code" })
}, indexes = {
        @Index(name = "idx_permission_id", columnList = "id"),
        @Index(name = "idx_permission_code", columnList = "code")
})
public class SysPermission extends BaseEntity {

    @Schema(title = "权限ID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(title = "权限名称")
    @Column(name = "name", length = 128, nullable = false)
    private String name;

    @Schema(title = "权限代码")
    @Column(name = "code", length = 64, unique = true, nullable = false)
    private String code;

    @Schema(title = "权限描述")
    @Column(name = "description", length = 500)
    private String description;

    @Schema(title = "是否为超级权限")
    @Column(name = "super_permission")
    private Boolean superPermission = false;

    @Schema(title = "所属模块")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "module_id")
    @JsonIgnoreProperties({ "permissions", "hibernateLazyInitializer", "handler" })
    private SysModule module;

    /**
     * 辅助构造函数
     *
     * @param id 权限ID
     */
    public SysPermission(Long id) {
        this.id = id;
    }
}
