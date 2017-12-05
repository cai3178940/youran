<template>
  <div class="fieldShow">
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/project' }">项目管理</el-breadcrumb-item>
      <el-breadcrumb-item :to="{ path: `/project/${this.projectId}/entity` }">实体管理</el-breadcrumb-item>
      <el-breadcrumb-item :to="{ path: `/project/${this.projectId}/entity/${this.entityId}/field` }">字段管理</el-breadcrumb-item>
      <el-breadcrumb-item>查看</el-breadcrumb-item>
    </el-breadcrumb>
    <el-row type="flex" align="middle" :gutter="20">
      <el-col :span="12">
        <el-form ref="showForm" class="showForm" :model="form" label-width="120px">
          <el-form-item label="java字段名" prop="jfieldName">
            <el-input :disabled="true" v-model="form.jfieldName"></el-input>
          </el-form-item>
          <el-form-item label="mysql字段名" prop="fieldName">
            <el-input :disabled="true" v-model="form.fieldName"></el-input>
          </el-form-item>
          <el-form-item label="字段描述" prop="fieldDesc">
            <el-input :disabled="true" v-model="form.fieldDesc"></el-input>
          </el-form-item>
          <el-form-item label="java字段类型" prop="jfieldType">
            <el-select :disabled="true" v-model="form.jfieldType" style="width:100%;" filterable>
              <el-option
                v-for="item in jfieldTypeOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="mysql字段类型" prop="fieldType">
            <el-select :disabled="true" v-model="form.fieldType" style="width:100%;" filterable>
              <el-option
                v-for="item in fieldTypeOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="字段长度" prop="fieldLength">
            <el-input-number :disabled="true" v-model="form.fieldLength" style="width:100%;" :min="0"></el-input-number>
          </el-form-item>
          <el-form-item label="字段精度" prop="fieldScale">
            <el-input-number :disabled="true" v-model="form.fieldScale" style="width:100%;" :min="0"></el-input-number>
          </el-form-item>
          <el-form-item label="是否主键" prop="primaryKey">
            <el-radio-group :disabled="true" v-model="form.primaryKey">
              <el-radio border v-for="item in boolOptions" :key="item.value" :label="item.value">{{item.label}}</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="是否自增" prop="autoIncrement">
            <el-radio-group :disabled="true" v-model="form.autoIncrement">
              <el-radio border v-for="item in boolOptions" :key="item.value" :label="item.value">{{item.label}}</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="不能为空" prop="notNull">
            <el-radio-group :disabled="true" v-model="form.notNull">
              <el-radio border v-for="item in boolOptions" :key="item.value" :label="item.value">{{item.label}}</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="特殊字段类型" prop="specialField">
            <el-select :disabled="true" v-model="form.specialField" style="width:100%;" filterable>
              <el-option
                v-for="item in specialFieldOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="字段示例" prop="fieldExample">
            <el-input :disabled="true" v-model="form.fieldExample"></el-input>
          </el-form-item>
          <el-form-item label="字段备注" prop="fieldComment">
            <el-input :disabled="true" v-model="form.fieldComment" type="textarea" :rows="2"></el-input>
          </el-form-item>
          <el-form-item label="枚举字典" prop="dicType">
            <el-input :disabled="true" style="width:100%;" v-model="form.dicType"></el-input>
          </el-form-item>
          <el-form-item label="是否查询字段" prop="query">
            <el-radio-group :disabled="true" v-model="form.query">
              <el-radio border v-for="item in boolOptions" :key="item.value" :label="item.value">{{item.label}}</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="查询方式" prop="queryType">
            <el-select :disabled="true" v-model="form.queryType" style="width:100%;" filterable>
              <el-option
                v-for="item in queryTypeOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="是否新增字段" prop="insert">
            <el-radio-group :disabled="true" v-model="form.insert">
              <el-radio border v-for="item in boolOptions" :key="item.value" :label="item.value">{{item.label}}</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="是否编辑字段" prop="update">
            <el-radio-group :disabled="true" v-model="form.update">
              <el-radio border v-for="item in boolOptions" :key="item.value" :label="item.value">{{item.label}}</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="是否列表字段" prop="list">
            <el-radio-group :disabled="true" v-model="form.list">
              <el-radio border v-for="item in boolOptions" :key="item.value" :label="item.value">{{item.label}}</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="是否详情字段" prop="show">
            <el-radio-group :disabled="true" v-model="form.show">
              <el-radio border v-for="item in boolOptions" :key="item.value" :label="item.value">{{item.label}}</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="排序号" prop="orderNo">
            <el-input-number :disabled="true" v-model="form.orderNo" style="width:100%;" :min="1"></el-input-number>
          </el-form-item>

          <el-form-item>
            <el-button @click="goBack()">返回</el-button>
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>
  </div>
</template>

<script>

  import options from '@/components/options.js'
  //字段模型
  const fieldModel = {
    fieldId: null,
    entityId: null,
    //java字段名
    jfieldName: '',
    //mysql字段名
    fieldName: '',
    //字段描述
    fieldDesc: '',
    //java字段类型
    jfieldType: '',
    //mysql字段类型
    fieldType: '',
    //字段长度
    fieldLength: 0,
    //字段精度
    fieldScale: 0,
    //是否主键
    primaryKey: 0,
    //是否自增
    autoIncrement: 0,
    //默认值(暂时不用)
    defaultValue:'',
    //不能为空
    notNull: 0,
    //特殊字段类型
    specialField: '',
    //字段示例
    fieldExample: '',
    //字段备注
    fieldComment: '',
    //枚举字典
    dicType: '',
    //是否查询字段
    query: 0,
    //查询方式
    queryType: null,
    //是否新增字段
    insert: 1,
    //是否编辑字段
    update: 1,
    //是否列表字段
    list: 1,
    //是否详情字段
    show: 1,
    //编辑方式(暂时不用)
    editType: null,
    //排序号
    orderNo: 1
  }

  export default {
    name: 'fieldShow',
    props: ['projectId','entityId','fieldId'],
    data: function () {
      return {
        boolOptions: options.boolOptions,
        fieldTypeOptions: options.fieldTypeOptions,
        jfieldTypeOptions: options.jfieldTypeOptions,
        queryTypeOptions: options.queryTypeOptions,
        specialFieldOptions: options.specialFieldOptions,
        form: {
          ...fieldModel
        }
      }
    },
    methods: {
      getField: function () {
        return this.$ajax.get(`/generate/meta_field/${this.fieldId}`)
          .then(response => this.$common.checkResult(response.data))
          .then(result => this.form = result.data)
          .catch(error => this.$common.showNotifyError(error))
      },
      goBack: function () {
        this.$router.push(`/project/${this.projectId}/entity/${this.entityId}/field`)
      }
    },
    created: function () {
      this.getField()
    }
  }
</script>

<style>
  .fieldShow .showForm {
    padding: 30px 50px;
  }

</style>
