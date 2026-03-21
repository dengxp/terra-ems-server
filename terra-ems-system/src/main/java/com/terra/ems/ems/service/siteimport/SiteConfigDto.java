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

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 场站配置文件 DTO
 *
 * 用于解析 YAML 场站配置文件，映射到 Java 对象。
 *
 * @author dengxueping
 * @since 2026-03-21
 */
public class SiteConfigDto {

    /** 顶层配置 */
    @Data
    public static class SiteConfig {
        private SiteInfo site;
        private List<EnergyTypeConfig> energy_types;
        private List<EnergyUnitConfig> energy_units;
    }

    /** 场站基本信息 */
    @Data
    public static class SiteInfo {
        private String name;
        private String code;
        private String industry;
        private String address;
        private String contact;
        private String description;
    }

    /** 能源类型 */
    @Data
    public static class EnergyTypeConfig {
        private String code;
        private String name;
        private String unit;
        private BigDecimal coefficient;
        private BigDecimal emission_factor;
    }

    /** 用能单元（递归） */
    @Data
    public static class EnergyUnitConfig {
        private String code;
        private String name;
        private String type;
        private List<GatewayConfig> gateways;
        private List<EquipmentConfig> equipments;
        private List<MeterConfig> meters;
        private List<EnergyUnitConfig> children;
    }

    /** 网关 */
    @Data
    public static class GatewayConfig {
        private String code;
        private String name;
        private String model;
        private String manufacturer;
        private String ip_address;
        private String install_location;
        private List<DataSourceConfig> data_sources;
    }

    /** 数据源 */
    @Data
    public static class DataSourceConfig {
        private String name;
        private String protocol;
        private Map<String, Object> connection;
        private Integer poll_interval_secs;
    }

    /** 用能设备 */
    @Data
    public static class EquipmentConfig {
        private String code;
        private String name;
        private String type;
        private String model_number;
        private String manufacturer;
        private BigDecimal rated_power;
        private String location;
        private List<MeterConfig> meters;
    }

    /** 计量器具 */
    @Data
    public static class MeterConfig {
        private String code;
        private String name;
        private String type;
        private String energy_type;
        private String model_number;
        private String manufacturer;
        private String location;
        private String gateway;
        private String data_source;
        private Map<String, Object> comm_params;
        private String data_source_type;  // HTTP 等无网关场景
        private List<PointConfig> points;
    }

    /** 计量点 */
    @Data
    public static class PointConfig {
        private String code;
        private String name;
        private String type;
        private String category;
        private String unit;
        private String energy_type;
        private Map<String, Object> acquisition_params;
        // sim_* 字段在导入时忽略
    }
}
