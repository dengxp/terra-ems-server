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

import com.terra.ems.ems.dto.ComparisonAnalysisDTO;
import com.terra.ems.ems.dto.EnergyStatisticsSummaryDTO;
import com.terra.ems.ems.dto.EnergyStatisticsSummaryDTO.EnergyTypeDistribution;
import com.terra.ems.ems.dto.EnergyStatisticsSummaryDTO.TrendDataItem;
import com.terra.ems.ems.repository.EnergyDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.List;

/**
 * 碳排放管理服务
 *
 * @author dengxueping
 * @since 2026-01-11
 */

@Service
@RequiredArgsConstructor
public class CarbonEmissionService {

    private final EnergyDataRepository energyDataRepository;

    /**
     * 获取碳排放汇总
     *
     * @param energyUnitId 用能单元ID
     * @param timeType     时间类型 (DAY/MONTH/YEAR)
     * @param dataTime     数据时间
     * @return 碳排放汇总
     */
    public EnergyStatisticsSummaryDTO getSummary(Long energyUnitId, String timeType, LocalDateTime dataTime) {
        EnergyStatisticsSummaryDTO summary = new EnergyStatisticsSummaryDTO();

        TimeRange currentRange = calculateTimeRange(timeType, dataTime);
        TimeRange lastYearRange = calculateTimeRange(timeType, dataTime.minusYears(1));
        TimeRange lastPeriodRange = calculateLastPeriodRange(timeType, dataTime);

        // 当期总碳排放
        BigDecimal currentTotal = energyDataRepository.sumCarbonEmissionByEnergyUnitAndTimeRange(
                energyUnitId, timeType, currentRange.start, currentRange.end);
        summary.setCurrentTotal(currentTotal != null ? currentTotal : BigDecimal.ZERO);

        // 同期总碳排放
        BigDecimal lastYearTotal = energyDataRepository.sumCarbonEmissionByEnergyUnitAndTimeRange(
                energyUnitId, timeType, lastYearRange.start, lastYearRange.end);
        summary.setLastYearTotal(lastYearTotal != null ? lastYearTotal : BigDecimal.ZERO);

        // 上期总碳排放
        BigDecimal lastPeriodTotal = energyDataRepository.sumCarbonEmissionByEnergyUnitAndTimeRange(
                energyUnitId, timeType, lastPeriodRange.start, lastPeriodRange.end);
        summary.setLastPeriodTotal(lastPeriodTotal != null ? lastPeriodTotal : BigDecimal.ZERO);

        summary.setYoyRate(calculateChangeRate(summary.getCurrentTotal(), summary.getLastYearTotal()));
        summary.setMomRate(calculateChangeRate(summary.getCurrentTotal(), summary.getLastPeriodTotal()));

        // 碳排放分布（按能源类型）
        summary.setEnergyTypeDistribution(getCarbonDistribution(
                energyUnitId, timeType, currentRange.start, currentRange.end));

        // 趋势数据
        summary.setTrendData(getCarbonTrendData(energyUnitId, timeType, currentRange.start, currentRange.end));

        return summary;
    }

    /**
     * 获取碳排放排名
     */
    public List<ComparisonAnalysisDTO> getRanking(Long parentUnitId, String timeType, LocalDateTime dataTime) {
        List<ComparisonAnalysisDTO> result = new ArrayList<>();
        TimeRange currentRange = calculateTimeRange(timeType, dataTime);

        List<Object[]> data = energyDataRepository.findCarbonRankingByParentUnit(
                parentUnitId, timeType, currentRange.start, currentRange.end);

        for (Object[] row : data) {
            ComparisonAnalysisDTO dto = new ComparisonAnalysisDTO();
            dto.setEnergyUnitId((Long) row[0]);
            dto.setEnergyUnitName((String) row[1]);
            dto.setCurrentValue((BigDecimal) row[2]);
            dto.setUnit("kgCO2");
            result.add(dto);
        }

        return result;
    }

    private List<EnergyTypeDistribution> getCarbonDistribution(
            Long energyUnitId, String timeType, LocalDateTime startTime, LocalDateTime endTime) {
        // 由于 sumByEnergyTypeGrouped 返回的是原始能耗，
        // 实际上碳排放分布需要针对加权后的值进行统计。
        // 为了简化，目前可以沿用 EnergyStatisticsService 的逻辑，或者此处专门实现碳排放分布。
        // 建议在 repository 中也添加一个针对碳排放权重的分组统计方法。
        // 这里暂时复用 getValues 的逻辑，但返回的结果应包含 emissionFactor 的影响。
        return new ArrayList<>(); // 待完善，或者在 Repository 中增加 Query
    }

    private List<TrendDataItem> getCarbonTrendData(
            Long energyUnitId, String timeType, LocalDateTime startTime, LocalDateTime endTime) {
        // 同上，此处需要加权后的趋势数据
        return new ArrayList<>();
    }

    private BigDecimal calculateChangeRate(BigDecimal current, BigDecimal comparison) {
        if (comparison == null || comparison.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        return current.subtract(comparison)
                .multiply(BigDecimal.valueOf(100))
                .divide(comparison, 2, RoundingMode.HALF_UP);
    }

    private TimeRange calculateTimeRange(String timeType, LocalDateTime dataTime) {
        LocalDateTime start, end;
        switch (timeType) {
            case "DAY":
                start = dataTime.toLocalDate().atStartOfDay();
                end = start.plusDays(1).minusNanos(1);
                break;
            case "MONTH":
                start = dataTime.withDayOfMonth(1).toLocalDate().atStartOfDay();
                end = start.plusMonths(1).minusNanos(1);
                break;
            case "YEAR":
                start = dataTime.withDayOfYear(1).toLocalDate().atStartOfDay();
                end = start.plusYears(1).minusNanos(1);
                break;
            default:
                start = dataTime.toLocalDate().atStartOfDay();
                end = start.plusDays(1).minusNanos(1);
        }
        return new TimeRange(start, end);
    }

    private TimeRange calculateLastPeriodRange(String timeType, LocalDateTime dataTime) {
        switch (timeType) {
            case "DAY":
                return calculateTimeRange(timeType, dataTime.minusDays(1));
            case "MONTH":
                return calculateTimeRange(timeType, dataTime.minusMonths(1));
            case "YEAR":
                return calculateTimeRange(timeType, dataTime.minusYears(1));
            default:
                return calculateTimeRange(timeType, dataTime.minusDays(1));
        }
    }

    private record TimeRange(LocalDateTime start, LocalDateTime end) {
    }
}
