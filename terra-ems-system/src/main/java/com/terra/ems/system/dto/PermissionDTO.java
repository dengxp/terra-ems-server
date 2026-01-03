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

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Name: PermissionDTO
 * Email: dengxueping@gmail.com
 * Date: 2024-12-20
 * Description: 权限数据传输对象
 *
 * @author dengxueping
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "权限DTO")
public class PermissionDTO {

    @Schema(title = "权限ID")
    private Long id;

    @Schema(title = "权限名称")
    private String name;

    @Schema(title = "权限代码")
    private String code;

    @Schema(title = "权限描述")
    private String description;

    @Schema(title = "是否为超级权限")
    private Boolean superPermission;

    @Schema(title = "创建时间")
    private LocalDateTime createdAt;

    @Schema(title = "更新时间")
    private LocalDateTime updatedAt;
}
