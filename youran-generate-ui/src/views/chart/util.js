export function findEntityInEntityOptions (entityOptions, entityId) {
  return entityOptions.find(entity => entity.entityId === entityId)
}

export function findFieldInEntity (entity, fieldId) {
  return entity.fieldList.find(field => field.fieldId === fieldId)
}

export function findEntityInFormBean (formBean, joinIndex) {
  let entity
  if (joinIndex === 0) {
    entity = formBean.entity
  } else {
    const join = formBean.joins[joinIndex - 1]
    entity = join.right.entity
  }
  return entity
}

export function findEntityFieldInFormBean (formBean, joinIndex, fieldId) {
  const entity = findEntityInFormBean(formBean, joinIndex)
  const field = findFieldInEntity(entity, fieldId)
  return [entity, field]
}

export function findSourceItemById (sourceItemList, sourceItemId) {
  return sourceItemList.find(item => item.sourceItemId === sourceItemId)
}
