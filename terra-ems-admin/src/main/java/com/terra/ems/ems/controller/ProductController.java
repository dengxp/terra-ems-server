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

import com.terra.ems.common.domain.Option;
import com.terra.ems.ems.entity.Product;
import com.terra.ems.ems.service.ProductService;
import com.terra.ems.framework.controller.BaseController;
import com.terra.ems.framework.enums.DataItemStatus;
import com.terra.ems.framework.service.BaseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.terra.ems.ems.enums.ProductType;
import com.terra.ems.framework.definition.dto.Pager;
import com.terra.ems.common.domain.Result;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.terra.ems.common.annotation.Log;
import com.terra.ems.common.enums.BusinessType;
import org.springframework.security.access.prepost.PreAuthorize;

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

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ProductController.class);

    private final ProductService productService;

    @Override
    protected BaseService<Product, Long> getService() {
        return productService;
    }

    @Operation(summary = "分页查询产品")
    @PreAuthorize("hasPerm('ems:product:list')")
    @GetMapping
    public Result<Map<String, Object>> findByPage(
            Pager pager,
            @RequestParam(required = false) String code,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) Integer status) {

        log.info("[ProductController] findByPage, code: {}, name: {}, type: {}, status: {}", code, name, type, status);

        Specification<Product> spec = (root, q, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (StringUtils.hasText(code)) {
                predicates.add(cb.like(root.get("code"), "%" + code + "%"));
            }
            if (StringUtils.hasText(name)) {
                predicates.add(cb.like(root.get("name"), "%" + name + "%"));
            }
            if (StringUtils.hasText(type)) {
                try {
                    // 尝试根据 value 转换
                    ProductType enumType = ProductType.fromValue(type);
                    predicates.add(cb.equal(root.get("type"), enumType));
                } catch (Exception e) {
                    // 如果 value 转换失败，尝试根据 name 转换
                    try {
                        ProductType enumType = ProductType.valueOf(type);
                        predicates.add(cb.equal(root.get("type"), enumType));
                    } catch (Exception ex) {
                        log.warn("Invalid product type parameter: {}", type);
                    }
                }
            }
            if (status != null) {
                predicates.add(cb.equal(root.get("status"), DataItemStatus.fromValue(status)));
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
    @Log(title = "产品管理", businessType = BusinessType.UPDATE)
    @PreAuthorize("hasPerm('ems:product:edit')")
    @PatchMapping("/{id}/status")
    public Result<Product> updateStatus(
            @PathVariable Long id,
            @RequestParam DataItemStatus status) {
        return Result.content(productService.updateStatus(id, status));
    }

    @Operation(summary = "获取产品选项列表")
    @GetMapping("/options")
    public Result<List<Option<Long>>> findOptions() {
        return Result.content(productService.findOptions());
    }
}
