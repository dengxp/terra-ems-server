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
import com.terra.ems.ems.entity.Meter;
import com.terra.ems.ems.service.MeterService;
import com.terra.ems.framework.controller.BaseController;
import com.terra.ems.framework.enums.DataItemStatus;
import com.terra.ems.framework.service.BaseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 计量器具档案控制器
 *
 * @author dengxueping
 * @since 2026-01-11
 */
@Tag(name = "计量器具管理")
@RestController
@RequestMapping("/meters")
public class MeterController extends BaseController<Meter, Long> {

    private final MeterService meterService;

    public MeterController(MeterService meterService) {
        this.meterService = meterService;
    }

    @Override
    protected BaseService<Meter, Long> getService() {
        return meterService;
    }

    /**
     * 分页查询计量器具
     */
    @Operation(summary = "分页查询")
    @GetMapping("/search")
    public Result<Page<Meter>> search(
            @Parameter(description = "编码") @RequestParam(required = false) String code,
            @Parameter(description = "名称") @RequestParam(required = false) String name,
            @Parameter(description = "种类") @RequestParam(required = false) String type,
            @Parameter(description = "状态值") @RequestParam(required = false) Integer status,
            @Parameter(description = "页码") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "每页大小") @RequestParam(defaultValue = "10") int size) {

        DataItemStatus statusEnum = status != null ? DataItemStatus.fromValue(status) : null;
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        return Result.content(meterService.findPage(code, name, type, statusEnum, pageable));
    }

    @Operation(summary = "更新计量器具")
    @PutMapping("/{id}")
    public Result<Meter> update(@PathVariable Long id, @RequestBody Meter meter) {
        // 确保ID一致
        meter.setId(id);
        return saveOrUpdate(meter);
    }
}
