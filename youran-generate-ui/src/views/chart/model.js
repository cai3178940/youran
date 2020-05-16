export function initSourceFormBean (forEdit) {
  const formBean = {
    projectId: null,
    aggregation: false,
    entityId: null,
    joins: [],
    limit: 0
  }
  if (forEdit) {
    formBean['sourceId'] = null
  }
  return formBean
}

export function initJoinDTO (rightIndex) {
  const join = {
    joinType: 1,
    leftIndex: 0,
    rightIndex: rightIndex,
    leftEntityId: null,
    rightEntityId: null,
    leftFieldId: null,
    rightFieldId: null,
    leftMtmId: null,
    rightMtmId: null,
    leftMtmField: null,
    rightMtmField: null,
    // 后面的属性是缓存对象，表单提交时需要过滤掉
    leftEntity: null,
    leftMtm: null,
    rightEntity: null,
    rightMtm: null
  }
  return join
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
