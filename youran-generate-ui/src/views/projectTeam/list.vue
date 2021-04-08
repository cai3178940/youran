<template>
  <div class="projectTeamList">
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item>项目组管理</el-breadcrumb-item>
    </el-breadcrumb>
    <el-row type="flex" align="middle" :gutter="20" style="padding:10px 0;">
      <el-col :span="6" class="activeNum">
        已选择{{ this.activeNum }}个项目组
      </el-col>
      <el-col :span="18" style="text-align: right;">
        <el-button @click.native="handleAdd" type="success">创建项目组</el-button>
        <el-button @click.native="handleDel" type="danger">删除项目组</el-button>
      </el-col>
    </el-row>
    <el-table :data="list" style="width: 100%"
              @selection-change="selectionChange"
              v-loading="loading" :border="true">
      <el-table-column type="selection" width="50"></el-table-column>
      <el-table-column property="name" label="名称"></el-table-column>
      <el-table-column property="createdTime" width="250" label="创建时间"></el-table-column>
      <el-table-column property="operatedTime" width="250" label="修改时间"></el-table-column>
      <el-table-column
        label="操作"
        width="250">
        <template v-slot="scope">
          <el-button @click="handleEdit(scope.row)"
                     type="text" size="medium">编辑</el-button>
          <el-button @click="handleMemberManage(scope.row)"
                     type="text" size="medium">成员管理</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import projectTeamApi from '@/api/team/projectTeam'
import eventHub from '@/utils/event-hub'

export default {
  name: 'projectTeamList',
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
        this.$common.showMsg('warning', '请选择项目组')
        return
      }
      const teamIds = this.selectItems.map(team => team.teamId)
      this.$common.confirm('是否确认删除')
        .then(() => projectTeamApi.deleteBatch(teamIds))
        .then(() => this.doQuery())
        .catch(error => this.$common.showNotifyError(error))
    },
    // 列表查询
    doQuery () {
      this.loading = true
      return projectTeamApi.getList()
        .then(data => {
          this.list = data
        })
        .catch(error => this.$common.showNotifyError(error))
        .finally(() => { this.loading = false })
    },
    handleAdd () {
      this.$router.push(`/team/add`)
    },
    handleEdit (row) {
      this.$router.push(`/team/edit/${row.teamId}`)
    },
    handleMemberManage (row) {
      this.$router.push(`/team/${row.teamId}/member`)
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

  .projectTeamList {
    .activeNum {
      min-width: 160px;
      text-align: left;
      padding: 0 0 0 20px;
    }

  }
</style>
