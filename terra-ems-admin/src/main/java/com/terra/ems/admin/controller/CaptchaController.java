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
import com.terra.ems.system.service.CaptchaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Name: CaptchaController
 * Email: dengxueping@gmail.com
 * Date: 2024-12-20
 * Description: 验证码控制器
 *
 * @author dengxueping
 */

@Tag(name = "验证码管理", description = "验证码生成和验证接口")
@RestController
@RequestMapping("/captcha")
public class CaptchaController {

    private final CaptchaService captchaService;

    public CaptchaController(CaptchaService captchaService) {
        this.captchaService = captchaService;
    }

    @Operation(summary = "生成验证码", description = "通过传递身份信息和类型信息生成验证码")
    @GetMapping("/create")
    public Result<Map<String, Object>> create(
            @RequestParam(value = "identity", required = false) String identity,
            @RequestParam(value = "category", required = false, defaultValue = "default") String category) {
        Map<String, Object> captcha = captchaService.generate(identity, category);
        return Result.success("验证码生成成功", captcha);
    }

    @Operation(summary = "验证验证码")
    @GetMapping("/check")
    public Result<Boolean> check(
            @RequestParam("identity") String identity,
            @RequestParam("code") String code) {

        boolean isValid = captchaService.validate(identity, code);

        if (isValid) {
            return Result.success("验证码正确", true);
        } else {
            return Result.failure("验证码错误或已过期");
        }
    }
}
