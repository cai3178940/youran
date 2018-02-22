<template>
  <div class="indexShow">
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/project' }">项目管理</el-breadcrumb-item>
      <el-breadcrumb-item :to="{ path: `/project/${this.projectId}/entity` }">实体管理</el-breadcrumb-item>
      <el-breadcrumb-item :to="{ path: `/project/${this.projectId}/entity/${this.entityId}/index` }">索引管理</el-breadcrumb-item>
      <el-breadcrumb-item>查看</el-breadcrumb-item>
    </el-breadcrumb>
    <el-row type="flex" align="middle" :gutter="20">
      <el-col :span="12">
        <el-form ref="showForm" class="showForm" :rules="rules" :model="form" label-width="120px">
          <el-form-item label="索引名" prop="indexName">
            <el-input :disabled="true" v-model="form.indexName" placeholder="索引名，例如：IDX_ORDER_1"></el-input>
          </el-form-item>
          <el-form-item label="字段" prop="fieldIds">
            <el-select :disabled="true" v-model="form.fieldIds" multiple placeholder="请选择">
              <el-option
                v-for="item in fieldList"
                :key="item.fieldId"
                :label="item.fieldName"
                :value="item.fieldId">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="是否唯一" prop="unique">
            <el-radio-group :disabled="true" v-model="form.unique">
              <el-radio border v-for="item in boolOptions" :key="item.value" :label="item.value">{{item.label}}
              </el-radio>
            </el-radio-group>
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
  //索引模型
  const indexModel = {
    indexId: null,
    indexName: '',
    entityId: null,
    unique:0,
    fieldIds:[]
  }

  export default {
    name: 'indexShow',
    props: ['projectId','entityId','indexId'],
    data: function () {
      return {
        boolOptions: options.boolOptions,
        fieldList: [],
        form: {
          ...indexModel
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
      goBack: function () {
        this.$router.push(`/project/${this.projectId}/entity/${this.entityId}/index`)
      }
    },
    created: function () {
      Promise.all([this.getIndex(),this.queryField(this.entityId)])
        .then(() => this.reset())
    }
  }
</script>

<style>
  .indexShow .showForm {
    padding: 30px 50px;
  }

</style>
