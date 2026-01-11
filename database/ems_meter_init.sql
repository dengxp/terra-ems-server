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
