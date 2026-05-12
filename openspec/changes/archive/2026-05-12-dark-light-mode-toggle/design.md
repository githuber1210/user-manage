## Context

当前 Vue 3 前端应用使用 CSS 变量定义颜色主题，所有颜色值集中在 `App.vue` 的 `:root` 块中。这种架构天然支持主题切换，只需为暗黑模式定义一组新的 CSS 变量值即可。

**现有架构特点**：
- 颜色变量已集中定义（约 20 个变量）
- 使用 Vue 3 Composition API
- 无全局状态管理库（无 Vuex/Pinia）
- 页面组件样式内联在各自 `.vue` 文件中

**约束**：
- 不引入新的外部依赖
- 保持现有代码风格（`<script setup>` 语法）
- 遵循项目命名规范

## Goals / Non-Goals

**Goals:**
- 实现浅色/暗黑模式一键切换
- 主题状态持久化，刷新后保持用户选择
- 支持跟随系统主题偏好（`prefers-color-scheme`）
- 所有现有页面组件适配暗黑模式
- 切换过程平滑无闪烁

**Non-Goals:**
- 不支持自定义主题颜色
- 不支持多主题扩展（仅浅色/暗黑两种）
- 不涉及后端 API 变化

## Decisions

### 1. 主题状态管理方案

**选择：Vue Composable + localStorage**

**理由**：
- 项目无 Pinia/Vuex，使用 composable 符合 Vue 3 最佳实践
- localStorage 实现持久化简单可靠
- 响应式状态可在任意组件中访问

**替代方案**：
- Pinia store：需引入新依赖，违反约束
- 全局 ref in main.js：不够模块化，难以复用

### 2. CSS 变量切换方式

**选择：`data-theme` 属性 + CSS 变量覆盖**

```css
[data-theme="dark"] {
  --sidebar-bg: #1a1a2e;
  /* ... */
}
```

**理由**：
- 切换只需修改一个属性，性能最优
- CSS 变量继承机制自动覆盖所有子元素
- 无需 JavaScript 操作样式

**替代方案**：
- 动态修改 `:root` 变量：需 JS 操作，性能较差
- class 切换（`.dark-mode`）：语义不如 `data-theme` 清晰

### 3. 初始化时机

**选择：`main.js` 中初始化，在 `app.mount` 之前**

**理由**：
- 避免首次渲染闪烁（FOUC）
- 确保首次渲染即应用正确主题
- 符合 Vue 应用初始化流程

### 4. 切换按钮位置

**选择：顶部导航栏右侧（topbar-right 区域）**

**理由**：
- 与现有布局一致，不破坏视觉结构
- 顶部栏固定可见，切换便捷
- 与时间显示并列，符合用户习惯

## Risks / Trade-offs

| 风险 | 缓解措施 |
|------|----------|
| 首次加载闪烁（FOUC） | 在 `main.js` 中于 `app.mount` 前读取 localStorage 并设置主题 |
| localStorage 不可用（隐私模式） | 检测可用性，不可用时降级为 session 状态 |
| 部分组件样式遗漏 | 建立样式检查清单，逐一验证所有页面 |
| 系统主题变化未响应 | 监听 `matchMedia` 变化事件，实时同步 |

## Migration Plan

无需迁移，纯增量功能：

1. 创建 `useTheme.js` composable
2. 修改 `main.js` 初始化主题
3. 修改 `App.vue` 添加暗黑 CSS 变量和切换按钮
4. 逐一适配各页面组件样式

**回滚策略**：删除新增代码，移除切换按钮，恢复原始状态。

## Open Questions

无待解决问题，设计方案已明确。