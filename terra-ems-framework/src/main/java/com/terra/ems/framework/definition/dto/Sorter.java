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

package com.terra.ems.framework.definition.dto;

import com.terra.ems.framework.rest.annotation.EnumeratedValue;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

/**
 * 排序参数BO对象
 *
 * @author dengxueping
 * @since 2026-01-11
 */

@Getter
@Setter
@Schema(title = "排序参数BO对象")
public class Sorter extends AbstractDto {

    @Schema(name = "排序条件列表", title = "排序字段及排序方向")
    private List<SortOrder> sortOrders = new ArrayList<>();

    public void addSortOrder(String property, String direction) {
        this.sortOrders.add(new SortOrder(property, direction));
    }

    public Sort getSort() {
        if (CollectionUtils.isEmpty(sortOrders)) {
            return Sort.unsorted();
        }

        List<Sort.Order> orders = new ArrayList<>();
        for (SortOrder sortOrder : sortOrders) {
            Sort.Direction direction = Sort.Direction
                    .fromString(sortOrder.getDirection() != null ? sortOrder.getDirection().toUpperCase() : "DESC");
            orders.add(new Sort.Order(direction, sortOrder.getProperty()));
        }
        return Sort.by(orders);
    }

    @Getter
    @Setter
    public static class SortOrder {
        @EnumeratedValue(names = { "asc", "ASC", "desc", "DESC" }, message = "排序方式的值只能是asc|ASC 或者 desc|DESC")
        @Schema(name = "排序方向", title = "排序方向的值只能是大写 ASC 或者 DESC, 默认值：DESC", defaultValue = "DESC")
        private String direction;

        @Schema(name = "属性值", title = "指定排序的字段名称")
        private String property;

        public SortOrder() {
        }

        public SortOrder(String property, String direction) {
            this.property = property;
            this.direction = direction;
        }
    }
}
