export function initFormBean (forEdit) {
  const formBean = {
    groupId: '',
    projectName: '',
    projectDesc: '',
    packageName: '',
    author: '',
    templateId: null,
    templateId2: null,
    templateId3: null,
    remote: false,
    remoteUrl: null,
    remoteUrl2: null,
    remoteUrl3: null,
    username: '',
    password: '',
    feature: {
      bootVersion: 2,
      lombokEnabled: false
    },
    labels: []
  }
  if (forEdit) {
    formBean['projectId'] = null
  }
  return formBean
}

export function getRules () {
  return {
    groupId: [
      { required: true, message: '请输入groupId', trigger: 'blur' },
      { max: 50, message: '长度不能超过50个字符', trigger: 'blur' }
    ],
    projectName: [
      { required: true, message: '请输入项目名称', trigger: 'blur' },
      { max: 50, message: '长度不能超过50个字符', trigger: 'blur' },
      {
        validator: (rule, value, callback) => {
          if (!/^[a-z|-]+$/.test(value)) {
            callback(new Error('项目名称不合法,只允许小写字母和横杠'))
          }
          callback()
        },
        trigger: 'blur'
      }
    ],
    projectDesc: [
      { required: true, message: '请输入项目名称', trigger: 'blur' },
      { max: 100, message: '长度不能超过100个字符', trigger: 'blur' }
    ],
    packageName: [
      { required: true, message: '请输入包名', trigger: 'blur' },
      { max: 100, message: '长度不能超过100个字符', trigger: 'blur' }
    ],
    author: [
      { required: true, message: '请输入作者', trigger: 'blur' },
      { max: 50, message: '长度不能超过50个字符', trigger: 'blur' }
    ],
    remote: [
      { required: false, type: 'boolean', message: '请选择是否启用', trigger: 'change' }
    ],
    templateId: [
      { required: true, type: 'number', message: '请选择模板', trigger: 'change' }
    ],
    templateId2: [
      { required: true, type: 'number', message: '请选择模板2', trigger: 'change' }
    ],
    templateId3: [
      { required: true, type: 'number', message: '请选择模板3', trigger: 'change' }
    ],
    remoteUrl: [
      { max: 256, message: '长度不能超过256个字符', trigger: 'blur' }
    ],
    username: [
      { max: 32, message: '长度不能超过32个字符', trigger: 'blur' }
    ],
    password: [
      { max: 32, message: '长度不能超过32个字符', trigger: 'blur' }
    ],
    feature: {
      bootVersion: [
        { required: true, type: 'number', message: '请选择spring-boot版本', trigger: 'change' }
      ]
    }
  }
}
