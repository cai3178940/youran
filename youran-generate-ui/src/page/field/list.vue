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
          <el-form-item label="实体">
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
            <el-button @click.native="addTemplateFormVisible = true;templateForm.template=''" type="success">添加</el-button>
            <el-button @click.native="handleDel" type="danger">删除</el-button>
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>
    <el-table :data="entities" style="width: 100%" @selection-change="selectionChange" v-loading="loading">
      <el-table-column type="selection" width="50"></el-table-column>
      <el-table-column property="fieldDesc" label="字段描述"></el-table-column>
      <el-table-column label="字段名">
        <template slot-scope="scope">
          {{ scope.row.jfieldName }} / {{ scope.row.fieldName }}
        </template>
      </el-table-column>
      <el-table-column label="字段类型" width="150px">
        <template slot-scope="scope">
          {{ scope.row.jfieldType | optionLabel('jfieldTypeOptions')}}
          / {{ scope.row.fieldType | optionLabel('fieldTypeOptions') }}
        </template>
      </el-table-column>
      <el-table-column property="fieldLength" label="字段长度" width="80px"></el-table-column>
      <el-table-column label="非空" width="50px">
        <template slot-scope="scope">
          <icon v-if="scope.row.notNull==1" name="check" class="color-success"></icon>
          <icon v-if="scope.row.notNull!=1" name="close" class="color-danger"></icon>
        </template>
      </el-table-column>
      <el-table-column label="主键" width="50px">
        <template slot-scope="scope">
          <icon v-if="scope.row.primaryKey==1" name="key" class="color-warning"></icon>
        </template>
      </el-table-column>
      <el-table-column property="fieldExample" label="字段示例"></el-table-column>
      <el-table-column
        label="操作"
        width="100">
        <template slot-scope="scope">
          <el-button @click="handleShow(scope.row)" type="text" size="small">查看</el-button>
          <el-button @click="handleEdit(scope.row)" type="text" size="small">编辑</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog title="请选择字段模板" :visible.sync="addTemplateFormVisible" width="30%">
      <el-form :model="templateForm">
        <el-form-item label="请选择：" label-width="100px">
          <el-select v-model="templateForm.template">
            <el-option label="不使用模板" value=""></el-option>
            <el-option v-for="(value,key) in fieldTemplate"
                       :key="key"
                       :label="key"
                       :value="key"></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="addTemplateFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="handleAdd">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import options from '@/components/options.js'
  import fieldTemplate from '@/components/fieldTemplate.js'
  export default {
    name: 'fieldList',
    props: ['projectId', 'entityId'],
    data: function () {
      return {
        addTemplateFormVisible: false,
        fieldTemplate,
        templateForm: {
          template: '',
        },
        //查询参数
        query: {
          projectId: null,
          entityId: null
        },
        //查询表单参数
        queryForm: {
          projectId: null,
          entityId: null
        },
        projectList: [],
        entityList: [],
        activeNum: 0,
        selectItems: [],
        entities: [],
        loading: false
      }
    },
    filters: {
      optionLabel: function (value, optionType) {
        var ops = options[optionType];
        for (var op of ops) {
          if (op.value == value) {
            return op.label
          }
        }
        return null
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
          .then(() => this.$ajax.post('/generate/meta_field/deleteBatch', this.selectItems.map(field => field.fieldId)))
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
      //项目选择框修改时，清空实体选择框
      projectChange: function () {
        this.queryForm.entityId = null
        this.queryEntity(this.queryForm.projectId)
      },
      handleQuery: function () {
        //将查询表单参数赋值给查询参数
        this.query = {
          ...this.queryForm
        }
        if (this.query.entityId == null) {
          this.$common.showNotifyError('请选择实体')
          return
        }
        if (this.query.entityId != parseInt(this.entityId)) {
          this.$router.push(`/project/${this.query.projectId}/entity/${this.query.entityId}/field`)
        }
        this.doQuery()
      },
      // 列表查询
      doQuery: function () {
        if (!this.query.projectId || !this.query.entityId) {
          return
        }
        this.loading = true
        this.$ajax.post('/generate/meta_field/list', this.query)
          .then(response => this.$common.checkResult(response.data))
          .then(result => this.entities = result.data)
          .catch(error => this.$common.showNotifyError(error))
          .finally(() => this.loading = false)
      },
      handleAdd: function () {
        this.addTemplateFormVisible = false
        this.$router.push(`/project/${this.projectId}/entity/${this.entityId}/field/add?template=${this.templateForm.template}`)
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
        .then(() => {
          this.queryForm.projectId = parseInt(this.projectId)
          this.queryForm.entityId = parseInt(this.entityId)
          this.query = {
            ...this.queryForm
          }
        })
        .then(() => this.doQuery())
    }
  }
</script>
<style>
  .fieldList .activeNum {
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
