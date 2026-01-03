# Terra EMS v3 Project

Terra Enterprise Management System - 现代化企业管理系统

## 技术栈

- **Java**: 21
- **Spring Boot**: 3.4.4
- **ORM**: Spring Data JPA + Hibernate
- **Authentication**: Header-Based Session (X-Terra-Auth-Token + Redis)
- **Database**: MySQL 8.0+
- **Cache**: Redis

## 模块结构

```
terra-ems-v3/
├── terra-ems-common/      # 通用工具、常量、基础类
├── terra-ems-framework/   # Spring配置、Security、JPA基类
├── terra-ems-system/      # 业务实体、Repository、Service
└── terra-ems-admin/       # 启动类、Controller
```

## 快速开始

### 前置条件

- JDK 21
- MySQL 8.0+
- Redis

### 构建运行

```bash
# 构建项目
mvn clean install

# 运行应用
cd terra-ems-admin
mvn spring-boot:run
```

应用将在 http://localhost:8080 启动

## License

MIT License - Copyright (c) 2024 泰若科技（广州）有限公司
