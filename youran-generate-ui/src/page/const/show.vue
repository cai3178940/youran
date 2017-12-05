<template>
  <div class="constShow">
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/project' }">项目管理</el-breadcrumb-item>
      <el-breadcrumb-item :to="{ path: `/project/${this.projectId}/const` }">枚举管理</el-breadcrumb-item>
      <el-breadcrumb-item>查看</el-breadcrumb-item>
    </el-breadcrumb>
    <el-row type="flex" align="middle" :gutter="20">
      <el-col :span="12">
        <el-form ref="showForm" class="showForm" :model="form" label-width="80px">
          <el-form-item label="项目" prop="projectId">
            <el-select v-model="form.projectId" style="width:100%;" filterable :disabled="true">
              <el-option
                v-for="item in projectList"
                :key="item.projectId"
                :label="item.projectName"
                :value="item.projectId">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="枚举名称" prop="constRemark">
            <el-input v-model="form.constRemark" :disabled="true"></el-input>
          </el-form-item>
          <el-form-item label="枚举类名" prop="constName">
            <el-input v-model="form.constName" :disabled="true"></el-input>
          </el-form-item>
          <el-form-item label="类型" prop="constType">
            <el-radio-group v-model="form.constType" :disabled="true">
              <el-radio border v-for="item in constTypeOptions" :key="item.value" :label="item.value">{{item.label}}</el-radio>
            </el-radio-group>
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
    name: 'constShow',
    props: ['projectId', 'constId'],
    data: function () {
      return {
        constTypeOptions: options.constTypeOptions,
        form: {
          ...constModel
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
          .then(result => this.form = result.data)
          .catch(error => this.$common.showNotifyError(error))
      },
      goBack: function () {
        this.$router.push(`/project/${this.projectId}/const`)
      }
    },
    created: function () {
      this.queryProject()
        .then(()=>this.getConst())
    }
  }
</script>

<style>
  .constShow .showForm {
    padding: 30px 50px;
  }

</style>
