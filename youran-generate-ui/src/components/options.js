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

  fieldTypeOptions: [
    {
      value: 'int',
      label: 'INT',
      selectLabel: 'INT(11)',
      fieldLength: 11
    },
    {
      value: 'varchar',
      label: 'VARCHAR',
      selectLabel: 'VARCHAR'
    },
    {
      value: 'text',
      label: 'TEXT',
      selectLabel: 'TEXT'
    },
    {
      value: 'datetime',
      label: 'DATETIME',
      selectLabel: 'DATETIME'
    },
    {
      value: 'float',
      label: 'FLOAT',
      selectLabel: 'FLOAT'
    },
    {
      value: 'double',
      label: 'DOUBLE',
      selectLabel: 'DOUBLE'
    },
    {
      value: 'decimal',
      label: 'DECIMAL',
      selectLabel: 'DECIMAL'
    },
    {
      value: 'bigint',
      label: 'BIGINT',
      selectLabel: 'BIGINT(20)',
      fieldLength: 20
    },
    {
      value: 'mediumint',
      label: 'MEDIUMINT',
      selectLabel: 'MEDIUMINT(9)',
      fieldLength: 9
    },
    {
      value: 'smallint',
      label: 'SMALLINT',
      selectLabel: 'SMALLINT(6)',
      fieldLength: 6
    },
    {
      value: 'tinyint',
      label: 'TINYINT',
      selectLabel: 'TINYINT(4)',
      fieldLength: 4
    }
  ],
  /**
   * 根据字段类型判断是否需要显示长度
   * @param fieldType
   * @returns {boolean}
   */
  showFieldLength (fieldType) {
    return fieldType !== 'datetime' && fieldType !== 'text'
  },
  /**
   * 根据字段类型判断是否需要显示精度
   * @param fieldType
   * @returns {boolean}
   */
  showFieldScale (fieldType) {
    return fieldType === 'decimal' || fieldType === 'double' || fieldType === 'float'
  },

  jfieldTypeOptions: [
    {
      value: 'String',
      label: 'String'
    },
    {
      value: 'Integer',
      label: 'Integer'
    },
    {
      value: 'Short',
      label: 'Short'
    },
    {
      value: 'Long',
      label: 'Long'
    },
    {
      value: 'Date',
      label: 'Date'
    },
    {
      value: 'Double',
      label: 'Double'
    },
    {
      value: 'Float',
      label: 'Float'
    },
    {
      value: 'Boolean',
      label: 'Boolean'
    },
    {
      value: 'BigDecimal',
      label: 'BigDecimal'
    }
  ],
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
    }
  ],
  specialFieldOptions: [
    {
      value: 'deleted',
      label: '逻辑删除'
    },
    {
      value: 'createdTime',
      label: '创建时间'
    },
    {
      value: 'createdBy',
      label: '创建人员'
    },
    {
      value: 'operatedTime',
      label: '更新时间'
    },
    {
      value: 'operatedBy',
      label: '更新人员'
    },
    {
      value: 'version',
      label: '乐观锁版本'
    }
  ],
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
