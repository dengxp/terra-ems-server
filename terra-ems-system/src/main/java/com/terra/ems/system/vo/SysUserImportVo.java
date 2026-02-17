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
