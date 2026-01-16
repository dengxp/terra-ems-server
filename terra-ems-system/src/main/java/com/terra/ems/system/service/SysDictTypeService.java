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
import com.terra.ems.system.entity.SysDictType;
import com.terra.ems.system.repository.SysDictTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * 字典类型服务类
 *
 * @author dengxueping
 * @since 2026-01-16
 */
@Service
@RequiredArgsConstructor
public class SysDictTypeService extends BaseService<SysDictType, Long> {

    private final SysDictTypeRepository dictTypeRepository;

    @Override
    protected BaseRepository<SysDictType, Long> getRepository() {
        return dictTypeRepository;
    }

    /**
     * 根据类型标识查询
     */
    public Optional<SysDictType> findByType(String type) {
        return dictTypeRepository.findByType(type);
    }
}
