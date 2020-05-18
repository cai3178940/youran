<template>
  <el-dialog title="配置过滤" :visible.sync="formVisible" width="50%">
    <el-form ref="whereForm" class="whereForm"
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
              v-for="([joinIndex,entity]) in whereOptions"
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
        <el-button @click="cancel()">取消</el-button>
      </el-form-item>
    </el-form>
  </el-dialog>
</template>

<script>
import chartOptions from '@/utils/options-chart'

function initFormBean () {
  return {
    joinIndex: null,
    fieldId: null,
    filterOperator: null,
    filterValue: null,
    timeGranularity: null,
    custom: false,
    customContent: null
  }
}

function initTmp () {
  return {
    // 过滤字段下拉框绑定对象
    tmp1: {
      key: '',
      field: null,
      joinIndex: null
    },
    // 过滤操作符下拉框绑定对象
    tmp2: {
      value: null,
      label: null,
      filterValueType: null,
      matchFieldTypes: [],
      timeGranularity: false
    },
    // 单过滤值绑定对象
    tmp3: [null],
    // 双过滤值绑定对象
    tmp4: [null, null],
    // 多过滤值绑定对象
    tmp5: []
  }
}

export default {
  name: 'where-form',
  data () {
    return {
      formVisible: false,
      whereOptions: [],
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
     * 实体字段选项
     */
    filterOperatorOptions () {
      if (this.tmp.tmp1.field) {
        return chartOptions.getFilterOperatorOptions(this.tmp.tmp1.field.jfieldType)
      }
      return []
    }
  },
  methods: {
    show (whereOptions, formBean) {
      this.whereOptions = whereOptions
      this.tmp = initTmp()
      if (formBean) {
        this.form = formBean
        this.resetTmp()
      } else {
        this.form = initFormBean()
      }
      this.formVisible = true
    },
    /**
     * 重置临时数据
     */
    resetTmp () {
      if (!this.form.custom) {
        const [joinIndex, entity] = this.whereOptions.find(
          ([joinIndex, entity]) => joinIndex === this.form.joinIndex)
        const field = entity.fieldList.find(field => field.fieldId === this.form.fieldId)
        this.tmp.tmp1 = {
          key: joinIndex + '' + field.fieldId,
          field: field,
          joinIndex: joinIndex
        }
        this.tmp.tmp2 = chartOptions.getFilterOperatorOption(this.form.filterOperator)
        if (this.tmp.tmp2.filterValueType === 1) {
          this.tmp.tmp3 = this.form.filterValue
        } else if (this.tmp.tmp2.filterValueType === 2) {
          this.tmp.tmp4 = this.form.filterValue
        } else if (this.tmp.tmp2.filterValueType === 3) {
          this.tmp.tmp5 = this.form.filterValue
        }
      }
    },
    submit () {
      // TODO
    },
    cancel () {
      this.formVisible = false
    }
  }
}
</script>

<style lang="scss">
</style>
