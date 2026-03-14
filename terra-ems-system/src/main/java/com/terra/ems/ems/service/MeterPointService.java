/*
 * Copyright (c) 2024-2026 Terra Technology (Guangzhou) Co., Ltd.
 * Copyright (c) 2024-2026 泰若科技（广州）有限公司.
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
import com.terra.ems.ems.entity.EnergyUnit;
import com.terra.ems.ems.entity.Meter;
import com.terra.ems.ems.entity.MeterPoint;
import com.terra.ems.ems.repository.EnergyTypeRepository;
import com.terra.ems.ems.repository.EnergyUnitRepository;
import com.terra.ems.ems.repository.MeterPointRepository;
import com.terra.ems.ems.repository.MeterRepository;
import com.terra.ems.framework.enums.DataItemStatus;
import com.terra.ems.framework.jpa.repository.BaseRepository;
import com.terra.ems.framework.service.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * 采集点位服务
 *
 * @author dengxueping
 * @since 2026-01-11
 */

@Service
@RequiredArgsConstructor
public class MeterPointService extends BaseService<MeterPoint, Long> {

    private final MeterPointRepository meterPointRepository;
    private final MeterRepository meterRepository;
    private final EnergyTypeRepository energyTypeRepository;
    private final EnergyUnitRepository energyUnitRepository;

    @Override
    public BaseRepository<MeterPoint, Long> getRepository() {
        return meterPointRepository;
    }

    /**
     * 保存或更新采集点位（重写基类方法以实现编码重复校验）
     *
     * @param meterPoint 采集点位
     * @return 保存后的采集点位
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public MeterPoint saveOrUpdate(MeterPoint meterPoint) {
        // 检查编码是否重复
        Optional<MeterPoint> existing = meterPointRepository.findByCode(meterPoint.getCode());
        if (existing.isPresent() && !existing.get().getId().equals(meterPoint.getId())) {
            throw new IllegalArgumentException("编码已存在: " + meterPoint.getCode());
        }
        return meterPointRepository.save(meterPoint);
    }

    /**
     * 分页查询
     *
     * @param pageable 分页参数
     * @return 分页结果
     */
    public Page<MeterPoint> findAll(Pageable pageable) {
        return meterPointRepository.findAll(pageable);
    }

    /**
     * 根据编码查询
     *
     * @param code 编码
     * @return 采集点位
     */
    public Optional<MeterPoint> findByCode(String code) {
        return meterPointRepository.findByCode(code);
    }

    /**
     * 根据计量器具ID查询
     *
     * @param meterId 计量器具ID
     * @return 采集点位列表
     */
    public List<MeterPoint> findByMeterId(Long meterId) {
        return meterPointRepository.findByMeter_IdOrderBySortOrderAsc(meterId);
    }

    /**
     * 根据能源类型ID查询
     *
     * @param energyTypeId 能源类型ID
     * @return 采集点位列表
     */
    public List<MeterPoint> findByEnergyTypeId(Long energyTypeId) {
        return meterPointRepository.findByEnergyType_IdOrderBySortOrderAsc(energyTypeId);
    }

    /**
     * 根据用能单元ID查询关联的采集点位
     *
     * @param energyUnitId 用能单元ID
     * @return 采集点位列表
     */
    public List<MeterPoint> findByEnergyUnitId(Long energyUnitId) {
        return meterPointRepository.findByEnergyUnitId(energyUnitId);
    }

    /**
     * 创建采集点位
     *
     * @param meterPoint   采集点位
     * @param meterId      计量器具ID
     * @param energyTypeId 能源类型ID
     * @return 创建后的采集点位
     */
    @Transactional(rollbackFor = Exception.class)
    public MeterPoint create(MeterPoint meterPoint, Long meterId, Long energyTypeId) {
        // 检查编码是否重复
        if (meterPointRepository.existsByCode(meterPoint.getCode())) {
            throw new IllegalArgumentException("编码已存在: " + meterPoint.getCode());
        }

        // 设置关联的计量器具
        if (meterId != null) {
            Meter meter = meterRepository.findById(meterId)
                    .orElseThrow(() -> new IllegalArgumentException("计量器具不存在: " + meterId));
            meterPoint.setMeter(meter);
        }

        // 设置关联的能源类型
        if (energyTypeId != null) {
            EnergyType energyType = energyTypeRepository.findById(energyTypeId)
                    .orElseThrow(() -> new IllegalArgumentException("能源类型不存在: " + energyTypeId));
            meterPoint.setEnergyType(energyType);
        }

        return meterPointRepository.save(meterPoint);
    }

    /**
     * 更新采集点位
     *
     * @param id           ID
     * @param meterPoint   采集点位
     * @param meterId      计量器具ID
     * @param energyTypeId 能源类型ID
     * @return 更新后的采集点位
     */
    @Transactional(rollbackFor = Exception.class)
    public MeterPoint update(Long id, MeterPoint meterPoint, Long meterId, Long energyTypeId) {
        MeterPoint existing = meterPointRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("采集点位不存在: " + id));

        // 检查编码是否被其他记录使用
        Optional<MeterPoint> byCode = meterPointRepository.findByCode(meterPoint.getCode());
        if (byCode.isPresent() && !byCode.get().getId().equals(id)) {
            throw new IllegalArgumentException("编码已被使用: " + meterPoint.getCode());
        }

        // 更新基本信息
        existing.setCode(meterPoint.getCode());
        existing.setName(meterPoint.getName());
        existing.setPointType(meterPoint.getPointType());
        existing.setCategory(meterPoint.getCategory());
        existing.setUnit(meterPoint.getUnit());
        existing.setInitialValue(meterPoint.getInitialValue());
        existing.setMinValue(meterPoint.getMinValue());
        existing.setMaxValue(meterPoint.getMaxValue());
        existing.setStepMin(meterPoint.getStepMin());
        existing.setStepMax(meterPoint.getStepMax());
        existing.setSortOrder(meterPoint.getSortOrder());
        existing.setStatus(meterPoint.getStatus());
        existing.setRemark(meterPoint.getRemark());

        // 更新关联的计量器具
        if (meterId != null) {
            Meter meter = meterRepository.findById(meterId)
                    .orElseThrow(() -> new IllegalArgumentException("计量器具不存在: " + meterId));
            existing.setMeter(meter);
        } else {
            existing.setMeter(null);
        }

        // 更新关联的能源类型
        if (energyTypeId != null) {
            EnergyType energyType = energyTypeRepository.findById(energyTypeId)
                    .orElseThrow(() -> new IllegalArgumentException("能源类型不存在: " + energyTypeId));
            existing.setEnergyType(energyType);
        } else {
            existing.setEnergyType(null);
        }

        return meterPointRepository.save(existing);
    }

    /**
     * 删除采集点位
     *
     * @param id ID
     */
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        if (!meterPointRepository.existsById(id)) {
            throw new IllegalArgumentException("采集点位不存在: " + id);
        }
        meterPointRepository.deleteById(id);
    }

    /**
     * 修改状态
     *
     * @param id     ID
     * @param status 状态
     * @return 更新后的采集点位
     */
    @Transactional(rollbackFor = Exception.class)
    public MeterPoint updateStatus(Long id, DataItemStatus status) {
        MeterPoint existing = meterPointRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("采集点位不存在: " + id));
        existing.setStatus(status);
        return meterPointRepository.save(existing);
    }

    /**
     * 关联用能单元
     *
     * @param id            采集点位ID
     * @param energyUnitIds 用能单元ID集合
     * @return 更新后的采集点位
     */
    @Transactional(rollbackFor = Exception.class)
    public MeterPoint assignEnergyUnits(Long id, Set<Long> energyUnitIds) {
        MeterPoint existing = meterPointRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("采集点位不存在: " + id));

        // 清除现有关联
        existing.getEnergyUnits().clear();

        // 添加新关联
        if (energyUnitIds != null && !energyUnitIds.isEmpty()) {
            Set<EnergyUnit> energyUnits = new HashSet<>();
            for (Long energyUnitId : energyUnitIds) {
                EnergyUnit energyUnit = energyUnitRepository.findById(energyUnitId)
                        .orElseThrow(() -> new IllegalArgumentException("用能单元不存在: " + energyUnitId));
                energyUnits.add(energyUnit);
            }
            existing.setEnergyUnits(energyUnits);
        }

        return meterPointRepository.save(existing);
    }
}
