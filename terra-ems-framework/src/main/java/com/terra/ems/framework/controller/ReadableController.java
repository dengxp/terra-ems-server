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
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 只读Controller基类
 *
 * @author dengxueping
 * @since 2026-01-11
 */

public abstract class ReadableController<E extends Entity, ID extends Serializable> extends Controller {

    protected abstract ReadableService<E, ID> getReadableService();

    /**
     * 查询所有数据
     *
     * @return 实体数据列表
     */
    @AccessLimited
    @Operation(summary = "查询所有数据", description = "获取该实体的所有数据列表")
    @GetMapping("/all")
    public Result<List<E>> findAll() {
        List<E> list = getReadableService().findAll();
        return Result.success("查询成功", list);
    }

    /**
     * 根据ID查询数据
     *
     * @param id 主键ID
     * @return 实体详情
     */
    @AccessLimited
    @Operation(summary = "按ID查询", description = "获取指定ID的实体详情")
    @GetMapping("/{id}")
    public Result<E> findById(
            @Parameter(name = "id", description = "实体ID", in = ParameterIn.PATH) @PathVariable ID id) {
        E domain = getReadableService().findById(id);
        return domain != null ? Result.content(domain) : Result.empty();
    }

    /**
     * 分页查询数据助手方法 (不直接映射端点)
     *
     * @param pager 分页参数
     * @param spec  查询条件
     * @return 分页结果 Map
     */
    protected Result<Map<String, Object>> findByPage(Pager pager, Specification<E> spec) {
        Page<E> page;
        if (spec != null) {
            page = getReadableService().findByPage(spec, pager.getPageable());
        } else {
            page = getReadableService().findByPage(pager.getPageable());
        }
        return result(page);
    }
}
