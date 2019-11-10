export function initFormBean (forEdit) {
  const formBean = {
    entityId: null,
    // java字段名
    jfieldName: '',
    // mysql字段名
    fieldName: '',
    // 字段描述
    fieldDesc: '',
    // java字段类型
    jfieldType: '',
    // mysql字段类型
    fieldType: '',
    // 字段长度
    fieldLength: 0,
    // 字段精度
    fieldScale: 0,
    // 是否主键
    primaryKey: false,
    // 是否自增
    autoIncrement: false,
    // 默认值(暂时不用)
    defaultValue: '',
    // 不能为空
    notNull: false,
    // 是否外键
    foreignKey: false,
    // 外键实体id
    foreignEntityId: null,
    // 外键字段id
    foreignFieldId: null,
    // 特殊字段类型
    specialField: '',
    // 字段示例
    fieldExample: '',
    // 字段备注
    fieldComment: '',
    // 枚举字典
    dicType: '',
    // 是否查询字段
    query: false,
    // 查询方式
    queryType: null,
    // 是否新增字段
    insert: true,
    // 是否编辑字段
    update: true,
    // 是否列表字段
    list: true,
    // 是否支持排序
    listSort: false,
    // 是否详情字段
    show: true,
    // 编辑方式(暂时不用)
    editType: null,
    // 排序号
    orderNo: 1
  }
  if (forEdit) {
    formBean['fieldId'] = null
  }
  return formBean
}

export function getRules (vm) {
  return {
    jieldNameCouple: [
      {
        validator: (rule, value, callback) => {
          if (!vm.form.jfieldName) {
            callback(new Error('请输入java字段名'))
          } else if (vm.form.jfieldName.length > 50) {
            callback(new Error('java字段长度不能超过50个字符'))
          } else if (!vm.form.fieldName) {
            callback(new Error('请输入mysql字段名'))
          } else if (vm.form.fieldName.length > 64) {
            callback(new Error('mysql字段长度不能超过64个字符'))
          } else {
            callback()
          }
        },
        trigger: 'blur'
      }
    ],
    jfieldName: [
      { required: true, message: '请输入字段名', trigger: 'blur' },
      { max: 50, message: '长度不能超过50个字符', trigger: 'blur' }
    ],
    fieldName: [
      { required: true, message: '请输入mysql字段名', trigger: 'blur' },
      { max: 64, message: '长度不能超过64个字符', trigger: 'blur' }
    ],
    fieldDesc: [
      { required: true, message: '请输入字段描述', trigger: 'blur' },
      { max: 40, message: '长度不能超过40个字符', trigger: 'blur' }
    ],
    jfieldType: [
      { required: true, type: 'string', message: '请选择java字段类型', trigger: 'change' }
    ],
    fieldType: [
      { required: true, type: 'string', message: '请选择mysql字段类型', trigger: 'change' }
    ],
    fieldLength: [
      { required: true, message: '请输入字段长度', trigger: 'blur' }
    ],
    fieldScale: [
      { required: true, message: '请输入字段精度', trigger: 'blur' }
    ],
    primaryKey: [
      { required: false, type: 'boolean', message: '请选择是否主键', trigger: 'change' }
    ],
    notNull: [
      { required: false, type: 'boolean', message: '请选择不能为空', trigger: 'change' }
    ],
    foreignKey: [
      { required: true, type: 'boolean', message: '请选择是否外键', trigger: 'change' }
    ],
    fieldExample: [
      { required: true, message: '请输入字段示例', trigger: 'blur' },
      { max: 100, message: '长度不能超过100个字符', trigger: 'blur' }
    ],
    fieldComment: [
      { required: true, message: '请输入字段备注', trigger: 'blur' },
      { max: 100, message: '长度不能超过100个字符', trigger: 'blur' }
    ],
    dicType: [
      { max: 40, message: '长度不能超过40个字符', trigger: 'blur' }
    ],
    query: [
      { required: false, type: 'boolean', message: '请选择是否查询字段', trigger: 'change' }
    ],
    queryType: [
      {
        validator: (rule, value, callback) => {
          if (!value && vm.form.query) {
            callback(new Error('请选择查询方式'))
          } else {
            callback()
          }
        },
        trigger: 'change'
      }
    ],
    orderNo: [
      { required: true, message: '请输入排序号', trigger: 'blur' }
    ]
  }
}

export function initIndexFormBean (forEdit) {
  const formBean = {
    entityId: null,
    // 索引名
    indexName: '',
    // 是否唯一
    unique: false,
    // 唯一性校验
    uniqueCheck: false,
    fieldIds: []
  }
  if (forEdit) {
    formBean['indexId'] = null
  }
  return formBean
}

export function getIndexRules () {
  return {
    indexName: [
      { required: true, message: '请输入索引名', trigger: 'blur' },
      { max: 20, message: '长度不能超过20个字符', trigger: 'blur' }
    ],
    unique: [
      { required: true, type: 'boolean', message: '请选择是否唯一', trigger: 'change' }
    ],
    uniqueCheck: [
      { required: true, type: 'boolean', message: '请选择唯一性校验', trigger: 'change' }
    ],
    fieldIds: [
      { required: true, type: 'array', message: '请选择字段', trigger: 'change' }
    ]
  }
}
