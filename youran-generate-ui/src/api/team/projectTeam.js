import { request, apiPath, checkResult } from '@/utils/request'

export default {

  /**
   * 查询单个项目组
   */
  get (teamId) {
    return request.get(`/${apiPath}/project_team/${teamId}`)
      .then(response => checkResult(response))
  },
  /**
   * 查询项目组列表
   */
  getList (fieldId) {
    return request.get(`/${apiPath}/project_team`,
      {
        params: {
          fieldId
        }
      })
      .then(response => checkResult(response))
  },
  /**
   * 保存项目组
   */
  saveOrUpdate (data, isUpdate) {
    return request[isUpdate ? 'put' : 'post'](`/${apiPath}/project_team`, data)
      .then(response => checkResult(response))
  },
  /**
   * 批量删除项目组
   */
  deleteBatch (data) {
    return request.delete(`/${apiPath}/project_team`, { data })
      .then(response => checkResult(response))
  },
  /**
   * 查询【项目组】选项列表
   */
  findOptions(query) {
    return request.get(`${apiPath}/project_team/options`, { params: query })
  }
}
