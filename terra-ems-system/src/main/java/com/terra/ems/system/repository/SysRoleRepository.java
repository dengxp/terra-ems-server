package com.terra.ems.system.repository;

import com.terra.ems.common.domain.Option;
import com.terra.ems.framework.enums.DataItemStatus;
import com.terra.ems.framework.jpa.repository.BaseRepository;
import com.terra.ems.system.entity.SysRole;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * 角色仓库
 *
 * @author dengxueping
 * @since 2026-02-16
 */
@Repository
public interface SysRoleRepository extends BaseRepository<SysRole, Long> {
    Set<SysRole> findByIdIn(Collection<Long> ids);

    /**
     * 查询角色选项列表
     *
     * @return 角色选项列表
     */
    @Query("select new com.terra.ems.common.domain.Option(r.id, r.name) from SysRole r where r.status = com.terra.ems.framework.enums.DataItemStatus.ENABLE")
    List<Option<Long>> findOptions();
}
