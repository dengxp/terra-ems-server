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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.terra.ems.common.annotation.Log;
import com.terra.ems.common.enums.BusinessType;
import org.springframework.web.bind.annotation.PathVariable;

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

    /**
     * 保存或更新报警限值类型
     */
    @Operation(summary = "保存或更新报警限值类型")
    @PostMapping
    @Override
    @Log(title = "报警限值类型", businessType = BusinessType.UPDATE)
    @PreAuthorize("hasAnyPerm('ems:alarm-limit-type:add', 'ems:alarm-limit-type:edit')")
    public Result<AlarmLimitType> saveOrUpdate(@Validated @RequestBody AlarmLimitType alarmLimitType) {
        return super.saveOrUpdate(alarmLimitType);
    }

    /**
     * 删除报警限值类型
     */
    @Operation(summary = "删除数据")
    @PreAuthorize("hasPerm('ems:alarm-limit-type:remove')")
    @Log(title = "报警限值类型", businessType = BusinessType.DELETE)
    @DeleteMapping("/{id}")
    @Override
    public Result<String> delete(@PathVariable Long id) {
        return super.delete(id);
    }

    /**
     * 批量删除报警限值类型
     */
    @Operation(summary = "批量删除数据")
    @PreAuthorize("hasPerm('ems:alarm-limit-type:remove')")
    @Log(title = "报警限值类型", businessType = BusinessType.DELETE)
    @DeleteMapping
    @Override
    public Result<String> deleteBatch(@RequestBody List<Long> ids) {
        return super.deleteBatch(ids);
    }

    /**
     * 分页查询报警限值类型
     *
     * @param pager 分页参数
     * @param name  限值名称（模糊匹配）
     * @param code  限值编码（模糊匹配）
     * @return 分页结果
     */
    @GetMapping
    @Operation(summary = "分页查询")
    @PreAuthorize("hasPerm('ems:alarm-limit-type:list')")
    public Result<Map<String, Object>> findByPage(
            Pager pager,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String code) {
        return findByPage(pager, buildSpecification(name, code));
    }

    private Specification<AlarmLimitType> buildSpecification(String name, String code) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.hasText(name)) {
                predicates.add(cb.like(root.get("limitName"), "%" + name + "%"));
            }
            if (StringUtils.hasText(code)) {
                predicates.add(cb.like(root.get("limitCode"), "%" + code + "%"));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    /**
     * 根据ID查询报警限值类型
     */
    @Operation(summary = "按ID查询")
    @PreAuthorize("hasPerm('ems:alarm-limit-type:query')")
    @GetMapping("/{id}")
    @Override
    public Result<AlarmLimitType> findById(@PathVariable Long id) {
        return super.findById(id);
    }

    /**
     * 查询所有报警限值类型
     */
    @Operation(summary = "查询所有数据")
    @PreAuthorize("hasPerm('ems:alarm-limit-type:list')")
    @GetMapping("/all")
    public Result<List<AlarmLimitType>> findAll() {
        return super.findAll();
    }

    /**
     * 获取业务服务
     */
    @Override
    protected BaseService<AlarmLimitType, Long> getService() {
        return alarmLimitTypeService;
    }
}
