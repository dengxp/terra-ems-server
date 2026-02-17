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

package com.terra.ems.admin.controller.monitor;

import com.terra.ems.common.domain.Result;
import com.terra.ems.framework.controller.BaseController;
import com.terra.ems.framework.definition.dto.Pager;
import com.terra.ems.framework.service.BaseService;
import com.terra.ems.system.entity.SysLoginLog;
import com.terra.ems.system.service.SysLoginLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 系统访问记录控制器
 *
 * @author dengxueping
 * @since 2026-02-10
 */
@Tag(name = "系统访问记录")
@RestController
@RequestMapping("/monitor/login-log")
@RequiredArgsConstructor
public class SysLoginLogController extends BaseController<SysLoginLog, Long> {

    private final SysLoginLogService loginLogService;

    @Override
    protected BaseService<SysLoginLog, Long> getService() {
        return loginLogService;
    }

    /**
     * 分页查询登录日志
     */
    @Operation(summary = "分页查询")
    @GetMapping
    public Result<Map<String, Object>> findByPage(
            Pager pager,
            @RequestParam(required = false) String userName,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String ipaddr) {
        Specification<SysLoginLog> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.hasText(userName)) {
                predicates.add(cb.like(root.get("userName"), "%" + userName + "%"));
            }
            if (StringUtils.hasText(status)) {
                predicates.add(cb.equal(root.get("status"), status));
            }
            if (StringUtils.hasText(ipaddr)) {
                predicates.add(cb.like(root.get("ipaddr"), "%" + ipaddr + "%"));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
        return findByPage(pager, spec);
    }

    @Operation(summary = "清空登录日志")
    @DeleteMapping("/clean")
    public Result<Void> clean() {
        loginLogService.cleanLoginLog();
        return Result.success();
    }
}
