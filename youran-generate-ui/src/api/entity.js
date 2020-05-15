import { request, apiPath, checkResult } from '@/utils/request'

export default {

  /**
   * 查询实体详情
   */
  get (entityId) {
    return request.get(`/${apiPath}/meta_entity/${entityId}`)
      .then(response => checkResult(response))
  },
  /**
   * 查询项目下的实体列表
   */
  getList (projectId) {
    return request.get(`/${apiPath}/meta_entity/list`,
      {
        params: {
          projectId
        }
      })
      .then(response => checkResult(response))
  },
  /**
   * 保存实体
   */
  saveOrUpdate (data, isUpdate) {
    let saveURL = `/${apiPath}/meta_entity/save`
    let method = 'post'
    if (isUpdate) {
      saveURL = `/${apiPath}/meta_entity/update`
      method = 'put'
    }
    return request[method](saveURL, data)
      .then(response => checkResult(response))
  },
  /**
   * 修改实体特性
   */
  updateFeature (entityId, data) {
    return request.put(`/${apiPath}/meta_entity/${entityId}/feature`, data)
      .then(response => checkResult(response))
  },
  /**
   * 批量删除实体
   */
  deleteBatch (data) {
    return request.put(`/${apiPath}/meta_entity/delete_batch`, data)
      .then(response => checkResult(response))
  },
  /**
   * 查询多对多关联实体
   */
  getMtmEntityListPair (entityId) {
    return request.get(`/${apiPath}/meta_entity/${entityId}/mtm_entity_list_pair`)
      .then(response => checkResult(response))
  },
  /**
   * 校验实体内部数据
   */
  validateEntityInner (projectId, entityId) {
    return request.get(`/${apiPath}/meta_validate/${projectId}/entity_inner/${entityId}`)
      .then(response => checkResult(response))
  },
  /**
   * 获取某实体作为外键关联时的默认外键名(mysql字段名)
   */
  getDefaultFkFieldNameForSql (entityId) {
    return request.get(`/${apiPath}/meta_entity/${entityId}/default_fk_field_name_for_sql`,
      {
        responseType: 'text'
      })
      .then(response => checkResult(response))
  },
  /**
   * 查询ER图数据
   */
  getErDiagram (projectId, entityIds) {
    return request.get(`/${apiPath}/er_diagram/show`,
      {
        params: {
          projectId,
          entityIds
        }
      })
      .then(response => checkResult(response))
  }

}
