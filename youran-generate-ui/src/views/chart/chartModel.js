function getRules () {
  return {
    projectId: [
      { required: true, type: 'number', message: '请选择项目', trigger: 'change' }
    ],
    sourceId: [
      { required: true, message: 'sourceId不能为空', trigger: 'blur' }
    ],
    chartName: [
      { required: true, message: '请输入图表名称', trigger: 'blur' },
      { max: 64, message: '长度不能超过64个字符', trigger: 'blur' },
      {
        validator: (rule, value, callback) => {
          if (!/^[A-Z](\w*(_|\$)*\w*)*$/.test(value)) {
            callback(new Error('不符合类名规范'))
          }
          callback()
        },
        trigger: 'blur'
      }
    ],
    module: [
      { max: 50, message: '长度不能超过50个字符', trigger: 'blur' }
    ],
    title: [
      { required: true, message: '请输入图表标题', trigger: 'blur' },
      { max: 64, message: '长度不能超过64个字符', trigger: 'blur' }
    ]
  }
}

export default {
  getRules
}
