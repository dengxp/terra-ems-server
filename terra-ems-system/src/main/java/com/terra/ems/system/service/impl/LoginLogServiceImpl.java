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

package com.terra.ems.system.service.impl;

import org.springframework.stereotype.Service;
import com.terra.ems.framework.manager.AsyncManager;
import com.terra.ems.framework.service.LoginLogService;
import com.terra.ems.system.manager.factory.AsyncFactory;

/**
 * 登录日志服务实现
 * 
 * @author dengxueping
 */
@Service
public class LoginLogServiceImpl implements LoginLogService {
    @Override
    public void recordLoginLog(String username, String status, String message, Object... args) {
        AsyncManager.me().execute(AsyncFactory.recordLoginLog(username, status, message, args));
    }
}
