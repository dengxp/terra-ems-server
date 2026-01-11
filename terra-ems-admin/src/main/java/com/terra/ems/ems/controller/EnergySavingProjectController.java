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

import com.terra.ems.ems.entity.EnergySavingProject;
import com.terra.ems.ems.enums.ProjectStatus;
import com.terra.ems.ems.service.EnergySavingProjectService;
import com.terra.ems.framework.controller.WritableController;
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

import java.math.BigDecimal;
import java.util.List;

/**
 * 节能项目控制器
 *
 * @author dengxueping
 * @since 2026-01-11
 */
@RestController
@RequestMapping("/ems/saving-projects")
@Tag(name = "节能项目管理")
@RequiredArgsConstructor
public class EnergySavingProjectController extends WritableController<EnergySavingProject, Long> {

    private final EnergySavingProjectService service;

    /**
     * 获取可写服务
     *
     * @return 节能项目服务
     */
    @Override
    protected WritableService<EnergySavingProject, Long> getWritableService() {
        return service;
    }

    /**
     * 获取可读服务
     *
     * @return 节能项目服务
     */
    @Override
    protected ReadableService<EnergySavingProject, Long> getReadableService() {
        return service;
    }

    /**
     * 按状态查询项目列表
     *
     * @param status 项目状态
     * @return 项目列表
     */
    @GetMapping("/status/{status}")
    @Operation(summary = "按状态查询项目列表")
    public Result<List<EnergySavingProject>> findByStatus(
            @PathVariable @Parameter(description = "项目状态") ProjectStatus status) {
        return Result.content(service.findByStatus(status));
    }

    /**
     * 分页条件查询节能项目
     *
     * @param name   项目名称
     * @param status 项目状态
     * @param page   页码
     * @param size   每页数量
     * @return 分页结果
     */
    @GetMapping("/search")
    @Operation(summary = "分页条件查询")
    public Result<Page<EnergySavingProject>> search(
            @RequestParam(required = false) @Parameter(description = "项目名称") String name,
            @RequestParam(required = false) @Parameter(description = "项目状态") ProjectStatus status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return Result.content(service.findByConditions(name, status, PageRequest.of(page, size)));
    }

    /**
     * 获取已完成项目的节约量总和
     *
     * @return 节约量总和
     */
    @GetMapping("/statistics/saving-amount")
    @Operation(summary = "获取已完成项目的节约量总和")
    public Result<BigDecimal> getCompletedSavingAmount() {
        return Result.content(service.getCompletedSavingAmount());
    }

    /**
     * 按状态统计项目数量
     *
     * @param status 项目状态
     * @return 项目数量
     */
    @GetMapping("/statistics/count/{status}")
    @Operation(summary = "按状态统计项目数量")
    public Result<Long> countByStatus(
            @PathVariable @Parameter(description = "项目状态") ProjectStatus status) {
        return Result.content(service.countByStatus(status));
    }

    /**
     * 更新项目状态
     *
     * @param id     项目ID
     * @param status 新状态
     * @return 更新后的实体
     */
    @PutMapping("/{id}/status")
    @Operation(summary = "更新项目状态")
    public Result<EnergySavingProject> updateStatus(
            @PathVariable Long id,
            @RequestParam @Parameter(description = "新状态") ProjectStatus status) {
        return Result.content(service.updateStatus(id, status));
    }
}
