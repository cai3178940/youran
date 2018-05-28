<template>
  <div class="fieldEdit">
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/project' }">项目管理</el-breadcrumb-item>
      <el-breadcrumb-item :to="{ path: `/project/${this.projectId}/entity` }">实体管理</el-breadcrumb-item>
      <el-breadcrumb-item :to="{ path: `/project/${this.projectId}/entity/${this.entityId}/field` }">字段管理</el-breadcrumb-item>
      <el-breadcrumb-item>编辑</el-breadcrumb-item>
    </el-breadcrumb>
    <el-row type="flex" align="middle" :gutter="20">
      <el-col :span="12">
        <el-form ref="editForm" class="editForm" :rules="rules" :model="form" label-width="120px">
          <el-form-item label="java字段名" prop="jfieldName">
            <help-popover name="field.jfieldName">
              <el-input v-model="form.jfieldName" placeholder="java字段名，例如：age"></el-input>
            </help-popover>
          </el-form-item>
          <el-form-item label="mysql字段名" prop="fieldName">
            <help-popover name="field.fieldName">
              <el-input v-model="form.fieldName" placeholder="mysql字段名，例如：age"></el-input>
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
                </el-option>
              </el-select>
            </help-popover>
          </el-form-item>
          <el-form-item label="mysql字段类型" prop="fieldType">
            <help-popover name="field.fieldType">
              <el-select v-model="form.fieldType" style="width:100%;" filterable placeholder="请选择">
                <el-option
                  v-for="item in fieldTypeOptions"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </help-popover>
          </el-form-item>
          <el-form-item label="字段长度" prop="fieldLength">
            <help-popover name="field.fieldLength">
              <el-input-number v-model="form.fieldLength" style="width:100%;" :min="0" placeholder="字段长度，例如：10"></el-input-number>
            </help-popover>
          </el-form-item>
          <el-form-item v-if="fieldScaleVisible" label="字段精度" prop="fieldScale">
            <help-popover name="field.fieldScale">
              <el-input-number v-model="form.fieldScale" style="width:100%;" :min="0" placeholder="字段精度，例如：2"></el-input-number>
            </help-popover>
          </el-form-item>
          <el-form-item label="是否主键" prop="primaryKey">
            <help-popover name="field.primaryKey">
              <el-radio-group v-model="form.primaryKey">
                <el-radio border v-for="item in boolOptions" :key="item.value" :label="item.value">{{item.label}}</el-radio>
              </el-radio-group>
            </help-popover>
          </el-form-item>
          <el-form-item label="是否自增" prop="autoIncrement">
            <help-popover name="field.autoIncrement">
              <el-radio-group :disabled="autoIncrementDisabled" v-model="form.autoIncrement">
                <el-radio border v-for="item in boolOptions" :key="item.value" :label="item.value">{{item.label}}</el-radio>
              </el-radio-group>
            </help-popover>
          </el-form-item>
          <el-form-item label="不能为空" prop="notNull">
            <help-popover name="field.notNull">
              <el-radio-group :disabled="notNullDisabled" v-model="form.notNull">
                <el-radio border v-for="item in boolOptions" :key="item.value" :label="item.value">{{item.label}}</el-radio>
              </el-radio-group>
            </help-popover>
          </el-form-item>
          <el-form-item label="是否外键" prop="foreignKey">
            <help-popover name="field.foreignKey">
              <el-radio-group v-model="form.foreignKey">
                <el-radio border v-for="item in boolOptions" :key="item.value" :label="item.value">{{item.label}}</el-radio>
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
          <el-form-item label="特殊字段类型" prop="specialField">
            <help-popover name="field.specialField">
              <el-select :disabled="specialFieldDisabled" clearable v-model="form.specialField" style="width:100%;" filterable placeholder="请选择">
                <el-option
                  v-for="item in specialFieldOptions"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
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
                <el-radio border v-for="item in boolOptions" :key="item.value" :label="item.value">{{item.label}}</el-radio>
              </el-radio-group>
            </help-popover>
          </el-form-item>
          <el-form-item label="查询方式" prop="queryType">
            <help-popover name="field.queryType">
              <el-select :disabled="queryTypeDisabled" clearable v-model="form.queryType" style="width:100%;" filterable placeholder="请选择">
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
                <el-radio border v-for="item in boolOptions" :key="item.value" :label="item.value">{{item.label}}</el-radio>
              </el-radio-group>
            </help-popover>
          </el-form-item>
          <el-form-item label="是否编辑字段" prop="update">
            <help-popover name="field.update">
              <el-radio-group v-model="form.update">
                <el-radio border v-for="item in boolOptions" :key="item.value" :label="item.value">{{item.label}}</el-radio>
              </el-radio-group>
            </help-popover>
          </el-form-item>
          <el-form-item label="是否列表字段" prop="list">
            <help-popover name="field.list">
              <el-radio-group v-model="form.list">
                <el-radio border v-for="item in boolOptions" :key="item.value" :label="item.value">{{item.label}}</el-radio>
              </el-radio-group>
            </help-popover>
          </el-form-item>
          <el-form-item label="是否支持排序" prop="listSort">
            <help-popover name="field.listSort">
              <el-radio-group v-model="form.listSort">
                <el-radio border v-for="item in boolOptions" :key="item.value" :label="item.value">{{item.label}}</el-radio>
              </el-radio-group>
            </help-popover>
          </el-form-item>
          <el-form-item label="是否详情字段" prop="show">
            <help-popover name="field.show">
              <el-radio-group v-model="form.show">
                <el-radio border v-for="item in boolOptions" :key="item.value" :label="item.value">{{item.label}}</el-radio>
              </el-radio-group>
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
    //是否外键
    foreignKey: 0,
    //外键实体id
    foreignEntityId: null,
    //外键字段id
    foreignFieldId: null,
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
    //是否支持排序
    listSort: 0,
    //是否详情字段
    show: 1,
    //编辑方式(暂时不用)
    editType: null,
    //排序号
    orderNo: 1
  }

  export default {
    name: 'fieldEdit',
    props: ['projectId','entityId','fieldId'],
    data: function () {
      var checkQueryType = (rule, value, callback) => {
        if (!value && this.form.query==1) {
          callback(new Error('请选择查询方式'))
        } else {
          callback()
        }
      }
      return {
        boolOptions: options.boolOptions,
        fieldTypeOptions: options.fieldTypeOptions,
        jfieldTypeOptions: options.jfieldTypeOptions,
        queryTypeOptions: options.queryTypeOptions,
        specialFieldOptions: options.specialFieldOptions,
        entityFieldOptions:[],
        constList:null,
        notNullDisabled:false,
        autoIncrementDisabled:true,
        queryTypeDisabled:true,
        specialFieldDisabled:false,
        foreignField:[0,0],
        foreignFieldDisabled: true,
        old: {
          ...fieldModel
        },
        form: {
          ...fieldModel
        },
        rules: {
          jfieldName: [
            {required: true, message: '请输入java字段名', trigger: 'blur'},
            {max: 50, message: '长度不能超过50个字符', trigger: 'blur'}
          ],
          fieldName: [
            {required: true, message: '请输入mysql字段名', trigger: 'blur'},
            {max: 64, message: '长度不能超过64个字符', trigger: 'blur'}
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
          foreignKey: [
            {required: true, type: 'number', message: '请选择是否外键', trigger: 'change'},
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
          queryType: [
            {validator: checkQueryType, trigger: 'change'},
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
          listSort: [
            {required: true, type: 'number', message: '请选择是否支持排序', trigger: 'change'},
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
    computed:{
      dicTypeDisabled:function(){
        if(this.form.primaryKey==1||this.form.specialField){
          return true
        }
        return false
      },
      fieldScaleVisible:function () {
        return options.showFieldScale(this.form.fieldType)
      }
    },
    watch: {
      'form.primaryKey':function (value) {
        if(value==1){
          this.form.notNull=1
          this.notNullDisabled=true
          this.form.specialField=''
          this.specialFieldDisabled=true
          this.autoIncrementDisabled=false
        }else{
          this.notNullDisabled=false
          this.specialFieldDisabled=false
          this.form.autoIncrement=0
          this.autoIncrementDisabled=true
        }
      },
      'form.foreignKey':function (value) {
        if(value==1){
          this.foreignFieldDisabled=false
        }else{
          this.foreignFieldDisabled=true
        }
      },
      'form.query':function (value) {
        if(value==1){
          this.queryTypeDisabled=false
        }else{
          this.queryTypeDisabled=true
          this.queryType=''
        }
      },
      'dicTypeDisabled':function (value) {
        if(value){
          this.form.dicType=''
        }
      }
    },
    methods: {
      initForeignEntityOptions: function () {
        return this.$common.getEntityOptions(this.projectId)
          .then(response => this.$common.checkResult(response.data))
          .then(result => this.entityFieldOptions = result.data.entities.map(entity=>({value:entity.entityId,label:entity.title,children:[]})))
      },
      handleForeignEntityChange: function (optionArray) {
        var entityId = optionArray[0]
        //获取被激活的option
        var entity = this.entityFieldOptions.find(option=>option.value==entityId)
        if(entity.children.length){
          return
        }
        return this.$common.getFieldOptions(entityId)
          .then(response => this.$common.checkResult(response.data))
          .then(result => entity.children = result.data.filter(field=>field.primaryKey==1).map(field=>({value:field.fieldId,label:field.fieldDesc})))
      },
      //查询可用枚举字典
      queryDicType: function (queryString, cb) {
        //定义回调操作
        var action = function () {
          var constList = this.constList.slice(0)
          constList.push(...options.defaultConstList)
          var results = queryString ? constList.filter(
            c=>c.constName.toLowerCase().indexOf(queryString.toLowerCase()) === 0
          ) : constList
          cb(results.map(c=>({value:c.constName})))
        }.bind(this)
        if(this.constList){
          action()
        }else{
          this.$common.getConstOptions(this.projectId)
            .then(response => this.$common.checkResult(response.data))
            .then(result=>{
              this.constList = result.data.entities
              action()
            })
        }
      },
      getField: function () {
        return this.$ajax.get(`/generate/meta_field/${this.fieldId}`)
          .then(response => this.$common.checkResult(response.data))
          .then(result => this.old = result.data)
          .catch(error => this.$common.showNotifyError(error))
      },
      reset: function () {
        for (const key in fieldModel) {
          this.form[key] = this.old[key]
        }
      },
      submit: function () {
        //表单预处理
        if (!options.showFieldScale(this.form.fieldType)) {
          this.form.fieldScale=null
        }
        this.form.foreignEntityId = this.foreignField[0]
        this.form.foreignFieldId = this.foreignField[1]
        var loading = null
        //校验表单
        this.$refs.editForm.validate()
        //提交表单
          .then(() => {
            loading = this.$loading()
            return this.$ajax.put('/generate/meta_field/update', this.$common.removeBlankField(this.form))
          })
          //校验返回结果
          .then(response => this.$common.checkResult(response.data))
          //执行页面跳转
          .then(() => {
            this.$common.showMsg('success', '修改成功')
            this.goBack()
          })
          .catch(error => this.$common.showNotifyError(error))
          .finally(()=>{
            if(loading){
              loading.close()
            }
          })
      },
      goBack: function () {
        this.$router.push(`/project/${this.projectId}/entity/${this.entityId}/field`)
      }
    },
    created: function () {
      Promise.all([this.getField(),this.initForeignEntityOptions()])
        .then(() => this.reset())
        .then(()=>this.handleForeignEntityChange([this.form.foreignEntityId]))
        .then(()=>{
          this.foreignField = [this.form.foreignEntityId,this.form.foreignFieldId]
        })
    }
  }
</script>

<style>
  .fieldEdit .editForm {
    padding: 30px 50px;
  }

</style>
