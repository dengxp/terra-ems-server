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

import com.terra.ems.common.domain.Option;
import com.terra.ems.framework.jpa.repository.BaseRepository;
import com.terra.ems.framework.service.BaseService;
import com.terra.ems.system.entity.SysPost;
import com.terra.ems.system.repository.SysPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 系统岗位服务
 *
 * @author dengxueping
 * @since 2026-01-11
 */

@Service
@RequiredArgsConstructor
public class SysPostService extends BaseService<SysPost, Long> {

    private final SysPostRepository postRepository;

    /**
     * 获取数据访问仓库
     *
     * @return 岗位仓库
     */
    @Override
    public BaseRepository<SysPost, Long> getRepository() {
        return postRepository;
    }

    /**
     * 校验岗位编码是否唯一
     *
     * @param code 岗位编码
     * @param id   岗位ID（排除自身）
     * @return true 表示唯一，false 表示已存在
     */
    public boolean checkCodeUnique(String code, Long id) {
        return postRepository.findByCode(code)
                .map(post -> post.getId().equals(id))
                .orElse(true);
    }

    /**
     * 查询岗位选项列表
     *
     * @return 岗位选项列表
     */
    public List<Option<Long>> findOptions() {
        return postRepository.findOptions();
    }
}
