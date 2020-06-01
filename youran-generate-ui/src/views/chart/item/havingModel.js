import searchUtil from '../searchUtil'
import metricsModel from './metricsModel'
import chartOptions from '@/utils/options-chart'

function initFormBean () {
  return {
    joinIndex: null,
    filterOperator: null,
    filterValue: null,
    parentId: null,
    parentKey: null,
    metrics: null,
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

function displayText (having) {
  return metricsModel.displayText(having.metrics) +
    having.tmp2.display(having.metrics.field.jfieldType, having.filterValue)
}

/**
 * 从tmp中抽取数据到form
 */
function repairHavingForSubmit (having) {
  having.filterOperator = having.tmp2.value
  if (having.tmp2.filterValueType === 1) {
    having.filterValue = having.tmp3
  } else if (having.tmp2.filterValueType === 2) {
    having.filterValue = having.tmp4
  } else if (having.tmp2.filterValueType === 3) {
    having.filterValue = having.tmp5
  }
}

/**
 * 修复过滤数据
 */
function repairHavingForEdit (having, metricsList) {
  if (!having.metrics) {
    const metrics = searchUtil.findSourceItemById(metricsList, having.parentId)
    having.metrics = metrics
  }
  having.joinIndex = having.metrics.joinIndex
  having.parentKey = having.metrics.key
  having.tmp2 = chartOptions.getFilterOperatorOption(having.filterOperator)
  if (having.tmp2.filterValueType === 1) {
    having.tmp3 = having.filterValue
  } else if (having.tmp2.filterValueType === 2) {
    having.tmp4 = having.filterValue
  } else if (having.tmp2.filterValueType === 3) {
    having.tmp5 = having.filterValue
  }
}

export default {
  initFormBean,
  displayText,
  repairHavingForEdit,
  repairHavingForSubmit
}
