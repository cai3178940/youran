import { request, apiPath, checkResult } from '@/utils/request'

export default {

  /**
   * 查询字段详情
   */
  get (fieldId) {
    return request.get(`/${apiPath}/meta_field/${fieldId}`)
      .then(response => checkResult(response))
  },
  /**
   * 查询实体下的字段列表
   */
  getList (entityId, withCascadeFieldNum) {
    return request.get(`/${apiPath}/meta_field/list`,
      {
        params: {
          entityId,
          withCascadeFieldNum
        }
      })
      .then(response => checkResult(response))
  },
  /**
   * 保存字段
   */
  saveOrUpdate (data, isUpdate) {
    let saveURL = `/${apiPath}/meta_field/save`
    let method = 'post'
    if (isUpdate) {
      saveURL = `/${apiPath}/meta_field/update`
      method = 'put'
    }
    return request[method](saveURL, data)
      .then(response => checkResult(response))
  },
  /**
   * 修改字段序号
   */
  updateOrderNo (fieldId, orderNo) {
    return request.put(`/${apiPath}/meta_field/update_order_no`,
      {
        fieldId,
        orderNo
      })
      .then(response => checkResult(response))
  },
  /**
   * 批量删除字段
   */
  deleteBatch (data) {
    return request.put(`/${apiPath}/meta_field/deleteBatch`, data)
      .then(response => checkResult(response))
  }

}
