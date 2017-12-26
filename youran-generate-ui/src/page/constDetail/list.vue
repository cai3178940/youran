<template>
  <div class="constDetailList">
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/project' }">项目管理</el-breadcrumb-item>
      <el-breadcrumb-item :to="{ path: `/project/${this.projectId}/const` }">枚举管理</el-breadcrumb-item>
      <el-breadcrumb-item>枚举值管理</el-breadcrumb-item>
    </el-breadcrumb>
    <el-row type="flex" align="middle" :gutter="20" style="padding:10px 0;">
      <el-col :span="4" class="activeNum">
        已选择{{ activeNum }}个枚举值
      </el-col>
      <el-col :span="20" style="text-align: right;">
        <el-form :inline="true" :model="queryForm" class="demo-form-inline">
          <el-form-item>
            <el-cascader
              placeholder="请选择枚举"
              :options="queryForm.projectConstOptions"
              v-model="queryForm.projectConst"
              @active-item-change="handleProjectChange"
              @change="handleQuery">
            </el-cascader>
          </el-form-item>
          <el-form-item>
            <el-button @click.native="handleAdd" type="success">添加</el-button>
            <el-button @click.native="handleDel" type="danger">删除</el-button>
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>
    <el-table :data="entities" style="width: 100%" @selection-change="selectionChange" v-loading="loading">
      <el-table-column type="selection" width="50"></el-table-column>
      <el-table-column property="detailName" label="枚举字段名"></el-table-column>
      <el-table-column property="detailValue" label="枚举值"></el-table-column>
      <el-table-column property="detailRemark" label="备注"></el-table-column>
      <el-table-column
        label="操作"
        width="100">
        <template slot-scope="scope">
          <el-button @click="handleShow(scope.row)" type="text" size="small">查看</el-button>
          <el-button @click="handleEdit(scope.row)" type="text" size="small">编辑</el-button>
        </template>
      </el-table-column>
    </el-table>

  </div>
</template>

<script>
  export default {
    name: 'constDetailList',
    props: ['projectId', 'constId'],
    data: function () {
      return {
        //查询参数
        query: {
          projectId: null,
          constId: null
        },
        //查询表单参数
        queryForm: {
          projectConstOptions:[],
          projectConst:[0,0]
        },
        activeNum: 0,
        selectItems: [],
        entities: [],
        loading: false
      }
    },
    methods: {
      selectionChange: function (val) {
        this.selectItems = val
        this.activeNum = this.selectItems.length
      },
      handleDel: function () {
        if (this.activeNum <= 0) {
          this.$common.showMsg('warning', '请选择枚举值')
          return
        }
        this.$common.confirm('是否确认删除')
          .then(() => this.$ajax.put('/generate/meta_const_detail/deleteBatch', this.selectItems.map(constDetail => constDetail.constDetailId)))
          .then(() => this.doQuery())
      },
      initProjectOptions: function () {
        return this.$common.getProjectOptions()
          .then(response => this.$common.checkResult(response.data))
          .then(result => this.queryForm.projectConstOptions = result.data.map(project=>({value:project.projectId,label:project.projectName,children:[]})))
      },
      handleProjectChange: function (optionArray) {
        var projectId = optionArray[0]
        //获取被激活的option
        var project = this.queryForm.projectConstOptions.find(option=>option.value==projectId)
        if(project.children.length){
          return
        }
        return this.$common.getConstOptions(projectId)
          .then(response => this.$common.checkResult(response.data))
          .then(result => project.children = result.data.entities.map(c=>({value:c.constId,label:c.constRemark})))
      },
      handleQuery: function () {
        if (this.queryForm.projectConst[1] == null) {
          this.$common.showNotifyError('请选择常量')
          return
        }
        //将查询表单参数赋值给查询参数
        this.query.projectId = this.queryForm.projectConst[0]
        this.query.constId = this.queryForm.projectConst[1]
        if (this.query.constId != parseInt(this.constId)) {
          this.$router.push(`/project/${this.query.projectId}/const/${this.query.constId}/constDetail`)
        }
        this.doQuery()
      },
      // 列表查询
      doQuery: function () {
        if (!this.query.projectId || !this.query.constId) {
          return
        }
        this.loading = true
        this.$ajax.get('/generate/meta_const_detail/list', {params:this.query})
          .then(response => this.$common.checkResult(response.data))
          .then(result => this.entities = result.data)
          .catch(error => this.$common.showNotifyError(error))
          .finally(() => this.loading = false)
      },
      handleAdd: function () {
        this.$router.push(`/project/${this.projectId}/const/${this.constId}/constDetail/add`)
      },
      handleEdit: function (row) {
        this.$router.push(`/project/${this.projectId}/const/${this.constId}/constDetail/edit/${row.constDetailId}`)
      },
      handleShow: function (row) {
        this.$router.push(`/project/${this.projectId}/const/${this.constId}/constDetail/show/${row.constDetailId}`)
      }
    },
    activated: function () {
      var projectId = parseInt(this.projectId);
      var constId = parseInt(this.constId);
      this.queryForm.projectConst[0] = projectId
      this.queryForm.projectConst[1] = constId
      this.query.projectId = projectId
      this.query.constId = constId
      this.initProjectOptions()
        .then(() => this.handleProjectChange([projectId]))
        .then(() => this.doQuery())
    }
  }
</script>
<style>
  .constDetailList .activeNum {
    min-width: 160px;
    text-align: left;
    padding: 0 0 0 20px;
  }

  .demo-form-inline .el-select .el-input {
    width: 150px;
  }

  .demo-form-inline .el-form-item {
    margin-bottom: 0px;
  }
</style>
