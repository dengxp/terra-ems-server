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

package com.terra.ems.framework.jpa.util;

import java.util.Set;

/**
 * Name: DataPermissionContext
 * Email: dengxueping@gmail.com
 * Date: 2024-01-09
 * Description: 数据权限上下文，用于存储当前线程的数据权限过滤参数
 *
 * @author dengxueping
 */
public class DataPermissionContext {

    private static final ThreadLocal<Set<Long>> DEPT_IDS = new ThreadLocal<>();
    private static final ThreadLocal<Boolean> IS_ALL = new ThreadLocal<>();
    private static final ThreadLocal<Boolean> IS_SELF = new ThreadLocal<>();

    public static void setDeptIds(Set<Long> deptIds) {
        DEPT_IDS.set(deptIds);
    }

    public static Set<Long> getDeptIds() {
        return DEPT_IDS.get();
    }

    public static void setIsAll(Boolean isAll) {
        IS_ALL.set(isAll);
    }

    public static Boolean getIsAll() {
        return IS_ALL.get() != null && IS_ALL.get();
    }

    public static void setIsSelf(Boolean isSelf) {
        IS_SELF.set(isSelf);
    }

    public static Boolean getIsSelf() {
        return IS_SELF.get() != null && IS_SELF.get();
    }

    public static void clear() {
        DEPT_IDS.remove();
        IS_ALL.remove();
        IS_SELF.remove();
    }
}
