# Terra EMS Server — Backend Service

<h3 align="center">
  <img src="docs/assets/terra-logo-1.png" height="20" style="vertical-align: middle;" /> Terra Energy Management System — Enterprise-grade Energy Management & Carbon Analysis Platform
</h3>

<p align="center">
  <img src="https://img.shields.io/badge/Java-21-orange?style=flat-square&logo=openjdk" alt="Java 21"/>
  <img src="https://img.shields.io/badge/Spring%20Boot-3.4.4-brightgreen?style=flat-square&logo=springboot" alt="Spring Boot"/>
  <img src="https://img.shields.io/badge/PostgreSQL-17-blue?style=flat-square&logo=postgresql" alt="PostgreSQL"/>
  <img src="https://img.shields.io/badge/Redis-6+-red?style=flat-square&logo=redis" alt="Redis"/>
  <img src="https://img.shields.io/badge/Docker-Ready-blue?style=flat-square&logo=docker" alt="Docker"/>
  <img src="https://img.shields.io/badge/License-MIT-blue?style=flat-square" alt="MIT License"/>
</p>

### 🏗️ System Architecture

```mermaid
graph TD
    subgraph "Edge"
        GW[terra-ems-gateway: Rust Collector]
        Meter[Hardware / Meters] -->|Modbus/DLT645| GW
    end

    subgraph "Cloud / Center"
        EMQX{EMQX: MQTT Message Bus}
        Proc[terra-ems-processor: Rust Processor]
        Server[terra-ems-server: Java Backend]
        
        GW -->|MQTT| EMQX
        EMQX -->|Subscribe| Proc
        Proc -->|Ingest| GDB[(GreptimeDB: TSDB)]
        Proc <-->|API| Server
        Server <--> DB[(PostgreSQL: Business DB)]
        Server <--> Redis[(Redis: Cache)]
        Web[terra-ems-web: React Frontend] <-->|RESTful| Server
    end
```

<p align="center">
  <a href="./README.zh-CN.md">中文文档</a> | <span>English</span>
</p>

---

## 🖼️ System Screenshots

<p align="center">
  <img src="https://raw.githubusercontent.com/dengxp/terra-ems-web/main/docs/images/dashboard_v3.png" width="45%" alt="Dashboard V3 Pro"/>
  <img src="https://raw.githubusercontent.com/dengxp/terra-ems-web/main/docs/images/branch_analysis.png" width="45%" alt="Branch Analysis"/>
</p>
<p align="center">
  <img src="https://raw.githubusercontent.com/dengxp/terra-ems-web/main/docs/images/price_policy.png" width="45%" alt="Price Policy"/>
  <img src="https://raw.githubusercontent.com/dengxp/terra-ems-web/main/docs/images/carbon_analysis.png" width="45%" alt="Carbon Analysis"/>
</p>

---

## 📋 Introduction

<p align="center">
  <img src="https://raw.githubusercontent.com/dengxp/terra-ems-web/main/docs/images/demo.webp" width="100%" alt="Terra EMS Demo Video"/>
</p>

Terra EMS (Terra Energy Management System) is a **modern energy management platform** designed for industrial enterprises. Built with Spring Boot 3.4 and Spring Data JPA, it provides comprehensive features including energy monitoring, TOU (Time-of-Use) electricity analysis, cost accounting, carbon emission measurement, energy benchmarking, and smart alerting.

> 📦 Frontend Repository: [terra-ems-web](https://github.com/dengxp/terra-ems-web)

## 🚀 Online Demo

*   **URL**: [http://terra-ems.com](http://terra-ems.com)
*   **Username**: `admin`
*   **Password**: `admin123`

> [!TIP]
> **Key Innovations**:
> 1. **High-Performance Edge Computing**: Rust-based gateway supporting thousand-level point collection, with disk-level Local Cache to ensure zero data loss during network flips.
> 2. **Minute-Level Deployment**: Support for YAML site configuration import, enabling one-click initialization of the entire site hierarchy (Site-Gateway-Meter-Point).

---

## ✨ Core Features

| Module | Description | Status |
|:---|:---|:---:|
| 🔋 **Base Data** | Energy types, Energy units (tree structure), Meters, Sampling points | ✅ |
| 🚀 **Fast Deploy** | **One-click YAML Site Import**, Auto-initialization | ✅ |
| 🛡️ **Edge Intel** | **Rust Collector**, Local Cache, Transparent Transmission | ✅ |
| 📊 **Statistics** | Consumption stats, YoY/MoM analysis, Trend analysis, Ranking, Dashboards | ✅ |
| ⚡ **Peak & Valley** | TOU pricing policy configuration, peak/valley/flat usage analysis | ✅ |
| 💰 **Cost Mgmt** | Price policy management, cost binding, cost records & variance analysis | ✅ |
| 🌍 **Carbon** | Carbon emission calculation, trend analysis, ranking | ✅ |
| 🎯 **Benchmarking** | Energy benchmarking (National/Industry/Enterprise/Regional standards) | ✅ |
| 🌱 **Energy Saving** | Lifecycle tracking of energy-saving projects, policy & regulation mgmt | ✅ |
| ⚠️ **Alerting** | Limit types, pre-alarm configuration, alert records & processing | ✅ |
| 📖 **Knowledge** | Energy-saving knowledge base (Markdown support) | ✅ |
| 🏭 **Production** | Product information, production record management | ✅ |
| 👤 **System Mgmt** | Users, Roles, Depts, Posts, Menus, Permissions, Dicts, Configs | ✅ |
| 📋 **Monitoring** | Login logs, Operation logs, Online users, Cache management | ✅ |

---

## 🛠️ Tech Stack

| Category | Technology | Version |
|:---|:---|:---|
| **Language** | Java 21 / **Rust 1.82+** | — |
| **Message Bus** | **EMQX (MQTT 5.0)** | 5.x |
| **Business DB** | PostgreSQL | 17 |
| **TSDB** | **GreptimeDB** | 0.9+ |
| **Cache** | Redis | 6+ |
| **Backend** | Spring Boot | 3.4.4 |
| **Frontend** | React + TypeScript + Ant Design Pro | — |

---

## 📁 Project Structure

```
terra-ems-server/
├── terra-ems-common/       # Common module: Result, ErrorCodes, Utilities
├── terra-ems-framework/    # Framework module: Security, JPA Base, Controller Hierarchy
├── terra-ems-system/       # Business module: Entity, Repository, Service (System + Business)
├── terra-ems-admin/        # Admin module: Bootloader, Controllers, API definitions
├── database/               # SQL scripts
└── Dockerfile              # Docker build file
### 3. All-in-One Start (Docker Compose)

If you have both `terra-ems-server` and `terra-ems-web` cloned in the same parent directory:

```bash
docker-compose up --build
```

This will spin up PostgreSQL, Redis, the Backend (8081), and the Frontend (80) automatically.

---

## 🚀 Quick Start

### 1. Database Initialization

```bash
# Using combined init script (includes schema + demo data)
psql -U postgres -d terra_ems -f database/combined_init_postgres.sql
```

### 2. Build & Run

```bash
mvn clean install -DskipTests
cd terra-ems-admin
mvn spring-boot:run
```

Access Swagger UI: `http://localhost:8081/api/swagger-ui.html`

---

## 🤝 Contribution & Feedback

We welcome bug reports, feature suggestions, or usage inquiries via [Issues](https://github.com/dengxp/terra-ems-server/issues).

> [!IMPORTANT]
> **About Pull Requests (PR)**:
> To maintain project architectural consistency and ensure the stability of future commercial roadmap, **we are not currently accepting external code contributions (Pull Requests)**. We appreciate your understanding and welcome discussions through Issues.

---

## 📜 License

[MIT License](LICENSE) — Copyright © 2025-2026 Terra Technology (Guangzhou) Co., Ltd.
