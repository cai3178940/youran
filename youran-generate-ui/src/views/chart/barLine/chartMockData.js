import chartItemMock from '../item/chartItemMock'

export default {
  /**
   * 假设axisX为部门，axisX2为性别
   * 则返回结果为：['deptId','男','女']
   */
  mockDimensionsForMode1 (axisX, axisX2) {
    const dimensions = chartItemMock.mockDimensionList(axisX2, 3)
    return [axisX.alias].concat(dimensions)
  },
  mockSourceForMode1 (dimensions, axisX, axisY) {
    const source = []
    for (let i = 0; i < 5; i++) {
      const item = {}
      item[dimensions[0]] = chartItemMock.mockDimension(axisX, i)
      for (let j = 1; j < dimensions.length; j++) {
        item[dimensions[j]] = chartItemMock.mockMetrics(axisY, i + j)
      }
      source.push(item)
    }
    return source
  },
  mockSeriesForMode1 (dimensions) {
    const series = []
    for (let i = 0; i < dimensions.length - 1; i++) {
      series.push({ type: 'bar' })
    }
    return series
  },
  mockDimensionsForMode2 (axisX, axisYList) {
    const dimensions = [axisX.alias]
    axisYList.forEach(axisY => dimensions.push(axisY.titleAlias))
    return dimensions
  },
  mockSourceForMode2 (dimensions, axisX, axisYList) {
    const source = []
    for (let i = 0; i < 5; i++) {
      const item = {}
      item[dimensions[0]] = chartItemMock.mockDimension(axisX, i)
      for (let j = 1; j < dimensions.length; j++) {
        const axisY = axisYList[j - 1]
        item[dimensions[j]] = chartItemMock.mockMetrics(axisY, i + j)
      }
      source.push(item)
    }
    return source
  },
  mockSeriesForMode2 (axisYList) {
    const series = []
    for (let i = 0; i < axisYList.length; i++) {
      const axisY = axisYList[i]
      const seriesType = axisY.seriesType ? axisY.seriesType : 'bar'
      series.push({ type: seriesType })
    }
    return series
  }
}
