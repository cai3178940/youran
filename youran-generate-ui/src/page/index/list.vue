<template>
  <div class="indexList">
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/project' }">项目管理</el-breadcrumb-item>
      <el-breadcrumb-item :to="{ path: `/project/${this.projectId}/entity` }">实体管理</el-breadcrumb-item>
      <el-breadcrumb-item>索引管理</el-breadcrumb-item>
    </el-breadcrumb>
    <el-row type="flex" align="middle" :gutter="20" style="padding:10px 0;">
      <el-col :span="6" class="activeNum">
        已选择{{ activeNum }}个索引
      </el-col>
      <el-col :span="18" style="text-align: right;">
        <el-form :inline="true" :model="queryForm" class="demo-form-inline">
          <el-form-item>
            <el-cascader
              placeholder="请选择实体"
              :options="queryForm.projectEntityOptions"
              v-model="queryForm.projectEntity"
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
      <el-table-column property="indexName" label="索引名" width="200px"></el-table-column>
      <el-table-column label="是否唯一" width="150px">
        <template slot-scope="scope">
          <icon v-if="scope.row.unique==1" name="check" class="color-success"></icon>
          <icon v-if="scope.row.unique!=1" name="close" class="color-danger"></icon>
        </template>
      </el-table-column>
      <el-table-column label="字段">
        <template slot-scope="scope">
            {{scope.row.fields | renderFields}}
        </template>
      </el-table-column>
      <el-table-column
        label="操作"
        width="200">
        <template slot-scope="scope">
          <el-button @click="handleShow(scope.row)" type="text" size="medium">查看</el-button>
          <el-button @click="handleEdit(scope.row)" type="text" size="medium">编辑</el-button>
        </template>
      </el-table-column>
    </el-table>

  </div>
</template>

<script>

  export default {
    name: 'indexList',
    props: ['projectId', 'entityId'],
    data: function () {
      return {
        //查询参数
        query: {
          projectId: null,
          entityId: null
        },
        //查询表单参数
        queryForm: {
          projectEntityOptions:[],
          projectEntity:[0,0]
        },
        activeNum: 0,
        selectItems: [],
        entities: [],
        loading: false
      }
    },
    filters:{
      renderFields:function (fields) {
        if(!fields){
          return ''
        }
        return fields.map(field=>field.fieldName).join(',')
      }
    },
    methods: {
      selectionChange: function (val) {
        this.selectItems = val
        this.activeNum = this.selectItems.length
      },
      handleDel: function () {
        if (this.activeNum <= 0) {
          this.$common.showMsg('warning', '请选择索引')
          return
        }
        this.$common.confirm('是否确认删除')
          .then(() => this.$ajax.put('/generate/meta_index/deleteBatch', this.selectItems.map(index => index.indexId)))
          .then(() => this.doQuery())
      },
      initProjectOptions: function () {
        return this.$common.getProjectOptions()
          .then(response => this.$common.checkResult(response.data))
          .then(result => this.queryForm.projectEntityOptions = result.data.map(project=>({value:project.projectId,label:project.projectName,children:[]})))
      },
      handleProjectChange: function (optionArray) {
        var projectId = optionArray[0]
        //获取被激活的option
        var project = this.queryForm.projectEntityOptions.find(option=>option.value==projectId)
        if(project.children.length){
          return
        }
        return this.$common.getEntityOptions(projectId)
          .then(response => this.$common.checkResult(response.data))
          .then(result => project.children = result.data.entities.map(entity=>({value:entity.entityId,label:entity.title})))
      },
      handleQuery: function () {
        if (this.queryForm.projectEntity[1] == null) {
          this.$common.showNotifyError('请选择实体')
          return
        }
        //将查询表单参数赋值给查询参数
        this.query.projectId = this.queryForm.projectEntity[0]
        this.query.entityId = this.queryForm.projectEntity[1]
        if (this.query.entityId != parseInt(this.entityId)) {
          this.$router.push(`/project/${this.query.projectId}/entity/${this.query.entityId}/index`)
        }
        this.doQuery()
      },
      // 列表查询
      doQuery: function () {
        if (!this.query.projectId || !this.query.entityId) {
          return
        }
        this.loading = true
        this.$ajax.get('/generate/meta_index/list', {params:this.query})
          .then(response => this.$common.checkResult(response.data))
          .then(result => this.entities = result.data)
          .catch(error => this.$common.showNotifyError(error))
          .finally(() => this.loading = false)
      },
      handleAdd: function () {
        this.$router.push(`/project/${this.projectId}/entity/${this.entityId}/index/add`)
      },
      handleEdit: function (row) {
        this.$router.push(`/project/${this.projectId}/entity/${this.entityId}/index/edit/${row.indexId}`)
      },
      handleShow: function (row) {
        this.$router.push(`/project/${this.projectId}/entity/${this.entityId}/index/show/${row.indexId}`)
      }
    },
    activated: function () {
      var projectId = parseInt(this.projectId);
      var entityId = parseInt(this.entityId);
      this.queryForm.projectEntity[0] = projectId
      this.queryForm.projectEntity[1] = entityId
      this.query.projectId = projectId
      this.query.entityId = entityId
      this.initProjectOptions()
        .then(() => this.handleProjectChange([projectId]))
        .then(() => this.doQuery())
    }
  }
</script>
<style>
  .indexList .activeNum {
    min-width: 160px;
    text-align: left;
    padding: 0 0 0 20px;
  }
</style>
