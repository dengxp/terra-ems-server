/*
 * Copyright (c) 2024 泰若科技（广州）有限公司. All rights reserved.
 * Copyright (c) 2024-2026 Terra EMS
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
 * Name: TimePeriodPriceService.java
 * Email: dengxueping@gmail.com
 * Date: 2026-01-10
 * Description:
 * 分时电价配置服务
 *
 * @author dengxueping
 */
@Service
@RequiredArgsConstructor
public class TimePeriodPriceService extends BaseService<TimePeriodPrice, Long> {

    private final TimePeriodPriceRepository timePeriodPriceRepository;

    @Override
    protected BaseRepository<TimePeriodPrice, Long> getRepository() {
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
     * 创建分时电价配置
     */
    @Transactional(rollbackFor = Exception.class)
    public TimePeriodPrice create(TimePeriodPrice timePeriodPrice) {
        // 设置默认值
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
    }

    /**
     * 更新分时电价配置
     */
    @Transactional(rollbackFor = Exception.class)
    public TimePeriodPrice update(Long id, TimePeriodPrice timePeriodPrice) {
        TimePeriodPrice existing = timePeriodPriceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("分时电价配置不存在: " + id));

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

    /**
     * 删除分时电价配置
     */
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
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
