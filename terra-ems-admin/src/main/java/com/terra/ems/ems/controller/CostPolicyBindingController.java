/*
 * Copyright (c) 2025-2026 Terra Technology (Guangzhou) Co., Ltd.
 * Copyright (c) 2025-2026 泰若科技（广州）有限公司.
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
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

import com.terra.ems.framework.definition.dto.Pager;

import java.util.ArrayList;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import com.terra.ems.common.annotation.Log;
import com.terra.ems.common.enums.BusinessType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;

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

    /**
     * 保存或更新成本策略绑定
     */
    @Operation(summary = "保存或更新成本策略绑定")
    @PostMapping
    @PutMapping
    @Override
    @Log(title = "费用策略绑定", businessType = BusinessType.UPDATE)
    @PreAuthorize("hasAnyPerm('ems:cost-policy-binding:add', 'ems:cost-policy-binding:edit')")
    public Result<CostPolicyBinding> saveOrUpdate(@Validated @RequestBody CostPolicyBinding binding) {
        return super.saveOrUpdate(binding);
    }

    /**
     * 删除成本策略绑定
     */
    @Operation(summary = "删除数据")
    @PreAuthorize("hasPerm('ems:cost-policy-binding:remove')")
    @Log(title = "费用策略绑定", businessType = BusinessType.DELETE)
    @DeleteMapping("/{id}")
    @Override
    public Result<String> delete(@PathVariable Long id) {
        return super.delete(id);
    }

    /**
     * 批量删除成本策略绑定
     */
    @Operation(summary = "批量删除数据")
    @PreAuthorize("hasPerm('ems:cost-policy-binding:remove')")
    @Log(title = "费用策略绑定", businessType = BusinessType.DELETE)
    @DeleteMapping
    @Override
    public Result<String> deleteBatch(@RequestBody List<Long> ids) {
        return super.deleteBatch(ids);
    }

    /**
     * 分页查询成本策略绑定
     *
     * @param pager         分页参数
     * @param energyUnitId  用能单元ID
     * @param pricePolicyId 电价策略ID
     * @param status        状态
     * @return 分页结果
     */
    @GetMapping
    @Operation(summary = "分页查询")
    @PreAuthorize("hasPerm('ems:cost-policy-binding:list')")
    public Result<Page<CostPolicyBinding>> findByPage(
            Pager pager,
            @RequestParam(required = false) @Parameter(description = "用能单元ID") Long energyUnitId,
            @RequestParam(required = false) @Parameter(description = "电价策略ID") Long pricePolicyId,
            @RequestParam(required = false) @Parameter(description = "状态") DataItemStatus status) {
        Page<CostPolicyBinding> page = costPolicyBindingService.findByPage(
                buildSpecification(energyUnitId, pricePolicyId, status), pager.getPageable());
        return Result.content(page);
    }

    /**
     * 构建查询规范
     */
    private Specification<CostPolicyBinding> buildSpecification(Long energyUnitId, Long pricePolicyId,
            DataItemStatus status) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (energyUnitId != null) {
                predicates.add(cb.equal(root.get("energyUnit").get("id"), energyUnitId));
            }
            if (pricePolicyId != null) {
                predicates.add(cb.equal(root.get("pricePolicy").get("id"), pricePolicyId));
            }
            if (status != null) {
                predicates.add(cb.equal(root.get("status"), status));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
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
     * 更新绑定记录状态
     *
     * @param id     绑定ID
     * @param status 新状态
     * @return 更新后的实体
     */
    @Log(title = "费用策略绑定", businessType = BusinessType.UPDATE)
    @PutMapping("/{id}/status")
    @Operation(summary = "更新状态")
    public Result<CostPolicyBinding> updateStatus(
            @PathVariable Long id,
            @RequestParam @Parameter(description = "新状态") DataItemStatus status) {
        return Result.content(costPolicyBindingService.updateStatus(id, status));
    }

}
