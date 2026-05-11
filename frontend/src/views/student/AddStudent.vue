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
          添加学生
        </h2>
        <button class="btn btn-secondary" @click="$router.push('/students')">
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
              <label class="form-label">学号 <span class="required">*</span></label>
              <input type="text" v-model="form.studentNo" class="form-input" placeholder="请输入学号" />
              <span v-if="errors.studentNo" class="error-text">{{ errors.studentNo }}</span>
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
              <label class="form-label">出生日期</label>
              <input type="date" v-model="form.birthDate" class="form-input" />
            </div>

            <div class="form-group">
              <label class="form-label">班级 <span class="required">*</span></label>
              <select v-model="form.classId" class="form-select">
                <option value="">请选择班级</option>
                <option v-for="cls in classes" :key="cls.id" :value="cls.id">
                  {{ cls.className }}
                </option>
              </select>
              <span v-if="errors.classId" class="error-text">{{ errors.classId }}</span>
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
              <select v-model="form.status" class="form-select">
                <option value="在读">在读</option>
                <option value="休学">休学</option>
                <option value="毕业">毕业</option>
              </select>
            </div>
          </div>

          <div class="form-actions">
            <button type="button" class="btn btn-secondary" @click="$router.push('/students')">取消</button>
            <button type="submit" class="btn btn-primary" :disabled="submitting">
              <span v-if="submitting">提交中...</span>
              <span v-else>创建学生</span>
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
import { studentApi } from '../api/student'
import { classApi } from '../api/class'

const router = useRouter()
const submitting = ref(false)
const errors = reactive({})
const classes = ref([])

const form = reactive({
  studentNo: '',
  name: '',
  gender: '',
  birthDate: '',
  classId: '',
  phone: '',
  email: '',
  status: '在读'
})

const fetchClasses = async () => {
  try {
    const response = await classApi.getAllClasses()
    if (response.code === 200) {
      classes.value = response.data || []
    }
  } catch (error) {
    console.error('获取班级列表失败:', error)
  }
}

const validate = () => {
  Object.keys(errors).forEach(key => delete errors[key])
  
  if (!form.studentNo.trim()) {
    errors.studentNo = '学号不能为空'
  }
  
  if (!form.name.trim()) {
    errors.name = '姓名不能为空'
  }
  
  if (!form.classId) {
    errors.classId = '请选择班级'
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
    const response = await studentApi.createStudent(form)
    if (response.code === 200) {
      alert('创建成功')
      router.push('/students')
    }
  } catch (error) {
    console.error('创建学生失败:', error)
  } finally {
    submitting.value = false
  }
}

onMounted(() => {
  fetchClasses()
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
  color: #6366f1;
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
  border-color: #6366f1;
  box-shadow: 0 0 0 3px rgba(99, 102, 241, 0.15);
}

.error-text {
  font-size: 12px;
  color: var(--danger);
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
  background: #6366f1;
  color: #ffffff;
}

.btn-primary:hover:not(:disabled) {
  background: #4f46e5;
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
