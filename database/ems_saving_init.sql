-- Terra EMS 初始数据 - 节能管理
-- 表名: ems_energy_saving_project, ems_policy
-- 说明: 节能项目管理和政策法规管理

-- 状态值说明:
-- ProjectStatus: PLANNING(规划中), IN_PROGRESS(进行中), COMPLETED(已完成), CANCELLED(已取消)
-- PolicyType: NATIONAL(国家级), LOCAL(地方级), INDUSTRY(行业标准), OTHER(其他)
-- DataItemStatus: 0 = ENABLE (启用), 1 = FORBIDDEN (禁用)

-- =====================================================
-- 如果表已存在且列类型有问题，先删除重建
-- =====================================================
-- DROP TABLE IF EXISTS ems_energy_saving_project CASCADE;
-- DROP TABLE IF EXISTS ems_policy CASCADE;

-- =====================================================
-- 创建节能项目表（如果不存在）
-- =====================================================
CREATE TABLE IF NOT EXISTS ems_energy_saving_project (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    plan TEXT,
    implementation_plan TEXT,
    current_work VARCHAR(500),
    liable_person VARCHAR(50),
    saving_amount NUMERIC(12, 2),
    completion_time DATE,
    status VARCHAR(20) DEFAULT 'PLANNING',
    remark VARCHAR(500),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_saving_project_status ON ems_energy_saving_project(status);
CREATE INDEX IF NOT EXISTS idx_saving_project_liable ON ems_energy_saving_project(liable_person);

-- =====================================================
-- 创建政策法规表（如果不存在）
-- =====================================================
CREATE TABLE IF NOT EXISTS ems_policy (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    type VARCHAR(20) DEFAULT 'NATIONAL',
    department VARCHAR(100),
    issuing_date DATE,
    file_url VARCHAR(500),
    summary TEXT,
    status INTEGER DEFAULT 0,
    remark VARCHAR(500),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_policy_type ON ems_policy(type);
CREATE INDEX IF NOT EXISTS idx_policy_issuing_date ON ems_policy(issuing_date);

-- =====================================================
-- 清空现有数据（可选）
-- =====================================================
-- DELETE FROM ems_energy_saving_project;
-- DELETE FROM ems_policy;

-- =====================================================
-- 节能项目示例数据
-- =====================================================
INSERT INTO ems_energy_saving_project (name, plan, implementation_plan, current_work, liable_person, saving_amount, completion_time, status, remark, created_at, updated_at) VALUES
('空压系统节能改造', '对现有空压机组进行变频改造，预计年节电15%', 
 '第一阶段：设备评估（1月）
第二阶段：设备采购（2月）
第三阶段：安装调试（3月）
第四阶段：验收运行（4月）',
 '设备采购阶段，已完成招标', '张工', 50000.00, '2026-04-30', 'IN_PROGRESS', '投资回收期约2年', NOW(), NOW());

INSERT INTO ems_energy_saving_project (name, plan, implementation_plan, current_work, liable_person, saving_amount, completion_time, status, remark, created_at, updated_at) VALUES
('LED照明改造', '将传统照明替换为LED照明，覆盖全厂车间', 
 '第一阶段：调研设计（2周）
第二阶段：分区实施（4周）
第三阶段：效果评估（2周）',
 '已完成全部改造', '李工', 30000.00, '2025-12-31', 'COMPLETED', '年节电约20万度', NOW(), NOW());

INSERT INTO ems_energy_saving_project (name, plan, implementation_plan, current_work, liable_person, saving_amount, completion_time, status, remark, created_at, updated_at) VALUES
('烘干炉余热回收', '利用烘干炉余热进行预热，减少能源消耗', 
 '第一阶段：可行性研究
第二阶段：方案设计
第三阶段：设备选型',
 '可行性研究阶段', '王工', 80000.00, '2026-06-30', 'PLANNING', '需要专业设计院支持', NOW(), NOW());

INSERT INTO ems_energy_saving_project (name, plan, implementation_plan, current_work, liable_person, saving_amount, completion_time, status, remark, created_at, updated_at) VALUES
('电机能效提升', '更换高效电机，提升系统效率', 
 NULL, NULL, '赵工', NULL, '2026-08-31', 'PLANNING', '待确定实施范围', NOW(), NOW());

INSERT INTO ems_energy_saving_project (name, plan, implementation_plan, current_work, liable_person, saving_amount, completion_time, status, remark, created_at, updated_at) VALUES
('中央空调优化', '已取消原因：预算调整', 
 NULL, NULL, '刘工', NULL, NULL, 'CANCELLED', '2025年度预算不足', NOW(), NOW());

-- =====================================================
-- 政策法规示例数据
-- =====================================================
INSERT INTO ems_policy (title, type, department, issuing_date, file_url, summary, status, remark, created_at, updated_at) VALUES
('中华人民共和国节约能源法', 'NATIONAL', '全国人民代表大会常务委员会', '2018-10-26', 
 'https://flk.npc.gov.cn/detail2.html?ZmY4MDgwODE2ZjNjYmIzYzAxNmY0MTBhY2YxYjAxNGY%3D',
 '国家实行节约资源的基本国策，国家实施节约与开发并举、把节约放在首位的能源发展战略，鼓励、支持节能科学技术的研究、开发、示范和推广。',
 0, '主要法律依据', NOW(), NOW());

INSERT INTO ems_policy (title, type, department, issuing_date, file_url, summary, status, remark, created_at, updated_at) VALUES
('工业节能诊断服务行动计划', 'NATIONAL', '工业和信息化部', '2019-05-27',
 'https://www.miit.gov.cn/zwgk/zcwj/wjfb/txy/art/2020/art_d5f4d2e0c3614da2a3e1a6b3f0b2c5d7.html',
 '推动开展工业节能诊断服务，帮助企业发掘节能潜力，提高能效水平。',
 0, '节能诊断参考', NOW(), NOW());

INSERT INTO ems_policy (title, type, department, issuing_date, file_url, summary, status, remark, created_at, updated_at) VALUES
('广东省节约能源条例', 'LOCAL', '广东省人民代表大会常务委员会', '2022-11-30',
 NULL,
 '本省行政区域内从事和涉及能源生产、经营、消费及其相关活动的单位和个人适用本条例。',
 0, '地方性法规', NOW(), NOW());

INSERT INTO ems_policy (title, type, department, issuing_date, file_url, summary, status, remark, created_at, updated_at) VALUES
('电机系统节能技术规范', 'INDUSTRY', '国家市场监督管理总局', '2020-06-01',
 NULL,
 '规定了电机系统节能的技术要求、检测方法和评价准则。',
 0, '技术标准', NOW(), NOW());

INSERT INTO ems_policy (title, type, department, issuing_date, file_url, summary, status, remark, created_at, updated_at) VALUES
('重点用能单位能耗在线监测系统技术规范', 'NATIONAL', '国家发展和改革委员会', '2017-12-01',
 NULL,
 '规定了重点用能单位能耗在线监测系统的技术要求、数据采集、传输和应用等内容。',
 0, '能耗监测依据', NOW(), NOW());

INSERT INTO ems_policy (title, type, department, issuing_date, file_url, summary, status, remark, created_at, updated_at) VALUES
('企业能效对标指南', 'INDUSTRY', '中国标准化研究院', '2021-03-15',
 NULL,
 '指导企业开展能效对标活动，包括对标指标体系、对标流程、最佳实践等内容。',
 0, '对标管理参考', NOW(), NOW());

-- 验证数据
-- SELECT * FROM ems_energy_saving_project ORDER BY created_at DESC;
-- SELECT * FROM ems_policy ORDER BY issuing_date DESC;
