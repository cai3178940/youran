<template>
  <div class="barLineDemoDiv">
    <div class="barLineDemo"/>
  </div>
</template>
<script>
import echarts from 'echarts'

export default {
  name: 'barLineChart',
  data () {
    return {
      option: {
        legend: {},
        tooltip: {},
        dataset: {
          source: [
            ['product', '指标A', '指标B', '指标C'],
            ['序列1', 43.3, 85.8, 93.7],
            ['序列2', 83.1, 73.4, 55.1],
            ['序列3', 86.4, 65.2, 82.5],
            ['序列4', 72.4, 53.9, 39.1]
          ]
        },
        xAxis: { type: 'category' },
        yAxis: {},
        series: [
          { type: 'bar' },
          { type: 'bar' },
          { type: 'bar' }
        ]
      },
      chart: null
    }
  },
  methods: {
    /**
     * 渲染图表
     */
    renderChart () {
      const chartEl = this.$el.children[0]
      this.chart = echarts.init(chartEl)
      this.chart.setOption(this.option, true)
    },
    resize () {
      this.chart.resize({
        width: this.$parent.$el.clientWidth - 20,
        height: this.$parent.$el.clientHeight - 25
      })
    }
  },
  mounted () {
    this.renderChart()
    setTimeout(() => {
      this.resize()
    }, 50)
  }
}
</script>
