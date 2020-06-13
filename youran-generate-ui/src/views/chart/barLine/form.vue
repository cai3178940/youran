<template>
  <div class="barLineFormDiv">
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/project' }">项目管理</el-breadcrumb-item>
      <el-breadcrumb-item :to="{ path: `/project/${this.projectId}/chart` }">图表管理</el-breadcrumb-item>
      <el-breadcrumb-item>
        {{edit?'编辑':'创建'}}柱线图
      </el-breadcrumb-item>
    </el-breadcrumb>
    <el-steps :active="2" simple>
      <el-step title="第一步：配置数据源" icon="el-icon-coin"></el-step>
      <el-step title="第二步：配置柱线图" icon="el-icon-set-up"></el-step>
    </el-steps>
    <el-container v-loading="formLoading" style="border:solid 1px #e6e6e6;">
      <el-aside style="width: 400px;">
        <el-form ref="barLineForm" class="barLineForm"
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
              <el-input v-model="form.title" @change="renderChart"
                        placeholder="例如：我的第一张图表" tabindex="20"></el-input>
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
          <el-form-item label="x轴(维度)" prop="axisX">
            <help-popover name="chart.axisX">
              <!-- 下拉框 -->
              <el-col :span="15" class="col-left">
                <el-select v-model="form.axisX" value-key="sourceItemId"
                           style="width:100%;" placeholder="请选择维度"
                           @change="renderChart"
                           filterable>
                  <el-option v-for="dimension in sourceForm.dimensionList"
                             :disabled="dimensionOptionDisabled(form.axisX2, dimension)"
                             :key="dimension.key" :label="dimension | displayDimension"
                             :value="initChartItemByDimension(dimension)">
                  </el-option>
                </el-select>
              </el-col>
              <!-- 设置按钮 -->
              <el-col :span="4" class="col-inner" style="text-align: left;">
                <el-button :disabled="!form.axisX" size="small" type="text"
                           style="padding-left: 5px;" @click="editItem(form.axisX,{seriesType:false})">
                  <svg-icon iconClass="setting"></svg-icon>
                </el-button>
              </el-col>
              <!-- 添加附加维度 -->
              <el-col :span="5" class="col-right">
                <el-button v-if="!axisX2Visible && axisYListVisible === 0 && sourceForm.dimensionList.length > 1"
                           size="small" type="text" @click="axisX2Visible = true">
                  添加维度
                </el-button>
              </el-col>
            </help-popover>
          </el-form-item>
          <el-form-item v-if="axisX2Visible" label="附加(维度)" prop="axisX2">
            <help-popover name="chart.axisX2">
              <!-- 下拉框 -->
              <el-col :span="15" class="col-left">
                <el-select v-model="form.axisX2" value-key="sourceItemId"
                           style="width:100%;" placeholder="请选择维度"
                           @change="renderChart"
                           filterable>
                  <el-option v-for="dimension in sourceForm.dimensionList"
                             :disabled="dimensionOptionDisabled(form.axisX, dimension)"
                             :key="dimension.key" :label="dimension | displayDimension"
                             :value="initChartItemByDimension(dimension)">
                  </el-option>
                </el-select>
              </el-col>
              <!-- 设置按钮 -->
              <el-col :span="4" class="col-inner" style="text-align: left;">
                <el-button :disabled="!form.axisX2" size="small" type="text"
                           style="padding-left: 5px;" @click="editItem(form.axisX2,{seriesType:false})">
                  <svg-icon iconClass="setting"></svg-icon>
                </el-button>
              </el-col>
              <!-- 移除附加维度 -->
              <el-col :span="5" class="col-right">
                <el-button size="small" type="text" @click="removeAxisX2">移除维度</el-button>
              </el-col>
            </help-popover>
          </el-form-item>
          <el-form-item label="y轴(指标)" prop="axisYList">
            <help-popover name="chart.axisYList">
              <!-- 下拉框 -->
              <el-col :span="15" class="col-left">
                <el-select v-model="form.axisYList[0]" value-key="sourceItemId"
                           style="width:100%;" placeholder="请选择指标"
                           @change="renderChart"
                           filterable>
                  <el-option v-for="metrics in sourceForm.metricsList"
                             :disabled="metricsOptionDisabled(0, metrics)"
                             :key="metrics.key" :label="metrics | displayMetrics"
                             :value="initChartItemByMetrics(metrics)">
                  </el-option>
                </el-select>
              </el-col>
              <!-- 设置按钮 -->
              <el-col :span="4" class="col-inner" style="text-align: left;">
                <el-button :disabled="!form.axisYList[0]" size="small" type="text"
                           style="padding-left: 5px;" @click="editItem(form.axisYList[0],{seriesType:true})">
                  <svg-icon iconClass="setting"></svg-icon>
                </el-button>
              </el-col>
              <!-- 增加指标 -->
              <el-col :span="5" class="col-right">
                <el-button v-if="!axisX2Visible && sourceForm.metricsList.length > axisYListVisible+1"
                           size="small" type="text" @click="axisYListVisible ++">
                  添加指标
                </el-button>
              </el-col>
            </help-popover>
          </el-form-item>
          <el-form-item v-for="i in axisYListVisible"
                        :key="i"
                        label="y轴(指标)">
            <help-popover name="chart.axisYList2">
              <!-- 下拉框 -->
              <el-col :span="15" class="col-left">
                <el-select v-model="form.axisYList[i]" value-key="sourceItemId"
                           style="width:100%;" placeholder="请选择指标"
                           @change="renderChart"
                           filterable>
                  <el-option v-for="metrics in sourceForm.metricsList"
                             :disabled="metricsOptionDisabled(i, metrics)"
                             :key="metrics.key" :label="metrics | displayMetrics"
                             :value="initChartItemByMetrics(metrics)">
                  </el-option>
                </el-select>
              </el-col>
              <!-- 设置按钮 -->
              <el-col :span="4" class="col-inner" style="text-align: left;">
                <el-button :disabled="!form.axisYList[i]" size="small" type="text"
                           style="padding-left: 5px;" @click="editItem(form.axisYList[i],{seriesType:true})">
                  <svg-icon iconClass="setting"></svg-icon>
                </el-button>
              </el-col>
              <!-- 移除指标 -->
              <el-col :span="5" class="col-right">
                <el-button size="small" type="text" @click="removeAxisY(i)">
                  移除指标
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
        <bar-line-chart ref="barLineChart"></bar-line-chart>
      </el-main>
    </el-container>
    <chart-item-form ref="chartItemForm" @submit="renderChart"></chart-item-form>
  </div>
</template>

<script>
import fieldApi from '@/api/field'
import barLineApi from '@/api/chart/barLine'
import chartSourceApi from '@/api/chart/chartSource'
import modulesMixin from '@/components/Mixins/modules'
import chartItemForm from '../item/chartItemForm'
import model from './model'
import sourceModel from '../sourceModel'
import searchUtil from '../searchUtil'
import dimensionModel from '../item/dimensionModel'
import metricsModel from '../item/metricsModel'
import barLineChart from './chart'

export default {
  name: 'barLineForm',
  props: [
    'projectId',
    'chartId'
  ],
  mixins: [modulesMixin],
  components: {
    chartItemForm,
    barLineChart
  },
  data () {
    const edit = !!this.chartId
    return {
      edit: edit,
      axisX2Visible: false,
      axisYListVisible: 0,
      form: model.initBarLineFormBean(this.projectId),
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
    dimensionOptionDisabled (otherAxisX, dimension) {
      if (otherAxisX && otherAxisX.dimension === dimension) {
        return true
      }
      return false
    },
    metricsOptionDisabled (i, metrics) {
      const currentItem = this.form.axisYList[i]
      if (currentItem && currentItem.metrics === metrics) {
        return false
      }
      return !!this.form.axisYList.find(item => item.metrics === metrics)
    },
    removeAxisX2 () {
      this.form.axisX2 = null
      this.axisX2Visible = false
      this.renderChart()
    },
    removeAxisY (i) {
      this.form.axisYList.splice(i, 1)
      this.axisYListVisible = this.axisYListVisible - 1
      this.renderChart()
    },
    editItem (chartItem, visible) {
      this.$refs.chartItemForm.show(chartItem, visible)
    },
    submit () {
      let loading = null
      // 校验表单
      return this.$refs.barLineForm.validate()
        // 提交表单
        .then(() => {
          loading = this.$loading()
          return barLineApi.saveOrUpdate(this.form, this.edit)
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
        this.$router.push(`/project/${this.projectId}/chart/barLine/edit/${this.chartId}?sourceId=${this.form.sourceId}`)
      } else {
        this.$router.push(`/project/${this.projectId}/chart/barLine/add?sourceId=${this.form.sourceId}`)
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
      if (this.form.axisX) {
        this.form.axisX.dimension = this.sourceForm.dimensionList
          .find(dimension => dimension.sourceItemId === this.form.axisX.sourceItemId)
      }
      if (this.form.axisX2) {
        this.form.axisX2.dimension = this.sourceForm.dimensionList
          .find(dimension => dimension.sourceItemId === this.form.axisX2.sourceItemId)
      }
      this.form.axisYList.forEach(axisY => {
        axisY.metrics = this.sourceForm.metricsList
          .find(metrics => metrics.sourceItemId === axisY.sourceItemId)
      })
    },
    renderChart () {
      this.$refs.barLineChart.renderChart(this.form)
    }
  },
  created () {
    this.formLoading = true
    if (this.edit) {
      barLineApi.get(this.chartId)
        .then(formBean => {
          if (formBean.axisX2) {
            this.axisX2Visible = true
          }
          if (formBean.axisYList.length > 1) {
            this.axisYListVisible = formBean.axisYList.length - 1
          }
          this.form = formBean
        })
        .then(() => this.loadSourceWithDimensionMetricsFields())
        .then(() => this.repairEditChartForm())
        .then(() => this.renderChart())
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
  .barLineFormDiv .barLineForm {
    @include youran-form;
    padding: 20px 10px;
  }

</style>
