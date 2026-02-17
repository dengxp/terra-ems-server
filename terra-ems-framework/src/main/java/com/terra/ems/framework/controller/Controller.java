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
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

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
        return Result.success("查询数据成功!", domains != null ? domains : new ArrayList<>());
    }

    /**
     * 数组转换为统一响应实体
     *
     * @param domains 数组
     * @param <T>     数组类型
     * @return {@link Result} List
     */
    protected <T> Result<T[]> result(T[] domains) {
        if (domains != null) {
            return Result.success("查询数据成功！", domains);
        } else {
            return Result.failure("查询数据失败！");
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
        return Result.success("查询数据成功！", pages != null ? getPageInfoMap(pages) : new HashMap<>(0));
    }

    /**
     * 数据 Map 转换为统一响应实体
     *
     * @param map 数据 Map
     * @return {@link Result} Map
     */
    protected Result<Map<String, Object>> result(Map<String, Object> map) {
        return Result.success("查询数据成功！", map != null ? map : new HashMap<>(0));
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
     * 构建树形结构数据响应 (Map实现，不依赖 Hutool)
     *
     * @param list           原始数据列表
     * @param idGetter       ID获取器
     * @param parentIdGetter 父ID获取器
     * @param nameGetter     名称获取器
     * @param weightGetter   权重获取器
     * @param rootId         根节点ID（null表示构建所有根节点）
     * @param <E>            实体类型
     * @param <T>            ID类型
     * @return 树形结构数据
     */
    protected <E, T> Result<List<Map<String, Object>>> result(List<E> list, Function<E, T> idGetter,
            Function<E, T> parentIdGetter,
            Function<E, String> nameGetter, Function<E, Integer> weightGetter,
            T rootId) {
        if (CollectionUtils.isEmpty(list)) {
            return Result.success("查询数据成功!", new ArrayList<>());
        }

        List<Map<String, Object>> treeList = buildTree(list, idGetter, parentIdGetter, nameGetter, weightGetter,
                rootId);
        return Result.success("查询数据成功!", treeList);
    }

    /**
     * 手动构建树形结构
     */
    @SuppressWarnings("unchecked")
    private <E, T> List<Map<String, Object>> buildTree(List<E> list, Function<E, T> idGetter,
            Function<E, T> parentIdGetter,
            Function<E, String> nameGetter, Function<E, Integer> weightGetter,
            T rootId) {
        // 1. 将所有节点转为 Map 并存入 ID 映射
        Map<T, Map<String, Object>> nodeMap = new HashMap<>(); // ID -> Node
        List<Map<String, Object>> nodeList = new ArrayList<>(); // 所有节点列表 (保持原序或 sortOrder)

        for (E e : list) {
            T id = idGetter.apply(e);
            if (id == null)
                continue;

            // 如果已存在相同 ID 的节点，跳过（防止重复）
            if (nodeMap.containsKey(id)) {
                continue;
            }

            T parentId = parentIdGetter.apply(e);
            String name = nameGetter.apply(e);
            Integer weight = weightGetter.apply(e);

            Map<String, Object> node = new HashMap<>();
            node.put("key", id);
            node.put("title", name);
            node.put("value", id);
            node.put("label", name);
            node.put("id", id);
            node.put("parentId", parentId);
            node.put("name", name);
            node.put("sortOrder", weight != null ? weight : 0);

            nodeMap.put(id, node);
            nodeList.add(node);
        }

        // 2. 组装父子关系
        List<Map<String, Object>> roots = new ArrayList<>();

        for (Map<String, Object> node : nodeList) {
            T parentId = (T) node.get("parentId");

            // 判断是否为根节点：parentId 为 null，或者 parentId 等于指定的 rootId，或者父节点在当前列表中找不到
            boolean isRoot = (parentId == null) ||
                    (rootId != null && parentId.equals(rootId)) ||
                    (!nodeMap.containsKey(parentId));

            if (isRoot) {
                roots.add(node);
            } else {
                Map<String, Object> parentNode = nodeMap.get(parentId);
                if (parentNode != null) {
                    List<Map<String, Object>> children = (List<Map<String, Object>>) parentNode.get("children");
                    if (children == null) {
                        children = new ArrayList<>();
                        parentNode.put("children", children);
                    }
                    children.add(node);
                }
            }
        }

        // 3. 如果需要对 children 排序，可以在这里递归 sort，但通常 list 输入时已经是 sortOrder ASC，所以 sequence
        // 保持一致即可
        return roots;
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
