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
import com.terra.ems.ems.entity.EnergyType;
import com.terra.ems.ems.service.EnergyTypeService;
import com.terra.ems.framework.controller.BaseController;
import com.terra.ems.framework.enums.DataItemStatus;
import com.terra.ems.framework.service.BaseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * 能源类型控制器
 *
 * @author dengxueping
 * @since 2026-01-11
 */
@Tag(name = "能源类型管理")
@RestController
@RequestMapping("/energy-types")
public class EnergyTypeController extends BaseController<EnergyType, Long> {

    private final EnergyTypeService energyTypeService;

    public EnergyTypeController(EnergyTypeService energyTypeService) {
        this.energyTypeService = energyTypeService;
    }

    @Override
    protected BaseService<EnergyType, Long> getService() {
        return energyTypeService;
    }

    /**
     * 分页查询能源类型（复杂搜索）
     */
    @Operation(summary = "分页搜索")
    @GetMapping("/search")
    public Result<Page<EnergyType>> search(
            @Parameter(description = "编码") @RequestParam(required = false) String code,
            @Parameter(description = "名称") @RequestParam(required = false) String name,
            @Parameter(description = "类别") @RequestParam(required = false) String category,
            @Parameter(description = "状态值") @RequestParam(required = false) Integer status,
            @Parameter(description = "页码") @RequestParam(defaultValue = "1") int pageNumber,
            @Parameter(description = "每页大小") @RequestParam(defaultValue = "10") int pageSize) {

        // 构建 Specification
        Specification<EnergyType> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (StringUtils.hasText(code)) {
                predicates.add(cb.like(root.get("code"), "%" + code + "%"));
            }
            if (StringUtils.hasText(name)) {
                predicates.add(cb.like(root.get("name"), "%" + name + "%"));
            }
            if (StringUtils.hasText(category)) {
                predicates.add(cb.equal(root.get("category"), category));
            }
            if (status != null) {
                DataItemStatus statusEnum = DataItemStatus.fromValue(status);
                predicates.add(cb.equal(root.get("status"), statusEnum));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, Sort.by("sortOrder").ascending());
        return Result.content(energyTypeService.findByPage(spec, pageable));
    }

    /**
     * 查询所有启用的能源类型
     */
    @Operation(summary = "查询所有启用的能源类型")
    @GetMapping("/enabled")
    public Result<List<EnergyType>> listEnabled() {
        return Result.content(energyTypeService.findAllEnabled());
    }

    /**
     * 根据编码查询能源类型
     */
    @Operation(summary = "根据编码查询能源类型")
    @GetMapping("/code/{code}")
    public Result<EnergyType> findByCode(@PathVariable String code) {
        return energyTypeService.findByCode(code)
                .map(Result::content)
                .orElse(Result.failure("能源类型不存在"));
    }

    /**
     * 修改能源类型状态
     */
    @Operation(summary = "修改能源类型状态")
    @PatchMapping("/{id}/status")
    public Result<EnergyType> updateStatus(
            @PathVariable Long id,
            @RequestParam DataItemStatus status) {
        return Result.content(energyTypeService.updateStatus(id, status));
    }

    // BaseController 已提供标准 CRUD (getById, saveOrUpdate, delete)，无需重复实现
}
