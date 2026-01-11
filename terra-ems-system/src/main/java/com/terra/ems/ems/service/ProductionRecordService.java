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

import com.terra.ems.ems.entity.ProductionRecord;
import com.terra.ems.ems.enums.TimeGranularity;
import com.terra.ems.ems.repository.ProductionRecordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Name: ProductionRecordService.java
 * Email: dengxueping@gmail.com
 * Date: 2026-01-10
 * Description:
 * 产品产量记录服务
 *
 * @author dengxueping
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ProductionRecordService {

    private final ProductionRecordRepository productionRecordRepository;

    /**
     * 创建产量记录
     */
    @Transactional
    public ProductionRecord create(ProductionRecord record) {
        // 检查是否已存在相同记录
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
    }

    /**
     * 更新产量记录
     */
    @Transactional
    public ProductionRecord update(Long id, ProductionRecord record) {
        ProductionRecord existing = productionRecordRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("产量记录不存在: " + id));

        existing.setEnergyUnitId(record.getEnergyUnitId());
        existing.setRecordDate(record.getRecordDate());
        existing.setProductName(record.getProductName());
        existing.setQuantity(record.getQuantity());
        existing.setUnit(record.getUnit());
        existing.setGranularity(record.getGranularity());
        existing.setDataType(record.getDataType());
        existing.setProductType(record.getProductType());
        existing.setRemark(record.getRemark());

        log.info("更新产量记录: ID={}", id);
        return productionRecordRepository.save(existing);
    }

    /**
     * 删除产量记录
     */
    @Transactional
    public void delete(Long id) {
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
     * 根据ID查询
     */
    public Optional<ProductionRecord> findById(Long id) {
        return productionRecordRepository.findById(id);
    }

    /**
     * 分页查询产量记录
     */
    public Page<ProductionRecord> findByEnergyUnitAndDateRange(
            Long energyUnitId, String dataType, LocalDate startDate, LocalDate endDate, Pageable pageable) {
        return productionRecordRepository.findByEnergyUnitIdAndDataTypeAndRecordDateBetween(
                energyUnitId, dataType, startDate, endDate, pageable);
    }

    /**
     * 查询日期范围内的所有产量记录
     */
    public List<ProductionRecord> findByDateRange(LocalDate startDate, LocalDate endDate) {
        return productionRecordRepository.findByRecordDateBetween(startDate, endDate);
    }

    /**
     * 汇总指定用能单元在日期范围内的总产量
     */
    public BigDecimal sumQuantity(Long energyUnitId, String dataType, LocalDate startDate, LocalDate endDate) {
        BigDecimal total = productionRecordRepository.sumQuantityByEnergyUnitAndDataTypeAndDateRange(
                energyUnitId, dataType, startDate, endDate);
        return total != null ? total : BigDecimal.ZERO;
    }

    /**
     * 按产品分组统计产量
     */
    public Map<String, BigDecimal> sumQuantityGroupByProduct(
            Long energyUnitId, String dataType, LocalDate startDate, LocalDate endDate) {
        List<Object[]> results = productionRecordRepository.sumQuantityGroupByProduct(
                energyUnitId, dataType, startDate, endDate);

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
