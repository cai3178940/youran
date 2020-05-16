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
                <el-select v-model="form.entity" placeholder="请选择实体"
                           style="width:100%;" @change="handleEntityChange" filterable>
                  <el-option
                    v-for="entity in entityOptions"
                    :key="entity.entityId"
                    :label="entity.title+'('+entity.tableName+')'"
                    :value="entity">
                  </el-option>
                </el-select>
              </el-col>
              <el-col :span="1" class="col-inner" style="text-align: center;">
                <span class="text-in-form">t0</span>
              </el-col>
              <el-col :span="5" class="col-right">
                <el-button size="small" type="text" @click="addJoin">+ 添加关联</el-button>
              </el-col>
            </help-popover>
          </el-form-item>
          <template v-for="(join,index) in form.joins">
            <el-form-item :key="'a'+index">
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
                  <el-select v-model="join.entityId" placeholder="请选择"
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
                  <span class="text-in-form">t{{index+1}}</span>
                </el-col>
                <el-col :span="5" class="col-right">
                  <el-button size="small" type="text" @click="removeJoin(index)">- 移除关联</el-button>
                </el-col>
              </help-popover>
            </el-form-item>
            <el-form-item :key="'b'+index">
              <help-popover name="chartSource.joins">
                <el-col :span="2" class="col-left" style="text-align: center;">
                  <span class="text-in-form" style="color:blueviolet;">on</span>
                </el-col>
                <el-col :span="8" class="col-inner">
                  <el-select v-model="join.entityId" placeholder="请选择字段"
                             style="width:100%;" filterable>
                    <span class="text-in-form" slot="prefix">t0</span>
                    <!--所有上面的实体-->
                    <el-option-group
                      v-for="group in options"
                      :key="group.label"
                      :label="group.label">
                      <el-option
                        v-for="item in group.options"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value">
                      </el-option>
                    </el-option-group>
                    <!--所有上面的多对多-->
                    <el-option-group
                      v-for="group in options"
                      :key="group.label"
                      :label="group.label">
                      <el-option
                        v-for="item in group.options"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value">
                      </el-option>
                    </el-option-group>
                  </el-select>
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
import fieldApi from '@/api/field'
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
            tableName: entity.tableName,
            fieldList: []
          }))
        })
    },
    handleEntityChange (entity) {
      this.form.entityId = entity.entityId
      this.loadEntityFields(entity)
    },
    loadEntityFields (entity) {
      if (entity.fieldList.length) {
        return
      }
      return fieldApi.getList(entity.entityId, false)
        .then(data => {
          entity.fieldList = data
        })
    },
    /**
     * 获取某个关联之前的关联实体
     */
    getJoinEntitiesAbove (index) {

    },
    /**
     * 获取某个关联之前的关联多对多
     */
    getJoinMtmsAbove (index) {

    },
    addJoin () {
      this.form.joins.push(initJoinDTO(this.form.joins.length))
    },
    removeJoin (index) {
      this.form.joins.splice(index, 1)
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

    .text-in-form {
      display:inline-block;
      font-size: 16px;
      color: #FA8072;
      padding: 7px 0px;
    }
  }

</style>
