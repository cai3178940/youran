<template>
  <div class="fieldAdd">
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/project' }">项目管理</el-breadcrumb-item>
      <el-breadcrumb-item :to="{ path: `/project/${this.projectId}/entity` }">实体管理</el-breadcrumb-item>
      <el-breadcrumb-item :to="{ path: `/project/${this.projectId}/entity/${this.entityId}/field` }">字段管理</el-breadcrumb-item>
      <el-breadcrumb-item>添加</el-breadcrumb-item>
    </el-breadcrumb>
    <el-row type="flex" align="middle" :gutter="20">
      <el-col :span="12">
        <el-form ref="addForm" class="addForm" :rules="rules" :model="form" label-width="120px">
          <el-form-item label="java字段名" prop="jfieldName">
            <el-input v-model="form.jfieldName" placeholder="java字段名，例如：age"></el-input>
          </el-form-item>
          <el-form-item label="mysql字段名" prop="fieldName">
            <el-input v-model="form.fieldName" placeholder="mysql字段名，例如：age"></el-input>
          </el-form-item>
          <el-form-item label="字段描述" prop="fieldDesc">
            <el-input v-model="form.fieldDesc" placeholder="字段描述，例如：年龄"></el-input>
          </el-form-item>
          <el-form-item label="java字段类型" prop="jfieldType">
            <el-select v-model="form.jfieldType" style="width:100%;" filterable placeholder="请选择">
              <el-option
                v-for="item in jfieldTypeOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="mysql字段类型" prop="fieldType">
            <el-select v-model="form.fieldType" style="width:100%;" filterable placeholder="请选择">
              <el-option
                v-for="item in fieldTypeOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="字段长度" prop="fieldLength">
            <el-input-number v-model="form.fieldLength" style="width:100%;" :min="0" placeholder="字段长度，例如：10"></el-input-number>
          </el-form-item>
          <el-form-item v-if="form.fieldType=='decimal'" label="字段精度" prop="fieldScale">
            <el-input-number v-model="form.fieldScale" style="width:100%;" :min="0" placeholder="字段精度，例如：2"></el-input-number>
          </el-form-item>
          <el-form-item label="是否主键" prop="primaryKey">
            <el-radio-group v-model="form.primaryKey">
              <el-radio border v-for="item in boolOptions" :key="item.value" :label="item.value">{{item.label}}</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="是否自增" prop="autoIncrement">
            <el-radio-group :disabled="autoIncrementDisabled" v-model="form.autoIncrement">
              <el-radio border v-for="item in boolOptions" :key="item.value" :label="item.value">{{item.label}}</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="不能为空" prop="notNull">
            <el-radio-group :disabled="notNullDisabled" v-model="form.notNull">
              <el-radio border v-for="item in boolOptions" :key="item.value" :label="item.value">{{item.label}}</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item v-if="!specialFieldHidden" label="特殊字段类型" prop="specialField">
            <el-select v-model="form.specialField" style="width:100%;" filterable placeholder="请选择">
              <el-option
                v-for="item in specialFieldOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="字段示例" prop="fieldExample">
            <el-input v-model="form.fieldExample" placeholder="字段示例，例如年龄字段：21"></el-input>
          </el-form-item>
          <el-form-item label="字段备注" prop="fieldComment">
            <el-input v-model="form.fieldComment" type="textarea" :rows="2" placeholder="字段备注，例如：年龄【整型】"></el-input>
          </el-form-item>
          <el-form-item label="枚举字典" prop="dicType">
            <el-autocomplete style="width:100%;"
              v-model="form.dicType"
              :fetch-suggestions="queryDicType"
              placeholder="请输入枚举字典"
            ></el-autocomplete>
          </el-form-item>
          <el-form-item label="是否查询字段" prop="query">
            <el-radio-group v-model="form.query">
              <el-radio border v-for="item in boolOptions" :key="item.value" :label="item.value">{{item.label}}</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="查询方式" prop="queryType">
            <el-select v-model="form.queryType" style="width:100%;" filterable placeholder="请选择">
              <el-option
                v-for="item in queryTypeOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="是否新增字段" prop="insert">
            <el-radio-group v-model="form.insert">
              <el-radio border v-for="item in boolOptions" :key="item.value" :label="item.value">{{item.label}}</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="是否编辑字段" prop="update">
            <el-radio-group v-model="form.update">
              <el-radio border v-for="item in boolOptions" :key="item.value" :label="item.value">{{item.label}}</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="是否列表字段" prop="list">
            <el-radio-group v-model="form.list">
              <el-radio border v-for="item in boolOptions" :key="item.value" :label="item.value">{{item.label}}</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="是否详情字段" prop="show">
            <el-radio-group v-model="form.show">
              <el-radio border v-for="item in boolOptions" :key="item.value" :label="item.value">{{item.label}}</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="排序号" prop="orderNo">
            <el-input-number v-model="form.orderNo" style="width:100%;" :min="1"></el-input-number>
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
  import options from '@/components/options.js'
  export default {
    name: 'fieldAdd',
    props: ['projectId','entityId'],
    data: function () {
      return {
        boolOptions: options.boolOptions,
        fieldTypeOptions: options.fieldTypeOptions,
        jfieldTypeOptions: options.jfieldTypeOptions,
        queryTypeOptions: options.queryTypeOptions,
        specialFieldOptions: options.specialFieldOptions,
        constList:null,
        notNullDisabled:false,
        autoIncrementDisabled:true,
        specialFieldHidden:false,
        form: {
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
        },
        rules: {
          jfieldName: [
            {required: true, message: '请输入java字段名', trigger: 'blur'},
            {max: 20, message: '长度不能超过20个字符', trigger: 'blur'}
          ],
          fieldName: [
            {required: true, message: '请输入mysql字段名', trigger: 'blur'},
            {max: 20, message: '长度不能超过20个字符', trigger: 'blur'}
          ],
          fieldDesc: [
            {required: true, message: '请输入字段描述', trigger: 'blur'},
            {max: 40, message: '长度不能超过40个字符', trigger: 'blur'}
          ],
          jfieldType: [
            {required: true, type: 'string',message: '请选择java字段类型', trigger: 'change'}
          ],
          fieldType: [
            {required: true, type: 'string',message: '请选择mysql字段类型', trigger: 'change'}
          ],
          fieldLength: [
            {required: true, message: '请输入字段长度', trigger: 'blur'},
          ],
          fieldScale: [
            {required: true, message: '请输入字段精度', trigger: 'blur'},
          ],
          primaryKey: [
            {required: true, type: 'number', message: '请选择是否主键', trigger: 'change'},
          ],
          autoIncrement: [
            {required: true, type: 'number', message: '请选择是否自增', trigger: 'change'},
          ],
          notNull: [
            {required: true, type: 'number', message: '请选择不能为空', trigger: 'change'},
          ],
          fieldExample: [
            {required: true, message: '请输入字段示例', trigger: 'blur'},
            {max: 100, message: '长度不能超过100个字符', trigger: 'blur'}
          ],
          fieldComment: [
            {required: true, message: '请输入字段备注', trigger: 'blur'},
            {max: 100, message: '长度不能超过100个字符', trigger: 'blur'}
          ],
          dicType: [
            {max: 40, message: '长度不能超过40个字符', trigger: 'blur'}
          ],
          query: [
            {required: true, type: 'number', message: '请选择是否查询字段', trigger: 'change'},
          ],
          insert: [
            {required: true, type: 'number', message: '请选择是否新增字段', trigger: 'change'},
          ],
          update: [
            {required: true, type: 'number', message: '请选择是否编辑字段', trigger: 'change'},
          ],
          list: [
            {required: true, type: 'number', message: '请选择是否列表字段', trigger: 'change'},
          ],
          show: [
            {required: true, type: 'number', message: '请选择是否详情字段', trigger: 'change'},
          ],
          orderNo: [
            {required: true, message: '请输入排序号', trigger: 'blur'},
          ]
        }
      }
    },
    watch: {
      'form.primaryKey':function (value) {
        if(value==1){
          this.form.notNull=1
          this.notNullDisabled=true
          this.form.specialField=''
          this.specialFieldHidden=true
          this.autoIncrementDisabled=false
        }else{
          this.notNullDisabled=false
          this.specialFieldHidden=false
          this.form.autoIncrement=0
          this.autoIncrementDisabled=true
        }
      }
    },
    methods: {
      //查询可用枚举字典
      queryDicType: function (queryString, cb) {
        //定义回调操作
        var action = function () {
          var results = queryString ? this.constList.filter(
            c=>c.constName.toLowerCase().indexOf(queryString.toLowerCase()) === 0
          ) : this.constList;
          cb(results.map(c=>({value:c.constName})));
        }.bind(this)
        if(this.constList){
          action()
        }else{
          this.$common.getConstOptions(this.projectId)
            .then(response => this.$common.checkResult(response.data))
            .then(result=>{
              this.constList = result.data.entities
              action();
            })
        }
      },
      submit: function () {
        //表单预处理
        if(this.form.fieldType!='decimal'){
          this.form.fieldScale=null
        }
        //校验表单
        this.$refs.addForm.validate()
        //提交表单
          .then(() => this.$ajax.post('/generate/meta_field/save', this.$common.removeBlankField(this.form)))
          //校验返回结果
          .then(response => this.$common.checkResult(response.data))
          //执行页面跳转
          .then(() => {
            this.$common.showMsg('success', '添加成功')
            this.goBack()
          })
          .catch(error => this.$common.showNotifyError(error))
      },
      goBack: function () {
        this.$router.push(`/project/${this.projectId}/entity/${this.entityId}/field`)
      }
    },
    created: function () {
      this.form.entityId = parseInt(this.entityId)
    }
  }
</script>

<style>
  .fieldAdd .addForm {
    padding: 30px 50px;
  }

</style>
