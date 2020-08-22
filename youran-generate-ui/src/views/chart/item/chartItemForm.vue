<template>
  <el-dialog title="属性配置" :visible.sync="formVisible" width="50%">
    <el-form ref="chartItemForm"
             :rules="rules" :model="form"
             label-width="120px" size="small">
      <el-form-item label="标题">
        <help-popover name="chartItem.titleAlias">
          <el-input placeholder="请输入标题" v-model="form.titleAlias">
          </el-input>
        </help-popover>
      </el-form-item>
      <el-form-item label="字段名">
        <help-popover name="chartItem.alias">
          <el-input placeholder="请输入字段名" v-model="form.alias">
          </el-input>
        </help-popover>
      </el-form-item>
      <el-form-item v-if="visible.valuePrefix" label="内容前缀">
        <help-popover name="chartItem.valuePrefix">
          <el-input placeholder="请输入内容前缀" v-model="form.valuePrefix">
          </el-input>
        </help-popover>
      </el-form-item>
      <el-form-item v-if="visible.valueSuffix" label="内容后缀">
        <help-popover name="chartItem.valueSuffix">
          <el-input placeholder="请输入内容后缀" v-model="form.valueSuffix">
          </el-input>
        </help-popover>
      </el-form-item>
      <el-form-item v-if="visible.format" label="格式化">
        <help-popover name="chartItem.format">
          <el-input placeholder="请输入格式化" v-model="form.format">
          </el-input>
        </help-popover>
      </el-form-item>
      <el-form-item v-if="visible.seriesType" label="展示形式">
        <help-popover name="chartItem.seriesType">
          <el-select v-model="form.seriesType"
                     style="width:100%;">
            <el-option label="柱型" :value="'bar'"></el-option>
            <el-option label="折线" :value="'line'"></el-option>
            <el-option label="平滑曲线" :value="'line-smooth'"></el-option>
            <el-option label="区域堆叠" :value="'area-stack'"></el-option>
            <el-option label="柱型堆叠" :value="'bar-stack'"></el-option>
          </el-select>
        </help-popover>
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
        format: null,
        seriesType: null
      },
      visible: {
        valuePrefix: true,
        valueSuffix: true,
        format: true,
        seriesType: false
      },
      rules: {}
    }
  },
  computed: {
  },
  methods: {
    show (formBean, visible) {
      this.oldForm = formBean
      this.form = JSON.parse(JSON.stringify(formBean))
      if (visible) {
        Object.assign(this.visible, visible)
      }
      // 暂时不开启以下3项配置
      this.visible.valuePrefix = false
      this.visible.valueSuffix = false
      this.visible.format = false
      this.formVisible = true
    },
    submit () {
      this.oldForm.titleAlias = this.form.titleAlias
      this.oldForm.alias = this.form.alias
      this.oldForm.valuePrefix = this.form.valuePrefix
      this.oldForm.valueSuffix = this.form.valueSuffix
      this.oldForm.format = this.form.format
      this.oldForm.seriesType = this.form.seriesType
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
