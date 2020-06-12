function initBarLineFormBean (projectId) {
  const formBean = {
    chartId: null,
    projectId: projectId,
    sourceId: null,
    chartName: '',
    module: '',
    title: '',
    axisX: null,
    axisX2: null,
    axisYList: []
  }
  return formBean
}

/**
 * 从维度构建图表项
 */
function initChartItemByDimension (dimension) {
  return {
    sourceItemId: dimension.sourceItemId,
    alias: dimension.field.jfieldName,
    titleAlias: dimension.field.fieldDesc,
    showFkTitle: false,
    valuePrefix: '',
    valueSuffix: '',
    percent: false,
    format: '',
    dimension: dimension
  }
}
/**
 * 从维度构建图表项
 */
function initChartItemByMetrics (metrics) {
  const sourceItemId = metrics.sourceItemId
  let alias
  let titleAlias
  if (metrics.custom) {
    alias = 'custom' + sourceItemId
    titleAlias = '【自定义指标】'
  } else {
    alias = metrics.field.jfieldName
    titleAlias = metrics.field.fieldDesc
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
    seriesType: 'bar',
    metrics: metrics
  }
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
    axisX: [
      { required: true, message: '请选择x轴', trigger: 'change' }
    ],
    axisYList: [
      { required: true, type: 'array', message: '请设置y轴', trigger: 'change' }
    ]
  }
}

export default {
  initBarLineFormBean,
  initChartItemByDimension,
  initChartItemByMetrics,
  getRules
}
