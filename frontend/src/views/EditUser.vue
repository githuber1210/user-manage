<template>
  <div class="form-container">
    <div class="form-card">
      <!-- 卡片头部 -->
      <div class="form-header">
        <div class="header-icon bg-primary">
          <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
            <path d="M11 4H4a2 2 0 0 0-2 2v14a2 2 0 0 0 2 2h14a2 2 0 0 0 2-2v-7"/>
            <path d="M18.5 2.5a2.121 2.121 0 0 1 3 3L12 15l-4 1 1-4 9.5-9.5z"/>
          </svg>
        </div>
        <div class="header-text">
          <h2 class="form-title">编辑用户信息</h2>
          <p class="form-desc">修改用户的基本信息</p>
        </div>
      </div>

      <!-- 表单内容 -->
      <div v-if="loading" class="loading-body">
        <div class="loading-spinner"></div>
        <span class="loading-text">正在加载用户信息...</span>
      </div>

      <form v-else @submit.prevent="submitForm" class="form-body">
        <input type="hidden" v-model="formData.id">
        
        <!-- 用户名 -->
        <div class="form-group">
          <label for="username" class="form-label">
            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="label-icon">
              <path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2"/>
              <circle cx="12" cy="7" r="4"/>
            </svg>
            用户名
            <span class="required">*</span>
          </label>
          <div class="input-wrapper">
            <input 
              type="text" 
              id="username"
              v-model="formData.username"
              class="form-input"
              placeholder="请输入用户名"
              :class="{ 'error': errors.username }"
            />
            <div v-if="formData.username && !errors.username" class="valid-icon">
              <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                <path d="M22 11.08V12a10 10 0 1 1-5.93-9.14"/>
                <polyline points="22 4 12 14.01 9 11.01"/>
              </svg>
            </div>
          </div>
          <div v-if="errors.username" class="error-message">
            <svg xmlns="http://www.w3.org/2000/svg" width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
              <circle cx="12" cy="12" r="10"/>
              <line x1="12" y1="8" x2="12" y2="12"/>
              <line x1="12" y1="16" x2="12.01" y2="16"/>
            </svg>
            {{ errors.username }}
          </div>
        </div>

        <!-- 邮箱 -->
        <div class="form-group">
          <label for="email" class="form-label">
            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="label-icon">
              <path d="M4 4h16c1.1 0 2 .9 2 2v12c0 1.1-.9 2-2 2H4c-1.1 0-2-.9-2-2V6c0-1.1.9-2 2-2z"/>
              <polyline points="22,6 12,13 2,6"/>
            </svg>
            邮箱地址
            <span class="required">*</span>
          </label>
          <div class="input-wrapper">
            <input 
              type="email" 
              id="email"
              v-model="formData.email"
              class="form-input"
              placeholder="example@email.com"
              :class="{ 'error': errors.email }"
            />
            <div v-if="formData.email && !errors.email" class="valid-icon">
              <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                <path d="M22 11.08V12a10 10 0 1 1-5.93-9.14"/>
                <polyline points="22 4 12 14.01 9 11.01"/>
              </svg>
            </div>
          </div>
          <div v-if="errors.email" class="error-message">
            <svg xmlns="http://www.w3.org/2000/svg" width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
              <circle cx="12" cy="12" r="10"/>
              <line x1="12" y1="8" x2="12" y2="12"/>
              <line x1="12" y1="16" x2="12.01" y2="16"/>
            </svg>
            {{ errors.email }}
          </div>
        </div>

        <!-- 年龄 -->
        <div class="form-group">
          <label for="age" class="form-label">
            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="label-icon">
              <rect x="2" y="7" width="20" height="14" rx="2" ry="2"/>
              <path d="M16 21V5a2 2 0 0 0-2-2h-4a2 2 0 0 0-2 2v16"/>
            </svg>
            年龄
            <span class="optional">（可选）</span>
          </label>
          <input 
            type="number" 
            id="age"
            v-model="formData.age"
            class="form-input"
            placeholder="请输入年龄"
            min="1"
            max="150"
          />
          <p class="form-hint">年龄范围：1-150岁</p>
        </div>

        <!-- 表单按钮 -->
        <div class="form-actions">
          <button type="submit" class="btn btn-primary" :disabled="submitting">
            <div v-if="submitting" class="btn-spinner"></div>
            <svg v-else xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
              <path d="M19 21H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h11l5 5v11a2 2 0 0 1-2 2z"/>
              <polyline points="17 21 17 13 7 13 7 21"/>
              <polyline points="7 3 7 8 15 8"/>
            </svg>
            {{ submitting ? '保存中...' : '保存修改' }}
          </button>
          <router-link to="/" class="btn btn-outline">
            <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
              <line x1="18" y1="6" x2="6" y2="18"/>
              <line x1="6" y1="6" x2="18" y2="18"/>
            </svg>
            取消
          </router-link>
        </div>
      </form>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { userApi } from '../api/user'

const router = useRouter()
const route = useRoute()

const formData = reactive({
  id: '',
  username: '',
  email: '',
  age: ''
})

const errors = reactive({})
const loading = ref(true)
const submitting = ref(false)

const fetchUser = async () => {
  const userId = route.params.id
  if (!userId) {
    alert('用户ID不存在')
    router.push('/')
    return
  }
  
  try {
    const response = await userApi.getUserById(userId)
    if (response.code === 200) {
      const user = response.data
      formData.id = user.id
      formData.username = user.username
      formData.email = user.email
      formData.age = user.age || ''
    }
  } catch (error) {
    console.error('获取用户信息失败:', error)
    router.push('/')
  } finally {
    loading.value = false
  }
}

const submitForm = async () => {
  Object.keys(errors).forEach(key => delete errors[key])
  
  submitting.value = true
  
  try {
    const response = await userApi.updateUser(formData.id, {
      username: formData.username,
      email: formData.email,
      age: formData.age ? parseInt(formData.age) : null
    })
    
    if (response.code === 200) {
      alert('更新成功')
      router.push('/')
    }
  } catch (error) {
    if (error.response && error.response.data) {
      const data = error.response.data
      if (data.data && typeof data.data === 'object') {
        Object.assign(errors, data.data)
      }
    }
  } finally {
    submitting.value = false
  }
}

onMounted(() => {
  fetchUser()
})
</script>

<style scoped>
.form-container {
  max-width: 560px;
  margin: 0 auto;
}

.form-card {
  background: var(--card-bg);
  border-radius: var(--radius-lg);
  box-shadow: var(--card-shadow);
  overflow: hidden;
}

/* 表单头部 */
.form-header {
  display: flex;
  align-items: center;
  gap: 16px;
  padding: 24px;
  border-bottom: 1px solid var(--border-color);
}

.header-icon {
  width: 56px;
  height: 56px;
  border-radius: var(--radius-lg);
  display: flex;
  align-items: center;
  justify-content: center;
  color: #ffffff;
}

.bg-primary { background: var(--primary); }

.header-text .form-title {
  font-size: 18px;
  font-weight: 600;
  color: var(--text-primary);
  margin: 0 0 4px 0;
}

.header-text .form-desc {
  font-size: 13px;
  color: var(--text-secondary);
  margin: 0;
}

/* 加载状态 */
.loading-body {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 60px 20px;
  gap: 16px;
}

.loading-spinner {
  width: 40px;
  height: 40px;
  border: 3px solid var(--border-color);
  border-top-color: var(--primary);
  border-radius: 50%;
  animation: spin 0.8s linear infinite;
}

.loading-text {
  font-size: 14px;
  color: var(--text-secondary);
}

/* 表单主体 */
.form-body {
  padding: 24px;
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.form-group {
  display: flex;
  flex-direction: column;
  gap: 8px;
}

.form-label {
  display: flex;
  align-items: center;
  gap: 8px;
  font-size: 14px;
  font-weight: 500;
  color: var(--text-primary);
}

.label-icon {
  color: var(--text-secondary);
}

.required {
  color: var(--danger);
  font-weight: 500;
}

.optional {
  color: var(--text-secondary);
  font-weight: 400;
  font-size: 12px;
}

.input-wrapper {
  position: relative;
}

.form-input {
  width: 100%;
  height: 44px;
  padding: 0 40px 0 12px;
  border: 1px solid var(--border-color);
  border-radius: var(--radius);
  font-size: 14px;
  background: #ffffff;
  transition: border-color 0.2s, box-shadow 0.2s;
}

.form-input:focus {
  outline: none;
  border-color: var(--accent);
  box-shadow: 0 0 0 3px rgba(245, 158, 11, 0.15);
}

.form-input.error {
  border-color: var(--danger);
}

.form-input.error:focus {
  box-shadow: 0 0 0 3px rgba(239, 68, 68, 0.15);
}

.valid-icon {
  position: absolute;
  right: 12px;
  top: 50%;
  transform: translateY(-50%);
  color: var(--success);
}

.error-message {
  display: flex;
  align-items: center;
  gap: 6px;
  font-size: 13px;
  color: var(--danger);
}

.error-message svg {
  flex-shrink: 0;
}

.form-hint {
  font-size: 12px;
  color: var(--text-secondary);
  margin: 0;
}

/* 按钮区域 */
.form-actions {
  display: flex;
  gap: 12px;
  margin-top: 8px;
}

.btn {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  gap: 8px;
  padding: 10px 20px;
  border-radius: var(--radius);
  font-size: 14px;
  font-weight: 500;
  border: none;
  cursor: pointer;
  transition: all 0.2s;
  text-decoration: none;
  flex: 1;
  height: 44px;
}

.btn:disabled {
  opacity: 0.6;
  cursor: not-allowed;
}

.btn-primary {
  background: var(--primary);
  color: #ffffff;
}

.btn-primary:hover:not(:disabled) {
  background: #2563eb;
}

.btn-outline {
  background: transparent;
  border: 1px solid var(--border-color);
  color: var(--text-secondary);
}

.btn-outline:hover {
  background: var(--content-bg);
  color: var(--text-primary);
}

.btn-spinner {
  width: 18px;
  height: 18px;
  border: 2px solid rgba(255,255,255,0.3);
  border-top-color: #ffffff;
  border-radius: 50%;
  animation: spin 0.8s linear infinite;
}

@keyframes spin {
  to { transform: rotate(360deg); }
}
</style>