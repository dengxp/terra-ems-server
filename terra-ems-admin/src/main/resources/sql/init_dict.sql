-- ===================================================
-- Terra EMS 字典初始化数据
-- 从 zhitan-ems 迁移并按 terra 命名规范调整
-- ===================================================

-- ===================================================
-- 1. 系统基础字典类型
-- ===================================================
INSERT INTO sys_dict_type (id, name, type, status, create_time, remark) VALUES
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
(43, '阶段数据录入时间', 'ENTRY_DATA_TIME', '0', NOW(), '阶段数据录入时间类型');

-- ===================================================
-- 2. 系统基础字典数据
-- ===================================================

-- SYS_USER_SEX 用户性别
INSERT INTO sys_dict_data (sort_order, label, value, type_code, tag_type, is_default, status, create_time) VALUES
(1, '男', '0', 'SYS_USER_SEX', 'processing', 'Y', '0', NOW()),
(2, '女', '1', 'SYS_USER_SEX', 'success', 'N', '0', NOW()),
(3, '未知', '2', 'SYS_USER_SEX', 'default', 'N', '0', NOW());

-- SYS_SHOW_HIDE 菜单状态
INSERT INTO sys_dict_data (sort_order, label, value, type_code, tag_type, is_default, status, create_time) VALUES
(1, '显示', '0', 'SYS_SHOW_HIDE', 'processing', 'Y', '0', NOW()),
(2, '隐藏', '1', 'SYS_SHOW_HIDE', 'error', 'N', '0', NOW());

-- SYS_NORMAL_DISABLE 系统开关
INSERT INTO sys_dict_data (sort_order, label, value, type_code, tag_type, is_default, status, create_time) VALUES
(1, '正常', '0', 'SYS_NORMAL_DISABLE', 'processing', 'Y', '0', NOW()),
(2, '停用', '1', 'SYS_NORMAL_DISABLE', 'error', 'N', '0', NOW());

-- SYS_JOB_STATUS 任务状态
INSERT INTO sys_dict_data (sort_order, label, value, type_code, tag_type, is_default, status, create_time) VALUES
(1, '正常', '0', 'SYS_JOB_STATUS', 'processing', 'Y', '0', NOW()),
(2, '暂停', '1', 'SYS_JOB_STATUS', 'error', 'N', '0', NOW());

-- SYS_JOB_GROUP 任务分组
INSERT INTO sys_dict_data (sort_order, label, value, type_code, tag_type, is_default, status, create_time) VALUES
(1, '默认', 'DEFAULT', 'SYS_JOB_GROUP', 'default', 'Y', '0', NOW()),
(2, '系统', 'SYSTEM', 'SYS_JOB_GROUP', 'processing', 'N', '0', NOW());

-- SYS_YES_NO 系统是否
INSERT INTO sys_dict_data (sort_order, label, value, type_code, tag_type, is_default, status, create_time) VALUES
(1, '是', 'Y', 'SYS_YES_NO', 'processing', 'Y', '0', NOW()),
(2, '否', 'N', 'SYS_YES_NO', 'error', 'N', '0', NOW());

-- SYS_NOTICE_TYPE 通知类型
INSERT INTO sys_dict_data (sort_order, label, value, type_code, tag_type, is_default, status, create_time) VALUES
(1, '通知', '1', 'SYS_NOTICE_TYPE', 'warning', 'Y', '0', NOW()),
(2, '公告', '2', 'SYS_NOTICE_TYPE', 'success', 'N', '0', NOW());

-- SYS_NOTICE_STATUS 通知状态
INSERT INTO sys_dict_data (sort_order, label, value, type_code, tag_type, is_default, status, create_time) VALUES
(1, '正常', '0', 'SYS_NOTICE_STATUS', 'processing', 'Y', '0', NOW()),
(2, '关闭', '1', 'SYS_NOTICE_STATUS', 'error', 'N', '0', NOW());

-- SYS_OPER_TYPE 操作类型
INSERT INTO sys_dict_data (sort_order, label, value, type_code, tag_type, is_default, status, create_time) VALUES
(1, '新增', '1', 'SYS_OPER_TYPE', 'success', 'N', '0', NOW()),
(2, '修改', '2', 'SYS_OPER_TYPE', 'processing', 'N', '0', NOW()),
(3, '删除', '3', 'SYS_OPER_TYPE', 'error', 'N', '0', NOW()),
(4, '授权', '4', 'SYS_OPER_TYPE', 'processing', 'N', '0', NOW()),
(5, '导出', '5', 'SYS_OPER_TYPE', 'warning', 'N', '0', NOW()),
(6, '导入', '6', 'SYS_OPER_TYPE', 'warning', 'N', '0', NOW()),
(7, '强退', '7', 'SYS_OPER_TYPE', 'error', 'N', '0', NOW()),
(8, '生成代码', '8', 'SYS_OPER_TYPE', 'warning', 'N', '0', NOW()),
(9, '清空数据', '9', 'SYS_OPER_TYPE', 'error', 'N', '0', NOW());

-- SYS_COMMON_STATUS 系统状态
INSERT INTO sys_dict_data (sort_order, label, value, type_code, tag_type, is_default, status, create_time) VALUES
(1, '成功', '0', 'SYS_COMMON_STATUS', 'success', 'Y', '0', NOW()),
(2, '失败', '1', 'SYS_COMMON_STATUS', 'error', 'N', '0', NOW());

-- SYS_IS_DEFAULT 是否默认
INSERT INTO sys_dict_data (sort_order, label, value, type_code, tag_type, is_default, status, create_time) VALUES
(1, '是', 'Y', 'SYS_IS_DEFAULT', 'processing', 'Y', '0', NOW()),
(2, '否', 'N', 'SYS_IS_DEFAULT', 'default', 'N', '0', NOW());

-- SYS_IS_ENABLE 是否启用
INSERT INTO sys_dict_data (sort_order, label, value, type_code, tag_type, is_default, status, create_time) VALUES
(1, '启用', 'Y', 'SYS_IS_ENABLE', 'success', 'Y', '0', NOW()),
(2, '停用', 'N', 'SYS_IS_ENABLE', 'error', 'N', '0', NOW());

-- ===================================================
-- 3. 单位管理 SYS_UNIT
-- ===================================================
INSERT INTO sys_dict_data (sort_order, label, value, type_code, tag_type, is_default, status, create_time) VALUES
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
(31, '千克标准煤/吨', 'kgce/t', 'SYS_UNIT', 'default', 'N', '0', NOW());

-- ===================================================
-- 4. 尖峰平谷配置 ELECTRICITY_PRICE
-- ===================================================
INSERT INTO sys_dict_data (sort_order, label, value, type_code, tag_type, is_default, status, create_time) VALUES
(1, '尖', 'SHARP', 'ELECTRICITY_PRICE', 'error', 'N', '0', NOW()),
(2, '峰', 'PEAK', 'ELECTRICITY_PRICE', 'warning', 'N', '0', NOW()),
(3, '平', 'FLAT', 'ELECTRICITY_PRICE', 'success', 'N', '0', NOW()),
(4, '谷', 'VALLEY', 'ELECTRICITY_PRICE', 'default', 'N', '0', NOW()),
(5, '深谷', 'DEEP', 'ELECTRICITY_PRICE', 'processing', 'N', '0', NOW());

-- ===================================================
-- 5. 指标类型 INDEX_CATEGORY
-- ===================================================
INSERT INTO sys_dict_data (sort_order, label, value, type_code, tag_type, is_default, status, create_time) VALUES
(1, '能源类指标', '1', 'INDEX_CATEGORY', 'processing', 'Y', '0', NOW()),
(2, '产品类指标', '2', 'INDEX_CATEGORY', 'success', 'N', '0', NOW()),
(3, '能效类指标', '3', 'INDEX_CATEGORY', 'warning', 'N', '0', NOW()),
(4, '经营类指标', '4', 'INDEX_CATEGORY', 'default', 'N', '0', NOW()),
(5, '其他', '5', 'INDEX_CATEGORY', 'default', 'N', '0', NOW());

-- ===================================================
-- 6. 计量器具类型 DEVICE_TYPE
-- ===================================================
INSERT INTO sys_dict_data (sort_order, label, value, type_code, tag_type, is_default, status, create_time) VALUES
(1, '电表', '1', 'DEVICE_TYPE', 'processing', 'Y', '0', NOW()),
(2, '水表', '2', 'DEVICE_TYPE', 'processing', 'N', '0', NOW()),
(3, '变频器', '3', 'DEVICE_TYPE', 'default', 'N', '0', NOW()),
(4, '温湿度表', '4', 'DEVICE_TYPE', 'default', 'N', '0', NOW()),
(5, '压力表', '5', 'DEVICE_TYPE', 'default', 'N', '0', NOW()),
(6, '蒸汽表', '6', 'DEVICE_TYPE', 'default', 'N', '0', NOW()),
(7, '氮气表', '7', 'DEVICE_TYPE', 'default', 'N', '0', NOW()),
(10, '天然气表', '10', 'DEVICE_TYPE', 'default', 'N', '0', NOW());

-- ===================================================
-- 7. 计量器具状态 METER_STATUS
-- ===================================================
INSERT INTO sys_dict_data (sort_order, label, value, type_code, tag_type, is_default, status, create_time) VALUES
(1, '使用中', '1', 'METER_STATUS', 'success', 'N', '0', NOW()),
(2, '检修中', '2', 'METER_STATUS', 'warning', 'N', '0', NOW()),
(3, '备用', '3', 'METER_STATUS', 'default', 'Y', '0', NOW());

-- ===================================================
-- 8. 参数数据类型 DATA_TYPE
-- ===================================================
INSERT INTO sys_dict_data (sort_order, label, value, type_code, tag_type, is_default, status, create_time) VALUES
(1, '实时数据', '1', 'DATA_TYPE', 'processing', 'N', '0', NOW()),
(2, '阶段数据', '2', 'DATA_TYPE', 'success', 'N', '0', NOW());

-- ===================================================
-- 9. 指标节点类型 NODE_CATEGORY
-- ===================================================
INSERT INTO sys_dict_data (sort_order, label, value, type_code, tag_type, is_default, status, create_time) VALUES
(0, '公司', '0', 'NODE_CATEGORY', 'processing', 'Y', '0', NOW()),
(1, '厂部', '1', 'NODE_CATEGORY', 'processing', 'N', '0', NOW()),
(2, '配电室', '2', 'NODE_CATEGORY', 'default', 'N', '0', NOW()),
(3, '区域', '3', 'NODE_CATEGORY', 'default', 'N', '0', NOW()),
(4, '重点能耗设备', '4', 'NODE_CATEGORY', 'warning', 'N', '0', NOW()),
(5, '设备', '5', 'NODE_CATEGORY', 'default', 'N', '0', NOW()),
(9, '其他', '9', 'NODE_CATEGORY', 'default', 'N', '0', NOW());

-- ===================================================
-- 10. 能耗等级 FACILITY_GRADE
-- ===================================================
INSERT INTO sys_dict_data (sort_order, label, value, type_code, tag_type, is_default, status, create_time) VALUES
(1, '一级', '1', 'FACILITY_GRADE', 'success', 'Y', '0', NOW()),
(2, '二级', '2', 'FACILITY_GRADE', 'processing', 'N', '0', NOW()),
(3, '三级', '3', 'FACILITY_GRADE', 'warning', 'N', '0', NOW()),
(4, '无', '4', 'FACILITY_GRADE', 'default', 'N', '0', NOW());

-- ===================================================
-- 11. 设备类型 FACILITY_TYPE
-- ===================================================
INSERT INTO sys_dict_data (sort_order, label, value, type_code, tag_type, is_default, status, create_time) VALUES
(1, '空压机', '0', 'FACILITY_TYPE', 'default', 'Y', '0', NOW()),
(2, '电机', '1', 'FACILITY_TYPE', 'default', 'N', '0', NOW()),
(3, '风机', '2', 'FACILITY_TYPE', 'default', 'N', '0', NOW()),
(4, '水泵', '3', 'FACILITY_TYPE', 'default', 'N', '0', NOW()),
(5, '变压器', '4', 'FACILITY_TYPE', 'default', 'N', '0', NOW()),
(6, '制冷机', '5', 'FACILITY_TYPE', 'default', 'N', '0', NOW()),
(7, '锅炉', '6', 'FACILITY_TYPE', 'default', 'N', '0', NOW()),
(8, '输送机', '7', 'FACILITY_TYPE', 'default', 'N', '0', NOW()),
(9, '破碎机', '8', 'FACILITY_TYPE', 'default', 'N', '0', NOW()),
(10, '其他', '99', 'FACILITY_TYPE', 'default', 'N', '0', NOW());

-- ===================================================
-- 12. 应用模型类型 MODEL_TYPE
-- ===================================================
INSERT INTO sys_dict_data (sort_order, label, value, type_code, tag_type, is_default, status, create_time) VALUES
(1, '指标配置', 'index', 'MODEL_TYPE', 'processing', 'Y', '0', NOW()),
(2, '仅节点', 'node', 'MODEL_TYPE', 'default', 'N', '0', NOW());

-- ===================================================
-- 13. 预警报警时段 WARN_TIME_SLOT
-- ===================================================
INSERT INTO sys_dict_data (sort_order, label, value, type_code, tag_type, is_default, status, create_time) VALUES
(0, '实时', 'LIVE', 'WARN_TIME_SLOT', 'processing', 'Y', '0', NOW()),
(1, '小时', 'HOUR', 'WARN_TIME_SLOT', 'default', 'N', '0', NOW()),
(2, '天', 'DAY', 'WARN_TIME_SLOT', 'default', 'N', '0', NOW()),
(3, '月', 'MONTH', 'WARN_TIME_SLOT', 'default', 'N', '0', NOW()),
(4, '年', 'YEAR', 'WARN_TIME_SLOT', 'default', 'N', '0', NOW());

-- ===================================================
-- 14. 预警报警类型 ALARM_TYPE
-- ===================================================
INSERT INTO sys_dict_data (sort_order, label, value, type_code, tag_type, is_default, status, create_time) VALUES
(1, '预警', 'WARNING', 'ALARM_TYPE', 'warning', 'Y', '0', NOW()),
(2, '报警', 'ALARM', 'ALARM_TYPE', 'error', 'N', '0', NOW());

-- ===================================================
-- 15. 报警级别 ALARM_LEVEL
-- ===================================================
INSERT INTO sys_dict_data (sort_order, label, value, type_code, tag_type, is_default, status, create_time) VALUES
(1, '高', '1', 'ALARM_LEVEL', 'error', 'N', '0', NOW()),
(2, '中', '2', 'ALARM_LEVEL', 'warning', 'N', '0', NOW()),
(3, '低', '3', 'ALARM_LEVEL', 'default', 'Y', '0', NOW());

-- ===================================================
-- 16. 预报警限值类型 LIMIT_TYPE
-- ===================================================
INSERT INTO sys_dict_data (sort_order, label, value, type_code, tag_type, is_default, status, create_time) VALUES
(1, '上限', '1', 'LIMIT_TYPE', 'warning', 'N', '0', NOW()),
(2, '下限', '2', 'LIMIT_TYPE', 'processing', 'N', '0', NOW());

-- ===================================================
-- 17. 统计时间 TIME_TYPE
-- ===================================================
INSERT INTO sys_dict_data (sort_order, label, value, type_code, tag_type, is_default, status, create_time) VALUES
(1, '月', 'MONTH', 'TIME_TYPE', 'default', 'Y', '0', NOW()),
(2, '年', 'YEAR', 'TIME_TYPE', 'default', 'N', '0', NOW());

-- ===================================================
-- 18. 用能统计时间 DATE_TYPE
-- ===================================================
INSERT INTO sys_dict_data (sort_order, label, value, type_code, tag_type, is_default, status, create_time) VALUES
(1, '日', 'DAY', 'DATE_TYPE', 'default', 'N', '0', NOW()),
(2, '月', 'MONTH', 'DATE_TYPE', 'default', 'Y', '0', NOW()),
(3, '年', 'YEAR', 'DATE_TYPE', 'default', 'N', '0', NOW());

-- ===================================================
-- 19. 期间 PERIOD
-- ===================================================
INSERT INTO sys_dict_data (sort_order, label, value, type_code, tag_type, is_default, status, create_time) VALUES
(1, '天', 'DAY', 'PERIOD', 'default', 'N', '0', NOW()),
(2, '月', 'MONTH', 'PERIOD', 'default', 'Y', '0', NOW()),
(3, '年', 'YEAR', 'PERIOD', 'default', 'N', '0', NOW());

-- ===================================================
-- 20. 阶段数据录入时间 ENTRY_DATA_TIME
-- ===================================================
INSERT INTO sys_dict_data (sort_order, label, value, type_code, tag_type, is_default, status, create_time) VALUES
(1, '小时', 'HOUR', 'ENTRY_DATA_TIME', 'default', 'Y', '0', NOW()),
(2, '日', 'DAY', 'ENTRY_DATA_TIME', 'default', 'N', '0', NOW()),
(3, '月', 'MONTH', 'ENTRY_DATA_TIME', 'default', 'N', '0', NOW()),
(4, '年', 'YEAR', 'ENTRY_DATA_TIME', 'default', 'N', '0', NOW());
