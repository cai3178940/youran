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
        <el-form ref="mtmForm" class="mtmForm" :rules="rules" :model="form" label-width="140px" size="small">
          <el-form-item label="项目" prop="projectId">
            <help-popover name="mtm.projectId">
              <el-select v-model="form.projectId" class="red-font"
                         style="width:100%;" filterable placeholder="请选择项目" :disabled="true">
                <el-option
                  v-for="item in projectList"
                  :key="item.projectId"
                  :label="item.projectDesc"
                  :value="item.projectId">
                </el-option>
              </el-select>
            </help-popover>
          </el-form-item>
          <el-form-item label="实体1" prop="entityId1">
            <help-popover name="mtm.entityId1">
              <el-col :span="12" style="padding-left: 0px;">
                <el-select v-model="form.entityId1" class="red-font"
                           style="width:100%;" filterable placeholder="请选择实体1" :disabled="true">
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
                  <el-radio-button :key="1" :label="true">持有引用</el-radio-button>
                  <el-radio-button :key="0" :label="false">不持有</el-radio-button>
                </el-radio-group>
              </el-col>
            </help-popover>
          </el-form-item>
          <el-form-item label="实体2" prop="entityId2">
            <help-popover name="mtm.entityId2">
              <el-col :span="12" style="padding-left: 0px;">
                <el-select v-model="form.entityId2" class="red-font"
                           style="width:100%;" filterable placeholder="请选择实体2" :disabled="true">
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
                  <el-radio-button :key="1" :label="true">持有引用</el-radio-button>
                  <el-radio-button :key="0" :label="false">不持有</el-radio-button>
                </el-radio-group>
              </el-col>
            </help-popover>
          </el-form-item>
          <el-form-item label="关联表名" prop="tableName">
            <help-popover name="mtm.tableName">
              <el-input v-model="form.tableName" placeholder="例如：r_user_role"
                        tabindex="20"></el-input>
            </help-popover>
          </el-form-item>
          <el-form-item label="描述" prop="desc">
            <help-popover name="mtm.desc">
              <el-input v-model="form.desc" placeholder="例如：用户角色关联表"
                        type="textarea" :rows="2" tabindex="30"></el-input>
            </help-popover>
          </el-form-item>
          <el-form-item v-if="form.holdRefer1" label="实体1功能">
            <help-popover name="mtm.feature">
              <el-checkbox v-model="form.feature.f1.withinEntity">随实体一起维护</el-checkbox>
              <el-checkbox v-model="form.feature.f1.set">设置关联</el-checkbox>
              <el-checkbox v-model="form.feature.f1.addRemove">添加+移除</el-checkbox>
            </help-popover>
          </el-form-item>
          <el-form-item v-if="form.holdRefer2" label="实体2功能">
            <help-popover name="mtm.feature">
              <el-checkbox v-model="form.feature.f2.withinEntity">随实体一起维护</el-checkbox>
              <el-checkbox v-model="form.feature.f2.set">设置关联</el-checkbox>
              <el-checkbox v-model="form.feature.f2.addRemove">添加+移除</el-checkbox>
            </help-popover>
          </el-form-item>
          <el-form-item label="实体1外键字段" prop="entityIdField1">
            <help-popover name="mtm.entityIdField1">
              <el-input v-model="form.entityIdField1"
                        :placeholder="'例如：' + entityIdFieldPlaceholder1" tabindex="40"></el-input>
            </help-popover>
          </el-form-item>
          <el-form-item label="实体2外键字段" prop="entityIdField2">
            <help-popover name="mtm.entityIdField2">
              <el-input v-model="form.entityIdField2"
                        :placeholder="'例如：' + entityIdFieldPlaceholder2" tabindex="50"></el-input>
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
            <el-button @click="goBack(true)">返回</el-button>
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import projectApi from '@/api/project'
import entityApi from '@/api/entity'
import mtmApi from '@/api/mtm'
import { initMtmFormBean, getMtmRules } from './model'

export default {
  name: 'mtmForm',
  props: ['projectId', 'mtmId', 'entityIds'],
  data () {
    const edit = !!this.mtmId
    return {
      edit: edit,
      projectList: [],
      entityList: [],
      old: initMtmFormBean(edit),
      form: initMtmFormBean(edit),
      rules: getMtmRules(),
      entityIdFieldPlaceholder1: 'id_1',
      entityIdFieldPlaceholder2: 'id_2'
    }
  },
  methods: {
    queryProject () {
      return projectApi.getList()
        .then(data => { this.projectList = data })
    },
    queryEntity (projectId) {
      return entityApi.getList(projectId)
        .then(data => { this.entityList = data })
    },
    getMtm () {
      return mtmApi.get(this.mtmId)
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
          return mtmApi.saveOrUpdate(this.form, this.edit)
        })
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
    }
  },
  created () {
    if (this.edit) {
      Promise.all([this.getMtm(), this.queryProject(), this.queryEntity(this.projectId)])
        .then(() => this.reset())
        .then(() => {
          this.entityIdFieldPlaceholder1 = this.old.entityIdField1
          this.entityIdFieldPlaceholder2 = this.old.entityIdField2
        })
    } else {
      this.form.projectId = parseInt(this.projectId)
      if (this.entityIds) {
        const array = this.entityIds.split('-').map(value => parseInt(value))
        this.form.entityId1 = array[0]
        this.form.entityId2 = array[1]
        // 设置外键字段默认值
        entityApi.getDefaultFkFieldNameForSql(array[0])
          .then(data => {
            this.form.entityIdField1 = data
            this.entityIdFieldPlaceholder1 = data
          })
        entityApi.getDefaultFkFieldNameForSql(array[1])
          .then(data => {
            this.form.entityIdField2 = data
            this.entityIdFieldPlaceholder2 = data
          })
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
