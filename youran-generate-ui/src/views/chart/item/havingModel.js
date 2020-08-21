import shortid from 'shortid'
import searchUtil from '../searchUtil'
import metricsModel from './metricsModel'
import chartFilterOperator from '@/utils/options-chart-filter-operator'
import chartCustomFieldType from '@/utils/options-chart-custom-field-type'
import { boolExceptZero } from '@/utils/common-util'

function initFormBean () {
  return {
    key: 'having_' + shortid.generate(),
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
  const metricsText = metricsModel.displayText(having.metrics)
  let jfieldType
  if (having.metrics.custom) {
    jfieldType = chartCustomFieldType.getCustomFieldTypeOption(having.metrics.customFieldType).jfieldType
  } else {
    jfieldType = having.metrics.field.jfieldType
  }
  return metricsText + having.tmp2.display(jfieldType, having.filterValue)
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
  having.joinIndex = having.metrics.joinIndex
  having.parentKey = having.metrics.key
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
  having.tmp2 = chartFilterOperator.getFilterOperatorOption(having.filterOperator)
  if (having.tmp2.filterValueType === 1) {
    having.tmp3 = having.filterValue
  } else if (having.tmp2.filterValueType === 2) {
    having.tmp4 = having.filterValue
  } else if (having.tmp2.filterValueType === 3) {
    having.tmp5 = having.filterValue
  }
}

function getRules () {
  return {
    metrics: [
      { required: true, message: '请选择过滤列', trigger: 'change' }
    ],
    tmp2: [
      {
        required: true,
        validator: (rule, value, callback) => {
          if (!value || !value.value) {
            callback(new Error('请选择过滤运算符'))
          }
          callback()
        },
        trigger: 'change'
      }
    ],
    tmp3: [
      {
        required: true,
        validator: (rule, value, callback) => {
          if (!value || !boolExceptZero(value[0])) {
            callback(new Error('请输入过滤值'))
          }
          callback()
        },
        trigger: 'blur'
      }
    ],
    tmp4: [
      {
        required: true,
        validator: (rule, value, callback) => {
          if (!value ||
            !boolExceptZero(value[0]) ||
            !boolExceptZero(value[1])) {
            callback(new Error('请输入过滤值范围'))
          }
          callback()
        },
        trigger: 'blur'
      }
    ],
    tmp5: [
      {
        required: true,
        validator: (rule, value, callback) => {
          if (!value || !value.length || !boolExceptZero(value[0])) {
            callback(new Error('请输入过滤值'))
          }
          callback()
        },
        trigger: 'change'
      }
    ]
  }
}

export default {
  initFormBean,
  displayText,
  repairHavingForEdit,
  repairHavingForSubmit,
  getRules
}
