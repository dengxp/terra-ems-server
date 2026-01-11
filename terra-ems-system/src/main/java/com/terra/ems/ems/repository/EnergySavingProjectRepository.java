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

package com.terra.ems.ems.repository;

import com.terra.ems.ems.entity.EnergySavingProject;
import com.terra.ems.ems.enums.ProjectStatus;
import com.terra.ems.framework.jpa.repository.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * 节能项目仓库
 *
 * @author dengxueping
 * @since 2026-01-11
 */

@Repository
public interface EnergySavingProjectRepository extends BaseRepository<EnergySavingProject, Long> {

        /**
         * 按状态查询项目列表
         */
        List<EnergySavingProject> findByStatusOrderByCreatedAtDesc(ProjectStatus status);

        /**
         * 按负责人查询项目列表
         */
        List<EnergySavingProject> findByLiablePersonContainingOrderByCreatedAtDesc(String liablePerson);

        /**
         * 统计节约量总和
         */
        @Query("SELECT COALESCE(SUM(p.savingAmount), 0) FROM EnergySavingProject p WHERE p.status = :status")
        BigDecimal sumSavingAmountByStatus(@Param("status") ProjectStatus status);

        /**
         * 按状态统计项目数量
         */
        long countByStatus(ProjectStatus status);
}
