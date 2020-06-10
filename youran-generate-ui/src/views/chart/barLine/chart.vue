<template>
  <div class="barLineChartDiv">
    <div class="barLineChart"/>
  </div>
</template>

<script>
import echarts from 'echarts'
import chartMockData from './chartMockData'

export default {
  name: 'barLineChart',
  data () {
    return {
      chart: null
    }
  },
  methods: {
    buildOption (axisX, axisX2, axisYList) {
      const option = {
        legend: {},
        tooltip: {},
        dataset: {
          dimensions: [],
          source: []
        },
        xAxis: { type: 'category' },
        yAxis: {},
        series: []
      }
      const mode = this.checkParamMode(axisX, axisX2, axisYList)
      if (mode === 1) {
        // 模式1：存在附加维度，则将附加维度每个值转换成列，和主维度共同形成x轴
        const dimensions = chartMockData.mockDimensionsForMode1(axisX, axisX2)
        option.dataset.dimensions = dimensions
        const source = chartMockData.mockSourceForMode1(dimensions, axisX, axisYList[0])
        option.dataset.source = source
        const series = chartMockData.mockSeriesForMode1(dimensions)
        option.series = series
      } else if (mode === 2) {
        // 模式2：存在多个指标，每个指标作为单独的一列
        const dimensions = chartMockData.mockDimensionsForMode2(axisX, axisYList)
        option.dataset.dimensions = dimensions
        const source = chartMockData.mockSourceForMode2(dimensions, axisX, axisYList)
        option.dataset.source = source
        const series = chartMockData.mockSeriesForMode2(axisYList)
        option.series = series
      }
      return option
    },
    /**
     * 校验参数模式
     */
    checkParamMode (axisX, axisX2, axisYList) {
      // 不存在主维度，则为异常模式
      if (!axisX) {
        return 0
      }
      // 不存在指标，则为异常模式
      if (!axisYList || axisYList.length === 0) {
        return 0
      }
      // 存在附加维度，则为模式1，否则为模式2
      if (axisX2) {
        return 1
      } else {
        return 2
      }
    },
    renderChart (axisX, axisX2, axisYList) {
      const chartEl = this.$el.children[0]
      this.chart = echarts.init(chartEl)
      this.chart.setOption(this.buildOption(axisX, axisX2, axisYList))
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
    this.renderChart(null, null, null)
  }
}
</script>

<style lang="scss">
  @import '../../../assets/common.scss';
  .barLineChart {
    width: 100%;
    height: 400px;
  }
</style>
