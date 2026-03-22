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

package com.terra.ems.ems.service.siteimport;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.terra.ems.ems.entity.*;
import com.terra.ems.ems.repository.*;
import com.terra.ems.framework.enums.DataItemStatus;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.*;

/**
 * 场站配置导入服务
 *
 * 解析 YAML 场站配置文件，按正确顺序创建全部实体和关联关系：
 * 1. 能源类型
 * 2. 用能单元树（递归）
 * 3. 网关 → 数据源
 * 4. 用能设备
 * 5. 计量器具
 * 6. 计量点
 * 7. 用能单元 ↔ 计量点 多对多关联
 *
 * @author dengxueping
 * @since 2026-03-21
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SiteImportService {

    private final EnergyTypeRepository energyTypeRepository;
    private final EnergyUnitRepository energyUnitRepository;
    private final GatewayRepository gatewayRepository;
    private final DataSourceRepository dataSourceRepository;
    private final EquipmentRepository equipmentRepository;
    private final MeterRepository meterRepository;
    private final MeterPointRepository meterPointRepository;

    private final ObjectMapper objectMapper;

    /**
     * 导入场站配置
     *
     * @param inputStream YAML 文件输入流
     * @return 导入结果统计
     */
    @Transactional
    public ImportResult importSiteConfig(InputStream inputStream) {
        // 解析 YAML
        Yaml yaml = new Yaml();
        Map<String, Object> rawData = yaml.load(inputStream);
        ObjectMapper mapper = new ObjectMapper();
        SiteConfigDto.SiteConfig config = mapper.convertValue(rawData, SiteConfigDto.SiteConfig.class);

        ImportResult result = new ImportResult();

        // 1. 导入能源类型
        Map<String, EnergyType> energyTypeMap = new HashMap<>();
        if (config.getEnergy_types() != null) {
            for (SiteConfigDto.EnergyTypeConfig etc : config.getEnergy_types()) {
                EnergyType et = energyTypeRepository.findByCode(etc.getCode())
                        .orElseGet(() -> {
                            EnergyType newEt = new EnergyType();
                            newEt.setCode(etc.getCode());
                            return newEt;
                        });
                et.setName(etc.getName());
                et.setUnit(etc.getUnit());
                if (etc.getCoefficient() != null) {
                    et.setCoefficient(etc.getCoefficient());
                }
                if (etc.getEmission_factor() != null) {
                    et.setEmissionFactor(etc.getEmission_factor());
                }
                et.setStatus(DataItemStatus.ENABLE);
                energyTypeRepository.save(et);
                energyTypeMap.put(et.getCode(), et);
                result.energyTypes++;
            }
            log.info("导入能源类型: {} 个", result.energyTypes);
        }

        // 2. 递归导入用能单元树及内嵌的网关、设备、仪表
        // 先收集所有网关和数据源的引用映射
        Map<String, Gateway> gatewayMap = new HashMap<>();
        Map<String, DataSource> dataSourceMap = new HashMap<>();

        if (config.getEnergy_units() != null) {
            for (SiteConfigDto.EnergyUnitConfig unitConfig : config.getEnergy_units()) {
                importEnergyUnit(unitConfig, null, 0, energyTypeMap, gatewayMap, dataSourceMap, result);
            }
        }

        log.info("场站配置导入完成: {}", result);
        return result;
    }

    /**
     * 递归导入用能单元
     */
    private void importEnergyUnit(
            SiteConfigDto.EnergyUnitConfig unitConfig,
            EnergyUnit parent,
            int level,
            Map<String, EnergyType> energyTypeMap,
            Map<String, Gateway> gatewayMap,
            Map<String, DataSource> dataSourceMap,
            ImportResult result
    ) {
        // 创建用能单元
        EnergyUnit unit = energyUnitRepository.findByCode(unitConfig.getCode())
                .orElseGet(() -> {
                    EnergyUnit newUnit = new EnergyUnit();
                    newUnit.setCode(unitConfig.getCode());
                    return newUnit;
                });
        unit.setName(unitConfig.getName());
        unit.setParent(parent);
        unit.setLevel(level);
        // 设置用能单元类型
        if (unitConfig.getType() != null) {
            try {
                unit.setUnitType(com.terra.ems.ems.enums.EnergyUnitType.valueOf(unitConfig.getType()));
            } catch (IllegalArgumentException e) {
                log.warn("未知的用能单元类型: {}，使用默认值 GENERAL", unitConfig.getType());
            }
        }
        unit.setStatus(DataItemStatus.ENABLE);
        unit = energyUnitRepository.save(unit);
        result.energyUnits++;

        // 导入该单元下的网关和数据源
        if (unitConfig.getGateways() != null) {
            for (SiteConfigDto.GatewayConfig gwConfig : unitConfig.getGateways()) {
                Gateway gw = importGateway(gwConfig, unit, dataSourceMap, result);
                gatewayMap.put(gw.getCode(), gw);
            }
        }

        // 导入该单元下的用能设备及其仪表
        if (unitConfig.getEquipments() != null) {
            for (SiteConfigDto.EquipmentConfig eqConfig : unitConfig.getEquipments()) {
                importEquipment(eqConfig, unit, energyTypeMap, gatewayMap, dataSourceMap, result);
            }
        }

        // 导入该单元下的独立仪表（不属于特定设备）
        if (unitConfig.getMeters() != null) {
            for (SiteConfigDto.MeterConfig meterConfig : unitConfig.getMeters()) {
                importMeter(meterConfig, unit, null, energyTypeMap, gatewayMap, dataSourceMap, result);
            }
        }

        // 递归导入子单元
        if (unitConfig.getChildren() != null) {
            for (SiteConfigDto.EnergyUnitConfig child : unitConfig.getChildren()) {
                importEnergyUnit(child, unit, level + 1, energyTypeMap, gatewayMap, dataSourceMap, result);
            }
        }
    }

    /**
     * 导入网关及其数据源
     */
    private Gateway importGateway(
            SiteConfigDto.GatewayConfig gwConfig,
            EnergyUnit energyUnit,
            Map<String, DataSource> dataSourceMap,
            ImportResult result
    ) {
        Gateway gw = gatewayRepository.findByCode(gwConfig.getCode())
                .orElseGet(() -> {
                    Gateway newGw = new Gateway();
                    newGw.setCode(gwConfig.getCode());
                    return newGw;
                });
        gw.setName(gwConfig.getName());
        gw.setModel(gwConfig.getModel());
        gw.setManufacturer(gwConfig.getManufacturer());
        gw.setIpAddress(gwConfig.getIp_address());
        gw.setInstallLocation(gwConfig.getInstall_location());
        gw.setEnergyUnit(energyUnit);
        gw.setStatus(DataItemStatus.ENABLE);
        gw = gatewayRepository.save(gw);
        result.gateways++;

        // 导入数据源
        if (gwConfig.getData_sources() != null) {
            for (SiteConfigDto.DataSourceConfig dsConfig : gwConfig.getData_sources()) {
                DataSource ds = importDataSource(dsConfig, gw, result);
                // 用 "网关编码:数据源名称" 作为 key，确保唯一
                dataSourceMap.put(gw.getCode() + ":" + ds.getName(), ds);
            }
        }

        return gw;
    }

    /**
     * 导入数据源
     */
    private DataSource importDataSource(
            SiteConfigDto.DataSourceConfig dsConfig,
            Gateway gateway,
            ImportResult result
    ) {
        DataSource ds = new DataSource();
        ds.setName(dsConfig.getName());
        ds.setGateway(gateway);
        ds.setProtocol(dsConfig.getProtocol());
        if (dsConfig.getConnection() != null) {
            try {
                ds.setConnection(objectMapper.writeValueAsString(dsConfig.getConnection()));
            } catch (Exception e) {
                log.warn("序列化 connection 失败: {}", e.getMessage());
            }
        }
        if (dsConfig.getPoll_interval_secs() != null) {
            ds.setPollIntervalSecs(dsConfig.getPoll_interval_secs());
        }
        ds.setStatus(DataItemStatus.ENABLE);
        ds = dataSourceRepository.save(ds);
        result.dataSources++;
        return ds;
    }

    /**
     * 导入用能设备及其仪表
     */
    private void importEquipment(
            SiteConfigDto.EquipmentConfig eqConfig,
            EnergyUnit energyUnit,
            Map<String, EnergyType> energyTypeMap,
            Map<String, Gateway> gatewayMap,
            Map<String, DataSource> dataSourceMap,
            ImportResult result
    ) {
        Equipment eq = equipmentRepository.findByCode(eqConfig.getCode())
                .orElseGet(() -> {
                    Equipment newEq = new Equipment();
                    newEq.setCode(eqConfig.getCode());
                    return newEq;
                });
        eq.setName(eqConfig.getName());
        eq.setType(eqConfig.getType());
        eq.setModelNumber(eqConfig.getModel_number());
        eq.setManufacturer(eqConfig.getManufacturer());
        eq.setRatedPower(eqConfig.getRated_power());
        eq.setLocation(eqConfig.getLocation());
        eq.setEnergyUnit(energyUnit);
        eq.setStatus(DataItemStatus.ENABLE);
        eq = equipmentRepository.save(eq);
        result.equipments++;

        // 导入设备下的仪表
        if (eqConfig.getMeters() != null) {
            for (SiteConfigDto.MeterConfig meterConfig : eqConfig.getMeters()) {
                importMeter(meterConfig, energyUnit, eq, energyTypeMap, gatewayMap, dataSourceMap, result);
            }
        }
    }

    /**
     * 导入计量器具及其计量点
     */
    private void importMeter(
            SiteConfigDto.MeterConfig meterConfig,
            EnergyUnit energyUnit,
            Equipment equipment,
            Map<String, EnergyType> energyTypeMap,
            Map<String, Gateway> gatewayMap,
            Map<String, DataSource> dataSourceMap,
            ImportResult result
    ) {
        // 查找能源类型
        EnergyType energyType = null;
        if (meterConfig.getEnergy_type() != null) {
            energyType = energyTypeMap.get(meterConfig.getEnergy_type());
        }

        // 查找关联的网关和数据源
        Gateway gateway = null;
        DataSource dataSource = null;
        if (meterConfig.getGateway() != null) {
            gateway = gatewayMap.get(meterConfig.getGateway());
            if (gateway != null && meterConfig.getData_source() != null) {
                dataSource = dataSourceMap.get(gateway.getCode() + ":" + meterConfig.getData_source());
            }
        }

        Meter meter = meterRepository.findByCode(meterConfig.getCode())
                .orElseGet(() -> {
                    Meter newMeter = new Meter();
                    newMeter.setCode(meterConfig.getCode());
                    return newMeter;
                });
        meter.setName(meterConfig.getName());
        meter.setType(meterConfig.getType());
        if (energyType != null) {
            meter.setEnergyType(energyType);
        }
        meter.setModelNumber(meterConfig.getModel_number());
        meter.setManufacturer(meterConfig.getManufacturer());
        meter.setLocation(meterConfig.getLocation());
        meter.setGateway(gateway);
        meter.setDataSource(dataSource);
        meter.setEquipment(equipment);
        meter.setStatus(DataItemStatus.ENABLE);

        // 通信参数 JSON
        if (meterConfig.getComm_params() != null) {
            try {
                meter.setCommParams(objectMapper.writeValueAsString(meterConfig.getComm_params()));
            } catch (Exception e) {
                log.warn("序列化 comm_params 失败: {}", e.getMessage());
            }
        }

        meter = meterRepository.save(meter);
        result.meters++;

        // 导入计量点
        if (meterConfig.getPoints() != null) {
            for (SiteConfigDto.PointConfig pointConfig : meterConfig.getPoints()) {
                importPoint(pointConfig, meter, energyUnit, energyTypeMap, result);
            }
        }
    }

    /**
     * 导入计量点，并建立与用能单元的多对多关联
     */
    private void importPoint(
            SiteConfigDto.PointConfig pointConfig,
            Meter meter,
            EnergyUnit energyUnit,
            Map<String, EnergyType> energyTypeMap,
            ImportResult result
    ) {
        // 查找能源类型
        EnergyType energyType = null;
        if (pointConfig.getEnergy_type() != null) {
            energyType = energyTypeMap.get(pointConfig.getEnergy_type());
        }

        MeterPoint point = meterPointRepository.findByCode(pointConfig.getCode())
                .orElseGet(() -> {
                    MeterPoint newPoint = new MeterPoint();
                    newPoint.setCode(pointConfig.getCode());
                    return newPoint;
                });
        point.setName(pointConfig.getName());
        point.setPointType(pointConfig.getType());
        point.setCategory(pointConfig.getCategory());
        point.setUnit(pointConfig.getUnit());
        point.setMeter(meter);
        if (energyType != null) {
            point.setEnergyType(energyType);
        }
        point.setStatus(DataItemStatus.ENABLE);

        // 采集参数 JSON
        if (pointConfig.getAcquisition_params() != null) {
            try {
                point.setAcquisitionParams(objectMapper.writeValueAsString(pointConfig.getAcquisition_params()));
            } catch (Exception e) {
                log.warn("序列化 acquisition_params 失败: {}", e.getMessage());
            }
        }

        // 关联用能单元（一对一：一个计量点只关联一个用能单元）
        point.getEnergyUnits().clear();
        if (energyUnit != null) {
            point.getEnergyUnits().add(energyUnit);
        }

        meterPointRepository.save(point);
        result.meterPoints++;
    }

    /**
     * 导入结果统计
     */
    @Data
    public static class ImportResult {
        private int energyTypes = 0;
        private int energyUnits = 0;
        private int gateways = 0;
        private int dataSources = 0;
        private int equipments = 0;
        private int meters = 0;
        private int meterPoints = 0;

        @Override
        public String toString() {
            return String.format(
                    "能源类型=%d, 用能单元=%d, 网关=%d, 数据源=%d, 用能设备=%d, 计量器具=%d, 计量点=%d",
                    energyTypes, energyUnits, gateways, dataSources, equipments, meters, meterPoints
            );
        }
    }
}
