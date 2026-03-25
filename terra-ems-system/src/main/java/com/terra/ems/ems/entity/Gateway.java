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
import com.fasterxml.jackson.annotation.JsonProperty;
import com.terra.ems.framework.enums.DataItemStatus;
import com.terra.ems.framework.jpa.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 网关实体
 *
 * 通信网关设备，负责采集现场计量器具的数据并上报至平台。
 * 一个网关可包含多个通信通道（CommChannel），每个通道连接多个计量器具。
 *
 * @author dengxueping
 * @since 2026-03-21
 */

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "ems_gateway", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "code" })
}, indexes = {
        @Index(name = "idx_gateway_code", columnList = "code"),
        @Index(name = "idx_gateway_energy_unit", columnList = "energy_unit_id")
})
@Schema(title = "网关")
public class Gateway extends BaseEntity {

    @Schema(title = "ID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(title = "网关编码", description = "唯一标识，如 GW-001")
    @Column(name = "code", length = 50, unique = true, nullable = false)
    private String code;

    @Schema(title = "网关名称")
    @Column(name = "name", length = 150, nullable = false)
    private String name;

    @Schema(title = "规格型号")
    @Column(name = "model", length = 150)
    private String model;

    @Schema(title = "生产厂商")
    @Column(name = "manufacturer", length = 255)
    private String manufacturer;

    @Schema(title = "IP 地址")
    @Column(name = "ip_address", length = 50)
    private String ipAddress;

    @Schema(title = "安装位置")
    @Column(name = "install_location", length = 300)
    private String installLocation;

    @Schema(title = "所属用能单元", description = "网关安装在哪个用能单元")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "energy_unit_id")
    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler", "children", "parent" })
    private EnergyUnit energyUnit;

    @Schema(title = "运行状态", description = "ONLINE/OFFLINE/UNKNOWN")
    @Column(name = "run_status", length = 20)
    private String runStatus = "UNKNOWN";

    @Schema(title = "最后心跳时间")
    @Column(name = "last_heartbeat")
    private LocalDateTime lastHeartbeat;

    @Schema(title = "CPU 使用率 (%)")
    @Column(name = "cpu_usage")
    private Double cpuUsage;

    @Schema(title = "内存使用率 (%)")
    @Column(name = "mem_usage")
    private Double memUsage;

    @Schema(title = "状态")
    @Column(name = "status", nullable = false)
    private DataItemStatus status = DataItemStatus.ENABLE;

    @Schema(title = "备注")
    @Column(name = "remark", length = 500)
    private String remark;

    // ============================================================================
    // @JsonProperty 桥接模式
    // ============================================================================

    @JsonProperty("energyUnitId")
    public Long getEnergyUnitId() {
        return energyUnit != null ? energyUnit.getId() : null;
    }

    @JsonProperty("energyUnitId")
    public void setEnergyUnitId(Long energyUnitId) {
        if (energyUnitId != null) {
            this.energyUnit = new EnergyUnit();
            this.energyUnit.setId(energyUnitId);
        } else {
            this.energyUnit = null;
        }
    }
}
