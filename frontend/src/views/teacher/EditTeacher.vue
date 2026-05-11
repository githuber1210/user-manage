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
          编辑教师
        </h2>
        <button class="btn btn-secondary" @click="$router.push('/teachers')">
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
          <span class="loading-text">正在加载教师数据...</span>
        </div>

        <form v-else @submit.prevent="handleSubmit" class="form">
          <div class="form-grid">
            <div class="form-group">
              <label class="form-label">ID</label>
              <input type="text" :value="form.id" class="form-input" disabled />
            </div>

            <div class="form-group">
              <label class="form-label">工号 <span class="required">*</span></label>
              <input type="text" v-model="form.teacherNo" class="form-input" placeholder="请输入工号" />
              <span v-if="errors.teacherNo" class="error-text">{{ errors.teacherNo }}</span>
            </div>

            <div class="form-group">
              <label class="form-label">姓名 <span class="required">*</span></label>
              <input type="text" v-model="form.name" class="form-input" placeholder="请输入姓名" />
              <span v-if="errors.name" class="error-text">{{ errors.name }}</span>
            </div>

            <div class="form-group">
              <label class="form-label">性别</label>
              <select v-model="form.gender" class="form-select">
                <option value="">请选择</option>
                <option value="男">男</option>
                <option value="女">女</option>
              </select>
            </div>

            <div class="form-group">
              <label class="form-label">职称</label>
              <select v-model="form.title" class="form-select">
                <option value="">请选择</option>
                <option value="教授">教授</option>
                <option value="副教授">副教授</option>
                <option value="讲师">讲师</option>
                <option value="助教">助教</option>
              </select>
            </div>

            <div class="form-group">
              <label class="form-label">所属专业</label>
              <select v-model="form.majorId" class="form-select">
                <option value="">请选择专业</option>
                <option v-for="major in majors" :key="major.id" :value="major.id">
                  {{ major.majorName }}
                </option>
              </select>
            </div>

            <div class="form-group">
              <label class="form-label">所属学院</label>
              <input type="text" v-model="form.college" class="form-input" placeholder="如: 计算机学院" />
            </div>

            <div class="form-group">
              <label class="form-label">联系电话</label>
              <input type="text" v-model="form.phone" class="form-input" placeholder="请输入联系电话" />
            </div>

            <div class="form-group">
              <label class="form-label">邮箱</label>
              <input type="email" v-model="form.email" class="form-input" placeholder="请输入邮箱" />
              <span v-if="errors.email" class="error-text">{{ errors.email }}</span>
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
            <button type="button" class="btn btn-secondary" @click="$router.push('/teachers')">取消</button>
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
import { teacherApi } from '../../api/teacher'
import { majorApi } from '../../api/major'

const router = useRouter()
const route = useRoute()
const loading = ref(true)
const submitting = ref(false)
const errors = reactive({})
const majors = ref([])

const form = reactive({
  id: '',
  teacherNo: '',
  name: '',
  gender: '',
  title: '',
  majorId: '',
  college: '',
  phone: '',
  email: '',
  enabled: true
})

const fetchMajors = async () => {
  try {
    const response = await majorApi.getAllMajors()
    if (response.code === 200) {
      majors.value = response.data || []
    }
  } catch (error) {
    console.error('获取专业列表失败:', error)
  }
}

const fetchTeacher = async () => {
  loading.value = true
  try {
    const response = await teacherApi.getTeacherById(route.params.id)
    if (response.code === 200 && response.data) {
      const data = response.data
      form.id = data.id
      form.teacherNo = data.teacherNo
      form.name = data.name
      form.gender = data.gender || ''
      form.title = data.title || ''
      form.majorId = data.majorId || ''
      form.college = data.college || ''
      form.phone = data.phone || ''
      form.email = data.email || ''
      form.enabled = data.enabled
    }
  } catch (error) {
    console.error('获取教师信息失败:', error)
    alert('获取教师信息失败')
    router.push('/teachers')
  } finally {
    loading.value = false
  }
}

const validate = () => {
  Object.keys(errors).forEach(key => delete errors[key])
  
  if (!form.teacherNo.trim()) {
    errors.teacherNo = '工号不能为空'
  }
  
  if (!form.name.trim()) {
    errors.name = '姓名不能为空'
  }
  
  if (form.email && !/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(form.email)) {
    errors.email = '邮箱格式不正确'
  }
  
  return Object.keys(errors).length === 0
}

const handleSubmit = async () => {
  if (!validate()) return
  
  submitting.value = true
  try {
    const data = { ...form }
    if (!data.majorId) delete data.majorId
    const response = await teacherApi.updateTeacher(route.params.id, data)
    if (response.code === 200) {
      alert('保存成功')
      router.push('/teachers')
    }
  } catch (error) {
    console.error('保存教师失败:', error)
  } finally {
    submitting.value = false
  }
}

onMounted(() => {
  Promise.all([fetchTeacher(), fetchMajors()])
})
</script>

<style scoped>
.page-container {
  max-width: 900px;
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
  color: #f97316;
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
  border-top-color: #f97316;
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
  border-color: #f97316;
  box-shadow: 0 0 0 3px rgba(249, 115, 22, 0.15);
  background: #ffffff;
}

.form-input:disabled {
  cursor: not-allowed;
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
  background: #f97316;
  color: #ffffff;
}

.btn-primary:hover:not(:disabled) {
  background: #ea580c;
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
