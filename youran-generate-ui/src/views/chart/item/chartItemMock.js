import chartGranularity from '@/utils/options-chart-granularity'
import chartAggFunction from '@/utils/options-chart-agg-function'
import chartCustomFieldType from '@/utils/options-chart-custom-field-type'
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
  // mock自定义指标
  if (chartItem.metrics.custom) {
    const customFieldTypeOption = chartCustomFieldType.getCustomFieldTypeOption(chartItem.metrics.customFieldType)
    return customFieldTypeOption.mockValue(i)
  }
  const aggFunctionOption = chartAggFunction.getAggFunctionOption(chartItem.metrics.aggFunction)
  if (aggFunctionOption) {
    if (aggFunctionOption.value === 5 || aggFunctionOption.value === 6) {
      return 10 + i * 10
    } else {
      const field = chartItem.metrics.field
      if (field) {
        return field.fieldExample
      }
    }
  }
  return ''
}

/**
 * 模拟第i条维度数据
 */
function mockDimension (chartItem, i, constDetails, unique) {
  const field = chartItem.dimension.field
  let value = null
  const granularityOption = chartGranularity.getGranularityOption(chartItem.dimension.granularity)
  if (granularityOption && granularityOption.mockDimension) {
    // 非单值维度，按聚合粒度mock
    value = granularityOption.mockDimension(i)
  } else if (field) {
    // 单值维度且是枚举，从枚举值mock
    if (field.dicType && constDetails[field.dicType] && constDetails[field.dicType].length) {
      value = mockDimensionByConst(i, constDetails[field.dicType], unique)
    } else {
      // 单值普通字段，按类型mock
      if (field.jfieldType === 'String') {
        value = chartItem.titleAlias + convertIndexToAlphabet(i)
      } else if (['Integer', 'Short', 'Long', 'Double', 'Float', 'BigDecimal'].includes(field.jfieldType)) {
        value = i + ''
      } else {
        // todo 其他类型
        value = i + ''
      }
    }
  }
  if (value === null) {
    return null
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
 * 从枚举中mock
 */
function mockDimensionByConst (i, details, unique) {
  if (unique && details.length - 1 < i) {
    return null
  }
  const detail = details[i % details.length]
  return detail.detailRemark
}

/**
 * 模拟维度列表数据
 */
function mockDimensionList (chartItem, count, constDetails, unique) {
  const list = []
  for (let i = 0; i < count; i++) {
    const item = mockDimension(chartItem, i, constDetails, unique)
    if (item) {
      list.push(item)
    }
  }
  return list
}

export default {
  mockDimensionList,
  mockDimension,
  mockMetrics
}
