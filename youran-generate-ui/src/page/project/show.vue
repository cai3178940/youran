<template>
  <div class="projectShow">
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/project' }">项目管理</el-breadcrumb-item>
      <el-breadcrumb-item>查看</el-breadcrumb-item>
    </el-breadcrumb>
    <el-row type="flex" align="middle" :gutter="20">
      <el-col :span="12">
        <el-form ref="showForm" class="showForm" :model="form" label-width="80px">
          <el-form-item label="项目名称" prop="projectName">
            <el-input v-model="form.projectName" :readonly="true"></el-input>
          </el-form-item>
          <el-form-item label="包名" prop="packageName">
            <el-input v-model="form.packageName" :readonly="true"></el-input>
          </el-form-item>
          <el-form-item label="作者" prop="author">
            <el-input v-model="form.author" :readonly="true"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button @click="goBack()">返回</el-button>
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>
  </div>
</template>

<script>

  //项目模型
  const projectModel = {
    projectId: null,
    projectName: '',
    packageName: '',
    author: ''
  }

  export default {
    name: 'projectShow',
    props: ['projectId'],
    data: function () {
      return {
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
