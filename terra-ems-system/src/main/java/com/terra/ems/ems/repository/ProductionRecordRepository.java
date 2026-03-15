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

package com.terra.ems.ems.repository;

import com.terra.ems.ems.entity.ProductionRecord;
import com.terra.ems.ems.enums.TimeGranularity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.terra.ems.framework.jpa.repository.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * 产品产量记录仓库
 *
 * @author dengxueping
 * @since 2026-01-11
 */

@Repository
public interface ProductionRecordRepository extends BaseRepository<ProductionRecord, Long> {

        /**
         * 按用能单元ID查询产量记录
         */
        List<ProductionRecord> findByEnergyUnitId(Long energyUnitId);

        /**
         * 分页查询产量记录
         */
        Page<ProductionRecord> findByEnergyUnitIdAndDataTypeAndRecordDateBetween(
                        Long energyUnitId, String dataType, LocalDateTime startDate, LocalDateTime endDate,
                        Pageable pageable);

        /**
         * 按日期范围查询所有记录
         */
        List<ProductionRecord> findByRecordDateBetween(LocalDateTime startDate, LocalDateTime endDate);

        /**
         * 按用能单元、产品名称和日期范围查询
         */
        List<ProductionRecord> findByEnergyUnitIdAndProductNameAndRecordDateBetween(
                        Long energyUnitId, String productName, LocalDateTime startDate, LocalDateTime endDate);

        /**
         * 查询指定日期的产量记录（避免重复录入）
         */
        Optional<ProductionRecord> findByEnergyUnitIdAndDataTypeAndProductNameAndRecordDateAndGranularity(
                        Long energyUnitId, String dataType, String productName, LocalDateTime recordDate,
                        TimeGranularity granularity);

        /**
         * 按用能单元汇总产量
         */
        @Query("SELECT SUM(p.quantity) FROM ProductionRecord p " +
                        "WHERE p.energyUnitId = :energyUnitId AND p.dataType = :dataType " +
                        "AND p.recordDate BETWEEN :startDate AND :endDate")
        BigDecimal sumQuantityByEnergyUnitAndDataTypeAndDateRange(
                        @Param("energyUnitId") Long energyUnitId,
                        @Param("dataType") String dataType,
                        @Param("startDate") LocalDateTime startDate,
                        @Param("endDate") LocalDateTime endDate);

        /**
         * 按产品名称汇总产量
         */
        @Query("SELECT SUM(p.quantity) FROM ProductionRecord p " +
                        "WHERE p.energyUnitId = :energyUnitId AND p.dataType = :dataType AND p.productName = :productName "
                        +
                        "AND p.recordDate BETWEEN :startDate AND :endDate")
        BigDecimal sumQuantityByEnergyUnitAndDataTypeAndProductNameAndDateRange(
                        @Param("energyUnitId") Long energyUnitId,
                        @Param("dataType") String dataType,
                        @Param("productName") String productName,
                        @Param("startDate") LocalDateTime startDate,
                        @Param("endDate") LocalDateTime endDate);

        /**
         * 查询产量趋势
         */
        @Query("SELECT p.recordDate, SUM(p.quantity) FROM ProductionRecord p " +
                        "WHERE p.energyUnitId = :energyUnitId AND p.dataType = :dataType " +
                        "AND p.recordDate BETWEEN :startDate AND :endDate " +
                        "GROUP BY p.recordDate ORDER BY p.recordDate")
        List<Object[]> findTrendByEnergyUnitAndDataType(
                        @Param("energyUnitId") Long energyUnitId,
                        @Param("dataType") String dataType,
                        @Param("startDate") LocalDateTime startDate,
                        @Param("endDate") LocalDateTime endDate);

        /**
         * 按产品名称查询产量趋势
         */
        @Query("SELECT p.recordDate, SUM(p.quantity) FROM ProductionRecord p " +
                        "WHERE p.energyUnitId = :energyUnitId AND p.dataType = :dataType AND p.productName = :productName "
                        +
                        "AND p.recordDate BETWEEN :startDate AND :endDate " +
                        "GROUP BY p.recordDate ORDER BY p.recordDate")
        List<Object[]> findTrendByEnergyUnitAndDataTypeAndProductName(
                        @Param("energyUnitId") Long energyUnitId,
                        @Param("dataType") String dataType,
                        @Param("productName") String productName,
                        @Param("startDate") LocalDateTime startDate,
                        @Param("endDate") LocalDateTime endDate);

        /**
         * 按产品名称汇总产量
         */
        @Query("SELECT p.productName, SUM(p.quantity) FROM ProductionRecord p " +
                        "WHERE p.energyUnitId = :energyUnitId AND p.dataType = :dataType " +
                        "AND p.recordDate BETWEEN :startDate AND :endDate " +
                        "GROUP BY p.productName")
        List<Object[]> sumQuantityGroupByProduct(
                        @Param("energyUnitId") Long energyUnitId,
                        @Param("dataType") String dataType,
                        @Param("startDate") LocalDateTime startDate,
                        @Param("endDate") LocalDateTime endDate);

        /**
         * 获取用能单元下的产品名称列表
         */
        @Query("SELECT DISTINCT p.productName FROM ProductionRecord p WHERE p.energyUnitId = :energyUnitId AND p.dataType = :dataType")
        List<String> findDistinctProductNamesByEnergyUnitIdAndDataType(
                        @Param("energyUnitId") Long energyUnitId,
                        @Param("dataType") String dataType);
}
