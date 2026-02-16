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
 * 用户通知已读状态实体
 *
 * @author Antigravity
 * @since 2026-02-11
 */

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "sys_user_notice", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "user_id", "notice_id" })
}, indexes = {
        @Index(name = "idx_user_notice_user_id", columnList = "user_id"),
        @Index(name = "idx_user_notice_notice_id", columnList = "notice_id")
})
@Schema(title = "用户通知已读状态")
public class SysUserNotice extends BaseEntity {

    @Schema(title = "实体ID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(title = "用户ID")
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Schema(title = "通知ID")
    @Column(name = "notice_id", nullable = false)
    private Long noticeId;

    @Schema(title = "阅读时间")
    @Column(name = "read_time")
    private LocalDateTime readTime;
}
