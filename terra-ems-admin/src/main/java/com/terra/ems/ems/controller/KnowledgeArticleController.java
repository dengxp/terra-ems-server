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

package com.terra.ems.ems.controller;

import com.terra.ems.common.domain.Result;
import com.terra.ems.ems.entity.KnowledgeArticle;
import com.terra.ems.ems.param.KnowledgeArticleQueryParam;
import com.terra.ems.ems.service.KnowledgeArticleService;
import com.terra.ems.framework.controller.BaseController;
import com.terra.ems.framework.definition.dto.Pager;
import com.terra.ems.framework.enums.DataItemStatus;
import com.terra.ems.framework.service.BaseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 知识库文章控制器
 *
 * @author dengxueping
 * @since 2026-01-11
 */
@Tag(name = "知识库管理", description = "知识库文章的增删改查及搜索")
@RestController
@RequestMapping("/ems/knowledge")
public class KnowledgeArticleController extends BaseController<KnowledgeArticle, Long> {

    private final KnowledgeArticleService knowledgeArticleService;

    public KnowledgeArticleController(KnowledgeArticleService knowledgeArticleService) {
        this.knowledgeArticleService = knowledgeArticleService;
    }

    @Override
    protected BaseService<KnowledgeArticle, Long> getService() {
        return knowledgeArticleService;
    }

    /**
     * 创建或更新文章
     *
     * @param article 文章实体
     * @return 保存后的实体
     */
    @Override
    @PostMapping
    @Operation(summary = "创建或更新文章")
    public Result<KnowledgeArticle> saveOrUpdate(@RequestBody @Validated KnowledgeArticle article) {
        if (article.getId() == null) {
            return Result.content(knowledgeArticleService.create(article));
        } else {
            return Result.content(knowledgeArticleService.update(article.getId(), article));
        }
    }

    /**
     * 更新文章
     *
     * @param id      文章ID
     * @param article 文章实体
     * @return 更新后的实体
     */
    @Operation(summary = "更新文章")
    @PutMapping("/{id}")
    public Result<KnowledgeArticle> update(
            @Parameter(description = "文章ID") @PathVariable Long id,
            @RequestBody KnowledgeArticle article) {
        KnowledgeArticle updated = knowledgeArticleService.update(id, article);
        return Result.content(updated);
    }

    /**
     * 删除文章
     *
     * @param id 文章ID
     * @return 操作结果
     */
    @Operation(summary = "删除文章")
    @DeleteMapping("/{id}")
    public Result<String> delete(@Parameter(description = "文章ID") @PathVariable Long id) {
        knowledgeArticleService.deleteById(id);
        return Result.success("删除成功");
    }

    /**
     * 获取文章详情（会增加阅读次数）
     *
     * @param id 文章ID
     * @return 文章详情
     */
    @Operation(summary = "获取文章详情（会增加阅读次数）")
    @GetMapping("/{id}")
    @Override
    public Result<KnowledgeArticle> findById(@Parameter(description = "文章ID") @PathVariable Long id) {
        return knowledgeArticleService.findByIdAndIncrementView(id)
                .map(Result::content)
                .orElse(Result.failure("文章不存在"));
    }

    /**
     * 分页查询文章
     *
     * @param pager 分页参数
     * @param param 查询参数
     * @return 分页结果
     */
    @Operation(summary = "分页查询")
    @GetMapping
    public Result<Map<String, Object>> findByPage(Pager pager, KnowledgeArticleQueryParam param) {
        Specification<KnowledgeArticle> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            // 1. 关键词全局搜索 (标题或内容)
            if (org.springframework.util.StringUtils.hasText(param.getKeyword())) {
                String keyword = "%" + param.getKeyword() + "%";
                predicates.add(cb.or(
                        cb.like(root.get("title"), keyword),
                        cb.like(root.get("content"), keyword)));
            }

            // 2. 精确或特定字段模糊搜索
            if (StringUtils.hasText(param.getTitle())) {
                predicates.add(cb.like(root.get("title"), "%" + param.getTitle() + "%"));
            }
            if (StringUtils.hasText(param.getAuthor())) {
                predicates.add(cb.like(root.get("author"), "%" + param.getAuthor() + "%"));
            }
            if (StringUtils.hasText(param.getCategory())) {
                predicates.add(cb.equal(root.get("category"), param.getCategory()));
            }
            if (param.getEnergyTypeId() != null) {
                predicates.add(cb.equal(root.get("energyTypeId"), param.getEnergyTypeId()));
            }
            if (param.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), DataItemStatus.fromValue(param.getStatus())));
            } else {
                // 如果没传状态，默认查启用
                predicates.add(cb.equal(root.get("status"), DataItemStatus.ENABLE));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
        return findByPage(pager, spec);
    }

    /**
     * 搜索文章
     *
     * @param pager 分页参数
     * @param param 查询参数
     * @return 搜索结果
     */
    @Operation(summary = "搜索文章")
    @GetMapping("/search")
    public Result<Map<String, Object>> search(Pager pager, KnowledgeArticleQueryParam param) {
        Page<KnowledgeArticle> articles = knowledgeArticleService.search(param.getKeyword(), pager.getPageable());
        return result(articles);
    }

    /**
     * 获取所有分类
     *
     * @return 分类列表
     */
    @Operation(summary = "获取所有分类")
    @GetMapping("/categories")
    public Result<List<String>> findCategories() {
        List<String> categories = knowledgeArticleService.getCategories();
        return Result.content(categories);
    }

    /**
     * 获取热门文章
     *
     * @return 热门文章列表
     */
    @Operation(summary = "获取热门文章")
    @GetMapping("/hot")
    public Result<List<KnowledgeArticle>> findHotArticles() {
        List<KnowledgeArticle> hotArticles = knowledgeArticleService.getHotArticles();
        return Result.content(hotArticles);
    }

    /**
     * 更新文章状态
     *
     * @param id     文章ID
     * @param status 新状态
     * @return 操作结果
     */
    @Operation(summary = "更新文章状态")
    @PatchMapping("/{id}/status")
    public Result<Void> updateStatus(
            @Parameter(description = "文章ID") @PathVariable Long id,
            @Parameter(description = "状态") @RequestParam DataItemStatus status) {
        knowledgeArticleService.updateStatus(id, status);
        return Result.success();
    }
}
