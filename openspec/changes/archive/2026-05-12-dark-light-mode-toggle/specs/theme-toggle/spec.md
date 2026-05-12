## ADDED Requirements

### Requirement: Theme toggle button visibility

系统 SHALL 在顶部导航栏右侧显示主题切换按钮，按钮包含明确的视觉图标（太阳/月亮）表示当前主题状态。

#### Scenario: Light mode button display
- **WHEN** 当前主题为浅色模式
- **THEN** 切换按钮显示太阳图标，提示可切换至暗黑模式

#### Scenario: Dark mode button display
- **WHEN** 当前主题为暗黑模式
- **THEN** 切换按钮显示月亮图标，提示可切换至浅色模式

### Requirement: Theme toggle functionality

系统 SHALL 允许用户通过点击切换按钮在浅色模式和暗黑模式之间切换，切换 SHALL 立即生效无需页面刷新。

#### Scenario: Toggle from light to dark
- **WHEN** 用户在浅色模式下点击切换按钮
- **THEN** 系统立即切换至暗黑模式，所有界面元素颜色同步变化

#### Scenario: Toggle from dark to light
- **WHEN** 用户在暗黑模式下点击切换按钮
- **THEN** 系统立即切换至浅色模式，所有界面元素颜色同步变化

### Requirement: Theme state persistence

系统 SHALL 将用户选择的主题状态持久化存储至 localStorage，页面刷新或重新打开后 SHALL 自动恢复用户上次选择的主题。

#### Scenario: Persist light mode choice
- **WHEN** 用户选择浅色模式
- **THEN** 系统将 `theme=light` 存储至 localStorage

#### Scenario: Persist dark mode choice
- **WHEN** 用户选择暗黑模式
- **THEN** 系统将 `theme=dark` 存储至 localStorage

#### Scenario: Restore theme on page load
- **WHEN** 用户刷新页面或重新打开应用
- **THEN** 系统读取 localStorage 中的主题设置并应用

#### Scenario: No stored preference
- **WHEN** localStorage 中无主题设置记录
- **THEN** 系统默认使用浅色模式

### Requirement: System theme preference detection

系统 SHALL 支持检测并跟随用户系统的主题偏好设置（`prefers-color-scheme`），当用户未手动设置主题时 SHALL 自动跟随系统偏好。

#### Scenario: Follow system dark preference
- **WHEN** 用户系统设置为暗黑模式且用户未手动设置主题
- **THEN** 系统自动应用暗黑模式

#### Scenario: Follow system light preference
- **WHEN** 用户系统设置为浅色模式且用户未手动设置主题
- **THEN** 系统自动应用浅色模式

#### Scenario: Manual setting overrides system
- **WHEN** 用户手动设置主题后系统偏好发生变化
- **THEN** 系统保持用户手动设置的主题，不自动切换

### Requirement: Dark mode color scheme

系统 SHALL 为暗黑模式定义完整的颜色方案，覆盖所有现有 CSS 变量，确保所有界面元素在暗黑模式下具有适当的对比度和可读性。

#### Scenario: Sidebar dark colors
- **WHEN** 暗黑模式激活
- **THEN** 侧边栏背景使用深色（`#1a1a2e`），文字使用浅色，保持足够对比度

#### Scenario: Content area dark colors
- **WHEN** 暗黑模式激活
- **THEN** 内容区域背景使用深色，卡片背景使用稍浅的深色，文字清晰可读

#### Scenario: Interactive elements dark colors
- **WHEN** 暗黑模式激活
- **THEN** 按钮、链接等交互元素保持品牌色（蓝色）但调整亮度以适应深色背景

### Requirement: No flash on initial load

系统 SHALL 在应用首次渲染前确定并应用主题设置，避免首次加载时出现主题闪烁（FOUC）现象。

#### Scenario: Initial render with correct theme
- **WHEN** 应用首次加载
- **THEN** 页面渲染时即显示正确的主题，无短暂闪烁

### Requirement: All pages dark mode support

系统 SHALL 确保所有现有页面组件（用户管理、角色管理、专业管理、班级管理、教师管理、学生管理及其子页面）在暗黑模式下正常显示。

#### Scenario: List pages dark mode
- **WHEN** 暗黑模式激活且用户访问任意列表页面
- **THEN** 表格、卡片、按钮等元素均使用暗黑模式颜色

#### Scenario: Form pages dark mode
- **WHEN** 暗黑模式激活且用户访问添加/编辑页面
- **THEN** 表单输入框、标签、按钮等元素均使用暗黑模式颜色