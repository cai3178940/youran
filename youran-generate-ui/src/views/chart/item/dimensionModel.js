import chartOptions from '@/utils/options-chart'
import searchUtil from '../searchUtil'

function initFormBean () {
  return {
    key: '',
    joinIndex: null,
    fieldId: null,
    granularity: null,
    // 维度字段下拉框绑定对象
    tmp1: {
      key: '',
      field: null,
      joinIndex: null
    },
    // 粒度绑定对象
    tmp2: {}
  }
}

function displayText (dimension) {
  return dimension.tmp2.display(
    dimension.tmp1.field.jfieldType, 't' + dimension.joinIndex + '.' + dimension.tmp1.field.fieldName)
}

/**
 * 从tmp中抽取数据到form
 */
function repairDimensionForSubmit (form) {
  form.key = 'dimension_' + form.tmp1.joinIndex + '_' + form.tmp1.field.fieldId
  form.joinIndex = form.tmp1.joinIndex
  form.granularity = form.tmp2.value
  form.fieldId = form.tmp1.field.fieldId
}

/**
 * 表单回显时修复维度条件数据
 */
function repairDimensionForEdit (dimension, sourceForm) {
  const field = searchUtil.findEntityFieldInFormBean(sourceForm, dimension.joinIndex, dimension.fieldId)[1]
  dimension.key = 'dimension_' + dimension.joinIndex + '_' + field.fieldId
  dimension.tmp2 = chartOptions.getGranularityOption(dimension.granularity)
  dimension.tmp1 = {
    key: dimension.joinIndex + '_' + field.fieldId,
    field: field,
    joinIndex: dimension.joinIndex
  }
}

export default {
  initFormBean,
  displayText,
  repairDimensionForEdit,
  repairDimensionForSubmit
}
