-- Terra EMS 初始数据 - 采集点位
-- 表名: ems_meter_point
-- 说明: 定义计量器具的具体采集指标
-- 执行前提: 需要先执行能源类型和计量器具初始化脚本

-- 状态值说明:
-- 0 = ENABLE (启用)
-- 1 = FORBIDDEN (禁用)

-- 点位类型:
-- COLLECT = 采集类（直接从仪表采集）
-- CALC = 计算类（由其他点位计算得出）

-- 分类:
-- ENERGY = 能源类
-- PRODUCT = 产品类
-- EFFICIENCY = 能效类
-- OPERATION = 经营类
-- OTHER = 其他

-- =====================================================
-- 采集点位示例数据
-- =====================================================

-- 电力类采集点位（关联电表和电力能源类型）
INSERT INTO ems_meter_point (code, name, point_type, category, unit, initial_value, sort_order, status, remark, created_at, updated_at) VALUES
('PT_EP_TOTAL', '有功电量', 'COLLECT', 'ENERGY', 'kWh', 0, 1, 0, '正向有功总电量', NOW(), NOW()),
('PT_EQ_TOTAL', '无功电量', 'COLLECT', 'ENERGY', 'kvarh', 0, 2, 0, '正向无功总电量', NOW(), NOW()),
('PT_P_INST', '瞬时有功功率', 'COLLECT', 'ENERGY', 'kW', NULL, 3, 0, '瞬时有功功率', NOW(), NOW()),
('PT_Q_INST', '瞬时无功功率', 'COLLECT', 'ENERGY', 'kvar', NULL, 4, 0, '瞬时无功功率', NOW(), NOW()),
('PT_I_A', 'A相电流', 'COLLECT', 'ENERGY', 'A', NULL, 5, 0, NULL, NOW(), NOW()),
('PT_I_B', 'B相电流', 'COLLECT', 'ENERGY', 'A', NULL, 6, 0, NULL, NOW(), NOW()),
('PT_I_C', 'C相电流', 'COLLECT', 'ENERGY', 'A', NULL, 7, 0, NULL, NOW(), NOW()),
('PT_U_A', 'A相电压', 'COLLECT', 'ENERGY', 'V', NULL, 8, 0, NULL, NOW(), NOW()),
('PT_U_B', 'B相电压', 'COLLECT', 'ENERGY', 'V', NULL, 9, 0, NULL, NOW(), NOW()),
('PT_U_C', 'C相电压', 'COLLECT', 'ENERGY', 'V', NULL, 10, 0, NULL, NOW(), NOW()),
('PT_PF', '功率因数', 'COLLECT', 'EFFICIENCY', '', NULL, 11, 0, '综合功率因数', NOW(), NOW());

-- 水类采集点位
INSERT INTO ems_meter_point (code, name, point_type, category, unit, initial_value, sort_order, status, remark, created_at, updated_at) VALUES
('PT_WATER_TOTAL', '用水量', 'COLLECT', 'ENERGY', 'm³', 0, 1, 0, '累计用水量', NOW(), NOW()),
('PT_WATER_FLOW', '瞬时流量', 'COLLECT', 'ENERGY', 'm³/h', NULL, 2, 0, '瞬时水流量', NOW(), NOW());

-- 天然气类采集点位
INSERT INTO ems_meter_point (code, name, point_type, category, unit, initial_value, sort_order, status, remark, created_at, updated_at) VALUES
('PT_GAS_TOTAL', '用气量', 'COLLECT', 'ENERGY', 'm³', 0, 1, 0, '累计用气量', NOW(), NOW()),
('PT_GAS_FLOW', '瞬时流量', 'COLLECT', 'ENERGY', 'm³/h', NULL, 2, 0, '瞬时气流量', NOW(), NOW()),
('PT_GAS_TEMP', '气体温度', 'COLLECT', 'OTHER', '℃', NULL, 3, 0, '温度补偿用', NOW(), NOW()),
('PT_GAS_PRESS', '气体压力', 'COLLECT', 'OTHER', 'kPa', NULL, 4, 0, '压力补偿用', NOW(), NOW());

-- 蒸汽类采集点位
INSERT INTO ems_meter_point (code, name, point_type, category, unit, initial_value, sort_order, status, remark, created_at, updated_at) VALUES
('PT_STEAM_TOTAL', '蒸汽用量', 'COLLECT', 'ENERGY', 't', 0, 1, 0, '累计蒸汽用量', NOW(), NOW()),
('PT_STEAM_FLOW', '瞬时流量', 'COLLECT', 'ENERGY', 't/h', NULL, 2, 0, '瞬时蒸汽流量', NOW(), NOW()),
('PT_STEAM_TEMP', '蒸汽温度', 'COLLECT', 'OTHER', '℃', NULL, 3, 0, NULL, NOW(), NOW()),
('PT_STEAM_PRESS', '蒸汽压力', 'COLLECT', 'OTHER', 'MPa', NULL, 4, 0, NULL, NOW(), NOW());

-- 计算类点位示例
INSERT INTO ems_meter_point (code, name, point_type, category, unit, initial_value, sort_order, status, remark, created_at, updated_at) VALUES
('PT_ENERGY_COST', '能源成本', 'CALC', 'OPERATION', '元', NULL, 1, 0, '根据用量和单价计算', NOW(), NOW()),
('PT_UNIT_ENERGY', '单位产品能耗', 'CALC', 'EFFICIENCY', 'kWh/件', NULL, 2, 0, '能耗/产量', NOW(), NOW()),
('PT_CARBON_EMIT', '碳排放量', 'CALC', 'OTHER', 'tCO2', NULL, 3, 0, '根据能耗计算碳排放', NOW(), NOW());

-- 验证数据
-- SELECT * FROM ems_meter_point ORDER BY category, sort_order;
