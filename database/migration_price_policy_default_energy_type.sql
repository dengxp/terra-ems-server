-- 电价策略表结构变更
-- 为 energy_type_id 字段设置默认值（默认为电 ID=1）
-- Terra EMS v3 (PostgreSQL)

-- PostgreSQL 设置默认值
ALTER TABLE ems_price_policy 
ALTER COLUMN energy_type_id SET DEFAULT 1;

-- 更新现有记录中 energy_type_id 为空的数据
UPDATE ems_price_policy 
SET energy_type_id = 1 
WHERE energy_type_id IS NULL;
