<template>
  <el-dialog title="模板导入"
             :visible.sync="importTemplateFormVisible"
             width="400px">
    <el-upload drag
               :action="importTemplateUrl"
               :on-success="onImportTemplateSuccess"
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

export default {
  name: 'import-template',
  data () {
    return {
      importTemplateFormVisible: false,
      importTemplateUrl: `/${apiPath}/code_template/import`
    }
  },
  methods: {
    onImportTemplateSuccess (response, file, fileList) {
      this.importTemplateFormVisible = false
      this.$common.showMsg('success', '导入成功')
      this.$emit('success')
    },
    onImportTemplateError (error, file, fileList) {
      this.$common.showNotifyError(JSON.parse(error.message))
      this.$emit('error')
    },
    show () {
      this.importTemplateFormVisible = true
    }
  }
}
</script>
<style lang="scss">

</style>
