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
import com.terra.ems.framework.jpa.repository.BaseRepository;
import com.terra.ems.system.entity.SysPermission;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 系统权限动态数据仓库
 *
 * @author antigravity
 * @since 2026-02-19
 */
@Repository
public interface SysPermissionRepository extends BaseRepository<SysPermission, Long> {

    /**
     * 根据权限代码查询权限
     *
     * @param code 权限代码
     * @return 权限对象
     */
    Optional<SysPermission> findByCode(String code);

    /**
     * 查询权限选项列表
     *
     * @return 权限选项列表
     */
    @Query("select new com.terra.ems.common.domain.Option(p.id, concat(p.name, ' (', p.code, ')')) from SysPermission p")
    List<Option<Long>> findOptions();
}
