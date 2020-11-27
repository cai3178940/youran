import { request, apiPath, checkResult } from '@/utils/request'

export default {

  /**
   * 查询项目组成员列表
   */
  getList (fieldId) {
    return request.get(`/${apiPath}/project_team_member`,
      {
        params: {
          fieldId
        }
      })
      .then(response => checkResult(response))
  },
  /**
   * 添加项目组成员
   */
  save (data) {
    return request.post(`/${apiPath}/project_team_member`, data)
      .then(response => checkResult(response))
  },
  /**
   * 批量删除项目组成员
   */
  deleteBatch (data) {
    return request.delete(`/${apiPath}/project_team_member`, { data })
      .then(response => checkResult(response))
  }
}
