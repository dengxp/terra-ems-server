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

import com.terra.ems.ems.entity.EnergyUnit;
import com.terra.ems.ems.enums.EnergyUnitType;
import com.terra.ems.framework.enums.DataItemStatus;
import com.terra.ems.framework.jpa.repository.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Name: EnergyUnitRepository.java
 * Email: dengxueping@gmail.com
 * Date: 2026-01-10
 * Description:
 * 用能单元数据访问层
 *
 * @author dengxueping
 */
@Repository
public interface EnergyUnitRepository extends BaseRepository<EnergyUnit, Long> {

    /**
     * 根据编码查询
     *
     * @param code 编码
     * @return 用能单元
     */
    Optional<EnergyUnit> findByCode(String code);

    /**
     * 检查编码是否存在
     *
     * @param code 编码
     * @return 是否存在
     */
    boolean existsByCode(String code);

    /**
     * 查询所有根节点（无父节点）
     *
     * @return 根节点列表
     */
    List<EnergyUnit> findByParentIsNullOrderBySortOrderAsc();

    /**
     * 查询指定父节点的子节点
     *
     * @param parentId 父节点ID
     * @return 子节点列表
     */
    List<EnergyUnit> findByParent_IdOrderBySortOrderAsc(Long parentId);

    /**
     * 根据状态查询所有根节点
     *
     * @param status 状态
     * @return 根节点列表
     */
    List<EnergyUnit> findByParentIsNullAndStatusOrderBySortOrderAsc(DataItemStatus status);

    /**
     * 查询所有节点（按层级和排序字段排序）
     *
     * @return 节点列表
     */
    List<EnergyUnit> findAllByOrderByLevelAscSortOrderAsc();

    /**
     * 统计子节点数量
     *
     * @param parentId 父节点ID
     * @return 子节点数量
     */
    long countByParent_Id(Long parentId);

    /**
     * 查询指定层级的所有节点
     *
     * @param level 层级
     * @return 节点列表
     */
    List<EnergyUnit> findByLevelOrderBySortOrderAsc(Integer level);

    /**
     * 根据状态查询所有节点
     *
     * @param status 状态
     * @return 节点列表
     */
    List<EnergyUnit> findByStatusOrderBySortOrderAsc(DataItemStatus status);

    // ==================== 支路相关查询 ====================

    /**
     * 根据类型查询所有节点
     *
     * @param unitType 单元类型
     * @param status   状态
     * @return 节点列表
     */
    List<EnergyUnit> findByUnitTypeAndStatusOrderBySortOrderAsc(EnergyUnitType unitType, DataItemStatus status);

    /**
     * 根据父节点和类型查询
     *
     * @param parentId 父节点ID
     * @param unitType 单元类型
     * @return 节点列表
     */
    List<EnergyUnit> findByParent_IdAndUnitTypeOrderBySortOrderAsc(Long parentId, EnergyUnitType unitType);

    /**
     * 根据父节点和类型及状态查询
     *
     * @param parentId 父节点ID
     * @param unitType 单元类型
     * @param status   状态
     * @return 节点列表
     */
    List<EnergyUnit> findByParent_IdAndUnitTypeAndStatusOrderBySortOrderAsc(
            Long parentId, EnergyUnitType unitType, DataItemStatus status);
}
