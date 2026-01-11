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
import com.terra.ems.ems.entity.PricePolicy;
import com.terra.ems.ems.entity.PricePolicyItem;
import com.terra.ems.ems.service.PricePolicyService;
import com.terra.ems.framework.controller.Controller;
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
import java.util.Map;

/**
 * Name: PricePolicyController.java
 * Email: dengxueping@gmail.com
 * Date: 2026-01-10
 * Description:
 * 电价策略控制器
 *
 * @author dengxueping
 */
@Tag(name = "电价策略管理")
@RestController
@RequestMapping("/price-policies")
@RequiredArgsConstructor
public class PricePolicyController extends Controller {

    private final PricePolicyService pricePolicyService;

    @Operation(summary = "分页查询电价策略")
    @GetMapping
    public Result<Page<PricePolicy>> list(
            @Parameter(description = "页码") @RequestParam(defaultValue = "1") int current,
            @Parameter(description = "每页数量") @RequestParam(defaultValue = "10") int pageSize,
            @Parameter(description = "排序字段") @RequestParam(defaultValue = "sortOrder") String sortField,
            @Parameter(description = "排序方向") @RequestParam(defaultValue = "asc") String sortOrder) {

        Sort sort = sortOrder.equalsIgnoreCase("desc")
                ? Sort.by(sortField).descending()
                : Sort.by(sortField).ascending();
        Pageable pageable = PageRequest.of(current - 1, pageSize, sort);
        return Result.content(pricePolicyService.findAll(pageable));
    }

    @Operation(summary = "查询所有电价策略")
    @GetMapping("/all")
    public Result<List<PricePolicy>> listAll() {
        return Result.content(pricePolicyService.findAll());
    }

    @Operation(summary = "查询启用的电价策略")
    @GetMapping("/enabled")
    public Result<List<PricePolicy>> listEnabled() {
        return Result.content(pricePolicyService.findEnabled());
    }

    @Operation(summary = "根据ID查询电价策略")
    @GetMapping("/{id}")
    public Result<PricePolicy> getById(@PathVariable Long id) {
        return java.util.Optional.ofNullable(pricePolicyService.findById(id))
                .map(Result::content)
                .orElse(Result.failure("电价策略不存在"));
    }

    @Operation(summary = "根据编码查询电价策略")
    @GetMapping("/code/{code}")
    public Result<PricePolicy> getByCode(@PathVariable String code) {
        return pricePolicyService.findByCode(code)
                .map(Result::content)
                .orElse(Result.failure("电价策略不存在"));
    }

    @Operation(summary = "根据能源类型ID查询电价策略")
    @GetMapping("/energy-type/{energyTypeId}")
    public Result<List<PricePolicy>> getByEnergyTypeId(@PathVariable Long energyTypeId) {
        return Result.content(pricePolicyService.findByEnergyTypeId(energyTypeId));
    }

    @Operation(summary = "创建电价策略")
    @PostMapping
    public Result<PricePolicy> create(@RequestBody Map<String, Object> payload) {
        try {
            PricePolicy pricePolicy = extractPricePolicy(payload);
            Long energyTypeId = payload.get("energyTypeId") != null
                    ? Long.valueOf(payload.get("energyTypeId").toString())
                    : 1L; // 默认为电（ID=1）
            List<PricePolicyItem> items = extractItems(payload);
            PricePolicy created = pricePolicyService.create(pricePolicy, energyTypeId, items);
            return Result.content(created);
        } catch (IllegalArgumentException e) {
            return Result.failure(e.getMessage());
        }
    }

    @Operation(summary = "更新电价策略")
    @PutMapping("/{id}")
    public Result<PricePolicy> update(@PathVariable Long id, @RequestBody Map<String, Object> payload) {
        try {
            PricePolicy pricePolicy = extractPricePolicy(payload);
            Long energyTypeId = payload.get("energyTypeId") != null
                    ? Long.valueOf(payload.get("energyTypeId").toString())
                    : 1L;
            List<PricePolicyItem> items = extractItems(payload);
            PricePolicy updated = pricePolicyService.update(id, pricePolicy, energyTypeId, items);
            return Result.content(updated);
        } catch (IllegalArgumentException e) {
            return Result.failure(e.getMessage());
        }
    }

    @Operation(summary = "删除电价策略")
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        try {
            pricePolicyService.delete(id);
            return Result.success();
        } catch (IllegalArgumentException e) {
            return Result.failure(e.getMessage());
        }
    }

    @Operation(summary = "修改电价策略状态")
    @PatchMapping("/{id}/status")
    public Result<PricePolicy> updateStatus(
            @PathVariable Long id,
            @RequestParam DataItemStatus status) {
        try {
            PricePolicy updated = pricePolicyService.updateStatus(id, status);
            return Result.content(updated);
        } catch (IllegalArgumentException e) {
            return Result.failure(e.getMessage());
        }
    }

    /**
     * 从请求体提取策略基本信息
     */
    private PricePolicy extractPricePolicy(Map<String, Object> payload) {
        PricePolicy policy = new PricePolicy();
        policy.setCode((String) payload.get("code"));
        policy.setName((String) payload.get("name"));
        policy.setIsMultiRate(payload.get("isMultiRate") != null
                ? (Boolean) payload.get("isMultiRate")
                : true);
        policy.setSortOrder(payload.get("sortOrder") != null
                ? Integer.valueOf(payload.get("sortOrder").toString())
                : 0);
        if (payload.get("status") != null) {
            int statusValue = Integer.parseInt(payload.get("status").toString());
            policy.setStatus(statusValue == 0 ? DataItemStatus.ENABLE : DataItemStatus.FORBIDDEN);
        }
        policy.setRemark((String) payload.get("remark"));
        return policy;
    }

    /**
     * 从请求体提取明细列表
     */
    @SuppressWarnings("unchecked")
    private List<PricePolicyItem> extractItems(Map<String, Object> payload) {
        Object itemsObj = payload.get("items");
        if (itemsObj == null) {
            return List.of();
        }
        List<Map<String, Object>> itemMaps = (List<Map<String, Object>>) itemsObj;
        return itemMaps.stream().map(m -> {
            PricePolicyItem item = new PricePolicyItem();
            if (m.get("periodType") != null) {
                item.setPeriodType(com.terra.ems.ems.enums.PeriodType.valueOf(m.get("periodType").toString()));
            }
            if (m.get("price") != null) {
                item.setPrice(new java.math.BigDecimal(m.get("price").toString()));
            }
            if (m.get("sortOrder") != null) {
                item.setSortOrder(Integer.valueOf(m.get("sortOrder").toString()));
            }
            item.setStartTime((String) m.get("startTime"));
            item.setEndTime((String) m.get("endTime"));
            item.setRemark((String) m.get("remark"));
            return item;
        }).toList();
    }
}
