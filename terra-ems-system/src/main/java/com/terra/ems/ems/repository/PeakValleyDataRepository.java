/*
 * Copyright (c) 2024 泰若科技（广州）有限公司. All rights reserved.
 * Copyright (c) 2024-2026 Terra EMS
 */
package com.terra.ems.ems.repository;

import com.terra.ems.ems.entity.PeakValleyData;
import com.terra.ems.ems.enums.TimePeriodType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Name: PeakValleyDataRepository.java
 * Email: dengxueping@gmail.com
 * Date: 2026-01-10
 * Description:
 * 分时用电数据 Repository
 *
 * @author dengxueping
 */
@Repository
public interface PeakValleyDataRepository extends JpaRepository<PeakValleyData, Long> {

    /**
     * 根据采集点位和日期范围查询
     */
    List<PeakValleyData> findByMeterPointIdAndDataTimeBetweenOrderByDataTimeAsc(
            Long meterPointId, LocalDate startDate, LocalDate endDate);

    /**
     * 根据用能单元和日期范围查询
     */
    List<PeakValleyData> findByEnergyUnitIdAndDataTimeBetweenOrderByDataTimeAsc(
            Long energyUnitId, LocalDate startDate, LocalDate endDate);

    /**
     * 按时段类型汇总
     */
    @Query("SELECT p.periodType, SUM(p.electricity), SUM(p.cost) " +
            "FROM PeakValleyData p " +
            "WHERE p.energyUnitId = :unitId " +
            "AND p.dataTime BETWEEN :startDate AND :endDate " +
            "AND p.timeType = :timeType " +
            "GROUP BY p.periodType")
    List<Object[]> sumByPeriodType(@Param("unitId") Long unitId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("timeType") String timeType);

    /**
     * 按日期和时段类型汇总
     */
    @Query("SELECT p.dataTime, p.periodType, SUM(p.electricity), SUM(p.cost) " +
            "FROM PeakValleyData p " +
            "WHERE p.energyUnitId = :unitId " +
            "AND p.dataTime BETWEEN :startDate AND :endDate " +
            "AND p.timeType = :timeType " +
            "GROUP BY p.dataTime, p.periodType " +
            "ORDER BY p.dataTime ASC")
    List<Object[]> sumByDateAndPeriodType(@Param("unitId") Long unitId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("timeType") String timeType);

    /**
     * 查询指定日期的数据
     */
    List<PeakValleyData> findByEnergyUnitIdAndDataTimeAndTimeType(
            Long energyUnitId, LocalDate dataTime, String timeType);

    /**
     * 删除指定日期范围的数据
     */
    void deleteByMeterPointIdAndDataTimeBetween(Long meterPointId, LocalDate startDate, LocalDate endDate);
}
