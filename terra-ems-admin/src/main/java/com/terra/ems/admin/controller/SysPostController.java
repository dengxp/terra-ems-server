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
package com.terra.ems.admin.controller;

import com.terra.ems.common.annotation.Log;
import com.terra.ems.common.domain.Option;
import com.terra.ems.common.domain.Result;
import com.terra.ems.common.enums.BusinessType;
import com.terra.ems.common.utils.poi.ExcelUtil;
import com.terra.ems.framework.controller.BaseController;
import com.terra.ems.framework.definition.dto.Pager;
import com.terra.ems.framework.enums.DataItemStatus;
import com.terra.ems.framework.service.BaseService;
import com.terra.ems.system.entity.SysPost;
import com.terra.ems.system.service.SysPostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.criteria.Predicate;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 岗位管理控制器
 *
 * @author dengxueping
 * @since 2026-01-11
 */

@Tag(name = "系统管理-岗位管理")
@RestController
@RequestMapping("/system/post")
public class SysPostController extends BaseController<SysPost, Long> {
    private final SysPostService postService;

    @Autowired
    public SysPostController(SysPostService postService) {
        this.postService = postService;
    }

    /**
     * 获取业务服务
     *
     * @return 岗位管理服务
     */
    @Override
    protected BaseService<SysPost, Long> getService() {
        return postService;
    }

    /**
     * 分页查询岗位
     */
    @Operation(summary = "查询岗位列表")
    @PreAuthorize("hasPerm('system:post:list')")
    @GetMapping
    public Result<Map<String, Object>> findByPage(
            Pager pager,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String code,
            @RequestParam(required = false) DataItemStatus status) {

        // 如果没有指定排序，则默认按创建时间倒序
        if (pager.getSortOrders().isEmpty()) {
            pager.addSortOrder("createdAt", "DESC");
        }

        Specification<SysPost> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.hasText(name)) {
                predicates.add(cb.like(root.get("name"), "%" + name + "%"));
            }
            if (StringUtils.hasText(code)) {
                predicates.add(cb.like(root.get("code"), "%" + code + "%"));
            }
            if (status != null) {
                predicates.add(cb.equal(root.get("status"), status));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
        return findByPage(pager, spec);
    }

    @Operation(summary = "查询岗位详情")
    @PreAuthorize("hasPerm('system:post:query')")
    @GetMapping("/{id:\\d+}")
    @Override
    public Result<SysPost> findById(@PathVariable Long id) {
        return super.findById(id);
    }

    /**
     * 保存或更新岗位信息
     *
     * @param post 岗位实体
     * @return 操作结果及实体
     */
    @Log(title = "岗位管理", businessType = BusinessType.UPDATE)
    @Operation(summary = "保存岗位")
    @PostMapping
    @Override
    @PreAuthorize("hasAnyPerm('system:post:add', 'system:post:edit')")
    public Result<SysPost> saveOrUpdate(@Validated @RequestBody SysPost post) {
        if (!postService.checkCodeUnique(post.getCode(), post.getId())) {
            return Result.failure("操作岗位'" + post.getName() + "'失败，岗位编码已存在");
        }
        return super.saveOrUpdate(post);
    }

    /**
     * 通过ID更新岗位
     */
    @Operation(summary = "修改岗位")
    @PreAuthorize("hasPerm('system:post:edit')")
    @PutMapping("/{id:\\d+}")
    @Override
    public Result<SysPost> update(@PathVariable Long id, @RequestBody @Validated SysPost domain) {
        return super.update(id, domain);
    }

    /**
     * 删除岗位
     */
    @Operation(summary = "删除岗位")
    @PreAuthorize("hasPerm('system:post:remove')")
    @Log(title = "岗位管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{id:\\d+}")
    @Override
    public Result<String> delete(@PathVariable Long id) {
        return super.delete(id);
    }

    /**
     * 批量删除岗位
     */
    @Operation(summary = "批量删除岗位")
    @PreAuthorize("hasPerm('system:post:remove')")
    @Log(title = "岗位管理", businessType = BusinessType.DELETE)
    @DeleteMapping
    @Override
    public Result<String> deleteBatch(@RequestBody List<Long> ids) {
        return super.deleteBatch(ids);
    }

    /**
     * 查询所有岗位
     */
    @Operation(summary = "查询所有数据")
    @PreAuthorize("hasPerm('system:post:list')")
    @GetMapping("/all")
    public Result<List<SysPost>> findAll() {
        return super.findAll();
    }

    /**
     * 导出岗位列表
     */
    @Operation(summary = "导出岗位数据")
    @PreAuthorize("hasPerm('system:post:export')")
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysPost post) {
        Specification<SysPost> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.hasText(post.getName())) {
                predicates.add(cb.like(root.get("name"), "%" + post.getName() + "%"));
            }
            if (StringUtils.hasText(post.getCode())) {
                predicates.add(cb.like(root.get("code"), "%" + post.getCode() + "%"));
            }
            if (post.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), post.getStatus()));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
        List<SysPost> list = postService.findAll(spec);
        ExcelUtil<SysPost> util = new ExcelUtil<SysPost>(SysPost.class);
        util.exportExcel(response, list, "岗位数据");
    }

    @Operation(summary = "查询岗位选项")
    @GetMapping("/options")
    public Result<List<Option<Long>>> findOptions() {
        return Result.content(postService.findOptions());
    }
}
