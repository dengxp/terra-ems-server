# Terra EMS — Backend Service

<p align="center">
  <strong>🌿 Terra Energy Management System — Enterprise-grade Energy Management & Carbon Analysis Platform</strong>
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Java-21-orange?style=flat-square&logo=openjdk" alt="Java 21"/>
  <img src="https://img.shields.io/badge/Spring%20Boot-3.4.4-brightgreen?style=flat-square&logo=springboot" alt="Spring Boot"/>
  <img src="https://img.shields.io/badge/PostgreSQL-17-blue?style=flat-square&logo=postgresql" alt="PostgreSQL"/>
  <img src="https://img.shields.io/badge/Redis-6+-red?style=flat-square&logo=redis" alt="Redis"/>
  <img src="https://img.shields.io/badge/Docker-Ready-blue?style=flat-square&logo=docker" alt="Docker"/>
  <img src="https://img.shields.io/badge/License-MIT-blue?style=flat-square" alt="MIT License"/>
</p>

<p align="center">
  <a href="./README.zh-CN.md">中文文档</a> | <span>English</span>
</p>

---

## 📋 Introduction

Terra EMS (Terra Energy Management System) is a **modern energy management platform** designed for industrial enterprises. Built with Spring Boot 3.4 and Spring Data JPA, it provides comprehensive features including energy monitoring, TOU (Time-of-Use) electricity analysis, cost accounting, carbon emission measurement, energy benchmarking, and smart alerting.

> 📦 Frontend Repository: [terra-ems-web](https://github.com/dengxp/terra-ems-web)

## 🚀 Online Demo

*   **URL**: [http://ems.vipcti.com](http://ems.vipcti.com)
*   **Username**: `admin`
*   **Password**: `admin123`

> [!TIP]
> **More than an EMS - A Robust Web Development Base**:
> The underlying architecture of Terra EMS follows industry-leading standards. Its **permission management, data dictionary, configuration, logging, and automated CRUD Hook systems** are highly mature. It provides a development experience comparable to RuoYi (若依), and can be used as a general-purpose Java Web rapid development framework.

---

## ✨ Core Features

| Module | Description | Status |
|:---|:---|:---:|
| 🔋 **Base Data** | Energy types, Energy units (tree structure), Meters, Sampling points | ✅ |
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
| **Language** | Java | 21 |
| **Framework** | Spring Boot | 3.4.4 |
| **ORM** | Spring Data JPA + Hibernate | — |
| **Database** | PostgreSQL | 17 |
| **Cache** | Redis + Spring Session | 6+ |
| **Auth** | Header-Based Token (`X-Terra-Auth-Token`) | — |
| **Docs** | SpringDoc OpenAPI (Swagger) | — |
| **Build** | Maven | 3.9+ |

---

## 📁 Project Structure

```
terra-ems/
├── terra-ems-common/       # Common module: Result, ErrorCodes, Utilities
├── terra-ems-framework/    # Framework module: Security, JPA Base, Controller Hierarchy
├── terra-ems-system/       # Business module: Entity, Repository, Service (System + Business)
├── terra-ems-admin/        # Admin module: Bootloader, Controllers, API definitions
├── database/               # SQL scripts
└── Dockerfile              # Docker build file
```

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

We welcome bug reports, feature suggestions, or usage inquiries via [Issues](https://github.com/dengxp/terra-ems/issues).

> [!IMPORTANT]
> **About Pull Requests (PR)**:
> To maintain project architectural consistency and ensure the stability of future commercial roadmap, **we are not currently accepting external code contributions (Pull Requests)**. We appreciate your understanding and welcome discussions through Issues.

---

## 📜 License

[MIT License](LICENSE) — Copyright © 2024-2026 Terra Technology (Guangzhou) Co., Ltd.
