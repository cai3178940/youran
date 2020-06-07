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
    buildOption (dimension, metrics) {
      const option = {
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
            name: '',
            type: 'pie',
            data: []
          }
        ]
      }
      if (dimension) {
        option.series[0].name = dimension.titleAlias
        const legendData = chartMockData.mockLegendData(dimension)
        option.legend.data = legendData
        if (metrics) {
          option.series[0].data = chartMockData.mockSeriesData(metrics, legendData)
        }
      }
      return option
    },
    renderChart (dimension, metrics) {
      const chartEl = this.$el.children[0]
      this.chart = echarts.init(chartEl)
      this.chart.setOption(this.buildOption(dimension, metrics))
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
    this.renderChart(null, null)
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
