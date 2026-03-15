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

package com.terra.ems.system.service;

import com.terra.ems.framework.jpa.repository.BaseRepository;
import com.terra.ems.framework.service.BaseService;
import com.terra.ems.system.entity.SysLoginLog;
import com.terra.ems.system.repository.SysLoginLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 系统访问记录服务
 *
 * @author dengxueping
 * @since 2026-02-10
 */
@Service
@RequiredArgsConstructor
public class SysLoginLogService extends BaseService<SysLoginLog, Long> {

    private final SysLoginLogRepository loginLogRepository;

    @Override
    public BaseRepository<SysLoginLog, Long> getRepository() {
        return loginLogRepository;
    }

    /**
     * 清空登录日志
     */
    public void cleanLoginLog() {
        loginLogRepository.deleteAll();
    }
}
