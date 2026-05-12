<template>
  <div class="page-container">
    <div class="form-card">
      <div class="card-header">
        <h2 class="card-title">
          <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
            <path d="M12 22s8-4 8-10V5l-8-3-8 3v7c0 6 8 10 8 10z"/>
            <path d="M11 4H4a2 2 0 0 0-2 2v14a2 2 0 0 0 2 2h14a2 2 0 0 0 2-2v-7"/>
            <path d="M18.5 2.5a2.121 2.121 0 0 1 3 3L12 15l-4 1 1-4 9.5-9.5z"/>
          </svg>
          编辑角色
        </h2>
        <button class="btn btn-secondary" @click="$router.push('/roles')">
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
          <span class="loading-text">正在加载角色数据...</span>
        </div>

        <form v-else @submit.prevent="handleSubmit" class="form">
          <div class="form-grid">
            <div class="form-group">
              <label class="form-label">ID</label>
              <input type="text" :value="form.id" class="form-input" disabled />
            </div>

            <div class="form-group">
              <label class="form-label">角色名称 <span class="required">*</span></label>
              <input type="text" v-model="form.roleName" class="form-input" placeholder="请输入角色名称" />
              <span v-if="errors.roleName" class="error-text">{{ errors.roleName }}</span>
            </div>

            <div class="form-group">
              <label class="form-label">角色编码 <span class="required">*</span></label>
              <input type="text" v-model="form.roleCode" class="form-input" placeholder="如: ADMIN, USER" />
              <span class="hint-text">只能包含大写字母和下划线</span>
              <span v-if="errors.roleCode" class="error-text">{{ errors.roleCode }}</span>
            </div>

            <div class="form-group form-group-full">
              <label class="form-label">描述</label>
              <textarea v-model="form.description" class="form-textarea" placeholder="请输入角色描述" rows="3"></textarea>
            </div>

            <div class="form-group form-group-full">
              <label class="form-label">权限标识</label>
              <input type="text" v-model="form.permissions" class="form-input" placeholder="多个权限用逗号分隔，如: user:read,user:write" />
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
            <button type="button" class="btn btn-secondary" @click="$router.push('/roles')">取消</button>
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
import { roleApi } from '../../api/role'

const router = useRouter()
const route = useRoute()
const loading = ref(true)
const submitting = ref(false)
const errors = reactive({})

const form = reactive({
  id: '',
  roleName: '',
  roleCode: '',
  description: '',
  permissions: '',
  enabled: true
})

const fetchRole = async () => {
  loading.value = true
  try {
    const response = await roleApi.getRoleById(route.params.id)
    if (response.code === 200 && response.data) {
      Object.assign(form, response.data)
    }
  } catch (error) {
    console.error('获取角色信息失败:', error)
    alert('获取角色信息失败')
    router.push('/roles')
  } finally {
    loading.value = false
  }
}

const validate = () => {
  Object.keys(errors).forEach(key => delete errors[key])
  
  if (!form.roleName.trim()) {
    errors.roleName = '角色名称不能为空'
  }
  
  if (!form.roleCode.trim()) {
    errors.roleCode = '角色编码不能为空'
  } else if (!/^[A-Z_]+$/.test(form.roleCode)) {
    errors.roleCode = '角色编码只能包含大写字母和下划线'
  }
  
  return Object.keys(errors).length === 0
}

const handleSubmit = async () => {
  if (!validate()) return
  
  submitting.value = true
  try {
    const response = await roleApi.updateRole(route.params.id, form)
    if (response.code === 200) {
      alert('保存成功')
      router.push('/roles')
    }
  } catch (error) {
    console.error('保存角色失败:', error)
  } finally {
    submitting.value = false
  }
}

onMounted(() => {
  fetchRole()
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
  color: var(--primary);
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
  border-top-color: var(--primary);
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
  background: var(--header-bg);
  transition: border-color 0.2s, box-shadow 0.2s;
}

.form-input:focus,
.form-textarea:focus {
  outline: none;
  border-color: var(--primary);
  box-shadow: 0 0 0 3px rgba(59, 130, 246, 0.15);
  background: var(--input-bg); color: var(--input-text);
}

.form-input:disabled {
  cursor: not-allowed;
}

.form-textarea {
  resize: vertical;
  min-height: 80px;
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
  background: var(--primary);
  color: #ffffff;
}

.btn-primary:hover:not(:disabled) {
  background: #2563eb;
}

.btn-primary:disabled {
  opacity: 0.6;
  cursor: not-allowed;
}

.btn-secondary { background: var(--hover-bg);
  color: var(--text-primary);
}

.btn-secondary:hover { background: var(--border-color);
}
</style>
