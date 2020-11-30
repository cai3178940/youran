<template>
  <div class="fieldFormDiv">
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/project' }">项目管理</el-breadcrumb-item>
      <el-breadcrumb-item :to="{ path: `/project/${this.projectId}/entity` }">实体管理</el-breadcrumb-item>
      <el-breadcrumb-item :to="{ path: `/project/${this.projectId}/entity/${this.entityId}/field` }">字段管理</el-breadcrumb-item>
      <el-breadcrumb-item>
        {{edit?'编辑字段':'创建字段'}}
      </el-breadcrumb-item>
    </el-breadcrumb>
    <el-row type="flex" align="middle" :gutter="10">
      <el-col :span="14">
        <el-form v-if="ready" ref="fieldForm" class="fieldForm"
                 :rules="rules" :model="form"
                 label-width="120px" size="small">
          <el-form-item label="性质">
            <help-popover name="field.feature">
              <el-input class="red-font" :value="fieldFeature.label" readonly>
                <!-- 如果不是主键也不是特殊字段，则支持切换模板-->
                <el-dropdown v-if="!form.specialField && !form.primaryKey" slot="append" trigger="click" @command="handleFieldFeatureChange">
                  <span class="el-dropdown-link">
                    切换<i class="el-icon-arrow-down el-icon--right"></i>
                  </span>
                  <el-dropdown-menu slot="dropdown">
                    <el-dropdown-item :disabled="fieldFeature.value===commonFeature.value"
                                      :command="{method:'changeCommonFeature'}">
                      <svg-icon :className="commonFeature.iconClassName"
                                :iconClass="commonFeature.icon"></svg-icon>
                      {{commonFeature.label}}
                    </el-dropdown-item>
                    <el-dropdown-item :disabled="fieldFeature.value===fkFeature.value"
                                      :command="{method:'changeFkFeature'}">
                      <svg-icon :className="fkFeature.iconClassName"
                                :iconClass="fkFeature.icon"></svg-icon>
                      {{fkFeature.label}}
                    </el-dropdown-item>
                  </el-dropdown-menu>
                </el-dropdown>
              </el-input>
            </help-popover>
          </el-form-item>
          <el-form-item v-if="!isAttrHide('pkStrategy')" label="主键策略">
            <help-popover name="field.pkStrategy">
              <el-select v-model="form.pkStrategy" style="width:100%;">
                <el-option
                  v-for="item in primaryKeyStrategy"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </help-popover>
          </el-form-item>
          <el-form-item v-if="!isAttrHide('foreignKey')" label="外键关联" prop="foreignKey">
            <help-popover name="field.foreignKey">
              <el-cascader
                placeholder="请选择外键字段"
                :options="entityFieldOptions"
                v-model="foreignField"
                @change="handleForeignKeyChange"
                @expand-change="handleForeignEntityChange">
              </el-cascader>
            </help-popover>
          </el-form-item>
          <el-form-item label="字段名" prop="jieldNameCouple">
            <help-popover name="field.jfieldName">
              <el-col :span="11" class="col-left">
                <el-input v-lower-case-first v-model="form.jfieldName" placeholder="java字段名，例如：age" tabindex="10"></el-input>
                <el-button size="mini" type="text" @click="form.jfieldName = $common.camelCase(form.jfieldName)">转驼峰</el-button>
              </el-col>
              <el-col :span="2" class="col-inner" style="text-align: center;">
                <el-tooltip class="item" effect="dark" content="粘贴到右边并转下划线" placement="top">
                  <el-button type="text" @click="copyJfieldNameToFieldName()" tabindex="11">
                    <svg-icon className="table-cell-icon color-primary"
                              svgStyle="vertical-align: middle;" iconClass="double-right"></svg-icon>
                  </el-button>
                </el-tooltip>
              </el-col>
              <el-col :span="11" class="col-right">
                <el-input v-model="form.fieldName" placeholder="mysql字段名，例如：age" tabindex="20"></el-input>
                <el-button size="mini" type="text" @click="form.fieldName = $common.snakeCase(form.fieldName)">转下划线</el-button>
              </el-col>
            </help-popover>
          </el-form-item>
          <el-form-item label="字段标题" prop="fieldDesc">
            <help-popover name="field.fieldDesc">
              <el-input v-model="form.fieldDesc" placeholder="字段标题，例如：年龄" tabindex="30"></el-input>
            </help-popover>
          </el-form-item>
          <el-form-item label="字段注释" prop="fieldComment">
            <help-popover name="field.fieldComment">
              <el-input v-model="form.fieldComment" type="textarea" :rows="2"
                        placeholder="字段注释，尽量写详细，例如：年龄（法定年龄、周岁）" tabindex="40"></el-input>
            </help-popover>
          </el-form-item>
          <el-form-item label="字段示例" prop="fieldExample">
            <help-popover name="field.fieldExample">
              <el-input v-model="form.fieldExample" placeholder="字段示例，例如年龄字段：21"
                        tabindex="50"></el-input>
            </help-popover>
          </el-form-item>
          <el-form-item label="字段类型" prop="fieldType">
            <help-popover name="field.fieldType">
              <el-col :span="6" class="col-left">
                <el-select v-model="form.jfieldType" @change="jfieldTypeChange"
                           style="width:100%;" filterable placeholder="java类型" tabindex="60">
                  <el-option
                    v-for="item in jfieldTypeOptions"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                  </el-option>
                </el-select>
              </el-col>
              <el-col :span="6">
                <el-select v-model="form.fieldType" @change="fieldTypeChange"
                           style="width:100%;" filterable placeholder="sql类型" tabindex="70">
                  <el-option
                    v-for="item in fieldTypeOptions"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value"
                    :disabled="item.disabled">
                    <span style="float: left">{{ item.selectLabel }}</span>
                  </el-option>
                </el-select>
              </el-col>
              <template v-if="fieldLengthVisible && fieldScaleVisible">
                <el-col :span="6">
                  <el-input-number v-model="form.fieldLength" controls-position="right"
                                   style="width:100%;" :min="0" placeholder="长度"
                                   tabindex="80"></el-input-number>
                </el-col>
                <el-col :span="6" class="col-right">
                  <el-input-number v-model="form.fieldScale" controls-position="right"
                                   style="width:100%;" :min="0" placeholder="精度"
                                   tabindex="90"></el-input-number>
                </el-col>
              </template>
              <template v-if="fieldLengthVisible && !fieldScaleVisible">
                <el-col :span="12" class="col-right">
                  <el-input-number v-model="form.fieldLength" controls-position="right"
                                   style="width:100%;" :min="0" placeholder="长度"
                                   tabindex="100"></el-input-number>
                </el-col>
              </template>
            </help-popover>
          </el-form-item>
          <el-form-item  v-if="!isAttrHide('dicType')" label="枚举字典" prop="dicType">
            <help-popover name="field.dicType">
              <el-autocomplete :disabled="dicTypeDisabled" style="width:100%;"
                               v-model="form.dicType"
                               :fetch-suggestions="queryDicType"
                               placeholder="请输入枚举字典，例如：Sex"
                               tabindex="110"
              ></el-autocomplete>
            </help-popover>
          </el-form-item>
          <el-form-item label="不能为空" prop="notNull">
            <help-popover name="field.notNull">
              <el-row type="flex" align="middle" :gutter="10">
                <el-col :span="6">
                <el-checkbox v-model="form.notNull" :disabled="isAttrDisable('notNull')"
                             tabindex="120">是</el-checkbox>
                </el-col>
                <el-col :span="18" class="col-right">
                  <span class="inline-label">默认值</span>
                  <el-input v-model="form.defaultValue"
                            tabindex="121"></el-input>
                </el-col>
              </el-row>
            </help-popover>
          </el-form-item>
          <el-form-item v-if="!isAttrHide('query')" label="可搜索" prop="query">
            <help-popover name="field.query">
              <el-row type="flex" align="middle" :gutter="10">
                <el-col :span="6">
                  <el-checkbox v-model="form.query"
                               tabindex="130">是</el-checkbox>
                </el-col>
                <el-col :span="18" class="col-right">
                  <span class="inline-label">搜索方式</span>
                  <el-select :disabled="queryTypeDisabled" clearable v-model="form.queryType" style="" filterable
                             placeholder="请选择" tabindex="140">
                    <el-option
                      v-for="item in allowedQueryTypes"
                      :key="item.value"
                      :label="item.label"
                      :value="item.value"
                      :disabled="item.disabled">
                    </el-option>
                  </el-select>
                </el-col>
              </el-row>
            </help-popover>
          </el-form-item>
          <el-form-item v-if="!isAttrHide('attributes')" label="字段功能">
            <help-popover name="field.attributes">
              <el-checkbox v-model="form.list"
                           :disabled="isAttrDisable('attr-list')"
                           tabindex="150">
                列表展示
              </el-checkbox>
              <el-checkbox v-model="form.show"
                           :disabled="isAttrDisable('attr-show')"
                           tabindex="160">
                详情展示
              </el-checkbox>
              <el-checkbox v-model="form.insert"
                           :disabled="isAttrDisable('attr-insert')"
                           tabindex="180">
                可插入
              </el-checkbox>
              <el-checkbox v-model="form.update"
                           :disabled="isAttrDisable('attr-update') || !form.show"
                           tabindex="170">
                可修改
              </el-checkbox>
              <el-checkbox v-model="form.listSort"
                           :disabled="isAttrDisable('attr-listSort') || !form.list"
                           tabindex="190">
                可排序
              </el-checkbox>
            </help-popover>
          </el-form-item>
          <el-form-item label="列宽">
            <help-popover name="field.columnWidth">
              <el-input-number v-model="form.columnWidth" controls-position="right"
                               style="width:100%;" placeholder="列宽"
                               :step="10" :min="0" :max="1000"
                               :disabled="!form.list"
                               tabindex="200"></el-input-number>
            </help-popover>
          </el-form-item>
          <el-form-item label="编辑框" prop="editType">
            <help-popover name="field.editType">
              <el-select v-model="form.editType" filterable placeholder="编辑框"
                         style="width:100%;" tabindex="210">
                <el-option
                  v-for="item in allowedEditTypes"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                  :disabled="item.disabled">
                  <span style="float: left">{{ item.label }}</span>
                </el-option>
              </el-select>
            </help-popover>
          </el-form-item>
          <el-form-item label="标签" prop="labels" >
            <help-popover name="field.labels">
              <el-button v-for="(label,index) in form.labels"
                         :key="index" class="inner-form-button"
                         type="primary" @click="editLabel(index, label)"
                         plain>
                {{label | displayLabel}}
              </el-button>
              <el-button type="success" @click="addLabel"
                         class="inner-form-button inner-add-button"
                         icon="el-icon-plus" plain>
              </el-button>
            </help-popover>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="submit()">提交</el-button>
            <el-button v-if="edit" type="warning" @click="reset()">重置</el-button>
            <el-button @click="goBack(true)">返回</el-button>
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>
    <el-backtop target=".el-main"></el-backtop>
    <label-form ref="labelForm" @submit="onLabelSubmit" @remove="onLabelRemove"></label-form>
  </div>
</template>

<script>
import options from '@/utils/options'
import entityApi from '@/api/entity'
import fieldApi from '@/api/field'
import constApi from '@/api/const'
import labelForm from '@/components/Label/form'
import { initFormBean, getRules } from './model'
import { findSystemTemplate } from '@/utils/field-template'

export default {
  name: 'fieldForm',
  props: ['projectId', 'entityId', 'fieldId'],
  components: {
    labelForm
  },
  data () {
    const edit = !!this.fieldId
    return {
      ready: false,
      // 字段性质
      fieldFeature: {},
      // 需要隐藏的输入域
      hiddenAttrs: [],
      // 需要禁用的输入域
      disabledAttrs: [],
      edit: edit,
      fieldTypeOptions: options.getFieldTypeOptions(),
      jfieldTypeOptions: options.jfieldTypeOptions,
      specialFieldFeatures: options.specialFieldFeatures,
      primaryKeyStrategy: options.primaryKeyStrategy,
      entityFieldOptions: [],
      constList: null,
      queryTypeDisabled: true,
      foreignField: [0, 0],
      old: initFormBean(edit),
      form: initFormBean(edit),
      rules: getRules(this),
      commonFeature: options.commonFeature,
      fkFeature: options.fkFeature,
      inputVisible: false,
      inputValue: '',
      labels: null
    }
  },
  filters: {
    displayLabel (label) {
      if (label.value) {
        return label.key + ':' + label.value
      } else {
        return label.key
      }
    }
  },
  computed: {
    dicTypeDisabled () {
      if (this.form.primaryKey || this.form.specialField) {
        if (this.form.specialField === 'status') {
          return false
        }
        return true
      }
      return false
    },
    fieldLengthVisible () {
      return options.showFieldLength(this.form.fieldType, 1)
    },
    fieldScaleVisible () {
      return options.showFieldScale(this.form.fieldType)
    },
    allowedEditTypes () {
      return options.getAllowedEditTypes(this.form)
    },
    allowedQueryTypes () {
      return options.getAllowedQueryTypes(this.form)
    }
  },
  watch: {
    'form.primaryKey' (value) {
      if (value) {
        this.form.notNull = true
        this.form.specialField = ''
      } else {
        this.form.pkStrategy = 0
      }
    },
    'form.query' (value) {
      if (value) {
        this.queryTypeDisabled = false
      } else {
        this.queryTypeDisabled = true
        this.form.queryType = ''
      }
    },
    'form.show' (value) {
      if (!value) {
        this.form.update = false
      }
    },
    'form.list' (value) {
      if (!value) {
        this.form.listSort = false
      }
    },
    'dicTypeDisabled' (value) {
      if (value) {
        this.form.dicType = ''
      }
    }
  },
  methods: {
    /**
     * 输入域是否隐藏
     */
    isAttrHide (attr) {
      return this.hiddenAttrs.includes(attr)
    },
    /**
     * 输入域是否禁用
     */
    isAttrDisable (attr) {
      return this.disabledAttrs.includes(attr)
    },
    /**
     * java字段类型变化后
     */
    jfieldTypeChange (value) {
      if (!value) {
        this.fieldTypeOptions = options.getFieldTypeOptions()
        return
      }
      const typeObj = this.jfieldTypeOptions.find(obj => obj.value === value)
      if (typeObj && typeObj.allowFieldTypes) {
        if (!typeObj.allowFieldTypes.includes(this.form.fieldType)) {
          // 设置默认字段类型
          this.form.fieldType = typeObj.defaultFieldType
          // 查找默认字段类型，并设置默认长度
          const defaultField = this.fieldTypeOptions.find(fieldType => fieldType.value === typeObj.defaultFieldType)
          this.form.fieldLength = defaultField.fieldLength
        }
        // 下拉列表项设置是否可选
        this.fieldTypeOptions.forEach(fieldType => {
          if (typeObj.allowFieldTypes.includes(fieldType.value)) {
            fieldType.disabled = false
          } else {
            fieldType.disabled = true
          }
        })
      }
    },
    /**
     * mysql字段类型变化后，触发字段长度变化
     * 这里没有用watch是为了首次加载不调用
     * @param value
     */
    fieldTypeChange (value) {
      if (!value) {
        return
      }
      const typeObj = this.fieldTypeOptions.find(obj => obj.value === value)
      if (typeObj && typeObj.fieldLength) {
        this.form.fieldLength = typeObj.fieldLength
      }
    },
    initForeignEntityOptions () {
      return entityApi.getList(this.projectId)
        .then(data => { this.entityFieldOptions = data.map(entity => ({ value: entity.entityId, label: entity.title, children: [] })) })
    },
    handleForeignEntityChange (optionArray) {
      const entityId = optionArray[0]
      // 获取被激活的option
      const entity = this.entityFieldOptions.find(option => option.value === entityId)
      // 没找到则直接返回
      if (!entity) {
        return
      }
      if (entity.children.length) {
        return
      }
      return fieldApi.getList(entityId, false)
        .then(data => {
          entity.children = data.filter(field => field.primaryKey)
            .map(field => ({
              value: field.fieldId,
              label: field.fieldDesc,
              jfieldName: field.jfieldName,
              fieldName: field.fieldName,
              fieldComment: field.fieldComment,
              fieldExample: field.fieldExample,
              jfieldType: field.jfieldType,
              fieldType: field.fieldType,
              fieldLength: field.fieldLength,
              fieldScale: field.fieldScale
            }))
        })
    },
    handleForeignKeyChange (optionArray) {
      const entityId = optionArray[0]
      const fieldId = optionArray[1]
      const entity = this.entityFieldOptions.find(option => option.value === entityId)
      const field = entity.children.find(option => option.value === fieldId)
      this.form.jfieldName = field.jfieldName
      this.form.fieldName = field.fieldName
      this.form.fieldDesc = field.label
      this.form.fieldComment = field.fieldComment
      this.form.fieldExample = field.fieldExample
      this.form.jfieldType = field.jfieldType
      this.form.fieldType = field.fieldType
      this.form.fieldLength = field.fieldLength
      this.form.fieldScale = field.fieldScale
    },
    // 搜索可用枚举字典
    queryDicType (queryString, cb) {
      // 定义回调操作
      const action = () => {
        const constList = this.constList.slice(0)
        const results = queryString ? constList.filter(
          c => c.constName.toLowerCase().indexOf(queryString.toLowerCase()) === 0
        ) : constList
        cb(results.map(c => ({ value: c.constName })))
      }
      if (this.constList) {
        action()
      } else {
        constApi.getList(this.projectId)
          .then(data => {
            this.constList = data
            action()
          })
      }
    },
    getField () {
      return fieldApi.get(this.fieldId)
        .then(data => { this.old = data })
        .catch(error => this.$common.showNotifyError(error))
    },
    reset () {
      for (const key in initFormBean(true)) {
        this.form[key] = this.old[key]
      }
      this.formReady()
    },
    submit () {
      // 表单预处理
      if (!options.showFieldScale(this.form.fieldType)) {
        this.form.fieldScale = null
      }
      this.form.foreignEntityId = this.foreignField[0]
      this.form.foreignFieldId = this.foreignField[1]
      let loading = null
      // 校验表单
      this.$refs.fieldForm.validate()
        // 提交表单
        .then(() => {
          loading = this.$loading()
          return fieldApi.saveOrUpdate(this.$common.removeBlankField(this.form), this.edit)
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
    goBack (preferHistory) {
      if (preferHistory && window.history.length > 1) {
        this.$router.go(-1)
      } else {
        this.$router.push(`/project/${this.projectId}/entity/${this.entityId}/field`)
      }
    },
    copyJfieldNameToFieldName () {
      this.form.fieldName = this.$common.snakeCase(this.form.jfieldName)
      this.$refs.fieldForm.validateField('jieldNameCouple')
    },
    /**
     * 数据准备完成
     */
    formReady () {
      this.fieldFeature = options.getFieldFeature(this.form)
      this.hiddenAttrs = this.fieldFeature.hiddenAttrs
      this.disabledAttrs = this.fieldFeature.disabledAttrs
      this.ready = true
    },
    /**
     * 切换字段
     */
    handleFieldFeatureChange (command) {
      this[command.method]()
    },
    /**
     * 切换普通字段
     */
    changeCommonFeature () {
      this.form.foreignKey = false
      this.formReady()
    },
    /**
     * 切换外键字段
     */
    changeFkFeature () {
      this.form.foreignKey = true
      this.formReady()
    },
    editLabel (index, label) {
      this.$refs.labelForm.show({
        projectId: this.projectId,
        labelType: 'field'
      }, label, index)
    },
    addLabel () {
      this.$refs.labelForm.show({
        projectId: this.projectId,
        labelType: 'field'
      }, null, this.form.labels.length)
    },
    onLabelSubmit (index, label) {
      if (index >= this.form.labels.length) {
        this.form.labels.push(label)
      } else {
        this.$set(this.form.labels, index, label)
      }
    },
    onLabelRemove (index, label) {
      if (index < this.form.labels.length) {
        this.form.labels.splice(index, 1)
      }
    }
  },
  created () {
    if (this.edit) {
      Promise.all([this.getField(), this.initForeignEntityOptions()])
        .then(() => this.reset())
        .then(() => this.handleForeignEntityChange([this.form.foreignEntityId]))
        .then(() => {
          this.foreignField = [this.form.foreignEntityId, this.form.foreignFieldId]
        })
        .then(() => this.formReady())
        .catch(error => this.$common.showNotifyError(error))
    } else {
      const entityId = parseInt(this.entityId)
      const promise = this.initForeignEntityOptions()
      const type = this.$router.currentRoute.query.type
      const template = this.$router.currentRoute.query.template
      let orderNo = this.$router.currentRoute.query.orderNo
      if (!orderNo) {
        orderNo = 1
      }
      if (!template) {
        this.form.entityId = entityId
        this.formReady()
        return
      }
      if (type === 'system') {
        this.form = Object.assign({}, findSystemTemplate(template), {
          entityId: entityId,
          orderNo: orderNo
        })
        this.formReady()
      }
      if (type === 'temp') {
        const promise2 = fieldApi.get(template)
          .then(data => new Promise((resolve, reject) => {
            this.form = Object.assign({}, data, {
              entityId: entityId,
              orderNo: orderNo
            })
            return resolve()
          }))
        Promise.all([promise, promise2])
          .then(() => {
            if (this.form.foreignFieldId && this.form.foreignEntityId) {
              return this.handleForeignEntityChange([this.form.foreignEntityId])
                .then(() => {
                  this.foreignField = [this.form.foreignEntityId, this.form.foreignFieldId]
                })
            }
          })
          .then(() => this.formReady())
          .catch(error => this.$common.showNotifyError(error))
      }
    }
  }
}
</script>

<style lang="scss">
  @import '../../assets/common.scss';
  .fieldFormDiv .fieldForm {

    @include youran-form;

    .el-button--mini {
      padding: 1px 5px;
    }

    .inline-label {
      font-size: 14px;
      color: #606266;
      margin-right: 10px;
    }
  }

  //  popper-class="alert-tip"
  .alert-tip{
    background-color: #f56c6c!important;
    .popper__arrow, .popper__arrow::after {
      border-top-color: #f56c6c!important;
    }
  }

 .el-tag + .el-tag {
    margin-left: 10px;
  }
  .button-new-tag {
    margin-left: 10px;
    height: 32px;
    line-height: 30px;
    padding-top: 0;
    padding-bottom: 0;
  }
  .input-new-tag {
    width: 90px;
    margin-left: 10px;
    vertical-align: bottom;
  }
</style>
