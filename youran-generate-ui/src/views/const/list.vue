<template>
  <div class="constList">
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/project' }">项目管理</el-breadcrumb-item>
      <el-breadcrumb-item>枚举管理</el-breadcrumb-item>
    </el-breadcrumb>
    <el-row type="flex" align="middle" :gutter="20" style="padding:10px 0;">
      <el-col :span="6" class="activeNum">
        已选择{{ activeNum }}个枚举
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
            <el-button @click.native="handleAdd" type="success">添加枚举</el-button>
            <el-button @click.native="handleDel" type="danger">删除枚举</el-button>
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>
    <el-table ref="constTable" :data="page.entities" style="width: 100%" @selection-change="selectionChange" @expand-change="expandChange" v-loading="loading">
      <el-table-column type="expand" width="50">
        <template slot-scope="scope">
          <el-table class="detailTable" :data="detailList" v-loading="detailLoading" :show-header="false">
            <el-table-column width="100"></el-table-column>
            <el-table-column property="detailRemark" label="备注" width="250"></el-table-column>
            <el-table-column property="detailName" label="枚举字段名" width="250"></el-table-column>
            <el-table-column property="detailValue" label="枚举值"></el-table-column>
            <el-table-column
              label="操作"
              width="150">
              <template slot-scope="detail">
                <el-button @click="handleDetailEdit(scope.row,detail.row)" type="text" size="medium">编辑</el-button>
                <el-button @click="handleDetailDel(scope.row,detail.row)" type="text" size="medium">删除</el-button>
              </template>
            </el-table-column>
          </el-table>
        </template>
      </el-table-column>
      <el-table-column type="selection" width="50"></el-table-column>
      <el-table-column property="constRemark" label="枚举名称 / 值描述" width="250"></el-table-column>
      <el-table-column property="constName" label="枚举类名 / 字段名" width="250"></el-table-column>
      <el-table-column label="类型 / 枚举值">
        <template slot-scope="scope">
          {{ scope.row.constType | optionLabel('constTypeOptions')}}
        </template>
      </el-table-column>
      <el-table-column
        label="操作"
        width="150">
        <template slot-scope="scope">
          <!--<el-button @click="handleShow(scope.row)" type="text" size="medium">查看</el-button>-->
          <el-button @click="handleEdit(scope.row)" type="text" size="medium">编辑</el-button>
          <el-button @click="handleDetailAdd(scope.row)" type="text" size="medium">添加枚举值</el-button>
          <!--<el-button @click="handleConstDetail(scope.row)" type="text" size="medium">枚举值管理</el-button>-->
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
  </div>
</template>

<script>
import options from '@/components/options'
import { apiPath } from '@/components/common'

export default {
  name: 'constList',
  props: ['projectId', 'constId'],
  data () {
    return {
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
      page: {
        pageNo: 1,
        entityCount: 0,
        pageSize: 20,
        entities: []
      },
      expandedRow: null,
      loading: false,
      detailList: [],
      detailLoading: false
    }
  },
  filters: {
    optionLabel (value, optionType) {
      const ops = options[optionType]
      for (const op of ops) {
        if (op.value === value) {
          return op.label
        }
      }
      return null
    }
  },
  methods: {
    selectionChange (val) {
      this.selectItems = val
      this.activeNum = this.selectItems.length
    },
    handleDel () {
      if (this.activeNum <= 0) {
        this.$common.showMsg('warning', '请选择枚举')
        return
      }
      this.$common.confirm('是否确认删除')
        .then(() => this.$ajax.put(`/${apiPath}/meta_const/deleteBatch`, this.selectItems.map(c => c.constId)))
        .then(response => this.$common.checkResult(response))
        .then(() => this.doQuery())
        .catch(error => this.$common.showNotifyError(error))
    },
    sizeChange (pageSize) {
      this.page.pageSize = pageSize
      this.doQuery()
    },
    currentChange (pageNo) {
      this.page.pageNo = pageNo
      this.doQuery()
    },
    queryProject () {
      return this.$common.getProjectOptions()
        .then(response => this.$common.checkResult(response))
        .then(data => { this.projectList = data })
    },
    handleQuery () {
      // 将查询表单参数赋值给查询参数
      this.query = {
        ...this.queryForm
      }
      if (this.query.projectId !== parseInt(this.projectId)) {
        this.$router.push(`/project/${this.query.projectId}/const`)
      }
      this.doQuery()
    },
    // 列表查询
    doQuery () {
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
      return this.$ajax.get(`/${apiPath}/meta_const/list`, { params: params })
        .then(response => this.$common.checkResult(response))
        .then(data => { this.page = data })
        .catch(error => this.$common.showNotifyError(error))
        .finally(() => { this.loading = false })
    },
    handleAdd () {
      this.$router.push(`/project/${this.projectId}/const/add`)
    },
    handleEdit (row) {
      this.$router.push(`/project/${this.projectId}/const/edit/${row.constId}`)
    },
    handleShow (row) {
      this.$router.push(`/project/${this.projectId}/const/show/${row.constId}`)
    },
    expandChange (row, expandedRows) {
      // 如果当前展开大于1行，则将另一行关闭
      if (expandedRows && expandedRows.length > 1) {
        const otherRow = expandedRows.find(r => r !== row)
        this.$refs.constTable.toggleRowExpansion(otherRow, false)
      }
      // 如果当前展开行等于1行，则加载枚举值列表
      if (expandedRows && expandedRows.length === 1 && this.expandedRow !== row) {
        this.doDetailQuery(expandedRows[0].constId)
        this.expandedRow = expandedRows[0]
      }
      // 如果当前展开行等于0行，则清空缓存数据
      if (!expandedRows || expandedRows.length === 0) {
        this.expandedRow = null
        this.detailList = []
      }
    },
    // 枚举值列表查询
    doDetailQuery (constId) {
      this.detailLoading = true
      this.$ajax.get(`/${apiPath}/meta_const_detail/list`, { params: { 'projectId': this.query.projectId, 'constId': constId } })
        .then(response => this.$common.checkResult(response))
        .then(data => { this.detailList = data })
        .catch(error => this.$common.showNotifyError(error))
        .finally(() => { this.detailLoading = false })
    },
    handleDetailAdd (row) {
      this.$router.push(`/project/${this.projectId}/const/${row.constId}/constDetailAdd`)
    },
    handleDetailEdit (theConst, detail) {
      this.$router.push(`/project/${this.projectId}/const/${theConst.constId}/constDetailEdit/${detail.constDetailId}`)
    },
    handleDetailDel (theConst, detail) {
      this.$common.confirm('是否确认删除枚举值')
        .then(() => this.$ajax.put(`/${apiPath}/meta_const_detail/deleteBatch`, [detail.constDetailId]))
        .then(response => this.$common.checkResult(response))
        .then(() => this.doDetailQuery(theConst.constId))
        .catch(error => this.$common.showNotifyError(error))
    }
  },
  activated () {
    this.queryProject()
      .then(() => {
        this.queryForm.projectId = parseInt(this.projectId)
        this.query.projectId = this.queryForm.projectId
      })
      .then(() => this.doQuery())
      .then(() => {
        if (this.constId) {
          const row = this.page.entities.find(e => e.constId === parseInt(this.constId))
          this.$refs.constTable.toggleRowExpansion(row, true)
        }
      })
  }
}
</script>
<style lang="scss">
  @import '../../assets/common.scss';

  .constList {
    .activeNum {
      min-width: 160px;
      text-align: left;
      padding: 0 0 0 20px;
    }

    .el-table {
      /**
       * 调整表格行高
       */
      td {
        padding: $el-table-padding;
      }

      .el-table__expanded-cell {
        padding: 0px;
        border-bottom: 0px;
      }
    }

    .detailTable {
      color: #9ea0a7;
    }
  }
</style>
