<template>
  <el-dialog title="配置指标" :visible.sync="formVisible" width="50%">
    <el-form ref="metricsForm"
             :rules="rules" :model="form"
             label-width="120px" size="small">
      <el-form-item label="自定义指标">
        <el-switch v-model="form.custom"></el-switch>
      </el-form-item>
      <template v-if="form.custom">
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
      </template>
      <template v-else>
        <el-form-item label="指标字段">
          <el-select v-model="tmp.tmp1" value-key="key"
                     style="width:100%;" placeholder="请选择指标字段"
                     filterable>
            <el-option-group
              v-for="([joinIndex,entity]) in entityFieldOptions"
              :key="joinIndex"
              :label="entity.title+'('+entity.tableName+')'">
              <el-option
                v-for="field in entity.fieldList"
                :key="field.fieldId"
                :label="field.fieldDesc+'('+field.fieldName+')'"
                :value="{
                  key: joinIndex+'_'+field.fieldId,
                  field: field,
                  joinIndex: joinIndex
                }">
              </el-option>
            </el-option-group>
          </el-select>
        </el-form-item>
        <el-form-item label="聚合函数">
          <el-select v-model="tmp.tmp2" value-key="value"
                     style="width:100%;">
            <el-option
              v-for="option in aggFunctionOptions"
              :key="option.value"
              :label="option.label"
              :value="option">
            </el-option>
          </el-select>
        </el-form-item>
      </template>
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
import metricsModel from './metricsModel'

export default {
  name: 'metrics-form',
  data () {
    return {
      edit: false,
      position: 0,
      formVisible: false,
      entityFieldOptions: [],
      customFieldTypeOptions: chartOptions.customFieldTypeOptions,
      // 最终返回给调用组件的表单数据
      form: metricsModel.initFormBean(),
      // 临时数据
      tmp: metricsModel.initTmp(),
      rules: {}
    }
  },
  computed: {
    /**
     * 指标操作符选项
     */
    aggFunctionOptions () {
      if (this.tmp.tmp1.field) {
        return chartOptions.getAggFunctionOptions(this.tmp.tmp1.field.jfieldType)
      }
      return []
    }
  },
  methods: {
    /**
     * 显示表单窗口
     * @param entityFieldOptions 可选的metrics字段
     * @param formBean 编辑的metrics，如果新增则为空
     * @param position 当前编辑的metrics在数组中的位置
     */
    show (entityFieldOptions, formBean, position) {
      this.tmp = metricsModel.initTmp()
      this.position = position
      this.entityFieldOptions = entityFieldOptions
      this.tmp = metricsModel.initTmp()
      if (formBean) {
        this.edit = true
        this.form = formBean
        metricsModel.formToTmp(this.form, this.tmp, entityFieldOptions)
      } else {
        this.edit = false
        this.form = metricsModel.initFormBean()
      }
      this.formVisible = true
    },
    submit () {
      metricsModel.tmpToForm(this.tmp, this.form)
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
