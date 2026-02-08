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
import com.terra.ems.ems.entity.Benchmark;
import com.terra.ems.ems.enums.BenchmarkType;
import com.terra.ems.ems.param.BenchmarkQueryParam;
import com.terra.ems.ems.service.BenchmarkService;
import com.terra.ems.framework.controller.BaseController;
import com.terra.ems.framework.definition.dto.Pager;
import com.terra.ems.framework.service.BaseService;
import com.terra.ems.framework.enums.DataItemStatus;
import java.util.Optional;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.ArrayList;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

/**
 * 对标值控制器
 *
 * @author dengxueping
 * @since 2026-01-11
 */
@RestController
@RequestMapping("/ems/benchmarks")
@Tag(name = "对标值管理")
public class BenchmarkController extends BaseController<Benchmark, Long> {

    private final BenchmarkService benchmarkService;

    public BenchmarkController(BenchmarkService benchmarkService) {
        this.benchmarkService = benchmarkService;
    }

    @Override
    protected BaseService<Benchmark, Long> getService() {
        return benchmarkService;
    }

    /**
     * 分页查询对标值
     *
     * @param pager      分页参数
     * @param queryParam 查询参数
     * @return 分页结果
     */
    @GetMapping
    @Operation(summary = "分页查询")
    public Result<Page<Benchmark>> findByPage(Pager pager, BenchmarkQueryParam queryParam) {
        Page<Benchmark> page = benchmarkService.findByPage(buildSpecification(queryParam), pager.getPageable());
        return Result.content(page);
    }

    /**
     * 构建查询规范
     */
    private Specification<Benchmark> buildSpecification(BenchmarkQueryParam queryParam) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            // code: 精确匹配或模糊匹配
            if (StringUtils.hasText(queryParam.getCode())) {
                predicates.add(cb.like(root.get("code"), "%" + queryParam.getCode() + "%"));
            }
            // keyword: 仅检索文本字段（name, remark）
            if (StringUtils.hasText(queryParam.getKeyword())) {
                String keyword = "%" + queryParam.getKeyword() + "%";
                predicates.add(cb.or(
                        cb.like(root.get("name"), keyword),
                        cb.like(root.get("remark"), keyword)));
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
     * 查询所有对标值
     *
     * @return 对标值列表
     */
    @GetMapping("/all")
    @Operation(summary = "查询所有对标值")
    public Result<List<Benchmark>> findAll() {
        return Result.content(benchmarkService.findAll());
    }

    /**
     * 查询所有启用的对标值
     *
     * @return 启用的对标值列表
     */
    @GetMapping("/enabled")
    @Operation(summary = "查询所有启用的对标值")
    public Result<List<Benchmark>> findAllEnabled() {
        return Result.content(benchmarkService.findAllEnabled());
    }

    /**
     * 根据ID查询对标值
     *
     * @param id 对标值ID
     * @return 对标值详情
     */
    @GetMapping("/{id}")
    @Operation(summary = "根据ID查询")
    public Result<Benchmark> findById(@PathVariable Long id) {
        return Optional.ofNullable(benchmarkService.findById(id))
                .map(Result::content)
                .orElse(Result.failure("对标值不存在"));
    }

    /**
     * 根据编码查询对标值
     *
     * @param code 对标值编码
     * @return 对标值详情
     */
    @GetMapping("/code/{code}")
    @Operation(summary = "根据编码查询")
    public Result<Benchmark> findByCode(@PathVariable String code) {
        return benchmarkService.findByCode(code)
                .map(Result::content)
                .orElse(Result.failure("对标值不存在"));
    }

    /**
     * 按类型查询对标值
     *
     * @param type 对标值类型
     * @return 对标值列表
     */
    @GetMapping("/type/{type}")
    @Operation(summary = "按类型查询")
    public Result<List<Benchmark>> findByType(
            @PathVariable @Parameter(description = "类型") BenchmarkType type) {
        return Result.content(benchmarkService.findByType(type));
    }

    /**
     * 按能源类型查询对标值
     *
     * @param energyTypeId 能源类型ID
     * @return 对标值列表
     */
    @GetMapping("/energy-type/{energyTypeId}")
    @Operation(summary = "按能源类型查询")
    public Result<List<Benchmark>> findByEnergyType(
            @PathVariable @Parameter(description = "能源类型ID") Long energyTypeId) {
        return Result.content(benchmarkService.findByEnergyType(energyTypeId));
    }

    /**
     * 更新对标值
     *
     * @param id        对标值ID
     * @param benchmark 对标值实体
     * @return 更新后的实体
     */
    @PutMapping("/{id}")
    @Operation(summary = "更新对标值")
    public Result<Benchmark> update(@PathVariable Long id, @RequestBody Benchmark benchmark) {
        benchmark.setId(id);
        return Result.content(benchmarkService.saveOrUpdate(benchmark));
    }

    /**
     * 删除对标值
     *
     * @param id 对标值ID
     * @return 操作结果
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "删除基准定义")
    public Result<String> delete(@PathVariable Long id) {
        benchmarkService.deleteById(id);
        return Result.success("删除成功");
    }

    /**
     * 更新对标值状态
     *
     * @param id     对标值ID
     * @param status 新状态
     * @return 更新后的实体
     */
    @PutMapping("/{id}/status")
    @Operation(summary = "更新状态")
    public Result<Benchmark> updateStatus(
            @PathVariable Long id,
            @RequestParam @Parameter(description = "新状态") DataItemStatus status) {
        return Result.content(benchmarkService.updateStatus(id, status));
    }

    /**
     * 按类型统计数量
     *
     * @param type 对标值类型
     * @return 数量
     */
    @GetMapping("/statistics/count/{type}")
    @Operation(summary = "按类型统计数量")
    public Result<Long> countByType(
            @PathVariable @Parameter(description = "类型") BenchmarkType type) {
        return Result.content(benchmarkService.countByType(type));
    }
}
