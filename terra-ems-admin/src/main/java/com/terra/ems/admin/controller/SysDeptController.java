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

package com.terra.ems.admin.controller;

import com.terra.ems.common.domain.Result;
import com.terra.ems.framework.controller.BaseController;
import com.terra.ems.framework.service.BaseService;
import com.terra.ems.system.entity.SysDept;
import com.terra.ems.system.service.SysDeptService;
import com.terra.ems.system.param.SysDeptQueryParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.terra.ems.framework.definition.dto.Pager;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 部门管理控制器
 *
 * @author dengxueping
 * @since 2026-01-11
 */

@Tag(name = "部门管理")
@RestController
@RequestMapping("/system/dept")
public class SysDeptController extends BaseController<SysDept, Long> {

    private final SysDeptService deptService;
    private final com.terra.ems.system.service.SysUserService sysUserService;

    @Autowired
    public SysDeptController(SysDeptService deptService, com.terra.ems.system.service.SysUserService sysUserService) {
        this.deptService = deptService;
        this.sysUserService = sysUserService;
    }

    /**
     * 获取业务服务
     *
     * @return 部门管理服务
     */
    @Override
    protected BaseService<SysDept, Long> getService() {
        return deptService;
    }

    /**
     * 分页查询部门
     */
    @Operation(summary = "分页查询")
    @GetMapping
    public Result<Map<String, Object>> findByPage(Pager pager, SysDeptQueryParam queryParam) {
        return super.findByPage(pager, buildSpecification(queryParam));
    }

    /**
     * 构建查询规范
     */
    private Specification<SysDept> buildSpecification(SysDeptQueryParam queryParam) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (StringUtils.hasText(queryParam.getName())) {
                predicates.add(cb.like(root.get("name"), "%" + queryParam.getName() + "%"));
            }
            if (queryParam.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), queryParam.getStatus()));
            }
            if (queryParam.getParentId() != null) {
                predicates.add(cb.equal(root.get("parent").get("id"), queryParam.getParentId()));
            } else if (!StringUtils.hasText(queryParam.getName())) {
                // 如果没有名称查询，也没有指定父节点，则默认只给根节点，防止在树形表格中出现重复 Key（父子同时出现在列表中）
                predicates.add(cb.isNull(root.get("parent")));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    /**
     * 查询部门树形结构
     *
     * @return 部门树列表结果
     */
    /**
     * 查询部门树形结构
     *
     * @return 部门树列表结果
     */
    @Operation(summary = "查询部门树")
    @GetMapping("/tree")
    public Result<List<SysDept>> findTree() {
        List<SysDept> allDepts = deptService.findAllWithDetails();
        // 深度复制或转换为非托管对象以避免 JPA orphanRemoval 风险
        List<SysDept> detachedDepts = new ArrayList<>();
        for (SysDept dept : allDepts) {
            SysDept newDept = new SysDept();
            // 排除 children (手动构建)
            // 排除 parentId, managerId (防止 setters 覆盖 parent/manager 实体)
            org.springframework.beans.BeanUtils.copyProperties(dept, newDept, "children", "parentId", "managerId");
            detachedDepts.add(newDept);
        }

        List<SysDept> tree = buildDeptTree(detachedDepts);
        return Result.content(tree);
    }

    /**
     * 构建部门树
     *
     * @param depts 部门列表
     * @return 树形结构
     */
    private List<SysDept> buildDeptTree(List<SysDept> depts) {
        List<SysDept> returnList = new ArrayList<>();
        List<Long> tempList = new ArrayList<>();
        for (SysDept dept : depts) {
            tempList.add(dept.getId());
        }
        for (SysDept dept : depts) {
            // 如果是顶级节点, 遍历该父节点的所有子节点
            if (dept.getParentId() == null || !tempList.contains(dept.getParentId())) {
                recursionFn(depts, dept);
                returnList.add(dept);
            }
        }
        if (returnList.isEmpty()) {
            return depts;
        }
        return returnList;
    }

    /**
     * 递归列表
     */
    private void recursionFn(List<SysDept> list, SysDept t) {
        // 得到子节点列表
        List<SysDept> childList = getChildList(list, t);
        t.setChildren(childList);
        for (SysDept tChild : childList) {
            if (hasChild(list, tChild)) {
                recursionFn(list, tChild);
            }
        }
    }

    /**
     * 得到子节点列表
     */
    private List<SysDept> getChildList(List<SysDept> list, SysDept t) {
        List<SysDept> tlist = new ArrayList<>();
        for (SysDept n : list) {
            if (n.getParentId() != null && n.getParentId().equals(t.getId())) {
                tlist.add(n);
            }
        }
        return tlist;
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<SysDept> list, SysDept t) {
        return getChildList(list, t).size() > 0;
    }

    /**
     * 获取指定ID部门的成员
     *
     * @param id    部门ID
     * @param pager 分页参数
     * @param param 查询参数
     * @return 成员列表
     */
    @Operation(summary = "获取部门成员")
    @GetMapping("/{id}/members")
    public Result<Map<String, Object>> getMembers(@PathVariable Long id, Pager pager,
            com.terra.ems.system.param.UserQueryParam param) {
        param.setDeptId(id);
        return result(sysUserService.findPage(pager, param));
    }

    /**
     * 添加部门成员
     *
     * @param id     部门ID
     * @param params 参数(userIds)
     * @return 结果
     */
    @Operation(summary = "添加部门成员")
    @PostMapping("/{id}/members")
    public Result<Void> addMembers(@PathVariable Long id, @RequestBody Map<String, Object> params) {
        List<Integer> userIdsInt = (List<Integer>) params.get("userIds");
        if (userIdsInt == null || userIdsInt.isEmpty()) {
            return Result.failure("请选择用户");
        }
        List<Long> userIds = userIdsInt.stream().map(Integer::longValue).toList();
        deptService.addMembers(id, userIds);
        return Result.success("添加成功");
    }

    /**
     * 移除部门成员
     *
     * @param id     部门ID
     * @param userId 用户ID
     * @return 结果
     */
    @Operation(summary = "移除部门成员")
    @DeleteMapping("/{id}/members/{userId}")
    public Result<Void> removeMember(@PathVariable Long id, @PathVariable Long userId) {
        deptService.removeMember(id, userId);
        return Result.success("移除成功");
    }

    /**
     * 批量移除部门成员
     *
     * @param id      部门ID
     * @param userIds 用户ID集合
     * @return 结果
     */
    @Operation(summary = "批量移除部门成员")
    @DeleteMapping("/{id}/members")
    public Result<Void> removeMembers(@PathVariable Long id, @RequestBody List<Long> userIds) {
        deptService.removeMembers(id, userIds);
        return Result.success("移除成功");
    }

}
