import chartOptions from '@/utils/options-chart'
import searchUtil from '../searchUtil'

function initFormBean () {
  return {
    joinIndex: null,
    fieldId: null,
    aggFunction: null,
    custom: false,
    customContent: null,
    customFieldType: null,
    field: null,
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

function displayText (form) {
  if (form.custom) {
    return '[自定义内容]'
  }
  return form.tmp2.display('t' + form.joinIndex + '.' + form.field.fieldName)
}

/**
 * 表单回显时修复指标条件数据
 */
function repairMetricsForEdit (metrics, sourceForm) {
  const joinIndex = metrics.joinIndex
  if (!metrics.custom) {
    const field = searchUtil.findEntityFieldInFormBean(sourceForm, joinIndex, metrics.fieldId)[1]
    metrics.field = field
    metrics.tmp1 = {
      key: joinIndex + '_' + field.fieldId,
      field: field,
      joinIndex: joinIndex
    }
    metrics.tmp2 = chartOptions.getAggFunctionOption(metrics.aggFunction)
  }
}

/**
 * 从tmp中抽取数据到form
 */
function repairMetricsForSubmit (form) {
  if (!form.custom) {
    form.joinIndex = form.tmp1.joinIndex
    form.fieldId = form.tmp1.field.fieldId
    form.field = form.tmp1.field
    form.aggFunction = form.tmp2.value
  }
}

export default {
  initFormBean,
  displayText,
  repairMetricsForEdit,
  repairMetricsForSubmit
}
