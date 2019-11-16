<template>
  <div class="mtmCascadeExtList">
    <el-row type="flex" align="middle" :gutter="20" style="padding:0px;">
      <el-col :span="4" class="activeNum">
        已选择{{ activeNum }}个字段
      </el-col>
      <el-col :span="20" style="text-align: right; margin-bottom: 10px;">
        <el-button @click.native="handleAdd" type="success">添加</el-button>
        <el-button @click.native="handleDel" type="danger">删除</el-button>
      </el-col>
    </el-row>
    <el-table :data="list" style="width: 100%" :border="true"
              @selection-change="selectionChange" v-loading="loading">
      <el-table-column type="selection" width="50"></el-table-column>
      <el-table-column label="级联字段">
        <template v-slot="scope">
          <span v-if="!scope.row.editFlag">{{ scope.row.cascadeFieldDesc+'('+scope.row.cascadeJfieldName+')' }}</span>
          <span v-if="scope.row.editFlag">
            <el-select v-model="scope.row.cascadeFieldId" @change="handleCascadeFieldChange(scope.row)"
                       placeholder="请选择级联字段" size="small">
              <el-option
                v-for="item in cascadeFieldList"
                :key="item.fieldId"
                :label="item.fieldDesc+'('+item.jfieldName+')'"
                :value="item.fieldId"
                :disabled="!hold && !item.query">
              </el-option>
            </el-select>
          </span>
        </template>
      </el-table-column>
      <el-table-column label="搜索条件" width="120px">
        <template v-slot="scope">
          <template v-if="scope.row.editFlag">
            <el-checkbox v-model="scope.row.query"
                         :true-label="true"
                         :false-label="false"
                         :disabled="!scope.row.fieldQuery">
            </el-checkbox>
          </template>
          <template v-else>
            <i v-if="scope.row.query" class="iconfont icon-check2 table-cell-icon color-success"></i>
            <i v-else class="iconfont icon-times1 table-cell-icon color-danger"></i>
          </template>
        </template>
      </el-table-column>
      <el-table-column label="搜索字段别名">
        <template v-slot="scope">
          <span v-if="scope.row.query && !scope.row.editFlag">{{ scope.row.alias }}</span>
          <span v-if="scope.row.query && scope.row.editFlag">
            <el-input v-model="scope.row.alias" placeholder="字段别名" size="small"></el-input>
          </span>
        </template>
      </el-table-column>
      <el-table-column v-if="this.hold" label="列表展示" width="120px">
        <template v-slot="scope">
          <template v-if="scope.row.editFlag">
            <el-checkbox v-model="scope.row.list"
                         :true-label="true"
                         :false-label="false">
            </el-checkbox>
          </template>
          <template v-else>
            <i v-if="scope.row.list" class="iconfont icon-check2 table-cell-icon color-success"></i>
            <i v-else class="iconfont icon-times1 table-cell-icon color-danger"></i>
          </template>
        </template>
      </el-table-column>
      <el-table-column v-if="this.hold" label="详情展示" width="120px">
        <template v-slot="scope">
          <template v-if="scope.row.editFlag">
            <el-checkbox v-model="scope.row.show"
                         :true-label="true"
                         :false-label="false">
            </el-checkbox>
          </template>
          <template v-else>
            <i v-if="scope.row.show" class="iconfont icon-check2 table-cell-icon color-success"></i>
            <i v-else class="iconfont icon-times1 table-cell-icon color-danger"></i>
          </template>
        </template>
      </el-table-column>
      <el-table-column
        label="操作"
        width="140">
        <template v-slot="scope">
          <el-button v-if="!scope.row.editFlag" @click="handleEdit(scope.row)" type="text" size="medium">编辑</el-button>
          <el-button v-if="scope.row.editFlag" @click="handleSave(scope.row)" type="text" size="medium">保存</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>
<script>
import Vue from 'vue'
import fieldApi from '@/api/field'
import mtmCascadeExtApi from '@/api/mtmCascadeExt'

// 记录扩展模型
const cascadeExtModel = {
  mtmCascadeExtId: null,
  mtmId: null,
  entityId: null,
  // 别名
  alias: '',
  // 是否在列表中展示
  list: false,
  // 是否在详情中展示
  show: false,
  // 是否为搜索条件
  query: false,
  // 字段本身是否支持搜索
  fieldQuery: false,
  // 级联实体的id
  cascadeEntityId: null,
  // 级联展示字段的id
  cascadeFieldId: null,
  cascadeJfieldName: null,
  cascadeFieldDesc: null,
  editFlag: true
}

export default {
  name: 'mtmCascadeExtList',
  data () {
    return {
      mtmId: null,
      entityId: null,
      cascadeEntityId: null,
      cascadeFieldList: [],
      activeNum: 0,
      selectItems: [],
      list: [],
      loading: false,
      hold: false
    }
  },
  methods: {
    init (hold, mtmId, entityId, cascadeEntityId) {
      this.hold = hold
      this.mtmId = mtmId
      this.entityId = entityId
      this.cascadeEntityId = cascadeEntityId
      this.initCascadeFieldOptions()
      this.doQuery()
    },
    selectionChange (val) {
      this.selectItems = val
      this.activeNum = this.selectItems.length
    },
    initCascadeFieldOptions () {
      fieldApi.getList(this.cascadeEntityId, false)
        .then(data => { this.cascadeFieldList = data })
        .catch(error => this.$common.showNotifyError(error))
    },
    doQuery () {
      this.loading = true
      mtmCascadeExtApi.getList(this.mtmId, this.entityId, this.cascadeEntityId)
        .then(data => {
          this.list = data
          this.$emit('cascadeFieldNumChange', this.mtmId, this.cascadeEntityId, this.list.length)
        })
        .catch(error => this.$common.showNotifyError(error))
        .finally(() => { this.loading = false })
    },
    handleAdd () {
      const newRow = Object.assign({}, cascadeExtModel, {
        mtmId: this.mtmId,
        entityId: this.entityId,
        cascadeEntityId: this.cascadeEntityId
      })
      this.list.unshift(newRow)
    },
    handleEdit (row) {
      Vue.set(row, 'editFlag', true)
    },
    handleCascadeFieldChange (row) {
      const cascadeField = this.cascadeFieldList.find(field => field.fieldId === row.cascadeFieldId)
      row.alias = cascadeField.jfieldName
      if (cascadeField.query || cascadeField.primaryKey) {
        row.fieldQuery = true
      } else {
        row.fieldQuery = false
      }
      if (!row.fieldQuery) {
        row.query = false
      }
    },
    handleSave (row) {
      const loading = this.$loading()
      // 提交
      mtmCascadeExtApi.saveOrUpdate(row, !!row.mtmCascadeExtId)
      // 执行页面跳转
        .then(data => {
          this.$common.showMsg('success', '保存成功')
          if (!row.mtmCascadeExtId) {
            row.mtmCascadeExtId = data.mtmCascadeExtId
            this.$emit('cascadeFieldNumAdd', this.mtmId, this.cascadeEntityId, 1)
          }
          return mtmCascadeExtApi.get(row.mtmCascadeExtId)
        })
        .then(data => {
          Object.assign(row, data, {
            editFlag: false
          })
        })
        .catch(error => this.$common.showNotifyError(error))
        .finally(() => {
          if (loading) {
            loading.close()
          }
        })
    },
    handleDel () {
      if (this.activeNum <= 0) {
        this.$common.showMsg('warning', '请选择字段')
        return
      }
      const params = this.selectItems.map(cascadeExt => cascadeExt.mtmCascadeExtId).filter(id => id != null)
      this.$common.confirm('是否确认删除')
        .then(() => {
          if (params.length > 0) {
            return mtmCascadeExtApi.deleteBatch(params)
          }
        })
        .then(() => this.doQuery())
        .catch(error => this.$common.showNotifyError(error))
    }

  }

}
</script>
