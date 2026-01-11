# Terra EMS v3

<p align="center">
  <strong>🌿 Terra 能源管理系统 - 企业级能源管理与碳排放分析平台</strong>
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Java-21-orange?style=flat-square" alt="Java 21"/>
  <img src="https://img.shields.io/badge/Spring%20Boot-3.4.4-brightgreen?style=flat-square" alt="Spring Boot"/>
  <img src="https://img.shields.io/badge/License-MIT-blue?style=flat-square" alt="MIT License"/>
</p>

---

## 📋 项目简介

Terra EMS 是一套现代化的企业能源管理系统，提供能耗监测、成本分析、碳排放核算、峰谷分析等功能，帮助企业实现节能减排目标。

### ✨ 核心功能

| 模块 | 功能描述 |
|------|----------|
| 🔋 能源管理 | 能源类型、用能单元、计量器具、计量点位管理 |
| 📊 统计分析 | 能耗统计、同比环比、趋势分析、排名分析 |
| ⚡ 峰谷分析 | 分时电价配置、尖峰平谷用电分析 |
| 💰 成本管理 | 电价策略、成本记录、成本偏差分析 |
| 🌍 碳排放 | 碳排放核算、碳排放趋势、排名分析 |
| 🎯 对标管理 | 能耗对标分析、标杆值配置 |
| 🌱 节能管理 | 节能项目跟踪、节能效果评估 |
| ⚠️ 告警管理 | 告警配置、告警记录、限值类型管理 |
| 📖 知识库 | 节能知识文章管理 |
| ⚙️ 系统管理 | 用户、角色、部门、岗位、菜单、配置管理 |

---

## 🛠️ 技术栈

| 类别 | 技术 |
|------|------|
| **语言** | Java 21 |
| **框架** | Spring Boot 3.4.4 |
| **ORM** | Spring Data JPA + Hibernate |
| **认证** | Header-Based Session (X-Terra-Auth-Token + Redis) |
| **数据库** | MySQL 8.0+ |
| **缓存** | Redis |
| **API文档** | SpringDoc OpenAPI (Swagger) |
| **构建工具** | Maven |

---

## 📁 项目结构

```
terra-ems-v3/
├── terra-ems-common/      # 通用模块：工具类、常量、Result 响应封装
├── terra-ems-framework/   # 框架模块：Security 配置、JPA 基类、Controller 基类
├── terra-ems-system/      # 业务模块：Entity、Repository、Service
├── terra-ems-admin/       # 管理模块：启动类、Controller、API 接口
└── database/              # 数据库脚本：建表语句、初始化数据
```

---

## 🚀 快速开始

### 环境要求

- JDK 21+
- MySQL 8.0+
- Redis 6.0+
- Maven 3.9+

### 数据库初始化

```bash
# 导入数据库脚本
mysql -u root -p < database/ems_energy_type_init.sql
mysql -u root -p < database/ems_energy_unit_init.sql
# ... 其他脚本
```

### 配置文件

编辑 `terra-ems-admin/src/main/resources/application.yml`：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/terra_ems?useSSL=false&serverTimezone=Asia/Shanghai
    username: your_username
    password: your_password
  redis:
    host: localhost
    port: 6379
```

### 构建运行

```bash
# 构建项目
mvn clean install -DskipTests

# 运行应用
cd terra-ems-admin
mvn spring-boot:run
```

应用启动后访问：
- API 地址：http://localhost:8080
- Swagger 文档：http://localhost:8080/swagger-ui.html

---

## 📄 API 接口

### 主要接口模块

| 模块 | 路径前缀 | 说明 |
|------|----------|------|
| 能源类型 | `/energy-types` | 能源类型 CRUD |
| 用能单元 | `/energy-units` | 用能单元树形结构管理 |
| 计量器具 | `/meters` | 计量器具 CRUD |
| 计量点位 | `/meter-points` | 计量点位 CRUD |
| 峰谷分析 | `/peak-valley` | 按日/月/年分析 |
| 电价策略 | `/price-policies` | 电价策略配置 |
| 能耗统计 | `/statistics` | 能耗综合分析 |
| 碳排放 | `/carbon` | 碳排放核算 |
| 系统用户 | `/api/system/user` | 用户管理 |

---

## 🤝 贡献

欢迎提交 Issue 和 Pull Request！

---

## 📜 开源协议

[MIT License](LICENSE) - Copyright (c) 2024 泰若科技（广州）有限公司
