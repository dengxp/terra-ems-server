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

package com.terra.ems.system.mapper;

import com.terra.ems.system.dto.UserDTO;
import com.terra.ems.system.entity.SysUser;
import org.mapstruct.*;

import java.util.List;

/**
 * 用户实体与DTO转换器
 *
 * @author dengxueping
 * @since 2026-01-11
 */

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    /**
     * 实体转 DTO（忽略敏感信息）
     * UserDTO 本身就不包含 password 字段，无需 ignore
     */
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "roleIds", expression = "java(entity.getRoles() != null ? entity.getRoles().stream().map(r -> r.getId()).collect(java.util.stream.Collectors.toList()) : null)")
    @Mapping(target = "departmentId", source = "dept.id")
    @Mapping(target = "departmentName", source = "dept.name")
    UserDTO toDTO(SysUser entity);

    /**
     * DTO 转实体（密码需单独处理）
     */
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "permissions", ignore = true)
    SysUser toEntity(UserDTO dto);

    /**
     * 批量转换
     */
    List<UserDTO> toDTOList(List<SysUser> entities);

    /**
     * 更新实体（忽略 null 值）
     */
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "permissions", ignore = true)
    void updateEntityFromDTO(UserDTO dto, @MappingTarget SysUser entity);
}
