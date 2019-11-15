import { request, apiPath, checkResult } from '@/utils/request'

export default {

  /**
   * 查询项目详情
   */
  get (projectId) {
    return request.get(`/${apiPath}/meta_project/${projectId}`)
  },
  /**
   * 查询项目列表
   */
  getList () {
    return request.get(`/${apiPath}/meta_project/list`)
      .then(response => checkResult(response))
  },
  /**
   * 新增项目
   */
  save (data) {
    return request.post(`/${apiPath}/meta_project/save`, data)
  },
  /**
   * 修改项目
   */
  update (data) {
    return request.put(`/${apiPath}/meta_project/update`, data)
  },
  /**
   * 批量删除项目
   */
  deleteBatch (data) {
    return request.put(`/${apiPath}/meta_project/deleteBatch`, data)
  },
  /**
   * 反向工程校验
   */
  reverseEngineeringCheck (data) {
    return request.post(`/${apiPath}/reverse_engineering/check`, data)
  },
  /**
   * 反向工程执行
   */
  reverseEngineeringExecute (data) {
    return request.post(`/${apiPath}/reverse_engineering/execute`, data)
  },
  /**
   * 获取代码目录树
   */
  getCodeTree (projectId, templateIndex) {
    return request.get(`/${apiPath}/code_preview/code_tree`, {
      params: {
        'projectId': projectId,
        'templateIndex': templateIndex
      }
    })
  },
  /**
   * 获取代码文件内容
   */
  getCodeFileContent (params) {
    return request.get(`/${apiPath}/code_preview/file_content`, {
      responseType: 'text',
      params: params
    })
  }

}
