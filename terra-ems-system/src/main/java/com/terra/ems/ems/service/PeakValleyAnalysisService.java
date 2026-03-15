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
package com.terra.ems.ems.service;

import com.terra.ems.ems.entity.PeakValleyData;
import com.terra.ems.ems.entity.TimePeriodPrice;
import com.terra.ems.ems.enums.TimePeriodType;
import com.terra.ems.ems.repository.PeakValleyDataRepository;
import com.terra.ems.ems.repository.TimePeriodPriceRepository;
import com.terra.ems.framework.enums.DataItemStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;

/**
 * 尖峰平谷分析服务
 *
 * @author dengxueping
 * @since 2026-01-11
 */

@Service
@RequiredArgsConstructor
public class PeakValleyAnalysisService {

    private final PeakValleyDataRepository peakValleyDataRepository;
    private final TimePeriodPriceRepository timePeriodPriceRepository;

    /**
     * 按日分析 - 返回某一天各时段的用电量和电费
     */
    public PeakValleyAnalysisResult getDailyAnalysis(Long energyUnitId, LocalDate date) {
        List<PeakValleyData> dataList = peakValleyDataRepository
                .findByEnergyUnitIdAndDataTimeAndTimeType(energyUnitId, date, "HOUR");

        return buildAnalysisResult(dataList);
    }

    /**
     * 按月分析 - 返回某月各天各时段的用电量和电费
     */
    public PeakValleyAnalysisResult getMonthlyAnalysis(Long energyUnitId, YearMonth yearMonth) {
        LocalDate startDate = yearMonth.atDay(1);
        LocalDate endDate = yearMonth.atEndOfMonth();

        List<Object[]> summaryData = peakValleyDataRepository.sumByPeriodType(
                energyUnitId, startDate, endDate, "DAY");

        return buildAnalysisResultFromSummary(summaryData);
    }

    /**
     * 按年分析 - 返回某年各月各时段的用电量和电费
     */
    public PeakValleyAnalysisResult getYearlyAnalysis(Long energyUnitId, int year) {
        LocalDate startDate = LocalDate.of(year, 1, 1);
        LocalDate endDate = LocalDate.of(year, 12, 31);

        List<Object[]> summaryData = peakValleyDataRepository.sumByPeriodType(
                energyUnitId, startDate, endDate, "MONTH");

        return buildAnalysisResultFromSummary(summaryData);
    }

    /**
     * 时段汇总 - 返回指定日期范围内各时段的汇总数据
     */
    public PeakValleyAnalysisResult getPeriodSummary(Long energyUnitId, LocalDate startDate, LocalDate endDate) {
        List<Object[]> summaryData = peakValleyDataRepository.sumByPeriodType(
                energyUnitId, startDate, endDate, "DAY");

        return buildAnalysisResultFromSummary(summaryData);
    }

    /**
     * 获取按日期分组的详细数据
     */
    public List<DailyPeakValleyData> getDailyDetailedData(Long energyUnitId, LocalDate startDate, LocalDate endDate) {
        List<Object[]> data = peakValleyDataRepository.sumByDateAndPeriodType(
                energyUnitId, startDate, endDate, "DAY");

        Map<LocalDate, DailyPeakValleyData> dateMap = new LinkedHashMap<>();

        for (Object[] row : data) {
            LocalDate date = (LocalDate) row[0];
            TimePeriodType periodType = (TimePeriodType) row[1];
            BigDecimal electricity = (BigDecimal) row[2];
            BigDecimal cost = (BigDecimal) row[3];

            DailyPeakValleyData dailyData = dateMap.computeIfAbsent(date,
                    d -> new DailyPeakValleyData(d));
            dailyData.addPeriodData(periodType, electricity, cost);
        }

        return new ArrayList<>(dateMap.values());
    }

    /**
     * 获取电价配置列表
     */
    public List<TimePeriodPrice> getPriceConfigs() {
        return timePeriodPriceRepository.findByStatusOrderBySortOrderAsc(DataItemStatus.ENABLE);
    }

    // ========================= 私有方法 =========================

    private PeakValleyAnalysisResult buildAnalysisResult(List<PeakValleyData> dataList) {
        PeakValleyAnalysisResult result = new PeakValleyAnalysisResult();

        Map<TimePeriodType, PeriodSummary> periodSummaries = new EnumMap<>(TimePeriodType.class);
        BigDecimal totalElectricity = BigDecimal.ZERO;
        BigDecimal totalCost = BigDecimal.ZERO;

        for (PeakValleyData data : dataList) {
            TimePeriodType periodType = data.getPeriodType();
            PeriodSummary summary = periodSummaries.computeIfAbsent(periodType,
                    t -> new PeriodSummary(t));

            summary.addData(data.getElectricity(), data.getCost());
            totalElectricity = totalElectricity.add(
                    data.getElectricity() != null ? data.getElectricity() : BigDecimal.ZERO);
            totalCost = totalCost.add(
                    data.getCost() != null ? data.getCost() : BigDecimal.ZERO);
        }

        result.setPeriodSummaries(new ArrayList<>(periodSummaries.values()));
        result.setTotalElectricity(totalElectricity);
        result.setTotalCost(totalCost);
        result.calculatePercentages();

        return result;
    }

    private PeakValleyAnalysisResult buildAnalysisResultFromSummary(List<Object[]> summaryData) {
        PeakValleyAnalysisResult result = new PeakValleyAnalysisResult();

        List<PeriodSummary> summaries = new ArrayList<>();
        BigDecimal totalElectricity = BigDecimal.ZERO;
        BigDecimal totalCost = BigDecimal.ZERO;

        for (Object[] row : summaryData) {
            TimePeriodType periodType = (TimePeriodType) row[0];
            BigDecimal electricity = (BigDecimal) row[1];
            BigDecimal cost = (BigDecimal) row[2];

            PeriodSummary summary = new PeriodSummary(periodType);
            summary.setElectricity(electricity != null ? electricity : BigDecimal.ZERO);
            summary.setCost(cost != null ? cost : BigDecimal.ZERO);
            summaries.add(summary);

            totalElectricity = totalElectricity.add(summary.getElectricity());
            totalCost = totalCost.add(summary.getCost());
        }

        result.setPeriodSummaries(summaries);
        result.setTotalElectricity(totalElectricity);
        result.setTotalCost(totalCost);
        result.calculatePercentages();

        return result;
    }

    // ========================= 内部数据类 =========================

    /**
     * 分析结果
     */
    @lombok.Data
    public static class PeakValleyAnalysisResult {
        private List<PeriodSummary> periodSummaries = new ArrayList<>();
        private BigDecimal totalElectricity = BigDecimal.ZERO;
        private BigDecimal totalCost = BigDecimal.ZERO;

        public void calculatePercentages() {
            if (totalElectricity.compareTo(BigDecimal.ZERO) > 0) {
                for (PeriodSummary summary : periodSummaries) {
                    BigDecimal percentage = summary.getElectricity()
                            .divide(totalElectricity, 4, RoundingMode.HALF_UP)
                            .multiply(BigDecimal.valueOf(100));
                    summary.setPercentage(percentage);
                }
            }
        }
    }

    /**
     * 时段汇总
     */
    @lombok.Data
    public static class PeriodSummary {
        private TimePeriodType periodType;
        private String periodName;
        private BigDecimal electricity = BigDecimal.ZERO;
        private BigDecimal cost = BigDecimal.ZERO;
        private BigDecimal percentage = BigDecimal.ZERO;

        public PeriodSummary(TimePeriodType periodType) {
            this.periodType = periodType;
            this.periodName = periodType.getLabel();
        }

        public void addData(BigDecimal electricity, BigDecimal cost) {
            if (electricity != null) {
                this.electricity = this.electricity.add(electricity);
            }
            if (cost != null) {
                this.cost = this.cost.add(cost);
            }
        }
    }

    /**
     * 每日峰谷数据
     */
    @lombok.Data
    public static class DailyPeakValleyData {
        private LocalDate date;
        private Map<TimePeriodType, PeriodDetail> periodDetails = new EnumMap<>(TimePeriodType.class);
        private BigDecimal totalElectricity = BigDecimal.ZERO;
        private BigDecimal totalCost = BigDecimal.ZERO;

        public DailyPeakValleyData(LocalDate date) {
            this.date = date;
        }

        public void addPeriodData(TimePeriodType periodType, BigDecimal electricity, BigDecimal cost) {
            PeriodDetail detail = new PeriodDetail(periodType);
            detail.setElectricity(electricity != null ? electricity : BigDecimal.ZERO);
            detail.setCost(cost != null ? cost : BigDecimal.ZERO);
            periodDetails.put(periodType, detail);

            totalElectricity = totalElectricity.add(detail.getElectricity());
            totalCost = totalCost.add(detail.getCost());
        }
    }

    /**
     * 时段详情
     */
    @lombok.Data
    public static class PeriodDetail {
        private TimePeriodType periodType;
        private String periodName;
        private BigDecimal electricity = BigDecimal.ZERO;
        private BigDecimal cost = BigDecimal.ZERO;

        public PeriodDetail(TimePeriodType periodType) {
            this.periodType = periodType;
            this.periodName = periodType.getLabel();
        }
    }
}
