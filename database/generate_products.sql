-- Terra EMS 产品基础数据生成脚本
-- 生成 10 条模拟产品数据
-- 执行方式：在 PostgreSQL 客户端执行此脚本

-- 清理错误数据 (防止主键或唯一约束冲突，如果是首次运行可忽略报错)
-- TRUNCATE TABLE ems_product CASCADE; 
-- 也可以使用 DELETE FROM ems_product WHERE code LIKE 'PROD_%';

DELETE FROM ems_product WHERE code LIKE 'PROD_%';

-- 1. 精密加工车间产品
-- FINISHED -> '1', SEMI_FINISHED -> '2', RAW_MATERIAL -> '3'

INSERT INTO ems_product (code, name, unit, type, sort_order, status, remark, created_at, updated_at)
VALUES 
('PROD_JM_001', '精密零件', '件', '1', 1, 0, '主要产品，用于汽车制造', NOW(), NOW()),
('PROD_JM_002', '传动轴', '件', '1', 2, 0, '高精度传动轴', NOW(), NOW()),
('PROD_JM_003', '粗钢板', '吨', '3', 3, 0, '原材料', NOW(), NOW());

-- 2. 组装车间产品
INSERT INTO ems_product (code, name, unit, type, sort_order, status, remark, created_at, updated_at)
VALUES 
('PROD_ZP_001', '发电机组', '台', '1', 4, 0, '大型柴油发电机组', NOW(), NOW()),
('PROD_ZP_002', '控制柜', '台', '1', 5, 0, '电气控制柜', NOW(), NOW()),
('PROD_ZP_003', '水泥', '吨', '3', 6, 0, '建筑原材料', NOW(), NOW());

-- 3. 烘干车间产品
INSERT INTO ems_product (code, name, unit, type, sort_order, status, remark, created_at, updated_at)
VALUES 
('PROD_HG_001', '烘干件', 'kg', '2', 7, 0, '经过脱水处理的半成品', NOW(), NOW()),
('PROD_HG_002', '陶瓷胚体', '件', '2', 8, 0, '未烧制的陶瓷半成品', NOW(), NOW());

-- 4. 通用产品
INSERT INTO ems_product (code, name, unit, type, sort_order, status, remark, created_at, updated_at)
VALUES 
('PROD_COMMON_001', 'CNC加工件', '件', '1', 9, 0, '通用数控加工零件', NOW(), NOW()),
('PROD_COMMON_002', '包装箱', '个', '3', 10, 0, '产品包装使用', NOW(), NOW());

-- 验证数据
-- SELECT * FROM ems_product;
