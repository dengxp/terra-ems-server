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

package com.terra.ems.system.service;

import com.terra.ems.framework.enums.DataItemStatus;
import com.terra.ems.framework.enums.Gender;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Name: ConstantService
 * Email: dengxueping@gmail.com
 * Date: 2024-12-20
 * Description: 常量服务
 *
 * @author dengxueping
 */
@Service
public class ConstantService {

    public Map<String, Object> getAllOptions() {
        Map<String, Object> map = new HashMap<>();
        map.put("gender", Gender.getPreprocessedJsonStructure());
        map.put("status", DataItemStatus.getPreprocessedJsonStructure());
        return map;
    }

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
