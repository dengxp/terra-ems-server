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

package com.terra.ems.ems.repository;

import com.terra.ems.ems.entity.EnergyCostRecord;
import com.terra.ems.ems.enums.RecordPeriodType;
import com.terra.ems.framework.jpa.repository.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * 能源成本记录仓库
 *
 * @author dengxueping
 * @since 2026-01-11
 */

@Repository
public interface EnergyCostRecordRepository extends BaseRepository<EnergyCostRecord, Long> {

        /**
         * 按用能单元和日期范围查询
         */
        List<EnergyCostRecord> findByEnergyUnitIdAndRecordDateBetweenOrderByRecordDateDesc(
                        Long energyUnitId, LocalDate startDate, LocalDate endDate);

        /**
         * 按日期范围查询所有记录
         */
        List<EnergyCostRecord> findByRecordDateBetween(LocalDate startDate, LocalDate endDate);

        /**
         * 按用能单元、周期类型和日期范围查询
         */
        List<EnergyCostRecord> findByEnergyUnitIdAndPeriodTypeAndRecordDateBetweenOrderByRecordDateAsc(
                        Long energyUnitId, RecordPeriodType periodType, LocalDate startDate, LocalDate endDate);

        /**
         * 统计日期范围内的总成本
         */
        @Query("SELECT COALESCE(SUM(r.cost), 0) FROM EnergyCostRecord r WHERE " +
                        "(:energyUnitId IS NULL OR r.energyUnit.id = :energyUnitId) AND " +
                        "r.recordDate BETWEEN :startDate AND :endDate")
        BigDecimal sumCostByDateRange(
                        @Param("energyUnitId") Long energyUnitId,
                        @Param("startDate") LocalDate startDate,
                        @Param("endDate") LocalDate endDate);

        /**
         * 统计日期范围内的总用量
         */
        @Query("SELECT COALESCE(SUM(r.consumption), 0) FROM EnergyCostRecord r WHERE " +
                        "(:energyUnitId IS NULL OR r.energyUnit.id = :energyUnitId) AND " +
                        "r.recordDate BETWEEN :startDate AND :endDate")
        BigDecimal sumConsumptionByDateRange(
                        @Param("energyUnitId") Long energyUnitId,
                        @Param("startDate") LocalDate startDate,
                        @Param("endDate") LocalDate endDate);

        /**
         * 按用能单元和周期类型统计记录数
         */
        long countByEnergyUnitIdAndPeriodType(Long energyUnitId, RecordPeriodType periodType);
}
