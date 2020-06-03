function initDetailListFormBean (projectId) {
  const formBean = {
    chartId: null,
    projectId: projectId,
    sourceId: null,
    chartName: '',
    module: '',
    title: '',
    defaultPageSize: 10,
    columnList: [],
    hiddenColumnList: []
  }
  return formBean
}

/**
 * 从明细列构建图表项
 */
function initChartItemByDetailColumn (detailColumn) {
  const sourceItemId = detailColumn.sourceItemId
  let alias
  let titleAlias
  if (detailColumn.custom) {
    alias = 'custom' + sourceItemId
    titleAlias = '【自定义列】'
  } else {
    alias = detailColumn.field.jfieldName
    titleAlias = detailColumn.field.fieldDesc
  }
  return {
    sourceItemId: sourceItemId,
    alias: alias,
    titleAlias: titleAlias,
    showFkTitle: false,
    valuePrefix: '',
    valueSuffix: '',
    percent: false,
    format: '',
    detailColumn: detailColumn
  }
}

/**
 * 模拟表格数据
 */
function mockTableData (rowIndex, chartItem) {
  const detailColumn = chartItem.detailColumn
  if (!detailColumn) {
    return ''
  }
  let value
  if (detailColumn.custom) {
    value = rowIndex + 1
  } else {
    if (detailColumn.field) {
      value = detailColumn.field.fieldExample
    } else {
      value = rowIndex + 1
    }
  }
  if (chartItem.valuePrefix) {
    value = chartItem.valuePrefix + value
  }
  if (chartItem.valueSuffix) {
    value = value + chartItem.valueSuffix
  }
  return value
}

function getRules () {
  return {
    projectId: [
      { required: true, type: 'number', message: '请选择项目', trigger: 'change' }
    ],
    sourceId: [
      { required: true, message: 'sourceId不能为空', trigger: 'blur' }
    ],
    chartName: [
      { required: true, message: '请输入图表名称', trigger: 'blur' },
      { max: 64, message: '长度不能超过64个字符', trigger: 'blur' }
    ],
    module: [
      { max: 50, message: '长度不能超过50个字符', trigger: 'blur' }
    ],
    title: [
      { required: true, message: '请输入图表标题', trigger: 'blur' },
      { max: 64, message: '长度不能超过64个字符', trigger: 'blur' }
    ],
    defaultPageSize: [
      { required: true, message: '请输入每页记录数', trigger: 'blur' }
    ],
    columnList: [
      { required: true, type: 'array', message: '请设置明细列', trigger: 'blur' }
    ]
  }
}

export default {
  initDetailListFormBean,
  initChartItemByDetailColumn,
  mockTableData,
  getRules
}
