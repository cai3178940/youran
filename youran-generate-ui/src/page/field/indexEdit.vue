<template>
  <div class="indexEdit">
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/project' }">项目管理</el-breadcrumb-item>
      <el-breadcrumb-item :to="{ path: `/project/${this.projectId}/entity` }">实体管理</el-breadcrumb-item>
      <el-breadcrumb-item :to="{ path: `/project/${this.projectId}/entity/${this.entityId}/field` }">字段管理</el-breadcrumb-item>
      <el-breadcrumb-item>编辑索引</el-breadcrumb-item>
    </el-breadcrumb>
    <el-row type="flex" align="middle" :gutter="20">
      <el-col :span="12">
        <el-form ref="editForm" class="editForm" :rules="rules" :model="form" label-width="120px">
          <el-form-item label="索引名" prop="indexName">
            <help-popover name="index.indexName">
              <el-input v-model="form.indexName" placeholder="索引名，例如：IDX_ORDER_1"></el-input>
            </help-popover>
          </el-form-item>
          <el-form-item label="字段" prop="fieldIds">
            <help-popover name="index.fieldIds">
              <el-select v-model="form.fieldIds" multiple placeholder="请选择">
                <el-option
                  v-for="item in fieldList"
                  :key="item.fieldId"
                  :label="item.fieldName"
                  :value="item.fieldId">
                </el-option>
              </el-select>
            </help-popover>
          </el-form-item>
          <el-form-item label="是否唯一" prop="unique">
            <help-popover name="index.unique">
              <el-radio-group v-model="form.unique">
                <el-radio border v-for="item in boolOptions" :key="item.value" :label="item.value">{{item.label}}
                </el-radio>
              </el-radio-group>
            </help-popover>
          </el-form-item>
          <el-form-item label="唯一性校验" prop="uniqueCheck">
            <help-popover name="index.uniqueCheck">
              <el-radio-group v-model="form.uniqueCheck" :disabled="uniqueCheckDisabled">
                <el-radio border v-for="item in boolOptions" :key="item.value" :label="item.value">{{item.label}}
                </el-radio>
              </el-radio-group>
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
  //索引模型
  const indexModel = {
    indexId: null,
    indexName: '',
    entityId: null,
    unique:0,
    uniqueCheck:0,
    fieldIds:[]
  }

  export default {
    name: 'indexEdit',
    props: ['projectId','entityId','indexId'],
    data: function () {
      return {
        boolOptions: options.boolOptions,
        fieldList: [],
        old: {
          ...indexModel
        },
        form: {
          ...indexModel
        },
        uniqueCheckDisabled: false,
        rules: {
          indexName: [
            {required: true, message: '请输入索引名', trigger: 'blur'},
            {max: 20, message: '长度不能超过20个字符', trigger: 'blur'}
          ],
          unique: [
            {required: true, type: 'number', message: '请选择是否唯一', trigger: 'change'},
          ],
          uniqueCheck: [
            {required: true, type: 'number', message: '请选择唯一性校验', trigger: 'change'},
          ],
          fieldIds: [
            {required: true, type: 'array', message: '请选择字段', trigger: 'change'},
          ]
        }
      }
    },
    watch: {
      'form.indexName': function (value) {
        const lc = /[a-z]/i;
        if(lc.test(value)){
          this.form.indexName = value.toUpperCase()
        }
      },
      'form.unique': function (value) {
        if (value == 1) {
          this.form.uniqueCheck = 1
          this.uniqueCheckDisabled = true
        }else{
          this.uniqueCheckDisabled = false
        }
      }
    },
    methods: {
      queryField: function (entityId) {
        return this.$common.getFieldOptions(entityId)
          .then(response => this.$common.checkResult(response.data))
          .then(result => this.fieldList = result.data)
      },
      getIndex: function () {
        return this.$ajax.get(`/generate/meta_index/${this.indexId}`)
          .then(response => this.$common.checkResult(response.data))
          .then(result => {
            var old = {
              ...result.data
            }
            old.fieldIds=old.fields.map(field=>field.fieldId)
            old.fields = null
            this.old = old
          })
          .catch(error => this.$common.showNotifyError(error))
      },
      reset: function () {
        for (const key in indexModel) {
          this.form[key] = this.old[key]
        }
      },
      submit: function () {
        var params = {
          ...this.form
        }
        params.fieldIds = this.form.fieldIds.join(",")
        var loading = null
        //校验表单
        this.$refs.editForm.validate()
        //提交表单
          .then(() => {
            loading = this.$loading()
            return this.$ajax.put('/generate/meta_index/update', this.$common.removeBlankField(params))
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
      Promise.all([this.getIndex(),this.queryField(this.entityId)])
        .then(() => this.reset())
    }
  }
</script>

<style>
  .indexEdit .editForm {
    padding: 30px 50px;
  }

</style>
