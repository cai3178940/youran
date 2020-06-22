import chartItemMock from '../item/chartItemMock'

export default {
  /**
   * 假设axisX为部门，axisX2为性别
   * 则返回结果为：['dept','男','女']
   */
  mockDimensionsForMode1 (axisX, axisX2) {
    const dimensions = chartItemMock.mockDimensionList(axisX2, 3)
    return [axisX.alias].concat(dimensions)
  },
  /**
   * 返回结果：
   * [
   *   ['dept','男','女'],
   *   ['部门1',20,30],
   *   ['部门2',30,40],
   *   ['部门3',40,50],
   *   ['部门4',50,60],
   *   ['部门5',60,70]
   * ]
   */
  mockSourceForMode1 (dimensions, axisX, axisY) {
    // 第一行为维度行
    const source = [dimensions]
    for (let i = 0; i < 5; i++) {
      const item = []
      item[0] = chartItemMock.mockDimension(axisX, i)
      for (let j = 1; j < dimensions.length; j++) {
        item[j] = chartItemMock.mockMetrics(axisY, i + j)
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
  /**
   * 假设axisX为部门，axisYList[0]为部门人数，axisYList[1]为部门平均年龄
   * 则返回结果为：['dept','employeeNum','avgAge']
   */
  mockDimensionsForMode2 (axisX, axisYList) {
    const dimensions = [axisX.alias]
    axisYList.forEach(axisY => dimensions.push(axisY.titleAlias))
    return dimensions
  },
  /**
   * 返回结果：
   * [
   *   ['dept','employeeNum','avgAge'],
   *   ['部门1',20,30],
   *   ['部门2',30,40],
   *   ['部门3',40,50],
   *   ['部门4',50,60],
   *   ['部门5',60,70]
   * ]
   */
  mockSourceForMode2 (dimensions, axisX, axisYList) {
    // 第一行为维度行
    const source = [dimensions]
    for (let i = 0; i < 5; i++) {
      const item = []
      item[0] = chartItemMock.mockDimension(axisX, i)
      for (let j = 1; j < dimensions.length; j++) {
        const axisY = axisYList[j - 1]
        item[j] = chartItemMock.mockMetrics(axisY, i + j)
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
