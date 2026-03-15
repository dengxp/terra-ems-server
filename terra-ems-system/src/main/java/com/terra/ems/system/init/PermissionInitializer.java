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

package com.terra.ems.system.init;

import com.terra.ems.system.entity.SysModule;
import com.terra.ems.system.entity.SysPermission;
import com.terra.ems.system.repository.SysModuleRepository;
import com.terra.ems.system.service.SysPermissionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.support.AopUtils;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RestController;
import com.terra.ems.common.annotation.SuperPermission;

import java.lang.reflect.Method;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 权限初始化器
 * 自动扫描Controller中的@PreAuthorize注解，并同步到数据库
 *
 * @author antigravity
 * @since 2026-02-19
 */
@Component
@RequiredArgsConstructor
public class PermissionInitializer implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(PermissionInitializer.class);

    private final ApplicationContext applicationContext;
    private final SysModuleRepository moduleRepository;
    private final SysPermissionService permissionService;

    private PermissionInitializer self;

    @Autowired
    public void setSelf(@Lazy PermissionInitializer self) {
        this.self = self;
    }

    @PersistenceContext
    private EntityManager entityManager;

    // 匹配 hasPerm('code') 或 hasAnyPerm('code') 或 hasAuthority('code')
    private static final Pattern PERM_PATTERN = Pattern
            .compile("(?:has(?:Any)?Perm(?:i)?|hasAuthority)\\(['\"]([^'\"]+)['\"]\\)");

    @Override
    public void run(ApplicationArguments args) {
        // 通过代理对象调用，确保 @Transactional 生效
        self.scanAndSync(false);
    }

    /**
     * 全量同步 (包含清理)
     */
    @Transactional
    public void fullSync() {
        log.warn("[PermissionInitializer] Starting FULL sync. This will clear existing permissions and modules!");
        // 物理清理 (PostgreSQL)
        entityManager
                .createNativeQuery(
                        "TRUNCATE TABLE sys_role_permission, sys_permission, sys_module RESTART IDENTITY CASCADE")
                .executeUpdate();
        self.scanAndSync(true);
    }

    /**
     * 扫描并同步权限
     * 
     * @param isFull 是否为全量模式
     */
    @Transactional
    public void scanAndSync(boolean isFull) {
        log.info("[PermissionInitializer] Starting {} permission sync...", isFull ? "FULL" : "INCREMENTAL");
        Map<String, Object> controllers = applicationContext.getBeansWithAnnotation(RestController.class);

        // 初始化缓存
        Map<String, SysModule> moduleCache = new HashMap<>();
        moduleRepository.findAll().forEach(m -> moduleCache.put(m.getCode(), m));

        Map<String, SysPermission> permissionCache = new HashMap<>();
        if (!isFull) {
            permissionService.findAll().forEach(p -> permissionCache.put(p.getCode(), p));
        }

        List<SysPermission> toSave = new ArrayList<>();

        // 对控制器进行排序处理，确保 ID 分配和自然顺序更加稳定（按模块名排序）
        List<Object> sortedControllers = new ArrayList<>(controllers.values());
        sortedControllers.sort(Comparator.comparing(bean -> resolveModuleName(AopUtils.getTargetClass(bean))));

        for (Object bean : sortedControllers) {
            Class<?> beanClass = AopUtils.getTargetClass(bean);

            // 模块名称提取: 从 @Tag 提取最后一级 (如 "系统管理-用户管理" -> "用户管理")
            String moduleName = resolveModuleName(beanClass);

            for (Method method : beanClass.getMethods()) {
                PreAuthorize preAuthorize = AnnotationUtils.findAnnotation(method, PreAuthorize.class);
                if (preAuthorize != null) {
                    processMethod(preAuthorize.value(), method, moduleName, moduleCache, permissionCache,
                            toSave);
                }
            }
        }

        if (!toSave.isEmpty()) {
            permissionService.saveAll(toSave);
            log.info("[PermissionInitializer] Sync completed. Saved/Updated {} permissions.", toSave.size());
        }

        // 最终清理：删除没有任何权限关联的“孤儿”模块（如旧的 SYSTEM/MONITOR）
        cleanupOrphanModules();
    }

    private void cleanupOrphanModules() {
        log.info("[PermissionInitializer] Cleaning up orphan modules...");
        int deleted = entityManager.createNativeQuery(
                "DELETE FROM sys_module WHERE id NOT IN (SELECT module_id FROM sys_permission WHERE module_id IS NOT NULL)")
                .executeUpdate();
        if (deleted > 0) {
            log.info("[PermissionInitializer] Deleted {} orphan modules.", deleted);
        }
    }

    /**
     * 解析模块名称: 获取 @Tag 最后一级
     */
    private String resolveModuleName(Class<?> beanClass) {
        Tag tag = AnnotationUtils.findAnnotation(beanClass, Tag.class);
        if (tag != null && StringUtils.hasText(tag.name())) {
            String name = tag.name();
            if (name.contains("-")) {
                String[] parts = name.split("-");
                return parts[parts.length - 1].trim();
            }
            return name.trim();
        }
        return "常用功能";
    }

    /**
     * 生成模块编码 (如 system:user:list -> SYSTEM_USER)
     */
    private String generateModuleCode(String permissionCode) {
        if (!StringUtils.hasText(permissionCode)) {
            return "COMMON";
        }
        String[] parts = permissionCode.split(":");
        if (parts.length >= 2) {
            return (parts[0] + "_" + parts[1]).toUpperCase();
        }
        return parts[0].toUpperCase();
    }

    private void processMethod(String expression, Method method, String moduleName,
            Map<String, SysModule> moduleCache, Map<String, SysPermission> permissionCache,
            List<SysPermission> toSave) {
        Matcher matcher = PERM_PATTERN.matcher(expression);
        while (matcher.find()) {
            String code = matcher.group(1);
            if (code.contains("*")) {
                continue;
            }

            Operation operation = AnnotationUtils.findAnnotation(method, Operation.class);
            // 严格采用注解摘要作为权限名
            String permissionName = (operation != null && StringUtils.hasText(operation.summary()))
                    ? operation.summary()
                    : code;

            // 根据权限 code 动态生成模块 code (如 system:user:list -> SYSTEM_USER)
            String moduleCode = generateModuleCode(code);

            // 获取或创建模块
            SysModule module = moduleCache.computeIfAbsent(moduleCode, k -> {
                SysModule m = new SysModule(moduleName, k);
                // 默认排序: 系统开头排前面，其他排后面
                int sortOrder = k.startsWith("SYSTEM") ? 10 : 20;
                m.setSortOrder(sortOrder);
                return moduleRepository.save(m);
            });

            // 如果模块名称发生变化，同步更新
            if (!moduleName.equals(module.getName())) {
                module.setName(moduleName);
                moduleRepository.save(module);
            }

            SysPermission permission = permissionCache.get(code);
            SuperPermission superPerm = AnnotationUtils.findAnnotation(method, SuperPermission.class);
            boolean isSuper = superPerm != null;

            if (permission == null) {
                permission = new SysPermission();
                permission.setCode(code);
                permission.setName(permissionName);
                permission.setModule(module);
                permission.setSuperPermission(isSuper);
                toSave.add(permission);
                permissionCache.put(code, permission);
            } else {
                // 检查名称、模块或超级权限标识是否变更
                boolean nameChanged = !permissionName.equals(permission.getName());
                boolean moduleChanged = permission.getModule() == null
                        || !module.getId().equals(permission.getModule().getId());
                boolean superChanged = isSuper != (permission.getSuperPermission() != null
                        && permission.getSuperPermission());

                if (nameChanged || moduleChanged || superChanged) {
                    permission.setName(permissionName);
                    permission.setModule(module);
                    permission.setSuperPermission(isSuper);
                    toSave.add(permission);
                }
            }
        }
    }
}
