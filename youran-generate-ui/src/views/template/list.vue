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
        <el-button @click.native="handleDel" type="danger">删除模板</el-button>
      </el-col>
    </el-row>
    <el-table :data="list" style="width: 100%" @selection-change="selectionChange" v-loading="loading">
      <el-table-column type="selection" width="50"></el-table-column>
      <el-table-column property="className" label="类名"></el-table-column>
      <el-table-column property="tableName" label="表名"></el-table-column>
      <el-table-column property="desc" label="描述"></el-table-column>
      <el-table-column
        label="操作"
        width="200">
        <template slot-scope="scope">
          <el-button @click="handleEdit(scope.row)" type="text" size="medium">编辑</el-button>
          <el-button @click="handleTemplateFile(scope.row)" type="text" size="medium">文件管理</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import { apiPath } from '@/components/common'

export default {
  name: 'templateList',
  components: {  },
  data () {
    return {
      query: {},
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
      this.$common.confirm('是否确认删除')
        .then(() => this.$ajax.put(`/${apiPath}/meta_template/deleteBatch`, this.selectItems.map(template => template.templateId)))
        .then(response => this.$common.checkResult(response))
        .then(() => this.doQuery())
        .catch(error => this.$common.showNotifyError(error))
    },
    // 列表查询
    doQuery () {
      this.loading = true
      this.loadingText = '列表加载中'
      return this.$ajax.get(`/${apiPath}/meta_template/list`, { params: this.query })
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
      this.$router.push(`/template/${row.templateId}/template_file`)
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
     * 调整表格行高
     */
    .el-table td {
      padding: $el-table-padding;
    }

  }
</style>
