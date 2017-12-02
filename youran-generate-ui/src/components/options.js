export default {
  //布尔枚举
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
    }
  ],

  jfieldTypeOptions:[
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
  queryTypeOptions:[
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
  specialFieldOptions:[
    {
      value: 'delSign',
      label: '逻辑删除'
    },
    {
      value: 'createDate',
      label: '创建时间'
    },
    {
      value: 'createBy',
      label: '创建人员'
    },
    {
      value: 'operateDate',
      label: '更新时间'
    },
    {
      value: 'operateBy',
      label: '更新人员'
    },
    {
      value: 'version',
      label: '乐观锁版本'
    }
  ],

}
