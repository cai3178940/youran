<template>
  <div class="fieldShow">
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/project' }">项目管理</el-breadcrumb-item>
      <el-breadcrumb-item :to="{ path: `/project/${this.projectId}/entity` }">实体管理</el-breadcrumb-item>
      <el-breadcrumb-item :to="{ path: `/project/${this.projectId}/entity/${this.entityId}/field` }">字段管理</el-breadcrumb-item>
      <el-breadcrumb-item>查看</el-breadcrumb-item>
    </el-breadcrumb>
    <el-row type="flex" align="middle" :gutter="20">
      <el-col :span="12">
        <el-form ref="showForm" class="showForm" :model="form" label-width="80px">
          <el-form-item label="项目" prop="projectId">
            <el-select v-model="form.projectId" filterable placeholder="请选择项目" :disabled="true">
              <el-option
                v-for="item in projectList"
                :key="item.projectId"
                :label="item.projectName"
                :value="item.projectId">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="字段名" prop="title">
            <el-input v-model="form.title" :readonly="true"></el-input>
          </el-form-item>
          <el-form-item label="类名" prop="className">
            <el-input v-model="form.className" :readonly="true"></el-input>
          </el-form-item>
          <el-form-item label="表名" prop="tableName">
            <el-input v-model="form.tableName" :readonly="true"></el-input>
          </el-form-item>
          <el-form-item label="描述" prop="desc">
            <el-input v-model="form.desc" type="textarea" :rows="2" :readonly="true"></el-input>
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

  //字段模型
  const fieldModel = {
    fieldId: null,
    entityId: null,
    autoIncrement: null,
    dicType: '',
    editType: null,
    fieldComment: '',
    fieldDesc: '',
    fieldExample: '',
    fieldLength: null,
    fieldName: '',
    fieldScale: null,
    fieldType: '',
    insert: null,
    jfieldName: '',
    jfieldType: '',
    list: null,
    notNull: null,
    orderNo: null,
    primaryKey: null,
    query: null,
    queryType: null,
    show: null,
    update: null,
    specialField: ''
  }

  export default {
    name: 'fieldShow',
    props: ['projectId','entityId','fieldId'],
    data: function () {
      return {
        form: {
          ...fieldModel
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
        return this.$ajax.get(`/generate/meta_field/${this.fieldId}`)
          .then(response => this.$common.checkResult(response.data))
          .then(result => this.form = result.data)
          .catch(error => this.$common.showNotifyError(error))
      },
      goBack: function () {
        this.$router.push(`/project/${this.projectId}/entity/${this.entityId}/field`)
      }
    },
    created: function () {
      this.queryProject()
        .then(()=>this.getEntity())
    }
  }
</script>

<style>
  .fieldShow .showForm {
    padding: 30px 50px;
  }

</style>
