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
package com.terra.ems.ems.service;

import com.terra.ems.ems.entity.TimePeriodPrice;
import com.terra.ems.ems.enums.TimePeriodType;
import com.terra.ems.ems.repository.TimePeriodPriceRepository;
import com.terra.ems.framework.enums.DataItemStatus;
import com.terra.ems.framework.jpa.repository.BaseRepository;
import com.terra.ems.framework.service.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

/**
 * 分时电价配置服务
 *
 * @author dengxueping
 * @since 2026-01-11
 */

@Service
@RequiredArgsConstructor
public class TimePeriodPriceService extends BaseService<TimePeriodPrice, Long> {

    private final TimePeriodPriceRepository timePeriodPriceRepository;

    @Override
    public BaseRepository<TimePeriodPrice, Long> getRepository() {
        return timePeriodPriceRepository;
    }

    /**
     * 查询所有配置（按排序）
     */
    public List<TimePeriodPrice> findAll() {
        return timePeriodPriceRepository.findByStatusOrderBySortOrderAsc(DataItemStatus.ENABLE);
    }

    /**
     * 根据电价政策ID查询
     */
    public List<TimePeriodPrice> findByPricePolicyId(Long pricePolicyId) {
        return timePeriodPriceRepository.findByPricePolicyIdOrderBySortOrderAsc(pricePolicyId);
    }

    /**
     * 根据时段类型查询
     */
    public List<TimePeriodPrice> findByPeriodType(TimePeriodType periodType) {
        return timePeriodPriceRepository.findByPeriodTypeAndStatus(periodType, DataItemStatus.ENABLE);
    }

    /**
     * 根据时间点查询对应的时段配置
     */
    public Optional<TimePeriodPrice> findByTimePoint(LocalTime time) {
        return timePeriodPriceRepository.findByTimePoint(time, DataItemStatus.ENABLE);
    }

    /**
     * 获取所有启用的配置（按开始时间排序）
     */
    public List<TimePeriodPrice> findAllEnabledOrderByStartTime() {
        return timePeriodPriceRepository.findAllEnabledOrderByStartTime(DataItemStatus.ENABLE);
    }

    /**
     * 创建或更新分时电价配置
     * 统一处理默认值设置和字段更新
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public TimePeriodPrice saveOrUpdate(TimePeriodPrice timePeriodPrice) {
        if (timePeriodPrice.getId() == null) {
            // 新建：设置默认值
            if (timePeriodPrice.getPeriodName() == null && timePeriodPrice.getPeriodType() != null) {
                timePeriodPrice.setPeriodName(timePeriodPrice.getPeriodType().getLabel());
            }
            if (timePeriodPrice.getStatus() == null) {
                timePeriodPrice.setStatus(DataItemStatus.ENABLE);
            }
            if (timePeriodPrice.getSortOrder() == null) {
                timePeriodPrice.setSortOrder(0);
            }
            return timePeriodPriceRepository.save(timePeriodPrice);
        } else {
            // 更新：检查存在性并复制字段
            TimePeriodPrice existing = timePeriodPriceRepository.findById(timePeriodPrice.getId())
                    .orElseThrow(() -> new IllegalArgumentException("分时电价配置不存在: " + timePeriodPrice.getId()));

            existing.setPricePolicyId(timePeriodPrice.getPricePolicyId());
            existing.setPeriodType(timePeriodPrice.getPeriodType());
            existing.setPeriodName(timePeriodPrice.getPeriodName());
            existing.setStartTime(timePeriodPrice.getStartTime());
            existing.setEndTime(timePeriodPrice.getEndTime());
            existing.setPrice(timePeriodPrice.getPrice());
            existing.setSortOrder(timePeriodPrice.getSortOrder());
            existing.setStatus(timePeriodPrice.getStatus());
            existing.setRemark(timePeriodPrice.getRemark());

            return timePeriodPriceRepository.save(existing);
        }
    }

    /**
     * 创建分时电价配置 (保留向后兼容)
     */
    @Transactional(rollbackFor = Exception.class)
    public TimePeriodPrice create(TimePeriodPrice timePeriodPrice) {
        timePeriodPrice.setId(null);
        return saveOrUpdate(timePeriodPrice);
    }

    /**
     * 更新分时电价配置 (保留向后兼容)
     */
    @Transactional(rollbackFor = Exception.class)
    public TimePeriodPrice update(Long id, TimePeriodPrice timePeriodPrice) {
        timePeriodPrice.setId(id);
        return saveOrUpdate(timePeriodPrice);
    }

    /**
     * 删除分时电价配置
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(Long id) {
        if (!timePeriodPriceRepository.existsById(id)) {
            throw new IllegalArgumentException("分时电价配置不存在: " + id);
        }
        timePeriodPriceRepository.deleteById(id);
    }

    /**
     * 修改状态
     */
    @Transactional(rollbackFor = Exception.class)
    public TimePeriodPrice updateStatus(Long id, DataItemStatus status) {
        TimePeriodPrice existing = timePeriodPriceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("分时电价配置不存在: " + id));
        existing.setStatus(status);
        return timePeriodPriceRepository.save(existing);
    }

    /**
     * 批量创建电价配置（用于电价政策关联）
     */
    @Transactional(rollbackFor = Exception.class)
    public List<TimePeriodPrice> createBatch(Long pricePolicyId, List<TimePeriodPrice> items) {
        for (TimePeriodPrice item : items) {
            item.setPricePolicyId(pricePolicyId);
            if (item.getPeriodName() == null && item.getPeriodType() != null) {
                item.setPeriodName(item.getPeriodType().getLabel());
            }
            if (item.getStatus() == null) {
                item.setStatus(DataItemStatus.ENABLE);
            }
        }
        return timePeriodPriceRepository.saveAll(items);
    }
}
