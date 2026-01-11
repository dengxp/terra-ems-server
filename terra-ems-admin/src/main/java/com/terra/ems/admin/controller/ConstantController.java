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
import com.terra.ems.framework.controller.Controller;
import com.terra.ems.system.service.ConstantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.collections4.MapUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 系统常量控制器
 *
 * @author dengxueping
 * @since 2026-01-11
 */

@Tag(name = "系统常量")
@RestController
@RequestMapping("/system/constant")
public class ConstantController extends Controller {

    private final ConstantService constantService;

    public ConstantController(ConstantService constantService) {
        this.constantService = constantService;
    }

    /**
     * 获取系统所有常量选项列表
     *
     * @return 包含所有枚举/常量选项信息的 Map
     */
    @Operation(summary = "获取系统常量")
    @GetMapping("/options")
    public Result<Map<String, Object>> findAllOptions() {
        Map<String, Object> allEnums = constantService.getAllOptions();
        if (MapUtils.isNotEmpty(allEnums)) {
            return Result.content(allEnums);
        } else {
            return Result.failure("获取系统常量失败");
        }
    }

    /**
     * 获取系统所有常量映射集
     *
     * @return 包含所有常量映射信息的 Map
     */
    @Operation(summary = "获取系统映射集")
    @GetMapping("/maps")
    public Result<Map<String, Object>> findAllMaps() {
        Map<String, Object> allEnums = constantService.getAllMaps();
        if (MapUtils.isNotEmpty(allEnums)) {
            return Result.content(allEnums);
        }
        return Result.failure("获取常量信息失败");
    }
}
