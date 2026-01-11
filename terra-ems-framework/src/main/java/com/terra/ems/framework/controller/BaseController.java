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

package com.terra.ems.framework.controller;

import com.terra.ems.framework.jpa.entity.Entity;
import com.terra.ems.framework.service.BaseService;
import com.terra.ems.framework.service.ReadableService;
import com.terra.ems.framework.service.WritableService;

import java.io.Serializable;

/**
 * Name: BaseController
 * Email: dengxueping@gmail.com
 * Date: 2024-12-14
 * Description: Spring Data JPA 基础 controller 定义
 *
 * @param <E>  Entity 实体类型
 * @param <ID> 主键类型
 * @author dengxueping
 */
public abstract class BaseController<E extends Entity, ID extends Serializable>
        extends WritableController<E, ID> {

    /**
     * 获取Service实例（子类必须实现）
     */
    protected abstract BaseService<E, ID> getService();

    @Override
    protected WritableService<E, ID> getWritableService() {
        return getService();
    }

    @Override
    protected ReadableService<E, ID> getReadableService() {
        return getService();
    }
}
