<template>
  <div class="pieChartDiv">
    <div class="pieChart"></div>
  </div>
</template>

<script>
import echarts from 'echarts'
import chartMockData from './chartMockData'

export default {
  name: 'pieChart',
  data () {
    return {
      chart: null
    }
  },
  methods: {
    buildOption (chartBean) {
      const option = {
        title: {
          text: ''
        },
        tooltip: {
          trigger: 'item',
          formatter: '{a} <br/>{b}: {c} ({d}%)'
        },
        legend: {
          type: 'scroll',
          orient: 'vertical',
          right: 10,
          top: 20,
          bottom: 20,
          data: []
        },
        series: [
          {
            center: ['40%', '50%'],
            name: '',
            type: 'pie',
            data: []
          }
        ]
      }
      option.title.text = chartBean.title
      if (chartBean.dimension) {
        option.series[0].name = chartBean.dimension.titleAlias
        const legendData = chartMockData.mockLegendData(chartBean.dimension)
        option.legend.data = legendData
        if (chartBean.metrics) {
          option.series[0].data = chartMockData.mockSeriesData(chartBean.metrics, legendData)
        }
      }
      return option
    },
    renderChart (chartBean) {
      const chartEl = this.$el.children[0]
      this.chart = echarts.init(chartEl)
      this.chart.setOption(this.buildOption(chartBean))
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
