<template>
  <div class="projectShow">
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/project' }">项目管理</el-breadcrumb-item>
      <el-breadcrumb-item>查看</el-breadcrumb-item>
    </el-breadcrumb>
    <el-row type="flex" align="middle" :gutter="20">
      <el-col :span="12">
        <el-form ref="showForm" class="showForm" :model="form" label-width="120px">
          <el-form-item label="项目名称" prop="projectName">
            <el-input v-model="form.projectName" :disabled="true"></el-input>
          </el-form-item>
          <el-form-item label="包名" prop="packageName">
            <el-input v-model="form.packageName" :disabled="true"></el-input>
          </el-form-item>
          <el-form-item label="作者" prop="author">
            <el-input v-model="form.author" :disabled="true"></el-input>
          </el-form-item>
          <el-form-item label="启用Git仓库">
            <help-popover name="project.remote">
              <el-radio-group v-model="form.remote" :disabled="true">
                <el-radio border v-for="item in boolOptions" :key="item.value" :label="item.value">{{item.label}}
                </el-radio>
              </el-radio-group>
            </help-popover>
          </el-form-item>
          <template v-if="form.remote==1">
            <el-form-item label="Git仓库地址">
              <help-popover name="project.remoteUrl">
                <el-input v-model="form.remoteUrl" placeholder="例如：https://github.com/github/testrepo.git" :disabled="true"></el-input>
              </help-popover>
            </el-form-item>
            <el-form-item label="Git用户名">
              <help-popover name="project.username">
                <el-input v-model="form.username" placeholder="例如：zhangsan" :disabled="true"></el-input>
              </help-popover>
            </el-form-item>
          </template>
          <el-form-item>
            <el-button @click="goBack()">返回</el-button>
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>
  </div>
</template>

<script>
  import options from '@/components/options.js'
  //项目模型
  const projectModel = {
    projectId: null,
    projectName: '',
    packageName: '',
    author: '',
    remote: 0,
    remoteUrl: '',
    username: '',
    password: ''
  }

  export default {
    name: 'projectShow',
    props: ['projectId'],
    data: function () {
      return {
        boolOptions: options.boolOptions,
        form: {
          ...projectModel
        }
      }
    },
    methods: {
      getProject: function () {
        return this.$ajax.get(`/generate/meta_project/${this.projectId}`)
          .then(response => this.$common.checkResult(response.data))
          .then(result => this.form = result.data)
          .catch(error => this.$common.showNotifyError(error))
      },
      goBack: function () {
        this.$router.push('/project')
      }
    },
    created: function () {
      this.getProject()
    }
  }
</script>

<style>
  .projectShow .showForm {
    padding: 30px 50px;
  }

</style>
