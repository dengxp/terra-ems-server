package com.terra.ems.ems.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.terra.ems.ems.dto.ModbusDeviceDTO;
import com.terra.ems.ems.dto.ModbusRegisterDTO;
import com.terra.ems.ems.entity.DataSource;
import com.terra.ems.ems.entity.Meter;
import com.terra.ems.ems.entity.MeterPoint;
import com.terra.ems.ems.repository.DataSourceRepository;
import com.terra.ems.ems.repository.MeterPointRepository;
import com.terra.ems.ems.repository.MeterRepository;
import com.terra.ems.framework.enums.DataItemStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class GatewayConfigService {

    private final DataSourceRepository dataSourceRepository;
    private final MeterRepository meterRepository;
    private final MeterPointRepository meterPointRepository;
    private final ObjectMapper objectMapper;

    /**
     * 生成提供给 Collector 的 Modbus 配置 JSON 输出
     * @param gatewayCode 网关编码
     * @return 该网关下的 Modbus 设备及测点配置列表
     */
    @Transactional(readOnly = true)
    public List<ModbusDeviceDTO> getModbusConfigForGateway(String gatewayCode) {
        List<ModbusDeviceDTO> deviceConfigs = new ArrayList<>();
        
        List<DataSource> dataSources = dataSourceRepository.findAll().stream()
                .filter(ds -> DataItemStatus.ENABLE.equals(ds.getStatus()))
                .filter(ds -> ds.getGateway() != null && gatewayCode.equals(ds.getGateway().getCode()))
                .filter(ds -> ds.getProtocol() != null && (ds.getProtocol().startsWith("modbus") || ds.getProtocol().equals("dlt645")))
                .toList();

        for (DataSource ds : dataSources) {
            // 解析 DataSource 连接参数
            String host = "127.0.0.1";
            int port = 502;
            Integer baudRate = null;
            Integer dataBits = null;
            String parity = null;
            Integer stopBits = null;

            try {
                if (ds.getConnection() != null && !ds.getConnection().isEmpty()) {
                    JsonNode connNode = objectMapper.readTree(ds.getConnection());
                    if (connNode.has("host")) host = connNode.get("host").asText();
                    if (connNode.has("port")) port = connNode.get("port").asInt();
                    if (connNode.has("baudRate")) baudRate = connNode.get("baudRate").asInt();
                    if (connNode.has("dataBits")) dataBits = connNode.get("dataBits").asInt();
                    if (connNode.has("parity")) parity = connNode.get("parity").asText();
                    if (connNode.has("stopBits")) stopBits = connNode.get("stopBits").asInt();
                }
            } catch (Exception e) {
                log.warn("无法解析 DataSource (ID:{}) 的 connection JSON: {}", ds.getId(), e.getMessage());
            }
            
            String protocol;
            if ("modbus-tcp".equals(ds.getProtocol())) {
                protocol = "tcp";
            } else if ("modbus-rtu".equals(ds.getProtocol())) {
                protocol = "rtu";
            } else {
                protocol = ds.getProtocol(); // dlt645 等协议直接透传
            }

            // 2. 查询该数据源下的 Meter
            List<Meter> meters = meterRepository.findAll().stream()
                    .filter(m -> DataItemStatus.ENABLE.equals(m.getStatus()))
                    .filter(m -> m.getDataSource() != null && m.getDataSource().getId().equals(ds.getId()))
                    .toList();

            for (Meter meter : meters) {
                ModbusDeviceDTO deviceDTO = new ModbusDeviceDTO();
                deviceDTO.setDeviceCode(meter.getCode());
                deviceDTO.setName(meter.getName());
                deviceDTO.setGatewayCode(gatewayCode);
                deviceDTO.setProtocol(protocol);
                deviceDTO.setHost(host);
                deviceDTO.setPort(port);
                deviceDTO.setBaudRate(baudRate);
                deviceDTO.setDataBits(dataBits);
                deviceDTO.setParity(parity);
                deviceDTO.setStopBits(stopBits);
                
                // 默认设置
                deviceDTO.setSlaveId(1);
                deviceDTO.setIntervalSecs(ds.getPollIntervalSecs() != null ? ds.getPollIntervalSecs() : 15);
                deviceDTO.setTimeoutMs(3000);
                deviceDTO.setRetryCount(3);

                // 解析 Meter 的通信参数
                try {
                    if (meter.getCommParams() != null && !meter.getCommParams().isEmpty()) {
                        JsonNode commNode = objectMapper.readTree(meter.getCommParams());
                        if (commNode.has("slaveId")) deviceDTO.setSlaveId(commNode.get("slaveId").asInt());
                        if (commNode.has("intervalSecs")) deviceDTO.setIntervalSecs(commNode.get("intervalSecs").asInt());
                        if (commNode.has("timeoutMs")) deviceDTO.setTimeoutMs(commNode.get("timeoutMs").asInt());
                        if (commNode.has("retryCount")) deviceDTO.setRetryCount(commNode.get("retryCount").asInt());
                    }
                } catch (Exception e) {
                    log.warn("无法解析 Meter (ID:{}) 的 commParams JSON: {}", meter.getId(), e.getMessage());
                }

                // 3. 查找该 Meter 下所有的 MeterPoint
                List<MeterPoint> points = meterPointRepository.findAll().stream()
                        .filter(p -> DataItemStatus.ENABLE.equals(p.getStatus()))
                        .filter(p -> "COLLECT".equals(p.getPointType()))
                        .filter(p -> p.getMeter() != null && p.getMeter().getId().equals(meter.getId()))
                        .toList();
                
                List<ModbusRegisterDTO> registers = new ArrayList<>();
                for (MeterPoint point : points) {
                    ModbusRegisterDTO reg = new ModbusRegisterDTO();
                    reg.setPointCode(point.getCode());
                    reg.setName(point.getName());
                    
                    // 默认设置
                    reg.setAddress(0);
                    reg.setFunctionCode(3);
                    reg.setDataType("float32");
                    reg.setByteOrder("big_endian");
                    reg.setScale(1.0);
                    reg.setOffset(0.0);

                    // 解析采集参数
                    try {
                        if (point.getAcquisitionParams() != null && !point.getAcquisitionParams().isEmpty()) {
                            JsonNode acqNode = objectMapper.readTree(point.getAcquisitionParams());
                            if (acqNode.has("address")) reg.setAddress(acqNode.get("address").asInt());
                            if (acqNode.has("functionCode")) reg.setFunctionCode(acqNode.get("functionCode").asInt());
                            if (acqNode.has("dataType")) reg.setDataType(acqNode.get("dataType").asText());
                            if (acqNode.has("byteOrder")) reg.setByteOrder(acqNode.get("byteOrder").asText());
                            if (acqNode.has("scale")) reg.setScale(acqNode.get("scale").asDouble());
                            if (acqNode.has("offset")) reg.setOffset(acqNode.get("offset").asDouble());
                        }
                    } catch (Exception e) {
                        log.warn("无法解析 MeterPoint (ID:{}) 的 acquisitionParams JSON: {}", point.getId(), e.getMessage());
                    }

                    registers.add(reg);
                }
                
                deviceDTO.setRegisters(registers);
                
                // 只有包含至少一个采集点才下发设备
                if (!registers.isEmpty()) {
                    deviceConfigs.add(deviceDTO);
                }
            }
        }
        
        return deviceConfigs;
    }
}
