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

package com.terra.ems.framework.security.permission;

import org.aopalliance.intercept.MethodInvocation;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PermissionExpressionTest {

        @Test
        public void testHasPermExpression() throws Exception {
                // 模拟认证信息
                Authentication auth = new UsernamePasswordAuthenticationToken("admin", "pass",
                                Collections.singletonList(new SimpleGrantedAuthority("system:user:list")));

                // 创建处理器
                TerraMethodSecurityExpressionHandler handler = new TerraMethodSecurityExpressionHandler();

                // 模拟方法调用
                MethodInvocation invocation = Mockito.mock(MethodInvocation.class);
                Mockito.when(invocation.getThis()).thenReturn(this);
                Mockito.when(invocation.getMethod())
                                .thenReturn(this.getClass().getDeclaredMethod("testHasPermExpression"));

                // 获取 Root 对象
                MethodSecurityExpressionOperations root = handler.createSecurityExpressionRoot(auth, invocation);

                // 验证 root 是否是我们的 TerraSecurityExpressionRoot
                assertTrue(root instanceof TerraSecurityExpressionRoot);

                // 手动测试 SpEL 表达式
                ExpressionParser parser = new SpelExpressionParser();
                Expression expression = parser.parseExpression("hasPerm('system:user:list')");

                // 执行验证
                Boolean result = expression.getValue(handler.createEvaluationContext(auth, invocation), Boolean.class);
                assertTrue(result);

                // 测试上帝权限
                Authentication superAuth = new UsernamePasswordAuthenticationToken("super", "pass",
                                Collections.singletonList(new SimpleGrantedAuthority("*:*:*")));
                Boolean superResult = expression.getValue(handler.createEvaluationContext(superAuth, invocation),
                                Boolean.class);
                assertTrue(superResult);
        }
}
