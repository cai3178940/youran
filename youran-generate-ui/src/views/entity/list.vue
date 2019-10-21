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
          <el-form-item>
            <el-select v-model="queryForm.projectId" @change="handleQuery" placeholder="请选择项目">
              <el-option
                v-for="item in projectList"
                :key="item.projectId"
                :label="item.projectDesc"
                :value="item.projectId">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button @click.native="handleAdd" type="success">添加实体</el-button>
            <el-button @click.native="handleMtmAdd" type="success">添加多对多</el-button>
            <el-button @click.native="handleErDiagram" type="primary">查看ER图</el-button>
            <el-button @click.native="handleDel" type="danger">删除</el-button>
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>
    <el-table :data="list" style="width: 100%" :border="true"
              @selection-change="selectionChange" v-loading="loading">
      <el-table-column type="selection" width="50"></el-table-column>
      <el-table-column label="实体名">
        <template v-slot="scope">
          {{ scope.row.title }}
          <template v-for="mtm in scope.row.mtms">
            <el-dropdown @command="handleMtmCommand" :key="mtm.mtmId" size="mini" placement="bottom-start" trigger="click" style="margin-left:5px;cursor:pointer;">
              <span @mouseover="setActiveMtm(mtm)" @mouseout="clearActiveMtm()"
                    :class="[
                      'mtm_span',
                      (activeMtmId==mtm.mtmId)?'mtmActive':'',
                      ((mtm.holdRefer1==1&&mtm.entityId1==scope.row.entityId)||(mtm.holdRefer2==1&&mtm.entityId2==scope.row.entityId))?'mtm_hold_span':'mtm_unhold_span'
                    ]"
                title="多对多">
                {{mtm.tableName}}
              </span>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item :command="{method:'handleMtmDel',arg:[mtm]}">
                  <icon name="trash-alt" scale="0.7" color="red"></icon> 删除多对多
                </el-dropdown-item>
                <el-dropdown-item :command="{method:'handleMtmEdit',arg:[mtm]}">
                  <icon name="edit" scale="0.7" color="red"></icon> 编辑多对多
                </el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
          </template>
        </template>
      </el-table-column>
      <el-table-column property="className" label="类名"></el-table-column>
      <el-table-column property="tableName" label="表名"></el-table-column>
      <el-table-column label="分页" width="60px">
        <template v-slot="scope">
          <icon v-if="scope.row.pageSign==1" name="check" class="table-cell-icon color-success"></icon>
          <icon v-else name="times" class="table-cell-icon color-danger"></icon>
        </template>
      </el-table-column>
      <el-table-column property="desc" label="描述"></el-table-column>
      <el-table-column
        label="操作"
        width="200">
        <template v-slot="scope">
          <!--<el-button @click="handleShow(scope.row)" type="text" size="medium">查看</el-button>-->
          <el-button @click="handleEdit(scope.row)" type="text" size="medium">编辑</el-button>
          <el-button @click="handleField(scope.row)" type="text" size="medium">字段管理</el-button>
          <el-button @click="handleSqlPreview(scope.row)" type="text" size="medium">sql预览</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog title="sql预览" :visible.sync="sqlPreviewVisible" width="50%">
      <el-input :readonly="true" v-model="sqlPreview" type="textarea" :autosize="{ minRows: 10, maxRows: 1000}"></el-input>
      <div slot="footer" class="dialog-footer">
        <el-button @click="sqlPreviewVisible = false">关 闭</el-button>
      </div>
    </el-dialog>

    <er-diagram ref="erDiagram"></er-diagram>
  </div>
</template>

<script>
import { apiPath } from '@/components/common'
import ErDiagram from './erDiagram'

export default {
  name: 'entityList',
  components: { ErDiagram },
  props: ['projectId'],
  data () {
    return {
      sqlPreview: '',
      sqlPreviewVisible: false,
      // 查询参数
      query: {
        projectId: null
      },
      // 查询表单参数
      queryForm: {
        projectId: null
      },
      projectList: [],
      activeNum: 0,
      selectItems: [],
      list: [],
      activeMtmId: null,
      mtms: [],
      loading: false
    }
  },
  watch: {
    mtms (value) {
      if (!value) {
        return
      }
      // 首先将每个entity中的mtms置空
      this.list.forEach(entity => {
        entity.mtms = []
      })
      value.forEach(mtm => {
        const entity1 = this.list.find(entity => entity.entityId === mtm.entityId1)
        const entity2 = this.list.find(entity => entity.entityId === mtm.entityId2)
        if (entity1) {
          entity1.mtms.push(mtm)
        }
        if (entity2) {
          entity2.mtms.push(mtm)
        }
      })
    }
  },
  methods: {
    setActiveMtm (mtm) {
      this.activeMtmId = mtm.mtmId
    },
    clearActiveMtm () {
      this.activeMtmId = null
    },
    selectionChange (val) {
      this.selectItems = val
      this.activeNum = this.selectItems.length
    },
    handleDel () {
      if (this.activeNum <= 0) {
        this.$common.showMsg('warning', '请选择实体')
        return
      }
      this.$common.confirm('是否确认删除')
        .then(() => this.$ajax.put(`/${apiPath}/meta_entity/deleteBatch`, this.selectItems.map(entity => entity.entityId)))
        .then(response => this.$common.checkResult(response))
        .then(() => this.doQuery())
        .then(() => this.doQueryMtm())
        .catch(error => this.$common.showNotifyError(error))
    },
    queryProject () {
      this.loading = true
      return this.$common.getProjectOptions()
        .then(response => this.$common.checkResult(response))
        .then(data => { this.projectList = data })
        .finally(() => { this.loading = false })
    },
    handleQuery () {
      // 将查询表单参数赋值给查询参数
      this.query = {
        ...this.queryForm
      }
      if (this.query.projectId !== parseInt(this.projectId)) {
        this.$router.push(`/project/${this.query.projectId}/entity`)
      }
      this.doQuery()
        .then(() => this.doQueryMtm())
    },
    // 列表查询
    doQuery () {
      if (!this.query.projectId) {
        return
      }
      this.loading = true
      return this.$ajax.get(`/${apiPath}/meta_entity/list`, { params: this.query })
        .then(response => this.$common.checkResult(response))
        .then(data => {
          data.forEach(value => {
            value.mtms = []
          })
          this.list = data
        })
        .catch(error => this.$common.showNotifyError(error))
        .finally(() => { this.loading = false })
    },
    // 多对多查询
    doQueryMtm () {
      if (!this.query.projectId) {
        return
      }
      this.loading = true
      return this.$ajax.get(`/${apiPath}/meta_mtm/list`, { params: { projectId: this.query.projectId } })
        .then(response => this.$common.checkResult(response))
        .then(data => { this.mtms = data })
        .catch(error => this.$common.showNotifyError(error))
        .finally(() => { this.loading = false })
    },
    handleMtmCommand (command) {
      this[command.method](...command.arg)
    },
    handleAdd () {
      this.$router.push(`/project/${this.projectId}/entity/add`)
    },
    handleMtmAdd () {
      if (!this.selectItems.length || this.selectItems.length !== 2) {
        this.$common.showMsg('warning', '请选择两个实体')
        return
      }
      const entityIds = this.selectItems.map(entity => entity.entityId).join('-')
      this.$router.push(`/project/${this.projectId}/entity/mtmAdd/${entityIds}`)
    },
    handleMtmEdit (mtm) {
      this.$router.push(`/project/${this.projectId}/entity/mtmEdit/${mtm.mtmId}`)
    },
    handleMtmDel (mtm) {
      this.$common.confirm(`请确认是否删除多对多【${mtm.tableName}】`)
        .then(() => this.$ajax.delete(`/${apiPath}/meta_mtm/${mtm.mtmId}`))
        .then(response => this.$common.checkResult(response))
        .then(() => this.doQueryMtm())
        .catch(error => this.$common.showNotifyError(error))
    },
    handleErDiagram () {
      if (!this.selectItems.length) {
        this.$common.showMsg('warning', '请选择需要查看的实体')
        return
      }
      const entityIds = this.selectItems.map(entity => entity.entityId)
      this.$refs.erDiagram.show(this.projectId, entityIds)
    },
    handleField (row) {
      this.$router.push(`/project/${this.projectId}/entity/${row.entityId}/field`)
    },
    handleEdit (row) {
      this.$router.push(`/project/${this.projectId}/entity/edit/${row.entityId}`)
    },
    handleShow (row) {
      this.$router.push(`/project/${this.projectId}/entity/show/${row.entityId}`)
    },
    handleSqlPreview (row) {
      this.$ajax.get(`/${apiPath}/code_gen/sqlPreview?entityId=${row.entityId}`, { responseType: 'text' })
        .then(response => this.$common.checkResult(response))
        .then(data => {
          this.sqlPreview = data
          this.sqlPreviewVisible = true
        })
        .catch(error => this.$common.showNotifyError(error))
    },
    handleCommand (command) {
      this[command.method](command.arg)
    }
  },
  activated () {
    this.queryProject()
      .then(() => {
        this.queryForm.projectId = parseInt(this.projectId)
        this.query.projectId = this.queryForm.projectId
      })
      .then(() => this.doQuery())
      .then(() => this.doQueryMtm())
  }
}
</script>
<style lang="scss">
  @import '../../assets/common.scss';
  $hold-color: #ff1507;
  $hold-back-color: #ebd9f7;
  $unhold-color: #ff8d08;
  $unhold-back-color: #fff2ce;
  $active-color: #7a8cf5;

  .entityList {
    .activeNum {
      min-width: 160px;
      text-align: left;
      padding: 0 0 0 20px;
    }

    /**
     * 调整表格行高
     */
    .el-table td {
      padding: $el-table-padding;
    }

    .mtm_span {
      font-size: 10px;
      //border-radius: 4px;
      padding: 3px;
      border: 2px solid transparent;
      //margin: 1px;
    }

    .mtm_hold_span {
      color: $hold-color;
      background-color: $hold-back-color;
    }

    .mtm_hold_span.mtmActive {
      border-color: $active-color;
    }

    .mtm_unhold_span {
      color: $unhold-color;
      background-color: $unhold-back-color;
    }

    .mtm_unhold_span.mtmActive {
      border-color: $active-color;
    }

  }
</style>
