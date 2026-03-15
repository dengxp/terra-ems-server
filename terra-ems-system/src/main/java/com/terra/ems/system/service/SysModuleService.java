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

import com.terra.ems.common.domain.Option;
import com.terra.ems.framework.jpa.repository.BaseRepository;
import com.terra.ems.framework.service.BaseService;
import com.terra.ems.system.entity.SysModule;
import com.terra.ems.system.repository.SysModuleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 系统模块服务
 *
 * @author antigravity
 * @since 2026-02-19
 */
@Service
@RequiredArgsConstructor
public class SysModuleService extends BaseService<SysModule, Long> {

    private final SysModuleRepository moduleRepository;

    @Override
    public BaseRepository<SysModule, Long> getRepository() {
        return moduleRepository;
    }

    /**
     * 查询模块选项列表
     *
     * @return 模块选项列表
     */
    public List<Option<Long>> findOptions() {
        return moduleRepository.findOptions();
    }

    /**
     * 查询模块树（含权限）
     *
     * @return 模块列表（含权限）
     */
    public List<SysModule> findTree() {
        return moduleRepository.findAll();
    }
}
