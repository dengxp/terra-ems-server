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

package com.terra.ems.ems.service;

import com.terra.ems.ems.entity.EnergyUnit;
import com.terra.ems.ems.repository.EnergyUnitRepository;
import com.terra.ems.framework.enums.DataItemStatus;
import com.terra.ems.framework.jpa.repository.BaseRepository;
import com.terra.ems.framework.service.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * 用能单元服务
 *
 * @author dengxueping
 * @since 2026-01-11
 */

@Service
@RequiredArgsConstructor
public class EnergyUnitService extends BaseService<EnergyUnit, Long> {

    private final EnergyUnitRepository energyUnitRepository;

    @Override
    public BaseRepository<EnergyUnit, Long> getRepository() {
        return energyUnitRepository;
    }

    /**
     * 获取完整树形结构
     *
     * @return 根节点列表（包含子节点）
     */
    public List<EnergyUnit> getTree() {
        // 查询所有根节点，子节点会通过 @OneToMany 自动加载
        return energyUnitRepository.findByParentIsNullOrderBySortOrderAsc();
    }

    /**
     * 获取启用状态的树形结构
     *
     * @return 根节点列表（包含子节点）
     */
    public List<EnergyUnit> getEnabledTree() {
        return energyUnitRepository.findByParentIsNullAndStatusOrderBySortOrderAsc(DataItemStatus.ENABLE);
    }

    /**
     * 获取指定节点的子节点（懒加载）
     *
     * @param parentId 父节点ID
     * @return 子节点列表
     */
    public List<EnergyUnit> getChildren(Long parentId) {
        return energyUnitRepository.findByParent_IdOrderBySortOrderAsc(parentId);
    }

    /**
     * 根据编码查询
     *
     * @param code 编码
     * @return 用能单元
     */
    public Optional<EnergyUnit> findByCode(String code) {
        return energyUnitRepository.findByCode(code);
    }

    /**
     * 创建用能单元
     *
     * @param energyUnit 用能单元
     * @param parentId   父节点ID（null表示根节点）
     * @return 创建后的用能单元
     */
    @Transactional(rollbackFor = Exception.class)
    public EnergyUnit create(EnergyUnit energyUnit, Long parentId) {
        // 检查编码是否重复
        if (energyUnitRepository.existsByCode(energyUnit.getCode())) {
            throw new IllegalArgumentException("编码已存在: " + energyUnit.getCode());
        }

        // 设置父节点和层级
        if (parentId != null) {
            EnergyUnit parent = energyUnitRepository.findById(parentId)
                    .orElseThrow(() -> new IllegalArgumentException("父节点不存在: " + parentId));
            energyUnit.setParent(parent);
            energyUnit.setLevel(parent.getLevel() + 1);
        } else {
            energyUnit.setParent(null);
            energyUnit.setLevel(0);
        }

        return energyUnitRepository.save(energyUnit);
    }

    /**
     * 更新用能单元
     *
     * @param id         ID
     * @param energyUnit 用能单元
     * @return 更新后的用能单元
     */
    @Transactional(rollbackFor = Exception.class)
    public EnergyUnit update(Long id, EnergyUnit energyUnit) {
        EnergyUnit existing = energyUnitRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("用能单元不存在: " + id));

        // 检查编码是否被其他记录使用
        Optional<EnergyUnit> byCode = energyUnitRepository.findByCode(energyUnit.getCode());
        if (byCode.isPresent() && !byCode.get().getId().equals(id)) {
            throw new IllegalArgumentException("编码已被使用: " + energyUnit.getCode());
        }

        existing.setCode(energyUnit.getCode());
        existing.setName(energyUnit.getName());
        existing.setSortOrder(energyUnit.getSortOrder());
        existing.setStatus(energyUnit.getStatus());
        existing.setRemark(energyUnit.getRemark());

        return energyUnitRepository.save(existing);
    }

    /**
     * 移动节点（更改父节点）
     *
     * @param id          节点ID
     * @param newParentId 新父节点ID（null表示移动到根级别）
     * @return 更新后的用能单元
     */
    @Transactional(rollbackFor = Exception.class)
    public EnergyUnit move(Long id, Long newParentId) {
        EnergyUnit node = energyUnitRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("用能单元不存在: " + id));

        // 检查是否移动到自己的子节点下（防止循环引用）
        if (newParentId != null) {
            if (newParentId.equals(id)) {
                throw new IllegalArgumentException("不能将节点移动到自身下");
            }
            EnergyUnit newParent = energyUnitRepository.findById(newParentId)
                    .orElseThrow(() -> new IllegalArgumentException("目标父节点不存在: " + newParentId));

            // 检查新父节点是否是当前节点的后代
            if (isDescendant(node, newParent)) {
                throw new IllegalArgumentException("不能将节点移动到其子节点下");
            }

            node.setParent(newParent);
            node.setLevel(newParent.getLevel() + 1);
        } else {
            node.setParent(null);
            node.setLevel(0);
        }

        // 递归更新子节点层级
        updateChildrenLevel(node);

        return energyUnitRepository.save(node);
    }

    /**
     * 检查 potentialDescendant 是否是 node 的后代
     */
    private boolean isDescendant(EnergyUnit node, EnergyUnit potentialDescendant) {
        EnergyUnit current = potentialDescendant.getParent();
        while (current != null) {
            if (current.getId().equals(node.getId())) {
                return true;
            }
            current = current.getParent();
        }
        return false;
    }

    /**
     * 递归更新子节点层级
     */
    private void updateChildrenLevel(EnergyUnit parent) {
        for (EnergyUnit child : parent.getChildren()) {
            child.setLevel(parent.getLevel() + 1);
            energyUnitRepository.save(child);
            updateChildrenLevel(child);
        }
    }

    /**
     * 删除用能单元
     *
     * @param id ID
     */
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        EnergyUnit node = energyUnitRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("用能单元不存在: " + id));

        // 检查是否有子节点
        if (!node.getChildren().isEmpty()) {
            throw new IllegalArgumentException("该节点下存在子节点，请先删除子节点");
        }

        energyUnitRepository.deleteById(id);
    }

    /**
     * 修改状态
     *
     * @param id     ID
     * @param status 状态
     * @return 更新后的用能单元
     */
    @Transactional(rollbackFor = Exception.class)
    public EnergyUnit updateStatus(Long id, DataItemStatus status) {
        EnergyUnit existing = energyUnitRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("用能单元不存在: " + id));
        existing.setStatus(status);
        return energyUnitRepository.save(existing);
    }

    /**
     * 获取所有启用的用能单元
     *
     * @return 启用的用能单元列表
     */
    public List<EnergyUnit> findEnabled() {
        return energyUnitRepository.findByStatusOrderBySortOrderAsc(DataItemStatus.ENABLE);
    }
}
