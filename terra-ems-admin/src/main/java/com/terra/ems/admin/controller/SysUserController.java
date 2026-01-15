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

package com.terra.ems.admin.controller;

import com.terra.ems.common.domain.Result;
import com.terra.ems.framework.controller.BaseController;
import com.terra.ems.framework.definition.dto.Pager;
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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Map;

/**
 * 系统用户控制器
 *
 * @author dengxueping
 * @since 2026-01-11
 */

@Tag(name = "系统用户管理", description = "系统用户的CRUD及特定业务接口")
@RestController
@RequestMapping("/system/user")
public class SysUserController extends BaseController<SysUser, Long> {

    private static final Logger log = LoggerFactory.getLogger(SysUserController.class);

    private final SysUserService userService;
    private final UserMapper userMapper;

    @Autowired
    public SysUserController(SysUserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    /**
     * 获取业务服务
     *
     * @return 系统用户服务
     */
    @Override
    protected BaseService<SysUser, Long> getService() {
        return userService;
    }

    /**
     * 高级搜索用户列表
     *
     * @param pager 分页参数
     * @param param 用户查询参数
     * @return 分页结果
     */
    @Operation(summary = "高级搜索用户列表", description = "使用强类型参数进行高级搜索")
    @GetMapping("/search")
    public Result<Map<String, Object>> search(Pager pager, com.terra.ems.system.param.UserQueryParam param) {
        return result(userService.findPage(pager, param));
    }

    /**
     * 保存或更新用户信息
     *
     * @param user 用户实体
     * @return 操作结果及实体
     */
    @Operation(summary = "保存或更新用户（标准实体模式）")
    @Override
    public Result<SysUser> saveOrUpdate(@RequestBody @Validated SysUser user) {
        Result<SysUser> result = super.saveOrUpdate(user);
        if (result.getData() != null) {
            result.getData().setPassword(null);
        }
        return result;
    }

    /**
     * 获取当前登录用户信息
     *
     * @param principal 当前认证主体
     * @return 用户信息
     */
    @Operation(summary = "获取当前登录用户信息")
    @GetMapping("/current-user")
    public Result<SysUser> findCurrentUser(Principal principal) {
        log.info("[Terra]|- SysUser Controller findCurrentUser, principal: {}", principal);
        if (principal == null) {
            return Result.failure("未获取到登录信息");
        }

        // 从Authentication中获取用户名，然后查询数据库获取最新信息
        Authentication authentication = (Authentication) principal;
        String username = authentication.getName();

        SysUser user = userService.findByUsername(username);
        if (user != null) {
            return Result.success("获取成功", user);
        } else {
            return Result.failure("用户不存在");
        }
    }
}
