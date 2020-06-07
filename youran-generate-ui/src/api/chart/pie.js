import { request, apiPath, checkResult } from '@/utils/request'

export default {

  /**
   * 获取饼图详情
   */
  get (chartId) {
    return request.get(`/${apiPath}/meta_chart/pie/${chartId}`)
      .then(response => checkResult(response))
  },
  /**
   * 保存饼图
   */
  saveOrUpdate (data, isUpdate) {
    let saveURL = `/${apiPath}/meta_chart/pie`
    let method = isUpdate ? 'put' : 'post'
    return request[method](saveURL, data)
      .then(response => checkResult(response))
  }

}
