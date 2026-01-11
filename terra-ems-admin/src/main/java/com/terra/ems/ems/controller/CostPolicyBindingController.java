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
import com.terra.ems.ems.entity.CostPolicyBinding;
import com.terra.ems.ems.service.CostPolicyBindingService;
import com.terra.ems.framework.enums.DataItemStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * 成本策略绑定控制器
 *
 * @author dengxueping
 */
@RestController
@RequestMapping("/ems/cost-policy-bindings")
@Tag(name = "成本策略绑定管理")
@RequiredArgsConstructor
public class CostPolicyBindingController {

    private final CostPolicyBindingService service;

    @GetMapping
    @Operation(summary = "分页条件查询")
    public Result<Page<CostPolicyBinding>> search(
            @RequestParam(required = false) @Parameter(description = "用能单元ID") Long energyUnitId,
            @RequestParam(required = false) @Parameter(description = "电价策略ID") Long pricePolicyId,
            @RequestParam(required = false) @Parameter(description = "状态") DataItemStatus status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return Result
                .content(service.findByConditions(energyUnitId, pricePolicyId, status, PageRequest.of(page, size)));
    }

    @GetMapping("/{id}")
    @Operation(summary = "根据ID查询")
    public Result<CostPolicyBinding> findById(@PathVariable Long id) {
        return java.util.Optional.ofNullable(service.findById(id))
                .map(Result::content)
                .orElse(Result.failure("绑定记录不存在"));
    }

    @GetMapping("/energy-unit/{energyUnitId}")
    @Operation(summary = "按用能单元查询")
    public Result<List<CostPolicyBinding>> findByEnergyUnit(
            @PathVariable @Parameter(description = "用能单元ID") Long energyUnitId) {
        return Result.content(service.findByEnergyUnit(energyUnitId));
    }

    @GetMapping("/price-policy/{pricePolicyId}")
    @Operation(summary = "按电价策略查询")
    public Result<List<CostPolicyBinding>> findByPricePolicy(
            @PathVariable @Parameter(description = "电价策略ID") Long pricePolicyId) {
        return Result.content(service.findByPricePolicy(pricePolicyId));
    }

    @GetMapping("/effective")
    @Operation(summary = "查询有效绑定")
    public Result<CostPolicyBinding> findEffectiveBinding(
            @RequestParam @Parameter(description = "用能单元ID") Long energyUnitId,
            @RequestParam(required = false) @Parameter(description = "日期") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        LocalDate queryDate = date != null ? date : LocalDate.now();
        return service.findEffectiveBinding(energyUnitId, queryDate)
                .map(Result::content)
                .orElse(Result.failure("未找到有效绑定"));
    }

    @PostMapping
    @Operation(summary = "创建绑定")
    public Result<CostPolicyBinding> create(
            @RequestParam @Parameter(description = "用能单元ID") Long energyUnitId,
            @RequestParam @Parameter(description = "电价策略ID") Long pricePolicyId,
            @RequestParam @Parameter(description = "生效开始日期") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @Parameter(description = "生效结束日期") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(required = false) @Parameter(description = "备注") String remark) {
        try {
            return Result.content(service.create(energyUnitId, pricePolicyId, startDate, endDate, remark));
        } catch (IllegalArgumentException e) {
            return Result.failure(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "更新绑定")
    public Result<CostPolicyBinding> update(
            @PathVariable Long id,
            @RequestParam(required = false) @Parameter(description = "电价策略ID") Long pricePolicyId,
            @RequestParam @Parameter(description = "生效开始日期") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @Parameter(description = "生效结束日期") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(required = false) @Parameter(description = "备注") String remark) {
        try {
            return Result.content(service.update(id, pricePolicyId, startDate, endDate, remark));
        } catch (IllegalArgumentException e) {
            return Result.failure(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除绑定")
    public Result<Void> delete(@PathVariable Long id) {
        try {
            service.deleteById(id);
            return Result.success();
        } catch (Exception e) {
            return Result.failure(e.getMessage());
        }
    }

    @PutMapping("/{id}/status")
    @Operation(summary = "更新状态")
    public Result<CostPolicyBinding> updateStatus(
            @PathVariable Long id,
            @RequestParam @Parameter(description = "新状态") DataItemStatus status) {
        try {
            return Result.content(service.updateStatus(id, status));
        } catch (IllegalArgumentException e) {
            return Result.failure(e.getMessage());
        }
    }
}
