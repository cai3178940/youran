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
        <el-form :inline="true" :model="queryForm" class="demo-form-inline">
          <el-form-item>
            <el-cascader
              placeholder="请选择实体"
              :options="queryForm.projectEntityOptions"
              v-model="queryForm.projectEntity"
              @active-item-change="handleProjectChange"
              @change="handleQuery">
            </el-cascader>
          </el-form-item>
          <el-form-item>
              <el-button @click.native="handleIndexAdd" type="primary">创建索引</el-button>
              <el-button @click.native="handleDel" type="danger">删除字段</el-button>
              <el-badge :value="cacheFieldTemplateCount" :hidden="!cacheFieldTemplateCount" class="item">
                <!--<el-button ref="copyButton" @click.native="handleCopy" type="warning" style="margin: 0 0 0 10px;">复制为模板</el-button>-->
                <el-button ref="copyButton" @click.native="addTemplateFormVisible = true;templateForm.template=''" type="success" style="margin: 0 0 2px 10px;">添加字段</el-button>
              </el-badge>
          </el-form-item>
          <el-form-item>
            <help-popover name="fieldListHelp" :pic="{copyField:copyFieldUrl}">
            </help-popover>
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>

    <div v-loading="loading">
      <el-table :data="list" style="width: 100%" :border="true"
                @selection-change="selectionChange" :row-class-name="rowClassName">
        <el-table-column type="selection" width="37px"></el-table-column>
        <el-table-column property="orderNo" label="序号" width="63px">
          <template slot-scope="scope">
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
          <template slot-scope="scope">
            <el-popover
              :content="scope.row.fieldComment"
              placement="top"
              trigger="click"
              popper-class="field-comment-popper">
              <el-button slot="reference" size="medium" type="text">{{ scope.row.fieldDesc }}</el-button>
            </el-popover>
            <template v-for="index in scope.row.indexes">
              <el-dropdown :key="index.indexId" @command="handleIndexCommand" size="mini" placement="bottom-start" trigger="click" style="margin-left:5px;cursor:pointer;">
                <span :class="['index_span',index.unique==1?'index_u_span':(index.uniqueCheck==1?'index_check_span':'index_com_span')]" :title="[index.unique==1?'唯一索引':(index.uniqueCheck==1?'普通索引(唯一性校验)':'普通索引')]">
                  {{index.indexName}}
                </span>
                <el-dropdown-menu slot="dropdown">
                  <el-dropdown-item :command="{method:'handleDelIndexField',arg:[index,scope.row]}">
                    <icon name="times" scale="0.7" :color="[index.unique==1?'red':'blue']"></icon> 删除索引字段
                  </el-dropdown-item>
                  <el-dropdown-item :command="{method:'handleDelIndex',arg:[index]}">
                    <icon name="trash-alt" scale="0.7" :color="[index.unique==1?'red':'blue']"></icon> 删除整个索引
                  </el-dropdown-item>
                  <el-dropdown-item :command="{method:'handleIndexEdit',arg:[index]}">
                    <icon name="edit" scale="0.7" :color="[index.unique==1?'red':'blue']"></icon> 编辑索引
                  </el-dropdown-item>
                </el-dropdown-menu>
              </el-dropdown>
            </template>
          </template>
        </el-table-column>
        <el-table-column label="字段名">
          <template slot-scope="scope">
            {{ scope.row.jfieldName }} / {{ scope.row.fieldName }}
          </template>
        </el-table-column>
        <el-table-column label="字段类型" width="200px">
          <template slot-scope="scope">
            {{ scope.row.jfieldType | optionLabel('jfieldTypeOptions')}}
            / {{ scope.row.fieldType | optionLabel('fieldTypeOptions') }}{{scope.row | lengthAndScale}}
          </template>
        </el-table-column>
        <el-table-column label="非空" width="50px">
          <template slot-scope="scope">
            <icon v-if="scope.row.notNull==1" name="check" class="color-success"></icon>
            <icon v-if="scope.row.notNull!=1" name="times" class="color-danger"></icon>
          </template>
        </el-table-column>
        <el-table-column label="特性" width="70px">
          <template slot-scope="scope">
            <template v-for="feature in getFieldFeatures(scope.row)">
              <el-tooltip :key="feature.value" class="item" effect="dark" :content="feature.label" placement="right">
                <icon :name="feature.icon"
                      :style="feature.style">
                </icon>
              </el-tooltip>
            </template>
          </template>
        </el-table-column>
        <el-table-column property="fieldExample" label="字段示例"></el-table-column>
        <el-table-column
          label="操作"
          width="130">
          <template slot-scope="scope">
            <el-button @click="handleEdit(scope.row)" type="text" size="medium" style="margin-left: 5px;">编辑</el-button>
            <el-button :ref="'copyButton'+scope.row.fieldId" :disabled="fieldCached(scope.row.fieldId)" @click="handleCopyOne(scope.row,$event)" type="text" size="medium" style="margin-left: 5px;">复制</el-button>
            <el-badge v-if="scope.row.foreignKey==1" :value="scope.row.cascadeFieldNum" :hidden="!scope.row.cascadeFieldNum" class="cascadeBadge">
              <el-button @click="handleShowCascadeExt(scope.row)" type="text" size="medium" style="margin-left: 5px;">级联</el-button>
            </el-badge>
          </template>
        </el-table-column>
      </el-table>

      <!--<template v-if="mtmEntities.holds.length || mtmEntities.unholds.length">-->
      <template v-if="false">
        <!-- 纯表头 -->
        <div class="mtmEntitiesHeader" style="margin-top: 20px;">
          <el-table :data="[]" style="width: 100%" :border="true">
            <!--<el-table-column width="50px"/>-->
            <el-table-column width="200px" label="多对多关联实体"/>
            <el-table-column width="200px" label="类名"/>
            <el-table-column width="200px" label="表名"/>
            <el-table-column label="描述"/>
            <el-table-column label="操作" width="130"/>
          </el-table>
        </div>
        <!-- 持有引用的实体 -->
        <el-table v-if="mtmEntities.holds.length" :data="mtmEntities.holds" :border="true"
                  style="width: 100%" :show-header="false" row-class-name="hold-mtm-row">
          <!--<el-table-column width="50px"/>-->
          <el-table-column width="200px" property="title"/>
          <el-table-column width="200px" property="className"/>
          <el-table-column width="200px" property="tableName"/>
          <el-table-column property="desc"/>
          <el-table-column width="130">
            <template slot-scope="scope">
              <el-badge :value="scope.row.cascadeFieldNum" :hidden="!scope.row.cascadeFieldNum" class="cascadeBadge">
                <el-button @click="handleShowCascadeExt(scope.row)" type="text" size="medium" style="margin-left: 5px;">级联</el-button>
              </el-badge>
            </template>
          </el-table-column>
        </el-table>
        <!-- 未持有引用的实体 -->
        <el-table v-if="mtmEntities.unholds.length" :data="mtmEntities.unholds" :border="true"
                  style="width: 100%" :show-header="false" row-class-name="unhold-mtm-row">
          <!--<el-table-column width="50px"/>-->
          <el-table-column width="200px" property="title"/>
          <el-table-column width="200px" property="className"/>
          <el-table-column width="200px" property="tableName"/>
          <el-table-column property="desc"/>
          <el-table-column width="130">
            <template slot-scope="scope">
              <el-badge :value="scope.row.cascadeFieldNum" :hidden="!scope.row.cascadeFieldNum" class="cascadeBadge">
                <el-button @click="handleShowCascadeExt(scope.row)" type="text" size="medium" style="margin-left: 5px;">级联</el-button>
              </el-badge>
            </template>
          </el-table-column>
        </el-table>
      </template>
    </div>

    <el-dialog title="请选择字段模板" :visible.sync="addTemplateFormVisible" width="30%">
      <el-form :model="templateForm" size="small">

        <el-form-item label="模式：" label-width="100px">
          <el-radio-group v-model="templateForm.multiple">
            <el-radio border label="0">表单编辑</el-radio>
            <el-radio border label="1">批量保存</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item v-show="templateForm.multiple=='0'" label="模板：" label-width="100px">
          <el-select v-model="templateForm.template">
            <el-option-group>
              <el-option label="不使用模板" value=""></el-option>
            </el-option-group>
            <el-option-group v-if="cacheFieldTemplateCount>0" label="临时模板">
              <el-option v-for="value in cacheFieldTemplate"
                         :key="value.fieldId"
                         :label="value.fieldDesc"
                         :value="value.fieldId">
                <span style="float: left">
                  <span class="template-option">
                    <template v-for="feature in getFieldFeatures(value)">
                      <icon :key="feature.value"
                              :name="feature.icon"
                              :style="feature.style">
                      </icon>
                    </template>
                  </span>
                  {{ value.fieldDesc }}
                </span>
                <span style="float: right; color: #8492a6; font-size: 13px"><i @click.stop="removeFieldTemplate(value.fieldId)" class="el-icon-delete"></i></span>
              </el-option>
            </el-option-group>
            <el-option-group label="系统内置模板">
              <el-option v-for="(value,key) in fieldTemplate"
                         :key="key"
                         :label="key"
                         :value="key">
                <span style="float: left">
                  <span class="template-option">
                    <template v-for="feature in getFieldFeatures(value)">
                      <icon :key="feature.value"
                            :name="feature.icon"
                            :style="feature.style">
                      </icon>
                    </template>
                  </span>
                  {{ key }}
                </span>
              </el-option>
            </el-option-group>
          </el-select>
        </el-form-item>
        <el-form-item v-show="templateForm.multiple=='1'" label="模板：" label-width="100px">
          <el-select
            v-model="templateForm.templates"
            multiple
            collapse-tags>
            <el-option-group v-if="cacheFieldTemplateCount>0" label="临时模板">
              <el-option v-for="value in cacheFieldTemplate"
                         :key="value.fieldId"
                         :label="value.fieldDesc"
                         :value="value.fieldId">
                <span style="float: left">
                  <span class="template-option">
                    <template v-for="feature in getFieldFeatures(value)">
                      <icon :key="feature.value"
                            :name="feature.icon"
                            :style="feature.style">
                      </icon>
                    </template>
                  </span>
                  {{ value.fieldDesc }}
                </span>
                <span style="float: right; color: #8492a6; font-size: 13px">
                  <i @click.stop="removeFieldTemplate(value.fieldId)" class="el-icon-delete"></i>
                </span>
              </el-option>
            </el-option-group>
            <el-option-group label="系统内置模板">
              <el-option v-for="(value,key) in fieldTemplate"
                         :key="key"
                         :label="key"
                         :value="key">
                <span style="float: left">
                  <span class="template-option">
                    <template v-for="feature in getFieldFeatures(value)">
                      <icon :key="feature.value"
                            :name="feature.icon"
                            :style="feature.style">
                      </icon>
                    </template>
                  </span>
                  {{ key }}
                </span>
              </el-option>
            </el-option-group>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="addTemplateFormVisible = false">取 消</el-button>
        <el-button v-if="templateForm.multiple=='0'" type="primary" @click="handleAdd">开始编辑</el-button>
        <el-button v-if="templateForm.multiple=='1'" type="success" @click="handleAddImm">立即保存</el-button>
      </div>
    </el-dialog>

    <el-dialog class="cascadeExtDialog" title="级联扩展" :visible.sync="cascadeExtListVisible" width="60%">
      <cascade-ext-list ref="cascadeExtList" @cascadeFieldNumChange="resetCascadeFieldNum" @cascadeFieldNumAdd="addCascadeFieldNum"></cascade-ext-list>
    </el-dialog>
    <!-- 所有复制按钮上的浮动小红点 -->
    <meteor v-for="field in list" :key="field.fieldId" :ref="'meteor'+field.fieldId"></meteor>
  </div>
</template>

<script>
import Vue from 'vue'
import cascadeExtList from '../cascadeExt/list'
import options from '@/components/options'
import { apiPath } from '@/components/common'
import fieldTemplate from '@/components/fieldTemplate'
import copyFieldUrl from '@/assets/copyField.gif'
import meteor from '@/components/meteor'
import { mapGetters, mapState, mapMutations } from 'vuex'

export default {
  name: 'fieldList',
  props: ['projectId', 'entityId'],
  components: {
    'cascade-ext-list': cascadeExtList,
    meteor
  },
  data () {
    return {
      // 此处不知为何，生产编译时，图片路径有误。本来应该是ui/static变成了uistatic
      copyFieldUrl: copyFieldUrl.replace('uistatic', 'ui/static'),
      addTemplateFormVisible: false,
      fieldTemplate,
      templateForm: {
        multiple: '0',
        template: '',
        templates: []
      },
      // 查询参数
      query: {
        projectId: null,
        entityId: null
      },
      // 查询表单参数
      queryForm: {
        projectEntityOptions: [],
        projectEntity: [0, 0]
      },
      activeNum: 0,
      selectItems: [],
      list: [],
      indexes: [],
      addImmFieldIds: [],
      loading: false,
      cascadeExtListVisible: false,
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
    ...mapGetters(['fieldCached'])
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
      'addFieldTemplate'
    ]),
    getFieldFeatures: options.getFieldFeatures,

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
        .catch(error => this.$common.showNotifyError(error))
    },
    handleCopy () {
      if (this.activeNum <= 0) {
        this.$common.showMsg('warning', '请选择字段')
        return
      }
      for (const item of this.selectItems) {
        if (!this.fieldCached(item.fieldId)) {
          this.addFieldTemplate(item)
          this.showMeteor(item.fieldId)
        }
      }
    },
    /**
     * 显示复制动画
     * @param fieldId
     */
    showMeteor (fieldId) {
      // 注意：这里返回的是数组，所以取里面第一个
      const meteor = this.$refs['meteor' + fieldId][0]
      const copyButton = this.$refs['copyButton' + fieldId]
      meteor.init(copyButton.$el, this.$refs.copyButton.$el)
      meteor.adjust(10, 0, 90, -8)
      meteor.show(500)
    },
    handleCopyOne (row) {
      if (!this.fieldCached(row.fieldId)) {
        this.addFieldTemplate(row)
        this.showMeteor(row.fieldId)
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
      return this.$common.getEntityOptions(projectId)
        .then(response => this.$common.checkResult(response))
        .then(data => { project.children = data.map(entity => ({ value: entity.entityId, label: entity.title })) })
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
          })
          this.list = data
        })
        .catch(error => this.$common.showNotifyError(error))
        .finally(() => { this.loading = false })
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
      this.loading = true
      return this.$ajax.get(`/${apiPath}/meta_entity/${this.query.entityId}/mtm_entity_list_pair`)
        .then(response => this.$common.checkResult(response))
        .then(data => { this.mtmEntities = data })
        .catch(error => this.$common.showNotifyError(error))
        .finally(() => { this.loading = false })
    },
    handleAdd () {
      this.addTemplateFormVisible = false
      // 默认类型是系统内置模板
      let type = 'system'
      // 如果目标值是数字，则改为临时模板
      if (typeof this.templateForm.template === 'number') {
        type = 'temp'
      }
      this.$router.push(`/project/${this.projectId}/entity/${this.entityId}/field/add?type=${type}&template=${this.templateForm.template}`)
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
          return callback(fieldTemplate[temp], refresh)
        }
      }
      const loading = this.$loading()
      let promise = null
      if (multiple === '0') {
        promise = doAddImm(template, true)
      } else {
        promise = Promise.all(templates.map(temp => doAddImm(temp)))
          .then(() => {
            this.$common.showMsg('success', '添加成功')
            return this.doQuery()
              .then(() => this.doQueryIndex())
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
    handleShowCascadeExt (row) {
      this.cascadeExtListVisible = true
      Vue.nextTick(() => this.$refs.cascadeExtList.init(row.entityId, row.fieldId, row.foreignEntityId))
    },
    resetCascadeFieldNum (fieldId, cascadeFieldNum) {
      const field = this.list.find(field => field.fieldId === fieldId)
      field.cascadeFieldNum = cascadeFieldNum
    },
    addCascadeFieldNum (fieldId, num) {
      const field = this.list.find(field => field.fieldId === fieldId)
      field.cascadeFieldNum += num
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
    handleIndexAdd () {
      if (!this.selectItems.length) {
        this.$common.showMsg('warning', '请先选择需要建立索引的字段')
        return
      }
      const fieldIdStr = this.selectItems.map(field => field.fieldId).join('-')
      this.$router.push(`/project/${this.projectId}/entity/${this.entityId}/field/indexAdd/${fieldIdStr}`)
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

  @keyframes fade {
    from{
      background-color: $add-imm-color;
    }
    to{
      background-color: #FFFFFF;
    }
  }

  .fieldList {

    /**
     * 调整表格行高
     */
    .el-table td {
      padding: $el-table-padding;
    }

    .add-imm-field {
      animation: fade 3s linear;
    }

    .activeNum {
      min-width: 160px;
      text-align: left;
      padding: 0 0 0 20px;
    }

    .index_span {
      font-size: 10px;
      border-radius: 4px;
      padding: 3px;
      margin: 1px;
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

    .demo-form-inline {
      .el-select .el-input {
        width: 150px;
      }

      .el-form-item {
        margin-bottom: 0px;
      }
    }

    .cascadeExtDialog .el-dialog__body {
      padding-top: 10px;
    }

    .cascadeBadge .el-badge__content {
      transform: translateY(-5%) translateX(120%);
      font-size: 10px;
      height: 12px;
      line-height: 13px;
      padding: 0 3px;
    }

    .hold-mtm-row {
      background: #feecec;
    }

    .unhold-mtm-row {
      background: #f0f9eb;
    }

    .mtmEntitiesHeader {
      .el-table__empty-block{
        display: none!important;
      }
    }
    .order-no-input {
      width: 45px;
      input {
        padding-left: 3px!important;
        padding-right: 3px!important;;
      }
    }

  }
  .template-option {
    width: 15px;
    display: inline-block;
    .fa-icon {
      vertical-align: text-top;
    }
  }
  .field-comment-popper {
    text-align: center;
  }
</style>
