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

package com.terra.ems.ems.repository;

import com.terra.ems.ems.entity.AlarmConfig;
import com.terra.ems.framework.jpa.repository.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 预报警配置仓库
 *
 * @author dengxueping
 * @since 2026-01-11
 */

@Repository
public interface AlarmConfigRepository extends BaseRepository<AlarmConfig, Long> {

    /**
     * 根据采集点位查询配置
     *
     * @param meterPointId 采集点位ID
     * @return 配置列表
     */
    List<AlarmConfig> findByMeterPointId(Long meterPointId);

    /**
     * 根据采集点位和限值类型查询配置
     *
     * @param meterPointId 采集点位ID
     * @param limitTypeId  限值类型ID
     * @return 配置
     */
    AlarmConfig findByMeterPointIdAndAlarmLimitTypeId(Long meterPointId, Long limitTypeId);
}
