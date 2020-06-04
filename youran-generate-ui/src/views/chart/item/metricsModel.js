import chartOptions from '@/utils/options-chart'
import searchUtil from '../searchUtil'
import shortid from 'shortid'

function initFormBean () {
  return {
    key: '',
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

function displayText (metrics) {
  if (metrics.custom) {
    return '[自定义指标]'
  }
  return metrics.tmp2.display('t' + metrics.joinIndex + '.' + metrics.field.fieldName)
}

/**
 * 表单回显时修复指标条件数据
 */
function repairMetricsForEdit (metrics, sourceForm) {
  const joinIndex = metrics.joinIndex
  if (!metrics.custom) {
    if (!metrics.field) {
      metrics.field = searchUtil.findEntityFieldInFormBean(sourceForm, joinIndex, metrics.fieldId)[1]
    }
    metrics.key = 'metrics_' + joinIndex + '_' + metrics.field.fieldId
    metrics.tmp1 = {
      key: joinIndex + '_' + metrics.field.fieldId,
      field: metrics.field,
      joinIndex: joinIndex
    }
    metrics.tmp2 = chartOptions.getAggFunctionOption(metrics.aggFunction)
  } else {
    metrics.key = 'custom_' + shortid.generate()
  }
}

/**
 * 从tmp中抽取数据到form
 */
function repairMetricsForSubmit (metrics) {
  if (!metrics.custom) {
    metrics.key = 'metrics_' + metrics.tmp1.joinIndex + '_' + metrics.tmp1.field.fieldId
    metrics.joinIndex = metrics.tmp1.joinIndex
    metrics.fieldId = metrics.tmp1.field.fieldId
    metrics.field = metrics.tmp1.field
    metrics.aggFunction = metrics.tmp2.value
  } else {
    metrics.key = 'custom_' + shortid.generate()
    metrics.joinIndex = 0
  }
}

export default {
  initFormBean,
  displayText,
  repairMetricsForEdit,
  repairMetricsForSubmit
}
