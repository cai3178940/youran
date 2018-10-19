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
      label: 'INT'
    },
    {
      value: 'varchar',
      label: 'VARCHAR'
    },
    {
      value: 'text',
      label: 'TEXT'
    },
    {
      value: 'datetime',
      label: 'DATETIME'
    },
    {
      value: 'float',
      label: 'FLOAT'
    },
    {
      value: 'double',
      label: 'DOUBLE'
    },
    {
      value: 'decimal',
      label: 'DECIMAL'
    },
    {
      value: 'bigint',
      label: 'BIGINT'
    },
    {
      value: 'smallint',
      label: 'SMALLINT'
    },
    {
      value: 'tinyint',
      label: 'TINYINT'
    }
  ],
  /**
   * 根据字段类型判断是否需要显示长度
   * @param fieldType
   * @returns {boolean}
   */
  showFieldLength: function (fieldType) {
    return fieldType !== 'datetime' && fieldType !== 'text'
  },
  /**
   * 根据字段类型判断是否需要显示精度
   * @param fieldType
   * @returns {boolean}
   */
  showFieldScale: function (fieldType) {
    return fieldType === 'decimal' || fieldType === 'double' || fieldType === 'float'
  },

  jfieldTypeOptions: [
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
      value: 'String',
      label: 'String'
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
    {
      constName: 'BoolConst'
    }
  ]

}
