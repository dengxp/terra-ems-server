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

package com.terra.ems.system.vo;

import com.terra.ems.common.annotation.Excel;
import com.terra.ems.system.entity.SysUser;
import lombok.Getter;
import lombok.Setter;

/**
 * 用户导入对象
 * 
 * @author terra
 */
@Getter
@Setter
public class SysUserImportVo extends SysUser {

    /**
     * 导入结果
     */
    @Excel(name = "导入结果", type = Excel.Type.EXPORT, sort = 100)
    private String importStatus;

    /**
     * 导入详情
     */
    @Excel(name = "导入详情", type = Excel.Type.EXPORT, sort = 101)
    private String importReason;

    public SysUserImportVo() {
    }

    public SysUserImportVo(SysUser user) {
        this.setId(user.getId());
        this.setUsername(user.getUsername());
        this.setRealName(user.getRealName());
        this.setPhone(user.getPhone());
        this.setDept(user.getDept());
        this.setGender(user.getGender());
        this.setStatus(user.getStatus());
        this.setRemark(user.getRemark());
        this.setEmployeeNo(user.getEmployeeNo());
        this.setRoleIds(user.getRoleIds());
        this.setPostIds(user.getPostIds());
        this.setDeptId(user.getDeptId());
    }
}
