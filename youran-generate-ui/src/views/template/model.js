import options from '@/utils/options'

export function initFormBean (forEdit) {
  const formBean = {
    code: '',
    name: '',
    templateVersion: '0.0.1',
    sysLowVersion: '',
    remark: ''
  }
  if (forEdit) {
    formBean['templateId'] = null
  }
  return formBean
}

export function getRules () {
  return {
    code: [
      { required: true, message: '请输入模板编码', trigger: 'blur' },
      { max: 32, message: '长度不能超过32个字符', trigger: 'blur' }
    ],
    name: [
      { required: true, message: '请输入模板名称', trigger: 'blur' },
      { max: 32, message: '长度不能超过32个字符', trigger: 'blur' }
    ],
    templateVersion: [
      { required: true, message: '请输入版本号', trigger: 'blur' },
      { max: 10, message: '长度不能超过10个字符', trigger: 'blur' },
      { validator: (rule, value, callback) => {
        if (!/^([1-9]\d|[0-9])(\.([1-9]\d|\d)){2}$/.test(value)) {
          callback(new Error('不符合版本号格式'))
        }
        callback()
      },
      trigger: 'blur' }
    ],
    sysLowVersion: [
      { required: true, message: '请输入最低系统兼容版本', trigger: 'blur' },
      { max: 10, message: '长度不能超过10个字符', trigger: 'blur' },
      { validator: (rule, value, callback) => {
        if (!/^([1-9]\d|[0-9])(\.([1-9]\d|\d)){2}$/.test(value)) {
          callback(new Error('不符合版本号格式'))
        }
        callback()
      },
      trigger: 'blur' }
    ]
  }
}

export function initTemplateFileFormBean (forEdit) {
  const formBean = {
    // 文件名
    fileName: '',
    // 文件目录
    fileDir: '',
    // 上下文类型
    contextType: options.contextType[0].value,
    // 是否抽象文件
    abstracted: false,
    // 是否二进制文件
    binary: false,
    // 文件内容
    content: ''
  }
  if (forEdit) {
    formBean['fileId'] = null
  }
  return formBean
}

export function getTemplateFileRulesRules () {
  return {
    fileName: [
      { required: true, message: '请输入文件名', trigger: 'blur' },
      { max: 100, message: '长度不能超过100个字符', trigger: 'blur' },
      {
        validator: (rule, value, callback) => {
          if (!/\.ftl$/.test(value)) {
            callback(new Error('文件名必须是.ftl结尾'))
          }
          callback()
        },
        trigger: 'blur'
      }
    ],
    fileDir: [
      { required: true, message: '请输入文件目录', trigger: 'blur' },
      { max: 300, message: '长度不能超过300个字符', trigger: 'blur' }
    ]
  }
}
