# 项目代理与智能体配置

## 1. API 代理配置

### 1.1 前端开发代理

前端使用 Vite 配置开发环境代理，将 API 请求转发到后端服务：

```javascript
// frontend/vite.config.js
server: {
  proxy: {
    '/api': {
      target: 'http://localhost:8080',
      changeOrigin: true
    }
  }
}
```

**代理规则**：
- 请求路径 `/api/*` → 转发至 `http://localhost:8080/api/*`
- 开发环境：前端 `http://localhost:5173` → 后端 `http://localhost:8080`

### 1.2 后端跨域配置

后端通过 `WebConfig` 配置跨域允许列表：

```java
// backend/config/WebConfig.java
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("http://localhost:5173")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*");
    }
}
```

## 2. 智能体配置

### 2.1 代码助手智能体

本项目集成了代码助手智能体，支持以下功能：

| 功能 | 说明 |
|------|------|
| 代码生成 | 根据需求自动生成 Java/Vue 代码 |
| 代码审查 | 检查代码风格和潜在问题 |
| 文档生成 | 自动生成 API 文档和项目规范 |
| 代码重构 | 优化现有代码结构 |

### 2.2 智能体工作流程

```
用户需求 → 智能体分析 → 代码生成/修改 → 规范校验 → 测试验证 → 交付
```

## 3. API 接口代理

### 3.1 接口列表

| 接口路径 | HTTP方法 | 功能描述 |
|----------|----------|----------|
| `/api/users` | GET | 获取用户列表 |
| `/api/users/{id}` | GET | 获取单个用户 |
| `/api/users` | POST | 创建用户 |
| `/api/users/{id}` | PUT | 更新用户 |
| `/api/users/{id}` | DELETE | 删除用户 |

### 3.2 响应格式

统一响应封装：

```json
{
  "code": 200,
  "message": "操作成功",
  "data": {}
}
```

## 4. 环境变量配置

### 4.1 后端环境变量

| 变量名 | 说明 | 默认值 |
|--------|------|--------|
| `SERVER_PORT` | 服务端口 | 8080 |
| `DB_URL` | 数据库连接URL | `jdbc:mysql://localhost:3306/example_db` |
| `DB_USERNAME` | 数据库用户名 | root |
| `DB_PASSWORD` | 数据库密码 | 123456 |

### 4.2 前端环境变量

| 变量名 | 说明 | 默认值 |
|--------|------|--------|
| `VITE_API_BASE_URL` | API 基础地址 | `/api` |

## 5. 部署代理

### 5.1 生产环境

生产环境建议使用反向代理（如 Nginx）：

```nginx
server {
    listen 80;
    server_name example.com;
    
    location / {
        root /path/to/frontend/dist;
        try_files $uri $uri/ /index.html;
    }
    
    location /api/ {
        proxy_pass http://localhost:8080/;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
    }
}
```

### 5.2 Docker 部署

使用 Docker Compose 编排前后端服务：

```yaml
version: '3.8'
services:
  backend:
    build: ./backend
    ports:
      - "8080:8080"
  
  frontend:
    build: ./frontend
    ports:
      - "5173:5173"
```

