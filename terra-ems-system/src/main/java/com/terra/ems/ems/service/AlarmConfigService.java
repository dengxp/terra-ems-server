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

package com.terra.ems.ems.service;

import com.terra.ems.ems.entity.AlarmConfig;
import com.terra.ems.ems.repository.AlarmConfigRepository;
import com.terra.ems.framework.jpa.repository.BaseRepository;
import com.terra.ems.framework.service.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 预报警配置服务
 *
 * @author dengxueping
 * @since 2026-01-11
 */

@Service
@RequiredArgsConstructor
public class AlarmConfigService extends BaseService<AlarmConfig, Long> {

    private final AlarmConfigRepository alarmConfigRepository;

    @Override
    public BaseRepository<AlarmConfig, Long> getRepository() {
        return alarmConfigRepository;
    }

    /**
     * 根据采集点位查询配置
     *
     * @param meterPointId 采集点位ID
     * @return 配置列表
     */
    public List<AlarmConfig> findByMeterPoint(Long meterPointId) {
        return alarmConfigRepository.findByMeterPointId(meterPointId);
    }

    /**
     * 保存或更新配置，确保同一采集点位同一限值类型唯一
     *
     * @param alarmConfig 配置
     * @return 保存后的配置
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public AlarmConfig saveOrUpdate(AlarmConfig alarmConfig) {
        AlarmConfig existing = alarmConfigRepository.findByMeterPointIdAndAlarmLimitTypeId(
                alarmConfig.getMeterPoint().getId(), alarmConfig.getAlarmLimitType().getId());

        if (existing != null && (alarmConfig.getId() == null || !existing.getId().equals(alarmConfig.getId()))) {
            throw new IllegalArgumentException("该采集点位已存在相同的限值类型配置");
        }

        return super.saveOrUpdate(alarmConfig);
    }
}
