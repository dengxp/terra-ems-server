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

package com.terra.ems.framework.service;

import com.terra.ems.framework.jpa.entity.Entity;
import com.terra.ems.framework.jpa.repository.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.io.Serializable;
import java.util.List;

/**
 * 只读服务接口
 *
 * @author dengxueping
 * @since 2026-01-11
 */
public interface ReadableService<E extends Entity, ID extends Serializable> {

    /**
     * 获取Repository
     *
     * @return {@link com.terra.ems.framework.jpa.repository.BaseRepository}
     */
    com.terra.ems.framework.jpa.repository.BaseRepository<E, ID> getRepository();

    /**
     * 根据ID查询实体
     *
     * @param id 实体ID
     * @return 实体
     */
    default E findById(ID id) {
        return getRepository().findById(id).orElse(null);
    }

    /**
     * 数据是否存在
     *
     * @param id 数据ID
     * @return true 存在，false 不存在
     */
    default boolean existsById(ID id) {
        return getRepository().existsById(id);
    }

    /**
     * 根据条件查询所有实体
     *
     * @param specification 查询条件
     * @return 实体列表
     */
    default List<E> findAll(Specification<E> specification) {
        return getRepository().findAll(specification);
    }

    /**
     * 根据条件查询所有实体，并排序
     *
     * @param specification 查询条件
     * @param sort          排序
     * @return 实体列表
     */
    default List<E> findAll(Specification<E> specification, Sort sort) {
        return getRepository().findAll(specification, sort);
    }

    /**
     * 查找所有实体
     *
     * @return 实体列表
     */
    default List<E> findAll() {
        return getRepository().findAll();
    }

    /**
     * 查找所有实体
     *
     * @param sort 排序
     * @return 实体列表
     */
    default List<E> findAll(Sort sort) {
        return getRepository().findAll(sort);
    }

    /**
     * 分页查询
     *
     * @param pageNumber 当前页
     * @param pageSize   每页大小
     * @return 分页数据
     */
    default Page<E> findByPage(int pageNumber, int pageSize) {
        return findByPage(org.springframework.data.domain.PageRequest.of(pageNumber, pageSize));
    }

    /**
     * 分页查询
     *
     * @param pageNumber 当前页
     * @param pageSize   每页大小
     * @param sort       排序
     * @return 分页数据
     */
    default Page<E> findByPage(int pageNumber, int pageSize, Sort sort) {
        return findByPage(org.springframework.data.domain.PageRequest.of(pageNumber, pageSize, sort));
    }

    /**
     * 分页查询
     *
     * @param pageable 分页参数
     * @return 分页数据
     */
    default Page<E> findByPage(Pageable pageable) {
        return getRepository().findAll(pageable);
    }

    /**
     * 根据条件分页查询
     *
     * @param specification 查询条件
     * @param pageable      分页参数
     * @return 分页数据
     */
    default Page<E> findByPage(Specification<E> specification, Pageable pageable) {
        return getRepository().findAll(specification, pageable);
    }

    /**
     * 根据条件分页查询
     *
     * @param specification 查询条件
     * @param pageNumber    当前页
     * @param pageSize      每页大小
     * @return 分页数据
     */
    default Page<E> findByPage(Specification<E> specification, int pageNumber, int pageSize) {
        return getRepository().findAll(specification,
                org.springframework.data.domain.PageRequest.of(pageNumber, pageSize));
    }

    /**
     * 获取实体总数
     *
     * @return 总数
     */
    default long count() {
        return getRepository().count();
    }

    /**
     * 根据条件获取实体总数
     *
     * @param specification 查询条件
     * @return 总数
     */
    default long count(Specification<E> specification) {
        return getRepository().count(specification);
    }
}
