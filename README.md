# Vue + Spring Boot 前后端分离演示项目

本项目展示了一个完整的前后端分离架构示例，包含用户管理的基本CRUD操作，适合学习和理解现代化Web开发模式。

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

| 层级        | 文件                            | 职责说明                          |
| --------- | ----------------------------- | ----------------------------- |
| **启动类**   | DemoApplication.java          | Spring Boot 应用入口，负责启动整个后端服务   |
| **控制器层**  | UserController.java           | 接收HTTP请求，参数校验，调用服务层，返回响应      |
| **服务层接口** | UserService.java              | 定义业务方法契约，解耦接口与实现              |
| **服务层实现** | UserServiceImpl.java          | 实现业务逻辑，事务管理，数据校验              |
| **数据访问层** | UserMapper.java               | 数据库CRUD操作，MyBatis接口，配合XML映射文件 |
| **实体类**   | User.java                     | **普通POJO实体，映射数**据库表结构         |
| **DTO**   | UserDTO.java / ResultDTO.java | 数据传输对象，隔离内部实体与外部接口            |
| **异常处理**  | GlobalExceptionHandler.java   | 统一异常捕获，返回标准化错误响应              |
| **配置类**   | WebConfig.java                | 跨域配置、过滤器等Web相关配置              |

### 前端 Vue 各组件作用

| 文件                     | 职责说明                  |
| ---------------------- | --------------------- |
| **main.js**            | 创建Vue实例，配置路由，挂载应用     |
| **App.vue**            | 根组件，定义全局布局和导航         |
| **router/index.js**    | 路由配置，定义页面路径与组件映射      |
| **api/axios.js**       | Axios封装，配置请求拦截器和响应拦截器 |
| **api/user.js**        | 用户相关API接口封装，统一管理API调用 |
| **views/UserList.vue** | 用户列表页面，展示用户数据，支持编辑删除  |
| **views/AddUser.vue**  | 添加用户页面，表单验证，提交创建请求    |
| **views/EditUser.vue** | 编辑用户页面，获取用户信息，提交更新请求  |

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

| 特性 | Entity (实体类) | DTO (数据传输对象) |
| --- | --- | --- |
| **用途** | 映射数据库表结构，与数据库直接交互 | 在不同层级之间传输数据，隔离内部实现 |
| **字段** | 与数据库表字段一一对应 | 根据业务需求灵活定义字段 |
| **生命周期** | 与数据库事务绑定 | 请求开始时创建，响应结束时销毁 |
| **安全性** | 可能包含敏感字段（如密码） | 只包含需要暴露的数据 |
| **序列化** | 一般不直接序列化返回 | 专门设计用于 JSON 序列化 |

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

#### 🤔 为什么没有 VO？

**VO（View Object，视图对象）** 是专门为前端视图展示设计的数据对象，通常用于封装需要在页面上展示的数据。本项目中没有单独定义 VO，主要有以下原因：

**1. DTO 已承担 VO 的职责**
- 在本项目中，`UserDTO` 等响应 DTO 实际上已经承担了 VO 的角色
- 响应 DTO 只包含前端需要展示的字段（如 `id`, `username`, `email`, `createdAt`）
- 过滤掉了敏感字段（如 `password`）和不需要的内部字段

**2. 项目规模较小**
- 对于小型项目，DTO 和 VO 的职责可以合并
- 当业务复杂、需要在不同视图展示不同数据时，才需要单独定义 VO

**3. DTO 与 VO 的区别**

| 特性 | DTO (数据传输对象) | VO (视图对象) |
| --- | --- | --- |
| **用途** | 在不同层级间传输数据 | 专为前端视图展示设计 |
| **关注点** | 数据传输和接口契约 | 页面展示和用户体验 |
| **使用场景** | 请求参数、响应数据 | 页面渲染、报表展示 |
| **生命周期** | 请求/响应周期 | 视图渲染周期 |

**4. 何时需要引入 VO？**

当项目满足以下条件时，可以考虑引入 VO：

- **复杂展示需求**：同一实体需要在不同页面展示不同字段
- **数据聚合**：需要从多个实体中提取数据组合成展示对象
- **格式转换**：需要对数据进行格式化处理（如日期格式、金额格式）
- **权限控制**：不同角色看到的数据字段不同

**示例：引入 VO 的场景**

```java
// UserDTO - 用于数据传输
public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private LocalDateTime createdAt;
}

// UserVO - 用于页面展示（增加格式化字段）
public class UserVO {
    private Long id;
    private String username;
    private String email;
    private String createdAtStr;  // 格式化后的日期字符串
    private String statusDesc;    // 状态描述（ACTIVE -> "正常"）
}
```

**总结**：在本项目中，DTO 已经足够满足需求，无需额外引入 VO。当项目规模扩大、展示需求变得复杂时，可以考虑分离 DTO 和 VO 的职责。

***

## 🛠️ 技术栈

### 后端技术栈

| 技术          | 版本     | 说明     |
| ----------- | ------ | ------ |
| Java        | 8      | 编程语言   |
| Spring Boot | 2.7.18 | 后端框架   |
| MyBatis     | 2.3.0  | 数据访问层  |
| MySQL       | 8.0+   | 关系型数据库 |
| Lombok      | 1.18.x | 简化代码   |

### 前端技术栈

| 技术         | 版本    | 说明      |
| ---------- | ----- | ------- |
| Vue        | 3.x   | 前端框架    |
| Vue Router | 4.x   | 路由管理    |
| Axios      | 1.6.x | HTTP客户端 |
| Bootstrap  | 5.3.x | CSS框架   |
| Vite       | 5.x   | 构建工具    |

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

| 步骤 | 环节    | 这一步在做什么         | 举个通俗的例子         |
| -- | ----- | --------------- | --------------- |
| 1  | 前端页面  | 用户在网页表单中填写信息    | 你在注册页面输入用户名、邮箱  |
| 2  | 组装请求  | 把用户填写的数据组装成请求格式 | 把填好的信息整理好装进"信封" |
| 3  | 发送请求  | 将请求发送到后端服务器     | 把"信封"寄到公司总部     |
| 4  | 接收请求  | 后端接收并检查数据格式     | 前台收到信件，检查格式对不对  |
| 5  | 业务检查  | 检查数据是否符合业务规则    | 检查用户名是否已被注册     |
| 6  | 存储数据  | 将数据存入数据库        | 把用户信息录入公司档案系统   |
| 7  | 数据库写入 | 数据库执行插入操作       | 档案管理员把信息存进文件柜   |
| 8  | 封装响应  | 准备返回结果给前端       | 准备回复信件          |
| 9  | 返回结果  | 将结果返回给前端        | 把回复信件寄回给用户      |
| 10 | 显示结果  | 前端显示操作结果        | 用户看到"注册成功"的提示   |

**简单总结**：就像你去银行开户，你填表格（前端）→ 柜员检查你的资料（后端）→ 系统记录你的信息（数据库）→ 告诉你开户成功（返回结果）。

***

## 🚀 运行步骤

### 0. 前置环境准备

#### 0.1 安装 JDK 8

**下载地址**：

- Oracle JDK 8：<https://www.oracle.com/java/technologies/downloads/#java8>
- OpenJDK 8：<https://adoptium.net/temurin/releases/?version=8>

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

执行 `backend/src/main/resources/schema.sql` 文件中的 SQL 脚本：

```sql
CREATE TABLE IF NOT EXISTS `user` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '用户ID',
    `username` VARCHAR(50) NOT NULL UNIQUE COMMENT '用户名',
    `password` VARCHAR(255) NOT NULL COMMENT '密码(BCrypt加密)',
    `nickname` VARCHAR(50) DEFAULT NULL COMMENT '昵称',
    `email` VARCHAR(100) NOT NULL UNIQUE COMMENT '邮箱',
    `phone` VARCHAR(20) DEFAULT NULL COMMENT '联系电话',
    `role_id` BIGINT DEFAULT 3 COMMENT '角色ID',
    `status` VARCHAR(20) NOT NULL DEFAULT 'ACTIVE' COMMENT '状态(ACTIVE/DISABLED)',
    `age` INT DEFAULT NULL COMMENT '年龄',
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX `idx_username` (`username`),
    INDEX `idx_email` (`email`),
    INDEX `idx_role_id` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户表';

CREATE TABLE IF NOT EXISTS `role` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '角色ID',
    `role_name` VARCHAR(50) NOT NULL UNIQUE COMMENT '角色名称',
    `role_code` VARCHAR(50) NOT NULL UNIQUE COMMENT '角色编码',
    `description` VARCHAR(200) DEFAULT NULL COMMENT '角色描述',
    `permissions` TEXT DEFAULT NULL COMMENT '权限标识(逗号分隔)',
    `enabled` TINYINT(1) NOT NULL DEFAULT 1 COMMENT '是否启用',
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX `idx_role_code` (`role_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='角色表';

CREATE TABLE IF NOT EXISTS `major` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '专业ID',
    `major_name` VARCHAR(100) NOT NULL UNIQUE COMMENT '专业名称',
    `major_code` VARCHAR(50) NOT NULL UNIQUE COMMENT '专业代码',
    `college` VARCHAR(100) DEFAULT NULL COMMENT '所属学院',
    `training_program` TEXT DEFAULT NULL COMMENT '培养方案',
    `duration` INT DEFAULT 4 COMMENT '学制(年)',
    `enabled` TINYINT(1) NOT NULL DEFAULT 1 COMMENT '是否启用',
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX `idx_major_code` (`major_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='专业表';

CREATE TABLE IF NOT EXISTS `class_info` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '班级ID',
    `class_name` VARCHAR(100) NOT NULL UNIQUE COMMENT '班级名称',
    `major_id` BIGINT NOT NULL COMMENT '所属专业ID',
    `major_name` VARCHAR(100) NOT NULL COMMENT '专业名称(冗余)',
    `grade` VARCHAR(20) NOT NULL COMMENT '年级',
    `teacher_id` BIGINT DEFAULT NULL COMMENT '班主任ID',
    `teacher_name` VARCHAR(50) DEFAULT NULL COMMENT '班主任姓名(冗余)',
    `student_count` INT NOT NULL DEFAULT 0 COMMENT '班级人数',
    `enabled` TINYINT(1) NOT NULL DEFAULT 1 COMMENT '是否启用',
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX `idx_major_id` (`major_id`),
    INDEX `idx_teacher_id` (`teacher_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='班级表';

CREATE TABLE IF NOT EXISTS `teacher` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '教师ID',
    `teacher_no` VARCHAR(50) NOT NULL UNIQUE COMMENT '工号',
    `name` VARCHAR(50) NOT NULL COMMENT '姓名',
    `gender` VARCHAR(10) DEFAULT NULL COMMENT '性别',
    `title` VARCHAR(50) DEFAULT NULL COMMENT '职称',
    `major_id` BIGINT DEFAULT NULL COMMENT '所属专业ID',
    `major_name` VARCHAR(100) DEFAULT NULL COMMENT '专业名称(冗余)',
    `college` VARCHAR(100) DEFAULT NULL COMMENT '所属学院',
    `phone` VARCHAR(20) DEFAULT NULL COMMENT '联系电话',
    `email` VARCHAR(100) DEFAULT NULL COMMENT '邮箱',
    `enabled` TINYINT(1) NOT NULL DEFAULT 1 COMMENT '是否启用',
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX `idx_teacher_no` (`teacher_no`),
    INDEX `idx_major_id` (`major_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='教师表';

CREATE TABLE IF NOT EXISTS `student` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '学生ID',
    `student_no` VARCHAR(50) NOT NULL UNIQUE COMMENT '学号',
    `name` VARCHAR(50) NOT NULL COMMENT '姓名',
    `gender` VARCHAR(10) DEFAULT NULL COMMENT '性别',
    `birth_date` DATE DEFAULT NULL COMMENT '出生日期',
    `class_id` BIGINT NOT NULL COMMENT '班级ID',
    `class_name` VARCHAR(100) NOT NULL COMMENT '班级名称(冗余)',
    `major_id` BIGINT NOT NULL COMMENT '专业ID',
    `major_name` VARCHAR(100) NOT NULL COMMENT '专业名称(冗余)',
    `grade` VARCHAR(20) NOT NULL COMMENT '年级',
    `phone` VARCHAR(20) DEFAULT NULL COMMENT '联系电话',
    `email` VARCHAR(100) DEFAULT NULL COMMENT '邮箱',
    `status` VARCHAR(20) NOT NULL DEFAULT '在读' COMMENT '状态(在读/休学/毕业)',
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX `idx_student_no` (`student_no`),
    INDEX `idx_class_id` (`class_id`),
    INDEX `idx_major_id` (`major_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='学生表';
```

#### 2.2 插入初始数据

执行 `backend/src/main/resources/data.sql` 文件中的 SQL 脚本：

```sql
INSERT IGNORE INTO role (id, role_name, role_code, description, permissions, enabled, created_at, updated_at) VALUES
(1, '管理员', 'ADMIN', '系统管理员，拥有最高权限', 'user:manage,role:manage,major:manage,class:manage,teacher:manage,student:manage', 1, NOW(), NOW()),
(2, '教师', 'TEACHER', '任课教师', 'student:view,class:view,major:view', 1, NOW(), NOW()),
(3, '学生', 'STUDENT', '在校学生', 'student:self,class:view', 1, NOW(), NOW());

INSERT INTO major (id, major_name, major_code, college, training_program, duration, enabled, created_at, updated_at) VALUES
(1, '计算机科学与技术', 'CS', '信息学院', '培养具备计算机科学与技术基础理论、专业知识和实践能力的高级专门人才', 4, 1, NOW(), NOW()),
(2, '软件工程', 'SE', '信息学院', '培养掌握软件工程基本理论和专业知识，具备软件开发能力的工程技术人才', 4, 1, NOW(), NOW()),
(3, '网络工程', 'NE', '信息学院', '培养掌握网络技术、网络安全等方面知识的高级技术人才', 4, 1, NOW(), NOW()),
(4, '数据科学与大数据技术', 'DS', '信息学院', '培养掌握数据科学理论和大数据技术的复合型人才', 4, 1, NOW(), NOW()),
(5, '电子信息工程', 'EE', '电子工程学院', '培养掌握电子信息领域基本理论和技术的高级工程技术人才', 4, 1, NOW(), NOW());

INSERT INTO teacher (id, teacher_no, name, gender, title, major_id, major_name, college, phone, email, enabled, created_at, updated_at) VALUES
(1, 'T001', '张明', '男', '教授', 1, '计算机科学与技术', '信息学院', '13800138001', 'zhangming@example.com', 1, NOW(), NOW()),
(2, 'T002', '李华', '女', '副教授', 1, '计算机科学与技术', '信息学院', '13800138002', 'lihua@example.com', 1, NOW(), NOW()),
(3, 'T003', '王强', '男', '讲师', 2, '软件工程', '信息学院', '13800138003', 'wangqiang@example.com', 1, NOW(), NOW()),
(4, 'T004', '陈芳', '女', '副教授', 3, '网络工程', '信息学院', '13800138004', 'chenfang@example.com', 1, NOW(), NOW()),
(5, 'T005', '刘伟', '男', '教授', 4, '数据科学与大数据技术', '信息学院', '13800138005', 'liuwei@example.com', 1, NOW(), NOW());

INSERT INTO class_info (id, class_name, major_id, major_name, grade, teacher_id, teacher_name, student_count, enabled, created_at, updated_at) VALUES
(1, '2024级计算机1班', 1, '计算机科学与技术', '2024', 1, '张明', 35, 1, NOW(), NOW()),
(2, '2024级计算机2班', 1, '计算机科学与技术', '2024', 2, '李华', 32, 1, NOW(), NOW()),
(3, '2024级软件工程1班', 2, '软件工程', '2024', 3, '王强', 30, 1, NOW(), NOW()),
(4, '2023级计算机1班', 1, '计算机科学与技术', '2023', 1, '张明', 34, 1, NOW(), NOW()),
(5, '2024级网络工程1班', 3, '网络工程', '2024', 4, '陈芳', 28, 1, NOW(), NOW()),
(6, '2024级数据科学1班', 4, '数据科学与大数据技术', '2024', 5, '刘伟', 31, 1, NOW(), NOW());

INSERT INTO student (id, student_no, name, gender, birth_date, class_id, class_name, major_id, major_name, grade, phone, email, status, created_at, updated_at) VALUES
(1, '2024001', '张三', '男', '2005-03-15', 1, '2024级计算机1班', 1, '计算机科学与技术', '2024', '13900139001', 'zhangsan@example.com', '在读', NOW(), NOW()),
(2, '2024002', '李四', '女', '2005-06-20', 1, '2024级计算机1班', 1, '计算机科学与技术', '2024', '13900139002', 'lisi@example.com', '在读', NOW(), NOW()),
(3, '2024003', '王五', '男', '2005-09-10', 1, '2024级计算机1班', 1, '计算机科学与技术', '2024', '13900139003', 'wangwu@example.com', '在读', NOW(), NOW()),
(4, '2024004', '赵六', '女', '2005-12-05', 2, '2024级计算机2班', 1, '计算机科学与技术', '2024', '13900139004', 'zhaoliu@example.com', '在读', NOW(), NOW()),
(5, '2024005', '孙七', '男', '2006-01-20', 2, '2024级计算机2班', 1, '计算机科学与技术', '2024', '13900139005', 'sunqi@example.com', '在读', NOW(), NOW()),
(6, '2024006', '周八', '女', '2005-04-18', 3, '2024级软件工程1班', 2, '软件工程', '2024', '13900139006', 'zhouba@example.com', '在读', NOW(), NOW()),
(7, '2024007', '吴九', '男', '2005-07-25', 3, '2024级软件工程1班', 2, '软件工程', '2024', '13900139007', 'wujiu@example.com', '在读', NOW(), NOW()),
(8, '2024008', '郑十', '女', '2005-10-08', 4, '2023级计算机1班', 1, '计算机科学与技术', '2023', '13900139008', 'zhengshi@example.com', '在读', NOW(), NOW()),
(9, '2024009', '钱十一', '男', '2004-11-30', 4, '2023级计算机1班', 1, '计算机科学与技术', '2023', '13900139009', 'qianshiyi@example.com', '在读', NOW(), NOW()),
(10, '2024010', '刘十二', '女', '2005-02-14', 5, '2024级网络工程1班', 3, '网络工程', '2024', '13900139010', 'liushier@example.com', '在读', NOW(), NOW()),
(11, '2024011', '陈十三', '男', '2005-05-12', 6, '2024级数据科学1班', 4, '数据科学与大数据技术', '2024', '13900139011', 'chenshisan@example.com', '在读', NOW(), NOW()),
(12, '2024012', '杨十四', '女', '2005-08-08', 1, '2024级计算机1班', 1, '计算机科学与技术', '2024', '13900139012', 'yangshisi@example.com', '在读', NOW(), NOW()),
(13, '2024013', '黄十五', '男', '2005-11-22', 1, '2024级计算机1班', 1, '计算机科学与技术', '2024', '13900139013', 'huangshiwu@example.com', '休学', NOW(), NOW()),
(14, '2024014', '林十六', '女', '2005-01-05', 2, '2024级计算机2班', 1, '计算机科学与技术', '2024', '13900139014', 'linshiliu@example.com', '在读', NOW(), NOW()),
(15, '2024015', '何十七', '男', '2005-04-30', 3, '2024级软件工程1班', 2, '软件工程', '2024', '13900139015', 'heshiyi@example.com', '在读', NOW(), NOW());

INSERT INTO user (id, username, password, nickname, email, phone, role_id, status, age, created_at, updated_at) VALUES
(1, 'admin', '$2a$10$N9qo8uLOickgx2ZMRZoMye.IjzqAKL9xL5jvMFVdNJHvGCgTq/VEq', '管理员', 'admin@example.com', '13800138000', 1, 'ACTIVE', 35, NOW(), NOW()),
(2, 'zhangming', '$2a$10$N9qo8uLOickgx2ZMRZoMye.IjzqAKL9xL5jvMFVdNJHvGCgTq/VEq', '张明', 'zhangming@example.com', '13800138001', 2, 'ACTIVE', 45, NOW(), NOW()),
(3, 'lihua', '$2a$10$N9qo8uLOickgx2ZMRZoMye.IjzqAKL9xL5jvMFVdNJHvGCgTq/VEq', '李华', 'lihua@example.com', '13800138002', 2, 'ACTIVE', 38, NOW(), NOW()),
(4, 'zhangsan', '$2a$10$N9qo8uLOickgx2ZMRZoMye.IjzqAKL9xL5jvMFVdNJHvGCgTq/VEq', '张三', 'zhangsan@example.com', '13900139001', 3, 'ACTIVE', 20, NOW(), NOW()),
(5, 'lisi', '$2a$10$N9qo8uLOickgx2ZMRZoMye.IjzqAKL9xL5jvMFVdNJHvGCgTq/VEq', '李四', 'lisi@example.com', '13900139002', 3, 'ACTIVE', 19, NOW(), NOW());
```

### 3. 启动后端服务

**方式一：使用命令行**

```bash
cd backend
mvn spring-boot:run
```

**方式二：使用 IntelliJ IDEA**

1. 打开项目，等待 Maven 依赖下载完成
2. 找到 `DemoApplication.java`，点击绿色运行按钮

**方式三：使用 Trae IDE**

1. 打开 Trae IDE，进入项目工作空间
2. 在左侧文件树中找到 `backend/pom.xml`
3. 右键点击 `pom.xml`，选择「运行 Maven」→ 输入 `spring-boot:run` 并执行
4. 或在终端中执行：
   ```bash
   mvn spring-boot:run
   ```

启动成功后显示：

```
Started DemoApplication in X.X seconds
```

后端服务将在 `http://localhost:8080` 启动。

### 4. 启动前端服务

**方式一：使用命令行**

```bash
cd frontend
npm install   # 首次运行安装依赖
npm run dev   # 启动开发服务器
```

**方式二：使用 Trae IDE**

1. 在 Trae IDE 中打开新终端
2. 切换到前端目录：
   ```bash
   cd frontend
   ```
3. 首次运行需安装依赖：
   ```bash
   npm install
   ```
4. 启动开发服务器：
   ```bash
   npm run dev
   ```

启动成功后显示：

```
VITE v5.x.x  ready in X ms
➜  Local:   http://localhost:5173/
```

前端服务将在 `http://localhost:5173` 启动。

### 5. 验证项目运行

1. 打开浏览器，访问 `http://localhost:5173`
2. 应该能看到用户列表页面，显示初始化的3个用户数据
3. 尝试点击"添加用户"按钮，填写表单并提交，验证新增功能
4. 尝试编辑和删除用户，验证编辑和删除功能

### 6. 常见问题排查

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

| 方法     | 路径              | 描述       |
| ------ | --------------- | -------- |
| GET    | /api/users      | 获取所有用户列表 |
| GET    | /api/users/{id} | 根据ID获取用户 |
| POST   | /api/users      | 创建新用户    |
| PUT    | /api/users/{id} | 更新用户信息   |
| DELETE | /api/users/{id} | 删除用户     |

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

| 字段      | 说明                                   |
| ------- | ------------------------------------ |
| code    | 状态码（200成功，400参数错误，404资源未找到，500服务器错误） |
| message | 提示信息                                 |
| data    | 响应数据                                 |

