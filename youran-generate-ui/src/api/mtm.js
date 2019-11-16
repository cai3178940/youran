import { request, apiPath, checkResult } from '@/utils/request'

export default {

  /**
   * 查询多对多详情
   */
  get (mtmId) {
    return request.get(`/${apiPath}/meta_mtm/${mtmId}`)
      .then(response => checkResult(response))
  },
  /**
   * 查询多对多列表
   */
  getList (projectId) {
    return request.get(`/${apiPath}/meta_mtm/list`,
      {
        params: {
          projectId: projectId
        }
      })
      .then(response => checkResult(response))
  },
  /**
   * 保存多对多
   */
  saveOrUpdate (data, isUpdate) {
    let saveURL = `/${apiPath}/meta_mtm/save`
    let method = 'post'
    if (isUpdate) {
      saveURL = `/${apiPath}/meta_mtm/update`
      method = 'put'
    }
    return request[method](saveURL, data)
      .then(response => checkResult(response))
  },
  /**
   * 删除单个多对多
   */
  deleteSingle (mtmId) {
    return request.delete(`/${apiPath}/meta_mtm/${mtmId}`)
      .then(response => checkResult(response))
  }

}
