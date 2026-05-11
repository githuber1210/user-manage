<template>
  <div class="page-container">
    <div class="form-card">
      <div class="card-header">
        <h2 class="card-title">
          <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
            <path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"/>
            <circle cx="9" cy="7" r="4"/>
            <line x1="12" y1="5" x2="12" y2="19"/>
            <line x1="5" y1="12" x2="19" y2="12"/>
          </svg>
          添加班级
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
        <form @submit.prevent="handleSubmit" class="form">
          <div class="form-grid">
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
              <span v-if="submitting">提交中...</span>
              <span v-else>创建班级</span>
            </button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { classApi } from '../api/class'
import { majorApi } from '../api/major'
import { teacherApi } from '../api/teacher'

const router = useRouter()
const submitting = ref(false)
const errors = reactive({})
const majors = ref([])
const teachers = ref([])

const form = reactive({
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
    const response = await classApi.createClass(data)
    if (response.code === 200) {
      alert('创建成功')
      router.push('/classes')
    }
  } catch (error) {
    console.error('创建班级失败:', error)
  } finally {
    submitting.value = false
  }
}

onMounted(() => {
  fetchOptions()
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

.required {
  color: var(--danger);
}

.form-input,
.form-select {
  padding: 10px 14px;
  border: 1px solid var(--border-color);
  border-radius: var(--radius);
  font-size: 14px;
  transition: border-color 0.2s, box-shadow 0.2s;
  background: #ffffff;
}

.form-input:focus,
.form-select:focus {
  outline: none;
  border-color: #ec4899;
  box-shadow: 0 0 0 3px rgba(236, 72, 153, 0.15);
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
