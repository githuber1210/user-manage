## Why

当前管理系统仅支持浅色主题，在夜间或低光环境下使用时，用户长时间面对明亮界面容易产生视觉疲劳。提供暗黑模式切换功能可以改善用户体验，减少眼睛疲劳，同时符合现代应用的 UI 标准。

## What Changes

- 新增全局主题切换功能，支持浅色模式和暗黑模式
- 在顶部导航栏添加主题切换按钮
- 主题状态持久化存储（localStorage），刷新页面后保持用户选择
- 为所有现有页面组件适配暗黑模式样式
- 支持跟随系统主题偏好自动切换

## Capabilities

### New Capabilities

- `theme-toggle`: 主题切换功能，包含切换按钮、状态管理、样式适配和持久化存储

### Modified Capabilities

无现有规范需要修改

## Impact

- **前端代码**：
  - `App.vue`：添加暗黑模式 CSS 变量和切换按钮
  - `main.js`：初始化主题状态
  - 所有 `views/*.vue` 页面组件：适配暗黑模式样式
- **新增文件**：
  - `src/composables/useTheme.js`：主题状态管理 composable
- **依赖**：无新增外部依赖，使用 Vue 3 内置响应式系统和 localStorage