<template>
  <div class="entityList">
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/project' }">项目管理</el-breadcrumb-item>
      <el-breadcrumb-item>实体管理</el-breadcrumb-item>
    </el-breadcrumb>
    <el-row type="flex" align="middle" :gutter="20" style="padding:10px 0;">
      <el-col :span="6" class="activeNum">
        已选择{{ activeNum }}个实体
      </el-col>
      <el-col :span="18" style="text-align: right;">
        <el-form :inline="true" :model="queryForm" class="demo-form-inline">
          <el-form-item label="请选择项目">
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
    <el-table :data="page.entities" style="width: 100%" @selection-change="selectionChange" v-loading="loading">
      <el-table-column type="selection" width="50"></el-table-column>
      <el-table-column property="title" label="实体名"></el-table-column>
      <el-table-column property="className" label="类名"></el-table-column>
      <el-table-column property="tableName" label="表名"></el-table-column>
      <el-table-column property="desc" label="描述"></el-table-column>
      <el-table-column
        label="操作"
        width="180">
        <template slot-scope="scope">
          <el-button @click="handleField(scope.row)" type="text" size="small">字段管理</el-button>
          <el-button @click="handleShow(scope.row)" type="text" size="small">查看</el-button>
          <el-button @click="handleEdit(scope.row)" type="text" size="small">编辑</el-button>
          <el-button @click="handleSqlPreview(scope.row)" type="text" size="small">sql预览</el-button>
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
        :total="page.entityCount">
      </el-pagination>
    </el-row>


    <el-dialog title="查看sql" :visible.sync="sqlPreviewVisible" width="50%">
      <el-input :readonly="true" v-model="sqlPreview" type="textarea" :rows="20"></el-input>
      <div slot="footer" class="dialog-footer">
        <el-button @click="sqlPreviewVisible = false">关 闭</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>

  export default {
    name: 'entityList',
    props: ['projectId'],
    data: function () {
      return {
        sqlPreview:'',
        sqlPreviewVisible:false,
        //查询参数
        query: {
          projectId: null
        },
        //查询表单参数
        queryForm: {
          projectId: null
        },
        projectList: [],
        activeNum: 0,
        selectItems: [],
        page: {
          pageNo: 1,
          entityCount: 0,
          pageSize: 20,
          entities: []
        },
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
          this.$common.showMsg('warning', '请选择实体')
          return
        }
        this.$common.confirm('是否确认删除')
          .then(() => this.$ajax.post('/generate/meta_entity/deleteBatch', this.selectItems.map(entity => entity.entityId)))
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
      queryProject: function () {
        return this.$common.getProjectOptions()
          .then(response => this.$common.checkResult(response.data))
          .then(result => this.projectList = result.data)
      },
      handleQuery: function () {
        //将查询表单参数赋值给查询参数
        this.query = {
          ...this.queryForm
        }
        if (this.query.projectId != parseInt(this.projectId)) {
          this.$router.push(`/project/${this.query.projectId}/entity`)
        }
        this.doQuery()
      },
      // 列表查询
      doQuery: function () {
        if (!this.query.projectId) {
          return
        }
        // 将查询参数和分页参数合并
        const params = {
          ...this.query,
          pageNo: this.page.pageNo,
          pageSize: this.page.pageSize
        }
        this.loading = true
        this.$ajax.get('/generate/meta_entity/list', {params:params})
          .then(response => this.$common.checkResult(response.data))
          .then(result => this.page = result.data)
          .catch(error => this.$common.showNotifyError(error))
          .finally(() => this.loading = false)
      },
      handleAdd: function () {
        this.$router.push(`/project/${this.projectId}/entity/add`)
      },
      handleField: function (row) {
        this.$router.push(`/project/${this.projectId}/entity/${row.entityId}/field`)
      },
      handleEdit: function (row) {
        this.$router.push(`/project/${this.projectId}/entity/edit/${row.entityId}`)
      },
      handleShow: function (row) {
        this.$router.push(`/project/${this.projectId}/entity/show/${row.entityId}`)
      },
      handleSqlPreview: function (row) {
        this.$ajax.get(`/generate/code_gen/sqlPreview?entityId=${row.entityId}`)
          .then(response => this.$common.checkResult(response.data))
          .then(result => {
            this.sqlPreview = result.data
            this.sqlPreviewVisible = true
          })
          .catch(error => this.$common.showNotifyError(error))
      }
    },
    activated: function () {
      this.queryProject()
        .then(() => {
          this.queryForm.projectId = parseInt(this.projectId)
          this.query.projectId = this.queryForm.projectId
        })
        .then(() => this.doQuery())
    }
  }
</script>
<style>
  .entityList .activeNum {
    min-width: 160px;
    text-align: left;
    padding: 0 0 0 20px;
  }
</style>
