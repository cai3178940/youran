import shortid from 'shortid'
import chartFilterOperator from '@/utils/options-chart-filter-operator'
import searchUtil from '../searchUtil'

function initFormBean () {
  const formBean = {
    key: 'where_' + shortid.generate(),
    joinIndex: null,
    fieldId: null,
    filterOperator: null,
    filterValue: null,
    timeGranularity: null,
    custom: false,
    customContent: null
  }
  initOtherInfo(formBean)
  return formBean
}

function initOtherInfo (formBean) {
  formBean.field = null
  formBean.tmp1 = {
    key: '',
    field: null,
    joinIndex: null
  }
  formBean.tmp2 = chartFilterOperator.getFilterOperatorOption(1)
  formBean.tmp3 = [null]
  formBean.tmp4 = [null, null]
  formBean.tmp5 = []
}

function displayText (form) {
  if (form.custom) {
    return '[自定义过滤]'
  }
  return 't' + form.joinIndex + '.' + form.field.fieldName +
    form.tmp2.display(form.field.jfieldType, form.filterValue, form.timeGranularity)
}

/**
 * 表单回显时修复过滤条件数据
 */
function repairWhereForEdit (where, sourceForm) {
  if (!where.custom) {
    where.field = searchUtil.findEntityFieldInFormBean(sourceForm, where.joinIndex, where.fieldId)[1]
    where.tmp2 = chartFilterOperator.getFilterOperatorOption(where.filterOperator)
    where.tmp1 = {
      key: where.joinIndex + '_' + where.fieldId,
      field: where.field,
      joinIndex: where.joinIndex
    }
    if (where.tmp2.filterValueType === 1) {
      where.tmp3 = where.filterValue
    } else if (where.tmp2.filterValueType === 2) {
      where.tmp4 = where.filterValue
    } else if (where.tmp2.filterValueType === 3) {
      where.tmp5 = where.filterValue
    }
  } else {
    initOtherInfo(where)
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
    where.filterOperator = where.tmp2.value
    if (where.tmp2.filterValueType === 1) {
      where.filterValue = where.tmp3
    } else if (where.tmp2.filterValueType === 2) {
      where.filterValue = where.tmp4
    } else if (where.tmp2.filterValueType === 3) {
      where.filterValue = where.tmp5
    }
  } else {
    where.joinIndex = 0
  }
}

export default {
  initFormBean,
  displayText,
  repairWhereForSubmit,
  repairWhereForEdit
}
