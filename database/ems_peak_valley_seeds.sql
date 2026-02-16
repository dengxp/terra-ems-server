-- 插入示例数据：为 meter_point_id=1, energy_unit_id=1 生成 2025-01-01 的分时数据

-- 【修复步骤】
-- 之前脚本误将 status 设置为 1 (禁用)，导致前端不显示“总进线柜”。
-- 这里先将第一个用能单元和采集点的状态改为 0 (启用)。
UPDATE ems_energy_unit SET status = 0 WHERE id = (SELECT id FROM ems_energy_unit ORDER BY id ASC LIMIT 1);
UPDATE ems_meter_point SET status = 0 WHERE id = (SELECT id FROM ems_meter_point ORDER BY id ASC LIMIT 1);

-- 【插入数据】
INSERT INTO ems_peak_valley_data (meter_point_id, energy_unit_id, data_time, time_type, period_type, electricity, price, cost, created_at)
SELECT
    m.id as meter_point_id,
    e.id as energy_unit_id,
    '2025-01-01' as data_time,
    'DAY' as time_type,
    p.period_type,
    p.electricity,
    p.price,
    p.cost,
    NOW() as created_at
FROM
    (SELECT id FROM ems_meter_point ORDER BY id ASC LIMIT 1) m,
    (SELECT id FROM ems_energy_unit ORDER BY id ASC LIMIT 1) e,
    (
        SELECT 'SHARP' as period_type, 10.50 as electricity, 1.50 as price, 15.75 as cost
        UNION ALL SELECT 'PEAK', 22.00, 1.20, 26.40
        UNION ALL SELECT 'FLAT', 35.00, 0.80, 28.00
        UNION ALL SELECT 'VALLEY', 18.00, 0.40, 7.20
    ) p;
