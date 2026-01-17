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

package com.terra.ems.framework.service;

import com.terra.ems.framework.jpa.entity.Entity;
import com.terra.ems.framework.jpa.repository.BaseRepository;
import com.terra.ems.framework.jpa.util.DataPermissionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

/**
 * 基础服务实现类
 *
 * @author dengxueping
 * @since 2026-01-11
 */
public abstract class BaseService<E extends Entity, ID extends Serializable> implements WritableService<E, ID> {

    /**
     * 获取 Repository
     *
     * @return 基础 Repository
     */
    public abstract BaseRepository<E, ID> getRepository();

    /**
     * 构建模糊查询字符串
     *
     * @param property 属性值
     * @return 模糊查询字符串
     */
    protected String like(String property) {
        if (StringUtils.isNotEmpty(property)) {
            return "%" + property + "%";
        } else {
            return "%%";
        }
    }

    @Override
    public List<E> findAll(Specification<E> specification) {
        Specification<E> dpSpec = DataPermissionUtils.buildSpecification("dept", "createBy");
        return getRepository().findAll(specification == null ? dpSpec : specification.and(dpSpec));
    }

    @Override
    public List<E> findAll(Specification<E> specification, Sort sort) {
        Specification<E> dpSpec = DataPermissionUtils.buildSpecification("dept", "createBy");
        return getRepository().findAll(specification == null ? dpSpec : specification.and(dpSpec), sort);
    }

    @Override
    public Page<E> findByPage(Pageable pageable) {
        return findByPage(null, pageable);
    }

    @Override
    public Page<E> findByPage(Specification<E> specification, Pageable pageable) {
        Specification<E> dpSpec = DataPermissionUtils.buildSpecification("dept", "createBy");
        return getRepository().findAll(specification == null ? dpSpec : specification.and(dpSpec), pageable);
    }
}
