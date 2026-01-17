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

import com.terra.ems.ems.entity.Meter;
import com.terra.ems.ems.repository.MeterRepository;
import com.terra.ems.framework.enums.DataItemStatus;
import com.terra.ems.framework.jpa.repository.BaseRepository;
import com.terra.ems.framework.service.BaseService;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 计量器具档案服务
 *
 * @author dengxueping
 * @since 2026-01-11
 */

@Service
@RequiredArgsConstructor
public class MeterService extends BaseService<Meter, Long> {

    private final MeterRepository meterRepository;

    @Override
    public BaseRepository<Meter, Long> getRepository() {
        return meterRepository;
    }

    /**
     * 分页查询
     *
     * @param code     编码
     * @param name     名称
     * @param type     类型
     * @param status   状态
     * @param pageable 分页参数
     * @return 分页结果
     */
    public Page<Meter> findPage(String code, String name, String type, DataItemStatus status, Pageable pageable) {
        Specification<Meter> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (StringUtils.hasText(code)) {
                predicates.add(cb.like(root.get("code"), "%" + code + "%"));
            }
            if (StringUtils.hasText(name)) {
                predicates.add(cb.like(root.get("name"), "%" + name + "%"));
            }
            if (StringUtils.hasText(type)) {
                predicates.add(cb.equal(root.get("type"), type));
            }
            if (status != null) {
                predicates.add(cb.equal(root.get("status"), status));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        return meterRepository.findAll(spec, pageable);
    }

    /**
     * 创建计量器具
     *
     * @param meter 计量器具
     * @return 创建后的计量器具
     */
    @Transactional(rollbackFor = Exception.class)
    public Meter create(Meter meter) {
        if (meterRepository.existsByCode(meter.getCode())) {
            throw new IllegalArgumentException("编码已存在: " + meter.getCode());
        }
        return meterRepository.save(meter);
    }

    /**
     * 更新计量器具
     *
     * @param id    ID
     * @param meter 计量器具信息
     * @return 更新后的计量器具
     */
    @Transactional(rollbackFor = Exception.class)
    public Meter update(Long id, Meter meter) {
        Meter existing = meterRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("计量器具不存在: " + id));

        Optional<Meter> byCode = meterRepository.findByCode(meter.getCode());
        if (byCode.isPresent() && !byCode.get().getId().equals(id)) {
            throw new IllegalArgumentException("编码已被使用: " + meter.getCode());
        }

        existing.setCode(meter.getCode());
        existing.setName(meter.getName());
        existing.setType(meter.getType());
        existing.setEnergyType(meter.getEnergyType());
        existing.setModelNumber(meter.getModelNumber());
        existing.setMeasureRange(meter.getMeasureRange());
        existing.setManufacturer(meter.getManufacturer());
        existing.setPersonCharge(meter.getPersonCharge());
        existing.setLocation(meter.getLocation());
        existing.setStartTime(meter.getStartTime());
        existing.setPutrunTime(meter.getPutrunTime());
        existing.setCheckCycle(meter.getCheckCycle());
        existing.setReminderCycle(meter.getReminderCycle());
        existing.setMaxPower(meter.getMaxPower());
        existing.setWireDiameter(meter.getWireDiameter());
        existing.setGatewayId(meter.getGatewayId());
        existing.setStatus(meter.getStatus());
        existing.setRemark(meter.getRemark());

        return meterRepository.save(existing);
    }

    /**
     * 删除计量器具
     *
     * @param id ID
     */
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        if (!meterRepository.existsById(id)) {
            throw new IllegalArgumentException("计量器具不存在: " + id);
        }
        meterRepository.deleteById(id);
    }

    /**
     * 批量删除计量器具
     *
     * @param ids ID列表
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteBatch(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return;
        }
        meterRepository.deleteAllById(ids);
    }
}
