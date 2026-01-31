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
import com.terra.ems.ems.dto.ProcessEnergyAnalysisDTO;
import com.terra.ems.ems.dto.UnitConsumptionDTO;
import com.terra.ems.ems.entity.EnergyUnit;
import com.terra.ems.ems.dto.BenchmarkAnalysisDTO;
import com.terra.ems.ems.dto.BranchAnalysisDTO;

import com.terra.ems.ems.enums.EnergyUnitType;
import com.terra.ems.ems.repository.EnergyDataRepository;
import com.terra.ems.ems.repository.EnergyTypeRepository;
import com.terra.ems.ems.repository.EnergyUnitRepository;
import com.terra.ems.ems.repository.ProductionRecordRepository;
import com.terra.ems.ems.repository.ProductRepository;
import com.terra.ems.framework.enums.DataItemStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * 能耗统计分析服务
 *
 * @author dengxueping
 * @since 2026-01-11
 */

@Service
@RequiredArgsConstructor
public class EnergyStatisticsService {

    private final EnergyDataRepository energyDataRepository;
    private final EnergyUnitRepository energyUnitRepository;
    private final ProductionRecordRepository productionRecordRepository;
    private final EnergyTypeRepository energyTypeRepository;
    private final ProductRepository productRepository;

    /**
     * 获取统计汇总
     *
     * @param energyUnitId 用能单元ID
     * @param timeType     时间类型 (DAY/MONTH/YEAR)
     * @param dataTime     数据时间
     * @param energyTypeId 能源类型ID（可选，为空时统计折标煤汇总）
     * @return 统计汇总
     */
    public EnergyStatisticsSummaryDTO getSummary(Long energyUnitId, String timeType, LocalDateTime dataTime,
            Long energyTypeId) {
        EnergyStatisticsSummaryDTO summary = new EnergyStatisticsSummaryDTO();

        // 计算时间范围
        TimeRange currentRange = calculateTimeRange(timeType, dataTime);
        TimeRange lastYearRange = calculateTimeRange(timeType, dataTime.minusYears(1));
        TimeRange lastPeriodRange = calculateLastPeriodRange(timeType, dataTime);

        // 如果没有指定能源类型，则汇总折标煤，否则按物理量统计
        if (energyTypeId == null) {
            // 当期总能耗 (tce)
            summary.setCurrentTotal(orZero(energyDataRepository.sumStandardCoalByEnergyUnitAndTimeRange(
                    energyUnitId, timeType, currentRange.start, currentRange.end)));

            // 同期总能耗 (tce)
            summary.setLastYearTotal(orZero(energyDataRepository.sumStandardCoalByEnergyUnitAndTimeRange(
                    energyUnitId, timeType, lastYearRange.start, lastYearRange.end)));

            // 上期总能耗 (tce)
            summary.setLastPeriodTotal(orZero(energyDataRepository.sumStandardCoalByEnergyUnitAndTimeRange(
                    energyUnitId, timeType, lastPeriodRange.start, lastPeriodRange.end)));

            // 趋势数据项 (tce)
            summary.setTrendData(getComprehensiveTrendData(energyUnitId, getSubTimeType(timeType), currentRange.start,
                    currentRange.end));
        } else {
            // 当期总能耗 (物理量)
            summary.setCurrentTotal(orZero(energyDataRepository.sumByEnergyUnitAndEnergyTypeAndTimeRange(
                    energyUnitId, energyTypeId, timeType, currentRange.start, currentRange.end)));

            // 同期总能耗 (物理量)
            summary.setLastYearTotal(orZero(energyDataRepository.sumByEnergyUnitAndEnergyTypeAndTimeRange(
                    energyUnitId, energyTypeId, timeType, lastYearRange.start, lastYearRange.end)));

            // 上期总能耗 (物理量)
            summary.setLastPeriodTotal(orZero(energyDataRepository.sumByEnergyUnitAndEnergyTypeAndTimeRange(
                    energyUnitId, energyTypeId, timeType, lastPeriodRange.start, lastPeriodRange.end)));

            // 趋势数据项 (物理量)
            summary.setTrendData(
                    getTrendDataByEnergyType(energyUnitId, energyTypeId, getSubTimeType(timeType), currentRange.start,
                            currentRange.end));
        }

        // 计算同比增长率
        summary.setYoyRate(calculateChangeRate(summary.getCurrentTotal(), summary.getLastYearTotal()));

        // 计算环比增长率
        summary.setMomRate(calculateChangeRate(summary.getCurrentTotal(), summary.getLastPeriodTotal()));

        // 能源类型分布（目前主要针对综合汇总显示）
        summary.setEnergyTypeDistribution(getEnergyTypeDistribution(
                energyUnitId, timeType, currentRange.start, currentRange.end));

        return summary;
    }

    private BigDecimal orZero(BigDecimal value) {
        return value != null ? value : BigDecimal.ZERO;
    }

    /**
     * 获取同比分析列表
     */
    public List<ComparisonAnalysisDTO> getYoyAnalysis(Long parentUnitId, String timeType, LocalDateTime dataTime,
            Long energyTypeId) {
        return getComparisonAnalysis(parentUnitId, timeType, dataTime, true, energyTypeId);
    }

    /**
     * 获取环比分析列表
     */
    public List<ComparisonAnalysisDTO> getMomAnalysis(Long parentUnitId, String timeType, LocalDateTime dataTime,
            Long energyTypeId) {
        return getComparisonAnalysis(parentUnitId, timeType, dataTime, false, energyTypeId);
    }

    private List<ComparisonAnalysisDTO> getComparisonAnalysis(
            Long parentUnitId, String timeType, LocalDateTime dataTime, boolean isYoy, Long energyTypeId) {
        List<ComparisonAnalysisDTO> result = new ArrayList<>();

        // 获取子用能单元
        List<EnergyUnit> childUnits = energyUnitRepository.findByParentIdOrderBySortOrderAsc(parentUnitId);

        TimeRange currentRange = calculateTimeRange(timeType, dataTime);
        TimeRange comparisonRange = isYoy
                ? calculateTimeRange(timeType, dataTime.minusYears(1))
                : calculateLastPeriodRange(timeType, dataTime);

        // 获取能源单位名称
        String unitName = "tce";
        if (energyTypeId != null) {
            unitName = energyTypeRepository.findById(energyTypeId)
                    .map(com.terra.ems.ems.entity.EnergyType::getUnit)
                    .orElse("kWh");
        }

        for (EnergyUnit unit : childUnits) {
            ComparisonAnalysisDTO dto = new ComparisonAnalysisDTO();
            dto.setEnergyUnitId(unit.getId());
            dto.setEnergyUnitName(unit.getName());

            BigDecimal currentValue, comparisonValue;
            if (energyTypeId == null) {
                currentValue = energyDataRepository.sumStandardCoalByEnergyUnitAndTimeRange(
                        unit.getId(), timeType, currentRange.start, currentRange.end);
                comparisonValue = energyDataRepository.sumStandardCoalByEnergyUnitAndTimeRange(
                        unit.getId(), timeType, comparisonRange.start, comparisonRange.end);
            } else {
                currentValue = energyDataRepository.sumByEnergyUnitAndEnergyTypeAndTimeRange(
                        unit.getId(), energyTypeId, timeType, currentRange.start, currentRange.end);
                comparisonValue = energyDataRepository.sumByEnergyUnitAndEnergyTypeAndTimeRange(
                        unit.getId(), energyTypeId, timeType, comparisonRange.start, comparisonRange.end);
            }

            dto.setCurrentValue(currentValue != null ? currentValue : BigDecimal.ZERO);
            dto.setComparisonValue(comparisonValue != null ? comparisonValue : BigDecimal.ZERO);
            dto.setDifference(dto.getCurrentValue().subtract(dto.getComparisonValue()));
            dto.setChangeRate(calculateChangeRate(dto.getCurrentValue(), dto.getComparisonValue()));
            dto.setUnit(unitName);

            result.add(dto);
        }

        return result;
    }

    /**
     * 获取综合能耗汇总（折标煤）
     */
    public EnergyStatisticsSummaryDTO getComprehensiveSummary(Long energyUnitId, String timeType,
            LocalDateTime dataTime) {
        EnergyStatisticsSummaryDTO summary = new EnergyStatisticsSummaryDTO();

        TimeRange currentRange = calculateTimeRange(timeType, dataTime);
        TimeRange lastYearRange = calculateTimeRange(timeType, dataTime.minusYears(1));
        TimeRange lastPeriodRange = calculateLastPeriodRange(timeType, dataTime);

        // 当期总能耗 (tce)
        BigDecimal currentTotal = energyDataRepository.sumStandardCoalByEnergyUnitAndTimeRange(
                energyUnitId, timeType, currentRange.start, currentRange.end);
        summary.setCurrentTotal(currentTotal != null ? currentTotal : BigDecimal.ZERO);

        // 同期总能耗 (tce)
        BigDecimal lastYearTotal = energyDataRepository.sumStandardCoalByEnergyUnitAndTimeRange(
                energyUnitId, timeType, lastYearRange.start, lastYearRange.end);
        summary.setLastYearTotal(lastYearTotal != null ? lastYearTotal : BigDecimal.ZERO);

        // 上期总能耗 (tce)
        BigDecimal lastPeriodTotal = energyDataRepository.sumStandardCoalByEnergyUnitAndTimeRange(
                energyUnitId, timeType, lastPeriodRange.start, lastPeriodRange.end);
        summary.setLastPeriodTotal(lastPeriodTotal != null ? lastPeriodTotal : BigDecimal.ZERO);

        summary.setYoyRate(calculateChangeRate(summary.getCurrentTotal(), summary.getLastYearTotal()));
        summary.setMomRate(calculateChangeRate(summary.getCurrentTotal(), summary.getLastPeriodTotal()));

        summary.setEnergyTypeDistribution(getEnergyTypeDistribution(
                energyUnitId, timeType, currentRange.start, currentRange.end));

        summary.setTrendData(getComprehensiveTrendData(energyUnitId, getSubTimeType(timeType), currentRange.start,
                currentRange.end));

        return summary;
    }

    /**
     * 获取单元能耗排名
     */
    public List<ComparisonAnalysisDTO> getRankingAnalysis(Long parentUnitId, String timeType, LocalDateTime dataTime) {
        List<ComparisonAnalysisDTO> result = new ArrayList<>();
        TimeRange currentRange = calculateTimeRange(timeType, dataTime);

        List<Object[]> data = energyDataRepository.findRankingByParentUnit(
                parentUnitId, timeType, currentRange.start, currentRange.end);

        for (Object[] row : data) {
            ComparisonAnalysisDTO dto = new ComparisonAnalysisDTO();
            dto.setEnergyUnitId((Long) row[0]);
            dto.setEnergyUnitName((String) row[1]);
            dto.setCurrentValue((BigDecimal) row[2]);
            dto.setUnit("tce");
            result.add(dto);
        }

        return result;
    }

    private List<TrendDataItem> getComprehensiveTrendData(
            Long energyUnitId, String timeType, LocalDateTime startTime, LocalDateTime endTime) {
        List<Object[]> data = energyDataRepository.findStandardCoalTrendByEnergyUnit(
                energyUnitId, timeType, startTime, endTime);
        List<TrendDataItem> result = new ArrayList<>();
        DateTimeFormatter formatter = getDateTimeFormatter(timeType);

        for (Object[] row : data) {
            TrendDataItem item = new TrendDataItem();
            item.setLabel(((LocalDateTime) row[0]).format(formatter));
            item.setValue((BigDecimal) row[1]);
            result.add(item);
        }
        return result;
    }

    private DateTimeFormatter getDateTimeFormatter(String timeType) {
        switch (timeType) {
            case "DAY":
                return DateTimeFormatter.ofPattern("HH:mm");
            case "MONTH":
                return DateTimeFormatter.ofPattern("MM-dd");
            case "YEAR":
                return DateTimeFormatter.ofPattern("yyyy-MM");
            default:
                return DateTimeFormatter.ofPattern("yyyy-MM-dd");
        }
    }

    private List<EnergyTypeDistribution> getEnergyTypeDistribution(
            Long energyUnitId, String timeType, LocalDateTime startTime, LocalDateTime endTime) {
        List<Object[]> data = energyDataRepository.sumStandardCoalByEnergyTypeGrouped(
                energyUnitId, timeType, startTime, endTime);

        List<EnergyTypeDistribution> result = new ArrayList<>();
        BigDecimal total = data.stream()
                .map(row -> (BigDecimal) row[2])
                .filter(v -> v != null)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        for (Object[] row : data) {
            EnergyTypeDistribution item = new EnergyTypeDistribution();
            item.setEnergyTypeId((Long) row[0]);
            item.setEnergyTypeName((String) row[1]);
            item.setValue((BigDecimal) row[2]);
            if (total.compareTo(BigDecimal.ZERO) > 0 && item.getValue() != null) {
                item.setPercentage(item.getValue()
                        .multiply(BigDecimal.valueOf(100))
                        .divide(total, 2, RoundingMode.HALF_UP));
            } else {
                item.setPercentage(BigDecimal.ZERO);
            }
            result.add(item);
        }

        return result;
    }

    private List<TrendDataItem> getTrendDataByEnergyType(
            Long energyUnitId, Long energyTypeId, String timeType, LocalDateTime startTime, LocalDateTime endTime) {
        List<Object[]> data = energyDataRepository.findTrendByEnergyUnitAndEnergyType(
                energyUnitId, energyTypeId, timeType, startTime, endTime);

        List<TrendDataItem> result = new ArrayList<>();
        DateTimeFormatter formatter = getFormatter(timeType);

        for (Object[] row : data) {
            TrendDataItem item = new TrendDataItem();
            LocalDateTime dt = (LocalDateTime) row[0];
            item.setDataTime(dt);
            item.setLabel(dt.format(formatter));
            item.setValue((BigDecimal) row[1]);
            result.add(item);
        }

        return result;
    }

    private List<TrendDataItem> getTrendData(
            Long energyUnitId, String timeType, LocalDateTime startTime, LocalDateTime endTime) {
        List<Object[]> data = energyDataRepository.findTrendByEnergyUnit(
                energyUnitId, timeType, startTime, endTime);

        List<TrendDataItem> result = new ArrayList<>();
        DateTimeFormatter formatter = getFormatter(timeType);

        for (Object[] row : data) {
            TrendDataItem item = new TrendDataItem();
            LocalDateTime dt = (LocalDateTime) row[0];
            item.setDataTime(dt);
            item.setLabel(dt.format(formatter));
            item.setValue((BigDecimal) row[1]);
            result.add(item);
        }

        return result;
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
        end = switch (timeType) {
            case "MONTH" -> {
                start = dataTime.withDayOfMonth(1).toLocalDate().atStartOfDay();
                yield start.plusMonths(1).minusNanos(1);
            }
            case "YEAR" -> {
                start = dataTime.withDayOfYear(1).toLocalDate().atStartOfDay();
                yield start.plusYears(1).minusNanos(1);
            }
            default -> {
                start = dataTime.toLocalDate().atStartOfDay();
                yield start.plusDays(1).minusNanos(1);
            }
        };
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

    private DateTimeFormatter getFormatter(String timeType) {
        switch (timeType) {
            case "DAY":
                return DateTimeFormatter.ofPattern("HH:mm");
            case "MONTH":
                return DateTimeFormatter.ofPattern("MM-dd");
            case "YEAR":
                return DateTimeFormatter.ofPattern("yyyy-MM");
            default:
                return DateTimeFormatter.ofPattern("MM-dd HH:mm");
        }
    }

    private record TimeRange(LocalDateTime start, LocalDateTime end) {
    }

    private String getSubTimeType(String timeType) {
        return switch (timeType) {
            case "YEAR" -> "MONTH";
            case "MONTH" -> "DAY";
            case "DAY" -> "HOUR";
            default -> "DAY";
        };
    }

    /**
     * 获取工序能耗分析
     * 分析各子用能单元（工序）的能耗情况
     *
     * @param parentUnitId 父用能单元ID
     * @param timeType     时间类型
     * @param dataTime     数据时间
     * @param energyTypeId 能源类型ID（可选）
     * @return 工序能耗分析列表
     */
    public List<ProcessEnergyAnalysisDTO> getProcessEnergyAnalysis(
            Long parentUnitId, String timeType, LocalDateTime dataTime, Long energyTypeId) {

        List<ProcessEnergyAnalysisDTO> result = new ArrayList<>();
        TimeRange currentRange = calculateTimeRange(timeType, dataTime);

        // 获取子用能单元
        List<EnergyUnit> childUnits = energyUnitRepository.findByParentIdOrderBySortOrderAsc(parentUnitId);

        // 获取能源单位
        String unitName = "tce";
        if (energyTypeId != null) {
            unitName = energyTypeRepository.findById(energyTypeId)
                    .map(com.terra.ems.ems.entity.EnergyType::getUnit)
                    .orElse("tce");
        }

        // 计算总能耗用于占比计算
        BigDecimal totalEnergy = BigDecimal.ZERO;
        List<Object[]> energyData = new ArrayList<>();

        for (EnergyUnit unit : childUnits) {
            BigDecimal consumption;
            if (energyTypeId != null) {
                consumption = energyDataRepository.sumByEnergyUnitAndEnergyTypeAndTimeRange(
                        unit.getId(), energyTypeId, timeType, currentRange.start, currentRange.end);
            } else {
                consumption = energyDataRepository.sumStandardCoalByEnergyUnitAndTimeRange(
                        unit.getId(), timeType, currentRange.start, currentRange.end);
            }
            consumption = consumption != null ? consumption : BigDecimal.ZERO;
            totalEnergy = totalEnergy.add(consumption);
            energyData.add(new Object[] { unit, consumption });
        }

        // 构建结果
        for (Object[] data : energyData) {
            EnergyUnit unit = (EnergyUnit) data[0];
            BigDecimal consumption = (BigDecimal) data[1];

            ProcessEnergyAnalysisDTO dto = new ProcessEnergyAnalysisDTO();
            dto.setEnergyUnitId(unit.getId());
            dto.setEnergyUnitName(unit.getName());
            dto.setTotalConsumption(consumption);
            dto.setUnit(unitName);

            // 计算占比
            if (totalEnergy.compareTo(BigDecimal.ZERO) > 0) {
                dto.setPercentage(consumption
                        .multiply(BigDecimal.valueOf(100))
                        .divide(totalEnergy, 2, RoundingMode.HALF_UP));
            } else {
                dto.setPercentage(BigDecimal.ZERO);
            }

            // 获取时段能耗明细
            List<ProcessEnergyAnalysisDTO.TimeSlotEnergy> timeSlotData = new ArrayList<>();
            List<Object[]> trendData;
            if (energyTypeId != null) {
                trendData = energyDataRepository.findTrendByEnergyUnitAndEnergyType(
                        unit.getId(), energyTypeId, getSubTimeType(timeType), currentRange.start, currentRange.end);
            } else {
                trendData = energyDataRepository.findStandardCoalTrendByEnergyUnit(
                        unit.getId(), getSubTimeType(timeType), currentRange.start, currentRange.end);
            }

            DateTimeFormatter formatter = getFormatter(getSubTimeType(timeType));
            for (Object[] row : trendData) {
                ProcessEnergyAnalysisDTO.TimeSlotEnergy slot = new ProcessEnergyAnalysisDTO.TimeSlotEnergy();
                slot.setLabel(((LocalDateTime) row[0]).format(formatter));
                slot.setValue((BigDecimal) row[1]);
                timeSlotData.add(slot);
            }
            dto.setTimeSlotData(timeSlotData);

            result.add(dto);
        }

        return result;
    }

    /**
     * 获取单耗分析
     * 计算能耗/产量的单耗指标
     *
     * @param energyUnitId 用能单元ID
     * @param timeType     时间类型
     * @param dataTime     数据时间
     * @return 单耗分析结果
     */
    public UnitConsumptionDTO getUnitConsumptionAnalysis(
            Long energyUnitId, String timeType, LocalDateTime dataTime, Long energyTypeId, Long productId) {

        UnitConsumptionDTO dto = new UnitConsumptionDTO();

        TimeRange currentRange = calculateTimeRange(timeType, dataTime);
        TimeRange lastYearRange = calculateTimeRange(timeType, dataTime.minusYears(1));
        TimeRange lastPeriodRange = calculateLastPeriodRange(timeType, dataTime);

        // 获取用能单元信息
        energyUnitRepository.findById(energyUnitId).ifPresent(unit -> {
            dto.setEnergyUnitId(unit.getId());
            dto.setEnergyUnitName(unit.getName());
        });

        // 解析产品名称
        String productName = null;
        if (productId != null) {
            productName = productRepository.findById(productId)
                    .map(com.terra.ems.ems.entity.Product::getName)
                    .orElse(null);
        }

        // 当期能耗
        BigDecimal currentEnergy = sumEnergyConsumption(energyUnitId, energyTypeId, timeType, currentRange);
        dto.setEnergyConsumption(currentEnergy);

        // 当期产量
        BigDecimal currentProduction = sumProductionQuantity(energyUnitId, productName, currentRange);
        dto.setProduction(currentProduction);

        // 当期单耗
        BigDecimal currentUnitConsumption = calculateUnitConsumption(currentEnergy, currentProduction);
        dto.setUnitConsumption(currentUnitConsumption);

        // 同期数据
        BigDecimal lastYearEnergy = sumEnergyConsumption(energyUnitId, energyTypeId, timeType, lastYearRange);
        BigDecimal lastYearProduction = sumProductionQuantity(energyUnitId, productName, lastYearRange);
        BigDecimal lastYearUnitConsumption = calculateUnitConsumption(lastYearEnergy, lastYearProduction);
        dto.setLastYearUnitConsumption(lastYearUnitConsumption);

        // 同比变化率
        if (lastYearUnitConsumption != null && currentUnitConsumption != null) {
            dto.setYoyRate(calculateChangeRate(currentUnitConsumption, lastYearUnitConsumption));
        }

        // 上期数据
        BigDecimal lastPeriodEnergy = sumEnergyConsumption(energyUnitId, energyTypeId, timeType, lastPeriodRange);
        BigDecimal lastPeriodProduction = sumProductionQuantity(energyUnitId, productName, lastPeriodRange);
        BigDecimal lastPeriodUnitConsumption = calculateUnitConsumption(lastPeriodEnergy, lastPeriodProduction);
        dto.setLastPeriodUnitConsumption(lastPeriodUnitConsumption);

        // 环比变化率
        if (lastPeriodUnitConsumption != null && currentUnitConsumption != null) {
            dto.setMomRate(calculateChangeRate(currentUnitConsumption, lastPeriodUnitConsumption));
        }

        // 生成趋势数据
        dto.setTrendData(getUnitConsumptionTrend(energyUnitId, energyTypeId, productName, timeType, currentRange));

        return dto;
    }

    private BigDecimal sumEnergyConsumption(Long energyUnitId, Long energyTypeId, String timeType, TimeRange range) {
        if (energyTypeId != null) {
            BigDecimal sum = energyDataRepository.sumByEnergyUnitAndEnergyTypeAndTimeRange(
                    energyUnitId, energyTypeId, timeType, range.start, range.end);
            return sum != null ? sum : BigDecimal.ZERO;
        } else {
            BigDecimal sum = energyDataRepository.sumStandardCoalByEnergyUnitAndTimeRange(
                    energyUnitId, timeType, range.start, range.end);
            return sum != null ? sum : BigDecimal.ZERO;
        }
    }

    private BigDecimal sumProductionQuantity(Long energyUnitId, String productName, TimeRange range) {
        if (productName != null && !productName.isEmpty()) {
            return productionRecordRepository.sumQuantityByEnergyUnitAndDataTypeAndProductNameAndDateRange(
                    energyUnitId, "1", productName, range.start.toLocalDate(), range.end.toLocalDate());
        } else {
            return productionRecordRepository.sumQuantityByEnergyUnitAndDataTypeAndDateRange(
                    energyUnitId, "1", range.start.toLocalDate(), range.end.toLocalDate());
        }
    }

    private List<UnitConsumptionDTO.TrendItem> getUnitConsumptionTrend(
            Long energyUnitId, Long energyTypeId, String productName, String timeType, TimeRange range) {

        // 获取能耗趋势
        List<Object[]> energyTrend;
        if (energyTypeId != null) {
            energyTrend = energyDataRepository.findTrendByEnergyUnitAndEnergyType(
                    energyUnitId, energyTypeId, getSubTimeType(timeType), range.start, range.end);
        } else {
            energyTrend = energyDataRepository.findStandardCoalTrendByEnergyUnit(
                    energyUnitId, getSubTimeType(timeType), range.start, range.end);
        }

        // 获取产量趋势
        List<Object[]> prodTrend;
        if (productName != null && !productName.isEmpty()) {
            prodTrend = productionRecordRepository.findTrendByEnergyUnitAndDataTypeAndProductName(
                    energyUnitId, "1", productName, range.start.toLocalDate(), range.end.toLocalDate());
        } else {
            prodTrend = productionRecordRepository.findTrendByEnergyUnitAndDataType(
                    energyUnitId, "1", range.start.toLocalDate(), range.end.toLocalDate());
        }

        // 合并数据
        java.util.Map<String, BigDecimal> energyMap = new java.util.HashMap<>();
        java.util.Map<String, BigDecimal> prodMap = new java.util.HashMap<>();
        DateTimeFormatter formatter = getFormatter(getSubTimeType(timeType));

        for (Object[] row : energyTrend) {
            String label = ((LocalDateTime) row[0]).format(formatter);
            energyMap.put(label, (BigDecimal) row[1]);
        }

        for (Object[] row : prodTrend) {
            // 产量记录里是 LocalDate
            String label;
            if (row[0] instanceof LocalDate) {
                label = ((LocalDate) row[0]).atStartOfDay().format(formatter);
            } else {
                label = ((LocalDateTime) row[0]).format(formatter);
            }
            prodMap.put(label, (BigDecimal) row[1]);
        }

        // 统一时间轴（以能耗或产量时间为准，通常能耗更密）
        java.util.Set<String> allLabels = new java.util.TreeSet<>();
        allLabels.addAll(energyMap.keySet());
        allLabels.addAll(prodMap.keySet());

        List<UnitConsumptionDTO.TrendItem> result = new ArrayList<>();
        for (String label : allLabels) {
            UnitConsumptionDTO.TrendItem item = new UnitConsumptionDTO.TrendItem();
            item.setLabel(label);
            item.setEnergyConsumption(energyMap.getOrDefault(label, BigDecimal.ZERO));
            item.setProduction(prodMap.getOrDefault(label, BigDecimal.ZERO));
            item.setUnitConsumption(calculateUnitConsumption(item.getEnergyConsumption(), item.getProduction()));
            result.add(item);
        }

        return result;
    }

    private BigDecimal calculateUnitConsumption(BigDecimal energy, BigDecimal production) {
        if (production == null || production.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        return energy.divide(production, 4, RoundingMode.HALF_UP);
    }

    // ==================== 支路分析 ====================

    /**
     * 获取支路能耗分析
     * 分析指定父节点下所有支路类型子节点的能耗情况
     *
     * @param parentUnitId 父用能单元ID
     * @param timeType     时间类型 (DAY/MONTH/YEAR)
     * @param dataTime     数据时间
     * @param energyTypeId 能源类型ID（可选，默认电力）
     * @return 支路能耗分析列表
     */
    public List<BranchAnalysisDTO> getBranchAnalysis(
            Long parentUnitId, String timeType, LocalDateTime dataTime, Long energyTypeId,
            EnergyUnitType childUnitType) {

        List<BranchAnalysisDTO> result = new ArrayList<>();
        TimeRange currentRange = calculateTimeRange(timeType, dataTime);

        // 如果未指定子节点类型，默认为支路
        if (childUnitType == null) {
            childUnitType = EnergyUnitType.BRANCH;
        }

        // 获取子用能单元（使用指定的类型）
        List<EnergyUnit> branches = energyUnitRepository.findByParentIdAndUnitTypeAndStatusOrderBySortOrderAsc(
                parentUnitId, childUnitType,
                DataItemStatus.ENABLE);

        if (branches.isEmpty()) {
            // 如果没有标记为支路的节点，则使用所有子节点
            branches = energyUnitRepository.findByParentIdOrderBySortOrderAsc(parentUnitId);
        }

        // 获取能源单位 (默认kWh)
        String unitName = "kWh";

        // 计算总能耗用于占比计算
        BigDecimal totalEnergy = BigDecimal.ZERO;
        List<Object[]> branchData = new ArrayList<>();

        for (EnergyUnit branch : branches) {
            BigDecimal consumption;
            if (energyTypeId != null) {
                consumption = energyDataRepository.sumByEnergyUnitAndEnergyTypeAndTimeRange(
                        branch.getId(), energyTypeId, timeType, currentRange.start, currentRange.end);
            } else {
                consumption = energyDataRepository.sumByEnergyUnitAndTimeRange(
                        branch.getId(), timeType, currentRange.start, currentRange.end);
            }
            consumption = consumption != null ? consumption : BigDecimal.ZERO;
            totalEnergy = totalEnergy.add(consumption);
            branchData.add(new Object[] { branch, consumption });
        }

        // 构建结果
        for (Object[] data : branchData) {
            EnergyUnit branch = (EnergyUnit) data[0];
            BigDecimal consumption = (BigDecimal) data[1];

            BranchAnalysisDTO dto = new BranchAnalysisDTO();
            dto.setBranchId(branch.getId());
            dto.setBranchName(branch.getName());
            dto.setVoltageLevel(branch.getVoltageLevel());
            dto.setRatedCurrent(branch.getRatedCurrent());
            dto.setRatedPower(branch.getRatedPower());
            dto.setTotalConsumption(consumption);
            dto.setUnit(unitName);

            // 计算占比
            if (totalEnergy.compareTo(BigDecimal.ZERO) > 0) {
                dto.setPercentage(consumption
                        .multiply(BigDecimal.valueOf(100))
                        .divide(totalEnergy, 2, RoundingMode.HALF_UP));
            } else {
                dto.setPercentage(BigDecimal.ZERO);
            }

            // 获取时段能耗明细
            List<BranchAnalysisDTO.TimeSlotData> timeSlotData = new ArrayList<>();
            List<Object[]> trendData = energyDataRepository.findTrendByEnergyUnit(
                    branch.getId(), getSubTimeType(timeType), currentRange.start, currentRange.end);

            DateTimeFormatter formatter = getFormatter(getSubTimeType(timeType));
            for (Object[] row : trendData) {
                BranchAnalysisDTO.TimeSlotData slot = new BranchAnalysisDTO.TimeSlotData();
                slot.setLabel(((LocalDateTime) row[0]).format(formatter));
                slot.setValue((BigDecimal) row[1]);
                timeSlotData.add(slot);
            }
            dto.setTimeSlotData(timeSlotData);

            result.add(dto);
        }

        return result;
    }

    // ==================== 对标分析 ====================

    /**
     * 获取对标分析
     * 对比实际能耗与标杆值
     *
     * @param parentUnitId  父用能单元ID
     * @param timeType      时间类型 (DAY/MONTH/YEAR)
     * @param dataTime      数据时间
     * @param benchmarkType 标杆类型（可选）
     * @return 对标分析列表
     */
    public List<BenchmarkAnalysisDTO> getBenchmarkAnalysis(
            Long parentUnitId, String timeType, LocalDateTime dataTime, String benchmarkType) {

        List<BenchmarkAnalysisDTO> result = new ArrayList<>();
        TimeRange currentRange = calculateTimeRange(timeType, dataTime);

        // 获取子用能单元
        List<EnergyUnit> childUnits = energyUnitRepository.findByParentIdOrderBySortOrderAsc(parentUnitId);

        for (EnergyUnit unit : childUnits) {
            BenchmarkAnalysisDTO dto = new BenchmarkAnalysisDTO();
            dto.setEnergyUnitId(unit.getId());
            dto.setEnergyUnitName(unit.getName());

            // 获取实际能耗（折标煤）
            BigDecimal actualConsumption = energyDataRepository.sumStandardCoalByEnergyUnitAndTimeRange(
                    unit.getId(), timeType, currentRange.start, currentRange.end);
            actualConsumption = actualConsumption != null ? actualConsumption : BigDecimal.ZERO;
            dto.setActualConsumption(actualConsumption);
            dto.setUnit("tce");

            // TODO: 获取对应的标杆值
            // 这里需要根据用能单元关联的标杆进行查询
            // 暂时使用模拟数据
            BigDecimal benchmarkValue = BigDecimal.ZERO;
            dto.setBenchmarkValue(benchmarkValue);
            dto.setBenchmarkGrade("--");
            dto.setBenchmarkCode("--");

            // 计算差异
            dto.setDifference(actualConsumption.subtract(benchmarkValue));

            // 计算达标率
            if (benchmarkValue.compareTo(BigDecimal.ZERO) > 0) {
                dto.setComplianceRate(actualConsumption
                        .multiply(BigDecimal.valueOf(100))
                        .divide(benchmarkValue, 2, RoundingMode.HALF_UP));
                dto.setIsCompliant(actualConsumption.compareTo(benchmarkValue) <= 0);
            } else {
                dto.setComplianceRate(BigDecimal.ZERO);
                dto.setIsCompliant(false);
            }

            // 评价
            if (dto.getIsCompliant()) {
                dto.setEvaluation("达标");
            } else if (dto.getComplianceRate().compareTo(BigDecimal.valueOf(120)) > 0) {
                dto.setEvaluation("差");
            } else if (dto.getComplianceRate().compareTo(BigDecimal.valueOf(110)) > 0) {
                dto.setEvaluation("中");
            } else {
                dto.setEvaluation("良");
            }

            result.add(dto);
        }

        return result;
    }
}
