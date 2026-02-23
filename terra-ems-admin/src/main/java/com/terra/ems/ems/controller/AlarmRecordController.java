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
import com.terra.ems.ems.entity.AlarmRecord;
import com.terra.ems.ems.param.AlarmRecordQueryParam;
import com.terra.ems.ems.service.AlarmRecordService;
import com.terra.ems.framework.controller.ReadableController;
import com.terra.ems.framework.definition.dto.Pager;
import com.terra.ems.framework.service.ReadableService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.terra.ems.common.annotation.Log;
import com.terra.ems.common.enums.BusinessType;

/**
 * 报警历史记录控制器
 *
 * @author dengxueping
 * @since 2026-01-11
 */
@RestController
@RequestMapping("/alarm/records")
@Tag(name = "报警历史记录")
public class AlarmRecordController extends ReadableController<AlarmRecord, Long> {

    private final AlarmRecordService alarmRecordService;

    public AlarmRecordController(AlarmRecordService alarmRecordService) {
        this.alarmRecordService = alarmRecordService;
    }

    @Override
    protected ReadableService<AlarmRecord, Long> getReadableService() {
        return alarmRecordService;
    }

    /**
     * 分页查询报警记录
     */
    @Operation(summary = "分页查询报警记录")
    @PreAuthorize("hasPerm('ems:alarm-record:list')")
    @GetMapping
    public Result<Map<String, Object>> findByPage(Pager pager, AlarmRecordQueryParam param) {
        return findByPage(pager, buildSpecification(param));
    }

    private Specification<AlarmRecord> buildSpecification(AlarmRecordQueryParam param) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (param.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), param.getStatus()));
            }
            if (param.getMeterPointId() != null) {
                predicates.add(cb.equal(root.get("alarmConfig").get("meterPoint").get("id"), param.getMeterPointId()));
            }
            if (param.getAlarmLimitTypeId() != null) {
                predicates.add(
                        cb.equal(root.get("alarmConfig").get("alarmLimitType").get("id"), param.getAlarmLimitTypeId()));
            }
            if (param.getStartTime() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("triggerTime"), param.getStartTime()));
            }
            if (param.getEndTime() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("triggerTime"), param.getEndTime()));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    /**
     * 处理报警记录
     */
    @Operation(summary = "处理报警记录")
    @Log(title = "告警记录", businessType = BusinessType.UPDATE)
    @PreAuthorize("hasPerm('ems:alarm-record:handle')")
    @PostMapping("/{id}/handle")
    public Result<AlarmRecord> handleAlarm(
            @PathVariable Long id,
            @RequestParam String remark,
            @RequestParam Integer status) {
        return Result.content(alarmRecordService.handleAlarm(id, remark, status));
    }
}
