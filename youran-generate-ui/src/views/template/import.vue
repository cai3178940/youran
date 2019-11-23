<template>
  <el-dialog title="模板导入"
             :visible.sync="importTemplateFormVisible"
             v-loading="importTemplateFormLoading"
             width="400px">
    <el-upload drag
               :action="importTemplateUrl"
               :on-success="onImportTemplateSuccess"
               :on-progress="onImportTemplateProgress"
               :on-error="onImportTemplateError"
               :show-file-list="false"
               accept="application/zip">
      <i class="el-icon-upload"></i>
      <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
      <div class="el-upload__tip" slot="tip">只能上传zip格式的压缩包</div>
    </el-upload>
  </el-dialog>
</template>
<script>
import { apiPath } from '@/utils/request'
import eventHub from '@/utils/event-hub'

export default {
  name: 'import-template',
  data () {
    return {
      importTemplateFormLoading: false,
      importTemplateFormVisible: false,
      importTemplateUrl: `/${apiPath}/code_template/import`
    }
  },
  methods: {
    onImportTemplateProgress (event, file, fileList) {
      this.importTemplateFormLoading = true
    },
    onImportTemplateSuccess (response, file, fileList) {
      this.importTemplateFormVisible = false
      this.importTemplateFormLoading = false
      this.$common.showMsg('success', '导入成功')
      eventHub.$emit('import-template-success')
    },
    onImportTemplateError (error, file, fileList) {
      this.importTemplateFormLoading = false
      this.$common.showNotifyError(JSON.parse(error.message))
      eventHub.$emit('import-template-error', error)
    },
    show () {
      this.importTemplateFormVisible = true
    }
  },
  created: function () {
    eventHub.$on('import-template-show', this.show)
  }
}
</script>
<style lang="scss">

</style>
