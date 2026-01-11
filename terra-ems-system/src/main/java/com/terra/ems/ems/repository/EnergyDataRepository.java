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

import com.terra.ems.ems.entity.EnergyData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 能耗数据仓库
 *
 * @author dengxueping
 * @since 2026-01-11
 */

@Repository
public interface EnergyDataRepository extends JpaRepository<EnergyData, Long> {

        /**
         * 按时间范围和类型查询能耗数据
         */
        List<EnergyData> findByMeterPointIdAndTimeTypeAndDataTimeBetween(
                        Long meterPointId, String timeType, LocalDateTime startTime, LocalDateTime endTime);

        /**
         * 按能源类型和时间范围查询
         */
        List<EnergyData> findByEnergyTypeIdAndTimeTypeAndDataTimeBetween(
                        Long energyTypeId, String timeType, LocalDateTime startTime, LocalDateTime endTime);

        /**
         * 查询时间范围内的总能耗
         */
        @Query("SELECT SUM(e.value) FROM EnergyData e WHERE e.meterPoint.id = :pointId " +
                        "AND e.timeType = :timeType AND e.dataTime BETWEEN :startTime AND :endTime")
        BigDecimal sumByMeterPointAndTimeRange(
                        @Param("pointId") Long pointId,
                        @Param("timeType") String timeType,
                        @Param("startTime") LocalDateTime startTime,
                        @Param("endTime") LocalDateTime endTime);

        /**
         * 按用能单元ID查询能耗汇总
         */
        @Query("SELECT SUM(e.value) FROM EnergyData e " +
                        "JOIN e.meterPoint mp JOIN mp.energyUnits eu " +
                        "WHERE eu.id = :energyUnitId AND e.timeType = :timeType " +
                        "AND e.dataTime BETWEEN :startTime AND :endTime")
        BigDecimal sumByEnergyUnitAndTimeRange(
                        @Param("energyUnitId") Long energyUnitId,
                        @Param("timeType") String timeType,
                        @Param("startTime") LocalDateTime startTime,
                        @Param("endTime") LocalDateTime endTime);

        /**
         * 按用能单元ID和能源类型查询能耗汇总
         */
        @Query("SELECT SUM(e.value) FROM EnergyData e " +
                        "JOIN e.meterPoint mp JOIN mp.energyUnits eu " +
                        "WHERE eu.id = :energyUnitId AND e.energyType.id = :energyTypeId " +
                        "AND e.timeType = :timeType AND e.dataTime BETWEEN :startTime AND :endTime")
        BigDecimal sumByEnergyUnitAndEnergyTypeAndTimeRange(
                        @Param("energyUnitId") Long energyUnitId,
                        @Param("energyTypeId") Long energyTypeId,
                        @Param("timeType") String timeType,
                        @Param("startTime") LocalDateTime startTime,
                        @Param("endTime") LocalDateTime endTime);

        /**
         * 查询趋势数据（按时间分组）
         */
        @Query("SELECT e.dataTime, SUM(e.value) FROM EnergyData e " +
                        "JOIN e.meterPoint mp JOIN mp.energyUnits eu " +
                        "WHERE eu.id = :energyUnitId AND e.timeType = :timeType " +
                        "AND e.dataTime BETWEEN :startTime AND :endTime " +
                        "GROUP BY e.dataTime ORDER BY e.dataTime")
        List<Object[]> findTrendByEnergyUnit(
                        @Param("energyUnitId") Long energyUnitId,
                        @Param("timeType") String timeType,
                        @Param("startTime") LocalDateTime startTime,
                        @Param("endTime") LocalDateTime endTime);

        /**
         * 按能源类型查询趋势数据
         */
        @Query("SELECT e.dataTime, SUM(e.value) FROM EnergyData e " +
                        "JOIN e.meterPoint mp JOIN mp.energyUnits eu " +
                        "WHERE eu.id = :energyUnitId AND e.energyType.id = :energyTypeId " +
                        "AND e.timeType = :timeType AND e.dataTime BETWEEN :startTime AND :endTime " +
                        "GROUP BY e.dataTime ORDER BY e.dataTime")
        List<Object[]> findTrendByEnergyUnitAndEnergyType(
                        @Param("energyUnitId") Long energyUnitId,
                        @Param("energyTypeId") Long energyTypeId,
                        @Param("timeType") String timeType,
                        @Param("startTime") LocalDateTime startTime,
                        @Param("endTime") LocalDateTime endTime);

        /**
         * 按能源类型分组统计
         */
        @Query("SELECT e.energyType.id, e.energyType.name, SUM(e.value) FROM EnergyData e " +
                        "JOIN e.meterPoint mp JOIN mp.energyUnits eu " +
                        "WHERE eu.id = :energyUnitId AND e.timeType = :timeType " +
                        "AND e.dataTime BETWEEN :startTime AND :endTime " +
                        "GROUP BY e.energyType.id, e.energyType.name")
        List<Object[]> sumByEnergyTypeGrouped(
                        @Param("energyUnitId") Long energyUnitId,
                        @Param("timeType") String timeType,
                        @Param("startTime") LocalDateTime startTime,
                        @Param("endTime") LocalDateTime endTime);

        /**
         * 查询时间范围内的总折标煤能耗
         */
        @Query("SELECT SUM(e.value * et.coefficient) FROM EnergyData e " +
                        "JOIN e.energyType et " +
                        "JOIN e.meterPoint mp JOIN mp.energyUnits eu " +
                        "WHERE eu.id = :energyUnitId AND e.timeType = :timeType " +
                        "AND e.dataTime BETWEEN :startTime AND :endTime")
        BigDecimal sumStandardCoalByEnergyUnitAndTimeRange(
                        @Param("energyUnitId") Long energyUnitId,
                        @Param("timeType") String timeType,
                        @Param("startTime") LocalDateTime startTime,
                        @Param("endTime") LocalDateTime endTime);

        /**
         * 获取子单元能耗排名
         */
        @Query("SELECT eu.id, eu.name, SUM(e.value * et.coefficient) as totalValue FROM EnergyData e " +
                        "JOIN e.energyType et " +
                        "JOIN e.meterPoint mp JOIN mp.energyUnits eu " +
                        "WHERE eu.parent.id = :parentId AND e.timeType = :timeType " +
                        "AND e.dataTime BETWEEN :startTime AND :endTime " +
                        "GROUP BY eu.id, eu.name " +
                        "ORDER BY totalValue DESC")
        List<Object[]> findRankingByParentUnit(
                        @Param("parentId") Long parentId,
                        @Param("timeType") String timeType,
                        @Param("startTime") LocalDateTime startTime,
                        @Param("endTime") LocalDateTime endTime);

        /**
         * 查询时间范围内的总碳排放量
         */
        @Query("SELECT SUM(e.value * et.emissionFactor) FROM EnergyData e " +
                        "JOIN e.energyType et " +
                        "JOIN e.meterPoint mp JOIN mp.energyUnits eu " +
                        "WHERE eu.id = :energyUnitId AND e.timeType = :timeType " +
                        "AND e.dataTime BETWEEN :startTime AND :endTime")
        BigDecimal sumCarbonEmissionByEnergyUnitAndTimeRange(
                        @Param("energyUnitId") Long energyUnitId,
                        @Param("timeType") String timeType,
                        @Param("startTime") LocalDateTime startTime,
                        @Param("endTime") LocalDateTime endTime);

        /**
         * 获取子单元碳排放排名
         */
        @Query("SELECT eu.id, eu.name, SUM(e.value * et.emissionFactor) as totalValue FROM EnergyData e " +
                        "JOIN e.energyType et " +
                        "JOIN e.meterPoint mp JOIN mp.energyUnits eu " +
                        "WHERE eu.parent.id = :parentId AND e.timeType = :timeType " +
                        "AND e.dataTime BETWEEN :startTime AND :endTime " +
                        "GROUP BY eu.id, eu.name " +
                        "ORDER BY totalValue DESC")
        List<Object[]> findCarbonRankingByParentUnit(
                        @Param("parentId") Long parentId,
                        @Param("timeType") String timeType,
                        @Param("startTime") LocalDateTime startTime,
                        @Param("endTime") LocalDateTime endTime);

        /**
         * 查询折标煤趋势数据
         */
        @Query("SELECT e.dataTime, SUM(e.value * et.coefficient) FROM EnergyData e " +
                        "JOIN e.energyType et " +
                        "JOIN e.meterPoint mp JOIN mp.energyUnits eu " +
                        "WHERE eu.id = :energyUnitId AND e.timeType = :timeType " +
                        "AND e.dataTime BETWEEN :startTime AND :endTime " +
                        "GROUP BY e.dataTime ORDER BY e.dataTime")
        List<Object[]> findStandardCoalTrendByEnergyUnit(
                        @Param("energyUnitId") Long energyUnitId,
                        @Param("timeType") String timeType,
                        @Param("startTime") LocalDateTime startTime,
                        @Param("endTime") LocalDateTime endTime);

        /**
         * 按能源类型统计折标煤汇总
         */
        @Query("SELECT et.id, et.name, SUM(e.value * et.coefficient) FROM EnergyData e " +
                        "JOIN e.energyType et " +
                        "JOIN e.meterPoint mp JOIN mp.energyUnits eu " +
                        "WHERE eu.id = :energyUnitId AND e.timeType = :timeType " +
                        "AND e.dataTime BETWEEN :startTime AND :endTime " +
                        "GROUP BY et.id, et.name")
        List<Object[]> sumStandardCoalByEnergyTypeGrouped(
                        @Param("energyUnitId") Long energyUnitId,
                        @Param("timeType") String timeType,
                        @Param("startTime") LocalDateTime startTime,
                        @Param("endTime") LocalDateTime endTime);

        /**
         * 查询碳排放趋势数据
         */
        @Query("SELECT e.dataTime, SUM(e.value * et.emissionFactor) FROM EnergyData e " +
                        "JOIN e.energyType et " +
                        "JOIN e.meterPoint mp JOIN mp.energyUnits eu " +
                        "WHERE eu.id = :energyUnitId AND e.timeType = :timeType " +
                        "AND e.dataTime BETWEEN :startTime AND :endTime " +
                        "GROUP BY e.dataTime ORDER BY e.dataTime")
        List<Object[]> findCarbonTrendByEnergyUnit(
                        @Param("energyUnitId") Long energyUnitId,
                        @Param("timeType") String timeType,
                        @Param("startTime") LocalDateTime startTime,
                        @Param("endTime") LocalDateTime endTime);

        /**
         * 按能源类型统计碳排放汇总
         */
        @Query("SELECT et.id, et.name, SUM(e.value * et.emissionFactor) FROM EnergyData e " +
                        "JOIN e.energyType et " +
                        "JOIN e.meterPoint mp JOIN mp.energyUnits eu " +
                        "WHERE eu.id = :energyUnitId AND e.timeType = :timeType " +
                        "AND e.dataTime BETWEEN :startTime AND :endTime " +
                        "GROUP BY et.id, et.name")
        List<Object[]> sumCarbonByEnergyTypeGrouped(
                        @Param("energyUnitId") Long energyUnitId,
                        @Param("timeType") String timeType,
                        @Param("startTime") LocalDateTime startTime,
                        @Param("endTime") LocalDateTime endTime);
}
