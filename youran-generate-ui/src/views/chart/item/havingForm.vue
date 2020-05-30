<template>
  <el-dialog title="配置过滤" :visible.sync="formVisible" width="50%">
    <el-form ref="havingForm"
             :rules="rules" :model="form"
             label-width="120px" size="small">
      <el-form-item label="过滤列">
        <el-select v-model="form.metrics" value-key="key"
                   style="width:100%;" placeholder="请选择过滤列"
                   filterable>
          <el-option v-for="detailColumn in metricsList"
                     :key="detailColumn.key" :label="detailColumn._displayText"
                     :value="detailColumn">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="过滤运算符">
        <el-select v-model="tmp.tmp2" value-key="value"
                   style="width:100%;">
          <el-option
            v-for="option in filterOperatorOptions"
            :key="option.value"
            :label="option.label"
            :value="option">
          </el-option>
        </el-select>
      </el-form-item>
      <!-- 单值过滤 -->
      <el-form-item v-if="tmp.tmp2.filterValueType===1" label="过滤值">
        <el-input-number style="width:100%;" v-if="isNumberField"
                         v-model="tmp.tmp3[0]"></el-input-number>
        <el-input v-else v-model="tmp.tmp3[0]"></el-input>
      </el-form-item>
      <!-- 双值过滤 -->
      <el-form-item v-if="tmp.tmp2.filterValueType===2" label="过滤值范围">
        <el-col :span="10" class="col-left">
          <el-input-number style="width:100%;" v-if="isNumberField"
                           v-model="tmp.tmp4[0]"></el-input-number>
          <el-input v-else v-model="tmp.tmp4[0]"></el-input>
        </el-col>
        <el-col :span="4" class="col-inner" style="text-align: center;">
          <span class="text-in-form" style="color:blueviolet;"> ~ </span>
        </el-col>
        <el-col :span="10" class="col-right">
          <el-input-number style="width:100%;" v-if="isNumberField"
                           v-model="tmp.tmp4[1]"></el-input-number>
          <el-input v-else v-model="tmp.tmp4[1]"></el-input>
        </el-col>
      </el-form-item>
      <!-- 多值过滤 -->
      <el-form-item v-if="tmp.tmp2.filterValueType===3" label="过滤值">
        <el-select v-model="tmp.tmp5" style="width:100%;"
                   multiple allow-create
                   filterable placeholder="请输入过滤值">
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
import chartOptions from '@/utils/options-chart'
import havingModel from './havingModel'

export default {
  name: 'having-form',
  data () {
    return {
      edit: false,
      position: 0,
      formVisible: false,
      metricsList: [],
      // 最终返回给调用组件的表单数据
      form: havingModel.initFormBean(),
      // 临时数据
      tmp: havingModel.initTmp(),
      rules: {}
    }
  },
  computed: {
    /**
     * 当前选中字段是否数字类型
     */
    isNumberField () {
      if (this.form.metrics.field) {
        return ['Integer', 'Short', 'Long', 'Double', 'Float', 'BigDecimal']
          .indexOf(this.form.metrics.field.jfieldType) > -1
      }
      return false
    },
    /**
     * 过滤操作符选项
     */
    filterOperatorOptions () {
      if (this.form.metrics && this.form.metrics.field) {
        return chartOptions.getFilterOperatorOptions(this.form.metrics.field.jfieldType)
      }
      return []
    }
  },
  methods: {
    /**
     * 显示表单窗口
     * @param metricsList 可选的指标
     * @param formBean 编辑的having条件，如果新增则为空
     * @param position 当前编辑的having条件在数组中的位置
     */
    show (metricsList, formBean, position) {
      this.tmp = havingModel.initTmp()
      this.position = position
      this.metricsList = metricsList
      if (formBean) {
        this.edit = true
        this.form = formBean
        havingModel.formToTmp(this.form, this.tmp)
      } else {
        this.edit = false
        this.form = havingModel.initFormBean()
      }
      this.formVisible = true
    },
    submit () {
      havingModel.tmpToForm(this.tmp, this.form)
      havingModel.repairHaving(this.form, this.metricsList)
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
