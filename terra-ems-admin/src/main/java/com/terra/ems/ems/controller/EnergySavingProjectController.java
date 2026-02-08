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

import com.terra.ems.ems.entity.EnergySavingProject;
import com.terra.ems.ems.enums.ProjectStatus;
import com.terra.ems.ems.param.EnergySavingProjectQueryParam;
import com.terra.ems.ems.service.EnergySavingProjectService;
import com.terra.ems.framework.controller.BaseController;
import com.terra.ems.framework.service.BaseService;
import com.terra.ems.common.domain.Result;
import com.terra.ems.framework.definition.dto.Pager;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Page;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * 节能项目控制器
 *
 * @author dengxueping
 * @since 2026-01-11
 */
@RestController
@RequestMapping("/ems/saving-projects")
@Tag(name = "节能项目管理")
public class EnergySavingProjectController extends BaseController<EnergySavingProject, Long> {

    private final EnergySavingProjectService energySavingProjectService;

    public EnergySavingProjectController(EnergySavingProjectService energySavingProjectService) {
        this.energySavingProjectService = energySavingProjectService;
    }

    @Override
    protected BaseService<EnergySavingProject, Long> getService() {
        return energySavingProjectService;
    }

    /**
     * 构建查询规范
     *
     * @param queryParam 查询参数
     * @return JPA Specification
     */
    protected Specification<EnergySavingProject> buildSpecification(EnergySavingProjectQueryParam queryParam) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (StringUtils.hasText(queryParam.getKeyword())) {
                String keyword = "%" + queryParam.getKeyword() + "%";
                predicates.add(cb.or(
                        cb.like(root.get("name"), keyword),
                        cb.like(root.get("liablePerson"), keyword),
                        cb.like(root.get("remark"), keyword)));
            }
            if (StringUtils.hasText(queryParam.getName())) {
                predicates.add(cb.like(root.get("name"), "%" + queryParam.getName() + "%"));
            }
            if (queryParam.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), queryParam.getStatus()));
            }
            if (StringUtils.hasText(queryParam.getLiablePerson())) {
                predicates.add(cb.like(root.get("liablePerson"), "%" + queryParam.getLiablePerson() + "%"));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    /**
     * 分页查询节能项目
     *
     * @param pager      分页参数
     * @param queryParam 查询参数
     * @return 分页结果
     */
    @GetMapping
    @Operation(summary = "分页查询节能项目")
    public Result<Page<EnergySavingProject>> findByPage(Pager pager, EnergySavingProjectQueryParam queryParam) {
        Page<EnergySavingProject> page = energySavingProjectService.findByPage(buildSpecification(queryParam),
                pager.getPageable());
        return Result.success(page);
    }

    /**
     * 按状态查询项目列表
     *
     * @param status 项目状态
     * @return 项目列表
     */
    @GetMapping("/status/{status}")
    @Operation(summary = "按状态查询项目列表")
    public Result<List<EnergySavingProject>> findByStatus(
            @PathVariable @Parameter(description = "项目状态") ProjectStatus status) {
        return Result.content(energySavingProjectService.findByStatus(status));
    }

    /**
     * 获取已完成项目的节约量总和
     *
     * @return 节约量总和
     */
    @GetMapping("/statistics/saving-amount")
    @Operation(summary = "获取已完成项目的节约量总和")
    public Result<BigDecimal> findCompletedSavingAmount() {
        return Result.content(energySavingProjectService.getCompletedSavingAmount());
    }

    /**
     * 按状态统计项目数量
     *
     * @param status 项目状态
     * @return 项目数量
     */
    @GetMapping("/statistics/count/{status}")
    @Operation(summary = "按状态统计项目数量")
    public Result<Long> countByStatus(
            @PathVariable @Parameter(description = "项目状态") ProjectStatus status) {
        return Result.content(energySavingProjectService.countByStatus(status));
    }

    /**
     * 更新项目状态
     *
     * @param id     项目ID
     * @param status 新状态
     * @return 更新后的实体
     */
    @PutMapping("/{id}/status")
    @Operation(summary = "更新项目状态")
    public Result<EnergySavingProject> updateStatus(
            @PathVariable Long id,
            @RequestParam @Parameter(description = "新状态") ProjectStatus status) {
        return Result.content(energySavingProjectService.updateStatus(id, status));
    }

}
