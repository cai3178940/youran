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
          <!-- 实体： entity_0 t0 添加关联 -->
          <el-form-item label="实体" prop="entityId">
            <help-popover name="chartSource.entityId">
              <!-- 实体： entity_0 -->
              <el-col :span="18" class="col-left">
                <el-select v-model="form.entityId" placeholder="请选择实体"
                           style="width:100%;" @change="handleEntityChange" filterable>
                  <el-option
                    v-for="entity in entityOptions"
                    :key="entity.entityId"
                    :label="entity.title+'('+entity.tableName+')'"
                    :value="entity.entityId">
                  </el-option>
                </el-select>
              </el-col>
              <!-- 实体： t0 -->
              <el-col :span="1" class="col-inner" style="text-align: center;">
                <span class="text-in-form">t0</span>
              </el-col>
              <!-- 实体： 添加关联 -->
              <el-col :span="5" class="col-right">
                <el-button size="small" type="text" @click="addJoin">+ 添加关联</el-button>
              </el-col>
            </help-popover>
          </el-form-item>
          <template v-for="(join,index) in form.joins">
            <!-- 关联(表)： inner join entity_1 t1 移除关联 -->
            <el-form-item :key="'a'+index">
              <help-popover name="chartSource.joins">
                <!-- 关联(join方式)： inner join -->
                <el-col :span="6" class="col-left">
                  <el-select v-model="form.joins[index].joinType" placeholder="关联方式"
                             style="width:100%;">
                    <el-option
                      v-for="option in joinTypeOptions"
                      :key="option.value"
                      :label="option.label"
                      :value="option.value">
                    </el-option>
                  </el-select>
                </el-col>
                <!-- 关联(右边实体/多对多)： entity_1 -->
                <el-col :span="12" class="col-left">
                  <el-select v-model="form.joins[index].right.tmp1" placeholder="请选择"
                             style="width:100%;" @change="fillTmp1ToPart(form.joins[index].right)"
                             value-key="key" filterable>
                    <el-option-group label="实体">
                      <el-option
                        v-for="entity in entityOptions"
                        :key="entity.entityId"
                        :label="entity.title+'('+entity.tableName+')'"
                        :value="{
                          key: 'entity_'+entity.entityId,
                          joinPartType: 'entity',
                          joinIndex: index+1,
                          obj: entity
                        }">
                      </el-option>
                    </el-option-group>
                    <el-option-group label="多对多">
                      <el-option
                        v-for="mtm in mtmOptions"
                        :key="mtm.mtmId"
                        :label="mtm.tableName"
                        :value="{
                          key: 'mtm_'+mtm.mtmId,
                          joinPartType: 'mtm',
                          joinIndex: index+1,
                          obj: mtm
                        }">
                      </el-option>
                    </el-option-group>
                  </el-select>
                </el-col>
                <!-- 关联(右边别名)： t1 -->
                <el-col :span="1" class="col-inner" style="text-align: center;">
                  <span class="text-in-form">t{{index+1}}</span>
                </el-col>
                <!-- 关联(操作按钮)： 移除关联 -->
                <el-col :span="5" class="col-right">
                  <el-button size="small" type="text" @click="removeJoin(index)">- 移除关联</el-button>
                </el-col>
              </help-popover>
            </el-form-item>
            <!-- 关联： on t0.xx_id = t1.xx_id -->
            <el-form-item :key="'b'+index">
              <help-popover name="chartSource.joins">
                <!-- 关联： on  -->
                <el-col :span="2" class="col-left" style="text-align: center;">
                  <span class="text-in-form" style="color:blueviolet;">on</span>
                </el-col>
                <!-- 关联(左边字段)： t0.xx_id  -->
                <el-col :span="8" class="col-inner">
                  <el-select v-model="form.joins[index].left.tmp2" placeholder="请选择字段"
                             style="width:100%;" value-key="key"
                             @change="fillTmp2ToPart(form.joins[index].left)" filterable>
                    <span class="text-in-form" slot="prefix">
                      t{{form.joins[index].left.joinIndex}}
                    </span>
                    <!--所有上面的实体-->
                    <el-option-group
                      v-for="([joinIndex,entity]) in getJoinEntitiesAbove(index)"
                      :key="'entity_'+entity.entityId"
                      :label="entity.title+'('+entity.tableName+')'">
                      <el-option
                        v-for="field in entity.fieldList"
                        :key="field.fieldId"
                        :label="field.fieldDesc+'('+field.fieldName+')'"
                        :value="{
                          key: 'entity_'+entity.entityId+'_'+field.fieldId,
                          joinPartType: 'entity',
                          joinIndex: joinIndex,
                          obj: entity,
                          field: field
                        }">
                      </el-option>
                    </el-option-group>
                    <!--所有上面的多对多-->
                    <el-option-group
                      v-for="([joinIndex,mtm]) in getJoinMtmsAbove(index)"
                      :key="'mtm_'+mtm.mtmId"
                      :label="'多对多('+mtm.tableName+')'">
                      <el-option
                        :label="mtm.entityIdField1"
                        :value="{
                          key: 'mtm_'+mtm.mtmId+'_'+mtm.entityIdField1,
                          joinPartType: 'mtm',
                          joinIndex: joinIndex,
                          obj: mtm,
                          field: mtm.entityIdField1
                        }">
                      </el-option>
                      <el-option
                        :label="mtm.entityIdField2"
                        :value="{
                          key: 'mtm_'+mtm.mtmId+'_'+mtm.entityIdField2,
                          joinPartType: 'mtm',
                          joinIndex: joinIndex,
                          obj: mtm,
                          field: mtm.entityIdField2
                        }">
                      </el-option>
                    </el-option-group>
                  </el-select>
                </el-col>
                <!-- 关联(中间符号)： =  -->
                <el-col :span="2" class="col-inner" style="text-align: center;">
                  <span class="text-in-form" style="color:blueviolet;">=</span>
                </el-col>
                <!-- 关联(右边字段)： t1.xx_id  -->
                <el-col :span="8" class="col-inner">
                  <el-select v-model="form.joins[index].right.tmp2" placeholder="请选择字段"
                             style="width:100%;" value-key="key"
                             @change="fillTmp2ToPart(form.joins[index].right)" filterable>
                    <span class="text-in-form" slot="prefix">
                      t{{form.joins[index].right.joinIndex}}
                    </span>
                    <!--如果右边是实体-->
                    <template v-if="form.joins[index].right.joinPartType==='entity'">
                      <el-option
                        v-for="field in form.joins[index].right.entity.fieldList"
                        :key="field.fieldId"
                        :label="field.fieldDesc+'('+field.fieldName+')'"
                        :value="{
                          key: 'entity_'+form.joins[index].right.entity.entityId+'_'+field.fieldId,
                          joinPartType: 'entity',
                          joinIndex: index+1,
                          obj: form.joins[index].right.entity,
                          field: field
                        }">
                      </el-option>
                    </template>
                    <!--如果右边是多对多-->
                    <template v-if="form.joins[index].right.joinPartType==='mtm'">
                      <el-option
                        :label="form.joins[index].right.mtm.entityIdField1"
                        :value="{
                          key: 'mtm_'+form.joins[index].right.mtm.mtmId+'_'+form.joins[index].right.mtm.entityIdField1,
                          joinPartType: 'mtm',
                          joinIndex: index+1,
                          obj: form.joins[index].right.mtm,
                          field: form.joins[index].right.mtm.entityIdField1
                        }">
                      </el-option>
                      <el-option
                        :label="form.joins[index].right.mtm.entityIdField2"
                        :value="{
                          key: 'mtm_'+form.joins[index].right.mtm.mtmId+'_'+form.joins[index].right.mtm.entityIdField2,
                          joinPartType: 'mtm',
                          joinIndex: index+1,
                          obj: form.joins[index].right.mtm,
                          field: form.joins[index].right.mtm.entityIdField2
                        }">
                      </el-option>
                    </template>
                  </el-select>
                </el-col>
              </help-popover>
            </el-form-item>
          </template>
          <!-- 明细列：多选下拉框 -->
          <el-form-item label="明细列" prop="detailColumnList">
            <help-popover name="chartSource.detailColumnList">
              <el-select v-model="form.detailColumnList" value-key="key"
                         style="width:100%;" placeholder="请选择明细列"
                         multiple filterable>
                <el-option-group
                  v-for="([joinIndex,entity]) in detailColumnOptions"
                  :key="joinIndex"
                  :label="entity.title+'('+entity.tableName+')'">
                  <el-option
                    v-for="field in entity.fieldList"
                    :key="field.fieldId"
                    :label="field.fieldDesc+'('+field.fieldName+')'"
                    :value="{
                          key: 'common_'+joinIndex+'_'+field.fieldId,
                          fieldId: field.fieldId,
                          custom: false,
                          joinIndex: joinIndex
                        }">
                  </el-option>
                </el-option-group>
              </el-select>
            </help-popover>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="submit()" tabindex="160">保存并下一步</el-button>
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
import mtmApi from '@/api/mtm'
import fieldApi from '@/api/field'
import options from '@/utils/options'
import {
  initSourceFormBean,
  initJoinDTO,
  repairAtJoinChange,
  repairAtJoinRemove,
  getRules
} from './model'

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
  computed: {
    /**
     * 明细列选项
     */
    detailColumnOptions () {
      const options = []
      if (this.form.entity) {
        options.push([0, this.form.entity])
      }
      this.form.joins.forEach(join => {
        if (join.right.entity) {
          options.push([join.right.joinIndex, join.right.entity])
        }
      })
      return options
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
    initMtmOptions () {
      return mtmApi.getList(this.projectId)
        .then(data => {
          this.mtmOptions = data
        })
    },
    handleEntityChange (entityId) {
      this.form.entity = this.entityOptions.find(value => value.entityId === entityId)
      this.loadEntityFields(this.form.entity)
      repairAtJoinChange(this.form)
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
      const pairs = []
      if (this.form.entity) {
        pairs.push([0, this.form.entity])
      }
      for (let i = 0; i < index; i++) {
        if (this.form.joins.length > i) {
          const joinEntity = this.form.joins[i].right.entity
          if (joinEntity) {
            pairs.push([i + 1, joinEntity])
          }
        }
      }
      return pairs
    },
    /**
     * 获取某个关联之前的关联多对多
     */
    getJoinMtmsAbove (index) {
      const pairs = []
      for (let i = 0; i < index; i++) {
        if (this.form.joins.length > i) {
          const joinMtm = this.form.joins[i].right.mtm
          if (joinMtm) {
            pairs.push([i + 1, joinMtm])
          }
        }
      }
      return pairs
    },
    fillTmp1ToPart (part) {
      part.joinPartType = part.tmp1.joinPartType
      part.joinIndex = part.tmp1.joinIndex
      if (part.joinPartType === 'entity') {
        this.loadEntityFields(part.tmp1.obj)
        part.entity = part.tmp1.obj
        part.entityId = part.tmp1.obj.entityId
      } else if (part.joinPartType === 'mtm') {
        part.mtm = part.tmp1.obj
        part.mtmId = part.tmp1.obj.mtmId
      }
      repairAtJoinChange(this.form)
    },
    fillTmp2ToPart (part) {
      part.joinPartType = part.tmp2.joinPartType
      part.joinIndex = part.tmp2.joinIndex
      if (part.joinPartType === 'entity') {
        this.loadEntityFields(part.tmp2.obj)
        part.entity = part.tmp2.obj
        part.entityId = part.tmp2.obj.entityId
        part.field = part.tmp2.field
        part.fieldId = part.tmp2.field.fieldId
      } else if (part.joinPartType === 'mtm') {
        part.mtm = part.tmp2.obj
        part.mtmId = part.tmp2.obj.mtmId
        part.mtmField = part.tmp2.field
      }
    },
    addJoin () {
      this.form.joins.push(initJoinDTO(0, this.form.joins.length))
    },
    removeJoin (index) {
      this.form.joins.splice(index, 1)
      repairAtJoinRemove(this.form, index + 1)
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
      .then(() => this.initMtmOptions())
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
