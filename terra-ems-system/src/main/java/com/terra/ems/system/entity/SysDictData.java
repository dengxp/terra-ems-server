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

import com.terra.ems.framework.jpa.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 字典数据实体
 *
 * @author dengxueping
 * @since 2026-01-16
 */
@Data
@Entity
@Table(name = "sys_dict_data", indexes = {
        @Index(name = "idx_dict_data_type", columnList = "typeCode")
}, uniqueConstraints = {
        @UniqueConstraint(name = "uk_dict_data_value_type", columnNames = { "value", "typeCode" })
})
@EqualsAndHashCode(callSuper = true)
public class SysDictData extends BaseEntity {

    /**
     * 字典数据主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 字典排序
     */
    private Long sortOrder;

    /**
     * 字典标签
     */
    @Column(nullable = false, length = 100)
    private String label;

    /**
     * 字典键值
     */
    @Column(nullable = false, length = 100)
    private String value;

    /**
     * 字典类型标识 (大写)
     */
    @Column(nullable = false, length = 100)
    private String typeCode;

    /**
     * 对应 Antd Tag 状态 (success, processing, error, warning, default)
     */
    @Column(length = 100)
    private String tagType;

    /**
     * 对应 Antd 预设颜色值或自定义 Hex 颜色
     */
    @Column(length = 100)
    private String tagColor;

    /**
     * 是否默认（Y是 N否）
     */
    @Column(length = 1)
    private String isDefault;

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
