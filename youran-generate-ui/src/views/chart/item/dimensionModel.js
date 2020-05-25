import chartOptions from '@/utils/options-chart'
import searchUtil from '../searchUtil'

export function initFormBean () {
  return {
    _displayText: '',
    joinIndex: null,
    fieldId: null,
    granularity: null
  }
}

export function initTmp () {
  return {
    // 维度字段下拉框绑定对象
    tmp1: {
      key: '',
      field: null,
      joinIndex: null
    }
  }
}

/**
 * 从form中抽取数据到tmp
 */
export function formToTmp (form, tmp, entityFieldOptions) {
  const [joinIndex, entity] = entityFieldOptions.find(
    ([joinIndex, entity]) => joinIndex === form.joinIndex)
  const field = searchUtil.findFieldInEntity(entity, form.fieldId)
  tmp.tmp1 = {
    key: joinIndex + '_' + field.fieldId,
    field: field,
    joinIndex: joinIndex
  }
}

/**
 * 从tmp中抽取数据到form
 */
export function tmpToForm (tmp, form) {
  const field = tmp.tmp1.field
  form.joinIndex = tmp.tmp1.joinIndex
  form.fieldId = field.fieldId
  const granularityOption = chartOptions.getGranularityOption(form.granularity)
  form._displayText = granularityOption.display(
    field.jfieldType, 't' + form.joinIndex + '.' + field.fieldName)
}

/**
 * 表单回显时修复维度条件数据
 */
export function repairDimension (dimension, sourceForm) {
  const joinIndex = dimension.joinIndex
  const field = searchUtil.findEntityFieldInFormBean(sourceForm, joinIndex, dimension.fieldId)[1]
  const granularityOption = chartOptions.getGranularityOption(dimension.granularity)
  dimension._displayText = granularityOption.display(
    field.jfieldType, 't' + dimension.joinIndex + '.' + field.fieldName)
}
