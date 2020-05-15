import { request, apiPath, checkResult } from '@/utils/request'

export default {

  /**
   * 查询图表详情
   */
  get (chartId) {
    return request.get(`/${apiPath}/meta_chart/${chartId}`)
      .then(response => checkResult(response))
  },
  /**
   * 查询项目下的图表列表
   */
  getList (projectId) {
    return request.get(`/${apiPath}/meta_chart`,
      {
        params: {
          projectId
        }
      })
      .then(response => checkResult(response))
  },
  /**
   * 保存图表
   */
  saveOrUpdate (data, isUpdate) {
    let saveURL = `/${apiPath}/meta_chart`
    let method = isUpdate ? 'put' : 'post'
    return request[method](saveURL, data)
      .then(response => checkResult(response))
  },
  /**
   * 批量删除图表
   */
  deleteBatch (data) {
    return request.put(`/${apiPath}/meta_chart`, data)
      .then(response => checkResult(response))
  }

}
