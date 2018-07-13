<template>
  <div class="constDetailEdit">
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/project' }">项目管理</el-breadcrumb-item>
      <el-breadcrumb-item :to="{ path: `/project/${this.projectId}/const` }">枚举管理</el-breadcrumb-item>
      <el-breadcrumb-item>编辑枚举值</el-breadcrumb-item>
    </el-breadcrumb>
    <el-row type="flex" align="middle" :gutter="20">
      <el-col :span="12">
        <el-form ref="editForm" class="editForm" :rules="rules" :model="form" label-width="120px">
          <el-form-item label="字段名" prop="detailName">
            <help-popover name="constDetail.detailName">
              <el-input v-model="form.detailName" placeholder="字段名，例如：WOMAN"></el-input>
            </help-popover>
          </el-form-item>
          <el-form-item label="枚举值" prop="detailValue">
            <help-popover name="constDetail.detailValue">
              <el-input v-model="form.detailValue" placeholder="枚举值，例如：2"></el-input>
            </help-popover>
          </el-form-item>
          <el-form-item label="值描述" prop="detailRemark">
            <help-popover name="constDetail.detailRemark">
              <el-input v-model="form.detailRemark" placeholder="值描述，例如：女"></el-input>
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

  //枚举值模型
  const constDetailModel = {
    constDetailId: null,
    constId: null,
    //枚举字段名
    detailName: '',
    //枚举值
    detailValue: '',
    //值描述
    detailRemark: ''
  }

  export default {
    name: 'constDetailEdit',
    props: ['projectId','constId','constDetailId'],
    data: function () {
      return {
        old: {
          ...constDetailModel
        },
        form: {
          ...constDetailModel
        },
        rules: {
          detailName: [
            {required: true, message: '请输入字段名', trigger: 'blur'},
            {max: 50, message: '长度不能超过50个字符', trigger: 'blur'}
          ],
          detailValue: [
            {required: true, message: '请输入枚举值', trigger: 'blur'},
            {max: 50, message: '长度不能超过50个字符', trigger: 'blur'}
          ],
          detailRemark: [
            {required: true, message: '请输入值描述', trigger: 'blur'},
            {max: 100, message: '长度不能超过100个字符', trigger: 'blur'}
          ]
        }
      }
    },
    methods: {
      getConstDetail: function () {
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
        var loading = null
        //校验表单
        this.$refs.editForm.validate()
        //提交表单
          .then(() => {
            loading = this.$loading()
            return this.$ajax.put('/generate/meta_const_detail/update', this.form)
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
        this.$router.push(`/project/${this.projectId}/const/${this.constId}`)
      }
    },
    created: function () {
      this.getConstDetail()
        .then(() => this.reset())
    }
  }
</script>

<style>
  .constDetailEdit .editForm {
    padding: 30px 50px;
  }

</style>
