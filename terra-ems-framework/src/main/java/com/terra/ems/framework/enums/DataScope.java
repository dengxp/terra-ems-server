/*
 * Copyright (c) 2025-2026 Terra Technology (Guangzhou) Co., Ltd.
 * Copyright (c) 2025-2026 泰若科技（广州）有限公司.
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

package com.terra.ems.framework.enums;

import lombok.Getter;

/**
 * 数据权限范围枚举
 *
 * @author dengxueping
 * @since 2026-01-11
 */

@Getter
public enum DataScope {

    /**
     * 全部数据权限
     */
    ALL("all", "全部数据权限"),

    /**
     * 本部门数据权限
     */
    DEPT("dept", "本部门数据权限"),

    /**
     * 本部门及以下数据权限
     */
    DEPT_AND_CHILD("dept_and_child", "本部门及以下数据权限"),

    /**
     * 仅本人数据权限
     */
    SELF("self", "仅本人数据权限"),

    /**
     * 自定义数据权限
     */
    CUSTOM("custom", "自定义数据权限");

    private final String code;
    private final String description;

    DataScope(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public static DataScope fromCode(String code) {
        for (DataScope scope : values()) {
            if (scope.code.equals(code)) {
                return scope;
            }
        }
        return SELF; // 默认最小权限
    }
}
