<template>
  <div class="constDetailAdd">
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/project' }">项目管理</el-breadcrumb-item>
      <el-breadcrumb-item :to="{ path: `/project/${this.projectId}/const` }">枚举管理</el-breadcrumb-item>
      <el-breadcrumb-item>添加枚举值</el-breadcrumb-item>
    </el-breadcrumb>
    <el-row type="flex" align="middle" :gutter="20">
      <el-col :span="12">
        <el-form ref="addForm" class="addForm" :rules="rules" :model="form" label-width="120px">
          <el-form-item label="字段名" prop="detailName">
            <help-popover name="constDetail.detailName">
              <el-input v-model="form.detailName" placeholder="枚举字段名，例如：WOMAN"></el-input>
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
import { apiPath } from '@/components/common'
import { initDetailFormBean, getDetailRules } from './model'

export default {
  name: 'constDetailAdd',
  props: ['projectId', 'constId'],
  data () {
    return {
      form: initDetailFormBean(false),
      rules: getDetailRules()
    }
  },
  watch: {
    'form.detailName' (value) {
      const lc = /[a-z]/i
      if (lc.test(value)) {
        this.form.detailName = value.toUpperCase()
      }
    }
  },
  methods: {
    submit () {
      let loading = null
      // 校验表单
      this.$refs.addForm.validate()
        // 提交表单
        .then(() => {
          loading = this.$loading()
          return this.$ajax.post(`/${apiPath}/meta_const_detail/save`, this.form)
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
      this.$router.push(`/project/${this.projectId}/const/${this.constId}`)
    }
  },
  created () {
    this.form.constId = parseInt(this.constId)
  }
}
</script>

<style lang="scss">
  @import '../../assets/common.scss';
  .constDetailAdd .addForm {
    @include youran-form;
  }

</style>
