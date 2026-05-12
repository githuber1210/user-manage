# Agent 全局指令

## 1. 项目概述

Vue + Spring Boot 前后端分离演示项目，包含用户管理 CRUD 操作。

**技术栈**：

- 后端：Java 8 + Spring Boot 2.7.18 + MyBatis + MySQL
- 前端：Vue 3 + Vue Router + Axios + Bootstrap + Vite

## 2. 核心约束

### 2.1 禁止操作

- ❌ 禁止使用 Java 11+ 语法特性（项目要求 Java 8）
- ❌ 禁止引入未在 `pom.xml` 中声明的新依赖
- ❌ 禁止修改 `application.yml` 中的数据库连接配置
- ❌ 禁止删除或重命名已有文件
- ❌ 禁止提交包含敏感信息（密码、密钥）的代码

### 2.2 命名规范

- Java 类：PascalCase（如 `UserController.java`）
- Vue 组件：PascalCase（如 `UserList.vue`）
- API 文件：小写连字符（如 `user.js`）
- 变量/方法：camelCase
- 常量：UPPER\_CASE\_WITH\_UNDERSCORES

### 2.3 代码风格

- Java：UTF-8，4 空格缩进，方法≤50行，类≤200行
- Vue：UTF-8，2 空格缩进，使用 `<script setup>` 语法
- API 调用必须封装到 `src/api/` 目录

## 3. 目录职责

| 目录                                                   | 职责           | 约束                  |
| :--------------------------------------------------- | :----------- | :------------------ |
| `backend/src/main/java/com/example/demo/controller/` | REST API 控制层 | 仅处理 HTTP 请求，不包含业务逻辑 |
| `backend/src/main/java/com/example/demo/service/`    | 业务逻辑层        | 事务管理、数据校验           |
| `backend/src/main/java/com/example/demo/mapper/`     | 数据访问层        | MyBatis 接口，配合 XML   |
| `backend/src/main/java/com/example/demo/dto/`        | 数据传输对象       | 隔离实体与外部接口           |
| `frontend/src/api/`                                  | API 封装       | 统一管理所有后端调用          |
| `frontend/src/views/`                                | 页面组件         | Vue 单文件组件           |

## 4. 工作流程

### 4.1 开发流程

```
需求分析 → 代码实现 → 测试验证 → 提交
```

### 4.2 代码修改规范

1. 先读取目标文件最新内容
2. 最小化修改，保持代码风格一致
3. 确保修改后项目可正常构建运行

### 4.3 测试验证

- 后端：运行 `mvn spring-boot:run` 启动服务
- 前端：运行 `npm install && npm run dev` 启动开发服务器

## 5. API 规范

### 5.1 接口路径前缀

所有 API 接口以 `/api/` 开头

### 5.2 统一响应格式

```json
{
  "code": 200,
  "message": "操作成功",
  "data": {}
}
```

### 5.3 状态码

- 200：成功
- 400：参数错误
- 404：资源未找到
- 500：服务器错误

## 6. 部署约束

### 6.1 开发环境

- 后端：`http://localhost:8080`
- 前端：`http://localhost:5173`

### 6.2 代理配置

前端请求 `/api/*` 自动转发至后端 `http://localhost:8080/api/*`
