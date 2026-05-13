# Vue + Spring Boot 前后端分离演示项目

本项目展示了一个完整的前后端分离架构示例，包含用户管理的基本CRUD操作，适合学习和理解现代化Web开发模式。

## 🎯 为什么选择 Vue.js + Spring Boot + MySQL 技术栈？

当前主流企业级Web开发中，**Vue.js + Spring Boot + MySQL** 组合被广泛采用，成为许多公司的技术选型首选。以下是选择这个技术栈的核心原因：

### 🌟 技术栈优势分析

| 维度           | Vue.js                              | Spring Boot               | MySQL                    |
| -------------- | ----------------------------------- | ------------------------- | ------------------------ |
| **学习曲线**   | 渐进式框架，易于上手                | 约定优于配置，快速开发    | 语法简单，文档丰富       |
| **生态成熟度** | 组件库丰富（Element Plus、Vuetify） | Spring 生态完善，社区活跃 | 市场占有率高，工具链成熟 |
| **性能表现**   | 响应式设计，虚拟DOM优化             | 内嵌Tomcat，性能稳定      | 索引优化，查询效率高     |
| **社区支持**   | 中文文档齐全，国内开发者多          | 官方文档完善，问题易解决  | 资料丰富，遇到问题易搜索 |

### 💡 为什么是这个组合？

**1. Vue.js - 渐进式前端框架**

- **易学易用**：HTML/CSS/JavaScript基础即可上手，学习成本低
- **响应式数据绑定**：简化状态管理，视图自动更新
- **组件化开发**：代码复用率高，项目结构清晰
- **轻量级**：打包体积小，加载速度快
- **国内生态好**：Element Plus等UI库专为中文开发者设计

**2. Spring Boot - 社区最成熟的Java后端框架**

- **约定优于配置**：自动配置减少样板代码，快速搭建项目
- **生态完善**：Spring Security、Spring Data JPA、MyBatis等组件丰富
- **稳定性强**：经过大量企业级项目验证，运行稳定可靠
- **社区活跃**：遇到问题容易找到解决方案
- **适合企业级**：成熟的事务管理、安全框架、监控体系

> **💡 Spring Boot 与 Spring Cloud 的关系**
>
> 当前主流企业级后端架构中，**Spring Cloud** 确实是微服务领域的主流技术栈，但它是建立在 **Spring Boot** 基础之上的：
>
> | 技术             | 定位                | 作用                                    |
> | ---------------- | ------------------- | --------------------------------------- |
> | **Spring Boot**  | 单体/微服务基础框架 | 快速构建单个独立运行的应用              |
> | **Spring Cloud** | 微服务治理框架      | 管理多个Spring Boot应用组成的分布式系统 |
>
> **两者关系**：
>
> - Spring Boot 是"地基"，解决单个应用的快速开发问题
> - Spring Cloud 是"上层建筑"，解决多个应用之间的协同问题
> - Spring Cloud 依赖 Spring Boot，没有 Spring Boot 就没有 Spring Cloud

**3. MySQL - 世界上最流行的开源数据库**

- **开源免费**：降低企业成本，无需license费用
- **性能稳定**：经过多年优化，处理海量数据能力强
- **工具链完善**：Navicat、MySQL Workbench等可视化工具成熟
- **社区支持好**：资料丰富，问题解决方案多
- **生态兼容性强**：与Java生态无缝集成

### 🔄 前后端分离的优势

采用前后端分离架构的核心价值：

1. **职责清晰**：前端专注用户体验，后端专注业务逻辑
2. **独立开发**：前后端团队可并行开发，提升效率
3. **技术选型灵活**：前端可选择Vue/React/Angular，后端可选择Java/Go/Python
4. **易于扩展**：前端可部署CDN，后端可水平扩展
5. **便于测试**：前后端API契约明确，易于自动化测试

***

## 📁 项目结构

### 后端 Spring Boot 项目结构

```
backend/
├── pom.xml                           # Maven 依赖管理文件
└── src/main/
    ├── java/com/example/demo/
    │   ├── DemoApplication.java       # Spring Boot 启动类
    │   ├── controller/                # 控制器层 - 处理HTTP请求
    │   │   └── UserController.java    # 用户模块REST API控制器
    │   ├── service/                   # 服务层 - 业务逻辑处理
    │   │   ├── UserService.java       # 用户服务接口定义
    │   │   └── impl/
    │   │       └── UserServiceImpl.java  # 用户服务实现类
    │   ├── mapper/                    # 数据访问层 - MyBatis接口
    │   │   └── UserMapper.java        # 用户数据访问接口
    │   ├── entity/                    # 实体类 - 数据库表映射
    │   │   └── User.java              # 用户实体
    │   ├── dto/                       # 数据传输对象
    │   │   ├── UserDTO.java           # 用户请求/响应DTO
    │   │   └── ResultDTO.java         # 统一响应封装类
    │   ├── exception/                 # 异常处理
    │   │   ├── BusinessException.java # 业务异常类
    │   │   └── GlobalExceptionHandler.java # 全局异常处理器
    │   └── config/                    # 配置类
    │       └── WebConfig.java         # Web配置(跨域等)
    └── resources/
        ├── application.yml            # 应用配置文件
        ├── mapper/                    # MyBatis XML映射文件
        │   └── UserMapper.xml         # 用户操作SQL映射
        └── schema.sql                 # 数据库初始化脚本
```

### 前端 Vue 项目结构

```
frontend/
├── index.html                         # HTML入口文件
├── package.json                       # npm 依赖管理文件
├── vite.config.js                     # Vite 构建工具配置
└── src/
    ├── main.js                        # Vue 应用入口文件
    ├── App.vue                        # 根组件
    ├── router/                        # 路由配置
    │   └── index.js                   # 路由定义
    ├── api/                           # API 封装
    │   ├── axios.js                   # Axios 请求拦截配置
    │   └── user.js                    # 用户相关API接口封装
    └── views/                         # 视图组件
        ├── UserList.vue               # 用户列表页面
        ├── AddUser.vue                # 添加用户页面
        └── EditUser.vue               # 编辑用户页面
```

***

## 🏗️ 各层结构详解

### 后端 Spring Boot 各层作用

| 层级           | 文件                          | 职责说明                                     |
| -------------- | ----------------------------- | -------------------------------------------- |
| **启动类**     | DemoApplication.java          | Spring Boot 应用入口，负责启动整个后端服务   |
| **控制器层**   | UserController.java           | 接收HTTP请求，参数校验，调用服务层，返回响应 |
| **服务层接口** | UserService.java              | 定义业务方法契约，解耦接口与实现             |
| **服务层实现** | UserServiceImpl.java          | 实现业务逻辑，事务管理，数据校验             |
| **数据访问层** | UserMapper.java               | 数据库CRUD操作，MyBatis接口，配合XML映射文件 |
| **实体类**     | User.java                     | **普通POJO实体，映射数**据库表结构           |
| **DTO**        | UserDTO.java / ResultDTO.java | 数据传输对象，隔离内部实体与外部接口         |
| **异常处理**   | GlobalExceptionHandler.java   | 统一异常捕获，返回标准化错误响应             |
| **配置类**     | WebConfig.java                | 跨域配置、过滤器等Web相关配置                |

### 前端 Vue 各组件作用

| 文件                   | 职责说明                                 |
| ---------------------- | ---------------------------------------- |
| **main.js**            | 创建Vue实例，配置路由，挂载应用          |
| **App.vue**            | 根组件，定义全局布局和导航               |
| **router/index.js**    | 路由配置，定义页面路径与组件映射         |
| **api/axios.js**       | Axios封装，配置请求拦截器和响应拦截器    |
| **api/user.js**        | 用户相关API接口封装，统一管理API调用     |
| **views/UserList.vue** | 用户列表页面，展示用户数据，支持编辑删除 |
| **views/AddUser.vue**  | 添加用户页面，表单验证，提交创建请求     |
| **views/EditUser.vue** | 编辑用户页面，获取用户信息，提交更新请求 |

### 📚 分层架构的意义

#### 为什么 Spring Boot 要分层？

分层架构是一种经典的软件设计模式，其核心思想是**关注点分离**。在 Spring Boot 中采用分层设计有以下重要意义：

**1. 高内聚，低耦合**

- 每个层级只关注自己的职责，不越界处理其他层的事情
- 层与层之间通过接口进行交互，降低依赖关系

**2. 便于维护和扩展**

- 业务逻辑变更时，只需修改 Service 层，不影响 Controller 和 Mapper
- 需要更换数据库时，只需修改 Mapper 层，不影响上层业务

**3. 提高代码复用性**

- Service 层的业务逻辑可以被多个 Controller 调用
- 通用的异常处理、数据校验逻辑可以统一处理

**4. 便于单元测试**

- 每层都可以独立进行单元测试
- Mock 数据时只需关注当前层的依赖

**5. 团队协作高效**

- 不同开发者可以负责不同的层级
- 前端开发人员只需关注 API 接口（Controller 层）
- 数据库开发人员只需关注数据访问层（Mapper）

#### Entity 与 DTO 的区别与作用

| 特性         | Entity (实体类)                    | DTO (数据传输对象)                   |
| ------------ | ---------------------------------- | ------------------------------------ |
| **用途**     | 映射数据库表结构，与数据库直接交互 | 在不同层级之间传输数据，隔离内部实现 |
| **字段**     | 与数据库表字段一一对应             | 根据业务需求灵活定义字段             |
| **生命周期** | 与数据库事务绑定                   | 请求开始时创建，响应结束时销毁       |
| **安全性**   | 可能包含敏感字段（如密码）         | 只包含需要暴露的数据                 |
| **序列化**   | 一般不直接序列化返回               | 专门设计用于 JSON 序列化             |

**Entity 的作用：**

- 作为数据库表的直接映射，包含表的所有字段
- 用于 MyBatis/Hibernate 等 ORM 框架进行数据持久化
- 保持与数据库结构的一致性

**DTO 的作用：**

- **数据隔离**：防止内部实体结构暴露给外部
- **字段裁剪**：只返回前端需要的字段，减少数据传输量
- **格式转换**：将内部数据格式转换为前端需要的格式
- **敏感信息保护**：过滤掉密码等敏感字段
- **版本兼容**：API 接口可以保持稳定，内部实体可独立演进

**典型使用场景：**

```
┌─────────────────────────────────────────────────────────────────┐
│  请求流程: Frontend → Controller → Service → Mapper → Database │
├─────────────────────────────────────────────────────────────────┤
│  UserDTO (请求)       UserDTO           UserEntity             │
│  {username, email}    → 校验处理       → {id, username,       │
│                                        password, email, ...}  │
├─────────────────────────────────────────────────────────────────┤
│  响应流程: Database → Mapper → Service → Controller → Frontend │
├─────────────────────────────────────────────────────────────────┤
│  UserEntity           UserEntity        UserDTO (响应)         │
│  {id, username,       → 业务处理       → {id, username,       │
│  password, email, ...}                  email, createdAt}     │
└─────────────────────────────────────────────────────────────────┘
```

**ResultDTO（统一响应封装）的作用：**

- 统一所有 API 响应格式，便于前端处理
- 包含状态码、提示信息和数据字段
- 便于全局异常处理和日志记录

***

## 🛠️ 技术栈

### 后端技术栈

| 技术        | 版本   | 说明         |
| ----------- | ------ | ------------ |
| Java        | 8      | 编程语言     |
| Spring Boot | 2.7.18 | 后端框架     |
| MyBatis     | 2.3.0  | 数据访问层   |
| MySQL       | 8.0+   | 关系型数据库 |
| Lombok      | 1.18.x | 简化代码     |

### 前端技术栈

| 技术       | 版本  | 说明       |
| ---------- | ----- | ---------- |
| Vue        | 3.x   | 前端框架   |
| Vue Router | 4.x   | 路由管理   |
| Axios      | 1.6.x | HTTP客户端 |
| Bootstrap  | 5.3.x | CSS框架    |
| Vite       | 5.x   | 构建工具   |

## 🔄 前后端交互流程

```
┌─────────────────────────────────────────────────────────────────────┐
│                        用户操作流程                                  │
├─────────────────────────────────────────────────────────────────────┤
│  前端页面 (Vue)                                                     │
│       │                                                            │
│       │ 1. 用户点击"获取用户列表"                                    │
│       ▼                                                            │
│  axios.get('/users')                                               │
│       │                                                            │
│       │ 2. 请求发送到 /api/users (Vite代理)                         │
│       ▼                                                            │
│  http://localhost:8080/api/users                                   │
│       │                                                            │
│       │ 3. 后端Controller接收请求                                  │
│       ▼                                                            │
│  UserController.getAllUsers()                                      │
│       │                                                            │
│       │ 4. 调用Service层处理业务                                    │
│       ▼                                                            │
│  UserService.getAllUsers()                                         │
│       │                                                            │
│       │ 5. 调用Mapper层访问数据库                                  │
│       ▼                                                            │
│  UserMapper.findAll()                                              │
│       │                                                            │
│       │ 6. 查询MySQL数据库                                          │
│       ▼                                                            │
│  SELECT * FROM user                                                │
│       │                                                            │
│       │ 7. 返回用户列表数据                                         │
│       ▼                                                            │
│  ResultDTO {code: 200, message: "查询成功", data: [...]}           │
│       │                                                            │
│       │ 8. 前端接收响应，更新视图                                   │
│       ▼                                                            │
│  Vue组件渲染用户列表                                                │
└─────────────────────────────────────────────────────────────────────┘
```

### 各环节详细说明

下面用简单的话解释每个步骤在做什么：

| 步骤 | 环节       | 这一步在做什么                 | 举个通俗的例子               |
| ---- | ---------- | ------------------------------ | ---------------------------- |
| 1    | 前端页面   | 用户在网页表单中填写信息       | 你在注册页面输入用户名、邮箱 |
| 2    | 组装请求   | 把用户填写的数据组装成请求格式 | 把填好的信息整理好装进"信封" |
| 3    | 发送请求   | 将请求发送到后端服务器         | 把"信封"寄到公司总部         |
| 4    | 接收请求   | 后端接收并检查数据格式         | 前台收到信件，检查格式对不对 |
| 5    | 业务检查   | 检查数据是否符合业务规则       | 检查用户名是否已被注册       |
| 6    | 存储数据   | 将数据存入数据库               | 把用户信息录入公司档案系统   |
| 7    | 数据库写入 | 数据库执行插入操作             | 档案管理员把信息存进文件柜   |
| 8    | 封装响应   | 准备返回结果给前端             | 准备回复信件                 |
| 9    | 返回结果   | 将结果返回给前端               | 把回复信件寄回给用户         |
| 10   | 显示结果   | 前端显示操作结果               | 用户看到"注册成功"的提示     |

**简单总结**：就像你去银行开户，你填表格（前端）→ 柜员检查你的资料（后端）→ 系统记录你的信息（数据库）→ 告诉你开户成功（返回结果）。

***

## 🚀 运行步骤

### 0. 前置环境准备

#### 0.1 安装 JDK 8

**下载地址**：

- Oracle JDK 8：<https://www.oracle.com/java/technologies/downloads/#java8>

**配置环境变量**：

1. 右键"此电脑" → "属性" → "高级系统设置" → "环境变量"

2. 在"系统变量"中新建：

   - **变量名**：`JAVA_HOME`
   - **变量值**：JDK 安装路径（如 `C:\Program Files\Java\jdk1.8.0_381`）

3. 在"系统变量"的 `Path` 中添加：

   - `%JAVA_HOME%\bin`

4. 验证安装：

   ```bash
   java -version
   ```

   显示类似：`java version "1.8.0_381"`

#### 0.2 安装 Maven

**下载地址**：<https://maven.apache.org/download.cgi>

**配置步骤**：

1. 解压 Maven 到任意目录（如 `D:\apache-maven-3.9.6`）

2. 配置环境变量：

   - 新建系统变量 `MAVEN_HOME`，值为 Maven 路径
   - 在 `Path` 中添加 `%MAVEN_HOME%\bin`

3. 验证安装：

   ```bash
   mvn -v
   ```

   显示 Maven 版本信息

**配置 Maven 镜像（可选，推荐）**：

编辑 `conf/settings.xml` 文件，添加阿里云镜像：

```xml
<mirrors>
    <mirror>
        <id>aliyunmaven</id>
        <mirrorOf>central</mirrorOf>
        <url>https://maven.aliyun.com/repository/public</url>
    </mirror>
</mirrors>
```

#### 0.3 使用 Navicat 连接 MySQL

**前提条件**：已安装 MySQL 和 Navicat

**连接步骤**：

1. 打开 Navicat，点击"连接" → "MySQL"
2. 填写连接信息：
   - **连接名**：`example_db`（自定义）
   - **主机**：`localhost`
   - **端口**：`3306`
   - **用户名**：`root`（或你的数据库用户名）
   - **密码**：你的数据库密码
3. 点击"连接测试"，确认连接成功
4. 点击"确定"保存连接

### 1. 数据库配置

#### 1.1 创建数据库

在 Navicat 中：

1. 双击刚才创建的连接，打开数据库列表
2. 右键点击连接名 → "新建数据库"
3. 填写：
   - **数据库名**：`example_db`
   - **字符集**：`utf8mb4`
   - **排序规则**：`utf8mb4_unicode_ci`
4. 点击"确定"

#### 1.2 配置后端数据库连接

修改 `backend/src/main/resources/application.yml`：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/example_db?useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai
    username: root
    password: 123456  # 替换为你的数据库密码
```

### 2. 初始化数据表

#### 2.1 创建表结构

执行 `backend/src/main/resources/schema.sql` 文件中的 SQL 脚本

#### 2.2 插入初始数据

执行 `backend/src/main/resources/data.sql` 文件中的 SQL 脚本

### 4. 常见问题排查

**问题1：数据库连接失败**

- 确保 MySQL 服务已启动
- 检查 `application.yml` 中的数据库配置是否正确
- 确保数据库 `example_db` 已创建

**问题2：前端无法访问后端 API**

- 确保后端服务已启动在 `http://localhost:8080`
- 检查 `vite.config.js` 中的代理配置是否正确
- 查看浏览器控制台是否有 CORS 错误

**问题3：MyBatis 映射文件找不到**

- 确保 `UserMapper.xml` 文件位于 `src/main/resources/mapper/` 目录下
- 检查 `application.yml` 中的 `mapper-locations` 配置是否正确

**问题4：Maven 依赖下载慢**

- 配置阿里云 Maven 镜像（参考 1.2 节）
- 确保网络连接正常

***

## 🌐 API 接口

| 方法   | 路径            | 描述             |
| ------ | --------------- | ---------------- |
| GET    | /api/users      | 获取所有用户列表 |
| GET    | /api/users/{id} | 根据ID获取用户   |
| POST   | /api/users      | 创建新用户       |
| PUT    | /api/users/{id} | 更新用户信息     |
| DELETE | /api/users/{id} | 删除用户         |

**请求示例 (POST /api/users)：**

```json
npm run build -- --skip-eslint
```

**响应示例：**

```json
{
    "code": 200,
    "message": "创建成功",
    "data": {
        "id": 4,
        "username": "新用户",
        "email": "newuser@example.com",
        "age": 25,
        "createdAt": "2024-01-01 10:00:00",
        "updatedAt": "2024-01-01 10:00:00"
    }
}
```

***

## ✨ 功能说明

1. **用户列表展示** - 显示所有用户信息，支持编辑和删除操作
2. **添加用户** - 表单验证（用户名、邮箱格式校验），创建新用户
3. **编辑用户** - 回显用户信息，修改后提交更新
4. **删除用户** - 确认提示后删除用户

***

## 🔒 跨域处理

后端配置了 CORS 过滤器，允许前端应用跨域访问：

```java
@Configuration
public class WebConfig {
    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOriginPattern("*");      // 允许所有来源
        config.addAllowedMethod("*");            // 允许所有HTTP方法
        config.addAllowedHeader("*");            // 允许所有请求头
        config.setAllowCredentials(true);        // 支持携带Cookie
        config.setMaxAge(3600L);                // 预检请求缓存时间
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
```

***

## 📊 统一响应格式

所有 API 响应都遵循统一格式：

```json
{
    "code": 200,
    "message": "操作成功",
    "data": {}
}
```

| 字段    | 说明                                                         |
| ------- | ------------------------------------------------------------ |
| code    | 状态码（200成功，400参数错误，404资源未找到，500服务器错误） |
| message | 提示信息                                                     |
| data    | 响应数据                                                     |

