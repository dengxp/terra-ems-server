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

package com.terra.ems.ems.controller;

import com.terra.ems.common.domain.Result;
import com.terra.ems.ems.entity.AlarmConfig;
import com.terra.ems.ems.service.AlarmConfigService;
import com.terra.ems.framework.controller.BaseController;
import com.terra.ems.framework.service.BaseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import com.terra.ems.common.annotation.Log;
import com.terra.ems.common.enums.BusinessType;

/**
 * 预报警配置控制器
 *
 * @author dengxueping
 * @since 2026-01-11
 */
@RestController
@RequestMapping("/alarm/configs")
@Tag(name = "预报警配置")
@RequiredArgsConstructor
public class AlarmConfigController extends BaseController<AlarmConfig, Long> {

    private final AlarmConfigService alarmConfigService;

    /**
     * 获取业务服务
     *
     * @return 业务服务
     */
    @Override
    protected BaseService<AlarmConfig, Long> getService() {
        return alarmConfigService;
    }

    /**
     * 根据采集点位查询配置
     *
     * @param meterPointId 采集点位ID
     * @return 预报警配置列表
     */
    @Operation(summary = "根据采集点位查询配置")
    @GetMapping("/meter-point/{meterPointId}")
    public Result<List<AlarmConfig>> findByMeterPoint(@PathVariable Long meterPointId) {
        return Result.content(alarmConfigService.findByMeterPoint(meterPointId));
    }
}
