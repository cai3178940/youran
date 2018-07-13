<template>
  <div class="constEdit">
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/project' }">项目管理</el-breadcrumb-item>
      <el-breadcrumb-item :to="{ path: `/project/${this.projectId}/const` }">枚举管理</el-breadcrumb-item>
      <el-breadcrumb-item>编辑枚举</el-breadcrumb-item>
    </el-breadcrumb>
    <el-row type="flex" align="middle" :gutter="20">
      <el-col :span="12">
        <el-form ref="editForm" class="editForm" :rules="rules" :model="form" label-width="80px">
          <el-form-item label="项目" prop="projectId">
            <help-popover name="const.projectId">
              <el-select v-model="form.projectId" style="width:100%;" filterable placeholder="请选择项目" :disabled="true">
                <el-option
                  v-for="item in projectList"
                  :key="item.projectId"
                  :label="item.projectName"
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
            <el-button @click="goBack()">返回</el-button>
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>
  </div>
</template>

<script>
  import options from '@/components/options.js'
  //枚举模型
  const constModel = {
    constId: null,
    projectId: null,
    constRemark: '',
    constName: '',
    constType: ''
  }

  export default {
    name: 'constEdit',
    props: ['projectId', 'constId'],
    data: function () {
      return {
        constTypeOptions: options.constTypeOptions,
        projectList: [],
        old: {
          ...constModel
        },
        form: {
          ...constModel
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
      getConst: function () {
        return this.$ajax.get(`/generate/meta_const/${this.constId}`)
          .then(response => this.$common.checkResult(response.data))
          .then(result => this.old = result.data)
          .catch(error => this.$common.showNotifyError(error))
      },
      reset: function () {
        for (const key in constModel) {
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
            return this.$ajax.put('/generate/meta_const/update', this.form)
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
        this.$router.push(`/project/${this.projectId}/const`)
      }
    },
    created: function () {
      Promise.all([this.getConst(),this.queryProject()])
        .then(() => this.reset())
    }
  }
</script>

<style>
  .constEdit .editForm {
    padding: 30px 50px;
  }

</style>
