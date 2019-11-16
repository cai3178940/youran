import { request, apiPath, checkResult } from '@/utils/request'

export default {

  /**
   * 查询外键级联扩展详情
   */
  get (cascadeExtId) {
    return request.get(`/${apiPath}/meta_cascade_ext/${cascadeExtId}`)
      .then(response => checkResult(response))
  },
  /**
   * 查询外键级联扩展列表
   */
  getList (fieldId) {
    return request.get(`/${apiPath}/meta_cascade_ext/list`,
      {
        params: {
          fieldId
        }
      })
      .then(response => checkResult(response))
  },
  /**
   * 保存外键级联扩展
   */
  saveOrUpdate (data, isUpdate) {
    let saveURL = `/${apiPath}/meta_cascade_ext/save`
    let method = 'post'
    if (isUpdate) {
      saveURL = `/${apiPath}/meta_cascade_ext/update`
      method = 'put'
    }
    return request[method](saveURL, data)
      .then(response => checkResult(response))
  },
  /**
   * 批量删除外键级联扩展
   */
  deleteBatch (data) {
    return request.put(`/${apiPath}/meta_cascade_ext/deleteBatch`, data)
      .then(response => checkResult(response))
  }

}
