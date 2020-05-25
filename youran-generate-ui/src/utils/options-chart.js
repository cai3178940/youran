import { abbreviate } from './common-util'

/**
 * 粒度
 * matchFieldTypes为匹配的java字段类型
 */
const granularityOptions = [
  {
    value: 1,
    label: '等于',
    matchFieldTypes: [
      'String',
      'Integer', 'Short', 'Long', 'Double', 'Float', 'BigDecimal',
      'LocalDate', 'LocalDateTime', 'Date',
      'Boolean'
    ],
    display: function (type, value) {
      return value
    }
  },
  {
    value: 10,
    label: '10个刻度间隔',
    matchFieldTypes: [
      'Integer', 'Short', 'Long', 'Double', 'Float', 'BigDecimal'
    ],
    display: function (type, value) {
      return '10个刻度(' + value + ')'
    }
  },
  {
    value: 11,
    label: '100个刻度间隔',
    matchFieldTypes: [
      'Integer', 'Short', 'Long', 'Double', 'Float', 'BigDecimal'
    ],
    display: function (type, value) {
      return '100个刻度(' + value + ')'
    }
  },
  {
    value: 12,
    label: '1000个刻度间隔',
    matchFieldTypes: [
      'Integer', 'Short', 'Long', 'Double', 'Float', 'BigDecimal'
    ],
    display: function (type, value) {
      return '1000个刻度(' + value + ')'
    }
  },
  {
    value: 13,
    label: '10000个刻度间隔',
    matchFieldTypes: [
      'Integer', 'Short', 'Long', 'Double', 'Float', 'BigDecimal'
    ],
    display: function (type, value) {
      return '10000个刻度(' + value + ')'
    }
  },
  {
    value: 20,
    label: '分钟',
    matchFieldTypes: [
      'LocalDateTime', 'Date'
    ],
    display: function (type, value) {
      return '每分钟(' + value + ')'
    }
  },
  {
    value: 21,
    label: '小时',
    matchFieldTypes: [
      'LocalDateTime', 'Date'
    ],
    display: function (type, value) {
      return '每小时(' + value + ')'
    }
  },
  {
    value: 22,
    label: '天',
    matchFieldTypes: [
      'LocalDate', 'LocalDateTime', 'Date'
    ],
    display: function (type, value) {
      return '每天(' + value + ')'
    }
  },
  {
    value: 23,
    label: '周',
    matchFieldTypes: [
      'LocalDate', 'LocalDateTime', 'Date'
    ],
    display: function (type, value) {
      return '每周(' + value + ')'
    }
  },
  {
    value: 24,
    label: '月',
    matchFieldTypes: [
      'LocalDate', 'LocalDateTime', 'Date'
    ],
    display: function (type, value) {
      return '每月(' + value + ')'
    }
  },
  {
    value: 25,
    label: '季度',
    matchFieldTypes: [
      'LocalDate', 'LocalDateTime', 'Date'
    ],
    display: function (type, value) {
      return '每季度(' + value + ')'
    }
  },
  {
    value: 26,
    label: '年',
    matchFieldTypes: [
      'LocalDate', 'LocalDateTime', 'Date'
    ],
    display: function (type, value) {
      return '每年(' + value + ')'
    }
  }
]

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
        return ' = \'' + abbreviate(value[0], 20) + '\''
      }
      return ' = ' + value[0]
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
        return ' < \'' + abbreviate(value[0], 20) + '\''
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
        return ' >= \'' + abbreviate(value[0], 20) + '\''
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
        return ' <= \'' + abbreviate(value[0], 20) + '\''
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
        return ' between \'' + abbreviate(value[0], 20) + '\' and \'' + abbreviate(value[1], 20) + '\''
      }
      return ' between ' + value[0] + ' and ' + value[1] + ''
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
        return ' != \'' + abbreviate(value[0], 20) + '\''
      }
      return ' != ' + value[0]
    },
    timeGranularity: false
  },
  {
    value: 21,
    label: '是当前',
    filterValueType: 4,
    matchFieldTypes: ['LocalDate', 'LocalDateTime', 'Date'],
    display: function (type, value, timeGranularity) {
      return '是当前' + findTimeGranularityOption(timeGranularity).label
    },
    timeGranularity: true
  },
  {
    value: 22,
    label: '前段时间',
    filterValueType: 1,
    matchFieldTypes: ['LocalDate', 'LocalDateTime', 'Date'],
    display: function (type, value, timeGranularity) {
      return '是前' + value[0] + findTimeGranularityOption(timeGranularity).label
    },
    timeGranularity: true
  },
  {
    value: 23,
    label: '后段时间',
    filterValueType: 1,
    matchFieldTypes: ['LocalDate', 'LocalDateTime', 'Date'],
    display: function (type, value, timeGranularity) {
      return '是后' + value[0] + findTimeGranularityOption(timeGranularity).label
    },
    timeGranularity: true
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
      return ' like \'%' + abbreviate(value[0], 20) + '%\''
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
        return ' > \'' + abbreviate(value[0], 20) + '\''
      }
      return ' > ' + value[0]
    },
    timeGranularity: false
  }
]

const timeGranularityOptions = [
  {
    value: 1,
    label: '分钟'
  },
  {
    value: 2,
    label: '小时'
  },
  {
    value: 3,
    label: '天'
  },
  {
    value: 4,
    label: '周'
  },
  {
    value: 5,
    label: '月'
  },
  {
    value: 6,
    label: '季度'
  },
  {
    value: 7,
    label: '年'
  }
]

function findTimeGranularityOption (value) {
  return timeGranularityOptions.find(option => option.value === value)
}

export default {
  /**
   * 图表类型
   */
  chartTypeOptions: [
    {
      value: 1,
      label: '明细表',
      name: 'detailList',
      aggregation: false
    },
    {
      value: 2,
      label: '聚合表',
      name: 'aggTable',
      aggregation: true
    },
    {
      value: 3,
      label: '柱线图',
      name: 'barLine',
      aggregation: true
    },
    {
      value: 4,
      label: '饼图',
      name: 'pie',
      aggregation: true
    }
  ],
  /**
   * 关联类型
   */
  joinTypeOptions: [
    {
      value: 1,
      label: 'inner join'
    },
    {
      value: 2,
      label: 'left join'
    },
    {
      value: 3,
      label: 'right join'
    }
  ],
  /**
   * 自定义列字段类型
   */
  customFieldTypeOptions: [
    {
      value: 1,
      label: '字符串'
    },
    {
      value: 2,
      label: '整形'
    },
    {
      value: 3,
      label: '浮点型'
    },
    {
      value: 4,
      label: '日期'
    },
    {
      value: 5,
      label: '日期时间'
    }
  ],
  /**
   * 根据字段类型获取匹配的操作符
   */
  getFilterOperatorOptions (fieldType) {
    return filterOperatorOptions.filter(value => value.matchFieldTypes.indexOf(fieldType) > -1)
  },
  /**
   * 根据值获取过滤操作符选项
   */
  getFilterOperatorOption (value) {
    return filterOperatorOptions.find(option => option.value === value)
  },
  /**
   * 时间粒度
   */
  timeGranularityOptions: timeGranularityOptions,
  /**
   * 根据字段类型获取粒度
   */
  getGranularityOptions (fieldType) {
    return granularityOptions.filter(value => value.matchFieldTypes.indexOf(fieldType) > -1)
  },
  /**
   * 根据字段类型获取粒度
   */
  getGranularityOption (value) {
    return granularityOptions.find(option => option.value === value)
  }
}
