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
import com.terra.ems.ems.entity.CommChannel;
import com.terra.ems.ems.service.CommChannelService;
import com.terra.ems.framework.controller.BaseController;
import com.terra.ems.framework.service.BaseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 通信通道管理控制器
 *
 * @author dengxueping
 * @since 2026-03-21
 */
@Tag(name = "通信通道管理")
@RestController
@RequestMapping("/comm-channels")
public class CommChannelController extends BaseController<CommChannel, Long> {

    private final CommChannelService commChannelService;

    public CommChannelController(CommChannelService commChannelService) {
        this.commChannelService = commChannelService;
    }

    @Override
    protected BaseService<CommChannel, Long> getService() {
        return commChannelService;
    }

    @Operation(summary = "保存或更新通信通道")
    @PostMapping
    @Override
    @Log(title = "通信通道", businessType = BusinessType.UPDATE)
    @PreAuthorize("hasAnyPerm('ems:comm-channel:add', 'ems:comm-channel:edit')")
    public Result<CommChannel> saveOrUpdate(@Validated @RequestBody CommChannel commChannel) {
        return super.saveOrUpdate(commChannel);
    }

    @Operation(summary = "删除通信通道")
    @DeleteMapping("/{id}")
    @Override
    @Log(title = "通信通道", businessType = BusinessType.DELETE)
    @PreAuthorize("hasPerm('ems:comm-channel:remove')")
    public Result<String> delete(@PathVariable Long id) {
        return super.delete(id);
    }

    @Operation(summary = "批量删除通信通道")
    @DeleteMapping
    @Override
    @Log(title = "通信通道", businessType = BusinessType.DELETE)
    @PreAuthorize("hasPerm('ems:comm-channel:remove')")
    public Result<String> deleteBatch(@RequestBody List<Long> ids) {
        return super.deleteBatch(ids);
    }

    @Operation(summary = "按ID查询")
    @PreAuthorize("hasPerm('ems:comm-channel:query')")
    @GetMapping("/{id}")
    @Override
    public Result<CommChannel> findById(@PathVariable Long id) {
        return super.findById(id);
    }

    @Operation(summary = "根据网关查询通信通道")
    @GetMapping("/gateway/{gatewayId}")
    public Result<List<CommChannel>> findByGatewayId(@PathVariable Long gatewayId) {
        return Result.content(commChannelService.findByGatewayId(gatewayId));
    }
}
