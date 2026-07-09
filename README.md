# 个人健康管理系统

前后端分离的个人健康管理系统，适合课程设计 / 毕业设计答辩。

## 项目结构

```
personal-health-manage/
├── docker-compose.yml     # Docker 一键部署
├── .env.example           # 环境变量模板
├── docs/              # 📖 项目文档
├── health-server/     # Spring Boot 后端
└── health-web/        # Vue3 前端
```

## 文档

完整项目文档请查看 **[docs/](./docs/README.md)** 目录：

- [项目概述](./docs/01-项目概述.md)
- [环境搭建与运行](./docs/02-环境搭建与运行.md)
- [数据库设计](./docs/03-数据库设计.md)
- [API 接口文档](./docs/04-API接口文档.md)
- [前端开发说明](./docs/05-前端开发说明.md)
- [后端开发说明](./docs/06-后端开发说明.md)
- [AI 功能说明](./docs/07-AI功能说明.md)
- [Docker 部署](./docs/08-Docker部署.md)

## 快速启动

### Docker 一键部署（推荐）

```bash
copy .env.example .env    # 编辑 .env 配置 API Key
docker compose up -d --build
```

| 服务 | 地址 |
|------|------|
| 前端 | http://localhost |
| 后端 API | http://localhost:8080 |
| 接口文档 | http://localhost:8080/doc.html |

### 本地开发

## 技术栈

**后端**：JDK 17 · Spring Boot 3.2.5 · MyBatis-Plus · MySQL 8 · Spring AI · Knife4j

**前端**：Vue 3 · Vite 4 · Element Plus · Axios · Vue Router 4

**部署**：Docker · Docker Compose · Nginx
