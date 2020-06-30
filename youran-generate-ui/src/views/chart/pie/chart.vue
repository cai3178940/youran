<template>
  <div class="pieChartDiv">
    <div class="pieChart"></div>
  </div>
</template>

<script>
import echarts from 'echarts'
import chartMockData from './chartMockData'
import constDetailMixin from '@/components/Mixins/const-detail'

/**
 * 获取需要加载的常量名
 */
function getConstName (chartBean) {
  if (!chartBean.dimension) {
    return null
  }
  const field = chartBean.dimension.dimension.field
  return field.dicType
}

export default {
  name: 'pieChart',
  mixins: [constDetailMixin],
  data () {
    return {
      chart: null
    }
  },
  methods: {
    buildOption (chartBean) {
      const optionTemplate = chartBean.optionTemplate
      if (!optionTemplate) {
        return {}
      }
      const optionJson = optionTemplate.replace(`\${title}`, `""`)
        .replace(`\${source}`, '[]')
      const option = JSON.parse(optionJson)
      option.title.text = chartBean.title
      if (chartBean.dimension) {
        if (chartBean.metrics) {
          option.dataset.source = chartMockData.mockDatasetSource(chartBean.dimension, chartBean.metrics, this.constDetails)
        }
      }
      return option
    },
    renderChart (chartBean) {
      const callback = () => {
        const chartEl = this.$el.children[0]
        this.chart = echarts.init(chartEl)
        this.chart.setOption(this.buildOption(chartBean))
      }
      const constName = getConstName(chartBean)
      if (constName) {
        this.loadConstDetail(chartBean.projectId, constName)
          .then(callback)
      } else {
        callback()
      }
    }
  },
  beforeDestroy () {
    if (!this.chart) {
      return
    }
    this.chart.dispose()
    this.chart = null
  },
  mounted () {
    this.renderChart({})
  }
}
</script>

<style lang="scss">
  @import '../../../assets/common.scss';
  .pieChart {
    width: 100%;
    height: 400px;
  }
</style>
