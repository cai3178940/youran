<template>
  <div class="constDetailShow">
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/project' }">项目管理</el-breadcrumb-item>
      <el-breadcrumb-item :to="{ path: `/project/${this.projectId}/entity` }">枚举管理</el-breadcrumb-item>
      <el-breadcrumb-item :to="{ path: `/project/${this.projectId}/entity/${this.entityId}/constDetail` }">枚举值管理</el-breadcrumb-item>
      <el-breadcrumb-item>查看</el-breadcrumb-item>
    </el-breadcrumb>
    <el-row type="flex" align="middle" :gutter="20">
      <el-col :span="12">
        <el-form ref="showForm" class="showForm" :model="form" label-width="120px">
          <el-form-item label="java枚举值名" prop="jconstDetailName">
            <el-input :disabled="true" v-model="form.jconstDetailName"></el-input>
          </el-form-item>
          <el-form-item label="mysql枚举值名" prop="constDetailName">
            <el-input :disabled="true" v-model="form.constDetailName"></el-input>
          </el-form-item>
          <el-form-item label="枚举值描述" prop="constDetailDesc">
            <el-input :disabled="true" v-model="form.constDetailDesc"></el-input>
          </el-form-item>
          <el-form-item label="java枚举值类型" prop="jconstDetailType">
            <el-select :disabled="true" v-model="form.jconstDetailType" style="width:100%;" filterable>
              <el-option
                v-for="item in jconstDetailTypeOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="mysql枚举值类型" prop="constDetailType">
            <el-select :disabled="true" v-model="form.constDetailType" style="width:100%;" filterable>
              <el-option
                v-for="item in constDetailTypeOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="枚举值长度" prop="constDetailLength">
            <el-input-number :disabled="true" v-model="form.constDetailLength" style="width:100%;" :min="0"></el-input-number>
          </el-form-item>
          <el-form-item label="枚举值精度" prop="constDetailScale">
            <el-input-number :disabled="true" v-model="form.constDetailScale" style="width:100%;" :min="0"></el-input-number>
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
          <el-form-item label="枚举值示例" prop="constDetailExample">
            <el-input :disabled="true" v-model="form.constDetailExample"></el-input>
          </el-form-item>
          <el-form-item label="枚举值备注" prop="constDetailComment">
            <el-input :disabled="true" v-model="form.constDetailComment" type="textarea" :rows="2"></el-input>
          </el-form-item>
          <el-form-item label="枚举字典" prop="dicType">
            <el-input :disabled="true" style="width:100%;" v-model="form.dicType"></el-input>
          </el-form-item>
          <el-form-item label="是否查询枚举值" prop="query">
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
          <el-form-item label="是否新增枚举值" prop="insert">
            <el-radio-group :disabled="true" v-model="form.insert">
              <el-radio border v-for="item in boolOptions" :key="item.value" :label="item.value">{{item.label}}</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="是否编辑枚举值" prop="update">
            <el-radio-group :disabled="true" v-model="form.update">
              <el-radio border v-for="item in boolOptions" :key="item.value" :label="item.value">{{item.label}}</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="是否列表枚举值" prop="list">
            <el-radio-group :disabled="true" v-model="form.list">
              <el-radio border v-for="item in boolOptions" :key="item.value" :label="item.value">{{item.label}}</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="是否详情枚举值" prop="show">
            <el-radio-group :disabled="true" v-model="form.show">
              <el-radio border v-for="item in boolOptions" :key="item.value" :label="item.value">{{item.label}}</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="排序号" prop="orderNo">
            <el-input-number :disabled="true" v-model="form.orderNo" style="width:100%;" :min="1"></el-input-number>
          </el-form-item>
          <el-form-item label="特殊枚举值类型" prop="specialField">
            <el-select :disabled="true" v-model="form.specialField" style="width:100%;" filterable>
              <el-option
                v-for="item in specialFieldOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
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
  //枚举值模型
  const constDetailModel = {
    constDetailId: null,
    entityId: null,
    //java枚举值名
    jconstDetailName: '',
    //mysql枚举值名
    constDetailName: '',
    //枚举值描述
    constDetailDesc: '',
    //java枚举值类型
    jconstDetailType: '',
    //mysql枚举值类型
    constDetailType: '',
    //枚举值长度
    constDetailLength: 0,
    //枚举值精度
    constDetailScale: 0,
    //是否主键
    primaryKey: 0,
    //是否自增
    autoIncrement: 0,
    //默认值(暂时不用)
    defaultValue:'',
    //不能为空
    notNull: 0,
    //枚举值示例
    constDetailExample: '',
    //枚举值备注
    constDetailComment: '',
    //枚举字典
    dicType: '',
    //是否查询枚举值
    query: 0,
    //查询方式
    queryType: null,
    //是否新增枚举值
    insert: 1,
    //是否编辑枚举值
    update: 1,
    //是否列表枚举值
    list: 1,
    //是否详情枚举值
    show: 1,
    //编辑方式(暂时不用)
    editType: null,
    //排序号
    orderNo: 1,
    //特殊枚举值类型
    specialField: ''
  }

  export default {
    name: 'constDetailShow',
    props: ['projectId','entityId','constDetailId'],
    data: function () {
      return {
        boolOptions: options.boolOptions,
        constDetailTypeOptions: options.constDetailTypeOptions,
        jconstDetailTypeOptions: options.jconstDetailTypeOptions,
        queryTypeOptions: options.queryTypeOptions,
        specialFieldOptions: options.specialFieldOptions,
        form: {
          ...constDetailModel
        }
      }
    },
    methods: {
      getField: function () {
        return this.$ajax.get(`/generate/meta_const_detail/${this.constDetailId}`)
          .then(response => this.$common.checkResult(response.data))
          .then(result => this.form = result.data)
          .catch(error => this.$common.showNotifyError(error))
      },
      goBack: function () {
        this.$router.push(`/project/${this.projectId}/entity/${this.entityId}/constDetail`)
      }
    },
    created: function () {
      this.getField()
    }
  }
</script>

<style>
  .constDetailShow .showForm {
    padding: 30px 50px;
  }

</style>
