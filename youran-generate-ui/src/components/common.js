import axios from 'axios'
import qs from 'qs'
import changeCase from 'change-case'
const BASE_API_URL = process.env.VUE_APP_BASE_API_URL
const ajax = axios.create({
  baseURL: BASE_API_URL,
  timeout: 60000,
  responseType: 'json',
  headers: {
    'Content-Type': 'application/json; charset=utf-8'
  },
  paramsSerializer: function (params) {
    return qs.stringify(params, {
      arrayFormat: 'repeat'
    })
  }
})

export const apiPath = process.env.VUE_APP_API_PATH

export const CommonPlugin = {
  install: function (Vue, options) {
    Vue.prototype.$ajax = ajax

    Vue.prototype.$common = {

      BASE_API_URL,
      // 字符串转下划线格式
      snakeCase: function (value) {
        return changeCase.snakeCase(value)
      },
      // 字符串转驼峰格式
      camelCase: function (value) {
        return changeCase.camelCase(value)
      },
      // 查询项目下拉列表
      getProjectOptions: function () {
        return ajax.get(`/${apiPath}/meta_project/list`)
      },
      // 查询实体下拉列表
      getEntityOptions: function (projectId) {
        return ajax.get(`/${apiPath}/meta_entity/list`, {
          params: {
            projectId,
            pageSize: 1000,
            pageNo: 1
          }
        })
      },
      // 查询字段下拉列表
      getFieldOptions: function (entityId) {
        return ajax.get(`/${apiPath}/meta_field/list`, {
          params: {
            entityId
          }
        })
      },
      // 查询枚举下拉列表
      getConstOptions: function (projectId) {
        return ajax.get(`/${apiPath}/meta_const/list`, {
          params: {
            projectId,
            pageSize: 1000,
            pageNo: 1
          }
        })
      },

      confirm: function (msg) {
        return Vue.prototype.$confirm(msg, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })
      },

      showNotify: function (type, title, msg) {
        return Vue.prototype.$notify({
          title: title,
          type: type,
          message: msg
        })
      },
      // 打印常用异常
      showNotifyError: function (error) {
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
              if (error.response.status === 500) {
                return this.showNotify('error', '出错了', '系统内部错误')
              }
            }
            if (error.message) {
              return this.showNotify('error', '出错了', error.message)
            }
          }
          // 远程200返回结果中的异常
          if (error.code && error.code !== '0') {
            return this.showNotify('error', '出错了', error.message)
          }
        }
        // 未知异常
        return this.showNotify('error', '出错了', '')
      },

      showMsg: function (type, msg) {
        Vue.prototype.$message({
          message: msg,
          type: type
        })
      },

      // 校验服务器返回结果
      checkResult: function (result) {
        return new Promise(function (resolve, reject) {
          if (result) {
            if (result.code === '0') {
              return resolve(result)
            } else {
              return reject(new Error(result.message))
            }
          } else {
            return reject(new Error('返回结果有误'))
          }
        })
      },
      // trim+移除空串字段
      removeBlankField: function (form) {
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
  }
}
