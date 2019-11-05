<template>
  <div class="entityFormDiv">
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/project' }">项目管理</el-breadcrumb-item>
      <el-breadcrumb-item :to="{ path: `/project/${this.projectId}/entity` }">实体管理</el-breadcrumb-item>
      <el-breadcrumb-item>
        {{edit?'编辑实体':'添加实体'}}
      </el-breadcrumb-item>
    </el-breadcrumb>
    <el-row type="flex" align="middle" :gutter="20">
      <el-col :span="12">
        <el-form ref="entityForm" class="entityForm" :rules="rules" :model="form" label-width="90px" size="small">
          <el-form-item label="项目" prop="projectId">
            <help-popover name="entity.projectId">
              <el-select v-model="form.projectId" style="width:100%;" filterable placeholder="请选择项目" :disabled="true">
                <el-option
                  v-for="item in projectList"
                  :key="item.projectId"
                  :label="item.projectDesc"
                  :value="item.projectId">
                </el-option>
              </el-select>
            </help-popover>
          </el-form-item>
          <el-form-item label="实体名" prop="title">
            <help-popover name="entity.title">
              <el-input v-model="form.title" placeholder="例如：用户"></el-input>
            </help-popover>
          </el-form-item>
          <el-form-item label="类名/表名" prop="classAndTableName">
            <help-popover name="entity.classAndTableName">
              <el-col :span="11" class="col-left">
                <el-input v-model="form.className" placeholder="java类名，例如：User"></el-input>
                <el-button size="mini" type="text" @click="form.className = $common.upperCaseFirst($common.camelCase(form.className))">转驼峰</el-button>
              </el-col>
              <el-col :span="2" style="padding-left: 0px;padding-right: 0px;text-align: center;">
                <el-tooltip class="item" effect="dark" content="粘贴到右边并转下划线" placement="top">
                  <el-button type="text" @click="copyClassNameToTableName()">
                    <i class="iconfont icon-double-right table-cell-icon color-primary" style="vertical-align: middle;"></i>
                  </el-button>
                </el-tooltip>
              </el-col>
              <el-col :span="11" class="col-right">
                <el-input v-model="form.tableName" placeholder="mysql表名，例如：t_user"></el-input>
                <el-button size="mini" type="text" @click="form.tableName = $common.snakeCase(form.tableName)">转下划线</el-button>
              </el-col>
            </help-popover>
          </el-form-item>
          <el-form-item label="描述" prop="desc">
            <help-popover name="entity.desc">
              <el-input v-model="form.desc" type="textarea" :autosize="{ minRows: 2, maxRows: 100}"></el-input>
            </help-popover>
          </el-form-item>
          <el-form-item label="分页" prop="pageSign">
            <help-popover name="entity.pageSign">
              <el-checkbox v-model="form.pageSign" :true-label="1" :false-label="0">
                支持分页
              </el-checkbox>
            </help-popover>
          </el-form-item>
          <el-form-item label="rest服务">
            <help-popover name="entity.feature">
              <el-checkbox v-model="form.feature.save" :true-label="1" :false-label="0">添加</el-checkbox>
              <el-checkbox v-model="form.feature.update" :true-label="1" :false-label="0">修改</el-checkbox>
              <el-checkbox v-model="form.feature.delete" :true-label="1" :false-label="0">单个删除</el-checkbox>
              <el-checkbox v-model="form.feature.deleteBatch" :true-label="1" :false-label="0">批量删除</el-checkbox>
              <el-checkbox v-model="form.feature.show" :true-label="1" :false-label="0">详情</el-checkbox>
              <el-checkbox v-model="form.feature.list" :true-label="1" :false-label="0">列表查询</el-checkbox>
            </help-popover>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="submit()">提交</el-button>
            <el-button v-if="edit" type="warning" @click="reset()">重置</el-button>
            <el-button @click="goBack(true)">返回</el-button>
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import options from '@/components/options'
import { apiPath } from '@/components/common'
import { initFormBean, getRules } from './model'
export default {
  name: 'entityForm',
  props: ['projectId', 'entityId'],
  data () {
    const edit = !!this.entityId
    return {
      edit: edit,
      boolOptions: options.boolOptions,
      projectList: [],
      old: initFormBean(edit),
      form: initFormBean(edit),
      rules: getRules(this)
    }
  },
  methods: {
    queryProject () {
      return this.$common.getProjectOptions()
        .then(response => this.$common.checkResult(response))
        .then(data => { this.projectList = data })
    },
    getEntity () {
      return this.$ajax.get(`/${apiPath}/meta_entity/${this.entityId}`)
        .then(response => this.$common.checkResult(response))
        .then(data => { this.old = data })
        .catch(error => this.$common.showNotifyError(error))
    },
    reset () {
      for (const key in initFormBean(true)) {
        this.form[key] = this.old[key]
      }
    },
    submit () {
      let loading = null
      // 校验表单
      this.$refs.entityForm.validate()
        // 提交表单
        .then(() => {
          loading = this.$loading()
          if (this.edit) {
            return this.$ajax.put(`/${apiPath}/meta_entity/update`, this.form)
          } else {
            return this.$ajax.post(`/${apiPath}/meta_entity/save`, this.form)
          }
        })
      // 校验返回结果
        .then(response => this.$common.checkResult(response))
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
        this.$router.push(`/project/${this.projectId}/entity`)
      }
    },
    copyClassNameToTableName () {
      this.form.tableName = this.$common.snakeCase(this.form.className)
      this.$refs.entityForm.validateField('classAndTableName')
    }
  },
  created () {
    if (this.edit) {
      Promise.all([this.getEntity(), this.queryProject()])
        .then(() => this.reset())
    } else {
      this.queryProject()
        .then(() => { this.form.projectId = parseInt(this.projectId) })
    }
  }
}
</script>

<style lang="scss">
  @import '../../assets/common.scss';
  .entityFormDiv .entityForm {
    @include youran-form;

  }

</style>
