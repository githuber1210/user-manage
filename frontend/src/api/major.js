import axios from './axios'

export const majorApi = {
  getAllMajors() {
    return axios.get('/majors')
  },

  getMajorById(id) {
    return axios.get(`/majors/${id}`)
  },

  createMajor(data) {
    return axios.post('/majors', data)
  },

  updateMajor(id, data) {
    return axios.put(`/majors/${id}`, data)
  },

  deleteMajor(id) {
    return axios.delete(`/majors/${id}`)
  }
}
