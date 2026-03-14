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

package com.terra.ems.ems.service;

import com.terra.ems.ems.entity.AlarmRecord;
import com.terra.ems.ems.repository.AlarmRecordRepository;
import com.terra.ems.framework.jpa.repository.BaseRepository;
import com.terra.ems.framework.service.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * 报警历史记录服务
 *
 * @author dengxueping
 * @since 2026-01-11
 */

@Service
@RequiredArgsConstructor
public class AlarmRecordService extends BaseService<AlarmRecord, Long> {

    private final AlarmRecordRepository alarmRecordRepository;

    @Override
    public BaseRepository<AlarmRecord, Long> getRepository() {
        return alarmRecordRepository;
    }

    /**
     * 处理报警记录
     *
     * @param id     记录ID
     * @param remark 处理备注
     * @param status 处理状态 (1: 已处理, 2: 忽略)
     * @return 报警记录
     */
    @Transactional(rollbackFor = Exception.class)
    public AlarmRecord handleAlarm(Long id, String remark, Integer status) {
        AlarmRecord record = findById(id);
        if (record == null) {
            throw new IllegalArgumentException("报警记录不存在");
        }
        record.setStatus(status);
        record.setHandleRemark(remark);
        record.setHandleTime(LocalDateTime.now());
        return saveOrUpdate(record);
    }
}
