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

package com.terra.ems.ems.controller;

import com.terra.ems.common.annotation.Log;
import com.terra.ems.common.domain.Result;
import com.terra.ems.common.enums.BusinessType;
import com.terra.ems.ems.entity.DataSource;
import com.terra.ems.ems.service.DataSourceService;
import com.terra.ems.framework.controller.BaseController;
import com.terra.ems.framework.service.BaseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 数据源管理控制器
 *
 * @author dengxueping
 * @since 2026-03-21
 */
@Tag(name = "数据源管理")
@RestController
@RequestMapping("/data-sources")
public class DataSourceController extends BaseController<DataSource, Long> {

    private final DataSourceService dataSourceService;

    public DataSourceController(DataSourceService dataSourceService) {
        this.dataSourceService = dataSourceService;
    }

    @Override
    protected BaseService<DataSource, Long> getService() {
        return dataSourceService;
    }

    @Operation(summary = "保存或更新数据源")
    @PostMapping
    @Override
    @Log(title = "数据源", businessType = BusinessType.UPDATE)
    @PreAuthorize("hasAnyPerm('ems:data-source:add', 'ems:data-source:edit')")
    public Result<DataSource> saveOrUpdate(@Validated @RequestBody DataSource config) {
        return super.saveOrUpdate(config);
    }

    @Operation(summary = "删除数据源")
    @DeleteMapping("/{id}")
    @Override
    @Log(title = "数据源", businessType = BusinessType.DELETE)
    @PreAuthorize("hasPerm('ems:data-source:remove')")
    public Result<String> delete(@PathVariable Long id) {
        return super.delete(id);
    }

    @Operation(summary = "批量删除数据源")
    @DeleteMapping
    @Override
    @Log(title = "数据源", businessType = BusinessType.DELETE)
    @PreAuthorize("hasPerm('ems:data-source:remove')")
    public Result<String> deleteBatch(@RequestBody List<Long> ids) {
        return super.deleteBatch(ids);
    }

    @Operation(summary = "按ID查询")
    @PreAuthorize("hasPerm('ems:data-source:query')")
    @GetMapping("/{id}")
    @Override
    public Result<DataSource> findById(@PathVariable Long id) {
        return super.findById(id);
    }

    @Operation(summary = "查询所有数据源")
    @PreAuthorize("hasPerm('ems:data-source:list')")
    @GetMapping("/all")
    public Result<List<DataSource>> findAll() {
        return super.findAll();
    }

    @Operation(summary = "根据网关查询数据源")
    @GetMapping("/gateway/{gatewayId}")
    public Result<List<DataSource>> findByGatewayId(@PathVariable Long gatewayId) {
        return Result.content(dataSourceService.findByGatewayId(gatewayId));
    }

    @Operation(summary = "根据协议查询数据源")
    @GetMapping("/protocol/{protocol}")
    public Result<List<DataSource>> findByProtocol(@PathVariable String protocol) {
        return Result.content(dataSourceService.findByProtocol(protocol));
    }
}
