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

import com.terra.ems.framework.service.BaseService;
import com.terra.ems.framework.jpa.repository.BaseRepository;
import com.terra.ems.ems.entity.ProductionRecord;

import com.terra.ems.ems.repository.ProductionRecordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 产品产量记录服务
 *
 * @author dengxueping
 * @since 2026-01-11
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductionRecordService extends BaseService<ProductionRecord, Long> {

    private final ProductionRecordRepository productionRecordRepository;

    @Override
    public BaseRepository<ProductionRecord, Long> getRepository() {
        return productionRecordRepository;
    }

    /**
     * 创建或更新产量记录
     * 统一处理重复检查和字段更新
     */
    @Override
    @Transactional
    public ProductionRecord saveOrUpdate(ProductionRecord record) {
        if (record.getId() == null) {
            // 新建：检查是否已存在相同记录
            Optional<ProductionRecord> existing = productionRecordRepository
                    .findByEnergyUnitIdAndDataTypeAndProductNameAndRecordDateAndGranularity(
                            record.getEnergyUnitId(),
                            record.getDataType(),
                            record.getProductName(),
                            record.getRecordDate(),
                            record.getGranularity());

            if (existing.isPresent()) {
                throw new IllegalArgumentException("该用能单元在此日期已存在相同类型及其产品的产量记录");
            }

            log.info("创建产量记录: 用能单元={}, 产品={}, 日期={}, 产量={}",
                    record.getEnergyUnitId(), record.getProductName(),
                    record.getRecordDate(), record.getQuantity());

            return productionRecordRepository.save(record);
        } else {
            // 更新：检查存在性并复制字段
            ProductionRecord existing = productionRecordRepository.findById(record.getId())
                    .orElseThrow(() -> new IllegalArgumentException("产量记录不存在: " + record.getId()));

            existing.setEnergyUnitId(record.getEnergyUnitId());
            existing.setRecordDate(record.getRecordDate());
            existing.setProductName(record.getProductName());
            existing.setQuantity(record.getQuantity());
            existing.setUnit(record.getUnit());
            existing.setGranularity(record.getGranularity());
            existing.setDataType(record.getDataType());
            existing.setProductType(record.getProductType());
            existing.setRemark(record.getRemark());

            log.info("更新产量记录: ID={}", record.getId());
            return productionRecordRepository.save(existing);
        }
    }

    /**
     * 创建产量记录 (保留向后兼容)
     */
    @Transactional
    public ProductionRecord create(ProductionRecord record) {
        record.setId(null);
        return saveOrUpdate(record);
    }

    /**
     * 更新产量记录 (保留向后兼容)
     */
    @Transactional
    public ProductionRecord update(Long id, ProductionRecord record) {
        record.setId(id);
        return saveOrUpdate(record);
    }

    /**
     * 删除产量记录
     */
    @Transactional
    @Override
    public void deleteById(Long id) {
        if (!productionRecordRepository.existsById(id)) {
            throw new IllegalArgumentException("产量记录不存在: " + id);
        }
        productionRecordRepository.deleteById(id);
        log.info("删除产量记录: ID={}", id);
    }

    /**
     * 批量删除
     */
    @Transactional
    public void deleteByIds(List<Long> ids) {
        productionRecordRepository.deleteAllById(ids);
        log.info("批量删除产量记录: count={}", ids.size());
    }

    /**
     * 分页查询产量记录
     */
    public Page<ProductionRecord> findByEnergyUnitAndDateRange(
            Long energyUnitId, String dataType, LocalDate startDate, LocalDate endDate, Pageable pageable) {
        LocalDateTime start = startDate != null ? startDate.atStartOfDay() : null;
        LocalDateTime end = endDate != null ? endDate.atTime(LocalTime.MAX) : null;
        return productionRecordRepository.findByEnergyUnitIdAndDataTypeAndRecordDateBetween(
                energyUnitId, dataType, start, end, pageable);
    }

    /**
     * 查询日期范围内的所有产量记录
     */
    public List<ProductionRecord> findByDateRange(LocalDate startDate, LocalDate endDate) {
        LocalDateTime start = startDate != null ? startDate.atStartOfDay() : null;
        LocalDateTime end = endDate != null ? endDate.atTime(LocalTime.MAX) : null;
        return productionRecordRepository.findByRecordDateBetween(start, end);
    }

    /**
     * 汇总指定用能单元在日期范围内的总产量
     */
    public BigDecimal sumQuantity(Long energyUnitId, String dataType, LocalDate startDate, LocalDate endDate) {
        LocalDateTime start = startDate != null ? startDate.atStartOfDay() : null;
        LocalDateTime end = endDate != null ? endDate.atTime(LocalTime.MAX) : null;
        BigDecimal total = productionRecordRepository.sumQuantityByEnergyUnitAndDataTypeAndDateRange(
                energyUnitId, dataType, start, end);
        return total != null ? total : BigDecimal.ZERO;
    }

    /**
     * 按产品分组统计产量
     */
    public Map<String, BigDecimal> sumQuantityGroupByProduct(
            Long energyUnitId, String dataType, LocalDate startDate, LocalDate endDate) {
        LocalDateTime start = startDate != null ? startDate.atStartOfDay() : null;
        LocalDateTime end = endDate != null ? endDate.atTime(LocalTime.MAX) : null;
        List<Object[]> results = productionRecordRepository.sumQuantityGroupByProduct(
                energyUnitId, dataType, start, end);

        Map<String, BigDecimal> productQuantities = new HashMap<>();
        for (Object[] row : results) {
            String productName = (String) row[0];
            BigDecimal quantity = (BigDecimal) row[1];
            productQuantities.put(productName, quantity != null ? quantity : BigDecimal.ZERO);
        }
        return productQuantities;
    }

    /**
     * 获取用能单元下的产品列表
     */
    public List<String> getProductNames(Long energyUnitId, String dataType) {
        return productionRecordRepository.findDistinctProductNamesByEnergyUnitIdAndDataType(energyUnitId, dataType);
    }

    /**
     * 计算单耗（能耗/产量）
     *
     * @param energyConsumption 能耗总量
     * @param production        产量总量
     * @return 单耗值，产量为0时返回null
     */
    public BigDecimal calculateUnitConsumption(BigDecimal energyConsumption, BigDecimal production) {
        if (production == null || production.compareTo(BigDecimal.ZERO) == 0) {
            return null;
        }
        return energyConsumption.divide(production, 4, java.math.RoundingMode.HALF_UP);
    }
}
