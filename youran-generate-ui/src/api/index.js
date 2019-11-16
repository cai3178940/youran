import { request, apiPath, checkResult } from '@/utils/request'

export default {

  /**
   * 查询索引详情
   */
  get (indexId) {
    return request.get(`/${apiPath}/meta_index/${indexId}`)
      .then(response => checkResult(response))
  },
  /**
   * 查询索引列表
   */
  getList (params) {
    return request.get(`/${apiPath}/meta_index/list`, { params: params })
      .then(response => checkResult(response))
  },
  /**
   * 保存索引
   */
  saveOrUpdate (data, isUpdate) {
    let saveURL = `/${apiPath}/meta_index/save`
    let method = 'post'
    if (isUpdate) {
      saveURL = `/${apiPath}/meta_index/update`
      method = 'put'
    }
    return request[method](saveURL, data)
      .then(response => checkResult(response))
  },
  /**
   * 删除单个索引
   */
  deleteSingle (indexId) {
    return request.delete(`/${apiPath}/meta_index/${indexId}`)
      .then(response => checkResult(response))
  },
  /**
   * 移除索引字段
   */
  removeField (indexId, fieldIds) {
    return request.put(`/${apiPath}/meta_index/${indexId}/removeField`, fieldIds)
      .then(response => checkResult(response))
  }

}
