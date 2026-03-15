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

import com.terra.ems.common.domain.Result;
import com.terra.ems.framework.controller.BaseController;
import com.terra.ems.framework.definition.dto.Pager;
import com.terra.ems.framework.service.BaseService;
import com.terra.ems.system.entity.SysConfig;
import com.terra.ems.system.service.SysConfigService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.terra.ems.common.annotation.SuperPermission;
import com.terra.ems.common.annotation.Log;
import com.terra.ems.common.enums.BusinessType;
import org.springframework.validation.annotation.Validated;

/**
 * 参数配置控制器
 *
 * @author dengxueping
 * @since 2026-01-11
 */

@Tag(name = "系统管理-参数配置")
@RestController
@RequestMapping("/system/config")
public class SysConfigController extends BaseController<SysConfig, Long> {

    private final SysConfigService configService;

    @Autowired
    public SysConfigController(SysConfigService configService) {
        this.configService = configService;
    }

    /**
     * 获取业务服务
     *
     * @return 系统配置服务
     */
    @Override
    protected BaseService<SysConfig, Long> getService() {
        return configService;
    }

    /**
     * 通过键名查询参数值
     *
     * @param configKey 参数键名
     * @return 参数值结果
     */
    /**
     * 通过键名查询参数值
     *
     * @param configKey 参数键名
     * @return 参数值结果
     */
    @Operation(summary = "查询参数值")
    @PreAuthorize("hasPerm('system:config:query')")
    @GetMapping("/configKey/{configKey}")
    public Result<String> findConfigValue(@PathVariable String configKey) {
        return Result.content(configService.getConfigValue(configKey));
    }

    @Operation(summary = "查询参数详情")
    @PreAuthorize("hasPerm('system:config:query')")
    @GetMapping("/{id}")
    @Override
    public Result<SysConfig> findById(@PathVariable Long id) {
        return super.findById(id);
    }

    /**
     * 保存或更新参数配置
     *
     * @param config 参数配置实体
     * @return 操作结果及实体
     */
    @Log(title = "系统配置", businessType = BusinessType.UPDATE)
    @Operation(summary = "保存参数")
    @PostMapping
    @PutMapping
    @Override
    @PreAuthorize("hasAnyPerm('system:config:add', 'system:config:edit')")
    public Result<SysConfig> saveOrUpdate(@Validated @RequestBody SysConfig config) {
        return super.saveOrUpdate(config);
    }

    /**
     * 通过ID更新参数配置
     */
    @Operation(summary = "修改参数")
    @PreAuthorize("hasPerm('system:config:edit')")
    @PutMapping("/{id}")
    @Override
    public Result<SysConfig> update(@PathVariable Long id, @RequestBody @Validated SysConfig domain) {
        return super.update(id, domain);
    }

    /**
     * 删除参数配置
     */
    @Operation(summary = "删除参数")
    @PreAuthorize("hasPerm('system:config:remove')")
    @Log(title = "参数配置", businessType = BusinessType.DELETE)
    @DeleteMapping("/{id}")
    @Override
    public Result<String> delete(@PathVariable Long id) {
        return super.delete(id);
    }

    /**
     * 批量删除参数配置
     */
    @Operation(summary = "批量删除参数")
    @PreAuthorize("hasPerm('system:config:remove')")
    @Log(title = "参数配置", businessType = BusinessType.DELETE)
    @DeleteMapping
    @Override
    public Result<String> deleteBatch(@RequestBody List<Long> ids) {
        return super.deleteBatch(ids);
    }

    /**
     * 分页查询参数配置
     */
    @Operation(summary = "查询参数列表")
    @PreAuthorize("hasPerm('system:config:list')")
    @GetMapping
    public Result<Map<String, Object>> findByPage(
            Pager pager,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String key,
            @RequestParam(required = false) String type) {
        Specification<SysConfig> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.hasText(name)) {
                predicates.add(cb.like(root.get("name"), "%" + name + "%"));
            }
            if (StringUtils.hasText(key)) {
                predicates.add(cb.like(root.get("key"), "%" + key + "%"));
            }
            if (StringUtils.hasText(type)) {
                predicates.add(cb.equal(root.get("type"), type));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
        return findByPage(pager, spec);
    }
}
