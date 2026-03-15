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

package com.terra.ems.system.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.terra.ems.framework.jpa.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 系统访问记录
 *
 * @author dengxueping
 * @since 2026-02-10
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "sys_login_log", indexes = {
        @Index(name = "idx_sys_login_status", columnList = "status"),
        @Index(name = "idx_sys_login_time", columnList = "login_time")
})
@Schema(title = "系统访问记录")
public class SysLoginLog extends BaseEntity {

    @Schema(title = "访问ID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(title = "用户账号")
    @Column(name = "user_name", length = 50)
    private String userName;

    @Schema(title = "登录状态 0成功 1失败")
    @Column(name = "status", length = 1)
    private String status;

    @Schema(title = "登录IP地址")
    @Column(name = "ipaddr", length = 128)
    private String ipaddr;

    @Schema(title = "登录地点")
    @Column(name = "login_location", length = 255)
    private String loginLocation;

    @Schema(title = "浏览器类型")
    @Column(name = "browser", length = 50)
    private String browser;

    @Schema(title = "操作系统")
    @Column(name = "os", length = 50)
    private String os;

    @Schema(title = "提示消息")
    @Column(name = "msg", length = 255)
    private String msg;

    @Schema(title = "访问时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "login_time")
    private LocalDateTime loginTime;
}
