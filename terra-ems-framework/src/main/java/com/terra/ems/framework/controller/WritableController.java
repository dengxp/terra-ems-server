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

package com.terra.ems.framework.controller;

import com.terra.ems.common.annotation.Log;
import com.terra.ems.common.domain.Result;
import com.terra.ems.common.enums.BusinessType;
import com.terra.ems.framework.jpa.entity.Entity;
import com.terra.ems.framework.rest.annotation.Idempotent;
import com.terra.ems.framework.service.WritableService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PutMapping;

import java.io.Serializable;
import java.util.List;

/**
 * 可写Controller基类
 *
 * @author dengxueping
 * @since 2026-01-11
 */

public abstract class WritableController<E extends Entity, ID extends Serializable> extends ReadableController<E, ID> {

        protected abstract WritableService<E, ID> getWritableService();

        /**
         * 保存或更新数据
         *
         * @param domain 实体数据
         * @return 保存后的实体数据
         */
        @Idempotent
        @Operation(summary = "保存或更新数据", description = "接收JSON数据，转换为实体，进行保存或更新", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = "application/json")), responses = {
                        @ApiResponse(description = "已保存数据", content = @Content(mediaType = "application/json")) })
        @Parameters({
                        @Parameter(name = "domain", required = true, description = "可转换为实体的json数据")
        })
        @Log(title = "", businessType = BusinessType.UPDATE)
        @PostMapping
        // @PutMapping
        public Result<E> saveOrUpdate(@RequestBody @Validated E domain) {
                E savedEntity = getWritableService().saveOrUpdate(domain);
                return Result.success("保存成功", savedEntity);
        }

        /**
         * 根据ID更新数据
         *
         * @param id     实体ID
         * @param domain 实体数据
         * @return 更新后的实体数据
         */
        @Idempotent
        @Operation(summary = "通过ID更新数据", description = "根据路径中的ID更新实体数据")
        @PutMapping("/{id}")
        @Log(title = "", businessType = BusinessType.UPDATE)
        public Result<E> update(@PathVariable ID id, @RequestBody @Validated E domain) {
                BeanWrapper bw = PropertyAccessorFactory.forBeanPropertyAccess(domain);
                bw.setPropertyValue("id", id);
                return saveOrUpdate(domain);
        }

        /**
         * 根据ID删除数据
         *
         * @param id 实体ID
         * @return 操作消息
         */
        @Idempotent
        @Operation(summary = "删除数据", description = "根据实体ID删除数据，以及相关联的关联数据", responses = {
                        @ApiResponse(description = "操作消息", content = @Content(mediaType = "application/json")) })
        @Parameters({
                        @Parameter(name = "id", required = true, in = ParameterIn.PATH, description = "实体ID")
        })
        @Log(title = "", businessType = BusinessType.DELETE)
        @DeleteMapping("/{id}")
        public Result<String> delete(@PathVariable ID id) {
                getWritableService().deleteById(id);
                return Result.success("删除成功");
        }

        /**
         * 批量删除数据
         *
         * @param ids 实体ID集合
         * @return 操作消息
         */
        @Idempotent
        @Operation(summary = "批量删除数据", description = "根据实体ID集合批量删除数据")
        @Log(title = "", businessType = BusinessType.DELETE)
        @DeleteMapping
        public Result<String> deleteBatch(@RequestBody List<ID> ids) {
                getWritableService().deleteAllById(ids);
                return Result.success("删除成功");
        }
}
