<template>
  <el-dialog title="配置列属性" :visible.sync="formVisible" width="50%">
    <el-form ref="chartItemForm"
             :rules="rules" :model="form"
             label-width="120px" size="small">
      <el-form-item label="列名">
        <el-input placeholder="请输入列名" v-model="form.titleAlias">
        </el-input>
      </el-form-item>
      <el-form-item label="字段名">
        <el-input placeholder="请输入字段名" v-model="form.alias">
        </el-input>
      </el-form-item>
      <el-form-item label="内容前缀">
        <el-input placeholder="请输入内容前缀" v-model="form.valuePrefix">
        </el-input>
      </el-form-item>
      <el-form-item label="内容后缀">
        <el-input placeholder="请输入内容后缀" v-model="form.valueSuffix">
        </el-input>
      </el-form-item>
      <el-form-item label="格式化">
        <el-input placeholder="请输入格式化" v-model="form.format">
        </el-input>
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
  name: 'chart-item-form',
  data () {
    return {
      formVisible: false,
      oldForm: null,
      // 表单数据
      form: {
        sourceItemId: null,
        titleAlias: null,
        alias: null,
        valuePrefix: null,
        valueSuffix: null,
        format: null
      },
      rules: {}
    }
  },
  computed: {
  },
  methods: {
    show (formBean) {
      this.oldForm = formBean
      this.form = JSON.parse(JSON.stringify(formBean))
      this.formVisible = true
    },
    submit () {
      this.oldForm.titleAlias = this.form.titleAlias
      this.oldForm.alias = this.form.alias
      this.oldForm.valuePrefix = this.form.valuePrefix
      this.oldForm.valueSuffix = this.form.valueSuffix
      this.oldForm.format = this.form.format
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
</style>
