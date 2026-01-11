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
import com.terra.ems.ems.entity.ProductionRecord;
import com.terra.ems.ems.service.ProductionRecordService;
import com.terra.ems.framework.controller.Controller;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * 产品产量记录控制器
 *
 * @author dengxueping
 * @since 2026-01-11
 */
@Tag(name = "产品产量管理", description = "产品产量记录的增删改查及统计分析")
@RestController
@RequestMapping("/ems/production-records")
@RequiredArgsConstructor
public class ProductionRecordController extends Controller {

    private final ProductionRecordService productionRecordService;

    /**
     * 创建产量记录
     *
     * @param record 产量记录实体
     * @return 创建后的实体
     */
    @Operation(summary = "创建产量记录")
    @PostMapping
    public Result<ProductionRecord> create(@RequestBody ProductionRecord record) {
        ProductionRecord created = productionRecordService.create(record);
        return Result.content(created);
    }

    /**
     * 更新产量记录详情
     *
     * @param id     记录ID
     * @param record 产量记录实体
     * @return 更新后的实体
     */
    @Operation(summary = "更新产量记录")
    @PutMapping("/{id}")
    public Result<ProductionRecord> update(
            @Parameter(description = "记录ID") @PathVariable Long id,
            @RequestBody ProductionRecord record) {
        ProductionRecord updated = productionRecordService.update(id, record);
        return Result.content(updated);
    }

    /**
     * 删除指定的产量记录
     *
     * @param id 记录ID
     * @return 操作结果
     */
    @Operation(summary = "删除产量记录")
    @DeleteMapping("/{id}")
    public Result<Void> delete(@Parameter(description = "记录ID") @PathVariable Long id) {
        productionRecordService.delete(id);
        return Result.success();
    }

    /**
     * 批量删除产量记录
     *
     * @param ids 记录ID列表
     * @return 操作结果
     */
    @Operation(summary = "批量删除产量记录")
    @DeleteMapping("/batch")
    public Result<Void> deleteBatch(@RequestBody List<Long> ids) {
        productionRecordService.deleteByIds(ids);
        return Result.success();
    }

    /**
     * 获取指定产量记录的详细信息
     *
     * @param id 记录ID
     * @return 产量记录详情
     */
    @Operation(summary = "获取产量记录详情")
    @GetMapping("/{id}")
    public Result<ProductionRecord> getById(@Parameter(description = "记录ID") @PathVariable Long id) {
        return productionRecordService.findById(id)
                .map(Result::content)
                .orElse(Result.failure("产量记录不存在"));
    }

    /**
     * 分页查询指定单元和日期范围内的产量记录
     *
     * @param energyUnitId 用能单元ID
     * @param dataType     数据类型
     * @param startDate    开始日期
     * @param endDate      结束日期
     * @param page         页码
     * @param size         每页大小
     * @return 分页结果
     */
    @Operation(summary = "分页查询产量记录")
    @GetMapping
    public Result<Page<ProductionRecord>> list(
            @Parameter(description = "用能单元ID") @RequestParam Long energyUnitId,
            @Parameter(description = "数据类型") @RequestParam(required = false, defaultValue = "1") String dataType,
            @Parameter(description = "开始日期") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @Parameter(description = "结束日期") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @Parameter(description = "页码") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "每页大小") @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "recordDate"));
        Page<ProductionRecord> records = productionRecordService.findByEnergyUnitAndDateRange(
                energyUnitId, dataType, startDate, endDate, pageable);
        return Result.content(records);
    }

    /**
     * 汇总指定范围内的总产量
     *
     * @param energyUnitId 用能单元ID
     * @param dataType     数据类型
     * @param startDate    开始日期
     * @param endDate      结束日期
     * @return 总产量
     */
    @Operation(summary = "汇总产量")
    @GetMapping("/summary")
    public Result<BigDecimal> sumQuantity(
            @Parameter(description = "用能单元ID") @RequestParam Long energyUnitId,
            @Parameter(description = "数据类型") @RequestParam(required = false, defaultValue = "1") String dataType,
            @Parameter(description = "开始日期") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @Parameter(description = "结束日期") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        BigDecimal total = productionRecordService.sumQuantity(energyUnitId, dataType, startDate, endDate);
        return Result.content(total);
    }

    /**
     * 按产品名称分组统计指定范围内的产量
     *
     * @param energyUnitId 用能单元ID
     * @param dataType     数据类型
     * @param startDate    开始日期
     * @param endDate      结束日期
     * @return 产品产量分布映射
     */
    @Operation(summary = "按产品分组统计产量")
    @GetMapping("/summary/by-product")
    public Result<Map<String, BigDecimal>> sumByProduct(
            @Parameter(description = "用能单元ID") @RequestParam Long energyUnitId,
            @Parameter(description = "数据类型") @RequestParam(required = false, defaultValue = "1") String dataType,
            @Parameter(description = "开始日期") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @Parameter(description = "结束日期") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        Map<String, BigDecimal> productQuantities = productionRecordService.sumQuantityGroupByProduct(
                energyUnitId, dataType, startDate, endDate);
        return Result.content(productQuantities);
    }

    /**
     * 获取指定用能单元下已有的产品名称列表
     *
     * @param energyUnitId 用能单元ID
     * @param dataType     数据类型
     * @return 产品名称列表
     */
    @Operation(summary = "获取产品名称列表")
    @GetMapping("/products")
    public Result<List<String>> getProductNames(
            @Parameter(description = "用能单元ID") @RequestParam Long energyUnitId,
            @Parameter(description = "数据类型") @RequestParam(required = false, defaultValue = "1") String dataType) {

        List<String> productNames = productionRecordService.getProductNames(energyUnitId, dataType);
        return Result.content(productNames);
    }
}
