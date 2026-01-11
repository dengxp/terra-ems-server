-- Terra EMS 尖峰平谷模块数据库表
-- 分时电价配置和分时用电数据

-- =====================================================
-- 分时电价配置表
-- =====================================================
CREATE TABLE IF NOT EXISTS ems_time_period_price (
    id BIGSERIAL PRIMARY KEY,
    price_policy_id BIGINT,                  -- 关联电价政策（可选）
    period_type VARCHAR(20) NOT NULL,        -- SHARP/PEAK/FLAT/VALLEY/DEEP_VALLEY
    period_name VARCHAR(50) NOT NULL,        -- 尖峰/高峰/平段/低谷/深谷
    start_time TIME NOT NULL,                -- 开始时间 HH:mm:ss
    end_time TIME NOT NULL,                  -- 结束时间 HH:mm:ss
    price DECIMAL(10, 4) NOT NULL,           -- 电价 (元/kWh)
    sort_order INTEGER DEFAULT 0,
    status INTEGER DEFAULT 0,                -- 0=启用, 1=禁用
    remark VARCHAR(500),
    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP DEFAULT NOW(),
    
    CONSTRAINT fk_tpp_price_policy FOREIGN KEY (price_policy_id) 
        REFERENCES ems_price_policy(id) ON DELETE SET NULL
);

-- 索引
CREATE INDEX IF NOT EXISTS idx_tpp_policy ON ems_time_period_price(price_policy_id);
CREATE INDEX IF NOT EXISTS idx_tpp_type ON ems_time_period_price(period_type);

-- 注释
COMMENT ON TABLE ems_time_period_price IS '分时电价配置表';
COMMENT ON COLUMN ems_time_period_price.period_type IS '时段类型：SHARP-尖峰/PEAK-高峰/FLAT-平段/VALLEY-低谷/DEEP_VALLEY-深谷';
COMMENT ON COLUMN ems_time_period_price.start_time IS '时段开始时间';
COMMENT ON COLUMN ems_time_period_price.end_time IS '时段结束时间';
COMMENT ON COLUMN ems_time_period_price.price IS '电价(元/kWh)';

-- =====================================================
-- 分时用电数据表
-- =====================================================
CREATE TABLE IF NOT EXISTS ems_peak_valley_data (
    id BIGSERIAL PRIMARY KEY,
    meter_point_id BIGINT NOT NULL,
    energy_unit_id BIGINT,
    data_time DATE NOT NULL,                 -- 数据日期
    time_type VARCHAR(10) NOT NULL,          -- HOUR/DAY/MONTH
    period_type VARCHAR(20) NOT NULL,        -- SHARP/PEAK/FLAT/VALLEY/DEEP_VALLEY
    electricity DECIMAL(18, 4),              -- 用电量 kWh
    price DECIMAL(10, 4),                    -- 单价
    cost DECIMAL(18, 4),                     -- 电费
    created_at TIMESTAMP DEFAULT NOW(),
    
    CONSTRAINT fk_pvd_meter_point FOREIGN KEY (meter_point_id) 
        REFERENCES ems_meter_point(id),
    CONSTRAINT fk_pvd_energy_unit FOREIGN KEY (energy_unit_id) 
        REFERENCES ems_energy_unit(id) ON DELETE SET NULL
);

-- 索引
CREATE INDEX IF NOT EXISTS idx_pvd_meter_point ON ems_peak_valley_data(meter_point_id);
CREATE INDEX IF NOT EXISTS idx_pvd_energy_unit ON ems_peak_valley_data(energy_unit_id);
CREATE INDEX IF NOT EXISTS idx_pvd_data_time ON ems_peak_valley_data(data_time, time_type);
CREATE INDEX IF NOT EXISTS idx_pvd_period ON ems_peak_valley_data(period_type);

-- 注释
COMMENT ON TABLE ems_peak_valley_data IS '分时用电数据表';
COMMENT ON COLUMN ems_peak_valley_data.time_type IS '时间类型：HOUR-小时/DAY-日/MONTH-月';
COMMENT ON COLUMN ems_peak_valley_data.period_type IS '时段类型：SHARP-尖峰/PEAK-高峰/FLAT-平段/VALLEY-低谷/DEEP_VALLEY-深谷';
COMMENT ON COLUMN ems_peak_valley_data.electricity IS '用电量(kWh)';
COMMENT ON COLUMN ems_peak_valley_data.cost IS '电费(元)';
