<template>
  <div class="aggTableFormDiv">
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/project' }">项目管理</el-breadcrumb-item>
      <el-breadcrumb-item :to="{ path: `/project/${this.projectId}/chart` }">图表管理</el-breadcrumb-item>
      <el-breadcrumb-item>
        {{edit?'编辑':'创建'}}聚合表
      </el-breadcrumb-item>
    </el-breadcrumb>
    <el-steps :active="2" simple>
      <el-step title="第一步：配置数据源" icon="el-icon-coin"></el-step>
      <el-step title="第二步：配置聚合表" icon="el-icon-set-up"></el-step>
    </el-steps>
    <el-container v-loading="formLoading" style="border:solid 1px #e6e6e6;">
      <el-aside style="width: 400px;">
        <el-form ref="aggTableForm" class="aggTableForm"
                 :rules="rules" :model="form" label-width="100px"
                 size="small">
          <el-form-item label="图表名称" prop="chartName">
            <help-popover name="chart.chartName">
              <el-input v-upper-case-first v-model="form.chartName"
                        placeholder="例如：MyFirstChart" tabindex="10"></el-input>
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
                v-lower-case
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
          <el-form-item label="导出" prop="excelExport">
            <help-popover name="chart.excelExport">
              <el-checkbox v-model="form.excelExport" tabindex="50">
                支持excel导出
              </el-checkbox>
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
          <el-table-column v-for="chartItem in form.dimensionList"
                           :key="chartItem.sourceItemId" align="center"
                           min-width="180" class-name="head-column">
            <template slot="header" slot-scope="scope">
              <el-button v-if="scope.$index" @click="moveLeft(chartItem,form.dimensionList)" size="small" type="text">
                <i class="el-icon-arrow-left" style="font-size:14px"></i>
              </el-button>
              <el-dropdown size="small" trigger="click" @command="handleCommand">
                <el-button size="small">
                  {{chartItem.titleAlias|truncate(7)}}
                  <i class="el-icon-arrow-down el-icon--right"></i>
                </el-button>
                <el-dropdown-menu slot="dropdown">
                  <el-dropdown-item :command="{method:'editColumn',arg: chartItem}">
                    <svg-icon className="dropdown-icon color-primary" iconClass="setting"></svg-icon>
                    配置
                  </el-dropdown-item>
                </el-dropdown-menu>
              </el-dropdown>
              <el-button v-if="scope.$index < form.dimensionList.length - 1"
                         @click="moveRight(chartItem,form.dimensionList)" size="small" type="text">
                <i class="el-icon-arrow-right" style="font-size:14px"></i>
              </el-button>
            </template>
            <template v-slot="scope">
              {{mockDimension(chartItem, scope.row.i, constDetails, false)}}
            </template>
          </el-table-column>
          <el-table-column v-for="chartItem in form.metricsList"
                           :key="chartItem.sourceItemId" align="center"
                           min-width="180" class-name="head-column">
            <template slot="header" slot-scope="scope">
              <el-button v-if="scope.$index-form.dimensionList.length" @click="moveLeft(chartItem,form.metricsList)" size="small" type="text">
                <i class="el-icon-arrow-left" style="font-size:14px"></i>
              </el-button>
              <el-dropdown size="small" trigger="click" @command="handleCommand">
                <el-button size="small">
                  {{chartItem.titleAlias|truncate(7)}}
                  <i class="el-icon-arrow-down el-icon--right"></i>
                </el-button>
                <el-dropdown-menu slot="dropdown">
                  <el-dropdown-item :command="{method:'editColumn',arg: chartItem}">
                    <svg-icon className="dropdown-icon color-primary" iconClass="setting"></svg-icon>
                    配置
                  </el-dropdown-item>
                </el-dropdown-menu>
              </el-dropdown>
              <el-button v-if="scope.$index - form.dimensionList.length < form.metricsList.length - 1"
                         @click="moveRight(chartItem,form.metricsList)" size="small" type="text">
                <i class="el-icon-arrow-right" style="font-size:14px"></i>
              </el-button>
            </template>
            <template v-slot="scope">
              {{mockMetrics(chartItem, scope.row.i)}}
            </template>
          </el-table-column>
        </el-table>
      </el-main>
    </el-container>
    <chart-item-form ref="chartItemForm"></chart-item-form>
  </div>
</template>

<script>
import Vue from 'vue'
import fieldApi from '@/api/field'
import aggTableApi from '@/api/chart/aggTable'
import chartSourceApi from '@/api/chart/chartSource'
import modulesMixin from '@/components/Mixins/modules'
import constDetailMixin from '@/components/Mixins/const-detail'
import chartItemForm from '../item/chartItemForm'
import _differenceBy from 'lodash/differenceBy'
import _intersectionBy from 'lodash/intersectionBy'
import _truncate from 'lodash/truncate'
import _uniq from 'lodash/uniq'
import model from './model'
import sourceModel from '../sourceModel'
import dimensionModel from '../item/dimensionModel'
import metricsModel from '../item/metricsModel'
import searchUtil from '../searchUtil'
import chartItemMock from '../item/chartItemMock'

export default {
  name: 'aggTableForm',
  props: [
    'projectId',
    'chartId'
  ],
  mixins: [modulesMixin, constDetailMixin],
  components: {
    chartItemForm
  },
  data () {
    const edit = !!this.chartId
    return {
      edit: edit,
      form: model.initAggTableFormBean(this.projectId),
      sourceForm: sourceModel.initSourceFormBean(this.projectId),
      formLoading: false,
      rules: model.getRules()
    }
  },
  computed: {
    emptyTableList () {
      const size = this.form.defaultPageSize > 100 ? 100 : this.form.defaultPageSize
      return Array.from({ length: size }, (v, i) => ({ i: i }))
    }
  },
  filters: {
    truncate (value, length) {
      return _truncate(value, { length })
    }
  },
  methods: {
    mockDimension: chartItemMock.mockDimension,
    mockMetrics: chartItemMock.mockMetrics,
    handleCommand: function (command) {
      this[command.method](command.arg)
    },
    editColumn (chartItem) {
      this.$refs.chartItemForm.show(chartItem)
    },
    moveLeft (chartItem, arr) {
      const index = arr.indexOf(chartItem)
      if (index > 0) {
        const tmp = arr[index]
        arr.splice(index, 1)
        Vue.nextTick(() => arr.splice(index - 1, 0, tmp))
      }
    },
    moveRight (chartItem, arr) {
      const index = arr.indexOf(chartItem)
      if (index < arr.length - 1) {
        const tmp = arr[index]
        arr.splice(index, 1)
        Vue.nextTick(() => arr.splice(index + 1, 0, tmp))
      }
    },
    submit () {
      let loading = null
      // 校验表单
      return this.$refs.aggTableForm.validate()
        // 提交表单
        .then(() => {
          loading = this.$loading()
          return aggTableApi.saveOrUpdate(this.form, this.edit)
        })
        // 执行页面跳转
        .then(() => {
          this.$common.showMsg('success', '操作成功')
          this.$router.push(`/project/${this.projectId}/chart`)
        })
        .catch(error => this.$common.showNotifyError(error))
        .finally(() => {
          if (loading) {
            loading.close()
          }
        })
    },
    lastStep () {
      if (this.edit) {
        this.$router.push(`/project/${this.projectId}/chart/aggTable/edit/${this.chartId}?sourceId=${this.form.sourceId}`)
      } else {
        this.$router.push(`/project/${this.projectId}/chart/aggTable/add?sourceId=${this.form.sourceId}`)
      }
    },
    /**
     * 加载数据源及维度列和指标列字段详情
     */
    loadSourceWithDimensionMetricsFields () {
      return chartSourceApi.getWithItems(this.form.sourceId)
        .then(formBean => {
          this.sourceForm = formBean
          // 获取维度和指标对应的字段id
          const fieldIds = searchUtil.findFieldIdsInDimensionAndMetrics(
            formBean.dimensionList, formBean.metricsList)
          // 从后端加载这些字段的详细信息
          return fieldApi.getListByFieldIds(fieldIds)
        })
        .then(fieldList => {
          // 将字段详情放入每个维度中
          this.sourceForm.dimensionList.forEach(dimension => {
            dimension.field = fieldList.find(field => field.fieldId === dimension.fieldId)
            dimensionModel.repairDimensionForEdit(dimension, this.sourceForm)
          })
          // 将字段详情放入每个指标中
          this.sourceForm.metricsList.forEach(metrics => {
            metrics.field = fieldList.find(field => field.fieldId === metrics.fieldId)
            metricsModel.repairMetricsForEdit(metrics, this.sourceForm)
          })
        })
        .then(() => {
          const constNames = _uniq(this.sourceForm.dimensionList
            .map(dimension => dimension.field.dicType)
            .filter(t => t))
          // 加载常量值
          return this.loadConstDetail(this.projectId, ...constNames)
        })
    },
    /**
     * 修复创建表单数据
     */
    repairAddChartForm () {
      this.form.dimensionList = this.sourceForm.dimensionList
        .map(dimension => model.initChartItemByDimension(dimension))
      this.form.metricsList = this.sourceForm.metricsList
        .map(metrics => model.initChartItemByMetrics(metrics))
    },
    /**
     * 修复编辑表单数据
     */
    repairEditChartForm () {
      // 获取以前的维度图表项和数据源维度的交集
      const interDimension = _intersectionBy(this.form.dimensionList, this.sourceForm.dimensionList, 'sourceItemId')
      // 给interDimension的每项，注入dimension
      interDimension.forEach(chartItem => {
        chartItem.dimension = this.sourceForm.dimensionList.find(dimension => dimension.sourceItemId === chartItem.sourceItemId)
      })
      // 找出上一步新增的维度，并映射成维度图表项
      const dimensionToAdd = _differenceBy(this.sourceForm.dimensionList, this.form.dimensionList, 'sourceItemId')
        .map(dimension => model.initChartItemByDimension(dimension))
      // 插入上一步新增的维度
      interDimension.push(...dimensionToAdd)
      this.form.dimensionList = interDimension

      // 获取以前的指标图表项和数据源指标的交集
      const interMetrics = _intersectionBy(this.form.metricsList, this.sourceForm.metricsList, 'sourceItemId')
      // 给interMetrics的每项，注入metrics
      interMetrics.forEach(chartItem => {
        chartItem.metrics = this.sourceForm.metricsList.find(metrics => metrics.sourceItemId === chartItem.sourceItemId)
      })
      // 对比数据源和当前表单中的metrics,并处理差异
      const metricsToAdd = _differenceBy(this.sourceForm.metricsList, this.form.metricsList, 'sourceItemId')
        .map(metrics => model.initChartItemByMetrics(metrics))
      interMetrics.push(...metricsToAdd)
      this.form.metricsList = interMetrics
    }
  },
  created () {
    this.formLoading = true
    if (this.edit) {
      aggTableApi.get(this.chartId)
        .then(formBean => {
          formBean.dimensionList.forEach(value => { value.dimension = {} })
          formBean.metricsList.forEach(value => { value.metrics = {} })
          this.form = formBean
        })
        .then(() => this.loadSourceWithDimensionMetricsFields())
        .then(() => this.repairEditChartForm())
        .catch(error => this.$common.showNotifyError(error))
        .finally(() => {
          this.formLoading = false
        })
    } else {
      this.form.sourceId = this.$router.currentRoute.query.sourceId
      if (this.form.sourceId) {
        this.loadSourceWithDimensionMetricsFields()
          .then(() => this.repairAddChartForm())
          .catch(error => this.$common.showNotifyError(error))
          .finally(() => {
            this.formLoading = false
          })
      } else {
        this.formLoading = false
        this.$common.showNotifyError('sourceId为空')
      }
    }
  }
}
</script>

<style lang="scss">
  @import '../../../assets/common.scss';
  .aggTableFormDiv {
    .aggTableForm {
      @include youran-form;
      padding: 20px 10px;
    }
    .head-column {
      .cell {
        padding-left: 0px;
        padding-right: 0px;
      }
    }
  }
</style>
