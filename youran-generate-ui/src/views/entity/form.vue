<template>
  <div class="entityFormDiv">
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/project' }">项目管理</el-breadcrumb-item>
      <el-breadcrumb-item :to="{ path: `/project/${this.projectId}/entity` }">实体管理</el-breadcrumb-item>
      <el-breadcrumb-item>
        {{edit?'编辑实体':'创建实体'}}
      </el-breadcrumb-item>
    </el-breadcrumb>
    <el-row type="flex" align="middle" :gutter="20">
      <el-col :span="12">
        <el-form ref="entityForm" class="entityForm"
                 :rules="rules" :model="form"
                 label-width="90px" size="small" v-loading="formLoading">
          <el-form-item label="项目" prop="projectId">
            <help-popover name="entity.projectId">
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
          <el-form-item label="实体名" prop="title">
            <help-popover name="entity.title">
              <el-input v-model="form.title" placeholder="例如：用户" tabindex="10"></el-input>
            </help-popover>
          </el-form-item>
          <el-form-item label="模块名" prop="module">
            <help-popover name="entity.module">
              <el-autocomplete
                v-model="form.module"
                :fetch-suggestions="findModules"
                style="width:100%;"
                placeholder="例如：system"
                v-lower-case
              ></el-autocomplete>
            </help-popover>
          </el-form-item>
          <el-form-item label="类名/表名" prop="classAndTableName">
            <help-popover name="entity.classAndTableName">
              <el-col :span="11" class="col-left">
                <el-input v-upper-case-first v-model="form.className" placeholder="java类名，例如：User" tabindex="20"></el-input>
                <el-button size="mini" type="text" @click="form.className = $common.upperCaseFirst($common.camelCase(form.className))">转驼峰</el-button>
              </el-col>
              <el-col :span="2" style="padding-left: 0px;padding-right: 0px;text-align: center;">
                <el-tooltip class="item" effect="dark" content="粘贴到右边并转下划线" placement="top">
                  <el-button type="text" @click="copyClassNameToTableName()" tabindex="25">
                    <svg-icon className="table-cell-icon color-primary"
                              svgStyle="vertical-align: middle;"
                              iconClass="double-right"></svg-icon>
                  </el-button>
                </el-tooltip>
              </el-col>
              <el-col :span="11" class="col-right">
                <el-input v-model="form.tableName" placeholder="mysql表名，例如：t_user" tabindex="30"></el-input>
                <el-button size="mini" type="text" @click="form.tableName = $common.snakeCase(form.tableName)">转下划线</el-button>
              </el-col>
            </help-popover>
          </el-form-item>
          <el-form-item label="描述" prop="desc">
            <help-popover name="entity.desc">
              <el-input v-model="form.desc" type="textarea"
                        :autosize="{ minRows: 2, maxRows: 100}"
                        tabindex="40"></el-input>
            </help-popover>
          </el-form-item>
          <el-form-item label="分页" prop="pageSign">
            <help-popover name="entity.pageSign">
              <el-checkbox v-model="form.pageSign" tabindex="50">
                支持分页
              </el-checkbox>
            </help-popover>
          </el-form-item>
          <el-form-item label="rest服务">
            <help-popover name="entity.feature">
              <el-checkbox v-model="form.feature.list" tabindex="60">列表</el-checkbox>
              <el-checkbox v-model="form.feature.show" tabindex="70"
                           :disabled="form.feature.update">详情</el-checkbox>
              <el-checkbox v-model="form.feature.update" tabindex="80">修改</el-checkbox>
              <el-checkbox v-model="form.feature.save" tabindex="90">新建</el-checkbox>
              <el-checkbox v-model="form.feature.delete" tabindex="100">单个删除</el-checkbox>
              <el-checkbox v-model="form.feature.deleteBatch" tabindex="110">批量删除</el-checkbox>
              <el-checkbox v-model="form.feature.excelExport" tabindex="120">excel导出</el-checkbox>
              <el-checkbox v-model="form.feature.excelImport" tabindex="130">excel导入</el-checkbox>
            </help-popover>
          </el-form-item>
          <el-form-item v-if="labelVisible" label="扩展属性" prop="labels" >
            <help-popover name="entity.labels">
              <el-button v-for="(label,index) in form.labels"
                         :key="index" class="inner-form-button"
                         type="primary" @click="editLabel(index, label)"
                         plain>
                {{label | displayLabel}}
              </el-button>
              <el-button type="success" @click="addLabel"
                         class="inner-form-button inner-add-button"
                         icon="el-icon-plus" plain>
              </el-button>
            </help-popover>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="submit()" tabindex="120">提交</el-button>
            <el-button v-if="edit" type="warning" @click="reset()" tabindex="130">重置</el-button>
            <el-button @click="goBack(true)" tabindex="140">返回</el-button>
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>
    <label-form ref="labelForm" @submit="onLabelSubmit" @remove="onLabelRemove"></label-form>
  </div>
</template>

<script>
import projectApi from '@/api/project'
import entityApi from '@/api/entity'
import modulesMixin from '@/components/Mixins/modules'
import labelForm from '@/components/Label/form'
import { initFormBean, getRules } from './model'

export default {
  name: 'entityForm',
  props: ['projectId', 'entityId'],
  mixins: [modulesMixin],
  components: {
    labelForm
  },
  data () {
    const edit = !!this.entityId
    return {
      edit: edit,
      projectList: [],
      formLoading: false,
      old: initFormBean(edit),
      form: initFormBean(edit),
      rules: getRules(this),
      inputVisible: false,
      inputValue: '',
      labelVisible: false,
      labels: null
    }
  },
  filters: {
    displayLabel (label) {
      if (label.value) {
        return label.key + ':' + label.value
      } else {
        return label.key
      }
    }
  },
  watch: {
    'form.feature.update' (value) {
      if (value) {
        this.form.feature.show = true
      }
    }
  },
  methods: {
    queryProject () {
      this.formLoading = true
      return projectApi.getList()
        .then(data => { this.projectList = data })
        .finally(() => { this.formLoading = false })
    },
    getEntity () {
      this.formLoading = true
      return entityApi.get(this.entityId)
        .then(data => { this.old = data })
        .catch(error => this.$common.showNotifyError(error))
        .finally(() => { this.formLoading = false })
    },
    reset () {
      this.form = JSON.parse(JSON.stringify(this.old))
    },
    submit () {
      let loading = null
      // 校验表单
      this.$refs.entityForm.validate()
        // 提交表单
        .then(() => {
          loading = this.$loading()
          return entityApi.saveOrUpdate(this.form, this.edit)
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
    },
    copyClassNameToTableName () {
      this.form.tableName = this.$common.snakeCase(this.form.className)
      this.$refs.entityForm.validateField('classAndTableName')
    },
    loadEntityMetaLabel () {
      this.$refs.labelForm.loadMetaLabel({
        projectId: this.projectId,
        labelType: 'entity'
      }, metaLabels => {
        this.labelVisible = metaLabels && metaLabels.length
      })
    },
    editLabel (index, label) {
      this.$refs.labelForm.show(label, index)
    },
    addLabel () {
      this.$refs.labelForm.show(null, this.form.labels.length)
    },
    onLabelSubmit (index, label) {
      if (index >= this.form.labels.length) {
        this.form.labels.push(label)
      } else {
        this.$set(this.form.labels, index, label)
      }
    },
    onLabelRemove (index, label) {
      if (index < this.form.labels.length) {
        this.form.labels.splice(index, 1)
      }
    }
  },
  created () {
    if (this.edit) {
      Promise.all([this.getEntity(), this.queryProject()])
        .then(() => this.reset())
    } else {
      this.queryProject()
        .then(() => { this.form.projectId = parseInt(this.projectId) })
    }
  },
  mounted () {
    this.loadEntityMetaLabel()
  }
}
</script>

<style lang="scss">
  @import '../../assets/common.scss';
  .entityFormDiv .entityForm {
    @include youran-form;

  }

 .el-tag + .el-tag {
    margin-left: 10px;
  }
  .button-new-tag {
    margin-left: 10px;
    height: 32px;
    line-height: 30px;
    padding-top: 0;
    padding-bottom: 0;
  }
  .input-new-tag {
    width: 90px;
    margin-left: 10px;
    vertical-align: bottom;
  }

</style>
