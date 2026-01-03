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
import com.terra.ems.framework.jpa.entity.Entity;
import com.terra.ems.framework.rest.annotation.Idempotent;
import com.terra.ems.framework.service.WritableService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.Serializable;

/**
 * Name: WritableController
 * Email: dengxueping@gmail.com
 * Date: 2024-12-14
 * Description: 可写Controller基类
 *
 * @author dengxueping
 */
public abstract class WritableController<E extends Entity, ID extends Serializable> extends ReadableController<E, ID> {

        protected abstract WritableService<E, ID> getWritableService();

        @Idempotent
        @Operation(summary = "保存或更新数据", description = "接收JSON数据，转换为实体，进行保存或更新", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = "application/json")), responses = {
                        @ApiResponse(description = "已保存数据", content = @Content(mediaType = "application/json")) })
        @Parameters({
                        @Parameter(name = "domain", required = true, description = "可转换为实体的json数据")
        })
        @PostMapping
        public Result<E> saveOrUpdate(@RequestBody @Validated E domain) {
                E savedEntity = getWritableService().saveOrUpdate(domain);
                return Result.success("保存成功", savedEntity);
        }

        @Idempotent
        @Operation(summary = "删除数据", description = "根据实体ID删除数据，以及相关联的关联数据", responses = {
                        @ApiResponse(description = "操作消息", content = @Content(mediaType = "application/json")) })
        @Parameters({
                        @Parameter(name = "id", required = true, in = ParameterIn.PATH, description = "实体ID")
        })
        @DeleteMapping("/{id}")
        public Result<String> delete(@PathVariable ID id) {
                getWritableService().deleteById(id);
                return Result.success("删除成功");
        }
}
