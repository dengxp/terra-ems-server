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

package com.terra.ems.ems.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.terra.ems.ems.enums.ProductType;
import com.terra.ems.framework.enums.DataItemStatus;
import com.terra.ems.framework.jpa.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 产品信息实体
 *
 * @author dengxueping
 * @since 2026-01-25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "ems_product", uniqueConstraints = {
                @UniqueConstraint(columnNames = { "code" })
}, indexes = {
                @Index(name = "idx_product_code", columnList = "code"),
                @Index(name = "idx_product_status", columnList = "status")
})
@Schema(title = "产品信息")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Product extends BaseEntity {

        @Schema(title = "ID")
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Schema(title = "产品编码")
        @Column(name = "code", length = 50, unique = true, nullable = false)
        private String code;

        @Schema(title = "产品名称")
        @Column(name = "name", length = 100, nullable = false)
        private String name;

        @Schema(title = "计量单位")
        @Column(name = "unit", length = 20)
        private String unit;

        @Schema(title = "产品类型")
        @Column(name = "type", length = 50)
        @Convert(converter = ProductType.ProductTypeConverter.class)
        private ProductType type;

        @Schema(title = "排序")
        @Column(name = "sort_order")
        private Integer sortOrder = 0;

        @Schema(title = "状态")
        @Column(name = "status", nullable = false)
        private DataItemStatus status = DataItemStatus.ENABLE;

        @Schema(title = "备注")
        @Column(name = "remark", length = 500)
        private String remark;
}
