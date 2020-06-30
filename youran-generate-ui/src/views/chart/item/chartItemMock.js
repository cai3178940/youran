import chartGranularity from '@/utils/options-chart-granularity'
const alphabet = ['a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z']

function convertIndexToAlphabet (i) {
  if (i >= alphabet.length) {
    return i
  } else {
    return alphabet[i]
  }
}

/**
 * 模拟第i条指标数据
 */
function mockMetrics (chartItem, i) {
  return 10 + i * 10
}
/**
 * 模拟第i条维度数据
 */
function mockDimension (chartItem, i) {
  console.info(chartItem.dimension)
  const field = chartItem.dimension.field
  let value = ''
  const granularityOption = chartGranularity.getGranularityOption(chartItem.dimension.granularity)
  if (granularityOption.mockDimension) {
    // 非单值维度，按聚合粒度mock
    value = granularityOption.mockDimension(i)
  } else {
    // 单值维度，按字段类型mock
    if (field.jfieldType === 'String') {
      value = chartItem.titleAlias + convertIndexToAlphabet(i)
    } else if (['Integer', 'Short', 'Long', 'Double', 'Float', 'BigDecimal'].includes(field.jfieldType)) {
      value = i + ''
    } else {
      // todo 其他类型，如果是枚举则加载枚举值
      value = i + ''
    }
  }
  if (chartItem.valuePrefix) {
    value = chartItem.valuePrefix + value
  }
  if (chartItem.valueSuffix) {
    value += chartItem.valueSuffix
  }
  return value
}

/**
 * 模拟维度列表数据
 */
function mockDimensionList (chartItem, count) {
  const list = []
  for (let i = 0; i < count; i++) {
    list.push(mockDimension(chartItem, i))
  }
  return list
}

export default {
  mockDimensionList,
  mockDimension,
  mockMetrics
}
