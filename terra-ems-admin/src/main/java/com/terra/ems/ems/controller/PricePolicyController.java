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
import com.terra.ems.ems.param.PricePolicyQueryParam;
import com.terra.ems.ems.service.PricePolicyService;
import com.terra.ems.framework.controller.BaseController;
import com.terra.ems.framework.definition.dto.Pager;
import com.terra.ems.framework.enums.DataItemStatus;
import com.terra.ems.framework.service.BaseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.terra.ems.common.annotation.Log;
import com.terra.ems.common.enums.BusinessType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 电价策略控制器
 * 
 * 继承 BaseController 自动获得：
 * - POST / → saveOrUpdate (新增/更新)
 * - DELETE /{id} → delete (单个删除)
 * - DELETE / → deleteBatch (批量删除)
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
            if (param.getStatus() != null) {
                DataItemStatus statusEnum = param.getStatus() == 0 ? DataItemStatus.ENABLE : DataItemStatus.FORBIDDEN;
                predicates.add(cb.equal(root.get("status"), statusEnum));
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
     * 快捷更新电价策略状态
     *
     * @param id     策略ID
     * @param status 新状态
     * @return 更新后的实体
     */
    @Operation(summary = "修改电价策略状态")
    @Log(title = "价格策略", businessType = BusinessType.UPDATE)
    @PatchMapping("/{id}/status")
    public Result<PricePolicy> updateStatus(
            @PathVariable Long id,
            @RequestParam DataItemStatus status) {
        PricePolicy updated = pricePolicyService.updateStatus(id, status);
        return Result.content(updated);
    }

    @Log(title = "价格策略", businessType = BusinessType.UPDATE)
    @Override
    public Result<PricePolicy> saveOrUpdate(@Validated @RequestBody PricePolicy domain) {
        return super.saveOrUpdate(domain);
    }

    @Log(title = "价格策略", businessType = BusinessType.DELETE)
    @Override
    public Result<String> delete(@PathVariable Long id) {
        return super.delete(id);
    }

    @Log(title = "价格策略", businessType = BusinessType.DELETE)
    @Override
    public Result<String> deleteBatch(@RequestBody List<Long> ids) {
        return super.deleteBatch(ids);
    }
}
