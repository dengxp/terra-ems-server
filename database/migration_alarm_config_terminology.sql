-- Terra EMS 数据库迁移 - 报警配置术语统一化
-- 将 node_id/node_type 改为 meter_point_id

-- 1. 删除 node_type 字段
ALTER TABLE ems_alarm_config DROP COLUMN IF EXISTS node_type;

-- 2. 将 node_id 重命名为 meter_point_id
ALTER TABLE ems_alarm_config RENAME COLUMN node_id TO meter_point_id;

-- 3. 删除旧索引
DROP INDEX IF EXISTS idx_alarm_config_node;

-- 4. 创建新索引
CREATE INDEX IF NOT EXISTS idx_alarm_config_meter_point ON ems_alarm_config(meter_point_id);

-- 5. 添加外键约束
ALTER TABLE ems_alarm_config 
  ADD CONSTRAINT fk_alarm_config_meter_point 
  FOREIGN KEY (meter_point_id) REFERENCES ems_meter_point(id);
