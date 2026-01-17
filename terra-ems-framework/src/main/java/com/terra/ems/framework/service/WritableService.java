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

import java.io.Serializable;

/**
 * 可写服务接口
 *
 * @author dengxueping
 * @since 2026-01-11
 */
public interface WritableService<E extends Entity, ID extends Serializable> extends ReadableService<E, ID> {

    /**
     * 保存实体
     *
     * @param domain 实体
     * @return 保存后的实体
     */
    E saveOrUpdate(E domain);

    /**
     * 删除实体
     *
     * @param id 实体ID
     */
    void deleteById(ID id);

    /**
     * 批量删除实体
     *
     * @param ids 实体ID集合
     */
    void deleteAllById(Iterable<ID> ids);

    /**
     * 删除实体
     *
     * @param domain 实体
     */
    void delete(E domain);
}
