<template>
  <div class="indexFormDiv">
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/project' }">项目管理</el-breadcrumb-item>
      <el-breadcrumb-item :to="{ path: `/project/${this.projectId}/entity` }">实体管理</el-breadcrumb-item>
      <el-breadcrumb-item :to="{ path: `/project/${this.projectId}/entity/${this.entityId}/field` }">字段管理</el-breadcrumb-item>
      <el-breadcrumb-item>
        {{edit?'编辑索引':'添加索引'}}
      </el-breadcrumb-item>
    </el-breadcrumb>
    <el-row type="flex" align="middle" :gutter="20">
      <el-col :span="12">
        <el-form ref="indexForm" class="indexForm" :rules="rules" :model="form" label-width="120px" size="small">
          <el-form-item label="索引名" prop="indexName">
            <help-popover name="index.indexName">
              <el-input v-model="form.indexName" placeholder="索引名，例如：IDX_ORDER_1"></el-input>
            </help-popover>
          </el-form-item>
          <el-form-item label="字段" prop="fieldIds">
            <help-popover name="index.fieldIds">
              <el-select v-model="form.fieldIds" multiple placeholder="请选择">
                <el-option
                  v-for="item in fieldList"
                  :key="item.fieldId"
                  :label="item.fieldName"
                  :value="item.fieldId">
                </el-option>
              </el-select>
            </help-popover>
          </el-form-item>
          <el-form-item label="是否唯一" prop="unique">
            <help-popover name="index.unique">
              <el-radio-group v-model="form.unique">
                <el-radio border v-for="item in boolOptions" :key="item.value" :label="item.value">{{item.label}}
                </el-radio>
              </el-radio-group>
            </help-popover>
          </el-form-item>
          <el-form-item label="唯一性校验" prop="uniqueCheck">
            <help-popover name="index.uniqueCheck">
              <el-radio-group v-model="form.uniqueCheck" :disabled="uniqueCheckDisabled">
                <el-radio border v-for="item in boolOptions" :key="item.value" :label="item.value">{{item.label}}
                </el-radio>
              </el-radio-group>
            </help-popover>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="submit()">提交</el-button>
            <el-button v-if="edit" type="warning" @click="reset()">重置</el-button>
            <el-button @click="goBack(true)">返回</el-button>
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import options from '@/components/options'
import { apiPath } from '@/components/common'
import { initIndexFormBean, getIndexRules } from './model'

export default {
  name: 'indexForm',
  props: ['projectId', 'entityId', 'indexId'],
  data () {
    const edit = !!this.indexId
    return {
      edit: edit,
      boolOptions: options.boolOptions,
      fieldList: [],
      old: initIndexFormBean(edit),
      form: initIndexFormBean(edit),
      uniqueCheckDisabled: false,
      rules: getIndexRules()
    }
  },
  watch: {
    'form.indexName' (value) {
      const lc = /[a-z]/i
      if (lc.test(value)) {
        this.form.indexName = value.toUpperCase()
      }
    },
    'form.unique' (value) {
      if (value) {
        this.form.uniqueCheck = true
        this.uniqueCheckDisabled = true
      } else {
        this.uniqueCheckDisabled = false
      }
    }
  },
  methods: {
    queryField (entityId) {
      return this.$common.getFieldOptions(entityId)
        .then(response => this.$common.checkResult(response))
        .then(data => { this.fieldList = data })
    },
    getIndex () {
      return this.$ajax.get(`/${apiPath}/meta_index/${this.indexId}`)
        .then(response => this.$common.checkResult(response))
        .then(data => {
          const old = {
            ...data
          }
          old.fieldIds = old.fields.map(field => field.fieldId)
          old.fields = null
          this.old = old
        })
        .catch(error => this.$common.showNotifyError(error))
    },
    reset () {
      for (const key in initIndexFormBean(true)) {
        this.form[key] = this.old[key]
      }
    },
    submit () {
      const params = {
        ...this.form
      }
      params.fieldIds = this.form.fieldIds.join(',')
      let loading = null
      // 校验表单
      this.$refs.indexForm.validate()
        // 提交表单
        .then(() => {
          loading = this.$loading()
          if (this.edit) {
            return this.$ajax.put(`/${apiPath}/meta_index/update`, this.$common.removeBlankField(params))
          } else {
            return this.$ajax.post(`/${apiPath}/meta_index/save`, this.$common.removeBlankField(params))
          }
        })
      // 校验返回结果
        .then(response => this.$common.checkResult(response))
      // 执行页面跳转
        .then(() => {
          this.$common.showMsg('success', '操作成功')
          this.goBack(false)
        })
        .catch(error => this.$common.showNotifyError(error))
        .finally(() => {
          if (loading) {
            loading.close()
          }
        })
    },
    goBack (preferHistory) {
      if (preferHistory && window.history.length > 1) {
        this.$router.go(-1)
      } else {
        this.$router.push(`/project/${this.projectId}/entity/${this.entityId}/field`)
      }
    }
  },
  created () {
    if (this.edit) {
      Promise.all([this.getIndex(), this.queryField(this.entityId)])
        .then(() => this.reset())
    } else {
      this.form.entityId = parseInt(this.entityId)
      const fieldIds = this.$router.currentRoute.query.fieldIds
      const indexName = this.$router.currentRoute.query.indexName
      if (fieldIds) {
        this.form.fieldIds = fieldIds.split('-').map(value => parseInt(value))
      }
      if (indexName) {
        this.form.indexName = indexName
      }
      this.queryField(this.form.entityId)
    }
  }
}
</script>

<style lang="scss">
  @import '../../assets/common.scss';
  .indexFormDiv .indexForm {
    @include youran-form;
  }

</style>
