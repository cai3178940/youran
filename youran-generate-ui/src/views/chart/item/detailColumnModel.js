import shortid from 'shortid'
import { abbreviate } from '@/utils/common-util'
import searchUtil from '../searchUtil'

export function buildCommonDetailColumn (joinIndex, field) {
  return {
    key: 'common_' + joinIndex + '_' + field.fieldId,
    fieldId: field.fieldId,
    custom: false,
    joinIndex: joinIndex,
    _displayText: field.fieldDesc + '(' + field.fieldName + ')'
  }
}

export function buildCustomDetailColumn (customContent, customFieldType) {
  return {
    key: 'custom_' + shortid.generate(),
    custom: true,
    joinIndex: 0,
    customContent: customContent,
    customFieldType: customFieldType,
    _displayText: abbreviate(customContent, 20)
  }
}

/**
 * 表单回显时修复明细列数据
 */
export function repairDetailColumn (detailColumn, sourceForm) {
  const joinIndex = detailColumn.joinIndex
  if (detailColumn.custom) {
    detailColumn.key = 'custom_' + shortid.generate()
    detailColumn._displayText = abbreviate(detailColumn.customContent, 20)
  } else {
    const fieldId = detailColumn.fieldId
    const field = searchUtil.findEntityFieldInFormBean(sourceForm, joinIndex, fieldId)[1]
    detailColumn.key = 'common_' + joinIndex + '_' + fieldId
    detailColumn._displayText = field.fieldDesc + '(' + field.fieldName + ')'
  }
}
