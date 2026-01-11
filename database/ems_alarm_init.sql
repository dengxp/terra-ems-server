-- Terra EMS 初始数据 - 报警管理模块 (PostgreSQL)
-- 包含: 报警限值类型、预报警配置、报警历史记录
-- 执行前提: 需要先执行采集点位初始化脚本

-- =====================================================
-- 1. 报警限值类型 (ems_alarm_limit_type)
-- =====================================================
DELETE FROM ems_alarm_limit_type;

INSERT INTO ems_alarm_limit_type (limit_name, limit_code, color_number, comparator_operator, alarm_type, created_at, updated_at) VALUES
-- 预警类型 (WARNING)
('高值预警', 'HI_WARN', '#faad14', '>', 'WARNING', NOW(), NOW()),
('低值预警', 'LO_WARN', '#1890ff', '<', 'WARNING', NOW(), NOW()),
('偏高预警', 'HH_WARN', '#fa8c16', '>=', 'WARNING', NOW(), NOW()),
('偏低预警', 'LL_WARN', '#13c2c2', '<=', 'WARNING', NOW(), NOW()),

-- 报警类型 (ALARM)
('高高报警', 'HI_HI', '#ff4d4f', '>', 'ALARM', NOW(), NOW()),
('低低报警', 'LO_LO', '#f5222d', '<', 'ALARM', NOW(), NOW()),
('超限报警', 'OVER_LIMIT', '#eb2f96', '>=', 'ALARM', NOW(), NOW()),
('欠限报警', 'UNDER_LIMIT', '#722ed1', '<=', 'ALARM', NOW(), NOW());

-- =====================================================
-- 2. 预报警配置 (ems_alarm_config)
-- 关联采集点位 (node_type='POINT') 和报警限值类型
-- =====================================================
DELETE FROM ems_alarm_config;

-- 有功电量 - 高值预警和高高报警
INSERT INTO ems_alarm_config (node_type, node_id, limit_type_id, limit_value, is_enabled, remark, created_at, updated_at)
SELECT 'POINT', mp.id, alt.id, 1000, true, '日用电量超过1000kWh时预警', NOW(), NOW()
FROM ems_meter_point mp, ems_alarm_limit_type alt
WHERE mp.code = 'PT_EP_TOTAL' AND alt.limit_code = 'HI_WARN';

INSERT INTO ems_alarm_config (node_type, node_id, limit_type_id, limit_value, is_enabled, remark, created_at, updated_at)
SELECT 'POINT', mp.id, alt.id, 1500, true, '日用电量超过1500kWh时报警', NOW(), NOW()
FROM ems_meter_point mp, ems_alarm_limit_type alt
WHERE mp.code = 'PT_EP_TOTAL' AND alt.limit_code = 'HI_HI';

-- 瞬时功率 - 高值预警和高高报警
INSERT INTO ems_alarm_config (node_type, node_id, limit_type_id, limit_value, is_enabled, remark, created_at, updated_at)
SELECT 'POINT', mp.id, alt.id, 500, true, '瞬时功率超过500kW时预警', NOW(), NOW()
FROM ems_meter_point mp, ems_alarm_limit_type alt
WHERE mp.code = 'PT_P_INST' AND alt.limit_code = 'HI_WARN';

INSERT INTO ems_alarm_config (node_type, node_id, limit_type_id, limit_value, is_enabled, remark, created_at, updated_at)
SELECT 'POINT', mp.id, alt.id, 800, true, '瞬时功率超过800kW时报警', NOW(), NOW()
FROM ems_meter_point mp, ems_alarm_limit_type alt
WHERE mp.code = 'PT_P_INST' AND alt.limit_code = 'HI_HI';

-- 功率因数 - 低值预警和低低报警
INSERT INTO ems_alarm_config (node_type, node_id, limit_type_id, limit_value, is_enabled, remark, created_at, updated_at)
SELECT 'POINT', mp.id, alt.id, 0.9, true, '功率因数低于0.9时预警', NOW(), NOW()
FROM ems_meter_point mp, ems_alarm_limit_type alt
WHERE mp.code = 'PT_PF' AND alt.limit_code = 'LO_WARN';

INSERT INTO ems_alarm_config (node_type, node_id, limit_type_id, limit_value, is_enabled, remark, created_at, updated_at)
SELECT 'POINT', mp.id, alt.id, 0.85, true, '功率因数低于0.85时报警', NOW(), NOW()
FROM ems_meter_point mp, ems_alarm_limit_type alt
WHERE mp.code = 'PT_PF' AND alt.limit_code = 'LO_LO';

-- A相电流 - 超限报警
INSERT INTO ems_alarm_config (node_type, node_id, limit_type_id, limit_value, is_enabled, remark, created_at, updated_at)
SELECT 'POINT', mp.id, alt.id, 100, true, 'A相电流超过100A时报警', NOW(), NOW()
FROM ems_meter_point mp, ems_alarm_limit_type alt
WHERE mp.code = 'PT_I_A' AND alt.limit_code = 'OVER_LIMIT';

-- 用水量 - 高值预警
INSERT INTO ems_alarm_config (node_type, node_id, limit_type_id, limit_value, is_enabled, remark, created_at, updated_at)
SELECT 'POINT', mp.id, alt.id, 100, true, '日用水量超过100m³时预警', NOW(), NOW()
FROM ems_meter_point mp, ems_alarm_limit_type alt
WHERE mp.code = 'PT_WATER_TOTAL' AND alt.limit_code = 'HI_WARN';

-- 蒸汽压力 - 高值预警和低值预警
INSERT INTO ems_alarm_config (node_type, node_id, limit_type_id, limit_value, is_enabled, remark, created_at, updated_at)
SELECT 'POINT', mp.id, alt.id, 1.0, true, '蒸汽压力超过1.0MPa时预警', NOW(), NOW()
FROM ems_meter_point mp, ems_alarm_limit_type alt
WHERE mp.code = 'PT_STEAM_PRESS' AND alt.limit_code = 'HI_WARN';

INSERT INTO ems_alarm_config (node_type, node_id, limit_type_id, limit_value, is_enabled, remark, created_at, updated_at)
SELECT 'POINT', mp.id, alt.id, 0.3, true, '蒸汽压力低于0.3MPa时预警', NOW(), NOW()
FROM ems_meter_point mp, ems_alarm_limit_type alt
WHERE mp.code = 'PT_STEAM_PRESS' AND alt.limit_code = 'LO_WARN';

-- =====================================================
-- 3. 报警历史记录 (ems_alarm_record)
-- 字段: config_id, trigger_value, trigger_time, status, handle_time, handle_remark
-- status: 0=未处理, 1=已处理, 2=忽略
-- =====================================================
DELETE FROM ems_alarm_record;

-- 有功电量超限报警（已处理）
INSERT INTO ems_alarm_record (config_id, trigger_value, trigger_time, status, handle_time, handle_remark, created_at, updated_at)
SELECT ac.id, 1650, NOW() - INTERVAL '5 days', 1, NOW() - INTERVAL '5 days' + INTERVAL '30 minutes', '已通知生产部门降低负荷', NOW(), NOW()
FROM ems_alarm_config ac
JOIN ems_meter_point mp ON ac.node_id = mp.id
JOIN ems_alarm_limit_type alt ON ac.limit_type_id = alt.id
WHERE mp.code = 'PT_EP_TOTAL' AND alt.limit_code = 'HI_HI'
LIMIT 1;

-- 功率因数低报警（已处理）
INSERT INTO ems_alarm_record (config_id, trigger_value, trigger_time, status, handle_time, handle_remark, created_at, updated_at)
SELECT ac.id, 0.82, NOW() - INTERVAL '3 days', 1, NOW() - INTERVAL '3 days' + INTERVAL '2 hours', '已调整无功补偿装置', NOW(), NOW()
FROM ems_alarm_config ac
JOIN ems_meter_point mp ON ac.node_id = mp.id
JOIN ems_alarm_limit_type alt ON ac.limit_type_id = alt.id
WHERE mp.code = 'PT_PF' AND alt.limit_code = 'LO_LO'
LIMIT 1;

-- 瞬时功率高预警（已处理）
INSERT INTO ems_alarm_record (config_id, trigger_value, trigger_time, status, handle_time, handle_remark, created_at, updated_at)
SELECT ac.id, 520, NOW() - INTERVAL '2 days', 1, NOW() - INTERVAL '2 days' + INTERVAL '15 minutes', '生产高峰期正常波动，继续观察', NOW(), NOW()
FROM ems_alarm_config ac
JOIN ems_meter_point mp ON ac.node_id = mp.id
JOIN ems_alarm_limit_type alt ON ac.limit_type_id = alt.id
WHERE mp.code = 'PT_P_INST' AND alt.limit_code = 'HI_WARN'
LIMIT 1;

-- 电流超限报警（待处理）
INSERT INTO ems_alarm_record (config_id, trigger_value, trigger_time, status, created_at, updated_at)
SELECT ac.id, 115, NOW() - INTERVAL '1 day', 0, NOW(), NOW()
FROM ems_alarm_config ac
JOIN ems_meter_point mp ON ac.node_id = mp.id
JOIN ems_alarm_limit_type alt ON ac.limit_type_id = alt.id
WHERE mp.code = 'PT_I_A' AND alt.limit_code = 'OVER_LIMIT'
LIMIT 1;

-- 瞬时功率高高报警（待处理）
INSERT INTO ems_alarm_record (config_id, trigger_value, trigger_time, status, created_at, updated_at)
SELECT ac.id, 850, NOW() - INTERVAL '6 hours', 0, NOW(), NOW()
FROM ems_alarm_config ac
JOIN ems_meter_point mp ON ac.node_id = mp.id
JOIN ems_alarm_limit_type alt ON ac.limit_type_id = alt.id
WHERE mp.code = 'PT_P_INST' AND alt.limit_code = 'HI_HI'
LIMIT 1;

-- 用水量高预警（待处理）
INSERT INTO ems_alarm_record (config_id, trigger_value, trigger_time, status, created_at, updated_at)
SELECT ac.id, 125, NOW() - INTERVAL '2 hours', 0, NOW(), NOW()
FROM ems_alarm_config ac
JOIN ems_meter_point mp ON ac.node_id = mp.id
JOIN ems_alarm_limit_type alt ON ac.limit_type_id = alt.id
WHERE mp.code = 'PT_WATER_TOTAL' AND alt.limit_code = 'HI_WARN'
LIMIT 1;

-- 蒸汽压力低预警（已忽略）
INSERT INTO ems_alarm_record (config_id, trigger_value, trigger_time, status, handle_time, handle_remark, created_at, updated_at)
SELECT ac.id, 0.25, NOW() - INTERVAL '4 days', 2, NOW() - INTERVAL '4 days' + INTERVAL '10 minutes', '设备检修期间，正常现象', NOW(), NOW()
FROM ems_alarm_config ac
JOIN ems_meter_point mp ON ac.node_id = mp.id
JOIN ems_alarm_limit_type alt ON ac.limit_type_id = alt.id
WHERE mp.code = 'PT_STEAM_PRESS' AND alt.limit_code = 'LO_WARN'
LIMIT 1;

-- =====================================================
-- 验证数据
-- =====================================================
SELECT '报警限值类型' AS table_name, COUNT(*) AS count FROM ems_alarm_limit_type
UNION ALL
SELECT '预报警配置', COUNT(*) FROM ems_alarm_config
UNION ALL
SELECT '报警历史记录', COUNT(*) FROM ems_alarm_record;
