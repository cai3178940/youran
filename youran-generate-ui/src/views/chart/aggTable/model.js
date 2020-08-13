import dimensionModel from '../item/dimensionModel'
import metricsModel from '../item/metricsModel'
import chartModel from '../chartModel'

function initAggTableFormBean (projectId) {
  const formBean = {
    chartId: null,
    projectId: projectId,
    sourceId: null,
    chartName: '',
    module: '',
    title: '',
    defaultPageSize: 10,
    excelExport: true,
    dimensionList: [],
    metricsList: []
  }
  return formBean
}

/**
 * 从维度构建图表项
 */
function initChartItemByDimension (dimension) {
  return {
    sourceItemId: dimension.sourceItemId,
    alias: 'dimension' + dimension.sourceItemId,
    titleAlias: dimensionModel.displayText(dimension),
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
  return {
    sourceItemId: metrics.sourceItemId,
    alias: 'metrics' + metrics.sourceItemId,
    titleAlias: metricsModel.displayText(metrics),
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
    defaultPageSize: [
      { required: true, message: '请输入每页记录数', trigger: 'blur' }
    ],
    dimensionList: [
      { required: true, type: 'array', message: '请设置维度列', trigger: 'blur' }
    ],
    metricsList: [
      { required: true, type: 'array', message: '请设置指标列', trigger: 'blur' }
    ]
  }
}

export default {
  initAggTableFormBean,
  initChartItemByDimension,
  initChartItemByMetrics,
  getRules
}
