<template>
  <div class="projectList">
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item>项目管理</el-breadcrumb-item>
    </el-breadcrumb>
    <el-row type="flex" align="middle" :gutter="20">
      <el-col :span="6"  class="activeNum">
        已选择{{ activeNum }}个项目
      </el-col>
      <el-col :span="18" style="text-align: right;">
        <el-button @click.native="handleAdd" type="success">添加</el-button>
        <el-button @click.native="handleDel" type="danger">删除</el-button>
      </el-col>
    </el-row>

    <el-table :data="entities" style="width: 100%" @selection-change="selectionChange" v-loading="loading">
      <el-table-column type="selection" width="50"></el-table-column>
      <el-table-column property="projectName" label="项目名称"></el-table-column>
      <el-table-column property="author" label="作者"></el-table-column>
      <el-table-column property="packageName" label="包名"></el-table-column>
      <el-table-column
        label="操作"
        width="250">
        <template slot-scope="scope">
          <el-button @click="handleEntity(scope.row)" type="text" size="small">实体管理</el-button>
          <el-button @click="handleConst(scope.row)" type="text" size="small">枚举管理</el-button>
          <el-button @click="handleMtm(scope.row)" type="text" size="small">多对多管理</el-button>
          <el-button @click="handleShow(scope.row)" type="text" size="small">查看</el-button>
          <el-button @click="handleEdit(scope.row)" type="text" size="small">编辑</el-button>
          <el-button @click="handleGenCode(scope.row)" type="text" size="small">生成代码</el-button>
          <el-button @click="handleGenSql(scope.row)" type="text" size="small">生成sql</el-button>
        </template>
      </el-table-column>
    </el-table>

  </div>
</template>

<script>

  export default {
    name: 'projectList',
    data: function () {
      return {
        query: {},
        activeNum: 0,
        selectItems: [],
        entities: [],
        loading:false
      }
    },
    methods: {
      selectionChange: function (val) {
        this.selectItems = val
        this.activeNum = this.selectItems.length
      },
      handleDel: function () {
        if (this.activeNum <= 0) {
          this.$common.showMsg('warning', '请选择项目')
          return
        }
        this.$common.confirm('是否确认删除')
          .then(() => this.$ajax.put('/generate/meta_project/deleteBatch', this.selectItems.map(entity => entity.projectId)))
          .then(() => this.doQuery())
      },
      // 列表查询
      doQuery: function () {
        this.loading = true
        this.$ajax.get('/generate/meta_project/list', {params:this.query})
          .then(response => this.$common.checkResult(response.data))
          .then(result => this.entities = result.data)
          .catch(error => this.$common.showNotifyError(error))
          .finally(()=>this.loading = false)
      },
      handleAdd: function () {
        this.$router.push('/project/add')
      },
      handleEntity: function (row) {
        this.$router.push(`/project/${row.projectId}/entity`)
      },
      handleConst: function (row) {
        this.$router.push(`/project/${row.projectId}/const`)
      },
      handleMtm: function (row) {
        this.$router.push(`/project/${row.projectId}/mtm`)
      },
      handleEdit: function (row) {
        this.$router.push(`/project/edit/${row.projectId}`)
      },
      handleShow: function (row) {
        this.$router.push(`/project/show/${row.projectId}`)
      },
      handleGenSql: function (row) {
        window.open(`${this.$common.BASE_API_URL}/generate/code_gen/genSql?projectId=${row.projectId}`)
      },
      handleGenCode: function (row) {
        window.open(`${this.$common.BASE_API_URL}/generate/code_gen/genCode?projectId=${row.projectId}`)
      }
    },
    activated: function () {
      this.doQuery()
    }
  }
</script>
<style>
  .projectList .activeNum {
    min-width: 160px;
    text-align: left;
    padding: 0 0 0 20px;
  }
</style>
