<template>
  <div class="page-container">
    <div class="form-card">
      <div class="card-header">
        <h2 class="card-title">
          <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
            <path d="M4 19.5A2.5 2.5 0 0 1 6.5 17H20"/>
            <path d="M6.5 2H20v20H6.5A2.5 2.5 0 0 1 4 19.5v-15A2.5 2.5 0 0 1 6.5 2z"/>
            <path d="M11 4H4a2 2 0 0 0-2 2v14a2 2 0 0 0 2 2h14a2 2 0 0 0 2-2v-7"/>
            <path d="M18.5 2.5a2.121 2.121 0 0 1 3 3L12 15l-4 1 1-4 9.5-9.5z"/>
          </svg>
          编辑专业
        </h2>
        <button class="btn btn-secondary" @click="$router.push('/majors')">
          <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
            <line x1="19" y1="12" x2="5" y2="12"/>
            <polyline points="12 19 5 12 12 5"/>
          </svg>
          返回列表
        </button>
      </div>

      <div class="card-body">
        <div v-if="loading" class="loading-state">
          <div class="spinner"></div>
          <span class="loading-text">正在加载专业数据...</span>
        </div>

        <form v-else @submit.prevent="handleSubmit" class="form">
          <div class="form-grid">
            <div class="form-group">
              <label class="form-label">ID</label>
              <input type="text" :value="form.id" class="form-input" disabled />
            </div>

            <div class="form-group">
              <label class="form-label">专业名称 <span class="required">*</span></label>
              <input type="text" v-model="form.majorName" class="form-input" placeholder="请输入专业名称" />
              <span v-if="errors.majorName" class="error-text">{{ errors.majorName }}</span>
            </div>

            <div class="form-group">
              <label class="form-label">专业代码 <span class="required">*</span></label>
              <input type="text" v-model="form.majorCode" class="form-input" placeholder="如: CS, SE" />
              <span v-if="errors.majorCode" class="error-text">{{ errors.majorCode }}</span>
            </div>

            <div class="form-group">
              <label class="form-label">所属学院</label>
              <input type="text" v-model="form.college" class="form-input" placeholder="如: 计算机学院" />
            </div>

            <div class="form-group">
              <label class="form-label">学制（年）</label>
              <input type="number" v-model="form.duration" class="form-input" placeholder="如: 4" min="1" />
              <span class="hint-text">请输入正整数</span>
            </div>

            <div class="form-group form-group-full">
              <label class="form-label">培养方案</label>
              <textarea v-model="form.trainingProgram" class="form-textarea" placeholder="请输入培养方案" rows="4"></textarea>
            </div>

            <div class="form-group">
              <label class="form-label">状态</label>
              <div class="toggle-wrapper">
                <label class="toggle">
                  <input type="checkbox" v-model="form.enabled" />
                  <span class="toggle-slider"></span>
                </label>
                <span class="toggle-label">{{ form.enabled ? '启用' : '禁用' }}</span>
              </div>
            </div>
          </div>

          <div class="form-actions">
            <button type="button" class="btn btn-secondary" @click="$router.push('/majors')">取消</button>
            <button type="submit" class="btn btn-primary" :disabled="submitting">
              <span v-if="submitting">保存中...</span>
              <span v-else>保存修改</span>
            </button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { majorApi } from '../../api/major'

const router = useRouter()
const route = useRoute()
const loading = ref(true)
const submitting = ref(false)
const errors = reactive({})

const form = reactive({
  id: '',
  majorName: '',
  majorCode: '',
  college: '',
  trainingProgram: '',
  duration: 4,
  enabled: true
})

const fetchMajor = async () => {
  loading.value = true
  try {
    const response = await majorApi.getMajorById(route.params.id)
    if (response.code === 200 && response.data) {
      Object.assign(form, response.data)
    }
  } catch (error) {
    console.error('获取专业信息失败:', error)
    alert('获取专业信息失败')
    router.push('/majors')
  } finally {
    loading.value = false
  }
}

const validate = () => {
  Object.keys(errors).forEach(key => delete errors[key])
  
  if (!form.majorName.trim()) {
    errors.majorName = '专业名称不能为空'
  }
  
  if (!form.majorCode.trim()) {
    errors.majorCode = '专业代码不能为空'
  }
  
  if (form.duration && form.duration < 1) {
    errors.duration = '学制必须为正整数'
  }
  
  return Object.keys(errors).length === 0
}

const handleSubmit = async () => {
  if (!validate()) return
  
  submitting.value = true
  try {
    const response = await majorApi.updateMajor(route.params.id, form)
    if (response.code === 200) {
      alert('保存成功')
      router.push('/majors')
    }
  } catch (error) {
    console.error('保存专业失败:', error)
  } finally {
    submitting.value = false
  }
}

onMounted(() => {
  fetchMajor()
})
</script>

<style scoped>
.page-container {
  max-width: 800px;
}

.form-card {
  background: var(--card-bg);
  border-radius: var(--radius-lg);
  box-shadow: var(--card-shadow);
  overflow: hidden;
}

.card-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 16px 20px;
  border-bottom: 1px solid var(--border-color);
}

.card-title {
  display: flex;
  align-items: center;
  gap: 8px;
  font-size: 16px;
  font-weight: 600;
  color: var(--text-primary);
  margin: 0;
}

.card-title svg {
  color: #8b5cf6;
}

.card-body {
  padding: 24px;
}

.loading-state {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 60px 20px;
  gap: 16px;
}

.spinner {
  width: 40px;
  height: 40px;
  border: 3px solid var(--border-color);
  border-top-color: #8b5cf6;
  border-radius: 50%;
  animation: spin 0.8s linear infinite;
}

@keyframes spin {
  to { transform: rotate(360deg); }
}

.loading-text {
  font-size: 14px;
  color: var(--text-secondary);
}

.form-grid {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 20px;
}

.form-group-full {
  grid-column: 1 / -1;
}

.form-group {
  display: flex;
  flex-direction: column;
  gap: 6px;
}

.form-label {
  font-size: 14px;
  font-weight: 500;
  color: var(--text-primary);
}

.form-input,
.form-textarea {
  padding: 10px 14px;
  border: 1px solid var(--border-color);
  border-radius: var(--radius);
  font-size: 14px;
  background: #f8fafc;
  transition: border-color 0.2s, box-shadow 0.2s;
}

.form-input:focus,
.form-textarea:focus {
  outline: none;
  border-color: #8b5cf6;
  box-shadow: 0 0 0 3px rgba(139, 92, 246, 0.15);
  background: #ffffff;
}

.form-input:disabled {
  cursor: not-allowed;
}

.form-textarea {
  resize: vertical;
  min-height: 100px;
}

.hint-text {
  font-size: 12px;
  color: var(--text-secondary);
}

.error-text {
  font-size: 12px;
  color: var(--danger);
}

.toggle-wrapper {
  display: flex;
  align-items: center;
  gap: 12px;
}

.toggle {
  position: relative;
  width: 44px;
  height: 24px;
}

.toggle input {
  opacity: 0;
  width: 0;
  height: 0;
}

.toggle-slider {
  position: absolute;
  cursor: pointer;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: #ccc;
  transition: 0.3s;
  border-radius: 24px;
}

.toggle-slider:before {
  position: absolute;
  content: "";
  height: 18px;
  width: 18px;
  left: 3px;
  bottom: 3px;
  background-color: white;
  transition: 0.3s;
  border-radius: 50%;
}

.toggle input:checked + .toggle-slider {
  background-color: var(--success);
}

.toggle input:checked + .toggle-slider:before {
  transform: translateX(20px);
}

.toggle-label {
  font-size: 14px;
  color: var(--text-primary);
}

.form-actions {
  display: flex;
  justify-content: flex-end;
  gap: 12px;
  margin-top: 24px;
  padding-top: 24px;
  border-top: 1px solid var(--border-color);
}

.btn {
  display: inline-flex;
  align-items: center;
  gap: 8px;
  padding: 10px 20px;
  border-radius: var(--radius);
  font-size: 14px;
  font-weight: 500;
  border: none;
  cursor: pointer;
  transition: all 0.2s;
}

.btn-primary {
  background: #8b5cf6;
  color: #ffffff;
}

.btn-primary:hover:not(:disabled) {
  background: #7c3aed;
}

.btn-primary:disabled {
  opacity: 0.6;
  cursor: not-allowed;
}

.btn-secondary {
  background: #f1f5f9;
  color: var(--text-primary);
}

.btn-secondary:hover {
  background: #e2e8f0;
}
</style>
