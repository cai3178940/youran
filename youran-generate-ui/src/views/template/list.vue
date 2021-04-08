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
        <!--<el-button @click.native="handleAdd" type="success">创建模板</el-button>-->
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
          <span v-if="scope.row.sysDefault" class="table-column-tag sys-default-tag">
            预置模板
          </span>
        </template>
      </el-table-column>
      <el-table-column property="name" label="模板名称"></el-table-column>
      <el-table-column property="templateVersion" width="150" label="版本号"></el-table-column>
      <el-table-column property="sysLowVersion" width="150" label="最低系统兼容"></el-table-column>
      <el-table-column property="operatedTime" width="180" label="修改时间"></el-table-column>
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
  </div>
</template>

<script>
import templateFiles from './templateFiles'
import templateApi from '@/api/template'
import eventHub from '@/utils/event-hub'

export default {
  name: 'templateList',
  components: {
    templateFiles
  },
  data () {
    return {
      activeNum: 0,
      selectItems: [],
      list: [],
      loading: false
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
      const templateIds = this.selectItems.map(template => template.templateId)
      this.$common.confirm('是否确认删除')
        .then(() => templateApi.deleteBatch(templateIds))
        .then(() => this.doQuery())
        .catch(error => this.$common.showNotifyError(error))
    },
    // 列表查询
    doQuery () {
      this.loading = true
      return templateApi.getList()
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
      templateApi.exportZip(row.templateId)
    },
    copyTemplate (row) {
      this.$common.confirm(`是否确认复制模板(${row.code})`)
        .then(() => templateApi.copy(row.templateId))
        .then(() => this.doQuery())
        .catch(error => this.$common.showNotifyError(error))
    },
    handleImport () {
      eventHub.$emit('import-template-show')
    }
  },
  activated () {
    this.doQuery()
  },
  created: function () {
    eventHub.$on('import-template-success', this.doQuery)
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
      background-color: $color-warning;
    }

    /**
     * 调整表格行高
     */
    .el-table td {
      padding: $el-table-padding;
    }

  }
</style>
