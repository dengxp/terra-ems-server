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
import com.terra.ems.framework.controller.BaseController;
import com.terra.ems.framework.service.BaseService;
import com.terra.ems.framework.enums.DataItemStatus;
import java.util.Optional;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

import com.terra.ems.framework.definition.dto.Pager;
import org.springframework.validation.annotation.Validated;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

/**
 * 成本策略绑定控制器
 *
 * @author dengxueping
 * @since 2026-01-11
 */
@RestController
@RequestMapping("/ems/cost-policy-bindings")
@Tag(name = "成本策略绑定管理")
public class CostPolicyBindingController extends BaseController<CostPolicyBinding, Long> {

    private final CostPolicyBindingService costPolicyBindingService;

    public CostPolicyBindingController(CostPolicyBindingService costPolicyBindingService) {
        this.costPolicyBindingService = costPolicyBindingService;
    }

    @Override
    protected BaseService<CostPolicyBinding, Long> getService() {
        return costPolicyBindingService;
    }

    @Override
    protected Specification<CostPolicyBinding> buildSpecification(Map<String, Object> params) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (params.containsKey("energyUnitId") && params.get("energyUnitId") != null) {
                predicates.add(cb.equal(root.get("energyUnitId"), Long.valueOf(params.get("energyUnitId").toString())));
            }
            if (params.containsKey("pricePolicyId") && params.get("pricePolicyId") != null) {
                predicates
                        .add(cb.equal(root.get("pricePolicyId"), Long.valueOf(params.get("pricePolicyId").toString())));
            }
            if (params.containsKey("status") && params.get("status") != null) {
                predicates.add(cb.equal(root.get("status"), DataItemStatus.valueOf(params.get("status").toString())));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    /**
     * 分页查询成本策略绑定
     *
     * @param pager  分页参数
     * @param params 查询参数
     * @return 分页结果
     */
    @GetMapping
    @Operation(summary = "分页查询")
    public Result<Map<String, Object>> findByPage(Pager pager, @RequestParam Map<String, Object> params) {
        return findByPage(pager, buildSpecification(params));
    }

    /**
     * 根据ID查询绑定详情
     *
     * @param id 绑定ID
     * @return 绑定记录详情
     */
    @GetMapping("/{id}")
    @Operation(summary = "根据ID查询")
    public Result<CostPolicyBinding> findById(@PathVariable Long id) {
        return Optional.ofNullable(costPolicyBindingService.findById(id))
                .map(Result::content)
                .orElse(Result.failure("绑定记录不存在"));
    }

    /**
     * 按用能单元查询绑定列表
     *
     * @param energyUnitId 用能单元ID
     * @return 绑定列表
     */
    @GetMapping("/energy-unit/{energyUnitId}")
    @Operation(summary = "按用能单元查询")
    public Result<List<CostPolicyBinding>> findByEnergyUnit(
            @PathVariable @Parameter(description = "用能单元ID") Long energyUnitId) {
        return Result.content(costPolicyBindingService.findByEnergyUnit(energyUnitId));
    }

    /**
     * 按电价策略查询绑定列表
     *
     * @param pricePolicyId 电价策略ID
     * @return 绑定列表
     */
    @GetMapping("/price-policy/{pricePolicyId}")
    @Operation(summary = "按电价策略查询")
    public Result<List<CostPolicyBinding>> findByPricePolicy(
            @PathVariable @Parameter(description = "电价策略ID") Long pricePolicyId) {
        return Result.content(costPolicyBindingService.findByPricePolicy(pricePolicyId));
    }

    /**
     * 查询指定用能单元在特定日期的有效绑定
     *
     * @param energyUnitId 用能单元ID
     * @param date         查询日期
     * @return 有效绑定记录
     */
    @GetMapping("/effective")
    @Operation(summary = "查询有效绑定")
    public Result<CostPolicyBinding> findEffectiveBinding(
            @RequestParam @Parameter(description = "用能单元ID") Long energyUnitId,
            @RequestParam(required = false) @Parameter(description = "日期") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        LocalDate queryDate = date != null ? date : LocalDate.now();
        return costPolicyBindingService.findEffectiveBinding(energyUnitId, queryDate)
                .map(Result::content)
                .orElse(Result.failure("未找到有效绑定"));
    }

    /**
     * 删除绑定记录
     *
     * @param id 绑定ID
     * @return 操作结果
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "删除绑定关系")
    public Result<String> delete(@PathVariable Long id) {
        costPolicyBindingService.deleteById(id);
        return Result.success("删除成功");
    }

    /**
     * 更新绑定记录状态
     *
     * @param id     绑定ID
     * @param status 新状态
     * @return 更新后的实体
     */
    @PutMapping("/{id}/status")
    @Operation(summary = "更新状态")
    public Result<CostPolicyBinding> updateStatus(
            @PathVariable Long id,
            @RequestParam @Parameter(description = "新状态") DataItemStatus status) {
        return Result.content(costPolicyBindingService.updateStatus(id, status));
    }

}
