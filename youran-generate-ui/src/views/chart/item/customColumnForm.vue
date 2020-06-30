<template>
  <el-dialog title="配置自定义列" :visible.sync="formVisible" width="50%">
    <el-form ref="customColumnForm"
             :rules="rules" :model="form"
             label-width="120px" size="small">
      <el-form-item label="自定义内容">
        <el-input type="textarea" :rows="2"
                  placeholder="请输入内容" v-model="form.customContent">
        </el-input>
      </el-form-item>
      <el-form-item label="自定义字段类型">
        <el-select v-model="form.customFieldType" placeholder="请选择"
                   style="width:100%;">
          <el-option
            v-for="option in customFieldTypeOptions"
            :key="option.value"
            :label="option.label"
            :value="option.value">
          </el-option>
        </el-select>
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
import chartCustomFieldType from '@/utils/options-chart-custom-field-type'
import detailColumnModel from './detailColumnModel'

export default {
  name: 'custom-column-form',
  data () {
    return {
      edit: false,
      position: 0,
      formVisible: false,
      customFieldTypeOptions: chartCustomFieldType.customFieldTypeOptions,
      // 最终返回给调用组件的表单数据
      form: detailColumnModel.buildCustomDetailColumn('', 1),
      oldForm: null,
      rules: {}
    }
  },
  methods: {
    /**
     * 显示表单窗口
     * @param formBean 编辑的customColumn条件，如果新增则为空
     * @param position 当前编辑的customColumn条件在数组中的位置
     */
    show (formBean, position) {
      this.position = position
      if (formBean) {
        this.edit = true
        this.oldForm = formBean
        this.form = Object.assign({}, formBean)
      } else {
        this.edit = false
        this.form = detailColumnModel.buildCustomDetailColumn('', 1)
      }
      this.formVisible = true
    },
    submit () {
      this.$emit('submit', this.position, this.form)
      this.formVisible = false
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
