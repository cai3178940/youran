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
      <el-table-column property="username" label="用户名"></el-table-column>
      <el-table-column property="createdTime" width="280" label="添加时间"></el-table-column>
    </el-table>

    <!-- 添加成员对话框 -->
    <el-dialog title="批量添加成员" :visible.sync="addFormVisible" width="40%">
      <el-form ref="addForm" :model="addForm" size="small">
        <el-form-item label="成员账号：" label-width="100px">
          <el-input
            placeholder="多个账号用英文逗号分隔"
            v-model="addForm.username"
            type="textarea"
            :autosize="{ minRows: 5, maxRows: 20}">
          </el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="addFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="handleAddSubmit">提 交</el-button>
      </div>
    </el-dialog>
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
      loading: false,
      addForm: {
        teamId: null,
        username: ''
      },
      addFormVisible: false
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
      this.addFormVisible = true
      this.addForm.teamId = this.teamId
      this.addForm.username = ''
    },
    handleAddSubmit () {
      let loading = null
      // 校验表单
      this.$refs.addForm.validate()
        // 提交表单
        .then(() => {
          loading = this.$loading()
          return projectTeamMemberApi.save(this.addForm)
        })
        .then(() => {
          this.$common.showMsg('success', '添加成功')
          this.addFormVisible = false
          this.doQuery()
        })
        .catch(error => this.$common.showNotifyError(error))
        .finally(() => {
          if (loading) {
            loading.close()
          }
        })
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
