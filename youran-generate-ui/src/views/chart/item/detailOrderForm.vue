<template>
  <el-dialog title="配置排序" :visible.sync="formVisible" width="50%">
    <el-form ref="detailOrderForm"
             :rules="rules" :model="form"
             label-width="120px" size="small">
      <el-form-item label="排序列">
        <el-select v-model="form.detailColumn" value-key="key"
                   style="width:100%;" placeholder="请选择排序列"
                   filterable>
          <el-option-group label="明细列">
            <el-option v-for="detailColumn in detailColumnList"
                       :key="detailColumn.key" :label="detailColumn | displayDetailColumn"
                       :value="detailColumn">
            </el-option>
          </el-option-group>
          <el-option-group label="自定义明细列">
            <el-option v-for="customColumn in customColumnList"
                       :key="customColumn.key" :label="customColumn | displayDetailColumn"
                       :value="customColumn">
            </el-option>
          </el-option-group>
        </el-select>
      </el-form-item>
      <el-form-item label="排序方式">
        <el-radio-group v-model="form.sortType">
          <el-radio border :label="1">升序▲</el-radio>
          <el-radio border :label="2">降序▼</el-radio>
        </el-radio-group>
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
import detailOrderModel from './detailOrderModel'
import detailColumnModel from './detailColumnModel'

export default {
  name: 'detail-order-form',
  data () {
    return {
      edit: false,
      position: 0,
      formVisible: false,
      detailColumnList: [],
      customColumnList: [],
      // 最终返回给调用组件的表单数据
      form: detailOrderModel.initFormBean(),
      oldForm: null,
      rules: {}
    }
  },
  filters: {
    displayDetailColumn: detailColumnModel.displayText
  },
  methods: {
    /**
     * 显示表单窗口
     * @param detailColumnList 可选的明细列
     * @param customColumnList 可选的自定义明细列
     * @param formBean 编辑的detailOrder条件，如果新增则为空
     * @param position 当前编辑的detailOrder条件在数组中的位置
     */
    show (detailColumnList, customColumnList, formBean, position) {
      this.position = position
      this.detailColumnList = detailColumnList
      this.customColumnList = customColumnList
      if (formBean) {
        this.edit = true
        this.oldForm = formBean
        this.form = Object.assign({}, formBean)
      } else {
        this.edit = false
        this.form = detailOrderModel.initFormBean()
      }
      this.formVisible = true
    },
    submit () {
      detailOrderModel.repairDetailOrderForSubmit(this.form)
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
