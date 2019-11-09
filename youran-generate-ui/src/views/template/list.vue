<template>
  <div class="templateList">
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item>模板管理</el-breadcrumb-item>
    </el-breadcrumb>
    <el-row type="flex" align="middle" :gutter="20" style="padding:10px 0;">
      <el-col :span="6" class="activeNum">
        已选择{{ this.activeNum }}个模板
      </el-col>
      <el-col :span="18" style="text-align: right;">
        <el-button @click.native="handleAdd" type="success">添加模板</el-button>
        <el-button @click.native="handleImport" type="warning">模板导入</el-button>
        <el-button @click.native="handleDel" type="danger">删除模板</el-button>
      </el-col>
    </el-row>
    <el-table :data="list" style="width: 100%"
              @selection-change="selectionChange"
              v-loading="loading" :border="true">
      <el-table-column type="selection" width="50"></el-table-column>
      <el-table-column label="模板编码">
        <template v-slot="scope">
          {{scope.row.code}}
          <span v-if="scope.row.sysDefault" class="sys-default-tag">
            预置模板
          </span>
        </template>
      </el-table-column>
      <el-table-column property="name" label="模板名称"></el-table-column>
      <el-table-column property="templateVersion" width="150" label="版本号"></el-table-column>
      <el-table-column property="sysLowVersion" width="150" label="最低系统兼容"></el-table-column>
      <el-table-column
        label="操作"
        width="200">
        <template v-slot="scope">
          <el-button v-if="!scope.row.sysDefault"
                     @click="handleEdit(scope.row)"
                     type="text" size="medium">编辑</el-button>
          <el-button @click="exportTemplate(scope.row)"
                     type="text" size="medium">导出</el-button>
          <el-button @click="copyTemplate(scope.row)"
                     type="text" size="medium">复制</el-button>
          <el-button v-if="!scope.row.sysDefault"
                     @click="handleTemplateFile(scope.row)"
                     type="text" size="medium">文件管理</el-button>
        </template>
      </el-table-column>
    </el-table>
    <template-files ref="templateFiles"></template-files>
    <!-- 文件下载专用iframe -->
    <iframe style="display:none;" :src="downloadUrl"></iframe>
    <!-- 模板导入对话框 -->
    <import-template ref="importTemplate" @success="doQuery"></import-template>
  </div>
</template>

<script>
import { apiPath } from '@/components/common'
import templateFiles from './templateFiles'
import importTemplate from './import.vue'

export default {
  name: 'templateList',
  components: {
    templateFiles,
    importTemplate
  },
  data () {
    return {
      query: {},
      activeNum: 0,
      selectItems: [],
      list: [],
      loading: false,
      downloadUrl: null
    }
  },
  methods: {
    selectionChange (val) {
      this.selectItems = val
      this.activeNum = this.selectItems.length
    },
    handleDel () {
      if (this.activeNum <= 0) {
        this.$common.showMsg('warning', '请选择模板')
        return
      }
      this.$common.confirm('是否确认删除')
        .then(() => this.$ajax.delete(`/${apiPath}/code_template`,
          {
            data: this.selectItems.map(template => template.templateId)
          }))
        .then(response => this.$common.checkResult(response))
        .then(() => this.doQuery())
        .catch(error => this.$common.showNotifyError(error))
    },
    // 列表查询
    doQuery () {
      this.loading = true
      this.loadingText = '列表加载中'
      return this.$ajax.get(`/${apiPath}/code_template`, { params: this.query })
        .then(response => this.$common.checkResult(response))
        .then(data => {
          this.list = data
        })
        .catch(error => this.$common.showNotifyError(error))
        .finally(() => { this.loading = false })
    },
    handleAdd () {
      this.$router.push(`/template/add`)
    },
    handleEdit (row) {
      this.$router.push(`/template/edit/${row.templateId}`)
    },
    handleTemplateFile (row) {
      this.$refs.templateFiles.show(row.templateId, row.name)
    },
    exportTemplate (row) {
      this.downloadUrl = `/${apiPath}/code_template/${row.templateId}/export`
      setTimeout(() => {
        this.downloadUrl = null
      }, 2000)
    },
    copyTemplate (row) {
      this.$common.confirm(`是否确认复制模板(${row.code})`)
        .then(() => this.$ajax.post(`/${apiPath}/code_template/${row.templateId}/copy`))
        .then(response => this.$common.checkResult(response))
        .then(() => this.doQuery())
        .catch(error => this.$common.showNotifyError(error))
    },
    handleImport () {
      this.$refs.importTemplate.show()
    }
  },
  activated () {
    this.doQuery()
  }
}
</script>
<style lang="scss">
  @import '../../assets/common.scss';

  .templateList {
    .activeNum {
      min-width: 160px;
      text-align: left;
      padding: 0 0 0 20px;
    }

    /**
     * 预置标签
     */
    .sys-default-tag {
      font-size: 11px;
      background-color: $color-warning;
      color: #FFFFFF;
      padding: 2px;
      border: 2px solid transparent;
      border-radius:4px;
      margin: 0px;
      user-select: none;
    }

    /**
     * 调整表格行高
     */
    .el-table td {
      padding: $el-table-padding;
    }

  }
</style>
