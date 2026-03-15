/*
 * Copyright (c) 2025-2026 Terra Technology (Guangzhou) Co., Ltd.
 * Copyright (c) 2025-2026 泰若科技（广州）有限公司.
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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.terra.ems.framework.enums.DataScope;
import com.terra.ems.framework.enums.DataItemStatus;
import com.terra.ems.framework.jpa.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

/**
 * 系统角色实体
 *
 * @author dengxueping
 * @since 2026-01-11
 */

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "角色表")
@Entity
@Table(name = "sys_role", uniqueConstraints = {
                @UniqueConstraint(columnNames = { "code" })
}, indexes = {
                @Index(name = "idx_role_id", columnList = "id"),
                @Index(name = "idx_role_code", columnList = "code")
})
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class SysRole extends BaseEntity {

        @Schema(title = "角色ID")
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Schema(title = "角色名称")
        @Column(name = "name", length = 128, nullable = false)
        private String name;

        @Schema(title = "角色代码")
        @Column(name = "code", length = 64, unique = true, nullable = false)
        private String code;

        @Schema(title = "权限数量")
        @Column(name = "permission_count", nullable = false)
        private Integer permissionCount = 0;

        @Schema(title = "成员数量")
        @Column(name = "member_count", nullable = false)
        private Integer memberCount = 0;

        @Schema(title = "备注")
        @Column(name = "remark", length = 500)
        private String remark;

        @Schema(title = "数据权限范围")
        @Enumerated(EnumType.STRING)
        @Column(name = "data_scope", length = 20)
        private DataScope dataScope = DataScope.SELF;

        @Schema(title = "自定义数据权限部门ID")
        @ElementCollection
        @CollectionTable(name = "sys_role_dept", joinColumns = @JoinColumn(name = "role_id"))
        @Column(name = "dept_id")
        private Set<Long> dataScopeDeptIds = new HashSet<>();

        @Schema(title = "角色权限列表")
        @JsonIgnore
        @ToString.Exclude
        @EqualsAndHashCode.Exclude
        @ManyToMany(fetch = FetchType.LAZY)
        @JoinTable(name = "sys_role_permission", joinColumns = {
                        @JoinColumn(name = "role_id", referencedColumnName = "id") }, inverseJoinColumns = {
                                        @JoinColumn(name = "permission_id", referencedColumnName = "id") }, uniqueConstraints = @UniqueConstraint(columnNames = {
                                                        "role_id", "permission_id" }), indexes = {
                                                                        @Index(name = "idx_role_perm_role_id", columnList = "role_id"),
                                                                        @Index(name = "idx_role_perm_permission_id", columnList = "permission_id")
                                                        })
        private Set<SysPermission> permissions = new HashSet<>();

        @Schema(title = "角色成员列表")
        @JsonIgnore
        @ToString.Exclude
        @EqualsAndHashCode.Exclude
        @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
        private Set<SysUser> users = new HashSet<>();

        @Schema(title = "角色状态")
        @Column(name = "status", nullable = false)
        private DataItemStatus status = DataItemStatus.ENABLE;

        @Transient
        private Set<Long> permissionIds;

        public Set<Long> getPermissionIds() {
                if (permissionIds == null && permissions != null) {
                        return permissions.stream().map(SysPermission::getId)
                                        .collect(java.util.stream.Collectors.toSet());
                }
                return permissionIds;
        }

        public void setPermissionIds(Set<Long> permissionIds) {
                this.permissionIds = permissionIds;
                if (permissionIds != null) {
                        this.permissions = permissionIds.stream().map(SysPermission::new)
                                        .collect(java.util.stream.Collectors.toSet());
                }
        }
}
