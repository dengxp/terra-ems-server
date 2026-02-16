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

import com.terra.ems.common.annotation.Log;
import com.terra.ems.common.domain.CurrentUser;
import com.terra.ems.common.domain.Result;
import com.terra.ems.common.enums.BusinessType;
import com.terra.ems.framework.controller.BaseController;
import com.terra.ems.framework.definition.dto.Pager;
import com.terra.ems.framework.enums.DataItemStatus;
import com.terra.ems.framework.service.BaseService;
import com.terra.ems.system.entity.SysNotice;
import com.terra.ems.system.service.SysNoticeService;
import com.terra.ems.system.service.SysUserNoticeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 通知公告控制器
 *
 * @author dengxueping
 * @since 2026-01-11
 */

@Tag(name = "通知公告")
@RestController
@RequestMapping("/system/notice")
public class SysNoticeController extends BaseController<SysNotice, Long> {

    private final SysNoticeService noticeService;
    private final SysUserNoticeService userNoticeService;

    @Autowired
    public SysNoticeController(SysNoticeService noticeService, SysUserNoticeService userNoticeService) {
        this.noticeService = noticeService;
        this.userNoticeService = userNoticeService;
    }

    /**
     * 获取业务服务
     *
     * @return 通知公告服务
     */
    @Override
    protected BaseService<SysNotice, Long> getService() {
        return noticeService;
    }

    /**
     * 保存或更新通知公告
     *
     * @param notice 通知公告实体
     * @return 操作结果及实体
     */
    @Operation(summary = "保存或更新通知公告")
    @Override
    @PostMapping
    @Log(title = "通知公告", businessType = BusinessType.UPDATE)
    @PreAuthorize("hasAnyAuthority('system:notice:add', 'system:notice:edit')")
    public Result<SysNotice> saveOrUpdate(@Validated @RequestBody SysNotice notice) {
        return super.saveOrUpdate(notice);
    }

    /**
     * 删除通知公告
     *
     * @param id 公告ID
     * @return 操作结果
     */
    @Operation(summary = "删除通知公告")
    @Override
    @DeleteMapping("/{id}")
    @Log(title = "通知公告", businessType = BusinessType.DELETE)
    @PreAuthorize("hasAuthority('system:notice:remove')")
    public Result<String> delete(@PathVariable Long id) {
        return super.delete(id);
    }

    /**
     * 批量删除通知公告
     *
     * @param ids 公告ID集合
     * @return 操作结果
     */
    @Operation(summary = "批量删除通知公告")
    @Override
    @DeleteMapping
    @Log(title = "通知公告", businessType = BusinessType.DELETE)
    @PreAuthorize("hasAuthority('system:notice:remove')")
    public Result<String> deleteBatch(@RequestBody List<Long> ids) {
        return super.deleteBatch(ids);
    }

    /**
     * 分页查询通知公告
     *
     * @param pager      分页参数
     * @param title      公告标题
     * @param noticeType 公告类型
     * @param status     公告状态
     * @param unreadOnly 是否只查未读
     * @return 分页结果
     */
    @Operation(summary = "分页查询通知公告")
    @GetMapping
    public Result<Map<String, Object>> findByPage(
            Pager pager,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String noticeType,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) Boolean unreadOnly) {

        Long userId = getCurrentUserId();

        Specification<SysNotice> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.hasText(title)) {
                predicates.add(cb.like(root.get("noticeTitle"), "%" + title + "%"));
            }
            if (StringUtils.hasText(noticeType)) {
                predicates.add(cb.equal(root.get("noticeType"), noticeType));
            }
            if (StringUtils.hasText(status)) {
                try {
                    DataItemStatus statusEnum = DataItemStatus.fromValue(Integer.parseInt(status));
                    predicates.add(cb.equal(root.get("status"), statusEnum));
                } catch (Exception e) {
                    // 无法转换则忽略此过滤条件
                }
            }

            // 已读未读过滤逻辑
            if (Boolean.TRUE.equals(unreadOnly) && userId != null) {
                // 子查询：查询该用户的已读记录
                jakarta.persistence.criteria.Subquery<Long> subquery = query.subquery(Long.class);
                jakarta.persistence.criteria.Root<com.terra.ems.system.entity.SysUserNotice> subRoot = subquery
                        .from(com.terra.ems.system.entity.SysUserNotice.class);
                subquery.select(subRoot.get("noticeId"));
                subquery.where(cb.equal(subRoot.get("userId"), userId));

                // 排除已读项
                predicates.add(cb.not(root.get("id").in(subquery)));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        Page<SysNotice> page = noticeService.findByPage(spec, pager.getPageable());

        // 转换为包含已读标志的列表
        List<Map<String, Object>> content = page.getContent().stream().map(notice -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", notice.getId());
            map.put("noticeTitle", notice.getNoticeTitle());
            map.put("noticeType", notice.getNoticeType());
            map.put("noticeContent", notice.getNoticeContent());
            map.put("status", notice.getStatus());
            map.put("remark", notice.getRemark());
            map.put("createdAt", notice.getCreatedAt());

            // 添加已读标志
            boolean readFlag = userId != null && userNoticeService.isRead(userId, notice.getId());
            map.put("readFlag", readFlag);

            return map;
        }).collect(Collectors.toList());

        Map<String, Object> result = new HashMap<>();
        result.put("content", content);
        result.put("totalElements", page.getTotalElements());
        result.put("totalPages", page.getTotalPages());
        result.put("size", page.getSize());
        result.put("number", page.getNumber());

        return Result.success("查询成功", result);
    }

    /**
     * 标记公告为已读
     *
     * @param id 公告ID
     * @return 操作结果
     */
    @Operation(summary = "标记公告为已读")
    @PostMapping("/{id}/read")
    public Result<String> markAsRead(@PathVariable Long id) {
        Long userId = getCurrentUserId();
        if (userId != null) {
            userNoticeService.markAsRead(userId, id);
            return Result.success("标记成功");
        }
        return Result.failure("用户未登录");
    }

    /**
     * 获取当前登录用户ID
     */
    private Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof CurrentUser) {
            CurrentUser currentUser = (CurrentUser) authentication.getPrincipal();
            if (currentUser.getUserId() != null) {
                return Long.parseLong(currentUser.getUserId());
            }
        }
        return null;
    }
}
