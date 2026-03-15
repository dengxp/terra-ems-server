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

package com.terra.ems.system.repository;

import com.terra.ems.common.domain.Option;
import com.terra.ems.framework.enums.DataItemStatus;
import com.terra.ems.framework.jpa.repository.BaseRepository;
import com.terra.ems.system.entity.SysPost;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * 系统岗位仓库
 *
 * @author dengxueping
 * @since 2026-01-11
 */

@Repository
public interface SysPostRepository extends BaseRepository<SysPost, Long> {

    /**
     * 根据编码查询岗位
     */
    Optional<SysPost> findByCode(String code);

    /**
     * 根据名称模糊查询
     */
    List<SysPost> findByNameContainingOrderBySortOrderAsc(String name);

    /**
     * 根据状态查询
     */
    List<SysPost> findByStatusOrderBySortOrderAsc(DataItemStatus status);

    /**
     * 根据ID集合查询
     */
    Set<SysPost> findByIdIn(Collection<Long> ids);

    /**
     * 查询岗位选项列表
     *
     * @return 岗位选项列表
     */
    @Query("select new com.terra.ems.common.domain.Option(p.id, p.name) from SysPost p where p.status = com.terra.ems.framework.enums.DataItemStatus.ENABLE order by p.sortOrder asc")
    List<Option<Long>> findOptions();
}
