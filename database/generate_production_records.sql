-- Terra EMS 生产记录生成脚本
-- 生成 20 条模拟生产记录，基于现有用能单元编码
-- 执行方式：在 PostgreSQL 客户端执行此脚本

-- 清理之前的测试数据（可选）
-- DELETE FROM ems_production_record WHERE remark = '自动生成测试数据';

-- =====================================================
-- 2026年1月 产量记录 (data_type = '1')
-- =====================================================

-- 1. 精密加工车间 - 精密零件 (ID: CJ_JMJG)
INSERT INTO ems_production_record (energy_unit_id, record_date, product_name, quantity, unit, granularity, data_type, product_type, remark, created_at, updated_at)
SELECT id, '2026-01-05', '精密零件', 1250.0, '件', 'DAY', '1', '成品', '自动生成测试数据', NOW(), NOW() FROM ems_energy_unit WHERE code = 'CJ_JMJG';

INSERT INTO ems_production_record (energy_unit_id, record_date, product_name, quantity, unit, granularity, data_type, product_type, remark, created_at, updated_at)
SELECT id, '2026-01-06', '精密零件', 1320.5, '件', 'DAY', '1', '成品', '自动生成测试数据', NOW(), NOW() FROM ems_energy_unit WHERE code = 'CJ_JMJG';

INSERT INTO ems_production_record (energy_unit_id, record_date, product_name, quantity, unit, granularity, data_type, product_type, remark, created_at, updated_at)
SELECT id, '2026-01-07', '精密零件', 1180.0, '件', 'DAY', '1', '成品', '自动生成测试数据', NOW(), NOW() FROM ems_energy_unit WHERE code = 'CJ_JMJG';

INSERT INTO ems_production_record (energy_unit_id, record_date, product_name, quantity, unit, granularity, data_type, product_type, remark, created_at, updated_at)
SELECT id, '2026-01-08', '精密零件', 1405.2, '件', 'DAY', '1', '成品', '自动生成测试数据', NOW(), NOW() FROM ems_energy_unit WHERE code = 'CJ_JMJG';

-- 2. 组装车间 - 总装成品 (ID: CJ_ZP)
INSERT INTO ems_production_record (energy_unit_id, record_date, product_name, quantity, unit, granularity, data_type, product_type, remark, created_at, updated_at)
SELECT id, '2026-01-05', '总装成品', 450.0, '台', 'DAY', '1', '成品', '自动生成测试数据', NOW(), NOW() FROM ems_energy_unit WHERE code = 'CJ_ZP';

INSERT INTO ems_production_record (energy_unit_id, record_date, product_name, quantity, unit, granularity, data_type, product_type, remark, created_at, updated_at)
SELECT id, '2026-01-06', '总装成品', 485.0, '台', 'DAY', '1', '成品', '自动生成测试数据', NOW(), NOW() FROM ems_energy_unit WHERE code = 'CJ_ZP';

INSERT INTO ems_production_record (energy_unit_id, record_date, product_name, quantity, unit, granularity, data_type, product_type, remark, created_at, updated_at)
SELECT id, '2026-01-07', '总装成品', 420.0, '台', 'DAY', '1', '成品', '自动生成测试数据', NOW(), NOW() FROM ems_energy_unit WHERE code = 'CJ_ZP';

INSERT INTO ems_production_record (energy_unit_id, record_date, product_name, quantity, unit, granularity, data_type, product_type, remark, created_at, updated_at)
SELECT id, '2026-01-08', '总装成品', 510.0, '台', 'DAY', '1', '成品', '自动生成测试数据', NOW(), NOW() FROM ems_energy_unit WHERE code = 'CJ_ZP';

-- 3. 烘干车间 - 烘干件 (ID: CJ_HG)
INSERT INTO ems_production_record (energy_unit_id, record_date, product_name, quantity, unit, granularity, data_type, product_type, remark, created_at, updated_at)
SELECT id, '2026-01-05', '烘干件', 2100.0, 'kg', 'DAY', '1', '半成品', '自动生成测试数据', NOW(), NOW() FROM ems_energy_unit WHERE code = 'CJ_HG';

INSERT INTO ems_production_record (energy_unit_id, record_date, product_name, quantity, unit, granularity, data_type, product_type, remark, created_at, updated_at)
SELECT id, '2026-01-06', '烘干件', 2250.0, 'kg', 'DAY', '1', '半成品', '自动生成测试数据', NOW(), NOW() FROM ems_energy_unit WHERE code = 'CJ_HG';

INSERT INTO ems_production_record (energy_unit_id, record_date, product_name, quantity, unit, granularity, data_type, product_type, remark, created_at, updated_at)
SELECT id, '2026-01-07', '烘干件', 1980.0, 'kg', 'DAY', '1', '半成品', '自动生成测试数据', NOW(), NOW() FROM ems_energy_unit WHERE code = 'CJ_HG';

INSERT INTO ems_production_record (energy_unit_id, record_date, product_name, quantity, unit, granularity, data_type, product_type, remark, created_at, updated_at)
SELECT id, '2026-01-08', '烘干件', 2400.0, 'kg', 'DAY', '1', '半成品', '自动生成测试数据', NOW(), NOW() FROM ems_energy_unit WHERE code = 'CJ_HG';

-- 4. 精密加工车间 - 粗钢 (ID: CJ_JMJG) - 模拟另一种产品
INSERT INTO ems_production_record (energy_unit_id, record_date, product_name, quantity, unit, granularity, data_type, product_type, remark, created_at, updated_at)
SELECT id, '2026-01-09', '粗钢', 85.5, '吨', 'DAY', '1', '原材料', '自动生成测试数据', NOW(), NOW() FROM ems_energy_unit WHERE code = 'CJ_JMJG';

INSERT INTO ems_production_record (energy_unit_id, record_date, product_name, quantity, unit, granularity, data_type, product_type, remark, created_at, updated_at)
SELECT id, '2026-01-10', '粗钢', 92.0, '吨', 'DAY', '1', '原材料', '自动生成测试数据', NOW(), NOW() FROM ems_energy_unit WHERE code = 'CJ_JMJG';

INSERT INTO ems_production_record (energy_unit_id, record_date, product_name, quantity, unit, granularity, data_type, product_type, remark, created_at, updated_at)
SELECT id, '2026-01-11', '粗钢', 78.5, '吨', 'DAY', '1', '原材料', '自动生成测试数据', NOW(), NOW() FROM ems_energy_unit WHERE code = 'CJ_JMJG';

-- 5. 组装车间 - 水泥 (ID: CJ_ZP) - 模拟另一种产品
INSERT INTO ems_production_record (energy_unit_id, record_date, product_name, quantity, unit, granularity, data_type, product_type, remark, created_at, updated_at)
SELECT id, '2026-01-09', '水泥', 320.0, '吨', 'DAY', '1', '成品', '自动生成测试数据', NOW(), NOW() FROM ems_energy_unit WHERE code = 'CJ_ZP';

INSERT INTO ems_production_record (energy_unit_id, record_date, product_name, quantity, unit, granularity, data_type, product_type, remark, created_at, updated_at)
SELECT id, '2026-01-10', '水泥', 345.5, '吨', 'DAY', '1', '成品', '自动生成测试数据', NOW(), NOW() FROM ems_energy_unit WHERE code = 'CJ_ZP';

-- 6. 1号产线 - CNC加工件 (ID: JMJG_CX1)
INSERT INTO ems_production_record (energy_unit_id, record_date, product_name, quantity, unit, granularity, data_type, product_type, remark, created_at, updated_at)
SELECT id, '2026-01-05', 'CNC加工件', 850.0, '件', 'DAY', '1', '成品', '自动生成测试数据', NOW(), NOW() FROM ems_energy_unit WHERE code = 'JMJG_CX1';

INSERT INTO ems_production_record (energy_unit_id, record_date, product_name, quantity, unit, granularity, data_type, product_type, remark, created_at, updated_at)
SELECT id, '2026-01-06', 'CNC加工件', 910.0, '件', 'DAY', '1', '成品', '自动生成测试数据', NOW(), NOW() FROM ems_energy_unit WHERE code = 'JMJG_CX1';

INSERT INTO ems_production_record (energy_unit_id, record_date, product_name, quantity, unit, granularity, data_type, product_type, remark, created_at, updated_at)
SELECT id, '2026-01-07', 'CNC加工件', 880.0, '件', 'DAY', '1', '成品', '自动生成测试数据', NOW(), NOW() FROM ems_energy_unit WHERE code = 'JMJG_CX1';

-- 验证数据
-- SELECT * FROM ems_production_record WHERE remark = '自动生成测试数据' ORDER BY record_date;
