/*
 * Copyright (c) 2024 泰若科技（广州）有限公司. All rights reserved.
 */

package com.terra.ems.ems.repository;

import com.terra.ems.ems.entity.Product;
import com.terra.ems.framework.enums.DataItemStatus;
import com.terra.ems.framework.jpa.repository.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 产品信息 Repository
 *
 * @author dengxueping
 * @since 2026-01-25
 */
@Repository
public interface ProductRepository extends BaseRepository<Product, Long> {

    /**
     * 查询指定状态的产品
     *
     * @param status 状态
     * @return 产品列表
     */
    List<Product> findByStatusOrderBySortOrderAsc(DataItemStatus status);
}
