import shortid from 'shortid'
import _truncate from 'lodash/truncate'
import searchUtil from '../searchUtil'

function buildCommonDetailColumn (joinIndex, field) {
  return {
    key: 'detialColumn_' + joinIndex + '_' + field.fieldId,
    fieldId: field.fieldId,
    custom: false,
    joinIndex: joinIndex,
    field: field
  }
}

function buildCustomDetailColumn (customContent, customFieldType) {
  return {
    key: 'detialColumn_' + shortid.generate(),
    custom: true,
    joinIndex: 0,
    customContent: customContent,
    customFieldType: customFieldType
  }
}

function displayText (detailColumn) {
  if (detailColumn.custom) {
    return _truncate(detailColumn.customContent, { length: 20 })
  } else {
    return detailColumn.field.fieldDesc + '(' + detailColumn.field.fieldName + ')'
  }
}

/**
 * 表单回显时修复明细列数据
 */
function repairDetailColumnForEdit (detailColumn, sourceForm) {
  const joinIndex = detailColumn.joinIndex
  if (!detailColumn.custom) {
    const fieldId = detailColumn.fieldId
    detailColumn.field = searchUtil.findEntityFieldInFormBean(sourceForm, joinIndex, fieldId)[1]
  }
}

function getCustomColumnRules () {
  return {
    customContent: [
      { required: true, message: '请填写自定义内容', trigger: 'blur' }
    ],
    customFieldType: [
      { required: true, message: '请选择自定义字段类型', trigger: 'change' }
    ]
  }
}

export default {
  buildCommonDetailColumn,
  buildCustomDetailColumn,
  displayText,
  repairDetailColumnForEdit,
  getCustomColumnRules
}
