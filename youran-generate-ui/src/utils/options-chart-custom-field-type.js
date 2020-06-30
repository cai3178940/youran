const customFieldTypeOptions = [
  {
    value: 1,
    jfieldType: 'String',
    label: '字符串'
  },
  {
    value: 2,
    jfieldType: 'Integer',
    label: '整形'
  },
  {
    value: 3,
    jfieldType: 'Double',
    label: '浮点型'
  },
  {
    value: 4,
    jfieldType: 'Date',
    label: '日期'
  },
  {
    value: 5,
    jfieldType: 'Date',
    label: '日期时间'
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
