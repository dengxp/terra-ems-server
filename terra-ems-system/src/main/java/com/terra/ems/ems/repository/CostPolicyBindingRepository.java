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

import com.terra.ems.ems.entity.CostPolicyBinding;
import com.terra.ems.framework.enums.DataItemStatus;
import com.terra.ems.framework.jpa.repository.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * 成本策略绑定仓库
 *
 * @author dengxueping
 * @since 2026-01-11
 */

@Repository
public interface CostPolicyBindingRepository extends BaseRepository<CostPolicyBinding, Long> {

        /**
         * 按用能单元查询
         */
        List<CostPolicyBinding> findByEnergyUnit_IdAndStatusOrderByEffectiveStartDateDesc(Long energyUnitId,
                        DataItemStatus status);

        /**
         * 按电价策略查询
         */
        List<CostPolicyBinding> findByPricePolicy_IdAndStatusOrderByCreatedAtDesc(Long pricePolicyId,
                        DataItemStatus status);

        /**
         * 查询用能单元在指定日期有效的绑定
         */
        @Query("SELECT b FROM CostPolicyBinding b WHERE b.energyUnit.id = :energyUnitId " +
                        "AND b.status = :status " +
                        "AND b.effectiveStartDate <= :date " +
                        "AND (b.effectiveEndDate IS NULL OR b.effectiveEndDate >= :date)")
        Optional<CostPolicyBinding> findEffectiveBinding(
                        @Param("energyUnitId") Long energyUnitId,
                        @Param("date") LocalDate date,
                        @Param("status") DataItemStatus status);

        /**
         * 按用能单元统计绑定数量
         */
        long countByEnergyUnit_IdAndStatus(Long energyUnitId, DataItemStatus status);
}
