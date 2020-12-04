<template>
  <el-dialog title="扩展属性" :visible.sync="formVisible" width="40%">
    <el-form ref="labelForm"
             :rules="rules" :model="form"
             label-width="120px" size="small">
      <el-form-item label="key" prop="key">
        <el-autocomplete v-model="form.key" :fetch-suggestions="findKeySuggestions">
        </el-autocomplete>
      </el-form-item>
      <el-form-item label="value" prop="value">
        <el-autocomplete v-model="form.value" :fetch-suggestions="findValueSuggestions">
        </el-autocomplete>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submit()">确定</el-button>
        <el-button v-if="edit" type="danger" @click="remove()">删除</el-button>
        <el-button @click="cancel()">取消</el-button>
      </el-form-item>
    </el-form>
  </el-dialog>
</template>

<script>

import labelApi from '@/api/label'

function initFormBean () {
  return {
    key: '',
    value: ''
  }
}

export default {
  name: 'label-form',
  data () {
    return {
      edit: false,
      position: 0,
      formVisible: false,
      // 最终返回给调用组件的表单数据
      form: initFormBean(),
      projectId: null,
      templateId: null,
      labelType: null,
      metaLabels: null,
      rules: {
        key: [
          { required: true, message: '请填写key', trigger: 'change' }
        ]
      }
    }
  },
  methods: {
    /**
     * 显示表单窗口
     * @param projectId  项目id
     * @param templateId 模板id
     * @param labelType  类型
     * @param formBean   编辑的label，如果新增则为空
     * @param position   当前编辑的label在数组中的位置
     */
    show ({ projectId, templateId, labelType }, formBean, position) {
      this.projectId = projectId
      if (this.templateId !== templateId) {
        this.templateId = templateId
        this.metaLabels = null
      }
      this.labelType = labelType
      this.position = position
      if (formBean) {
        this.edit = true
        this.form = Object.assign({}, formBean)
      } else {
        this.edit = false
        this.form = initFormBean()
      }
      this.formVisible = true
      this.$nextTick(() => {
        this.$refs.labelForm.clearValidate()
      })
    },
    submit () {
      this.$refs.labelForm.validate()
        .then(() => {
          this.$emit('submit', this.position, this.form)
          this.formVisible = false
        })
    },
    findKeySuggestions (queryString, cb) {
      const action = () => {
        const results = this.metaLabels.slice(0)
        cb(results.map(c => ({ value: c.key })))
      }
      if (this.metaLabels) {
        action()
      } else {
        labelApi.findMetaLabel(this.projectId, this.templateId, this.labelType)
          .then(data => {
            this.metaLabels = data
            action()
          })
      }
    },
    findValueSuggestions (queryString, cb) {
      let foundCandidate = false
      if (this.form.key && this.metaLabels) {
        const metaLabel = this.metaLabels.find(c => c.key === this.form.key)
        if (metaLabel && metaLabel.candidate) {
          foundCandidate = true
          cb(metaLabel.candidate.map(v => ({ value: v })))
        }
      }
      if (!foundCandidate) {
        // eslint-disable-next-line standard/no-callback-literal
        cb([])
      }
    },
    remove () {
      this.$emit('remove', this.position, this.form)
      this.formVisible = false
    },
    cancel () {
      this.formVisible = false
    }
  }
}
</script>

<style lang="scss">
</style>
