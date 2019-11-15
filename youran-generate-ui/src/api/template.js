import { request, apiPath, checkResult } from '@/utils/request'

export default {

  /**
   * 查询模板列表
   */
  getList () {
    return request.get(`/${apiPath}/code_template`)
  }

}
