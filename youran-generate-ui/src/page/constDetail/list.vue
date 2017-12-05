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
          <el-form-item label="项目">
            <el-select v-model="queryForm.projectId" placeholder="请选择项目" @change="projectChange">
              <el-option
                v-for="item in projectList"
                :key="item.projectId"
                :label="item.projectName"
                :value="item.projectId">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="枚举">
            <el-select v-model="queryForm.constId" placeholder="请选择枚举">
              <el-option
                v-for="item in constList"
                :key="item.constId"
                :label="item.constRemark"
                :value="item.constId">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="handleQuery">查询</el-button>
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
          projectId: null,
          constId: null
        },
        projectList: [],
        constList: [],
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
          .then(() => this.$ajax.post('/generate/meta_const_detail/deleteBatch', this.selectItems.map(constDetail => constDetail.constDetailId)))
          .then(() => this.doQuery())
      },
      queryProject: function () {
        return this.$common.getProjectOptions()
          .then(response => this.$common.checkResult(response.data))
          .then(result => this.projectList = result.data)
      },
      queryConst: function (projectId) {
        return this.$common.getConstOptions(projectId)
          .then(response => this.$common.checkResult(response.data))
          .then(result => this.constList = result.data.entities)
      },
      //项目选择框修改时，清空枚举选择框
      projectChange: function () {
        this.queryForm.constId = null
        this.queryConst(this.queryForm.projectId)
      },
      handleQuery: function () {
        //将查询表单参数赋值给查询参数
        this.query = {
          ...this.queryForm
        }
        if (this.query.constId == null) {
          this.$common.showNotifyError('请选择常量')
          return
        }
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
        this.$ajax.post('/generate/meta_const_detail/list', this.query)
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
      this.queryProject()
        .then(this.queryConst(this.projectId))
        .then(() => {
          this.queryForm.projectId = parseInt(this.projectId)
          this.queryForm.constId = parseInt(this.constId)
          this.query = {
            ...this.queryForm
          }
        })
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
