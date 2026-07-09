# 个人健康管理系统 - 前端

基于 **Vue3 + Vite5 + Element Plus** 的前端项目，与 Spring Boot 后端前后端分离。

## 技术栈

| 技术 | 版本 | 说明 |
|------|------|------|
| Vue | 3.x | 前端框架 |
| Vite | 5.x | 构建工具 |
| Element Plus | 2.x | UI 组件库 |
| Axios | 1.x | HTTP 请求 |
| Vue Router | 4.x | 路由管理 |

## 项目结构

```
health-web/
├── index.html
├── package.json
├── vite.config.js
├── README.md
└── src/
    ├── main.js                 # 入口文件
    ├── App.vue
    ├── api/
    │   └── health.js           # 健康记录 API 封装
    ├── router/
    │   └── index.js            # 路由配置
    ├── utils/
    │   └── request.js          # Axios 封装 + 拦截器
    ├── layout/
    │   └── MainLayout.vue      # 侧边栏 + 顶部导航布局
    └── views/
        └── HealthRecord.vue    # 健康记录管理主页面
```

## 快速启动

### 1. 环境要求

- Node.js 18+
- npm 或 pnpm

### 2. 安装依赖

```bash
cd health-web
npm install
```

### 3. 启动开发服务器

```bash
npm run dev
```

启动后访问：**http://localhost:5173**

> 请确保后端 `health-server` 已在 `http://localhost:8080` 启动。

### 4. 生产构建

```bash
npm run build
npm run preview
```

## 功能说明

### 页面布局
- 左侧菜单栏：健康记录管理
- 顶部导航栏：系统标题

### 健康记录管理
- 分页表格展示全部健康数据
- 多条件筛选：记录时间范围、BMI 区间
- 新增 / 编辑弹窗，表单必填校验
- 单条删除、批量多选删除

### AI 健康分析
- 每行操作栏【生成健康建议】按钮
- 调用后端 Spring AI 接口，弹窗展示三类建议

## 接口对接

后端基础地址：`http://localhost:8080`（配置在 `src/utils/request.js`）

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | `/health/list` | 分页查询 |
| GET | `/health/{id}` | 根据 ID 查询 |
| POST | `/health/add` | 新增 |
| PUT | `/health/update` | 修改 |
| DELETE | `/health/{id}` | 单条删除 |
| DELETE | `/health/batch` | 批量删除 |
| POST | `/health/ai/suggest` | AI 健康分析 |

## 联调说明

1. 先启动后端（`health-server` 目录下 `mvn spring-boot:run`）
2. 再启动前端（`health-web` 目录下 `npm run dev`）
3. 后端已配置 CORS，允许 `http://localhost:5173` 跨域访问
