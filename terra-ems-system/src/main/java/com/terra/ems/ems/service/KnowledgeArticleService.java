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

package com.terra.ems.ems.service;

import com.terra.ems.framework.service.BaseService;
import com.terra.ems.framework.jpa.repository.BaseRepository;
import com.terra.ems.ems.entity.KnowledgeArticle;
import com.terra.ems.ems.repository.KnowledgeArticleRepository;
import com.terra.ems.framework.enums.DataItemStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * 知识库文章服务
 *
 * @author dengxueping
 * @since 2026-01-11
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class KnowledgeArticleService extends BaseService<KnowledgeArticle, Long> {

    private final KnowledgeArticleRepository knowledgeArticleRepository;

    @Override
    public BaseRepository<KnowledgeArticle, Long> getRepository() {
        return knowledgeArticleRepository;
    }

    /**
     * 创建文章
     */
    @Transactional
    public KnowledgeArticle create(KnowledgeArticle article) {
        article.setViewCount(0);
        article.setStatus(DataItemStatus.ENABLE);
        log.info("创建知识库文章: {}", article.getTitle());
        return knowledgeArticleRepository.save(article);
    }

    /**
     * 更新文章
     */
    @Transactional
    public KnowledgeArticle update(Long id, KnowledgeArticle article) {
        KnowledgeArticle existing = knowledgeArticleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("文章不存在: " + id));

        existing.setTitle(article.getTitle());
        existing.setEnergyTypeId(article.getEnergyTypeId());
        existing.setCategory(article.getCategory());
        existing.setSummary(article.getSummary());
        existing.setContent(article.getContent());
        existing.setAuthor(article.getAuthor());
        existing.setSortOrder(article.getSortOrder());

        log.info("更新知识库文章: ID={}", id);
        return knowledgeArticleRepository.save(existing);
    }

    /**
     * 删除文章（软删除）
     */
    @Transactional
    @Override
    public void deleteById(Long id) {
        KnowledgeArticle article = knowledgeArticleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("文章不存在: " + id));

        article.setStatus(DataItemStatus.FORBIDDEN);
        knowledgeArticleRepository.save(article);
        log.info("删除知识库文章: ID={}", id);
    }

    /**
     * 批量删除（软删除）
     */
    @Transactional
    public void deleteByIds(List<Long> ids) {
        for (Long id : ids) {
            knowledgeArticleRepository.findById(id).ifPresent(article -> {
                article.setStatus(DataItemStatus.FORBIDDEN);
                knowledgeArticleRepository.save(article);
            });
        }
        log.info("批量删除知识库文章: count={}", ids.size());
    }

    /**
     * 根据ID查询并增加阅读次数
     */
    @Transactional
    public Optional<KnowledgeArticle> findByIdAndIncrementView(Long id) {
        Optional<KnowledgeArticle> article = knowledgeArticleRepository.findById(id);
        if (article.isPresent() && article.get().getStatus() == DataItemStatus.ENABLE) {
            knowledgeArticleRepository.incrementViewCount(id);
        }
        return article;
    }

    /**
     * 分页查询文章
     */
    public Page<KnowledgeArticle> findAll(Pageable pageable) {
        return knowledgeArticleRepository.findByStatus(DataItemStatus.ENABLE, pageable);
    }

    /**
     * 按能源类型分页查询
     */
    public Page<KnowledgeArticle> findByEnergyType(Long energyTypeId, Pageable pageable) {
        return knowledgeArticleRepository.findByEnergyTypeIdAndStatus(
                energyTypeId, DataItemStatus.ENABLE, pageable);
    }

    /**
     * 关键词搜索
     */
    public Page<KnowledgeArticle> search(String keyword, Pageable pageable) {
        return knowledgeArticleRepository.searchByKeyword(keyword, DataItemStatus.ENABLE, pageable);
    }

    /**
     * 获取所有分类
     */
    public List<String> getCategories() {
        return knowledgeArticleRepository.findDistinctCategories(DataItemStatus.ENABLE);
    }

    /**
     * 获取热门文章
     */
    public List<KnowledgeArticle> getHotArticles() {
        return knowledgeArticleRepository.findTop10ByStatusOrderByViewCountDesc(DataItemStatus.ENABLE);
    }

    /**
     * 更新状态
     */
    @Transactional
    public void updateStatus(Long id, DataItemStatus status) {
        KnowledgeArticle article = knowledgeArticleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("文章不存在: " + id));
        article.setStatus(status);
        knowledgeArticleRepository.save(article);
        log.info("更新文章状态: ID={}, status={}", id, status);
    }
}
