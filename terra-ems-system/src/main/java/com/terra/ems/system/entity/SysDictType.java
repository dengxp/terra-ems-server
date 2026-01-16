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
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 字典类型实体
 *
 * @author dengxueping
 * @since 2026-01-16
 */
@Data
@Entity
@Table(name = "sys_dict_type", indexes = {
        @Index(name = "idx_dict_type", columnList = "type", unique = true)
})
@EqualsAndHashCode(callSuper = true)
public class SysDictType extends BaseEntity {

    /**
     * 字典主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 字典名称
     */
    @Column(nullable = false, length = 100)
    private String name;

    /**
     * 字典类型标识 (建议使用大写，如 SYS_USER_SEX)
     */
    @Column(nullable = false, length = 100, unique = true)
    private String type;

    /**
     * 状态（0正常 1停用）
     */
    @Column(length = 1)
    private String status;

    /**
     * 备注
     */
    @Column(length = 500)
    private String remark;
}
