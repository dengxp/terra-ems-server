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

package com.terra.ems.common.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.CredentialsContainer;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

/**
 * 当前登录用户信息，实现了UserDetails接口
 *
 * @author dengxueping
 * @since 2026-01-11
 */

@Data
@Schema(title = "当前登录用户信息")
public class CurrentUser implements UserDetails, CredentialsContainer, Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(title = "用户ID")
    private String userId;

    @Schema(title = "用户名")
    private String username;

    @Schema(title = "密码")
    @JsonIgnore
    private String password;

    @Schema(title = "真实姓名")
    private String realName;

    @Schema(title = "头像")
    private String avatar;

    @Schema(title = "所属部门ID")
    private Long deptId;

    @Schema(title = "所属部门名称")
    private String deptName;

    @Schema(title = "可访问部门ID集合")
    private Set<Long> accessibleDeptIds;

    @Schema(title = "角色集合")
    private Set<String> roles;

    @Schema(title = "是否启用")
    private boolean enabled;

    @Schema(title = "权限集合")
    private Collection<? extends GrantedAuthority> authorities;

    @Schema(title = "邮箱")
    private String email;

    @Schema(title = "手机号")
    private String phone;

    public CurrentUser() {
    }

    public CurrentUser(String userId, String username, String password, String realName, String avatar,
            Set<String> roles, Long deptId, String deptName, Set<Long> accessibleDeptIds, boolean enabled,
            Collection<? extends GrantedAuthority> authorities) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.realName = realName;
        this.avatar = avatar;
        this.roles = roles;
        this.deptId = deptId;
        this.deptName = deptName;
        this.accessibleDeptIds = accessibleDeptIds;
        this.enabled = enabled;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public void eraseCredentials() {
        this.password = null;
    }
}
