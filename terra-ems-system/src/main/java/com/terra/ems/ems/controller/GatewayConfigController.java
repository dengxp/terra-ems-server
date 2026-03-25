package com.terra.ems.ems.controller;

import com.terra.ems.ems.dto.ModbusDeviceDTO;
import com.terra.ems.ems.service.GatewayConfigService;
import com.terra.ems.common.domain.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "边缘网关配置分发接口", description = "供 terra-ems-gateway 拉取运行配置，免 Token 验证。")
@RestController
@RequestMapping("/public/gateway/config")
@RequiredArgsConstructor
public class GatewayConfigController {

    private final GatewayConfigService gatewayConfigService;

    @Operation(summary = "拉取指定网关的 Modbus 配置全集")
    @GetMapping("/modbus/{gatewayCode}")
    public Result<List<ModbusDeviceDTO>> getModbusConfig(@PathVariable String gatewayCode) {
        List<ModbusDeviceDTO> configs = gatewayConfigService.getModbusConfigForGateway(gatewayCode);
        return Result.content(configs);
    }
}
