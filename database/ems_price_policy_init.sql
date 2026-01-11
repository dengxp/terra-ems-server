-- 电价策略初始化数据
-- Terra EMS v3

-- 清理现有数据
SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE ems_price_policy_item;
TRUNCATE TABLE ems_price_policy;
SET FOREIGN_KEY_CHECKS = 1;

-- ==================== 策略主表 ====================

-- 1. 工业分时电价策略 (2024~2025)
INSERT INTO ems_price_policy (code, name, energy_type_id, is_multi_rate, sort_order, status, effective_start_date, effective_end_date, remark, created_at, updated_at)
SELECT 'INDUSTRY_TOU_2024', '工业分时电价(2024)', id, TRUE, 1, 0, '2024-01-01', '2024-12-31', '适用于一般工商业用电，采用尖峰平谷电价', NOW(), NOW()
FROM ems_energy_type WHERE code = 'ELECTRIC';

-- 2. 工业分时电价策略 (2025开始)
INSERT INTO ems_price_policy (code, name, energy_type_id, is_multi_rate, sort_order, status, effective_start_date, effective_end_date, remark, created_at, updated_at)
SELECT 'INDUSTRY_TOU_2025', '工业分时电价(2025)', id, TRUE, 2, 0, '2025-01-01', NULL, '适用于一般工商业用电，2025年度执行标准', NOW(), NOW()
FROM ems_energy_type WHERE code = 'ELECTRIC';

-- 3. 商业单一电价策略
INSERT INTO ems_price_policy (code, name, energy_type_id, is_multi_rate, sort_order, status, effective_start_date, effective_end_date, remark, created_at, updated_at)
SELECT 'COMMERCIAL_SINGLE', '商业电价', id, FALSE, 3, 0, '2024-01-01', NULL, '适用于小型商业用电，统一计价', NOW(), NOW()
FROM ems_energy_type WHERE code = 'ELECTRIC';


-- ==================== 时段价格明细 ====================

-- 1. 工业分时电价(2024) 明细
INSERT INTO ems_price_policy_item (policy_id, period_type, price, sort_order, start_time, end_time, created_at, updated_at)
SELECT p.id, 'SHARP', 1.2500, 1, '09:00', '11:00', NOW(), NOW() FROM ems_price_policy p WHERE p.code = 'INDUSTRY_TOU_2024';
INSERT INTO ems_price_policy_item (policy_id, period_type, price, sort_order, start_time, end_time, created_at, updated_at)
SELECT p.id, 'PEAK', 0.9500, 2, '11:00', '13:00', NOW(), NOW() FROM ems_price_policy p WHERE p.code = 'INDUSTRY_TOU_2024';
INSERT INTO ems_price_policy_item (policy_id, period_type, price, sort_order, start_time, end_time, created_at, updated_at)
SELECT p.id, 'FLAT', 0.6500, 3, '13:00', '17:00', NOW(), NOW() FROM ems_price_policy p WHERE p.code = 'INDUSTRY_TOU_2024';
INSERT INTO ems_price_policy_item (policy_id, period_type, price, sort_order, start_time, end_time, created_at, updated_at)
SELECT p.id, 'VALLEY', 0.3500, 4, '17:00', '22:00', NOW(), NOW() FROM ems_price_policy p WHERE p.code = 'INDUSTRY_TOU_2024';
INSERT INTO ems_price_policy_item (policy_id, period_type, price, sort_order, start_time, end_time, created_at, updated_at)
SELECT p.id, 'DEEP', 0.2000, 5, '22:00', '09:00', NOW(), NOW() FROM ems_price_policy p WHERE p.code = 'INDUSTRY_TOU_2024';

-- 2. 工业分时电价(2025) 明细 (略微调高)
INSERT INTO ems_price_policy_item (policy_id, period_type, price, sort_order, start_time, end_time, created_at, updated_at)
SELECT p.id, 'SHARP', 1.3000, 1, '09:00', '11:00', NOW(), NOW() FROM ems_price_policy p WHERE p.code = 'INDUSTRY_TOU_2025';
INSERT INTO ems_price_policy_item (policy_id, period_type, price, sort_order, start_time, end_time, created_at, updated_at)
SELECT p.id, 'PEAK', 1.0000, 2, '11:00', '13:00', NOW(), NOW() FROM ems_price_policy p WHERE p.code = 'INDUSTRY_TOU_2025';
INSERT INTO ems_price_policy_item (policy_id, period_type, price, sort_order, start_time, end_time, created_at, updated_at)
SELECT p.id, 'FLAT', 0.7000, 3, '13:00', '17:00', NOW(), NOW() FROM ems_price_policy p WHERE p.code = 'INDUSTRY_TOU_2025';
INSERT INTO ems_price_policy_item (policy_id, period_type, price, sort_order, start_time, end_time, created_at, updated_at)
SELECT p.id, 'VALLEY', 0.4000, 4, '17:00', '22:00', NOW(), NOW() FROM ems_price_policy p WHERE p.code = 'INDUSTRY_TOU_2025';
INSERT INTO ems_price_policy_item (policy_id, period_type, price, sort_order, start_time, end_time, created_at, updated_at)
SELECT p.id, 'DEEP', 0.2500, 5, '22:00', '09:00', NOW(), NOW() FROM ems_price_policy p WHERE p.code = 'INDUSTRY_TOU_2025';

-- 3. 商业电价 明细 (单一电价)
INSERT INTO ems_price_policy_item (policy_id, period_type, price, sort_order, start_time, end_time, created_at, updated_at)
SELECT p.id, 'FLAT', 0.8200, 1, '00:00', '23:59', NOW(), NOW() FROM ems_price_policy p WHERE p.code = 'COMMERCIAL_SINGLE';
