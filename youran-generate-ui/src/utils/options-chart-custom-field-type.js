import moment from 'moment'

const customFieldTypeOptions = [
  {
    value: 1,
    jfieldType: 'String',
    label: '字符串',
    mockValue (i) {
      return '字符串' + i
    }
  },
  {
    value: 2,
    jfieldType: 'Integer',
    label: '整形',
    mockValue (i) {
      return Math.ceil(Math.random() * 100)
    }
  },
  {
    value: 3,
    jfieldType: 'Double',
    label: '浮点型',
    mockValue (i) {
      return 100 * Math.random().toFixed(4)
    }
  },
  {
    value: 4,
    jfieldType: 'Date',
    label: '日期',
    mockValue (i) {
      return moment().subtract(i, 'day').format('YYYY-MM-DD')
    }
  },
  {
    value: 5,
    jfieldType: 'Date',
    label: '日期时间',
    mockValue (i) {
      return moment().subtract(i, 'hour').format('YYYY-MM-DD HH:mm:ss')
    }
  }
]

export default {
  /**
   * 自定义列字段类型
   */
  customFieldTypeOptions: customFieldTypeOptions,
  /**
   * 获取自定义字段类型选项
   */
  getCustomFieldTypeOption (customFieldType) {
    return customFieldTypeOptions.find(option => option.value === customFieldType)
  }
}
