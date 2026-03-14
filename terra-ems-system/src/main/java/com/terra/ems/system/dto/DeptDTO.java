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

package com.terra.ems.system.dto;

import com.terra.ems.framework.enums.DataItemStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 部门数据传输对象
 *
 * @author dengxueping
 * @since 2026-01-11
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "部门DTO")
public class DeptDTO {

    @Schema(title = "部门ID")
    private Long id;

    @Schema(title = "父部门ID")
    private Long parentId;

    @Schema(title = "父部门名称")
    private String parentName;

    @Schema(title = "部门名称")
    private String name;

    @Schema(title = "部门编码")
    private String code;

    @Schema(title = "显示顺序")
    private Integer sortOrder;

    @Schema(title = "负责人")
    private String leader;

    @Schema(title = "联系电话")
    private String phone;

    @Schema(title = "邮箱")
    private String email;

    @Schema(title = "祖级列表")
    private String ancestors;

    @Schema(title = "状态")
    private DataItemStatus status;

    @Schema(title = "子部门")
    private List<DeptDTO> children;

    @Schema(title = "创建时间")
    private LocalDateTime createdAt;
}
