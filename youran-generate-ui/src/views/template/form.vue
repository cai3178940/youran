<template>
  <div class="templateFormDiv">
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/template' }">模板管理</el-breadcrumb-item>
      <el-breadcrumb-item>
        {{edit?'编辑模板':'创建模板'}}
      </el-breadcrumb-item>
    </el-breadcrumb>
    <el-row type="flex" align="middle" :gutter="20">
      <el-col :span="18">
        <el-form ref="templateForm" class="templateForm"
                 :rules="rules" :model="form"
                 label-width="120px" size="small" v-loading="formLoading">
          <el-form-item label="模板名称" prop="name">
            <help-popover>
              <el-input v-model="form.name" placeholder="例如：标准java后端模板" tabindex="10"></el-input>
            </help-popover>
          </el-form-item>
          <el-form-item label="模板编码" prop="code">
            <help-popover>
              <el-input v-model="form.code" placeholder="例如：code-001" tabindex="20"></el-input>
            </help-popover>
          </el-form-item>
          <el-form-item label="版本号" prop="templateVersion">
            <help-popover name="template.templateVersion">
              <el-input v-model="form.templateVersion" placeholder="例如：0.0.1" tabindex="30"></el-input>
            </help-popover>
          </el-form-item>
          <el-form-item label="最低系统兼容" prop="sysLowVersion">
            <help-popover name="template.sysLowVersion">
              <el-input v-model="form.sysLowVersion"
                        :placeholder="'当前系统版本为：'+this.systemUserInfo.sysVersion"
                        tabindex="40"></el-input>
            </help-popover>
          </el-form-item>
          <el-form-item label="备注" prop="remark">
            <help-popover name="template.remark">
              <markdown-editor ref="markdownEditor"
                               v-model="form.remark"
                               :options="markdownOptions"
                               language="zh_CN"
                               height="400px" />
            </help-popover>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="submit()" tabindex="60">提交</el-button>
            <el-button v-if="edit" type="warning" @click="reset()" tabindex="70">重置</el-button>
            <el-button @click="goBack(true)" tabindex="80">返回</el-button>
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import templateApi from '@/api/template'
import { initFormBean, getRules } from './model'
import { mapState } from 'vuex'
import MarkdownEditor from '@/components/MarkdownEditor'

export default {
  name: 'templateForm',
  props: ['templateId'],
  components: { MarkdownEditor },
  data () {
    const edit = !!this.templateId
    return {
      edit: edit,
      formLoading: false,
      old: initFormBean(edit),
      form: initFormBean(edit),
      rules: getRules(),
      markdownOptions: {
        hideModeSwitch: true,
        previewStyle: 'tab'
      }
    }
  },
  computed: {
    ...mapState([
      'systemUserInfo'
    ])
  },
  methods: {
    getTemplate () {
      this.formLoading = true
      return templateApi.get(this.templateId)
        .then(data => { this.old = data })
        .catch(error => this.$common.showNotifyError(error))
        .finally(() => { this.formLoading = false })
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
          return templateApi.saveOrUpdate(this.form, this.edit)
        })
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
