import axios from 'axios'
import qs from 'qs'

const BASE_API_URL = process.env.VUE_APP_BASE_API_URL
export const apiPath = process.env.VUE_APP_API_PATH
export const wsApiPath = process.env.VUE_APP_WS_API_PATH
export const request = axios.create({
  baseURL: BASE_API_URL,
  timeout: 60000,
  responseType: 'json',
  headers: {
    'Content-Type': 'application/json; charset=utf-8'
  },
  paramsSerializer (params) {
    return qs.stringify(params, {
      arrayFormat: 'repeat'
    })
  }
})
// 校验服务器返回结果
export const checkResult = function (response) {
  return new Promise((resolve, reject) => {
    if (response.status >= 200 && response.status < 300) {
      return resolve(response.data)
    } else {
      return reject(new Error(response.data))
    }
  })
}
