# 08 - Docker 部署

## 1. 概述

项目提供 Docker Compose 一键部署，包含三个服务：

| 服务 | 容器名 | 端口 | 说明 |
|------|--------|------|------|
| mysql | health-mysql | 3306 | MySQL 8 数据库 |
| server | health-server | 8080 | Spring Boot 后端 |
| web | health-web | 80 | Nginx + Vue3 前端 |

## 2. 环境要求

- Docker 20.10+
- Docker Compose 2.0+

验证安装：

```bash
docker --version
docker compose version
```

## 3. 快速启动

### 3.1 配置环境变量

```bash
# 复制环境变量模板
copy .env.example .env        # Windows
cp .env.example .env          # Linux / macOS
```

编辑 `.env`，至少配置 AI API Key（若需使用 AI 功能）：

```env
OPENAI_API_KEY=sk-your-api-key
OPENAI_BASE_URL=https://dashscope.aliyuncs.com/compatible-mode
OPENAI_MODEL=qwen-turbo
MYSQL_ROOT_PASSWORD=root123
```

### 3.2 一键启动

在项目根目录执行：

```bash
docker compose up -d --build
```

首次启动会：
1. 拉取 MySQL 8 镜像
2. 构建后端、前端镜像（需几分钟）
3. 自动执行 `sql/init.sql` 初始化数据库

### 3.3 访问地址

| 服务 | 地址 |
|------|------|
| 前端页面 | http://localhost |
| 后端 API | http://localhost:8080 |
| 接口文档 | http://localhost:8080/doc.html |
| MySQL | localhost:3306 |

## 4. 常用命令

```bash
# 查看运行状态
docker compose ps

# 查看日志
docker compose logs -f
docker compose logs -f server    # 仅后端日志

# 停止服务
docker compose down

# 停止并删除数据卷（清空数据库）
docker compose down -v

# 重新构建并启动
docker compose up -d --build

# 仅重建某个服务
docker compose up -d --build server
```

## 5. 架构说明

```
浏览器
   │
   ▼ :80
┌─────────────┐
│  health-web │  Nginx 托管前端静态资源
│  (nginx)    │  /api/* 反向代理 ──────────┐
└─────────────┘                            │
                                           ▼ :8080
                                    ┌─────────────┐
                                    │health-server│
                                    │ Spring Boot │
                                    └──────┬──────┘
                                           │
                                           ▼ :3306
                                    ┌─────────────┐
                                    │ health-mysql│
                                    │   MySQL 8   │
                                    └─────────────┘
```

- 前端通过 Nginx 将 `/api` 请求代理到后端，无需跨域
- 后端通过 Docker 内部网络 `mysql` 主机名连接数据库
- 数据库数据持久化在 `mysql_data` 数据卷

## 6. 文件说明

```
personal-health-manage/
├── docker-compose.yml          # 编排配置
├── .env.example                # 环境变量模板
├── health-server/
│   ├── Dockerfile              # 后端镜像（Maven 多阶段构建）
│   └── .dockerignore
└── health-web/
    ├── Dockerfile              # 前端镜像（Node 构建 + Nginx）
    ├── nginx.conf              # Nginx 配置（SPA + API 代理）
    └── .dockerignore
```

## 7. 环境变量说明

| 变量 | 默认值 | 说明 |
|------|--------|------|
| OPENAI_API_KEY | your-api-key-here | AI API 密钥 |
| OPENAI_BASE_URL | dashscope 兼容地址 | OpenAI 兼容 API 地址 |
| OPENAI_MODEL | qwen-turbo | AI 模型名称 |
| MYSQL_ROOT_PASSWORD | root123 | MySQL root 密码 |
| MYSQL_DATABASE | health_manage | 数据库名 |

> Spring Boot 通过环境变量自动覆盖 `application.yml` 配置，无需挂载配置文件。

## 8. 常见问题

### Q: 后端启动失败，连接不上数据库？

MySQL 容器需完全就绪后后端才会启动（`depends_on` + `healthcheck`）。若仍失败：

```bash
docker compose logs mysql
docker compose restart server
```

### Q: 前端能打开但接口报错？

```bash
docker compose logs server
docker compose ps    # 确认 server 状态为 healthy / running
```

### Q: AI 功能不可用？

检查 `.env` 中 `OPENAI_API_KEY` 是否正确，然后重启后端：

```bash
docker compose up -d --build server
```

### Q: 如何清空数据重新初始化？

```bash
docker compose down -v
docker compose up -d --build
```

### Q: 构建后端 Maven 依赖下载慢？

`pom.xml` 使用 Spring Milestone 仓库，首次构建需联网下载，请耐心等待。

## 9. 生产部署建议

1. 修改 `.env` 中的默认密码和 API Key
2. 使用 HTTPS（Nginx 配置 SSL 证书）
3. 不要将 `.env` 提交到 Git
4. 考虑将 MySQL 端口 `3306` 从宿主机映射中移除，仅容器内访问
