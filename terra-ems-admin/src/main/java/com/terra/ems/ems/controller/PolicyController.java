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

import com.terra.ems.ems.entity.Policy;
import com.terra.ems.ems.enums.PolicyType;
import com.terra.ems.ems.param.PolicyQueryParam;
import com.terra.ems.ems.service.PolicyService;
import com.terra.ems.framework.controller.BaseController;
import com.terra.ems.framework.service.BaseService;
import com.terra.ems.framework.enums.DataItemStatus;
import com.terra.ems.common.domain.Result;
import com.terra.ems.framework.definition.dto.Pager;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 政策法规控制器
 *
 * @author dengxueping
 * @since 2026-01-11
 */
@RestController
@RequestMapping("/ems/policies")
@Tag(name = "政策法规管理")
public class PolicyController extends BaseController<Policy, Long> {

    private final PolicyService policyService;

    public PolicyController(PolicyService policyService) {
        this.policyService = policyService;
    }

    @Override
    protected BaseService<Policy, Long> getService() {
        return policyService;
    }

    /**
     * 分页查询政策
     *
     * @param pager      分页参数
     * @param queryParam 查询参数
     * @return 分页结果
     */
    @GetMapping
    @Operation(summary = "分页查询政策")
    public Result<Page<Policy>> findByPage(Pager pager, PolicyQueryParam queryParam) {
        Page<Policy> page = policyService.findByPage(buildSpecification(queryParam), pager.getPageable());
        return Result.content(page);
    }

    /**
     * 构建查询规范
     */
    private Specification<Policy> buildSpecification(PolicyQueryParam queryParam) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            // keyword: 仅检索文本字段（title, summary, remark）
            if (StringUtils.hasText(queryParam.getKeyword())) {
                String keyword = "%" + queryParam.getKeyword() + "%";
                predicates.add(cb.or(
                        cb.like(root.get("title"), keyword),
                        cb.like(root.get("summary"), keyword),
                        cb.like(root.get("remark"), keyword)));
            }
            if (StringUtils.hasText(queryParam.getTitle())) {
                predicates.add(cb.like(root.get("title"), "%" + queryParam.getTitle() + "%"));
            }
            if (queryParam.getType() != null) {
                predicates.add(cb.equal(root.get("type"), queryParam.getType()));
            }
            if (queryParam.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), queryParam.getStatus()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    /**
     * 查询所有启用的政策
     *
     * @return 启用的政策列表
     */
    @GetMapping("/enabled")
    @Operation(summary = "查询所有启用的政策")
    public Result<List<Policy>> findAllEnabled() {
        return Result.content(policyService.findAllEnabled());
    }

    /**
     * 按类型查询政策列表
     *
     * @param type 政策类型
     * @return 政策列表
     */
    @GetMapping("/type/{type}")
    @Operation(summary = "按类型查询政策列表")
    public Result<List<Policy>> findByType(
            @PathVariable @Parameter(description = "政策类型") PolicyType type) {
        return Result.content(policyService.findByType(type));
    }

    /**
     * 按类型统计政策数量
     *
     * @param type 政策类型
     * @return 政策数量
     */
    @GetMapping("/statistics/count/{type}")
    @Operation(summary = "按类型统计政策数量")
    public Result<Long> countByType(
            @PathVariable @Parameter(description = "政策类型") PolicyType type) {
        return Result.content(policyService.countByType(type));
    }

    /**
     * 更新政策状态
     *
     * @param id     政策ID
     * @param status 新状态
     * @return 更新后的实体
     */
    @PutMapping("/{id}/status")
    @Operation(summary = "更新政策状态")
    public Result<Policy> updateStatus(
            @PathVariable Long id,
            @RequestParam @Parameter(description = "新状态") DataItemStatus status) {
        return Result.content(policyService.updateStatus(id, status));
    }
}
