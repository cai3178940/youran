import { request, apiPath, checkResult } from '@/utils/request'

export default {

  /**
   * 获取图表数据源
   */
  get (sourceId) {
    return request.get(`/${apiPath}/meta_chart_source/${sourceId}`)
      .then(response => checkResult(response))
  },
  /**
   * 获取图表数据源及其数据项
   */
  getSourceWithItems (sourceId) {
    return request.get(`/${apiPath}/meta_chart_source/${sourceId}/with_items`)
      .then(response => checkResult(response))
  },
  /**
   * 保存图表
   */
  saveOrUpdate (data, isUpdate) {
    let saveURL = `/${apiPath}/meta_chart_source`
    let method = isUpdate ? 'put' : 'post'
    return request[method](saveURL, data)
      .then(response => checkResult(response))
  },
  /**
   * 保存图表及其所有数据项
   */
  saveOrUpdateSourceWithItems (data, isUpdate) {
    let saveURL = `/${apiPath}/meta_chart_source/with_items`
    let method = isUpdate ? 'put' : 'post'
    return request[method](saveURL, data)
      .then(response => checkResult(response))
  },
  /**
   * 批量删除图表
   */
  deleteBatch (data) {
    return request.put(`/${apiPath}/meta_chart_source`, data)
      .then(response => checkResult(response))
  }

}
