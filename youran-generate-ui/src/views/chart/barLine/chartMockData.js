import chartItemMock from '../item/chartItemMock'

function mockSeries (chartItem) {
  let seriesType = chartItem.seriesType ? chartItem.seriesType : 'bar'
  let areaStyle = null
  let stack = null
  let smooth = null
  if (seriesType === 'area-stack') {
    seriesType = 'line'
    areaStyle = {}
    stack = '总量'
  } else if (seriesType === 'bar-stack') {
    seriesType = 'bar'
    areaStyle = {}
    stack = '总量'
  } else if (seriesType === 'line-smooth') {
    seriesType = 'line'
    smooth = true
  }
  return {
    type: seriesType,
    areaStyle: areaStyle,
    stack: stack,
    smooth: smooth
  }
}

export default {
  /**
   * 假设axisX为部门，axisX2为性别
   * 则返回结果为：['部门','男','女']
   */
  mockHeaderForMode1 (axisX, axisX2, constDetails) {
    const header = chartItemMock.mockDimensionList(axisX2, 3, constDetails, true)
    return [axisX.titleAlias].concat(header)
  },
  /**
   * 返回结果：
   * [
   *   ['部门','男','女'],
   *   ['部门1',20,30],
   *   ['部门2',30,40],
   *   ['部门3',40,50],
   *   ['部门4',50,60],
   *   ['部门5',60,70]
   * ]
   */
  mockSourceForMode1 (header, axisX, axisY, constDetails) {
    // 第一行为维度行
    const source = [header]
    for (let i = 0; i < 5; i++) {
      const item = []
      item[0] = chartItemMock.mockDimension(axisX, i, constDetails, true)
      for (let j = 1; j < header.length; j++) {
        item[j] = chartItemMock.mockMetrics(axisY, i + j)
      }
      source.push(item)
    }
    return source
  },
  mockSeriesForMode1 (header, axisY) {
    const series = []
    for (let i = 0; i < header.length - 1; i++) {
      series.push(mockSeries(axisY))
    }
    return series
  },
  /**
   * 假设axisX为部门，axisYList[0]为人数，axisYList[1]为平均年龄
   * 则返回结果为：['部门','人数','平均年龄']
   */
  mockHeaderForMode2 (axisX, axisYList) {
    const header = [axisX.titleAlias]
    axisYList.forEach(axisY => header.push(axisY.titleAlias))
    return header
  },
  /**
   * 返回结果：
   * [
   *   ['部门','人数','平均年龄'],
   *   ['部门1',20,30],
   *   ['部门2',30,40],
   *   ['部门3',40,50],
   *   ['部门4',50,60],
   *   ['部门5',60,70]
   * ]
   */
  mockSourceForMode2 (header, axisX, axisYList, constDetails) {
    // 第一行为维度行
    const source = [header]
    for (let i = 0; i < 5; i++) {
      const item = []
      const value0 = chartItemMock.mockDimension(axisX, i, constDetails, true)
      if (value0 === null) {
        continue
      }
      item[0] = value0
      for (let j = 1; j < header.length; j++) {
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
      series.push(mockSeries(axisYList[i]))
    }
    return series
  }
}
