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
import com.terra.ems.ems.repository.EnergyTypeRepository;
import com.terra.ems.framework.enums.DataItemStatus;
import com.terra.ems.framework.jpa.repository.BaseRepository;
import com.terra.ems.framework.service.BaseService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * 能源类型服务实现类
 *
 * @author dengxueping
 * @since 2026-01-11
 */

@Service
@RequiredArgsConstructor
public class EnergyTypeService extends BaseService<EnergyType, Long> {

    private final EnergyTypeRepository energyTypeRepository;

    @Override
    public BaseRepository<EnergyType, Long> getRepository() {
        return energyTypeRepository;
    }

    /**
     * 查询所有启用的能源类型
     *
     * @return 能源类型列表
     */
    public List<EnergyType> findAllEnabled() {
        return energyTypeRepository.findByStatusOrderBySortOrderAsc(DataItemStatus.ENABLE);
    }

    /**
     * 根据编码查询
     *
     * @param code 编码
     * @return 能源类型
     */
    public Optional<EnergyType> findByCode(String code) {
        return energyTypeRepository.findByCode(code);
    }

    /**
     * 删除能源类型
     *
     * @param id ID
     */
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        if (!energyTypeRepository.existsById(id)) {
            throw new IllegalArgumentException("能源类型不存在: " + id);
        }
        energyTypeRepository.deleteById(id);
    }

    /**
     * 批量删除能源类型
     *
     * @param ids ID列表
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteBatch(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return;
        }
        energyTypeRepository.deleteAllById(ids);
    }

    /**
     * 修改状态
     *
     * @param id     ID
     * @param status 状态
     * @return 更新后的能源类型
     */
    @Transactional(rollbackFor = Exception.class)
    public EnergyType updateStatus(Long id, DataItemStatus status) {
        EnergyType existing = energyTypeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("能源类型不存在: " + id));
        existing.setStatus(status);
        return energyTypeRepository.save(existing);
    }
}
