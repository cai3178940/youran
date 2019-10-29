<template>
  <div class="templateFormDiv">
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/template' }">模板管理</el-breadcrumb-item>
      <el-breadcrumb-item>
        {{edit?'编辑模板':'添加模板'}}
      </el-breadcrumb-item>
    </el-breadcrumb>
    <el-row type="flex" align="middle" :gutter="20">
      <el-col :span="12">
        <el-form ref="templateForm" class="templateForm" :rules="rules" :model="form" label-width="120px" size="small">
          <el-form-item label="模板名称" prop="name">
            <help-popover>
              <el-input v-model="form.name" placeholder="例如：标准java后端模板"></el-input>
            </help-popover>
          </el-form-item>
          <el-form-item label="模板类型" prop="templateType">
            <help-popover name="template.templateType">
              <el-radio-group v-model="form.templateType">
                <el-radio border :label="1">后端</el-radio>
                <el-radio border :label="2">前端</el-radio>
              </el-radio-group>
            </help-popover>
          </el-form-item>
          <el-form-item label="版本号" prop="templateVersion">
            <help-popover name="template.templateVersion">
              <el-input v-model="form.templateVersion" placeholder="例如：1.0.0"></el-input>
            </help-popover>
          </el-form-item>
          <el-form-item label="最低系统兼容" prop="sysLowVersion">
            <help-popover name="template.sysLowVersion">
              <el-input v-model="form.sysLowVersion" :placeholder="'当前系统版本为：'+this.systemUserInfo.sysVersion"></el-input>
            </help-popover>
          </el-form-item>
          <el-form-item label="备注" prop="remark">
            <help-popover name="template.remark">
              <el-input v-model="form.remark" type="textarea" :autosize="{ minRows: 2, maxRows: 10}"></el-input>
            </help-popover>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="submit()">提交</el-button>
            <el-button v-if="edit" type="warning" @click="reset()">重置</el-button>
            <el-button @click="goBack(true)">返回</el-button>
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import options from '@/components/options'
import { apiPath } from '@/components/common'
import { initFormBean, getRules } from './model'
import { mapState } from 'vuex'

export default {
  name: 'templateForm',
  props: ['templateId'],
  data () {
    const edit = !!this.templateId
    return {
      edit: edit,
      boolOptions: options.boolOptions,
      old: initFormBean(edit),
      form: initFormBean(edit),
      rules: getRules()
    }
  },
  computed: {
    ...mapState([
      'systemUserInfo'
    ])
  },
  methods: {
    getTemplate () {
      return this.$ajax.get(`/${apiPath}/code_template/${this.templateId}`)
        .then(response => this.$common.checkResult(response))
        .then(data => { this.old = data })
        .catch(error => this.$common.showNotifyError(error))
    },
    reset () {
      for (const key in initFormBean(true)) {
        this.form[key] = this.old[key]
      }
    },
    submit () {
      let loading = null
      // 校验表单
      this.$refs.templateForm.validate()
        // 提交表单
        .then(() => {
          loading = this.$loading()
          if (this.edit) {
            return this.$ajax.put(`/${apiPath}/code_template`, this.form)
          } else {
            return this.$ajax.post(`/${apiPath}/code_template`, this.form)
          }
        })
        // 校验返回结果
        .then(response => this.$common.checkResult(response))
        // 执行页面跳转
        .then(() => {
          this.$common.showMsg('success', '操作成功')
          this.goBack(false)
        })
        .catch(error => this.$common.showNotifyError(error))
        .finally(() => {
          if (loading) {
            loading.close()
          }
        })
    },
    goBack (preferHistory) {
      if (preferHistory && window.history.length > 1) {
        this.$router.go(-1)
      } else {
        this.$router.push('/template')
      }
    }
  },
  created () {
    if (this.edit) {
      this.getTemplate().then(() => this.reset())
    } else {
      this.form.sysLowVersion = this.systemUserInfo.sysVersion
    }
  }
}
</script>

<style lang="scss">
  @import '../../assets/common.scss';
  .templateFormDiv .templateForm {
    @include youran-form;
  }

</style>
