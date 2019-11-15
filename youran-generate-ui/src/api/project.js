import { request, apiPath } from '@/utils/request'

export default {

  /**
   * 查询项目列表
   */
  getList (params) {
    return request.get(`/${apiPath}/meta_project/list`, { params: params })
  }

}
