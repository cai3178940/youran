<template>
  <div class="pieDemoDiv">
    <div class="pieDemo"/>
  </div>
</template>
<script>
import echarts from 'echarts'

export default {
  name: 'pieDemo',
  data () {
    return {
      option: {
        tooltip: {},
        dataset: {
          source: [
            ['维度', '指标'],
            ['序列1', 41.1],
            ['序列2', 86.5],
            ['序列3', 24.1],
            ['序列4', 55.2]
          ]
        },
        series: [
          {
            center: [
              '50%',
              '50%'
            ],
            type: 'pie'
          }
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
        height: this.$parent.$el.clientHeight - 20
      })
    }
  },
  mounted () {
    this.renderChart()
    setTimeout(() => {
      this.resize()
    }, 100)
  }
}
</script>
