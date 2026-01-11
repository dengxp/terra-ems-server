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
package com.terra.ems.system.service;

import com.terra.ems.common.domain.CurrentUser;
import com.terra.ems.framework.jpa.repository.BaseRepository;
import com.terra.ems.framework.service.BaseService;
import com.terra.ems.system.entity.SysPermission;
import com.terra.ems.system.entity.SysRole;
import com.terra.ems.system.entity.SysUser;
import com.terra.ems.system.repository.SysUserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Name: SysUserService.java
 * Email: dengxueping@gmail.com
 * Date: 2026-01-10
 * Description:
 * 用户服务实现
 * <p>
 * 集成 UserDetailsService 用于 Spring Security 认证
 *
 * @author dengxueping
 */
@Service
public class SysUserService extends BaseService<SysUser, Long> implements UserDetailsService {

    private final SysUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final SysDeptService deptService;

    public SysUserService(SysUserRepository userRepository, PasswordEncoder passwordEncoder,
            SysDeptService deptService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.deptService = deptService;
    }

    @Override
    protected BaseRepository<SysUser, Long> getRepository() {
        return userRepository;
    }

    /**
     * 根据用户名查找用户
     */
    public SysUser findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    /**
     * 根据手机号查找用户
     */
    public SysUser findByPhone(String phone) {
        return userRepository.findByPhone(phone).orElse(null);
    }

    /**
     * 分页查询用户
     *
     * @param pager 分页参数
     * @param param 查询参数
     * @return 用户列表
     */
    public org.springframework.data.domain.Page<SysUser> findPage(com.terra.ems.framework.definition.dto.Pager pager,
            com.terra.ems.system.param.UserQueryParam param) {
        return userRepository.findAll((root, query, cb) -> {
            java.util.List<jakarta.persistence.criteria.Predicate> list = new java.util.ArrayList<>();

            // 关键字模糊查询
            if (org.springframework.util.StringUtils.hasText(param.getKeyword())) {
                String keyword = "%" + param.getKeyword() + "%";
                list.add(cb.or(
                        cb.like(root.get("username"), keyword),
                        cb.like(root.get("nickname"), keyword),
                        cb.like(root.get("phone"), keyword)));
            }

            // 精确查询
            if (org.springframework.util.StringUtils.hasText(param.getUsername())) {
                list.add(cb.like(root.get("username"), param.getUsername() + "%"));
            }
            if (org.springframework.util.StringUtils.hasText(param.getPhone())) {
                list.add(cb.like(root.get("phone"), param.getPhone() + "%"));
            }
            if (param.getDeptId() != null) {
                list.add(cb.equal(root.get("dept").get("id"), param.getDeptId()));
            }
            if (param.getStatus() != null) {
                list.add(cb.equal(root.get("status"),
                        com.terra.ems.framework.enums.DataItemStatus.fromValue(param.getStatus())));
            }

            // 时间范围
            if (param.getBeginTime() != null) {
                list.add(cb.greaterThanOrEqualTo(root.get("createTime"), param.getBeginTime()));
            }
            if (param.getEndTime() != null) {
                list.add(cb.lessThanOrEqualTo(root.get("createTime"), param.getEndTime()));
            }

            return cb.and(list.toArray(new jakarta.persistence.criteria.Predicate[0]));
        }, pager.getPageable());
    }

    /**
     * 添加用户（示例方法，用于处理密码加密）
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysUser saveOrUpdate(SysUser user) {
        if (user.getId() == null && user.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        return super.saveOrUpdate(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser sysUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("用户不存在: " + username));

        // 更新最后登录时间
        sysUser.setLastLoginAt(LocalDateTime.now());
        userRepository.save(sysUser);

        // 获取权限
        Set<GrantedAuthority> authorities = getAuthorities(sysUser);

        // 获取角色代码集合
        Set<String> roleCodes = sysUser.getRoles() != null
                ? sysUser.getRoles().stream().map(SysRole::getCode).collect(Collectors.toSet())
                : new HashSet<>();

        // 获取部门信息
        Long deptId = sysUser.getDept() != null ? sysUser.getDept().getId() : null;
        String deptName = sysUser.getDept() != null ? sysUser.getDept().getName() : null;

        // 获取可访问部门ID集合
        Set<Long> accessibleDeptIds = deptService.getAccessibleDeptIds(sysUser);

        // 返回 CurrentUser
        CurrentUser currentUser = new CurrentUser(
                String.valueOf(sysUser.getId()),
                sysUser.getUsername(),
                sysUser.getPassword(),
                sysUser.getNickname(),
                sysUser.getAvatar(),
                roleCodes,
                deptId,
                deptName,
                accessibleDeptIds,
                sysUser.isEnabled(),
                authorities);
        currentUser.setEmail(sysUser.getEmail());
        currentUser.setPhone(sysUser.getPhone());

        return currentUser;
    }

    /**
     * 获取用户权限
     */
    private Set<GrantedAuthority> getAuthorities(SysUser user) {
        Set<GrantedAuthority> authorities = new HashSet<>();

        // 添加角色
        if (user.getRoles() != null) {
            authorities.addAll(user.getRoles().stream()
                    .map(SysRole::getCode)
                    .map(roleCode -> new SimpleGrantedAuthority("ROLE_" + roleCode))
                    .collect(Collectors.toSet()));

            // 添加角色关联的权限
            for (SysRole role : user.getRoles()) {
                if (role.getPermissions() != null) {
                    authorities.addAll(role.getPermissions().stream()
                            .map(SysPermission::getCode)
                            .map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toSet()));
                }
            }
        }

        // 添加用户直接关联的权限
        if (user.getPermissions() != null) {
            authorities.addAll(user.getPermissions().stream()
                    .map(SysPermission::getCode)
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toSet()));
        }

        return authorities;
    }
}
