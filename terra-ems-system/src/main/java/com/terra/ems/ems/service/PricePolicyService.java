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

import com.terra.ems.ems.entity.PricePolicy;
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

    @Override
    public BaseRepository<PricePolicy, Long> getRepository() {
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
     * 修改状态
     */
    @Transactional(rollbackFor = Exception.class)
    public PricePolicy updateStatus(Long id, DataItemStatus status) {
        PricePolicy existing = pricePolicyRepository.findById(id).orElseThrow();
        existing.setStatus(status);
        return pricePolicyRepository.save(existing);
    }
}
