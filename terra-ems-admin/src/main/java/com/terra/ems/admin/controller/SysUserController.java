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

package com.terra.ems.admin.controller;

import com.terra.ems.common.domain.Result;
import com.terra.ems.common.utils.poi.ExcelUtil;
import com.terra.ems.framework.controller.BaseController;
import com.terra.ems.framework.definition.dto.Pager;
import com.terra.ems.framework.enums.DataItemStatus;
import com.terra.ems.framework.service.BaseService;
import com.terra.ems.system.entity.SysUser;
import com.terra.ems.system.mapper.UserMapper;
import com.terra.ems.system.param.UserQueryParam;
import com.terra.ems.system.service.SysUserService;
import com.terra.ems.system.vo.SysUserImportVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.criteria.Predicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import jakarta.servlet.http.HttpServletResponse;
import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.terra.ems.common.annotation.Log;
import com.terra.ems.common.enums.BusinessType;
import com.terra.ems.common.annotation.SuperPermission;
import org.springframework.security.access.prepost.PreAuthorize;
import java.util.HashSet;
import java.util.Set;

import java.time.LocalDateTime;

/**
 * 系统用户控制器
 *
 * @author dengxueping
 * @since 2026-01-11
 */

@Tag(name = "系统管理-用户管理", description = "系统用户的CRUD及特定业务接口")
@RestController
@RequestMapping("/system/user")
public class SysUserController extends BaseController<SysUser, Long> {

    private static final Logger log = LoggerFactory.getLogger(SysUserController.class);

    private final SysUserService userService;
    private final UserMapper userMapper;

    @Autowired
    public SysUserController(SysUserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    /**
     * 获取业务服务
     *
     * @return 系统用户服务
     */
    @Override
    protected BaseService<SysUser, Long> getService() {
        return userService;
    }

    /**
     * 构建查询条件
     */
    protected Specification<SysUser> buildSpecification(Map<String, Object> params) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            String keywordParam = (String) params.get("keyword");
            String username = (String) params.get("username");
            String phone = (String) params.get("phone");
            Object deptIdObj = params.get("deptId");
            Object statusObj = params.get("status");
            Object beginTimeObj = params.get("beginTime");
            Object endTimeObj = params.get("endTime");

            // 关键字模糊查询
            if (StringUtils.hasText(keywordParam)) {
                String keyword = "%" + keywordParam + "%";
                predicates.add(cb.or(
                        cb.like(root.get("username"), keyword),
                        cb.like(root.get("realName"), keyword),
                        cb.like(root.get("phone"), keyword)));
            }

            // 精确/前缀查询
            if (StringUtils.hasText(username)) {
                predicates.add(cb.like(root.get("username"), username + "%"));
            }
            if (StringUtils.hasText(phone)) {
                predicates.add(cb.like(root.get("phone"), phone + "%"));
            }
            if (deptIdObj != null) {
                Long deptId = null;
                if (deptIdObj instanceof Long) {
                    deptId = (Long) deptIdObj;
                } else if (deptIdObj instanceof String) {
                    deptId = Long.valueOf((String) deptIdObj);
                }
                if (deptId != null) {
                    predicates.add(cb.equal(root.get("dept").get("id"), deptId));
                }
            }
            Object roleIdObj = params.get("roleId");
            if (roleIdObj != null) {
                Long roleId = null;
                if (roleIdObj instanceof Long) {
                    roleId = (Long) roleIdObj;
                } else if (roleIdObj instanceof String) {
                    roleId = Long.valueOf((String) roleIdObj);
                }
                if (roleId != null) {
                    predicates.add(cb.equal(root.join("roles").get("id"), roleId));
                }
            }
            if (statusObj != null) {
                Integer status = null;
                if (statusObj instanceof Integer) {
                    status = (Integer) statusObj;
                } else if (statusObj instanceof String) {
                    status = Integer.valueOf((String) statusObj);
                }
                if (status != null) {
                    predicates.add(cb.equal(root.get("status"), DataItemStatus.fromValue(status)));
                }
            }

            // 时间范围
            if (beginTimeObj != null) {
                LocalDateTime beginTime = null;
                if (beginTimeObj instanceof LocalDateTime) {
                    beginTime = (LocalDateTime) beginTimeObj;
                } else if (beginTimeObj instanceof String) {
                    String timeStr = (String) beginTimeObj;
                    if (timeStr.length() == 10) {
                        timeStr += "T00:00:00";
                    }
                    beginTime = LocalDateTime.parse(timeStr);
                }
                if (beginTime != null) {
                    predicates.add(cb.greaterThanOrEqualTo(root.get("createdAt"), beginTime));
                }
            }
            if (endTimeObj != null) {
                LocalDateTime endTime = null;
                if (endTimeObj instanceof LocalDateTime) {
                    endTime = (LocalDateTime) endTimeObj;
                } else if (endTimeObj instanceof String) {
                    String timeStr = (String) endTimeObj;
                    if (timeStr.length() == 10) {
                        timeStr += "T23:59:59";
                    }
                    endTime = LocalDateTime.parse(timeStr);
                }
                if (endTime != null) {
                    predicates.add(cb.lessThanOrEqualTo(root.get("createdAt"), endTime));
                }
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    /**
     * 分页查询用户
     *
     * @param pager  分页参数
     * @param params 查询参数
     * @return 分页结果
     */
    @Operation(summary = "查询用户列表")
    @PreAuthorize("hasPerm('system:user:list')")
    @GetMapping
    public Result<Map<String, Object>> findByPage(Pager pager, UserQueryParam param) {
        // 如果没有指定排序，则默认按创建时间倒序
        if (pager.getSortOrders().isEmpty()) {
            pager.addSortOrder("createdAt", "DESC");
        }
        return result(userService.findPage(pager, param));
    }

    @Operation(summary = "查询用户详情")
    @PreAuthorize("hasPerm('system:user:query')")
    @GetMapping("/{id:\\d+}")
    @Override
    public Result<SysUser> findById(@PathVariable Long id) {
        return super.findById(id);
    }

    /**
     * 保存或更新用户信息
     *
     * @param user 用户实体
     * @return 操作结果及实体
     */
    @Log(title = "用户管理", businessType = BusinessType.UPDATE)
    @Operation(summary = "保存用户")
    @PreAuthorize("hasAnyPerm('system:user:add', 'system:user:edit')")
    @PostMapping
    @Override
    public Result<SysUser> saveOrUpdate(@RequestBody @Validated SysUser user) {
        Result<SysUser> result = super.saveOrUpdate(user);
        if (result.getData() != null) {
            result.getData().setPassword(null);
        }
        return result;
    }

    /**
     * 通过ID更新用户
     */
    @Operation(summary = "修改用户")
    @PreAuthorize("hasPerm('system:user:edit')")
    @PutMapping("/{id:\\d+}")
    @Override
    public Result<SysUser> update(@PathVariable Long id, @RequestBody @Validated SysUser domain) {
        return super.update(id, domain);
    }

    /**
     * 删除用户
     */
    @Operation(summary = "删除用户")
    @PreAuthorize("hasPerm('system:user:remove')")
    @Log(title = "用户管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{id:\\d+}")
    @Override
    public Result<String> delete(@PathVariable Long id) {
        return super.delete(id);
    }

    /**
     * 批量删除用户
     */
    @Operation(summary = "批量删除用户")
    @PreAuthorize("hasPerm('system:user:remove')")
    @Log(title = "用户管理", businessType = BusinessType.DELETE)
    @DeleteMapping
    @Override
    public Result<String> deleteBatch(@RequestBody List<Long> ids) {
        return super.deleteBatch(ids);
    }

    /**
     * 查询所有用户
     */
    @Operation(summary = "查询所有数据")
    @PreAuthorize("hasPerm('system:user:list')")
    @GetMapping("/all")
    public Result<List<SysUser>> findAll() {
        return super.findAll();
    }

    /**
     * 创建用户 (复杂逻辑，支持关联绑定)
     *
     * @param user 用户实体
     * @return 操作结果及实体
     */
    @Log(title = "用户管理", businessType = BusinessType.INSERT)
    @Operation(summary = "新增用户(带关联)")
    @PreAuthorize("hasPerm('system:user:add')")
    @PostMapping("/create")
    public Result<SysUser> create(@RequestBody @Validated SysUser user) {
        log.info("[Terra]|- SysUser Controller create: user: {}", user);
        SysUser result = userService.create(user);
        if (result != null) {
            result.setPassword(null);
        }
        return Result.success("创建成功", result);
    }

    /**
     * 重置用户密码
     *
     * @param params 用户ID和新密码
     * @return 操作结果
     */
    @Log(title = "用户管理", businessType = BusinessType.UPDATE)
    @Operation(summary = "重置用户密码")
    @PreAuthorize("hasPerm('system:user:resetPwd')")
    @PostMapping("/resetPwd")
    public Result<Void> resetPwd(@RequestBody Map<String, Object> params) {
        Object userId = params.get("userId");
        Object password = params.get("password");
        if (userId == null || password == null || !StringUtils.hasText(password.toString())) {
            return Result.failure("参数错误");
        }
        userService.resetPassword(Long.valueOf(userId.toString()), password.toString());
        return Result.success("重置成功");
    }

    /**
     * 分配用户角色
     *
     * @param id      用户ID
     * @param roleIds 角色ID集合
     * @return 操作结果
     */
    @Log(title = "用户管理", businessType = BusinessType.UPDATE)
    @Operation(summary = "分配用户角色")
    @PreAuthorize("hasPerm('system:user:assignRole')")
    @PostMapping("/{id:\\d+}/roles")
    public Result<Void> updateRoles(@PathVariable Long id, @RequestBody List<Long> roleIds) {
        userService.updateRoles(id, new HashSet<>(roleIds));
        return Result.success("分配成功");
    }

    /**
     * 设置用户超级管理员状态
     *
     * @param id     用户ID
     * @param params 包含 isSuper 的 Map
     * @return 操作结果
     */
    @Log(title = "用户管理", businessType = BusinessType.UPDATE)
    @Operation(summary = "设置超级管理员")
    @SuperPermission
    @PostMapping("/{id:\\d+}/setSuper")
    public Result<Void> setSuper(@PathVariable Long id, @RequestBody Map<String, Boolean> params) {
        Boolean isSuper = params.get("isSuper");
        if (isSuper == null) {
            return Result.failure("参数错误: isSuper 不能为空");
        }
        userService.updateSuperAdminStatus(id, isSuper);
        return Result.success("设置成功");
    }

    /**
     * 获取当前登录用户信息
     *
     * @param principal 当前认证主体
     * @return 用户信息
     */
    @Operation(summary = "获取当前登录用户信息")
    @GetMapping("/current-user")
    public Result<SysUser> findCurrentUser(Principal principal) {
        log.info("[Terra]|- SysUser Controller findCurrentUser, principal: {}", principal);
        if (principal == null) {
            return Result.failure("未获取到登录信息");
        }

        // 从Authentication中获取用户名，然后查询数据库获取最新信息
        Authentication authentication = (Authentication) principal;
        String username = authentication.getName();

        SysUser user = userService.findByUsername(username);
        if (user != null) {
            return Result.success("获取成功", user);
        } else {
            return Result.failure("用户不存在");
        }
    }

    /**
     * 导出用户列表
     */
    @Log(title = "用户管理", businessType = BusinessType.EXPORT)
    @Operation(summary = "导出用户数据")
    @PreAuthorize("hasPerm('system:user:export')")
    @PostMapping("/export")
    public void export(HttpServletResponse response, @RequestParam Map<String, Object> params) {
        log.info("[Terra]|- SysUser Controller export, params: {}", params);
        Specification<SysUser> spec = buildSpecification(params);
        List<SysUser> list = userService.findAll(spec);
        ExcelUtil<SysUser> util = new ExcelUtil<>(SysUser.class);
        util.exportExcel(response, list, "用户数据");
    }

    /**
     * 导出导入模板
     */
    @Operation(summary = "导出导入模板")
    @PreAuthorize("hasPerm('system:user:export')")
    @PostMapping("/exportTemplate")
    public void exportTemplate(HttpServletResponse response) {
        ExcelUtil<SysUser> util = new ExcelUtil<>(SysUser.class);
        util.exportExcel(response, new ArrayList<>(), "用户数据");
    }

    /**
     * 导入用户数据
     */
    @Log(title = "用户管理", businessType = BusinessType.IMPORT)
    @Operation(summary = "导入用户数据")
    @PreAuthorize("hasPerm('system:user:import')")
    @PostMapping("/importData")
    public Result<Map<String, Object>> importData(MultipartFile file, boolean updateSupport)
            throws Exception {
        ExcelUtil<SysUser> util = new ExcelUtil<>(SysUser.class);
        List<SysUser> userList = util.importExcel(file.getInputStream());
        String operName = SecurityContextHolder.getContext().getAuthentication().getName();
        List<SysUserImportVo> resultList = userService.importUser(userList, updateSupport, operName);

        // Always generate result file
        ExcelUtil<SysUserImportVo> exportUtil = new ExcelUtil<>(SysUserImportVo.class);
        Result<String> exportResult = exportUtil.exportExcel(resultList, "用户导入结果");
        String fileName = exportResult.getData();

        long total = resultList.size();
        long failureCount = resultList.stream().filter(u -> "失败".equals(u.getImportStatus())).count();
        long successCount = total - failureCount;

        Map<String, Object> data = new HashMap<>();
        data.put("total", total);
        data.put("successCount", successCount);
        data.put("failureCount", failureCount);
        data.put("fileUrl", fileName);

        if (failureCount > 0) {
            Result<Map<String, Object>> result = Result.success("导入完成（存在失败记录），请查看结果文档");
            return result.data(data);
        }
        Result<Map<String, Object>> result = Result.success("恭喜您，数据已全部导入成功！共 " + total + " 条。");
        return result.data(data);
    }

    /**
     * 下载导入结果
     * 
     * @param fileName 文件名
     * @param response 响应
     */
    @Operation(summary = "下载导入结果")
    @GetMapping("/import/result/download")
    public void downloadImportResult(String fileName, HttpServletResponse response) {
        try {
            ExcelUtil<SysUserImportVo> util = new ExcelUtil<>(SysUserImportVo.class);
            String filePath = util.getAbsoluteFile(fileName);
            File file = new File(filePath);

            log.info("Try to download import result file: path={}, exists={}", filePath, file.exists());

            if (!file.exists()) {
                log.error("Import result file not found: {}", filePath);
                throw new RuntimeException("文件不存在: " + fileName);
            }

            log.info("File size: {} bytes", file.length());

            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setCharacterEncoding("utf-8");
            String encodedFileName = java.net.URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-Disposition",
                    "attachment; filename=" + encodedFileName + "; filename*=utf-8''" + encodedFileName);

            try (java.io.FileInputStream fis = new java.io.FileInputStream(file);
                    java.io.OutputStream os = response.getOutputStream()) {
                byte[] buffer = new byte[1024];
                int len;
                while ((len = fis.read(buffer)) != -1) {
                    os.write(buffer, 0, len);
                }
                os.flush();
            }
        } catch (Exception e) {
            log.error("下载导入结果失败", e);
            try {
                response.setStatus(500); // Set HTTP 500 error
                response.setContentType("application/json");
                response.setCharacterEncoding("utf-8");
                response.getWriter().write("{\"code\":500,\"msg\":\"下载文件失败: " + e.getMessage() + "\"}");
            } catch (java.io.IOException ex) {
                log.error("写入响应失败", ex);
            }
        }
    }

    /**
     * 查询部门管理员选项
     *
     * @param departmentId 部门ID (可选)
     * @param keyword      关键字 (可选)
     * @return 选项列表
     */
    @Operation(summary = "查询部门管理员选项")
    @GetMapping("/options-for-department-manager")
    public Result<List<Map<String, Object>>> findOptionsForDepartmentManager(
            @RequestParam(required = false) Long departmentId,
            @RequestParam(required = false) String keyword) {
        UserQueryParam param = new UserQueryParam();
        param.setKeyword(keyword);
        param.setStatus(DataItemStatus.ENABLE.getValue());

        List<SysUser> users = userService.findList(param);
        List<Map<String, Object>> options = new ArrayList<>();
        for (SysUser user : users) {
            Map<String, Object> map = new HashMap<>();
            map.put("label", user.getRealName());
            map.put("value", user.getId());
            options.add(map);
        }
        return Result.content(options);
    }

    /**
     * 修改个人资料
     */
    @Operation(summary = "修改个人资料")
    @PutMapping("/profile")
    public Result<Void> updateProfile(@RequestBody SysUser user, Principal principal) {
        if (principal == null) {
            return Result.failure("未登录");
        }
        SysUser currentUser = userService.findByUsername(principal.getName());
        user.setId(currentUser.getId());
        userService.updateProfile(user);
        return Result.success("修改成功");
    }

    /**
     * 修改当前用户密码
     */
    @Operation(summary = "修改个人密码")
    @PutMapping("/updatePwd")
    public Result<Void> updatePwd(@RequestBody Map<String, String> params, Principal principal) {
        if (principal == null) {
            return Result.failure("未登录");
        }
        String oldPassword = params.get("oldPassword");
        String newPassword = params.get("newPassword");
        if (!StringUtils.hasText(oldPassword) || !StringUtils.hasText(newPassword)) {
            return Result.failure("旧密码与新密码不能为空");
        }
        SysUser currentUser = userService.findByUsername(principal.getName());
        userService.updatePassword(currentUser.getId(), oldPassword, newPassword);
        return Result.success("修改成功");
    }
}
