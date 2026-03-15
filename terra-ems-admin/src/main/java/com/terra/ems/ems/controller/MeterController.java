/*
 * Copyright (c) 2025-2026 Terra Technology (Guangzhou) Co., Ltd.
 * Copyright (c) 2025-2026 泰若科技（广州）有限公司.
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
import com.terra.ems.ems.entity.Meter;
import com.terra.ems.ems.param.MeterQueryParam;
import com.terra.ems.ems.service.MeterService;
import com.terra.ems.framework.controller.BaseController;
import com.terra.ems.framework.definition.dto.Pager;
import com.terra.ems.framework.enums.DataItemStatus;
import com.terra.ems.framework.service.BaseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.terra.ems.common.annotation.Log;
import com.terra.ems.common.enums.BusinessType;
import org.springframework.security.access.prepost.PreAuthorize;

/**
 * 计量器具档案控制器
 *
 * @author dengxueping
 * @since 2026-01-11
 */
@Tag(name = "计量器具管理")
@RestController
@RequestMapping("/meters")
public class MeterController extends BaseController<Meter, Long> {

    private final MeterService meterService;

    public MeterController(MeterService meterService) {
        this.meterService = meterService;
    }

    @Override
    protected BaseService<Meter, Long> getService() {
        return meterService;
    }

    /**
     * 保存或更新计量器具
     */
    @Operation(summary = "保存或更新计量器具")
    @PostMapping
    @Override
    @Log(title = "计量器具", businessType = BusinessType.UPDATE)
    @PreAuthorize("hasAnyPerm('ems:meter:add', 'ems:meter:edit')")
    public Result<Meter> saveOrUpdate(@Validated @RequestBody Meter meter) {
        return super.saveOrUpdate(meter);
    }

    /**
     * 删除计量器具
     */
    @Operation(summary = "删除数据")
    @PreAuthorize("hasPerm('ems:meter:remove')")
    @Log(title = "计量器具", businessType = BusinessType.DELETE)
    @DeleteMapping("/{id}")
    @Override
    public Result<String> delete(@PathVariable Long id) {
        return super.delete(id);
    }

    /**
     * 批量删除计量器具
     */
    @Operation(summary = "批量删除数据")
    @PreAuthorize("hasPerm('ems:meter:remove')")
    @Log(title = "计量器具", businessType = BusinessType.DELETE)
    @DeleteMapping
    @Override
    public Result<String> deleteBatch(@RequestBody List<Long> ids) {
        return super.deleteBatch(ids);
    }

    /**
     * 分页查询计量器具
     */
    @Operation(summary = "分页查询")
    @PreAuthorize("hasPerm('ems:meter:list')")
    @GetMapping
    public Result<Map<String, Object>> findByPage(Pager pager, MeterQueryParam param) {
        return findByPage(pager, buildSpecification(param));
    }

    private Specification<Meter> buildSpecification(MeterQueryParam param) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.hasText(param.getCode())) {
                predicates.add(cb.like(root.get("code"), "%" + param.getCode() + "%"));
            }
            if (StringUtils.hasText(param.getName())) {
                predicates.add(cb.like(root.get("name"), "%" + param.getName() + "%"));
            }
            if (StringUtils.hasText(param.getType())) {
                predicates.add(cb.equal(root.get("type"), param.getType()));
            }
            if (param.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), DataItemStatus.fromValue(param.getStatus())));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    /**
     * 根据ID查询计量器具
     */
    @Operation(summary = "按ID查询")
    @PreAuthorize("hasPerm('ems:meter:query')")
    @GetMapping("/{id}")
    @Override
    public Result<Meter> findById(@PathVariable Long id) {
        return super.findById(id);
    }

    /**
     * 查询所有计量器具
     */
    @Operation(summary = "查询所有数据")
    @PreAuthorize("hasPerm('ems:meter:list')")
    @GetMapping("/all")
    public Result<List<Meter>> findAll() {
        return super.findAll();
    }

    @Operation(summary = "更新计量器具")
    @Log(title = "仪表管理", businessType = BusinessType.UPDATE)
    @PreAuthorize("hasPerm('ems:meter:edit')")
    @PutMapping("/{id}")
    @Override
    public Result<Meter> update(@PathVariable Long id, @RequestBody @Validated Meter meter) {
        // 确保ID一致
        meter.setId(id);
        return saveOrUpdate(meter);
    }
}
