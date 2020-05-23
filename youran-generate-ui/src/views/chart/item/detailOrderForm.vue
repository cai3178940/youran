<template>
  <el-dialog title="配置排序" :visible.sync="formVisible" width="50%">
    <el-form ref="detailOrderForm" class="detailOrderForm"
             :rules="rules" :model="form"
             label-width="120px" size="small">
      <el-form-item label="排序列">
        <el-select v-model="form.detailColumn" value-key="key"
                   style="width:100%;" placeholder="请选择排序列"
                   filterable>
          <el-option v-for="detailColumn in detailColumnList"
                     :key="detailColumn.key" :label="detailColumn._displayText"
                     :value="detailColumn">
          </el-option>
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
import {
  initFormBean,
  repairDetailOrder
} from './detailOrderModel'

export default {
  name: 'detailOrder-form',
  data () {
    return {
      edit: false,
      position: 0,
      formVisible: false,
      detailColumnList: [],
      // 最终返回给调用组件的表单数据
      form: initFormBean(),
      rules: {}
    }
  },
  methods: {
    /**
     * 显示表单窗口
     * @param detailColumnList 可选的排序列
     * @param formBean 编辑的detailOrder条件，如果新增则为空
     * @param position 当前编辑的detailOrder条件在数组中的位置
     */
    show (detailColumnList, formBean, position) {
      this.position = position
      this.detailColumnList = detailColumnList
      if (formBean) {
        this.edit = true
        this.form = formBean
      } else {
        this.edit = false
        this.form = initFormBean()
      }
      this.formVisible = true
    },
    submit () {
      repairDetailOrder(this.form, this.detailColumnList)
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
