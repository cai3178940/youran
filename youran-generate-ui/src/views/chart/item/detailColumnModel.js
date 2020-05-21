export function buildCommonDetailColumn (joinIndex, field) {
  return {
    key: 'common_' + joinIndex + '_' + field.fieldId,
    fieldId: field.fieldId,
    custom: false,
    joinIndex: joinIndex,
    _displayText: field.fieldDesc + '(' + field.fieldName + ')'
  }
}

/**
 * 表单回显时修复明细列数据
 */
export function repairDetailColumn (detailColumn, form) {
  const joinIndex = detailColumn.joinIndex
  if (joinIndex === 0) {

  }
  const fieldId = detailColumn.fieldId
  detailColumn.key = 'common_' + joinIndex + '_' + fieldId
  detailColumn._displayText = ''
}
