<template>
  <div class="cascadeExtList">
    <el-row type="flex" align="middle" :gutter="20" style="padding:0px;">
      <el-col :span="4" class="activeNum">
        已选择{{ activeNum }}个字段
      </el-col>
      <el-col :span="20" style="text-align: right;">
        <el-button @click.native="handleAdd" type="success">添加</el-button>
        <el-button @click.native="handleDel" type="danger">删除</el-button>
      </el-col>
    </el-row>
    <el-table :data="entities" style="width: 100%" @selection-change="selectionChange" v-loading="loading">
      <el-table-column type="selection" width="50"></el-table-column>
      <el-table-column label="展示字段">
        <template slot-scope="scope">
          <span v-if="!scope.row.editFlag">{{ scope.row.cascadeFieldDesc+'('+scope.row.cascadeJfieldName+')' }}</span>
          <span v-if="scope.row.editFlag">
            <el-select v-model="scope.row.cascadeFieldId" @change="handleCascadeFieldChange(scope.row)" placeholder="请选择级联字段">
              <el-option
                v-for="item in cascadeFieldList"
                :key="item.fieldId"
                :label="item.fieldDesc+'('+item.jfieldName+')'"
                :value="item.fieldId">
              </el-option>
            </el-select>
          </span>
        </template>
      </el-table-column>
      <el-table-column label="字段别名">
        <template slot-scope="scope">
          <span v-if="!scope.row.editFlag">{{ scope.row.alias }}</span>
          <span v-if="scope.row.editFlag">
            <el-input v-model="scope.row.alias" placeholder="字段别名"></el-input>
          </span>
        </template>
      </el-table-column>
      <el-table-column label="查询条件" width="120px">
        <template slot-scope="scope">
          <el-switch v-model="scope.row.query"
                     :active-value="1"
                     :inactive-value="0"
                     :disabled="!scope.row.editFlag">
          </el-switch>
        </template>
      </el-table-column>
      <el-table-column label="列表展示" width="120px">
        <template slot-scope="scope">
          <el-switch v-model="scope.row.list"
                     :active-value="1"
                     :inactive-value="0"
                     :disabled="!scope.row.editFlag">
          </el-switch>
        </template>
      </el-table-column>
      <el-table-column label="详情展示" width="120px">
        <template slot-scope="scope">
          <el-switch v-model="scope.row.show"
                     :active-value="1"
                     :inactive-value="0"
                      :disabled="!scope.row.editFlag">
          </el-switch>
        </template>
      </el-table-column>
      <el-table-column
        label="操作"
        width="140">
        <template slot-scope="scope">
          <el-button v-if="!scope.row.editFlag" @click="handleEdit(scope.row)" type="text" size="medium">编辑</el-button>
          <el-button v-if="scope.row.editFlag" @click="handleSave(scope.row)" type="text" size="medium">保存</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>
<script>
import Vue from 'vue'
import { apiPath } from '@/components/common'

// 记录扩展模型
const cascadeExtModel = {
  cascadeExtId: null,
  fieldId: null,
  entityId: null,
  // 别名
  alias: '',
  // 是否在列表中展示
  list: 1,
  // 是否在详情中展示
  show: 1,
  // 是否为查询条件
  query: 1,
  // 级联实体的id
  cascadeEntityId: null,
  // 级联展示字段的id
  cascadeFieldId: null,
  cascadeJfieldName: null,
  cascadeFieldDesc: null,
  editFlag: true
}

export default {
  name: 'cascadeExtList',
  data () {
    return {
      entityId: null,
      fieldId: null,
      cascadeEntityId: null,
      cascadeFieldList: [],
      activeNum: 0,
      selectItems: [],
      entities: [],
      loading: false
    }
  },
  methods: {
    init (entityId, fieldId, cascadeEntityId) {
      this.entityId = entityId
      this.fieldId = fieldId
      this.cascadeEntityId = cascadeEntityId
      this.initCascadeFieldOptions()
      this.doQuery()
    },
    selectionChange (val) {
      this.selectItems = val
      this.activeNum = this.selectItems.length
    },
    initCascadeFieldOptions () {
      this.$ajax.get(`/${apiPath}/meta_field/list`, { params: { entityId: this.cascadeEntityId } })
        .then(response => this.$common.checkResult(response.data))
        .then(result => { this.cascadeFieldList = result.data })
        .catch(error => this.$common.showNotifyError(error))
    },
    doQuery () {
      this.loading = true
      this.$ajax.get(`/${apiPath}/meta_cascade_ext/list`, { params: { fieldId: this.fieldId } })
        .then(response => this.$common.checkResult(response.data))
        .then(result => {
          this.entities = result.data
          this.$emit('cascadeFieldNumChange', this.fieldId, this.entities.length)
        })
        .catch(error => this.$common.showNotifyError(error))
        .finally(() => { this.loading = false })
    },
    handleAdd () {
      const newRow = Object.assign({}, cascadeExtModel, {
        entityId: this.entityId,
        fieldId: this.fieldId,
        cascadeEntityId: this.cascadeEntityId
      })
      this.entities.unshift(newRow)
    },
    handleEdit (row) {
      Vue.set(row, 'editFlag', true)
    },
    handleCascadeFieldChange (row) {
      const cascadeField = this.cascadeFieldList.find(field => field.fieldId === row.cascadeFieldId)
      row.alias = cascadeField.jfieldName
    },
    handleSave (row) {
      let saveURL = `/${apiPath}/meta_cascade_ext/save`
      let method = 'post'
      if (row.cascadeExtId) {
        saveURL = `/${apiPath}/meta_cascade_ext/update`
        method = 'put'
      }
      const loading = this.$loading()
      // 提交
      this.$ajax[method](saveURL, row)
      // 校验返回结果
        .then(response => this.$common.checkResult(response.data))
      // 执行页面跳转
        .then(result => {
          this.$common.showMsg('success', '保存成功')
          if (!row.cascadeExtId) {
            row.cascadeExtId = result.data.cascadeExtId
            this.$emit('cascadeFieldNumAdd', this.fieldId, 1)
          }
          return this.$ajax.get(`/${apiPath}/meta_cascade_ext/${row.cascadeExtId}`)
        })
        .then(response => this.$common.checkResult(response.data))
        .then(result => {
          Object.assign(row, result.data, {
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
      const params = this.selectItems.map(cascadeExt => cascadeExt.cascadeExtId).filter(id => id != null)
      this.$common.confirm('是否确认删除')
        .then(() => {
          if (params.length > 0) {
            return this.$ajax.put(`/${apiPath}/meta_cascade_ext/deleteBatch`, params)
          }
        })
        .then(response => this.$common.checkResult(response.data))
        .then(() => this.doQuery())
        .catch(error => this.$common.showNotifyError(error))
    }

  }

}
</script>

<style>

</style>