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

import com.terra.ems.common.annotation.Log;
import com.terra.ems.common.domain.Result;
import com.terra.ems.common.enums.BusinessType;
import com.terra.ems.ems.entity.Gateway;
import com.terra.ems.ems.service.GatewayService;
import com.terra.ems.framework.controller.BaseController;
import com.terra.ems.framework.definition.dto.Pager;
import com.terra.ems.framework.service.BaseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.*;

/**
 * 网关管理控制器
 *
 * @author dengxueping
 * @since 2026-03-21
 */
@Tag(name = "网关管理")
@RestController
@RequestMapping("/gateways")
public class GatewayController extends BaseController<Gateway, Long> {

    private final GatewayService gatewayService;
    private final StringRedisTemplate redisTemplate;

    public GatewayController(GatewayService gatewayService, StringRedisTemplate redisTemplate) {
        this.gatewayService = gatewayService;
        this.redisTemplate = redisTemplate;
    }

    @Override
    protected BaseService<Gateway, Long> getService() {
        return gatewayService;
    }

    @Operation(summary = "保存或更新网关")
    @PostMapping
    @Override
    @Log(title = "网关", businessType = BusinessType.UPDATE)
    @PreAuthorize("hasAnyPerm('ems:gateway:add', 'ems:gateway:edit')")
    public Result<Gateway> saveOrUpdate(@Validated @RequestBody Gateway gateway) {
        return super.saveOrUpdate(gateway);
    }

    @Operation(summary = "删除网关")
    @DeleteMapping("/{id}")
    @Override
    @Log(title = "网关", businessType = BusinessType.DELETE)
    @PreAuthorize("hasPerm('ems:gateway:remove')")
    public Result<String> delete(@PathVariable Long id) {
        return super.delete(id);
    }

    @Operation(summary = "批量删除网关")
    @DeleteMapping
    @Override
    @Log(title = "网关", businessType = BusinessType.DELETE)
    @PreAuthorize("hasPerm('ems:gateway:remove')")
    public Result<String> deleteBatch(@RequestBody List<Long> ids) {
        return super.deleteBatch(ids);
    }

    @Operation(summary = "分页查询")
    @PreAuthorize("hasPerm('ems:gateway:list')")
    @GetMapping
    public Result<Map<String, Object>> findByPage(Pager pager,
            @RequestParam(required = false) String code,
            @RequestParam(required = false) String name) {
        Specification<Gateway> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.hasText(code)) {
                predicates.add(cb.like(root.get("code"), "%" + code + "%"));
            }
            if (StringUtils.hasText(name)) {
                predicates.add(cb.like(root.get("name"), "%" + name + "%"));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
        return findByPage(pager, spec);
    }

    @Operation(summary = "按ID查询")
    @PreAuthorize("hasPerm('ems:gateway:query')")
    @GetMapping("/{id}")
    @Override
    public Result<Gateway> findById(@PathVariable Long id) {
        return super.findById(id);
    }

    @Operation(summary = "查询所有网关")
    @PreAuthorize("hasPerm('ems:gateway:list')")
    @GetMapping("/all")
    public Result<List<Gateway>> findAll() {
        return super.findAll();
    }

    @Operation(summary = "根据用能单元查询网关")
    @GetMapping("/energy-unit/{energyUnitId}")
    public Result<List<Gateway>> findByEnergyUnitId(@PathVariable Long energyUnitId) {
        return Result.content(gatewayService.findByEnergyUnitId(energyUnitId));
    }

    /**
     * 查询所有网关的在线状态
     *
     * 从 Redis 读取 Collector 写入的网关心跳数据，
     * key 格式：ems:gateway:{code}:status（300秒过期）
     * key 存在 = 在线，key 过期/不存在 = 离线
     *
     * @return 网关编码 → 在线状态的映射
     */
    @Operation(summary = "查询网关在线状态")
    @GetMapping("/online-status")
    public Result<Map<String, GatewayOnlineInfo>> getOnlineStatus() {
        List<Gateway> gateways = gatewayService.findAll();
        Map<String, GatewayOnlineInfo> result = new LinkedHashMap<>();
        ObjectMapper mapper = new ObjectMapper();

        for (Gateway gw : gateways) {
            String key = "ems:gateway:" + gw.getCode() + ":status";
            String value = redisTemplate.opsForValue().get(key);

            GatewayOnlineInfo info = new GatewayOnlineInfo();
            if (value != null) {
                info.setOnline(true);
                try {
                    var node = mapper.readTree(value);
                    if (node.has("lastHeartbeat")) {
                        info.setLastHeartbeat(node.get("lastHeartbeat").asText());
                    }
                } catch (Exception ignored) {}
            } else {
                info.setOnline(false);
            }
            result.put(gw.getCode(), info);
        }

        return Result.content(result);
    }

    @lombok.Data
    public static class GatewayOnlineInfo {
        private boolean online;
        private String lastHeartbeat;
    }
}
