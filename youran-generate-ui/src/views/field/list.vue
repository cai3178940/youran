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
              <el-button @click.native="addTemplateFormVisible = true;templateForm.template=''" type="success">添加字段</el-button>
              <el-button @click.native="handleIndexAdd" type="primary">创建索引</el-button>
              <el-button @click.native="handleDel" type="danger">删除字段</el-button>
              <el-badge :value="cacheFieldTemplateCount" :hidden="!cacheFieldTemplateCount" class="item">
                <el-button ref="copyButton" @click.native="handleCopy" type="warning" style="margin: 0 0 0 10px;">复制为模板</el-button>
              </el-badge>
          </el-form-item>
          <el-form-item>
            <help-popover name="fieldListHelp" :pic="{copyField:copyFieldUrl}">
            </help-popover>
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>
    <el-table :data="entities" style="width: 100%" @selection-change="selectionChange" v-loading="loading">
      <el-table-column type="selection" width="50"></el-table-column>
      <el-table-column label="字段描述">
        <template slot-scope="scope">
          {{ scope.row.fieldDesc }}
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
      <el-table-column label="主外键" width="70px">
        <template slot-scope="scope">
          <el-tooltip v-if="scope.row.primaryKey==1" class="item" effect="dark" content="主键" placement="right">
            <icon name="key" class="color-warning"></icon>
          </el-tooltip>
          <el-tooltip v-if="scope.row.foreignKey==1" class="item" effect="dark" content="外键" placement="right">
            <icon name="key" class="color-primary"></icon>
          </el-tooltip>
        </template>
      </el-table-column>
      <el-table-column property="orderNo" label="排序" width="50px"></el-table-column>
      <el-table-column property="fieldExample" label="字段示例"></el-table-column>
      <el-table-column
        label="操作"
        width="130">
        <template slot-scope="scope">
          <!--<el-button @click="handleShow(scope.row)" type="text" size="medium">查看</el-button>-->
          <el-button @click="handleEdit(scope.row)" type="text" size="medium" style="margin-left: 5px;">编辑</el-button>
          <el-button :ref="'copyButton'+scope.row.fieldId" :disabled="fieldCached(scope.row.fieldId)" @click="handleCopyOne(scope.row,$event)" type="text" size="medium" style="margin-left: 5px;">复制</el-button>
          <el-badge v-if="scope.row.foreignKey==1" :value="scope.row.cascadeFieldNum" :hidden="!scope.row.cascadeFieldNum" class="cascadeBadge">
            <el-button @click="handleShowCascadeExt(scope.row)" type="text" size="medium" style="margin-left: 5px;">级联</el-button>
          </el-badge>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog title="请选择字段模板" :visible.sync="addTemplateFormVisible" width="30%">
      <el-form :model="templateForm">

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
                <span style="float: left">{{ value.fieldDesc }}</span>
                <span style="float: right; color: #8492a6; font-size: 13px"><i @click.stop="removeFieldTemplate(value.fieldId)" class="el-icon-delete"></i></span>
              </el-option>
            </el-option-group>
            <el-option-group label="系统内置模板">
              <el-option v-for="(_,key) in fieldTemplate"
                         :key="key"
                         :label="key"
                         :value="key"></el-option>
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
                <span style="float: left">{{ value.fieldDesc }}</span>
                <span style="float: right; color: #8492a6; font-size: 13px"><i @click.stop="removeFieldTemplate(value.fieldId)" class="el-icon-delete"></i></span>
              </el-option>
            </el-option-group>
            <el-option-group label="系统内置模板">
              <el-option v-for="(value,key) in fieldTemplate"
                         :key="key"
                         :label="key"
                         :value="key"></el-option>
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
    <meteor v-for="field in entities" :key="field.fieldId" :ref="'meteor'+field.fieldId"></meteor>
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
      entities: [],
      indexes: [],
      loading: false,
      cascadeExtListVisible: false
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
      this.entities.forEach(field => {
        field.indexes = []
      })
      value.forEach(index => {
        index.fields.forEach(field => {
          const f = this.entities.find(item => item.fieldId === field.fieldId)
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
      if (options.showFieldLength(row.fieldType)) {
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
        .then(response => this.$common.checkResult(response.data))
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
      meteor.adjust(10, 0, 103, -8)
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
        .then(response => this.$common.checkResult(response.data))
        .then(result => { this.queryForm.projectEntityOptions = result.data.map(project => ({ value: project.projectId, label: project.projectDesc, children: [] })) })
    },
    handleProjectChange (optionArray) {
      const projectId = optionArray[0]
      // 获取被激活的option
      const project = this.queryForm.projectEntityOptions.find(option => option.value === projectId)
      if (project.children.length) {
        return
      }
      return this.$common.getEntityOptions(projectId)
        .then(response => this.$common.checkResult(response.data))
        .then(result => { project.children = result.data.entities.map(entity => ({ value: entity.entityId, label: entity.title })) })
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
    },
    // 列表查询
    doQuery () {
      if (!this.query.projectId || !this.query.entityId) {
        return
      }
      this.loading = true
      return this.$ajax.get(`/${apiPath}/meta_field/list`, { params: { ...this.query, withCascadeFieldNum: 1 } })
        .then(response => this.$common.checkResult(response.data))
        .then(result => {
          result.data.forEach(value => {
            value.indexes = []
          })
          this.entities = result.data
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
        .then(response => this.$common.checkResult(response.data))
        .then(result => { this.indexes = result.data })
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
          .then(response => this.$common.checkResult(response.data))
          // 执行页面刷新
          .then(() => {
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
            .then(response => this.$common.checkResult(response.data))
            .then(result => callback(result.data, refresh))
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
            this.doQuery()
              .then(() => this.doQueryIndex())
          })
      }
      promise.finally(() => loading.close())
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
      const field = this.entities.find(field => field.fieldId === fieldId)
      field.cascadeFieldNum = cascadeFieldNum
    },
    addCascadeFieldNum (fieldId, num) {
      const field = this.entities.find(field => field.fieldId === fieldId)
      field.cascadeFieldNum += num
    },
    handleIndexCommand (command) {
      this[command.method](...command.arg)
    },
    handleDelIndexField (index, field) {
      this.$common.confirm(`请确认是否从索引【${index.indexName}】中删除【${field.fieldDesc}】字段`)
        .then(() => this.$ajax.put(`/${apiPath}/meta_index/${index.indexId}/removeField`, [field.fieldId]))
        .then(response => this.$common.checkResult(response.data))
        .then(() => this.doQueryIndex())
        .catch(error => this.$common.showNotifyError(error))
    },
    handleDelIndex (index) {
      this.$common.confirm(`请确认是否删除索引【${index.indexName}】`)
        .then(() => this.$ajax.delete(`/${apiPath}/meta_index/${index.indexId}`))
        .then(response => this.$common.checkResult(response.data))
        .then(() => this.doQueryIndex())
        .catch(error => this.$common.showNotifyError(error))
    },
    handleIndexAdd () {
      const fieldIdStr = this.selectItems.map(field => field.fieldId).join('-')
      this.$router.push(`/project/${this.projectId}/entity/${this.entityId}/field/indexAdd/${fieldIdStr}`)
    },
    handleIndexEdit (index) {
      this.$router.push(`/project/${this.projectId}/entity/${this.entityId}/field/indexEdit/${index.indexId}`)
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
  }
}
</script>
<style lang="scss">
  @import '../../assets/common.scss';
  .fieldList {

    /**
     * 调整表格行高
     */
    .el-table td {
      padding: $el-table-padding;
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
      color: #2759ff;
      background-color: #d9e5f7;
    }

    .index_com_span:hover {
      color: #0235ff;
      background-color: #c2cdf7;
    }

    .index_check_span {
      color: #ff7f1d;
      background-color: #f7ddd2;
    }

    .index_check_span:hover {
      color: #ff6501;
      background-color: #f7cac1;
    }

    .index_u_span {
      color: #ff233b;
      background-color: #f7def7;
    }

    .index_u_span:hover {
      color: #ff000e;
      background-color: #f6c2f7;
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
  }
</style>
