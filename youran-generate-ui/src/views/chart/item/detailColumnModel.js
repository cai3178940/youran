import shortid from 'shortid'
import { abbreviate } from '@/utils/common-util'
import searchUtil from '../searchUtil'

function buildCommonDetailColumn (joinIndex, field) {
  return {
    key: 'common_' + joinIndex + '_' + field.fieldId,
    fieldId: field.fieldId,
    custom: false,
    joinIndex: joinIndex,
    field: null
  }
}

function buildCustomDetailColumn (customContent, customFieldType) {
  return {
    key: 'custom_' + shortid.generate(),
    custom: true,
    joinIndex: 0,
    customContent: customContent,
    customFieldType: customFieldType
  }
}

function displayText (detailColumn) {
  if (detailColumn.custom) {
    return abbreviate(detailColumn.customContent, 20)
  } else {
    return detailColumn.field.fieldDesc + '(' + detailColumn.field.fieldName + ')'
  }
}

/**
 * 表单回显时修复明细列数据
 */
function repairDetailColumnForEdit (detailColumn, sourceForm) {
  const joinIndex = detailColumn.joinIndex
  if (detailColumn.custom) {
    detailColumn.key = 'custom_' + shortid.generate()
  } else {
    const fieldId = detailColumn.fieldId
    detailColumn.field = searchUtil.findEntityFieldInFormBean(sourceForm, joinIndex, fieldId)[1]
    detailColumn.key = 'common_' + joinIndex + '_' + fieldId
  }
}

export default {
  buildCommonDetailColumn,
  buildCustomDetailColumn,
  displayText,
  repairDetailColumnForEdit
}
