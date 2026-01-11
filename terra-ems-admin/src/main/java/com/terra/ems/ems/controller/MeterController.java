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

import com.terra.ems.common.domain.Result;
import com.terra.ems.ems.entity.Meter;
import com.terra.ems.ems.service.MeterService;
import com.terra.ems.framework.enums.DataItemStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 计量器具档案控制器
 *
 * @author dengxueping
 * @since 2026-01-11
 */
@Tag(name = "计量器具管理")
@RestController
@RequestMapping("/meters")
@RequiredArgsConstructor
public class MeterController {

    private final MeterService meterService;

    /**
     * 分页查询计量器具
     *
     * @param code   编码
     * @param name   名称
     * @param type   种类
     * @param status 状态值
     * @param page   页码
     * @param size   每页大小
     * @return 分页结果
     */
    @Operation(summary = "分页查询计量器具")
    @GetMapping
    public Result<Page<Meter>> list(
            @Parameter(description = "编码") @RequestParam(required = false) String code,
            @Parameter(description = "名称") @RequestParam(required = false) String name,
            @Parameter(description = "种类") @RequestParam(required = false) String type,
            @Parameter(description = "状态值") @RequestParam(required = false) Integer status,
            @Parameter(description = "页码") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "每页大小") @RequestParam(defaultValue = "10") int size) {

        DataItemStatus statusEnum = status != null ? DataItemStatus.fromValue(status) : null;
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<Meter> result = meterService.findPage(code, name, type, statusEnum, pageable);
        return Result.content(result);
    }

    /**
     * 根据ID查询计量器具
     *
     * @param id 计量器具ID
     * @return 计量器具详情
     */
    @Operation(summary = "根据ID查询计量器具")
    @GetMapping("/{id}")
    public Result<Meter> getById(@PathVariable Long id) {
        return java.util.Optional.ofNullable(meterService.findById(id))
                .map(Result::content)
                .orElse(Result.failure("计量器具不存在"));
    }

    /**
     * 创建计量器具
     *
     * @param meter 计量器具实体
     * @return 创建后的实体
     */
    @Operation(summary = "创建计量器具")
    @PostMapping
    public Result<Meter> create(@RequestBody Meter meter) {
        try {
            Meter created = meterService.create(meter);
            return Result.content(created);
        } catch (IllegalArgumentException e) {
            return Result.failure(e.getMessage());
        }
    }

    /**
     * 更新计量器具
     *
     * @param id    计量器具ID
     * @param meter 计量器具实体
     * @return 更新后的实体
     */
    @Operation(summary = "更新计量器具")
    @PutMapping("/{id}")
    public Result<Meter> update(@PathVariable Long id, @RequestBody Meter meter) {
        try {
            Meter updated = meterService.update(id, meter);
            return Result.content(updated);
        } catch (IllegalArgumentException e) {
            return Result.failure(e.getMessage());
        }
    }

    /**
     * 删除计量器具
     *
     * @param id 计量器具ID
     * @return 操作结果
     */
    @Operation(summary = "删除计量器具")
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        try {
            meterService.delete(id);
            return Result.success();
        } catch (IllegalArgumentException e) {
            return Result.failure(e.getMessage());
        }
    }

    /**
     * 批量删除计量器具
     *
     * @param ids ID列表
     * @return 操作结果
     */
    @Operation(summary = "批量删除计量器具")
    @DeleteMapping("/batch")
    public Result<Void> deleteBatch(@RequestParam List<Long> ids) {
        try {
            meterService.deleteBatch(ids);
            return Result.success();
        } catch (IllegalArgumentException e) {
            return Result.failure(e.getMessage());
        }
    }
}
