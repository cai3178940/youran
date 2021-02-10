<template>
  <div class="sourceFormDiv">
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/project' }">项目管理</el-breadcrumb-item>
      <el-breadcrumb-item :to="{ path: `/project/${this.projectId}/chart` }">图表管理</el-breadcrumb-item>
      <el-breadcrumb-item>
        {{nextEdit?'编辑':'创建'}}{{chartType.label}}
      </el-breadcrumb-item>
    </el-breadcrumb>
    <el-steps :active="1" simple>
      <el-step title="第一步：配置数据源" icon="el-icon-coin"></el-step>
      <el-step :title="'第二步：配置'+chartType.label" icon="el-icon-set-up"></el-step>
    </el-steps>
    <el-main style="border:solid 1px #e6e6e6;">
      <el-row type="flex" align="middle" :gutter="20">
        <el-col :span="16">
          <el-form ref="sourceForm" class="sourceForm"
                   :rules="rules" :model="form" label-width="120px"
                   size="small" v-loading="formLoading">
            <!-- 实体： entity_0 t0 添加关联 -->
            <el-form-item label="实体" prop="entityId">
              <help-popover name="chartSource.entityId">
                <!-- 实体： entity_0 -->
                <el-col :span="18" class="col-left">
                  <el-select v-model="form.entityId" placeholder="请选择实体"
                             style="width:100%;" @change="handleEntityChange" filterable>
                    <el-option
                      v-for="entity in entityOptions"
                      :key="entity.entityId"
                      :label="entity.title+'('+entity.tableName+')'"
                      :value="entity.entityId">
                    </el-option>
                  </el-select>
                </el-col>
                <!-- 实体： t0 -->
                <el-col :span="1" class="col-inner" style="text-align: center;">
                  <span class="text-in-form">t0</span>
                </el-col>
                <!-- 实体： 添加关联 -->
                <el-col :span="5" class="col-right">
                  <el-button size="small" type="text" @click="addJoin">+ 添加关联</el-button>
                </el-col>
              </help-popover>
            </el-form-item>
            <template v-for="(join,index) in form.joins">
              <!-- 关联(表)： inner join entity_1 t1 移除关联 -->
              <el-form-item :key="'a'+index" :prop="'joins.' + index"
                            :rules="getJoinRuleTmp1()">
                <help-popover name="chartSource.joins">
                  <!-- 关联(join方式)： inner join -->
                  <el-col :span="6" class="col-left">
                    <el-select v-model="form.joins[index].joinType" placeholder="关联方式"
                               style="width:100%;">
                      <el-option
                        v-for="option in joinTypeOptions"
                        :key="option.value"
                        :label="option.label"
                        :value="option.value">
                      </el-option>
                    </el-select>
                  </el-col>
                  <!-- 关联(右边实体/多对多)： entity_1 -->
                  <el-col :span="12" class="col-left">
                    <el-select v-model="form.joins[index].right.tmp1" placeholder="请选择"
                               style="width:100%;" @change="changeJoinTmp1(form.joins[index].right)"
                               value-key="key" filterable>
                      <el-option-group label="实体">
                        <el-option
                          v-for="entity in entityOptions"
                          :key="entity.entityId"
                          :label="entity.title+'('+entity.tableName+')'"
                          :value="buildTmp1ByEntity(index+1, entity)">
                        </el-option>
                      </el-option-group>
                      <el-option-group label="多对多">
                        <el-option
                          v-for="mtm in mtmOptions"
                          :key="mtm.mtmId"
                          :label="mtm.tableName"
                          :value="buildTmp1ByMtm(index+1, mtm)">
                        </el-option>
                      </el-option-group>
                    </el-select>
                  </el-col>
                  <!-- 关联(右边别名)： t1 -->
                  <el-col :span="1" class="col-inner" style="text-align: center;">
                    <span class="text-in-form">t{{index+1}}</span>
                  </el-col>
                  <!-- 关联(操作按钮)： 移除关联 -->
                  <el-col :span="5" class="col-right">
                    <el-button size="small" type="text" @click="removeJoin(index)">- 移除关联</el-button>
                  </el-col>
                </help-popover>
              </el-form-item>
              <!-- 关联： on t0.xx_id = t1.xx_id -->
              <el-form-item :key="'b'+index" :prop="'joins.' + index"
                            :rules="getJoinRuleTmp2()">
                <help-popover name="chartSource.joins">
                  <!-- 关联： on  -->
                  <el-col :span="2" class="col-left" style="text-align: center;">
                    <span class="text-in-form" style="color:blueviolet;">on</span>
                  </el-col>
                  <!-- 关联(左边字段)： t0.xx_id  -->
                  <el-col :span="8" class="col-inner">
                    <el-select v-model="form.joins[index].left.tmp2" placeholder="请选择字段"
                               style="width:100%;" value-key="key"
                               @change="changeJoinTmp2(form.joins[index].left)" filterable>
                      <span class="text-in-form" slot="prefix">
                        t{{form.joins[index].left.joinIndex}}
                      </span>
                      <!--所有上面的实体-->
                      <el-option-group
                        v-for="([joinIndex,entity]) in getJoinEntitiesAbove(index)"
                        :key="'entity_'+entity.entityId"
                        :label="entity.title+'('+entity.tableName+')'">
                        <el-option
                          v-for="field in entity.fieldList"
                          :key="field.fieldId"
                          :label="field.fieldDesc+'('+field.fieldName+')'"
                          :value="buildTmp2ByEntity(joinIndex, entity, field)">
                        </el-option>
                      </el-option-group>
                      <!--所有上面的多对多-->
                      <el-option-group
                        v-for="([joinIndex,mtm]) in getJoinMtmsAbove(index)"
                        :key="'mtm_'+mtm.mtmId"
                        :label="'多对多('+mtm.tableName+')'">
                        <el-option
                          :label="mtm.entityIdField1"
                          :value="buildTmp2ByMtm(joinIndex, mtm, mtm.entityIdField1)">
                        </el-option>
                        <el-option
                          :label="mtm.entityIdField2"
                          :value="buildTmp2ByMtm(joinIndex, mtm, mtm.entityIdField2)">
                        </el-option>
                      </el-option-group>
                    </el-select>
                  </el-col>
                  <!-- 关联(中间符号)： =  -->
                  <el-col :span="2" class="col-inner" style="text-align: center;">
                    <span class="text-in-form" style="color:blueviolet;">=</span>
                  </el-col>
                  <!-- 关联(右边字段)： t1.xx_id  -->
                  <el-col :span="8" class="col-inner">
                    <el-select v-model="form.joins[index].right.tmp2" placeholder="请选择字段"
                               style="width:100%;" value-key="key"
                               @change="changeJoinTmp2(form.joins[index].right)" filterable>
                      <span class="text-in-form" slot="prefix">
                        t{{form.joins[index].right.joinIndex}}
                      </span>
                      <!--如果右边是实体-->
                      <template v-if="form.joins[index].right.joinPartType==='entity'">
                        <el-option
                          v-for="field in form.joins[index].right.entity.fieldList"
                          :key="field.fieldId"
                          :label="field.fieldDesc+'('+field.fieldName+')'"
                          :value="buildTmp2ByEntity(index+1, form.joins[index].right.entity, field)">
                        </el-option>
                      </template>
                      <!--如果右边是多对多-->
                      <template v-if="form.joins[index].right.joinPartType==='mtm'">
                        <el-option
                          :label="form.joins[index].right.mtm.entityIdField1"
                          :value="buildTmp2ByMtm(index+1, form.joins[index].right.mtm, form.joins[index].right.mtm.entityIdField1)">
                        </el-option>
                        <el-option
                          :label="form.joins[index].right.mtm.entityIdField2"
                          :value="buildTmp2ByMtm(index+1, form.joins[index].right.mtm, form.joins[index].right.mtm.entityIdField2)">
                        </el-option>
                      </template>
                    </el-select>
                  </el-col>
                </help-popover>
              </el-form-item>
            </template>
            <!-- 明细列：多选下拉框 -->
            <el-form-item v-if="!form.aggregation" label="明细列" prop="detailColumnList">
              <help-popover name="chartSource.detailColumnList">
                <el-select v-model="form.detailColumnList" value-key="key"
                           style="width:100%;" placeholder="请选择明细列"
                           @change="changeDetailColumn"
                           multiple filterable>
                  <el-option-group
                    v-for="([joinIndex,entity]) in entityFieldOptions"
                    :key="joinIndex"
                    :label="entity.title+'('+entity.tableName+')'">
                    <el-option
                      v-for="field in entity.fieldList"
                      :key="field.fieldId"
                      :label="field.fieldDesc+'('+field.fieldName+')'"
                      :value="buildCommonDetailColumn(joinIndex, field)">
                    </el-option>
                  </el-option-group>
                </el-select>
              </help-popover>
            </el-form-item>
            <!-- 自定义明细列 -->
            <el-form-item v-if="!form.aggregation" label="自定义列">
              <help-popover name="chartSource.customColumnList">
                <el-button v-for="(customColumn,index) in form.customColumnList"
                           :key="index" class="inner-form-button"
                           type="primary" @click="editCustomColumn(index, customColumn)"
                           plain>
                  {{customColumn | displayDetailColumn}}
                </el-button>
                <el-button type="success" @click="addCustomColumn"
                           class="inner-form-button inner-add-button"
                           icon="el-icon-plus" plain>
                </el-button>
              </help-popover>
            </el-form-item>
            <!-- 过滤条件 -->
            <el-form-item label="过滤">
              <help-popover name="chartSource.whereList">
                <el-button v-for="(where,index) in form.whereList"
                           :key="index" class="inner-form-button"
                           type="primary" @click="editWhere(index, where)"
                           plain>
                  {{where | displayWhere}}
                </el-button>
                <el-button type="success" @click="addWhere"
                           class="inner-form-button inner-add-button"
                           icon="el-icon-plus" plain>
                </el-button>
              </help-popover>
            </el-form-item>
            <!-- 排序 -->
            <el-form-item v-if="!form.aggregation" label="排序">
              <help-popover name="chartSource.detailOrderList">
                <el-button v-for="(detailOrder,index) in form.detailOrderList"
                           :key="index" class="inner-form-button"
                           type="primary" @click="editDetailOrder(index, detailOrder)"
                           plain>
                  {{detailOrder | displayDetailOrder}}
                </el-button>
                <el-button type="success" @click="addDetailOrder"
                           class="inner-form-button inner-add-button"
                           icon="el-icon-plus" plain>
                </el-button>
              </help-popover>
            </el-form-item>
            <!-- 聚合(维度) -->
            <el-form-item v-if="form.aggregation" label="聚合(维度)" prop="dimensionList">
              <help-popover name="chartSource.dimensionList">
                <el-button v-for="(dimension,index) in form.dimensionList"
                           :key="index" class="inner-form-button"
                           type="primary" @click="editDimension(index, dimension)"
                           plain>
                  {{dimension | displayDimension}}
                </el-button>
                <el-button type="success" @click="addDimension"
                           class="inner-form-button inner-add-button"
                           icon="el-icon-plus" plain>
                </el-button>
              </help-popover>
            </el-form-item>
            <!-- 聚合(指标) -->
            <el-form-item v-if="form.aggregation" label="聚合(指标)" prop="metricsList">
              <help-popover name="chartSource.metricsList">
                <el-button v-for="(metrics,index) in form.metricsList"
                           :key="index" class="inner-form-button"
                           type="primary" @click="editMetrics(index, metrics)"
                           plain>
                  {{metrics | displayMetrics}}
                </el-button>
                <el-button type="success" @click="addMetrics"
                           class="inner-form-button inner-add-button"
                           icon="el-icon-plus" plain>
                </el-button>
              </help-popover>
            </el-form-item>
            <!-- 聚合(过滤) -->
            <el-form-item v-if="form.aggregation && form.metricsList.length>0" label="聚合(过滤)">
              <help-popover name="chartSource.havingList">
                <el-button v-for="(having,index) in form.havingList"
                           :key="index" class="inner-form-button"
                           type="primary" @click="editHaving(index, having)"
                           plain>
                  {{having | displayHaving}}
                </el-button>
                <el-button type="success" @click="addHaving"
                           class="inner-form-button inner-add-button"
                           icon="el-icon-plus" plain>
                </el-button>
              </help-popover>
            </el-form-item>
            <!-- 聚合(排序) -->
            <el-form-item v-if="form.aggregation" label="聚合(排序)">
              <help-popover name="chartSource.aggOrderList">
                <el-button v-for="(aggOrder,index) in form.aggOrderList"
                           :key="index" class="inner-form-button"
                           type="primary" @click="editAggOrder(index, aggOrder)"
                           plain>
                  {{aggOrder | displayAggOrder}}
                </el-button>
                <el-button type="success" @click="addAggOrder"
                           class="inner-form-button inner-add-button"
                           icon="el-icon-plus" plain>
                </el-button>
              </help-popover>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="next()" tabindex="160">下一步</el-button>
              <el-button @click="goBack()" tabindex="180">返回</el-button>
            </el-form-item>
          </el-form>
        </el-col>
      </el-row>
    </el-main>
    <custom-column-form ref="customColumnForm" @submit="onCustomColumnSubmit" @remove="onCustomColumnRemove"/>
    <where-form ref="whereForm" @submit="onWhereSubmit" @remove="onWhereRemove"/>
    <detail-order-form ref="detailOrderForm" @submit="onDetailOrderSubmit" @remove="onDetailOrderRemove"/>
    <dimension-form ref="dimensionForm" @submit="onDimensionSubmit" @remove="onDimensionRemove"/>
    <metrics-form ref="metricsForm" @submit="onMetricsSubmit" @remove="onMetricsRemove"/>
    <having-form ref="havingForm" @submit="onHavingSubmit" @remove="onHavingRemove"/>
    <agg-order-form ref="aggOrderForm" @submit="onAggOrderSubmit" @remove="onAggOrderRemove"/>
  </div>
</template>
<script>
import chartSourceApi from '@/api/chart/chartSource'
import entityApi from '@/api/entity'
import mtmApi from '@/api/mtm'
import fieldApi from '@/api/field'
import customColumnForm from './item/customColumnForm'
import whereForm from './item/whereForm'
import whereModel from './item/whereModel'
import detailOrderForm from './item/detailOrderForm'
import detailOrderModel from './item/detailOrderModel'
import dimensionForm from './item/dimensionForm'
import dimensionModel from './item/dimensionModel'
import metricsForm from './item/metricsForm'
import metricsModel from './item/metricsModel'
import havingForm from './item/havingForm'
import havingModel from './item/havingModel'
import aggOrderForm from './item/aggOrderForm'
import aggOrderModel from './item/aggOrderModel'
import joinTypeUtil from '@/utils/options-chart-join-type'
import chartTypeUtil from '@/utils/options-chart-type'
import model from './sourceModel'
import detailColumnModel from './item/detailColumnModel'
import searchUtil from './searchUtil'

export default {
  name: 'sourceForm',
  props: [
    'projectId',
    'chartTypeName',
    'chartId'
  ],
  components: {
    customColumnForm,
    whereForm,
    detailOrderForm,
    dimensionForm,
    metricsForm,
    havingForm,
    aggOrderForm
  },
  data () {
    const nextEdit = !!this.chartId
    return {
      // 当前表单是否编辑
      edit: false,
      // 下一步表单是否编辑
      nextEdit: nextEdit,
      sourceId: null,
      formChanged: false,
      joinTypeOptions: joinTypeUtil.joinTypeOptions,
      entityOptions: [],
      mtmOptions: [],
      chartType: {
        value: 1,
        label: '',
        name: '',
        aggregation: false
      },
      formLoading: false,
      form: model.initSourceFormBean(this.projectId),
      rules: model.getRules()
    }
  },
  computed: {
    /**
     * 实体字段选项
     */
    entityFieldOptions () {
      const options = []
      if (this.form.entity) {
        options.push([0, this.form.entity])
      }
      this.form.joins.forEach(join => {
        if (join.right.entity) {
          options.push([join.right.joinIndex, join.right.entity])
        }
      })
      return options
    }
  },
  filters: {
    displayWhere: whereModel.displayText,
    displayHaving: havingModel.displayText,
    displayMetrics: metricsModel.displayText,
    displayDimension: dimensionModel.displayText,
    displayDetailOrder: detailOrderModel.displayText,
    displayAggOrder: aggOrderModel.displayText,
    displayDetailColumn: detailColumnModel.displayText
  },
  methods: {
    buildCommonDetailColumn: detailColumnModel.buildCommonDetailColumn,
    buildTmp1ByEntity: model.buildTmp1ByEntity,
    buildTmp1ByMtm: model.buildTmp1ByMtm,
    buildTmp2ByEntity: model.buildTmp2ByEntity,
    buildTmp2ByMtm: model.buildTmp2ByMtm,
    initEntityOptions () {
      return entityApi.getList(this.projectId)
        .then(data => {
          this.entityOptions = data.map(entity => ({
            entityId: entity.entityId,
            title: entity.title,
            tableName: entity.tableName,
            fieldList: []
          }))
        })
    },
    initMtmOptions () {
      return mtmApi.getList(this.projectId)
        .then(data => {
          this.mtmOptions = data
        })
    },
    changeForm () {
      this.formChanged = true
    },
    handleEntityChange (entityId) {
      this.changeForm()
      this.form.entity = this.entityOptions.find(value => value.entityId === entityId)
      this.loadEntityFields(this.form.entity)
      model.repairAtJoinChange(this.form, 0)
    },
    loadEntityFields (entity) {
      if (entity.fieldList.length) {
        return Promise.resolve()
      }
      return fieldApi.getList(entity.entityId, false)
        .then(data => {
          entity.fieldList = data
        })
    },
    getJoinRuleTmp1 () {
      return {
        validator (rule, join, callback) {
          if (!join.right.tmp1.key) {
            callback(new Error('请选择关联对象'))
          } else {
            callback()
          }
        },
        trigger: 'change'
      }
    },
    getJoinRuleTmp2 () {
      return {
        validator (rule, join, callback) {
          if (!join.left.tmp2.key || !join.right.tmp2.key) {
            callback(new Error('请选择关联字段'))
          } else {
            callback()
          }
        },
        trigger: 'change'
      }
    },
    /**
     * 获取某个关联之前的关联实体
     */
    getJoinEntitiesAbove (index) {
      const pairs = []
      if (this.form.entity) {
        pairs.push([0, this.form.entity])
      }
      for (let i = 0; i < index; i++) {
        if (this.form.joins.length > i) {
          const joinEntity = this.form.joins[i].right.entity
          if (joinEntity) {
            pairs.push([i + 1, joinEntity])
          }
        }
      }
      return pairs
    },
    /**
     * 获取某个关联之前的关联多对多
     */
    getJoinMtmsAbove (index) {
      const pairs = []
      for (let i = 0; i < index; i++) {
        if (this.form.joins.length > i) {
          const joinMtm = this.form.joins[i].right.mtm
          if (joinMtm) {
            pairs.push([i + 1, joinMtm])
          }
        }
      }
      return pairs
    },
    /**
     * 关联对象变更
     */
    changeJoinTmp1 (part) {
      part.joinPartType = part.tmp1.joinPartType
      part.joinIndex = part.tmp1.joinIndex
      if (part.joinPartType === 'entity') {
        this.loadEntityFields(part.tmp1.obj)
        part.entity = part.tmp1.obj
        part.entityId = part.tmp1.obj.entityId
      } else if (part.joinPartType === 'mtm') {
        part.mtm = part.tmp1.obj
        part.mtmId = part.tmp1.obj.mtmId
      }
      model.repairAtJoinChange(this.form, part.joinIndex)
      this.changeForm()
    },
    /**
     * 关联字段变更
     */
    changeJoinTmp2 (part) {
      part.joinPartType = part.tmp2.joinPartType
      part.joinIndex = part.tmp2.joinIndex
      if (part.joinPartType === 'entity') {
        this.loadEntityFields(part.tmp2.obj)
        part.entity = part.tmp2.obj
        part.entityId = part.tmp2.obj.entityId
        part.field = part.tmp2.field
        part.fieldId = part.tmp2.field.fieldId
      } else if (part.joinPartType === 'mtm') {
        part.mtm = part.tmp2.obj
        part.mtmId = part.tmp2.obj.mtmId
        part.mtmField = part.tmp2.field
      }
      this.changeForm()
    },
    addJoin () {
      this.form.joins.push(model.initJoinDTO(0, this.form.joins.length))
      this.changeForm()
    },
    removeJoin (index) {
      this.form.joins.splice(index, 1)
      model.repairAtJoinRemove(this.form, index + 1)
      this.changeForm()
    },
    changeDetailColumn () {
      model.repairAtDetailColumnChange(this.form)
      this.changeForm()
    },
    addCustomColumn () {
      this.$refs.customColumnForm.show(null, this.form.customColumnList.length)
    },
    editCustomColumn (index, customColumn) {
      this.$refs.customColumnForm.show(customColumn, index)
    },
    onCustomColumnSubmit (index, customColumn) {
      if (index >= this.form.customColumnList.length) {
        this.form.customColumnList.push(customColumn)
      } else {
        this.$set(this.form.customColumnList, index, customColumn)
        model.repairAtDetailColumnChange(this.form)
      }
      this.changeForm()
    },
    onCustomColumnRemove (index, customColumn) {
      if (index < this.form.customColumnList.length) {
        this.form.customColumnList.splice(index, 1)
      }
      model.repairAtDetailColumnChange(this.form)
      this.changeForm()
    },
    /**
     * where条件
     */
    addWhere () {
      this.$refs.whereForm.show(this.projectId, this.entityFieldOptions, null, this.form.whereList.length)
    },
    editWhere (index, where) {
      this.$refs.whereForm.show(this.projectId, this.entityFieldOptions, where, index)
    },
    onWhereSubmit (index, where) {
      if (index >= this.form.whereList.length) {
        this.form.whereList.push(where)
      } else {
        this.$set(this.form.whereList, index, where)
      }
      this.changeForm()
    },
    onWhereRemove (index, where) {
      if (index < this.form.whereList.length) {
        this.form.whereList.splice(index, 1)
      }
      this.changeForm()
    },
    /**
     * 明细列排序
     */
    addDetailOrder () {
      this.$refs.detailOrderForm.show(this.form.detailColumnList, this.form.customColumnList,
        null, this.form.detailOrderList.length)
    },
    editDetailOrder (index, detailOrder) {
      this.$refs.detailOrderForm.show(this.form.detailColumnList, this.form.customColumnList,
        detailOrder, index)
    },
    onDetailOrderSubmit (index, detailOrder) {
      if (index >= this.form.detailOrderList.length) {
        this.form.detailOrderList.push(detailOrder)
      } else {
        this.$set(this.form.detailOrderList, index, detailOrder)
      }
      this.changeForm()
    },
    onDetailOrderRemove (index, detailOrder) {
      if (index < this.form.detailOrderList.length) {
        this.form.detailOrderList.splice(index, 1)
      }
      this.changeForm()
    },
    /**
     * 维度
     */
    addDimension () {
      this.$refs.dimensionForm.show(this.entityFieldOptions, null, this.form.dimensionList.length)
    },
    editDimension (index, dimension) {
      this.$refs.dimensionForm.show(this.entityFieldOptions, dimension, index)
    },
    onDimensionSubmit (index, dimension) {
      if (index >= this.form.dimensionList.length) {
        this.form.dimensionList.push(dimension)
      } else {
        this.$set(this.form.dimensionList, index, dimension)
      }
      this.changeForm()
    },
    onDimensionRemove (index, dimension) {
      if (index < this.form.dimensionList.length) {
        this.form.dimensionList.splice(index, 1)
      }
      this.changeForm()
    },
    /**
     * 指标
     */
    addMetrics () {
      this.$refs.metricsForm.show(this.entityFieldOptions, null, this.form.metricsList.length)
    },
    editMetrics (index, metrics) {
      this.$refs.metricsForm.show(this.entityFieldOptions, metrics, index)
    },
    onMetricsSubmit (index, metrics) {
      if (index >= this.form.metricsList.length) {
        this.form.metricsList.push(metrics)
      } else {
        this.$set(this.form.metricsList, index, metrics)
      }
      this.changeForm()
    },
    onMetricsRemove (index, metrics) {
      if (index < this.form.metricsList.length) {
        this.form.metricsList.splice(index, 1)
      }
      this.changeForm()
    },
    /**
     * having条件
     */
    addHaving () {
      this.$refs.havingForm.show(this.form.metricsList, null, this.form.havingList.length)
    },
    editHaving (index, having) {
      this.$refs.havingForm.show(this.form.metricsList, having, index)
    },
    onHavingSubmit (index, having) {
      if (index >= this.form.havingList.length) {
        this.form.havingList.push(having)
      } else {
        this.$set(this.form.havingList, index, having)
      }
      this.changeForm()
    },
    onHavingRemove (index, having) {
      if (index < this.form.havingList.length) {
        this.form.havingList.splice(index, 1)
      }
      this.changeForm()
    },
    /**
     * 聚合排序
     */
    addAggOrder () {
      this.$refs.aggOrderForm.show(this.form.dimensionList, this.form.metricsList, null, this.form.aggOrderList.length)
    },
    editAggOrder (index, aggOrder) {
      this.$refs.aggOrderForm.show(this.form.dimensionList, this.form.metricsList, aggOrder, index)
    },
    onAggOrderSubmit (index, aggOrder) {
      if (index >= this.form.aggOrderList.length) {
        this.form.aggOrderList.push(aggOrder)
      } else {
        this.$set(this.form.aggOrderList, index, aggOrder)
      }
      this.changeForm()
    },
    onAggOrderRemove (index, aggOrder) {
      if (index < this.form.aggOrderList.length) {
        this.form.aggOrderList.splice(index, 1)
      }
      this.changeForm()
    },
    /**
     * 下一步
     */
    next () {
      // 编辑的情况下，未进行任何修改，则直接进入下一页
      if (this.edit && !this.formChanged) {
        this.nextPage()
      } else {
        this.submit()
      }
    },
    nextPage () {
      if (this.nextEdit) {
        this.$router.push(`/project/${this.projectId}/chart/${this.chartTypeName}/edit/${this.chartId}/next`)
      } else {
        this.$router.push(`/project/${this.projectId}/chart/${this.chartTypeName}/add/next?sourceId=${this.sourceId}`)
      }
    },
    submit () {
      let loading = null
      // 校验表单
      return this.$refs.sourceForm.validate()
        // 提交表单
        .then(() => {
          loading = this.$loading()
          return chartSourceApi.saveOrUpdateWithItems(model.extractFormBean(this.form), this.edit)
        })
        // 执行页面跳转
        .then(data => {
          this.sourceId = data.sourceId
          this.$common.showMsg('success', '操作成功')
        })
        .then(() => this.nextPage())
        .catch(error => this.$common.showNotifyError(error))
        .finally(() => {
          if (loading) {
            loading.close()
          }
        })
    },
    goBack () {
      this.$router.push(`/project/${this.projectId}/chart`)
    }
  },
  created () {
    this.chartType = chartTypeUtil.chartTypeOptions.find(op => op.name === this.chartTypeName)
    this.form.aggregation = this.chartType.aggregation
    const promise = this.initEntityOptions()
      .then(() => this.initMtmOptions())
    this.sourceId = this.$router.currentRoute.query.sourceId
    if (this.sourceId) {
      this.edit = true
      this.formLoading = true
      promise.then(() => chartSourceApi.getWithItems(this.sourceId))
        .then(formBean => {
          const array = searchUtil.findEntityIdsInFormBean(formBean)
            .map(entityId => {
              const entity = this.entityOptions.find(value => value.entityId === entityId)
              return this.loadEntityFields(entity)
            })
          return Promise.all(array)
            .then(() => {
              model.repairSourceFormForEdit(formBean, this.entityOptions, this.mtmOptions)
              this.form = formBean
            })
        })
        .catch(error => this.$common.showNotifyError(error))
        .finally(() => {
          this.formLoading = false
        })
    }
  }
}
</script>

<style lang="scss">
  @import '../../assets/common.scss';
  .sourceFormDiv .sourceForm {
    padding-bottom: 0px!important;
    @include youran-form;

    .text-in-form {
      display:inline-block;
      font-size: 16px;
      color: #fa8072;
      padding: 7px 0px;
    }

  }

</style>
