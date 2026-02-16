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
