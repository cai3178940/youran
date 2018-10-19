<template>
  <div class="mtmEdit">
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/project' }">项目管理</el-breadcrumb-item>
      <el-breadcrumb-item :to="{ path: `/project/${this.projectId}/entity` }">实体管理</el-breadcrumb-item>
      <el-breadcrumb-item>编辑多对多</el-breadcrumb-item>
    </el-breadcrumb>
    <el-row type="flex" align="middle" :gutter="20">
      <el-col :span="12">
        <el-form ref="editForm" class="editForm" :rules="rules" :model="form" label-width="140px">
          <el-form-item label="项目" prop="projectId">
            <help-popover name="mtm.projectId">
              <el-select v-model="form.projectId" style="width:100%;" filterable placeholder="请选择项目" :disabled="true">
                <el-option
                  v-for="item in projectList"
                  :key="item.projectId"
                  :label="item.projectName"
                  :value="item.projectId">
                </el-option>
              </el-select>
            </help-popover>
          </el-form-item>
          <el-form-item label="关联表名" prop="tableName">
            <help-popover name="mtm.tableName">
              <el-input v-model="form.tableName" placeholder="例如：r_user_role"></el-input>
            </help-popover>
          </el-form-item>
          <el-form-item label="描述" prop="desc">
            <help-popover name="mtm.desc">
              <el-input v-model="form.desc" placeholder="例如：用户角色关联表" type="textarea" :rows="2"></el-input>
            </help-popover>
          </el-form-item>
          <el-form-item label="实体1" prop="entityId1">
            <help-popover name="mtm.entityId1">
              <el-select v-model="form.entityId1" style="width:100%;" filterable placeholder="请选择实体1">
                <el-option
                  v-for="item in entityList"
                  :key="item.entityId"
                  :label="item.title"
                  :value="item.entityId">
                </el-option>
              </el-select>
            </help-popover>
          </el-form-item>
          <el-form-item label="实体2" prop="entityId2">
            <help-popover name="mtm.entityId2">
              <el-select v-model="form.entityId2" style="width:100%;" filterable placeholder="请选择实体2">
                <el-option
                  v-for="item in entityList"
                  :key="item.entityId"
                  :label="item.title"
                  :value="item.entityId">
                </el-option>
              </el-select>
            </help-popover>
          </el-form-item>
          <el-form-item label="实体1持有引用" prop="holdRefer1">
            <help-popover name="mtm.holdRefer1">
              <el-radio-group v-model="form.holdRefer1">
                <el-radio border v-for="item in boolOptions" :key="item.value" :label="item.value">{{item.label}}
                </el-radio>
              </el-radio-group>
            </help-popover>
          </el-form-item>
          <el-form-item label="实体2持有引用" prop="holdRefer2">
            <help-popover name="mtm.holdRefer2">
              <el-radio-group v-model="form.holdRefer2">
                <el-radio border v-for="item in boolOptions" :key="item.value" :label="item.value">{{item.label}}
                </el-radio>
              </el-radio-group>
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
  import {initMtmFormBean, getMtmRules} from './model'

  export default {
    name: 'mtmEdit',
    props: ['projectId', 'mtmId'],
    data: function () {
      return {
        boolOptions: options.boolOptions,
        projectList: [],
        entityList: [],
        old: initMtmFormBean(true),
        form: initMtmFormBean(true),
        rules: getMtmRules()
      }
    },
    methods: {
      queryProject: function () {
        return this.$common.getProjectOptions()
          .then(response => this.$common.checkResult(response.data))
          .then(result => { this.projectList = result.data })
      },
      queryEntity: function (projectId) {
        return this.$common.getEntityOptions(projectId)
          .then(response => this.$common.checkResult(response.data))
          .then(result => { this.entityList = result.data.entities })
      },
      getMtm: function () {
        return this.$ajax.get(`/generate/meta_mtm/${this.mtmId}`)
          .then(response => this.$common.checkResult(response.data))
          .then(result => { this.old = result.data })
          .catch(error => this.$common.showNotifyError(error))
      },
      reset: function () {
        for (const key in initMtmFormBean(true)) {
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
            return this.$ajax.put('/generate/meta_mtm/update', this.form)
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
      Promise.all([this.getMtm(), this.queryProject(), this.queryEntity(this.projectId)])
        .then(() => this.reset())
    }
  }
</script>

<style>
  .mtmEdit .editForm {
    padding: 30px 50px;
  }

</style>
