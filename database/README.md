# Terra EMS v3 - PostgreSQL 数据库配置指南

## 快速开始

### 1. 安装PostgreSQL（如果尚未安装）

```bash
# macOS (使用Homebrew)
brew install postgresql@16
brew services start postgresql@16

# 或使用Docker
docker run -d \
  --name terra-postgres \
  -e POSTGRES_PASSWORD=postgres \
  -p 5432:5432 \
  postgres:16
```

### 2. 创建数据库

**方法一：使用SQL脚本（推荐）**

```bash
# 连接到PostgreSQL
psql -U postgres

# 执行初始化脚本
\i /path/to/terra-ems-v3/database/init-postgres.sql

# 或者直接执行
psql -U postgres -f database/init-postgres.sql
```

**方法二：手动创建**

```sql
CREATE DATABASE terra_ems
    WITH 
    OWNER = postgres
    ENCODING = 'UTF8';

-- 连接到数据库
\c terra_ems;

-- 插入测试用户
INSERT INTO sys_user (username, password, nickname, status, created_at, updated_at) 
VALUES (
    'admin', 
    '$2a$10$N.zmdr9k7uOCQb376NoUnuTUNiCbsd7r0fPp.kGTw3DZSlPRdN5Y6', 
    '系统管理员', 
    1, 
    NOW(), 
    NOW()
);
```

### 3. 配置说明

项目已自动配置PostgreSQL：

**application.yml**:
```yaml
datasource:
  driver-class-name: org.postgresql.Driver
  url: jdbc:postgresql://localhost:5432/terra_ems
  username: postgres
  password: postgres
```

**pom.xml**（已替换驱动）:
```xml
<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
</dependency>
```

### 4. 运行项目

```bash
cd terra-ems-admin
mvn spring-boot:run
```

### 5. 验证

```bash
# 登录测试（默认用户: admin / admin123）
curl -X POST http://localhost:8080/api/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"admin123"}'
```

## PostgreSQL vs MySQL 主要差异

| 特性 | PostgreSQL | MySQL |
|:---|:---|:---|
| 默认端口 | 5432 | 3306 |
| 自增主键 | `BIGSERIAL` | `BIGINT AUTO_INCREMENT` |
| 布尔类型 | `BOOLEAN` | `TINYINT(1)` |
| 时间类型 | `TIMESTAMP` | `DATETIME` |

JPA会自动处理这些差异，无需修改代码。
