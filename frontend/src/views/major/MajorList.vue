<template>
  <div class="page-container">
    <div class="stats-grid">
      <div class="stat-card">
        <div class="stat-icon bg-primary">
          <svg xmlns="http://www.w3.org/2000/svg" width="22" height="22" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
            <path d="M4 19.5A2.5 2.5 0 0 1 6.5 17H20"/>
            <path d="M6.5 2H20v20H6.5A2.5 2.5 0 0 1 4 19.5v-15A2.5 2.5 0 0 1 6.5 2z"/>
          </svg>
        </div>
        <div class="stat-content">
          <div class="stat-label">专业总数</div>
          <div class="stat-value">{{ majors.length }}</div>
        </div>
      </div>
      <div class="stat-card">
        <div class="stat-icon bg-success">
          <svg xmlns="http://www.w3.org/2000/svg" width="22" height="22" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
            <rect x="3" y="3" width="18" height="18" rx="2" ry="2"/>
            <line x1="3" y1="9" x2="21" y2="9"/>
            <line x1="9" y1="21" x2="9" y2="9"/>
          </svg>
        </div>
        <div class="stat-content">
          <div class="stat-label">学院数量</div>
          <div class="stat-value">{{ collegeCount }}</div>
        </div>
      </div>
      <div class="stat-card">
        <div class="stat-icon bg-info">
          <svg xmlns="http://www.w3.org/2000/svg" width="22" height="22" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
            <circle cx="12" cy="12" r="10"/>
            <polyline points="12 6 12 12 16 14"/>
          </svg>
        </div>
        <div class="stat-content">
          <div class="stat-label">平均学制</div>
          <div class="stat-value">{{ avgDuration }}年</div>
        </div>
      </div>
    </div>

    <div class="data-card">
      <div class="card-header">
        <h2 class="card-title">
          <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
            <path d="M4 19.5A2.5 2.5 0 0 1 6.5 17H20"/>
            <path d="M6.5 2H20v20H6.5A2.5 2.5 0 0 1 4 19.5v-15A2.5 2.5 0 0 1 6.5 2z"/>
          </svg>
          专业列表
        </h2>
        <button class="btn btn-primary" @click="$router.push('/majors/add')">
          <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
            <line x1="12" y1="5" x2="12" y2="19"/>
            <line x1="5" y1="12" x2="19" y2="12"/>
          </svg>
          添加专业
        </button>
      </div>

      <div class="search-bar">
        <div class="search-input-wrapper">
          <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" class="search-icon">
            <circle cx="11" cy="11" r="8"/>
            <line x1="21" y1="21" x2="16.65" y2="16.65"/>
          </svg>
          <input type="text" v-model="searchQuery" class="search-input" placeholder="搜索专业名称或代码..." />
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
          <span class="loading-text">正在加载专业数据...</span>
        </div>

        <div v-else>
          <div v-if="paginatedMajors.length === 0" class="empty-state">
            <svg xmlns="http://www.w3.org/2000/svg" width="64" height="64" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.5" class="empty-icon">
              <path d="M4 19.5A2.5 2.5 0 0 1 6.5 17H20"/>
              <path d="M6.5 2H20v20H6.5A2.5 2.5 0 0 1 4 19.5v-15A2.5 2.5 0 0 1 6.5 2z"/>
            </svg>
            <h3 class="empty-title">{{ searchQuery ? '未找到匹配的专业' : '暂无专业数据' }}</h3>
            <p class="empty-desc">{{ searchQuery ? '尝试使用其他关键词搜索' : '添加第一个专业开始管理' }}</p>
            <router-link v-if="!searchQuery" to="/majors/add" class="btn btn-primary">
              <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                <line x1="12" y1="5" x2="12" y2="19"/>
                <line x1="5" y1="12" x2="19" y2="12"/>
              </svg>
              添加专业
            </router-link>
          </div>

          <div v-else class="table-responsive">
            <table class="data-table">
              <thead>
                <tr>
                  <th class="col-id">ID</th>
                  <th class="col-name">专业名称</th>
                  <th class="col-code">专业代码</th>
                  <th class="col-college">所属学院</th>
                  <th class="col-duration">学制</th>
                  <th class="col-status">状态</th>
                  <th class="col-actions">操作</th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="major in paginatedMajors" :key="major.id" class="table-row">
                  <td class="cell-id">
                    <span class="id-badge">{{ major.id }}</span>
                  </td>
                  <td class="cell-name">
                    <div class="name-badge">{{ major.majorName }}</div>
                  </td>
                  <td class="cell-code">
                    <code class="code-tag">{{ major.majorCode }}</code>
                  </td>
                  <td class="cell-college">{{ major.college || '-' }}</td>
                  <td class="cell-duration">
                    <span class="duration-tag">{{ major.duration }}年</span>
                  </td>
                  <td class="cell-status">
                    <span :class="['status-tag', major.enabled ? 'status-active' : 'status-disabled']">
                      {{ major.enabled ? '启用' : '禁用' }}
                    </span>
                  </td>
                  <td class="cell-actions">
                    <div class="action-group">
                      <button class="action-btn action-edit" @click="editMajor(major.id)" title="编辑">
                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                          <path d="M11 4H4a2 2 0 0 0-2 2v14a2 2 0 0 0 2 2h14a2 2 0 0 0 2-2v-7"/>
                          <path d="M18.5 2.5a2.121 2.121 0 0 1 3 3L12 15l-4 1 1-4 9.5-9.5z"/>
                        </svg>
                      </button>
                      <button class="action-btn action-delete" @click="deleteMajor(major.id, major.majorName)" title="删除">
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
import { majorApi } from '../../api/major'

const router = useRouter()
const majors = ref([])
const loading = ref(true)
const searchQuery = ref('')
const currentPage = ref(1)
const pageSize = 10

const filteredMajors = computed(() => {
  if (!searchQuery.value) return majors.value
  const query = searchQuery.value.toLowerCase()
  return majors.value.filter(major =>
    major.majorName.toLowerCase().includes(query) ||
    major.majorCode.toLowerCase().includes(query)
  )
})

const totalPages = computed(() => Math.ceil(filteredMajors.value.length / pageSize))

const paginatedMajors = computed(() => {
  const start = (currentPage.value - 1) * pageSize
  return filteredMajors.value.slice(start, start + pageSize)
})

const collegeCount = computed(() => {
  const colleges = new Set(majors.value.map(m => m.college).filter(c => c))
  return colleges.size
})

const avgDuration = computed(() => {
  if (majors.value.length === 0) return 0
  const sum = majors.value.reduce((acc, m) => acc + (m.duration || 0), 0)
  return (sum / majors.value.length).toFixed(1)
})

const fetchMajors = async () => {
  loading.value = true
  try {
    const response = await majorApi.getAllMajors()
    if (response.code === 200) {
      majors.value = response.data || []
    }
  } catch (error) {
    console.error('获取专业列表失败:', error)
  } finally {
    loading.value = false
  }
}

const editMajor = (id) => {
  router.push(`/majors/edit/${id}`)
}

const deleteMajor = async (id, majorName) => {
  if (!confirm(`确定要删除专业 "${majorName}" 吗？此操作无法撤销。`)) {
    return
  }
  try {
    const response = await majorApi.deleteMajor(id)
    if (response.code === 200) {
      alert('删除成功')
      fetchMajors()
    }
  } catch (error) {
    console.error('删除专业失败:', error)
  }
}

onMounted(() => {
  fetchMajors()
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

.bg-primary { background: var(--primary); }
.bg-success { background: var(--success); }
.bg-info { background: #8b5cf6; }

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
  color: #8b5cf6;
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
  border-color: #8b5cf6;
  box-shadow: 0 0 0 3px rgba(139, 92, 246, 0.15);
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
.col-code { width: 120px; }
.col-college { min-width: 150px; }
.col-duration { width: 80px; }
.col-status { width: 80px; }
.col-actions { width: 100px; text-align: right; }

.cell-id { text-align: center; }
.id-badge {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  width: 32px;
  height: 32px;
  background: rgba(139, 92, 246, 0.1);
  color: #8b5cf6;
  border-radius: 8px;
  font-size: 13px;
  font-weight: 600;
}

.name-badge {
  font-weight: 600;
  color: var(--text-primary);
}

.code-tag {
  background: rgba(139, 92, 246, 0.1);
  color: #8b5cf6;
  padding: 4px 8px;
  border-radius: 4px;
  font-size: 12px;
  font-family: monospace;
}

.cell-college {
  font-size: 13px;
  color: var(--text-secondary);
}

.cell-duration { text-align: center; }
.duration-tag {
  display: inline-block;
  padding: 4px 10px;
  background: rgba(139, 92, 246, 0.1);
  color: #8b5cf6;
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
  background: #8b5cf6;
  color: #ffffff;
  border-color: #8b5cf6;
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
  background: #8b5cf6;
  color: #ffffff;
}

.btn-primary:hover {
  background: #7c3aed;
}
</style>
