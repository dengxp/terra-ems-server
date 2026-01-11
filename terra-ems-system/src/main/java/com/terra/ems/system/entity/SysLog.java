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

package com.terra.ems.system.entity;

import com.terra.ems.framework.jpa.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * Name: SysLog
 * Email: dengxueping@gmail.com
 * Date: 2024-01-09
 * Description: 系统日志实体（操作日志+登录日志）
 *
 * @author dengxueping
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "sys_log", indexes = {
        @Index(name = "idx_sys_log_type", columnList = "log_type"),
        @Index(name = "idx_sys_log_user", columnList = "username"),
        @Index(name = "idx_sys_log_time", columnList = "created_at")
})
@Schema(title = "系统日志")
public class SysLog extends BaseEntity {

    @Schema(title = "日志ID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(title = "日志类型（LOGIN: 登录, OPER: 操作）")
    @Column(name = "log_type", length = 10, nullable = false)
    private String logType;

    @Schema(title = "系统模块")
    @Column(name = "title", length = 50)
    private String title;

    @Schema(title = "业务类型（0其它 1新增 2修改 3删除）")
    @Column(name = "business_type")
    private Integer businessType = 0;

    @Schema(title = "方法名称")
    @Column(name = "method", length = 100)
    private String method;

    @Schema(title = "请求方式")
    @Column(name = "request_method", length = 10)
    private String requestMethod;

    @Schema(title = "操作人员")
    @Column(name = "username", length = 50)
    private String username;

    @Schema(title = "部门名称")
    @Column(name = "dept_name", length = 50)
    private String deptName;

    @Schema(title = "请求URL")
    @Column(name = "url", length = 255)
    private String url;

    @Schema(title = "主机地址")
    @Column(name = "ip", length = 128)
    private String ip;

    @Schema(title = "操作地点")
    @Column(name = "location", length = 255)
    private String location;

    @Schema(title = "请求参数")
    @Column(name = "param", columnDefinition = "text")
    private String param;

    @Schema(title = "返回参数")
    @Column(name = "result", columnDefinition = "text")
    private String result;

    @Schema(title = "操作状态（0正常 1异常）")
    @Column(name = "status")
    private Integer status = 0;

    @Schema(title = "错误消息")
    @Column(name = "error_msg", columnDefinition = "text")
    private String errorMsg;

    @Schema(title = "消耗时间")
    @Column(name = "cost_time")
    private Long costTime;
}
