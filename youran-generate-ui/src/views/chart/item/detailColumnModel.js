import shortid from 'shortid'
import { abbreviate } from '@/utils/common-util'
import searchUtil from '../searchUtil'

function buildCommonDetailColumn (joinIndex, field) {
  return {
    key: 'common_' + joinIndex + '_' + field.fieldId,
    fieldId: field.fieldId,
    custom: false,
    joinIndex: joinIndex,
    _displayText: field.fieldDesc + '(' + field.fieldName + ')'
  }
}

function buildCustomDetailColumn (customContent, customFieldType) {
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
function repairDetailColumnForEdit (detailColumn, sourceForm) {
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

export default {
  buildCommonDetailColumn,
  buildCustomDetailColumn,
  repairDetailColumnForEdit
}
