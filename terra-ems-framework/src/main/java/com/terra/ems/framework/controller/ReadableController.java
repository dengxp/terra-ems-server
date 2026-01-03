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

package com.terra.ems.framework.controller;

import com.terra.ems.common.domain.Result;
import com.terra.ems.framework.definition.dto.Pager;
import com.terra.ems.framework.jpa.entity.Entity;
import com.terra.ems.framework.rest.annotation.AccessLimited;
import com.terra.ems.framework.service.ReadableService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.Serializable;
import java.util.Map;

/**
 * Name: ReadableController
 * Email: dengxueping@gmail.com
 * Date: 2024-12-14
 * Description: 只读Controller基类
 *
 * @author dengxueping
 */
public abstract class ReadableController<E extends Entity, ID extends Serializable> {

    protected abstract ReadableService<E, ID> getReadableService();

    @AccessLimited
    @Operation(summary = "获取单条数据", description = "通过ID获取单条数据", responses = {
            @ApiResponse(description = "实体数据", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Map.class))),
            @ApiResponse(responseCode = "204", description = "查询成功，未查到数据"),
            @ApiResponse(responseCode = "500", description = "查询失败")
    })
    @Parameters({
            @Parameter(name = "id", required = true, in = ParameterIn.PATH, description = "实体ID")
    })
    @GetMapping("/{id}")
    public Result<E> findById(@PathVariable ID id) {
        E entity = getReadableService().findById(id);
        if (entity != null) {
            return Result.success("查询成功", entity);
        } else {
            return Result.failure("未查到数据");
        }
    }

    @AccessLimited
    @Operation(summary = "分页查询数据", description = "通过pageNumber和pageSize获取分页数据", responses = {
            @ApiResponse(description = "数据列表", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Map.class))),
            @ApiResponse(responseCode = "204", description = "查询成功，未查到数据"),
            @ApiResponse(responseCode = "500", description = "查询失败")
    })
    @GetMapping
    public Result<Page<E>> findByPage(Pager pager) {
        Page<E> page = getReadableService().findByPage(pager.getPageable());
        return Result.success("查询成功", page);
    }
}
