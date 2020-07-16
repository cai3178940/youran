<template>
  <el-dialog title="布局配置" :visible.sync="formVisible"
             custom-class="layoutSettingDiv" width="50%">
    <el-form ref="layoutSettingForm"
             :model="form"
             label-width="80px" size="small">
      <el-form-item label="卡片布局">
        <el-radio-group v-model="form.showCard">
          <el-radio border :label="true">显示</el-radio>
          <el-radio border :label="false">隐藏</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="标题">
        <el-radio-group v-model="form.showTitle">
          <el-radio border :label="true">显示</el-radio>
          <el-radio border :label="false">隐藏</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submit()">确定</el-button>
        <el-button @click="cancel()">取消</el-button>
      </el-form-item>
    </el-form>
  </el-dialog>
</template>

<script>

export default {
  name: 'layout-setting-form',
  data () {
    return {
      formVisible: false,
      oldForm: null,
      // 表单数据
      form: {
        showCard: true,
        showTitle: true
      }
    }
  },
  methods: {
    show (formBean) {
      this.oldForm = formBean
      this.form = JSON.parse(JSON.stringify(formBean))
      this.formVisible = true
    },
    submit () {
      this.oldForm.showCard = this.form.showCard
      this.oldForm.showTitle = this.form.showTitle
      this.formVisible = false
      this.$emit('submit', this.oldForm)
    },
    cancel () {
      this.formVisible = false
    }
  }
}
</script>

<style lang="scss">
  .layoutSettingDiv {
    max-width: 400px;
  }
</style>
