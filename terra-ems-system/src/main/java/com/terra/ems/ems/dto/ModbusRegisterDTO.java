package com.terra.ems.ems.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ModbusRegisterDTO {
    @JsonProperty("point_code")
    private String pointCode;
    
    @JsonProperty("name")
    private String name;
    
    @JsonProperty("address")
    private Integer address;
    
    @JsonProperty("function_code")
    private Integer functionCode;
    
    @JsonProperty("data_type")
    private String dataType;
    
    @JsonProperty("byte_order")
    private String byteOrder;
    
    @JsonProperty("scale")
    private Double scale;
    
    @JsonProperty("offset")
    private Double offset;
}
