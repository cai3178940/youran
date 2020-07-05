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
          <el-select v-model="form.tmp1" value-key="key"
                     style="width:100%;" placeholder="请选择过滤字段"
                     @change="handleFieldChange"
                     filterable>
            <el-option-group
              v-for="([joinIndex,entity]) in entityFieldOptions"
              :key="joinIndex"
              :label="entity.title+'('+entity.tableName+' as t'+joinIndex+')'">
              <el-option
                v-for="field in entity.fieldList"
                :key="field.fieldId"
                :label="field.fieldDesc+'(t'+joinIndex+'.'+field.fieldName+')'"
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
          <el-select v-model="form.tmp2" value-key="value"
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
        <el-form-item v-if="form.tmp2.filterValueType===1" label="过滤值">
          <!-- 枚举类型 -->
          <el-select v-if="form.tmp1.field && form.tmp1.field.dicType"
                     v-model="form.tmp3[0]" style="width:100%;">
            <el-option
              v-for="constDetail in constDetailOptions"
              :key="constDetail.constDetailId"
              :label="constDetail.detailRemark"
              :value="constDetail.detailValue">
            </el-option>
          </el-select>
          <!-- 数值类型 -->
          <el-input-number style="width:100%;" v-else-if="isNumberField"
                           v-model="form.tmp3[0]"></el-input-number>
          <!-- 其他 -->
          <el-input v-else v-model="form.tmp3[0]"></el-input>
        </el-form-item>
        <!-- 双值过滤 -->
        <el-form-item v-if="form.tmp2.filterValueType===2" label="过滤值范围">
          <el-col :span="10" class="col-left">
            <el-input-number style="width:100%;" v-if="isNumberField"
                             v-model="form.tmp4[0]"></el-input-number>
            <el-input v-else v-model="form.tmp4[0]"></el-input>
          </el-col>
          <el-col :span="4" class="col-inner" style="text-align: center;">
            <span class="text-in-form" style="color:blueviolet;"> ~ </span>
          </el-col>
          <el-col :span="10" class="col-right">
            <el-input-number style="width:100%;" v-if="isNumberField"
                             v-model="form.tmp4[1]"></el-input-number>
            <el-input v-else v-model="form.tmp4[1]"></el-input>
          </el-col>
        </el-form-item>
        <!-- 多值过滤 -->
        <el-form-item v-if="form.tmp2.filterValueType===3" label="过滤值">
          <el-select v-if="form.tmp1.field && form.tmp1.field.dicType"
                     v-model="form.tmp5" style="width:100%;"
                     multiple filterable
                     placeholder="请输入过滤值">
            <el-option
              v-for="constDetail in constDetailOptions"
              :key="constDetail.constDetailId"
              :label="constDetail.detailRemark"
              :value="constDetail.detailValue">
            </el-option>
          </el-select>
          <el-select v-else
                     v-model="form.tmp5" style="width:100%;"
                     multiple allow-create
                     filterable placeholder="请输入过滤值">
          </el-select>
        </el-form-item>
        <el-form-item v-if="form.tmp2.timeGranularity" label="时间粒度">
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
import chartFilterOperator from '@/utils/options-chart-filter-operator'
import chartTimeGranularity from '@/utils/options-chart-time-granularity'
import whereModel from './whereModel'
import constDetailMixin from '@/components/Mixins/const-detail'

export default {
  name: 'where-form',
  mixins: [constDetailMixin],
  data () {
    return {
      edit: false,
      position: 0,
      formVisible: false,
      entityFieldOptions: [],
      timeGranularityOptions: chartTimeGranularity.timeGranularityOptions,
      projectId: null,
      // 最终返回给调用组件的表单数据
      form: whereModel.initFormBean(),
      oldForm: null,
      rules: {}
    }
  },
  computed: {
    /**
     * 当前选中字段是否数字类型
     */
    isNumberField () {
      if (this.form.tmp1.field) {
        return ['Integer', 'Short', 'Long', 'Double', 'Float', 'BigDecimal']
          .indexOf(this.form.tmp1.field.jfieldType) > -1
      }
      return false
    },
    /**
     * 过滤操作符选项
     */
    filterOperatorOptions () {
      if (this.form.tmp1.field) {
        return chartFilterOperator.getFilterOperatorOptions(this.form.tmp1.field.jfieldType)
      }
      return []
    },
    /**
     * 枚举选项列表
     */
    constDetailOptions () {
      if (this.form.tmp1.field && this.form.tmp1.field.dicType) {
        return this.constDetails[this.form.tmp1.field.dicType]
      }
      return []
    }
  },
  methods: {
    handleFieldChange ({ field }) {
      if (field.dicType) {
        this.loadConstDetail(this.projectId, field.dicType)
          // 加载完以后强刷一下
          .then(() => { this.$forceUpdate() })
      }
    },
    /**
     * 显示表单窗口
     * @param entityFieldOptions 可选的where条件字段
     * @param formBean 编辑的where条件，如果新增则为空
     * @param position 当前编辑的where条件在数组中的位置
     */
    show (projectId, entityFieldOptions, formBean, position) {
      this.projectId = projectId
      this.position = position
      this.entityFieldOptions = entityFieldOptions
      if (formBean) {
        this.edit = true
        this.oldForm = formBean
        this.form = Object.assign({}, formBean)
      } else {
        this.edit = false
        this.form = whereModel.initFormBean()
      }
      const field = this.form.tmp1.field
      if (field && field.dicType) {
        this.loadConstDetail(projectId, field.dicType)
          .then(() => { this.formVisible = true })
      } else {
        this.formVisible = true
      }
    },
    submit () {
      whereModel.repairWhereForSubmit(this.form)
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
