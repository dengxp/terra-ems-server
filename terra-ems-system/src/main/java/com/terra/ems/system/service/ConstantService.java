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

package com.terra.ems.system.service;

import com.terra.ems.framework.enums.DataItemStatus;
import com.terra.ems.framework.enums.Gender;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 常量服务，提供性别、状态等通用字典选项的查询
 *
 * @author dengxueping
 * @since 2026-01-11
 */

@Service
public class ConstantService {

    /**
     * 获取所有选项的预处理 JSON 结构
     * 适用于前端下拉选择器
     *
     * @return 包含所有选项的 Map
     */
    public Map<String, Object> getAllOptions() {
        Map<String, Object> map = new HashMap<>();
        map.put("gender", Gender.getPreprocessedJsonStructure());
        map.put("status", DataItemStatus.getPreprocessedJsonStructure());
        return map;
    }

    /**
     * 获取所有选项的 ID-Value 映射
     *
     * @return 包含所有选项映射的 Map
     */
    public Map<String, Object> getAllMaps() {
        Map<String, Object> map = new HashMap<>();
        map.put("gender", getEnumMap(Gender.values()));
        map.put("status", getEnumMap(DataItemStatus.values()));
        return map;
    }

    private Map<Integer, String> getEnumMap(Enum<?>[] enums) {
        Map<Integer, String> map = new HashMap<>();
        for (Enum<?> e : enums) {
            if (e instanceof DataItemStatus) {
                map.put(((DataItemStatus) e).getValue(), ((DataItemStatus) e).getDescription());
            } else if (e instanceof Gender) {
                map.put(((Gender) e).getValue(), ((Gender) e).getDescription());
            }
        }
        return map;
    }
}
