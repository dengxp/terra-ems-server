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

package com.terra.ems.ems.repository;

import com.terra.ems.ems.entity.KnowledgeArticle;
import com.terra.ems.framework.enums.DataItemStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.terra.ems.framework.jpa.repository.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 知识库文章仓库
 *
 * @author dengxueping
 * @since 2026-01-11
 */

@Repository
public interface KnowledgeArticleRepository extends BaseRepository<KnowledgeArticle, Long> {

        /**
         * 按能源类型查询文章
         */
        List<KnowledgeArticle> findByEnergyTypeIdAndStatus(Long energyTypeId, DataItemStatus status);

        /**
         * 按分类查询文章
         */
        List<KnowledgeArticle> findByCategoryAndStatus(String category, DataItemStatus status);

        /**
         * 分页查询启用的文章
         */
        Page<KnowledgeArticle> findByStatus(DataItemStatus status, Pageable pageable);

        /**
         * 按能源类型分页查询
         */
        Page<KnowledgeArticle> findByEnergyTypeIdAndStatus(Long energyTypeId, DataItemStatus status, Pageable pageable);

        /**
         * 关键词搜索（标题和内容）
         */
        @Query("SELECT k FROM KnowledgeArticle k WHERE k.status = :status " +
                        "AND (k.title LIKE %:keyword% OR k.content LIKE %:keyword%)")
        Page<KnowledgeArticle> searchByKeyword(@Param("keyword") String keyword,
                        @Param("status") DataItemStatus status,
                        Pageable pageable);

        /**
         * 获取所有分类
         */
        @Query("SELECT DISTINCT k.category FROM KnowledgeArticle k WHERE k.category IS NOT NULL AND k.status = :status")
        List<String> findDistinctCategories(@Param("status") DataItemStatus status);

        /**
         * 增加阅读次数
         */
        @Modifying
        @Query("UPDATE KnowledgeArticle k SET k.viewCount = k.viewCount + 1 WHERE k.id = :id")
        void incrementViewCount(@Param("id") Long id);

        /**
         * 获取热门文章
         */
        List<KnowledgeArticle> findTop10ByStatusOrderByViewCountDesc(DataItemStatus status);
}
