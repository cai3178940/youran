<template>
  <div class="detailListFormDiv">
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/project' }">项目管理</el-breadcrumb-item>
      <el-breadcrumb-item :to="{ path: `/project/${this.projectId}/chart` }">图表管理</el-breadcrumb-item>
      <el-breadcrumb-item>
        {{edit?'编辑':'创建'}}明细表
      </el-breadcrumb-item>
    </el-breadcrumb>
    <el-steps :active="2" simple>
      <el-step title="第一步：配置数据源" icon="el-icon-coin"></el-step>
      <el-step title="第二步：配置明细表" icon="el-icon-set-up"></el-step>
    </el-steps>
    <el-container v-loading="formLoading" style="border:solid 1px #e6e6e6;">
      <el-aside style="width: 400px;">
        <el-form ref="detailListForm" class="detailListForm"
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
        <el-col :span="24" style="text-align: right; margin-bottom: 10px;">
          <el-tooltip class="item" effect="dark" content="添加更多列请返回上一步" placement="left">
            <el-badge :value="form.hiddenColumnList.length" :hidden="!form.hiddenColumnList.length" class="item">
              <el-dropdown size="small"
                           trigger="click" @command="addColumn">
                <el-button :disabled="!form.hiddenColumnList.length"
                           type="success" size="small">
                  添加列
                  <i class="el-icon-arrow-down el-icon--right"></i>
                </el-button>
                <el-dropdown-menu slot="dropdown">
                  <el-dropdown-item v-for="chartItem in form.hiddenColumnList"
                                    :key="chartItem.sourceItemId"
                                    :command="chartItem">
                    {{chartItem.titleAlias}}
                  </el-dropdown-item>
                </el-dropdown-menu>
              </el-dropdown>
            </el-badge>
          </el-tooltip>
        </el-col>
        <el-table size="small"
                  :data="emptyTableList"
                  style="width: 100%"
                  :border="true">
          <el-table-column v-for="chartItem in form.columnList"
                           :key="chartItem.sourceItemId" align="center"
                           min-width="180" class-name="head-column">
            <template slot="header" slot-scope="scope">
              <el-button v-if="scope.$index" @click="moveLeft(chartItem)" size="small" type="text">
                <i class="el-icon-arrow-left" style="font-size:14px"></i>
              </el-button>
              <el-dropdown size="small" trigger="click" @command="handleCommand">
                <el-button size="small">
                  {{chartItem.titleAlias}}
                  <i class="el-icon-arrow-down el-icon--right"></i>
                </el-button>
                <el-dropdown-menu slot="dropdown">
                  <el-dropdown-item :command="{method:'editColumn',arg: chartItem}">
                    <svg-icon className="dropdown-icon color-primary" iconClass="setting"></svg-icon>
                    配置
                  </el-dropdown-item>
                  <el-dropdown-item :command="{method:'removeColumn',arg: chartItem}">
                    <svg-icon className="dropdown-icon color-danger" iconClass="delete"></svg-icon>
                    移除
                  </el-dropdown-item>
                </el-dropdown-menu>
              </el-dropdown>
              <el-button v-if="scope.$index < form.columnList.length - 1"
                         @click="moveRight(chartItem)" size="small" type="text">
                <i class="el-icon-arrow-right" style="font-size:14px"></i>
              </el-button>
            </template>
            <template v-slot="scope">
              {{mockTableData(scope.row.i, chartItem, constDetails)}}
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
import detailListApi from '@/api/chart/detailList'
import chartSourceApi from '@/api/chart/chartSource'
import modulesMixin from '@/components/Mixins/modules'
import constDetailMixin from '@/components/Mixins/const-detail'
import chartItemForm from '../item/chartItemForm'
import model from './model'
import sourceModel from '../sourceModel'
import searchUtil from '../searchUtil'
import _differenceBy from 'lodash/differenceBy'
import _intersectionBy from 'lodash/intersectionBy'
import _uniq from 'lodash/uniq'

export default {
  name: 'detailListForm',
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
      form: model.initDetailListFormBean(this.projectId),
      sourceForm: sourceModel.initSourceFormBean(this.projectId),
      formLoading: false,
      rules: model.getRules()
    }
  },
  computed: {
    /**
     * 空表格数据，每个单元格单独mock
     */
    emptyTableList () {
      const size = this.form.defaultPageSize > 100 ? 100 : this.form.defaultPageSize
      return Array.from({ length: size }, (v, i) => ({ i: i }))
    }
  },
  methods: {
    mockTableData: model.mockTableData,
    handleCommand: function (command) {
      this[command.method](command.arg)
    },
    removeColumn (chartItem) {
      const index = this.form.columnList.indexOf(chartItem)
      const item = this.form.columnList.splice(index, 1)[0]
      this.form.hiddenColumnList.push(item)
    },
    editColumn (chartItem) {
      this.$refs.chartItemForm.show(chartItem)
    },
    addColumn (chartItem) {
      const index = this.form.hiddenColumnList.indexOf(chartItem)
      const item = this.form.hiddenColumnList.splice(index, 1)[0]
      this.form.columnList.push(item)
    },
    moveLeft (chartItem) {
      const arr = this.form.columnList
      const index = arr.indexOf(chartItem)
      if (index > 0) {
        const tmp = arr[index]
        arr.splice(index, 1)
        Vue.nextTick(() => arr.splice(index - 1, 0, tmp))
      }
    },
    moveRight (chartItem) {
      const arr = this.form.columnList
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
      return this.$refs.detailListForm.validate()
        // 提交表单
        .then(() => {
          loading = this.$loading()
          return detailListApi.saveOrUpdate(this.form, this.edit)
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
        this.$router.push(`/project/${this.projectId}/chart/detailList/edit/${this.chartId}?sourceId=${this.form.sourceId}`)
      } else {
        this.$router.push(`/project/${this.projectId}/chart/detailList/add?sourceId=${this.form.sourceId}`)
      }
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
          // 从所有字段中获取常量名
          const constNames = _uniq(fieldList.map(field => field.dicType).filter(t => t))
          // 加载常量值
          return this.loadConstDetail(this.projectId, constNames)
        })
    },
    /**
     * 修复创建表单数据
     */
    repairAddChartForm () {
      this.form.columnList = this.sourceForm.detailColumnList
        .map(detailColumn => model.initChartItemByDetailColumn(detailColumn))
    },
    /**
     * 修复编辑表单数据
     */
    repairEditChartForm () {
      // 找出上一步新增加的列，并转换成chartItem后放入隐藏列中
      const columnToAdd = _differenceBy(this.sourceForm.detailColumnList, this.form.columnList,
        this.form.hiddenColumnList, 'sourceItemId')
        .map(detailColumn => model.initChartItemByDetailColumn(detailColumn))
      this.form.hiddenColumnList.push(...columnToAdd)
      // 删除匹配不上detailColumn的列
      this.form.columnList = _intersectionBy(this.form.columnList, this.sourceForm.detailColumnList, 'sourceItemId')
      this.form.hiddenColumnList = _intersectionBy(this.form.hiddenColumnList, this.sourceForm.detailColumnList, 'sourceItemId')
      // 给columnList的每项，注入detailColumn
      this.form.columnList.forEach(column => {
        column.detailColumn = this.sourceForm.detailColumnList.find(detailColumn => detailColumn.sourceItemId === column.sourceItemId)
      })
    }
  },
  created () {
    this.formLoading = true
    if (this.edit) {
      detailListApi.get(this.chartId)
        .then(formBean => {
          formBean.columnList.concat(formBean.hiddenColumnList)
            .forEach(value => {
              value.detailColumn = {}
            })
          this.form = formBean
        })
        .then(() => this.loadSourceWithDetailColumnFields())
        .then(() => this.repairEditChartForm())
        .catch(error => this.$common.showNotifyError(error))
        .finally(() => {
          this.formLoading = false
        })
    } else {
      this.form.sourceId = this.$router.currentRoute.query.sourceId
      if (this.form.sourceId) {
        this.loadSourceWithDetailColumnFields()
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
  .detailListFormDiv {
    .detailListForm {
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
