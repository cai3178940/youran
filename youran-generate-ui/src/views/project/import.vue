<template>
  <el-dialog title="元数据导入"
             :visible.sync="importFormVisible"
             v-loading="importFormLoading"
             width="400px">
    <el-upload drag
               :action="importUrl"
               :on-success="onImportSuccess"
               :on-progress="onImportProgress"
               :on-error="onImportError"
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
  name: 'import-project',
  data () {
    return {
      importFormLoading: false,
      importFormVisible: false,
      importUrl: `/${apiPath}/meta_import`
    }
  },
  methods: {
    onImportProgress (event, file, fileList) {
      this.importFormLoading = true
    },
    onImportSuccess (response, file, fileList) {
      this.importFormVisible = false
      this.importFormLoading = false
      this.$common.showMsg('success', '导入成功')
      this.$emit('success')
    },
    onImportError (error, file, fileList) {
      this.importFormLoading = false
      this.$common.showNotifyError(JSON.parse(error.message))
      this.$emit('error', error)
    },
    show () {
      this.importFormVisible = true
    }
  }
}
</script>
<style lang="scss">

</style>
