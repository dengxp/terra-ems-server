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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.io.Serializable;
import java.util.List;

/**
 * Name: ReadableService
 * Email: dengxueping@gmail.com
 * Date: 2024-12-14
 * Description: 只读服务接口
 *
 * @author dengxueping
 */
public interface ReadableService<E extends Entity, ID extends Serializable> {

    /**
     * 根据ID查询实体
     *
     * @param id 实体ID
     * @return 实体
     */
    E findById(ID id);

    /**
     * 根据条件查询所有实体
     *
     * @param specification 查询条件
     * @return 实体列表
     */
    List<E> findAll(Specification<E> specification);

    /**
     * 根据条件查询所有实体，并排序
     *
     * @param specification 查询条件
     * @param sort          排序
     * @return 实体列表
     */
    List<E> findAll(Specification<E> specification, Sort sort);

    /**
     * 查找所有实体
     *
     * @return 实体列表
     */
    List<E> findAll();

    /**
     * 分页查询
     *
     * @param pageNumber 当前页
     * @param pageSize   每页大小
     * @return 分页数据
     */
    Page<E> findByPage(int pageNumber, int pageSize);

    /**
     * 分页查询
     *
     * @param pageNumber 当前页
     * @param pageSize   每页大小
     * @param sort       排序
     * @return 分页数据
     */
    Page<E> findByPage(int pageNumber, int pageSize, Sort sort);

    /**
     * 分页查询
     *
     * @param pageable 分页参数
     * @return 分页数据
     */
    Page<E> findByPage(Pageable pageable);

    /**
     * 根据条件分页查询
     *
     * @param specification 查询条件
     * @param pageable      分页参数
     * @return 分页数据
     */
    Page<E> findByPage(Specification<E> specification, Pageable pageable);

    /**
     * 获取实体总数
     *
     * @return 总数
     */
    long count();

    /**
     * 根据条件获取实体总数
     *
     * @param specification 查询条件
     * @return 总数
     */
    long count(Specification<E> specification);
}
