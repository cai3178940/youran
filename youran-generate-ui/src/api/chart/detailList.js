import { request, apiPath, checkResult } from '@/utils/request'

export default {

  /**
   * 获取明细表详情
   */
  get (chartId) {
    return request.get(`/${apiPath}/meta_chart/detail_list/${chartId}`)
      .then(response => checkResult(response))
  },
  /**
   * 保存明细表
   */
  saveOrUpdate (data, isUpdate) {
    let saveURL = `/${apiPath}/meta_chart/detail_list`
    let method = isUpdate ? 'put' : 'post'
    return request[method](saveURL, data)
      .then(response => checkResult(response))
  }

}
