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

package com.terra.ems.system.service;

import com.terra.ems.framework.jpa.repository.BaseRepository;
import com.terra.ems.framework.service.BaseService;
import com.terra.ems.system.entity.SysUserNotice;
import com.terra.ems.system.repository.SysUserNoticeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * 用户通知已读状态服务
 *
 * @author Antigravity
 * @since 2026-02-11
 */

@Service
public class SysUserNoticeService extends BaseService<SysUserNotice, Long> {

    private final SysUserNoticeRepository userNoticeRepository;

    @Autowired
    public SysUserNoticeService(SysUserNoticeRepository userNoticeRepository) {
        this.userNoticeRepository = userNoticeRepository;
    }

    @Override
    public BaseRepository<SysUserNotice, Long> getRepository() {
        return userNoticeRepository;
    }

    /**
     * 标记为已读
     *
     * @param userId   用户ID
     * @param noticeId 通知ID
     */
    @Transactional(rollbackFor = Exception.class)
    public void markAsRead(Long userId, Long noticeId) {
        if (!userNoticeRepository.existsByUserIdAndNoticeId(userId, noticeId)) {
            SysUserNotice userNotice = new SysUserNotice();
            userNotice.setUserId(userId);
            userNotice.setNoticeId(noticeId);
            userNotice.setReadTime(LocalDateTime.now());
            userNoticeRepository.save(userNotice);
        }
    }

    /**
     * 判断是否已读
     *
     * @param userId   用户ID
     * @param noticeId 通知ID
     * @return 是否已读
     */
    public boolean isRead(Long userId, Long noticeId) {
        return userNoticeRepository.existsByUserIdAndNoticeId(userId, noticeId);
    }
}
