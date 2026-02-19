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
package com.terra.ems.system.repository;

import com.terra.ems.framework.enums.DataItemStatus;
import com.terra.ems.framework.jpa.repository.BaseRepository;
import com.terra.ems.system.entity.SysDept;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 系统部门仓库
 *
 * @author dengxueping
 * @since 2026-01-11
 */

@Repository
public interface SysDeptRepository extends BaseRepository<SysDept, Long> {

    /**
     * 根据状态查询部门列表
     */
    List<SysDept> findByStatusOrderBySortOrderAsc(DataItemStatus status);

    /**
     * 根据部门名称查询
     */
    SysDept findByName(String name);

    /**
     * 查询所有启用的部门
     */
    default List<SysDept> findAllEnabled() {
        return findByStatusOrderBySortOrderAsc(DataItemStatus.ENABLE);
    }

    /**
     * 查询所有根部门（无父部门）
     */
    List<SysDept> findByParentIsNullOrderBySortOrderAsc();

    /**
     * 根据父ID查询子部门
     */
    @Query("select e from SysDept e where e.parent.id = :parentId order by e.sortOrder asc")
    List<SysDept> findByParentIdOrderBySortOrderAsc(@Param("parentId") Long parentId);

    /**
     * 根据祖级查询部门列表（性能优化：用于查询某个部门下的所有层级）
     */
    @Query("select d from SysDept d where d.ancestors like concat('%', ?1, '%') or d.id = ?1")
    List<SysDept> findByAncestor(Long deptId);

    /**
     * 根据祖级列表模糊查询
     */
    List<SysDept> findByAncestorsLike(String ancestors);

    /**
     * 查询所有启用的部门（启用 eager loading）
     */
    @Query("select distinct d from SysDept d left join fetch d.parent left join fetch d.manager where d.status = 0 order by d.sortOrder asc")
    List<SysDept> findAllEnabledWithDetails();

    /**
     * 查询所有部门（含禁用）
     */
    @Query("select distinct d from SysDept d left join fetch d.parent left join fetch d.manager order by d.sortOrder asc")
    List<SysDept> findAllWithDetails();
}
