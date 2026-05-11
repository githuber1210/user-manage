import axios from './axios'

export const studentApi = {
  getAllStudents() {
    return axios.get('/students')
  },

  getStudentById(id) {
    return axios.get(`/students/${id}`)
  },

  getStudentsByClassId(classId) {
    return axios.get(`/students/class/${classId}`)
  },

  getStudentsByMajorId(majorId) {
    return axios.get(`/students/major/${majorId}`)
  },

  createStudent(data) {
    return axios.post('/students', data)
  },

  updateStudent(id, data) {
    return axios.put(`/students/${id}`, data)
  },

  deleteStudent(id) {
    return axios.delete(`/students/${id}`)
  }
}
