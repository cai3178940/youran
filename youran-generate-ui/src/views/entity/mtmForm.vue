<template>
  <div class="mtmFormDiv">
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/project' }">项目管理</el-breadcrumb-item>
      <el-breadcrumb-item :to="{ path: `/project/${this.projectId}/entity` }">实体管理</el-breadcrumb-item>
      <el-breadcrumb-item>
        {{edit?'编辑多对多':'添加多对多'}}
      </el-breadcrumb-item>
    </el-breadcrumb>
    <el-row type="flex" align="middle" :gutter="20">
      <el-col :span="12">
        <el-form ref="mtmForm" class="mtmForm" :rules="rules" :model="form" label-width="140px">
          <el-form-item label="项目" prop="projectId">
            <help-popover name="mtm.projectId">
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
              <el-col :span="12" style="padding-left: 0px;">
                <el-select v-model="form.entityId1" style="width:100%;" filterable placeholder="请选择实体1">
                  <el-option
                    v-for="item in entityList"
                    :key="item.entityId"
                    :label="item.title"
                    :value="item.entityId">
                  </el-option>
                </el-select>
              </el-col>
              <el-col :span="12" style="padding-right: 0px;">
                <el-radio-group v-model="form.holdRefer1" size="medium">
                  <el-radio-button :key="1" :label="1">持有引用</el-radio-button>
                  <el-radio-button :key="0" :label="0">不持有</el-radio-button>
                </el-radio-group>
              </el-col>
            </help-popover>
          </el-form-item>
          <el-form-item label="实体2" prop="entityId2">
            <help-popover name="mtm.entityId2">
              <el-col :span="12" style="padding-left: 0px;">
                <el-select v-model="form.entityId2" style="width:100%;" filterable placeholder="请选择实体2">
                  <el-option
                    v-for="item in entityList"
                    :key="item.entityId"
                    :label="item.title"
                    :value="item.entityId">
                  </el-option>
                </el-select>
              </el-col>
              <el-col :span="12" style="padding-right: 0px;">
                <el-radio-group v-model="form.holdRefer2" size="medium">
                  <el-radio-button :key="1" :label="1">持有引用</el-radio-button>
                  <el-radio-button :key="0" :label="0">不持有</el-radio-button>
                </el-radio-group>
              </el-col>
            </help-popover>
          </el-form-item>
          <el-form-item label="实体1外键字段" prop="entityIdField1">
            <help-popover name="mtm.entityIdField1">
              <el-input v-model="form.entityIdField1" placeholder="默认自动生成"></el-input>
            </help-popover>
          </el-form-item>
          <el-form-item label="实体2外键字段" prop="entityIdField2">
            <help-popover name="mtm.entityIdField2">
              <el-input v-model="form.entityIdField2" placeholder="默认自动生成"></el-input>
            </help-popover>
          </el-form-item>
          <el-form-item label="自增id" prop="needId">
            <help-popover name="mtm.needId">
              <el-col :span="12" style="padding-left: 0px;">
                <el-radio-group v-model="form.needId" size="medium">
                  <el-radio-button :key="true" :label="true">需要</el-radio-button>
                  <el-radio-button :key="false" :label="false">不需要</el-radio-button>
                </el-radio-group>
              </el-col>
              <el-col  :span="12" style="padding-right: 0px;">
                <el-radio-group :disabled="!form.needId" v-model="form.bigId" size="medium">
                  <el-radio-button :key="false" :label="false">整型</el-radio-button>
                  <el-radio-button :key="true" :label="true">长整型</el-radio-button>
                </el-radio-group>
              </el-col>
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
import { initMtmFormBean, getMtmRules } from './model'

export default {
  name: 'mtmForm',
  props: ['projectId', 'mtmId', 'entityIds'],
  data () {
    const edit = !!this.mtmId
    return {
      edit: edit,
      boolOptions: options.boolOptions,
      projectList: [],
      entityList: [],
      old: initMtmFormBean(edit),
      form: initMtmFormBean(edit),
      rules: getMtmRules()
    }
  },
  methods: {
    queryProject () {
      return this.$common.getProjectOptions()
        .then(response => this.$common.checkResult(response))
        .then(data => { this.projectList = data })
    },
    queryEntity (projectId) {
      return this.$common.getEntityOptions(projectId)
        .then(response => this.$common.checkResult(response))
        .then(data => { this.entityList = data })
    },
    getMtm () {
      return this.$ajax.get(`/${apiPath}/meta_mtm/${this.mtmId}`)
        .then(response => this.$common.checkResult(response))
        .then(data => { this.old = data })
        .catch(error => this.$common.showNotifyError(error))
    },
    reset () {
      for (const key in initMtmFormBean(true)) {
        this.form[key] = this.old[key]
      }
    },
    submit () {
      let loading = null
      // 校验表单
      this.$refs.mtmForm.validate()
        // 提交表单
        .then(() => {
          loading = this.$loading()
          if (this.edit) {
            return this.$ajax.put(`/${apiPath}/meta_mtm/update`, this.form)
          } else {
            return this.$ajax.post(`/${apiPath}/meta_mtm/save`, this.form)
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
      Promise.all([this.getMtm(), this.queryProject(), this.queryEntity(this.projectId)])
        .then(() => this.reset())
    } else {
      this.form.projectId = parseInt(this.projectId)
      if (this.entityIds) {
        const array = this.entityIds.split('-').map(value => parseInt(value))
        this.form.entityId1 = array[0]
        this.form.entityId2 = array[1]
      }
      this.queryProject()
        .then(() => this.queryEntity(this.form.projectId))
    }
  }
}
</script>

<style lang="scss">
  @import '../../assets/common.scss';
  .mtmFormDiv .mtmForm {
    @include youran-form;
    .el-radio-button__inner {
      padding: 10px 10px;
    }
  }

</style>
