export function initSourceFormBean (forEdit) {
  const formBean = {
    projectId: null,
    aggregation: false,
    entityId: null,
    joins: [],
    detailColumnList: [],
    whereList: [],
    detailOrderList: [],
    dimensionList: [],
    metricsList: [],
    havingList: [],
    aggOrderList: [],
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
    left: initJoinPartDTO(leftIndex, false),
    right: initJoinPartDTO(rightIndex, true)
  }
  return join
}

/**
 * 初始化关联左/右部分
 * @param joinIndex 关联序号
 * @param isRight 是否右边
 */
export function initJoinPartDTO (joinIndex, isRight) {
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
    tmp2: {
      key: '', // [joinPartType]_[objId]_[fieldId]
      joinPartType: '',
      joinIndex: joinIndex,
      obj: null,
      field: null
    }
  }
  if (isRight) {
    joinPart.tmp1 = {
      key: '', // [joinPartType]_[objId]
      joinPartType: '',
      joinIndex: joinIndex,
      obj: null
    }
  }
  return joinPart
}

/**
 * 修改关联时修复表单数据错误
 * @param form 表单数据
 */
export function repairAtJoinChange (form) {
  // TODO 修复各个list内相关记录
}

/**
 * 移除关联时修复表单数据错误
 * @param form 表单数据
 * @param removeJoinIndex 被移除的关联序号（从1开始）
 */
export function repairAtJoinRemove (form, removeJoinIndex) {
  // 修复该关联之后的所有关联序号
  let currentIndex = removeJoinIndex
  const currentLength = form.joins.length
  while (currentIndex - 1 < currentLength) {
    const join = form.joins[currentIndex - 1]
    // 如果左边就是被移除的，则把左边初始化
    if (join.left.joinIndex === removeJoinIndex) {
      join.left = initJoinPartDTO(0, false)
    } else if (join.left.joinIndex > removeJoinIndex) {
      //  如果左边序号大于被移除的序号，则序号--
      join.left.joinIndex--
      join.left.tmp2.joinIndex--
    }
    // 右边序号--
    join.right.joinIndex--
    join.right.tmp1.joinIndex--
    join.right.tmp2.joinIndex--
    // 继续往后迭代
    currentIndex++
  }
}

/**
 * 将表单数据剥离干净，只提交有用的信息
 * @param form
 */
export function stripFormBean (form) {

}

export function getRules () {
  return {
    projectId: [
      { required: true, type: 'number', message: '请选择项目', trigger: 'change' }
    ],
    entityId: [
      { required: true, message: '请输入实体', trigger: 'blur' }
    ],
    detailColumnList: [
      { required: true, type: 'array', message: '请选择明细列', trigger: 'change' }
    ],
    dimensionList: [
      { required: true, type: 'array', message: '请设置聚合(维度)', trigger: 'blur' }
    ],
    metricsList: [
      { required: true, type: 'array', message: '请设置聚合(指标)', trigger: 'blur' }
    ]
  }
}
