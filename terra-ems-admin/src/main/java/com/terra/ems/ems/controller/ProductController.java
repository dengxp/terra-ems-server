/*
 * Copyright (c) 2024 泰若科技（广州）有限公司. All rights reserved.
 */

package com.terra.ems.ems.controller;

import com.terra.ems.common.domain.Result;
import com.terra.ems.ems.entity.Product;
import com.terra.ems.ems.service.ProductService;
import com.terra.ems.framework.controller.BaseController;
import com.terra.ems.framework.enums.DataItemStatus;
import com.terra.ems.framework.service.BaseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.terra.ems.ems.param.ProductQueryParam;
import com.terra.ems.framework.definition.dto.Pager;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 产品管理控制器
 *
 * @author dengxueping
 * @since 2026-01-25
 */
@Tag(name = "产品管理")
@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController extends BaseController<Product, Long> {

    private final ProductService productService;

    @Override
    protected BaseService<Product, Long> getService() {
        return productService;
    }

    @Operation(summary = "分页查询产品")
    @GetMapping
    public Result<Map<String, Object>> findByPage(Pager pager, ProductQueryParam query) {
        Specification<Product> spec = (root, q, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (query != null) {
                if (StringUtils.hasText(query.getName())) {
                    predicates.add(cb.like(root.get("name"), "%" + query.getName() + "%"));
                }
                if (query.getStatus() != null) {
                    predicates.add(cb.equal(root.get("status"), query.getStatus()));
                }
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        return super.findByPage(pager, spec);
    }

    @Operation(summary = "获取启用的产品列表")
    @GetMapping("/enabled")
    public Result<List<Product>> findEnabled() {
        return Result.content(productService.findEnabled());
    }

    @Operation(summary = "修改状态")
    @PatchMapping("/{id}/status")
    public Result<Product> updateStatus(
            @PathVariable Long id,
            @RequestParam DataItemStatus status) {
        return Result.content(productService.updateStatus(id, status));
    }
}
