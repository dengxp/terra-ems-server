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

package com.terra.ems.admin.controller.monitor;

import com.terra.ems.common.domain.Result;
import com.terra.ems.framework.controller.BaseController;
import com.terra.ems.framework.definition.dto.Pager;
import com.terra.ems.framework.service.BaseService;
import com.terra.ems.system.entity.SysOperationLog;
import com.terra.ems.system.service.SysOperationLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.security.access.prepost.PreAuthorize;
import com.terra.ems.common.annotation.Log;
import com.terra.ems.common.enums.BusinessType;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 操作日志控制器
 *
 * @author dengxueping
 * @since 2026-02-10
 */
@Tag(name = "系统监控-操作日志")
@RestController
@RequestMapping("/monitor/operation-log")
@RequiredArgsConstructor
public class SysOperationLogController extends BaseController<SysOperationLog, Long> {

    private final SysOperationLogService operationLogService;

    @Override
    protected BaseService<SysOperationLog, Long> getService() {
        return operationLogService;
    }

    @Operation(summary = "查询操作日志详情")
    @PreAuthorize("hasPerm('monitor:operlog:query')")
    @GetMapping("/{id:\\d+}")
    @Override
    public Result<SysOperationLog> findById(@PathVariable Long id) {
        return super.findById(id);
    }

    /**
     * 保存或更新操作日志 (禁止)
     */
    @Operation(summary = "提交")
    @PostMapping
    @PutMapping
    @Override
    @PreAuthorize("denyAll()")
    public Result<SysOperationLog> saveOrUpdate(@Validated @RequestBody SysOperationLog log) {
        return Result.failure("操作日志不允许修改");
    }

    /**
     * 通过ID更新操作日志 (禁止)
     */
    @Operation(summary = "更新")
    @PreAuthorize("denyAll()")
    @PutMapping("/{id:\\d+}")
    @Override
    public Result<SysOperationLog> update(@PathVariable Long id, @RequestBody @Validated SysOperationLog domain) {
        return Result.failure("操作日志不允许修改");
    }

    /**
     * 删除操作日志
     */
    @Operation(summary = "删除操作日志")
    @PreAuthorize("hasPerm('monitor:operlog:remove')")
    @Log(title = "操作日志", businessType = BusinessType.DELETE)
    @DeleteMapping("/{id:\\d+}")
    @Override
    public Result<String> delete(@PathVariable Long id) {
        return super.delete(id);
    }

    /**
     * 批量删除操作日志
     */
    @Operation(summary = "批量删除操作日志")
    @PreAuthorize("hasPerm('monitor:operlog:remove')")
    @Log(title = "操作日志", businessType = BusinessType.DELETE)
    @DeleteMapping
    @Override
    public Result<String> deleteBatch(@RequestBody List<Long> ids) {
        return super.deleteBatch(ids);
    }

    /**
     * 分页查询操作日志
     */
    @Operation(summary = "查询操作日志列表")
    @PreAuthorize("hasPerm('monitor:operlog:list')")
    @GetMapping
    public Result<Map<String, Object>> findByPage(
            Pager pager,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String operationName,
            @RequestParam(required = false) Integer businessType,
            @RequestParam(required = false) Integer status) {

        // 如果没有指定排序，则默认按创建时间倒序
        if (pager.getSortOrders().isEmpty()) {
            pager.addSortOrder("createdAt", "DESC");
        }

        Specification<SysOperationLog> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.hasText(title)) {
                predicates.add(cb.like(root.get("title"), "%" + title + "%"));
            }
            if (StringUtils.hasText(operationName)) {
                predicates.add(cb.like(root.get("operationName"), "%" + operationName + "%"));
            }
            if (businessType != null) {
                predicates.add(cb.equal(root.get("businessType"), businessType));
            }
            if (status != null) {
                predicates.add(cb.equal(root.get("status"), status));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
        return findByPage(pager, spec);
    }

    @Operation(summary = "清空操作日志")
    @PreAuthorize("hasPerm('monitor:operlog:remove')")
    @Log(title = "操作日志", businessType = BusinessType.CLEAN)
    @DeleteMapping("/clean")
    public Result<Void> clean() {
        operationLogService.cleanOperationLog();
        return Result.success();
    }
}
