<template>
  <div class="fieldAdd">
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/project' }">项目管理</el-breadcrumb-item>
      <el-breadcrumb-item :to="{ path: `/project/${this.projectId}/entity` }">实体管理</el-breadcrumb-item>
      <el-breadcrumb-item :to="{ path: `/project/${this.projectId}/entity/${this.entityId}/field` }">字段管理</el-breadcrumb-item>
      <el-breadcrumb-item>添加</el-breadcrumb-item>
    </el-breadcrumb>
    <el-row type="flex" align="middle" :gutter="20">
      <el-col :span="12">
        <el-form ref="addForm" class="addForm" :rules="rules" :model="form" label-width="100px">
          <el-form-item label="字段名" prop="fieldName">
            <el-input v-model="form.fieldName" placeholder="请输入字段名，例如：age"></el-input>
          </el-form-item>
          <el-form-item label="java字段名" prop="jfieldName">
            <el-input v-model="form.jfieldName" placeholder="请输入java字段名，例如：age"></el-input>
          </el-form-item>
          <el-form-item label="字段描述" prop="fieldDesc">
            <el-input v-model="form.fieldDesc" placeholder="请输入字段描述（中文名），例如：年龄"></el-input>
          </el-form-item>
          <el-form-item label="字段类型" prop="fieldType">
            <el-select v-model="form.fieldType" filterable placeholder="请选择字段类型">
              <el-option
                v-for="item in fieldTypeOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="java字段类型" prop="jfieldType">
            <el-select v-model="form.jfieldType" filterable placeholder="请选择java字段类型">
              <el-option
                v-for="item in jfieldTypeOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="字段长度" prop="fieldLength">
            <el-input v-model="form.fieldLength" placeholder="请输入字段长度，例如：10"></el-input>
          </el-form-item>
          <el-form-item label="字段精度" prop="fieldScale">
            <el-input v-model="form.fieldScale" placeholder="请输入字段精度，例如：2"></el-input>
          </el-form-item>
          <el-form-item label="是否主键" prop="primaryKey">
            <el-radio-group v-model="form.primaryKey">
              <el-radio v-for="item in boolOptions" :label="item.value">{{item.label}}</el-radio>
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
    name: 'fieldAdd',
    props: ['projectId','entityId'],
    data: function () {
      return {
        boolOptions: options.boolOptions,
        fieldTypeOptions: options.fieldTypeOptions,
        jfieldTypeOptions: options.jfieldTypeOptions,
        form: {
          entityId: null,
          //字段名
          fieldName: '',
          //java字段名
          jfieldName: '',
          //字段描述
          fieldDesc: '',
          //字段长度
          fieldLength: null,
          //字段精度
          fieldScale: null,
          //字段类型
          fieldType: '',
          //java字段类型
          jfieldType: '',
          //是否主键
          primaryKey: null,
          notNull: null,
          autoIncrement: null,
          fieldExample: '',
          fieldComment: '',
          dicType: '',
          query: null,
          queryType: null,
          insert: null,
          update: null,
          list: null,
          show: null,
          editType: null,
          orderNo: null,
          specialField: ''
        },
        rules: {

        }
      }
    },
    methods: {
      queryProject: function () {
        return this.$common.getProjectOptions()
          .then(response => this.$common.checkResult(response.data))
          .then(result => this.projectList = result.data.entities)
      },
      submit: function () {
        console.info(this.form)
        //校验表单
        this.$refs.addForm.validate()
        //提交表单
          .then(() => this.$ajax.post('/generate/meta_field/save', this.form))
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
        this.$router.push(`/project/${this.projectId}/entity/${this.entityId}/field`)
      }
    },
    created: function () {
      this.queryProject()
        .then(() => this.form.projectId = parseInt(this.projectId))
    }
  }
</script>

<style>
  .fieldAdd .addForm {
    padding: 30px 50px;
  }

</style>
