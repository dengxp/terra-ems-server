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

package com.terra.ems.ems.controller;

import com.terra.ems.common.domain.Result;
import com.terra.ems.ems.entity.*;
import com.terra.ems.ems.repository.AlarmConfigRepository;
import com.terra.ems.ems.repository.AlarmRecordRepository;
import com.terra.ems.ems.repository.EnergyDataRepository;
import com.terra.ems.ems.repository.GatewayRepository;
import com.terra.ems.ems.repository.MeterPointRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 数据处理器同步控制器
 *
 * 供 terra-ems-processor（Rust 云端数据处理器）调用，
 * 将聚合后的能耗数据同步写入 ems_energy_data 表。
 *
 * @author dengxueping
 * @since 2026-03-19
 */
@Slf4j
@RestController
@RequestMapping("/processor")
@Tag(name = "数据处理器同步接口")
@RequiredArgsConstructor
public class ProcessorSyncController {

    private final EnergyDataRepository energyDataRepository;
    private final MeterPointRepository meterPointRepository;
    private final GatewayRepository gatewayRepository;
    private final AlarmConfigRepository alarmConfigRepository;
    private final AlarmRecordRepository alarmRecordRepository;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * 同步能耗聚合数据
     *
     * 接收 processor 推送的聚合数据，写入 ems_energy_data 表。
     * 使用 UPSERT 语义：同一点位、同一时间、同一时间类型的数据会被更新。
     */
    @Operation(summary = "同步能耗聚合数据")
    @PostMapping("/sync-energy-data")
    public Result<SyncResult> syncEnergyData(@RequestBody List<SyncEnergyDataItem> items) {
        if (items == null || items.isEmpty()) {
            return Result.content(new SyncResult(0, 0, 0));
        }

        log.info("收到处理器数据同步请求，数据条数：{}", items.size());

        int accepted = 0;
        int updated = 0;
        int skipped = 0;

        for (SyncEnergyDataItem item : items) {
            try {
                // 查找采集点位
                Optional<MeterPoint> pointOpt = meterPointRepository.findById(item.getMeterPointId());
                if (pointOpt.isEmpty()) {
                    log.warn("采集点位不存在，跳过同步：meterPointId={}", item.getMeterPointId());
                    skipped++;
                    continue;
                }

                MeterPoint meterPoint = pointOpt.get();
                EnergyType energyType = meterPoint.getEnergyType();

                if (energyType == null) {
                    log.warn("采集点位未关联能源类型，跳过同步：meterPointId={}", item.getMeterPointId());
                    skipped++;
                    continue;
                }

                LocalDateTime dataTime = LocalDateTime.parse(item.getDataTime(), FORMATTER);

                // UPSERT：查找是否已存在相同时段的数据
                List<EnergyData> existing = energyDataRepository
                        .findByMeterPointIdAndTimeTypeAndDataTimeBetween(
                                meterPoint.getId(), item.getTimeType(), dataTime, dataTime);

                EnergyData data;
                if (existing.isEmpty()) {
                    data = new EnergyData();
                    data.setMeterPoint(meterPoint);
                    data.setEnergyType(energyType);
                    data.setTimeType(item.getTimeType());
                    data.setDataTime(dataTime);
                    accepted++;
                } else {
                    data = existing.get(0);
                    updated++;
                }

                data.setValue(BigDecimal.valueOf(item.getValue()));
                energyDataRepository.save(data);

            } catch (Exception e) {
                log.error("同步单条数据失败：meterPointId={}, error={}", item.getMeterPointId(), e.getMessage());
                skipped++;
            }
        }

        log.info("数据同步完成：新增={}, 更新={}, 跳过={}", accepted, updated, skipped);
        return Result.content(new SyncResult(accepted, updated, skipped));
    }

    /**
     * 同步网关状态和资源指标
     */
    @Operation(summary = "同步网关状态和资源指标")
    @PostMapping("/sync-gateway-status")
    public Result<String> syncGatewayStatus(@RequestBody SyncGatewayStatusItem item) {
        if (item == null || item.getCode() == null) {
            return Result.failure("网关编码不能为空");
        }

        Optional<Gateway> gatewayOpt = gatewayRepository.findByCode(item.getCode());
        if (gatewayOpt.isEmpty()) {
            return Result.failure("网关不存在：" + item.getCode());
        }

        Gateway gateway = gatewayOpt.get();
        gateway.setCpuUsage(item.getCpuUsage());
        gateway.setMemUsage(item.getMemUsage());
        gateway.setLastHeartbeat(LocalDateTime.now());
        gateway.setRunStatus(item.getOnline() ? "ONLINE" : "OFFLINE");
        
        gatewayRepository.save(gateway);

        return Result.success("网关状态更新成功");
    }

    /**
     * 获取所有采集点位映射信息
     *
     * 供 processor 启动时拉取，用于建立 point_code → (meter_point_id, energy_type_id) 映射。
     */
    @Operation(summary = "获取采集点位映射")
    @GetMapping("/point-mappings")
    public Result<List<PointMapping>> getPointMappings() {
        List<MeterPoint> points = meterPointRepository.findAll();

        List<PointMapping> mappings = points.stream()
                .map(p -> {
                    PointMapping mapping = new PointMapping();
                    mapping.setId(p.getId());
                    mapping.setCode(p.getCode());
                    mapping.setName(p.getName());
                    mapping.setEnergyTypeId(p.getEnergyType() != null ? p.getEnergyType().getId() : null);
                    mapping.setPointType(p.getPointType());
                    mapping.setMeasureType(p.getMeasureType());
                    mapping.setMinValue(p.getMinValue());
                    mapping.setMaxValue(p.getMaxValue());
                    mapping.setStepMin(p.getStepMin());
                    mapping.setStepMax(p.getStepMax());
                    return mapping;
                })
                .collect(Collectors.toList());

        return Result.content(mappings);
    }

    /**
     * 同步数据请求项
     */
    @Data
    public static class SyncEnergyDataItem {
        /** 采集点位ID */
        private Long meterPointId;
        /** 能源类型ID */
        private Long energyTypeId;
        /** 数据时间（格式：yyyy-MM-dd HH:mm:ss） */
        private String dataTime;
        /** 时间类型：HOUR / DAY / MONTH / YEAR */
        private String timeType;
        /** 能耗值 */
        private Double value;
    }

    /**
     * 同步结果
     */
    @Data
    public static class SyncResult {
        /** 新增条数 */
        private final int accepted;
        /** 更新条数 */
        private final int updated;
        /** 跳过条数 */
        private final int skipped;
    }

    /**
     * 点位映射信息
     */
    @Data
    public static class PointMapping {
        private Long id;
        private String code;
        private String name;
        private Long energyTypeId;
        private String pointType;
        private String measureType;
        private BigDecimal minValue;
        private BigDecimal maxValue;
        private BigDecimal stepMin;
        private BigDecimal stepMax;
    }

    /**
     * 网关状态同步项
     */
    @Data
    public static class SyncGatewayStatusItem {
        private String code;
        private Double cpuUsage;
        private Double memUsage;
        private Boolean online;
        private String lastHeartbeat;
    }

    // ============================================================
    // 告警相关接口
    // ============================================================

    /**
     * 获取告警配置（供 processor 加载告警规则）
     *
     * 返回所有已启用的告警配置，包含点位编码、限值类型、限值、比较运算符等。
     */
    @Operation(summary = "获取告警配置")
    @GetMapping("/alarm-configs")
    public Result<List<AlarmConfigItem>> getAlarmConfigs() {
        List<AlarmConfig> configs = alarmConfigRepository.findAll();

        List<AlarmConfigItem> items = configs.stream()
                .filter(c -> Boolean.TRUE.equals(c.getIsEnabled()))
                .filter(c -> c.getMeterPoint() != null && c.getAlarmLimitType() != null)
                .map(c -> {
                    AlarmConfigItem item = new AlarmConfigItem();
                    item.setConfigId(c.getId());
                    item.setPointCode(c.getMeterPoint().getCode());
                    item.setLimitValue(c.getLimitValue() != null ? c.getLimitValue().doubleValue() : 0.0);
                    item.setLimitTypeCode(c.getAlarmLimitType().getLimitCode());
                    item.setLimitTypeName(c.getAlarmLimitType().getLimitName());
                    item.setOperator(c.getAlarmLimitType().getComparatorOperator() != null
                            ? c.getAlarmLimitType().getComparatorOperator() : ">");
                    item.setAlarmType(c.getAlarmLimitType().getAlarmType() != null
                            ? c.getAlarmLimitType().getAlarmType() : "ALARM");
                    return item;
                })
                .collect(Collectors.toList());

        return Result.content(items);
    }

    /**
     * 接收采集服务推送的告警事件
     *
     * 将告警事件写入 ems_alarm_record 表。
     */
    @Operation(summary = "接收告警事件推送")
    @PostMapping("/push-alarm-events")
    public Result<AlarmPushResult> pushAlarmEvents(@RequestBody List<AlarmEventItem> events) {
        if (events == null || events.isEmpty()) {
            return Result.content(new AlarmPushResult(0, 0));
        }

        log.info("收到处理器告警事件推送，数量：{}", events.size());

        int recorded = 0;
        int skipped = 0;

        for (AlarmEventItem event : events) {
            try {
                // 只处理触发事件（恢复事件暂只记录日志）
                if (!"TRIGGERED".equals(event.getEventType())) {
                    log.info("告警恢复：点位={}, 类型={}", event.getPointCode(), event.getAlarmType());
                    continue;
                }

                // 查找对应的 AlarmConfig
                // 通过 pointCode 找 MeterPoint，再通过 limitTypeCode 找 AlarmConfig
                Optional<MeterPoint> pointOpt = meterPointRepository.findByCode(event.getPointCode());
                if (pointOpt.isEmpty()) {
                    skipped++;
                    continue;
                }

                // 查找匹配的 AlarmConfig（按 config_id 直接查）
                Optional<AlarmConfig> configOpt = alarmConfigRepository.findById(event.getConfigId());
                if (configOpt.isEmpty()) {
                    skipped++;
                    continue;
                }

                AlarmRecord record = new AlarmRecord();
                record.setAlarmConfig(configOpt.get());
                record.setTriggerValue(java.math.BigDecimal.valueOf(event.getCurrentValue()));
                record.setTriggerTime(LocalDateTime.parse(event.getTimestamp(), DateTimeFormatter.ISO_DATE_TIME));
                record.setStatus(0); // 未处理

                alarmRecordRepository.save(record);
                recorded++;

            } catch (Exception e) {
                log.error("处理告警事件失败：pointCode={}, error={}", event.getPointCode(), e.getMessage());
                skipped++;
            }
        }

        log.info("告警事件处理完成：记录={}, 跳过={}", recorded, skipped);
        return Result.content(new AlarmPushResult(recorded, skipped));
    }

    /**
     * 告警配置项（返回给 processor）
     */
    @Data
    public static class AlarmConfigItem {
        private Long configId;
        private String pointCode;
        private Double limitValue;
        private String limitTypeCode;
        private String limitTypeName;
        private String operator;
        private String alarmType;
    }

    /**
     * 告警事件项（processor 推送）
     */
    @Data
    public static class AlarmEventItem {
        private Long configId;
        private String pointCode;
        private String alarmType;
        private String level;
        private Double currentValue;
        private Double limitValue;
        private String eventType;
        private String timestamp;
        private String message;
    }

    /**
     * 告警推送结果
     */
    @Data
    public static class AlarmPushResult {
        private final int recorded;
        private final int skipped;
    }
}
