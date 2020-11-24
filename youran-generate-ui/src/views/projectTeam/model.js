export function initFormBean (forEdit) {
  const formBean = {
    name: ''
  }
  if (forEdit) {
    formBean['teamId'] = null
  }
  return formBean
}

export function getRules () {
  return {
    name: [
      { required: true, message: '请输入名称', trigger: 'blur' },
      { max: 32, message: '长度不能超过32个字符', trigger: 'blur' }
    ]
  }
}
