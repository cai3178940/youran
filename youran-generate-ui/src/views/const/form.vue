<template>
  <div class="constFormDiv">
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/project' }">项目管理</el-breadcrumb-item>
      <el-breadcrumb-item :to="{ path: `/project/${this.projectId}/const` }">枚举管理</el-breadcrumb-item>
      <el-breadcrumb-item>
        {{edit?'编辑枚举':'添加枚举'}}
      </el-breadcrumb-item>
    </el-breadcrumb>
    <el-row type="flex" align="middle" :gutter="20">
      <el-col :span="12">
        <el-form ref="constForm" class="constForm" :rules="rules" :model="form" label-width="80px" size="small">
          <el-form-item label="项目" prop="projectId">
            <help-popover name="const.projectId">
              <el-select v-model="form.projectId" style="width:100%;" filterable placeholder="请选择项目" :disabled="true">
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
              <el-input v-model="form.constRemark" placeholder="例如：性别"></el-input>
            </help-popover>
          </el-form-item>
          <el-form-item label="枚举类名" prop="constName">
            <help-popover name="const.constName">
              <el-input v-model="form.constName" placeholder="例如：Sex"></el-input>
            </help-popover>
          </el-form-item>
          <el-form-item label="类型" prop="constType">
            <help-popover name="const.constType">
              <el-radio-group v-model="form.constType">
                <el-radio border v-for="item in constTypeOptions" :key="item.value" :label="item.value">{{item.label}}</el-radio>
              </el-radio-group>
            </help-popover>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="submit()">提交</el-button>
            <el-button v-if="edit" type="warning" @click="reset()">重置</el-button>
            <el-button @click="goBack()">返回</el-button>
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import options from '@/components/options'
import { apiPath } from '@/components/common'
import { initFormBean, getRules } from './model'

export default {
  name: 'constForm',
  props: ['projectId', 'constId'],
  data () {
    const edit = !!this.constId
    return {
      edit: edit,
      constTypeOptions: options.constTypeOptions,
      projectList: [],
      old: initFormBean(edit),
      form: initFormBean(edit),
      rules: getRules()
    }
  },
  methods: {
    queryProject () {
      return this.$common.getProjectOptions()
        .then(response => this.$common.checkResult(response))
        .then(data => { this.projectList = data })
    },
    getConst () {
      return this.$ajax.get(`/${apiPath}/meta_const/${this.constId}`)
        .then(response => this.$common.checkResult(response))
        .then(data => { this.old = data })
        .catch(error => this.$common.showNotifyError(error))
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
          if (this.edit) {
            return this.$ajax.put(`/${apiPath}/meta_const/update`, this.form)
          } else {
            return this.$ajax.post(`/${apiPath}/meta_const/save`, this.form)
          }
        })
      // 校验返回结果
        .then(response => this.$common.checkResult(response))
      // 执行页面跳转
        .then(() => {
          this.$common.showMsg('success', '操作成功')
          this.goBack()
        })
        .catch(error => this.$common.showNotifyError(error))
        .finally(() => {
          if (loading) {
            loading.close()
          }
        })
    },
    goBack () {
      this.$router.push(`/project/${this.projectId}/const`)
    }
  },
  created () {
    if (this.edit) {
      Promise.all([this.getConst(), this.queryProject()])
        .then(() => this.reset())
    } else {
      this.queryProject()
        .then(() => { this.form.projectId = parseInt(this.projectId) })
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
