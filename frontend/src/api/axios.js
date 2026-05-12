import axios from 'axios'

// 创建axios实例
const instance = axios.create({
  baseURL: '/api',
  timeout: 10000,
  headers: {
    'Content-Type': 'application/json;charset=UTF-8'
  }
})

// 请求拦截器
instance.interceptors.request.use(
  config => {
    const token = localStorage.getItem('token')
    if (token) {
      config.headers.Authorization = `Bearer ${token}`
    }
    console.log('Request:', config)
    return config
  },
  error => {
    // 处理请求错误
    console.error('Request Error:', error)
    return Promise.reject(error)
  }
)

// 响应拦截器
instance.interceptors.response.use(
  response => {
    // 对响应数据做些什么
    console.log('Response:', response)
    return response.data
  },
  error => {
    // 处理响应错误
    console.error('Response Error:', error)
    
    if (error.response) {
      const { status, data } = error.response
      
      if (status === 401) {
        localStorage.removeItem('token')
        localStorage.removeItem('userInfo')
        alert('登录已过期，请重新登录')
        window.location.href = '/login'
        return Promise.reject(error)
      }
      
      alert(data.message || `请求失败，状态码：${status}`)
    } else if (error.request) {
      alert('请求超时或网络异常')
    } else {
      alert('请求配置错误')
    }
    
    return Promise.reject(error)
  }
)

export default instance