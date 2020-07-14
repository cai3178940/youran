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
            <el-button @click.native="handleDel" type="danger">删除图表</el-button>
            <el-button @click.native="handleAddDashboard" type="primary">创建看板</el-button>
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>
    <el-table :data="list" style="width: 100%" :border="true"
              @selection-change="selectionChange" v-loading="loading">
      <el-table-column type="selection" width="50"></el-table-column>
      <el-table-column property="chartName" label="图表名称"></el-table-column>
      <el-table-column property="title" label="图表标题"></el-table-column>
      <el-table-column label="类型" width="200px">
        <template v-slot="scope">
          <template v-for="chartType in [getChartType(scope.row)]">
            <el-tooltip :key="chartType.value" class="item" effect="dark" :content="chartType.label" placement="right">
              <svg-icon :className="'dropdown-icon '+chartType.class"
                        :iconClass="chartType.icon">
              </svg-icon>
            </el-tooltip>
          </template>
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
    <el-table v-if="dashboardList.length" :data="dashboardList"
              style="width: 100%;margin-top: 20px;" :border="true"
              v-loading="loading">
      <el-table-column property="name" label="看板名称"></el-table-column>
      <el-table-column property="title" label="看板标题"></el-table-column>
      <el-table-column property="module" label="模块名"></el-table-column>
      <el-table-column
        label="操作"
        width="200">
        <template v-slot="scope">
          <el-button @click="handleEditDashboard(scope.row)" type="text" size="medium">编辑</el-button>
          <el-button @click="handleDeleteDashboard(scope.row)" type="text" size="medium">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import projectApi from '@/api/project'
import chartApi from '@/api/chart/chart'
import dashboardApi from '@/api/chart/dashboard'
import chartTypeUtil from '@/utils/options-chart-type'

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
      dashboardList: [],
      loading: false
    }
  },
  methods: {
    getChartType (chart) {
      return chartTypeUtil.getChartTypeOption(chart.chartType)
    },
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
      this.doQueryDashboard()
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
    // 看板列表查询
    doQueryDashboard () {
      if (!this.query.projectId) {
        return
      }
      this.loading = true
      return dashboardApi.getList(this.query.projectId)
        .then(data => {
          this.dashboardList = data
        })
        .catch(error => this.$common.showNotifyError(error))
        .finally(() => { this.loading = false })
    },
    handleAdd (chartTypeName) {
      this.$router.push(`/project/${this.projectId}/chart/${chartTypeName}/add`)
    },
    handleEdit (row) {
      const chartType = chartTypeUtil.chartTypeOptions.find(op => op.value === row.chartType)
      this.$router.push(`/project/${this.projectId}/chart/${chartType.name}/edit/${row.chartId}?sourceId=${row.sourceId}`)
    },
    handleAddDashboard () {
      this.$router.push(`/project/${this.projectId}/chart/dashboard/add`)
    },
    handleEditDashboard (row) {
      this.$router.push(`/project/${this.projectId}/chart/dashboard/edit/${row.dashboardId}`)
    },
    handleDeleteDashboard (row) {
      this.$common.confirm('是否确认删除')
        .then(() => dashboardApi.deleteSingle(row.dashboardId))
        .then(() => this.doQueryDashboard())
        .catch(error => this.$common.showNotifyError(error))
    }
  },
  activated () {
    this.queryProject()
      .then(() => {
        this.queryForm.projectId = parseInt(this.projectId)
        this.query.projectId = this.queryForm.projectId
      })
      .then(() => {
        this.doQuery()
        this.doQueryDashboard()
      })
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
