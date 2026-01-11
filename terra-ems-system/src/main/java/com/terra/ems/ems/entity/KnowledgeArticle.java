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

package com.terra.ems.ems.entity;

import com.terra.ems.framework.enums.DataItemStatus;
import com.terra.ems.framework.jpa.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 知识库文章
 * 用于存储能源管理相关的知识、经验和技术文档
 *
 * @author dengxueping
 * @since 2026-01-11
 */

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "ems_knowledge_article", indexes = {
        @Index(name = "idx_knowledge_energy_type", columnList = "energy_type_id"),
        @Index(name = "idx_knowledge_status", columnList = "status")
})
@Schema(title = "知识库文章")
public class KnowledgeArticle extends BaseEntity {

    @Schema(title = "ID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(title = "标题")
    @Column(name = "title", length = 200, nullable = false)
    private String title;

    @Schema(title = "能源类型ID", description = "关联ems_energy_type表，为空表示通用知识")
    @Column(name = "energy_type_id")
    private Long energyTypeId;

    @Schema(title = "分类标签", description = "如：节能技巧、设备维护、政策法规等")
    @Column(name = "category", length = 50)
    private String category;

    @Schema(title = "摘要")
    @Column(name = "summary", length = 500)
    private String summary;

    @Schema(title = "内容", description = "支持富文本HTML")
    @Lob
    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @Schema(title = "作者")
    @Column(name = "author", length = 50)
    private String author;

    @Schema(title = "阅读次数")
    @Column(name = "view_count", nullable = false)
    private Integer viewCount = 0;

    @Schema(title = "排序权重", description = "值越大越靠前")
    @Column(name = "sort_order")
    private Integer sortOrder = 0;

    @Schema(title = "状态")
    @Column(name = "status", nullable = false)
    private DataItemStatus status = DataItemStatus.ENABLE;
}
