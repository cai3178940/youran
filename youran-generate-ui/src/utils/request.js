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


