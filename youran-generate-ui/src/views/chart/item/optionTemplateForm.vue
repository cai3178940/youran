<template>
  <el-dialog title="echarts配置项" :visible.sync="formVisible" width="60%">
    <el-form ref="optionTemplateForm"
             :model="form" size="small">
      <el-form-item label="option配置" label-width="80px">
        <help-popover name="chartItem.optionTemplate">
          <el-input v-model="form.optionTemplate" type="textarea"
                    :autosize="{ minRows: 10 }"></el-input>
          <el-button type="text" @click="format()">格式化</el-button>
        </help-popover>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="submit()">确定</el-button>
      <el-button @click="cancel()">取消</el-button>
    </div>
  </el-dialog>
</template>

<script>
import beautify from 'js-beautify'

export default {
  name: 'option-template-form',
  data () {
    return {
      formVisible: false,
      // 最终返回给调用组件的表单数据
      form: {
        optionTemplate: ''
      }
    }
  },
  methods: {
    /**
     * 显示表单窗口
     */
    show (optionTemplate) {
      this.form.optionTemplate = optionTemplate
      this.formVisible = true
    },
    /**
     * 格式化json
     */
    format () {
      // eslint-disable-next-line no-template-curly-in-string
      const json = this.form.optionTemplate.replace('${source}', '\'${source}\'')
        // eslint-disable-next-line no-template-curly-in-string
        .replace('${series}', '\'${series}\'')
      this.form.optionTemplate = beautify(json, { indent_size: 2 })
        // eslint-disable-next-line no-template-curly-in-string
        .replace('\'${source}\'', '${source}')
        // eslint-disable-next-line no-template-curly-in-string
        .replace('\'${series}\'', '${series}')
    },
    submit () {
      this.$emit('submit', this.form.optionTemplate)
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
