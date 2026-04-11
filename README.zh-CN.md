# Terra EMS Server — 后端服务

<h3 align="center">
  <img src="docs/assets/terra-logo-1.png" height="20" style="vertical-align: middle;" /> Terra 能源管理系统 — 企业级能源管理与碳排放分析平台
</h3>

<p align="center">
  <img src="https://img.shields.io/badge/Java-21-orange?style=flat-square&logo=openjdk" alt="Java 21"/>
  <img src="https://img.shields.io/badge/Spring%20Boot-3.4.4-brightgreen?style=flat-square&logo=springboot" alt="Spring Boot"/>
  <img src="https://img.shields.io/badge/PostgreSQL-17-blue?style=flat-square&logo=postgresql" alt="PostgreSQL"/>
  <img src="https://img.shields.io/badge/Redis-6+-red?style=flat-square&logo=redis" alt="Redis"/>
  <img src="https://img.shields.io/badge/Docker-Ready-blue?style=flat-square&logo=docker" alt="Docker"/>
  <img src="https://img.shields.io/badge/License-MIT-blue?style=flat-square" alt="MIT License"/>
</p>

### 🏗️ 系统架构图

```mermaid
graph TD
    subgraph "边缘端 (Edge)"
        GW[terra-ems-gateway: Rust 采集网关]
        Meter[物理设备 / 仪表] -->|Modbus/DLT645| GW
    end

    subgraph "云端/中心端 (Cloud/Center)"
        EMQX{EMQX: MQTT 消息总线}
        Proc[terra-ems-processor: Rust 数据处理器]
        Server[terra-ems-server: Java 业务中后台]
        
        GW -->|MQTT| EMQX
        EMQX -->|Subscribe| Proc
        Proc -->|Ingest| GDB[(GreptimeDB: 时序存储)]
        Proc <-->|API| Server
        Server <--> DB[(PostgreSQL: 业务存储)]
        Server <--> Redis[(Redis: 缓存)]
        Web[terra-ems-web: React 前端] <-->|RESTful| Server
    end
```

---

## 🖼️ 系统截图展示

<p align="center">
  <img src="https://raw.githubusercontent.com/dengxp/terra-ems-web/main/docs/images/dashboard_v3.png" width="45%" alt="Dashboard V3 Pro"/>
  <img src="https://raw.githubusercontent.com/dengxp/terra-ems-web/main/docs/images/branch_analysis.png" width="45%" alt="支路分析"/>
</p>
<p align="center">
  <img src="https://raw.githubusercontent.com/dengxp/terra-ems-web/main/docs/images/price_policy.png" width="45%" alt="费率配置"/>
  <img src="https://raw.githubusercontent.com/dengxp/terra-ems-web/main/docs/images/carbon_analysis.png" width="45%" alt="碳排放分析"/>
</p>

---

## 📋 项目简介

<p align="center">
  <img src="https://raw.githubusercontent.com/dengxp/terra-ems-web/main/docs/images/demo.webp" width="100%" alt="Terra EMS 演示视频"/>
</p>

Terra EMS（Terra Energy Management System）是一套面向工业企业的**现代化能源管理平台**，基于 Spring Boot 3.4 + Spring Data JPA 构建，提供能耗监测、分时电价分析、成本核算、碳排放计量、能效对标、告警预警等核心功能，帮助企业实现能源数字化管理与节能减排目标。

> 📦 前端仓库：[terra-ems-web](https://github.com/dengxp/terra-ems-web)

## 🚀 在线演示

*   **演示地址**：[在线演示](https://terra-ems.com)
*   **账号**：`admin`
*   **密码**：`admin123`

> [!TIP]
> **两大核心黑科技**：
> 1. **高性能边缘计算**：基于 Rust 的边缘网关，支持千量级点位并发采集，内置磁盘级 Local Cache，保障网络波动时数据零丢失。
> 2. **分钟级场站部署**：支持 YAML 场站配置文件导入，一键完成“场站-网关-设备-点位”的整站层级初始化。

---

## ✨ 核心功能

| 🔋 **基础数据** | 能源类型、用能单元（树形结构）、计量器具、采集点位 | ✅ |
| 🚀 **快速部署** | **场站配置 YAML 一键导入**、整站初始化 | ✅ |
| 🛡️ **边缘智能** | **Rust 采集网关**、断网补显、协议透明传输 | ✅ |
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
| **语言** | Java 21 / **Rust 1.82+** | — |
| **消息总线** | **EMQX (MQTT 5.0)** | 5.x |
| **业务数据库** | PostgreSQL | 17 |
| **时序数据库** | **GreptimeDB** | 0.9+ |
| **缓存** | Redis | 6+ |
| **后端框架** | Spring Boot | 3.4.4 |
| **前端框架** | React + TypeScript + Ant Design Pro | — |

---

## 📁 项目结构

```
terra-ems-server/
├── terra-ems-common/       # 通用模块：统一响应 Result、异常码、工具类
├── terra-ems-framework/    # 框架模块：Security 配置、JPA 基类、Controller 继承体系
├── terra-ems-system/       # 业务模块：Entity、Repository、Service（系统+业务）
├── terra-ems-admin/        # 管理模块：启动类、Controller 层、API 接口定义
├── database/               # 数据库脚本
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

### 1. 数据库初始化

```bash
# 使用完整初始化脚本（包含表结构 + 演示数据）
psql -U postgres -d terra_ems -f database/combined_init_postgres.sql
```

### 2. 构建与运行

```bash
mvn clean install -DskipTests
cd terra-ems-admin
mvn spring-boot:run
```

应用启动后访问：`http://localhost:8081/api/swagger-ui.html`

### 3. 一键启动 (Docker Compose)

如果您将 `terra-ems-server` 和 `terra-ems-web` 克隆在同一个父目录下，可以使用以下命令一键启动全套系统：

```bash
docker-compose up --build
```

该命令将自动启动 PostgreSQL、Redis、后端服务 (8081) 和前端服务 (80)。

---

## 🤝 贡献与反馈

我们非常欢迎通过 [Issues](https://github.com/dengxp/terra-ems-server/issues) 提交 Bug 报告、功能建议或使用咨询。

> [!IMPORTANT]
> **关于代码提交（PR）**：
> 为确保项目核心架构的一致性及后续商业化规划的稳定性，**目前本项目暂不接受外部代码提交（Pull Requests）**。感谢您的理解，我们欢迎大家以 Issue 的形式参与讨论。

---

## 📜 开源协议

[MIT License](LICENSE) — Copyright © 2025-2026 泰若科技（广州）有限公司
