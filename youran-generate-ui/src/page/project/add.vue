<template>
  <div class="projectAdd">
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/project' }">项目管理</el-breadcrumb-item>
      <el-breadcrumb-item>添加</el-breadcrumb-item>
    </el-breadcrumb>

    <el-row type="flex" align="middle" :gutter="20">
      <el-col :span="12" >
        <el-form ref="addForm" class="addForm" :rules="rules" :model="form" label-width="80px">
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
  export default {
    name: 'projectAdd',
    data: function () {
      return {
        form: {
          projectName: '',
          packageName: '',
          author: ''
        },
        rules: {
          projectName: [
            {required: true, message: '请输入项目名称', trigger: 'blur'},
            {max: 50, message: '长度不能超过50个字符', trigger: 'blur'}
          ],
          packageName: [
            {required: true, message: '请输入包名', trigger: 'blur'},
            {max: 100, message: '长度不能超过100个字符', trigger: 'blur'}
          ],
          author: [
            {required: true, message: '请输入作者', trigger: 'blur'},
            {max: 50, message: '长度不能超过50个字符', trigger: 'blur'}
          ]
        }
      }
    },
    methods: {
      submit: function () {
        //校验表单
        this.$refs.addForm.validate()
          //提交表单
          .then(()=>this.$ajax.post('/generate/meta_project/save', this.form))
          //校验返回结果
          .then(response => this.$common.checkResult(response.data))
          //执行页面跳转
          .then(()=>{
            this.$common.showMsg('success', '添加成功')
            this.goBack()
          })
          .catch(error=> this.$common.showNotifyError(error))
      },
      goBack: function () {
        this.$router.push('/project')
      }
    },
    created: function () {

    }
  }
</script>

<style>
  .projectAdd .addForm {
    padding: 30px 50px;
  }

</style>
