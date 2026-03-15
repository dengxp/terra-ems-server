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

package com.terra.ems.ems.repository;

import com.terra.ems.ems.entity.MeterPoint;
import com.terra.ems.framework.enums.DataItemStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.terra.ems.framework.jpa.repository.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 采集点位数据访问层
 *
 * @author dengxueping
 * @since 2026-01-11
 */

@Repository
public interface MeterPointRepository extends BaseRepository<MeterPoint, Long> {

    /**
     * 根据编码查询
     *
     * @param code 编码
     * @return 采集点位
     */
    Optional<MeterPoint> findByCode(String code);

    /**
     * 检查编码是否存在
     *
     * @param code 编码
     * @return 是否存在
     */
    boolean existsByCode(String code);

    /**
     * 根据计量器具ID查询
     *
     * @param meterId 计量器具ID
     * @return 采集点位列表
     */
    List<MeterPoint> findByMeter_IdOrderBySortOrderAsc(Long meterId);

    /**
     * 根据能源类型ID查询
     *
     * @param energyTypeId 能源类型ID
     * @return 采集点位列表
     */
    List<MeterPoint> findByEnergyType_IdOrderBySortOrderAsc(Long energyTypeId);

    /**
     * 根据状态查询
     *
     * @param status 状态
     * @return 采集点位列表
     */
    List<MeterPoint> findByStatusOrderBySortOrderAsc(DataItemStatus status);

    /**
     * 根据点位类型查询
     *
     * @param pointType 点位类型
     * @return 采集点位列表
     */
    List<MeterPoint> findByPointTypeOrderBySortOrderAsc(String pointType);

    /**
     * 分页查询（关联查询）
     *
     * @param pageable 分页参数
     * @return 分页结果
     */
    @Query("SELECT mp FROM MeterPoint mp LEFT JOIN FETCH mp.meter LEFT JOIN FETCH mp.energyType")
    Page<MeterPoint> findAllWithRelations(Pageable pageable);

    /**
     * 根据用能单元ID查询关联的采集点位
     *
     * @param energyUnitId 用能单元ID
     * @return 采集点位列表
     */
    @Query("SELECT mp FROM MeterPoint mp JOIN mp.energyUnits eu WHERE eu.id = :energyUnitId ORDER BY mp.sortOrder ASC")
    List<MeterPoint> findByEnergyUnitId(@Param("energyUnitId") Long energyUnitId);

    /**
     * 统计计量器具下的点位数量
     *
     * @param meterId 计量器具ID
     * @return 点位数量
     */
    long countByMeter_Id(Long meterId);
}
