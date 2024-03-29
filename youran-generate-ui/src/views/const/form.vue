<template>
  <div class="constFormDiv">
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/project' }">项目管理</el-breadcrumb-item>
      <el-breadcrumb-item :to="{ path: `/project/${this.projectId}/const` }">枚举管理</el-breadcrumb-item>
      <el-breadcrumb-item>
        {{edit?'编辑枚举':'创建枚举'}}
      </el-breadcrumb-item>
    </el-breadcrumb>
    <el-row type="flex" align="middle" :gutter="20">
      <el-col :span="12">
        <el-form ref="constForm" class="constForm"
                 :rules="rules" :model="form"
                 label-width="80px" size="small" v-loading="formLoading">
          <el-form-item label="项目" prop="projectId">
            <help-popover name="const.projectId">
              <el-select v-model="form.projectId" class="red-font"
                         style="width:100%;" filterable :disabled="true">
                <el-option
                  v-for="item in projectList"
                  :key="item.projectId"
                  :label="item.projectDesc"
                  :value="item.projectId">
                </el-option>
              </el-select>
            </help-popover>
          </el-form-item>
          <el-form-item label="枚举名称" prop="constRemark">
            <help-popover name="const.constRemark">
              <el-input v-model="form.constRemark"
                        maxlength="100" show-word-limit
                        placeholder="例如：性别" tabindex="10"></el-input>
            </help-popover>
          </el-form-item>
          <el-form-item label="枚举类名" prop="constName">
            <help-popover name="const.constName">
              <el-input v-upper-case-first v-model="form.constName"
                        maxlength="20" show-word-limit
                        placeholder="例如：Sex" tabindex="20"></el-input>
            </help-popover>
          </el-form-item>
          <el-form-item label="类型" prop="constType">
            <help-popover name="const.constType">
              <el-radio-group v-model="form.constType">
                <!-- 解决bug:el-radio-group下第一个el-radio的tabindex一直等于0 -->
                <el-radio style="display: none;"></el-radio>
                <el-radio border v-for="item in constTypeOptions"
                          :key="item.value"
                          :tabindex="30+item.value"
                          :label="item.value">{{item.label}}</el-radio>
              </el-radio-group>
            </help-popover>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="submit()" tabindex="40">提交</el-button>
            <el-button v-if="edit" type="warning" @click="reset()" tabindex="50">重置</el-button>
            <el-button @click="goBack(true)" tabindex="60">返回</el-button>
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import options from '@/utils/options'
import projectApi from '@/api/project'
import constApi from '@/api/const'
import { initFormBean, getRules } from './model'

export default {
  name: 'constForm',
  props: [
    'projectId',
    'constId',
    'constName',
    'constType',
    'constRemark'
  ],
  data () {
    const edit = !!this.constId
    return {
      edit: edit,
      constTypeOptions: options.constTypeOptions,
      projectList: [],
      formLoading: false,
      old: initFormBean(edit),
      form: initFormBean(edit),
      rules: getRules()
    }
  },
  methods: {
    queryProject () {
      this.formLoading = true
      return projectApi.getList()
        .then(data => { this.projectList = data })
        .finally(() => { this.formLoading = false })
    },
    getConst () {
      this.formLoading = true
      return constApi.get(this.constId)
        .then(data => { this.old = data })
        .catch(error => this.$common.showNotifyError(error))
        .finally(() => { this.formLoading = false })
    },
    reset () {
      for (const key in initFormBean(true)) {
        this.form[key] = this.old[key]
      }
    },
    submit () {
      let loading = null
      // 校验表单
      this.$refs.constForm.validate()
        // 提交表单
        .then(() => {
          loading = this.$loading()
          return constApi.saveOrUpdate(this.form, this.edit)
        })
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
        this.$router.push(`/project/${this.projectId}/const`)
      }
    }
  },
  created () {
    if (this.edit) {
      Promise.all([this.getConst(), this.queryProject()])
        .then(() => this.reset())
    } else {
      this.queryProject()
        .then(() => {
          this.form.projectId = parseInt(this.projectId)
          if (this.constName) {
            this.form.constName = this.constName
          }
          if (this.constType) {
            this.form.constType = parseInt(this.constType)
          }
          if (this.constRemark) {
            this.form.constRemark = this.constRemark
          }
        })
    }
  }
}
</script>

<style lang="scss">
  @import '../../assets/common.scss';
  .constFormDiv .constForm {
    @include youran-form;
  }

</style>
