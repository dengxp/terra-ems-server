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

package com.terra.ems.ems.service;

import com.terra.ems.ems.entity.CostPolicyBinding;
import com.terra.ems.ems.entity.EnergyUnit;
import com.terra.ems.ems.entity.PricePolicy;
import com.terra.ems.ems.repository.CostPolicyBindingRepository;
import com.terra.ems.ems.repository.EnergyUnitRepository;
import com.terra.ems.ems.repository.PricePolicyRepository;
import com.terra.ems.framework.enums.DataItemStatus;
import com.terra.ems.framework.jpa.repository.BaseRepository;
import com.terra.ems.framework.service.BaseService;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * 成本策略绑定服务
 *
 * @author dengxueping
 * @since 2026-01-11
 */

@Service
@RequiredArgsConstructor
public class CostPolicyBindingService extends BaseService<CostPolicyBinding, Long> {

    private final CostPolicyBindingRepository repository;
    private final EnergyUnitRepository energyUnitRepository;
    private final PricePolicyRepository pricePolicyRepository;

    @Override
    public BaseRepository<CostPolicyBinding, Long> getRepository() {
        return repository;
    }

    /**
     * 按用能单元查询绑定
     */
    public List<CostPolicyBinding> findByEnergyUnit(Long energyUnitId) {
        return repository.findByEnergyUnit_IdAndStatusOrderByEffectiveStartDateDesc(energyUnitId,
                DataItemStatus.ENABLE);
    }

    /**
     * 按电价策略查询绑定
     */
    public List<CostPolicyBinding> findByPricePolicy(Long pricePolicyId) {
        return repository.findByPricePolicy_IdAndStatusOrderByCreatedAtDesc(pricePolicyId, DataItemStatus.ENABLE);
    }

    /**
     * 查询用能单元在指定日期有效的绑定
     */
    public Optional<CostPolicyBinding> findEffectiveBinding(Long energyUnitId, LocalDate date) {
        return repository.findEffectiveBinding(energyUnitId, date, DataItemStatus.ENABLE);
    }

    /**
     * 创建绑定
     */
    @Transactional(rollbackFor = Exception.class)
    public CostPolicyBinding create(CostPolicyBinding binding) {
        Long energyUnitId = binding.getEnergyUnit() != null ? binding.getEnergyUnit().getId() : null;
        Long pricePolicyId = binding.getPricePolicy() != null ? binding.getPricePolicy().getId() : null;

        EnergyUnit energyUnit = energyUnitRepository.findById(energyUnitId)
                .orElseThrow(() -> new IllegalArgumentException("用能单元不存在: " + energyUnitId));
        PricePolicy pricePolicy = pricePolicyRepository.findById(pricePolicyId)
                .orElseThrow(() -> new IllegalArgumentException("电价策略不存在: " + pricePolicyId));

        binding.setEnergyUnit(energyUnit);
        binding.setPricePolicy(pricePolicy);
        if (binding.getStatus() == null) {
            binding.setStatus(DataItemStatus.ENABLE);
        }

        return repository.save(binding);
    }

    /**
     * 更新绑定
     */
    @Transactional(rollbackFor = Exception.class)
    public CostPolicyBinding update(Long id, CostPolicyBinding binding) {
        CostPolicyBinding existing = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("绑定记录不存在: " + id));

        Long pricePolicyId = binding.getPricePolicy() != null ? binding.getPricePolicy().getId() : null;
        if (pricePolicyId != null && !pricePolicyId.equals(existing.getPricePolicy().getId())) {
            PricePolicy pricePolicy = pricePolicyRepository.findById(pricePolicyId)
                    .orElseThrow(() -> new IllegalArgumentException("电价策略不存在: " + pricePolicyId));
            existing.setPricePolicy(pricePolicy);
        }

        existing.setEffectiveStartDate(binding.getEffectiveStartDate());
        existing.setEffectiveEndDate(binding.getEffectiveEndDate());
        existing.setRemark(binding.getRemark());

        return repository.save(existing);
    }

    /**
     * 更新状态
     */
    @Transactional(rollbackFor = Exception.class)
    public CostPolicyBinding updateStatus(Long id, DataItemStatus status) {
        CostPolicyBinding existing = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("绑定记录不存在: " + id));
        existing.setStatus(status);
        return repository.save(existing);
    }

    @Override
    public CostPolicyBinding saveOrUpdate(CostPolicyBinding binding) {
        if (binding.getId() == null) {
            return create(binding);
        } else {
            return update(binding.getId(), binding);
        }
    }
}
