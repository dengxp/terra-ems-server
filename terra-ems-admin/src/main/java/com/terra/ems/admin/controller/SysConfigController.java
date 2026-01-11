/*
 * Copyright (c) 2024 泰若科技（广州）有限公司. All rights reserved.
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

package com.terra.ems.admin.controller;

import com.terra.ems.common.domain.Result;
import com.terra.ems.framework.controller.BaseController;
import com.terra.ems.framework.service.BaseService;
import com.terra.ems.system.entity.SysConfig;
import com.terra.ems.system.service.SysConfigService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 参数配置控制器
 *
 * @author dengxueping
 * @since 2026-01-11
 */

@Tag(name = "参数配置")
@RestController
@RequestMapping("/system/config")
public class SysConfigController extends BaseController<SysConfig, Long> {

    private final SysConfigService configService;

    @Autowired
    public SysConfigController(SysConfigService configService) {
        this.configService = configService;
    }

    /**
     * 获取业务服务
     *
     * @return 系统配置服务
     */
    @Override
    protected BaseService<SysConfig, Long> getService() {
        return configService;
    }

    /**
     * 通过键名查询参数值
     *
     * @param configKey 参数键名
     * @return 参数值结果
     */
    @Operation(summary = "通过键名查询参数值")
    @GetMapping("/configKey/{configKey}")
    public Result<String> findConfigValue(@PathVariable String configKey) {
        return Result.content(configService.getConfigValue(configKey));
    }

    /**
     * 保存或更新参数配置
     *
     * @param config 参数配置实体
     * @return 操作结果及实体
     */
    @Operation(summary = "保存或更新配置")
    @Override
    @PreAuthorize("hasAnyAuthority('system:config:add', 'system:config:edit')")
    public Result<SysConfig> saveOrUpdate(SysConfig config) {
        return super.saveOrUpdate(config);
    }
}
