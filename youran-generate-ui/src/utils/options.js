function getFieldTypeOptions () {
  return [
    {
      value: 'int',
      label: 'INT',
      selectLabel: 'INT(11)',
      fieldLength: 11,
      disabled: false
    },
    {
      value: 'varchar',
      label: 'VARCHAR',
      selectLabel: 'VARCHAR',
      fieldLength: 32,
      disabled: false
    },
    {
      value: 'text',
      label: 'TEXT',
      selectLabel: 'TEXT',
      fieldLength: 0,
      disabled: false
    },
    {
      value: 'date',
      label: 'DATE',
      selectLabel: 'DATE',
      fieldLength: 0,
      disabled: false
    },
    {
      value: 'datetime',
      label: 'DATETIME',
      selectLabel: 'DATETIME',
      fieldLength: 0,
      disabled: false
    },
    {
      value: 'float',
      label: 'FLOAT',
      selectLabel: 'FLOAT',
      fieldLength: null,
      disabled: false
    },
    {
      value: 'double',
      label: 'DOUBLE',
      selectLabel: 'DOUBLE',
      fieldLength: null,
      disabled: false
    },
    {
      value: 'decimal',
      label: 'DECIMAL',
      selectLabel: 'DECIMAL',
      fieldLength: 20,
      disabled: false
    },
    {
      value: 'bigint',
      label: 'BIGINT',
      selectLabel: 'BIGINT(20)',
      fieldLength: 20,
      disabled: false
    },
    {
      value: 'mediumint',
      label: 'MEDIUMINT',
      selectLabel: 'MEDIUMINT(9)',
      fieldLength: 9,
      disabled: false
    },
    {
      value: 'smallint',
      label: 'SMALLINT',
      selectLabel: 'SMALLINT(6)',
      fieldLength: 6,
      disabled: false
    },
    {
      value: 'tinyint',
      label: 'TINYINT',
      selectLabel: 'TINYINT(4)',
      fieldLength: 4,
      disabled: false
    }
  ]
}

/**
 * 获取所有编辑框选项，根据传入的数组，设置选项是否可选
 */
function getEditTypeOptions (allowedItems) {
  const options = {
    'TEXT': {
      label: '文本框',
      value: 1,
      disabled: true
    },
    'SELECT': {
      label: '单选下拉框',
      value: 2,
      disabled: true
    },
    'DATE': {
      label: '日期框',
      value: 3,
      disabled: true
    },
    'NUMBER': {
      label: '数字框',
      value: 4,
      disabled: true
    },
    'RADIO': {
      label: '单选按钮',
      value: 5,
      disabled: true
    },
    /* 'CHECKBOX': {
      label: '复选框',
      value: 6,
      disabled: true
    }, */
    'DATETIME': {
      label: '日期时间框',
      value: 7,
      disabled: true
    },
    /* 'MULTI_SELECT': {
      label: '多选下拉框',
      value: 8,
      disabled: true
    }, */
    'TEXTAREA': {
      label: '多行文本框',
      value: 9,
      disabled: true
    }
  }
  for (const item of allowedItems) {
    options[item].disabled = false
  }
  return options
}

/**
 * 获取所有搜索条件选项，根据传入的数组，设置选项是否可选
 */
function getQueryTypeOptions (allowedItems) {
  const options = {
    'EQ': {
      value: 1,
      label: '等于',
      disabled: true
    },
    'LIKE': {
      value: 2,
      label: 'like',
      disabled: true
    },
    'GT': {
      value: 3,
      label: '大于',
      disabled: true
    },
    'GE': {
      value: 4,
      label: '大于等于',
      disabled: true
    },
    'LT': {
      value: 5,
      label: '小于',
      disabled: true
    },
    'LE': {
      value: 6,
      label: '小于等于',
      disabled: true
    },
    'BETWEEN': {
      value: 7,
      label: 'between',
      disabled: true
    },
    'IN': {
      value: 8,
      label: 'in',
      disabled: true
    }
  }
  for (const item of allowedItems) {
    options[item].disabled = false
  }
  return options
}

const commonFeature = {
  value: 'default',
  label: '普通字段',
  icon: 'common',
  iconClassName: '',
  hiddenAttrs: ['autoIncrement', 'foreignKey'],
  disabledAttrs: []
}

const pkFeature = {
  value: 'pk',
  label: '主键',
  icon: 'key',
  iconClassName: 'color-warning',
  hiddenAttrs: ['foreignKey', 'dicType', 'attributes'],
  disabledAttrs: ['notNull']
}

const fkFeature = {
  value: 'fk',
  label: '外键',
  icon: 'key',
  iconClassName: 'color-primary',
  hiddenAttrs: ['autoIncrement', 'dicType'],
  disabledAttrs: []
}

const specialFieldFeatures = [
  {
    value: 'deleted',
    label: '逻辑删除',
    icon: 'delete',
    iconClassName: 'color-danger',
    hiddenAttrs: ['autoIncrement', 'foreignKey', 'dicType', 'query', 'attributes'],
    disabledAttrs: ['notNull']
  },
  {
    value: 'createdTime',
    label: '创建时间',
    icon: 'time',
    iconClassName: 'color-success',
    hiddenAttrs: ['autoIncrement', 'foreignKey', 'dicType'],
    disabledAttrs: ['attr-insert', 'attr-update']
  },
  {
    value: 'createdBy',
    label: '创建人员',
    icon: 'create-user',
    iconClassName: 'color-success',
    hiddenAttrs: ['autoIncrement', 'foreignKey', 'dicType'],
    disabledAttrs: ['attr-insert', 'attr-update']
  },
  {
    value: 'operatedTime',
    label: '更新时间',
    icon: 'time',
    iconClassName: 'color-primary',
    hiddenAttrs: ['autoIncrement', 'foreignKey', 'dicType'],
    disabledAttrs: ['attr-insert', 'attr-update']
  },
  {
    value: 'operatedBy',
    label: '更新人员',
    icon: 'edit-user',
    iconClassName: 'color-primary',
    hiddenAttrs: ['autoIncrement', 'foreignKey', 'dicType'],
    disabledAttrs: ['attr-insert', 'attr-update']
  },
  {
    value: 'version',
    label: '乐观锁版本号',
    icon: 'version',
    iconClassName: 'color-primary',
    hiddenAttrs: ['autoIncrement', 'foreignKey', 'dicType', 'query', 'attributes'],
    disabledAttrs: ['notNull']
  }
]

// 将上面的数组转成map
const specialFieldFeaturesMap = {}
for (const option of specialFieldFeatures) {
  specialFieldFeaturesMap[option.value] = option
}

export default {

  // 布尔枚举
  boolOptions: [
    {
      value: true,
      label: '是'
    },
    {
      value: false,
      label: '否'
    }
  ],
  /**
   * 获取新的字段类型
   */
  getFieldTypeOptions () {
    return getFieldTypeOptions()
  },
  /**
   * 获取字段类型常量
   */
  fieldTypeOptions: getFieldTypeOptions(),
  /**
   * 根据字段类型判断是否需要显示长度
   * @param fieldType
   * @param fieldLength
   * @returns {boolean}
   */
  showFieldLength (fieldType, fieldLength) {
    if (fieldType === 'date' || fieldType === 'datetime' || fieldType === 'text') {
      return false
    }
    return fieldLength > 0
  },
  /**
   * 根据字段类型判断是否需要显示精度
   * @param fieldType
   * @returns {boolean}
   */
  showFieldScale (fieldType) {
    return fieldType === 'decimal' || fieldType === 'double' || fieldType === 'float'
  },
  /**
   * java字段类型列表
   */
  jfieldTypeOptions: [
    {
      value: 'String',
      label: 'String',
      defaultFieldType: 'varchar',
      allowFieldTypes: ['varchar', 'text']
    },
    {
      value: 'Integer',
      label: 'Integer',
      defaultFieldType: 'int',
      allowFieldTypes: ['int', 'mediumint', 'smallint', 'tinyint']
    },
    {
      value: 'Short',
      label: 'Short',
      defaultFieldType: 'smallint',
      allowFieldTypes: ['smallint']
    },
    {
      value: 'Long',
      label: 'Long',
      defaultFieldType: 'bigint',
      allowFieldTypes: ['bigint']
    },
    {
      value: 'Date',
      label: 'Date',
      defaultFieldType: 'datetime',
      allowFieldTypes: ['date', 'datetime']
    },
    {
      value: 'Double',
      label: 'Double',
      defaultFieldType: 'double',
      allowFieldTypes: ['double', 'decimal']
    },
    {
      value: 'Float',
      label: 'Float',
      defaultFieldType: 'float',
      allowFieldTypes: ['float', 'decimal']
    },
    {
      value: 'Boolean',
      label: 'Boolean',
      defaultFieldType: 'tinyint',
      allowFieldTypes: ['tinyint']
    },
    {
      value: 'BigDecimal',
      label: 'BigDecimal',
      defaultFieldType: 'decimal',
      allowFieldTypes: ['decimal']
    }
  ],
  /**
   * 查询方式列表
   */
  queryTypeOptions: getQueryTypeOptions([]),
  /**
   * 识别允许设置的查询条件
   */
  getAllowedQueryTypes (field) {
    let allowed = []
    if (field.foreignKey) {
      // 外键： 只能等于、in
      allowed = ['EQ', 'IN']
    } else if (field.dicType) {
      // 枚举： 只能等于、in
      allowed = ['EQ', 'IN']
    } else if (field.jfieldType === 'Boolean') {
      // Boolean: 只能等于
      allowed = ['EQ']
    } else if (field.jfieldType === 'Date') {
      // 日期类型： 除like、in以外
      allowed = ['EQ', 'GT', 'GE', 'LT', 'LE', 'BETWEEN']
    } else if (['Integer', 'Short', 'Long', 'Double', 'Float', 'BigDecimal']
      .find((item) => field.jfieldType === item)) {
      // Integer Short Long Double Float BigDecimal：除like、in以外
      allowed = ['EQ', 'GT', 'GE', 'LT', 'LE', 'BETWEEN']
    } else {
      // 字符串： 等于、like
      allowed = ['EQ', 'LIKE']
    }
    return getQueryTypeOptions(allowed)
  },
  commonFeature: commonFeature,
  pkFeature: pkFeature,
  fkFeature: fkFeature,
  /**
   * 获取字段特性
   */
  getFieldFeature (field) {
    // 主键也作为特性返回
    if (field.primaryKey) {
      return pkFeature
    } else if (field.foreignKey) {
    // 外键也作为特性返回
      return fkFeature
    } else if (field.specialField) {
      const spFeature = specialFieldFeaturesMap[field.specialField]
      if (spFeature) {
        return spFeature
      }
    }
    return commonFeature
  },

  /**
   * 字段特性列表
   */
  specialFieldFeatures: specialFieldFeatures,
  constTypeOptions: [
    {
      value: 1,
      label: '整型'
    },
    {
      value: 2,
      label: '字符串'
    }
  ],
  /**
   * 所有编辑框选项
   */
  editTypeOptions: getEditTypeOptions([]),
  /**
   * 识别允许设置的编辑框类型
   */
  getAllowedEditTypes (field) {
    let allowed = []
    if (field.foreignKey) {
      // 外键： 只能下拉框
      allowed = ['SELECT']
    } else if (field.dicType) {
      // 枚举： 下拉框 单选按钮
      allowed = ['SELECT', 'RADIO']
    } else if (field.jfieldType === 'Boolean') {
      // Boolean: 单选框
      allowed = ['RADIO']
    } else if (field.jfieldType === 'Date') {
      // 日期类型： 日期框 日期时间框
      allowed = ['DATE', 'DATETIME']
    } else if (['Integer', 'Short', 'Long', 'Double', 'Float', 'BigDecimal']
      .find((item) => field.jfieldType === item)) {
      // Integer Short Long Double Float BigDecimal : 数字框 文本框
      allowed = ['TEXT', 'NUMBER']
    } else {
      // 字符串： 文本框 多行文本框
      allowed = ['TEXT', 'TEXTAREA']
    }
    return getEditTypeOptions(allowed)
  },
  /**
   * 模板文件上下文类型
   */
  contextType: [
    {
      value: 1,
      label: '全局'
    },
    {
      value: 2,
      label: '实体'
    },
    {
      value: 3,
      label: '枚举'
    }
  ],
  /**
   * 模板文件类型
   */
  templateFileType: [
    {
      value: 1,
      label: '普通模板文件'
    },
    {
      value: 2,
      label: '抽象模板文件'
    },
    {
      value: 3,
      label: '二进制模板文件'
    },
    {
      value: 4,
      label: '父路径渲染文件'
    },
    {
      value: 5,
      label: '文件名渲染文件'
    }
  ]

}
