import _truncate from 'lodash/truncate'
import timeGranularityUtil from './options-chart-time-granularity'

/**
 * 关联类型
 * filterValueType为过滤值类型：1单值、2双值、3多值、4无值
 * matchFieldTypes为匹配的java字段类型
 * display函数用来渲染表达式
 * timeGranularity为是否时间粒度
 */
const filterOperatorOptions = [
  {
    value: 1,
    label: '等于',
    filterValueType: 1,
    matchFieldTypes: [
      'String',
      'Integer', 'Short', 'Long', 'Double', 'Float', 'BigDecimal',
      'LocalDate', 'LocalDateTime', 'Date',
      'Boolean'
    ],
    display: function (type, value, timeGranularity) {
      if (['String', 'LocalDate', 'LocalDateTime', 'Date'].indexOf(type) > -1) {
        return ' = \'' + _truncate(value[0], { length: 20 }) + '\''
      }
      return ' = ' + value[0]
    },
    timeGranularity: false
  },
  {
    value: 2,
    label: '不等于',
    filterValueType: 1,
    matchFieldTypes: [
      'String',
      'Integer', 'Short', 'Long', 'Double', 'Float', 'BigDecimal',
      'LocalDate', 'LocalDateTime', 'Date'
    ],
    display: function (type, value, timeGranularity) {
      if (['String', 'LocalDate', 'LocalDateTime', 'Date'].indexOf(type) > -1) {
        return ' != \'' + _truncate(value[0], { length: 20 }) + '\''
      }
      return ' != ' + value[0]
    },
    timeGranularity: false
  },
  {
    value: 3,
    label: '包含',
    filterValueType: 3,
    matchFieldTypes: [
      'String',
      'Integer', 'Short', 'Long', 'Double', 'Float', 'BigDecimal',
      'LocalDate', 'LocalDateTime', 'Date'
    ],
    display: function (type, value, timeGranularity) {
      return ' in (...)'
    },
    timeGranularity: false
  },
  {
    value: 4,
    label: '不包含',
    filterValueType: 3,
    matchFieldTypes: [
      'String',
      'Integer', 'Short', 'Long', 'Double', 'Float', 'BigDecimal',
      'LocalDate', 'LocalDateTime', 'Date'
    ],
    display: function (type, value, timeGranularity) {
      return ' not in (...)'
    },
    timeGranularity: false
  },
  {
    value: 5,
    label: '为空',
    filterValueType: 4,
    matchFieldTypes: [
      'String',
      'Integer', 'Short', 'Long', 'Double', 'Float', 'BigDecimal',
      'LocalDate', 'LocalDateTime', 'Date',
      'Boolean'
    ],
    display: function (type, value, timeGranularity) {
      return ' is null'
    },
    timeGranularity: false
  },
  {
    value: 6,
    label: '不为空',
    filterValueType: 4,
    matchFieldTypes: [
      'String',
      'Integer', 'Short', 'Long', 'Double', 'Float', 'BigDecimal',
      'LocalDate', 'LocalDateTime', 'Date',
      'Boolean'
    ],
    display: function (type, value, timeGranularity) {
      return ' is not null'
    },
    timeGranularity: false
  },
  {
    value: 7,
    label: 'like',
    filterValueType: 1,
    matchFieldTypes: ['String'],
    display: function (type, value, timeGranularity) {
      return ' like \'%' + _truncate(value[0], { length: 20 }) + '%\''
    },
    timeGranularity: false
  },
  {
    value: 9,
    label: '大于',
    filterValueType: 1,
    matchFieldTypes: [
      'String',
      'Integer', 'Short', 'Long', 'Double', 'Float', 'BigDecimal',
      'LocalDate', 'LocalDateTime', 'Date'
    ],
    display: function (type, value, timeGranularity) {
      if (['String', 'LocalDate', 'LocalDateTime', 'Date'].indexOf(type) > -1) {
        return ' > \'' + _truncate(value[0], { length: 20 }) + '\''
      }
      return ' > ' + value[0]
    },
    timeGranularity: false
  },
  {
    value: 10,
    label: '小于',
    filterValueType: 1,
    matchFieldTypes: [
      'String',
      'Integer', 'Short', 'Long', 'Double', 'Float', 'BigDecimal',
      'LocalDate', 'LocalDateTime', 'Date'
    ],
    display: function (type, value, timeGranularity) {
      if (['String', 'LocalDate', 'LocalDateTime', 'Date'].indexOf(type) > -1) {
        return ' < \'' + _truncate(value[0], { length: 20 }) + '\''
      }
      return ' < ' + value[0]
    },
    timeGranularity: false
  },
  {
    value: 11,
    label: '大于等于',
    filterValueType: 1,
    matchFieldTypes: [
      'String',
      'Integer', 'Short', 'Long', 'Double', 'Float', 'BigDecimal',
      'LocalDate', 'LocalDateTime', 'Date'
    ],
    display: function (type, value, timeGranularity) {
      if (['String', 'LocalDate', 'LocalDateTime', 'Date'].indexOf(type) > -1) {
        return ' >= \'' + _truncate(value[0], { length: 20 }) + '\''
      }
      return ' >= ' + value[0]
    },
    timeGranularity: false
  },
  {
    value: 12,
    label: '小于等于',
    filterValueType: 1,
    matchFieldTypes: [
      'String',
      'Integer', 'Short', 'Long', 'Double', 'Float', 'BigDecimal',
      'LocalDate', 'LocalDateTime', 'Date'
    ],
    display: function (type, value, timeGranularity) {
      if (['String', 'LocalDate', 'LocalDateTime', 'Date'].indexOf(type) > -1) {
        return ' <= \'' + _truncate(value[0], { length: 20 }) + '\''
      }
      return ' <= ' + value[0]
    },
    timeGranularity: false
  },
  {
    value: 13,
    label: '介于之间',
    filterValueType: 2,
    matchFieldTypes: [
      'String',
      'Integer', 'Short', 'Long', 'Double', 'Float', 'BigDecimal',
      'LocalDate', 'LocalDateTime', 'Date'
    ],
    display: function (type, value, timeGranularity) {
      if (['String', 'LocalDate', 'LocalDateTime', 'Date'].indexOf(type) > -1) {
        return ' between \'' + _truncate(value[0], { length: 20 }) + '\' and \'' + _truncate(value[1], { length: 20 }) + '\''
      }
      return ' between ' + value[0] + ' and ' + value[1] + ''
    },
    timeGranularity: false
  },
  {
    value: 21,
    label: '是当前',
    filterValueType: 4,
    matchFieldTypes: ['LocalDate', 'LocalDateTime', 'Date'],
    display: function (type, value, timeGranularity) {
      return '是当前' + timeGranularityUtil.findTimeGranularityOption(timeGranularity).label
    },
    timeGranularity: true
  },
  {
    value: 22,
    label: '前段时间',
    filterValueType: 1,
    matchFieldTypes: ['LocalDate', 'LocalDateTime', 'Date'],
    display: function (type, value, timeGranularity) {
      return '是前' + value[0] + timeGranularityUtil.findTimeGranularityOption(timeGranularity).label
    },
    timeGranularity: true
  },
  {
    value: 23,
    label: '后段时间',
    filterValueType: 1,
    matchFieldTypes: ['LocalDate', 'LocalDateTime', 'Date'],
    display: function (type, value, timeGranularity) {
      return '是后' + value[0] + timeGranularityUtil.findTimeGranularityOption(timeGranularity).label
    },
    timeGranularity: true
  }
]

export default {
  /**
   * 根据字段类型获取匹配的操作符
   */
  getFilterOperatorOptions (fieldType) {
    return filterOperatorOptions.filter(value => value.matchFieldTypes.indexOf(fieldType) > -1)
  },
  /**
   * 获取过滤操作符选项
   */
  getFilterOperatorOption (value) {
    return filterOperatorOptions.find(option => option.value === value)
  }
}
