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

package com.terra.ems.common.constant;

/**
 * 错误码枚举
 *
 * @author dengxueping
 * @since 2026-01-11
 */

public enum ErrorCodes {

    /**
     * 操作成功
     */
    OK(20000, 200, "操作成功"),

    /**
     * 无内容
     */
    NO_CONTENT(1, 204, "未查询到相关内容"),

    /**
     * 内部服务器错误
     */
    INTERNAL_SERVER_ERROR(500, 500, "内部服务器错误"),

    /**
     * 未授权
     */
    UNAUTHORIZED(401, 401, "未授权,请先登录"),

    /**
     * 禁止访问
     */
    FORBIDDEN(403, 403, "禁止访问"),

    /**
     * 未找到资源
     */
    NOT_FOUND(404, 404, "未找到资源"),

    /**
     * Session过期
     */
    SESSION_EXPIRED(4001, 401, "Session已过期，请重新登录"),

    /**
     * 请求参数错误
     */
    BAD_REQUEST(400, 400, "请求参数错误"),

    /**
     * 访问被拒绝
     */
    ACCESS_DENIED(4003, 403, "您没有权限执行此操作");

    private final int sequence;
    private final int status;
    private final String message;

    ErrorCodes(int sequence, int status, String message) {
        this.sequence = sequence;
        this.status = status;
        this.message = message;
    }

    public int getSequence() {
        return sequence;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
