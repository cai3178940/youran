<template>
  <el-dialog title="配置过滤" :visible.sync="formVisible" width="50%">
    <el-form ref="whereForm"
             :rules="rules" :model="form"
             label-width="120px" size="small">
      <el-form-item label="自定义过滤条件">
        <el-switch v-model="form.custom"></el-switch>
      </el-form-item>
      <template v-if="form.custom">
        <el-form-item label="自定义内容">
          <el-input type="textarea" :rows="2"
                    placeholder="请输入内容" v-model="form.customContent">
          </el-input>
        </el-form-item>
      </template>
      <template v-else>
        <el-form-item label="过滤字段">
          <el-select v-model="tmp.tmp1" value-key="key"
                     style="width:100%;" placeholder="请选择过滤字段"
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
        <el-form-item v-if="tmp.tmp2.timeGranularity" label="时间粒度">
          <el-select v-model="form.timeGranularity"
                     style="width:100%;">
            <el-option
              v-for="option in timeGranularityOptions"
              :key="option.value"
              :label="option.label"
              :value="option.value">
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
import {
  initFormBean,
  initTmp,
  formToTmp,
  tmpToForm
} from './whereModel'

export default {
  name: 'where-form',
  data () {
    return {
      edit: false,
      position: 0,
      formVisible: false,
      entityFieldOptions: [],
      timeGranularityOptions: chartOptions.timeGranularityOptions,
      // 最终返回给调用组件的表单数据
      form: initFormBean(),
      // 临时数据
      tmp: initTmp(),
      rules: {}
    }
  },
  computed: {
    /**
     * 当前选中字段是否数字类型
     */
    isNumberField () {
      if (this.tmp.tmp1.field) {
        return ['Integer', 'Short', 'Long', 'Double', 'Float', 'BigDecimal']
          .indexOf(this.tmp.tmp1.field.jfieldType) > -1
      }
      return false
    },
    /**
     * 过滤操作符选项
     */
    filterOperatorOptions () {
      if (this.tmp.tmp1.field) {
        return chartOptions.getFilterOperatorOptions(this.tmp.tmp1.field.jfieldType)
      }
      return []
    }
  },
  methods: {
    /**
     * 显示表单窗口
     * @param entityFieldOptions 可选的where条件字段
     * @param formBean 编辑的where条件，如果新增则为空
     * @param position 当前编辑的where条件在数组中的位置
     */
    show (entityFieldOptions, formBean, position) {
      this.position = position
      this.entityFieldOptions = entityFieldOptions
      this.tmp = initTmp()
      if (formBean) {
        this.edit = true
        this.form = formBean
        formToTmp(this.form, this.tmp, entityFieldOptions)
      } else {
        this.edit = false
        this.form = initFormBean()
      }
      this.formVisible = true
    },
    submit () {
      tmpToForm(this.tmp, this.form)
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
