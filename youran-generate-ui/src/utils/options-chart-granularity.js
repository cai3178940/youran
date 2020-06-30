import moment from 'moment'
/**
 * 粒度
 * matchFieldTypes为匹配的java字段类型
 */
const granularityOptions = [
  {
    value: 1,
    label: '单个值',
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
    },
    mockDimension: function (i) {
      return i * 10
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
    },
    mockDimension: function (i) {
      return i * 100
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
    },
    mockDimension: function (i) {
      return i * 1000
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
    },
    mockDimension: function (i) {
      return i * 10000
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
    },
    mockDimension: function (i) {
      return moment().subtract(i, 'minute').format('YYYY-MM-DD HH:mm')
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
    },
    mockDimension: function (i) {
      return moment().subtract(i, 'hour').format('YYYY-MM-DD HH') + ':00'
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
    },
    mockDimension: function (i) {
      return moment().subtract(i, 'day').format('YYYY-MM-DD')
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
    },
    mockDimension: function (i) {
      return moment().subtract(i, 'week').format('YYYY-ww')
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
    },
    mockDimension: function (i) {
      return moment().subtract(i, 'month').format('YYYY-MM')
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
    },
    mockDimension: function (i) {
      return moment().subtract(i, 'quarter').format('YYYY-Q')
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
    },
    mockDimension: function (i) {
      return moment().subtract(i, 'year').format('YYYY')
    }
  }
]

export default {
  /**
   * 根据字段类型获取粒度
   */
  getGranularityOptions (fieldType) {
    return granularityOptions.filter(value => value.matchFieldTypes.indexOf(fieldType) > -1)
  },
  /**
   * 获取粒度选项
   */
  getGranularityOption (value) {
    return granularityOptions.find(option => option.value === value)
  }
}
