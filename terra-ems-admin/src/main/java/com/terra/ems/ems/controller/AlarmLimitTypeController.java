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
import com.terra.ems.ems.entity.AlarmLimitType;
import com.terra.ems.ems.service.AlarmLimitTypeService;
import com.terra.ems.framework.controller.BaseController;
import com.terra.ems.framework.definition.dto.Pager;
import com.terra.ems.framework.service.BaseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 报警限值类型控制器
 *
 * @author dengxueping
 * @since 2026-01-11
 */
@RestController
@RequestMapping("/alarm/limit-types")
@Tag(name = "报警限值类型")
@RequiredArgsConstructor
public class AlarmLimitTypeController extends BaseController<AlarmLimitType, Long> {

    private final AlarmLimitTypeService alarmLimitTypeService;

    @Override
    protected Specification<AlarmLimitType> buildSpecification(Map<String, Object> params) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (params.containsKey("keyword") && StringUtils.hasText((String) params.get("keyword"))) {
                String keyword = "%" + params.get("keyword") + "%";
                predicates.add(cb.or(
                        cb.like(root.get("limitName"), keyword),
                        cb.like(root.get("limitCode"), keyword)));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    /**
     * 分页查询报警限值类型
     *
     * @param pager  分页参数
     * @param params 查询参数
     * @return 分页结果
     */
    @GetMapping
    @Operation(summary = "分页查询")
    public Result<Map<String, Object>> findByPage(Pager pager, @RequestParam Map<String, Object> params) {
        return findByPage(pager, buildSpecification(params));
    }

    /**
     * 获取业务服务
     *
     * @return 业务服务
     */
    @Override
    protected BaseService<AlarmLimitType, Long> getService() {
        return alarmLimitTypeService;
    }
}
