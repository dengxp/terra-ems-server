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

import com.terra.ems.ems.entity.KnowledgeArticle;
import com.terra.ems.ems.service.KnowledgeArticleService;
import com.terra.ems.framework.enums.DataItemStatus;
import com.terra.ems.common.domain.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 知识库文章控制器
 *
 * @author dengxueping
 */
@Tag(name = "知识库管理", description = "知识库文章的增删改查及搜索")
@RestController
@RequestMapping("/ems/knowledge")
@RequiredArgsConstructor
public class KnowledgeArticleController {

    private final KnowledgeArticleService knowledgeArticleService;

    @Operation(summary = "创建文章")
    @PostMapping
    public Result<KnowledgeArticle> create(@RequestBody KnowledgeArticle article) {
        KnowledgeArticle created = knowledgeArticleService.create(article);
        return Result.content(created);
    }

    @Operation(summary = "更新文章")
    @PutMapping("/{id}")
    public Result<KnowledgeArticle> update(
            @Parameter(description = "文章ID") @PathVariable Long id,
            @RequestBody KnowledgeArticle article) {
        KnowledgeArticle updated = knowledgeArticleService.update(id, article);
        return Result.content(updated);
    }

    @Operation(summary = "删除文章")
    @DeleteMapping("/{id}")
    public Result<Void> delete(@Parameter(description = "文章ID") @PathVariable Long id) {
        knowledgeArticleService.delete(id);
        return Result.success();
    }

    @Operation(summary = "批量删除文章")
    @DeleteMapping("/batch")
    public Result<Void> deleteBatch(@RequestBody List<Long> ids) {
        knowledgeArticleService.deleteByIds(ids);
        return Result.success();
    }

    @Operation(summary = "获取文章详情（会增加阅读次数）")
    @GetMapping("/{id}")
    public Result<KnowledgeArticle> getById(@Parameter(description = "文章ID") @PathVariable Long id) {
        return knowledgeArticleService.findByIdAndIncrementView(id)
                .map(Result::content)
                .orElse(Result.failure("文章不存在"));
    }

    @Operation(summary = "分页查询文章")
    @GetMapping
    public Result<Page<KnowledgeArticle>> list(
            @Parameter(description = "能源类型ID") @RequestParam(required = false) Long energyTypeId,
            @Parameter(description = "页码") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "每页大小") @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "sortOrder", "createdAt"));

        Page<KnowledgeArticle> articles;
        if (energyTypeId != null) {
            articles = knowledgeArticleService.findByEnergyType(energyTypeId, pageable);
        } else {
            articles = knowledgeArticleService.findAll(pageable);
        }
        return Result.content(articles);
    }

    @Operation(summary = "搜索文章")
    @GetMapping("/search")
    public Result<Page<KnowledgeArticle>> search(
            @Parameter(description = "搜索关键词") @RequestParam String keyword,
            @Parameter(description = "页码") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "每页大小") @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<KnowledgeArticle> articles = knowledgeArticleService.search(keyword, pageable);
        return Result.content(articles);
    }

    @Operation(summary = "获取所有分类")
    @GetMapping("/categories")
    public Result<List<String>> getCategories() {
        List<String> categories = knowledgeArticleService.getCategories();
        return Result.content(categories);
    }

    @Operation(summary = "获取热门文章")
    @GetMapping("/hot")
    public Result<List<KnowledgeArticle>> getHotArticles() {
        List<KnowledgeArticle> hotArticles = knowledgeArticleService.getHotArticles();
        return Result.content(hotArticles);
    }

    @Operation(summary = "更新文章状态")
    @PatchMapping("/{id}/status")
    public Result<Void> updateStatus(
            @Parameter(description = "文章ID") @PathVariable Long id,
            @Parameter(description = "状态") @RequestParam DataItemStatus status) {
        knowledgeArticleService.updateStatus(id, status);
        return Result.success();
    }
}
