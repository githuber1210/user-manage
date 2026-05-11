import axios from './axios'

export const classApi = {
  getAllClasses() {
    return axios.get('/classes')
  },

  getClassById(id) {
    return axios.get(`/classes/${id}`)
  },

  getClassesByMajorId(majorId) {
    return axios.get(`/classes/major/${majorId}`)
  },

  createClass(data) {
    return axios.post('/classes', data)
  },

  updateClass(id, data) {
    return axios.put(`/classes/${id}`, data)
  },

  deleteClass(id) {
    return axios.delete(`/classes/${id}`)
  }
}
