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

import com.terra.ems.framework.enums.DataItemStatus;
import com.terra.ems.framework.enums.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 用户数据传输对象
 *
 * @author dengxueping
 * @since 2026-01-11
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "用户DTO")
public class UserDTO {

    @Schema(title = "用户ID")
    private Long id;

    @Schema(title = "用户名")
    private String username;

    @Schema(title = "真实姓名")
    private String realName;

    @Schema(title = "邮箱")
    private String email;

    @Schema(title = "手机号")
    private String phone;

    @Schema(title = "头像")
    private String avatar;

    @Schema(title = "性别")
    private Gender gender;

    @Schema(title = "工号")
    private String employeeNo;

    @Schema(title = "状态")
    private DataItemStatus status;

    @Schema(title = "最后登录时间")
    private LocalDateTime lastLoginAt;

    @Schema(title = "部门ID")
    private Long departmentId;

    @Schema(title = "部门名称")
    private String departmentName;

    @Schema(title = "角色ID列表")
    private List<Long> roleIds;

    @Schema(title = "角色列表")
    private List<RoleDTO> roles;

    @Schema(title = "创建时间")
    private LocalDateTime createdAt;

    @Schema(title = "更新时间")
    private LocalDateTime updatedAt;

    // 注意：不包含 password, permissions 等敏感信息
}
