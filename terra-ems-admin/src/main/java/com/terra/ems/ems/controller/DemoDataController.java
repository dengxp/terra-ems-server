/*
 * Copyright (c) 2024 泰若科技（广州）有限公司. All rights reserved.
 */

package com.terra.ems.ems.controller;

import com.terra.ems.common.domain.Result;
import com.terra.ems.ems.entity.EnergyData;
import com.terra.ems.ems.entity.EnergyType;
import com.terra.ems.ems.entity.EnergyUnit;
import com.terra.ems.ems.entity.MeterPoint;
import com.terra.ems.ems.repository.EnergyDataRepository;
import com.terra.ems.ems.repository.EnergyTypeRepository;
import com.terra.ems.ems.repository.EnergyUnitRepository;
import com.terra.ems.ems.repository.MeterPointRepository;
import com.terra.ems.framework.enums.DataItemStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import com.terra.ems.common.annotation.Log;
import com.terra.ems.common.enums.BusinessType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

/**
 * 演示数据生成控制器
 * 仅用于开发和演示环境下模拟数据
 */
@RestController
@RequestMapping("/demo")
@Tag(name = "演示数据生成")
@RequiredArgsConstructor
public class DemoDataController {

    private final EnergyUnitRepository energyUnitRepository;
    private final MeterPointRepository meterPointRepository;
    private final EnergyTypeRepository energyTypeRepository;
    private final EnergyDataRepository energyDataRepository;

    @Operation(summary = "生成能耗排名演示数据")
    @Log(title = "演示数据", businessType = BusinessType.INSERT)
    @PreAuthorize("hasRole('admin')")
    @PostMapping("/generate-ranking-data")
    public Result<String> generateRankingData(@RequestParam Long parentUnitId) {
        List<EnergyUnit> childUnits = energyUnitRepository.findByParentIdOrderBySortOrderAsc(parentUnitId);
        if (childUnits.isEmpty()) {
            return Result.failure("该单元下没有子单元，无法生成排名数据");
        }

        // 获取电力能源类型
        EnergyType electricType = energyTypeRepository.findByCode("ELECTRIC")
                .orElseGet(() -> {
                    EnergyType type = new EnergyType();
                    type.setCode("ELECTRIC");
                    type.setName("电力");
                    type.setUnit("kWh");
                    type.setCoefficient(new BigDecimal("0.1229")); // 折标煤系数
                    type.setEmissionFactor(new BigDecimal("0.5703")); // 碳排放因子
                    type.setStatus(DataItemStatus.ENABLE);
                    return energyTypeRepository.save(type);
                });

        Random random = new Random();
        LocalDateTime now = LocalDateTime.now();

        for (int i = 0; i < childUnits.size(); i++) {
            EnergyUnit unit = childUnits.get(i);

            // 获取或创建采集点位
            List<MeterPoint> points = meterPointRepository.findByEnergyUnitId(unit.getId());
            MeterPoint point;
            if (points.isEmpty()) {
                point = new MeterPoint();
                point.setCode("DEMO_" + unit.getCode());
                point.setName(unit.getName() + "演示点位");
                point.setPointType("COLLECT");
                point.setEnergyType(electricType);
                point.setStatus(DataItemStatus.ENABLE);
                point.getEnergyUnits().add(unit);
                point = meterPointRepository.save(point);
            } else {
                point = points.get(0);
            }

            // 为每个单位生成不同的基础值，创造排名差异
            double baseValue = 500.0 + (childUnits.size() - i) * 100.0 + random.nextDouble() * 50.0;

            // 生成过去12个月的数据
            for (int m = 0; m < 12; m++) {
                LocalDateTime monthTime = now.minusMonths(m).withDayOfMonth(1).toLocalDate().atStartOfDay();
                saveData(point, electricType, "MONTH", monthTime,
                        BigDecimal.valueOf(baseValue + random.nextDouble() * 100));
            }

            // 生成过去30天的数据
            for (int d = 0; d < 30; d++) {
                LocalDateTime dayTime = now.minusDays(d).toLocalDate().atStartOfDay();
                saveData(point, electricType, "DAY", dayTime,
                        BigDecimal.valueOf(baseValue / 30.0 + random.nextDouble() * 10));
            }

            // 生成今年的数据
            LocalDateTime yearTime = now.withDayOfYear(1).toLocalDate().atStartOfDay();
            saveData(point, electricType, "YEAR", yearTime,
                    BigDecimal.valueOf(baseValue * 12 + random.nextDouble() * 500));
        }

        return Result.success("演示数据生成成功，影响单元数量：" + childUnits.size());
    }

    private void saveData(MeterPoint point, EnergyType type, String timeType, LocalDateTime time, BigDecimal value) {
        // 简单防重：如果该点位在该时间点已有数据，则不插入（或可改为更新）
        List<EnergyData> existing = energyDataRepository.findByMeterPointIdAndTimeTypeAndDataTimeBetween(
                point.getId(), timeType, time, time);

        EnergyData data;
        if (existing.isEmpty()) {
            data = new EnergyData();
            data.setMeterPoint(point);
            data.setEnergyType(type);
            data.setTimeType(timeType);
            data.setDataTime(time);
        } else {
            data = existing.get(0);
        }
        data.setValue(value);
        energyDataRepository.save(data);
    }
}
