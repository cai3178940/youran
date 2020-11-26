<template>
  <div class="projectTeamFormDiv">
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/team' }">项目组管理</el-breadcrumb-item>
      <el-breadcrumb-item>
        {{edit?'编辑项目组':'创建项目组'}}
      </el-breadcrumb-item>
    </el-breadcrumb>
    <el-row type="flex" align="middle" :gutter="20">
      <el-col :span="12">
        <el-form ref="projectTeamForm" class="projectTeamForm"
                 :rules="rules" :model="form"
                 label-width="120px" size="small" v-loading="formLoading">
          <el-form-item label="项目组名称" prop="name">
            <help-popover>
              <el-input v-model="form.name" placeholder="例如：xxx开发组" tabindex="10"></el-input>
            </help-popover>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="submit()" tabindex="60">提交</el-button>
            <el-button v-if="edit" type="warning" @click="reset()" tabindex="70">重置</el-button>
            <el-button @click="goBack(true)" tabindex="80">返回</el-button>
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import projectTeamApi from '@/api/team/projectTeam'
import { initFormBean, getRules } from './model'

export default {
  name: 'projectTeamForm',
  props: ['teamId'],
  data () {
    const edit = !!this.teamId
    return {
      edit: edit,
      formLoading: false,
      old: initFormBean(edit),
      form: initFormBean(edit),
      rules: getRules()
    }
  },
  methods: {
    getTemplate () {
      this.formLoading = true
      return projectTeamApi.get(this.teamId)
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
      this.$refs.projectTeamForm.validate()
        // 提交表单
        .then(() => {
          loading = this.$loading()
          return projectTeamApi.saveOrUpdate(this.form, this.edit)
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
        this.$router.push('/team')
      }
    }
  },
  created () {
    if (this.edit) {
      this.getTemplate().then(() => this.reset())
    }
  }
}
</script>

<style lang="scss">
  @import '../../assets/common.scss';
  .projectTeamFormDiv .projectTeamForm {
    @include youran-form;
  }

</style>
