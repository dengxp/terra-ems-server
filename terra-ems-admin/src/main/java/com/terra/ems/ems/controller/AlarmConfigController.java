/*
 * Copyright (c) 2024-2026 Terra Technology (Guangzhou) Co., Ltd.
 * Copyright (c) 2024-2026 泰若科技（广州）有限公司.
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;

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
     * 保存或更新预报警配置
     */
    @Operation(summary = "保存或更新预报警配置")
    @PostMapping
    @Override
    @Log(title = "预报警配置", businessType = BusinessType.UPDATE)
    @PreAuthorize("hasAnyPerm('ems:alarm-config:add', 'ems:alarm-config:edit')")
    public Result<AlarmConfig> saveOrUpdate(@Validated @RequestBody AlarmConfig alarmConfig) {
        return super.saveOrUpdate(alarmConfig);
    }

    /**
     * 删除预报警配置
     */
    @Operation(summary = "删除数据")
    @PreAuthorize("hasPerm('ems:alarm-config:remove')")
    @Log(title = "预报警配置", businessType = BusinessType.DELETE)
    @DeleteMapping("/{id}")
    @Override
    public Result<String> delete(@PathVariable Long id) {
        return super.delete(id);
    }

    /**
     * 批量删除预报警配置
     */
    @Operation(summary = "批量删除数据")
    @PreAuthorize("hasPerm('ems:alarm-config:remove')")
    @Log(title = "预报警配置", businessType = BusinessType.DELETE)
    @DeleteMapping
    @Override
    public Result<String> deleteBatch(@RequestBody List<Long> ids) {
        return super.deleteBatch(ids);
    }

    /**
     * 根据采集点位查询配置
     *
     * @param meterPointId 采集点位ID
     * @return 预报警配置列表
     */
    @Operation(summary = "根据采集点位查询配置")
    @PreAuthorize("hasPerm('ems:alarm-config:list')")
    @GetMapping("/meter-point/{meterPointId}")
    public Result<List<AlarmConfig>> findByMeterPoint(@PathVariable Long meterPointId) {
        return Result.content(alarmConfigService.findByMeterPoint(meterPointId));
    }

    /**
     * 根据ID查询预报警配置
     */
    @Operation(summary = "按ID查询")
    @PreAuthorize("hasPerm('ems:alarm-config:query')")
    @GetMapping("/{id}")
    @Override
    public Result<AlarmConfig> findById(@PathVariable Long id) {
        return super.findById(id);
    }

    /**
     * 查询所有预报警配置
     */
    @Operation(summary = "查询所有数据")
    @PreAuthorize("hasPerm('ems:alarm-config:list')")
    @GetMapping("/all")
    public Result<List<AlarmConfig>> findAll() {
        return super.findAll();
    }
}
