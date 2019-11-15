import { request, apiPath, checkResult } from '@/utils/request'

export default {

  /**
   * 查询实体详情
   */
  get (entityId) {
    return request.get(`/${apiPath}/meta_entity/${entityId}`)
  },
  /**
   * 查询实体列表
   */
  getList (params) {
    return request.get(`/${apiPath}/meta_entity/list`, { params: params })
      .then(response => checkResult(response))
  },
  /**
   * 新增实体
   */
  save (data) {
    return request.post(`/${apiPath}/meta_entity/save`, data)
  },
  /**
   * 修改实体
   */
  update (data) {
    return request.put(`/${apiPath}/meta_entity/update`, data)
  },
  /**
   * 批量删除实体
   */
  deleteBatch (data) {
    return request.put(`/${apiPath}/meta_entity/deleteBatch`, data)
  },

}
