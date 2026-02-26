-- Terra EMS v3 数据库初始化脚本 (PostgreSQL)

-- 创建数据库
CREATE DATABASE terra_ems
    WITH 
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'zh_CN.UTF-8'
    LC_CTYPE = 'zh_CN.UTF-8'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;

-- 连接到数据库
\c terra_ems;

-- 创建用户表（JPA会自动创建，这里提供手动创建脚本作为参考）
CREATE TABLE IF NOT EXISTS sys_user (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(64) NOT NULL UNIQUE,
    password VARCHAR(128) NOT NULL,
    real_name VARCHAR(64),
    email VARCHAR(128),
    phone VARCHAR(20) UNIQUE,
    status INTEGER NOT NULL DEFAULT 1,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

-- 创建索引
CREATE INDEX IF NOT EXISTS idx_sys_user_username ON sys_user(username);
CREATE INDEX IF NOT EXISTS idx_sys_user_phone ON sys_user(phone);

-- 插入测试用户（密码: admin123, BCrypt加密）
INSERT INTO sys_user (username, password, real_name, status, created_at, updated_at) 
VALUES (
    'admin', 
    '$2a$10$N.zmdr9k7uOCQb376NoUnuTUNiCbsd7r0fPp.kGTw3DZSlPRdN5Y6', 
    '系统管理员', 
    1, 
    NOW(), 
    NOW()
) ON CONFLICT (username) DO NOTHING;

-- 查询验证
SELECT id, username, real_name, status, created_at FROM sys_user;
-- ===================================================
-- Terra EMS 字典初始化数据
-- ===================================================

-- ===================================================
-- 1. 系统基础字典类型
-- ===================================================
INSERT INTO sys_dict_type (id, name, type, status, created_at, remark) VALUES
-- 系统通用
(1, '用户性别', 'SYS_USER_SEX', '0', NOW(), '用户性别列表'),
(2, '菜单状态', 'SYS_SHOW_HIDE', '0', NOW(), '菜单状态列表'),
(3, '系统开关', 'SYS_NORMAL_DISABLE', '0', NOW(), '系统开关列表'),
(4, '任务状态', 'SYS_JOB_STATUS', '0', NOW(), '任务状态列表'),
(5, '任务分组', 'SYS_JOB_GROUP', '0', NOW(), '任务分组列表'),
(6, '系统是否', 'SYS_YES_NO', '0', NOW(), '系统是否列表'),
(7, '通知类型', 'SYS_NOTICE_TYPE', '0', NOW(), '通知类型列表'),
(8, '通知状态', 'SYS_NOTICE_STATUS', '0', NOW(), '通知状态列表'),
(9, '操作类型', 'SYS_OPER_TYPE', '0', NOW(), '操作类型列表'),
(10, '系统状态', 'SYS_COMMON_STATUS', '0', NOW(), '登录状态列表'),
(11, '单位管理', 'SYS_UNIT', '0', NOW(), '单位基础数据'),
(12, '是否默认', 'SYS_IS_DEFAULT', '0', NOW(), '字典数据是否默认'),
(13, '是否启用', 'SYS_IS_ENABLE', '0', NOW(), '是否启用开关'),
-- 能源管理相关
(20, '尖峰平谷配置', 'ELECTRICITY_PRICE', '0', NOW(), '电价类别配置'),
(21, '指标类型', 'INDEX_CATEGORY', '0', NOW(), '系统指标类型'),
(22, '计量器具类型', 'DEVICE_TYPE', '0', NOW(), '计量器具类型'),
(23, '计量器具状态', 'METER_STATUS', '0', NOW(), '计量器具的使用状态'),
(24, '参数数据类型', 'DATA_TYPE', '0', NOW(), '标准参数配置数据类型'),
(25, '指标节点类型', 'NODE_CATEGORY', '0', NOW(), '指标节点分类'),
(26, '能耗等级', 'FACILITY_GRADE', '0', NOW(), '设备档案的能耗等级'),
(27, '设备类型', 'FACILITY_TYPE', '0', NOW(), '设备档案管理的设备类型'),
(28, '应用模型类型', 'MODEL_TYPE', '0', NOW(), '应用模型类型'),
-- 报警相关
(30, '预警报警时段', 'WARN_TIME_SLOT', '0', NOW(), '预警报警时段类型'),
(31, '预警报警类型', 'ALARM_TYPE', '0', NOW(), '预警报警类型'),
(32, '报警级别', 'ALARM_LEVEL', '0', NOW(), '报警级别'),
(33, '预报警限值类型', 'LIMIT_TYPE', '0', NOW(), '预报警限值类型'),
-- 时间统计相关
(40, '统计时间', 'TIME_TYPE', '0', NOW(), '统计时间类型'),
(41, '用能统计时间', 'DATE_TYPE', '0', NOW(), '用能统计时间类型'),
(42, '期间', 'PERIOD', '0', NOW(), '数据录入期间类型'),
(43, '阶段数据录入时间', 'ENTRY_DATA_TIME', '0', NOW(), '阶段数据录入时间类型')
ON CONFLICT (id) DO NOTHING;

-- ===================================================
-- 2. 系统基础字典数据
-- ===================================================

-- SYS_USER_SEX 用户性别
INSERT INTO sys_dict_data (sort_order, label, value, type_code, tag_type, is_default, status, created_at) VALUES
(1, '男', '0', 'SYS_USER_SEX', 'processing', 'Y', '0', NOW()),
(2, '女', '1', 'SYS_USER_SEX', 'success', 'N', '0', NOW()),
(3, '未知', '2', 'SYS_USER_SEX', 'default', 'N', '0', NOW())
ON CONFLICT (value, type_code) DO NOTHING;

-- SYS_SHOW_HIDE 菜单状态
INSERT INTO sys_dict_data (sort_order, label, value, type_code, tag_type, is_default, status, created_at) VALUES
(1, '显示', '0', 'SYS_SHOW_HIDE', 'processing', 'Y', '0', NOW()),
(2, '隐藏', '1', 'SYS_SHOW_HIDE', 'error', 'N', '0', NOW())
ON CONFLICT (value, type_code) DO NOTHING;

-- SYS_NORMAL_DISABLE 系统开关
INSERT INTO sys_dict_data (sort_order, label, value, type_code, tag_type, is_default, status, created_at) VALUES
(1, '正常', '0', 'SYS_NORMAL_DISABLE', 'processing', 'Y', '0', NOW()),
(2, '停用', '1', 'SYS_NORMAL_DISABLE', 'error', 'N', '0', NOW())
ON CONFLICT (value, type_code) DO NOTHING;

-- SYS_JOB_STATUS 任务状态
INSERT INTO sys_dict_data (sort_order, label, value, type_code, tag_type, is_default, status, created_at) VALUES
(1, '正常', '0', 'SYS_JOB_STATUS', 'processing', 'Y', '0', NOW()),
(2, '暂停', '1', 'SYS_JOB_STATUS', 'error', 'N', '0', NOW())
ON CONFLICT (value, type_code) DO NOTHING;

-- SYS_JOB_GROUP 任务分组
INSERT INTO sys_dict_data (sort_order, label, value, type_code, tag_type, is_default, status, created_at) VALUES
(1, '默认', 'DEFAULT', 'SYS_JOB_GROUP', 'default', 'Y', '0', NOW()),
(2, '系统', 'SYSTEM', 'SYS_JOB_GROUP', 'processing', 'N', '0', NOW())
ON CONFLICT (value, type_code) DO NOTHING;

-- SYS_YES_NO 系统是否
INSERT INTO sys_dict_data (sort_order, label, value, type_code, tag_type, is_default, status, created_at) VALUES
(1, '是', 'Y', 'SYS_YES_NO', 'processing', 'Y', '0', NOW()),
(2, '否', 'N', 'SYS_YES_NO', 'error', 'N', '0', NOW())
ON CONFLICT (value, type_code) DO NOTHING;

-- SYS_NOTICE_TYPE 通知类型
INSERT INTO sys_dict_data (sort_order, label, value, type_code, tag_type, is_default, status, created_at) VALUES
(1, '通知', '1', 'SYS_NOTICE_TYPE', 'warning', 'Y', '0', NOW()),
(2, '公告', '2', 'SYS_NOTICE_TYPE', 'success', 'N', '0', NOW())
ON CONFLICT (value, type_code) DO NOTHING;

-- SYS_NOTICE_STATUS 通知状态
INSERT INTO sys_dict_data (sort_order, label, value, type_code, tag_type, is_default, status, created_at) VALUES
(1, '正常', '0', 'SYS_NOTICE_STATUS', 'processing', 'Y', '0', NOW()),
(2, '关闭', '1', 'SYS_NOTICE_STATUS', 'error', 'N', '0', NOW())
ON CONFLICT (value, type_code) DO NOTHING;

-- SYS_OPER_TYPE 操作类型
INSERT INTO sys_dict_data (sort_order, label, value, type_code, tag_type, is_default, status, created_at) VALUES
(1, '新增', '1', 'SYS_OPER_TYPE', 'success', 'N', '0', NOW()),
(2, '修改', '2', 'SYS_OPER_TYPE', 'processing', 'N', '0', NOW()),
(3, '删除', '3', 'SYS_OPER_TYPE', 'error', 'N', '0', NOW()),
(4, '授权', '4', 'SYS_OPER_TYPE', 'processing', 'N', '0', NOW()),
(5, '导出', '5', 'SYS_OPER_TYPE', 'warning', 'N', '0', NOW()),
(6, '导入', '6', 'SYS_OPER_TYPE', 'warning', 'N', '0', NOW()),
(7, '强退', '7', 'SYS_OPER_TYPE', 'error', 'N', '0', NOW()),
(8, '生成代码', '8', 'SYS_OPER_TYPE', 'warning', 'N', '0', NOW()),
(9, '清空数据', '9', 'SYS_OPER_TYPE', 'error', 'N', '0', NOW())
ON CONFLICT (value, type_code) DO NOTHING;

-- SYS_COMMON_STATUS 系统状态
INSERT INTO sys_dict_data (sort_order, label, value, type_code, tag_type, is_default, status, created_at) VALUES
(1, '成功', '0', 'SYS_COMMON_STATUS', 'success', 'Y', '0', NOW()),
(2, '失败', '1', 'SYS_COMMON_STATUS', 'error', 'N', '0', NOW())
ON CONFLICT (value, type_code) DO NOTHING;

-- SYS_IS_DEFAULT 是否默认
INSERT INTO sys_dict_data (sort_order, label, value, type_code, tag_type, is_default, status, created_at) VALUES
(1, '是', 'Y', 'SYS_IS_DEFAULT', 'processing', 'Y', '0', NOW()),
(2, '否', 'N', 'SYS_IS_DEFAULT', 'default', 'N', '0', NOW())
ON CONFLICT (value, type_code) DO NOTHING;

-- SYS_IS_ENABLE 是否启用
INSERT INTO sys_dict_data (sort_order, label, value, type_code, tag_type, is_default, status, created_at) VALUES
(1, '启用', 'Y', 'SYS_IS_ENABLE', 'success', 'Y', '0', NOW()),
(2, '停用', 'N', 'SYS_IS_ENABLE', 'error', 'N', '0', NOW())
ON CONFLICT (value, type_code) DO NOTHING;

-- ===================================================
-- 3. 单位管理 SYS_UNIT
-- ===================================================
INSERT INTO sys_dict_data (sort_order, label, value, type_code, tag_type, is_default, status, created_at) VALUES
-- 电力单位
(1, '千瓦时', 'kWh', 'SYS_UNIT', 'default', 'N', '0', NOW()),
(2, '千瓦', 'kW', 'SYS_UNIT', 'default', 'N', '0', NOW()),
(3, '安培', 'A', 'SYS_UNIT', 'default', 'N', '0', NOW()),
(4, '毫安', 'mA', 'SYS_UNIT', 'default', 'N', '0', NOW()),
(5, '伏特', 'V', 'SYS_UNIT', 'default', 'N', '0', NOW()),
(6, '千伏', 'kV', 'SYS_UNIT', 'default', 'N', '0', NOW()),
(7, '千乏', 'kVAR', 'SYS_UNIT', 'default', 'N', '0', NOW()),
(8, '伏安', 'VA', 'SYS_UNIT', 'default', 'N', '0', NOW()),
(9, '赫兹', 'Hz', 'SYS_UNIT', 'default', 'N', '0', NOW()),
-- 体积/流量
(10, '立方米', 'm³', 'SYS_UNIT', 'default', 'N', '0', NOW()),
(11, '立方米/小时', 'm³/h', 'SYS_UNIT', 'default', 'N', '0', NOW()),
(12, '标准立方米', 'Nm³', 'SYS_UNIT', 'default', 'N', '0', NOW()),
(13, '标准立方米/小时', 'Nm³/h', 'SYS_UNIT', 'default', 'N', '0', NOW()),
-- 质量
(14, '吨', 't', 'SYS_UNIT', 'default', 'N', '0', NOW()),
(15, '千克', 'kg', 'SYS_UNIT', 'default', 'N', '0', NOW()),
(16, '吨/小时', 't/h', 'SYS_UNIT', 'default', 'N', '0', NOW()),
(17, '千克/小时', 'kg/h', 'SYS_UNIT', 'default', 'N', '0', NOW()),
(18, '千克/立方米', 'kg/m³', 'SYS_UNIT', 'default', 'N', '0', NOW()),
-- 压力
(19, '兆帕', 'MPa', 'SYS_UNIT', 'default', 'N', '0', NOW()),
(20, '千帕', 'kPa', 'SYS_UNIT', 'default', 'N', '0', NOW()),
(21, '帕', 'Pa', 'SYS_UNIT', 'default', 'N', '0', NOW()),
-- 温度/湿度
(22, '摄氏度', '℃', 'SYS_UNIT', 'default', 'N', '0', NOW()),
(23, '华氏度', '℉', 'SYS_UNIT', 'default', 'N', '0', NOW()),
(24, '相对湿度', '%RH', 'SYS_UNIT', 'default', 'N', '0', NOW()),
-- 其他
(25, '百分比', '%', 'SYS_UNIT', 'default', 'N', '0', NOW()),
(26, '元', 'RMB', 'SYS_UNIT', 'default', 'N', '0', NOW()),
(27, '个', 'N', 'SYS_UNIT', 'default', 'N', '0', NOW()),
(28, '小时', 'hour', 'SYS_UNIT', 'default', 'N', '0', NOW()),
(29, '次', 'times', 'SYS_UNIT', 'default', 'N', '0', NOW()),
-- 标煤相关
(30, '吨标准煤', 'tce', 'SYS_UNIT', 'default', 'N', '0', NOW()),
(31, '千克标准煤/吨', 'kgce/t', 'SYS_UNIT', 'default', 'N', '0', NOW())
ON CONFLICT (value, type_code) DO NOTHING;

-- ===================================================
-- 4. 尖峰平谷配置 ELECTRICITY_PRICE
-- ===================================================
INSERT INTO sys_dict_data (sort_order, label, value, type_code, tag_type, is_default, status, created_at) VALUES
(1, '尖', 'SHARP', 'ELECTRICITY_PRICE', 'error', 'N', '0', NOW()),
(2, '峰', 'PEAK', 'ELECTRICITY_PRICE', 'warning', 'N', '0', NOW()),
(3, '平', 'FLAT', 'ELECTRICITY_PRICE', 'success', 'N', '0', NOW()),
(4, '谷', 'VALLEY', 'ELECTRICITY_PRICE', 'default', 'N', '0', NOW()),
(5, '深谷', 'DEEP', 'ELECTRICITY_PRICE', 'processing', 'N', '0', NOW())
ON CONFLICT (value, type_code) DO NOTHING;

-- ===================================================
-- 5. 指标类型 INDEX_CATEGORY
-- ===================================================
INSERT INTO sys_dict_data (sort_order, label, value, type_code, tag_type, is_default, status, created_at) VALUES
(1, '能源类指标', '1', 'INDEX_CATEGORY', 'processing', 'Y', '0', NOW()),
(2, '产品类指标', '2', 'INDEX_CATEGORY', 'success', 'N', '0', NOW()),
(3, '能效类指标', '3', 'INDEX_CATEGORY', 'warning', 'N', '0', NOW()),
(4, '经营类指标', '4', 'INDEX_CATEGORY', 'default', 'N', '0', NOW()),
(5, '其他', '5', 'INDEX_CATEGORY', 'default', 'N', '0', NOW())
ON CONFLICT (value, type_code) DO NOTHING;

-- ===================================================
-- 6. 计量器具类型 DEVICE_TYPE
-- ===================================================
INSERT INTO sys_dict_data (sort_order, label, value, type_code, tag_type, is_default, status, created_at) VALUES
(1, '电表', '1', 'DEVICE_TYPE', 'processing', 'Y', '0', NOW()),
(2, '水表', '2', 'DEVICE_TYPE', 'processing', 'N', '0', NOW()),
(3, '变频器', '3', 'DEVICE_TYPE', 'default', 'N', '0', NOW()),
(4, '温湿度表', '4', 'DEVICE_TYPE', 'default', 'N', '0', NOW()),
(5, '压力表', '5', 'DEVICE_TYPE', 'default', 'N', '0', NOW()),
(6, '蒸汽表', '6', 'DEVICE_TYPE', 'default', 'N', '0', NOW()),
(7, '氮气表', '7', 'DEVICE_TYPE', 'default', 'N', '0', NOW()),
(10, '天然气表', '10', 'DEVICE_TYPE', 'default', 'N', '0', NOW())
ON CONFLICT (value, type_code) DO NOTHING;

-- ===================================================
-- 7. 计量器具状态 METER_STATUS
-- ===================================================
INSERT INTO sys_dict_data (sort_order, label, value, type_code, tag_type, is_default, status, created_at) VALUES
(1, '使用中', '1', 'METER_STATUS', 'success', 'N', '0', NOW()),
(2, '检修中', '2', 'METER_STATUS', 'warning', 'N', '0', NOW()),
(3, '备用', '3', 'METER_STATUS', 'default', 'Y', '0', NOW())
ON CONFLICT (value, type_code) DO NOTHING;

-- ===================================================
-- 8. 参数数据类型 DATA_TYPE
-- ===================================================
INSERT INTO sys_dict_data (sort_order, label, value, type_code, tag_type, is_default, status, created_at) VALUES
(1, '实时数据', '1', 'DATA_TYPE', 'processing', 'N', '0', NOW()),
(2, '阶段数据', '2', 'DATA_TYPE', 'success', 'N', '0', NOW())
ON CONFLICT (value, type_code) DO NOTHING;

-- ===================================================
-- 9. 指标节点类型 NODE_CATEGORY
-- ===================================================
INSERT INTO sys_dict_data (sort_order, label, value, type_code, tag_type, is_default, status, created_at) VALUES
(0, '公司', '0', 'NODE_CATEGORY', 'processing', 'Y', '0', NOW()),
(1, '厂部', '1', 'NODE_CATEGORY', 'processing', 'N', '0', NOW()),
(2, '配电室', '2', 'NODE_CATEGORY', 'default', 'N', '0', NOW()),
(3, '区域', '3', 'NODE_CATEGORY', 'default', 'N', '0', NOW()),
(4, '重点能耗设备', '4', 'NODE_CATEGORY', 'warning', 'N', '0', NOW()),
(5, '设备', '5', 'NODE_CATEGORY', 'default', 'N', '0', NOW()),
(9, '其他', '9', 'NODE_CATEGORY', 'default', 'N', '0', NOW())
ON CONFLICT (value, type_code) DO NOTHING;

-- ===================================================
-- 10. 能耗等级 FACILITY_GRADE
-- ===================================================
INSERT INTO sys_dict_data (sort_order, label, value, type_code, tag_type, is_default, status, created_at) VALUES
(1, '一级', '1', 'FACILITY_GRADE', 'success', 'Y', '0', NOW()),
(2, '二级', '2', 'FACILITY_GRADE', 'processing', 'N', '0', NOW()),
(3, '三级', '3', 'FACILITY_GRADE', 'warning', 'N', '0', NOW()),
(4, '无', '4', 'FACILITY_GRADE', 'default', 'N', '0', NOW())
ON CONFLICT (value, type_code) DO NOTHING;

-- ===================================================
-- 11. 设备类型 FACILITY_TYPE
-- ===================================================
INSERT INTO sys_dict_data (sort_order, label, value, type_code, tag_type, is_default, status, created_at) VALUES
(1, '空压机', '0', 'FACILITY_TYPE', 'default', 'Y', '0', NOW()),
(2, '电机', '1', 'FACILITY_TYPE', 'default', 'N', '0', NOW()),
(3, '风机', '2', 'FACILITY_TYPE', 'default', 'N', '0', NOW()),
(4, '水泵', '3', 'FACILITY_TYPE', 'default', 'N', '0', NOW()),
(5, '变压器', '4', 'FACILITY_TYPE', 'default', 'N', '0', NOW()),
(6, '制冷机', '5', 'FACILITY_TYPE', 'default', 'N', '0', NOW()),
(7, '锅炉', '6', 'FACILITY_TYPE', 'default', 'N', '0', NOW()),
(8, '输送机', '7', 'FACILITY_TYPE', 'default', 'N', '0', NOW()),
(9, '破碎机', '8', 'FACILITY_TYPE', 'default', 'N', '0', NOW()),
(10, '其他', '99', 'FACILITY_TYPE', 'default', 'N', '0', NOW())
ON CONFLICT (value, type_code) DO NOTHING;

-- ===================================================
-- 12. 应用模型类型 MODEL_TYPE
-- ===================================================
INSERT INTO sys_dict_data (sort_order, label, value, type_code, tag_type, is_default, status, created_at) VALUES
(1, '指标配置', 'index', 'MODEL_TYPE', 'processing', 'Y', '0', NOW()),
(2, '仅节点', 'node', 'MODEL_TYPE', 'default', 'N', '0', NOW())
ON CONFLICT (value, type_code) DO NOTHING;

-- ===================================================
-- 13. 预警报警时段 WARN_TIME_SLOT
-- ===================================================
INSERT INTO sys_dict_data (sort_order, label, value, type_code, tag_type, is_default, status, created_at) VALUES
(0, '实时', 'LIVE', 'WARN_TIME_SLOT', 'processing', 'Y', '0', NOW()),
(1, '小时', 'HOUR', 'WARN_TIME_SLOT', 'default', 'N', '0', NOW()),
(2, '天', 'DAY', 'WARN_TIME_SLOT', 'default', 'N', '0', NOW()),
(3, '月', 'MONTH', 'WARN_TIME_SLOT', 'default', 'N', '0', NOW()),
(4, '年', 'YEAR', 'WARN_TIME_SLOT', 'default', 'N', '0', NOW())
ON CONFLICT (value, type_code) DO NOTHING;

-- ===================================================
-- 14. 预警报警类型 ALARM_TYPE
-- ===================================================
INSERT INTO sys_dict_data (sort_order, label, value, type_code, tag_type, is_default, status, created_at) VALUES
(1, '预警', 'WARNING', 'ALARM_TYPE', 'warning', 'Y', '0', NOW()),
(2, '报警', 'ALARM', 'ALARM_TYPE', 'error', 'N', '0', NOW())
ON CONFLICT (value, type_code) DO NOTHING;

-- ===================================================
-- 15. 报警级别 ALARM_LEVEL
-- ===================================================
INSERT INTO sys_dict_data (sort_order, label, value, type_code, tag_type, is_default, status, created_at) VALUES
(1, '高', '1', 'ALARM_LEVEL', 'error', 'N', '0', NOW()),
(2, '中', '2', 'ALARM_LEVEL', 'warning', 'N', '0', NOW()),
(3, '低', '3', 'ALARM_LEVEL', 'default', 'Y', '0', NOW())
ON CONFLICT (value, type_code) DO NOTHING;

-- ===================================================
-- 16. 预报警限值类型 LIMIT_TYPE
-- ===================================================
INSERT INTO sys_dict_data (sort_order, label, value, type_code, tag_type, is_default, status, created_at) VALUES
(1, '上限', '1', 'LIMIT_TYPE', 'warning', 'N', '0', NOW()),
(2, '下限', '2', 'LIMIT_TYPE', 'processing', 'N', '0', NOW())
ON CONFLICT (value, type_code) DO NOTHING;

-- ===================================================
-- 17. 统计时间 TIME_TYPE
-- ===================================================
INSERT INTO sys_dict_data (sort_order, label, value, type_code, tag_type, is_default, status, created_at) VALUES
(1, '月', 'MONTH', 'TIME_TYPE', 'default', 'Y', '0', NOW()),
(2, '年', 'YEAR', 'TIME_TYPE', 'default', 'N', '0', NOW())
ON CONFLICT (value, type_code) DO NOTHING;

-- ===================================================
-- 18. 用能统计时间 DATE_TYPE
-- ===================================================
INSERT INTO sys_dict_data (sort_order, label, value, type_code, tag_type, is_default, status, created_at) VALUES
(1, '日', 'DAY', 'DATE_TYPE', 'default', 'N', '0', NOW()),
(2, '月', 'MONTH', 'DATE_TYPE', 'default', 'Y', '0', NOW()),
(3, '年', 'YEAR', 'DATE_TYPE', 'default', 'N', '0', NOW())
ON CONFLICT (value, type_code) DO NOTHING;

-- ===================================================
-- 19. 期间 PERIOD
-- ===================================================
INSERT INTO sys_dict_data (sort_order, label, value, type_code, tag_type, is_default, status, created_at) VALUES
(1, '天', 'DAY', 'PERIOD', 'default', 'N', '0', NOW()),
(2, '月', 'MONTH', 'PERIOD', 'default', 'Y', '0', NOW()),
(3, '年', 'YEAR', 'PERIOD', 'default', 'N', '0', NOW())
ON CONFLICT (value, type_code) DO NOTHING;

-- ===================================================
-- 20. 阶段数据录入时间 ENTRY_DATA_TIME
-- ===================================================
INSERT INTO sys_dict_data (sort_order, label, value, type_code, tag_type, is_default, status, created_at) VALUES
(1, '小时', 'HOUR', 'ENTRY_DATA_TIME', 'default', 'Y', '0', NOW()),
(2, '日', 'DAY', 'ENTRY_DATA_TIME', 'default', 'N', '0', NOW()),
(3, '月', 'MONTH', 'ENTRY_DATA_TIME', 'default', 'N', '0', NOW()),
(4, '年', 'YEAR', 'ENTRY_DATA_TIME', 'default', 'N', '0', NOW())
ON CONFLICT (value, type_code) DO NOTHING;
-- Terra EMS 初始数据 - 能源类型
-- 表前缀: ems_ (业务表)
-- 数据参考: zhitan-ems sys_energy 表

-- 清空现有数据
DELETE FROM ems_energy_type;

-- 能源类型初始数据
-- 字段说明: code, name, unit, category, storable, coefficient(折标系数), emission_factor(碳排放因子), default_price, icon, color, sort_order, status, remark

-- 注意: DataItemStatus 枚举定义:
-- 0 = ENABLE (启用)
-- 1 = FORBIDDEN (禁用)

INSERT INTO ems_energy_type (code, name, unit, category, storable, coefficient, emission_factor, default_price, icon, color, sort_order, status, remark, created_at) VALUES
-- 电力类
('ELECTRIC', '电力', 'kWh', 'ELECTRIC', false, 0.1229, 0.5703, 1.0000, 'ThunderboltOutlined', '#1890ff', 1, 0, '国网电力，等价折标系数', NOW()),

-- 燃料类
('NATURAL_GAS', '天然气', 'm³', 'GAS', false, 1.2143, 2.1620, 3.1100, 'FireOutlined', '#52c41a', 2, 0, '管道天然气', NOW()),
('LNG', '液态天然气', 'kg', 'GAS', true, 1.7572, 2.9388, 5.5000, 'FireOutlined', '#13c2c2', 3, 0, '液化天然气LNG', NOW()),
('COAL', '煤炭', 't', 'OTHER', true, 0.7143, 2.6930, 800.0000, 'icon-coal', '#8c8c8c', 4, 0, '一般烟煤', NOW()),
('GASOLINE', '汽油', 'L', 'OTHER', true, 1.4714, 2.9251, 8.5000, 'CarOutlined', '#fa8c16', 5, 0, '车用汽油', NOW()),
('DIESEL', '柴油', 'L', 'OTHER', true, 1.4571, 3.0959, 7.8000, 'CarOutlined', '#faad14', 6, 0, '车用柴油', NOW()),

-- 热力/蒸汽类
('STEAM', '蒸汽', 't', 'STEAM', false, 0.1286, NULL, 120.0000, 'CloudOutlined', '#eb2f96', 7, 0, '工业蒸汽', NOW()),
('HOT_WATER', '热水', 'GJ', 'STEAM', false, 0.0341, NULL, 35.0000, 'icon-hotwater', '#f5222d', 8, 0, '供暖热水', NOW()),

-- 水类
('WATER', '自来水', 't', 'WATER', true, 0.0857, NULL, 5.1100, 'icon-water', '#1677ff', 9, 0, '市政自来水', NOW()),
('RECLAIMED_WATER', '中水', 't', 'WATER', true, 0.0286, NULL, 2.5000, 'icon-water', '#36cfc9', 10, 0, '再生水/中水', NOW()),

-- 其他
('COMPRESSED_AIR', '压缩空气', 'm³', 'OTHER', false, 0.0040, NULL, 0.1500, 'icon-air', '#722ed1', 11, 0, '工业压缩空气', NOW()),
('OXYGEN', '氧气', 'm³', 'OTHER', true, 0.0050, NULL, 3.5000, 'icon-oxygen', '#2f54eb', 12, 0, '工业氧气', NOW()),
('NITROGEN', '氮气', 'm³', 'OTHER', true, 0.0045, NULL, 2.8000, 'icon-nitrogen', '#597ef7', 13, 0, '工业氮气', NOW());
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
-- Terra EMS 初始数据 - 计量器具
-- 表前缀: ems_ (业务表)
-- 依赖表: ems_energy_type (需先执行 ems_energy_type_init.sql)

-- 能源类型 ID (基于实际数据库):
-- 3 = 液态天然气
-- 4 = 煤炭
-- 5 = 汽油
-- 6 = 柴油
-- 7 = 蒸汽
-- 8 = 热水
-- 9 = 自来水
-- 10 = 中水
-- 11 = 压缩空气
-- 12 = 氧气
-- 13 = 氮气

-- 计量器具测试数据
INSERT INTO ems_meter (
    code, name, type, energy_type_id, model_number, measure_range, 
    manufacturer, person_charge, location, start_time, putrun_time,
    check_cycle, reminder_cycle, max_power, wire_diameter, gateway_id, 
    status, remark, created_at, updated_at
) VALUES
-- 蒸汽表 (energy_type_id = 7, 蒸汽)
('HM-001', '一车间蒸汽表', 'HEAT_METER', 7, 'LWGY-DN80', '0-999 t', 
 '大连理工', '周工', '一车间蒸汽管道入口', '2023-02-01', '2023-02-28', 
 365, 30, NULL, NULL, 'GW-002', 
 0, '一车间蒸汽用量计量，涡街流量计', NOW(), NOW()),

('HM-002', '烘干车间蒸汽表', 'HEAT_METER', 7, 'LUGB-DN100', '0-9999 t', 
 '横河仪器', '周工', '烘干车间蒸汽分汽缸', '2023-03-01', '2023-03-15', 
 365, 30, NULL, NULL, 'GW-002', 
 0, NULL, NOW(), NOW()),

('HM-003', '二车间蒸汽表', 'HEAT_METER', 7, 'LWGY-DN50', '0-500 t', 
 '横河仪器', '周工', '二车间蒸汽管道入口', '2023-06-01', '2023-06-10', 
 365, 30, NULL, NULL, 'GW-002', 
 0, '二车间蒸汽用量计量', NOW(), NOW()),

-- 热水表 (energy_type_id = 8, 热水)
('HW-001', '采暖热水总表', 'HEAT_METER', 8, 'DNCG-DN100', '0-9999 GJ', 
 '开封仪表', '李工', '锅炉房供热出口', '2023-01-01', '2023-01-10', 
 365, 30, NULL, NULL, 'GW-002', 
 0, '园区采暖热水总计量', NOW(), NOW()),

('HW-002', '办公楼热水表', 'HEAT_METER', 8, 'DNCG-DN50', '0-999 GJ', 
 '开封仪表', '李工', '办公楼地下室热力站', '2023-01-15', '2023-01-25', 
 365, 30, NULL, NULL, 'GW-003', 
 0, NULL, NOW(), NOW()),

-- 自来水表 (energy_type_id = 9, 自来水)
('WM-001', '生产用水总表', 'WATER_METER', 9, 'LXLC-50', '0-99999 m³', 
 '宁波水表', '陈工', '厂区进水总阀门处', '2023-01-01', '2023-01-10', 
 730, 60, NULL, NULL, 'GW-001', 
 0, '全厂生产用水总计量', NOW(), NOW()),

('WM-002', '冷却塔补水表', 'WATER_METER', 9, 'LXS-25', '0-9999 m³', 
 '宁波水表', '陈工', '冷却塔泵房', '2023-04-01', '2023-04-15', 
 730, 60, NULL, NULL, 'GW-001', 
 0, NULL, NOW(), NOW()),

('WM-003', '生活区用水表', 'WATER_METER', 9, 'LXS-50', '0-99999 m³', 
 '宁波水表', '陈工', '宿舍楼地下室', '2022-03-01', '2022-03-15', 
 730, 60, NULL, NULL, NULL, 
 0, '员工宿舍及食堂用水', NOW(), NOW()),

-- 中水表 (energy_type_id = 10, 中水)
('RW-001', '中水回用总表', 'WATER_METER', 10, 'LXLC-80', '0-99999 m³', 
 '宁波水表', '陈工', '中水处理站出水口', '2023-05-01', '2023-05-15', 
 730, 60, NULL, NULL, 'GW-001', 
 0, '中水回用系统总计量', NOW(), NOW()),

('RW-002', '绿化用中水表', 'WATER_METER', 10, 'LXS-25', '0-9999 m³', 
 '宁波水表', '陈工', '绿化灌溉泵房', '2023-05-10', '2023-05-20', 
 730, 60, NULL, NULL, NULL, 
 0, '厂区绿化用中水', NOW(), NOW()),

-- 液态天然气表 (energy_type_id = 3, 液态天然气)
('LNG-001', 'LNG储罐计量表', 'GAS_METER', 3, 'CMF200', '0-9999 kg', 
 '艾默生', '周工', 'LNG储罐区', '2023-01-01', '2023-01-20', 
 365, 30, NULL, NULL, 'GW-002', 
 0, 'LNG储罐进料计量', NOW(), NOW()),

('LNG-002', 'LNG气化站出口表', 'GAS_METER', 3, 'CMF100', '0-9999 kg', 
 '艾默生', '周工', 'LNG气化站', '2023-02-01', '2023-02-15', 
 365, 30, NULL, NULL, 'GW-002', 
 0, 'LNG气化后计量', NOW(), NOW()),

-- 压缩空气表 (energy_type_id = 11, 压缩空气)
('CA-001', '压缩空气总表', 'GAS_METER', 11, 'VA520-DN80', '0-99999 m³', 
 'CS仪表', '王工', '空压机房出口', '2023-03-01', '2023-03-10', 
 365, 30, NULL, NULL, 'GW-001', 
 0, '全厂压缩空气总供给量', NOW(), NOW()),

('CA-002', '一车间压缩空气表', 'GAS_METER', 11, 'VA520-DN50', '0-9999 m³', 
 'CS仪表', '王工', '一车间进气口', '2023-03-15', '2023-03-25', 
 365, 30, NULL, NULL, 'GW-001', 
 0, NULL, NOW(), NOW()),

('CA-003', '二车间压缩空气表', 'GAS_METER', 11, 'VA520-DN50', '0-9999 m³', 
 'CS仪表', '王工', '二车间进气口', '2023-03-15', '2023-03-25', 
 365, 30, NULL, NULL, 'GW-001', 
 0, NULL, NOW(), NOW()),

-- 氧气表 (energy_type_id = 12, 氧气)
('O2-001', '氧气站总表', 'GAS_METER', 12, 'MF5700-DN25', '0-9999 m³', 
 '迈捷科', '李工', '制氧站出口', '2023-04-01', '2023-04-15', 
 365, 30, NULL, NULL, 'GW-003', 
 0, '工业氧气总供给计量', NOW(), NOW()),

-- 氮气表 (energy_type_id = 13, 氮气)
('N2-001', '氮气站总表', 'GAS_METER', 13, 'MF5700-DN50', '0-99999 m³', 
 '迈捷科', '李工', '制氮站出口', '2023-04-01', '2023-04-15', 
 365, 30, NULL, NULL, 'GW-003', 
 0, '工业氮气总供给计量', NOW(), NOW()),

('N2-002', '包装车间氮气表', 'GAS_METER', 13, 'MF5700-DN25', '0-9999 m³', 
 '迈捷科', '李工', '包装车间进气口', '2023-05-01', '2023-05-10', 
 365, 30, NULL, NULL, 'GW-003', 
 0, '包装充氮用氮气计量', NOW(), NOW());

-- 停用的表具记录
INSERT INTO ems_meter (
    code, name, type, energy_type_id, model_number, measure_range, 
    manufacturer, person_charge, location, start_time, putrun_time,
    check_cycle, reminder_cycle, status, remark, created_at, updated_at
) VALUES
('WM-OLD-001', '旧水表(已更换)', 'WATER_METER', 9, 'LXS-15', '0-9999 m³', 
 '宁波水表', '陈工', '一车间(已更换)', '2015-01-01', '2015-02-01', 
 730, 60, 1, '2023年6月因精度问题更换', NOW(), NOW()),

('HM-OLD-001', '旧蒸汽表(已拆除)', 'HEAT_METER', 7, 'LWGY-DN40', '0-200 t', 
 '大连理工', '周工', '旧厂房(已拆除)', '2016-01-01', '2016-02-01', 
 365, 30, 1, '2023年12月随厂房拆除', NOW(), NOW());
-- Terra EMS 初始数据 - 采集点位
-- 表名: ems_meter_point
-- 说明: 定义计量器具的具体采集指标
-- 执行前提: 需要先执行能源类型和计量器具初始化脚本

-- 状态值说明:
-- 0 = ENABLE (启用)
-- 1 = FORBIDDEN (禁用)

-- 点位类型:
-- COLLECT = 采集类（直接从仪表采集）
-- CALC = 计算类（由其他点位计算得出）

-- 分类:
-- ENERGY = 能源类
-- PRODUCT = 产品类
-- EFFICIENCY = 能效类
-- OPERATION = 经营类
-- OTHER = 其他

-- =====================================================
-- 采集点位示例数据
-- =====================================================

-- 电力类采集点位（关联电表和电力能源类型）
INSERT INTO ems_meter_point (code, name, point_type, category, unit, initial_value, sort_order, status, remark, created_at, updated_at) VALUES
('PT_EP_TOTAL', '有功电量', 'COLLECT', 'ENERGY', 'kWh', 0, 1, 0, '正向有功总电量', NOW(), NOW()),
('PT_EQ_TOTAL', '无功电量', 'COLLECT', 'ENERGY', 'kvarh', 0, 2, 0, '正向无功总电量', NOW(), NOW()),
('PT_P_INST', '瞬时有功功率', 'COLLECT', 'ENERGY', 'kW', NULL, 3, 0, '瞬时有功功率', NOW(), NOW()),
('PT_Q_INST', '瞬时无功功率', 'COLLECT', 'ENERGY', 'kvar', NULL, 4, 0, '瞬时无功功率', NOW(), NOW()),
('PT_I_A', 'A相电流', 'COLLECT', 'ENERGY', 'A', NULL, 5, 0, NULL, NOW(), NOW()),
('PT_I_B', 'B相电流', 'COLLECT', 'ENERGY', 'A', NULL, 6, 0, NULL, NOW(), NOW()),
('PT_I_C', 'C相电流', 'COLLECT', 'ENERGY', 'A', NULL, 7, 0, NULL, NOW(), NOW()),
('PT_U_A', 'A相电压', 'COLLECT', 'ENERGY', 'V', NULL, 8, 0, NULL, NOW(), NOW()),
('PT_U_B', 'B相电压', 'COLLECT', 'ENERGY', 'V', NULL, 9, 0, NULL, NOW(), NOW()),
('PT_U_C', 'C相电压', 'COLLECT', 'ENERGY', 'V', NULL, 10, 0, NULL, NOW(), NOW()),
('PT_PF', '功率因数', 'COLLECT', 'EFFICIENCY', '', NULL, 11, 0, '综合功率因数', NOW(), NOW());

-- 水类采集点位
INSERT INTO ems_meter_point (code, name, point_type, category, unit, initial_value, sort_order, status, remark, created_at, updated_at) VALUES
('PT_WATER_TOTAL', '用水量', 'COLLECT', 'ENERGY', 'm³', 0, 1, 0, '累计用水量', NOW(), NOW()),
('PT_WATER_FLOW', '瞬时流量', 'COLLECT', 'ENERGY', 'm³/h', NULL, 2, 0, '瞬时水流量', NOW(), NOW());

-- 天然气类采集点位
INSERT INTO ems_meter_point (code, name, point_type, category, unit, initial_value, sort_order, status, remark, created_at, updated_at) VALUES
('PT_GAS_TOTAL', '用气量', 'COLLECT', 'ENERGY', 'm³', 0, 1, 0, '累计用气量', NOW(), NOW()),
('PT_GAS_FLOW', '瞬时流量', 'COLLECT', 'ENERGY', 'm³/h', NULL, 2, 0, '瞬时气流量', NOW(), NOW()),
('PT_GAS_TEMP', '气体温度', 'COLLECT', 'OTHER', '℃', NULL, 3, 0, '温度补偿用', NOW(), NOW()),
('PT_GAS_PRESS', '气体压力', 'COLLECT', 'OTHER', 'kPa', NULL, 4, 0, '压力补偿用', NOW(), NOW());

-- 蒸汽类采集点位
INSERT INTO ems_meter_point (code, name, point_type, category, unit, initial_value, sort_order, status, remark, created_at, updated_at) VALUES
('PT_STEAM_TOTAL', '蒸汽用量', 'COLLECT', 'ENERGY', 't', 0, 1, 0, '累计蒸汽用量', NOW(), NOW()),
('PT_STEAM_FLOW', '瞬时流量', 'COLLECT', 'ENERGY', 't/h', NULL, 2, 0, '瞬时蒸汽流量', NOW(), NOW()),
('PT_STEAM_TEMP', '蒸汽温度', 'COLLECT', 'OTHER', '℃', NULL, 3, 0, NULL, NOW(), NOW()),
('PT_STEAM_PRESS', '蒸汽压力', 'COLLECT', 'OTHER', 'MPa', NULL, 4, 0, NULL, NOW(), NOW());

-- 计算类点位示例
INSERT INTO ems_meter_point (code, name, point_type, category, unit, initial_value, sort_order, status, remark, created_at, updated_at) VALUES
('PT_ENERGY_COST', '能源成本', 'CALC', 'OPERATION', '元', NULL, 1, 0, '根据用量和单价计算', NOW(), NOW()),
('PT_UNIT_ENERGY', '单位产品能耗', 'CALC', 'EFFICIENCY', 'kWh/件', NULL, 2, 0, '能耗/产量', NOW(), NOW()),
('PT_CARBON_EMIT', '碳排放量', 'CALC', 'OTHER', 'tCO2', NULL, 3, 0, '根据能耗计算碳排放', NOW(), NOW());

-- 验证数据
-- SELECT * FROM ems_meter_point ORDER BY category, sort_order;
-- Terra EMS 初始数据 - 报警管理模块 (PostgreSQL)
-- 包含: 报警限值类型、预报警配置、报警历史记录
-- 执行前提: 需要先执行采集点位初始化脚本

-- =====================================================
-- 1. 报警限值类型 (ems_alarm_limit_type)
-- =====================================================
DELETE FROM ems_alarm_limit_type;

INSERT INTO ems_alarm_limit_type (limit_name, limit_code, color_number, comparator_operator, alarm_type, created_at, updated_at) VALUES
-- 预警类型 (WARNING)
('高值预警', 'HI_WARN', '#faad14', '>', 'WARNING', NOW(), NOW()),
('低值预警', 'LO_WARN', '#1890ff', '<', 'WARNING', NOW(), NOW()),
('偏高预警', 'HH_WARN', '#fa8c16', '>=', 'WARNING', NOW(), NOW()),
('偏低预警', 'LL_WARN', '#13c2c2', '<=', 'WARNING', NOW(), NOW()),

-- 报警类型 (ALARM)
('高高报警', 'HI_HI', '#ff4d4f', '>', 'ALARM', NOW(), NOW()),
('低低报警', 'LO_LO', '#f5222d', '<', 'ALARM', NOW(), NOW()),
('超限报警', 'OVER_LIMIT', '#eb2f96', '>=', 'ALARM', NOW(), NOW()),
('欠限报警', 'UNDER_LIMIT', '#722ed1', '<=', 'ALARM', NOW(), NOW());

-- =====================================================
-- 2. 预报警配置 (ems_alarm_config)
-- 关联采集点位 (node_type='POINT') 和报警限值类型
-- =====================================================
DELETE FROM ems_alarm_config;

-- 有功电量 - 高值预警和高高报警
INSERT INTO ems_alarm_config (node_type, node_id, limit_type_id, limit_value, is_enabled, remark, created_at, updated_at)
SELECT 'POINT', mp.id, alt.id, 1000, true, '日用电量超过1000kWh时预警', NOW(), NOW()
FROM ems_meter_point mp, ems_alarm_limit_type alt
WHERE mp.code = 'PT_EP_TOTAL' AND alt.limit_code = 'HI_WARN';

INSERT INTO ems_alarm_config (node_type, node_id, limit_type_id, limit_value, is_enabled, remark, created_at, updated_at)
SELECT 'POINT', mp.id, alt.id, 1500, true, '日用电量超过1500kWh时报警', NOW(), NOW()
FROM ems_meter_point mp, ems_alarm_limit_type alt
WHERE mp.code = 'PT_EP_TOTAL' AND alt.limit_code = 'HI_HI';

-- 瞬时功率 - 高值预警和高高报警
INSERT INTO ems_alarm_config (node_type, node_id, limit_type_id, limit_value, is_enabled, remark, created_at, updated_at)
SELECT 'POINT', mp.id, alt.id, 500, true, '瞬时功率超过500kW时预警', NOW(), NOW()
FROM ems_meter_point mp, ems_alarm_limit_type alt
WHERE mp.code = 'PT_P_INST' AND alt.limit_code = 'HI_WARN';

INSERT INTO ems_alarm_config (node_type, node_id, limit_type_id, limit_value, is_enabled, remark, created_at, updated_at)
SELECT 'POINT', mp.id, alt.id, 800, true, '瞬时功率超过800kW时报警', NOW(), NOW()
FROM ems_meter_point mp, ems_alarm_limit_type alt
WHERE mp.code = 'PT_P_INST' AND alt.limit_code = 'HI_HI';

-- 功率因数 - 低值预警和低低报警
INSERT INTO ems_alarm_config (node_type, node_id, limit_type_id, limit_value, is_enabled, remark, created_at, updated_at)
SELECT 'POINT', mp.id, alt.id, 0.9, true, '功率因数低于0.9时预警', NOW(), NOW()
FROM ems_meter_point mp, ems_alarm_limit_type alt
WHERE mp.code = 'PT_PF' AND alt.limit_code = 'LO_WARN';

INSERT INTO ems_alarm_config (node_type, node_id, limit_type_id, limit_value, is_enabled, remark, created_at, updated_at)
SELECT 'POINT', mp.id, alt.id, 0.85, true, '功率因数低于0.85时报警', NOW(), NOW()
FROM ems_meter_point mp, ems_alarm_limit_type alt
WHERE mp.code = 'PT_PF' AND alt.limit_code = 'LO_LO';

-- A相电流 - 超限报警
INSERT INTO ems_alarm_config (node_type, node_id, limit_type_id, limit_value, is_enabled, remark, created_at, updated_at)
SELECT 'POINT', mp.id, alt.id, 100, true, 'A相电流超过100A时报警', NOW(), NOW()
FROM ems_meter_point mp, ems_alarm_limit_type alt
WHERE mp.code = 'PT_I_A' AND alt.limit_code = 'OVER_LIMIT';

-- 用水量 - 高值预警
INSERT INTO ems_alarm_config (node_type, node_id, limit_type_id, limit_value, is_enabled, remark, created_at, updated_at)
SELECT 'POINT', mp.id, alt.id, 100, true, '日用水量超过100m³时预警', NOW(), NOW()
FROM ems_meter_point mp, ems_alarm_limit_type alt
WHERE mp.code = 'PT_WATER_TOTAL' AND alt.limit_code = 'HI_WARN';

-- 蒸汽压力 - 高值预警和低值预警
INSERT INTO ems_alarm_config (node_type, node_id, limit_type_id, limit_value, is_enabled, remark, created_at, updated_at)
SELECT 'POINT', mp.id, alt.id, 1.0, true, '蒸汽压力超过1.0MPa时预警', NOW(), NOW()
FROM ems_meter_point mp, ems_alarm_limit_type alt
WHERE mp.code = 'PT_STEAM_PRESS' AND alt.limit_code = 'HI_WARN';

INSERT INTO ems_alarm_config (node_type, node_id, limit_type_id, limit_value, is_enabled, remark, created_at, updated_at)
SELECT 'POINT', mp.id, alt.id, 0.3, true, '蒸汽压力低于0.3MPa时预警', NOW(), NOW()
FROM ems_meter_point mp, ems_alarm_limit_type alt
WHERE mp.code = 'PT_STEAM_PRESS' AND alt.limit_code = 'LO_WARN';

-- =====================================================
-- 3. 报警历史记录 (ems_alarm_record)
-- 字段: config_id, trigger_value, trigger_time, status, handle_time, handle_remark
-- status: 0=未处理, 1=已处理, 2=忽略
-- =====================================================
DELETE FROM ems_alarm_record;

-- 有功电量超限报警（已处理）
INSERT INTO ems_alarm_record (config_id, trigger_value, trigger_time, status, handle_time, handle_remark, created_at, updated_at)
SELECT ac.id, 1650, NOW() - INTERVAL '5 days', 1, NOW() - INTERVAL '5 days' + INTERVAL '30 minutes', '已通知生产部门降低负荷', NOW(), NOW()
FROM ems_alarm_config ac
JOIN ems_meter_point mp ON ac.node_id = mp.id
JOIN ems_alarm_limit_type alt ON ac.limit_type_id = alt.id
WHERE mp.code = 'PT_EP_TOTAL' AND alt.limit_code = 'HI_HI'
LIMIT 1;

-- 功率因数低报警（已处理）
INSERT INTO ems_alarm_record (config_id, trigger_value, trigger_time, status, handle_time, handle_remark, created_at, updated_at)
SELECT ac.id, 0.82, NOW() - INTERVAL '3 days', 1, NOW() - INTERVAL '3 days' + INTERVAL '2 hours', '已调整无功补偿装置', NOW(), NOW()
FROM ems_alarm_config ac
JOIN ems_meter_point mp ON ac.node_id = mp.id
JOIN ems_alarm_limit_type alt ON ac.limit_type_id = alt.id
WHERE mp.code = 'PT_PF' AND alt.limit_code = 'LO_LO'
LIMIT 1;

-- 瞬时功率高预警（已处理）
INSERT INTO ems_alarm_record (config_id, trigger_value, trigger_time, status, handle_time, handle_remark, created_at, updated_at)
SELECT ac.id, 520, NOW() - INTERVAL '2 days', 1, NOW() - INTERVAL '2 days' + INTERVAL '15 minutes', '生产高峰期正常波动，继续观察', NOW(), NOW()
FROM ems_alarm_config ac
JOIN ems_meter_point mp ON ac.node_id = mp.id
JOIN ems_alarm_limit_type alt ON ac.limit_type_id = alt.id
WHERE mp.code = 'PT_P_INST' AND alt.limit_code = 'HI_WARN'
LIMIT 1;

-- 电流超限报警（待处理）
INSERT INTO ems_alarm_record (config_id, trigger_value, trigger_time, status, created_at, updated_at)
SELECT ac.id, 115, NOW() - INTERVAL '1 day', 0, NOW(), NOW()
FROM ems_alarm_config ac
JOIN ems_meter_point mp ON ac.node_id = mp.id
JOIN ems_alarm_limit_type alt ON ac.limit_type_id = alt.id
WHERE mp.code = 'PT_I_A' AND alt.limit_code = 'OVER_LIMIT'
LIMIT 1;

-- 瞬时功率高高报警（待处理）
INSERT INTO ems_alarm_record (config_id, trigger_value, trigger_time, status, created_at, updated_at)
SELECT ac.id, 850, NOW() - INTERVAL '6 hours', 0, NOW(), NOW()
FROM ems_alarm_config ac
JOIN ems_meter_point mp ON ac.node_id = mp.id
JOIN ems_alarm_limit_type alt ON ac.limit_type_id = alt.id
WHERE mp.code = 'PT_P_INST' AND alt.limit_code = 'HI_HI'
LIMIT 1;

-- 用水量高预警（待处理）
INSERT INTO ems_alarm_record (config_id, trigger_value, trigger_time, status, created_at, updated_at)
SELECT ac.id, 125, NOW() - INTERVAL '2 hours', 0, NOW(), NOW()
FROM ems_alarm_config ac
JOIN ems_meter_point mp ON ac.node_id = mp.id
JOIN ems_alarm_limit_type alt ON ac.limit_type_id = alt.id
WHERE mp.code = 'PT_WATER_TOTAL' AND alt.limit_code = 'HI_WARN'
LIMIT 1;

-- 蒸汽压力低预警（已忽略）
INSERT INTO ems_alarm_record (config_id, trigger_value, trigger_time, status, handle_time, handle_remark, created_at, updated_at)
SELECT ac.id, 0.25, NOW() - INTERVAL '4 days', 2, NOW() - INTERVAL '4 days' + INTERVAL '10 minutes', '设备检修期间，正常现象', NOW(), NOW()
FROM ems_alarm_config ac
JOIN ems_meter_point mp ON ac.node_id = mp.id
JOIN ems_alarm_limit_type alt ON ac.limit_type_id = alt.id
WHERE mp.code = 'PT_STEAM_PRESS' AND alt.limit_code = 'LO_WARN'
LIMIT 1;

-- =====================================================
-- 验证数据
-- =====================================================
SELECT '报警限值类型' AS table_name, COUNT(*) AS count FROM ems_alarm_limit_type
UNION ALL
SELECT '预报警配置', COUNT(*) FROM ems_alarm_config
UNION ALL
SELECT '报警历史记录', COUNT(*) FROM ems_alarm_record;
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
-- ===================================================
-- Terra EMS 岗位初始化数据 (PostgreSQL)
-- ===================================================

INSERT INTO sys_post (id, code, name, sort_order, status, remark, created_at, updated_at) VALUES
(1, 'CEO', '董事长', 1, 0, '公司最高负责人', NOW(), NOW()),
(2, 'GM', '总经理', 2, 0, '公司日常运营负责人', NOW(), NOW()),
(3, 'DGM', '副总经理', 3, 0, '协助总经理工作', NOW(), NOW()),
(4, 'HR_M', '人事经理', 4, 0, '人力资源部负责人', NOW(), NOW()),
(5, 'HR', '人事专员', 5, 0, '负责招聘、考勤等', NOW(), NOW()),
(6, 'FIN_M', '财务经理', 6, 0, '财务部负责人', NOW(), NOW()),
(7, 'ACC', '会计', 7, 0, '负责账务处理', NOW(), NOW()),
(8, 'CASH', '出纳', 8, 0, '负责现金收支', NOW(), NOW()),
(9, 'TECH_D', '技术总监', 9, 0, '负责公司技术战略', NOW(), NOW()),
(10, 'DEV_M', '研发经理', 10, 0, '研发团队负责人', NOW(), NOW()),
(11, 'DEV', '研发工程师', 11, 0, '负责系统开发', NOW(), NOW()),
(12, 'TEST_M', '测试经理', 12, 0, '测试团队负责人', NOW(), NOW()),
(13, 'TEST', '测试工程师', 13, 0, '负责系统测试', NOW(), NOW()),
(14, 'OPS_M', '运维经理', 14, 0, '运维团队负责人', NOW(), NOW()),
(15, 'OPS', '运维工程师', 15, 0, '负责系统运维', NOW(), NOW()),
(16, 'MKT_M', '市场经理', 16, 0, '市场部负责人', NOW(), NOW()),
(17, 'SALES', '销售专员', 17, 0, '负责产品销售', NOW(), NOW()),
(18, 'OLD_POST', '废弃岗位', 99, 1, '已停用的岗位示例', NOW(), NOW())
ON CONFLICT (id) DO UPDATE 
SET code = EXCLUDED.code,
    name = EXCLUDED.name,
    sort_order = EXCLUDED.sort_order,
    status = EXCLUDED.status,
    remark = EXCLUDED.remark,
    updated_at = NOW();

-- 重置序列 (可选，防止 ID 冲突)
SELECT setval('sys_post_id_seq', (SELECT MAX(id) FROM sys_post));
-- 电价策略初始化数据
-- Terra EMS v3

-- 清理现有数据
SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE ems_price_policy_item;
TRUNCATE TABLE ems_price_policy;
SET FOREIGN_KEY_CHECKS = 1;

-- ==================== 策略主表 ====================

-- 1. 工业分时电价策略 (2024~2025)
INSERT INTO ems_price_policy (code, name, energy_type_id, is_multi_rate, sort_order, status, effective_start_date, effective_end_date, remark, created_at, updated_at)
SELECT 'INDUSTRY_TOU_2024', '工业分时电价(2024)', id, TRUE, 1, 0, '2024-01-01', '2024-12-31', '适用于一般工商业用电，采用尖峰平谷电价', NOW(), NOW()
FROM ems_energy_type WHERE code = 'ELECTRIC';

-- 2. 工业分时电价策略 (2025开始)
INSERT INTO ems_price_policy (code, name, energy_type_id, is_multi_rate, sort_order, status, effective_start_date, effective_end_date, remark, created_at, updated_at)
SELECT 'INDUSTRY_TOU_2025', '工业分时电价(2025)', id, TRUE, 2, 0, '2025-01-01', NULL, '适用于一般工商业用电，2025年度执行标准', NOW(), NOW()
FROM ems_energy_type WHERE code = 'ELECTRIC';

-- 3. 商业单一电价策略
INSERT INTO ems_price_policy (code, name, energy_type_id, is_multi_rate, sort_order, status, effective_start_date, effective_end_date, remark, created_at, updated_at)
SELECT 'COMMERCIAL_SINGLE', '商业电价', id, FALSE, 3, 0, '2024-01-01', NULL, '适用于小型商业用电，统一计价', NOW(), NOW()
FROM ems_energy_type WHERE code = 'ELECTRIC';


-- ==================== 时段价格明细 ====================

-- 1. 工业分时电价(2024) 明细
INSERT INTO ems_price_policy_item (policy_id, period_type, price, sort_order, start_time, end_time, created_at, updated_at)
SELECT p.id, 'SHARP', 1.2500, 1, '09:00', '11:00', NOW(), NOW() FROM ems_price_policy p WHERE p.code = 'INDUSTRY_TOU_2024';
INSERT INTO ems_price_policy_item (policy_id, period_type, price, sort_order, start_time, end_time, created_at, updated_at)
SELECT p.id, 'PEAK', 0.9500, 2, '11:00', '13:00', NOW(), NOW() FROM ems_price_policy p WHERE p.code = 'INDUSTRY_TOU_2024';
INSERT INTO ems_price_policy_item (policy_id, period_type, price, sort_order, start_time, end_time, created_at, updated_at)
SELECT p.id, 'FLAT', 0.6500, 3, '13:00', '17:00', NOW(), NOW() FROM ems_price_policy p WHERE p.code = 'INDUSTRY_TOU_2024';
INSERT INTO ems_price_policy_item (policy_id, period_type, price, sort_order, start_time, end_time, created_at, updated_at)
SELECT p.id, 'VALLEY', 0.3500, 4, '17:00', '22:00', NOW(), NOW() FROM ems_price_policy p WHERE p.code = 'INDUSTRY_TOU_2024';
INSERT INTO ems_price_policy_item (policy_id, period_type, price, sort_order, start_time, end_time, created_at, updated_at)
SELECT p.id, 'DEEP', 0.2000, 5, '22:00', '09:00', NOW(), NOW() FROM ems_price_policy p WHERE p.code = 'INDUSTRY_TOU_2024';

-- 2. 工业分时电价(2025) 明细 (略微调高)
INSERT INTO ems_price_policy_item (policy_id, period_type, price, sort_order, start_time, end_time, created_at, updated_at)
SELECT p.id, 'SHARP', 1.3000, 1, '09:00', '11:00', NOW(), NOW() FROM ems_price_policy p WHERE p.code = 'INDUSTRY_TOU_2025';
INSERT INTO ems_price_policy_item (policy_id, period_type, price, sort_order, start_time, end_time, created_at, updated_at)
SELECT p.id, 'PEAK', 1.0000, 2, '11:00', '13:00', NOW(), NOW() FROM ems_price_policy p WHERE p.code = 'INDUSTRY_TOU_2025';
INSERT INTO ems_price_policy_item (policy_id, period_type, price, sort_order, start_time, end_time, created_at, updated_at)
SELECT p.id, 'FLAT', 0.7000, 3, '13:00', '17:00', NOW(), NOW() FROM ems_price_policy p WHERE p.code = 'INDUSTRY_TOU_2025';
INSERT INTO ems_price_policy_item (policy_id, period_type, price, sort_order, start_time, end_time, created_at, updated_at)
SELECT p.id, 'VALLEY', 0.4000, 4, '17:00', '22:00', NOW(), NOW() FROM ems_price_policy p WHERE p.code = 'INDUSTRY_TOU_2025';
INSERT INTO ems_price_policy_item (policy_id, period_type, price, sort_order, start_time, end_time, created_at, updated_at)
SELECT p.id, 'DEEP', 0.2500, 5, '22:00', '09:00', NOW(), NOW() FROM ems_price_policy p WHERE p.code = 'INDUSTRY_TOU_2025';

-- 3. 商业电价 明细 (单一电价)
INSERT INTO ems_price_policy_item (policy_id, period_type, price, sort_order, start_time, end_time, created_at, updated_at)
SELECT p.id, 'FLAT', 0.8200, 1, '00:00', '23:59', NOW(), NOW() FROM ems_price_policy p WHERE p.code = 'COMMERCIAL_SINGLE';
-- 添加产品类型字典
INSERT INTO `sys_dict_type` (`dict_name`, `dict_type`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) 
VALUES ('产品类型', 'product_type', '0', 'admin', NOW(), '', NULL, '产品物料类型分类');

-- 添加产品类型字典数据
INSERT INTO `sys_dict_data` (`dict_sort`, `dict_label`, `dict_value`, `dict_type`, `css_class`, `list_class`, `is_default`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) 
VALUES 
(1, '成品', '1', 'product_type', '', 'primary', 'Y', '0', 'admin', NOW(), '', NULL, ''),
(2, '半成品', '2', 'product_type', '', 'info', 'N', '0', 'admin', NOW(), '', NULL, ''),
(3, '原材料', '3', 'product_type', '', 'warning', 'N', '0', 'admin', NOW(), '', NULL, '');
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
-- Terra EMS 能耗统计模拟数据
-- 生成于 2026-01-05
-- 使用实际数据库中的ID

-- =====================================================
-- 能源类型ID对照表（来自实际数据库）:
-- 3  = LNG (液态天然气)
-- 7  = STEAM (蒸汽)
-- 9  = WATER (自来水)
-- 14 = ELECTRIC (电力)
-- =====================================================

-- =====================================================
-- 清理旧的模拟数据（可选）
-- =====================================================
-- DELETE FROM ems_energy_data;

-- =====================================================
-- 2024年月度电力数据
-- meter_point_id 使用子查询，energy_type_id = 14 (ELECTRIC)
-- 模拟工厂用电，夏季用电高峰
-- =====================================================
INSERT INTO ems_energy_data (meter_point_id, energy_type_id, data_time, time_type, value)
SELECT 
    (SELECT id FROM ems_meter_point WHERE code = 'PT_EP_TOTAL' LIMIT 1),
    14, data_time, time_type, value
FROM (VALUES
    ('2024-01-01 00:00:00'::timestamp, 'MONTH', 45680.25),
    ('2024-02-01 00:00:00'::timestamp, 'MONTH', 42150.80),
    ('2024-03-01 00:00:00'::timestamp, 'MONTH', 48320.15),
    ('2024-04-01 00:00:00'::timestamp, 'MONTH', 51240.50),
    ('2024-05-01 00:00:00'::timestamp, 'MONTH', 58760.35),
    ('2024-06-01 00:00:00'::timestamp, 'MONTH', 72450.90),
    ('2024-07-01 00:00:00'::timestamp, 'MONTH', 85320.45),
    ('2024-08-01 00:00:00'::timestamp, 'MONTH', 82150.70),
    ('2024-09-01 00:00:00'::timestamp, 'MONTH', 68450.25),
    ('2024-10-01 00:00:00'::timestamp, 'MONTH', 54230.80),
    ('2024-11-01 00:00:00'::timestamp, 'MONTH', 49870.15),
    ('2024-12-01 00:00:00'::timestamp, 'MONTH', 52340.60)
) AS t(data_time, time_type, value);

-- =====================================================
-- 2025年月度电力数据
-- 比2024年有约8%的增长
-- =====================================================
INSERT INTO ems_energy_data (meter_point_id, energy_type_id, data_time, time_type, value)
SELECT 
    (SELECT id FROM ems_meter_point WHERE code = 'PT_EP_TOTAL' LIMIT 1),
    14, data_time, time_type, value
FROM (VALUES
    ('2025-01-01 00:00:00'::timestamp, 'MONTH', 49334.67),
    ('2025-02-01 00:00:00'::timestamp, 'MONTH', 45522.86),
    ('2025-03-01 00:00:00'::timestamp, 'MONTH', 52185.76),
    ('2025-04-01 00:00:00'::timestamp, 'MONTH', 55339.74),
    ('2025-05-01 00:00:00'::timestamp, 'MONTH', 63461.18),
    ('2025-06-01 00:00:00'::timestamp, 'MONTH', 78246.97),
    ('2025-07-01 00:00:00'::timestamp, 'MONTH', 92146.09),
    ('2025-08-01 00:00:00'::timestamp, 'MONTH', 88722.76),
    ('2025-09-01 00:00:00'::timestamp, 'MONTH', 73926.27),
    ('2025-10-01 00:00:00'::timestamp, 'MONTH', 58569.26),
    ('2025-11-01 00:00:00'::timestamp, 'MONTH', 53859.76),
    ('2025-12-01 00:00:00'::timestamp, 'MONTH', 56527.85)
) AS t(data_time, time_type, value);

-- =====================================================
-- 2025年12月每日电力数据
-- =====================================================
INSERT INTO ems_energy_data (meter_point_id, energy_type_id, data_time, time_type, value)
SELECT 
    (SELECT id FROM ems_meter_point WHERE code = 'PT_EP_TOTAL' LIMIT 1),
    14, data_time, time_type, value
FROM (VALUES
    ('2025-12-01 00:00:00'::timestamp, 'DAY', 1785.25),
    ('2025-12-02 00:00:00'::timestamp, 'DAY', 1823.40),
    ('2025-12-03 00:00:00'::timestamp, 'DAY', 1798.65),
    ('2025-12-04 00:00:00'::timestamp, 'DAY', 1856.30),
    ('2025-12-05 00:00:00'::timestamp, 'DAY', 1912.15),
    ('2025-12-06 00:00:00'::timestamp, 'DAY', 1245.80),
    ('2025-12-07 00:00:00'::timestamp, 'DAY', 1189.55),
    ('2025-12-08 00:00:00'::timestamp, 'DAY', 1802.45),
    ('2025-12-09 00:00:00'::timestamp, 'DAY', 1834.70),
    ('2025-12-10 00:00:00'::timestamp, 'DAY', 1867.95),
    ('2025-12-11 00:00:00'::timestamp, 'DAY', 1889.20),
    ('2025-12-12 00:00:00'::timestamp, 'DAY', 1923.45),
    ('2025-12-13 00:00:00'::timestamp, 'DAY', 1278.90),
    ('2025-12-14 00:00:00'::timestamp, 'DAY', 1156.35),
    ('2025-12-15 00:00:00'::timestamp, 'DAY', 1845.60),
    ('2025-12-16 00:00:00'::timestamp, 'DAY', 1878.85),
    ('2025-12-17 00:00:00'::timestamp, 'DAY', 1934.10),
    ('2025-12-18 00:00:00'::timestamp, 'DAY', 1956.35),
    ('2025-12-19 00:00:00'::timestamp, 'DAY', 1989.60),
    ('2025-12-20 00:00:00'::timestamp, 'DAY', 1312.85),
    ('2025-12-21 00:00:00'::timestamp, 'DAY', 1198.10),
    ('2025-12-22 00:00:00'::timestamp, 'DAY', 1867.35),
    ('2025-12-23 00:00:00'::timestamp, 'DAY', 1898.60),
    ('2025-12-24 00:00:00'::timestamp, 'DAY', 1756.85),
    ('2025-12-25 00:00:00'::timestamp, 'DAY', 1423.10),
    ('2025-12-26 00:00:00'::timestamp, 'DAY', 1834.35),
    ('2025-12-27 00:00:00'::timestamp, 'DAY', 1289.60),
    ('2025-12-28 00:00:00'::timestamp, 'DAY', 1178.85),
    ('2025-12-29 00:00:00'::timestamp, 'DAY', 1812.10),
    ('2025-12-30 00:00:00'::timestamp, 'DAY', 1845.35),
    ('2025-12-31 00:00:00'::timestamp, 'DAY', 1667.80)
) AS t(data_time, time_type, value);

-- =====================================================
-- 2026年1月每日电力数据
-- =====================================================
INSERT INTO ems_energy_data (meter_point_id, energy_type_id, data_time, time_type, value)
SELECT 
    (SELECT id FROM ems_meter_point WHERE code = 'PT_EP_TOTAL' LIMIT 1),
    14, data_time, time_type, value
FROM (VALUES
    ('2026-01-01 00:00:00'::timestamp, 'DAY', 1456.20),
    ('2026-01-02 00:00:00'::timestamp, 'DAY', 1789.45),
    ('2026-01-03 00:00:00'::timestamp, 'DAY', 1834.70),
    ('2026-01-04 00:00:00'::timestamp, 'DAY', 1267.95),
    ('2026-01-05 00:00:00'::timestamp, 'DAY', 1198.20)
) AS t(data_time, time_type, value);

-- =====================================================
-- 年度汇总数据 - 电力 (energy_type_id = 14)
-- =====================================================
INSERT INTO ems_energy_data (meter_point_id, energy_type_id, data_time, time_type, value)
SELECT 
    (SELECT id FROM ems_meter_point WHERE code = 'PT_EP_TOTAL' LIMIT 1),
    14, data_time, time_type, value
FROM (VALUES
    ('2022-01-01 00:00:00'::timestamp, 'YEAR', 612450.50),
    ('2023-01-01 00:00:00'::timestamp, 'YEAR', 658920.75),
    ('2024-01-01 00:00:00'::timestamp, 'YEAR', 710962.90),
    ('2025-01-01 00:00:00'::timestamp, 'YEAR', 767843.17)
) AS t(data_time, time_type, value);

-- =====================================================
-- 水用量数据 (energy_type_id = 9 = WATER)
-- =====================================================
INSERT INTO ems_energy_data (meter_point_id, energy_type_id, data_time, time_type, value)
SELECT 
    (SELECT id FROM ems_meter_point WHERE code = 'PT_WATER_TOTAL' LIMIT 1),
    9, data_time, time_type, value
FROM (VALUES
    ('2024-01-01 00:00:00'::timestamp, 'MONTH', 1256.80),
    ('2024-02-01 00:00:00'::timestamp, 'MONTH', 1178.45),
    ('2024-03-01 00:00:00'::timestamp, 'MONTH', 1342.20),
    ('2024-04-01 00:00:00'::timestamp, 'MONTH', 1456.95),
    ('2024-05-01 00:00:00'::timestamp, 'MONTH', 1623.70),
    ('2024-06-01 00:00:00'::timestamp, 'MONTH', 1845.45),
    ('2024-07-01 00:00:00'::timestamp, 'MONTH', 2134.20),
    ('2024-08-01 00:00:00'::timestamp, 'MONTH', 2078.95),
    ('2024-09-01 00:00:00'::timestamp, 'MONTH', 1789.70),
    ('2024-10-01 00:00:00'::timestamp, 'MONTH', 1534.45),
    ('2024-11-01 00:00:00'::timestamp, 'MONTH', 1345.20),
    ('2024-12-01 00:00:00'::timestamp, 'MONTH', 1289.95),
    ('2025-01-01 00:00:00'::timestamp, 'MONTH', 1319.64),
    ('2025-02-01 00:00:00'::timestamp, 'MONTH', 1237.37),
    ('2025-03-01 00:00:00'::timestamp, 'MONTH', 1409.31),
    ('2025-04-01 00:00:00'::timestamp, 'MONTH', 1529.80),
    ('2025-05-01 00:00:00'::timestamp, 'MONTH', 1704.89),
    ('2025-06-01 00:00:00'::timestamp, 'MONTH', 1937.72),
    ('2025-07-01 00:00:00'::timestamp, 'MONTH', 2240.91),
    ('2025-08-01 00:00:00'::timestamp, 'MONTH', 2182.90),
    ('2025-09-01 00:00:00'::timestamp, 'MONTH', 1879.19),
    ('2025-10-01 00:00:00'::timestamp, 'MONTH', 1611.17),
    ('2025-11-01 00:00:00'::timestamp, 'MONTH', 1412.46),
    ('2025-12-01 00:00:00'::timestamp, 'MONTH', 1354.45)
) AS t(data_time, time_type, value);

-- =====================================================
-- LNG液态天然气用量数据 (energy_type_id = 3)
-- 冬季用量高（采暖），夏季用量低
-- =====================================================
INSERT INTO ems_energy_data (meter_point_id, energy_type_id, data_time, time_type, value)
SELECT 
    (SELECT id FROM ems_meter_point WHERE code = 'PT_GAS_TOTAL' LIMIT 1),
    3, data_time, time_type, value
FROM (VALUES
    ('2024-01-01 00:00:00'::timestamp, 'MONTH', 8956.30),
    ('2024-02-01 00:00:00'::timestamp, 'MONTH', 8234.85),
    ('2024-03-01 00:00:00'::timestamp, 'MONTH', 5678.40),
    ('2024-04-01 00:00:00'::timestamp, 'MONTH', 2345.95),
    ('2024-05-01 00:00:00'::timestamp, 'MONTH', 1234.50),
    ('2024-06-01 00:00:00'::timestamp, 'MONTH', 856.05),
    ('2024-07-01 00:00:00'::timestamp, 'MONTH', 678.60),
    ('2024-08-01 00:00:00'::timestamp, 'MONTH', 712.15),
    ('2024-09-01 00:00:00'::timestamp, 'MONTH', 1456.70),
    ('2024-10-01 00:00:00'::timestamp, 'MONTH', 3567.25),
    ('2024-11-01 00:00:00'::timestamp, 'MONTH', 6789.80),
    ('2024-12-01 00:00:00'::timestamp, 'MONTH', 8123.35),
    ('2025-01-01 00:00:00'::timestamp, 'MONTH', 9404.12),
    ('2025-02-01 00:00:00'::timestamp, 'MONTH', 8646.59),
    ('2025-03-01 00:00:00'::timestamp, 'MONTH', 5962.32),
    ('2025-04-01 00:00:00'::timestamp, 'MONTH', 2463.25),
    ('2025-05-01 00:00:00'::timestamp, 'MONTH', 1296.23),
    ('2025-06-01 00:00:00'::timestamp, 'MONTH', 898.85),
    ('2025-07-01 00:00:00'::timestamp, 'MONTH', 712.53),
    ('2025-08-01 00:00:00'::timestamp, 'MONTH', 747.76),
    ('2025-09-01 00:00:00'::timestamp, 'MONTH', 1529.54),
    ('2025-10-01 00:00:00'::timestamp, 'MONTH', 3745.61),
    ('2025-11-01 00:00:00'::timestamp, 'MONTH', 7129.29),
    ('2025-12-01 00:00:00'::timestamp, 'MONTH', 8529.52)
) AS t(data_time, time_type, value);

-- =====================================================
-- 蒸汽用量数据 (energy_type_id = 7 = STEAM)
-- =====================================================
INSERT INTO ems_energy_data (meter_point_id, energy_type_id, data_time, time_type, value)
SELECT 
    (SELECT id FROM ems_meter_point WHERE code = 'PT_STEAM_TOTAL' LIMIT 1),
    7, data_time, time_type, value
FROM (VALUES
    ('2024-01-01 00:00:00'::timestamp, 'MONTH', 2456.30),
    ('2024-02-01 00:00:00'::timestamp, 'MONTH', 2234.85),
    ('2024-03-01 00:00:00'::timestamp, 'MONTH', 2078.40),
    ('2024-04-01 00:00:00'::timestamp, 'MONTH', 1845.95),
    ('2024-05-01 00:00:00'::timestamp, 'MONTH', 1634.50),
    ('2024-06-01 00:00:00'::timestamp, 'MONTH', 1456.05),
    ('2024-07-01 00:00:00'::timestamp, 'MONTH', 1278.60),
    ('2024-08-01 00:00:00'::timestamp, 'MONTH', 1312.15),
    ('2024-09-01 00:00:00'::timestamp, 'MONTH', 1556.70),
    ('2024-10-01 00:00:00'::timestamp, 'MONTH', 1867.25),
    ('2024-11-01 00:00:00'::timestamp, 'MONTH', 2189.80),
    ('2024-12-01 00:00:00'::timestamp, 'MONTH', 2423.35),
    ('2025-01-01 00:00:00'::timestamp, 'MONTH', 2579.12),
    ('2025-02-01 00:00:00'::timestamp, 'MONTH', 2346.59),
    ('2025-03-01 00:00:00'::timestamp, 'MONTH', 2182.32),
    ('2025-04-01 00:00:00'::timestamp, 'MONTH', 1938.25),
    ('2025-05-01 00:00:00'::timestamp, 'MONTH', 1716.23),
    ('2025-06-01 00:00:00'::timestamp, 'MONTH', 1528.85),
    ('2025-07-01 00:00:00'::timestamp, 'MONTH', 1342.53),
    ('2025-08-01 00:00:00'::timestamp, 'MONTH', 1377.76),
    ('2025-09-01 00:00:00'::timestamp, 'MONTH', 1634.54),
    ('2025-10-01 00:00:00'::timestamp, 'MONTH', 1960.61),
    ('2025-11-01 00:00:00'::timestamp, 'MONTH', 2299.29),
    ('2025-12-01 00:00:00'::timestamp, 'MONTH', 2544.52)
) AS t(data_time, time_type, value);

-- =====================================================
-- 验证插入的数据
-- =====================================================
SELECT 
    mp.name AS meter_point_name,
    et.name AS energy_type_name,
    COUNT(*) AS record_count,
    MIN(ed.data_time) AS min_date,
    MAX(ed.data_time) AS max_date
FROM ems_energy_data ed
JOIN ems_meter_point mp ON ed.meter_point_id = mp.id
JOIN ems_energy_type et ON ed.energy_type_id = et.id
GROUP BY mp.name, et.name
ORDER BY mp.name, et.name;
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
