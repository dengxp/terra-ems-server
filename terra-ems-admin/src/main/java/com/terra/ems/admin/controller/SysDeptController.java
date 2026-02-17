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

package com.terra.ems.admin.controller;

import com.terra.ems.common.domain.Result;
import com.terra.ems.framework.controller.BaseController;
import com.terra.ems.framework.service.BaseService;
import com.terra.ems.system.entity.SysDept;
import com.terra.ems.system.service.SysDeptService;
import com.terra.ems.system.param.SysDeptQueryParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.terra.ems.framework.definition.dto.Pager;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 部门管理控制器
 *
 * @author dengxueping
 * @since 2026-01-11
 */

@Tag(name = "部门管理")
@RestController
@RequestMapping("/system/dept")
public class SysDeptController extends BaseController<SysDept, Long> {

    private final SysDeptService deptService;

    @Autowired
    public SysDeptController(SysDeptService deptService) {
        this.deptService = deptService;
    }

    /**
     * 获取业务服务
     *
     * @return 部门管理服务
     */
    @Override
    protected BaseService<SysDept, Long> getService() {
        return deptService;
    }

    /**
     * 分页查询部门
     */
    @Operation(summary = "分页查询")
    @GetMapping
    public Result<Map<String, Object>> findByPage(Pager pager, SysDeptQueryParam queryParam) {
        return super.findByPage(pager, buildSpecification(queryParam));
    }

    /**
     * 构建查询规范
     */
    private Specification<SysDept> buildSpecification(SysDeptQueryParam queryParam) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (StringUtils.hasText(queryParam.getName())) {
                predicates.add(cb.like(root.get("name"), "%" + queryParam.getName() + "%"));
            }
            if (queryParam.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), queryParam.getStatus()));
            }
            if (queryParam.getParentId() != null) {
                predicates.add(cb.equal(root.get("parent").get("id"), queryParam.getParentId()));
            } else if (!StringUtils.hasText(queryParam.getName())) {
                // 如果没有名称查询，也没有指定父节点，则默认只给根节点，防止在树形表格中出现重复 Key（父子同时出现在列表中）
                predicates.add(cb.isNull(root.get("parent")));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    /**
     * 查询部门树形结构
     *
     * @return 部门树列表结果
     */
    /**
     * 查询部门树形结构
     *
     * @return 部门树列表结果
     */
    @Operation(summary = "查询部门树")
    @GetMapping("/tree")
    public Result<List<Map<String, Object>>> findTree() {
        return result(deptService.findAllEnabled(), SysDept::getId, SysDept::getParentId, SysDept::getName,
                SysDept::getSortOrder, null);
    }

}
