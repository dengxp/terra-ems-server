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

package com.terra.ems.admin.controller.system;

import com.terra.ems.common.domain.Result;
import com.terra.ems.framework.controller.BaseController;
import com.terra.ems.framework.service.BaseService;
import com.terra.ems.system.dto.UserDTO;
import com.terra.ems.system.entity.SysUser;
import com.terra.ems.system.mapper.UserMapper;
import com.terra.ems.system.service.SysUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * Name: SysUserController
 * Email: dengxueping@gmail.com
 * Date: 2024-12-20
 * Description: 系统用户控制器，使用 DTO 模式
 *
 * @author dengxueping
 */
@Tag(name = "系统用户管理", description = "系统用户的CRUD及特定业务接口")
@RestController
@RequestMapping("/system/user")
public class SysUserController extends BaseController<SysUser, UserDTO, Long> {

    private static final Logger log = LoggerFactory.getLogger(SysUserController.class);

    private final SysUserService userService;
    private final UserMapper userMapper;

    @Autowired
    public SysUserController(SysUserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @Override
    protected BaseService<SysUser, Long> getService() {
        return userService;
    }

    @Override
    protected UserDTO toDTO(SysUser entity) {
        return userMapper.toDTO(entity);
    }

    @Override
    protected SysUser toEntity(UserDTO dto) {
        return userMapper.toEntity(dto);
    }

    /**
     * 获取当前登录用户信息
     */
    @Operation(summary = "获取当前登录用户信息")
    @GetMapping("/current-user")
    public Result<UserDTO> findCurrentUser(Principal principal) {
        log.info("[Terra]|- SysUser Controller findCurrentUser, principal: {}", principal);
        if (principal == null) {
            return Result.failure("未获取到登录信息");
        }

        // 从Authentication中获取用户名，然后查询数据库获取最新信息
        Authentication authentication = (Authentication) principal;
        String username = authentication.getName();

        // 查询数据库并转换为 DTO（不包含敏感信息）
        SysUser user = userService.findByUsername(username);
        if (user != null) {
            return Result.success("获取成功", userMapper.toDTO(user));
        } else {
            return Result.failure("用户不存在");
        }
    }
}
