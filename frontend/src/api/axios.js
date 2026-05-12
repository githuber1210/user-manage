import axios from 'axios'

const instance = axios.create({
  baseURL: '/api',
  timeout: 10000,
  headers: {
    'Content-Type': 'application/json;charset=UTF-8'
  }
})

const showToast = (type, message) => {
  if (window.$toast) {
    window.$toast[type](message)
  } else {
    alert(message)
  }
}

instance.interceptors.response.use(
  response => {
    const data = response.data
    if (data && data.code !== 200 && data.message) {
      showToast('warning', data.message)
    }
    return data
  },
  error => {
    console.error('Response Error:', error)
    
    if (error.response) {
      const { status, data } = error.response
      const message = data?.message || `请求失败，状态码：${status}`
      showToast('error', message)
    } else if (error.request) {
      showToast('error', '请求超时或网络异常')
    } else {
      showToast('error', '请求配置错误')
    }
    
    return Promise.reject(error)
  }
)

export default instance
