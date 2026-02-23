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
