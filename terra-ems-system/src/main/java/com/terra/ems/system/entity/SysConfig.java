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

/**
 * Name: SysConfig
 * Email: dengxueping@gmail.com
 * Date: 2024-01-09
 * Description: 系统参数配置实体
 *
 * @author dengxueping
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "sys_config", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "config_key" })
})
@Schema(title = "参数配置")
public class SysConfig extends BaseEntity {

    @Schema(title = "参数主键")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(title = "参数名称")
    @Column(name = "config_name", length = 100)
    private String configName;

    @Schema(title = "参数键名")
    @Column(name = "config_key", length = 100, unique = true, nullable = false)
    private String configKey;

    @Schema(title = "参数键值")
    @Column(name = "config_value", length = 500)
    private String configValue;

    @Schema(title = "系统内置（Y是 N否）")
    @Column(name = "config_type", length = 1)
    private String configType = "N";

    @Schema(title = "备注")
    @Column(name = "remark", length = 500)
    private String remark;
}
