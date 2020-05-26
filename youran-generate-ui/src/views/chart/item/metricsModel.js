import chartOptions from '@/utils/options-chart'
import searchUtil from '../searchUtil'

export function initFormBean () {
  return {
    _displayText: '',
    joinIndex: null,
    fieldId: null,
    aggFunction: null,
    custom: false,
    customContent: null,
    customFieldType: null,
    field: null
  }
}

export function initTmp () {
  return {
    // 指标字段下拉框绑定对象
    tmp1: {
      key: '',
      field: null,
      joinIndex: null
    },
    // 聚合函数下拉框绑定对象
    tmp2: {
      value: null,
      label: null,
      matchFieldTypes: []
    }
  }
}

/**
 * 从form中抽取数据到tmp
 */
export function formToTmp (form, tmp, entityFieldOptions) {
  if (!form.custom) {
    const [joinIndex, entity] = entityFieldOptions.find(
      ([joinIndex, entity]) => joinIndex === form.joinIndex)
    const field = searchUtil.findFieldInEntity(entity, form.fieldId)
    tmp.tmp1 = {
      key: joinIndex + '_' + field.fieldId,
      field: field,
      joinIndex: joinIndex
    }
    form.field = field
    tmp.tmp2 = chartOptions.getAggFunctionOption(form.filterOperator)
  }
}

/**
 * 从tmp中抽取数据到form
 */
export function tmpToForm (tmp, form) {
  if (!form.custom) {
    form.joinIndex = tmp.tmp1.joinIndex
    form.fieldId = tmp.tmp1.field.fieldId
    form.field = tmp.tmp1.field
    form.aggFunction = tmp.tmp2.value
    form._displayText = tmp.tmp2.display('t' + form.joinIndex + '.' + tmp.tmp1.field.fieldName)
  } else {
    form._displayText = '[自定义内容]'
  }
}

/**
 * 表单回显时修复指标条件数据
 */
export function repairMetrics (metrics, sourceForm) {
  const joinIndex = metrics.joinIndex
  if (metrics.custom) {
    metrics._displayText = '[自定义内容]'
  } else {
    const field = searchUtil.findEntityFieldInFormBean(sourceForm, joinIndex, metrics.fieldId)[1]
    metrics.field = field
    const aggFunctionOption = chartOptions.getAggFunctionOption(metrics.aggFunction)
    metrics._displayText = aggFunctionOption.display('t' + metrics.joinIndex + '.' + field.fieldName)
  }
}
