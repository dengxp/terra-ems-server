-- Terra EMS 初始数据 - 采集点位与用能单元关联
-- 表名: ems_energy_unit_point
-- 说明: 建立采集点位与用能单元的多对多关联关系
-- 执行前提: 需要先执行用能单元和采集点位初始化脚本

-- =====================================================
-- 关联数据说明
-- =====================================================
-- 此脚本将采集点位关联到用能单元
-- 实际应用中，一个用能单元可以关联多个采集点位
-- 一个采集点位也可以被多个用能单元共享

-- 清理现有关联数据
DELETE FROM ems_energy_unit_point;

-- =====================================================
-- 建立关联关系（根据实际用能单元编码）
-- =====================================================

-- 精密加工车间 (CJ_JMJG) - 电力相关点位
INSERT INTO ems_energy_unit_point (energy_unit_id, meter_point_id)
SELECT eu.id, mp.id
FROM ems_energy_unit eu
CROSS JOIN ems_meter_point mp
WHERE eu.code = 'CJ_JMJG'
  AND mp.code IN ('PT_EP_TOTAL', 'PT_EQ_TOTAL', 'PT_P_INST', 'PT_Q_INST', 'PT_PF');

-- 组装车间 (CJ_ZP) - 电力和电流电压相关点位
INSERT INTO ems_energy_unit_point (energy_unit_id, meter_point_id)
SELECT eu.id, mp.id
FROM ems_energy_unit eu
CROSS JOIN ems_meter_point mp
WHERE eu.code = 'CJ_ZP'
  AND mp.code IN ('PT_EP_TOTAL', 'PT_P_INST', 'PT_I_A', 'PT_I_B', 'PT_I_C', 'PT_U_A', 'PT_U_B', 'PT_U_C');

-- 高压配电室 (GY_PDS) - 所有电力点位
INSERT INTO ems_energy_unit_point (energy_unit_id, meter_point_id)
SELECT eu.id, mp.id
FROM ems_energy_unit eu
CROSS JOIN ems_meter_point mp
WHERE eu.code = 'GY_PDS'
  AND mp.code IN ('PT_EP_TOTAL', 'PT_EQ_TOTAL', 'PT_P_INST', 'PT_Q_INST', 'PT_I_A', 'PT_I_B', 'PT_I_C', 'PT_U_A', 'PT_U_B', 'PT_U_C', 'PT_PF');

-- 办公楼 (FZ_BGL) - 电力和水相关点位
INSERT INTO ems_energy_unit_point (energy_unit_id, meter_point_id)
SELECT eu.id, mp.id
FROM ems_energy_unit eu
CROSS JOIN ems_meter_point mp
WHERE eu.code = 'FZ_BGL'
  AND mp.code IN ('PT_EP_TOTAL', 'PT_P_INST', 'PT_WATER_TOTAL', 'PT_WATER_FLOW');

-- 食堂 (FZ_ST) - 电力、水和天然气点位
INSERT INTO ems_energy_unit_point (energy_unit_id, meter_point_id)
SELECT eu.id, mp.id
FROM ems_energy_unit eu
CROSS JOIN ems_meter_point mp
WHERE eu.code = 'FZ_ST'
  AND mp.code IN ('PT_EP_TOTAL', 'PT_WATER_TOTAL', 'PT_GAS_TOTAL', 'PT_GAS_FLOW');

-- 烘干车间 (CJ_HG) - 电力和蒸汽点位
INSERT INTO ems_energy_unit_point (energy_unit_id, meter_point_id)
SELECT eu.id, mp.id
FROM ems_energy_unit eu
CROSS JOIN ems_meter_point mp
WHERE eu.code = 'CJ_HG'
  AND mp.code IN ('PT_EP_TOTAL', 'PT_P_INST', 'PT_STEAM_TOTAL', 'PT_STEAM_FLOW', 'PT_STEAM_TEMP', 'PT_STEAM_PRESS');

-- 空压站 (GY_KYZ) - 电力点位
INSERT INTO ems_energy_unit_point (energy_unit_id, meter_point_id)
SELECT eu.id, mp.id
FROM ems_energy_unit eu
CROSS JOIN ems_meter_point mp
WHERE eu.code = 'GY_KYZ'
  AND mp.code IN ('PT_EP_TOTAL', 'PT_P_INST', 'PT_PF');

-- 1号产线 (JMJG_CX1) - 电力和能效点位
INSERT INTO ems_energy_unit_point (energy_unit_id, meter_point_id)
SELECT eu.id, mp.id
FROM ems_energy_unit eu
CROSS JOIN ems_meter_point mp
WHERE eu.code = 'JMJG_CX1'
  AND mp.code IN ('PT_EP_TOTAL', 'PT_P_INST', 'PT_UNIT_ENERGY');

-- 1号变压器 (PDS_1BY) - 电力点位
INSERT INTO ems_energy_unit_point (energy_unit_id, meter_point_id)
SELECT eu.id, mp.id
FROM ems_energy_unit eu
CROSS JOIN ems_meter_point mp
WHERE eu.code = 'PDS_1BY'
  AND mp.code IN ('PT_EP_TOTAL', 'PT_EQ_TOTAL', 'PT_P_INST', 'PT_Q_INST', 'PT_PF');

-- 验证数据
SELECT 
    eu.code AS unit_code, 
    eu.name AS unit_name, 
    mp.code AS point_code, 
    mp.name AS point_name
FROM ems_energy_unit_point eup
JOIN ems_energy_unit eu ON eu.id = eup.energy_unit_id
JOIN ems_meter_point mp ON mp.id = eup.meter_point_id
ORDER BY eu.code, mp.code;
