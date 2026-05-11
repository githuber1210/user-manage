import axios from './axios'

export const teacherApi = {
  getAllTeachers() {
    return axios.get('/teachers')
  },

  getTeacherById(id) {
    return axios.get(`/teachers/${id}`)
  },

  getTeachersByMajorId(majorId) {
    return axios.get(`/teachers/major/${majorId}`)
  },

  createTeacher(data) {
    return axios.post('/teachers', data)
  },

  updateTeacher(id, data) {
    return axios.put(`/teachers/${id}`, data)
  },

  deleteTeacher(id) {
    return axios.delete(`/teachers/${id}`)
  }
}
