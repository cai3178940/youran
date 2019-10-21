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
      disabled: false
    },
    {
      value: 'text',
      label: 'TEXT',
      selectLabel: 'TEXT',
      disabled: false
    },
    {
      value: 'date',
      label: 'DATE',
      selectLabel: 'DATE',
      disabled: false
    },
    {
      value: 'datetime',
      label: 'DATETIME',
      selectLabel: 'DATETIME',
      disabled: false
    },
    {
      value: 'float',
      label: 'FLOAT',
      selectLabel: 'FLOAT',
      disabled: false
    },
    {
      value: 'double',
      label: 'DOUBLE',
      selectLabel: 'DOUBLE',
      disabled: false
    },
    {
      value: 'decimal',
      label: 'DECIMAL',
      selectLabel: 'DECIMAL',
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

const commonFeature = {
  value: 'default',
  label: '普通字段',
  icon: 'asterisk',
  style: 'color: #87CEEB;',
  hiddenAttrs: ['foreignKey'],
  disabledAttrs: []
}

const pkFeature = {
  value: 'pk',
  label: '主键',
  icon: 'key',
  style: 'color: #eb9e05;',
  hiddenAttrs: ['foreignKey', 'dicType', 'attributes'],
  disabledAttrs: []
}

const fkFeature = {
  value: 'fk',
  label: '外键',
  icon: 'key',
  style: 'color: #409EFF;',
  hiddenAttrs: ['dicType', 'query'],
  disabledAttrs: []
}

const specialFieldFeatures = [
  {
    value: 'deleted',
    label: '逻辑删除',
    icon: 'ban',
    style: 'color: #ff3366;',
    hiddenAttrs: ['foreignKey', 'dicType', 'query', 'attributes'],
    disabledAttrs: []
  },
  {
    value: 'createdTime',
    label: '创建时间',
    icon: 'calendar-alt',
    style: 'color: #33cc66;',
    hiddenAttrs: ['foreignKey', 'dicType'],
    disabledAttrs: ['attr-insert', 'attr-update']
  },
  {
    value: 'createdBy',
    label: '创建人员',
    icon: 'user',
    style: 'color: #33cc66;',
    hiddenAttrs: ['foreignKey', 'dicType'],
    disabledAttrs: ['attr-insert', 'attr-update']
  },
  {
    value: 'operatedTime',
    label: '更新时间',
    icon: 'calendar-alt',
    style: 'color: #1C86EE;',
    hiddenAttrs: ['foreignKey', 'dicType'],
    disabledAttrs: ['attr-insert', 'attr-update']
  },
  {
    value: 'operatedBy',
    label: '更新人员',
    icon: 'user',
    style: 'color: #1C86EE;',
    hiddenAttrs: ['foreignKey', 'dicType'],
    disabledAttrs: ['attr-insert', 'attr-update']
  },
  {
    value: 'version',
    label: '乐观锁版本号',
    icon: 'unlock-alt',
    style: 'color: #AB82FF;',
    hiddenAttrs: ['foreignKey', 'dicType', 'query', 'attributes'],
    disabledAttrs: []
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
      value: 1,
      label: '是'
    },
    {
      value: 0,
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
  queryTypeOptions: [
    {
      value: 1,
      label: '等于'
    },
    {
      value: 2,
      label: 'like'
    },
    {
      value: 3,
      label: '大于'
    },
    {
      value: 4,
      label: '大于等于'
    },
    {
      value: 5,
      label: '小于'
    },
    {
      value: 6,
      label: '小于等于'
    },
    {
      value: 7,
      label: 'between'
    },
    {
      value: 8,
      label: 'in'
    }
  ],
  commonFeature: commonFeature,
  /**
   * 获取字段特性
   */
  getFieldFeatures (field) {
    const features = []
    // 主键也作为特性返回
    if (field.primaryKey === 1) {
      features.push(pkFeature)
    } else if (field.foreignKey === 1) {
    // 外键也作为特性返回
      features.push(fkFeature)
    } else if (field.specialField) {
      const spFeature = specialFieldFeaturesMap[field.specialField]
      if (spFeature) {
        features.push(spFeature)
      }
    }
    if (!features.length) {
      features.push(commonFeature)
    }
    return features
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

  // 默认常量列表
  defaultConstList: [
    // {
    //   constName: 'BoolConst'
    // }
  ]

}
