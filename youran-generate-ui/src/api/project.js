/**
 * websocket前端组件
 * https://www.npmjs.com/package/webstomp-client
 */
import webstomp from 'webstomp-client'
import SockJS from 'sockjs-client'
import shortid from 'shortid'
import { request, apiPath, wsApiPath, baseApiUrl, checkResult } from '@/utils/request'
import store from '@/store'

export default {

  /**
   * 查询项目详情
   */
  get (projectId) {
    return request.get(`/${apiPath}/meta_project/${projectId}`)
      .then(response => checkResult(response))
  },
  /**
   * 查询项目列表
   */
  getList () {
    return request.get(`/${apiPath}/meta_project/list`)
      .then(response => checkResult(response))
  },
  /**
   * 保存项目
   */
  saveOrUpdate (data, isUpdate) {
    let saveURL = `/${apiPath}/meta_project/save`
    let method = 'post'
    if (isUpdate) {
      saveURL = `/${apiPath}/meta_project/update`
      method = 'put'
    }
    return request[method](saveURL, data)
      .then(response => checkResult(response))
  },
  /**
   * 批量删除项目
   */
  deleteBatch (data) {
    return request.put(`/${apiPath}/meta_project/delete_batch`, data)
      .then(response => checkResult(response))
  },
  /**
   * 反向工程校验
   */
  reverseEngineeringCheck (data) {
    return request.post(`/${apiPath}/reverse_engineering/check`, data)
      .then(response => checkResult(response))
  },
  /**
   * 反向工程执行
   */
  reverseEngineeringExecute (data) {
    return request.post(`/${apiPath}/reverse_engineering/execute`, data)
      .then(response => checkResult(response))
  },
  /**
   * 调用【代码生成】相关websocket服务
   * @param serviceName 服务名
   * @param params 请求参数
   * @param afterConnect 连接建立之后的回调
   * @param doProgress 处理进度消息回调
   */
  callCodeGenWebSocketService (serviceName, params, afterConnect, doProgress) {
    return new Promise((resolve, reject) => {
      const sock = new SockJS(`${baseApiUrl}/${wsApiPath}`)
      const stompClient = webstomp.over(sock)
      stompClient.connect({}, () => {
        // 生成随机sessionId
        const sessionId = shortid.generate()
        // 订阅进度变化的topic
        stompClient.subscribe(`/code_gen/${serviceName}_progress/${sessionId}`, (frame) => {
          const progressVO = JSON.parse(frame.body)
          const done = doProgress(progressVO)
          if (done) {
            // 如果进度结束，则断开websocket连接
            stompClient.disconnect()
            resolve(progressVO)
          }
        })
        afterConnect()
        // 请求生成代码
        stompClient.send(`/code_gen/${serviceName}/${sessionId}`, '', params)
      })
    })
  },
  /**
   * 导出代码文件压缩包
   */
  exportCodeZip (projectId) {
    const downloadUrl = `/${apiPath}/meta_export/${projectId}`
    store.dispatch('app/downloadFile', downloadUrl)
  },
  /**
   * 导出代码文件压缩包
   */
  exportCodeZipBySessionId (sessionId) {
    const downloadUrl = `/${apiPath}/code_gen/download_code/${sessionId}`
    store.dispatch('app/downloadFile', downloadUrl)
  },
  /**
   * 校验git提交
   */
  checkCommit (projectId, templateId) {
    return request.get(`/${apiPath}/code_gen/check_commit`,
      {
        params: {
          'projectId': projectId,
          'templateId': templateId
        }
      })
      .then(response => checkResult(response))
  },
  /**
   * 获取git代码差异
   */
  getGitDiff (projectId, templateId) {
    return request.get(`/${apiPath}/code_gen/git_diff`,
      {
        responseType: 'text',
        params: {
          'projectId': projectId,
          'templateId': templateId
        }
      })
      .then(response => checkResult(response))
  },
  /**
   * 获取代码目录树
   */
  getCodeTree (projectId, templateId) {
    return request.get(`/${apiPath}/code_preview/code_tree`,
      {
        params: {
          'projectId': projectId,
          'templateId': templateId
        }
      })
      .then(response => checkResult(response))
  },
  /**
   * 获取代码文件内容
   */
  getCodeFileContent (params) {
    return request.get(`/${apiPath}/code_preview/file_content`,
      {
        responseType: 'text',
        params: params
      })
      .then(response => checkResult(response))
  }

}
