import axios from 'axios'
import qs from 'qs'
import { snakeCase, camelCase, upperCaseFirst, lowerCaseFirst } from 'change-case'
const BASE_API_URL = process.env.VUE_APP_BASE_API_URL
const ajax = axios.create({
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

export const apiPath = process.env.VUE_APP_API_PATH
export const wsApiPath = process.env.VUE_APP_WS_API_PATH

export const CommonPlugin = {
  install (Vue, options) {
    Vue.prototype.$ajax = ajax

    Vue.prototype.$common = {

      BASE_API_URL,
      // 字符串转下划线格式
      snakeCase: snakeCase,
      // 字符串转驼峰格式
      camelCase: camelCase,
      // 首字母转大写
      upperCaseFirst: upperCaseFirst,
      // 首字母转小写
      lowerCaseFirst: lowerCaseFirst,
      // 查询项目下拉列表
      getProjectOptions () {
        return ajax.get(`/${apiPath}/meta_project/list`)
      },
      // 查询实体下拉列表
      getEntityOptions (projectId) {
        return ajax.get(`/${apiPath}/meta_entity/list`, {
          params: {
            projectId
          }
        })
      },
      // 查询字段下拉列表
      getFieldOptions (entityId) {
        return ajax.get(`/${apiPath}/meta_field/list`, {
          params: {
            entityId
          }
        })
      },
      // 查询枚举下拉列表
      getConstOptions (projectId) {
        return ajax.get(`/${apiPath}/meta_const/list`, {
          params: {
            projectId,
            pageSize: 1000,
            currentPage: 1
          }
        })
      },

      confirm (msg) {
        return Vue.prototype.$confirm(msg, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })
      },

      showNotify (type, title, msg) {
        return Vue.prototype.$notify({
          title: title,
          type: type,
          message: msg
        })
      },
      // 打印常用异常
      showNotifyError (error) {
        // 表单校验异常
        if (error === false) {
          return this.showNotify('error', '出错了', '表单校验失败')
        }
        // 程序指定字符串异常信息
        if ((typeof error === 'string' && error.constructor === String)) {
          // 如果是确认窗口点击取消按钮，则不报任何错
          if (error === 'cancel') {
            return
          }
          return this.showNotify('error', '出错了', error)
        }
        if ((typeof error === 'object')) {
          if (error.constructor === Error) {
            if (error.response) {
              if (error.response.status >= 400) {
                return this.showErrorVO(error.response.data)
              }
            }
            if (error.message) {
              return this.showNotify('error', '出错了', error.message)
            }
          }
          // 远程200返回结果中的异常
          if (error.code && error.code !== '0') {
            return this.showErrorVO(error)
          }
        }
        // 未知异常
        return this.showNotify('error', '出错了', '')
      },

      showErrorVO (errorVO) {
        return this.showNotify('error', '出错了', errorVO.message)
      },

      showMsg (type, msg) {
        Vue.prototype.$message({
          message: msg,
          type: type
        })
      },

      // 校验服务器返回结果
      checkResult (response) {
        return new Promise((resolve, reject) => {
          if (response.status >= 200 && response.status < 300) {
            return resolve(response.data)
          } else {
            return reject(new Error(response.data))
          }
        })
      },
      // trim+移除空串字段
      removeBlankField (form) {
        const result = {}
        for (const field in form) {
          if (typeof form[field] === 'string') {
            const value = form[field].trim()
            if (value === '') {
              continue
            }
            result[field] = value
          } else {
            result[field] = form[field]
          }
        }
        return result
      }

    }
    // 注册全局vue指令
    Vue.directive('focus', {
      // When the bound element is inserted into the DOM...
      inserted: function (el, { value }) {
        const callback = value.callback
        callback(value.arg)
      }
    })
  }
}
