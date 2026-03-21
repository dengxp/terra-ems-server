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

/**
 * 通信通道实体
 *
 * 网关上的一个通信接口/通道，定义通信协议和参数。
 * 一个网关可以有多个通道（如多个 RS485 口），
 * 每个通道使用不同的协议和参数连接不同的计量器具。
 *
 * @author dengxueping
 * @since 2026-03-21
 */

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "ems_comm_channel", indexes = {
        @Index(name = "idx_comm_channel_gateway", columnList = "gateway_id")
})
@Schema(title = "通信通道")
public class CommChannel extends BaseEntity {

    @Schema(title = "ID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(title = "通道名称", description = "如 RS485-1口")
    @Column(name = "name", length = 150, nullable = false)
    private String name;

    @Schema(title = "所属网关")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gateway_id", nullable = false)
    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
    private Gateway gateway;

    @Schema(title = "通信协议", description = "MODBUS_RTU / MODBUS_TCP / DLT645 / BACNET / OPCUA")
    @Column(name = "protocol", length = 30, nullable = false)
    private String protocol;

    @Schema(title = "波特率", description = "串口通信波特率，如 9600、4800")
    @Column(name = "baud_rate")
    private Integer baudRate;

    @Schema(title = "数据位", description = "7 或 8")
    @Column(name = "data_bits")
    private Integer dataBits;

    @Schema(title = "停止位", description = "1 或 2")
    @Column(name = "stop_bits")
    private Integer stopBits;

    @Schema(title = "校验方式", description = "NONE / ODD / EVEN")
    @Column(name = "parity", length = 10)
    private String parity;

    @Schema(title = "端口号", description = "Modbus TCP 端口，如 502")
    @Column(name = "port")
    private Integer port;

    @Schema(title = "采集周期(秒)")
    @Column(name = "poll_interval_secs")
    private Integer pollIntervalSecs = 15;

    @Schema(title = "通信超时(毫秒)")
    @Column(name = "timeout_ms")
    private Integer timeoutMs = 3000;

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
}
