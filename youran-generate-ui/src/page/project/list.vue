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
        <el-button @click.native="handleAdd" type="success">创建项目</el-button>
        <el-button @click.native="handleDel" type="danger">删除</el-button>
      </el-col>
    </el-row>

    <el-table :data="entities" style="width: 100%" @selection-change="selectionChange" v-loading="loading">
      <el-table-column type="selection" width="50"></el-table-column>
      <el-table-column property="groupId" label="groupId" width="160"></el-table-column>
      <el-table-column property="projectName" label="项目标识" width="200"></el-table-column>
      <el-table-column property="projectDesc" label="项目名称" width="200"></el-table-column>
      <el-table-column property="author" label="作者" width="120"></el-table-column>
      <el-table-column property="packageName" label="包名"></el-table-column>
      <el-table-column label="启用Git仓库" width="120px">
        <template slot-scope="scope">
          <icon v-if="scope.row.remote==1" name="check" class="color-success"></icon>
          <icon v-if="scope.row.remote!=1" name="close" class="color-danger"></icon>
        </template>
      </el-table-column>
      <el-table-column
        label="操作"
        width="100">
        <template slot-scope="scope">
          <!--<el-button @click="handleShow(scope.row)" type="text" size="medium">查看</el-button>
          <el-button @click="handleEdit(scope.row)" type="text" size="medium">编辑</el-button>-->
          <el-dropdown trigger="click" @command="handleCommand" style="margin-left:10px;">
            <span class="el-dropdown-link">
              操作<i class="el-icon-arrow-down el-icon--right"></i>
            </span>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item :command="{method:'handleEdit',arg:scope.row}" >
                <icon name="edit" scale="0.8" ></icon> 编辑
              </el-dropdown-item>
              <el-dropdown-item :command="{method:'handleEntity',arg:scope.row}" >
                <icon name="cubes" scale="0.8" ></icon> 实体管理
              </el-dropdown-item>
              <el-dropdown-item :command="{method:'handleConst',arg:scope.row}" >
                <icon name="navicon" scale="0.8" ></icon> 枚举管理
              </el-dropdown-item>
              <el-dropdown-item :command="{method:'handleReverseEngineering',arg:scope.row}" >
                <icon name="object-group" scale="0.8" ></icon> 反向工程
              </el-dropdown-item>
              <el-dropdown-item :command="{method:'handleGenCode',arg:scope.row}" >
                <icon name="file-zip-o" scale="0.8" ></icon> 生成代码
              </el-dropdown-item>
              <el-dropdown-item :command="{method:'handleGenSql',arg:scope.row}" >
                <icon name="file-code-o" scale="0.8" ></icon> 生成sql
              </el-dropdown-item>
              <el-dropdown-item v-if="scope.row.remote==1" :command="{method:'handleCommit',arg:scope.row}" >
                <icon name="git" scale="0.8" ></icon> 提交Git
              </el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </template>
      </el-table-column>
    </el-table>


    <el-dialog title="反向工程" :visible.sync="reverseEngineeringFormVisible" width="60%">
      <el-form ref="reverseEngineeringForm" :model="reverseEngineeringForm" :rules="reverseEngineeringFormRules">
        <el-form-item label="脚本语言：" label-width="100px">
          <el-radio-group v-model="reverseEngineeringForm.dbType">
            <el-radio border label="mysql">MySql</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="DDL脚本：" label-width="100px" prop="ddl">
          <el-input v-model="reverseEngineeringForm.ddl" type="textarea" :rows="10"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="reverseEngineeringFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="handleReverseEngineeringCheck">校 验</el-button>
        <el-button type="success" @click="handleReverseEngineeringSubmit">反向生成</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
  import {apiPath} from '@/components/common'

  export default {
    name: 'projectList',
    data: function () {
      return {
        query: {},
        activeNum: 0,
        selectItems: [],
        entities: [],
        loading: false,
        reverseEngineeringFormVisible: false,
        reverseEngineeringForm: {
          projectId: null,
          dbType: 'mysql',
          ddl: ''
        },
        reverseEngineeringFormRules: {
          ddl: [
            {required: true, message: '请输入DDL脚本', trigger: 'blur'},
            {max: 10000, message: '长度不能超过10000个字符', trigger: 'blur'}
          ]
        }
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
          .then(() => this.$ajax.put(`/${apiPath}/meta_project/deleteBatch`, this.selectItems.map(entity => entity.projectId)))
          .then(() => this.doQuery())
      },
      // 列表查询
      doQuery: function () {
        this.loading = true
        this.$ajax.get(`/${apiPath}/meta_project/list`, {params: this.query})
          .then(response => this.$common.checkResult(response.data))
          .then(result => { this.entities = result.data })
          .catch(error => this.$common.showNotifyError(error))
          .finally(() => { this.loading = false })
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
        window.open(`${this.$common.BASE_API_URL}/${apiPath}/code_gen/genSql?projectId=${row.projectId}`)
      },
      handleGenCode: function (row) {
        window.open(`${this.$common.BASE_API_URL}/${apiPath}/code_gen/genCode?projectId=${row.projectId}`)
      },
      handleReverseEngineering: function (row) {
        this.reverseEngineeringFormVisible = true
        this.reverseEngineeringForm.projectId = row.projectId
        this.reverseEngineeringForm.ddl = ''
      },
      handleReverseEngineeringCheck: function () {
        let loading = null
        // 校验表单
        this.$refs.reverseEngineeringForm.validate()
        // 提交表单
          .then(() => {
            loading = this.$loading()
            return this.$ajax.post(`/${apiPath}/reverse_engineering/check`, this.reverseEngineeringForm)
          })
          // 校验返回结果
          .then(response => this.$common.checkResult(response.data))
          .then(() => {
            this.$common.showMsg('success', '校验通过')
          })
          .catch(error => this.$common.showNotifyError(error))
          .finally(() => {
            if (loading) {
              loading.close()
            }
          })
      },
      handleReverseEngineeringSubmit: function () {
        let loading = null
        // 校验表单
        this.$refs.reverseEngineeringForm.validate()
        // 提交表单
          .then(() => {
            loading = this.$loading()
            return this.$ajax.post(`/${apiPath}/reverse_engineering/execute`, this.reverseEngineeringForm)
          })
        // 校验返回结果
          .then(response => this.$common.checkResult(response.data))
          .then(() => {
            this.$common.showMsg('success', '执行成功')
            this.reverseEngineeringFormVisible = false
          })
          .catch(error => this.$common.showNotifyError(error))
          .finally(() => {
            if (loading) {
              loading.close()
            }
          })
      },
      handleCommit: function (row) {
        this.loading = true
        this.$ajax.get(`/${apiPath}/code_gen/gitCommit?projectId=${row.projectId}`)
          .then(response => this.$common.checkResult(response.data))
          .then(result => this.$common.showMsg('success', result.message))
          .catch(error => this.$common.showNotifyError(error))
          .finally(() => { this.loading = false })
      },
      handleCommand: function (command) {
        this[command.method](command.arg)
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

  /**
   * 调整表格行高
   */
  .projectList .el-table td{
    padding: 6px 0;
  }
</style>
