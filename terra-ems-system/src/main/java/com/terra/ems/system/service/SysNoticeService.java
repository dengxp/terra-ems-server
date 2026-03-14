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

package com.terra.ems.system.service;

import com.terra.ems.framework.enums.DataItemStatus;
import com.terra.ems.framework.jpa.repository.BaseRepository;
import com.terra.ems.framework.service.BaseService;
import com.terra.ems.framework.websocket.server.WebSocketServer;
import com.terra.ems.system.entity.SysNotice;
import com.terra.ems.system.repository.SysNoticeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import cn.hutool.json.JSONUtil;

/**
 * 通知公告服务
 *
 * @author dengxueping
 * @since 2026-01-11
 */

@Service
public class SysNoticeService extends BaseService<SysNotice, Long> {

    private static final Logger log = LoggerFactory.getLogger(SysNoticeService.class);

    private final SysNoticeRepository noticeRepository;

    @Autowired
    public SysNoticeService(SysNoticeRepository noticeRepository) {
        this.noticeRepository = noticeRepository;
    }

    /**
     * 获取数据访问仓库
     *
     * @return 通知公告仓库
     */
    @Override
    public BaseRepository<SysNotice, Long> getRepository() {
        return noticeRepository;
    }

    /**
     * 保存或更新，并根据状态发送WebSocket通知
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysNotice saveOrUpdate(SysNotice notice) {
        log.info("[SysNoticeService] Starting saveOrUpdate for notice: {}", notice.getNoticeTitle());
        SysNotice savedNotice = super.saveOrUpdate(notice);
        if (savedNotice != null && savedNotice.getStatus() == DataItemStatus.ENABLE) {
            log.info("[SysNoticeService] Notice saved and ENABLED, triggering WebSocket push. id: {}",
                    savedNotice.getId());
            // 推送实时消息
            sendWebSocketNotice(savedNotice);
        } else {
            log.info("[SysNoticeService] Notice saved but not pushed. status: {}",
                    savedNotice != null ? savedNotice.getStatus() : "null");
        }
        return savedNotice;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(Long id) {
        log.info("[SysNoticeService] Deleting notice by id: {}", id);
        super.deleteById(id);
        sendDeleteMessage(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteAllById(Iterable<Long> ids) {
        log.info("[SysNoticeService] Bulk deleting notices: {}", ids);
        super.deleteAllById(ids);
        if (ids != null) {
            ids.forEach(this::sendDeleteMessage);
        }
    }

    private void sendWebSocketNotice(SysNotice notice) {
        Map<String, Object> message = new HashMap<>();
        message.put("type", "NOTICE_NEW");
        message.put("data", notice);

        String json = JSONUtil.toJsonStr(message);
        log.info("[SysNoticeService] Broadcasting WebSocket message: {}", json);

        // 广播给所有人
        WebSocketServer.broadcast(json);
    }

    private void sendDeleteMessage(Long id) {
        Map<String, Object> message = new HashMap<>();
        message.put("type", "NOTICE_DELETE");
        message.put("data", id);

        String json = JSONUtil.toJsonStr(message);
        log.info("[SysNoticeService] Broadcasting deletion message: {}", json);
        WebSocketServer.broadcast(json);
    }
}
