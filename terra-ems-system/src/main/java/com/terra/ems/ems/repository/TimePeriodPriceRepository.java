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

import com.terra.ems.ems.entity.TimePeriodPrice;
import com.terra.ems.ems.enums.TimePeriodType;
import com.terra.ems.framework.enums.DataItemStatus;
import com.terra.ems.framework.jpa.repository.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

/**
 * Name: TimePeriodPriceRepository.java
 * Email: dengxueping@gmail.com
 * Date: 2026-01-10
 * Description:
 * 分时电价配置 Repository
 *
 * @author dengxueping
 */
@Repository
public interface TimePeriodPriceRepository extends BaseRepository<TimePeriodPrice, Long> {

    /**
     * 根据电价政策ID查询
     */
    List<TimePeriodPrice> findByPricePolicyIdOrderBySortOrderAsc(Long pricePolicyId);

    /**
     * 查询所有启用的分时电价配置
     */
    List<TimePeriodPrice> findByStatusOrderBySortOrderAsc(DataItemStatus status);

    /**
     * 根据时段类型查询
     */
    List<TimePeriodPrice> findByPeriodTypeAndStatus(TimePeriodType periodType, DataItemStatus status);

    /**
     * 根据时间点查询对应的时段配置
     */
    @Query("SELECT t FROM TimePeriodPrice t WHERE t.status = :status " +
            "AND ((t.startTime <= :time AND t.endTime > :time) " +
            "OR (t.startTime > t.endTime AND (t.startTime <= :time OR t.endTime > :time)))")
    Optional<TimePeriodPrice> findByTimePoint(@Param("time") LocalTime time,
            @Param("status") DataItemStatus status);

    /**
     * 查询所有启用的配置，按开始时间排序
     */
    @Query("SELECT t FROM TimePeriodPrice t WHERE t.status = :status ORDER BY t.startTime ASC")
    List<TimePeriodPrice> findAllEnabledOrderByStartTime(@Param("status") DataItemStatus status);
}
