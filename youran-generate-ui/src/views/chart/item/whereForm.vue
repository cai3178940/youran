<template>
  <el-dialog title="配置过滤" :visible.sync="formVisible" width="60%">
    <el-form ref="whereForm" class="whereForm"
             :rules="rules" :model="form"
             label-width="100px" size="small">
      <el-form-item label="过滤字段">
        <el-select v-model="form.tmp" value-key="key"
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
                fieldId: field.fieldId,
                joinIndex: joinIndex
              }">
            </el-option>
          </el-option-group>
        </el-select>
      </el-form-item>
      <el-form-item label="过滤运算符">
        <el-select v-model="form.filterOperator"
                   style="width:100%;">
          <el-option
            v-for="option in filterOperatorOptions"
            :key="option.value"
            :label="option.label"
            :value="option.value">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submit()" tabindex="40">确定</el-button>
        <el-button @click="cancel()" tabindex="60">取消</el-button>
      </el-form-item>
    </el-form>
  </el-dialog>
</template>

<script>
import chartOptions from '@/utils/options-chart'

export default {
  name: 'where-form',
  props: [
    'old',
    'whereOptions'
  ],
  data () {
    return {
      formVisible: false,
      filterOperatorOptions: chartOptions.filterOperatorOptions,
      form: {
        filterOperator: null,
        tmp: {
          key: ''
        }
      },
      rules: {}
    }
  },
  methods: {
    show () {
      this.formVisible = true
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
