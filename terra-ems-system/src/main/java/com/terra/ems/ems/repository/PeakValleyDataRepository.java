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
 * 分时用电数据仓库
 *
 * @author dengxueping
 * @since 2026-01-11
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
