import searchUtil from '../searchUtil'
import chartOptions from '@/utils/options-chart'

function initFormBean () {
  return {
    _displayText: '',
    joinIndex: null,
    filterOperator: null,
    filterValue: null,
    parentId: null,
    parentKey: null,
    metrics: null
  }
}

function initTmp () {
  return {
    // 过滤操作符下拉框绑定对象
    tmp2: {
      value: null,
      label: null,
      filterValueType: null,
      matchFieldTypes: []
    },
    // 单过滤值绑定对象
    tmp3: [null],
    // 双过滤值绑定对象
    tmp4: [null, null],
    // 多过滤值绑定对象
    tmp5: []
  }
}

/**
 * 从form中抽取数据到tmp
 */
function formToTmp (form, tmp) {
  tmp.tmp2 = chartOptions.getFilterOperatorOption(form.filterOperator)
  if (tmp.tmp2.filterValueType === 1) {
    tmp.tmp3 = form.filterValue
  } else if (tmp.tmp2.filterValueType === 2) {
    tmp.tmp4 = form.filterValue
  } else if (tmp.tmp2.filterValueType === 3) {
    tmp.tmp5 = form.filterValue
  }
}

/**
 * 从tmp中抽取数据到form
 */
function tmpToForm (tmp, form) {
  form.filterOperator = tmp.tmp2.value
  if (tmp.tmp2.filterValueType === 1) {
    form.filterValue = tmp.tmp3
  } else if (tmp.tmp2.filterValueType === 2) {
    form.filterValue = tmp.tmp4
  } else if (tmp.tmp2.filterValueType === 3) {
    form.filterValue = tmp.tmp5
  }
}

/**
 * 修复过滤数据
 */
function repairHaving (having, metricsList) {
  if (!having.metrics) {
    const metrics = searchUtil.findSourceItemById(metricsList, having.parentId)
    having.metrics = metrics
  }
  having.joinIndex = having.metrics.joinIndex
  having.parentKey = having.metrics.key
  const operatorOption = chartOptions.getFilterOperatorOption(having.filterOperator)
  having._displayText = having.metrics._displayText +
    operatorOption.display(having.metrics.field.jfieldType, having.filterValue)
}

export default {
  initFormBean,
  initTmp,
  formToTmp,
  tmpToForm,
  repairHaving
}
