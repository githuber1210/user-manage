<template>
  <div class="page-container">
    <div class="form-card">
      <div class="card-header">
        <h2 class="card-title">
          <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
            <path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"/>
            <circle cx="9" cy="7" r="4"/>
            <path d="M11 4H4a2 2 0 0 0-2 2v14a2 2 0 0 0 2 2h14a2 2 0 0 0 2-2v-7"/>
            <path d="M18.5 2.5a2.121 2.121 0 0 1 3 3L12 15l-4 1 1-4 9.5-9.5z"/>
          </svg>
          编辑班级
        </h2>
        <button class="btn btn-secondary" @click="$router.push('/classes')">
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
          <span class="loading-text">正在加载班级数据...</span>
        </div>

        <form v-else @submit.prevent="handleSubmit" class="form">
          <div class="form-grid">
            <div class="form-group">
              <label class="form-label">ID</label>
              <input type="text" :value="form.id" class="form-input" disabled />
            </div>

            <div class="form-group">
              <label class="form-label">班级名称 <span class="required">*</span></label>
              <input type="text" v-model="form.className" class="form-input" placeholder="如: 计算机2021级1班" />
              <span v-if="errors.className" class="error-text">{{ errors.className }}</span>
            </div>

            <div class="form-group">
              <label class="form-label">所属专业 <span class="required">*</span></label>
              <select v-model="form.majorId" class="form-select">
                <option value="">请选择专业</option>
                <option v-for="major in majors" :key="major.id" :value="major.id">
                  {{ major.majorName }}
                </option>
              </select>
              <span v-if="errors.majorId" class="error-text">{{ errors.majorId }}</span>
            </div>

            <div class="form-group">
              <label class="form-label">年级 <span class="required">*</span></label>
              <input type="text" v-model="form.grade" class="form-input" placeholder="如: 2021" />
              <span class="hint-text">请输入年级，如: 2021</span>
              <span v-if="errors.grade" class="error-text">{{ errors.grade }}</span>
            </div>

            <div class="form-group">
              <label class="form-label">班主任</label>
              <select v-model="form.teacherId" class="form-select">
                <option value="">请选择班主任</option>
                <option v-for="teacher in teachers" :key="teacher.id" :value="teacher.id">
                  {{ teacher.name }} ({{ teacher.teacherNo }})
                </option>
              </select>
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
            <button type="button" class="btn btn-secondary" @click="$router.push('/classes')">取消</button>
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
import { classApi } from '../../api/class'
import { majorApi } from '../../api/major'
import { teacherApi } from '../../api/teacher'

const router = useRouter()
const route = useRoute()
const loading = ref(true)
const submitting = ref(false)
const errors = reactive({})
const majors = ref([])
const teachers = ref([])

const form = reactive({
  id: '',
  className: '',
  majorId: '',
  grade: '',
  teacherId: '',
  enabled: true
})

const fetchOptions = async () => {
  try {
    const [majorsRes, teachersRes] = await Promise.all([
      majorApi.getAllMajors(),
      teacherApi.getAllTeachers()
    ])
    if (majorsRes.code === 200) {
      majors.value = majorsRes.data || []
    }
    if (teachersRes.code === 200) {
      teachers.value = teachersRes.data || []
    }
  } catch (error) {
    console.error('获取选项数据失败:', error)
  }
}

const fetchClass = async () => {
  loading.value = true
  try {
    const response = await classApi.getClassById(route.params.id)
    if (response.code === 200 && response.data) {
      const data = response.data
      form.id = data.id
      form.className = data.className
      form.majorId = data.majorId
      form.grade = data.grade
      form.teacherId = data.teacherId || ''
      form.enabled = data.enabled
    }
  } catch (error) {
    console.error('获取班级信息失败:', error)
    alert('获取班级信息失败')
    router.push('/classes')
  } finally {
    loading.value = false
  }
}

const validate = () => {
  Object.keys(errors).forEach(key => delete errors[key])
  
  if (!form.className.trim()) {
    errors.className = '班级名称不能为空'
  }
  
  if (!form.majorId) {
    errors.majorId = '请选择专业'
  }
  
  if (!form.grade.trim()) {
    errors.grade = '年级不能为空'
  }
  
  return Object.keys(errors).length === 0
}

const handleSubmit = async () => {
  if (!validate()) return
  
  submitting.value = true
  try {
    const data = { ...form }
    if (!data.teacherId) delete data.teacherId
    const response = await classApi.updateClass(route.params.id, data)
    if (response.code === 200) {
      alert('保存成功')
      router.push('/classes')
    }
  } catch (error) {
    console.error('保存班级失败:', error)
  } finally {
    submitting.value = false
  }
}

onMounted(() => {
  Promise.all([fetchClass(), fetchOptions()])
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
  color: #ec4899;
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
  border-top-color: #ec4899;
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
.form-select {
  padding: 10px 14px;
  border: 1px solid var(--border-color);
  border-radius: var(--radius);
  font-size: 14px;
  background: #f8fafc;
  transition: border-color 0.2s, box-shadow 0.2s;
}

.form-input:focus,
.form-select:focus {
  outline: none;
  border-color: #ec4899;
  box-shadow: 0 0 0 3px rgba(236, 72, 153, 0.15);
  background: #ffffff;
}

.form-input:disabled {
  cursor: not-allowed;
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
  padding-top: 8px;
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
  background: #ec4899;
  color: #ffffff;
}

.btn-primary:hover:not(:disabled) {
  background: #db2777;
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
