function initFormBean (projectId) {
  const formBean = {
    name: '',
    title: '',
    module: '',
    projectId: null,
    layout: [
      { 'x': 0, 'y': 0, 'w': 4, 'h': 4, 'i': '0', title: 'MyAggTable' }
    ]
  }
  return formBean
}

function getRules () {
  return {
    projectId: [
      { required: true, type: 'number', message: '请选择项目', trigger: 'change' }
    ],
    name: [
      { required: true, message: '请输入看板名称', trigger: 'blur' },
      { max: 64, message: '长度不能超过64个字符', trigger: 'blur' }
    ],
    module: [
      { max: 50, message: '长度不能超过50个字符', trigger: 'blur' }
    ],
    title: [
      { required: true, message: '请输入看板标题', trigger: 'blur' },
      { max: 64, message: '长度不能超过64个字符', trigger: 'blur' }
    ]
  }
}

export default {
  initFormBean,
  getRules
}
