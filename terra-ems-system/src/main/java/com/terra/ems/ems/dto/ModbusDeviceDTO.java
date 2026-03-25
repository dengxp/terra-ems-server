package com.terra.ems.ems.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ModbusDeviceDTO {
    @JsonProperty("name")
    private String name;
    
    @JsonProperty("device_code")
    private String deviceCode;
    
    @JsonProperty("gateway_code")
    private String gatewayCode;
    
    @JsonProperty("protocol")
    private String protocol;
    
    @JsonProperty("host")
    private String host;
    
    @JsonProperty("port")
    private Integer port;
    
    @JsonProperty("slave_id")
    private Integer slaveId;
    
    @JsonProperty("interval_secs")
    private Integer intervalSecs;
    
    @JsonProperty("timeout_ms")
    private Integer timeoutMs;
    
    @JsonProperty("retry_count")
    private Integer retryCount;

    @JsonProperty("baud_rate")
    private Integer baudRate;

    @JsonProperty("data_bits")
    private Integer dataBits;

    @JsonProperty("parity")
    private String parity;

    @JsonProperty("stop_bits")
    private Integer stopBits;
    
    @JsonProperty("registers")
    private List<ModbusRegisterDTO> registers;
}
