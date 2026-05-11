import axios from './axios'

export const roleApi = {
  getAllRoles() {
    return axios.get('/roles')
  },

  getRoleById(id) {
    return axios.get(`/roles/${id}`)
  },

  getRoleByCode(roleCode) {
    return axios.get(`/roles/code/${roleCode}`)
  },

  createRole(data) {
    return axios.post('/roles', data)
  },

  updateRole(id, data) {
    return axios.put(`/roles/${id}`, data)
  },

  deleteRole(id) {
    return axios.delete(`/roles/${id}`)
  }
}
