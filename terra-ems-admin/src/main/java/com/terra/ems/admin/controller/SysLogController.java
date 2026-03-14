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

package com.terra.ems.admin.controller;

import com.terra.ems.common.domain.Result;
import com.terra.ems.framework.controller.BaseController;
import com.terra.ems.framework.definition.dto.Pager;
import com.terra.ems.framework.service.BaseService;
import com.terra.ems.system.entity.SysLog;
import com.terra.ems.system.service.SysLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
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
 * 系统日志控制器
 *
 * @author dengxueping
 * @since 2026-01-11
 */

@Tag(name = "系统监控-运行日志")
@RestController
@RequestMapping("/system/log")
public class SysLogController extends BaseController<SysLog, Long> {

    private final SysLogService logService;

    @Autowired
    public SysLogController(SysLogService logService) {
        this.logService = logService;
    }

    /**
     * 获取业务服务
     *
     * @return 系统日志服务
     */
    @Override
    protected BaseService<SysLog, Long> getService() {
        return logService;
    }

    @Operation(summary = "详情")
    @PreAuthorize("hasPerm('system:log:query')")
    @GetMapping("/{id}")
    @Override
    public Result<SysLog> findById(@PathVariable Long id) {
        return super.findById(id);
    }

    /**
     * 保存或更新系统日志 (禁止)
     */
    @Operation(summary = "提交")
    @PostMapping
    @PutMapping
    @Override
    @PreAuthorize("denyAll()")
    public Result<SysLog> saveOrUpdate(@Validated @RequestBody SysLog log) {
        return Result.failure("系统日志不允许修改");
    }

    /**
     * 通过ID更新系统日志 (禁止)
     */
    @Operation(summary = "更新")
    @PreAuthorize("denyAll()")
    @PutMapping("/{id}")
    @Override
    public Result<SysLog> update(@PathVariable Long id, @RequestBody @Validated SysLog domain) {
        return Result.failure("系统日志不允许修改");
    }

    /**
     * 删除系统日志
     */
    @Operation(summary = "删除")
    @PreAuthorize("hasPerm('system:log:remove')")
    @Log(title = "系统日志", businessType = BusinessType.DELETE)
    @DeleteMapping("/{id}")
    @Override
    public Result<String> delete(@PathVariable Long id) {
        return super.delete(id);
    }

    /**
     * 批量删除系统日志
     */
    @Operation(summary = "批量删除")
    @PreAuthorize("hasPerm('system:log:remove')")
    @Log(title = "系统日志", businessType = BusinessType.DELETE)
    @DeleteMapping
    @Override
    public Result<String> deleteBatch(@RequestBody List<Long> ids) {
        return super.deleteBatch(ids);
    }

    /**
     * 分页查询系统日志
     */
    @Operation(summary = "分页查询")
    @PreAuthorize("hasPerm('system:log:list')")
    @GetMapping
    public Result<Map<String, Object>> findByPage(
            Pager pager,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Integer businessType,
            @RequestParam(required = false) Integer status) {
        Specification<SysLog> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.hasText(title)) {
                predicates.add(cb.like(root.get("title"), "%" + title + "%"));
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
}
