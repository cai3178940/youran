<template>
  <div class="chartFormDiv">
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/project' }">项目管理</el-breadcrumb-item>
      <el-breadcrumb-item :to="{ path: `/project/${this.projectId}/chart` }">图表管理</el-breadcrumb-item>
      <el-breadcrumb-item>
        {{edit?'编辑':'添加'}}{{chartType.label}}
      </el-breadcrumb-item>
    </el-breadcrumb>
    <el-row type="flex" align="middle" :gutter="20">
      <el-col :span="16">
        <el-form ref="chartForm" class="chartForm"
                 :rules="rules" :model="form" label-width="120px"
                 size="small" v-loading="formLoading">
          <el-form-item label="实体" prop="entityId">
            <help-popover name="chartSource.entityId">
              <el-col :span="18" class="col-left">
                <el-select v-model="form.entityId" placeholder="请选择实体"
                           style="width:100%;" filterable>
                  <el-option
                    v-for="entity in entityOptions"
                    :key="entity.entityId"
                    :label="entity.title+'('+entity.tableName+')'"
                    :value="entity.entityId">
                  </el-option>
                </el-select>
              </el-col>
              <el-col :span="1" class="col-inner" style="text-align: center;">
                <span style="display:inline-block;font-size: 16px;color: #FA8072;padding: 7px 0px;">t0</span>
              </el-col>
              <el-col :span="5" class="col-right">
                <el-button size="small" type="text" @click="addJoin">+ 添加关联</el-button>
              </el-col>
            </help-popover>
          </el-form-item>
          <template v-for="(join,index) in form.joins">
            <el-form-item :key="index">
              <help-popover name="chartSource.joins">
                <el-col :span="6" class="col-left">
                  <el-select v-model="join.joinType" placeholder="关联方式"
                             style="width:100%;">
                    <el-option
                      v-for="option in joinTypeOptions"
                      :key="option.value"
                      :label="option.label"
                      :value="option.value">
                    </el-option>
                  </el-select>
                </el-col>
                <el-col :span="12" class="col-left">
                  <el-select v-model="form.entityId" placeholder="请选择"
                             style="width:100%;" filterable>
                    <el-option
                      v-for="entity in entityOptions"
                      :key="entity.entityId"
                      :label="entity.title+'('+entity.tableName+')'"
                      :value="entity.entityId">
                    </el-option>
                  </el-select>
                </el-col>
                <el-col :span="1" class="col-inner" style="text-align: center;">
                  <span style="display:inline-block;font-size: 16px;color: #FA8072;padding: 7px 0px;">t{{index+1}}</span>
                </el-col>
                <el-col :span="5" class="col-right">
                  <el-button size="small" type="text" @click="removeJoin">- 移除关联</el-button>
                </el-col>
              </help-popover>
            </el-form-item>
          </template>
          <el-form-item>
            <el-button type="primary" @click="submit()" tabindex="160">提交</el-button>
            <el-button @click="goBack()" tabindex="180">返回</el-button>
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>
  </div>
</template>

<script>
// import projectApi from '@/api/project'
// import chartApi from '@/api/chart'
import entityApi from '@/api/entity'
import options from '@/utils/options'
import { initSourceFormBean, initJoinDTO, getRules } from './model'

export default {
  name: 'chartForm',
  props: [
    'projectId',
    'chartTypeName',
    'chartId'
  ],
  data () {
    const edit = !!this.chartId
    return {
      edit: edit,
      constTypeOptions: options.constTypeOptions,
      joinTypeOptions: options.joinTypeOptions,
      entityOptions: [],
      mtmOptions: [],
      chartType: {
        value: 1,
        label: '',
        name: '',
        aggregation: false
      },
      formLoading: false,
      form: initSourceFormBean(edit),
      rules: getRules()
    }
  },
  methods: {
    initEntityOptions () {
      return entityApi.getList(this.projectId)
        .then(data => {
          this.entityOptions = data.map(entity => ({
            entityId: entity.entityId,
            title: entity.title,
            tableName: entity.tableName
          }))
        })
    },
    addJoin () {
      this.form.joins.push(initJoinDTO(this.form.joins.length))
    },
    submit () {
      let loading = null
      // 校验表单
      this.$refs.chartForm.validate()
        // 提交表单
        .then(() => {
          loading = this.$loading()
          return Promise.resolve()
        })
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
      this.$router.push(`/project/${this.projectId}/chart`)
    }
  },
  created () {
    this.chartType = options.chartTypeOptions.find(op => op.name === this.chartTypeName)
    this.form.aggregation = this.chartType.aggregation
    this.initEntityOptions()
  }
}
</script>

<style lang="scss">
  @import '../../assets/common.scss';
  .chartFormDiv .chartForm {
    @include youran-form;

  }

</style>
