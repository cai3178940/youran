<template>
  <div class="fieldList">
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/project' }">项目管理</el-breadcrumb-item>
      <el-breadcrumb-item :to="{ path: `/project/${this.projectId}/entity` }">实体管理</el-breadcrumb-item>
      <el-breadcrumb-item>字段管理</el-breadcrumb-item>
    </el-breadcrumb>

    <el-row type="flex" align="middle" :gutter="20" style="padding:10px 0;">
      <el-col :span="4" class="activeNum">
        已选择{{ activeNum }}个字段
      </el-col>
      <el-col :span="20" style="text-align: right;">
        <el-form :inline="true" :model="queryForm" class="query-form-inline">
          <el-form-item>
            <el-cascader
              placeholder="请选择实体"
              :options="queryForm.projectEntityOptions"
              v-model="queryForm.projectEntity"
              @expand-change="handleProjectChange"
              @change="handleQuery">
            </el-cascader>
          </el-form-item>
          <el-form-item>
              <el-badge :value="cacheFieldTemplateCount" :hidden="!cacheFieldTemplateCount" class="item">
                <!--<el-button ref="copyButton" @click.native="handleCopy" type="warning" style="margin: 0 0 0 10px;">复制字段</el-button>-->
                <el-button ref="copyButton"
                           @click="showAddTemplateForm"
                           type="success"
                           style="margin: 0 0 2px 10px;">添加字段</el-button>
              </el-badge>
              <el-button style="margin-left: 10px;" @click.native="handleIndexAdd" type="primary">创建索引</el-button>
              <el-button @click.native="handleDel" type="danger">删除字段</el-button>
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>

    <el-table :data="list" style="width: 100%" :border="true" v-loading="loading"
              @selection-change="selectionChange" :row-class-name="rowClassName">
      <el-table-column type="selection" width="39px"></el-table-column>
      <el-table-column property="orderNo" label="序号" width="65px">
        <template v-slot="scope">
          <el-button v-if="!scope.row.orderNoEdit"
                     type="primary" plain
                     size="mini"
                     @click="scope.row.orderNoEdit=!scope.row.orderNoEdit"
                     style="width:45px;padding-right: 3px;padding-left: 3px;">
            {{scope.row.orderNo}}
          </el-button>
          <el-input-number v-if="scope.row.orderNoEdit"
                           size="mini"
                           v-model="scope.row.orderNo"
                           :ref="'orderNoInput_'+scope.row.fieldId"
                           :controls="false"
                           :min="1"
                           :max="999"
                           class="order-no-input"
                           @blur="updateOrderNo(scope.row)"
                           @keyup.enter.native="updateOrderNo(scope.row)"
                           v-focus="{callback: doOrderNoFocus, arg: scope.row}"></el-input-number>
        </template>
      </el-table-column>
      <el-table-column label="字段标题">
        <template v-slot="scope">
          <template v-if="!scope.row.validate.success">
            <i class="iconfont icon-exclamationmarkcircle color-warning"></i>
          </template>
          <el-popover
            placement="top"
            trigger="click">
            <p>
              <span class="popper-remark-label">备注：</span>
              {{ scope.row.fieldComment }}
            </p>
            <p v-if="!scope.row.validate.sameJfieldNameSuccess" style="margin-top: 5px;">
              <span class="popper-warn-label">异常：</span>
              java字段名"{{ scope.row.jfieldName }}"重复
            </p>
            <p v-if="!scope.row.validate.sameFieldNameSuccess" style="margin-top: 5px;">
              <span class="popper-warn-label">异常：</span>
              mysql字段名"{{ scope.row.fieldName }}"重复
            </p>
            <p v-if="!scope.row.validate.dicExistSuccess" style="margin-top: 5px;">
              <span class="popper-warn-label">异常：</span>
              枚举"{{ scope.row.validate.dicNotExist }}"不存在
              <el-button @click="handleAddConst(scope.row)"
                         style="padding: 0px;" type="text">前往创建</el-button>
            </p>
            <el-button slot="reference" size="medium" type="text">{{ scope.row.fieldDesc }}</el-button>
          </el-popover>
          <template v-for="index in scope.row.indexes">
            <el-dropdown :key="index.indexId" @command="handleIndexCommand" size="mini" placement="bottom-start" trigger="click" style="margin-left:5px;cursor:pointer;">
              <span @mouseover="setActiveIndex(index)" @mouseout="clearActiveIndex()"
                    :class="[
                      'index_span',
                      (activeIndexId==index.indexId)?'indexActive':'',
                      index.unique?'index_u_span':(index.uniqueCheck?'index_check_span':'index_com_span')
                      ]"
                    :title="[index.unique?'唯一索引':(index.uniqueCheck?'普通索引(唯一性校验)':'普通索引')]">
                {{index.indexName}}
              </span>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item :command="{method:'handleDelIndexField',arg:[index,scope.row]}">
                  <i class="iconfont icon-times1 table-cell-icon"
                     style="margin-right: 0px; vertical-align: middle;"
                     :class="[index.unique?'color-danger':'color-primary']"></i>
                  删除索引字段
                </el-dropdown-item>
                <el-dropdown-item :command="{method:'handleDelIndex',arg:[index]}">
                  <i class="iconfont icon-trash table-cell-icon"
                     style="margin-right: 0px; vertical-align: middle;"
                     :class="[index.unique?'color-danger':'color-primary']"></i>
                  删除整个索引
                </el-dropdown-item>
                <el-dropdown-item :command="{method:'handleIndexEdit',arg:[index]}">
                  <i class="iconfont icon-edit_small1 table-cell-icon"
                     style="margin-right: 0px; vertical-align: middle;"
                     :class="[index.unique?'color-danger':'color-primary']"></i>
                  编辑索引
                </el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
          </template>
        </template>
      </el-table-column>
      <el-table-column label="字段名">
        <template v-slot="scope">
          {{ scope.row.jfieldName }} / {{ scope.row.fieldName }}
        </template>
      </el-table-column>
      <el-table-column label="字段类型" width="200px">
        <template v-slot="scope">
          {{ scope.row.jfieldType | optionLabel('jfieldTypeOptions')}}
          / {{ scope.row.fieldType | optionLabel('fieldTypeOptions') }}{{scope.row | lengthAndScale}}
        </template>
      </el-table-column>
      <el-table-column label="非空" width="50px">
        <template v-slot="scope">
          <i v-if="scope.row.notNull" class="iconfont icon-check2 table-cell-icon color-success"></i>
          <i v-else class="iconfont icon-times1 table-cell-icon color-danger"></i>
        </template>
      </el-table-column>
      <el-table-column label="性质" width="70px">
        <template v-slot="scope">
          <template v-for="feature in [getFieldFeature(scope.row)]">
            <el-tooltip :key="feature.value" class="item" effect="dark" :content="feature.label" placement="right">
              <span :class="feature.icon" class="table-cell-icon"></span>
            </el-tooltip>
          </template>
        </template>
      </el-table-column>
      <el-table-column property="fieldExample" label="字段示例"></el-table-column>
      <el-table-column
        label="操作"
        width="130">
        <template v-slot="scope">
          <el-button @click="handleEdit(scope.row)" type="text" size="medium" style="margin-left: 5px;">编辑</el-button>
          <el-button :ref="'copyButton'+scope.row.fieldId"
                     :disabled="fieldCached(scope.row.fieldId) || fieldToCache(scope.row.fieldId)"
                     @click="handleCopyOne(scope.row,$event)"
                     type="text" size="medium" class="copyButtion">复制</el-button>
          <el-badge v-if="scope.row.foreignKey" :value="scope.row.cascadeFieldNum" :hidden="!scope.row.cascadeFieldNum" class="cascadeBadge">
            <el-button @click="handleShowCascadeExt(scope.row)" type="text" size="medium" style="margin-left: 5px;">级联</el-button>
          </el-badge>
        </template>
      </el-table-column>
    </el-table>

    <template v-if="mtmEntities.holds.length || mtmEntities.unholds.length">
      <!-- 纯表头 -->
      <div class="mtmEntitiesHeader">
        <el-table :data="[]" style="width: 100%" :border="true">
          <el-table-column width="200px" label="多对多级联"/>
          <el-table-column width="200px" label="类名"/>
          <el-table-column width="200px" label="表名"/>
          <el-table-column label="描述"/>
          <el-table-column label="操作" width="130"/>
        </el-table>
      </div>
      <div :loading="mtmEntitiesLoading">
        <!-- 持有引用的实体 -->
        <el-table v-if="mtmEntities.holds.length" :data="mtmEntities.holds" :border="true"
                  style="width: 100%" :show-header="false" row-class-name="hold-mtm-row">
          <el-table-column width="200px" property="title"/>
          <el-table-column width="200px" property="className"/>
          <el-table-column width="200px" property="tableName"/>
          <el-table-column property="desc"/>
          <el-table-column width="130">
            <template v-slot="scope">
              <el-badge :value="scope.row.cascadeFieldNum" :hidden="!scope.row.cascadeFieldNum" class="cascadeBadge">
                <el-button @click="handleShowMtmCascadeExt(scope.row, true)" type="text" size="medium" style="margin-left: 5px;">级联</el-button>
              </el-badge>
            </template>
          </el-table-column>
        </el-table>
        <!-- 未持有引用的实体 -->
        <el-table v-if="mtmEntities.unholds.length" :data="mtmEntities.unholds" :border="true"
                  style="width: 100%" :show-header="false" row-class-name="unhold-mtm-row">
          <el-table-column width="200px" property="title"/>
          <el-table-column width="200px" property="className"/>
          <el-table-column width="200px" property="tableName"/>
          <el-table-column property="desc"/>
          <el-table-column width="130">
            <template v-slot="scope">
              <el-badge :value="scope.row.cascadeFieldNum" :hidden="!scope.row.cascadeFieldNum" class="cascadeBadge">
                <el-button @click="handleShowMtmCascadeExt(scope.row, false)" type="text" size="medium" style="margin-left: 5px;">级联</el-button>
              </el-badge>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </template>

    <el-dialog title="请选择字段模板" :visible.sync="addTemplateFormVisible" width="30%">
      <el-form :model="templateForm" size="small">

        <el-form-item label="模式：" label-width="100px">
          <el-radio-group v-model="templateForm.multiple">
            <el-radio border :label="false">表单编辑</el-radio>
            <el-radio border :label="true">批量保存</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item v-show="!templateForm.multiple" label="模板：" label-width="100px">
          <el-badge :value="cacheFieldTemplateCount" :hidden="!cacheFieldTemplateCount" class="item">
            <el-select v-model="templateForm.template" @change="handleTemplateChange">
              <el-option-group>
                <el-option v-for="(value,key) in flexibleTemplate"
                           :key="key"
                           :label="key"
                           :value="key">
                  <span style="float: left">
                    <span class="template-option">
                      <template v-for="feature in [getFieldFeature(value)]">
                        <span :key="feature.value" :class="feature.icon"></span>
                      </template>
                    </span>
                    {{ key }}
                  </span>
                </el-option>
              </el-option-group>
              <el-option-group v-if="cacheFieldTemplateCount>0" label="复制字段">
                <el-option v-for="value in cacheFieldTemplate"
                           :key="value.fieldId"
                           :label="value.fieldDesc"
                           :value="value.fieldId">
                  <span style="float: left">
                    <span class="template-option">
                      <template v-for="feature in [getFieldFeature(value)]">
                        <span :key="feature.value" :class="feature.icon"></span>
                      </template>
                    </span>
                    {{ value.fieldDesc }}
                  </span>
                  <span style="float: right; color: #8492a6; font-size: 13px"><i @click.stop="removeFieldTemplate(value.fieldId)" class="el-icon-delete"></i></span>
                </el-option>
              </el-option-group>
              <el-option-group label="预置字段">
                <el-option v-for="(value,key) in fixedTemplate"
                           :key="key"
                           :label="key"
                           :value="key"
                           :disabled="isTemplateOptionDisabled(value)">
                  <span style="float: left">
                    <span class="template-option">
                      <template v-for="feature in [getFieldFeature(value)]">
                        <span :key="feature.value" :class="feature.icon"></span>
                      </template>
                    </span>
                    {{ key }}
                  </span>
                </el-option>
              </el-option-group>
            </el-select>
          </el-badge>
        </el-form-item>
        <el-form-item v-show="templateForm.multiple" label="模板：" label-width="100px">
          <el-badge :value="cacheFieldTemplateCount" :hidden="!cacheFieldTemplateCount" class="item">
            <el-select
              v-model="templateForm.templates"
              multiple
              collapse-tags>
              <el-option-group v-if="cacheFieldTemplateCount>0" label="复制字段">
                <el-option v-for="value in cacheFieldTemplate"
                           :key="value.fieldId"
                           :label="value.fieldDesc"
                           :value="value.fieldId">
                  <span style="float: left">
                    <span class="template-option">
                      <template v-for="feature in [getFieldFeature(value)]">
                        <span :key="feature.value" :class="feature.icon"></span>
                      </template>
                    </span>
                    {{ value.fieldDesc }}
                  </span>
                  <span style="float: right; color: #8492a6; font-size: 13px">
                    <i @click.stop="removeFieldTemplate(value.fieldId)" class="el-icon-delete"></i>
                  </span>
                </el-option>
              </el-option-group>
              <el-option-group label="预置字段">
                <el-option v-for="(value,key) in fixedTemplate"
                           :key="key"
                           :label="key"
                           :value="key"
                           :disabled="isTemplateOptionDisabled(value)">
                  <span style="float: left">
                    <span class="template-option">
                      <template v-for="feature in [getFieldFeature(value)]">
                        <span :key="feature.value" :class="feature.icon"></span>
                      </template>
                    </span>
                    {{ key }}
                  </span>
                </el-option>
              </el-option-group>
            </el-select>
          </el-badge>
        </el-form-item>
        <el-form-item v-show="!templateForm.multiple" label="序号：" label-width="100px">
          <el-input-number v-model="templateForm.orderNo" :min="1"></el-input-number>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="addTemplateFormVisible = false">取 消</el-button>
        <el-button v-if="templateForm.multiple" type="success" @click="handleAddImm">立即保存</el-button>
        <el-button v-else type="primary" @click="handleAdd">开始编辑</el-button>
      </div>
    </el-dialog>

    <el-dialog class="cascadeExtDialog" title="外键级联扩展" :visible.sync="cascadeExtListVisible" width="60%">
      <cascade-ext-list ref="cascadeExtList" @cascadeFieldNumChange="resetCascadeFieldNum" @cascadeFieldNumAdd="addCascadeFieldNum"></cascade-ext-list>
    </el-dialog>
    <el-dialog class="cascadeExtDialog" title="多对多级联扩展" :visible.sync="mtmCascadeExtListVisible" width="60%">
      <mtm-cascade-ext-list ref="mtmCascadeExtList" @cascadeFieldNumChange="resetMtmCascadeFieldNum" @cascadeFieldNumAdd="addMtmCascadeFieldNum"></mtm-cascade-ext-list>
    </el-dialog>
    <!-- 所有复制按钮上的浮动小红点 -->
    <meteor v-for="field in list" :key="field.fieldId" :ref="'meteor'+field.fieldId"></meteor>
  </div>
</template>

<script>
import Vue from 'vue'
import cascadeExtList from '../cascadeExt/list'
import mtmCascadeExtList from '../mtmCascadeExt/list'
import options from '@/components/options'
import { apiPath } from '@/components/common'
import { flexibleTemplate, fixedTemplate, findSystemTemplate } from '@/components/fieldTemplate'
import meteor from '@/components/meteor'
import { mapGetters, mapState, mapMutations } from 'vuex'
import shortid from 'shortid'

/**
 * 初始化是否包含特殊属性字段
 */
const initListContains = function () {
  return {
    pk: false,
    deleted: false,
    createdTime: false,
    createdBy: false,
    operatedTime: false,
    operatedBy: false,
    version: false
  }
}

/**
 * 初始化添加字段模板表单
 */
const initTemplateForm = function () {
  return {
    multiple: false,
    template: '普通字段模板',
    templates: [],
    orderNo: 1
  }
}

export default {
  name: 'fieldList',
  props: ['projectId', 'entityId'],
  components: {
    'cascade-ext-list': cascadeExtList,
    'mtm-cascade-ext-list': mtmCascadeExtList,
    meteor
  },
  data () {
    return {
      // 控制添加字段窗口的显示
      addTemplateFormVisible: false,
      // 可变字段模板
      flexibleTemplate,
      // 系统预置字段
      fixedTemplate,
      // 添加字段模板表单
      templateForm: initTemplateForm(),
      // 查询参数
      query: {
        projectId: null,
        entityId: null
      },
      // 查询表单
      queryForm: {
        projectEntityOptions: [],
        projectEntity: [0, 0]
      },
      // 选中字段数量
      activeNum: 0,
      // 鼠标移动到索引框时激活该索引
      activeIndexId: null,
      // 已选中的字段列表
      selectItems: [],
      // 字段列表
      list: [],
      // 字段列表是否包含如下特殊性质字段
      listContains: initListContains(),
      // 索引列表
      indexes: [],
      // 控制字段添加红色渐隐特效
      addImmFieldIds: [],
      loading: false,
      // 控制外键级联扩展列表弹出框是否显示
      cascadeExtListVisible: false,
      // 控制多对多级联扩展列表弹出框是否显示
      mtmCascadeExtListVisible: false,
      mtmEntitiesLoading: false,
      // 多对多实体列表
      mtmEntities: {
        holds: [],
        unholds: []
      }
    }
  },
  computed: {
    ...mapState([
      'cacheFieldTemplateCount',
      'cacheFieldTemplate'
    ]),
    ...mapGetters([
      'fieldCached',
      'fieldToCache'
    ])
  },
  watch: {
    indexes (value) {
      if (!value) {
        return
      }
      // 首先将每个field中的indexes置空
      this.list.forEach(field => {
        field.indexes = []
      })
      value.forEach(index => {
        index.fields.forEach(field => {
          const f = this.list.find(item => item.fieldId === field.fieldId)
          if (f) {
            f.indexes.push(index)
          }
        })
      })
    }
  },
  filters: {
    optionLabel (value, optionType) {
      const ops = options[optionType]
      for (const op of ops) {
        if (op.value === value) {
          return op.label
        }
      }
      return null
    },
    lengthAndScale (row) {
      let rel = ''
      if (options.showFieldLength(row.fieldType, row.fieldLength)) {
        rel += '(' + row.fieldLength
        if (options.showFieldScale(row.fieldType)) {
          rel += ',' + row.fieldScale
        }
        rel += ')'
      }
      return rel
    }
  },
  methods: {
    ...mapMutations([
      'removeFieldTemplate',
      'addFieldTemplate',
      'addToCacheFieldTemplate',
      'removeToCacheFieldTemplate'
    ]),
    getFieldFeature: options.getFieldFeature,

    rowClassName ({ row }) {
      if (this.addImmFieldIds.includes(row.fieldId)) {
        return 'add-imm-field'
      }
    },
    selectionChange (val) {
      this.selectItems = val
      this.activeNum = this.selectItems.length
    },
    handleDel () {
      if (this.activeNum <= 0) {
        this.$common.showMsg('warning', '请选择字段')
        return
      }
      this.$common.confirm('是否确认删除')
        .then(() => this.$ajax.put(`/${apiPath}/meta_field/deleteBatch`, this.selectItems.map(field => field.fieldId)))
        .then(response => this.$common.checkResult(response))
        .then(() => this.doQuery())
        .then(() => this.doQueryIndex())
        .then(() => this.doValidateEntityInner())
        .catch(error => this.$common.showNotifyError(error))
    },
    /**
     * 显示复制动画
     * @param fieldId
     * @param duration 动画持续时长，毫秒
     */
    showMeteor (fieldId, duration) {
      // 注意：这里返回的是数组，所以取里面第一个
      const meteor = this.$refs['meteor' + fieldId][0]
      const copyButton = this.$refs['copyButton' + fieldId]
      meteor.init(copyButton.$el, this.$refs.copyButton.$el)
      meteor.adjust(10, 0, 89, -10)
      meteor.show(duration)
    },
    handleCopyOne (row) {
      if (!this.fieldCached(row.fieldId) && !this.fieldToCache(row.fieldId)) {
        // 动画时长
        const delay = 500
        this.showMeteor(row.fieldId, delay)
        this.addToCacheFieldTemplate(row)
        setTimeout(() => {
          this.removeToCacheFieldTemplate(row)
          this.addFieldTemplate(row)
        }, delay)
      }
    },
    initProjectOptions () {
      return this.$common.getProjectOptions()
        .then(response => this.$common.checkResult(response))
        .then(data => { this.queryForm.projectEntityOptions = data.map(project => ({ value: project.projectId, label: project.projectDesc, children: [] })) })
    },
    handleProjectChange (optionArray) {
      const projectId = optionArray[0]
      // 获取被激活的option
      const project = this.queryForm.projectEntityOptions.find(option => option.value === projectId)
      if (project.children.length) {
        return
      }
      this.loading = true
      return this.$common.getEntityOptions(projectId)
        .then(response => this.$common.checkResult(response))
        .then(data => { project.children = data.map(entity => ({ value: entity.entityId, label: entity.title })) })
        .finally(() => { this.loading = false })
    },
    handleQuery () {
      if (this.queryForm.projectEntity[1] == null) {
        this.$common.showNotifyError('请选择实体')
        return
      }
      // 将查询表单参数赋值给查询参数
      this.query.projectId = this.queryForm.projectEntity[0]
      this.query.entityId = this.queryForm.projectEntity[1]
      if (this.query.entityId !== parseInt(this.entityId)) {
        this.$router.push(`/project/${this.query.projectId}/entity/${this.query.entityId}/field`)
      }
      this.doQuery()
        .then(() => this.doQueryIndex())
        .then(() => this.doQueryMtmEntities())
        .then(() => this.doValidateEntityInner())
    },
    // 列表查询
    doQuery () {
      if (!this.query.projectId || !this.query.entityId) {
        return
      }
      this.loading = true
      return this.$ajax.get(`/${apiPath}/meta_field/list`, { params: { ...this.query, withCascadeFieldNum: 1 } })
        .then(response => this.$common.checkResult(response))
        .then(data => {
          data.forEach(value => {
            value.indexes = []
            value.orderNoEdit = false
            value.oldOrderNo = value.orderNo
            value.validate = {
              success: true
            }
          })
          this.list = data
          this.resetListContains(this.list)
        })
        .catch(error => this.$common.showNotifyError(error))
        .finally(() => { this.loading = false })
    },
    /**
     * 展示添加字段模板表单
     */
    showAddTemplateForm () {
      this.addTemplateFormVisible = true
      this.templateForm = initTemplateForm()
    },
    /**
     * 重置列表包含的特殊性质
     */
    resetListContains (list) {
      this.listContains = initListContains()
      list.forEach(field => {
        const feature = options.getFieldFeature(field)
        if (this.listContains.hasOwnProperty(feature.value)) {
          this.listContains[feature.value] = true
        }
      })
    },
    // 索引查询
    doQueryIndex () {
      if (!this.query.projectId || !this.query.entityId) {
        return
      }
      this.loading = true
      return this.$ajax.get(`/${apiPath}/meta_index/list`, { params: this.query })
        .then(response => this.$common.checkResult(response))
        .then(data => { this.indexes = data })
        .catch(error => this.$common.showNotifyError(error))
        .finally(() => { this.loading = false })
    },
    // 多对多查询
    doQueryMtmEntities () {
      if (!this.query.projectId || !this.query.entityId) {
        return
      }
      this.mtmEntitiesLoading = true
      return this.$ajax.get(`/${apiPath}/meta_entity/${this.query.entityId}/mtm_entity_list_pair`)
        .then(response => this.$common.checkResult(response))
        .then(data => { this.mtmEntities = data })
        .catch(error => this.$common.showNotifyError(error))
        .finally(() => { this.mtmEntitiesLoading = false })
    },
    handleAdd () {
      this.addTemplateFormVisible = false
      // 默认类型是系统内置模板
      let type = 'system'
      // 如果目标值是数字，则改为临时模板
      if (typeof this.templateForm.template === 'number') {
        type = 'temp'
      }
      this.$router.push(`/project/${this.projectId}/entity/${this.entityId}/field/add?\
        type=${type}&template=${this.templateForm.template}&orderNo=${this.templateForm.orderNo}`)
    },
    /**
     * 立即添加字段
     * 读取添加模板表单中的数据，调用添加字段接口
     * 处理字段添加特效
     */
    handleAddImm () {
      this.addTemplateFormVisible = false
      const multiple = this.templateForm.multiple
      const templates = this.templateForm.templates
      const template = this.templateForm.template
      const callback = (form, refresh) => {
        return this.$ajax.post(`/${apiPath}/meta_field/save`, {
          ...this.$common.removeBlankField(form),
          entityId: this.entityId
        })
          .then(response => this.$common.checkResult(response))
          // 执行页面刷新
          .then(data => {
            // 将字段放入特效列表
            this.addImmFieldIds.push(data.fieldId)
            if (refresh) {
              this.$common.showMsg('success', '添加成功')
              this.doQuery()
                .then(() => this.doQueryIndex())
                .then(() => this.doValidateEntityInner())
            }
          })
          .catch(error => this.$common.showNotifyError(error))
      }
      const doAddImm = (temp, refresh) => {
        // 如果目标值是数字，则为临时模板
        if (typeof temp === 'number') {
          return this.$ajax.get(`/${apiPath}/meta_field/${temp}`)
            .then(response => this.$common.checkResult(response))
            .then(data => callback(data, refresh))
            .catch(error => this.$common.showNotifyError(error))
        } else {
          // 系统内置模板，直接保存
          return callback(findSystemTemplate(temp), refresh)
        }
      }
      const loading = this.$loading()
      let promise = null
      if (!multiple) {
        promise = doAddImm(template, true)
      } else {
        promise = Promise.all(templates.map(temp => doAddImm(temp)))
          .then(() => {
            this.$common.showMsg('success', '添加成功')
            return this.doQuery()
              .then(() => this.doQueryIndex())
              .then(() => this.doValidateEntityInner())
          })
      }
      promise.finally(() => {
        // 定时清空特效列表
        setTimeout(() => {
          this.addImmFieldIds = []
        }, 6000)
        loading.close()
      })
    },
    handleEdit (row) {
      this.$router.push(`/project/${this.projectId}/entity/${this.entityId}/field/edit/${row.fieldId}`)
    },
    handleShow (row) {
      this.$router.push(`/project/${this.projectId}/entity/${this.entityId}/field/show/${row.fieldId}`)
    },
    /**
     * 显示外键级联扩展
     * @param field 字段记录
     */
    handleShowCascadeExt (field) {
      this.cascadeExtListVisible = true
      Vue.nextTick(() => this.$refs.cascadeExtList.init(field.entityId, field.fieldId, field.foreignEntityId))
    },
    /**
     * 重置外键级联扩展的字段数量
     */
    resetCascadeFieldNum (fieldId, cascadeFieldNum) {
      const field = this.list.find(field => field.fieldId === fieldId)
      field.cascadeFieldNum = cascadeFieldNum
    },
    /**
     * 增加外键级联扩展的字段数量
     */
    addCascadeFieldNum (fieldId, num) {
      const field = this.list.find(field => field.fieldId === fieldId)
      field.cascadeFieldNum += num
    },
    /**
     * 显示多对多级联扩展
     * @param mtmEntity 多对多级联实体
     */
    handleShowMtmCascadeExt (mtmEntity, hold) {
      this.mtmCascadeExtListVisible = true
      Vue.nextTick(() => this.$refs.mtmCascadeExtList.init(hold, mtmEntity.mtmId, parseInt(this.entityId), mtmEntity.entityId))
    },
    /**
     * 查询多对多关联实体
     */
    getMtmEntity (mtmId, cascadeEntityId) {
      let mtmEntity = this.mtmEntities.holds.find(mtmEntity => mtmEntity.mtmId === mtmId && mtmEntity.entityId === cascadeEntityId)
      if (!mtmEntity) {
        mtmEntity = this.mtmEntities.unholds.find(mtmEntity => mtmEntity.mtmId === mtmId && mtmEntity.entityId === cascadeEntityId)
      }
      return mtmEntity
    },
    /**
     * 重置外键级联扩展的字段数量
     */
    resetMtmCascadeFieldNum (mtmId, cascadeEntityId, cascadeFieldNum) {
      const mtmEntity = this.getMtmEntity(mtmId, cascadeEntityId)
      mtmEntity.cascadeFieldNum = cascadeFieldNum
    },
    /**
     * 增加外键级联扩展的字段数量
     */
    addMtmCascadeFieldNum (mtmId, cascadeEntityId, num) {
      const mtmEntity = this.getMtmEntity(mtmId, cascadeEntityId)
      mtmEntity.cascadeFieldNum += num
    },
    setActiveIndex (index) {
      this.activeIndexId = index.indexId
    },
    clearActiveIndex () {
      this.activeIndexId = null
    },
    handleIndexCommand (command) {
      this[command.method](...command.arg)
    },
    handleDelIndexField (index, field) {
      this.$common.confirm(`请确认是否从索引【${index.indexName}】中删除【${field.fieldDesc}】字段`)
        .then(() => this.$ajax.put(`/${apiPath}/meta_index/${index.indexId}/removeField`, [field.fieldId]))
        .then(response => this.$common.checkResult(response))
        .then(() => this.doQueryIndex())
        .catch(error => this.$common.showNotifyError(error))
    },
    handleDelIndex (index) {
      this.$common.confirm(`请确认是否删除索引【${index.indexName}】`)
        .then(() => this.$ajax.delete(`/${apiPath}/meta_index/${index.indexId}`))
        .then(response => this.$common.checkResult(response))
        .then(() => this.doQueryIndex())
        .catch(error => this.$common.showNotifyError(error))
    },
    /**
     * 添加索引
     */
    handleIndexAdd () {
      if (!this.selectItems.length) {
        this.$common.showMsg('warning', '请先选择需要建立索引的字段')
        return
      }
      const fieldIdStr = this.selectItems.map(field => field.fieldId).join('-')
      const indexName = 'IDX_' + shortid.generate()
        .toUpperCase().replace(/-|_/g, '')
      this.$router.push(`/project/${this.projectId}/entity/${this.entityId}/field/indexAdd?\
        fieldIds=${fieldIdStr}&indexName=${indexName}`)
    },
    handleIndexEdit (index) {
      this.$router.push(`/project/${this.projectId}/entity/${this.entityId}/field/indexEdit/${index.indexId}`)
    },
    updateOrderNo (field) {
      if (field.oldOrderNo === field.orderNo) {
        field.orderNoEdit = false
        return
      }
      this.$ajax.put(`/${apiPath}/meta_field/update_order_no`, { orderNo: field.orderNo, fieldId: field.fieldId })
        .then(response => this.$common.checkResult(response))
        .then(data => {
          field.orderNoEdit = false
          field.oldOrderNo = data
          field.orderNo = data
          this.fieldListReorder()
        })
        .catch(error => this.$common.showNotifyError(error))
    },
    doOrderNoFocus (row) {
      this.$refs['orderNoInput_' + row.fieldId].focus()
    },
    fieldListReorder () {
      this.list = this.list.sort((f1, f2) => {
        if (f1.orderNo === f2.orderNo) {
          return f1.fieldId - f2.fieldId
        }
        return f1.orderNo - f2.orderNo
      })
    },
    /**
     * 实体内部校验
     */
    doValidateEntityInner () {
      if (!this.query.projectId || !this.query.entityId || !this.list.length) {
        return
      }
      return this.$ajax.get(`/${apiPath}/meta_validate/entity_inner/${this.query.entityId}`)
        .then(response => this.$common.checkResult(response))
        .then(data => {
          const fieldValidates = data.fields
          this.list.forEach(field => {
            const fieldValidate = fieldValidates.find(fv => fv.fieldId === field.fieldId)
            field.validate = fieldValidate
          })
        })
        .catch(error => this.$common.showNotifyError(error))
    },
    handleAddConst (field) {
      const validate = field.validate
      const constName = validate.dicNotExist
      const constType = validate.suggestConstType
      const constRemark = validate.suggestConstRemark
      this.$router.push(`/project/${this.projectId}/const/add?\
          constName=${constName}&constType=${constType}&constRemark=${constRemark}`)
    },
    /**
     * 字段模板是否禁用
     */
    isTemplateOptionDisabled (field) {
      const feature = options.getFieldFeature(field)
      if (this.listContains.hasOwnProperty(feature.value)) {
        return this.listContains[feature.value]
      }
      return false
    },
    /**
     * 模板单选下拉框变更事件触发
     */
    handleTemplateChange (value) {
      if (!value) {
        return
      }
      if (typeof value === 'number') {
        this.$ajax.get(`/${apiPath}/meta_field/${value}`)
          .then(response => this.$common.checkResult(response))
          .then(data => {
            this.templateForm.orderNo = data.orderNo
          })
      } else {
        const template = findSystemTemplate(value)
        this.templateForm.orderNo = template.orderNo
      }
    }
  },
  activated () {
    const projectId = parseInt(this.projectId)
    const entityId = parseInt(this.entityId)
    this.queryForm.projectEntity[0] = projectId
    this.queryForm.projectEntity[1] = entityId
    this.query.projectId = projectId
    this.query.entityId = entityId
    this.initProjectOptions()
      .then(() => this.handleProjectChange([projectId]))
      .then(() => this.doQuery())
      .then(() => this.doQueryIndex())
      .then(() => this.doQueryMtmEntities())
      .then(() => this.doValidateEntityInner())
  }
}
</script>
<style lang="scss">
  @import '../../assets/common.scss';
  $com-color: #2759ff;
  $com-back-color: #d9e5f7;
  $check-color: #ff7f1d;
  $check-back-color: #f7ddd2;
  $u-color: #ff233b;
  $u-back-color: #f7def7;
  $add-imm-color: #ff3300;
  $active-color: #7a8cf5;

  /**
   * 新增字段特效-颜色渐变
   */
  @keyframes add-imm-field-sfx {
    from{
      background-color: $add-imm-color;
    }
    to{
      background-color: #FFFFFF;
    }
  }

  .fieldList {
    /**
     * 列表页“复制”按钮样式
     */
    .copyButtion {
      margin-left: 5px;
      color: #f56c6c;
      :hover {
        color: #f59a95;
      }
    }
    /**
     * 列表页“复制”按钮置灰样式
     */
    .copyButtion.is-disabled{
      color: #C0C4CC;
      :hover {
        color: #C0C4CC;
      }
    }

    /**
     * 调整表格行高
     */
    .el-table td {
      padding: $el-table-padding;
    }

    /**
     * 新增字段红色渐隐特效
     */
    .add-imm-field {
      animation: add-imm-field-sfx 3s linear;
    }
    /**
     * 选中数量展示行样式
     */
    .activeNum {
      min-width: 160px;
      text-align: left;
      padding: 0 0 0 20px;
    }
    /**
     * 索引框样式
     */
    .index_span {
      font-size: 10px;
      padding: 3px;
      border: 2px solid transparent;
      margin: 1px;
    }
    .indexActive {
      border-color: $active-color;
    }
    .index_com_span {
      color: $com-color;
      background-color: $com-back-color;

      &:hover {
        color: darken($com-color,8);
        background-color: darken($com-back-color,8);
      }
    }

    .index_check_span {
      color: $check-color;
      background-color: $check-back-color;

      &:hover {
        color: darken($check-color,8);
        background-color: darken($check-back-color,8);
      }
    }

    .index_u_span {
      color: $u-color;
      background-color: $u-back-color;

      &:hover {
        color: darken($u-color,8);
        background-color: darken($u-back-color,8);
      }
    }

    /**
     * 查询表单样式
     */
    .query-form-inline {
      .el-select .el-input {
        width: 150px;
      }

      .el-form-item {
        margin-bottom: 0px;
      }
    }

    /**
     * 级联扩展对话框样式
     */
    .cascadeExtDialog .el-dialog__body {
      padding-top: 10px;
    }

    /**
     * 级联按钮小红点样式
     */
    .cascadeBadge .el-badge__content {
      transform: translateY(-5%) translateX(120%);
      font-size: 10px;
      height: 12px;
      line-height: 13px;
      padding: 0 3px;
    }

    /**
     * 持有引用的多对多背景色
     */
    .hold-mtm-row {
      background: #feecec;
    }
    /**
     * 未持有引用的多对多背景色
     */
    .unhold-mtm-row {
      background: #f0f9eb;
    }

    /**
     * 多对多表头样式
     */
    .mtmEntitiesHeader {
      margin-top: 20px;
      .el-table__empty-block{
        display: none!important;
      }
    }
    /**
     * 序号输入框样式
     */
    .order-no-input {
      width: 45px;
      input {
        padding-left: 3px!important;
        padding-right: 3px!important;;
      }
    }

  }

  /**
   * 字段备注弹出框中的备注标签样式
   */
  .popper-remark-label{
    color: #606266;
    font-weight: bold;
  }
  /**
   * 字段备注弹出框中的异常标签样式
   */
  .popper-warn-label{
    color: orange;
    font-weight: bold;
  }
  /**
   * 模板选项样式
   */
  .template-option {
    width: 15px;
    display: inline-block;
    .fa-icon {
      vertical-align: text-top;
    }
  }
</style>
