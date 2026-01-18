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
import com.terra.ems.ems.param.PricePolicyQueryParam;
import com.terra.ems.ems.service.PricePolicyService;
import com.terra.ems.framework.controller.BaseController;
import com.terra.ems.framework.definition.dto.Pager;
import com.terra.ems.framework.enums.DataItemStatus;
import com.terra.ems.framework.service.BaseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 电价策略控制器
 *
 * @author dengxueping
 * @since 2026-01-11
 */
@Tag(name = "电价策略管理")
@RestController
@RequestMapping("/price-policies")
public class PricePolicyController extends BaseController<PricePolicy, Long> {

    private final PricePolicyService pricePolicyService;

    public PricePolicyController(PricePolicyService pricePolicyService) {
        this.pricePolicyService = pricePolicyService;
    }

    @Override
    protected BaseService<PricePolicy, Long> getService() {
        return pricePolicyService;
    }

    /**
     * 分页查询电价策略
     *
     * @param pager 分页参数
     * @param param 查询参数
     * @return 分页结果
     */
    @Operation(summary = "分页查询")
    @GetMapping
    public Result<Map<String, Object>> findByPage(Pager pager, PricePolicyQueryParam param) {
        return findByPage(pager, buildSpecification(param));
    }

    private Specification<PricePolicy> buildSpecification(PricePolicyQueryParam param) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.hasText(param.getName())) {
                predicates.add(cb.like(root.get("name"), "%" + param.getName() + "%"));
            }
            if (StringUtils.hasText(param.getCode())) {
                predicates.add(cb.like(root.get("code"), "%" + param.getCode() + "%"));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    /**
     * 查询所有已启用的电价策略
     *
     * @return 启用的电价策略列表
     */
    @Operation(summary = "查询启用的电价策略")
    @GetMapping("/enabled")
    public Result<List<PricePolicy>> findEnabled() {
        return Result.content(pricePolicyService.findEnabled());
    }

    /**
     * 根据策略编码查询电价策略
     *
     * @param code 策略编码
     * @return 电价策略详情
     */
    @Operation(summary = "根据编码查询电价策略")
    @GetMapping("/code/{code}")
    public Result<PricePolicy> findByCode(@PathVariable String code) {
        return pricePolicyService.findByCode(code)
                .map(Result::content)
                .orElse(Result.failure("电价策略不存在"));
    }

    /**
     * 根据能源类型ID查询关联的电价策略
     *
     * @param energyTypeId 能源类型ID
     * @return 电价策略列表
     */
    @Operation(summary = "根据能源类型ID查询电价策略")
    @GetMapping("/energy-type/{energyTypeId}")
    public Result<List<PricePolicy>> findByEnergyTypeId(@PathVariable Long energyTypeId) {
        return Result.content(pricePolicyService.findByEnergyTypeId(energyTypeId));
    }

    /**
     * 创建电价策略及明细
     *
     * @param payload 包含策略基本信息和明细列表的负载
     * @return 创建后的电价策略实体
     */
    @Operation(summary = "创建电价策略")
    @PostMapping("/create")
    public Result<PricePolicy> create(@RequestBody Map<String, Object> payload) {
        PricePolicy pricePolicy = extractPricePolicy(payload);
        Long energyTypeId = payload.get("energyTypeId") != null
                ? Long.parseLong(payload.get("energyTypeId").toString())
                : 1L; // 默认为电（ID=1）
        List<PricePolicyItem> items = extractItems(payload);
        PricePolicy created = pricePolicyService.create(pricePolicy, energyTypeId, items);
        return Result.content(created);
    }

    /**
     * 更新电价策略及其明细
     *
     * @param id      策略ID
     * @param payload 包含更新信息的负载
     * @return 更新后的电价策略实体
     */
    @Operation(summary = "更新电价策略")
    @PutMapping("/{id}")
    public Result<PricePolicy> update(@PathVariable Long id, @RequestBody Map<String, Object> payload) {
        PricePolicy pricePolicy = extractPricePolicy(payload);
        Long energyTypeId = payload.get("energyTypeId") != null
                ? Long.valueOf(payload.get("energyTypeId").toString())
                : 1L;
        List<PricePolicyItem> items = extractItems(payload);
        PricePolicy updated = pricePolicyService.update(id, pricePolicy, energyTypeId, items);
        return Result.content(updated);
    }

    /**
     * 删除指定的电价策略
     *
     * @param id 策略ID
     * @return 操作结果
     */
    @Operation(summary = "删除电价策略")
    @DeleteMapping("/{id}")
    public Result<String> delete(@PathVariable Long id) {
        pricePolicyService.deleteById(id);
        return Result.success("删除成功");
    }

    /**
     * 快捷更新电价策略状态
     *
     * @param id     策略ID
     * @param status 新状态
     * @return 更新后的实体
     */
    @Operation(summary = "修改电价策略状态")
    @PatchMapping("/{id}/status")
    public Result<PricePolicy> updateStatus(
            @PathVariable Long id,
            @RequestParam DataItemStatus status) {
        PricePolicy updated = pricePolicyService.updateStatus(id, status);
        return Result.content(updated);
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
