package com.terra.ems.ems.controller;

import com.terra.ems.common.domain.Result;
import com.terra.ems.ems.entity.EnergyData;
import com.terra.ems.ems.repository.EnergyDataRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/energy-data")
@Tag(name = "能耗数据明细")
@RequiredArgsConstructor
public class EnergyDataController {

    private final EnergyDataRepository energyDataRepository;

    @Operation(summary = "按点位和时间范围查询历史能耗数据")
    @GetMapping("/point/{meterPointId}")
    public Result<List<EnergyData>> getPointEnergyData(
            @PathVariable Long meterPointId,
            @Parameter(description = "时间类型：HOUR/DAY/MONTH/YEAR") @RequestParam(defaultValue = "HOUR") String timeType,
            @Parameter(description = "开始时间") @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
            @Parameter(description = "结束时间") @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime) {
        return Result.content(energyDataRepository.findByMeterPointIdAndTimeTypeAndDataTimeBetween(
                meterPointId, timeType, startTime, endTime));
    }
}
