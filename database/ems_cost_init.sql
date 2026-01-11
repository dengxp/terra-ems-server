-- Terra EMS 初始数据 - 成本管理
-- 表名: ems_cost_policy_binding, ems_energy_cost_record
-- 说明: 成本策略绑定和能源成本记录

-- RecordPeriodType: DAY(日), MONTH(月), YEAR(年)
-- DataItemStatus: 0 = ENABLE (启用), 1 = FORBIDDEN (禁用)

-- =====================================================
-- 创建成本策略绑定表
-- =====================================================
CREATE TABLE IF NOT EXISTS ems_cost_policy_binding (
    id BIGSERIAL PRIMARY KEY,
    energy_unit_id BIGINT NOT NULL,
    price_policy_id BIGINT NOT NULL,
    effective_start_date DATE NOT NULL,
    effective_end_date DATE,
    status INTEGER DEFAULT 0,
    remark VARCHAR(500),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_cpb_energy_unit ON ems_cost_policy_binding(energy_unit_id);
CREATE INDEX IF NOT EXISTS idx_cpb_price_policy ON ems_cost_policy_binding(price_policy_id);

-- =====================================================
-- 创建能源成本记录表
-- =====================================================
CREATE TABLE IF NOT EXISTS ems_energy_cost_record (
    id BIGSERIAL PRIMARY KEY,
    energy_unit_id BIGINT,
    energy_type_id BIGINT,
    record_date DATE NOT NULL,
    period_type VARCHAR(10) DEFAULT 'DAY',
    consumption NUMERIC(14, 4),
    cost NUMERIC(14, 2),
    sharp_consumption NUMERIC(14, 4),
    peak_consumption NUMERIC(14, 4),
    flat_consumption NUMERIC(14, 4),
    valley_consumption NUMERIC(14, 4),
    power_factor NUMERIC(5, 2),
    remark VARCHAR(500),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_ecr_energy_unit ON ems_energy_cost_record(energy_unit_id);
CREATE INDEX IF NOT EXISTS idx_ecr_record_date ON ems_energy_cost_record(record_date);
CREATE INDEX IF NOT EXISTS idx_ecr_period_type ON ems_energy_cost_record(period_type);

-- =====================================================
-- 成本策略绑定示例数据（假设用能单元ID=1, 电价策略ID=1已存在）
-- =====================================================
-- INSERT INTO ems_cost_policy_binding (energy_unit_id, price_policy_id, effective_start_date, effective_end_date, status, remark) VALUES
-- (1, 1, '2026-01-01', NULL, 0, '2026年度电价策略');

-- =====================================================
-- 能源成本记录示例数据
-- =====================================================
-- INSERT INTO ems_energy_cost_record (energy_unit_id, energy_type_id, record_date, period_type, consumption, cost, sharp_consumption, peak_consumption, flat_consumption, valley_consumption, power_factor, remark) VALUES
-- (1, 1, '2026-01-01', 'DAY', 1500.0000, 1200.00, 100.0000, 400.0000, 600.0000, 400.0000, 0.92, '日常用电记录');

-- 验证数据
-- SELECT * FROM ems_cost_policy_binding;
-- SELECT * FROM ems_energy_cost_record ORDER BY record_date DESC;
