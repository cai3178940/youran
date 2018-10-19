<template>
  <div class="entityEdit">
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/project' }">项目管理</el-breadcrumb-item>
      <el-breadcrumb-item :to="{ path: `/project/${this.projectId}/entity` }">实体管理</el-breadcrumb-item>
      <el-breadcrumb-item>编辑实体</el-breadcrumb-item>
    </el-breadcrumb>
    <el-row type="flex" align="middle" :gutter="20">
      <el-col :span="12">
        <el-form ref="editForm" class="editForm" :rules="rules" :model="form" label-width="80px">
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
              <el-radio-group v-model="form.pageSign">
                <el-radio border v-for="item in boolOptions" :key="item.value" :label="item.value">{{item.label}}</el-radio>
              </el-radio-group>
            </help-popover>
          </el-form-item>
          <el-form-item label="描述" prop="desc">
            <help-popover name="entity.desc">
              <el-input v-model="form.desc" type="textarea" :rows="2"></el-input>
            </help-popover>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="submit()">提交</el-button>
            <el-button type="warning" @click="reset()">重置</el-button>
            <el-button @click="goBack()">返回</el-button>
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>
  </div>
</template>

<script>
  import options from '@/components/options.js'
  import {initFormBean, getRules} from './model'
  export default {
    name: 'entityEdit',
    props: ['projectId', 'entityId'],
    data: function () {
      return {
        boolOptions: options.boolOptions,
        projectList: [],
        old: initFormBean(true),
        form: initFormBean(true),
        rules: getRules()
      }
    },
    methods: {
      queryProject: function () {
        return this.$common.getProjectOptions()
          .then(response => this.$common.checkResult(response.data))
          .then(result => { this.projectList = result.data })
      },
      getEntity: function () {
        return this.$ajax.get(`/generate/meta_entity/${this.entityId}`)
          .then(response => this.$common.checkResult(response.data))
          .then(result => { this.old = result.data })
          .catch(error => this.$common.showNotifyError(error))
      },
      reset: function () {
        for (const key in initFormBean(true)) {
          this.form[key] = this.old[key]
        }
      },
      submit: function () {
        var loading = null
        // 校验表单
        this.$refs.editForm.validate()
        // 提交表单
          .then(() => {
            loading = this.$loading()
            return this.$ajax.put('/generate/meta_entity/update', this.form)
          })
          // 校验返回结果
          .then(response => this.$common.checkResult(response.data))
          // 执行页面跳转
          .then(() => {
            this.$common.showMsg('success', '修改成功')
            this.goBack()
          })
          .catch(error => this.$common.showNotifyError(error))
          .finally(() => {
            if (loading) {
              loading.close()
            }
          })
      },
      goBack: function () {
        this.$router.push(`/project/${this.projectId}/entity`)
      }
    },
    created: function () {
      Promise.all([this.getEntity(), this.queryProject()])
        .then(() => this.reset())
    }
  }
</script>

<style>
  .entityEdit .editForm {
    padding: 30px 50px;
  }

</style>
