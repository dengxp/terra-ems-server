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

import com.terra.ems.ems.entity.EnergyType;
import com.terra.ems.ems.entity.EnergyUnit;
import com.terra.ems.framework.enums.DataItemStatus;
import com.terra.ems.framework.jpa.repository.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 能源类型仓库
 *
 * @author dengxueping
 * @since 2026-01-11
 */

@Repository
public interface EnergyTypeRepository extends BaseRepository<EnergyType, Long> {

    /**
     * 根据编码查询
     *
     * @param code 编码
     * @return 能源类型
     */
    Optional<EnergyType> findByCode(String code);

    /**
     * 检查编码是否存在
     *
     * @param code 编码
     * @return 是否存在
     */
    boolean existsByCode(String code);

    /**
     * 根据类别查询
     *
     * @param category 类别
     * @return 能源类型列表
     */
    List<EnergyType> findByCategory(String category);

    /**
     * 根据状态查询
     *
     * @param status 状态
     * @return 能源类型列表
     */
    List<EnergyType> findByStatus(DataItemStatus status);

    /**
     * 查询所有启用的能源类型，按排序字段排序
     *
     * @param status 状态
     * @return 能源类型列表
     */
    List<EnergyType> findByStatusOrderBySortOrderAsc(DataItemStatus status);
}
