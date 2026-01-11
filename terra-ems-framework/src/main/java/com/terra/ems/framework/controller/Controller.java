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

import com.terra.ems.common.domain.Result;
import com.terra.ems.framework.jpa.entity.Entity;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.Page;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller基础定义，提供了统一的响应实体转换方法
 *
 * @author dengxueping
 * @since 2026-01-11
 */

public abstract class Controller {

    /**
     * 数据实体转换为统一响应实体
     *
     * @param domain 数据实体
     * @param <E>    {@link Entity} 子类型
     * @return {@link Result} Entity
     */
    protected <E extends Entity> Result<E> result(E domain) {
        return ObjectUtils.isNotEmpty(domain)
                ? Result.content(domain)
                : Result.empty();
    }

    /**
     * 数据列表转换为统一响应实体
     *
     * @param domains 数据实体 List
     * @param <E>     {@link Entity} 子类型
     * @return {@link Result} List
     */
    protected <E extends Entity> Result<List<E>> result(List<E> domains) {
        if (CollectionUtils.isNotEmpty(domains)) {
            return Result.success("查询数据成功!", domains);
        } else if (domains != null) {
            return Result.empty("未查询到数据！");
        } else {
            return Result.failure("查询数据失败");
        }
    }

    /**
     * 数组转换为统一响应实体
     *
     * @param domains 数组
     * @param <T>     数组类型
     * @return {@link Result} List
     */
    protected <T> Result<T[]> result(T[] domains) {
        if (ArrayUtils.isNotEmpty(domains)) {
            return Result.success("查询数据成功！", domains);
        } else {
            return Result.empty("未查询到数据！");
        }
    }

    /**
     * 数据分页对象转换为统一响应实体
     *
     * @param pages 分页查询结果 {@link Page}
     * @param <E>   {@link Entity} 子类型
     * @return {@link Result} Map
     */
    protected <E extends Entity> Result<Map<String, Object>> result(Page<E> pages) {
        if (null == pages) {
            return Result.failure("查询数据失败！");
        }

        if (CollectionUtils.isNotEmpty(pages.getContent())) {
            return Result.success("查询数据成功！", getPageInfoMap(pages));
        } else {
            return Result.empty("未查询到数据！");
        }
    }

    /**
     * 数据 Map 转换为统一响应实体
     *
     * @param map 数据 Map
     * @return {@link Result} Map
     */
    protected Result<Map<String, Object>> result(Map<String, Object> map) {

        if (null == map) {
            return Result.failure("查询数据失败！");
        }

        if (MapUtils.isNotEmpty(map)) {
            return Result.success("查询数据成功！", map);
        } else {
            return Result.empty("未查询到数据！");
        }
    }

    /**
     * 数据操作结果转换为统一响应实体
     *
     * @param parameter 数据ID
     * @return {@link Result} String
     */
    protected Result<String> result(String parameter) {
        if (ObjectUtils.isNotEmpty(parameter)) {
            return Result.success("操作成功!", parameter);
        } else {
            return Result.failure("操作失败!", parameter);
        }
    }

    /**
     * 数据操作结果转换为统一响应实体
     *
     * @param status 操作状态
     * @return {@link Result} String
     */
    protected Result<Boolean> result(boolean status) {
        if (status) {
            return Result.success("操作成功!", true);
        } else {
            return Result.failure("操作失败!", false);
        }
    }

    /**
     * 返回成功信息
     *
     * @return {@link Result} String
     */
    protected Result<String> success() {
        return Result.success();
    }

    /**
     * 返回成功信息
     *
     * @param message 信息
     * @return {@link Result} String
     */
    /**
     * 返回成功结果
     *
     * @param message 成功消息
     * @return 成功结果
     */
    protected Result<String> success(String message) {
        return Result.success(message);
    }

    /**
     * Page 对象转换为 Map
     *
     * @param pages 分页查询结果 {@link Page}
     * @param <E>   {@link Entity} 子类型
     * @return Map
     */
    protected <E extends Entity> Map<String, Object> getPageInfoMap(Page<E> pages) {
        return getPageInfoMap(pages.getContent(), pages.getTotalPages(), pages.getTotalElements());
    }

    /**
     * Page 对象转换为 Map
     *
     * @param content       数据实体 List
     * @param totalPages    分页总页数
     * @param totalElements 总的数据条目
     * @param <E>           {@link Entity} 子类型
     * @return Map
     */
    protected <E extends Entity> Map<String, Object> getPageInfoMap(List<E> content, int totalPages,
            long totalElements) {
        Map<String, Object> result = new HashMap<>(8);
        result.put("content", content);
        result.put("totalPages", totalPages);
        result.put("totalElements", totalElements);
        return result;
    }
}
