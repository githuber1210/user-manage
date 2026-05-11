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
          <div class="stat-label">班级总数</div>
          <div class="stat-value">{{ classes.length }}</div>
        </div>
      </div>
      <div class="stat-card">
        <div class="stat-icon bg-success">
          <svg xmlns="http://www.w3.org/2000/svg" width="22" height="22" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
            <path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"/>
            <circle cx="9" cy="7" r="4"/>
          </svg>
        </div>
        <div class="stat-content">
          <div class="stat-label">学生总数</div>
          <div class="stat-value">{{ totalStudents }}</div>
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
          <div class="stat-label">年级数量</div>
          <div class="stat-value">{{ gradeCount }}</div>
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
          班级列表
        </h2>
        <button class="btn btn-primary" @click="$router.push('/classes/add')">
          <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
            <line x1="12" y1="5" x2="12" y2="19"/>
            <line x1="5" y1="12" x2="19" y2="12"/>
          </svg>
          添加班级
        </button>
      </div>

      <div class="search-bar">
        <div class="search-input-wrapper">
          <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" class="search-icon">
            <circle cx="11" cy="11" r="8"/>
            <line x1="21" y1="21" x2="16.65" y2="16.65"/>
          </svg>
          <input type="text" v-model="searchQuery" class="search-input" placeholder="搜索班级名称..." />
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
          <span class="loading-text">正在加载班级数据...</span>
        </div>

        <div v-else>
          <div v-if="paginatedClasses.length === 0" class="empty-state">
            <svg xmlns="http://www.w3.org/2000/svg" width="64" height="64" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.5" class="empty-icon">
              <path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"/>
              <circle cx="9" cy="7" r="4"/>
            </svg>
            <h3 class="empty-title">{{ searchQuery ? '未找到匹配的班级' : '暂无班级数据' }}</h3>
            <p class="empty-desc">{{ searchQuery ? '尝试使用其他关键词搜索' : '添加第一个班级开始管理' }}</p>
            <router-link v-if="!searchQuery" to="/classes/add" class="btn btn-primary">
              <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                <line x1="12" y1="5" x2="12" y2="19"/>
                <line x1="5" y1="12" x2="19" y2="12"/>
              </svg>
              添加班级
            </router-link>
          </div>

          <div v-else class="table-responsive">
            <table class="data-table">
              <thead>
                <tr>
                  <th class="col-id">ID</th>
                  <th class="col-name">班级名称</th>
                  <th class="col-major">所属专业</th>
                  <th class="col-grade">年级</th>
                  <th class="col-teacher">班主任</th>
                  <th class="col-count">人数</th>
                  <th class="col-status">状态</th>
                  <th class="col-actions">操作</th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="cls in paginatedClasses" :key="cls.id" class="table-row">
                  <td class="cell-id">
                    <span class="id-badge">{{ cls.id }}</span>
                  </td>
                  <td class="cell-name">
                    <div class="name-badge">{{ cls.className }}</div>
                  </td>
                  <td class="cell-major">{{ cls.majorName || '-' }}</td>
                  <td class="cell-grade">
                    <span class="grade-tag">{{ cls.grade }}</span>
                  </td>
                  <td class="cell-teacher">{{ cls.teacherName || '-' }}</td>
                  <td class="cell-count">
                    <span class="count-tag">{{ cls.studentCount }}人</span>
                  </td>
                  <td class="cell-status">
                    <span :class="['status-tag', cls.enabled ? 'status-active' : 'status-disabled']">
                      {{ cls.enabled ? '启用' : '禁用' }}
                    </span>
                  </td>
                  <td class="cell-actions">
                    <div class="action-group">
                      <button class="action-btn action-edit" @click="editClass(cls.id)" title="编辑">
                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                          <path d="M11 4H4a2 2 0 0 0-2 2v14a2 2 0 0 0 2 2h14a2 2 0 0 0 2-2v-7"/>
                          <path d="M18.5 2.5a2.121 2.121 0 0 1 3 3L12 15l-4 1 1-4 9.5-9.5z"/>
                        </svg>
                      </button>
                      <button class="action-btn action-delete" @click="deleteClass(cls.id, cls.className)" title="删除">
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
import { classApi } from '../api/class'

const router = useRouter()
const classes = ref([])
const loading = ref(true)
const searchQuery = ref('')
const currentPage = ref(1)
const pageSize = 10

const filteredClasses = computed(() => {
  if (!searchQuery.value) return classes.value
  const query = searchQuery.value.toLowerCase()
  return classes.value.filter(cls => cls.className.toLowerCase().includes(query))
})

const totalPages = computed(() => Math.ceil(filteredClasses.value.length / pageSize))

const paginatedClasses = computed(() => {
  const start = (currentPage.value - 1) * pageSize
  return filteredClasses.value.slice(start, start + pageSize)
})

const totalStudents = computed(() => classes.value.reduce((acc, c) => acc + (c.studentCount || 0), 0))

const gradeCount = computed(() => {
  const grades = new Set(classes.value.map(c => c.grade).filter(g => g))
  return grades.size
})

const fetchClasses = async () => {
  loading.value = true
  try {
    const response = await classApi.getAllClasses()
    if (response.code === 200) {
      classes.value = response.data || []
    }
  } catch (error) {
    console.error('获取班级列表失败:', error)
  } finally {
    loading.value = false
  }
}

const editClass = (id) => {
  router.push(`/classes/edit/${id}`)
}

const deleteClass = async (id, className) => {
  if (!confirm(`确定要删除班级 "${className}" 吗？此操作无法撤销。`)) {
    return
  }
  try {
    const response = await classApi.deleteClass(id)
    if (response.code === 200) {
      alert('删除成功')
      fetchClasses()
    }
  } catch (error) {
    console.error('删除班级失败:', error)
  }
}

onMounted(() => {
  fetchClasses()
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

.bg-primary { background: #ec4899; }
.bg-success { background: var(--success); }
.bg-info { background: #06b6d4; }

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
  color: #ec4899;
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
  border-color: #ec4899;
  box-shadow: 0 0 0 3px rgba(236, 72, 153, 0.15);
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
.col-name { min-width: 150px; }
.col-major { min-width: 120px; }
.col-grade { width: 80px; }
.col-teacher { width: 100px; }
.col-count { width: 80px; }
.col-status { width: 80px; }
.col-actions { width: 100px; text-align: right; }

.cell-id { text-align: center; }
.id-badge {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  width: 32px;
  height: 32px;
  background: rgba(236, 72, 153, 0.1);
  color: #ec4899;
  border-radius: 8px;
  font-size: 13px;
  font-weight: 600;
}

.name-badge {
  font-weight: 600;
  color: var(--text-primary);
}

.cell-major {
  font-size: 13px;
  color: var(--text-secondary);
}

.cell-grade { text-align: center; }
.grade-tag {
  display: inline-block;
  padding: 4px 10px;
  background: rgba(6, 182, 212, 0.1);
  color: #06b6d4;
  border-radius: 20px;
  font-size: 13px;
  font-weight: 500;
}

.cell-teacher {
  font-size: 13px;
  color: var(--text-secondary);
}

.cell-count { text-align: center; }
.count-tag {
  display: inline-block;
  padding: 4px 10px;
  background: rgba(236, 72, 153, 0.1);
  color: #ec4899;
  border-radius: 20px;
  font-size: 13px;
  font-weight: 500;
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
  background: #ec4899;
  color: #ffffff;
  border-color: #ec4899;
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
  background: #ec4899;
  color: #ffffff;
}

.btn-primary:hover {
  background: #db2777;
}
</style>
