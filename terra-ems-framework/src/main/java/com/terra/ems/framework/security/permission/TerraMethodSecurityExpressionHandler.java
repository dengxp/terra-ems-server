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

package com.terra.ems.framework.security.permission;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;

import java.util.function.Supplier;

/**
 * 自定义方法安全表达式处理器
 * 终极修复：重构以覆盖 Supplier 版本的 createEvaluationContext，确保 100% 注入
 * TerraSecurityExpressionRoot
 */
public class TerraMethodSecurityExpressionHandler extends DefaultMethodSecurityExpressionHandler {

    public TerraMethodSecurityExpressionHandler() {
    }

    /**
     * 重写 Supplier 版本的上下文创建方法。
     * 诊断显示父类的 createSecurityExpressionRoot(Supplier, ...) 是 private 的，无法通过常规重写劫持。
     * 因此我们直接在上下文创建后，强行置换其 RootObject。
     */
    @Override
    public EvaluationContext createEvaluationContext(Supplier<Authentication> authentication, MethodInvocation mi) {
        EvaluationContext ctx = super.createEvaluationContext(authentication, mi);

        if (ctx instanceof StandardEvaluationContext) {
            Authentication auth = authentication.get();
            TerraSecurityExpressionRoot root = createTerraRoot(auth, mi);
            ((StandardEvaluationContext) ctx).setRootObject(root);
        }
        return ctx;
    }

    @Override
    protected MethodSecurityExpressionOperations createSecurityExpressionRoot(Authentication authentication,
            MethodInvocation invocation) {
        // 虽然父类在 Supplier 模式下可能不调用此方法，但在非 Supplier 模式下仍会使用，保留作为兜底
        return createTerraRoot(authentication, invocation);
    }

    private TerraSecurityExpressionRoot createTerraRoot(Authentication authentication, MethodInvocation invocation) {
        TerraSecurityExpressionRoot root = new TerraSecurityExpressionRoot(authentication);
        root.setThis(invocation.getThis());
        root.setPermissionEvaluator(getPermissionEvaluator());
        root.setTrustResolver(getTrustResolver());
        root.setRoleHierarchy(getRoleHierarchy());
        root.setDefaultRolePrefix(getDefaultRolePrefix());
        return root;
    }
}
