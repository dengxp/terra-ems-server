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

package com.terra.ems.ems.controller;

import com.terra.ems.common.domain.Result;
import com.terra.ems.ems.entity.ProductionRecord;
import com.terra.ems.ems.param.ProductionRecordQueryParam;
import com.terra.ems.ems.service.ProductionRecordService;
import com.terra.ems.framework.controller.BaseController;
import com.terra.ems.framework.definition.dto.Pager;
import com.terra.ems.framework.service.BaseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.terra.ems.common.annotation.Log;
import com.terra.ems.common.enums.BusinessType;
import org.springframework.security.access.prepost.PreAuthorize;

/**
 * 产品产量记录控制器
 *
 * @author dengxueping
 * @since 2026-01-11
 */
@Tag(name = "产品产量管理", description = "产品产量记录的增删改查及统计分析")
@RestController
@RequestMapping("/ems/production-records")
public class ProductionRecordController extends BaseController<ProductionRecord, Long> {

    private final ProductionRecordService productionRecordService;

    public ProductionRecordController(ProductionRecordService productionRecordService) {
        this.productionRecordService = productionRecordService;
    }

    @Override
    protected BaseService<ProductionRecord, Long> getService() {
        return productionRecordService;
    }

    /**
     * 更新产量记录详情
     *
     * @param id     记录ID
     * @param record 产量记录实体
     * @return 更新后的实体
     */
    @Operation(summary = "更新产量记录")
    @Log(title = "生产记录", businessType = BusinessType.UPDATE)
    @PreAuthorize("hasPerm('ems:production-record:edit')")
    @PutMapping("/{id}")
    @Override
    public Result<ProductionRecord> update(
            @Parameter(description = "记录ID") @PathVariable Long id,
            @RequestBody @Validated ProductionRecord record) {
        record.setId(id);
        return Result.content(productionRecordService.saveOrUpdate(record));
    }

    /**
     * 删除指定的产量记录
     *
     * @param id 记录ID
     * @return 操作结果
     */
    @Log(title = "生产记录", businessType = BusinessType.DELETE)
    @PreAuthorize("hasPerm('ems:production-record:remove')")
    @Operation(summary = "删除产量记录")
    @DeleteMapping("/{id}")
    public Result<String> delete(@Parameter(description = "记录ID") @PathVariable Long id) {
        productionRecordService.deleteById(id);
        return Result.success("删除成功");
    }

    /**
     * 分页查询指定单元和日期范围内的产量记录
     *
     * @param pager 分页参数
     * @param param 查询参数
     * @return 分页结果
     */
    @Operation(summary = "分页查询")
    @PreAuthorize("hasPerm('ems:production-record:list')")
    @GetMapping
    public Result<Map<String, Object>> findByPage(Pager pager, ProductionRecordQueryParam param) {
        return findByPage(pager, buildSpecification(param));
    }

    private Specification<ProductionRecord> buildSpecification(ProductionRecordQueryParam param) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (param.getEnergyUnitId() != null) {
                predicates.add(cb.equal(root.get("energyUnitId"), param.getEnergyUnitId()));
            }
            if (param.getDataType() != null) {
                predicates.add(cb.equal(root.get("dataType"), param.getDataType()));
            }
            if (param.getStartDate() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("recordDate"), param.getStartDate()));
            }
            if (param.getEndDate() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("recordDate"), param.getEndDate()));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
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
    public Result<List<String>> findProductNames(
            @Parameter(description = "用能单元ID") @RequestParam Long energyUnitId,
            @Parameter(description = "数据类型") @RequestParam(required = false, defaultValue = "1") String dataType) {

        List<String> productNames = productionRecordService.getProductNames(energyUnitId, dataType);
        return Result.content(productNames);
    }
}
