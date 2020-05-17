export function initSourceFormBean (forEdit) {
  const formBean = {
    projectId: null,
    aggregation: false,
    entityId: null,
    joins: [],
    limit: 0,
    // 后面的属性是缓存对象，表单提交时需要过滤掉
    entity: null
  }
  if (forEdit) {
    formBean['sourceId'] = null
  }
  return formBean
}

export function initJoinDTO (leftIndex, rightIndex) {
  const join = {
    joinType: 1,
    left: initJoinPartDTO(leftIndex),
    right: initJoinPartDTO(rightIndex)
  }
  return join
}

export function initJoinPartDTO (joinIndex) {
  const joinPart = {
    joinPartType: '',
    joinIndex: joinIndex,
    entityId: null,
    fieldId: null,
    mtmId: null,
    mtmField: null,
    // 后面的属性是缓存对象，表单提交时需要过滤掉
    entity: null,
    mtm: null,
    field: null,
    tmp1: [], // 临时存储select绑定值
    tmp2: [] // 临时存储select绑定值
  }
  return joinPart
}
/**
 * 修复表单数据错误
 */
export function repairFormBean (form) {
  console.info('修复表单数据')
}

export function getRules () {
  return {
    projectId: [
      { required: true, type: 'number', message: '请选择项目', trigger: 'change' }
    ],
    entityId: [
      { required: true, message: '请输入实体', trigger: 'blur' }
    ]
  }
}
