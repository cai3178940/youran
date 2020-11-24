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
   * 保存项目组成员
   */
  saveOrUpdate (data, isUpdate) {
    return request[isUpdate ? 'put' : 'post'](`/${apiPath}/project_team_member`, data)
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
