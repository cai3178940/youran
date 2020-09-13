import { apiPath, checkResult, request } from '@/utils/request'

export default {

  /**
   * 获取标签元数据
   */
  getMetaLabel (projectId, templateId, labelType) {
    return request.get(`/${apiPath}/meta_label`,
      {
        params: {
          'projectId': projectId,
          'templateId': templateId,
          'labelType': labelType
        }
      })
      .then(response => checkResult(response))
  }

}
