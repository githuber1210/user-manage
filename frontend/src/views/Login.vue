<template>
  <div class="login-container">
    <div class="login-wrapper">
      <div class="login-card">
        <div class="login-header">
          <div class="logo-icon">
            <svg xmlns="http://www.w3.org/2000/svg" width="48" height="48" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
              <path d="M3 9l9-7 9 7v11a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2z"/>
              <polyline points="9 22 9 12 15 12 15 22"/>
            </svg>
          </div>
          <h1 class="login-title">管理系统</h1>
          <p class="login-subtitle">请登录您的账号</p>
        </div>

        <form @submit.prevent="handleLogin" class="login-form">
          <div class="form-group">
            <label for="username" class="form-label">
              <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                <path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2"/>
                <circle cx="12" cy="7" r="4"/>
              </svg>
              用户名
            </label>
            <input
              type="text"
              id="username"
              v-model="formData.username"
              class="form-input"
              placeholder="请输入用户名"
              :class="{ 'error': errors.username }"
              autocomplete="username"
              @keyup.enter="handleLogin"
            />
            <div v-if="errors.username" class="error-text">{{ errors.username }}</div>
          </div>

          <div class="form-group">
            <label for="password" class="form-label">
              <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                <rect x="3" y="11" width="18" height="11" rx="2" ry="2"/>
                <path d="M7 11V7a5 5 0 0 1 10 0v4"/>
              </svg>
              密码
            </label>
            <div class="password-wrapper">
              <input
                :type="showPassword ? 'text' : 'password'"
                id="password"
                v-model="formData.password"
                class="form-input password-input"
                placeholder="请输入密码"
                :class="{ 'error': errors.password }"
                autocomplete="current-password"
                @keyup.enter="handleLogin"
              />
              <button
                type="button"
                class="toggle-password"
                @click="showPassword = !showPassword"
              >
                <svg v-if="!showPassword" xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                  <path d="M1 12s4-8 11-8 11 8 11 8-4 8-11 8-11-8-11-8z"/>
                  <circle cx="12" cy="12" r="3"/>
                </svg>
                <svg v-else xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                  <path d="M17.94 17.94A10.07 10.07 0 0 1 12 20c-7 0-11-8-11-8a18.45 18.45 0 0 1 5.06-5.94"/>
                  <path d="M9.9 4.24A9.12 9.12 0 0 1 12 4c7 0 11 8 11 8a18.5 18.5 0 0 1-6.66 11.34"/>
                  <line x1="1" y1="1" x2="23" y2="23"/>
                </svg>
              </button>
            </div>
            <div v-if="errors.password" class="error-text">{{ errors.password }}</div>
          </div>

          <div class="form-options">
            <label class="checkbox-label">
              <input type="checkbox" v-model="formData.rememberMe"/>
              <span class="checkmark"></span>
              记住我
            </label>
          </div>

          <button type="submit" class="login-btn" :disabled="loading">
            <div v-if="loading" class="btn-spinner"></div>
            <span>{{ loading ? '登录中...' : '登 录' }}</span>
          </button>
        </form>

        <div class="login-footer">
          <p>测试账号：admin / password</p>
        </div>
      </div>

      <div class="login-bg-decoration">
        <div class="decoration-circle circle-1"></div>
        <div class="decoration-circle circle-2"></div>
        <div class="decoration-circle circle-3"></div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import axios from '../api/axios'

const router = useRouter()

const formData = reactive({
  username: '',
  password: '',
  rememberMe: false
})

const showPassword = ref(false)
const loading = ref(false)
const errors = reactive({})

const handleLogin = async () => {
  Object.keys(errors).forEach(key => delete errors[key])

  if (!formData.username.trim()) {
    errors.username = '请输入用户名'
    return
  }
  if (!formData.password) {
    errors.password = '请输入密码'
    return
  }

  loading.value = true

  try {
    const response = await axios.post('/auth/login', {
      username: formData.username.trim(),
      password: formData.password
    })

    if (response.code === 200) {
      const tokenData = response.data
      localStorage.setItem('token', tokenData.accessToken)
      localStorage.setItem('userInfo', JSON.stringify({
        userId: tokenData.userId,
        username: tokenData.username,
        nickname: tokenData.nickname,
        roleCode: tokenData.roleCode
      }))

      router.push('/')
    }
  } catch (error) {
    if (error.response && error.response.data) {
      alert(error.response.data.message || '登录失败')
    } else {
      alert('登录失败，请检查网络连接')
    }
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.login-container {
  min-height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  position: relative;
  overflow: hidden;
}

.login-wrapper {
  position: relative;
  width: 100%;
  max-width: 420px;
  padding: 20px;
  z-index: 10;
}

.login-card {
  background: #ffffff;
  border-radius: 20px;
  box-shadow: 0 20px 60px rgba(0, 0, 0, 0.3);
  padding: 40px;
  position: relative;
}

.login-header {
  text-align: center;
  margin-bottom: 32px;
}

.logo-icon {
  color: #3b82f6;
  margin-bottom: 16px;
}

.login-title {
  font-size: 24px;
  font-weight: 700;
  color: #1f2937;
  margin: 0 0 8px 0;
}

.login-subtitle {
  font-size: 14px;
  color: #6b7280;
  margin: 0;
}

.login-form {
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
  color: #374151;
}

.form-input {
  width: 100%;
  height: 48px;
  padding: 0 16px;
  border: 1px solid #d1d5db;
  border-radius: 10px;
  font-size: 14px;
  transition: all 0.2s;
  background: #f9fafb;
}

.form-input:focus {
  outline: none;
  border-color: #3b82f6;
  box-shadow: 0 0 0 3px rgba(59, 130, 246, 0.1);
  background: #ffffff;
}

.form-input.error {
  border-color: #ef4444;
}

.form-input::placeholder {
  color: #9ca3af;
}

.password-wrapper {
  position: relative;
}

.password-input {
  padding-right: 48px;
}

.toggle-password {
  position: absolute;
  right: 14px;
  top: 50%;
  transform: translateY(-50%);
  background: none;
  border: none;
  color: #9ca3af;
  cursor: pointer;
  padding: 4px;
  border-radius: 6px;
  transition: all 0.2s;
}

.toggle-password:hover {
  color: #3b82f6;
  background: rgba(59, 130, 246, 0.1);
}

.error-text {
  font-size: 13px;
  color: #ef4444;
}

.form-options {
  display: flex;
  align-items: center;
}

.checkbox-label {
  display: flex;
  align-items: center;
  gap: 8px;
  font-size: 13px;
  color: #6b7280;
  cursor: pointer;
}

.checkbox-label input {
  display: none;
}

.checkmark {
  width: 18px;
  height: 18px;
  border: 1.5px solid #d1d5db;
  border-radius: 4px;
  position: relative;
  transition: all 0.2s;
}

.checkbox-label input:checked + .checkmark {
  background: #3b82f6;
  border-color: #3b82f6;
}

.checkbox-label input:checked + .checkmark::after {
  content: '';
  position: absolute;
  left: 6px;
  top: 2px;
  width: 5px;
  height: 10px;
  border: solid #ffffff;
  border-width: 0 2px 2px 0;
  transform: rotate(45deg);
}

.login-btn {
  width: 100%;
  height: 48px;
  background: #3b82f6;
  color: #ffffff;
  border: none;
  border-radius: 10px;
  font-size: 16px;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.2s;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 8px;
}

.login-btn:hover:not(:disabled) {
  background: #2563eb;
}

.login-btn:disabled {
  opacity: 0.6;
  cursor: not-allowed;
}

.btn-spinner {
  width: 20px;
  height: 20px;
  border: 2px solid rgba(255, 255, 255, 0.3);
  border-top-color: #ffffff;
  border-radius: 50%;
  animation: spin 0.8s linear infinite;
}

@keyframes spin {
  to { transform: rotate(360deg); }
}

.login-footer {
  margin-top: 24px;
  text-align: center;
}

.login-footer p {
  font-size: 12px;
  color: #9ca3af;
  margin: 0;
}

.login-bg-decoration {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  pointer-events: none;
  overflow: hidden;
}

.decoration-circle {
  position: absolute;
  border-radius: 50%;
  opacity: 0.1;
}

.circle-1 {
  width: 300px;
  height: 300px;
  background: #ffffff;
  top: -100px;
  right: -100px;
}

.circle-2 {
  width: 200px;
  height: 200px;
  background: #ffffff;
  bottom: -50px;
  left: -50px;
}

.circle-3 {
  width: 150px;
  height: 150px;
  background: #ffffff;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
}
</style>