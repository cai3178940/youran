<template>
  <el-dialog class="labelDialog" title="扩展属性"
             :visible.sync="formVisible" width="40%">
    <el-form ref="labelForm"
             :rules="rules" :model="form"
             label-width="60px" size="small">
      <el-form-item label="key" prop="key">
        <el-select v-model="form.key" placeholder="请选择key"
                   style="width:100%;"
                   @change="onKeyChange" filterable>
          <el-option
            v-for="metaLabel in metaLabels"
            :key="metaLabel.key"
            :label="metaLabel | displayMetaLabel"
            :value="metaLabel.key">
          </el-option>
        </el-select>
        <div style="line-height: 14px;"><span style="color: #5558fa;font-size: 10px;">{{desc}}</span></div>
      </el-form-item>
      <el-form-item v-if="mode !== 'none'" label="value" prop="value">
        <el-select v-if="mode === 'select'"
                   v-model="form.value" placeholder="请选择value"
                   style="width:100%;" filterable>
          <el-option
            v-for="item in candidate"
            :key="item"
            :label="item"
            :value="item">
          </el-option>
        </el-select>
        <el-input v-if="mode === 'any'"
                  v-model="form.value" placeholder="请输入value"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submit()">确定</el-button>
        <el-button v-if="edit" type="danger" @click="remove()">删除</el-button>
        <el-button @click="close()">取消</el-button>
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
      metaLabels: null,
      desc: '',
      mode: 'none',
      candidate: [],
      rules: {
        key: [
          { required: true, message: '请填写key', trigger: 'change' }
        ],
        value: [
          { required: true, message: '请输入value', trigger: 'change' }
        ]
      }
    }
  },
  filters: {
    displayMetaLabel (metaLabel) {
      if (metaLabel.name) {
        return `${metaLabel.key}(${metaLabel.name})`
      }
      return metaLabel.key
    }
  },
  methods: {
    /**
     * 显示表单窗口
     * @param formBean   编辑的label，如果新增则为空
     * @param position   当前编辑的label在数组中的位置
     */
    show (formBean, position) {
      this.position = position
      if (formBean) {
        this.edit = true
        this.form = Object.assign({}, formBean)
      } else {
        this.edit = false
        this.form = initFormBean()
      }
      this.onKeyChange()
      this.formVisible = true
      this.$nextTick(() => {
        this.$refs.labelForm.clearValidate()
      })
    },
    onKeyChange () {
      const metaLabel = this.metaLabels.find(v => v.key === this.form.key)
      if (metaLabel) {
        this.desc = metaLabel.desc
        this.candidate = metaLabel.candidate
        this.mode = metaLabel.mode
      } else {
        this.desc = ''
        this.candidate = []
        this.mode = 'none'
      }
    },
    submit () {
      this.$refs.labelForm.validate()
        .then(() => {
          this.$emit('submit', this.position, this.form)
        })
    },
    /**
     * 加载扩展标签
     * @param projectId  项目id
     * @param templateId 模板id
     * @param labelType  类型
     */
    loadMetaLabel ({ projectId, templateId, labelType }, callback) {
      labelApi.findMetaLabel(projectId, templateId, labelType)
        .then(data => {
          this.metaLabels = data
          callback(data)
        })
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
    },
    close () {
      this.formVisible = false
    }
  }
}
</script>

<style lang="scss">
  .labelDialog .el-dialog__body {
    padding: 20px 50px 20px 30px;
  }
</style>
