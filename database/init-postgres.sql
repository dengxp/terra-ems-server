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
    nickname VARCHAR(64),
    email VARCHAR(128),
    phone VARCHAR(20),
    status INTEGER NOT NULL DEFAULT 1,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

-- 创建索引
CREATE INDEX IF NOT EXISTS idx_sys_user_username ON sys_user(username);

-- 插入测试用户（密码: admin123, BCrypt加密）
INSERT INTO sys_user (username, password, nickname, status, created_at, updated_at) 
VALUES (
    'admin', 
    '$2a$10$N.zmdr9k7uOCQb376NoUnuTUNiCbsd7r0fPp.kGTw3DZSlPRdN5Y6', 
    '系统管理员', 
    1, 
    NOW(), 
    NOW()
) ON CONFLICT (username) DO NOTHING;

-- 查询验证
SELECT id, username, nickname, status, created_at FROM sys_user;
