
/**
 * 聚合函数
 */
const aggFunctionOptions = [
  {
    value: 1,
    label: 'sum',
    matchFieldTypes: [
      'Integer', 'Short', 'Long', 'Double', 'Float', 'BigDecimal'
    ],
    display: function (value) {
      return 'sum(' + value + ')'
    }
  },
  {
    value: 2,
    label: 'max',
    matchFieldTypes: [
      'String',
      'Integer', 'Short', 'Long', 'Double', 'Float', 'BigDecimal',
      'LocalDate', 'LocalDateTime', 'Date'
    ],
    display: function (value) {
      return 'max(' + value + ')'
    }
  },
  {
    value: 3,
    label: 'min',
    matchFieldTypes: [
      'String',
      'Integer', 'Short', 'Long', 'Double', 'Float', 'BigDecimal',
      'LocalDate', 'LocalDateTime', 'Date'
    ],
    display: function (value) {
      return 'min(' + value + ')'
    }
  },
  {
    value: 4,
    label: 'avg',
    matchFieldTypes: [
      'Integer', 'Short', 'Long', 'Double', 'Float', 'BigDecimal'
    ],
    display: function (value) {
      return 'avg(' + value + ')'
    }
  },
  {
    value: 5,
    label: 'count',
    matchFieldTypes: [
      'String',
      'Integer', 'Short', 'Long', 'Double', 'Float', 'BigDecimal',
      'LocalDate', 'LocalDateTime', 'Date',
      'Boolean'
    ],
    display: function (value) {
      return 'count(' + value + ')'
    }
  },
  {
    value: 6,
    label: 'count_distinct',
    matchFieldTypes: [
      'String',
      'Integer', 'Short', 'Long', 'Double', 'Float', 'BigDecimal',
      'LocalDate', 'LocalDateTime', 'Date',
      'Boolean'
    ],
    display: function (value) {
      return 'count(distinct ' + value + ')'
    }
  }
]

export default {
  /**
   * 根据字段类型获取聚合函数
   */
  getAggFunctionOptions (fieldType) {
    return aggFunctionOptions.filter(value => value.matchFieldTypes.indexOf(fieldType) > -1)
  },
  /**
   * 获取聚合函数选项
   */
  getAggFunctionOption (value) {
    return aggFunctionOptions.find(option => option.value === value)
  }
}
