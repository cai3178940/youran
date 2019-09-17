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
        <el-form ref="entityForm" class="entityForm" :rules="rules" :model="form" label-width="80px" size="small">
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
          <el-form-item label="类名" prop="className">
            <help-popover name="entity.className">
              <el-input v-model="form.className" placeholder="例如：User"></el-input>
            </help-popover>
          </el-form-item>
          <el-form-item label="表名" prop="tableName">
            <help-popover name="entity.tableName">
              <el-input v-model="form.tableName" placeholder="例如：t_user"></el-input>
            </help-popover>
          </el-form-item>
          <el-form-item label="分页" prop="pageSign">
            <help-popover name="entity.pageSign">
              <el-switch v-model="form.pageSign"
                         :active-value="1"
                         :inactive-value="0">
              </el-switch>
            </help-popover>
          </el-form-item>
          <el-form-item label="描述" prop="desc">
            <help-popover name="entity.desc">
              <el-input v-model="form.desc" type="textarea" :autosize="{ minRows: 2, maxRows: 100}"></el-input>
            </help-popover>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="submit()">提交</el-button>
            <el-button v-if="edit" type="warning" @click="reset()">重置</el-button>
            <el-button @click="goBack()">返回</el-button>
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
      rules: getRules()
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
          this.goBack()
        })
        .catch(error => this.$common.showNotifyError(error))
        .finally(() => {
          if (loading) {
            loading.close()
          }
        })
    },
    goBack () {
      this.$router.push(`/project/${this.projectId}/entity`)
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
