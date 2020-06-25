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
    buildOption (chartBean) {
      const option = {
        title: {
          text: ''
        },
        legend: {},
        tooltip: {},
        dataset: {
          source: []
        },
        xAxis: { type: 'category' },
        yAxis: {},
        series: []
      }
      option.title.text = chartBean.title
      const mode = this.checkParamMode(chartBean)
      if (mode === 1) {
        // 模式1：存在附加维度，则将附加维度每个值转换成列，和主维度共同形成x轴
        const header = chartMockData.mockHeaderForMode1(chartBean.axisX, chartBean.axisX2)
        const source = chartMockData.mockSourceForMode1(header, chartBean.axisX, chartBean.axisYList[0])
        option.dataset.source = source
        const series = chartMockData.mockSeriesForMode1(header, chartBean.axisYList[0])
        option.series = series
      } else if (mode === 2) {
        // 模式2：存在多个指标，每个指标作为单独的一列
        const header = chartMockData.mockHeaderForMode2(chartBean.axisX, chartBean.axisYList)
        const source = chartMockData.mockSourceForMode2(header, chartBean.axisX, chartBean.axisYList)
        option.dataset.source = source
        const series = chartMockData.mockSeriesForMode2(chartBean.axisYList)
        option.series = series
      }
      return option
    },
    /**
     * 校验参数模式
     */
    checkParamMode (chartBean) {
      // 不存在主维度，则为异常模式
      if (!chartBean.axisX) {
        return 0
      }
      // 不存在指标，则为异常模式
      if (!chartBean.axisYList || chartBean.axisYList.length === 0) {
        return 0
      }
      // 存在附加维度，则为模式1，否则为模式2
      if (chartBean.axisX2) {
        return 1
      } else {
        return 2
      }
    },
    renderChart (chartBean) {
      const chartEl = this.$el.children[0]
      this.chart = echarts.init(chartEl)
      this.chart.setOption(this.buildOption(chartBean), true)
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
  .barLineChart {
    width: 100%;
    height: 400px;
  }
</style>
