<template>
  <div class="page-container">
    <div class="stats-grid">
      <div class="stat-card">
        <div class="stat-icon bg-primary">
          <svg xmlns="http://www.w3.org/2000/svg" width="22" height="22" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
            <path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"/>
            <circle cx="9" cy="7" r="4"/>
            <path d="M23 21v-2a4 4 0 0 0-3-3.87"/>
            <path d="M16 3.13a4 4 0 0 1 0 7.75"/>
          </svg>
        </div>
        <div class="stat-content">
          <div class="stat-label">教师总数</div>
          <div class="stat-value">{{ teachers.length }}</div>
        </div>
      </div>
      <div class="stat-card">
        <div class="stat-icon bg-success">
          <svg xmlns="http://www.w3.org/2000/svg" width="22" height="22" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
            <path d="M22 10v6M2 10l10-5 10 5-10 5z"/>
            <path d="M6 12v5c0 1.657 2.686 3 6 3s6-1.343 6-3v-5"/>
          </svg>
        </div>
        <div class="stat-content">
          <div class="stat-label">所属学院</div>
          <div class="stat-value">{{ collegeCount }}</div>
        </div>
      </div>
      <div class="stat-card">
        <div class="stat-icon bg-info">
          <svg xmlns="http://www.w3.org/2000/svg" width="22" height="22" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
            <rect x="3" y="4" width="18" height="18" rx="2" ry="2"/>
            <line x1="16" y1="2" x2="16" y2="6"/>
            <line x1="8" y1="2" x2="8" y2="6"/>
            <line x1="3" y1="10" x2="21" y2="10"/>
          </svg>
        </div>
        <div class="stat-content">
          <div class="stat-label">已启用</div>
          <div class="stat-value">{{ enabledCount }}</div>
        </div>
      </div>
    </div>

    <div class="data-card">
      <div class="card-header">
        <h2 class="card-title">
          <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
            <path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"/>
            <circle cx="9" cy="7" r="4"/>
            <path d="M23 21v-2a4 4 0 0 0-3-3.87"/>
            <path d="M16 3.13a4 4 0 0 1 0 7.75"/>
          </svg>
          教师列表
        </h2>
        <button class="btn btn-primary" @click="$router.push('/teachers/add')">
          <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
            <line x1="12" y1="5" x2="12" y2="19"/>
            <line x1="5" y1="12" x2="19" y2="12"/>
          </svg>
          添加教师
        </button>
      </div>

      <div class="search-bar">
        <div class="search-input-wrapper">
          <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" class="search-icon">
            <circle cx="11" cy="11" r="8"/>
            <line x1="21" y1="21" x2="16.65" y2="16.65"/>
          </svg>
          <input type="text" v-model="searchQuery" class="search-input" placeholder="搜索姓名或工号..." />
          <button v-if="searchQuery" class="clear-btn" @click="searchQuery = ''">
            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
              <line x1="18" y1="6" x2="6" y2="18"/>
              <line x1="6" y1="6" x2="18" y2="18"/>
            </svg>
          </button>
        </div>
      </div>

      <div class="card-body">
        <div v-if="loading" class="loading-state">
          <div class="spinner"></div>
          <span class="loading-text">正在加载教师数据...</span>
        </div>

        <div v-else>
          <div v-if="paginatedTeachers.length === 0" class="empty-state">
            <svg xmlns="http://www.w3.org/2000/svg" width="64" height="64" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.5" class="empty-icon">
              <path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"/>
              <circle cx="9" cy="7" r="4"/>
            </svg>
            <h3 class="empty-title">{{ searchQuery ? '未找到匹配的教师' : '暂无教师数据' }}</h3>
            <p class="empty-desc">{{ searchQuery ? '尝试使用其他关键词搜索' : '添加第一位教师开始管理' }}</p>
            <router-link v-if="!searchQuery" to="/teachers/add" class="btn btn-primary">
              <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                <line x1="12" y1="5" x2="12" y2="19"/>
                <line x1="5" y1="12" x2="19" y2="12"/>
              </svg>
              添加教师
            </router-link>
          </div>

          <div v-else class="table-responsive">
            <table class="data-table">
              <thead>
                <tr>
                  <th class="col-id">ID</th>
                  <th class="col-info">教师信息</th>
                  <th class="col-major">所属专业</th>
                  <th class="col-title">职称</th>
                  <th class="col-contact">联系方式</th>
                  <th class="col-status">状态</th>
                  <th class="col-actions">操作</th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="teacher in paginatedTeachers" :key="teacher.id" class="table-row">
                  <td class="cell-id">
                    <span class="id-badge">{{ teacher.id }}</span>
                  </td>
                  <td class="cell-info">
                    <div class="teacher-avatar">{{ teacher.name.charAt(0) }}</div>
                    <div class="teacher-details">
                      <div class="teacher-name">{{ teacher.name }}</div>
                      <div class="teacher-no">{{ teacher.teacherNo }}</div>
                    </div>
                  </td>
                  <td class="cell-major">{{ teacher.majorName || '-' }}</td>
                  <td class="cell-title">
                    <span class="title-tag">{{ teacher.title || '-' }}</span>
                  </td>
                  <td class="cell-contact">
                    <div class="contact-info">
                      <div>{{ teacher.phone || '-' }}</div>
                      <div class="email-text">{{ teacher.email || '-' }}</div>
                    </div>
                  </td>
                  <td class="cell-status">
                    <span :class="['status-tag', teacher.enabled ? 'status-active' : 'status-disabled']">
                      {{ teacher.enabled ? '启用' : '禁用' }}
                    </span>
                  </td>
                  <td class="cell-actions">
                    <div class="action-group">
                      <button class="action-btn action-edit" @click="editTeacher(teacher.id)" title="编辑">
                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                          <path d="M11 4H4a2 2 0 0 0-2 2v14a2 2 0 0 0 2 2h14a2 2 0 0 0 2-2v-7"/>
                          <path d="M18.5 2.5a2.121 2.121 0 0 1 3 3L12 15l-4 1 1-4 9.5-9.5z"/>
                        </svg>
                      </button>
                      <button class="action-btn action-delete" @click="deleteTeacher(teacher.id, teacher.name)" title="删除">
                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                          <polyline points="3 6 5 6 21 6"/>
                          <path d="M19 6v14a2 2 0 0 1-2 2H7a2 2 0 0 1-2-2V6m3 0V4a2 2 0 0 1 2-2h4a2 2 0 0 1 2 2v2"/>
                        </svg>
                      </button>
                    </div>
                  </td>
                </tr>
              </tbody>
            </table>

            <div v-if="totalPages > 1" class="pagination">
              <button class="page-btn" :disabled="currentPage === 1" @click="currentPage--">
                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                  <polyline points="15 18 9 12 15 6"/>
                </svg>
              </button>
              <span class="page-info">{{ currentPage }} / {{ totalPages }}</span>
              <button class="page-btn" :disabled="currentPage === totalPages" @click="currentPage++">
                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                  <polyline points="9 18 15 12 9 6"/>
                </svg>
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { teacherApi } from '../../api/teacher'

const router = useRouter()
const teachers = ref([])
const loading = ref(true)
const searchQuery = ref('')
const currentPage = ref(1)
const pageSize = 10

const filteredTeachers = computed(() => {
  if (!searchQuery.value) return teachers.value
  const query = searchQuery.value.toLowerCase()
  return teachers.value.filter(t =>
    t.name.toLowerCase().includes(query) ||
    t.teacherNo.toLowerCase().includes(query)
  )
})

const totalPages = computed(() => Math.ceil(filteredTeachers.value.length / pageSize))

const paginatedTeachers = computed(() => {
  const start = (currentPage.value - 1) * pageSize
  return filteredTeachers.value.slice(start, start + pageSize)
})

const collegeCount = computed(() => {
  const colleges = new Set(teachers.value.map(t => t.college).filter(c => c))
  return colleges.size
})

const enabledCount = computed(() => teachers.value.filter(t => t.enabled).length)

const fetchTeachers = async () => {
  loading.value = true
  try {
    const response = await teacherApi.getAllTeachers()
    if (response.code === 200) {
      teachers.value = response.data || []
    }
  } catch (error) {
    console.error('获取教师列表失败:', error)
  } finally {
    loading.value = false
  }
}

const editTeacher = (id) => {
  router.push(`/teachers/edit/${id}`)
}

const deleteTeacher = async (id, name) => {
  if (!confirm(`确定要删除教师 "${name}" 吗？此操作无法撤销。`)) {
    return
  }
  try {
    const response = await teacherApi.deleteTeacher(id)
    if (response.code === 200) {
      alert('删除成功')
      fetchTeachers()
    }
  } catch (error) {
    console.error('删除教师失败:', error)
  }
}

onMounted(() => {
  fetchTeachers()
})
</script>

<style scoped>
.page-container {
  display: flex;
  flex-direction: column;
  gap: 24px;
}

.stats-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
  gap: 16px;
}

.stat-card {
  background: var(--card-bg);
  border-radius: var(--radius-lg);
  padding: 20px;
  display: flex;
  align-items: center;
  gap: 16px;
  box-shadow: var(--card-shadow);
  transition: box-shadow 0.2s, transform 0.2s;
}

.stat-card:hover {
  box-shadow: var(--card-shadow-hover);
  transform: translateY(-2px);
}

.stat-icon {
  width: 48px;
  height: 48px;
  border-radius: var(--radius);
  display: flex;
  align-items: center;
  justify-content: center;
  color: #ffffff;
}

.bg-primary { background: #f97316; }
.bg-success { background: var(--success); }
.bg-info { background: #14b8a6; }

.stat-content {
  display: flex;
  flex-direction: column;
}

.stat-label {
  font-size: 13px;
  color: var(--text-secondary);
  font-weight: 500;
}

.stat-value {
  font-size: 24px;
  font-weight: 700;
  color: var(--text-primary);
  line-height: 1.2;
}

.data-card {
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

.search-bar {
  padding: 16px 20px;
  border-bottom: 1px solid var(--border-color);
  background: #f8fafc;
}

.search-input-wrapper {
  position: relative;
  max-width: 400px;
}

.search-icon {
  position: absolute;
  left: 12px;
  top: 50%;
  transform: translateY(-50%);
  color: var(--text-secondary);
  pointer-events: none;
}

.search-input {
  width: 100%;
  height: 40px;
  padding: 0 40px 0 40px;
  border: 1px solid var(--border-color);
  border-radius: var(--radius);
  font-size: 14px;
  background: #ffffff;
  transition: border-color 0.2s, box-shadow 0.2s;
}

.search-input:focus {
  outline: none;
  border-color: #f97316;
  box-shadow: 0 0 0 3px rgba(249, 115, 22, 0.15);
}

.clear-btn {
  position: absolute;
  right: 8px;
  top: 50%;
  transform: translateY(-50%);
  background: none;
  border: none;
  color: var(--text-secondary);
  cursor: pointer;
  padding: 4px;
  border-radius: 4px;
  display: flex;
  align-items: center;
  justify-content: center;
  transition: all 0.2s;
}

.clear-btn:hover {
  background: var(--content-bg);
  color: var(--text-primary);
}

.card-body {
  padding: 0;
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

.table-responsive {
  overflow-x: auto;
}

.data-table {
  width: 100%;
  border-collapse: collapse;
}

.data-table thead th {
  padding: 12px 16px;
  text-align: left;
  font-size: 12px;
  font-weight: 600;
  color: var(--text-secondary);
  text-transform: uppercase;
  letter-spacing: 0.5px;
  background: #f8fafc;
  border-bottom: 1px solid var(--border-color);
  white-space: nowrap;
}

.table-row {
  border-bottom: 1px solid var(--border-color);
  transition: background 0.15s;
}

.table-row:last-child {
  border-bottom: none;
}

.table-row:hover {
  background: #f8fafc;
}

.table-row td {
  padding: 14px 16px;
  vertical-align: middle;
}

.col-id { width: 70px; }
.col-info { min-width: 200px; }
.col-major { min-width: 120px; }
.col-title { width: 100px; }
.col-contact { min-width: 180px; }
.col-status { width: 80px; }
.col-actions { width: 100px; text-align: right; }

.cell-id { text-align: center; }
.id-badge {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  width: 32px;
  height: 32px;
  background: rgba(249, 115, 22, 0.1);
  color: #f97316;
  border-radius: 8px;
  font-size: 13px;
  font-weight: 600;
}

.cell-info {
  display: flex;
  align-items: center;
  gap: 12px;
}

.teacher-avatar {
  width: 40px;
  height: 40px;
  background: #f97316;
  color: #ffffff;
  border-radius: var(--radius);
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 16px;
  font-weight: 600;
  flex-shrink: 0;
}

.teacher-name {
  font-weight: 600;
  color: var(--text-primary);
}

.teacher-no {
  font-size: 12px;
  color: var(--text-secondary);
}

.cell-major {
  font-size: 13px;
  color: var(--text-secondary);
}

.cell-title { text-align: center; }
.title-tag {
  display: inline-block;
  padding: 4px 10px;
  background: rgba(249, 115, 22, 0.1);
  color: #f97316;
  border-radius: 20px;
  font-size: 13px;
  font-weight: 500;
}

.cell-contact {
  font-size: 13px;
  color: var(--text-primary);
}

.email-text {
  font-size: 12px;
  color: var(--text-secondary);
  max-width: 150px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.cell-status { text-align: center; }
.status-tag {
  display: inline-block;
  padding: 4px 10px;
  border-radius: 20px;
  font-size: 12px;
  font-weight: 500;
}

.status-active {
  background: rgba(16, 185, 129, 0.1);
  color: var(--success);
}

.status-disabled {
  background: rgba(239, 68, 68, 0.1);
  color: var(--danger);
}

.cell-actions { text-align: right; }

.action-group {
  display: flex;
  gap: 8px;
  justify-content: flex-end;
}

.action-btn {
  width: 34px;
  height: 34px;
  display: flex;
  align-items: center;
  justify-content: center;
  border: none;
  border-radius: var(--radius);
  cursor: pointer;
  transition: all 0.2s;
}

.action-edit {
  background: rgba(59, 130, 246, 0.1);
  color: var(--primary);
}

.action-edit:hover {
  background: var(--primary);
  color: #ffffff;
}

.action-delete {
  background: rgba(239, 68, 68, 0.1);
  color: var(--danger);
}

.action-delete:hover {
  background: var(--danger);
  color: #ffffff;
}

.empty-state {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 60px 20px;
  text-align: center;
}

.empty-icon {
  color: var(--text-secondary);
  opacity: 0.3;
  margin-bottom: 16px;
}

.empty-title {
  font-size: 18px;
  font-weight: 600;
  color: var(--text-primary);
  margin-bottom: 8px;
}

.empty-desc {
  font-size: 14px;
  color: var(--text-secondary);
  margin-bottom: 24px;
}

.pagination {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 16px;
  padding: 16px 20px;
  border-top: 1px solid var(--border-color);
}

.page-btn {
  width: 36px;
  height: 36px;
  display: flex;
  align-items: center;
  justify-content: center;
  border: 1px solid var(--border-color);
  border-radius: var(--radius);
  background: #ffffff;
  cursor: pointer;
  transition: all 0.2s;
}

.page-btn:hover:not(:disabled) {
  background: #f97316;
  color: #ffffff;
  border-color: #f97316;
}

.page-btn:disabled {
  opacity: 0.5;
  cursor: not-allowed;
}

.page-info {
  font-size: 14px;
  color: var(--text-secondary);
}

.btn {
  display: inline-flex;
  align-items: center;
  gap: 8px;
  padding: 8px 16px;
  border-radius: var(--radius);
  font-size: 14px;
  font-weight: 500;
  border: none;
  cursor: pointer;
  text-decoration: none;
  transition: all 0.2s;
}

.btn-primary {
  background: #f97316;
  color: #ffffff;
}

.btn-primary:hover {
  background: #ea580c;
}
</style>
