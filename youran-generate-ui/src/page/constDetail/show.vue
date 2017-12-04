<template>
  <div class="constDetailShow">
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/project' }">项目管理</el-breadcrumb-item>
      <el-breadcrumb-item :to="{ path: `/project/${this.projectId}/const` }">枚举管理</el-breadcrumb-item>
      <el-breadcrumb-item :to="{ path: `/project/${this.projectId}/const/${this.constId}/constDetail` }">枚举值管理</el-breadcrumb-item>
      <el-breadcrumb-item>查看</el-breadcrumb-item>
    </el-breadcrumb>
    <el-row type="flex" align="middle" :gutter="20">
      <el-col :span="12">
        <el-form ref="showForm" class="showForm" :model="form" label-width="120px">
          <el-form-item label="枚举字段名" prop="detailName">
            <el-input :disabled="true" v-model="form.detailName"></el-input>
          </el-form-item>
          <el-form-item label="枚举值" prop="detailValue">
            <el-input :disabled="true" v-model="form.detailValue"></el-input>
          </el-form-item>
          <el-form-item label="备注" prop="detailRemark">
            <el-input :disabled="true" v-model="form.detailRemark"></el-input>
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

  //枚举值模型
  const constDetailModel = {
    constDetailId: null,
    constId: null,
    //枚举字段名
    detailName: '',
    //枚举值
    detailValue: '',
    //备注
    detailRemark: ''
  }

  export default {
    name: 'constDetailShow',
    props: ['projectId','entityId','constDetailId'],
    data: function () {
      return {
        form: {
          ...constDetailModel
        }
      }
    },
    methods: {
      getConstDetail: function () {
        return this.$ajax.get(`/generate/meta_const_detail/${this.constDetailId}`)
          .then(response => this.$common.checkResult(response.data))
          .then(result => this.form = result.data)
          .catch(error => this.$common.showNotifyError(error))
      },
      goBack: function () {
        this.$router.push(`/project/${this.projectId}/const/${this.constId}/constDetail`)
      }
    },
    created: function () {
      this.getConstDetail()
    }
  }
</script>

<style>
  .constDetailShow .showForm {
    padding: 30px 50px;
  }

</style>
