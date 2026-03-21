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
 * 数据源实体
 *
 * 定义数据采集任务的配置：使用什么协议、连接哪个地址、多久采集一次。
 * 每个数据源关联一个网关，一个网关可以有多个数据源。
 * 支持的协议：modbus-tcp, modbus-rtu, mqtt, opc-ua, dlt645, bacnet-ip, http 等。
 *
 * 连接参数使用 JSON 格式存储，不同协议有不同的参数结构，
 * 这样新增协议时无需修改表结构。
 *
 * @author dengxueping
 * @since 2026-03-21
 */

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "ems_data_source", indexes = {
        @Index(name = "idx_data_source_gateway", columnList = "gateway_id"),
        @Index(name = "idx_data_source_protocol", columnList = "protocol")
})
@Schema(title = "数据源")
public class DataSource extends BaseEntity {

    @Schema(title = "ID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(title = "配置名称", description = "如"配电房 Modbus TCP"、"生产车间 RS485-1口"")
    @Column(name = "name", length = 150, nullable = false)
    private String name;

    @Schema(title = "所属网关")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gateway_id", nullable = false)
    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
    private Gateway gateway;

    @Schema(title = "采集协议", description = "modbus-tcp / modbus-rtu / mqtt / opc-ua / dlt645 / bacnet-ip / http / s7 / ...")
    @Column(name = "protocol", length = 30, nullable = false)
    private String protocol;

    @Schema(title = "连接参数", description = "JSON 格式，存储协议特有的连接配置")
    @Column(name = "connection", columnDefinition = "TEXT")
    private String connection;

    @Schema(title = "采集周期(秒)", description = "多久采集一次数据")
    @Column(name = "poll_interval_secs")
    private Integer pollIntervalSecs = 15;

    @Schema(title = "最后通信时间")
    @Column(name = "last_seen_time")
    private LocalDateTime lastSeenTime;

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
