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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.terra.ems.framework.enums.DataItemStatus;
import com.terra.ems.framework.jpa.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Name: SysUser
 * Email: dengxueping@gmail.com
 * Date: 2024-12-14
 * Description: 系统用户实体
 *
 * @author dengxueping
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "sys_user", uniqueConstraints = { @UniqueConstraint(columnNames = { "username" }) }, indexes = {
        @Index(name = "idx_sys_user_username", columnList = "username") })
@Schema(title = "系统用户")
public class SysUser extends BaseEntity {

    @Schema(title = "用户ID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(title = "用户名")
    @Column(name = "username", length = 64, unique = true, nullable = false)
    private String username;

    @Schema(title = "密码")
    @Column(name = "password", length = 128, nullable = false)
    private String password;

    @Schema(title = "昵称")
    @Column(name = "nickname", length = 64)
    private String nickname;

    @Schema(title = "头像")
    @Column(name = "avatar", length = 256)
    private String avatar;

    @Schema(title = "邮箱")
    @Column(name = "email", length = 128)
    private String email;

    @Schema(title = "手机号")
    @Column(name = "phone", length = 20)
    private String phone;

    @Schema(title = "所属部门")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dept_id")
    private SysDept dept;

    @Schema(title = "用户状态")
    @Column(name = "status", nullable = false)
    private DataItemStatus status = DataItemStatus.ENABLE;

    @Schema(title = "账户过期日期")
    @Column(name = "account_expire_at")
    private LocalDateTime accountExpireAt;

    @Schema(title = "密码过期日期")
    @Column(name = "credentials_expire_at")
    private LocalDateTime credentialsExpireAt;

    @Schema(title = "登录失败次数")
    @Column(name = "fail_login_count", nullable = false)
    private Integer failLoginCount = 0;

    @Schema(title = "最后登录时间")
    @Column(name = "last_login_at")
    private LocalDateTime lastLoginAt;

    @JsonIgnore
    @Schema(title = "用户角色列表")
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "sys_user_role", joinColumns = {
            @JoinColumn(name = "user_id", referencedColumnName = "id") }, inverseJoinColumns = {
                    @JoinColumn(name = "role_id", referencedColumnName = "id") }, uniqueConstraints = {
                            @UniqueConstraint(columnNames = { "user_id", "role_id" }) }, indexes = {
                                    @Index(name = "idx_user_role_user_id", columnList = "user_id"),
                                    @Index(name = "idx_user_role_role_id", columnList = "role_id")
                            })
    private Set<SysRole> roles = new HashSet<>();

    @JsonIgnore
    @Schema(title = "用户权限列表")
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "sys_user_permission", joinColumns = {
            @JoinColumn(name = "user_id", referencedColumnName = "id") }, inverseJoinColumns = {
                    @JoinColumn(name = "permission_id", referencedColumnName = "id") }, uniqueConstraints = {
                            @UniqueConstraint(columnNames = { "user_id", "permission_id" }) }, indexes = {
                                    @Index(name = "idx_user_permission_user_id", columnList = "user_id"),
                                    @Index(name = "idx_user_permission_permission_id", columnList = "permission_id")
                            })
    private Set<SysPermission> permissions = new HashSet<>();

    /**
     * 检查日期是否未过期
     *
     * @param dateTime 日期时间
     * @return 是否未过期
     */
    private boolean isNonExpired(LocalDateTime dateTime) {
        if (dateTime == null) {
            return true;
        }
        return dateTime.isAfter(LocalDateTime.now());
    }

    /**
     * 账户是否启用
     *
     * @return 是否启用
     */
    public boolean isEnabled() {
        return status != DataItemStatus.FORBIDDEN;
    }

    /**
     * 账户是否未锁定
     *
     * @return 是否未锁定
     */
    public boolean isAccountNonLocked() {
        return status != DataItemStatus.FORBIDDEN;
    }

    /**
     * 账户是否未过期
     *
     * @return 是否未过期
     */
    public boolean isAccountNonExpired() {
        if (status == DataItemStatus.FORBIDDEN) {
            return false;
        }
        return isNonExpired(accountExpireAt);
    }

    /**
     * 凭证是否未过期
     *
     * @return 是否未过期
     */
    public boolean isCredentialsNonExpired() {
        return isNonExpired(credentialsExpireAt);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public DataItemStatus getStatus() {
        return status;
    }

    public void setStatus(DataItemStatus status) {
        this.status = status;
    }

    public LocalDateTime getAccountExpireAt() {
        return accountExpireAt;
    }

    public void setAccountExpireAt(LocalDateTime accountExpireAt) {
        this.accountExpireAt = accountExpireAt;
    }

    public LocalDateTime getCredentialsExpireAt() {
        return credentialsExpireAt;
    }

    public void setCredentialsExpireAt(LocalDateTime credentialsExpireAt) {
        this.credentialsExpireAt = credentialsExpireAt;
    }

    public int getFailLoginCount() {
        return failLoginCount;
    }

    public void setFailLoginCount(int failLoginCount) {
        this.failLoginCount = failLoginCount;
    }

    public LocalDateTime getLastLoginAt() {
        return lastLoginAt;
    }

    public void setLastLoginAt(LocalDateTime lastLoginAt) {
        this.lastLoginAt = lastLoginAt;
    }
}
