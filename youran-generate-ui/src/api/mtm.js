import { request, apiPath, checkResult } from '@/utils/request'

export default {

  /**
   * 查询多对多列表
   */
  getList (projectId) {
    return request.get(`/${apiPath}/meta_mtm/list`, {
      params: {
        projectId: projectId
      }
    })
  },
  /**
   * 删除单个多对多
   */
  deleteSingle (mtmId) {
    return request.delete(`/${apiPath}/meta_mtm/${mtmId}`)
  },

}
