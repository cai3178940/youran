export function initFormBean (forEdit) {
  const formBean = {
    projectId: null,
    constRemark: '',
    constName: '',
    constType: ''
  }
  if (forEdit) {
    formBean['constId'] = null
  }
  return formBean
}

export function getRules () {
  return {
    projectId: [
      { required: true, type: 'number', message: '请选择项目', trigger: 'change' }
    ],
    constRemark: [
      { required: true, message: '请输入枚举名称', trigger: 'blur' },
      { max: 100, message: '长度不能超过100个字符', trigger: 'blur' }
    ],
    constName: [
      { required: true, message: '请输入枚举类名', trigger: 'blur' },
      { max: 20, message: '长度不能超过20个字符', trigger: 'blur' }
    ],
    constType: [
      { required: true, type: 'number', message: '请选择类型', trigger: 'change' }
    ]
  }
}

export function initDetailFormBean (forEdit) {
  const formBean = {
    constId: null,
    // 枚举字段名
    detailName: '',
    // 枚举值
    detailValue: '',
    // 值描述
    detailRemark: '',
    // 是否编辑
    editFlag: true
  }
  if (forEdit) {
    formBean['constDetailId'] = null
  }
  return formBean
}

export function getDetailRules () {
  return {
    detailName: [
      { required: true, message: '请输入枚举字段名', trigger: 'blur' },
      { max: 50, message: '长度不能超过50个字符', trigger: 'blur' }
    ],
    detailValue: [
      { required: true, message: '请输入枚举值', trigger: 'blur' },
      { max: 50, message: '长度不能超过50个字符', trigger: 'blur' }
    ],
    detailRemark: [
      { required: true, message: '请输入值描述', trigger: 'blur' },
      { max: 100, message: '长度不能超过100个字符', trigger: 'blur' }
    ]
  }
}
