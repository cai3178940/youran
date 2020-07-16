function initFormBean (projectId) {
  const formBean = {
    name: '',
    title: '',
    module: '',
    projectId: projectId,
    layout: []
  }
  return formBean
}

function initLayout (chartId) {
  return {
    x: 0,
    y: 0,
    w: 6,
    h: 6,
    i: chartId,
    showCard: true,
    showTitle: true
  }
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
  getRules,
  initLayout
}
