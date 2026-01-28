-- 添加产品类型字典
INSERT INTO `sys_dict_type` (`dict_name`, `dict_type`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) 
VALUES ('产品类型', 'product_type', '0', 'admin', NOW(), '', NULL, '产品物料类型分类');

-- 添加产品类型字典数据
INSERT INTO `sys_dict_data` (`dict_sort`, `dict_label`, `dict_value`, `dict_type`, `css_class`, `list_class`, `is_default`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) 
VALUES 
(1, '成品', '1', 'product_type', '', 'primary', 'Y', '0', 'admin', NOW(), '', NULL, ''),
(2, '半成品', '2', 'product_type', '', 'info', 'N', '0', 'admin', NOW(), '', NULL, ''),
(3, '原材料', '3', 'product_type', '', 'warning', 'N', '0', 'admin', NOW(), '', NULL, '');
