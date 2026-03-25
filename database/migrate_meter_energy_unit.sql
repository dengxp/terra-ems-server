-- =============================================================================
-- 数据迁移脚本：回填 ems_meter.energy_unit_id
-- 
-- 背景：原模型中 MeterPoint ↔ EnergyUnit 是多对多关系（ems_energy_unit_point），
--       重构后改为 Meter → EnergyUnit 一对多关系（ems_meter.energy_unit_id）。
--       本脚本从旧中间表推导并回填。
--
-- 逻辑：通过 meter_point 的 meter_id 和 energy_unit_point 的 energy_unit_id，
--       找到每个 meter 对应的 energy_unit。如果一个 meter 关联了多个 energy_unit，
--       取关联最多的那个（或任取第一个）。
--
-- 日期：2026-03-23
-- =============================================================================

-- 查看迁移前状态（调试用）
-- SELECT m.id, m.code, m.name, m.energy_unit_id FROM ems_meter m;

-- 执行回填：从 ems_energy_unit_point + ems_meter_point 推导 meter → energy_unit
UPDATE ems_meter m
SET energy_unit_id = sub.energy_unit_id
FROM (
    SELECT DISTINCT ON (mp.meter_id) 
           mp.meter_id,
           eup.energy_unit_id
    FROM ems_energy_unit_point eup
    JOIN ems_meter_point mp ON mp.id = eup.meter_point_id
    WHERE mp.meter_id IS NOT NULL
    ORDER BY mp.meter_id, eup.energy_unit_id
) sub
WHERE m.id = sub.meter_id
  AND m.energy_unit_id IS NULL;

-- 查看迁移结果
-- SELECT m.id, m.code, m.name, m.energy_unit_id, eu.name as unit_name
-- FROM ems_meter m
-- LEFT JOIN ems_energy_unit eu ON eu.id = m.energy_unit_id;
