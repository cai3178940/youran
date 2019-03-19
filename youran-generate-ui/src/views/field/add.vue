<template>
  <div class="fieldAdd">
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/project' }">项目管理</el-breadcrumb-item>
      <el-breadcrumb-item :to="{ path: `/project/${this.projectId}/entity` }">实体管理</el-breadcrumb-item>
      <el-breadcrumb-item :to="{ path: `/project/${this.projectId}/entity/${this.entityId}/field` }">字段管理</el-breadcrumb-item>
      <el-breadcrumb-item>添加</el-breadcrumb-item>
    </el-breadcrumb>
    <el-row type="flex" align="middle" :gutter="10">
      <el-col :span="12">
        <el-form ref="addForm" class="addForm" :rules="rules" :model="form" label-width="120px">
          <el-form-item label="java字段名" prop="jfieldName">
            <help-popover name="field.jfieldName">
              <el-input v-model="form.jfieldName" placeholder="java字段名，例如：age"></el-input>
              <el-button size="mini" type="text" @click="form.jfieldName = $common.snakeCase(form.jfieldName)">转下划线</el-button>
              <el-button size="mini" type="text" @click="form.jfieldName = $common.camelCase(form.jfieldName)">转驼峰</el-button>
            </help-popover>
          </el-form-item>
          <el-form-item label="mysql字段名" prop="fieldName">
            <help-popover name="field.fieldName">
              <el-input v-model="form.fieldName" placeholder="mysql字段名，例如：age"></el-input>
              <el-button size="mini" type="text" @click="form.fieldName = $common.snakeCase(form.fieldName)">转下划线</el-button>
              <el-button size="mini" type="text" @click="form.fieldName = $common.camelCase(form.fieldName)">转驼峰</el-button>
            </help-popover>
          </el-form-item>
          <el-form-item label="字段描述" prop="fieldDesc">
            <help-popover name="field.fieldDesc">
              <el-input v-model="form.fieldDesc" placeholder="字段描述，例如：年龄"></el-input>
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
          <el-form-item label="java字段类型" prop="jfieldType">
            <help-popover name="field.jfieldType">
              <el-select v-model="form.jfieldType" style="width:100%;" filterable placeholder="请选择">
                <el-option
                  v-for="item in jfieldTypeOptions"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                  <span style="float: left">{{ item.selectLabel }}</span>
                </el-option>
              </el-select>
            </help-popover>
          </el-form-item>
          <el-form-item label="mysql字段类型" prop="fieldType">
            <help-popover name="field.fieldType">
              <el-col :span="10" style="padding-left: 0px;">
                <el-select v-model="form.fieldType" @change="fieldTypeChange" style="width:100%;" filterable placeholder="请选择">
                  <el-option
                    v-for="item in fieldTypeOptions"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                    <span style="float: left">{{ item.selectLabel }}</span>
                  </el-option>
                </el-select>
              </el-col>
              <template v-if="fieldScaleVisible">
                <el-col :span="7">
                  <el-input-number v-model="form.fieldLength" controls-position="right" style="width:100%;" :min="0" placeholder="长度"></el-input-number>
                </el-col>
                <el-col :span="7" style="padding-right: 0px;">
                  <el-input-number v-model="form.fieldScale" controls-position="right" style="width:100%;" :min="0" placeholder="精度"></el-input-number>
                </el-col>
              </template>
              <template v-if="!fieldScaleVisible">
                <el-col :span="14" style="padding-right: 0px;">
                  <el-input-number v-model="form.fieldLength" controls-position="right" style="width:100%;" :min="0" placeholder="长度"></el-input-number>
                </el-col>
              </template>
            </help-popover>
          </el-form-item>
          <el-form-item label="是否主键" prop="primaryKey">
            <help-popover name="field.primaryKey">
              <el-radio-group v-model="form.primaryKey">
                <el-radio border v-for="item in boolOptions" :key="item.value" :label="item.value">{{item.label}}
                </el-radio>
              </el-radio-group>
            </help-popover>
          </el-form-item>
          <el-form-item label="是否自增" prop="autoIncrement">
            <help-popover name="field.autoIncrement">
              <el-radio-group :disabled="autoIncrementDisabled" v-model="form.autoIncrement">
                <el-radio border v-for="item in boolOptions" :key="item.value" :label="item.value">{{item.label}}
                </el-radio>
              </el-radio-group>
            </help-popover>
          </el-form-item>
          <el-form-item label="不能为空" prop="notNull">
            <help-popover name="field.notNull">
              <el-radio-group :disabled="notNullDisabled" v-model="form.notNull">
                <el-radio border v-for="item in boolOptions" :key="item.value" :label="item.value">{{item.label}}
                </el-radio>
              </el-radio-group>
            </help-popover>
          </el-form-item>
          <el-form-item label="是否外键" prop="foreignKey">
            <help-popover name="field.foreignKey">
              <el-radio-group v-model="form.foreignKey">
                <el-radio border v-for="item in boolOptions" :key="item.value" :label="item.value">{{item.label}}
                </el-radio>
              </el-radio-group>
            </help-popover>
          </el-form-item>
          <el-form-item label="外键字段">
            <help-popover name="field.foreignField">
              <el-cascader
                :disabled="foreignFieldDisabled"
                placeholder="请选择"
                :options="entityFieldOptions"
                v-model="foreignField"
                @active-item-change="handleForeignEntityChange">
              </el-cascader>
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
          <el-form-item label="是否查询字段" prop="query">
            <help-popover name="field.query">
              <el-radio-group v-model="form.query">
                <el-radio border v-for="item in boolOptions" :key="item.value" :label="item.value">{{item.label}}
                </el-radio>
              </el-radio-group>
            </help-popover>
          </el-form-item>
          <el-form-item label="查询方式" prop="queryType">
            <help-popover name="field.queryType">
              <el-select :disabled="queryTypeDisabled" clearable v-model="form.queryType" style="width:100%;" filterable
                         placeholder="请选择">
                <el-option
                  v-for="item in queryTypeOptions"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </help-popover>
          </el-form-item>
          <el-form-item label="是否新增字段" prop="insert">
            <help-popover name="field.insert">
              <el-radio-group v-model="form.insert">
                <el-radio border v-for="item in boolOptions" :key="item.value" :label="item.value">{{item.label}}
                </el-radio>
              </el-radio-group>
            </help-popover>
          </el-form-item>
          <el-form-item label="是否编辑字段" prop="update">
            <help-popover name="field.update">
              <el-radio-group v-model="form.update">
                <el-radio border v-for="item in boolOptions" :key="item.value" :label="item.value">{{item.label}}
                </el-radio>
              </el-radio-group>
            </help-popover>
          </el-form-item>
          <el-form-item label="是否列表字段" prop="list">
            <help-popover name="field.list">
              <el-radio-group v-model="form.list">
                <el-radio border v-for="item in boolOptions" :key="item.value" :label="item.value">{{item.label}}
                </el-radio>
              </el-radio-group>
            </help-popover>
          </el-form-item>
          <el-form-item label="是否支持排序" prop="listSort">
            <help-popover name="field.listSort">
              <el-radio-group v-model="form.listSort">
                <el-radio border v-for="item in boolOptions" :key="item.value" :label="item.value">{{item.label}}
                </el-radio>
              </el-radio-group>
            </help-popover>
          </el-form-item>
          <el-form-item label="是否详情字段" prop="show">
            <help-popover name="field.show">
              <el-radio-group v-model="form.show">
                <el-radio border v-for="item in boolOptions" :key="item.value" :label="item.value">{{item.label}}
                </el-radio>
              </el-radio-group>
            </help-popover>
          </el-form-item>
          <el-form-item label="特殊字段类型" prop="specialField">
            <help-popover name="field.specialField">
              <el-select :disabled="specialFieldDisabled" clearable v-model="form.specialField" style="width:100%;"
                         filterable placeholder="请选择">
                <el-option
                  v-for="item in specialFieldOptions"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </help-popover>
          </el-form-item>
          <el-form-item label="排序号" prop="orderNo">
            <help-popover name="field.orderNo">
             <el-input-number v-model="form.orderNo" style="width:100%;" :min="1"></el-input-number>
            </help-popover>
          </el-form-item>

          <el-form-item>
            <el-button type="primary" @click="submit()">提交</el-button>
            <el-button @click="goBack()">返回</el-button>
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import options from '@/components/options'
import { apiPath } from '@/components/common'
import fieldTemplate from '@/components/fieldTemplate'
import { initFormBean, getRules } from './model'

export default {
  name: 'fieldAdd',
  props: ['projectId', 'entityId'],
  data () {
    return {
      boolOptions: options.boolOptions,
      fieldTypeOptions: options.fieldTypeOptions,
      jfieldTypeOptions: options.jfieldTypeOptions,
      queryTypeOptions: options.queryTypeOptions,
      specialFieldOptions: options.specialFieldOptions,
      entityFieldOptions: [],
      constList: null,
      notNullDisabled: false,
      autoIncrementDisabled: true,
      queryTypeDisabled: true,
      specialFieldDisabled: false,
      foreignField: [0, 0],
      foreignFieldDisabled: true,
      form: initFormBean(false),
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
        this.queryType = ''
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
        .then(response => this.$common.checkResult(response.data))
        .then(result => { this.entityFieldOptions = result.data.entities.map(entity => ({ value: entity.entityId, label: entity.title, children: [] })) })
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
        .then(response => this.$common.checkResult(response.data))
        .then(result => {
          entity.children = result.data.filter(field => field.primaryKey === 1)
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
          .then(response => this.$common.checkResult(response.data))
          .then(result => {
            this.constList = result.data.entities
            action()
          })
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
      this.$refs.addForm.validate()
        // 提交表单
        .then(() => {
          loading = this.$loading()
          return this.$ajax.post(`/${apiPath}/meta_field/save`, this.$common.removeBlankField(this.form))
        })
      // 校验返回结果
        .then(response => this.$common.checkResult(response.data))
      // 执行页面跳转
        .then(() => {
          this.$common.showMsg('success', '添加成功')
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
    }
  },
  created () {
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
        .then(response => this.$common.checkResult(response.data))
        .then(result => new Promise((resolve, reject) => {
          this.form = result.data
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
</script>

<style lang="scss">
  @import '../../assets/common.scss';
  .fieldAdd .addForm {
    @include youran-form;
  }

</style>
