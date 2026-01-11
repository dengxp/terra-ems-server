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
import com.terra.ems.system.entity.SysNotice;
import com.terra.ems.system.service.SysNoticeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Name: SysNoticeController
 * Email: dengxueping@gmail.com
 * Date: 2024-01-09
 * Description: 通知公告控制器
 *
 * @author dengxueping
 */

@Tag(name = "通知公告")
@RestController
@RequestMapping("/system/notice")
public class SysNoticeController extends BaseController<SysNotice, Long> {

    private final SysNoticeService noticeService;

    @Autowired
    public SysNoticeController(SysNoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @Override
    protected BaseService<SysNotice, Long> getService() {
        return noticeService;
    }

    @Operation(summary = "保存或更新通知公告")
    @Override
    @PreAuthorize("hasAnyAuthority('system:notice:add', 'system:notice:edit')")
    public Result<SysNotice> saveOrUpdate(@Validated @RequestBody SysNotice notice) {
        return super.saveOrUpdate(notice);
    }
}
