import chartModel from '../chartModel'

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
    axisYList: [],
    optionTemplate: `{
  legend: {},
  tooltip: {},
  dataset: {
    source: \${source}
  },
  xAxis: { type: 'category' },
  yAxis: {},
  series: \${series}
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
    ...chartModel.getRules(),
    axisX: [
      { required: true, message: '请选择x轴', trigger: 'change' }
    ],
    axisYList: [
      { required: true, type: 'array', message: '请设置y轴', trigger: 'change' }
    ]
  }
}

/**
 * 获取需要加载的常量名
 */
function getConstNames (formBean) {
  const names = new Set()
  if (formBean.axisX) {
    const constName = getConstName(formBean.axisX)
    if (constName) {
      names.add(constName)
    }
  }
  if (formBean.axisX2) {
    const constName = getConstName(formBean.axisX2)
    if (constName) {
      names.add(constName)
    }
  }
  return Array.from(names)
}

function getConstName (chartItem) {
  const field = chartItem.dimension.field
  return field.dicType
}

export default {
  initBarLineFormBean,
  initChartItemByDimension,
  initChartItemByMetrics,
  getRules,
  getConstNames
}
