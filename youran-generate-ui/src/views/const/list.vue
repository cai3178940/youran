<template>
  <div class="constList">
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/project' }">项目管理</el-breadcrumb-item>
      <el-breadcrumb-item>枚举管理</el-breadcrumb-item>
    </el-breadcrumb>
    <el-row type="flex" align="middle" :gutter="20" style="padding:10px 0;">
      <el-col :span="6" class="activeNum">
        已选择{{ activeNum }}个枚举
      </el-col>
      <el-col :span="18" style="text-align: right;">
        <el-form :inline="true" :model="queryForm" class="demo-form-inline">
          <el-form-item>
            <el-select v-model="queryForm.projectId" @change="handleQuery" placeholder="请选择项目">
              <el-option
                v-for="item in projectList"
                :key="item.projectId"
                :label="item.projectDesc"
                :value="item.projectId">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button @click.native="handleAdd" type="success">创建枚举</el-button>
            <el-button @click.native="handleDel" type="danger">删除枚举</el-button>
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>
    <el-table ref="constTable" :data="list" style="width: 100%" :border="true"
              @selection-change="selectionChange" @expand-change="expandChange" v-loading="loading">
      <el-table-column type="expand" width="50">
        <template v-slot="scope">
          <el-table class="detailTable" :data="totalDetailList" v-loading="detailLoading" :show-header="false">
            <el-table-column width="100"></el-table-column>
            <el-table-column label="枚举值描述" width="250">
              <template v-slot="detail">
                <span v-if="!detail.row.editFlag">{{ detail.row.detailRemark }}</span>
                <span v-if="detail.row.editFlag">
                  <el-input v-model="detail.row.detailRemark"
                            maxlength="100" show-word-limit
                            :ref="'detailCell_'+detail.$index+'_0'"
                            @paste.native="pasteDetail($event, detail.$index, 0)"
                            placeholder="值描述，如：女"></el-input>
                </span>
              </template>
            </el-table-column>
            <el-table-column label="枚举字段名" width="250">
              <template v-slot="detail">
                <span v-if="!detail.row.editFlag">{{ detail.row.detailName }}</span>
                <span v-if="detail.row.editFlag">
                  <el-input v-upper-case v-model="detail.row.detailName"
                            maxlength="50" show-word-limit
                            :ref="'detailCell_'+detail.$index+'_1'"
                            @paste.native="pasteDetail($event, detail.$index, 1)"
                            placeholder="字段名，如：WOMAN"></el-input>
                </span>
              </template>
            </el-table-column>
            <el-table-column label="枚举值">
              <template v-slot="detail">
                <span v-if="!detail.row.editFlag">{{ detail.row.detailValue }}</span>
                <span v-if="detail.row.editFlag">
                  <el-input v-model="detail.row.detailValue"
                            maxlength="50" show-word-limit
                            :ref="'detailCell_'+detail.$index+'_2'"
                            @paste.native="pasteDetail($event, detail.$index, 2)"
                            placeholder="枚举值，如：2"></el-input>
                </span>
              </template>
            </el-table-column>
            <el-table-column
              label="操作"
              width="150">
              <template v-slot="detail">
                <el-button v-if="!detail.row.editFlag" @click="handleDetailEdit(detail.row)" type="text" size="medium">编辑</el-button>
                <el-button v-if="detail.row.editFlag" @click="handleDetailSave(detail.row)" type="text" size="medium">保存</el-button>
                <el-button @click="handleDetailDel(scope.row,detail.row)" type="text" size="medium">删除</el-button>
              </template>
            </el-table-column>
          </el-table>
        </template>
      </el-table-column>
      <el-table-column type="selection" width="50"></el-table-column>
      <el-table-column property="constRemark" label="枚举名称 / 值描述" width="250"></el-table-column>
      <el-table-column property="constName" label="枚举类名 / 字段名" width="250"></el-table-column>
      <el-table-column label="类型 / 枚举值">
        <template v-slot="scope">
          {{ scope.row.constType | optionLabel('constTypeOptions')}}
        </template>
      </el-table-column>
      <el-table-column
        label="操作"
        width="150">
        <template v-slot="scope">
          <el-button @click="handleEdit(scope.row)" type="text" size="medium">编辑</el-button>
          <el-button @click="handleDetailAdd(scope.row)" type="text" size="medium">添加枚举值</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import Vue from 'vue'
import options from '@/utils/options'
import { convert2d } from '@/utils/string-util'
import projectApi from '@/api/project'
import constApi from '@/api/const'
import { initDetailFormBean } from './model'

const constDetailModel = initDetailFormBean(false)
export default {
  name: 'constList',
  props: ['projectId', 'constId'],
  data () {
    return {
      // 查询参数
      query: {
        projectId: null
      },
      // 查询表单参数
      queryForm: {
        projectId: null
      },
      projectList: [],
      activeNum: 0,
      selectItems: [],
      list: [],
      expandedRow: null,
      loading: false,
      detailList: [],
      detailAddList: [],
      detailLoading: false
    }
  },
  computed: {
    totalDetailList () {
      return this.detailAddList.concat(this.detailList)
    }
  },
  filters: {
    optionLabel (value, optionType) {
      const ops = options[optionType]
      for (const op of ops) {
        if (op.value === value) {
          return op.label
        }
      }
      return null
    }
  },
  methods: {
    selectionChange (val) {
      this.selectItems = val
      this.activeNum = this.selectItems.length
    },
    handleDel () {
      if (this.activeNum <= 0) {
        this.$common.showMsg('warning', '请选择枚举')
        return
      }
      this.$common.confirm('是否确认删除')
        .then(() => constApi.deleteBatch(this.selectItems.map(c => c.constId)))
        .then(() => this.doQuery())
        .catch(error => this.$common.showNotifyError(error))
    },
    queryProject () {
      this.loading = true
      return projectApi.getList()
        .then(data => { this.projectList = data })
        .finally(() => { this.loading = false })
    },
    handleQuery () {
      // 将查询表单参数赋值给查询参数
      this.query = {
        ...this.queryForm
      }
      if (this.query.projectId !== parseInt(this.projectId)) {
        this.$router.push(`/project/${this.query.projectId}/const`)
      }
      this.doQuery()
    },
    // 列表查询
    doQuery () {
      if (!this.query.projectId) {
        return
      }
      this.loading = true
      return constApi.getList(this.query.projectId)
        .then(data => { this.list = data })
        .catch(error => this.$common.showNotifyError(error))
        .finally(() => { this.loading = false })
    },
    handleAdd () {
      this.$router.push(`/project/${this.projectId}/const/add`)
    },
    handleEdit (row) {
      this.$router.push(`/project/${this.projectId}/const/edit/${row.constId}`)
    },
    handleShow (row) {
      this.$router.push(`/project/${this.projectId}/const/show/${row.constId}`)
    },
    expandChange (row, expandedRows) {
      this.detailAddList = []
      // 如果当前展开大于1行，则将另一行关闭
      if (expandedRows && expandedRows.length > 1) {
        this.detailList = []
        const otherRow = expandedRows.find(r => r !== row)
        this.$refs.constTable.toggleRowExpansion(otherRow, false)
        this.doDetailQuery(row.constId)
        this.expandedRow = row
      } else if (expandedRows && expandedRows.length === 1 && this.expandedRow !== row) {
        // 如果当前展开行等于1行，则加载枚举值列表
        this.doDetailQuery(expandedRows[0].constId)
        this.expandedRow = expandedRows[0]
      }
      // 如果当前展开行等于0行，则清空缓存数据
      if (!expandedRows || expandedRows.length === 0) {
        this.expandedRow = null
        this.detailList = []
      }
    },
    // 枚举值列表查询
    doDetailQuery (constId) {
      this.detailLoading = true
      return constApi.getDetailList(
        {
          'constId': constId
        })
        .then(data => { this.detailList = data })
        .catch(error => this.$common.showNotifyError(error))
        .finally(() => { this.detailLoading = false })
    },
    handleDetailAdd (row) {
      if (this.expandedRow !== row) {
        this.$refs.constTable.toggleRowExpansion(row, true)
      }
      this.doHandleDetailAdd(row.constId)
    },
    doHandleDetailAdd (constId) {
      const newRow = Object.assign({}, constDetailModel, {
        constId: constId
      })
      this.detailAddList.push(newRow)
    },
    handleDetailEdit (detail) {
      Vue.set(detail, 'editFlag', true)
    },
    handleDetailSave (detail) {
      const isUpdate = !!detail.constDetailId
      const loading = this.$loading()
      // 提交
      constApi.saveOrUpdateDetail(detail, isUpdate)
        // 执行页面跳转
        .then(data => {
          this.$common.showMsg('success', '保存成功')
          if (!detail.constDetailId) {
            detail.constDetailId = data.constDetailId
          }
          return constApi.getDetail(detail.constDetailId)
        })
        .then(data => {
          Object.assign(detail, data, {
            editFlag: false
          })
          if (!isUpdate) {
            this.removeDetailAdd(detail)
            this.detailList.push(detail)
          }
          // 根据枚举值排序
          this.detailList = this.detailList.sort((f1, f2) => {
            return f1.detailValue.localeCompare(f2.detailValue)
          })
        })
        .catch(error => this.$common.showNotifyError(error))
        .finally(() => {
          if (loading) {
            loading.close()
          }
        })
    },
    removeDetailAdd (detail) {
      const index = this.detailAddList.findIndex(value => value === detail)
      if (index >= 0) {
        this.detailAddList.splice(index, 1)
      }
    },
    handleDetailDel (theConst, detail) {
      if (detail.constDetailId) {
        this.$common.confirm('是否确认删除枚举值')
          .then(() => constApi.deleteDetailBatch([detail.constDetailId]))
          .then(() => this.doDetailQuery(theConst.constId))
          .catch(error => this.$common.showNotifyError(error))
      } else {
        this.removeDetailAdd(detail)
      }
    },
    /**
     * 粘贴事件
     */
    pasteDetail (e, rowIndex, columnIndex) {
      const text = e.clipboardData.getData('Text')
      const textMatrix = convert2d(text)
      let stop = false
      // 只操作一个单元格，则走默认行为
      if (textMatrix.length === 1 && textMatrix[0].length === 1) {
        return
      }
      // 阻止事件的默认行为
      e.preventDefault()
      for (let i = 0; i < textMatrix.length; i++) {
        if (stop) {
          break
        }
        const textArray = textMatrix[i]
        for (let j = 0; j < textArray.length; j++) {
          // 超过3列则跳过
          if (columnIndex + j > 2) {
            continue
          }
          const name = 'detailCell_' + (rowIndex + i) + '_' + (columnIndex + j)
          const input = this.$refs[name]
          if (!input) {
            if (j === 0) {
              stop = true
            }
            break
          }
          input.$emit('input', textArray[j])
        }
      }
    }
  },
  activated () {
    this.queryProject()
      .then(() => {
        this.queryForm.projectId = parseInt(this.projectId)
        this.query.projectId = this.queryForm.projectId
      })
      .then(() => this.doQuery())
      .then(() => {
        if (this.constId) {
          const row = this.list.find(e => e.constId === parseInt(this.constId))
          this.$refs.constTable.toggleRowExpansion(row, true)
        }
      })
  }
}
</script>
<style lang="scss">
  @import '../../assets/common.scss';

  .constList {
    .activeNum {
      min-width: 160px;
      text-align: left;
      padding: 0 0 0 20px;
    }

    .el-table {
      /**
       * 调整表格行高
       */
      td {
        padding: $el-table-padding;
      }

      .el-table__expanded-cell {
        padding: 0px;
        border-bottom: 0px;
      }
    }

    .detailTable {
      color: #9ea0a7;
    }
  }
</style>
