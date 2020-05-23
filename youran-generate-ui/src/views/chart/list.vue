<template>
  <div class="chartList">
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/project' }">项目管理</el-breadcrumb-item>
      <el-breadcrumb-item>图表管理</el-breadcrumb-item>
    </el-breadcrumb>
    <el-row type="flex" align="middle" :gutter="20" style="padding:10px 0;">
      <el-col :span="6" class="activeNum">
        已选择{{ activeNum }}个图表
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
            <el-dropdown size="small" trigger="click" @command="handleAdd" style="margin-left:10px;">
              <el-button type="success" style="margin: 0 10px 0 0;">
                创建图表<i class="el-icon-arrow-down el-icon--right"></i>
              </el-button>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item command="detailList">
                  <svg-icon className="dropdown-icon color-warning" iconClass="detail-list"></svg-icon>
                  明细表
                </el-dropdown-item>
                <el-dropdown-item command="aggTable">
                  <svg-icon className="dropdown-icon color-primary" iconClass="agg-table"></svg-icon>
                  聚合表
                </el-dropdown-item>
                <el-dropdown-item command="barLine">
                  <svg-icon className="dropdown-icon color-success" iconClass="chart"></svg-icon>
                  柱线图
                </el-dropdown-item>
                <el-dropdown-item command="pie">
                  <svg-icon className="dropdown-icon color-danger" iconClass="pie-chart"></svg-icon>
                  饼图
                </el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
            <el-button @click.native="handleDel" type="danger">删除</el-button>
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>
    <el-table :data="list" style="width: 100%" :border="true"
              @selection-change="selectionChange" v-loading="loading">
      <el-table-column type="selection" width="50"></el-table-column>
      <el-table-column property="chartName" label="名称"></el-table-column>
      <el-table-column property="title" label="标题"></el-table-column>
      <el-table-column label="类型" width="200px">
        <template v-slot="scope">
          {{ scope.row.chartType | optionLabel('chartTypeOptions')}}
        </template>
      </el-table-column>
      <el-table-column property="module" label="模块名"></el-table-column>
      <el-table-column
        label="操作"
        width="200">
        <template v-slot="scope">
          <el-button @click="handleEdit(scope.row)" type="text" size="medium">编辑</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import projectApi from '@/api/project'
import chartApi from '@/api/chart/chart'
import chartOptions from '@/utils/options-chart'

export default {
  name: 'chartList',
  props: ['projectId'],
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
      list: [],
      loading: false
    }
  },
  filters: {
    optionLabel (value, optionType) {
      const ops = chartOptions[optionType]
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
        this.$common.showMsg('warning', '请选择图表')
        return
      }
      this.$common.confirm('是否确认删除')
        .then(() => chartApi.deleteBatch(this.selectItems.map(chart => chart.chartId)))
        .then(() => this.doQuery())
        .catch(error => this.$common.showNotifyError(error))
    },
    queryProject () {
      this.loading = true
      return projectApi.getList()
        .then(data => { this.projectList = data })
        .finally(() => { this.loading = false })
    },
    handleQuery () {
      // 将查询表单参数赋值给查询参数
      this.query = {
        ...this.queryForm
      }
      if (this.query.projectId !== parseInt(this.projectId)) {
        this.$router.push(`/project/${this.query.projectId}/chart`)
      }
      this.doQuery()
    },
    // 列表查询
    doQuery () {
      if (!this.query.projectId) {
        return
      }
      this.loading = true
      return chartApi.getList(this.query.projectId)
        .then(data => {
          this.list = data
        })
        .catch(error => this.$common.showNotifyError(error))
        .finally(() => { this.loading = false })
    },
    handleAdd (chartTypeName) {
      this.$router.push(`/project/${this.projectId}/chart/${chartTypeName}/add`)
    },
    handleEdit (row) {
      const chartType = chartOptions.chartTypeOptions.find(op => op.value === row.chartType)
      this.$router.push(`/project/${this.projectId}/chart/${chartType.name}/edit/${row.chartId}?sourceId=${row.sourceId}`)
    }
  },
  activated () {
    this.queryProject()
      .then(() => {
        this.queryForm.projectId = parseInt(this.projectId)
        this.query.projectId = this.queryForm.projectId
      })
      .then(() => this.doQuery())
  }
}
</script>
<style lang="scss">
  @import '../../assets/common.scss';
  .chartList {
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

  }

</style>
