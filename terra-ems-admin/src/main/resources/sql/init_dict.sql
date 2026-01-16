-- 字典类型初始化
INSERT INTO sys_dict_type (id, name, type, status, create_time, remark) VALUES 
(1, '用户性别', 'SYS_USER_SEX', '0', NOW(), '用户性别列表'),
(2, '菜单状态', 'SYS_SHOW_HIDE', '0', NOW(), '菜单状态列表'),
(3, '系统开关', 'SYS_NORMAL_DISABLE', '0', NOW(), '系统开关列表'),
(6, '系统是否', 'SYS_YES_NO', '0', NOW(), '系统是否列表'),
(20, '尖峰平谷配置', 'ELECTRICITY_PRICE', '0', NOW(), '电价类别配置'),
(18, '计量器具状态', 'METER_STATUS', '0', NOW(), '计量器具的使用状态'),
(19, '参数数据类型', 'DATA_TYPE', '0', NOW(), '标准参数配置数据类型');

-- 字典数据初始化
-- SYS_USER_SEX
INSERT INTO sys_dict_data (sort_order, label, value, type_code, tag_type, is_default, status, create_time) VALUES
(1, '男', '0', 'SYS_USER_SEX', 'processing', 'Y', '0', NOW()),
(2, '女', '1', 'SYS_USER_SEX', 'success', 'N', '0', NOW()),
(3, '未知', '3', 'SYS_USER_SEX', 'default', 'N', '0', NOW());

-- SYS_SHOW_HIDE
INSERT INTO sys_dict_data (sort_order, label, value, type_code, tag_type, is_default, status, create_time) VALUES
(1, '显示', '0', 'SYS_SHOW_HIDE', 'processing', 'Y', '0', NOW()),
(2, '隐藏', '1', 'SYS_SHOW_HIDE', 'error', 'N', '0', NOW());

-- SYS_NORMAL_DISABLE
INSERT INTO sys_dict_data (sort_order, label, value, type_code, tag_type, is_default, status, create_time) VALUES
(1, '正常', '0', 'SYS_NORMAL_DISABLE', 'processing', 'Y', '0', NOW()),
(2, '停用', '1', 'SYS_NORMAL_DISABLE', 'error', 'N', '0', NOW());

-- SYS_YES_NO
INSERT INTO sys_dict_data (sort_order, label, value, type_code, tag_type, is_default, status, create_time) VALUES
(1, '是', 'Y', 'SYS_YES_NO', 'processing', 'Y', '0', NOW()),
(2, '否', 'N', 'SYS_YES_NO', 'error', 'N', '0', NOW());

-- ELECTRICITY_PRICE
INSERT INTO sys_dict_data (sort_order, label, value, type_code, tag_type, is_default, status, create_time) VALUES
(1, '峰', 'PEAK', 'ELECTRICITY_PRICE', 'warning', 'N', '0', NOW()),
(2, '平', 'FLAT', 'ELECTRICITY_PRICE', 'success', 'N', '0', NOW()),
(3, '谷', 'VALLEY', 'ELECTRICITY_PRICE', 'default', 'N', '0', NOW());

-- METER_STATUS
INSERT INTO sys_dict_data (sort_order, label, value, type_code, tag_type, is_default, status, create_time) VALUES
(1, '使用中', '1', 'METER_STATUS', 'success', 'N', '0', NOW()),
(2, '检修中', '2', 'METER_STATUS', 'warning', 'N', '0', NOW()),
(3, '备用', '3', 'METER_STATUS', 'default', 'Y', '0', NOW());

-- DATA_TYPE
INSERT INTO sys_dict_data (sort_order, label, value, type_code, tag_type, is_default, status, create_time) VALUES
(1, '实时数据', '1', 'DATA_TYPE', 'processing', 'N', '0', NOW()),
(2, '阶段数据', '2', 'DATA_TYPE', 'success', 'N', '0', NOW());
