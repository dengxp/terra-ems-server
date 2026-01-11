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

package com.terra.ems.framework.jpa.aspect;

import com.terra.ems.common.domain.CurrentUser;
import com.terra.ems.framework.jpa.annotation.DataPermission;
import com.terra.ems.framework.jpa.util.DataPermissionContext;
import com.terra.ems.framework.jpa.util.DataPermissionUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

/**
 * Name: JpaDataPermissionAspect
 * Email: dengxueping@gmail.com
 * Date: 2024-01-09
 * Description: JPA数据权限切面实现
 *
 * @author dengxueping
 */
@Aspect
@Component
@Slf4j
public class JpaDataPermissionAspect {

    @Before("@annotation(dataPermission)")
    public void doBefore(JoinPoint point, DataPermission dataPermission) {
        handleDataPermission(dataPermission);
    }

    @After("@annotation(dataPermission)")
    public void doAfter(DataPermission dataPermission) {
        DataPermissionContext.clear();
    }

    private void handleDataPermission(DataPermission dataPermission) {
        CurrentUser user = DataPermissionUtils.getCurrentUser();
        if (user == null) {
            return;
        }

        // 管理员跳过过滤
        if (user.getRoles().contains("admin")) {
            DataPermissionContext.setIsAll(true);
            return;
        }

        // 核心逻辑：从 CurrentUser 中提取预先计算好的 accessibleDeptIds
        if (!CollectionUtils.isEmpty(user.getAccessibleDeptIds())) {
            DataPermissionContext.setDeptIds(user.getAccessibleDeptIds());
        } else {
            // 如果 accessibleDeptIds 为空且不是 admin，则通常默认为仅本人
            DataPermissionContext.setIsSelf(true);
        }
    }
}
