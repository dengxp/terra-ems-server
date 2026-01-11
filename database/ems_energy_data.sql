-- Terra EMS 能耗数据表
-- 存储采集点位的历史能耗数据

-- 能耗数据主表
CREATE TABLE IF NOT EXISTS ems_energy_data (
    id BIGSERIAL PRIMARY KEY,
    meter_point_id BIGINT NOT NULL,
    energy_type_id BIGINT NOT NULL,
    data_time TIMESTAMP NOT NULL,           -- 数据时间
    time_type VARCHAR(10) NOT NULL,         -- HOUR/DAY/MONTH/YEAR
    value DECIMAL(18, 4),                   -- 能耗值
    created_at TIMESTAMP DEFAULT NOW(),
    
    CONSTRAINT fk_energy_data_meter_point FOREIGN KEY (meter_point_id) 
        REFERENCES ems_meter_point(id),
    CONSTRAINT fk_energy_data_energy_type FOREIGN KEY (energy_type_id) 
        REFERENCES ems_energy_type(id)
);

-- 索引优化
CREATE INDEX IF NOT EXISTS idx_energy_data_point ON ems_energy_data(meter_point_id);
CREATE INDEX IF NOT EXISTS idx_energy_data_type ON ems_energy_data(energy_type_id);
CREATE INDEX IF NOT EXISTS idx_energy_data_time ON ems_energy_data(data_time, time_type);
CREATE INDEX IF NOT EXISTS idx_energy_data_composite ON ems_energy_data(meter_point_id, data_time, time_type);

-- 添加注释
COMMENT ON TABLE ems_energy_data IS '能耗数据表';
COMMENT ON COLUMN ems_energy_data.meter_point_id IS '采集点位ID';
COMMENT ON COLUMN ems_energy_data.energy_type_id IS '能源类型ID';
COMMENT ON COLUMN ems_energy_data.data_time IS '数据时间';
COMMENT ON COLUMN ems_energy_data.time_type IS '时间类型：HOUR-小时/DAY-日/MONTH-月/YEAR-年';
COMMENT ON COLUMN ems_energy_data.value IS '能耗值';
