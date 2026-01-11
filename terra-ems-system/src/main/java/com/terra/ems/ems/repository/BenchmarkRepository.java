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

import com.terra.ems.ems.entity.Benchmark;
import com.terra.ems.ems.enums.BenchmarkType;
import com.terra.ems.framework.enums.DataItemStatus;
import com.terra.ems.framework.jpa.repository.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 对标值仓库
 *
 * @author dengxueping
 * @since 2026-01-11
 */

@Repository
public interface BenchmarkRepository extends BaseRepository<Benchmark, Long> {

        /**
         * 根据编码查询
         */
        Optional<Benchmark> findByCode(String code);

        /**
         * 检查编码是否存在
         */
        boolean existsByCode(String code);

        /**
         * 按类型查询启用的对标值
         */
        List<Benchmark> findByTypeAndStatusOrderByCodeAsc(BenchmarkType type, DataItemStatus status);

        /**
         * 查询所有启用的对标值
         */
        List<Benchmark> findByStatusOrderByTypeAscCodeAsc(DataItemStatus status);

        /**
         * 按能源类型查询
         */
        List<Benchmark> findByEnergyTypeIdAndStatusOrderByCodeAsc(Long energyTypeId, DataItemStatus status);

        /**
         * 分页条件查询
         */
        @Query("SELECT b FROM Benchmark b WHERE " +
                        "(:name IS NULL OR b.name LIKE %:name%) AND " +
                        "(:type IS NULL OR b.type = :type) AND " +
                        "(:status IS NULL OR b.status = :status) " +
                        "ORDER BY b.type ASC, b.code ASC")
        Page<Benchmark> findByConditions(
                        @Param("name") String name,
                        @Param("type") BenchmarkType type,
                        @Param("status") DataItemStatus status,
                        Pageable pageable);

        /**
         * 按类型统计数量
         */
        long countByTypeAndStatus(BenchmarkType type, DataItemStatus status);
}
