/*
 * Copyright (c) 2024-2026 Terra Technology (Guangzhou) Co., Ltd.
 * Copyright (c) 2024-2026 泰若科技（广州）有限公司.
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
     * 获取指定节点的子节点（懒加载）
     *
     * @param parentId 父节点ID
     * @return 子节点列表
     */
    public List<EnergyUnit> getChildren(Long parentId) {
        return energyUnitRepository.findByParentIdOrderBySortOrderAsc(parentId);
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
