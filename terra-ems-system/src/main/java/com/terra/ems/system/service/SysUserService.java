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
import com.terra.ems.system.entity.SysPost;
import com.terra.ems.system.entity.SysRole;
import com.terra.ems.system.entity.SysUser;
import com.terra.ems.system.repository.SysPostRepository;
import com.terra.ems.system.repository.SysRoleRepository;
import com.terra.ems.system.repository.SysUserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import com.terra.ems.framework.definition.dto.Pager;
import com.terra.ems.system.param.UserQueryParam;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;

import com.terra.ems.system.vo.SysUserImportVo;
import com.terra.ems.common.exception.TerraException;
import com.terra.ems.common.constant.ErrorCodes;
import com.terra.ems.framework.enums.DataItemStatus;
import com.terra.ems.system.entity.SysDept;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 用户服务实现，集成 UserDetailsService 用于 Spring Security 认证
 *
 * @author dengxueping
 * @since 2026-01-11
 */

@Service
public class SysUserService extends BaseService<SysUser, Long> implements UserDetailsService {

    private final SysUserRepository userRepository;
    private final SysRoleRepository roleRepository;
    private final SysPostRepository postRepository;
    private final PasswordEncoder passwordEncoder;
    private final SysDeptService deptService;

    private static final Logger log = LoggerFactory.getLogger(SysUserService.class);

    public SysUserService(SysUserRepository userRepository, SysRoleRepository roleRepository,
            SysPostRepository postRepository, PasswordEncoder passwordEncoder, SysDeptService deptService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.postRepository = postRepository;
        this.passwordEncoder = passwordEncoder;
        this.deptService = deptService;
    }

    /**
     * 获取数据访问仓库
     *
     * @return 用户仓库
     */
    @Override
    public BaseRepository<SysUser, Long> getRepository() {
        return userRepository;
    }

    /**
     * 根据 ID 查找用户
     * 并填充用于前端显示的桥接字段
     */
    @Override
    @Transactional(readOnly = true)
    public SysUser findById(Long id) {
        SysUser user = super.findById(id);
        if (user != null) {
            // 填充部门ID
            if (user.getDept() != null) {
                user.setDeptId(user.getDept().getId());
            }
            // 填充角色ID集合
            if (user.getRoles() != null && !user.getRoles().isEmpty()) {
                user.setRoleIds(user.getRoles().stream()
                        .map(SysRole::getId)
                        .collect(Collectors.toSet()));
            }
            // 填充岗位ID集合
            if (user.getPosts() != null && !user.getPosts().isEmpty()) {
                user.setPostIds(user.getPosts().stream()
                        .map(SysPost::getId)
                        .collect(Collectors.toSet()));
            }
        }
        return user;
    }

    /**
     * 根据用户名查找用户
     */
    @Transactional(readOnly = true)
    public SysUser findByUsername(String username) {
        SysUser user = userRepository.findByUsername(username).orElse(null);
        if (user != null) {
            // 聚合角色代码
            if (user.getRoles() != null) {
                user.setRoleCodes(user.getRoles().stream()
                        .map(SysRole::getCode)
                        .collect(Collectors.toSet()));
            }

            // 聚合权限代码
            Set<String> pCodes = new HashSet<>();
            // 1. 来自角色的权限
            if (user.getRoles() != null) {
                user.getRoles().forEach(role -> {
                    if (role.getPermissions() != null) {
                        pCodes.addAll(role.getPermissions().stream()
                                .map(SysPermission::getCode)
                                .collect(Collectors.toSet()));
                    }
                });
            }
            // 2. 来自用户直接绑定的权限
            if (user.getPermissions() != null) {
                pCodes.addAll(user.getPermissions().stream()
                        .map(SysPermission::getCode)
                        .collect(Collectors.toSet()));
            }

            // 3. 如果是超级管理员，注入上帝权限码
            if (user.isAdmin()) {
                pCodes.add("*:*:*");
            }

            user.setPermissionCodes(pCodes);
        }
        return user;
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
    /**
     * 分页查询用户
     *
     * @param pager 分页参数
     * @param param 查询参数
     * @return 用户列表
     */
    public Page<SysUser> findPage(Pager pager, UserQueryParam param) {
        return userRepository.findAll(buildSpecification(param), pager.getPageable());
    }

    /**
     * 列表查询用户
     *
     * @param param 查询参数
     * @return 用户列表
     */
    public List<SysUser> findList(UserQueryParam param) {
        return userRepository.findAll(buildSpecification(param));
    }

    /**
     * 构建查询条件
     */
    private Specification<SysUser> buildSpecification(UserQueryParam param) {
        return (root, query, cb) -> {
            List<Predicate> list = new ArrayList<>();

            // 关键字模糊查询
            if (StringUtils.hasText(param.getKeyword())) {
                String keyword = "%" + param.getKeyword() + "%";
                list.add(cb.or(
                        cb.like(root.get("username"), keyword),
                        cb.like(root.get("realName"), keyword),
                        cb.like(root.get("phone"), keyword)));
            }

            // 精确查询
            if (StringUtils.hasText(param.getUsername())) {
                list.add(cb.like(root.get("username"), param.getUsername() + "%"));
            }
            if (StringUtils.hasText(param.getRealName())) {
                list.add(cb.like(root.get("realName"), "%" + param.getRealName() + "%"));
            }
            if (StringUtils.hasText(param.getPhone())) {
                list.add(cb.like(root.get("phone"), param.getPhone() + "%"));
            }
            if (param.getDeptId() != null) {
                list.add(cb.equal(root.get("dept").get("id"), param.getDeptId()));
            }
            if (param.getExcludeDeptId() != null) {
                list.add(cb.or(
                        cb.notEqual(root.get("dept").get("id"), param.getExcludeDeptId()),
                        cb.isNull(root.get("dept"))));
            }
            if (param.getStatus() != null) {
                list.add(cb.equal(root.get("status"),
                        DataItemStatus.fromValue(param.getStatus())));
            }

            // 时间范围
            if (param.getBeginTime() != null) {
                list.add(cb.greaterThanOrEqualTo(root.get("createdAt"), param.getBeginTime()));
            }
            if (param.getEndTime() != null) {
                list.add(cb.lessThanOrEqualTo(root.get("createdAt"), param.getEndTime()));
            }

            // 角色过滤
            if (param.getRoleId() != null) {
                list.add(cb.equal(root.join("roles").get("id"), param.getRoleId()));
            }

            return cb.and(list.toArray(new Predicate[0]));
        };
    }

    /**
     * 分页查询无部门用户
     *
     * @param pager 分页参数
     * @param param 查询参数
     * @return 用户列表
     */
    public Page<SysUser> findUsersWithoutDepartment(Pager pager, UserQueryParam param) {
        return userRepository.findAll((root, query, cb) -> {
            List<Predicate> list = new ArrayList<>();

            // 关键字模糊查询
            if (StringUtils.hasText(param.getKeyword())) {
                String keyword = "%" + param.getKeyword() + "%";
                list.add(cb.or(
                        cb.like(root.get("username"), keyword),
                        cb.like(root.get("realName"), keyword),
                        cb.like(root.get("phone"), keyword)));
            }

            // 精确查询
            if (StringUtils.hasText(param.getUsername())) {
                list.add(cb.like(root.get("username"), param.getUsername() + "%"));
            }
            if (StringUtils.hasText(param.getRealName())) {
                list.add(cb.like(root.get("realName"), "%" + param.getRealName() + "%"));
            }
            if (StringUtils.hasText(param.getPhone())) {
                list.add(cb.like(root.get("phone"), param.getPhone() + "%"));
            }

            // 强制查询无部门
            list.add(cb.isNull(root.get("dept")));

            if (param.getStatus() != null) {
                list.add(cb.equal(root.get("status"),
                        DataItemStatus.fromValue(param.getStatus())));
            }

            // 时间范围
            if (param.getBeginTime() != null) {
                list.add(cb.greaterThanOrEqualTo(root.get("createdAt"), param.getBeginTime()));
            }
            if (param.getEndTime() != null) {
                list.add(cb.lessThanOrEqualTo(root.get("createdAt"), param.getEndTime()));
            }

            return cb.and(list.toArray(new Predicate[0]));
        }, pager.getPageable());
    }

    /**
     * 添加或更新用户
     * 处理密码加密及关联绑定逻辑
     *
     * @param user 用户实体
     * @return 保存后的用户实体
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysUser saveOrUpdate(SysUser user) {
        if (user.getId() != null) {
            // Update: Keep existing password and superAdmin status if not provided
            userRepository.findById(user.getId()).ifPresent(existing -> {
                if (!StringUtils.hasText(user.getPassword())) {
                    user.setPassword(existing.getPassword());
                }
                // 强制保持现有超级管理员状态，不允许通过此接口修改
                user.setSuperAdmin(existing.getSuperAdmin());
            });
        }

        if (user.getId() == null) {
            // 新增用户默认为非超级管理员
            user.setSuperAdmin(false);
            if (StringUtils.hasText(user.getPassword())) {
                user.setPassword(passwordEncoder.encode(user.getPassword()));
            }
        } else {
            if (StringUtils.hasText(user.getPassword())) {
                user.setPassword(passwordEncoder.encode(user.getPassword()));
            }
        }

        // 处理关联绑定 (部门、角色、岗位)
        handleAssociations(user);

        return super.saveOrUpdate(user);
    }

    /**
     * 创建用户 (复杂逻辑)
     *
     * @param user 用户实体 (包含 roleIds, postIds, deptId)
     * @return 保存后的用户实体
     */
    @Transactional(rollbackFor = Exception.class)
    public SysUser create(SysUser user) {
        // 1. 校验唯一性
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new RuntimeException("用户名 [" + user.getUsername() + "] 已存在");
        }
        if (StringUtils.hasText(user.getPhone())
                && userRepository.findByPhone(user.getPhone()).isPresent()) {
            throw new RuntimeException("手机号 [" + user.getPhone() + "] 已存在");
        }

        // 2. 加密密码
        if (StringUtils.hasText(user.getPassword())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        // 3. 强制设为非超级管理员
        user.setSuperAdmin(false);

        // 4. 处理关联绑定
        handleAssociations(user);

        return getRepository().save(user);
    }

    /**
     * 处理用户关联绑定 (部门、角色、岗位)
     *
     * @param user 用户实体
     */
    private void handleAssociations(SysUser user) {
        // 1. 关联部门
        if (user.getDeptId() != null) {
            user.setDept(deptService.findById(user.getDeptId()));
        }

        // 2. 关联角色
        if (user.getRoleIds() != null && !user.getRoleIds().isEmpty()) {
            user.setRoles(roleRepository.findByIdIn(user.getRoleIds()));
        }

        // 3. 关联岗位
        if (user.getPostIds() != null && !user.getPostIds().isEmpty()) {
            user.setPosts(postRepository.findByIdIn(user.getPostIds()));
        }
    }

    /**
     * 加载用户信息（Spring Security 专用）
     *
     * @param username 用户名
     * @return 用户详细信息 {@link UserDetails}
     * @throws UsernameNotFoundException 如果用户未找到
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserDetails loadUserByUsername(String identifier) throws UsernameNotFoundException {
        // 支持用户名或手机号登录
        SysUser sysUser = userRepository.findByUsernameOrPhone(identifier, identifier)
                .orElseThrow(() -> new UsernameNotFoundException("用户不存在: " + identifier));

        // 更新最后登录时间
        sysUser.setLastLoginAt(LocalDateTime.now());
        getRepository().save(sysUser);

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
                sysUser.getRealName(),
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

        // 如果是超级管理员，赋予上帝通配符权限码
        if (user.isAdmin()) {
            authorities.add(new SimpleGrantedAuthority("*:*:*"));
        }

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

    /**
     * 导入用户数据 (方案A: 逐行处理)
     *
     * @param userList      用户数据列表
     * @param updateSupport 是否允许更新已存在的数据
     * @param operName      操作用户
     * @return 包含导入结果的VO列表
     */
    @Transactional(rollbackFor = Exception.class)
    public List<SysUserImportVo> importUser(List<SysUser> userList, boolean updateSupport, String operName) {
        if (CollectionUtils.isEmpty(userList)) {
            throw new TerraException(ErrorCodes.BAD_REQUEST, "导入用户数据不能为空！");
        }

        List<SysUserImportVo> results = new ArrayList<>();

        for (SysUser user : userList) {
            if (user == null || !StringUtils.hasText(user.getUsername())) {
                continue;
            }
            SysUserImportVo vo = new SysUserImportVo(user);
            try {
                // 1. 验证是否存在这个用户
                SysUser u = userRepository.findByUsername(user.getUsername()).orElse(null);
                if (u == null) {
                    // 处理部门映射
                    if (user.getDept() != null && StringUtils.hasText(user.getDept().getName())) {
                        String deptName = user.getDept().getName();
                        SysDept dept = deptService.findByName(deptName);
                        if (dept != null) {
                            user.setDept(dept);
                        } else {
                            throw new RuntimeException("部门 [" + deptName + "] 不存在");
                        }
                    }

                    // saveOrUpdate will handle the encryption
                    user.setPassword("123456");
                    user.setStatus(DataItemStatus.ENABLE);
                    user.setCreatedAt(LocalDateTime.now());
                    user.setUpdatedAt(LocalDateTime.now());
                    saveOrUpdate(user);

                    vo.setImportStatus("成功");
                    vo.setImportReason("新增成功");
                } else if (updateSupport) {
                    // 更新逻辑
                    user.setId(u.getId());
                    if (user.getDept() != null && StringUtils.hasText(user.getDept().getName())) {
                        String deptName = user.getDept().getName();
                        SysDept dept = deptService.findByName(deptName);
                        if (dept != null) {
                            user.setDept(dept);
                        } else {
                            throw new RuntimeException("部门 [" + deptName + "] 不存在");
                        }
                    }
                    user.setUpdatedAt(LocalDateTime.now());
                    saveOrUpdate(user);

                    vo.setImportStatus("成功");
                    vo.setImportReason("更新成功");
                } else {
                    vo.setImportStatus("失败");
                    vo.setImportReason("账号已存在");
                }
            } catch (Exception e) {
                vo.setImportStatus("失败");
                vo.setImportReason(e.getMessage());
                log.warn("导入账号 {} 失败: {}", user.getUsername(), e.getMessage());
            }
            results.add(vo);
        }
        return results;
    }

    /**
     * 重置用户密码
     *
     * @param userId   用户ID
     * @param password 新密码(明文)
     */
    @Transactional(rollbackFor = Exception.class)
    public void resetPassword(Long userId, String password) {
        SysUser user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("用户不存在"));
        user.setPassword(passwordEncoder.encode(password));
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);
    }

    /**
     * 为多个用户添加角色
     *
     * @param roleId    角色ID
     * @param memberIds 用户ID列表
     */
    @Transactional(rollbackFor = Exception.class)
    public void addRoleToUsers(Long roleId, List<Long> memberIds) {
        SysRole role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("角色不存在"));
        List<SysUser> users = userRepository.findAllById(memberIds);
        for (SysUser user : users) {
            user.getRoles().add(role);
            userRepository.save(user);
        }
    }

    /**
     * 从多个用户移除角色
     *
     * @param roleId    角色ID
     * @param memberIds 用户ID列表
     */
    @Transactional(rollbackFor = Exception.class)
    public void removeRoleFromUsers(Long roleId, List<Long> memberIds) {
        SysRole role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("角色不存在"));
        List<SysUser> users = userRepository.findAllById(memberIds);
        for (SysUser user : users) {
            user.getRoles().remove(role);
            userRepository.save(user);
        }
    }

    /**
     * 更新用户角色
     *
     * @param userId  用户ID
     * @param roleIds 角色ID列表
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateRoles(Long userId, Set<Long> roleIds) {
        SysUser user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("用户不存在"));
        if (roleIds == null || roleIds.isEmpty()) {
            user.setRoles(new HashSet<>());
        } else {
            user.setRoles(roleRepository.findByIdIn(roleIds));
        }
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);
    }

    /**
     * 设置用户超级管理员状态
     *
     * @param userId  用户ID
     * @param isSuper 是否为超级管理员
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateSuperAdminStatus(Long userId, Boolean isSuper) {
        SysUser user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("用户不存在"));

        // 不能修改 admin 原生账号的状态（可选增强：或者允许修改，但 admin 逻辑在 SysUser.isAdmin 已经兜底）
        if ("admin".equals(user.getUsername())) {
            throw new RuntimeException("原生管理员账号状态禁止修改");
        }

        user.setSuperAdmin(isSuper);
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);
    }

    /**
     * 更新个人资料
     *
     * @param user 用户信息
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateProfile(SysUser user) {
        SysUser u = userRepository.findById(user.getId())
                .orElseThrow(() -> new RuntimeException("用户不存在"));

        // 仅允许修改部分字段
        if (StringUtils.hasText(user.getRealName())) {
            u.setRealName(user.getRealName());
        }
        if (StringUtils.hasText(user.getEmail())) {
            u.setEmail(user.getEmail());
        }
        if (StringUtils.hasText(user.getPhone())) {
            // 校验手机号唯一性（排除自己）
            userRepository.findByPhone(user.getPhone()).ifPresent(existing -> {
                if (!existing.getId().equals(u.getId())) {
                    throw new RuntimeException("手机号 [" + user.getPhone() + "] 已被占用");
                }
            });
            u.setPhone(user.getPhone());
        }
        if (user.getGender() != null) {
            u.setGender(user.getGender());
        }
        if (StringUtils.hasText(user.getAvatar())) {
            u.setAvatar(user.getAvatar());
        }

        u.setUpdatedAt(LocalDateTime.now());
        userRepository.save(u);
    }

    /**
     * 自助修改密码
     *
     * @param userId      用户ID
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     */
    @Transactional(rollbackFor = Exception.class)
    public void updatePassword(Long userId, String oldPassword, String newPassword) {
        SysUser user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("用户不存在"));

        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new RuntimeException("旧密码不正确");
        }

        if (passwordEncoder.matches(newPassword, user.getPassword())) {
            throw new RuntimeException("新密码不能与旧密码相同");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);
    }

    /**
     * 统计拥有该角色的用户数量
     *
     * @param roleId 角色ID
     * @return 数量
     */
    public Integer countByRoleId(Long roleId) {
        return userRepository.countByRolesId(roleId);
    }
}
