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

package com.terra.ems.common.domain;

import cn.hutool.core.date.DateUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.base.MoreObjects;
import com.terra.ems.common.constant.DefaultConstants;
import com.terra.ems.common.constant.ErrorCodes;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 统一响应实体
 *
 * @param <T> 响应数据类型
 * @author dengxueping
 * @since 2026-01-11
 */

@Getter
@Schema(title = "统一响应返回实体", description = "所有Rest接口统一返回的实体定义")
public class Result<T> implements Serializable {

    @Schema(title = "响应时间戳")
    private final String timestamp = DateUtil.format(new Date(),
            DefaultConstants.DATE_TIME_FORMAT);

    @Schema(title = "校验错误信息")
    private final Error error = new Error();

    @Schema(title = "自定义响应编码")
    private String code = "0";

    @Schema(title = "响应返回信息")
    private String message;

    @Schema(title = "请求路径")
    private String path;

    @Schema(title = "响应返回数据")
    private T data;

    @Schema(title = "http状态码")
    private int status;

    @Schema(title = "链路追踪TraceId")
    private String traceId;

    public Result() {
        super();
    }

    private static <T> Result<T> create(String message, String detail, String code, int status, T data,
            StackTraceElement[] stackTrace) {
        Result<T> result = new Result<>();
        if (StringUtils.isNotBlank(message)) {
            result.message(message);
        }

        if (StringUtils.isNotBlank(detail)) {
            result.detail(detail);
        }

        result.code(code);
        result.status(status);

        if (data != null) {
            result.data(data);
        }

        if (ArrayUtils.isNotEmpty(stackTrace)) {
            result.stackTrace(stackTrace);
        }

        return result;
    }

    public static <T> Result<T> success(String message, String code, int status, T data) {
        return create(message, null, code, status, data, null);
    }

    public static <T> Result<T> success(String message, String code, T data) {
        return success(message, code, 200, data);
    }

    public static <T> Result<T> success(String message, T data) {
        return success(message, String.valueOf(ErrorCodes.OK.getSequence()), data);
    }

    public static <T> Result<T> success(String message) {
        return success(message, null);
    }

    public static <T> Result<T> success() {
        return success("操作成功！");
    }

    public static <T> Result<T> content(T data) {
        return success("操作成功！", data);
    }

    public static <T> Result<T> failure(String message, String detail, String code, int status, T data,
            StackTraceElement[] stackTrace) {
        return create(message, detail, code, status, data, stackTrace);
    }

    public static <T> Result<T> failure(String message, String detail, String code, int status, T data) {
        return failure(message, detail, code, status, data, null);
    }

    public static <T> Result<T> failure(String message, String code, int status, T data) {
        return failure(message, message, code, status, data);
    }

    public static <T> Result<T> failure(String message, String detail, String code, T data) {
        return failure(message, detail, code, 500, data);
    }

    public static <T> Result<T> failure(String message, String code, T data) {
        return failure(message, message, code, data);
    }

    public static <T> Result<T> failure(ErrorCodes errorCode) {
        return failure(errorCode, null);
    }

    public static <T> Result<T> failure(ErrorCodes errorCode, T data) {
        return failure(errorCode.getMessage(), String.valueOf(errorCode.getSequence()), errorCode.getStatus(), data);
    }

    public static <T> Result<T> failure(String message, T data) {
        return failure(message, String.valueOf(ErrorCodes.INTERNAL_SERVER_ERROR.getSequence()), data);
    }

    public static <T> Result<T> failure(String message) {
        return failure(message, null);
    }

    public static <T> Result<T> failure() {
        return failure("操作失败！");
    }

    public static <T> Result<T> empty(String message, String code, int status) {
        return create(message, null, code, status, null, null);
    }

    public static <T> Result<T> empty(String message, String code) {
        return empty(message, code, ErrorCodes.NO_CONTENT.getStatus());
    }

    public static <T> Result<T> empty(String message) {
        return empty(message, String.valueOf(ErrorCodes.NO_CONTENT.getSequence()));
    }

    public static <T> Result<T> empty() {
        return empty("未查询到相关内容！");
    }

    public Result<T> code(String code) {
        this.code = code;
        return this;
    }

    public Result<T> message(String message) {
        this.message = message;
        return this;
    }

    public Result<T> data(T data) {
        this.data = data;
        return this;
    }

    public Result<T> path(String path) {
        this.path = path;
        return this;
    }

    public Result<T> type(ErrorCodes errorCode) {
        this.code = String.valueOf(errorCode.getSequence());
        this.message = errorCode.getMessage();
        this.status = errorCode.getStatus();
        return this;
    }

    public Result<T> status(int httpStatus) {
        this.status = httpStatus;
        return this;
    }

    public Result<T> traceId(String traceId) {
        this.traceId = traceId;
        return this;
    }

    public Result<T> stackTrace(StackTraceElement[] stackTrace) {
        this.error.setStackTrace(stackTrace);
        return this;
    }

    public Result<T> detail(String detail) {
        this.error.setDetail(detail);
        return this;
    }

    public Result<T> validation(String message, String code, String field) {
        this.error.setMessage(message);
        this.error.setCode(code);
        this.error.setField(field);
        return this;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("code", code)
                .add("message", message)
                .add("path", path)
                .add("data", data)
                .add("status", status)
                .add("timestamp", timestamp)
                .add("error", error)
                .toString();
    }

    public Map<String, Object> toModel() {
        Map<String, Object> result = new HashMap<>(8);
        result.put("code", code);
        result.put("message", message);
        result.put("path", path);
        result.put("data", data);
        result.put("status", status);
        result.put("timestamp", timestamp);
        result.put("error", error);
        return result;
    }

}
