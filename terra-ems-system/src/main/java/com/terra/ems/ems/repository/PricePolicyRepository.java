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

import com.terra.ems.ems.entity.PricePolicy;
import com.terra.ems.framework.enums.DataItemStatus;
import com.terra.ems.framework.jpa.repository.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Name: PricePolicyRepository.java
 * Email: dengxueping@gmail.com
 * Date: 2026-01-10
 * Description:
 * 能源价格策略数据访问层
 *
 * @author dengxueping
 */
@Repository
public interface PricePolicyRepository extends BaseRepository<PricePolicy, Long> {

    /**
     * 根据编码查询
     */
    Optional<PricePolicy> findByCode(String code);

    /**
     * 检查编码是否存在
     */
    boolean existsByCode(String code);

    /**
     * 根据状态查询并排序
     */
    List<PricePolicy> findByStatusOrderBySortOrderAsc(DataItemStatus status);

    /**
     * 根据能源类型ID查询
     */
    List<PricePolicy> findByEnergyType_IdOrderBySortOrderAsc(Long energyTypeId);

    /**
     * 查询所有并排序
     */
    List<PricePolicy> findAllByOrderBySortOrderAsc();
}
