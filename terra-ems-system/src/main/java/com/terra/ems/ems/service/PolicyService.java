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

import com.terra.ems.ems.entity.Policy;
import com.terra.ems.ems.enums.PolicyType;
import com.terra.ems.ems.repository.PolicyRepository;
import com.terra.ems.framework.enums.DataItemStatus;
import com.terra.ems.framework.jpa.repository.BaseRepository;
import com.terra.ems.framework.service.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Name: PolicyService.java
 * Email: dengxueping@gmail.com
 * Date: 2026-01-10
 * Description:
 * 政策法规服务
 *
 * @author dengxueping
 */
@Service
@RequiredArgsConstructor
public class PolicyService extends BaseService<Policy, Long> {

    private final PolicyRepository repository;

    @Override
    protected BaseRepository<Policy, Long> getRepository() {
        return repository;
    }

    /**
     * 按类型查询启用的政策列表
     */
    public List<Policy> findByType(PolicyType type) {
        return repository.findByTypeAndStatusOrderByIssuingDateDesc(type, DataItemStatus.ENABLE);
    }

    /**
     * 查询所有启用的政策
     */
    public List<Policy> findAllEnabled() {
        return repository.findByStatusOrderByIssuingDateDesc(DataItemStatus.ENABLE);
    }

    /**
     * 分页条件查询
     */
    public Page<Policy> findByConditions(String title, PolicyType type, DataItemStatus status, Pageable pageable) {
        return repository.findByConditions(title, type, status, pageable);
    }

    /**
     * 按类型统计启用的政策数量
     */
    public long countByType(PolicyType type) {
        return repository.countByTypeAndStatus(type, DataItemStatus.ENABLE);
    }

    /**
     * 创建政策法规
     */
    @Transactional(rollbackFor = Exception.class)
    public Policy create(Policy policy) {
        if (policy.getStatus() == null) {
            policy.setStatus(DataItemStatus.ENABLE);
        }
        return repository.save(policy);
    }

    /**
     * 更新政策法规
     */
    @Transactional(rollbackFor = Exception.class)
    public Policy update(Long id, Policy policy) {
        Policy existing = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("政策法规不存在: " + id));

        existing.setTitle(policy.getTitle());
        existing.setType(policy.getType());
        existing.setDepartment(policy.getDepartment());
        existing.setIssuingDate(policy.getIssuingDate());
        existing.setFileUrl(policy.getFileUrl());
        existing.setSummary(policy.getSummary());
        existing.setStatus(policy.getStatus());
        existing.setRemark(policy.getRemark());

        return repository.save(existing);
    }

    /**
     * 更新政策状态
     */
    @Transactional(rollbackFor = Exception.class)
    public Policy updateStatus(Long id, DataItemStatus status) {
        Policy existing = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("政策法规不存在: " + id));
        existing.setStatus(status);
        return repository.save(existing);
    }
}
