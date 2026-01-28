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

import com.terra.ems.ems.entity.EnergyCostRecord;
import com.terra.ems.ems.enums.RecordPeriodType;
import com.terra.ems.ems.repository.EnergyCostRecordRepository;
import com.terra.ems.framework.jpa.repository.BaseRepository;
import com.terra.ems.framework.service.BaseService;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.terra.ems.ems.dto.CostDeviationDTO;
import com.terra.ems.ems.dto.CostTrendDTO;
import com.terra.ems.ems.entity.EnergyType;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 能源成本记录服务
 *
 * @author dengxueping
 * @since 2026-01-11
 */

@Service
@RequiredArgsConstructor
public class EnergyCostRecordService extends BaseService<EnergyCostRecord, Long> {

    private final EnergyCostRecordRepository repository;

    @Override
    public BaseRepository<EnergyCostRecord, Long> getRepository() {
        return repository;
    }

    /**
     * 按用能单元和日期范围查询
     */
    public List<EnergyCostRecord> findByEnergyUnitAndDateRange(Long energyUnitId, LocalDate startDate,
            LocalDate endDate) {
        return repository.findByEnergyUnitIdAndRecordDateBetweenOrderByRecordDateDesc(energyUnitId, startDate, endDate);
    }

    /**
     * 按用能单元、周期类型和日期范围查询
     */
    public List<EnergyCostRecord> findByEnergyUnitAndPeriodType(Long energyUnitId, RecordPeriodType periodType,
            LocalDate startDate, LocalDate endDate) {
        return repository.findByEnergyUnitIdAndPeriodTypeAndRecordDateBetweenOrderByRecordDateAsc(energyUnitId,
                periodType, startDate, endDate);
    }

    /**
     * 统计日期范围内的总成本
     */
    public BigDecimal sumCostByDateRange(Long energyUnitId, LocalDate startDate, LocalDate endDate) {
        return repository.sumCostByDateRange(energyUnitId, startDate, endDate);
    }

    /**
     * 统计日期范围内的总用量
     */
    public BigDecimal sumConsumptionByDateRange(Long energyUnitId, LocalDate startDate, LocalDate endDate) {
        return repository.sumConsumptionByDateRange(energyUnitId, startDate, endDate);
    }

    /**
     * 创建成本记录
     */
    @Transactional(rollbackFor = Exception.class)
    public EnergyCostRecord create(EnergyCostRecord record) {
        if (record.getPeriodType() == null) {
            record.setPeriodType(RecordPeriodType.DAY);
        }
        return repository.save(record);
    }

    /**
     * 更新成本记录
     */
    @Transactional(rollbackFor = Exception.class)
    public EnergyCostRecord update(Long id, EnergyCostRecord record) {
        EnergyCostRecord existing = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("成本记录不存在: " + id));

        existing.setRecordDate(record.getRecordDate());
        existing.setPeriodType(record.getPeriodType());
        existing.setConsumption(record.getConsumption());
        existing.setCost(record.getCost());
        existing.setSharpConsumption(record.getSharpConsumption());
        existing.setPeakConsumption(record.getPeakConsumption());
        existing.setFlatConsumption(record.getFlatConsumption());
        existing.setValleyConsumption(record.getValleyConsumption());
        existing.setPowerFactor(record.getPowerFactor());
        existing.setRemark(record.getRemark());

        return repository.save(existing);
    }

    /**
     * 获取偏差分析数据
     */
    public CostDeviationDTO getDeviationAnalysis(Long energyUnitId, String timeType, LocalDate dataTime) {
        CostDeviationDTO result = new CostDeviationDTO();

        // 计算当期、同期、上期的日期范围
        LocalDate currentStart, currentEnd, lastYearStart, lastYearEnd, lastPeriodStart, lastPeriodEnd;

        if ("YEAR".equals(timeType)) {
            currentStart = dataTime.with(TemporalAdjusters.firstDayOfYear());
            currentEnd = dataTime.with(TemporalAdjusters.lastDayOfYear());
            lastYearStart = currentStart.minusYears(1);
            lastYearEnd = currentEnd.minusYears(1);
            lastPeriodStart = currentStart; // 年度没有环比概念
            lastPeriodEnd = currentEnd;
        } else {
            // MONTH
            currentStart = dataTime.with(TemporalAdjusters.firstDayOfMonth());
            currentEnd = dataTime.with(TemporalAdjusters.lastDayOfMonth());
            lastYearStart = currentStart.minusYears(1);
            lastYearEnd = currentEnd.minusYears(1);
            lastPeriodStart = currentStart.minusMonths(1);
            lastPeriodEnd = currentEnd.minusMonths(1);
        }

        // 获取当期数据
        List<EnergyCostRecord> currentRecords = energyUnitId != null
                ? repository.findByEnergyUnitIdAndRecordDateBetweenOrderByRecordDateDesc(energyUnitId, currentStart,
                        currentEnd)
                : repository.findByRecordDateBetween(currentStart, currentEnd);

        // 获取同期数据
        List<EnergyCostRecord> lastYearRecords = energyUnitId != null
                ? repository.findByEnergyUnitIdAndRecordDateBetweenOrderByRecordDateDesc(energyUnitId, lastYearStart,
                        lastYearEnd)
                : repository.findByRecordDateBetween(lastYearStart, lastYearEnd);

        // 获取上期数据
        List<EnergyCostRecord> lastPeriodRecords = energyUnitId != null
                ? repository.findByEnergyUnitIdAndRecordDateBetweenOrderByRecordDateDesc(energyUnitId, lastPeriodStart,
                        lastPeriodEnd)
                : repository.findByRecordDateBetween(lastPeriodStart, lastPeriodEnd);

        // 构建电力数据
        CostDeviationDTO.ElectricityData electricityData = buildElectricityData(currentRecords, lastYearRecords,
                lastPeriodRecords);
        result.setElectricityData(electricityData);

        // 构建统计数据
        CostDeviationDTO.StatisticsData statisticsData = buildStatisticsData(currentRecords, lastYearRecords,
                lastPeriodRecords);
        result.setStatisticsData(statisticsData);

        // 构建耗电明细（如果没有指定用能单元，则按用能单元分组）
        List<CostDeviationDTO.ConsumptionDetail> details = buildConsumptionDetails(currentRecords, lastYearRecords);
        result.setConsumptionDetails(details);

        return result;
    }

    private CostDeviationDTO.ElectricityData buildElectricityData(List<EnergyCostRecord> current,
            List<EnergyCostRecord> lastYear, List<EnergyCostRecord> lastPeriod) {
        CostDeviationDTO.ElectricityData data = new CostDeviationDTO.ElectricityData();

        BigDecimal currentTotal = sumConsumption(current);
        BigDecimal lastYearTotal = sumConsumption(lastYear);
        BigDecimal lastPeriodTotal = sumConsumption(lastPeriod);

        data.setTotalConsumption(currentTotal);
        data.setTotalConsumptionYoy(calcRate(currentTotal, lastYearTotal));
        data.setTotalConsumptionMom(calcRate(currentTotal, lastPeriodTotal));

        BigDecimal currentCost = sumCost(current);
        BigDecimal lastYearCost = sumCost(lastYear);
        BigDecimal lastPeriodCost = sumCost(lastPeriod);

        data.setTotalCost(currentCost);
        data.setTotalCostYoy(calcRate(currentCost, lastYearCost));
        data.setTotalCostMom(calcRate(currentCost, lastPeriodCost));

        data.setPowerFactor(avgPowerFactor(current));
        data.setPowerFactorYoy(calcRate(avgPowerFactor(current), avgPowerFactor(lastYear)));
        data.setPowerFactorMom(calcRate(avgPowerFactor(current), avgPowerFactor(lastPeriod)));

        data.setSharpConsumption(sumField(current, EnergyCostRecord::getSharpConsumption));
        data.setPeakConsumption(sumField(current, EnergyCostRecord::getPeakConsumption));
        data.setFlatConsumption(sumField(current, EnergyCostRecord::getFlatConsumption));
        data.setValleyConsumption(sumField(current, EnergyCostRecord::getValleyConsumption));

        return data;
    }

    private CostDeviationDTO.StatisticsData buildStatisticsData(List<EnergyCostRecord> current,
            List<EnergyCostRecord> lastYear, List<EnergyCostRecord> lastPeriod) {
        CostDeviationDTO.StatisticsData data = new CostDeviationDTO.StatisticsData();

        BigDecimal currentTotal = sumConsumption(current);
        BigDecimal lastYearTotal = sumConsumption(lastYear);
        BigDecimal lastPeriodTotal = sumConsumption(lastPeriod);

        data.setTotalConsumption(currentTotal);
        data.setTotalConsumptionYoy(calcRate(currentTotal, lastYearTotal));
        data.setTotalConsumptionMom(calcRate(currentTotal, lastPeriodTotal));
        data.setTotalConsumptionDiff(currentTotal.subtract(lastPeriodTotal));

        BigDecimal currentCost = sumCost(current);
        BigDecimal lastYearCost = sumCost(lastYear);
        BigDecimal lastPeriodCost = sumCost(lastPeriod);

        data.setTotalCost(currentCost);
        data.setTotalCostYoy(calcRate(currentCost, lastYearCost));
        data.setTotalCostMom(calcRate(currentCost, lastPeriodCost));
        data.setTotalCostDiff(currentCost.subtract(lastPeriodCost));

        data.setPowerFactor(avgPowerFactor(current));
        data.setPowerFactorYoy(calcRate(avgPowerFactor(current), avgPowerFactor(lastYear)));
        data.setPowerFactorMom(calcRate(avgPowerFactor(current), avgPowerFactor(lastPeriod)));
        data.setPowerFactorDiff(avgPowerFactor(current).subtract(avgPowerFactor(lastPeriod)));

        BigDecimal currentSharp = sumField(current, EnergyCostRecord::getSharpConsumption);
        BigDecimal currentPeak = sumField(current, EnergyCostRecord::getPeakConsumption);
        BigDecimal currentFlat = sumField(current, EnergyCostRecord::getFlatConsumption);
        BigDecimal currentValley = sumField(current, EnergyCostRecord::getValleyConsumption);

        BigDecimal lastSharp = sumField(lastPeriod, EnergyCostRecord::getSharpConsumption);
        BigDecimal lastPeak = sumField(lastPeriod, EnergyCostRecord::getPeakConsumption);
        BigDecimal lastFlat = sumField(lastPeriod, EnergyCostRecord::getFlatConsumption);
        BigDecimal lastValley = sumField(lastPeriod, EnergyCostRecord::getValleyConsumption);

        data.setSharpConsumption(currentSharp);
        data.setPeakConsumption(currentPeak);
        data.setFlatConsumption(currentFlat);
        data.setValleyConsumption(currentValley);

        data.setSharpConsumptionDiff(currentSharp.subtract(lastSharp));
        data.setPeakConsumptionDiff(currentPeak.subtract(lastPeak));
        data.setFlatConsumptionDiff(currentFlat.subtract(lastFlat));
        data.setValleyConsumptionDiff(currentValley.subtract(lastValley));

        return data;
    }

    private List<CostDeviationDTO.ConsumptionDetail> buildConsumptionDetails(List<EnergyCostRecord> current,
            List<EnergyCostRecord> lastYear) {
        Map<Long, List<EnergyCostRecord>> groupedCurrent = current.stream()
                .filter(r -> r.getEnergyUnit() != null)
                .collect(Collectors.groupingBy(r -> r.getEnergyUnit().getId()));

        Map<Long, List<EnergyCostRecord>> groupedLastYear = lastYear.stream()
                .filter(r -> r.getEnergyUnit() != null)
                .collect(Collectors.groupingBy(r -> r.getEnergyUnit().getId()));

        BigDecimal grandTotal = sumConsumption(current);

        List<CostDeviationDTO.ConsumptionDetail> details = new ArrayList<>();
        for (Map.Entry<Long, List<EnergyCostRecord>> entry : groupedCurrent.entrySet()) {
            Long unitId = entry.getKey();
            List<EnergyCostRecord> unitRecords = entry.getValue();
            List<EnergyCostRecord> unitLastYear = groupedLastYear.getOrDefault(unitId, new ArrayList<>());

            CostDeviationDTO.ConsumptionDetail detail = new CostDeviationDTO.ConsumptionDetail();
            detail.setEnergyUnitId(unitId);
            if (!unitRecords.isEmpty() && unitRecords.get(0).getEnergyUnit() != null) {
                detail.setEnergyUnitName(unitRecords.get(0).getEnergyUnit().getName());
            }

            BigDecimal unitTotal = sumConsumption(unitRecords);
            detail.setTotalConsumption(unitTotal);
            detail.setSharpConsumption(sumField(unitRecords, EnergyCostRecord::getSharpConsumption));
            detail.setPeakConsumption(sumField(unitRecords, EnergyCostRecord::getPeakConsumption));
            detail.setFlatConsumption(sumField(unitRecords, EnergyCostRecord::getFlatConsumption));
            detail.setValleyConsumption(sumField(unitRecords, EnergyCostRecord::getValleyConsumption));
            detail.setTotalCost(sumCost(unitRecords));
            detail.setYoy(calcRate(unitTotal, sumConsumption(unitLastYear)));
            detail.setPercentage(grandTotal.compareTo(BigDecimal.ZERO) > 0
                    ? unitTotal.multiply(new BigDecimal("100")).divide(grandTotal, 2, RoundingMode.HALF_UP)
                    : BigDecimal.ZERO);

            details.add(detail);
        }
        return details;
    }

    /**
     * 获取成本趋势分析数据
     */
    public CostTrendDTO getCostTrendAnalysis(Long energyUnitId, String timeType, LocalDate dataTime) {
        CostTrendDTO result = new CostTrendDTO();

        // 计算时间范围
        LocalDate startDate, endDate;
        int periods;
        String dateFormat;

        switch (timeType) {
            case "DAY":
                startDate = dataTime.with(TemporalAdjusters.firstDayOfMonth());
                endDate = dataTime.with(TemporalAdjusters.lastDayOfMonth());
                periods = endDate.getDayOfMonth();
                dateFormat = "dd";
                break;
            case "YEAR":
                startDate = dataTime.with(TemporalAdjusters.firstDayOfYear());
                endDate = dataTime.with(TemporalAdjusters.lastDayOfYear());
                periods = 12;
                dateFormat = "MM";
                break;
            default: // MONTH
                startDate = dataTime.with(TemporalAdjusters.firstDayOfYear());
                endDate = dataTime.with(TemporalAdjusters.lastDayOfYear());
                periods = 12;
                dateFormat = "MM";
                break;
        }

        // 获取记录
        List<EnergyCostRecord> records = energyUnitId != null
                ? repository.findByEnergyUnitIdAndRecordDateBetweenOrderByRecordDateDesc(energyUnitId, startDate,
                        endDate)
                : repository.findByRecordDateBetween(startDate, endDate);

        // 按能源类型分组
        Map<Long, List<EnergyCostRecord>> byEnergyType = records.stream()
                .filter(r -> r.getEnergyType() != null)
                .collect(Collectors.groupingBy(r -> r.getEnergyType().getId()));

        // 构建图表数据
        List<CostTrendDTO.TrendChartData> chartDataList = new ArrayList<>();
        for (Map.Entry<Long, List<EnergyCostRecord>> entry : byEnergyType.entrySet()) {
            List<EnergyCostRecord> typeRecords = entry.getValue();
            if (typeRecords.isEmpty())
                continue;

            EnergyType energyType = typeRecords.get(0).getEnergyType();

            CostTrendDTO.TrendChartData chartData = new CostTrendDTO.TrendChartData();
            chartData.setEnergyTypeId(energyType.getId());
            chartData.setEnergyTypeName(energyType.getName());
            chartData.setEnergyUnit(energyType.getUnit());
            chartData.setCostLabel(energyType.getName() + "费用");
            chartData.setConsumptionLabel(energyType.getName() + "消耗量");

            // 按日期分组
            Map<LocalDate, List<EnergyCostRecord>> byDate = typeRecords.stream()
                    .collect(Collectors.groupingBy(EnergyCostRecord::getRecordDate));

            List<String> timeLabels = new ArrayList<>();
            List<BigDecimal> costValues = new ArrayList<>();
            List<BigDecimal> consumptionValues = new ArrayList<>();

            LocalDate current = startDate;
            while (!current.isAfter(endDate)) {
                String label;
                LocalDate periodEnd;

                if ("DAY".equals(timeType)) {
                    label = String.format("%02d", current.getDayOfMonth());
                    periodEnd = current;
                } else {
                    label = String.format("%02d", current.getMonthValue());
                    periodEnd = current.with(TemporalAdjusters.lastDayOfMonth());
                }

                timeLabels.add(label);

                // 统计该时间段的数据
                BigDecimal periodCost = BigDecimal.ZERO;
                BigDecimal periodConsumption = BigDecimal.ZERO;

                LocalDate c = current;
                LocalDate pe = periodEnd;
                for (Map.Entry<LocalDate, List<EnergyCostRecord>> dateEntry : byDate.entrySet()) {
                    LocalDate d = dateEntry.getKey();
                    if (!d.isBefore(c) && !d.isAfter(pe)) {
                        for (EnergyCostRecord r : dateEntry.getValue()) {
                            periodCost = periodCost.add(r.getCost() != null ? r.getCost() : BigDecimal.ZERO);
                            periodConsumption = periodConsumption
                                    .add(r.getConsumption() != null ? r.getConsumption() : BigDecimal.ZERO);
                        }
                    }
                }

                costValues.add(periodCost);
                consumptionValues.add(periodConsumption);

                if ("DAY".equals(timeType)) {
                    current = current.plusDays(1);
                } else {
                    current = current.plusMonths(1);
                }
            }

            chartData.setTimeLabels(timeLabels);
            chartData.setCostValues(costValues);
            chartData.setConsumptionValues(consumptionValues);

            chartDataList.add(chartData);
        }

        result.setChartData(chartDataList);
        result.setTableItems(new ArrayList<>()); // 简化实现，表格数据可后续扩展

        return result;
    }

    // 辅助方法
    private BigDecimal sumConsumption(List<EnergyCostRecord> records) {
        return records.stream()
                .map(r -> r.getConsumption() != null ? r.getConsumption() : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal sumCost(List<EnergyCostRecord> records) {
        return records.stream()
                .map(r -> r.getCost() != null ? r.getCost() : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal avgPowerFactor(List<EnergyCostRecord> records) {
        List<BigDecimal> factors = records.stream()
                .filter(r -> r.getPowerFactor() != null)
                .map(EnergyCostRecord::getPowerFactor)
                .collect(Collectors.toList());
        if (factors.isEmpty())
            return BigDecimal.ZERO;
        return factors.stream().reduce(BigDecimal.ZERO, BigDecimal::add)
                .divide(new BigDecimal(factors.size()), 4, RoundingMode.HALF_UP);
    }

    private BigDecimal sumField(List<EnergyCostRecord> records,
            java.util.function.Function<EnergyCostRecord, BigDecimal> getter) {
        return records.stream()
                .map(r -> getter.apply(r) != null ? getter.apply(r) : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal calcRate(BigDecimal current, BigDecimal previous) {
        if (previous == null || previous.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        return current.subtract(previous)
                .multiply(new BigDecimal("100"))
                .divide(previous, 2, RoundingMode.HALF_UP);
    }
}
