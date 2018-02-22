<template>
  <div class="indexAdd">
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/project' }">项目管理</el-breadcrumb-item>
      <el-breadcrumb-item :to="{ path: `/project/${this.projectId}/entity` }">实体管理</el-breadcrumb-item>
      <el-breadcrumb-item :to="{ path: `/project/${this.projectId}/entity/${this.entityId}/index` }">索引管理
      </el-breadcrumb-item>
      <el-breadcrumb-item>添加</el-breadcrumb-item>
    </el-breadcrumb>
    <el-row type="flex" align="middle" :gutter="20">
      <el-col :span="12">
        <el-form ref="addForm" class="addForm" :rules="rules" :model="form" label-width="120px">
          <el-form-item label="索引名" prop="indexName">
            <el-input v-model="form.indexName" placeholder="索引名，例如：IDX_ORDER_1"></el-input>
          </el-form-item>
          <el-form-item label="是否唯一" prop="unique">
            <el-radio-group v-model="form.unique">
              <el-radio border v-for="item in boolOptions" :key="item.value" :label="item.value">{{item.label}}
              </el-radio>
            </el-radio-group>
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
    name: 'indexAdd',
    props: ['projectId','entityId'],
    data: function () {
      return {
        boolOptions: options.boolOptions,
        form: {
          entityId: null,
          //索引名
          indexName: '',
          //是否唯一
          unique: 0
        },
        rules: {
          indexName: [
            {required: true, message: '请输入索引名', trigger: 'blur'},
            {max: 20, message: '长度不能超过20个字符', trigger: 'blur'}
          ],
          unique: [
            {required: true, type: 'number', message: '请选择是否唯一', trigger: 'change'},
          ],
        }
      }
    },
    methods: {
      submit: function () {
        //校验表单
        this.$refs.addForm.validate()
        //提交表单
          .then(() => this.$ajax.post('/generate/meta_index/save', this.form))
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
        this.$router.push(`/project/${this.projectId}/entity/${this.entityId}/index`)
      }
    },
    created: function () {
      this.form.entityId = parseInt(this.entityId)
    }
  }
</script>

<style>
  .indexAdd .addForm {
    padding: 30px 50px;
  }

</style>
