# 04 - API 接口文档

> 基础地址：`http://localhost:8080`
>
> 在线文档：http://localhost:8080/doc.html （Knife4j）

## 1. 统一响应格式

所有接口返回 `Result<T>` 结构：

```json
{
  "code": 200,
  "message": "操作成功",
  "data": { }
}
```

### 状态码

| code | 说明 |
|------|------|
| 200 | 操作成功 |
| 400 | 请求参数错误 |
| 404 | 资源不存在 |
| 500 | 服务器内部错误 |
| 600 | 业务处理失败 |

---

## 2. 健康记录接口

基础路径：`/api/health`

### 2.1 分页条件查询

```
GET /api/health/list
```

**Query 参数：**

| 参数 | 类型 | 必填 | 说明 |
|------|------|------|------|
| pageNum | int | 否 | 页码，默认 1 |
| pageSize | int | 否 | 每页条数，默认 10 |
| minHeight | decimal | 否 | 最小身高 |
| maxHeight | decimal | 否 | 最大身高 |
| minWeight | decimal | 否 | 最小体重 |
| maxWeight | decimal | 否 | 最大体重 |
| minBmi | decimal | 否 | 最小 BMI |
| maxBmi | decimal | 否 | 最大 BMI |
| minHeartRate | int | 否 | 最小心率 |
| maxHeartRate | int | 否 | 最大心率 |
| bloodPressure | string | 否 | 血压关键词（模糊） |
| dietHabit | string | 否 | 饮食习惯关键词（模糊） |
| recordTimeStart | datetime | 否 | 记录开始时间 |
| recordTimeEnd | datetime | 否 | 记录结束时间 |

**请求示例：**

```
GET /api/health/list?pageNum=1&pageSize=10&minBmi=18&maxBmi=25
```

**响应示例：**

```json
{
  "code": 200,
  "message": "操作成功",
  "data": {
    "records": [
      {
        "id": 1,
        "height": 175.00,
        "weight": 70.00,
        "bmi": 22.86,
        "heartRate": 72,
        "bloodPressure": "120/80",
        "exerciseDuration": 45,
        "sleepTime": 7.5,
        "dietHabit": "三餐规律，少油少盐",
        "recordTime": "2026-07-01T08:00:00",
        "createTime": "2026-07-09T14:26:14",
        "updateTime": "2026-07-09T14:26:14"
      }
    ],
    "total": 3,
    "size": 10,
    "current": 1,
    "pages": 1
  }
}
```

---

### 2.2 根据 ID 查询

```
GET /api/health/{id}
```

**路径参数：**

| 参数 | 类型 | 说明 |
|------|------|------|
| id | long | 记录 ID |

**响应示例：**

```json
{
  "code": 200,
  "message": "操作成功",
  "data": {
    "id": 1,
    "height": 175.00,
    "weight": 70.00,
    "bmi": 22.86,
    "heartRate": 72,
    "bloodPressure": "120/80",
    "exerciseDuration": 45,
    "sleepTime": 7.5,
    "dietHabit": "三餐规律，少油少盐",
    "recordTime": "2026-07-01T08:00:00",
    "createTime": "2026-07-09T14:26:14",
    "updateTime": "2026-07-09T14:26:14"
  }
}
```

---

### 2.3 新增健康记录

```
POST /api/health/add
Content-Type: application/json
```

**请求体：**

| 字段 | 类型 | 必填 | 校验 | 说明 |
|------|------|------|------|------|
| height | decimal | 否 | 50~250 | 身高(cm) |
| weight | decimal | 否 | 20~300 | 体重(kg) |
| heartRate | int | 否 | 30~220 | 心率 |
| bloodPressure | string | 否 | max 20 | 血压 |
| exerciseDuration | int | 否 | 0~1440 | 运动时长(分钟) |
| sleepTime | decimal | 否 | 0~24 | 睡眠(小时) |
| dietHabit | string | 否 | max 500 | 饮食习惯 |
| recordTime | datetime | **是** | - | 记录时间 |

**请求示例：**

```json
{
  "height": 175.00,
  "weight": 70.00,
  "heartRate": 72,
  "bloodPressure": "120/80",
  "exerciseDuration": 45,
  "sleepTime": 7.5,
  "dietHabit": "三餐规律，少油少盐",
  "recordTime": "2026-07-09T08:00:00"
}
```

**响应示例：**

```json
{
  "code": 200,
  "message": "操作成功",
  "data": 4
}
```

> `data` 为新增记录的 ID。BMI 由后端根据身高体重自动计算。

---

### 2.4 修改健康记录

```
PUT /api/health/update
Content-Type: application/json
```

**请求体：** 同新增，但 `id` 必填。

```json
{
  "id": 1,
  "height": 176.00,
  "weight": 71.00,
  "heartRate": 75,
  "bloodPressure": "118/78",
  "exerciseDuration": 50,
  "sleepTime": 8.0,
  "dietHabit": "三餐规律",
  "recordTime": "2026-07-09T08:00:00"
}
```

**响应示例：**

```json
{
  "code": 200,
  "message": "操作成功",
  "data": null
}
```

---

### 2.5 单条逻辑删除

```
DELETE /api/health/{id}
```

**响应示例：**

```json
{
  "code": 200,
  "message": "操作成功",
  "data": null
}
```

---

### 2.6 批量逻辑删除

```
DELETE /api/health/batch
Content-Type: application/json
```

**请求体：**

```json
[1, 2, 3]
```

**响应示例：**

```json
{
  "code": 200,
  "message": "操作成功",
  "data": null
}
```

---

## 3. AI 健康分析接口

### 3.1 AI 生成健康建议

```
POST /api/health/ai/suggest
Content-Type: application/json
```

**请求体：**

| 字段 | 类型 | 必填 | 说明 |
|------|------|------|------|
| recordId | long | **是** | 健康记录 ID |

**请求示例：**

```json
{
  "recordId": 1
}
```

**响应示例：**

```json
{
  "code": 200,
  "message": "操作成功",
  "data": {
    "healthAdvice": "您的BMI处于正常范围，整体健康状况良好。建议保持规律作息...",
    "exerciseAdvice": "建议每周进行3-5次有氧运动，每次30-45分钟...",
    "dietAdvice": "保持三餐规律，增加蔬菜水果摄入，减少高盐高油食物..."
  }
}
```

---

## 4. 接口汇总

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | `/api/health/list` | 分页条件查询 |
| GET | `/api/health/{id}` | 根据 ID 查询 |
| POST | `/api/health/add` | 新增 |
| PUT | `/api/health/update` | 修改 |
| DELETE | `/api/health/{id}` | 单条删除 |
| DELETE | `/api/health/batch` | 批量删除 |
| POST | `/api/health/ai/suggest` | AI 健康分析 |

## 5. 错误响应示例

### 参数校验失败

```json
{
  "code": 400,
  "message": "记录时间不能为空",
  "data": null
}
```

### 业务异常

```json
{
  "code": 600,
  "message": "健康记录不存在，ID: 99",
  "data": null
}
```

### AI 调用失败

```json
{
  "code": 600,
  "message": "AI 分析失败，请检查 API Key 和网络配置：...",
  "data": null
}
```
