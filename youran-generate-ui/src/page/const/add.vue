<template>
  <div class="constAdd">
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/project' }">项目管理</el-breadcrumb-item>
      <el-breadcrumb-item :to="{ path: `/project/${this.projectId}/const` }">枚举管理</el-breadcrumb-item>
      <el-breadcrumb-item>添加</el-breadcrumb-item>
    </el-breadcrumb>
    <el-row type="flex" align="middle" :gutter="20">
      <el-col :span="12">
        <el-form ref="addForm" class="addForm" :rules="rules" :model="form" label-width="80px">
          <el-form-item label="项目" prop="projectId">
            <el-select v-model="form.projectId" style="width:100%;" filterable placeholder="请选择项目" :disabled="true">
              <el-option
                v-for="item in projectList"
                :key="item.projectId"
                :label="item.projectName"
                :value="item.projectId">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="枚举名称" prop="constRemark">
            <el-input v-model="form.constRemark" placeholder="例如：性别"></el-input>
          </el-form-item>
          <el-form-item label="枚举类名" prop="constName">
            <el-input v-model="form.constName" placeholder="例如：Sex"></el-input>
          </el-form-item>
          <el-form-item label="类型" prop="constType">
            <el-radio-group v-model="form.constType">
              <el-radio border v-for="item in constTypeOptions" :key="item.value" :label="item.value">{{item.label}}</el-radio>
            </el-radio-group>
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
  import options from '@/components/options.js'
  export default {
    name: 'constAdd',
    props: ['projectId'],
    data: function () {
      return {
        constTypeOptions: options.constTypeOptions,
        projectList: [],
        form: {
          projectId: null,
          title: '',
          className: '',
          tableName: '',
          desc: '',
          commonCall: true
        },
        rules: {
          projectId: [
            {required: true, type: 'number', message: '请选择项目', trigger: 'change'},
          ],
          constRemark: [
            {required: true, message: '请输入枚举名称', trigger: 'blur'},
            {max: 100, message: '长度不能超过100个字符', trigger: 'blur'}
          ],
          constName: [
            {required: true, message: '请输入枚举类名', trigger: 'blur'},
            {max: 20, message: '长度不能超过20个字符', trigger: 'blur'}
          ],
          constType: [
            {required: true, type: 'number', message: '请选择类型', trigger: 'change'},
          ]
        }
      }
    },
    methods: {
      queryProject: function () {
        return this.$common.getProjectOptions()
          .then(response => this.$common.checkResult(response.data))
          .then(result => this.projectList = result.data)
      },
      submit: function () {
        //校验表单
        this.$refs.addForm.validate()
        //提交表单
          .then(() => this.$ajax.post('/generate/meta_const/save', this.form))
          //校验返回结果
          .then(response => this.$common.checkResult(response.data))
          //执行页面跳转
          .then(() => {
            this.$common.showMsg('success', '添加成功')
            this.goBack()
          })
          .catch(error => this.$common.showNotifyError(error))
      },
      goBack: function () {
        this.$router.push(`/project/${this.projectId}/const`)
      }
    },
    created: function () {
      this.queryProject()
        .then(() => this.form.projectId = parseInt(this.projectId))
    }
  }
</script>

<style>
  .constAdd .addForm {
    padding: 30px 50px;
  }

</style>
