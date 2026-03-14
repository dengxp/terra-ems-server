# Terra EMS — 后端服务

<p align="center">
  <strong>🌿 Terra 能源管理系统 — 企业级能源管理与碳排放分析平台</strong>
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Java-21-orange?style=flat-square&logo=openjdk" alt="Java 21"/>
  <img src="https://img.shields.io/badge/Spring%20Boot-3.4.4-brightgreen?style=flat-square&logo=springboot" alt="Spring Boot"/>
  <img src="https://img.shields.io/badge/PostgreSQL-17-blue?style=flat-square&logo=postgresql" alt="PostgreSQL"/>
  <img src="https://img.shields.io/badge/Redis-6+-red?style=flat-square&logo=redis" alt="Redis"/>
  <img src="https://img.shields.io/badge/Docker-Ready-blue?style=flat-square&logo=docker" alt="Docker"/>
  <img src="https://img.shields.io/badge/License-MIT-blue?style=flat-square" alt="MIT License"/>
</p>

---

## 📋 项目简介

Terra EMS（Terra Energy Management System）是一套面向工业企业的**现代化能源管理平台**，基于 Spring Boot 3.4 + Spring Data JPA 构建，提供能耗监测、分时电价分析、成本核算、碳排放计量、能效对标、告警预警等核心功能，帮助企业实现能源数字化管理与节能减排目标。

> 📦 前端仓库：[terra-ems-web](https://github.com/dengxp/terra-ems-web)

---

## ✨ 核心功能
... (保持中间表格不变) ...
## 📁 项目结构

```
terra-ems/
├── terra-ems-common/       # 通用模块：统一响应 Result、异常码、工具类
├── terra-ems-framework/    # 框架模块：Security 配置、JPA 基类、Controller 继承体系
├── terra-ems-system/       # 业务模块：Entity、Repository、Service（系统+业务）
├── terra-ems-admin/        # 管理模块：启动类、Controller 层、API 接口定义
├── database/               # 数据库脚本
└── Dockerfile              # Docker 构建文件
```
... (保持中间内容不变) ...
## 📜 开源协议

[MIT License](LICENSE) — Copyright © 2024-2026 泰若科技（广州）有限公司

---

## ✨ 核心功能

| 模块 | 功能描述 | 状态 |
|:---|:---|:---:|
| 🔋 **基础数据** | 能源类型、用能单元（树形结构）、计量器具、采集点位 | ✅ |
| 📊 **统计分析** | 能耗统计、同比环比、趋势分析、排名分析、综合看板 | ✅ |
| ⚡ **峰谷分析** | 分时电价策略配置、尖峰平谷用电量分析 | ✅ |
| 💰 **成本管理** | 电价策略管理、成本策略绑定、能源成本记录与偏差分析 | ✅ |
| 🌍 **碳排放** | 碳排放核算、碳排放趋势、排名分析 | ✅ |
| 🎯 **对标管理** | 能效对标值管理（国标/行标/企标/区域标准） | ✅ |
| 🌱 **节能管理** | 节能项目全生命周期跟踪、政策法规管理 | ✅ |
| ⚠️ **告警管理** | 告警限值类型、预报警配置、告警记录处理 | ✅ |
| 📖 **知识库** | 节能知识文章管理（Markdown 支持） | ✅ |
| 🏭 **生产管理** | 产品信息管理、生产记录管理 | ✅ |
| 👤 **系统管理** | 用户、角色、部门、岗位、菜单、权限、字典、系统配置 | ✅ |
| 📋 **系统监控** | 登录日志、操作日志、在线用户、缓存管理 | ✅ |

---

## 🛠️ 技术栈

| 类别 | 技术 | 版本 |
|:---|:---|:---|
| **语言** | Java | 21 |
| **框架** | Spring Boot | 3.4.4 |
| **ORM** | Spring Data JPA + Hibernate | — |
| **数据库** | PostgreSQL | 14+ |
| **缓存** | Redis + Spring Session | 6+ |
| **认证** | Header-Based Token (`X-Terra-Auth-Token`) + Redis Session | — |
| **API 文档** | SpringDoc OpenAPI (Swagger) | — |
| **工具库** | Lombok / MapStruct / Hutool / Guava / Commons | — |
| **构建** | Maven | 3.9+ |
| **容器化** | Docker (eclipse-temurin:21) | — |

---

## 📁 项目结构

```
terra-ems-v3/
├── terra-ems-common/       # 通用模块：统一响应 Result、异常码、工具类
├── terra-ems-framework/    # 框架模块：Security 配置、JPA 基类、Controller 继承体系
├── terra-ems-system/       # 业务模块：Entity、Repository、Service（系统+业务）
├── terra-ems-admin/        # 管理模块：启动类、Controller 层、API 接口定义
├── database/               # 数据库脚本
│   ├── combined_init_postgres.sql   # 完整初始化脚本（结构 + 数据）
│   ├── init-postgres.sql            # 最小化建库脚本
│   └── ems_*_init.sql               # 各模块独立初始化脚本
└── Dockerfile              # Docker 构建文件
```

### Controller 继承体系

```
Controller                          # 基类，统一响应转换
    └── ReadableController          # 只读（查询、分页）
            └── WritableController  # 读写（增删改查）
                    └── BaseController  # 完整 CRUD
```

---

## 🚀 快速开始

### 环境要求

| 环境 | 最低版本 | 推荐版本 |
|:---|:---|:---|
| JDK | 21 | 21 |
| PostgreSQL | 14 | 16+ |
| Redis | 6.0 | 7.0+ |
| Maven | 3.9 | 3.9+ |

### 1. 数据库初始化

```bash
# 方式一：使用完整初始化脚本（推荐，包含表结构 + 演示数据）
PGPASSWORD=postgres psql -h localhost -p 5432 -U postgres -d terra_ems \
  -f database/combined_init_postgres.sql

# 方式二：仅创建数据库，由 JPA 自动建表
psql -U postgres -f database/init-postgres.sql
```

> 💡 项目配置了 `spring.jpa.hibernate.ddl-auto=update`，JPA 会自动创建/更新表结构。演示数据需通过 SQL 脚本手动导入。

### 2. 配置文件

编辑 `terra-ems-admin/src/main/resources/application.yml`：

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/terra_ems
    username: postgres
    password: postgres
  data:
    redis:
      host: localhost
      port: 6379

server:
  port: 8081
  servlet:
    context-path: /api
```

支持通过环境变量覆盖配置：

| 环境变量 | 说明 | 默认值 |
|:---|:---|:---|
| `SPRING_DATASOURCE_URL` | 数据库连接地址 | `jdbc:postgresql://localhost:5432/terra_ems` |
| `SPRING_DATASOURCE_USERNAME` | 数据库用户名 | `postgres` |
| `SPRING_DATASOURCE_PASSWORD` | 数据库密码 | `postgres` |
| `SPRING_DATA_REDIS_HOST` | Redis 地址 | `localhost` |
| `SPRING_DATA_REDIS_PORT` | Redis 端口 | `6379` |

### 3. 构建与运行

```bash
# 构建项目
mvn clean install -DskipTests

# 运行应用
cd terra-ems-admin
mvn spring-boot:run
```

应用启动后：

| 地址 | 说明 |
|:---|:---|
| `http://localhost:8081/api` | API 根路径 |
| `http://localhost:8081/api/swagger-ui.html` | Swagger 文档 |

### 4. 默认账户

| 用户名 | 密码 | 角色 |
|:---|:---|:---|
| `admin` | `admin123` | 超级管理员 |

---

## 🐳 Docker 部署

```bash
# 构建镜像
docker build -t terra-ems-backend .

# 运行容器
docker run -d \
  --name terra-ems-api \
  -p 8081:8081 \
  -e SPRING_DATASOURCE_URL=jdbc:postgresql://host.docker.internal:5432/terra_ems \
  -e SPRING_DATA_REDIS_HOST=host.docker.internal \
  terra-ems-backend
```

---

## 📄 API 接口概览

所有接口统一前缀 `/api`，遵循 RESTful 规范。

### 系统管理

| 模块 | 路径前缀 | Controller |
|:---|:---|:---|
| 用户管理 | `/api/system/user` | `SysUserController` |
| 角色管理 | `/api/system/role` | `SysRoleController` |
| 部门管理 | `/api/system/dept` | `SysDeptController` |
| 岗位管理 | `/api/system/post` | `SysPostController` |
| 菜单管理 | `/api/system/menu` | `SysMenuController` |
| 权限管理 | `/api/system/permission` | `SysPermissionController` |
| 模块管理 | `/api/system/module` | `SysModuleController` |
| 通知公告 | `/api/system/notice` | `SysNoticeController` |
| 字典类型 | `/api/system/dict-type` | `SysDictTypeController` |
| 字典数据 | `/api/system/dict-data` | `SysDictDataController` |
| 系统配置 | `/api/system/config` | `SysConfigController` |

### 系统监控

| 模块 | 路径前缀 | Controller |
|:---|:---|:---|
| 登录日志 | `/api/monitor/login-log` | `SysLoginLogController` |
| 操作日志 | `/api/monitor/oper-log` | `SysOperationLogController` |
| 缓存管理 | `/api/cache` | `CacheController` |

### 能源管理

| 模块 | 路径前缀 | Controller |
|:---|:---|:---|
| 能源类型 | `/api/ems/energy-type` | `EnergyTypeController` |
| 用能单元 | `/api/ems/energy-unit` | `EnergyUnitController` |
| 计量器具 | `/api/ems/meter` | `MeterController` |
| 采集点位 | `/api/ems/meter-point` | `MeterPointController` |
| 能耗统计 | `/api/ems/statistics` | `EnergyStatisticsController` |
| 峰谷分析 | `/api/ems/peak-valley` | `PeakValleyController` |
| 电价策略 | `/api/ems/price-policy` | `PricePolicyController` |
| 分时电价 | `/api/ems/time-period-price` | `TimePeriodPriceController` |
| 成本策略绑定 | `/api/ems/cost-policy-binding` | `CostPolicyBindingController` |
| 能源成本记录 | `/api/ems/energy-cost-record` | `EnergyCostRecordController` |
| 碳排放分析 | `/api/ems/carbon` | `CarbonEmissionController` |
| 对标管理 | `/api/ems/benchmark` | `BenchmarkController` |
| 告警限值类型 | `/api/ems/alarm-limit-type` | `AlarmLimitTypeController` |
| 告警配置 | `/api/ems/alarm-config` | `AlarmConfigController` |
| 告警记录 | `/api/ems/alarm-record` | `AlarmRecordController` |
| 节能项目 | `/api/ems/energy-saving` | `EnergySavingProjectController` |
| 政策法规 | `/api/ems/policy` | `PolicyController` |
| 知识库 | `/api/ems/knowledge` | `KnowledgeArticleController` |
| 产品管理 | `/api/ems/product` | `ProductController` |
| 生产记录 | `/api/ems/production-record` | `ProductionRecordController` |

### 接口规范

| 操作 | HTTP 方法 | 路径模式 | 说明 |
|:---|:---|:---|:---|
| 分页查询 | `GET` | `/` | 支持条件过滤，0-indexed 分页 |
| 列表查询 | `GET` | `/list` | 不分页，返回完整列表 |
| 详情查询 | `GET` | `/{id}` | 按 ID 查询 |
| 下拉选项 | `GET` | `/options` | 返回 `label/value` 格式 |
| 新增/修改 | `POST` | `/` | 请求体为实体 JSON |
| 单条删除 | `DELETE` | `/{id}` | 按 ID 删除 |
| 批量删除 | `DELETE` | `/` | 请求体为 ID 数组 |

---

## 🤝 贡献

欢迎提交 Issue 和 Pull Request！

---

## 📜 开源协议

[MIT License](LICENSE) — Copyright © 2024 泰若科技（广州）有限公司
