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

import com.terra.ems.ems.entity.EnergyType;
import com.terra.ems.ems.entity.PricePolicy;
import com.terra.ems.ems.entity.PricePolicyItem;
import com.terra.ems.ems.repository.EnergyTypeRepository;
import com.terra.ems.ems.repository.PricePolicyRepository;
import com.terra.ems.framework.enums.DataItemStatus;
import com.terra.ems.framework.jpa.repository.BaseRepository;
import com.terra.ems.framework.service.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * 电价策略服务
 *
 * @author dengxueping
 * @since 2026-01-11
 */

@Service
@RequiredArgsConstructor
public class PricePolicyService extends BaseService<PricePolicy, Long> {

    private final PricePolicyRepository pricePolicyRepository;
    private final EnergyTypeRepository energyTypeRepository;

    @Override
    protected BaseRepository<PricePolicy, Long> getRepository() {
        return pricePolicyRepository;
    }

    /**
     * 分页查询
     */
    public Page<PricePolicy> findAll(Pageable pageable) {
        return pricePolicyRepository.findAll(pageable);
    }

    /**
     * 查询所有
     */
    public List<PricePolicy> findAll() {
        return pricePolicyRepository.findAllByOrderBySortOrderAsc();
    }

    /**
     * 根据编码查询
     */
    public Optional<PricePolicy> findByCode(String code) {
        return pricePolicyRepository.findByCode(code);
    }

    /**
     * 查询启用的策略
     */
    public List<PricePolicy> findEnabled() {
        return pricePolicyRepository.findByStatusOrderBySortOrderAsc(DataItemStatus.ENABLE);
    }

    /**
     * 根据能源类型ID查询
     */
    public List<PricePolicy> findByEnergyTypeId(Long energyTypeId) {
        return pricePolicyRepository.findByEnergyType_IdOrderBySortOrderAsc(energyTypeId);
    }

    /**
     * 创建策略（含明细）
     */
    @Transactional(rollbackFor = Exception.class)
    public PricePolicy create(PricePolicy pricePolicy, Long energyTypeId, List<PricePolicyItem> items) {
        // 检查编码是否重复
        if (pricePolicyRepository.existsByCode(pricePolicy.getCode())) {
            throw new IllegalArgumentException("编码已存在: " + pricePolicy.getCode());
        }

        // 设置关联的能源类型
        if (energyTypeId != null) {
            EnergyType energyType = energyTypeRepository.findById(energyTypeId)
                    .orElseThrow(() -> new IllegalArgumentException("能源类型不存在: " + energyTypeId));
            pricePolicy.setEnergyType(energyType);
        }

        // 设置明细
        if (items != null && !items.isEmpty()) {
            for (PricePolicyItem item : items) {
                pricePolicy.addItem(item);
            }
        }

        return pricePolicyRepository.save(pricePolicy);
    }

    /**
     * 更新策略（含明细）
     */
    @Transactional(rollbackFor = Exception.class)
    public PricePolicy update(Long id, PricePolicy pricePolicy, Long energyTypeId, List<PricePolicyItem> items) {
        PricePolicy existing = pricePolicyRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("电价策略不存在: " + id));

        // 检查编码是否被其他记录使用
        Optional<PricePolicy> byCode = pricePolicyRepository.findByCode(pricePolicy.getCode());
        if (byCode.isPresent() && !byCode.get().getId().equals(id)) {
            throw new IllegalArgumentException("编码已被使用: " + pricePolicy.getCode());
        }

        // 更新基本信息
        existing.setCode(pricePolicy.getCode());
        existing.setName(pricePolicy.getName());
        existing.setIsMultiRate(pricePolicy.getIsMultiRate());
        existing.setSortOrder(pricePolicy.getSortOrder());
        existing.setStatus(pricePolicy.getStatus());
        existing.setRemark(pricePolicy.getRemark());

        // 更新关联的能源类型
        if (energyTypeId != null) {
            EnergyType energyType = energyTypeRepository.findById(energyTypeId)
                    .orElseThrow(() -> new IllegalArgumentException("能源类型不存在: " + energyTypeId));
            existing.setEnergyType(energyType);
        } else {
            existing.setEnergyType(null);
        }

        // 更新明细（先清空再添加）
        existing.getItems().clear();
        if (items != null && !items.isEmpty()) {
            for (PricePolicyItem item : items) {
                existing.addItem(item);
            }
        }

        return pricePolicyRepository.save(existing);
    }

    /**
     * 删除策略
     */
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        if (!pricePolicyRepository.existsById(id)) {
            throw new IllegalArgumentException("电价策略不存在: " + id);
        }
        pricePolicyRepository.deleteById(id);
    }

    /**
     * 修改状态
     */
    @Transactional(rollbackFor = Exception.class)
    public PricePolicy updateStatus(Long id, DataItemStatus status) {
        PricePolicy existing = pricePolicyRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("电价策略不存在: " + id));
        existing.setStatus(status);
        return pricePolicyRepository.save(existing);
    }
}
