<template>
  <div class="pieChartDiv">
    <div class="pieChart"></div>
  </div>
</template>

<script>
import echarts from 'echarts'
import JSON5 from 'json5'
import chartMockData from './chartMockData'
import constDetailMixin from '@/components/Mixins/const-detail'
import model from './model'

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
      const optionJson = optionTemplate.replace(`\${source}`, '[]')
      const option = JSON5.parse(optionJson)
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
        this.chart.setOption(this.buildOption(chartBean), true)
      }
      const constName = model.getConstName(chartBean)
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
