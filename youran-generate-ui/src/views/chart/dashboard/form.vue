<template>
  <div class="dashboardFormDiv">
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/project' }">项目管理</el-breadcrumb-item>
      <el-breadcrumb-item :to="{ path: `/project/${this.projectId}/chart` }">图表管理</el-breadcrumb-item>
      <el-breadcrumb-item>
        {{edit?'编辑':'创建'}}看板
      </el-breadcrumb-item>
    </el-breadcrumb>
    <el-container v-loading="formLoading" style="border:solid 1px #e6e6e6;">
      <el-aside style="width: 350px;">
        <el-form ref="dashboardForm" class="dashboardForm"
                 :rules="rules" :model="form" label-width="70px"
                 size="small">
          <el-form-item label="名称">
            <help-popover name="dashboard.name">
              <el-input v-upper-case-first v-model="form.name"
                        placeholder="请输入名称"></el-input>
            </help-popover>
          </el-form-item>
          <el-form-item label="标题">
            <help-popover name="dashboard.title">
              <el-input v-model="form.title" placeholder="请输入标题"></el-input>
            </help-popover>
          </el-form-item>
          <el-form-item label="模块名">
            <help-popover name="dashboard.module">
              <el-input v-model="form.module" placeholder="请输入模块名"></el-input>
            </help-popover>
          </el-form-item>
          <el-form-item label="图表">
            <help-popover name="dashboard.chartIds">
              <el-select v-model="chartIds"
                         style="width:100%;" placeholder="请选择图表"
                         @change="changeCharts"
                         collapse-tags
                         multiple filterable>
                <el-option
                  v-for="chart in chartOptions"
                  :key="chart.chartId"
                  :label="chart.title"
                  :value="chart.chartId">
                </el-option>
              </el-select>
            </help-popover>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="submit()" tabindex="160">提交</el-button>
            <el-button @click="goBack()" tabindex="180">返回</el-button>
          </el-form-item>
        </el-form>
      </el-aside>
      <el-main style="min-height:500px;border-left:solid 1px #e6e6e6;padding:0px;">
        <grid-layout
          ref="gridLayout"
          :layout.sync="form.layout"
          :col-num="12"
          :row-height="30"
          :margin="[10, 10]"
          :autoSize="true"
          :is-draggable="true"
          :is-resizable="true"
          :vertical-compact="true">
          <grid-item v-for="item in form.layout"
                     :minW="2"
                     :minH="2"
                     :x="item.x"
                     :y="item.y"
                     :w="item.w"
                     :h="item.h"
                     :i="item.i"
                     :key="item.i"
                     @resized="resizedEvent">
            <el-button class="settingButton"
                       type="text" icon="el-icon-s-tools"
                       @click="handleLayoutSetting(item)"></el-button>
            <el-card v-if="item.showCard" class="box-card" style="height:100%;">
              <div v-if="item.showTitle" slot="header">
                <span style="white-space:nowrap;">{{showTitle(item.i)}}</span>
              </div>
              <component :ref="'chart'+item.i" :is="mapDemoComponent(item.i)"
                         height="100%" width="100%"></component>
            </el-card>
            <template v-else>
              <div v-if="item.showTitle" class="chartTitle">
                <span style="white-space:nowrap;">{{showTitle(item.i)}}</span>
              </div>
              <component :ref="'chart'+item.i" :is="mapDemoComponent(item.i)"
                         height="100%" width="100%"></component>
            </template>
          </grid-item>
        </grid-layout>
      </el-main>
    </el-container>
    <layout-setting ref="layoutSettingForm"></layout-setting>
  </div>
</template>

<script>
import model from './model'
import dashboardApi from '@/api/chart/dashboard'
import chartApi from '@/api/chart/chart'
import VueGridLayout from 'vue-grid-layout'
import _intersectionWith from 'lodash/intersectionWith'
import _differenceWith from 'lodash/differenceWith'
import _remove from 'lodash/remove'
import barLineDemo from './demo/barLineDemo'
import pieDemo from './demo/pieDemo'
import aggTableDemo from './demo/aggTableDemo'
import detailListDemo from './demo/detailListDemo'
import chartTypeUtil from '@/utils/options-chart-type'
import layoutSetting from './layoutSetting'

export default {
  name: 'dashboard',
  components: {
    GridLayout: VueGridLayout.GridLayout,
    GridItem: VueGridLayout.GridItem,
    layoutSetting,
    barLineDemo,
    pieDemo,
    aggTableDemo,
    detailListDemo
  },
  props: ['projectId', 'dashboardId'],
  data () {
    const edit = !!this.dashboardId
    return {
      edit: edit,
      chartIds: [],
      chartOptions: [],
      form: model.initFormBean(this.projectId),
      formLoading: false,
      rules: model.getRules()
    }
  },
  methods: {
    getDashboard () {
      this.formLoading = true
      return dashboardApi.get(this.dashboardId)
        .then(data => { this.form = data })
        .catch(error => this.$common.showNotifyError(error))
        .finally(() => { this.formLoading = false })
    },
    showTitle (chartId) {
      const chart = this.chartOptions.find(chart => chart.chartId === chartId)
      if (chart) {
        return chart.title
      }
      return ''
    },
    mapDemoComponent (chartId) {
      const chart = this.chartOptions.find(chart => chart.chartId === chartId)
      if (chart) {
        const chartTypeOption = chartTypeUtil.getChartTypeOption(chart.chartType)
        return chartTypeOption.demoComponent
      }
      return ''
    },
    resizedEvent (i, newH, newW, newHPx, newWPx) {
      const ref = this.$refs['chart' + i]
      if (ref) {
        const chartComponent = ref[0]
        if (chartComponent.resize) {
          chartComponent.resize()
        }
      }
    },
    loadCharts () {
      this.formLoading = true
      return chartApi.getList()
        .then(data => { this.chartOptions = data })
        .finally(() => { this.formLoading = false })
    },
    changeCharts () {
      // 移除图表
      _remove(this.form.layout, item => this.chartIds.indexOf(item.i) < 0)
      // 添加新图表
      _differenceWith(this.chartIds, this.form.layout,
        (chartId, item) => chartId === item.i)
        .forEach(chartId => this.form.layout.push(model.initLayout(chartId)))
    },
    submit () {
      let loading = null
      // 校验表单
      this.$refs.dashboardForm.validate()
        // 提交表单
        .then(() => {
          loading = this.$loading()
          return dashboardApi.saveOrUpdate(this.form, this.edit)
        })
        // 执行页面跳转
        .then(() => {
          this.$common.showMsg('success', '操作成功')
          this.goBack(false)
        })
        .catch(error => this.$common.showNotifyError(error))
        .finally(() => {
          if (loading) {
            loading.close()
          }
        })
    },
    goBack () {
      this.$router.push(`/project/${this.projectId}/chart`)
    },
    handleLayoutSetting (item) {
      this.$refs.layoutSettingForm.show(item)
    }
  },
  created () {
    if (this.edit) {
      Promise.all([this.getDashboard(), this.loadCharts()])
        .then(() => {
          const oldChartIds = this.form.layout.map(item => item.i)
          this.chartIds = _intersectionWith(oldChartIds, this.chartOptions,
            (chartId, chart) => chartId === chart.chartId)
        })
    } else {
      this.loadCharts()
    }
  }
}
</script>

<style lang="scss">
  @import '../../../assets/common.scss';
  .dashboardFormDiv {
    .dashboardForm {
      @include youran-form;
      padding: 20px 10px;
    }
    .el-card__header {
      height: 30px;
      padding: 4px 10px;
    }
    .el-card__body {
      padding: 0px;
    }
    .chartTitle {
      color: #303133;
      margin: 3px;
    }
    .settingButton {
      position:absolute;
      z-index: 1000;
      right: 10px;
      font-size:17px;
      padding-top: 7px;
      transition: 0.5s;
      opacity: 0;
    }
    .vue-grid-item:hover .settingButton {
      opacity: 1;
    }
  }

</style>
