import chartItemMock from '../item/chartItemMock'
export default {
  mockLegendData (dimension) {
    return chartItemMock.mockDimensionList(dimension, 5)
  },
  mockSeriesData (metrics, legendData) {
    const array = []
    for (let i = 0; i < legendData.length; i++) {
      const name = legendData[i]
      array.push({
        value: chartItemMock.mockMetrics(metrics, i),
        name: name
      })
    }
    return array
  }
}
