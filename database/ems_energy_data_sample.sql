-- Terra EMS 能耗统计模拟数据
-- 生成于 2026-01-05
-- 使用实际数据库中的ID

-- =====================================================
-- 能源类型ID对照表（来自实际数据库）:
-- 3  = LNG (液态天然气)
-- 7  = STEAM (蒸汽)
-- 9  = WATER (自来水)
-- 14 = ELECTRIC (电力)
-- =====================================================

-- =====================================================
-- 清理旧的模拟数据（可选）
-- =====================================================
-- DELETE FROM ems_energy_data;

-- =====================================================
-- 2024年月度电力数据
-- meter_point_id 使用子查询，energy_type_id = 14 (ELECTRIC)
-- 模拟工厂用电，夏季用电高峰
-- =====================================================
INSERT INTO ems_energy_data (meter_point_id, energy_type_id, data_time, time_type, value)
SELECT 
    (SELECT id FROM ems_meter_point WHERE code = 'PT_EP_TOTAL' LIMIT 1),
    14, data_time, time_type, value
FROM (VALUES
    ('2024-01-01 00:00:00'::timestamp, 'MONTH', 45680.25),
    ('2024-02-01 00:00:00'::timestamp, 'MONTH', 42150.80),
    ('2024-03-01 00:00:00'::timestamp, 'MONTH', 48320.15),
    ('2024-04-01 00:00:00'::timestamp, 'MONTH', 51240.50),
    ('2024-05-01 00:00:00'::timestamp, 'MONTH', 58760.35),
    ('2024-06-01 00:00:00'::timestamp, 'MONTH', 72450.90),
    ('2024-07-01 00:00:00'::timestamp, 'MONTH', 85320.45),
    ('2024-08-01 00:00:00'::timestamp, 'MONTH', 82150.70),
    ('2024-09-01 00:00:00'::timestamp, 'MONTH', 68450.25),
    ('2024-10-01 00:00:00'::timestamp, 'MONTH', 54230.80),
    ('2024-11-01 00:00:00'::timestamp, 'MONTH', 49870.15),
    ('2024-12-01 00:00:00'::timestamp, 'MONTH', 52340.60)
) AS t(data_time, time_type, value);

-- =====================================================
-- 2025年月度电力数据
-- 比2024年有约8%的增长
-- =====================================================
INSERT INTO ems_energy_data (meter_point_id, energy_type_id, data_time, time_type, value)
SELECT 
    (SELECT id FROM ems_meter_point WHERE code = 'PT_EP_TOTAL' LIMIT 1),
    14, data_time, time_type, value
FROM (VALUES
    ('2025-01-01 00:00:00'::timestamp, 'MONTH', 49334.67),
    ('2025-02-01 00:00:00'::timestamp, 'MONTH', 45522.86),
    ('2025-03-01 00:00:00'::timestamp, 'MONTH', 52185.76),
    ('2025-04-01 00:00:00'::timestamp, 'MONTH', 55339.74),
    ('2025-05-01 00:00:00'::timestamp, 'MONTH', 63461.18),
    ('2025-06-01 00:00:00'::timestamp, 'MONTH', 78246.97),
    ('2025-07-01 00:00:00'::timestamp, 'MONTH', 92146.09),
    ('2025-08-01 00:00:00'::timestamp, 'MONTH', 88722.76),
    ('2025-09-01 00:00:00'::timestamp, 'MONTH', 73926.27),
    ('2025-10-01 00:00:00'::timestamp, 'MONTH', 58569.26),
    ('2025-11-01 00:00:00'::timestamp, 'MONTH', 53859.76),
    ('2025-12-01 00:00:00'::timestamp, 'MONTH', 56527.85)
) AS t(data_time, time_type, value);

-- =====================================================
-- 2025年12月每日电力数据
-- =====================================================
INSERT INTO ems_energy_data (meter_point_id, energy_type_id, data_time, time_type, value)
SELECT 
    (SELECT id FROM ems_meter_point WHERE code = 'PT_EP_TOTAL' LIMIT 1),
    14, data_time, time_type, value
FROM (VALUES
    ('2025-12-01 00:00:00'::timestamp, 'DAY', 1785.25),
    ('2025-12-02 00:00:00'::timestamp, 'DAY', 1823.40),
    ('2025-12-03 00:00:00'::timestamp, 'DAY', 1798.65),
    ('2025-12-04 00:00:00'::timestamp, 'DAY', 1856.30),
    ('2025-12-05 00:00:00'::timestamp, 'DAY', 1912.15),
    ('2025-12-06 00:00:00'::timestamp, 'DAY', 1245.80),
    ('2025-12-07 00:00:00'::timestamp, 'DAY', 1189.55),
    ('2025-12-08 00:00:00'::timestamp, 'DAY', 1802.45),
    ('2025-12-09 00:00:00'::timestamp, 'DAY', 1834.70),
    ('2025-12-10 00:00:00'::timestamp, 'DAY', 1867.95),
    ('2025-12-11 00:00:00'::timestamp, 'DAY', 1889.20),
    ('2025-12-12 00:00:00'::timestamp, 'DAY', 1923.45),
    ('2025-12-13 00:00:00'::timestamp, 'DAY', 1278.90),
    ('2025-12-14 00:00:00'::timestamp, 'DAY', 1156.35),
    ('2025-12-15 00:00:00'::timestamp, 'DAY', 1845.60),
    ('2025-12-16 00:00:00'::timestamp, 'DAY', 1878.85),
    ('2025-12-17 00:00:00'::timestamp, 'DAY', 1934.10),
    ('2025-12-18 00:00:00'::timestamp, 'DAY', 1956.35),
    ('2025-12-19 00:00:00'::timestamp, 'DAY', 1989.60),
    ('2025-12-20 00:00:00'::timestamp, 'DAY', 1312.85),
    ('2025-12-21 00:00:00'::timestamp, 'DAY', 1198.10),
    ('2025-12-22 00:00:00'::timestamp, 'DAY', 1867.35),
    ('2025-12-23 00:00:00'::timestamp, 'DAY', 1898.60),
    ('2025-12-24 00:00:00'::timestamp, 'DAY', 1756.85),
    ('2025-12-25 00:00:00'::timestamp, 'DAY', 1423.10),
    ('2025-12-26 00:00:00'::timestamp, 'DAY', 1834.35),
    ('2025-12-27 00:00:00'::timestamp, 'DAY', 1289.60),
    ('2025-12-28 00:00:00'::timestamp, 'DAY', 1178.85),
    ('2025-12-29 00:00:00'::timestamp, 'DAY', 1812.10),
    ('2025-12-30 00:00:00'::timestamp, 'DAY', 1845.35),
    ('2025-12-31 00:00:00'::timestamp, 'DAY', 1667.80)
) AS t(data_time, time_type, value);

-- =====================================================
-- 2026年1月每日电力数据
-- =====================================================
INSERT INTO ems_energy_data (meter_point_id, energy_type_id, data_time, time_type, value)
SELECT 
    (SELECT id FROM ems_meter_point WHERE code = 'PT_EP_TOTAL' LIMIT 1),
    14, data_time, time_type, value
FROM (VALUES
    ('2026-01-01 00:00:00'::timestamp, 'DAY', 1456.20),
    ('2026-01-02 00:00:00'::timestamp, 'DAY', 1789.45),
    ('2026-01-03 00:00:00'::timestamp, 'DAY', 1834.70),
    ('2026-01-04 00:00:00'::timestamp, 'DAY', 1267.95),
    ('2026-01-05 00:00:00'::timestamp, 'DAY', 1198.20)
) AS t(data_time, time_type, value);

-- =====================================================
-- 年度汇总数据 - 电力 (energy_type_id = 14)
-- =====================================================
INSERT INTO ems_energy_data (meter_point_id, energy_type_id, data_time, time_type, value)
SELECT 
    (SELECT id FROM ems_meter_point WHERE code = 'PT_EP_TOTAL' LIMIT 1),
    14, data_time, time_type, value
FROM (VALUES
    ('2022-01-01 00:00:00'::timestamp, 'YEAR', 612450.50),
    ('2023-01-01 00:00:00'::timestamp, 'YEAR', 658920.75),
    ('2024-01-01 00:00:00'::timestamp, 'YEAR', 710962.90),
    ('2025-01-01 00:00:00'::timestamp, 'YEAR', 767843.17)
) AS t(data_time, time_type, value);

-- =====================================================
-- 水用量数据 (energy_type_id = 9 = WATER)
-- =====================================================
INSERT INTO ems_energy_data (meter_point_id, energy_type_id, data_time, time_type, value)
SELECT 
    (SELECT id FROM ems_meter_point WHERE code = 'PT_WATER_TOTAL' LIMIT 1),
    9, data_time, time_type, value
FROM (VALUES
    ('2024-01-01 00:00:00'::timestamp, 'MONTH', 1256.80),
    ('2024-02-01 00:00:00'::timestamp, 'MONTH', 1178.45),
    ('2024-03-01 00:00:00'::timestamp, 'MONTH', 1342.20),
    ('2024-04-01 00:00:00'::timestamp, 'MONTH', 1456.95),
    ('2024-05-01 00:00:00'::timestamp, 'MONTH', 1623.70),
    ('2024-06-01 00:00:00'::timestamp, 'MONTH', 1845.45),
    ('2024-07-01 00:00:00'::timestamp, 'MONTH', 2134.20),
    ('2024-08-01 00:00:00'::timestamp, 'MONTH', 2078.95),
    ('2024-09-01 00:00:00'::timestamp, 'MONTH', 1789.70),
    ('2024-10-01 00:00:00'::timestamp, 'MONTH', 1534.45),
    ('2024-11-01 00:00:00'::timestamp, 'MONTH', 1345.20),
    ('2024-12-01 00:00:00'::timestamp, 'MONTH', 1289.95),
    ('2025-01-01 00:00:00'::timestamp, 'MONTH', 1319.64),
    ('2025-02-01 00:00:00'::timestamp, 'MONTH', 1237.37),
    ('2025-03-01 00:00:00'::timestamp, 'MONTH', 1409.31),
    ('2025-04-01 00:00:00'::timestamp, 'MONTH', 1529.80),
    ('2025-05-01 00:00:00'::timestamp, 'MONTH', 1704.89),
    ('2025-06-01 00:00:00'::timestamp, 'MONTH', 1937.72),
    ('2025-07-01 00:00:00'::timestamp, 'MONTH', 2240.91),
    ('2025-08-01 00:00:00'::timestamp, 'MONTH', 2182.90),
    ('2025-09-01 00:00:00'::timestamp, 'MONTH', 1879.19),
    ('2025-10-01 00:00:00'::timestamp, 'MONTH', 1611.17),
    ('2025-11-01 00:00:00'::timestamp, 'MONTH', 1412.46),
    ('2025-12-01 00:00:00'::timestamp, 'MONTH', 1354.45)
) AS t(data_time, time_type, value);

-- =====================================================
-- LNG液态天然气用量数据 (energy_type_id = 3)
-- 冬季用量高（采暖），夏季用量低
-- =====================================================
INSERT INTO ems_energy_data (meter_point_id, energy_type_id, data_time, time_type, value)
SELECT 
    (SELECT id FROM ems_meter_point WHERE code = 'PT_GAS_TOTAL' LIMIT 1),
    3, data_time, time_type, value
FROM (VALUES
    ('2024-01-01 00:00:00'::timestamp, 'MONTH', 8956.30),
    ('2024-02-01 00:00:00'::timestamp, 'MONTH', 8234.85),
    ('2024-03-01 00:00:00'::timestamp, 'MONTH', 5678.40),
    ('2024-04-01 00:00:00'::timestamp, 'MONTH', 2345.95),
    ('2024-05-01 00:00:00'::timestamp, 'MONTH', 1234.50),
    ('2024-06-01 00:00:00'::timestamp, 'MONTH', 856.05),
    ('2024-07-01 00:00:00'::timestamp, 'MONTH', 678.60),
    ('2024-08-01 00:00:00'::timestamp, 'MONTH', 712.15),
    ('2024-09-01 00:00:00'::timestamp, 'MONTH', 1456.70),
    ('2024-10-01 00:00:00'::timestamp, 'MONTH', 3567.25),
    ('2024-11-01 00:00:00'::timestamp, 'MONTH', 6789.80),
    ('2024-12-01 00:00:00'::timestamp, 'MONTH', 8123.35),
    ('2025-01-01 00:00:00'::timestamp, 'MONTH', 9404.12),
    ('2025-02-01 00:00:00'::timestamp, 'MONTH', 8646.59),
    ('2025-03-01 00:00:00'::timestamp, 'MONTH', 5962.32),
    ('2025-04-01 00:00:00'::timestamp, 'MONTH', 2463.25),
    ('2025-05-01 00:00:00'::timestamp, 'MONTH', 1296.23),
    ('2025-06-01 00:00:00'::timestamp, 'MONTH', 898.85),
    ('2025-07-01 00:00:00'::timestamp, 'MONTH', 712.53),
    ('2025-08-01 00:00:00'::timestamp, 'MONTH', 747.76),
    ('2025-09-01 00:00:00'::timestamp, 'MONTH', 1529.54),
    ('2025-10-01 00:00:00'::timestamp, 'MONTH', 3745.61),
    ('2025-11-01 00:00:00'::timestamp, 'MONTH', 7129.29),
    ('2025-12-01 00:00:00'::timestamp, 'MONTH', 8529.52)
) AS t(data_time, time_type, value);

-- =====================================================
-- 蒸汽用量数据 (energy_type_id = 7 = STEAM)
-- =====================================================
INSERT INTO ems_energy_data (meter_point_id, energy_type_id, data_time, time_type, value)
SELECT 
    (SELECT id FROM ems_meter_point WHERE code = 'PT_STEAM_TOTAL' LIMIT 1),
    7, data_time, time_type, value
FROM (VALUES
    ('2024-01-01 00:00:00'::timestamp, 'MONTH', 2456.30),
    ('2024-02-01 00:00:00'::timestamp, 'MONTH', 2234.85),
    ('2024-03-01 00:00:00'::timestamp, 'MONTH', 2078.40),
    ('2024-04-01 00:00:00'::timestamp, 'MONTH', 1845.95),
    ('2024-05-01 00:00:00'::timestamp, 'MONTH', 1634.50),
    ('2024-06-01 00:00:00'::timestamp, 'MONTH', 1456.05),
    ('2024-07-01 00:00:00'::timestamp, 'MONTH', 1278.60),
    ('2024-08-01 00:00:00'::timestamp, 'MONTH', 1312.15),
    ('2024-09-01 00:00:00'::timestamp, 'MONTH', 1556.70),
    ('2024-10-01 00:00:00'::timestamp, 'MONTH', 1867.25),
    ('2024-11-01 00:00:00'::timestamp, 'MONTH', 2189.80),
    ('2024-12-01 00:00:00'::timestamp, 'MONTH', 2423.35),
    ('2025-01-01 00:00:00'::timestamp, 'MONTH', 2579.12),
    ('2025-02-01 00:00:00'::timestamp, 'MONTH', 2346.59),
    ('2025-03-01 00:00:00'::timestamp, 'MONTH', 2182.32),
    ('2025-04-01 00:00:00'::timestamp, 'MONTH', 1938.25),
    ('2025-05-01 00:00:00'::timestamp, 'MONTH', 1716.23),
    ('2025-06-01 00:00:00'::timestamp, 'MONTH', 1528.85),
    ('2025-07-01 00:00:00'::timestamp, 'MONTH', 1342.53),
    ('2025-08-01 00:00:00'::timestamp, 'MONTH', 1377.76),
    ('2025-09-01 00:00:00'::timestamp, 'MONTH', 1634.54),
    ('2025-10-01 00:00:00'::timestamp, 'MONTH', 1960.61),
    ('2025-11-01 00:00:00'::timestamp, 'MONTH', 2299.29),
    ('2025-12-01 00:00:00'::timestamp, 'MONTH', 2544.52)
) AS t(data_time, time_type, value);

-- =====================================================
-- 验证插入的数据
-- =====================================================
SELECT 
    mp.name AS meter_point_name,
    et.name AS energy_type_name,
    COUNT(*) AS record_count,
    MIN(ed.data_time) AS min_date,
    MAX(ed.data_time) AS max_date
FROM ems_energy_data ed
JOIN ems_meter_point mp ON ed.meter_point_id = mp.id
JOIN ems_energy_type et ON ed.energy_type_id = et.id
GROUP BY mp.name, et.name
ORDER BY mp.name, et.name;
