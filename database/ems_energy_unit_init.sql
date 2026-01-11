-- Terra EMS 初始数据 - 用能单元
-- 表名: ems_energy_unit
-- 说明: 用于构建企业能源消耗的层级结构（总厂 → 车间 → 产线 → 设备）
-- 执行方式: 按顺序执行，确保父节点先于子节点插入

-- 状态值说明:
-- 0 = ENABLE (启用)
-- 1 = FORBIDDEN (禁用)

-- 清空现有数据（可选，生产环境慎用）
-- DELETE FROM ems_energy_unit;

-- =====================================================
-- 第一层：根节点（总厂）
-- =====================================================
INSERT INTO ems_energy_unit (code, name, parent_id, level, sort_order, status, remark, created_at, updated_at) VALUES
('ZC', '泰若总厂', NULL, 0, 1, 0, 'Terra EMS 演示总厂', NOW(), NOW());

-- =====================================================
-- 第二层：一级用能单元（车间/区域）
-- =====================================================
INSERT INTO ems_energy_unit (code, name, parent_id, level, sort_order, status, remark, created_at, updated_at)
SELECT 'CJ_JMJG', '精密加工车间', id, 1, 1, 0, '数控加工设备集中区域', NOW(), NOW() FROM ems_energy_unit WHERE code = 'ZC';

INSERT INTO ems_energy_unit (code, name, parent_id, level, sort_order, status, remark, created_at, updated_at)
SELECT 'CJ_ZP', '组装车间', id, 1, 2, 0, '产品组装生产线', NOW(), NOW() FROM ems_energy_unit WHERE code = 'ZC';

INSERT INTO ems_energy_unit (code, name, parent_id, level, sort_order, status, remark, created_at, updated_at)
SELECT 'CJ_ZLJC', '质量监测车间', id, 1, 3, 0, '质检与测试区域', NOW(), NOW() FROM ems_energy_unit WHERE code = 'ZC';

INSERT INTO ems_energy_unit (code, name, parent_id, level, sort_order, status, remark, created_at, updated_at)
SELECT 'CJ_HG', '烘干车间', id, 1, 4, 0, '烘干固化工艺区', NOW(), NOW() FROM ems_energy_unit WHERE code = 'ZC';

INSERT INTO ems_energy_unit (code, name, parent_id, level, sort_order, status, remark, created_at, updated_at)
SELECT 'GY_PDS', '高压配电室', id, 1, 5, 0, '35kV/10kV变电站', NOW(), NOW() FROM ems_energy_unit WHERE code = 'ZC';

INSERT INTO ems_energy_unit (code, name, parent_id, level, sort_order, status, remark, created_at, updated_at)
SELECT 'GY_KYZ', '空压站', id, 1, 6, 0, '压缩空气集中供应站', NOW(), NOW() FROM ems_energy_unit WHERE code = 'ZC';

INSERT INTO ems_energy_unit (code, name, parent_id, level, sort_order, status, remark, created_at, updated_at)
SELECT 'FZ_BGL', '办公楼', id, 1, 7, 0, '行政办公区域', NOW(), NOW() FROM ems_energy_unit WHERE code = 'ZC';

INSERT INTO ems_energy_unit (code, name, parent_id, level, sort_order, status, remark, created_at, updated_at)
SELECT 'FZ_ST', '食堂', id, 1, 8, 0, '员工餐厅', NOW(), NOW() FROM ems_energy_unit WHERE code = 'ZC';

INSERT INTO ems_energy_unit (code, name, parent_id, level, sort_order, status, remark, created_at, updated_at)
SELECT 'FZ_SS', '宿舍楼', id, 1, 9, 0, '员工住宿区', NOW(), NOW() FROM ems_energy_unit WHERE code = 'ZC';

-- =====================================================
-- 第三层：二级用能单元（产线/设备组）
-- =====================================================
-- 精密加工车间下属
INSERT INTO ems_energy_unit (code, name, parent_id, level, sort_order, status, remark, created_at, updated_at)
SELECT 'JMJG_CX1', '1号产线', id, 2, 1, 0, 'CNC加工中心产线', NOW(), NOW() FROM ems_energy_unit WHERE code = 'CJ_JMJG';

INSERT INTO ems_energy_unit (code, name, parent_id, level, sort_order, status, remark, created_at, updated_at)
SELECT 'JMJG_CX2', '2号产线', id, 2, 2, 0, '精密磨削产线', NOW(), NOW() FROM ems_energy_unit WHERE code = 'CJ_JMJG';

INSERT INTO ems_energy_unit (code, name, parent_id, level, sort_order, status, remark, created_at, updated_at)
SELECT 'JMJG_CX3', '3号产线', id, 2, 3, 0, '激光切割产线', NOW(), NOW() FROM ems_energy_unit WHERE code = 'CJ_JMJG';

INSERT INTO ems_energy_unit (code, name, parent_id, level, sort_order, status, remark, created_at, updated_at)
SELECT 'JMJG_FZ', '辅助设施', id, 2, 4, 0, '冷却系统、照明等', NOW(), NOW() FROM ems_energy_unit WHERE code = 'CJ_JMJG';

-- 组装车间下属
INSERT INTO ems_energy_unit (code, name, parent_id, level, sort_order, status, remark, created_at, updated_at)
SELECT 'ZP_ZZ1', '总装线1', id, 2, 1, 0, '主要产品组装', NOW(), NOW() FROM ems_energy_unit WHERE code = 'CJ_ZP';

INSERT INTO ems_energy_unit (code, name, parent_id, level, sort_order, status, remark, created_at, updated_at)
SELECT 'ZP_ZZ2', '总装线2', id, 2, 2, 0, '备用组装线', NOW(), NOW() FROM ems_energy_unit WHERE code = 'CJ_ZP';

INSERT INTO ems_energy_unit (code, name, parent_id, level, sort_order, status, remark, created_at, updated_at)
SELECT 'ZP_CS', '测试工位', id, 2, 3, 0, '产品功能测试', NOW(), NOW() FROM ems_energy_unit WHERE code = 'CJ_ZP';

INSERT INTO ems_energy_unit (code, name, parent_id, level, sort_order, status, remark, created_at, updated_at)
SELECT 'ZP_BZ', '包装区', id, 2, 4, 0, '产品包装', NOW(), NOW() FROM ems_energy_unit WHERE code = 'CJ_ZP';

-- 高压配电室下属
INSERT INTO ems_energy_unit (code, name, parent_id, level, sort_order, status, remark, created_at, updated_at)
SELECT 'PDS_1BY', '1号变压器', id, 2, 1, 0, '1000kVA变压器', NOW(), NOW() FROM ems_energy_unit WHERE code = 'GY_PDS';

INSERT INTO ems_energy_unit (code, name, parent_id, level, sort_order, status, remark, created_at, updated_at)
SELECT 'PDS_2BY', '2号变压器', id, 2, 2, 0, '800kVA变压器', NOW(), NOW() FROM ems_energy_unit WHERE code = 'GY_PDS';

INSERT INTO ems_energy_unit (code, name, parent_id, level, sort_order, status, remark, created_at, updated_at)
SELECT 'PDS_DPBG', '低压配电柜', id, 2, 3, 0, '400V配电系统', NOW(), NOW() FROM ems_energy_unit WHERE code = 'GY_PDS';

-- 空压站下属
INSERT INTO ems_energy_unit (code, name, parent_id, level, sort_order, status, remark, created_at, updated_at)
SELECT 'KYZ_1KYJ', '1号空压机', id, 2, 1, 0, '螺杆式132kW', NOW(), NOW() FROM ems_energy_unit WHERE code = 'GY_KYZ';

INSERT INTO ems_energy_unit (code, name, parent_id, level, sort_order, status, remark, created_at, updated_at)
SELECT 'KYZ_2KYJ', '2号空压机', id, 2, 2, 0, '螺杆式90kW', NOW(), NOW() FROM ems_energy_unit WHERE code = 'GY_KYZ';

INSERT INTO ems_energy_unit (code, name, parent_id, level, sort_order, status, remark, created_at, updated_at)
SELECT 'KYZ_GZJ', '干燥机', id, 2, 3, 0, '冷干机组', NOW(), NOW() FROM ems_energy_unit WHERE code = 'GY_KYZ';

-- 办公楼下属
INSERT INTO ems_energy_unit (code, name, parent_id, level, sort_order, status, remark, created_at, updated_at)
SELECT 'BGL_1F', '1楼大厅', id, 2, 1, 0, '前台接待区', NOW(), NOW() FROM ems_energy_unit WHERE code = 'FZ_BGL';

INSERT INTO ems_energy_unit (code, name, parent_id, level, sort_order, status, remark, created_at, updated_at)
SELECT 'BGL_2F', '2楼办公区', id, 2, 2, 0, '行政办公', NOW(), NOW() FROM ems_energy_unit WHERE code = 'FZ_BGL';

INSERT INTO ems_energy_unit (code, name, parent_id, level, sort_order, status, remark, created_at, updated_at)
SELECT 'BGL_3F', '3楼会议室', id, 2, 3, 0, '多功能会议室', NOW(), NOW() FROM ems_energy_unit WHERE code = 'FZ_BGL';

INSERT INTO ems_energy_unit (code, name, parent_id, level, sort_order, status, remark, created_at, updated_at)
SELECT 'BGL_JF', '机房', id, 2, 4, 0, 'IT数据中心', NOW(), NOW() FROM ems_energy_unit WHERE code = 'FZ_BGL';

-- =====================================================
-- 第四层：三级用能单元（具体设备/区域）
-- =====================================================
-- 1号产线下属
INSERT INTO ems_energy_unit (code, name, parent_id, level, sort_order, status, remark, created_at, updated_at)
SELECT 'CX1_CNC01', 'CNC-01', id, 3, 1, 0, '五轴加工中心', NOW(), NOW() FROM ems_energy_unit WHERE code = 'JMJG_CX1';

INSERT INTO ems_energy_unit (code, name, parent_id, level, sort_order, status, remark, created_at, updated_at)
SELECT 'CX1_CNC02', 'CNC-02', id, 3, 2, 0, '四轴加工中心', NOW(), NOW() FROM ems_energy_unit WHERE code = 'JMJG_CX1';

INSERT INTO ems_energy_unit (code, name, parent_id, level, sort_order, status, remark, created_at, updated_at)
SELECT 'CX1_CNC03', 'CNC-03', id, 3, 3, 0, '三轴加工中心', NOW(), NOW() FROM ems_energy_unit WHERE code = 'JMJG_CX1';

-- 1号变压器下属
INSERT INTO ems_energy_unit (code, name, parent_id, level, sort_order, status, remark, created_at, updated_at)
SELECT '1BY_JLL', '进线柜', id, 3, 1, 0, '高压进线', NOW(), NOW() FROM ems_energy_unit WHERE code = 'PDS_1BY';

INSERT INTO ems_energy_unit (code, name, parent_id, level, sort_order, status, remark, created_at, updated_at)
SELECT '1BY_CLL', '出线柜', id, 3, 2, 0, '低压出线', NOW(), NOW() FROM ems_energy_unit WHERE code = 'PDS_1BY';

-- =====================================================
-- 停用节点示例
-- =====================================================
INSERT INTO ems_energy_unit (code, name, parent_id, level, sort_order, status, remark, created_at, updated_at)
SELECT 'CJ_OLD', '旧车间(已停用)', id, 1, 99, 1, '2023年12月停产', NOW(), NOW() FROM ems_energy_unit WHERE code = 'ZC';

-- 验证数据
-- SELECT * FROM ems_energy_unit ORDER BY level, sort_order;
