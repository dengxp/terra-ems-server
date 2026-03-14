/*
 * Copyright (c) 2024-2026 Terra Technology (Guangzhou) Co., Ltd.
 * Copyright (c) 2024-2026 泰若科技（广州）有限公司.
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

package com.terra.ems.system.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.terra.ems.framework.jpa.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 操作日志记录
 *
 * @author dengxueping
 * @since 2026-02-10
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "sys_operation_log", indexes = {
        @Index(name = "idx_sys_oper_log_title", columnList = "title"),
        @Index(name = "idx_sys_oper_log_status", columnList = "status"),
        @Index(name = "idx_sys_oper_log_time", columnList = "created_at")
})
@Schema(title = "操作日志")
public class SysOperationLog extends BaseEntity {

    @Schema(title = "日志主键")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(title = "模块标题")
    @Column(name = "title", length = 50)
    private String title;

    @Schema(title = "业务类型（0其它 1新增 2修改 3删除）")
    @Column(name = "business_type")
    private Integer businessType;

    @Schema(title = "方法名称")
    @Column(name = "method", length = 100)
    private String method;

    @Schema(title = "请求方式")
    @Column(name = "request_method", length = 10)
    private String requestMethod;

    @Schema(title = "操作类别（0其它 1后台用户 2手机端用户）")
    @Column(name = "operator_type")
    private Integer operatorType;

    @Schema(title = "操作人员")
    @Column(name = "operation_name", length = 50)
    private String operationName;

    @Schema(title = "部门名称")
    @Column(name = "dept_name", length = 50)
    private String deptName;

    @Schema(title = "请求URL")
    @Column(name = "operation_url", length = 255)
    private String operationUrl;

    @Schema(title = "主机地址")
    @Column(name = "operation_ip", length = 128)
    private String operationIp;

    @Schema(title = "操作地点")
    @Column(name = "operation_location", length = 255)
    private String operationLocation;

    @Schema(title = "请求参数")
    @Column(name = "operation_param", columnDefinition = "text")
    private String operationParam;

    @Schema(title = "返回参数")
    @Column(name = "json_result", columnDefinition = "text")
    private String jsonResult;

    @Schema(title = "操作状态（0正常 1异常）")
    @Column(name = "status")
    private Integer status;

    @Schema(title = "错误消息")
    @Column(name = "error_msg", columnDefinition = "text")
    private String errorMsg;

    @Schema(title = "操作时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "operation_time")
    private LocalDateTime operationTime;

    @Schema(title = "消耗时间")
    @Column(name = "cost_time")
    private Long costTime;
}
