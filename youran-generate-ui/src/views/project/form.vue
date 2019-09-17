<template>
  <div class="projectFormDiv">
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/project' }">项目管理</el-breadcrumb-item>
      <el-breadcrumb-item>
        {{edit?'编辑项目':'添加项目'}}
      </el-breadcrumb-item>
    </el-breadcrumb>
    <el-row type="flex" align="middle" :gutter="20">
      <el-col :span="12">
        <el-form ref="projectForm" class="projectForm" :rules="rules" :model="form" label-width="120px" size="small">
          <el-form-item label="groupId" prop="groupId">
            <help-popover name="project.groupId">
              <el-input v-model="form.groupId" placeholder="例如：com.myGroup"></el-input>
            </help-popover>
          </el-form-item>
          <el-form-item label="项目标识" prop="projectName">
            <help-popover name="project.projectName">
              <el-input v-model="form.projectName" placeholder="例如：bbs"></el-input>
            </help-popover>
          </el-form-item>
          <el-form-item label="项目名称" prop="projectDesc">
            <help-popover name="project.projectDesc">
              <el-input v-model="form.projectDesc" placeholder="例如：论坛"></el-input>
            </help-popover>
          </el-form-item>
          <el-form-item label="包名" prop="packageName">
            <help-popover name="project.packageName">
              <el-input v-model="form.packageName" placeholder="例如：com.cbb.bbs"></el-input>
            </help-popover>
          </el-form-item>
          <el-form-item label="作者" prop="author">
            <help-popover name="project.author">
              <el-input v-model="form.author" placeholder="例如：菜小哥"></el-input>
            </help-popover>
          </el-form-item>
          <el-form-item label="spring-boot版本" prop="feature.bootVersion">
            <help-popover name="project.feature.bootVersion">
              <el-radio-group v-model="form.feature.bootVersion">
                <el-radio border :label="1">1.5.x</el-radio>
                <el-radio border :label="2">2.1.x</el-radio>
              </el-radio-group>
            </help-popover>
          </el-form-item>
          <el-form-item label="启用Git仓库" prop="remote">
            <help-popover name="project.remote">
              <el-switch v-model="form.remote"
                         :active-value="1"
                         :inactive-value="0">
              </el-switch>
            </help-popover>
          </el-form-item>
          <template v-if="form.remote==1">
            <el-form-item label="Git仓库地址" prop="remoteUrl">
              <help-popover name="project.remoteUrl">
                <el-input v-model="form.remoteUrl" placeholder="例如：https://github.com/github/testrepo.git"></el-input>
              </help-popover>
            </el-form-item>
            <el-form-item label="Git用户名" prop="username">
              <help-popover name="project.username">
                <el-input v-model="form.username" placeholder="例如：zhangsan"></el-input>
              </help-popover>
            </el-form-item>
            <el-form-item label="Git密码/token" prop="password">
              <help-popover name="project.password">
                <el-input v-model="form.password" placeholder="例如：123456"></el-input>
              </help-popover>
            </el-form-item>
          </template>
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
  name: 'projectForm',
  props: ['projectId'],
  data () {
    const edit = !!this.projectId
    return {
      edit: edit,
      boolOptions: options.boolOptions,
      old: initFormBean(edit),
      form: initFormBean(edit),
      rules: getRules()
    }
  },
  methods: {
    getProject () {
      return this.$ajax.get(`/${apiPath}/meta_project/${this.projectId}`)
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
      this.$refs.projectForm.validate()
        // 提交表单
        .then(() => {
          loading = this.$loading()
          if (this.edit) {
            return this.$ajax.put(`/${apiPath}/meta_project/update`, this.form)
          } else {
            return this.$ajax.post(`/${apiPath}/meta_project/save`, this.form)
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
      this.$router.push('/project')
    }
  },
  created () {
    if (this.edit) {
      this.getProject().then(() => this.reset())
    }
  }
}
</script>

<style lang="scss">
  @import '../../assets/common.scss';
  .projectFormDiv .projectForm {
    @include youran-form;
  }

</style>
