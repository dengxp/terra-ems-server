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

package com.terra.ems.system.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 系统日志传输对象
 *
 * @author dengxueping
 * @since 2026-01-11
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "系统日志DTO")
public class LogDTO {

    @Schema(title = "日志ID")
    private Long id;

    @Schema(title = "日志类型")
    private String logType;

    @Schema(title = "系统模块")
    private String title;

    @Schema(title = "业务类型")
    private Integer businessType;

    @Schema(title = "方法名称")
    private String method;

    @Schema(title = "请求方式")
    private String requestMethod;

    @Schema(title = "操作人员")
    private String username;

    @Schema(title = "部门名称")
    private String deptName;

    @Schema(title = "请求URL")
    private String url;

    @Schema(title = "主机地址")
    private String ip;

    @Schema(title = "操作地点")
    private String location;

    @Schema(title = "请求参数")
    private String param;

    @Schema(title = "返回参数")
    private String result;

    @Schema(title = "操作状态")
    private Integer status;

    @Schema(title = "错误消息")
    private String errorMsg;

    @Schema(title = "消耗时间")
    private Long costTime;

    @Schema(title = "操作时间")
    private LocalDateTime createdAt;
}
