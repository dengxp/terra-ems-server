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

import com.terra.ems.ems.entity.EnergySavingProject;
import com.terra.ems.ems.enums.ProjectStatus;
import com.terra.ems.ems.repository.EnergySavingProjectRepository;
import com.terra.ems.framework.jpa.repository.BaseRepository;
import com.terra.ems.framework.service.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * 节能项目服务
 *
 * @author dengxueping
 * @since 2026-01-11
 */

@Service
@RequiredArgsConstructor
public class EnergySavingProjectService extends BaseService<EnergySavingProject, Long> {

    private final EnergySavingProjectRepository repository;

    @Override
    protected BaseRepository<EnergySavingProject, Long> getRepository() {
        return repository;
    }

    /**
     * 按状态查询项目列表
     */
    public List<EnergySavingProject> findByStatus(ProjectStatus status) {
        return repository.findByStatusOrderByCreatedAtDesc(status);
    }

    /**
     * 按负责人查询项目列表
     */
    public List<EnergySavingProject> findByLiablePerson(String liablePerson) {
        return repository.findByLiablePersonContainingOrderByCreatedAtDesc(liablePerson);
    }

    /**
     * 获取指定状态的节约量总和
     */
    public BigDecimal getTotalSavingAmount(ProjectStatus status) {
        return repository.sumSavingAmountByStatus(status);
    }

    /**
     * 获取已完成项目的节约量总和
     */
    public BigDecimal getCompletedSavingAmount() {
        return getTotalSavingAmount(ProjectStatus.COMPLETED);
    }

    /**
     * 按状态统计项目数量
     */
    public long countByStatus(ProjectStatus status) {
        return repository.countByStatus(status);
    }

    /**
     * 创建节能项目
     */
    @Transactional(rollbackFor = Exception.class)
    public EnergySavingProject create(EnergySavingProject project) {
        if (project.getStatus() == null) {
            project.setStatus(ProjectStatus.PLANNING);
        }
        return repository.save(project);
    }

    /**
     * 更新节能项目
     */
    @Transactional(rollbackFor = Exception.class)
    public EnergySavingProject update(Long id, EnergySavingProject project) {
        EnergySavingProject existing = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("节能项目不存在: " + id));

        existing.setName(project.getName());
        existing.setPlan(project.getPlan());
        existing.setImplementationPlan(project.getImplementationPlan());
        existing.setCurrentWork(project.getCurrentWork());
        existing.setLiablePerson(project.getLiablePerson());
        existing.setSavingAmount(project.getSavingAmount());
        existing.setCompletionTime(project.getCompletionTime());
        existing.setStatus(project.getStatus());
        existing.setRemark(project.getRemark());

        return repository.save(existing);
    }

    /**
     * 更新项目状态
     */
    @Transactional(rollbackFor = Exception.class)
    public EnergySavingProject updateStatus(Long id, ProjectStatus status) {
        EnergySavingProject existing = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("节能项目不存在: " + id));
        existing.setStatus(status);
        return repository.save(existing);
    }
}
