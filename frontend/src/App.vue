<template>
  <div class="admin-layout">
    <aside class="sidebar" :class="{ collapsed: sidebarCollapsed }">
      <div class="sidebar-brand">
        <div class="brand-icon">
          <svg xmlns="http://www.w3.org/2000/svg" width="28" height="28" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
            <path d="M3 9l9-7 9 7v11a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2z"/>
            <polyline points="9 22 9 12 15 12 15 22"/>
          </svg>
        </div>
        <span v-if="!sidebarCollapsed" class="brand-text">管理系统</span>
      </div>

      <nav class="sidebar-nav">
        <div class="nav-section">
          <div v-if="!sidebarCollapsed" class="nav-section-title">基础管理</div>

          <router-link to="/" class="nav-item" :class="{ active: isActive('/users') || currentPath === '/' }">
            <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
              <path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"/>
              <circle cx="9" cy="7" r="4"/>
              <path d="M23 21v-2a4 4 0 0 0-3-3.87"/>
              <path d="M16 3.13a4 4 0 0 1 0 7.75"/>
            </svg>
            <span v-if="!sidebarCollapsed">用户管理</span>
          </router-link>

          <router-link to="/roles" class="nav-item" :class="{ active: isActive('/roles') }">
            <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
              <path d="M12 22s8-4 8-10V5l-8-3-8 3v7c0 6 8 10 8 10z"/>
            </svg>
            <span v-if="!sidebarCollapsed">角色管理</span>
          </router-link>
        </div>

        <div class="nav-section">
          <div v-if="!sidebarCollapsed" class="nav-section-title">教学管理</div>

          <router-link to="/majors" class="nav-item" :class="{ active: isActive('/majors') }">
            <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
              <path d="M4 19.5A2.5 2.5 0 0 1 6.5 17H20"/>
              <path d="M6.5 2H20v20H6.5A2.5 2.5 0 0 1 4 19.5v-15A2.5 2.5 0 0 1 6.5 2z"/>
            </svg>
            <span v-if="!sidebarCollapsed">专业管理</span>
          </router-link>

          <router-link to="/classes" class="nav-item" :class="{ active: isActive('/classes') }">
            <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
              <rect x="3" y="3" width="18" height="18" rx="2" ry="2"/>
              <line x1="3" y1="9" x2="21" y2="9"/>
              <line x1="9" y1="21" x2="9" y2="9"/>
            </svg>
            <span v-if="!sidebarCollapsed">班级管理</span>
          </router-link>

          <router-link to="/teachers" class="nav-item" :class="{ active: isActive('/teachers') }">
            <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
              <path d="M22 10v6M2 10l10-5 10 5-10 5z"/>
              <path d="M6 12v5c0 1.657 2.686 3 6 3s6-1.343 6-3v-5"/>
            </svg>
            <span v-if="!sidebarCollapsed">教师管理</span>
          </router-link>

          <router-link to="/students" class="nav-item" :class="{ active: isActive('/students') }">
            <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
              <path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"/>
              <circle cx="9" cy="7" r="4"/>
              <path d="M23 21v-2a4 4 0 0 0-3-3.87"/>
              <path d="M16 3.13a4 4 0 0 1 0 7.75"/>
            </svg>
            <span v-if="!sidebarCollapsed">学生管理</span>
          </router-link>
        </div>
      </nav>

      <div v-if="!sidebarCollapsed" class="sidebar-footer">
        <div class="version-info">v1.0.0</div>
      </div>
    </aside>

    <div class="main-area" :class="{ expanded: sidebarCollapsed }">
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

const isActive = (path) => {
  return currentPath.value.startsWith(path)
}

const pageTitle = computed(() => {
  const path = currentPath.value

  if (path === '/' || path === '/users') return '用户列表'
  if (path === '/users/add') return '添加用户'
  if (path.startsWith('/users/edit/')) return '编辑用户'

  if (path === '/roles') return '角色列表'
  if (path === '/roles/add') return '添加角色'
  if (path.startsWith('/roles/edit/')) return '编辑角色'

  if (path === '/majors') return '专业列表'
  if (path === '/majors/add') return '添加专业'
  if (path.startsWith('/majors/edit/')) return '编辑专业'

  if (path === '/classes') return '班级列表'
  if (path === '/classes/add') return '添加班级'
  if (path.startsWith('/classes/edit/')) return '编辑班级'

  if (path === '/teachers') return '教师列表'
  if (path === '/teachers/add') return '添加教师'
  if (path.startsWith('/teachers/edit/')) return '编辑教师'

  if (path === '/students') return '学生列表'
  if (path === '/students/add') return '添加学生'
  if (path.startsWith('/students/edit/')) return '编辑学生'

  return '管理系统'
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
  --sidebar-bg: #ffffff;
  --sidebar-hover: #f5f5f5;
  --sidebar-active: #3b82f6;
  --sidebar-active-bg: rgba(59, 130, 246, 0.08);
  --sidebar-text: #000000;
  --sidebar-text-hover: #000000;
  --sidebar-text-active: #3b82f6;
  --sidebar-border: #e0e0e0;
  --sidebar-width: 240px;
  --sidebar-collapsed-width: 64px;
  --topbar-bg: #ffffff;
  --topbar-height: 56px;
  --content-bg: #ffffff;
  --accent: #3b82f6;
  --accent-hover: #2563eb;
  --primary: #3b82f6;
  --danger: #ef4444;
  --success: #10b981;
  --text-primary: #000000;
  --text-secondary: #666666;
  --border-color: #e0e0e0;
  --card-bg: #ffffff;
  --card-shadow: 0 1px 3px rgba(0,0,0,0.04), 0 1px 2px rgba(0,0,0,0.02);
  --card-shadow-hover: 0 4px 12px rgba(0,0,0,0.06);
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

.sidebar {
  width: var(--sidebar-width);
  background: var(--sidebar-bg);
  display: flex;
  flex-direction: column;
  transition: width 0.3s cubic-bezier(0.4, 0, 0.2, 1);
  overflow: hidden;
  z-index: 100;
  border-right: 1px solid var(--sidebar-border);
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
  border-bottom: 1px solid var(--sidebar-border);
  background: linear-gradient(135deg, #ffffff 0%, #f8fafc 100%);
}

.brand-icon {
  color: var(--sidebar-active);
  display: flex;
  align-items: center;
  justify-content: center;
  min-width: 28px;
}

.brand-text {
  color: var(--text-primary);
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
  gap: 20px;
  overflow-y: auto;
  background: var(--sidebar-bg);
}

.nav-section {
  display: flex;
  flex-direction: column;
  gap: 4px;
}

.nav-section-title {
  font-size: 11px;
  font-weight: 600;
  text-transform: uppercase;
  letter-spacing: 1.2px;
  color: var(--sidebar-text);
  padding: 8px 12px 4px;
  opacity: 0.7;
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
  position: relative;
}

.nav-item::before {
  content: '';
  position: absolute;
  left: 0;
  top: 50%;
  transform: translateY(-50%);
  width: 3px;
  height: 0;
  background: var(--sidebar-active);
  border-radius: 0 2px 2px 0;
  transition: height 0.2s ease;
}

.nav-item:hover {
  background: var(--sidebar-hover);
  color: var(--sidebar-text-hover);
}

.nav-item.active {
  background: var(--sidebar-active-bg);
  color: var(--sidebar-text-active);
}

.nav-item.active::before {
  height: 20px;
}

.nav-item.active svg {
  stroke: var(--sidebar-active);
}

.sidebar-footer {
  padding: 16px 20px;
  border-top: 1px solid var(--sidebar-border);
  background: linear-gradient(135deg, #f8fafc 0%, #ffffff 100%);
}

.version-info {
  color: var(--sidebar-text);
  font-size: 12px;
  opacity: 0.6;
}

.main-area {
  flex: 1;
  display: flex;
  flex-direction: column;
  overflow: hidden;
  transition: margin-left 0s;
}

.main-area.expanded {}

.topbar {
  height: var(--topbar-height);
  background: var(--topbar-bg);
  border-bottom: 1px solid var(--border-color);
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 24px;
  box-shadow: 0 1px 2px rgba(0,0,0,0.02);
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
  background: var(--sidebar-hover);
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

.content-area {
  flex: 1;
  overflow-y: auto;
  padding: 24px;
  background: var(--content-bg);
}

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
