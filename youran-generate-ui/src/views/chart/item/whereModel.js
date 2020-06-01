import chartOptions from '@/utils/options-chart'
import searchUtil from '../searchUtil'

function initFormBean () {
  return {
    _displayText: '',
    joinIndex: null,
    fieldId: null,
    filterOperator: null,
    filterValue: null,
    timeGranularity: null,
    custom: false,
    customContent: null,
    operatorOption: {
      value: 1,
      filterValueType: 1,
      timeGranularity: false
    },
    field: null,
    // 以下是临时对象
    tmp1: {
      key: '',
      field: null,
      joinIndex: null
    },
    // 单过滤值绑定对象
    tmp3: [null],
    // 双过滤值绑定对象
    tmp4: [null, null],
    // 多过滤值绑定对象
    tmp5: []
  }
}

function displayText (form) {
  if (form.custom) {
    return '[自定义内容]'
  }
  return 't' + form.joinIndex + '.' + form.field.fieldName +
    form.operatorOption.display(form.field.jfieldType, form.filterValue, form.timeGranularity)
}

/**
 * 表单回显时修复过滤条件数据
 */
function repairWhereForEdit (where, sourceForm) {
  if (!where.custom) {
    where.operatorOption = chartOptions.getFilterOperatorOption(where.filterOperator)
    where.field = searchUtil.findEntityFieldInFormBean(sourceForm, where.joinIndex, where.fieldId)[1]
    where.tmp1 = {
      key: where.joinIndex + '_' + where.fieldId,
      field: where.field,
      joinIndex: where.joinIndex
    }
    if (where.operatorOption.filterValueType === 1) {
      where.tmp3 = where.filterValue
    } else if (where.operatorOption.filterValueType === 2) {
      where.tmp4 = where.filterValue
    } else if (where.operatorOption.filterValueType === 3) {
      where.tmp5 = where.filterValue
    }
  }
}

/**
 * 从临时数据中抽取数据填充到form
 */
function repairWhereForSubmit (where) {
  if (!where.custom) {
    where.joinIndex = where.tmp1.joinIndex
    where.field = where.tmp1.field
    where.fieldId = where.tmp1.field.fieldId
    where.filterOperator = where.operatorOption.value
    if (where.operatorOption.filterValueType === 1) {
      where.filterValue = where.tmp3
    } else if (where.operatorOption.filterValueType === 2) {
      where.filterValue = where.tmp4
    } else if (where.operatorOption.filterValueType === 3) {
      where.filterValue = where.tmp5
    }
  }
}

export default {
  initFormBean,
  displayText,
  repairWhereForSubmit,
  repairWhereForEdit
}
