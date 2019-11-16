import { request, apiPath, checkResult } from '@/utils/request'
import store from '@/store'

export default {

  /**
   * 查询模板详情
   */
  get (templateId) {
    return request.get(`/${apiPath}/code_template/${templateId}`)
      .then(response => checkResult(response))
  },
  /**
   * 查询模板列表
   */
  getList () {
    return request.get(`/${apiPath}/code_template`)
      .then(response => checkResult(response))
  },
  /**
   * 保存模板
   */
  saveOrUpdate (data, isUpdate) {
    return request[isUpdate ? 'put' : 'post'](`/${apiPath}/code_template`, data)
      .then(response => checkResult(response))
  },
  /**
   * 批量删除模板
   */
  deleteBatch (data) {
    return request.delete(`/${apiPath}/code_template`, { data })
      .then(response => checkResult(response))
  },
  /**
   * 导出模板文件压缩包
   */
  exportZip (templateId) {
    const downloadUrl = `/${apiPath}/code_template/${templateId}/export`
    store.dispatch('downloadFile', downloadUrl)
  },
  /**
   * 复制模板
   */
  copy (templateId) {
    return request.post(`/${apiPath}/code_template/${templateId}/copy`)
      .then(response => checkResult(response))
  },
  /**
   * 查询模板目录树
   */
  getDirTree (templateId) {
    return request.get(`/${apiPath}/code_template/${templateId}/dir_tree`)
      .then(response => checkResult(response))
  },
  /**
   * 获取模板文件
   */
  getTemplateFile (fileId) {
    return request.get(`/${apiPath}/template_file/${fileId}`)
      .then(response => checkResult(response))
  },
  /**
   * 保存模板文件
   */
  saveOrUpdateTemplateFile (data, isUpdate) {
    return request[isUpdate ? 'put' : 'post'](`/${apiPath}/template_file`, data)
      .then(response => checkResult(response))
  },
  /**
   * 修改模板文件内容
   */
  updateTemplateFileContent (fileId, version, content) {
    return request.put(`/${apiPath}/template_file/${fileId}/content`,
      {
        version,
        content
      })
      .then(response => checkResult(response))
  },
  /**
   * 删除单个模板文件
   */
  deleteTemplateFile (fileId) {
    return request.delete(`/${apiPath}/template_file/${fileId}`)
      .then(response => checkResult(response))
  }

}
