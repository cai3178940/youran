import { request, apiPath, checkResult } from '@/utils/request'

export default {

  /**
   * 查询项目下的看板列表
   */
  getList (projectId) {
    return request.get(`/${apiPath}/meta_dashboard`,
      {
        params: {
          projectId
        }
      })
      .then(response => checkResult(response))
  },
  /**
   * 删除单个看板
   */
  deleteSingle (dashboardId) {
    return request.delete(`/${apiPath}/meta_dashboard/${dashboardId}`)
      .then(response => checkResult(response))
  }

}
