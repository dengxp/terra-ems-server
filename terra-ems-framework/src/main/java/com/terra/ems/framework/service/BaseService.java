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
 * Name: BaseService
 * Email: dengxueping@gmail.com
 * Date: 2024-12-14
 * Description: 基础服务实现类
 *
 * @author dengxueping
 */
public abstract class BaseService<E extends Entity, ID extends Serializable> implements WritableService<E, ID> {

    protected abstract BaseRepository<E, ID> getRepository();

    protected String like(String property) {
        if (StringUtils.isNotEmpty(property)) {
            return "%" + property + "%";
        } else {
            return "%%";
        }
    }

    @Override
    public E findById(ID id) {
        return getRepository().findById(id).orElse(null);
    }

    @Override
    public List<E> findAll(Specification<E> specification) {
        return getRepository().findAll(specification);
    }

    @Override
    public List<E> findAll(Specification<E> specification, Sort sort) {
        return getRepository().findAll(specification, sort);
    }

    @Override
    public List<E> findAll() {
        return getRepository().findAll();
    }

    @Override
    public Page<E> findByPage(int pageNumber, int pageSize) {
        return findByPage(PageRequest.of(pageNumber, pageSize));
    }

    @Override
    public Page<E> findByPage(int pageNumber, int pageSize, Sort sort) {
        return findByPage(PageRequest.of(pageNumber, pageSize, sort));
    }

    @Override
    public Page<E> findByPage(Pageable pageable) {
        return getRepository().findAll(pageable);
    }

    @Override
    public Page<E> findByPage(Specification<E> specification, Pageable pageable) {
        return getRepository().findAll(specification, pageable);
    }

    @Override
    public long count() {
        return getRepository().count();
    }

    @Override
    public long count(Specification<E> specification) {
        return getRepository().count(specification);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public E saveOrUpdate(E domain) {
        return getRepository().save(domain);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteById(ID id) {
        getRepository().deleteById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(E domain) {
        getRepository().delete(domain);
    }
}
