<template>
  <div class="entityShow">
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/project' }">项目管理</el-breadcrumb-item>
      <el-breadcrumb-item :to="{ path: `/project/${this.projectId}/entity` }">实体管理</el-breadcrumb-item>
      <el-breadcrumb-item>查看</el-breadcrumb-item>
    </el-breadcrumb>
    <el-row type="flex" align="middle" :gutter="20">
      <el-col :span="12">
        <el-form ref="showForm" class="showForm" :model="form" label-width="80px">
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
          <el-form-item label="实体名" prop="title">
            <el-input v-model="form.title" :disabled="true"></el-input>
          </el-form-item>
          <el-form-item label="类名" prop="className">
            <el-input v-model="form.className" :disabled="true"></el-input>
          </el-form-item>
          <el-form-item label="表名" prop="tableName">
            <el-input v-model="form.tableName" :disabled="true"></el-input>
          </el-form-item>
          <el-form-item label="描述" prop="desc">
            <el-input v-model="form.desc" type="textarea" :rows="2" :disabled="true"></el-input>
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

  //实体模型
  const entityModel = {
    entityId: null,
    projectId: null,
    title: '',
    className: '',
    tableName: '',
    desc: '',
    commonCall: true
  }

  export default {
    name: 'entityShow',
    props: ['projectId', 'entityId'],
    data: function () {
      return {
        form: {
          ...entityModel
        }
      }
    },
    methods: {
      queryProject: function () {
        return this.$common.getProjectOptions()
          .then(response => this.$common.checkResult(response.data))
          .then(result => this.projectList = result.data.entities)
      },
      getEntity: function () {
        return this.$ajax.get(`/generate/meta_entity/${this.entityId}`)
          .then(response => this.$common.checkResult(response.data))
          .then(result => this.form = result.data)
          .catch(error => this.$common.showNotifyError(error))
      },
      goBack: function () {
        this.$router.push(`/project/${this.projectId}/entity`)
      }
    },
    created: function () {
      this.queryProject()
        .then(()=>this.getEntity())
    }
  }
</script>

<style>
  .entityShow .showForm {
    padding: 30px 50px;
  }

</style>
