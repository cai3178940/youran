<template>
  <div class="mtmList">
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/project' }">项目管理</el-breadcrumb-item>
      <el-breadcrumb-item>多对多管理</el-breadcrumb-item>
    </el-breadcrumb>
    <el-row type="flex" align="middle" :gutter="20" style="padding:10px 0;">
      <el-col :span="6" class="activeNum">
        已选择{{ activeNum }}个多对多
      </el-col>
      <el-col :span="18" style="text-align: right;">
        <el-form :inline="true" :model="queryForm" class="demo-form-inline">
          <el-form-item>
            <el-select v-model="queryForm.projectId" @change="handleQuery" placeholder="请选择项目">
              <el-option
                v-for="item in projectList"
                :key="item.projectId"
                :label="item.projectName"
                :value="item.projectId">
              </el-option>
            </el-select>
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
      <el-table-column property="tableName" label="关联表名"></el-table-column>
      <el-table-column label="实体1">
        <template slot-scope="scope">
          {{scope.row.entityId1 | renderEntity(entityList)}}
          <icon v-if="scope.row.holdRefer1==1" name="heart" class="color-warning"></icon>
          <icon v-else name="heart-o" class="color-warning"></icon>
        </template>
      </el-table-column>
      <el-table-column label="实体2">
        <template slot-scope="scope">
          {{scope.row.entityId2 | renderEntity(entityList)}}
          <icon v-if="scope.row.holdRefer2==1" name="heart" class="color-warning"></icon>
          <icon v-else name="heart-o" class="color-warning"></icon>
        </template>
      </el-table-column>
      <el-table-column property="desc" label="描述"></el-table-column>
      <el-table-column
        label="操作"
        width="100">
        <template slot-scope="scope">
          <!--<el-button @click="handleShow(scope.row)" type="text" size="medium">查看</el-button>-->
          <el-button @click="handleEdit(scope.row)" type="text" size="medium">编辑</el-button>
        </template>
      </el-table-column>
    </el-table>

  </div>
</template>

<script>

  export default {
    name: 'mtmList',
    props: ['projectId'],
    data: function () {
      return {
        //查询参数
        query: {
          projectId: null
        },
        //查询表单参数
        queryForm: {
          projectId: null
        },
        projectList: [],
        entityList:[],
        activeNum: 0,
        selectItems: [],
        entities: [],
        loading: false
      }
    },
    filters:{
      renderEntity:function (entityId,entityList) {
        var entity = entityList.find(entity=>entity.entityId==entityId)
        if(entity){
          return entity.title
        }
        return entityId
      }
    },
    methods: {
      selectionChange: function (val) {
        this.selectItems = val
        this.activeNum = this.selectItems.length
      },
      handleDel: function () {
        if (this.activeNum <= 0) {
          this.$common.showMsg('warning', '请选择多对多')
          return
        }
        this.$common.confirm('是否确认删除')
          .then(() => this.$ajax.put('/generate/meta_mtm/deleteBatch', this.selectItems.map(mtm => mtm.mtmId)))
          .then(() => this.doQuery())
      },
      queryProject: function () {
        return this.$common.getProjectOptions()
          .then(response => this.$common.checkResult(response.data))
          .then(result => this.projectList = result.data)
      },
      queryEntity: function (projectId) {
        return this.$common.getEntityOptions(projectId)
          .then(response => this.$common.checkResult(response.data))
          .then(result => this.entityList = result.data.entities)
      },
      handleQuery: function () {
        //将查询表单参数赋值给查询参数
        this.query = {
          ...this.queryForm
        }
        if (this.query.projectId != parseInt(this.projectId)) {
          this.$router.push(`/project/${this.query.projectId}/mtm`)
        }
        this.queryEntity(this.queryForm.projectId)
          .then(() => this.doQuery())
      },
      // 列表查询
      doQuery: function () {
        if (!this.query.projectId) {
          return
        }
        this.loading = true
        this.$ajax.get('/generate/meta_mtm/list', {params:this.query})
          .then(response => this.$common.checkResult(response.data))
          .then(result => this.entities = result.data)
          .catch(error => this.$common.showNotifyError(error))
          .finally(() => this.loading = false)
      },
      handleAdd: function () {
        this.$router.push(`/project/${this.projectId}/mtm/add`)
      },
      handleEdit: function (row) {
        this.$router.push(`/project/${this.projectId}/mtm/edit/${row.mtmId}`)
      },
      handleShow: function (row) {
        this.$router.push(`/project/${this.projectId}/mtm/show/${row.mtmId}`)
      }
    },
    activated: function () {
      this.queryForm.projectId = parseInt(this.projectId)
      this.query.projectId = this.queryForm.projectId
      this.queryProject()
      this.queryEntity(this.queryForm.projectId)
        .then(() => this.doQuery())
    }
  }
</script>
<style>
  .mtmList .activeNum {
    min-width: 160px;
    text-align: left;
    padding: 0 0 0 20px;
  }

  /**
   * 调整表格行高
   */
  .mtmList .el-table td{
    padding: 3px 0;
  }
</style>
