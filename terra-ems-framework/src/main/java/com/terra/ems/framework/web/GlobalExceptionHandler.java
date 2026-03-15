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

package com.terra.ems.framework.web;

import com.terra.ems.common.domain.Result;
import com.terra.ems.common.exception.TerraException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import com.terra.ems.common.constant.ErrorCodes;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

/**
 * 全局异常处理器
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理非法参数异常 (业务逻辑校验通常抛出此异常)
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public Result<String> handleIllegalArgumentException(IllegalArgumentException e, HttpServletRequest request,
            HttpServletResponse response) {
        log.warn("[Terra] |- 捕获到非法参数异常: {}", e.getMessage());
        Result<String> result = Result.failure(ErrorCodes.BAD_REQUEST);
        result.message(e.getMessage());
        result.path(request.getRequestURI());
        response.setStatus(result.getStatus());
        return result;
    }

    /**
     * 处理方法参数校验异常 (@Valid / @Validated)
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException e,
            HttpServletRequest request, HttpServletResponse response) {
        return handleBindException(e, request, response);
    }

    /**
     * 处理表单绑定异常
     */
    @ExceptionHandler(BindException.class)
    public Result<String> handleBindException(BindException e, HttpServletRequest request,
            HttpServletResponse response) {
        Result<String> result = GlobalExceptionResolver.resolveException(e, request.getRequestURI());

        // 提取具体的校验错误信息
        FieldError fieldError = e.getBindingResult().getFieldError();
        if (fieldError != null) {
            result.validation(fieldError.getDefaultMessage(), fieldError.getCode(), fieldError.getField());
            // 更新 message 为更友好的提示
            result.message("参数校验失败: " + fieldError.getDefaultMessage());
        }

        response.setStatus(result.getStatus());
        return result;
    }

    /**
     * 处理约束校验异常
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public Result<String> handleConstraintViolationException(ConstraintViolationException e, HttpServletRequest request,
            HttpServletResponse response) {
        Result<String> result = GlobalExceptionResolver.resolveException(e, request.getRequestURI());

        String message = e.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(", "));

        result.message("约束校验失败: " + message);

        response.setStatus(result.getStatus());
        return result;
    }

    /**
     * 处理权限拒绝异常 (Spring Security @PreAuthorize 校验不通过)
     */
    @ExceptionHandler({ AccessDeniedException.class, AuthorizationDeniedException.class })
    public Result<String> handleAccessDeniedException(Exception e, HttpServletRequest request,
            HttpServletResponse response) {
        log.warn("[Terra] |- 访问被拒绝: {} -> {}", request.getRequestURI(), e.getMessage());
        Result<String> result = Result.failure(ErrorCodes.ACCESS_DENIED);
        result.path(request.getRequestURI());
        response.setStatus(result.getStatus());
        return result;
    }

    /**
     * 通用异常处理
     */
    @ExceptionHandler(Exception.class)
    public Result<String> handleException(Exception e, HttpServletRequest request, HttpServletResponse response) {
        Result<String> result = GlobalExceptionResolver.resolveException(e, request.getRequestURI());
        response.setStatus(result.getStatus());
        return result;
    }
}
