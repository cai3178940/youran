<template>
  <div class="detailListFormDiv">
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/project' }">项目管理</el-breadcrumb-item>
      <el-breadcrumb-item :to="{ path: `/project/${this.projectId}/chart` }">图表管理</el-breadcrumb-item>
      <el-breadcrumb-item>
        {{edit?'编辑':'添加'}}明细表
      </el-breadcrumb-item>
    </el-breadcrumb>
    <el-container v-loading="formLoading" style="border:solid 1px #e6e6e6;">
      <el-aside style="width: 400px;">
        <el-form ref="detailListForm" class="detailListForm"
                 :rules="rules" :model="form" label-width="100px"
                 size="small">
          <el-form-item label="图表名称" prop="chartName">
            <help-popover name="chart.chartName">
              <el-input v-model="form.chartName" placeholder="例如：MyFirstChart" tabindex="10"></el-input>
            </help-popover>
          </el-form-item>
          <el-form-item label="图表标题" prop="title">
            <help-popover name="chart.title">
              <el-input v-model="form.title" placeholder="例如：我的第一张图表" tabindex="20"></el-input>
            </help-popover>
          </el-form-item>
          <el-form-item label="模块名" prop="module">
            <help-popover name="chart.module">
              <el-autocomplete
                v-model="form.module"
                :fetch-suggestions="findModules"
                style="width:100%;" tabindex="30"
                placeholder="例如：system"
              ></el-autocomplete>
            </help-popover>
          </el-form-item>
          <el-form-item label="每页记录数" prop="defaultPageSize">
            <help-popover name="chart.defaultPageSize">
              <el-input-number v-model="form.defaultPageSize"
                               style="width:100%;"
                               tabindex="40"></el-input-number>
            </help-popover>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="submit()" tabindex="160">提交</el-button>
            <el-button @click="lastStep()" tabindex="180">上一步</el-button>
          </el-form-item>
        </el-form>
      </el-aside>
      <el-main style="border-left:solid 1px #e6e6e6;">
        <el-table size="small"
                  :data="emptyTableList"
                  style="width: 100%"
                  :border="true">
          <el-table-column align="center" width="70">
            <template slot="header">
              <el-button @click="addColumn(0)"
                         size="small" icon="el-icon-plus" plain>
              </el-button>
            </template>
          </el-table-column>
          <el-table-column v-for="(chartItem, index) in form.columnList"
                           :key="chartItem.sourceItemId" align="center">
            <template slot="header">
              <el-button v-if="index!==0" size="small" type="text">
                <i class="el-icon-arrow-left" style="font-size:14px"></i>
              </el-button>
              <el-dropdown size="small" trigger="click" @command="handleCommand">
                <el-button size="small">
                  {{chartItem.titleAlias}}
                  <i class="el-icon-arrow-down el-icon--right"></i>
                </el-button>
                <el-dropdown-menu slot="dropdown">
                  <el-dropdown-item :command="{method:'editColumn',arg: [chartItem, index]}">
                    <svg-icon className="dropdown-icon color-primary" iconClass="setting"></svg-icon>
                    设置
                  </el-dropdown-item>
                  <el-dropdown-item :command="{method:'removeColumn',arg: [chartItem, index]}">
                    <svg-icon className="dropdown-icon color-danger" iconClass="delete"></svg-icon>
                    删除
                  </el-dropdown-item>
                </el-dropdown-menu>
              </el-dropdown>
              <el-button v-if="index < form.columnList.length-1" size="small" type="text">
                <i class="el-icon-arrow-right" style="font-size:14px"></i>
              </el-button>
            </template>
            <template v-slot="scope">
              {{mockTableData(scope.row.i, chartItem)}}
            </template>
          </el-table-column>
          <el-table-column v-if="form.columnList.length" align="center" width="70">
            <template slot="header">
              <el-button @click="addColumn(form.columnList.length)"
                         size="small" icon="el-icon-plus" plain>
              </el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-main>
    </el-container>
  </div>
</template>

<script>
import projectApi from '@/api/project'
import fieldApi from '@/api/field'
import detailListApi from '@/api/chart/detailList'
import chartSourceApi from '@/api/chart/chartSource'
import {
  initDetailListFormBean,
  repairChartForm,
  mockTableData,
  getRules
} from './detailListModel'
import { initSourceFormBean } from './sourceModel'
import { initChartItemByDetailColumn } from './chartItemModel'
import searchUtil from './searchUtil'

export default {
  name: 'detailListForm',
  props: [
    'projectId',
    'chartId'
  ],
  data () {
    const edit = !!this.chartId
    return {
      edit: edit,
      form: initDetailListFormBean(this.projectId),
      sourceForm: initSourceFormBean(this.projectId),
      chartType: {
        value: 1,
        label: '',
        name: '',
        aggregation: false
      },
      formLoading: false,
      rules: getRules()
    }
  },
  computed: {
    emptyTableList () {
      const size = this.form.defaultPageSize > 100 ? 100 : this.form.defaultPageSize
      return Array.from({ length: size }, (v, i) => ({ i: i }))
    }
  },
  methods: {
    mockTableData: mockTableData,
    findModules (queryString, cb) {
      const action = () => {
        const entityModules = this.entityModules.slice(0)
        const results = queryString ? entityModules.filter(
          c => c.toLowerCase().indexOf(queryString.toLowerCase()) === 0
        ) : entityModules
        cb(results.map(c => ({ value: c })))
      }
      if (this.entityModules) {
        action()
      } else {
        projectApi.findModules(this.projectId)
          .then(data => {
            this.entityModules = data
            action()
          })
      }
    },
    handleCommand: function (command) {
      this[command.method](command.arg)
    },
    removeColumn ([chartItem, index]) {
      console.info(chartItem)
    },
    editColumn ([chartItem, index]) {
      console.info(chartItem)
    },
    addColumn () {
      console.info(arguments)
    },
    submit () {
      // todo
    },
    lastStep () {
      if (this.edit) {
        this.$router.push(`/project/${this.projectId}/chart/detailList/edit/${this.chartId}?sourceId=${this.form.sourceId}`)
      } else {
        this.$router.push(`/project/${this.projectId}/chart/detailList/add?sourceId=${this.form.sourceId}`)
      }
    },
    /**
     * 根据数据源中的明细列构建图表项
     */
    buildChartItems () {
      this.form.columnList = this.sourceForm.detailColumnList
        .map(detailColumn => initChartItemByDetailColumn(detailColumn))
    },
    /**
     * 加载数据源及明细列字段详情
     */
    loadSourceWithDetailColumnFields () {
      return chartSourceApi.getWithItems(this.form.sourceId)
        .then(formBean => {
          this.sourceForm = formBean
          // 获取明细列对应的字段id
          const fieldIds = searchUtil.findFieldIdsInDetailColumns(formBean.detailColumnList)
          // 从后端加载这些字段的详细信息
          return fieldApi.getListByFieldIds(fieldIds)
        })
        .then(fieldList => {
          // 将字段详情放入每个明细列中
          this.sourceForm.detailColumnList.forEach(detailColumn => {
            detailColumn.field = fieldList.find(field => field.fieldId === detailColumn.fieldId)
          })
        })
    },
    repairChartForm () {
      repairChartForm(this.form, this.sourceForm)
    }
  },
  created () {
    if (this.edit) {
      detailListApi.get(this.chartId)
        .then(formBean => {
          formBean.columnList.forEach(value => {
            value.detailColumn = {}
          })
          this.form = formBean
        })
        .then(() => this.loadSourceWithDetailColumnFields())
        .then(() => this.repairChartForm())
    } else {
      this.form.sourceId = this.$router.currentRoute.query.sourceId
      if (this.form.sourceId) {
        this.loadSourceWithDetailColumnFields()
          .then(() => this.buildChartItems())
      } else {
        this.$common.showNotifyError('sourceId为空')
      }
    }
  }
}
</script>

<style lang="scss">
  @import '../../assets/common.scss';
  .detailListFormDiv .detailListForm {
    @include youran-form;
    padding: 20px 10px;
  }

</style>
