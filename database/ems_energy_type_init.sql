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
