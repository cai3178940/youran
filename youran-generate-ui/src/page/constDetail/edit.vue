<template>
  <div class="constDetailEdit">
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/project' }">项目管理</el-breadcrumb-item>
      <el-breadcrumb-item :to="{ path: `/project/${this.projectId}/entity` }">枚举管理</el-breadcrumb-item>
      <el-breadcrumb-item :to="{ path: `/project/${this.projectId}/entity/${this.entityId}/constDetail` }">枚举值管理</el-breadcrumb-item>
      <el-breadcrumb-item>编辑</el-breadcrumb-item>
    </el-breadcrumb>
    <el-row type="flex" align="middle" :gutter="20">
      <el-col :span="12">
        <el-form ref="editForm" class="editForm" :rules="rules" :model="form" label-width="120px">
          <el-form-item label="java枚举值名" prop="jconstDetailName">
            <el-input v-model="form.jconstDetailName" placeholder="java枚举值名，例如：age"></el-input>
          </el-form-item>
          <el-form-item label="mysql枚举值名" prop="constDetailName">
            <el-input v-model="form.constDetailName" placeholder="mysql枚举值名，例如：age"></el-input>
          </el-form-item>
          <el-form-item label="枚举值描述" prop="constDetailDesc">
            <el-input v-model="form.constDetailDesc" placeholder="枚举值描述，例如：年龄"></el-input>
          </el-form-item>
          <el-form-item label="java枚举值类型" prop="jconstDetailType">
            <el-select v-model="form.jconstDetailType" style="width:100%;" filterable placeholder="请选择">
              <el-option
                v-for="item in jconstDetailTypeOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="mysql枚举值类型" prop="constDetailType">
            <el-select v-model="form.constDetailType" style="width:100%;" filterable placeholder="请选择">
              <el-option
                v-for="item in constDetailTypeOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="枚举值长度" prop="constDetailLength">
            <el-input-number v-model="form.constDetailLength" style="width:100%;" :min="0" placeholder="枚举值长度，例如：10"></el-input-number>
          </el-form-item>
          <el-form-item v-if="form.constDetailType=='decimal'" label="枚举值精度" prop="constDetailScale">
            <el-input-number v-model="form.constDetailScale" style="width:100%;" :min="0" placeholder="枚举值精度，例如：2"></el-input-number>
          </el-form-item>
          <el-form-item label="是否主键" prop="primaryKey">
            <el-radio-group v-model="form.primaryKey">
              <el-radio border v-for="item in boolOptions" :key="item.value" :label="item.value">{{item.label}}</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="是否自增" prop="autoIncrement">
            <el-radio-group v-model="form.autoIncrement">
              <el-radio border v-for="item in boolOptions" :key="item.value" :label="item.value">{{item.label}}</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="不能为空" prop="notNull">
            <el-radio-group v-model="form.notNull">
              <el-radio border v-for="item in boolOptions" :key="item.value" :label="item.value">{{item.label}}</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="枚举值示例" prop="constDetailExample">
            <el-input v-model="form.constDetailExample" placeholder="枚举值示例，例如年龄枚举值：21"></el-input>
          </el-form-item>
          <el-form-item label="枚举值备注" prop="constDetailComment">
            <el-input v-model="form.constDetailComment" type="textarea" :rows="2" placeholder="枚举值备注，例如：年龄【整型】"></el-input>
          </el-form-item>
          <el-form-item label="枚举字典" prop="dicType">
            <el-autocomplete style="width:100%;"
                             v-model="form.dicType"
                             :fetch-suggestions="queryDicType"
                             placeholder="请输入枚举字典"
            ></el-autocomplete>
          </el-form-item>
          <el-form-item label="是否查询枚举值" prop="query">
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
          <el-form-item label="是否新增枚举值" prop="insert">
            <el-radio-group v-model="form.insert">
              <el-radio border v-for="item in boolOptions" :key="item.value" :label="item.value">{{item.label}}</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="是否编辑枚举值" prop="update">
            <el-radio-group v-model="form.update">
              <el-radio border v-for="item in boolOptions" :key="item.value" :label="item.value">{{item.label}}</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="是否列表枚举值" prop="list">
            <el-radio-group v-model="form.list">
              <el-radio border v-for="item in boolOptions" :key="item.value" :label="item.value">{{item.label}}</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="是否详情枚举值" prop="show">
            <el-radio-group v-model="form.show">
              <el-radio border v-for="item in boolOptions" :key="item.value" :label="item.value">{{item.label}}</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="排序号" prop="orderNo">
            <el-input-number v-model="form.orderNo" style="width:100%;" :min="1"></el-input-number>
          </el-form-item>
          <el-form-item label="特殊枚举值类型" prop="specialField">
            <el-select v-model="form.specialField" style="width:100%;" filterable placeholder="请选择">
              <el-option
                v-for="item in specialFieldOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
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
    name: 'constDetailEdit',
    props: ['projectId','entityId','constDetailId'],
    data: function () {
      return {
        boolOptions: options.boolOptions,
        constDetailTypeOptions: options.constDetailTypeOptions,
        jconstDetailTypeOptions: options.jconstDetailTypeOptions,
        queryTypeOptions: options.queryTypeOptions,
        specialFieldOptions: options.specialFieldOptions,
        constList:[{
          constName:'SexConst'
        }],
        old: {
          ...constDetailModel
        },
        form: {
          ...constDetailModel
        },
        rules: {
          jconstDetailName: [
            {required: true, message: '请输入java枚举值名', trigger: 'blur'},
            {max: 20, message: '长度不能超过20个字符', trigger: 'blur'}
          ],
          constDetailName: [
            {required: true, message: '请输入mysql枚举值名', trigger: 'blur'},
            {max: 20, message: '长度不能超过20个字符', trigger: 'blur'}
          ],
          constDetailDesc: [
            {required: true, message: '请输入枚举值描述', trigger: 'blur'},
            {max: 40, message: '长度不能超过40个字符', trigger: 'blur'}
          ],
          jconstDetailType: [
            {required: true, type: 'string',message: '请选择java枚举值类型', trigger: 'change'}
          ],
          constDetailType: [
            {required: true, type: 'string',message: '请选择mysql枚举值类型', trigger: 'change'}
          ],
          constDetailLength: [
            {required: true, message: '请输入枚举值长度', trigger: 'blur'},
          ],
          constDetailScale: [
            {required: true, message: '请输入枚举值精度', trigger: 'blur'},
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
          constDetailExample: [
            {required: true, message: '请输入枚举值示例', trigger: 'blur'},
            {max: 100, message: '长度不能超过100个字符', trigger: 'blur'}
          ],
          constDetailComment: [
            {required: true, message: '请输入枚举值备注', trigger: 'blur'},
            {max: 100, message: '长度不能超过100个字符', trigger: 'blur'}
          ],
          dicType: [
            {max: 40, message: '长度不能超过40个字符', trigger: 'blur'}
          ],
          query: [
            {required: true, type: 'number', message: '请选择是否查询枚举值', trigger: 'change'},
          ],
          insert: [
            {required: true, type: 'number', message: '请选择是否新增枚举值', trigger: 'change'},
          ],
          update: [
            {required: true, type: 'number', message: '请选择是否编辑枚举值', trigger: 'change'},
          ],
          list: [
            {required: true, type: 'number', message: '请选择是否列表枚举值', trigger: 'change'},
          ],
          show: [
            {required: true, type: 'number', message: '请选择是否详情枚举值', trigger: 'change'},
          ],
          orderNo: [
            {required: true, message: '请输入排序号', trigger: 'blur'},
          ]
        }
      }
    },
    methods: {
      //查询可用枚举字典
      queryDicType: function (queryString, cb) {
        var constList = this.constList;
        var results = queryString ? constList.filter(
          c=>c.constName.toLowerCase().indexOf(queryString.toLowerCase()) === 0
        ) : constList;
        cb(results.map(c=>({value:c.constName})));
      },
      getField: function () {
        return this.$ajax.get(`/generate/meta_const_detail/${this.constDetailId}`)
          .then(response => this.$common.checkResult(response.data))
          .then(result => this.old = result.data)
          .catch(error => this.$common.showNotifyError(error))
      },
      reset: function () {
        for (const key in constDetailModel) {
          this.form[key] = this.old[key]
        }
      },
      submit: function () {
        //表单预处理
        if(this.form.constDetailType!='decimal'){
          this.form.constDetailScale=null
        }
        //校验表单
        this.$refs.editForm.validate()
        //提交表单
          .then(() => this.$ajax.put('/generate/meta_const_detail/update', this.$common.removeBlankField(this.form)))
          //校验返回结果
          .then(response => this.$common.checkResult(response.data))
          //执行页面跳转
          .then(() => {
            this.$common.showMsg('success', '修改成功')
            this.goBack()
          })
          .catch(error => this.$common.showNotifyError(error))
      },
      goBack: function () {
        this.$router.push(`/project/${this.projectId}/entity/${this.entityId}/constDetail`)
      }
    },
    created: function () {
      this.getField()
        .then(() => this.reset())
    }
  }
</script>

<style>
  .constDetailEdit .editForm {
    padding: 30px 50px;
  }

</style>
