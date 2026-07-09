# 个人健康管理系统 — 项目文档

> 前后端分离的单体健康档案管理系统

## 文档目录

| 文档 | 说明 |
|------|------|
| [01-项目概述](./01-项目概述.md) | 项目背景、技术栈、功能清单、架构说明 |
| [02-环境搭建与运行](./02-环境搭建与运行.md) | 开发环境、数据库初始化、启动步骤 |
| [03-数据库设计](./03-数据库设计.md) | 表结构、字段说明、示例数据 |
| [04-API接口文档](./04-API接口文档.md) | 全部 REST 接口、请求/响应示例 |
| [05-前端开发说明](./05-前端开发说明.md) | Vue3 项目结构、页面功能、联调配置 |
| [06-后端开发说明](./06-后端开发说明.md) | Spring Boot 分层架构、核心模块说明 |
| [07-AI功能说明](./07-AI功能说明.md) | Spring AI 集成、配置与调用流程 |

## 项目结构

```
personal-health-manage/
├── docs/                  # 项目文档（本目录）
├── health-server/         # Spring Boot 后端
│   ├── pom.xml
│   ├── sql/init.sql       # 数据库初始化脚本
│   └── src/main/
│       ├── java/com/health/manage/
│       └── resources/application.yml
└── health-web/            # Vue3 前端
    ├── package.json
    ├── vite.config.js
    └── src/
```

## 快速启动

```bash
# 1. 初始化数据库
mysql -u root -p < health-server/sql/init.sql

# 2. 启动后端（端口 8080）
cd health-server && mvn spring-boot:run

# 3. 启动前端（端口 5173）
cd health-web && npm install && npm run dev
```

| 服务 | 地址 |
|------|------|
| 前端页面 | http://localhost:5173 |
| 后端 API | http://localhost:8080 |
| 接口文档 | http://localhost:8080/doc.html |

## 技术栈一览

**后端**：JDK 17 · Spring Boot 3.2.5 · MyBatis-Plus 3.5.5 · MySQL 8 · Spring AI · Knife4j

**前端**：Vue 3 · Vite 4 · Element Plus · Axios · Vue Router 4
