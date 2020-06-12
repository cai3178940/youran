<template>
  <div class="pieFormDiv">
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/project' }">项目管理</el-breadcrumb-item>
      <el-breadcrumb-item :to="{ path: `/project/${this.projectId}/chart` }">图表管理</el-breadcrumb-item>
      <el-breadcrumb-item>
        {{edit?'编辑':'创建'}}饼图
      </el-breadcrumb-item>
    </el-breadcrumb>
    <el-steps :active="2" simple>
      <el-step title="第一步：配置数据源" icon="el-icon-coin"></el-step>
      <el-step title="第二步：配置饼图" icon="el-icon-set-up"></el-step>
    </el-steps>
    <el-container v-loading="formLoading" style="border:solid 1px #e6e6e6;">
      <el-aside style="width: 400px;">
        <el-form ref="pieForm" class="pieForm"
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
          <el-form-item label="维度" prop="dimension">
            <help-popover name="chart.dimension">
              <!-- 下拉框 -->
              <el-col :span="20" class="col-left">
                <el-select v-model="form.dimension" value-key="sourceItemId"
                           style="width:100%;" placeholder="请选择维度"
                           @change="renderChart"
                           filterable>
                  <el-option v-for="dimension in sourceForm.dimensionList"
                             :key="dimension.key" :label="dimension | displayDimension"
                             :value="initChartItemByDimension(dimension)">
                  </el-option>
                </el-select>
              </el-col>
              <!-- 设置按钮 -->
              <el-col :span="4" class="col-right">
                <el-button :disabled="!form.dimension" size="small" type="text"
                           style="padding-left: 5px;" @click="editItem(form.dimension)">
                  <svg-icon iconClass="setting"></svg-icon>
                </el-button>
              </el-col>
            </help-popover>
          </el-form-item>
          <el-form-item label="指标" prop="metrics">
            <help-popover name="chart.metrics">
              <!-- 下拉框 -->
              <el-col :span="20" class="col-left">
                <el-select v-model="form.metrics" value-key="sourceItemId"
                           style="width:100%;" placeholder="请选择指标"
                           @change="renderChart"
                           filterable>
                  <el-option v-for="metrics in sourceForm.metricsList"
                             :key="metrics.key" :label="metrics | displayMetrics"
                             :value="initChartItemByMetrics(metrics)">
                  </el-option>
                </el-select>
              </el-col>
              <!-- 设置按钮 -->
              <el-col :span="4" class="col-right">
                <el-button :disabled="!form.metrics" size="small" type="text"
                           style="padding-left: 5px;" @click="editItem(form.metrics)">
                  <svg-icon iconClass="setting"></svg-icon>
                </el-button>
              </el-col>
            </help-popover>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="submit()" tabindex="160">提交</el-button>
            <el-button @click="lastStep()" tabindex="180">上一步</el-button>
          </el-form-item>
        </el-form>
      </el-aside>
      <el-main style="border-left:solid 1px #e6e6e6;">
        <pie-chart ref="pieChart"></pie-chart>
      </el-main>
    </el-container>
    <chart-item-form ref="chartItemForm" @submit="renderChart"></chart-item-form>
  </div>
</template>

<script>
import fieldApi from '@/api/field'
import pieApi from '@/api/chart/pie'
import chartSourceApi from '@/api/chart/chartSource'
import modulesMixin from '@/components/Mixins/modules'
import chartItemForm from '../item/chartItemForm'
import model from './model'
import sourceModel from '../sourceModel'
import searchUtil from '../searchUtil'
import dimensionModel from '../item/dimensionModel'
import metricsModel from '../item/metricsModel'
import pieChart from './chart'

export default {
  name: 'pieForm',
  props: [
    'projectId',
    'chartId'
  ],
  mixins: [modulesMixin],
  components: {
    chartItemForm,
    pieChart
  },
  data () {
    const edit = !!this.chartId
    return {
      edit: edit,
      form: model.initPieFormBean(this.projectId),
      sourceForm: sourceModel.initSourceFormBean(this.projectId),
      formLoading: false,
      rules: model.getRules()
    }
  },
  filters: {
    displayDimension: dimensionModel.displayText,
    displayMetrics: metricsModel.displayText
  },
  methods: {
    initChartItemByDimension: model.initChartItemByDimension,
    initChartItemByMetrics: model.initChartItemByMetrics,
    editItem (chartItem) {
      this.$refs.chartItemForm.show(chartItem)
    },
    submit () {
      let loading = null
      // 校验表单
      return this.$refs.pieForm.validate()
        // 提交表单
        .then(() => {
          loading = this.$loading()
          return pieApi.saveOrUpdate(this.form, this.edit)
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
        this.$router.push(`/project/${this.projectId}/chart/pie/edit/${this.chartId}?sourceId=${this.form.sourceId}`)
      } else {
        this.$router.push(`/project/${this.projectId}/chart/pie/add?sourceId=${this.form.sourceId}`)
      }
    },
    /**
     * 加载数据源及维度列和指标列字段详情
     */
    loadSourceWithDimensionMetricsFields () {
      return chartSourceApi.getWithItems(this.form.sourceId)
        .then(sourceForm => {
          // 获取维度指标对应的字段id
          const fieldIds = searchUtil.findFieldIdsInDimensionAndMetrics(sourceForm.dimensionList, sourceForm.metricsList)
          // 从后端加载这些字段的详细信息
          return fieldApi.getListByFieldIds(fieldIds)
            .then(fieldList => {
              // 将字段详情放入每个维度中
              sourceForm.dimensionList.forEach(dimension => {
                dimension.field = fieldList.find(field => field.fieldId === dimension.fieldId)
                dimensionModel.repairDimensionForEdit(dimension, sourceForm)
              })
              // 将字段详情放入每个指标中
              sourceForm.metricsList.forEach(metrics => {
                metrics.field = fieldList.find(field => field.fieldId === metrics.fieldId)
                metricsModel.repairMetricsForEdit(metrics, sourceForm)
              })
              this.sourceForm = sourceForm
            })
        })
    },
    /**
     * 修复编辑表单数据
     */
    repairEditChartForm () {
      const dimension = this.sourceForm.dimensionList
        .find(dimension => dimension.sourceItemId === this.form.dimension.sourceItemId)
      if (dimension) {
        this.form.dimension.dimension = dimension
      } else {
        this.form.dimension = null
      }
      const metrics = this.sourceForm.metricsList
        .find(metrics => metrics.sourceItemId === this.form.metrics.sourceItemId)
      if (metrics) {
        this.form.metrics.metrics = metrics
      } else {
        this.form.metrics = null
      }
    },
    renderChart () {
      this.$refs.pieChart.renderChart(this.form.dimension, this.form.metrics)
    }
  },
  created () {
    this.formLoading = true
    if (this.edit) {
      pieApi.get(this.chartId)
        .then(formBean => {
          if (formBean.dimension) {
            formBean.dimension.dimension = {}
          }
          if (formBean.metrics) {
            formBean.metrics.metrics = {}
          }
          this.form = formBean
        })
        .then(() => this.loadSourceWithDimensionMetricsFields())
        .then(() => {
          this.repairEditChartForm()
          this.renderChart()
        })
        .catch(error => this.$common.showNotifyError(error))
        .finally(() => {
          this.formLoading = false
        })
    } else {
      this.form.sourceId = this.$router.currentRoute.query.sourceId
      if (this.form.sourceId) {
        this.loadSourceWithDimensionMetricsFields()
          .then(() => this.renderChart())
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
  .pieFormDiv .pieForm {
    @include youran-form;
    padding: 20px 10px;
  }

</style>
