<template>
  <div class="toast-container">
    <TransitionGroup name="toast">
      <div
        v-for="toast in toasts"
        :key="toast.id"
        class="toast"
        :class="[`toast--${toast.type}`, { 'toast--close': toast.closing }]"
        @mouseenter="pauseToast(toast.id)"
        @mouseleave="resumeToast(toast.id)"
      >
        <div class="toast-icon">
          <svg v-if="toast.type === 'success'" xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
            <polyline points="20 6 9 17 4 12"/>
          </svg>
          <svg v-else-if="toast.type === 'error'" xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
            <line x1="18" y1="6" x2="6" y2="18"/>
            <line x1="6" y1="6" x2="18" y2="18"/>
          </svg>
          <svg v-else-if="toast.type === 'warning'" xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
            <path d="M12 9v4"/>
            <path d="M12 17h.01"/>
            <path d="M12 22c5.523 0 10-4.477 10-10S17.523 2 12 2 2 6.477 2 12s4.477 10 10 10z"/>
          </svg>
          <svg v-else xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
            <circle cx="12" cy="12" r="10"/>
            <line x1="12" y1="16" x2="12" y2="12"/>
            <line x1="12" y1="8" x2="12.01" y2="8"/>
          </svg>
        </div>
        <div class="toast-content">
          <div class="toast-title">{{ toast.title }}</div>
          <div class="toast-message">{{ toast.message }}</div>
        </div>
        <button class="toast-close" @click="removeToast(toast.id)">
          <svg xmlns="http://www.w3.org/2000/svg" width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
            <line x1="18" y1="6" x2="6" y2="18"/>
            <line x1="6" y1="6" x2="18" y2="18"/>
          </svg>
        </button>
        <div class="toast-progress" :style="{ width: toast.progress + '%' }"></div>
      </div>
    </TransitionGroup>
  </div>
</template>

<script setup>
import { ref, onMounted, onUnmounted } from 'vue'

const toasts = ref([])
let toastId = 0
let intervals = {}

const addToast = (options) => {
  const id = ++toastId
  const toast = {
    id,
    type: options.type || 'info',
    title: options.title || '',
    message: options.message || '',
    duration: options.duration || 4000,
    closing: false,
    progress: 100,
    startTime: Date.now()
  }
  
  toasts.value.push(toast)
  
  startProgress(id, toast.duration)
  
  return id
}

const startProgress = (id, duration) => {
  if (intervals[id]) clearInterval(intervals[id])
  
  const toast = toasts.value.find(t => t.id === id)
  if (!toast) return
  
  const interval = setInterval(() => {
    const elapsed = Date.now() - toast.startTime
    const progress = Math.max(0, 100 - (elapsed / duration) * 100)
    toast.progress = progress
    
    if (progress <= 0) {
      removeToast(id)
    }
  }, 20)
  
  intervals[id] = interval
}

const pauseToast = (id) => {
  if (intervals[id]) {
    clearInterval(intervals[id])
    const toast = toasts.value.find(t => t.id === id)
    if (toast) {
      toast.startTime = Date.now() - (100 - toast.progress) * (toast.duration / 100)
    }
  }
}

const resumeToast = (id) => {
  const toast = toasts.value.find(t => t.id === id)
  if (toast && toast.progress > 0) {
    startProgress(id, toast.duration)
  }
}

const removeToast = (id) => {
  const toast = toasts.value.find(t => t.id === id)
  if (toast) {
    toast.closing = true
    if (intervals[id]) {
      clearInterval(intervals[id])
      delete intervals[id]
    }
    setTimeout(() => {
      toasts.value = toasts.value.filter(t => t.id !== id)
    }, 300)
  }
}

const success = (message, title = '成功') => addToast({ type: 'success', title, message })
const error = (message, title = '错误') => addToast({ type: 'error', title, message })
const warning = (message, title = '警告') => addToast({ type: 'warning', title, message })
const info = (message, title = '提示') => addToast({ type: 'info', title, message })

defineExpose({
  addToast,
  success,
  error,
  warning,
  info,
  removeToast
})

onUnmounted(() => {
  Object.values(intervals).forEach(clearInterval)
})
</script>

<style scoped>
.toast-container {
  position: fixed;
  top: 24px;
  right: 24px;
  z-index: 9999;
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.toast {
  position: relative;
  min-width: 320px;
  max-width: 420px;
  background: #ffffff;
  border-radius: 12px;
  padding: 16px 16px 16px 16px;
  box-shadow: 
    0 4px 20px rgba(0, 0, 0, 0.12),
    0 1px 3px rgba(0, 0, 0, 0.08);
  display: flex;
  align-items: flex-start;
  gap: 12px;
  overflow: hidden;
  border-left: 4px solid transparent;
  transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
}

.toast--success {
  border-left-color: #10b981;
}
.toast--success .toast-icon {
  color: #10b981;
}
.toast--success .toast-title {
  color: #10b981;
}

.toast--error {
  border-left-color: #ef4444;
}
.toast--error .toast-icon {
  color: #ef4444;
}
.toast--error .toast-title {
  color: #ef4444;
}

.toast--warning {
  border-left-color: #f59e0b;
}
.toast--warning .toast-icon {
  color: #f59e0b;
}
.toast--warning .toast-title {
  color: #f59e0b;
}

.toast--info {
  border-left-color: #3b82f6;
}
.toast--info .toast-icon {
  color: #3b82f6;
}
.toast--info .toast-title {
  color: #3b82f6;
}

.toast--close {
  opacity: 0;
  transform: translateX(100%);
}

.toast-icon {
  flex-shrink: 0;
  width: 40px;
  height: 40px;
  border-radius: 10px;
  display: flex;
  align-items: center;
  justify-content: center;
  background: rgba(0, 0, 0, 0.04);
  transition: background 0.2s;
}

.toast:hover .toast-icon {
  background: rgba(0, 0, 0, 0.08);
}

.toast-content {
  flex: 1;
  min-width: 0;
}

.toast-title {
  font-size: 14px;
  font-weight: 600;
  margin-bottom: 4px;
  letter-spacing: 0.2px;
}

.toast-message {
  font-size: 13px;
  color: #666666;
  line-height: 1.5;
  word-break: break-word;
}

.toast-close {
  flex-shrink: 0;
  background: none;
  border: none;
  color: #9ca3af;
  cursor: pointer;
  padding: 6px;
  border-radius: 6px;
  transition: all 0.2s;
}

.toast-close:hover {
  color: #374151;
  background: rgba(0, 0, 0, 0.06);
}

.toast-progress {
  position: absolute;
  bottom: 0;
  left: 0;
  height: 3px;
  background: linear-gradient(90deg, currentColor, currentColor);
  opacity: 0.2;
  transition: width 0.02s linear;
}

.toast--success .toast-progress {
  background: #10b981;
}
.toast--error .toast-progress {
  background: #ef4444;
}
.toast--warning .toast-progress {
  background: #f59e0b;
}
.toast--info .toast-progress {
  background: #3b82f6;
}

.toast-enter-active {
  transition: all 0.4s cubic-bezier(0.16, 1, 0.3, 1);
}

.toast-leave-active {
  transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
}

.toast-enter-from {
  opacity: 0;
  transform: translateX(100%);
}

.toast-leave-to {
  opacity: 0;
  transform: translateX(100%);
}

.toast-move {
  transition: transform 0.3s cubic-bezier(0.4, 0, 0.2, 1);
}

@media (max-width: 480px) {
  .toast-container {
    top: 16px;
    right: 16px;
    left: 16px;
  }
  
  .toast {
    min-width: auto;
    max-width: none;
  }
}
</style>
