<template>
  <div class="fieldList">
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/project' }">项目管理</el-breadcrumb-item>
      <el-breadcrumb-item :to="{ path: `/project/${this.projectId}/entity` }">实体管理</el-breadcrumb-item>
      <el-breadcrumb-item>字段管理</el-breadcrumb-item>
    </el-breadcrumb>
    <el-row type="flex" align="middle" :gutter="20" style="padding:10px 0;">
      <el-col :span="4" class="activeNum">
        已选择{{ activeNum }}个字段
      </el-col>
      <el-col :span="20" style="text-align: right;">
        <el-form :inline="true" :model="queryForm" class="demo-form-inline">
          <el-form-item label="请选择项目">
            <el-select v-model="queryForm.projectId" placeholder="请选择项目" @change="projectChange">
              <el-option
                v-for="item in projectList"
                :key="item.projectId"
                :label="item.projectName"
                :value="item.projectId">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="请选择实体">
            <el-select v-model="queryForm.entityId" placeholder="请选择实体">
              <el-option
                v-for="item in entityList"
                :key="item.entityId"
                :label="item.title"
                :value="item.entityId">
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
    <el-table :data="page.entities" style="width: 100%" @selection-change="selectionChange" v-loading="loading">
      <el-table-column type="selection" width="50"></el-table-column>
      <el-table-column property="title" label="字段名"></el-table-column>
      <el-table-column property="className" label="类名"></el-table-column>
      <el-table-column property="tableName" label="表名"></el-table-column>
      <el-table-column property="desc" label="描述"></el-table-column>
      <el-table-column
        label="操作"
        width="150">
        <template slot-scope="scope">
          <el-button @click="handleShow(scope.row)" type="text" size="small">查看</el-button>
          <el-button @click="handleEdit(scope.row)" type="text" size="small">编辑</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-row type="flex" justify="end" style="padding:20px 0; ">
      <el-pagination
        @size-change="sizeChange"
        @current-change="currentChange"
        :current-page="page.pageNo"
        :page-sizes="[10, 20, 30, 40]"
        :page-size="page.pageSize"
        layout="sizes, prev, pager, next"
        :total="page.fieldCount">
      </el-pagination>
    </el-row>
  </div>
</template>

<script>

  export default {
    name: 'fieldList',
    props: ['projectId','entityId'],
    data: function () {
      return {
        //查询参数
        query: {
          projectId:null,
          entityId:null
        },
        //查询表单参数
        queryForm:{
          projectId:null,
          entityId:null
        },
        projectList: [],
        entityList: [],
        activeNum: 0,
        selectItems: [],
        page: {
          pageNo: 1,
          fieldCount: 0,
          pageSize: 2,
          entities: []
        },
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
          this.$common.showMsg('warning', '请选择字段')
          return
        }
        this.$common.confirm('是否确认删除')
          .then(() => this.$ajax.post('/generate/meta_field/deleteBatch',this.selectItems.map(field => field.fieldId)))
          .then(() => this.doQuery())
      },
      sizeChange: function (pageSize) {
        this.page.pageSize = pageSize
        this.doQuery()
      },
      currentChange: function (pageNo) {
        this.page.pageNo = pageNo
        this.doQuery()
      },
      queryProject:function () {
        return this.$common.getProjectOptions()
          .then(response => this.$common.checkResult(response.data))
          .then(result => this.projectList = result.data.entities)
      },
      queryEntity:function (projectId) {
        return this.$common.getEntityOptions(projectId)
          .then(response => this.$common.checkResult(response.data))
          .then(result => this.entityList = result.data.entities)
      },
      //项目选择框修改时，清空实体选择框
      projectChange:function(){
        this.queryForm.entityId = null
        this.queryEntity(this.queryForm.projectId)
      },
      handleQuery: function () {
        //将查询表单参数赋值给查询参数
        this.query = {
          ...this.queryForm
        }
        if(this.query.entityId!=parseInt(this.entityId)){
          this.$router.push(`/project/${this.query.projectId}/entity/${this.query.entityId}/field`)
        }
        this.doQuery()
      },
      // 列表查询
      doQuery: function () {
        if(!this.query.projectId || !this.query.entityId){
          return
        }
        // 将查询参数和分页参数合并
        const params = {
          ...this.query,
          pageNo: this.page.pageNo,
          pageSize: this.page.pageSize
        }
        this.loading = true
        this.$ajax.post('/generate/meta_field/list', params)
          .then(response => this.$common.checkResult(response.data))
          .then(result => this.page = result.data)
          .catch(error => this.$common.showNotifyError(error))
          .finally(()=>this.loading = false)
      },
      handleAdd: function () {
        this.$router.push(`/project/${this.projectId}/entity/${this.entityId}/field/add`)
      },
      handleEdit: function (row) {
        this.$router.push(`/project/${this.projectId}/entity/${this.entityId}/field/edit/${row.fieldId}`)
      },
      handleShow: function (row) {
        this.$router.push(`/project/${this.projectId}/entity/${this.entityId}/field/show/${row.fieldId}`)
      }
    },
    activated: function () {
      this.queryProject()
        .then(this.queryEntity(this.projectId))
        .then(()=>{
          this.queryForm.projectId = parseInt(this.projectId)
          this.queryForm.entityId = parseInt(this.entityId)
          this.query={
            ...this.queryForm
          }
        })
        .then(()=>this.doQuery())
    }
  }
</script>
<style>
  .fieldList .activeNum {
    min-width: 160px;
    text-align: left;
    padding: 0 0 0 20px;
  }
</style>
