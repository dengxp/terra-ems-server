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
import com.terra.ems.ems.entity.TimePeriodPrice;
import com.terra.ems.ems.enums.TimePeriodType;
import com.terra.ems.ems.service.TimePeriodPriceService;
import com.terra.ems.framework.controller.Controller;
import com.terra.ems.framework.enums.DataItemStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

/**
 * Name: TimePeriodPriceController.java
 * Email: dengxueping@gmail.com
 * Date: 2026-01-10
 * Description:
 * 分时电价配置控制器
 *
 * @author dengxueping
 */
@Tag(name = "分时电价配置管理")
@RestController
@RequestMapping("/time-period-prices")
@RequiredArgsConstructor
public class TimePeriodPriceController extends Controller {

    private final TimePeriodPriceService timePeriodPriceService;

    @Operation(summary = "查询所有分时电价配置")
    @GetMapping
    public Result<List<TimePeriodPrice>> list() {
        return Result.content(timePeriodPriceService.findAll());
    }

    @Operation(summary = "根据电价政策ID查询")
    @GetMapping("/policy/{pricePolicyId}")
    public Result<List<TimePeriodPrice>> getByPricePolicyId(@PathVariable Long pricePolicyId) {
        return Result.content(timePeriodPriceService.findByPricePolicyId(pricePolicyId));
    }

    @Operation(summary = "根据时段类型查询")
    @GetMapping("/period-type/{periodType}")
    public Result<List<TimePeriodPrice>> getByPeriodType(@PathVariable TimePeriodType periodType) {
        return Result.content(timePeriodPriceService.findByPeriodType(periodType));
    }

    @Operation(summary = "根据时间点查询当前时段")
    @GetMapping("/current")
    public Result<TimePeriodPrice> getCurrentPeriod(
            @Parameter(description = "时间点，格式 HH:mm:ss") @RequestParam(required = false) String time) {
        LocalTime queryTime = time != null ? LocalTime.parse(time) : LocalTime.now();
        return timePeriodPriceService.findByTimePoint(queryTime)
                .map(Result::content)
                .orElse(Result.failure("未找到对应的时段配置"));
    }

    @Operation(summary = "根据ID查询")
    @GetMapping("/{id}")
    public Result<TimePeriodPrice> getById(@PathVariable Long id) {
        TimePeriodPrice price = timePeriodPriceService.findById(id);
        return price != null ? Result.content(price) : Result.failure("分时电价配置不存在");
    }

    @Operation(summary = "创建分时电价配置")
    @PostMapping
    public Result<TimePeriodPrice> create(@RequestBody Map<String, Object> payload) {
        try {
            TimePeriodPrice timePeriodPrice = extractTimePeriodPrice(payload);
            TimePeriodPrice created = timePeriodPriceService.create(timePeriodPrice);
            return Result.content(created);
        } catch (Exception e) {
            return Result.failure(e.getMessage());
        }
    }

    @Operation(summary = "更新分时电价配置")
    @PutMapping("/{id}")
    public Result<TimePeriodPrice> update(@PathVariable Long id, @RequestBody Map<String, Object> payload) {
        try {
            TimePeriodPrice timePeriodPrice = extractTimePeriodPrice(payload);
            TimePeriodPrice updated = timePeriodPriceService.update(id, timePeriodPrice);
            return Result.content(updated);
        } catch (IllegalArgumentException e) {
            return Result.failure(e.getMessage());
        }
    }

    @Operation(summary = "删除分时电价配置")
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        try {
            timePeriodPriceService.delete(id);
            return Result.success();
        } catch (IllegalArgumentException e) {
            return Result.failure(e.getMessage());
        }
    }

    @Operation(summary = "修改状态")
    @PatchMapping("/{id}/status")
    public Result<TimePeriodPrice> updateStatus(
            @PathVariable Long id,
            @RequestParam DataItemStatus status) {
        try {
            TimePeriodPrice updated = timePeriodPriceService.updateStatus(id, status);
            return Result.content(updated);
        } catch (IllegalArgumentException e) {
            return Result.failure(e.getMessage());
        }
    }

    /**
     * 从请求体提取分时电价配置
     */
    private TimePeriodPrice extractTimePeriodPrice(Map<String, Object> payload) {
        TimePeriodPrice price = new TimePeriodPrice();
        if (payload.get("pricePolicyId") != null) {
            price.setPricePolicyId(Long.valueOf(payload.get("pricePolicyId").toString()));
        }
        if (payload.get("periodType") != null) {
            price.setPeriodType(TimePeriodType.valueOf(payload.get("periodType").toString()));
        }
        price.setPeriodName((String) payload.get("periodName"));
        if (payload.get("startTime") != null) {
            price.setStartTime(LocalTime.parse(payload.get("startTime").toString()));
        }
        if (payload.get("endTime") != null) {
            price.setEndTime(LocalTime.parse(payload.get("endTime").toString()));
        }
        if (payload.get("price") != null) {
            price.setPrice(new BigDecimal(payload.get("price").toString()));
        }
        if (payload.get("sortOrder") != null) {
            price.setSortOrder(Integer.valueOf(payload.get("sortOrder").toString()));
        }
        if (payload.get("status") != null) {
            int statusValue = Integer.parseInt(payload.get("status").toString());
            price.setStatus(statusValue == 0 ? DataItemStatus.ENABLE : DataItemStatus.FORBIDDEN);
        }
        price.setRemark((String) payload.get("remark"));
        return price;
    }
}
