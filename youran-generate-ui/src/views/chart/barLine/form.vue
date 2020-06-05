<template>
  <div class="barLineFormDiv">
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/project' }">项目管理</el-breadcrumb-item>
      <el-breadcrumb-item :to="{ path: `/project/${this.projectId}/chart` }">图表管理</el-breadcrumb-item>
      <el-breadcrumb-item>
        {{edit?'编辑':'添加'}}柱线图
      </el-breadcrumb-item>
    </el-breadcrumb>
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
          <el-form-item label="x轴(维度)" prop="axisX">
            <help-popover name="chart.axisX">
              <!-- 下拉框 -->
              <el-col :span="15" class="col-left">
                <el-select v-model="form.axisX" value-key="sourceItemId"
                           style="width:100%;" placeholder="请选择维度"
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
                           style="padding-left: 5px;" @click="editItem(form.axisX)">
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
                           style="padding-left: 5px;" @click="editItem(form.axisX2)">
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
                           style="padding-left: 5px;" @click="editItem(form.axisYList[0])">
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
                           style="padding-left: 5px;" @click="editItem(form.axisYList[i])">
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
      </el-main>
    </el-container>
    <column-form ref="columnForm"></column-form>
  </div>
</template>

<script>
import projectApi from '@/api/project'
import fieldApi from '@/api/field'
import barLineApi from '@/api/chart/barLine'
import chartSourceApi from '@/api/chart/chartSource'
import columnForm from '../item/columnForm'
import model from './model'
import sourceModel from '../sourceModel'
import searchUtil from '../searchUtil'
import dimensionModel from '../item/dimensionModel'
import metricsModel from '../item/metricsModel'

export default {
  name: 'barLineForm',
  props: [
    'projectId',
    'chartId'
  ],
  components: {
    columnForm
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
  computed: {
    emptyTableList () {
      const size = this.form.defaultPageSize > 100 ? 100 : this.form.defaultPageSize
      return Array.from({ length: size }, (v, i) => ({ i: i }))
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
    },
    removeAxisY (i) {
      this.form.axisYList.splice(i, 1)
      this.axisYListVisible = this.axisYListVisible - 1
    },
    editItem (chartItem) {
      this.$refs.columnForm.show(chartItem)
    },
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
    }
  },
  created () {
    this.formLoading = true
    if (this.edit) {
      barLineApi.get(this.chartId)
        .then(formBean => {
          if (formBean.axisX) {
            formBean.axisX.dimension = {}
          }
          if (formBean.axisX2) {
            formBean.axisX2.dimension = {}
          }
          formBean.axisYList.forEach(value => { value.metrics = {} })
          this.form = formBean
        })
        .then(() => this.loadSourceWithDimensionMetricsFields())
        .catch(error => this.$common.showNotifyError(error))
        .finally(() => {
          this.formLoading = false
        })
    } else {
      this.form.sourceId = this.$router.currentRoute.query.sourceId
      if (this.form.sourceId) {
        this.loadSourceWithDimensionMetricsFields()
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
