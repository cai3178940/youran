<template>
  <el-dialog title="echarts配置项" :visible.sync="formVisible" width="60%">
    <el-form ref="optionTemplateForm"
             :model="form" size="small">
      <el-form-item label="option配置" label-width="80px">
        <help-popover name="chartItem.optionTemplate">
          <el-input v-model="form.optionTemplate" type="textarea"
                    :autosize="{ minRows: 10 }"></el-input>
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
