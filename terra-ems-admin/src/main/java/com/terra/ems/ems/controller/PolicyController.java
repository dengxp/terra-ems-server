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

package com.terra.ems.ems.controller;

import com.terra.ems.ems.entity.Policy;
import com.terra.ems.ems.enums.PolicyType;
import com.terra.ems.ems.service.PolicyService;
import com.terra.ems.framework.controller.WritableController;
import com.terra.ems.framework.enums.DataItemStatus;
import com.terra.ems.common.domain.Result;
import com.terra.ems.framework.service.ReadableService;
import com.terra.ems.framework.service.WritableService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 政策法规控制器
 *
 * @author dengxueping
 * @since 2026-01-11
 */
@RestController
@RequestMapping("/ems/policies")
@Tag(name = "政策法规管理")
@RequiredArgsConstructor
public class PolicyController extends WritableController<Policy, Long> {

    private final PolicyService service;

    /**
     * 获取可写服务
     *
     * @return 政策法规服务
     */
    @Override
    protected WritableService<Policy, Long> getWritableService() {
        return service;
    }

    /**
     * 获取可读服务
     *
     * @return 政策法规服务
     */
    @Override
    protected ReadableService<Policy, Long> getReadableService() {
        return service;
    }

    /**
     * 查询所有启用的政策
     *
     * @return 启用的政策列表
     */
    @GetMapping("/enabled")
    @Operation(summary = "查询所有启用的政策")
    public Result<List<Policy>> findAllEnabled() {
        return Result.content(service.findAllEnabled());
    }

    /**
     * 按类型查询政策列表
     *
     * @param type 政策类型
     * @return 政策列表
     */
    @GetMapping("/type/{type}")
    @Operation(summary = "按类型查询政策列表")
    public Result<List<Policy>> findByType(
            @PathVariable @Parameter(description = "政策类型") PolicyType type) {
        return Result.content(service.findByType(type));
    }

    /**
     * 分页条件查询政策法规
     *
     * @param title  政策标题
     * @param type   政策类型
     * @param status 状态
     * @param page   页码
     * @param size   每页数量
     * @return 分页结果
     */
    @GetMapping("/search")
    @Operation(summary = "分页条件查询")
    public Result<Page<Policy>> search(
            @RequestParam(required = false) @Parameter(description = "政策标题") String title,
            @RequestParam(required = false) @Parameter(description = "政策类型") PolicyType type,
            @RequestParam(required = false) @Parameter(description = "状态") DataItemStatus status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return Result.content(service.findByConditions(title, type, status, PageRequest.of(page, size)));
    }

    /**
     * 按类型统计政策数量
     *
     * @param type 政策类型
     * @return 政策数量
     */
    @GetMapping("/statistics/count/{type}")
    @Operation(summary = "按类型统计政策数量")
    public Result<Long> countByType(
            @PathVariable @Parameter(description = "政策类型") PolicyType type) {
        return Result.content(service.countByType(type));
    }

    /**
     * 更新政策状态
     *
     * @param id     政策ID
     * @param status 新状态
     * @return 更新后的实体
     */
    @PutMapping("/{id}/status")
    @Operation(summary = "更新政策状态")
    public Result<Policy> updateStatus(
            @PathVariable Long id,
            @RequestParam @Parameter(description = "新状态") DataItemStatus status) {
        return Result.content(service.updateStatus(id, status));
    }
}
