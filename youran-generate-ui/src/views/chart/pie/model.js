import chartModel from '../chartModel'

function initPieFormBean (projectId) {
  const formBean = {
    chartId: null,
    projectId: projectId,
    sourceId: null,
    chartName: '',
    module: '',
    title: '',
    dimension: null,
    metrics: null,
    optionTemplate: `{
  tooltip: {},
  legend: {
    type: 'scroll',
    orient: 'vertical',
    right: 10,
    top: 20,
    bottom: 20
  },
  dataset: {
    source: \${source}
  },
  series: [
    {
      center: ['40%', '50%'],
      type: 'pie'
    }
  ]
}`
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
 * 从指标构建图表项
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
    metrics: metrics
  }
}

function getRules () {
  return {
    ...chartModel.getRules(),
    dimension: [
      { required: true, message: '请选择维度', trigger: 'change' }
    ],
    metrics: [
      { required: true, message: '请选择指标', trigger: 'change' }
    ]
  }
}

/**
 * 获取需要加载的常量名
 */
function getConstName (formBean) {
  if (!formBean.dimension) {
    return null
  }
  const field = formBean.dimension.dimension.field
  return field.dicType
}

export default {
  initPieFormBean,
  initChartItemByDimension,
  initChartItemByMetrics,
  getRules,
  getConstName
}
