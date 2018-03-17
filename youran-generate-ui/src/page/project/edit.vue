<template>
  <div class="projectEdit">
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/project' }">项目管理</el-breadcrumb-item>
      <el-breadcrumb-item>编辑</el-breadcrumb-item>
    </el-breadcrumb>
    <el-row type="flex" align="middle" :gutter="20">
      <el-col :span="12">
        <el-form ref="editForm" class="editForm" :rules="rules" :model="form" label-width="120px">
          <el-form-item label="项目名称" prop="projectName">
            <help-popover name="project.projectName">
              <el-input v-model="form.projectName" placeholder="例如：bbs"></el-input>
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
          <el-form-item label="启用Git仓库" prop="remote">
            <help-popover name="project.remote">
              <el-radio-group v-model="form.remote">
                <el-radio border v-for="item in boolOptions" :key="item.value" :label="item.value">{{item.label}}
                </el-radio>
              </el-radio-group>
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
            <el-form-item label="Git密码" prop="password">
              <help-popover name="project.password">
                <el-input v-model="form.password" placeholder="例如：123456"></el-input>
              </help-popover>
            </el-form-item>
          </template>
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
    name: 'projectEdit',
    props: ['projectId'],
    data: function () {
      return {
        boolOptions: options.boolOptions,
        old: {
          ...projectModel
        },
        form: {
          ...projectModel
        },
        rules: {
          projectName: [
            {required: true, message: '请输入项目名称', trigger: 'blur'},
            {max: 50, message: '长度不能超过50个字符', trigger: 'blur'},
            {validator: (rule, value, callback) => {
                if (!/^[a-z|-]+$/.test(value)){
                  callback(new Error('项目名称不合法,只允许小写字母和横杠'));
                }
                callback()
              }, trigger: 'blur'}
          ],
          packageName: [
            {required: true, message: '请输入包名', trigger: 'blur'},
            {max: 100, message: '长度不能超过100个字符', trigger: 'blur'}
          ],
          author: [
            {required: true, message: '请输入作者', trigger: 'blur'},
            {max: 50, message: '长度不能超过50个字符', trigger: 'blur'}
          ],
          remote: [
            {required: true, type: 'number', message: '请选择是否启用', trigger: 'change'},
          ],
          remoteUrl: [
            {max: 256, message: '长度不能超过256个字符', trigger: 'blur'}
          ],
          username: [
            {max: 32, message: '长度不能超过32个字符', trigger: 'blur'}
          ],
          password: [
            {max: 32, message: '长度不能超过32个字符', trigger: 'blur'}
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
        var loading = null
        //校验表单
        this.$refs.editForm.validate()
        //提交表单
          .then(() => {
            loading = this.$loading()
            return this.$ajax.put('/generate/meta_project/update', this.form)
          })
          //校验返回结果
          .then(response => this.$common.checkResult(response.data))
          //执行页面跳转
          .then(() => {
            this.$common.showMsg('success', '修改成功')
            this.goBack()
          })
          .catch(error => this.$common.showNotifyError(error))
          .finally(()=>{
            if(loading){
              loading.close()
            }
          })
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
