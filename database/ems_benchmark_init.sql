-- Terra EMS 初始数据 - 对标管理
-- 表名: ems_benchmark
-- 说明: 能效对标值管理

-- BenchmarkType: NATIONAL(国家标准), INDUSTRY(行业标准), ENTERPRISE(企业标准), REGIONAL(区域标准)
-- DataItemStatus: 0 = ENABLE (启用), 1 = FORBIDDEN (禁用)

-- =====================================================
-- 创建对标值表（如果不存在）
-- =====================================================
CREATE TABLE IF NOT EXISTS ems_benchmark (
    id BIGSERIAL PRIMARY KEY,
    code VARCHAR(50) NOT NULL UNIQUE,
    name VARCHAR(100) NOT NULL,
    type VARCHAR(20) DEFAULT 'NATIONAL',
    grade VARCHAR(20),
    value NUMERIC(12, 4),
    unit VARCHAR(20),
    national_num VARCHAR(50),
    energy_type_id BIGINT,
    status INTEGER DEFAULT 0,
    remark VARCHAR(500),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_benchmark_code ON ems_benchmark(code);
CREATE INDEX IF NOT EXISTS idx_benchmark_type ON ems_benchmark(type);

-- =====================================================
-- 对标值示例数据
-- =====================================================

-- 国家标准 - 电力
INSERT INTO ems_benchmark (code, name, type, grade, value, unit, national_num, status, remark, created_at, updated_at) VALUES
('GB-ELEC-001', '单位产品电耗限额（水泥）', 'NATIONAL', '先进值', 75.0000, 'kWh/t', 'GB 16780-2021', 0, '水泥单位产品综合电耗先进值', NOW(), NOW());

INSERT INTO ems_benchmark (code, name, type, grade, value, unit, national_num, status, remark, created_at, updated_at) VALUES
('GB-ELEC-002', '单位产品电耗限额（水泥）', 'NATIONAL', '准入值', 85.0000, 'kWh/t', 'GB 16780-2021', 0, '水泥单位产品综合电耗准入值', NOW(), NOW());

INSERT INTO ems_benchmark (code, name, type, grade, value, unit, national_num, status, remark, created_at, updated_at) VALUES
('GB-ELEC-003', '单位产品电耗限额（钢铁）', 'NATIONAL', '先进值', 450.0000, 'kWh/t', 'GB 21256-2021', 0, '粗钢综合电耗先进值', NOW(), NOW());

INSERT INTO ems_benchmark (code, name, type, grade, value, unit, national_num, status, remark, created_at, updated_at) VALUES
('GB-ELEC-004', '单位产品电耗限额（钢铁）', 'NATIONAL', '准入值', 520.0000, 'kWh/t', 'GB 21256-2021', 0, '粗钢综合电耗准入值', NOW(), NOW());

-- 行业标准
INSERT INTO ems_benchmark (code, name, type, grade, value, unit, national_num, status, remark, created_at, updated_at) VALUES
('IND-ELEC-001', '空压机能效限值', 'INDUSTRY', '1级', 6.5000, 'kW/(m³/min)', 'GB 19153-2019', 0, '空压机1级能效限值', NOW(), NOW());

INSERT INTO ems_benchmark (code, name, type, grade, value, unit, national_num, status, remark, created_at, updated_at) VALUES
('IND-ELEC-002', '空压机能效限值', 'INDUSTRY', '2级', 7.2000, 'kW/(m³/min)', 'GB 19153-2019', 0, '空压机2级能效限值', NOW(), NOW());

INSERT INTO ems_benchmark (code, name, type, grade, value, unit, national_num, status, remark, created_at, updated_at) VALUES
('IND-ELEC-003', '电动机能效限值', 'INDUSTRY', '1级', 96.2000, '%', 'GB 18613-2020', 0, '三相异步电动机1级能效限值(典型值)', NOW(), NOW());

-- 企业标准
INSERT INTO ems_benchmark (code, name, type, grade, value, unit, national_num, status, remark, created_at, updated_at) VALUES
('ENT-ELEC-001', '车间单位产出电耗', 'ENTERPRISE', '目标值', 120.0000, 'kWh/万元', NULL, 0, '企业内部目标值', NOW(), NOW());

INSERT INTO ems_benchmark (code, name, type, grade, value, unit, national_num, status, remark, created_at, updated_at) VALUES
('ENT-ELEC-002', '办公楼单位面积电耗', 'ENTERPRISE', '目标值', 80.0000, 'kWh/m²/年', NULL, 0, '办公楼年度用电目标', NOW(), NOW());

-- 区域标准
INSERT INTO ems_benchmark (code, name, type, grade, value, unit, national_num, status, remark, created_at, updated_at) VALUES
('REG-ELEC-001', '广东省工业用电限额', 'REGIONAL', '先进值', 95.0000, 'kWh/万元', 'DB44/T xxx', 0, '广东省地方标准', NOW(), NOW());

-- 验证数据
-- SELECT * FROM ems_benchmark ORDER BY type, code;
