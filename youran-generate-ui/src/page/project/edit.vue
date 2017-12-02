<template>
  <div class="projectEdit">
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/project' }">项目管理</el-breadcrumb-item>
      <el-breadcrumb-item>编辑</el-breadcrumb-item>
    </el-breadcrumb>
    <el-row type="flex" align="middle" :gutter="20">
      <el-col :span="12">
        <el-form ref="editForm" class="editForm" :rules="rules" :model="form" label-width="80px">
          <el-form-item label="项目名称" prop="projectName">
            <el-input v-model="form.projectName" placeholder="例如：bbs"></el-input>
          </el-form-item>
          <el-form-item label="包名" prop="packageName">
            <el-input v-model="form.packageName" placeholder="例如：com.cbb.bbs"></el-input>
          </el-form-item>
          <el-form-item label="作者" prop="author">
            <el-input v-model="form.author" placeholder="例如：菜小哥"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="submit()">提交</el-button>
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
    name: 'projectEdit',
    props: ['projectId'],
    data: function () {
      return {
        old: {
          ...projectModel
        },
        form: {
          ...projectModel
        },
        rules: {
          projectName: [
            {required: true, message: '请输入项目名称', trigger: 'blur'},
            {min: 1, max: 50, message: '长度在 1 到 50 个字符', trigger: 'blur'}
          ],
          packageName: [
            {required: true, message: '请输入包名', trigger: 'blur'},
            {min: 1, max: 100, message: '长度在 1 到 100 个字符', trigger: 'blur'}
          ],
          author: [
            {required: true, message: '请输入作者', trigger: 'blur'},
            {min: 1, max: 50, message: '长度在 1 到 50 个字符', trigger: 'blur'}
          ]
        }
      }
    },
    methods: {
      getProject: function () {
        return this.$ajax.get(`/generate/meta_project/${this.projectId}`)
          .then(response => this.$common.checkResult(response.data))
          .then(result => this.old = result.data)
          .catch(error => this.$common.showNotifyError(error))
      },
      reset: function () {
        for (const key in projectModel) {
          this.form[key] = this.old[key]
        }
      },
      submit: function () {
        //校验表单
        this.$refs.editForm.validate()
        //提交表单
          .then(() => this.$ajax.put('/generate/meta_project/update', this.form))
          //校验返回结果
          .then(response => this.$common.checkResult(response.data))
          //执行页面跳转
          .then(() => {
            this.$common.showMsg('success', '修改成功')
            this.goBack()
          })
          .catch(error => this.$common.showNotifyError(error))
      },
      goBack: function () {
        this.$router.push('/project')
      }
    },
    created: function () {
      this.getProject().then(() => this.reset())
    }
  }
</script>

<style>
  .projectEdit .editForm {
    padding: 30px 50px;
  }

</style>
