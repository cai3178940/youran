import { request, apiPath, checkResult } from '@/utils/request'

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
    return request.put(`/${apiPath}/meta_project/deleteBatch`, data)
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
   * 获取代码目录树
   */
  getCodeTree (projectId, templateIndex) {
    return request.get(`/${apiPath}/code_preview/code_tree`,
      {
        params: {
          'projectId': projectId,
          'templateIndex': templateIndex
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
