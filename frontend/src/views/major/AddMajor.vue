<template>
  <div class="page-container">
    <div class="form-card">
      <div class="card-header">
        <h2 class="card-title">
          <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
            <path d="M4 19.5A2.5 2.5 0 0 1 6.5 17H20"/>
            <path d="M6.5 2H20v20H6.5A2.5 2.5 0 0 1 4 19.5v-15A2.5 2.5 0 0 1 6.5 2z"/>
            <line x1="12" y1="5" x2="12" y2="19"/>
            <line x1="5" y1="12" x2="19" y2="12"/>
          </svg>
          添加专业
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
        <form @submit.prevent="handleSubmit" class="form">
          <div class="form-grid">
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
              <span v-if="submitting">提交中...</span>
              <span v-else>创建专业</span>
            </button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { majorApi } from '../../api/major'

const router = useRouter()
const submitting = ref(false)
const errors = reactive({})

const form = reactive({
  majorName: '',
  majorCode: '',
  college: '',
  trainingProgram: '',
  duration: 4,
  enabled: true
})

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
    const response = await majorApi.createMajor(form)
    if (response.code === 200) {
      alert('创建成功')
      router.push('/majors')
    }
  } catch (error) {
    console.error('创建专业失败:', error)
  } finally {
    submitting.value = false
  }
}
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

.required {
  color: var(--danger);
}

.form-input,
.form-textarea {
  padding: 10px 14px;
  border: 1px solid var(--border-color);
  border-radius: var(--radius);
  font-size: 14px;
  transition: border-color 0.2s, box-shadow 0.2s;
}

.form-input:focus,
.form-textarea:focus {
  outline: none;
  border-color: #8b5cf6;
  box-shadow: 0 0 0 3px rgba(139, 92, 246, 0.15);
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
