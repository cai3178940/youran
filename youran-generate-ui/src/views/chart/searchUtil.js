function findEntityInEntityOptions (entityOptions, entityId) {
  return entityOptions.find(entity => entity.entityId === entityId)
}

function findFieldInEntity (entity, fieldId) {
  return entity.fieldList.find(field => field.fieldId === fieldId)
}

function findEntityInFormBean (formBean, joinIndex) {
  let entity
  if (joinIndex === 0) {
    entity = formBean.entity
  } else {
    const join = formBean.joins[joinIndex - 1]
    entity = join.right.entity
  }
  return entity
}

function findEntityFieldInFormBean (formBean, joinIndex, fieldId) {
  const entity = findEntityInFormBean(formBean, joinIndex)
  const field = findFieldInEntity(entity, fieldId)
  return [entity, field]
}

function findSourceItemById (sourceItemList, sourceItemId) {
  return sourceItemList.find(item => item.sourceItemId === sourceItemId)
}

function findEntityIdsInFormBean (formBean) {
  const entityIds = new Set([formBean.entityId])
  formBean.joins.forEach(join => {
    if (join.right.joinPartType === 'entity') {
      entityIds.add(join.right.entityId)
    }
  })
  return Array.from(entityIds)
}

function findFieldIdsInDetailColumns (detailColumnList) {
  const fieldIds = new Set()
  detailColumnList.forEach(detailColumn => {
    if (!detailColumn.custom) {
      fieldIds.add(detailColumn.fieldId)
    }
  })
  return Array.from(fieldIds)
}

function findFieldIdsInDimensionAndMetrics (dimensionList, metricsList) {
  const fieldIds = new Set()
  dimensionList.forEach(dimension => {
    fieldIds.add(dimension.fieldId)
  })
  metricsList.forEach(metrics => {
    if (!metrics.custom) {
      fieldIds.add(metrics.fieldId)
    }
  })
  return Array.from(fieldIds)
}

export default {
  findEntityInEntityOptions,
  findFieldInEntity,
  findEntityInFormBean,
  findEntityFieldInFormBean,
  findSourceItemById,
  findEntityIdsInFormBean,
  findFieldIdsInDetailColumns,
  findFieldIdsInDimensionAndMetrics
}
