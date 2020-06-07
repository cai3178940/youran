<template>
  <div class="barLineChartDiv">
    <div class="barLineChart"/>
  </div>
</template>

<script>
import echarts from 'echarts'

export default {
  name: 'barLineChart',
  props: [
    'projectId',
    'chartId'
  ],
  data () {
    return {
      chart: null,
      list: [{
        deptId: 'aaa',
        count: 1
      }]
    }
  },
  methods: {
    initChart () {
      const chartEl = this.$el.children[0]
      this.chart = echarts.init(chartEl)
      this.chart.setOption({
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'shadow'
          }
        },
        dataset: {
          dimensions: ['deptId', 'count'],
          source: this.list
        },
        xAxis: {
          name: '部门id',
          type: 'category'
        },
        yAxis: {
          type: 'value',
          name: '用户数'
        },
        series: [
          {
            name: '用户数',
            type: 'bar'
          }
        ]
      })
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
    this.initChart()
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
