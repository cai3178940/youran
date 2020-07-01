import { request, apiPath, checkResult } from '@/utils/request'

export default {

  /**
   * 查询枚举详情
   */
  get (constId) {
    return request.get(`/${apiPath}/meta_const/${constId}`)
      .then(response => checkResult(response))
  },
  /**
   * 查询枚举列表
   */
  getList (projectId) {
    return request.get(`/${apiPath}/meta_const/list`,
      {
        params: {
          projectId
        }
      })
      .then(response => checkResult(response))
  },
  /**
   * 新增枚举
   */
  saveOrUpdate (data, isUpdate) {
    let saveURL = `/${apiPath}/meta_const/save`
    let method = 'post'
    if (isUpdate) {
      saveURL = `/${apiPath}/meta_const/update`
      method = 'put'
    }
    return request[method](saveURL, data)
      .then(response => checkResult(response))
  },
  /**
   * 批量删除枚举
   */
  deleteBatch (data) {
    return request.put(`/${apiPath}/meta_const/delete_batch`, data)
      .then(response => checkResult(response))
  },
  /**
   * 查询枚举值详情
   */
  getDetail (constId) {
    return request.get(`/${apiPath}/meta_const_detail/${constId}`)
      .then(response => checkResult(response))
  },
  /**
   * 查询枚举值列表
   */
  getDetailList (params) {
    return request.get(`/${apiPath}/meta_const_detail/list`, { params: params })
      .then(response => checkResult(response))
  },
  /**
   * 查询枚举值列表
   */
  getDetailLists (params) {
    return request.get(`/${apiPath}/meta_const_detail/lists`, { params: params })
      .then(response => checkResult(response))
  },
  /**
   * 新增或修改枚举值
   */
  saveOrUpdateDetail (data, isUpdate) {
    let saveURL = `/${apiPath}/meta_const_detail/save`
    let method = 'post'
    if (isUpdate) {
      saveURL = `/${apiPath}/meta_const_detail/update`
      method = 'put'
    }
    return request[method](saveURL, data)
      .then(response => checkResult(response))
  },
  /**
   * 批量删除枚举值
   */
  deleteDetailBatch (data) {
    return request.put(`/${apiPath}/meta_const_detail/delete_batch`, data)
      .then(response => checkResult(response))
  }

}
