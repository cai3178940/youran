import _snakeCase from 'lodash/snakeCase'
import _camelCase from 'lodash/camelCase'
import _upperFirst from 'lodash/upperFirst'
import _lowerFirst from 'lodash/lowerFirst'

export default {
  install (Vue, options) {
    Vue.prototype.$common = {
      // 字符串转下划线格式
      snakeCase: _snakeCase,
      // 字符串转驼峰格式
      camelCase: _camelCase,
      // 首字母转大写
      upperCaseFirst: _upperFirst,
      // 首字母转小写
      lowerCaseFirst: _lowerFirst,

      confirm (msg) {
        return Vue.prototype.$confirm(msg, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })
      },

      showNotify (type, title, msg) {
        let duration = 4500
        if (type === 'error') {
          duration = 10000
        }
        return Vue.prototype.$notify({
          title: title,
          type: type,
          message: msg,
          duration
        })
      },
      // 打印常用异常
      showNotifyError (error) {
        console.error(error)
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
                if (error.response.data) {
                  return this.showErrorVO(error.response.data)
                } else {
                  return this.showErrorVO(error)
                }
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

    // 注册只允许输入大写的指令
    Vue.directive('upper-case', {
      update: function (el, binding, vnode) {
        const value = vnode.data.model.value
        const lc = /[a-z]/i
        if (lc.test(value)) {
          vnode.data.model.callback(value.toUpperCase())
        }
      }
    })

    // 注册只允许输入小写的指令
    Vue.directive('lower-case', {
      update: function (el, binding, vnode) {
        const value = vnode.data.model.value
        const lc = /[A-Z]/i
        if (lc.test(value)) {
          vnode.data.model.callback(value.toLowerCase())
        }
      }
    })

    // 注册首字母大写指令
    Vue.directive('upper-case-first', {
      update: function (el, binding, vnode) {
        const value = vnode.data.model.value
        vnode.data.model.callback(_upperFirst(value))
      }
    })

    // 注册首字母小写指令
    Vue.directive('lower-case-first', {
      update: function (el, binding, vnode) {
        const value = vnode.data.model.value
        vnode.data.model.callback(_lowerFirst(value))
      }
    })
  }
}
