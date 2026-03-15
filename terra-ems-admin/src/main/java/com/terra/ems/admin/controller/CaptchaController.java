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

package com.terra.ems.admin.controller;

import com.terra.ems.common.domain.Result;
import com.terra.ems.framework.controller.Controller;
import com.terra.ems.system.service.CaptchaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 验证码控制器
 *
 * @author dengxueping
 * @since 2026-01-11
 */

@Tag(name = "系统管理-验证码管理", description = "验证码生成和验证接口")
@RestController
@RequestMapping("/captcha")
public class CaptchaController extends Controller {

    private final CaptchaService captchaService;

    public CaptchaController(CaptchaService captchaService) {
        this.captchaService = captchaService;
    }

    /**
     * 生成验证码
     *
     * @param identity 身份标识（如手机号、邮箱、IP等）
     * @param category 验证码类别
     * @return 包含验证码标识和图片的 Map
     */
    @Operation(summary = "生成验证码", description = "通过传递身份信息和类型信息生成验证码")
    @GetMapping("/create")
    public Result<Map<String, Object>> create(
            @RequestParam(value = "identity", required = false) String identity,
            @RequestParam(value = "category", required = false, defaultValue = "default") String category) {
        Map<String, Object> captcha = captchaService.generate(identity, category);
        return Result.success("验证码生成成功", captcha);
    }

    /**
     * 校验验证码
     *
     * @param identity 身份标识
     * @param code     验证码
     * @return 是否校验通过
     */
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
