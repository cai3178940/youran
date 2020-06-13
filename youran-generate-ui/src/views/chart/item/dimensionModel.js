import shortid from 'shortid'
import chartOptions from '@/utils/options-chart'
import searchUtil from '../searchUtil'

function initFormBean () {
  return {
    key: 'dimension_' + shortid.generate(),
    joinIndex: null,
    fieldId: null,
    granularity: null,
    field: null,
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
    dimension.field.jfieldType, 't' + dimension.joinIndex + '.' + dimension.field.fieldName)
}

/**
 * 从tmp中抽取数据到form
 */
function repairDimensionForSubmit (form) {
  form.joinIndex = form.tmp1.joinIndex
  form.granularity = form.tmp2.value
  form.fieldId = form.tmp1.field.fieldId
  form.field = form.tmp1.field
}

/**
 * 表单回显时修复维度条件数据
 */
function repairDimensionForEdit (dimension, sourceForm) {
  if (!dimension.field) {
    dimension.field = searchUtil.findEntityFieldInFormBean(sourceForm, dimension.joinIndex, dimension.fieldId)[1]
  }
  dimension.tmp2 = chartOptions.getGranularityOption(dimension.granularity)
  dimension.tmp1 = {
    key: dimension.joinIndex + '_' + dimension.fieldId,
    field: dimension.field,
    joinIndex: dimension.joinIndex
  }
}

export default {
  initFormBean,
  displayText,
  repairDimensionForEdit,
  repairDimensionForSubmit
}
