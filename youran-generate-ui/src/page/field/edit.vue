<template>
  <div class="fieldEdit">
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/project' }">项目管理</el-breadcrumb-item>
      <el-breadcrumb-item :to="{ path: `/project/${this.projectId}/entity` }">实体管理</el-breadcrumb-item>
      <el-breadcrumb-item :to="{ path: `/project/${this.projectId}/entity/${this.entityId}/field` }">字段管理</el-breadcrumb-item>
      <el-breadcrumb-item>编辑</el-breadcrumb-item>
    </el-breadcrumb>
    <el-row type="flex" align="middle" :gutter="20">
      <el-col :span="12">
        <el-form ref="editForm" class="editForm" :rules="rules" :model="form" label-width="80px">
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
            <el-input v-model="form.title"></el-input>
          </el-form-item>
          <el-form-item label="类名" prop="className">
            <el-input v-model="form.className"></el-input>
          </el-form-item>
          <el-form-item label="表名" prop="tableName">
            <el-input v-model="form.tableName"></el-input>
          </el-form-item>
          <el-form-item label="描述" prop="desc">
            <el-input v-model="form.desc" type="textarea" :rows="2"></el-input>
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
    name: 'fieldEdit',
    props: ['projectId','entityId','fieldId'],
    data: function () {
      return {
        projectList: [],
        old: {
          ...fieldModel
        },
        form: {
          ...fieldModel
        },
        rules: {
          projectId: [
            {required: true, type: 'number', message: '请选择项目', trigger: 'change'},
          ],
          title: [
            {required: true, message: '请输入字段名', trigger: 'blur'},
            {min: 1, max: 25, message: '长度在 1 到 25 个字符', trigger: 'blur'}
          ],
          className: [
            {required: true, message: '请输入类名', trigger: 'blur'},
            {min: 1, max: 50, message: '长度在 1 到 50 个字符', trigger: 'blur'}
          ],
          tableName: [
            {required: true, message: '请输入表名', trigger: 'blur'},
            {min: 1, max: 50, message: '长度在 1 到 50 个字符', trigger: 'blur'}
          ],
          desc: [
            {max: 250, message: '长度在 250 个字符以内', trigger: 'blur'}
          ]
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
          .then(result => this.old = result.data)
          .catch(error => this.$common.showNotifyError(error))
      },
      reset: function () {
        for (const key in fieldModel) {
          this.form[key] = this.old[key]
        }
      },
      submit: function () {
        //校验表单
        this.$refs.editForm.validate()
        //提交表单
          .then(() => this.$ajax.put('/generate/meta_field/update', this.form))
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
        this.$router.push(`/project/${this.projectId}/entity/${this.entityId}/field`)
      }
    },
    created: function () {
      Promise.all([this.getEntity(),this.queryProject()])
        .then(() => this.reset())
    }
  }
</script>

<style>
  .fieldEdit .editForm {
    padding: 30px 50px;
  }

</style>
