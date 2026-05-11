import axios from './axios'

/**
 * 用户相关API接口封装
 */
export const userApi = {
  /**
   * 获取所有用户列表
   * @returns {Promise}
   */
  getAllUsers() {
    return axios.get('/users')
  },

  /**
   * 根据ID获取用户信息
   * @param {number} id - 用户ID
   * @returns {Promise}
   */
  getUserById(id) {
    return axios.get(`/users/${id}`)
  },

  /**
   * 创建新用户
   * @param {object} userData - 用户数据
   * @returns {Promise}
   */
  createUser(userData) {
    return axios.post('/users', userData)
  },

  /**
   * 更新用户信息
   * @param {number} id - 用户ID
   * @param {object} userData - 更新的用户数据
   * @returns {Promise}
   */
  updateUser(id, userData) {
    return axios.put(`/users/${id}`, userData)
  },

  /**
   * 删除用户
   * @param {number} id - 用户ID
   * @returns {Promise}
   */
  deleteUser(id) {
    return axios.delete(`/users/${id}`)
  }
}