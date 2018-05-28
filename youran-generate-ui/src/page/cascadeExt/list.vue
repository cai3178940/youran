<template>
  <div class="cascadeExtList">
    <el-row type="flex" align="middle" :gutter="20" style="padding:10px 0;">
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
          <span v-if="!scope.row.editFlag">{{ scope.row.jfieldName }}</span>
          <span v-if="scope.row.editFlag">
            <el-select v-model="scope.row.cascadeFieldId" placeholder="请选择级联字段">
              <el-option
                v-for="item in cascadeFieldList"
                :key="item.fieldId"
                :label="item.fieldName"
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
      <el-table-column label="是否在列表中展示" width="150px">
        <template slot-scope="scope">
          <el-switch v-model="scope.row.list"
                     :active-value="1"
                     :inactive-value="0"
                     :disabled="!scope.row.editFlag">
          </el-switch>
        </template>
      </el-table-column>
      <el-table-column label="是否在详情中展示" width="150px">
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
          <el-button v-if="scope.row.editFlag" @click="handleCancel(scope.row)" type="text" size="medium">取消</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>
<script>

  //记录扩展模型
  const cascadeExtModel = {
    cascadeExtId: null,
    fieldId: null,
    entityId: null,
    //别名
    alias: '',
    //是否在列表中展示
    list: 1,
    //是否在详情中展示
    show: 1,
    //级联实体的id
    cascadeEntityId: null,
    //级联展示字段的id
    cascadeFieldId: null,
    editFlag:true
  }

  export default {
    name: 'cascadeExtList',
    data: function () {
      return {
        entityId:null,
        fieldId:null,
        cascadeEntityId:null,
        cascadeFieldList:[],
        activeNum: 0,
        selectItems: [],
        entities: [],
        loading:false
      }
    },
    methods: {
      init: function(entityId, fieldId, cascadeEntityId){
        this.entityId = entityId
        this.fieldId = fieldId
        this.cascadeEntityId = cascadeEntityId
        this.initCascadeFieldOptions()
        this.doQuery()
      },
      selectionChange: function (val) {
        this.selectItems = val
        this.activeNum = this.selectItems.length
      },
      initCascadeFieldOptions: function(){
        this.$ajax.get('/generate/meta_field/list', {params:{entityId:this.cascadeEntityId}})
          .then(response => this.$common.checkResult(response.data))
          .then(result => this.cascadeFieldList = result.data)
          .catch(error => this.$common.showNotifyError(error))
      },
      doQuery:function () {
        this.$ajax.get('/generate/meta_cascade_ext/list', {params:{fieldId:this.fieldId}})
          .then(response => this.$common.checkResult(response.data))
          .then(result => this.entities = result.data)
          .catch(error => this.$common.showNotifyError(error))
          .finally(() => this.loading = false)
      },
      handleAdd: function () {
        var newRow = Object.assign(cascadeExtModel,{
          entityId:this.entityId,
          fieldId:this.fieldId,
          cascadeEntityId:this.cascadeEntityId,
        })
        this.entities.unshift(newRow)
      }

    }

  }



</script>

<style>

</style>
