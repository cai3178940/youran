<template>
  <div class="projectList">
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item>项目管理</el-breadcrumb-item>
    </el-breadcrumb>

    <el-row>
      <el-col style="padding:10px 0px;" :span="5" v-for="(o, index) in entities" :key="o.projectId" :offset="1">
        <el-card :body-style="{padding: '0px',height: '180px'}">
          <div class="display">
            <div class="field">
              <span class="label">名称:</span>
              <span class="value">{{o.projectName}}</span>
            </div>
            <div class="field">
              <span class="label">作者:</span>
              <span class="value">{{o.author}}</span>
            </div>
            <div class="field">
              <span class="label">包名:</span>
              <span class="value">{{o.packageName}}</span>
            </div>
          </div>
          <div class="buttons">
            <el-button @click="handleEntity(o)" type="text" size="mini">实体管理</el-button>
            <el-button @click="handleConst(o)" type="text" size="mini">枚举管理</el-button>
            <el-dropdown style="float:right;" split-button type="text" size="mini" @command="handleCommand">
              <span class="el-dropdown-link">
                操作
              </span>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item :command="[handleShow,o]">查看</el-dropdown-item>
                <el-dropdown-item :command="[handleEdit,o]">编辑</el-dropdown-item>
                <el-dropdown-item :command="[handleDel,o]">删除</el-dropdown-item>
                <el-dropdown-item :command="[handleGenCode,o]">生成代码</el-dropdown-item>
                <el-dropdown-item :command="[handleGenSql,o]">生成sql</el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
          </div>
        </el-card>
      </el-col>

      <el-col style="padding:10px 0px;" :span="5" :offset="1">
        <el-card :body-style="{padding: '0px',height: '180px'}">
          <div class="display">

          </div>
          <div class="buttons">
            <el-button @click="handleAdd" type="text" size="small">添加</el-button>
          </div>
        </el-card>
      </el-col>
    </el-row>


  </div>
</template>

<script>

  export default {
    name: 'projectList',
    data: function () {
      return {
        query: {},
        entities: [],
        loading: false
      }
    },
    methods: {
      handleDel: function (row) {
        this.$common.confirm('是否确认删除')
          .then(() => this.$ajax.delete(`/generate/meta_project/${row.projectId}`))
          .then(() => this.doQuery())
      },
      // 列表查询
      doQuery: function () {
        this.loading = true
        this.$ajax.post('/generate/meta_project/list', this.query)
          .then(response => this.$common.checkResult(response.data))
          .then(result => this.entities = result.data)
          .catch(error => this.$common.showNotifyError(error))
          .finally(() => this.loading = false)
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
      },
      handleCommand: function (a) {
        a[0](a[1])
      }
    },
    activated: function () {
      this.doQuery()
    }
  }
</script>
<style>

  .projectList .display {
    padding: 4px 0px;
    height: 90px;
  }

  .projectList .buttons {
    padding: 20px;
  }

  .projectList .display .field {
    padding: 4px 10px;
  }

  .projectList .display .field .label {
    font-weight: bold;
    display: block;
    text-align: right;
    float: left;
    width: 50px;
    padding-right: 10px;
  }

  .projectList .display .field .value {
    display: block;
    text-align: left;
    padding: 3px 0;
  }
</style>
