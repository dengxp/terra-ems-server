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

package com.terra.ems.ems.service;

import com.terra.ems.common.domain.Option;
import com.terra.ems.ems.entity.Product;
import com.terra.ems.ems.repository.ProductRepository;
import com.terra.ems.framework.enums.DataItemStatus;
import com.terra.ems.framework.service.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    /**
     * 获取所有启用状态的产品选项列表 (Select组件专用)
     *
     * @return 选项列表
     */
    public List<Option<Long>> findOptions() {
        return findEnabled().stream()
                .map(p -> Option.of(p.getId(), p.getName()))
                .collect(Collectors.toList());
    }
}
