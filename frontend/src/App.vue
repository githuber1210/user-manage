<template>
  <div class="admin-layout">
    <!-- 侧边栏 -->
    <aside class="sidebar" :class="{ collapsed: sidebarCollapsed }">
      <div class="sidebar-brand">
        <div class="brand-icon">
          <svg xmlns="http://www.w3.org/2000/svg" width="28" height="28" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
            <path d="M3 9l9-7 9 7v11a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2z"/>
            <polyline points="9 22 9 12 15 12 15 22"/>
          </svg>
        </div>
        <span v-if="!sidebarCollapsed" class="brand-text">用户管理系统</span>
      </div>

      <nav class="sidebar-nav">
        <router-link to="/" class="nav-item" :class="{ active: currentPath === '/' }">
          <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
            <path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"/>
            <circle cx="9" cy="7" r="4"/>
            <path d="M23 21v-2a4 4 0 0 0-3-3.87"/>
            <path d="M16 3.13a4 4 0 0 1 0 7.75"/>
          </svg>
          <span v-if="!sidebarCollapsed">用户列表</span>
        </router-link>

      </nav>

      <div v-if="!sidebarCollapsed" class="sidebar-footer">
        <div class="version-info">v1.0.0</div>
      </div>
    </aside>

    <!-- 主内容区 -->
    <div class="main-area" :class="{ expanded: sidebarCollapsed }">
      <!-- 顶部栏 -->
      <header class="topbar">
        <div class="topbar-left">
          <button class="toggle-btn" @click="sidebarCollapsed = !sidebarCollapsed">
            <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
              <line x1="3" y1="12" x2="21" y2="12"/>
              <line x1="3" y1="6" x2="21" y2="6"/>
              <line x1="3" y1="18" x2="21" y2="18"/>
            </svg>
          </button>
          <div class="breadcrumb">
            <span class="breadcrumb-item">{{ pageTitle }}</span>
          </div>
        </div>
        <div class="topbar-right">
          <div class="current-time">{{ currentTime }}</div>
        </div>
      </header>

      <!-- 页面内容 -->
      <main class="content-area">
        <transition name="page-fade" mode="out-in">
          <router-view />
        </transition>
      </main>
    </div>
  </div>
</template>

<script setup>
import { computed, ref, onMounted, onUnmounted } from 'vue'
import { useRoute } from 'vue-router'

const route = useRoute()
const sidebarCollapsed = ref(false)
const currentTime = ref('')
let timer = null

const currentPath = computed(() => route.path)

const pageTitle = computed(() => {
  const titles = {
    '/': '用户列表',
    '/add': '添加用户'
  }
  if (route.path.startsWith('/edit/')) return '编辑用户'
  return titles[route.path] || '用户管理'
})

const updateTime = () => {
  const now = new Date()
  currentTime.value = now.toLocaleString('zh-CN', {
    year: 'numeric', month: '2-digit', day: '2-digit',
    hour: '2-digit', minute: '2-digit', second: '2-digit'
  })
}

onMounted(() => {
  updateTime()
  timer = setInterval(updateTime, 1000)
})

onUnmounted(() => {
  if (timer) clearInterval(timer)
})
</script>

<style>
@import url('https://fonts.googleapis.com/css2?family=Noto+Sans+SC:wght@300;400;500;600;700&display=swap');

:root {
  --sidebar-bg: #1e293b;
  --sidebar-hover: #334155;
  --sidebar-active: #f59e0b;
  --sidebar-text: #94a3b8;
  --sidebar-text-active: #ffffff;
  --sidebar-width: 240px;
  --sidebar-collapsed-width: 64px;
  --topbar-bg: #ffffff;
  --topbar-height: 56px;
  --content-bg: #f1f5f9;
  --accent: #f59e0b;
  --accent-hover: #d97706;
  --primary: #3b82f6;
  --danger: #ef4444;
  --success: #10b981;
  --text-primary: #1e293b;
  --text-secondary: #64748b;
  --border-color: #e2e8f0;
  --card-bg: #ffffff;
  --card-shadow: 0 1px 3px rgba(0,0,0,0.06), 0 1px 2px rgba(0,0,0,0.04);
  --card-shadow-hover: 0 4px 12px rgba(0,0,0,0.08);
  --radius: 8px;
  --radius-lg: 12px;
}

* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

html, body {
  height: 100%;
  font-family: 'Noto Sans SC', -apple-system, BlinkMacSystemFont, 'Segoe UI', sans-serif;
  background: var(--content-bg);
  color: var(--text-primary);
  -webkit-font-smoothing: antialiased;
}

#app {
  height: 100%;
}

.admin-layout {
  display: flex;
  height: 100vh;
  overflow: hidden;
}

/* 侧边栏 */
.sidebar {
  width: var(--sidebar-width);
  background: var(--sidebar-bg);
  display: flex;
  flex-direction: column;
  transition: width 0.3s cubic-bezier(0.4, 0, 0.2, 1);
  overflow: hidden;
  z-index: 100;
}

.sidebar.collapsed {
  width: var(--sidebar-collapsed-width);
}

.sidebar-brand {
  height: var(--topbar-height);
  display: flex;
  align-items: center;
  padding: 0 20px;
  gap: 12px;
  border-bottom: 1px solid rgba(255,255,255,0.08);
}

.brand-icon {
  color: var(--sidebar-active);
  display: flex;
  align-items: center;
  justify-content: center;
  min-width: 28px;
}

.brand-text {
  color: #ffffff;
  font-size: 16px;
  font-weight: 600;
  white-space: nowrap;
  letter-spacing: 0.5px;
}

.sidebar-nav {
  flex: 1;
  padding: 16px 12px;
  display: flex;
  flex-direction: column;
  gap: 4px;
}

.nav-item {
  display: flex;
  align-items: center;
  gap: 12px;
  padding: 10px 12px;
  color: var(--sidebar-text);
  text-decoration: none;
  border-radius: var(--radius);
  transition: all 0.2s ease;
  white-space: nowrap;
  font-size: 14px;
  font-weight: 500;
}

.nav-item:hover {
  background: var(--sidebar-hover);
  color: #e2e8f0;
}

.nav-item.active {
  background: rgba(245, 158, 11, 0.15);
  color: var(--sidebar-active);
}

.nav-item.active svg {
  stroke: var(--sidebar-active);
}

.sidebar-footer {
  padding: 16px 20px;
  border-top: 1px solid rgba(255,255,255,0.08);
}

.version-info {
  color: var(--sidebar-text);
  font-size: 12px;
  opacity: 0.6;
}

/* 主内容区 */
.main-area {
  flex: 1;
  display: flex;
  flex-direction: column;
  overflow: hidden;
  transition: margin-left 0s;
}

.main-area.expanded {
  /* 侧边栏折叠时无需额外处理，flex:1自动填充 */
}

/* 顶部栏 */
.topbar {
  height: var(--topbar-height);
  background: var(--topbar-bg);
  border-bottom: 1px solid var(--border-color);
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 24px;
  box-shadow: 0 1px 2px rgba(0,0,0,0.04);
}

.topbar-left {
  display: flex;
  align-items: center;
  gap: 16px;
}

.toggle-btn {
  background: none;
  border: none;
  color: var(--text-secondary);
  cursor: pointer;
  padding: 6px;
  border-radius: var(--radius);
  display: flex;
  align-items: center;
  justify-content: center;
  transition: all 0.2s;
}

.toggle-btn:hover {
  background: var(--content-bg);
  color: var(--text-primary);
}

.breadcrumb {
  font-size: 15px;
  font-weight: 600;
  color: var(--text-primary);
}

.breadcrumb-item {
  position: relative;
}

.topbar-right {
  display: flex;
  align-items: center;
  gap: 16px;
}

.current-time {
  font-size: 13px;
  color: var(--text-secondary);
  font-weight: 400;
}

/* 内容区 */
.content-area {
  flex: 1;
  overflow-y: auto;
  padding: 24px;
  background: var(--content-bg);
}

/* 页面过渡动画 */
.page-fade-enter-active {
  transition: opacity 0.25s ease, transform 0.25s ease;
}
.page-fade-leave-active {
  transition: opacity 0.15s ease;
}
.page-fade-enter-from {
  opacity: 0;
  transform: translateY(8px);
}
.page-fade-leave-to {
  opacity: 0;
}

/* 响应式 */
@media (max-width: 768px) {
  .sidebar {
    position: fixed;
    left: 0;
    top: 0;
    height: 100vh;
    transform: translateX(-100%);
    z-index: 200;
  }
  .sidebar.collapsed {
    transform: translateX(0);
    width: var(--sidebar-collapsed-width);
  }
  .content-area {
    padding: 16px;
  }
}
</style>