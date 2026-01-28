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

import com.terra.ems.ems.entity.Benchmark;
import com.terra.ems.ems.enums.BenchmarkType;
import com.terra.ems.ems.repository.BenchmarkRepository;
import com.terra.ems.framework.enums.DataItemStatus;
import com.terra.ems.framework.jpa.repository.BaseRepository;
import com.terra.ems.framework.service.BaseService;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * 对标值服务
 *
 * @author dengxueping
 * @since 2026-01-11
 */

@Service
@RequiredArgsConstructor
public class BenchmarkService extends BaseService<Benchmark, Long> {

    private final BenchmarkRepository repository;

    @Override
    public BaseRepository<Benchmark, Long> getRepository() {
        return repository;
    }

    /**
     * 根据编码查询
     */
    public Optional<Benchmark> findByCode(String code) {
        return repository.findByCode(code);
    }

    /**
     * 检查编码是否存在
     */
    public boolean existsByCode(String code) {
        return repository.existsByCode(code);
    }

    /**
     * 按类型查询启用的对标值
     */
    public List<Benchmark> findByType(BenchmarkType type) {
        return repository.findByTypeAndStatusOrderByCodeAsc(type, DataItemStatus.ENABLE);
    }

    /**
     * 查询所有启用的对标值
     */
    public List<Benchmark> findAllEnabled() {
        return repository.findByStatusOrderByTypeAscCodeAsc(DataItemStatus.ENABLE);
    }

    /**
     * 按能源类型查询
     */
    public List<Benchmark> findByEnergyType(Long energyTypeId) {
        return repository.findByEnergyTypeIdAndStatusOrderByCodeAsc(energyTypeId, DataItemStatus.ENABLE);
    }

    /**
     * 按类型统计数量
     */
    public long countByType(BenchmarkType type) {
        return repository.countByTypeAndStatus(type, DataItemStatus.ENABLE);
    }

    /**
     * 创建或更新对标值
     * 统一处理唯一性校验和字段更新
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Benchmark saveOrUpdate(Benchmark benchmark) {
        if (benchmark.getId() == null) {
            // 新建：检查编码唯一性
            if (existsByCode(benchmark.getCode())) {
                throw new IllegalArgumentException("标杆编码已存在: " + benchmark.getCode());
            }
            if (benchmark.getStatus() == null) {
                benchmark.setStatus(DataItemStatus.ENABLE);
            }
            return repository.save(benchmark);
        } else {
            // 更新：检查编码是否被其他记录占用
            Benchmark existing = repository.findById(benchmark.getId())
                    .orElseThrow(() -> new IllegalArgumentException("对标值不存在: " + benchmark.getId()));

            if (!existing.getCode().equals(benchmark.getCode()) && existsByCode(benchmark.getCode())) {
                throw new IllegalArgumentException("标杆编码已存在: " + benchmark.getCode());
            }

            existing.setCode(benchmark.getCode());
            existing.setName(benchmark.getName());
            existing.setType(benchmark.getType());
            existing.setGrade(benchmark.getGrade());
            existing.setValue(benchmark.getValue());
            existing.setUnit(benchmark.getUnit());
            existing.setNationalNum(benchmark.getNationalNum());
            existing.setEnergyType(benchmark.getEnergyType());
            existing.setStatus(benchmark.getStatus());
            existing.setRemark(benchmark.getRemark());

            return repository.save(existing);
        }
    }

    /**
     * 创建对标值 (保留向后兼容)
     */
    @Transactional(rollbackFor = Exception.class)
    public Benchmark create(Benchmark benchmark) {
        benchmark.setId(null); // 确保是新建
        return saveOrUpdate(benchmark);
    }

    /**
     * 更新对标值 (保留向后兼容)
     */
    @Transactional(rollbackFor = Exception.class)
    public Benchmark update(Long id, Benchmark benchmark) {
        benchmark.setId(id); // 确保是更新
        return saveOrUpdate(benchmark);
    }

    /**
     * 更新状态
     */
    @Transactional(rollbackFor = Exception.class)
    public Benchmark updateStatus(Long id, DataItemStatus status) {
        Benchmark existing = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("对标值不存在: " + id));
        existing.setStatus(status);
        return repository.save(existing);
    }
}
