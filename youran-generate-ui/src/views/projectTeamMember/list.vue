<template>
  <div class="projectTeamMemberList">
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/team' }">项目组管理</el-breadcrumb-item>
      <el-breadcrumb-item>成员管理</el-breadcrumb-item>
    </el-breadcrumb>
    <el-row type="flex" align="middle" :gutter="20" style="padding:10px 0;">
      <el-col :span="6" class="activeNum">
        已选择{{ this.activeNum }}个成员
      </el-col>
      <el-col :span="18" style="text-align: right;">
        <el-button @click.native="handleAdd" type="success">添加成员</el-button>
        <el-button @click.native="handleDel" type="danger">移除成员</el-button>
      </el-col>
    </el-row>
    <el-table :data="list" style="width: 100%"
              @selection-change="selectionChange"
              v-loading="loading" :border="true">
      <el-table-column type="selection" width="50"></el-table-column>
      <el-table-column property="name" label="用户名"></el-table-column>
      <el-table-column property="createdTime" width="180" label="添加时间"></el-table-column>
    </el-table>
  </div>
</template>

<script>
import projectTeamMemberApi from '@/api/team/projectTeamMember'

export default {
  name: 'projectTeamMemberList',
  props: ['teamId'],
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
        this.$common.showMsg('warning', '请选择成员')
        return
      }
      const ids = this.selectItems.map(obj => obj.id)
      this.$common.confirm('是否确认移除')
        .then(() => projectTeamMemberApi.deleteBatch(ids))
        .then(() => this.doQuery())
        .catch(error => this.$common.showNotifyError(error))
    },
    // 列表查询
    doQuery () {
      this.loading = true
      return projectTeamMemberApi.getList(this.teamId)
        .then(data => {
          this.list = data
        })
        .catch(error => this.$common.showNotifyError(error))
        .finally(() => { this.loading = false })
    },
    handleAdd () {
      // todo
    }
  },
  activated () {
    this.doQuery()
  }
}
</script>
<style lang="scss">
  @import '../../assets/common.scss';

  .projectTeamMemberList {
    .activeNum {
      min-width: 160px;
      text-align: left;
      padding: 0 0 0 20px;
    }

  }
</style>
