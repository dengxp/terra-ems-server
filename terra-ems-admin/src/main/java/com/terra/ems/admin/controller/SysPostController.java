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
import com.terra.ems.framework.service.BaseService;
import com.terra.ems.system.entity.SysPost;
import com.terra.ems.system.service.SysPostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 岗位管理控制器
 *
 * @author dengxueping
 * @since 2026-01-11
 */

@Tag(name = "岗位管理")
@RestController
@RequestMapping("/system/post")
public class SysPostController extends BaseController<SysPost, Long> {
    private final SysPostService postService;

    @Autowired
    public SysPostController(SysPostService postService) {
        this.postService = postService;
    }

    /**
     * 获取业务服务
     *
     * @return 岗位管理服务
     */
    @Override
    protected BaseService<SysPost, Long> getService() {
        return postService;
    }

    /**
     * 保存或更新岗位信息
     *
     * @param post 岗位实体
     * @return 操作结果及实体
     */
    @Operation(summary = "保存或更新岗位")
    @Override
    @PreAuthorize("hasAnyAuthority('system:post:add', 'system:post:edit')")
    public Result<SysPost> saveOrUpdate(@Validated @RequestBody SysPost post) {
        if (!postService.checkCodeUnique(post.getCode(), post.getId())) {
            return Result.failure("操作岗位'" + post.getName() + "'失败，岗位编码已存在");
        }
        return super.saveOrUpdate(post);
    }
}
