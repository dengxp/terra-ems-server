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
import com.terra.ems.framework.controller.BaseController;
import com.terra.ems.framework.service.BaseService;
import com.terra.ems.framework.enums.DataItemStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import com.terra.ems.common.annotation.Log;
import com.terra.ems.common.enums.BusinessType;
import org.springframework.security.access.prepost.PreAuthorize;

/**
 * 分时电价配置控制器
 *
 * @author dengxueping
 * @since 2026-01-11
 */
@Tag(name = "分时电价配置管理")
@RestController
@RequestMapping("/time-period-prices")
public class TimePeriodPriceController extends BaseController<TimePeriodPrice, Long> {

    private final TimePeriodPriceService timePeriodPriceService;

    public TimePeriodPriceController(TimePeriodPriceService timePeriodPriceService) {
        this.timePeriodPriceService = timePeriodPriceService;
    }

    @Override
    protected BaseService<TimePeriodPrice, Long> getService() {
        return timePeriodPriceService;
    }

    /**
     * 根据电价政策ID查询关联的分时电价配置
     *
     * @param pricePolicyId 电价政策ID
     * @return 分时电价配置列表
     */
    @Operation(summary = "根据电价政策ID查询")
    @GetMapping("/policy/{pricePolicyId}")
    public Result<List<TimePeriodPrice>> findByPricePolicyId(@PathVariable Long pricePolicyId) {
        return Result.content(timePeriodPriceService.findByPricePolicyId(pricePolicyId));
    }

    /**
     * 根据时段类型查询分时电价配置
     *
     * @param periodType 时段类型 (PEAK/VALLEY 等)
     * @return 分时电价配置列表
     */
    @Operation(summary = "根据时段类型查询")
    @GetMapping("/period-type/{periodType}")
    public Result<List<TimePeriodPrice>> findByPeriodType(@PathVariable TimePeriodType periodType) {
        return Result.content(timePeriodPriceService.findByPeriodType(periodType));
    }

    /**
     * 根据特定时间点查询所属的时段配置
     *
     * @param time 时间点 (HH:mm:ss)，为空则使用当前系统时间
     * @return 匹配的时间段配置
     */
    @Operation(summary = "根据时间点查询当前时段")
    @GetMapping("/current")
    public Result<TimePeriodPrice> findCurrentPeriod(
            @Parameter(description = "时间点，格式 HH:mm:ss") @RequestParam(required = false) String time) {
        LocalTime queryTime = time != null ? LocalTime.parse(time) : LocalTime.now();
        return timePeriodPriceService.findByTimePoint(queryTime)
                .map(Result::content)
                .orElse(Result.failure("未找到对应的时段配置"));
    }

    /**
     * 创建或更新分时电价配置
     *
     * @param payload 配置内容负载
     * @return 保存后的实体
     */
    @Operation(summary = "创建或更新分时电价配置")
    @Log(title = "分时电价", businessType = BusinessType.INSERT)
    @PreAuthorize("hasAnyPerm('ems:time-period-price:add', 'ems:time-period-price:edit')")
    @PostMapping("/create")
    public Result<TimePeriodPrice> create(@RequestBody Map<String, Object> payload) {
        TimePeriodPrice timePeriodPrice = extractTimePeriodPrice(payload);
        return Result.content(timePeriodPriceService.saveOrUpdate(timePeriodPrice));
    }

    /**
     * 更新现有的分时电价配置
     *
     * @param id     配置ID
     * @param entity 分时电价配置实体
     * @return 更新后的实体
     */
    @Operation(summary = "更新分时电价配置")
    @Log(title = "分时电价", businessType = BusinessType.UPDATE)
    @PreAuthorize("hasPerm('ems:time-period-price:edit')")
    @PutMapping("/{id}")
    @Override
    public Result<TimePeriodPrice> update(@PathVariable Long id, @RequestBody TimePeriodPrice entity) {
        entity.setId(id);
        return Result.content(timePeriodPriceService.saveOrUpdate(entity));
    }

    /**
     * 删除指定的分时电价配置
     *
     * @param id 配置ID
     * @return 操作结果
     */
    @Log(title = "分时电价", businessType = BusinessType.DELETE)
    @PreAuthorize("hasPerm('ems:time-period-price:remove')")
    @Operation(summary = "删除分时电价配置")
    @DeleteMapping("/{id}")
    public Result<String> delete(@PathVariable Long id) {
        timePeriodPriceService.deleteById(id);
        return Result.success("删除成功");
    }

    /**
     * 快捷更新分时电价配置的状态
     *
     * @param id     配置ID
     * @param status 新状态
     * @return 更新后的实体
     */
    @Operation(summary = "修改状态")
    @Log(title = "分时电价", businessType = BusinessType.UPDATE)
    @PreAuthorize("hasPerm('ems:time-period-price:edit')")
    @PatchMapping("/{id}/status")
    public Result<TimePeriodPrice> updateStatus(
            @PathVariable Long id,
            @RequestParam DataItemStatus status) {
        TimePeriodPrice updated = timePeriodPriceService.updateStatus(id, status);
        return Result.content(updated);
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
