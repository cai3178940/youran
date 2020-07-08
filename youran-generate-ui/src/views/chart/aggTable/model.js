import dimensionModel from '../item/dimensionModel'
import metricsModel from '../item/metricsModel'

function initAggTableFormBean (projectId) {
  const formBean = {
    chartId: null,
    projectId: projectId,
    sourceId: null,
    chartName: '',
    module: '',
    title: '',
    defaultPageSize: 10,
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
