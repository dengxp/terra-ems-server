/*
 * Copyright (c) 2024 泰若科技（广州）有限公司. All rights reserved.
 */

package com.terra.ems.ems.service;

import com.terra.ems.ems.entity.Product;
import com.terra.ems.ems.repository.ProductRepository;
import com.terra.ems.framework.enums.DataItemStatus;
import com.terra.ems.framework.service.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * 产品信息服务
 *
 * @author dengxueping
 * @since 2026-01-25
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService extends BaseService<Product, Long> {

    private final ProductRepository productRepository;

    @Override
    public ProductRepository getRepository() {
        return productRepository;
    }

    /**
     * 获取所有启用状态的产品
     *
     * @return 产品列表
     */
    public List<Product> findEnabled() {
        return productRepository.findByStatusOrderByCodeAsc(DataItemStatus.ENABLE);
    }

    /**
     * 修改状态
     * 
     * @param id     ID
     * @param status 新状态
     * @return 更新后的实体
     */
    @Transactional
    public Product updateStatus(Long id, DataItemStatus status) {
        Optional<Product> optional = productRepository.findById(id);
        if (optional.isPresent()) {
            Product entity = optional.get();
            entity.setStatus(status);
            return productRepository.save(entity);
        }
        return null;
    }
}
