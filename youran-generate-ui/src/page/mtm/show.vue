<template>
  <div class="mtmShow">
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/project' }">项目管理</el-breadcrumb-item>
      <el-breadcrumb-item :to="{ path: `/project/${this.projectId}/mtm` }">多对多管理</el-breadcrumb-item>
      <el-breadcrumb-item>查看</el-breadcrumb-item>
    </el-breadcrumb>
    <el-row type="flex" align="middle" :gutter="20">
      <el-col :span="12">
        <el-form ref="showForm" class="showForm" :model="form" label-width="140px">
          <el-form-item label="项目" prop="projectId">
            <el-select v-model="form.projectId" style="width:100%;" filterable :disabled="true">
              <el-option
                v-for="item in projectList"
                :key="item.projectId"
                :label="item.projectName"
                :value="item.projectId">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="关联表名" prop="tableName">
            <el-input :disabled="true" v-model="form.tableName" placeholder="例如：r_user_role"></el-input>
          </el-form-item>
          <el-form-item label="描述" prop="desc">
            <el-input :disabled="true" v-model="form.desc" placeholder="例如：用户角色关联表" type="textarea" :rows="2"></el-input>
          </el-form-item>
          <el-form-item label="实体1" prop="entityId1">
            <el-select :disabled="true" v-model="form.entityId1" style="width:100%;" filterable placeholder="请选择实体1">
              <el-option
                v-for="item in entityList"
                :key="item.entityId"
                :label="item.title"
                :value="item.entityId">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="实体2" prop="entityId2">
            <el-select :disabled="true" v-model="form.entityId2" style="width:100%;" filterable placeholder="请选择实体2">
              <el-option
                v-for="item in entityList"
                :key="item.entityId"
                :label="item.title"
                :value="item.entityId">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="实体1持有引用" prop="holdRefer1">
            <el-radio-group :disabled="true" v-model="form.holdRefer1">
              <el-radio border v-for="item in boolOptions" :key="item.value" :label="item.value">{{item.label}}
              </el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="实体2持有引用" prop="holdRefer2">
            <el-radio-group :disabled="true" v-model="form.holdRefer2">
              <el-radio border v-for="item in boolOptions" :key="item.value" :label="item.value">{{item.label}}
              </el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item>
            <el-button @click="goBack()">返回</el-button>
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>
  </div>
</template>

<script>
  import options from '@/components/options.js'
  //多对多模型
  const mtmModel = {
    mtmId: null,
    projectId: null,
    tableName: '',
    desc: '',
    entityId1: null,
    entityId2: null,
    holdRefer1:1,
    holdRefer2:1
  }

  export default {
    name: 'mtmShow',
    props: ['projectId', 'mtmId'],
    data: function () {
      return {
        boolOptions: options.boolOptions,
        projectList: [],
        entityList:[],
        form: {
          ...mtmModel
        }
      }
    },
    methods: {
      queryProject: function () {
        return this.$common.getProjectOptions()
          .then(response => this.$common.checkResult(response.data))
          .then(result => this.projectList = result.data)
      },
      queryEntity: function (projectId) {
        return this.$common.getEntityOptions(projectId)
          .then(response => this.$common.checkResult(response.data))
          .then(result => this.entityList = result.data.entities)
      },
      getMtm: function () {
        return this.$ajax.get(`/generate/meta_mtm/${this.mtmId}`)
          .then(response => this.$common.checkResult(response.data))
          .then(result => this.form = result.data)
          .catch(error => this.$common.showNotifyError(error))
      },
      goBack: function () {
        this.$router.push(`/project/${this.projectId}/mtm`)
      }
    },
    created: function () {
      this.queryProject()
      this.queryEntity(this.projectId)
      this.getMtm()
    }
  }
</script>

<style>
  .mtmShow .showForm {
    padding: 30px 50px;
  }

</style>
