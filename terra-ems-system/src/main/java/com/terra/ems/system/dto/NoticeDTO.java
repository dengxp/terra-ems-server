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

package com.terra.ems.system.dto;

import com.terra.ems.framework.enums.DataItemStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Name: NoticeDTO
 * Email: dengxueping@gmail.com
 * Date: 2024-01-09
 * Description: 通知公告传输对象
 *
 * @author dengxueping
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "通知公告DTO")
public class NoticeDTO {

    @Schema(title = "公告ID")
    private Long id;

    @Schema(title = "公告标题")
    private String noticeTitle;

    @Schema(title = "公告类型")
    private String noticeType;

    @Schema(title = "公告内容")
    private String noticeContent;

    @Schema(title = "公告状态")
    private DataItemStatus status;

    @Schema(title = "备注")
    private String remark;

    @Schema(title = "创建时间")
    private LocalDateTime createdAt;
}
