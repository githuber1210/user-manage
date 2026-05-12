## 1. 基础设施搭建

- [x] 1.1 创建 `src/composables/useTheme.js` 主题状态管理 composable
- [x] 1.2 在 `main.js` 中初始化主题状态，在 app.mount 前读取 localStorage

## 2. CSS 变量定义

- [x] 2.1 在 `App.vue` 中添加 `[data-theme="dark"]` CSS 变量块，定义暗黑模式颜色
- [x] 2.2 为暗黑模式定义侧边栏相关变量（sidebar-bg, sidebar-hover, sidebar-text 等）
- [x] 2.3 为暗黑模式定义内容区域变量（content-bg, card-bg, text-primary 等）
- [x] 2.4 为暗黑模式定义交互元素变量（accent, primary, danger, success 等）

## 3. 切换按钮实现

- [x] 3.1 在 `App.vue` topbar-right 区域添加主题切换按钮
- [x] 3.2 实现太阳/月亮图标切换显示逻辑
- [x] 3.3 绑定按钮点击事件调用 useTheme 的 toggle 方法

## 4. 主题状态管理

- [x] 4.1 实现 localStorage 读写功能，存储 theme 值
- [x] 4.2 实现 prefers-color-scheme 系统主题检测
- [x] 4.3 实现 matchMedia 变化事件监听
- [x] 4.4 实现手动设置覆盖系统偏好的逻辑

## 5. 页面组件样式适配

- [x] 5.1 适配 `UserList.vue` 暗黑模式样式（表格、按钮、卡片）
- [x] 5.2 适配 `AddUser.vue` 和 `EditUser.vue` 暗黑模式样式（表单、输入框）
- [x] 5.3 适配 `RoleList.vue` 暗黑模式样式
- [x] 5.4 适配 `AddRole.vue` 和 `EditRole.vue` 暗黑模式样式
- [x] 5.5 适配 `MajorList.vue` 暗黑模式样式
- [x] 5.6 适配 `AddMajor.vue` 和 `EditMajor.vue` 暗黑模式样式
- [x] 5.7 适配 `ClassList.vue` 暗黑模式样式
- [x] 5.8 适配 `AddClass.vue` 和 `EditClass.vue` 暗黑模式样式
- [x] 5.9 适配 `TeacherList.vue` 暗黑模式样式
- [x] 5.10 适配 `AddTeacher.vue` 和 `EditTeacher.vue` 暗黑模式样式
- [x] 5.11 适配 `StudentList.vue` 暗黑模式样式
- [x] 5.12 适配 `AddStudent.vue` 和 `EditStudent.vue` 暗黑模式样式

## 6. 测试验证

- [x] 6.1 验证浅色模式默认显示正确
- [x] 6.2 验证点击切换按钮可正常切换主题
- [x] 6.3 验证刷新页面后主题状态保持
- [x] 6.4 验证系统主题偏好自动检测生效
- [x] 6.5 验证首次加载无闪烁现象
- [x] 6.6 验证所有页面在暗黑模式下显示正常