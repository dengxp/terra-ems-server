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

package com.terra.ems.ems.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.terra.ems.framework.enums.DataItemStatus;
import com.terra.ems.framework.jpa.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

/**
 * 计量器具档案实体
 *
 * @author dengxueping
 * @since 2026-01-11
 */

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "ems_meter", uniqueConstraints = {
                @UniqueConstraint(columnNames = { "code" })
}, indexes = {
                @Index(name = "idx_meter_code", columnList = "code"),
                @Index(name = "idx_meter_type", columnList = "type"),
                @Index(name = "idx_meter_energy_type", columnList = "energy_type_id")
})
@Schema(title = "计量器具档案")
public class Meter extends BaseEntity {

        @Schema(title = "ID")
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Schema(title = "器具编码")
        @Column(name = "code", length = 50, unique = true, nullable = false)
        private String code;

        @Schema(title = "器具名称")
        @Column(name = "name", length = 150, nullable = false)
        private String name;

        @Schema(title = "器具类型", description = "字典：sys_device_type")
        @Column(name = "type", length = 20, nullable = false)
        private String type;

        @Schema(title = "能源类型")
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "energy_type_id", nullable = false)
        @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
        private EnergyType energyType;

        @Schema(title = "规格型号")
        @Column(name = "model_number", length = 150)
        private String modelNumber;

        @Schema(title = "测量范围")
        @Column(name = "measure_range", length = 150)
        private String measureRange;

        @Schema(title = "生产厂商")
        @Column(name = "manufacturer", length = 255)
        private String manufacturer;

        @Schema(title = "负责人")
        @Column(name = "person_charge", length = 200)
        private String personCharge;

        @Schema(title = "安装位置")
        @Column(name = "location", length = 300)
        private String location;

        @Schema(title = "起始时间")
        @Column(name = "start_time")
        private LocalDate startTime;

        @Schema(title = "投运时间")
        @Column(name = "putrun_time")
        private LocalDate putrunTime;

        @Schema(title = "检定周期(天)")
        @Column(name = "check_cycle")
        private Integer checkCycle;

        @Schema(title = "提醒周期(天)")
        @Column(name = "reminder_cycle")
        private Integer reminderCycle;

        @Schema(title = "最大允许功率")
        @Column(name = "max_power", length = 255)
        private String maxPower;

        @Schema(title = "线经")
        @Column(name = "wire_diameter", length = 255)
        private String wireDiameter;

        @Schema(title = "所属网关", description = "直接关联网关（无通道时使用，可选）")
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "gateway_id")
        @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
        private Gateway gateway;

        @Schema(title = "采集配置", description = "通过哪个采集配置采集数据（可选）")
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "acquisition_config_id")
        @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
        private AcquisitionConfig acquisitionConfig;

        @Schema(title = "所属用能设备", description = "给哪台设备计量（可选，总表无设备）")
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "equipment_id")
        @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
        private Equipment equipment;

        @Schema(title = "通信参数", description = "JSON 格式，存储协议特有的设备级参数（如 Modbus 从站地址、DL/T645 电表地址等）")
        @Column(name = "comm_params", columnDefinition = "TEXT")
        private String commParams;

        @Schema(title = "状态")
        @Column(name = "status", nullable = false)
        private DataItemStatus status = DataItemStatus.ENABLE;

        @Schema(title = "备注")
        @Column(name = "remark", length = 500)
        private String remark;

        // ============================================================================
        // @JsonProperty 桥接模式
        // ============================================================================

        @JsonProperty("gatewayId")
        public Long getGatewayId() {
                return gateway != null ? gateway.getId() : null;
        }

        @JsonProperty("gatewayId")
        public void setGatewayId(Long gatewayId) {
                if (gatewayId != null) {
                        this.gateway = new Gateway();
                        this.gateway.setId(gatewayId);
                } else {
                        this.gateway = null;
                }
        }

        @JsonProperty("acquisitionConfigId")
        public Long getAcquisitionConfigId() {
                return acquisitionConfig != null ? acquisitionConfig.getId() : null;
        }

        @JsonProperty("acquisitionConfigId")
        public void setAcquisitionConfigId(Long acquisitionConfigId) {
                if (acquisitionConfigId != null) {
                        this.acquisitionConfig = new AcquisitionConfig();
                        this.acquisitionConfig.setId(acquisitionConfigId);
                } else {
                        this.acquisitionConfig = null;
                }
        }

        @JsonProperty("equipmentId")
        public Long getEquipmentId() {
                return equipment != null ? equipment.getId() : null;
        }

        @JsonProperty("equipmentId")
        public void setEquipmentId(Long equipmentId) {
                if (equipmentId != null) {
                        this.equipment = new Equipment();
                        this.equipment.setId(equipmentId);
                } else {
                        this.equipment = null;
                }
        }
}
