import searchUtil from './searchUtil'

export function initDetailListFormBean (projectId) {
  const formBean = {
    chartId: null,
    projectId: projectId,
    sourceId: null,
    chartName: '',
    module: '',
    title: '',
    defaultPageSize: 10,
    columnList: []
  }
  return formBean
}

/**
 * 模拟表格数据
 */
export function mockTableData (rowIndex, chartItem) {
  const detailColumn = chartItem.detailColumn
  if (!detailColumn) {
    console.info(chartItem)
    return ''
  }
  let value
  if (detailColumn.custom) {
    value = rowIndex
  } else {
    value = rowIndex
  }
  if (chartItem.valuePrefix) {
    value = chartItem.valuePrefix + value
  }
  if (chartItem.valueSuffix) {
    value = value + chartItem.valueSuffix
  }
  return value
}

export function repairChartForm (chartFormBean, sourceFormBean) {
  chartFormBean.columnList.forEach(chartItem => {
    const detailColumn = searchUtil.findSourceItemById(sourceFormBean.detailColumnList, chartItem.sourceItemId)
    chartItem.detailColumn = detailColumn
  })
}

export function getRules () {
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
