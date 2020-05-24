import searchUtil from './searchUtil'
import { groupBy } from '@/utils/common-util'
import { repairDetailColumn } from './item/detailColumnModel'
import { repairWhere } from './item/whereModel'
import { repairDetailOrder } from './item/detailOrderModel'

export function initSourceFormBean (projectId) {
  const formBean = {
    sourceId: null,
    projectId: projectId,
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
    entity: null,
    customColumnList: []
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

export function buildTmp1ByEntity (joinIndex, entity) {
  return {
    key: 'entity_' + entity.entityId,
    joinPartType: 'entity',
    joinIndex: joinIndex,
    obj: entity
  }
}

export function buildTmp1ByMtm (joinIndex, mtm) {
  return {
    key: 'mtm_' + mtm.mtmId,
    joinPartType: 'mtm',
    joinIndex: joinIndex,
    obj: mtm
  }
}

export function buildTmp2ByEntity (joinIndex, entity, field) {
  return {
    key: 'entity_' + entity.entityId + '_' + field.fieldId,
    joinPartType: 'entity',
    joinIndex: joinIndex,
    obj: entity,
    field: field
  }
}

export function buildTmp2ByMtm (joinIndex, mtm, mtmField) {
  return {
    key: 'mtm_' + mtm.mtmId + '_' + mtmField,
    joinPartType: 'mtm',
    joinIndex: joinIndex,
    obj: mtm,
    field: mtmField
  }
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
 * 表单回显时修复formBean
 */
export function repairFormBean (form, entityOptions, mtmOptions) {
  // 修复entity属性
  form.entity = searchUtil.findEntityInEntityOptions(entityOptions, form.entityId)
  // 修复join数组
  for (let i = 0; i < form.joins.length; i++) {
    const join = form.joins[i]
    repairJoinPart(join.left, entityOptions, mtmOptions)
    repairJoinPart(join.right, entityOptions, mtmOptions)
  }
  // 修复明细列
  if (form.detailColumnList) {
    form.detailColumnList
      .forEach(detailColumn => repairDetailColumn(detailColumn, form))
    const group = groupBy(form.detailColumnList, detailColumn => detailColumn.custom)
    form.detailColumnList = group['false'] ? group['false'] : []
    form.customColumnList = group['true'] ? group['true'] : []
  }
  // 修复过滤条件
  if (form.whereList) {
    form.whereList
      .forEach(where => repairWhere(where, form))
  }
  // 修复排序列
  if (form.detailOrderList) {
    form.detailOrderList
      .forEach(detailOrder => repairDetailOrder(detailOrder, form.detailColumnList))
  }
  // 修复聚合(维度)
  // 修复聚合(指标)
  // 修复聚合(过滤)
  // 修复聚合(排序)
}

function repairJoinPart (part, entityOptions, mtmOptions) {
  if (part.joinPartType === 'entity') {
    const entity = entityOptions.find(entity => entity.entityId === part.entityId)
    const field = entity.fieldList.find(field => field.fieldId === part.fieldId)
    part.entity = entity
    part.field = field
    part.tmp1 = buildTmp1ByEntity(part.joinIndex, entity)
    part.tmp2 = buildTmp2ByEntity(part.joinIndex, entity, field)
  } else if (part.joinPartType === 'mtm') {
    const mtm = mtmOptions.find(mtm => mtm.mtmId === part.mtmId)
    part.mtm = mtm
    part.tmp1 = buildTmp1ByMtm(part.joinIndex, mtm)
    part.tmp2 = buildTmp2ByMtm(part.joinIndex, mtm, part.mtmField)
  }
}

/**
 * 从表单中提取出需要提交到后端的信息
 */
export function extractFormBean (form) {
  const copy = JSON.parse(JSON.stringify(form))
  // 将自定义列合并到明细列中
  copy.detailColumnList.push(...copy.customColumnList)
  delete copy.customColumnList
  // 清除临时对象
  delete copy.entity
  copy.joins.forEach(join => {
    delete join.left.entity
    delete join.left.mtm
    delete join.left.field
    delete join.left.tmp1
    delete join.left.tmp2
    delete join.right.entity
    delete join.right.mtm
    delete join.right.field
    delete join.right.tmp1
    delete join.right.tmp2
  })
  copy.detailOrderList.forEach(detailOrder => {
    delete detailOrder.detailColumn
  })
  return copy
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
