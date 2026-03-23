package com.terra.ems.ems.task;

import com.terra.ems.ems.entity.EnergyData;
import com.terra.ems.ems.entity.EnergyType;
import com.terra.ems.ems.entity.MeterPoint;
import com.terra.ems.ems.repository.EnergyDataRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/**
 * 能耗数据聚合定时任务
 *
 * - 日聚合：每天凌晨 1:00，将昨天的 HOUR 数据聚合为 DAY
 * - 月聚合：每月 1 号凌晨 2:00，将上月的 DAY 数据聚合为 MONTH
 * - 年聚合：每年 1 月 1 号凌晨 3:00，将上年的 MONTH 数据聚合为 YEAR
 *
 * 聚合幂等：使用 upsert 语义，重复执行不产生重复数据
 *
 * @author dengxueping
 * @since 2026-03-23
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class EnergyAggregationTask {

    private final EnergyDataRepository energyDataRepository;

    /**
     * 日聚合 — 每天凌晨 1:00 执行
     * 将昨天 00:00 ~ 今天 00:00 的 HOUR 数据聚合为一条 DAY 记录
     */
    @Scheduled(cron = "0 0 1 * * ?")
    @Transactional
    public void aggregateDaily() {
        LocalDate yesterday = LocalDate.now().minusDays(1);
        LocalDateTime start = yesterday.atStartOfDay();
        LocalDateTime end = yesterday.plusDays(1).atStartOfDay();

        log.info("开始日聚合: {} (HOUR → DAY)", yesterday);
        int count = aggregate("HOUR", "DAY", start, end, yesterday.atStartOfDay());
        log.info("日聚合完成: {} 条记录", count);
    }

    /**
     * 月聚合 — 每月 1 号凌晨 2:00 执行
     * 将上月所有 DAY 数据聚合为一条 MONTH 记录
     */
    @Scheduled(cron = "0 0 2 1 * ?")
    @Transactional
    public void aggregateMonthly() {
        LocalDate firstOfThisMonth = LocalDate.now().withDayOfMonth(1);
        LocalDate firstOfLastMonth = firstOfThisMonth.minusMonths(1);
        LocalDateTime start = firstOfLastMonth.atStartOfDay();
        LocalDateTime end = firstOfThisMonth.atStartOfDay();

        log.info("开始月聚合: {} (DAY → MONTH)", firstOfLastMonth.getMonth());
        int count = aggregate("DAY", "MONTH", start, end, firstOfLastMonth.atStartOfDay());
        log.info("月聚合完成: {} 条记录", count);
    }

    /**
     * 年聚合 — 每年 1 月 1 号凌晨 3:00 执行
     * 将上年所有 MONTH 数据聚合为一条 YEAR 记录
     */
    @Scheduled(cron = "0 0 3 1 1 ?")
    @Transactional
    public void aggregateYearly() {
        int lastYear = LocalDate.now().getYear() - 1;
        LocalDateTime start = LocalDate.of(lastYear, 1, 1).atStartOfDay();
        LocalDateTime end = LocalDate.of(lastYear + 1, 1, 1).atStartOfDay();

        log.info("开始年聚合: {} (MONTH → YEAR)", lastYear);
        int count = aggregate("MONTH", "YEAR", start, end, start);
        log.info("年聚合完成: {} 条记录", count);
    }

    /**
     * 通用聚合方法
     *
     * @param sourceType 源数据时间类型 (HOUR/DAY/MONTH)
     * @param targetType 目标时间类型 (DAY/MONTH/YEAR)
     * @param start      源数据起始时间（含）
     * @param end        源数据结束时间（不含）
     * @param dataTime   目标记录的 data_time
     * @return 聚合的记录数
     */
    private int aggregate(String sourceType, String targetType,
                          LocalDateTime start, LocalDateTime end, LocalDateTime dataTime) {
        List<Object[]> results = energyDataRepository.aggregateByTimeRange(sourceType, start, end);

        int count = 0;
        for (Object[] row : results) {
            Long meterPointId = (Long) row[0];
            Long energyTypeId = (Long) row[1];
            BigDecimal sumValue = (BigDecimal) row[2];

            if (sumValue == null || sumValue.compareTo(BigDecimal.ZERO) == 0) {
                continue;
            }

            // 幂等 upsert
            EnergyData data = energyDataRepository
                    .findByMeterPointIdAndTimeTypeAndDataTime(meterPointId, targetType, dataTime)
                    .orElseGet(() -> {
                        EnergyData newData = new EnergyData();
                        MeterPoint mp = new MeterPoint();
                        mp.setId(meterPointId);
                        newData.setMeterPoint(mp);
                        EnergyType et = new EnergyType();
                        et.setId(energyTypeId);
                        newData.setEnergyType(et);
                        newData.setTimeType(targetType);
                        newData.setDataTime(dataTime);
                        return newData;
                    });

            data.setValue(sumValue);
            energyDataRepository.save(data);
            count++;
        }

        return count;
    }
}
