<template>
  <div class="fieldFormDiv">
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/project' }">项目管理</el-breadcrumb-item>
      <el-breadcrumb-item :to="{ path: `/project/${this.projectId}/entity` }">实体管理</el-breadcrumb-item>
      <el-breadcrumb-item :to="{ path: `/project/${this.projectId}/entity/${this.entityId}/field` }">字段管理</el-breadcrumb-item>
      <el-breadcrumb-item>
        {{edit?'编辑字段':'添加字段'}}
      </el-breadcrumb-item>
    </el-breadcrumb>
    <el-row type="flex" align="middle" :gutter="10">
      <el-col :span="14">
        <el-form ref="fieldForm" class="fieldForm" :rules="rules" :model="form" label-width="120px" size="small">
          <el-form-item label="是否主键" prop="primaryKey">
            <help-popover name="field.primaryKey">
              <el-checkbox v-model="form.primaryKey" :true-label="1" :false-label="0">是</el-checkbox>
              <el-checkbox v-model="form.autoIncrement" :true-label="1" :false-label="0" :disabled="autoIncrementDisabled">自增</el-checkbox>
            </help-popover>
          </el-form-item>
          <el-form-item label="是否外键" prop="foreignKey">
            <help-popover name="field.foreignKey">
              <el-row type="flex" align="middle" :gutter="10">
                <el-col :span="6">
                  <el-checkbox v-model="form.foreignKey" :true-label="1" :false-label="0">是</el-checkbox>
                </el-col>
                <el-col :span="18" class="col-right">
                  <span class="inline-label">外键字段</span>
                  <el-cascader
                    :disabled="foreignFieldDisabled"
                    placeholder="请选择外键字段"
                    :options="entityFieldOptions"
                    v-model="foreignField"
                    @active-item-change="handleForeignEntityChange">
                  </el-cascader>
                </el-col>
              </el-row>
            </help-popover>
          </el-form-item>
          <el-form-item label="序号" prop="orderNo">
            <help-popover name="field.orderNo">
              <el-row type="flex" align="middle" :gutter="10">
                <el-col :span="8">
                  <el-input-number v-model="form.orderNo" :min="1"></el-input-number>
                </el-col>
                <el-col :span="16" class="col-right">
                  <span class="inline-label">特殊类型</span>
                  <el-select :disabled="specialFieldDisabled" clearable v-model="form.specialField"
                             filterable placeholder="无">
                    <el-option
                      v-for="feature in specialFieldFeatures"
                      :key="feature.value"
                      :label="feature.label"
                      :value="feature.value">
                      <span class="template-option">
                        <icon :key="feature.value"
                              :name="feature.icon"
                              :style="feature.style">
                        </icon>
                      </span>
                          {{ feature.label }}
                    </el-option>
                  </el-select>
                </el-col>
              </el-row>
            </help-popover>
          </el-form-item>
          <el-form-item label="字段名" prop="jfieldName">
            <help-popover name="field.jfieldName">
              <el-col :span="11" class="col-left">
                <el-input v-model="form.jfieldName" placeholder="java字段名，例如：age"></el-input>
                <!--
                <el-button size="mini" type="text" @click="form.jfieldName = $common.snakeCase(form.jfieldName)">转下划线</el-button>
                -->
                <el-button size="mini" type="text" @click="form.jfieldName = $common.camelCase(form.jfieldName)">转驼峰</el-button>
              </el-col>
              <el-col :span="2" style="padding-left: 0px;padding-right: 0px;text-align: center;">
                <el-tooltip class="item" effect="dark" content="粘贴到右边并转下划线" placement="top">
                  <el-button type="text" @click="copyJfieldNameToFieldName()">
                    <icon name="angle-double-right" style="vertical-align: middle;"></icon>
                  </el-button>
                </el-tooltip>
              </el-col>
              <el-col :span="11" class="col-right">
                <el-input v-model="form.fieldName" placeholder="mysql字段名，例如：age"></el-input>
                <el-button size="mini" type="text" @click="form.fieldName = $common.snakeCase(form.fieldName)">转下划线</el-button>
                <!--
                <el-button size="mini" type="text" @click="form.fieldName = $common.camelCase(form.fieldName)">转驼峰</el-button>
                -->
              </el-col>
            </help-popover>
          </el-form-item>
          <el-form-item label="字段标题" prop="fieldDesc">
            <help-popover name="field.fieldDesc">
              <el-input v-model="form.fieldDesc" placeholder="字段标题，例如：年龄"></el-input>
            </help-popover>
          </el-form-item>
          <el-form-item label="字段备注" prop="fieldComment">
            <help-popover name="field.fieldComment">
              <el-input v-model="form.fieldComment" type="textarea" :rows="2" placeholder="字段备注，例如：年龄【整型】"></el-input>
            </help-popover>
          </el-form-item>
          <el-form-item label="字段示例" prop="fieldExample">
            <help-popover name="field.fieldExample">
              <el-input v-model="form.fieldExample" placeholder="字段示例，例如年龄字段：21"></el-input>
            </help-popover>
          </el-form-item>
          <el-form-item label="字段类型" prop="fieldType">
            <help-popover name="field.fieldType">
              <el-col :span="6" class="col-left">
                <el-select v-model="form.jfieldType" @change="jfieldTypeChange" style="width:100%;" filterable placeholder="java类型">
                  <el-option
                    v-for="item in jfieldTypeOptions"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                  </el-option>
                </el-select>
              </el-col>
              <el-col :span="6">
                <el-select v-model="form.fieldType" @change="fieldTypeChange" style="width:100%;" filterable placeholder="sql类型">
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
                  <el-input-number v-model="form.fieldLength" controls-position="right" style="width:100%;" :min="0" placeholder="长度"></el-input-number>
                </el-col>
                <el-col :span="6" class="col-right">
                  <el-input-number v-model="form.fieldScale" controls-position="right" style="width:100%;" :min="0" placeholder="精度"></el-input-number>
                </el-col>
              </template>
              <template v-if="fieldLengthVisible && !fieldScaleVisible">
                <el-col :span="12" class="col-right">
                  <el-input-number v-model="form.fieldLength" controls-position="right" style="width:100%;" :min="0" placeholder="长度"></el-input-number>
                </el-col>
              </template>
            </help-popover>
          </el-form-item>
          <el-form-item label="枚举字典" prop="dicType">
            <help-popover name="field.dicType">
              <el-autocomplete :disabled="dicTypeDisabled" style="width:100%;"
                               v-model="form.dicType"
                               :fetch-suggestions="queryDicType"
                               placeholder="请输入枚举字典"
              ></el-autocomplete>
            </help-popover>
          </el-form-item>
          <el-form-item label="非空" prop="notNull">
            <help-popover name="field.notNull">
              <el-checkbox v-model="form.notNull" :disabled="notNullDisabled" :true-label="1" :false-label="0">是</el-checkbox>
            </help-popover>
          </el-form-item>
          <el-form-item label="可搜索" prop="query">
            <help-popover name="field.query">
              <el-row type="flex" align="middle" :gutter="10">
                <el-col :span="6">
                  <el-checkbox v-model="form.query" :true-label="1" :false-label="0">是</el-checkbox>
                </el-col>
                <el-col :span="18" class="col-right">
                  <span class="inline-label">查询方式</span>
                  <el-select :disabled="queryTypeDisabled" clearable v-model="form.queryType" style="" filterable
                             placeholder="请选择">
                    <el-option
                      v-for="item in queryTypeOptions"
                      :key="item.value"
                      :label="item.label"
                      :value="item.value">
                    </el-option>
                  </el-select>
                </el-col>
              </el-row>
            </help-popover>
          </el-form-item>
          <el-form-item label="字段属性">
            <help-popover name="field.attributes">
              <el-checkbox v-model="form.insert" :true-label="1" :false-label="0">可插入</el-checkbox>
              <el-checkbox v-model="form.update" :true-label="1" :false-label="0">可修改</el-checkbox>
              <el-checkbox v-model="form.list" :true-label="1" :false-label="0">列表展示</el-checkbox>
              <el-checkbox v-model="form.show" :true-label="1" :false-label="0">详情展示</el-checkbox>
              <el-checkbox v-model="form.listSort" :true-label="1" :false-label="0">可排序</el-checkbox>
            </help-popover>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="submit()">提交</el-button>
            <el-button v-if="edit" type="warning" @click="reset()">重置</el-button>
            <el-button @click="goBack()">返回</el-button>
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>
    <el-backtop target=".el-main"></el-backtop>
  </div>
</template>

<script>
import options from '@/components/options'
import { apiPath } from '@/components/common'
import { initFormBean, getRules } from './model'
import fieldTemplate from '@/components/fieldTemplate'

export default {
  name: 'fieldForm',
  props: ['projectId', 'entityId', 'fieldId'],
  data () {
    const edit = !!this.fieldId
    return {
      edit: edit,
      boolOptions: options.boolOptions,
      fieldTypeOptions: options.getFieldTypeOptions(),
      jfieldTypeOptions: options.jfieldTypeOptions,
      queryTypeOptions: options.queryTypeOptions,
      specialFieldFeatures: options.specialFieldFeatures,
      entityFieldOptions: [],
      constList: null,
      notNullDisabled: false,
      autoIncrementDisabled: true,
      queryTypeDisabled: true,
      specialFieldDisabled: false,
      foreignField: [0, 0],
      foreignFieldDisabled: true,
      old: initFormBean(edit),
      form: initFormBean(edit),
      rules: getRules(this)
    }
  },
  computed: {
    dicTypeDisabled () {
      if (this.form.primaryKey === 1 || this.form.specialField) {
        return true
      }
      return false
    },
    fieldLengthVisible () {
      return options.showFieldLength(this.form.fieldType, 1)
    },
    fieldScaleVisible () {
      return options.showFieldScale(this.form.fieldType)
    }
  },
  watch: {
    'form.primaryKey' (value) {
      if (value === 1) {
        this.form.notNull = 1
        this.notNullDisabled = true
        this.form.specialField = ''
        this.specialFieldDisabled = true
        this.autoIncrementDisabled = false
      } else {
        this.notNullDisabled = false
        this.specialFieldDisabled = false
        this.form.autoIncrement = 0
        this.autoIncrementDisabled = true
      }
    },
    'form.foreignKey' (value) {
      if (value === 1) {
        this.foreignFieldDisabled = false
      } else {
        this.foreignFieldDisabled = true
      }
    },
    'form.query' (value) {
      if (value === 1) {
        this.queryTypeDisabled = false
      } else {
        this.queryTypeDisabled = true
        this.form.queryType = ''
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
      return this.$common.getEntityOptions(this.projectId)
        .then(response => this.$common.checkResult(response))
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
      return this.$common.getFieldOptions(entityId)
        .then(response => this.$common.checkResult(response))
        .then(data => {
          entity.children = data.filter(field => field.primaryKey === 1)
            .map(field => ({ value: field.fieldId, label: field.fieldDesc }))
        })
    },
    // 查询可用枚举字典
    queryDicType (queryString, cb) {
      // 定义回调操作
      const action = () => {
        const constList = this.constList.slice(0)
        constList.push(...options.defaultConstList)
        const results = queryString ? constList.filter(
          c => c.constName.toLowerCase().indexOf(queryString.toLowerCase()) === 0
        ) : constList
        cb(results.map(c => ({ value: c.constName })))
      }
      if (this.constList) {
        action()
      } else {
        this.$common.getConstOptions(this.projectId)
          .then(response => this.$common.checkResult(response))
          .then(data => {
            this.constList = data.list
            action()
          })
      }
    },
    getField () {
      return this.$ajax.get(`/${apiPath}/meta_field/${this.fieldId}`)
        .then(response => this.$common.checkResult(response))
        .then(data => { this.old = data })
        .catch(error => this.$common.showNotifyError(error))
    },
    reset () {
      for (const key in initFormBean(true)) {
        this.form[key] = this.old[key]
      }
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
          if (this.edit) {
            return this.$ajax.put(`/${apiPath}/meta_field/update`, this.$common.removeBlankField(this.form))
          } else {
            return this.$ajax.post(`/${apiPath}/meta_field/save`, this.$common.removeBlankField(this.form))
          }
        })
      // 校验返回结果
        .then(response => this.$common.checkResult(response))
      // 执行页面跳转
        .then(() => {
          this.$common.showMsg('success', '操作成功')
          this.goBack()
        })
        .catch(error => this.$common.showNotifyError(error))
        .finally(() => {
          if (loading) {
            loading.close()
          }
        })
    },
    goBack () {
      this.$router.push(`/project/${this.projectId}/entity/${this.entityId}/field`)
    },
    copyJfieldNameToFieldName () {
      this.form.fieldName = this.$common.snakeCase(this.form.jfieldName)
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
        .catch(error => this.this.$common.showNotifyError(error))
    } else {
      const entityId = parseInt(this.entityId)
      this.form.entityId = entityId
      const promise = this.initForeignEntityOptions()
      const type = this.$router.currentRoute.query.type
      const template = this.$router.currentRoute.query.template
      if (!template) {
        return
      }
      if (type === 'system') {
        this.form = fieldTemplate[template]
        this.form.entityId = entityId
      }
      if (type === 'temp') {
        const promise2 = this.$ajax.get(`/${apiPath}/meta_field/${template}`)
          .then(response => this.$common.checkResult(response))
          .then(data => new Promise((resolve, reject) => {
            this.form = data
            this.form.entityId = entityId
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

    .col-left {
      padding-left: 0px!important;
      line-height: normal;
    }
    .col-right {
      padding-right: 0px!important;;
      line-height: normal;
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
</style>
