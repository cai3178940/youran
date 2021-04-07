<template>
  <div class="projectFormDiv">
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/project' }">项目管理</el-breadcrumb-item>
      <el-breadcrumb-item>
        {{edit?'编辑项目':'创建项目'}}
      </el-breadcrumb-item>
    </el-breadcrumb>
    <el-row type="flex" align="middle" :gutter="20">
      <el-col :span="12">
        <el-form ref="projectForm" class="projectForm"
                 :rules="rules" :model="form" label-width="120px"
                 size="small" v-loading="formLoading">
          <el-form-item label="groupId" prop="groupId">
            <help-popover name="project.groupId">
              <el-input v-model="form.groupId"
                        maxlength="50" show-word-limit
                        placeholder="例如：com.mygroup" tabindex="10"></el-input>
            </help-popover>
          </el-form-item>
          <el-form-item label="项目标识" prop="projectName">
            <help-popover name="project.projectName">
              <el-input v-model="form.projectName"
                        maxlength="50" show-word-limit
                        placeholder="例如：bbs" tabindex="20"></el-input>
            </help-popover>
          </el-form-item>
          <el-form-item label="项目名称" prop="projectDesc">
            <help-popover name="project.projectDesc">
              <el-input v-model="form.projectDesc"
                        maxlength="100" show-word-limit
                        placeholder="例如：论坛" tabindex="30"></el-input>
            </help-popover>
          </el-form-item>
          <el-form-item label="包名" prop="packageName">
            <help-popover name="project.packageName">
              <el-input v-model="form.packageName"
                        maxlength="100" show-word-limit
                        placeholder="例如：com.cbb.bbs" tabindex="40"></el-input>
            </help-popover>
          </el-form-item>
          <el-form-item label="作者" prop="author">
            <help-popover name="project.author">
              <el-input v-model="form.author"
                        maxlength="50" show-word-limit
                        placeholder="例如：Jack" tabindex="50"></el-input>
            </help-popover>
          </el-form-item>
          <el-form-item label="spring-boot版本" prop="feature.bootVersion">
            <help-popover name="project.feature.bootVersion">
              <el-radio-group v-model="form.feature.bootVersion">
                <el-radio border disabled :label="1" tabindex="60">1.5.x</el-radio>
                <el-radio border :label="2" tabindex="61">2.3.x</el-radio>
              </el-radio-group>
            </help-popover>
          </el-form-item>
          <el-form-item label="代码模板" prop="templateId">
            <el-col :span="18" class="col-left">
              <el-select style="width:100%;" v-model="form.templateId"
                         placeholder="请选择代码模板" tabindex="70" clearable>
                <el-option
                  v-for="item in templateList"
                  :key="item.templateId"
                  :disabled="item.templateId===form.templateId2 || item.templateId===form.templateId3"
                  :label="item.name+'v'+item.templateVersion"
                  :value="item.templateId">
                </el-option>
              </el-select>
            </el-col>
            <el-col :span="6" class="col-right">
              <el-button v-if="!templateItemVisible2 || !templateItemVisible3"
                         size="small" type="text" @click="addTemplateItem">再加一个模板</el-button>
            </el-col>
          </el-form-item>
          <el-form-item v-if="templateItemVisible2" prop="templateId2">
            <el-col :span="18" class="col-left">
              <el-select style="width:100%;" v-model="form.templateId2"
                         placeholder="请选择第二模板" tabindex="80" clearable>
                <el-option
                  v-for="item in templateList"
                  :key="item.templateId"
                  :disabled="item.templateId===form.templateId || item.templateId===form.templateId3"
                  :label="item.name+'v'+item.templateVersion"
                  :value="item.templateId">
                </el-option>
              </el-select>
            </el-col>
            <el-col :span="6" class="col-right">
              <el-button size="small" type="text" @click="removeTemplateItem(2)">移除第二模板</el-button>
            </el-col>
          </el-form-item>
          <el-form-item v-if="templateItemVisible3" prop="templateId3">
            <el-col :span="18" class="col-left">
              <el-select style="width:100%;" v-model="form.templateId3"
                         placeholder="请选择第三模板" tabindex="90" clearable>
                <el-option
                  v-for="item in templateList"
                  :key="item.templateId"
                  :disabled="item.templateId===form.templateId || item.templateId===form.templateId2"
                  :label="item.name+'v'+item.templateVersion"
                  :value="item.templateId">
                </el-option>
              </el-select>
            </el-col>
            <el-col :span="6" class="col-right">
              <el-button size="small" type="text" @click="removeTemplateItem(3)">移除第三模板</el-button>
            </el-col>
          </el-form-item>
          <el-form-item label="启用Git仓库" prop="remote">
            <help-popover name="project.remote">
              <el-switch v-model="form.remote" tabindex="100">
              </el-switch>
            </help-popover>
          </el-form-item>
          <template v-if="form.remote">
            <el-form-item label="Git仓库地址" prop="remoteUrl">
              <help-popover name="project.remoteUrl">
                <el-input v-model="form.remoteUrl"
                          maxlength="256" show-word-limit
                          placeholder="例如：https://github.com/github/testrepo.git"
                          tabindex="110"></el-input>
              </help-popover>
            </el-form-item>
            <el-form-item v-if="templateItemVisible2" prop="remoteUrl2">
              <help-popover name="project.remoteUrl2">
                <el-input v-model="form.remoteUrl2"
                          maxlength="256" show-word-limit
                          placeholder="第二模板对应的Git仓库"
                          tabindex="120"></el-input>
              </help-popover>
            </el-form-item>
            <el-form-item v-if="templateItemVisible3" prop="remoteUrl3">
              <help-popover name="project.remoteUrl3">
                <el-input v-model="form.remoteUrl3"
                          maxlength="256" show-word-limit
                          placeholder="第三模板对应的Git仓库"
                          tabindex="130"></el-input>
              </help-popover>
            </el-form-item>
            <el-form-item label="Git用户名" prop="username">
              <help-popover name="project.username">
                <el-input v-model="form.username"
                          maxlength="32" show-word-limit
                          placeholder="例如：zhangsan" tabindex="140"></el-input>
              </help-popover>
            </el-form-item>
            <el-form-item label="Git密码/token" prop="password">
              <help-popover name="project.password">
                <el-input v-model="form.password"
                          maxlength="32" show-word-limit
                          placeholder="例如：123456" tabindex="150"></el-input>
              </help-popover>
            </el-form-item>
          </template>
          <el-form-item v-if="labelVisible" label="扩展属性" prop="labels" >
            <el-button v-for="(label,index) in form.labels"
                       :key="index" class="inner-form-button"
                       type="primary" @click="editLabel(index, label)"
                       plain>
              {{label | displayLabel}}
              <span class="button-in-button"
                    @click.stop="removeLabel(index, label)">&times;</span>
            </el-button>
            <el-button type="success" @click="addLabel"
                       class="inner-form-button inner-add-button"
                       icon="el-icon-plus" plain>
            </el-button>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="submit()" tabindex="160">提交</el-button>
            <el-button v-if="edit" type="warning" @click="reset()" tabindex="170">重置</el-button>
            <el-button @click="goBack(true)" tabindex="180">返回</el-button>
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>
    <label-form ref="labelForm" @submit="onLabelSubmit"></label-form>
  </div>
</template>

<script>
import projectApi from '@/api/project'
import templateApi from '@/api/template'
import labelForm from '@/components/Label/form'
import { initFormBean, getRules } from './model'

export default {
  name: 'projectForm',
  props: ['projectId'],
  components: {
    labelForm
  },
  data () {
    const edit = !!this.projectId
    return {
      edit: edit,
      formLoading: false,
      old: initFormBean(edit),
      form: initFormBean(edit),
      rules: getRules(),
      templateList: [],
      templateItemVisible2: false,
      templateItemVisible3: false,
      inputVisible: false,
      inputValue: '',
      labelVisible: false,
      labels: null
    }
  },
  watch: {
    'form.templateId' () {
      this.loadTemplateMetaLabel()
    },
    'form.templateId2' () {
      this.loadTemplateMetaLabel()
    },
    'form.templateId3' () {
      this.loadTemplateMetaLabel()
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
  methods: {
    getProject () {
      this.formLoading = true
      return projectApi.get(this.projectId)
        .then(data => { this.old = data })
        .catch(error => this.$common.showNotifyError(error))
        .finally(() => { this.formLoading = false })
    },
    getTemplateList () {
      return templateApi.getList()
        .then(data => {
          this.templateList = data
        })
        .catch(error => this.$common.showNotifyError(error))
    },
    /**
     * 再加一个模板
     */
    addTemplateItem () {
      if (!this.templateItemVisible2) {
        this.templateItemVisible2 = true
      } else if (!this.templateItemVisible3) {
        this.templateItemVisible3 = true
      }
    },
    /**
     * 移除模板
     */
    removeTemplateItem (i) {
      if (i === 2) {
        // 如果第三个模板存在，则把第三个模板内容移到第二个，再删除第三个
        if (this.templateItemVisible3) {
          this.form.templateId2 = this.form.templateId3
          this.form.remoteUrl2 = this.form.remoteUrl3
          this.form.templateId3 = null
          this.form.remoteUrl3 = null
          this.templateItemVisible3 = false
        } else {
          this.form.templateId2 = null
          this.form.remoteUrl2 = null
          this.templateItemVisible2 = false
        }
      } else if (i === 3) {
        this.form.templateId3 = null
        this.form.remoteUrl3 = null
        this.templateItemVisible3 = false
      }
    },
    reset () {
      for (const key in initFormBean(true)) {
        this.form[key] = this.old[key]
      }
      this.templateItemVisible2 = !!this.form.templateId2
      this.templateItemVisible3 = !!this.form.templateId3
    },
    submit () {
      let loading = null
      // 校验表单
      this.$refs.projectForm.validate()
        // 提交表单
        .then(() => {
          loading = this.$loading()
          return projectApi.saveOrUpdate(this.form, this.edit)
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
        this.$router.push('/project')
      }
    },
    loadTemplateMetaLabel () {
      const templateIds = [this.form.templateId, this.form.templateId2, this.form.templateId3]
        .filter(value => value)
      this.$refs.labelForm.loadMetaLabel({
        projectId: this.projectId,
        templateId: templateIds,
        labelType: 'project'
      }, metaLabels => {
        this.labelVisible = metaLabels && metaLabels.length
      })
    },
    editLabel (index, label) {
      this.$refs.labelForm.show(label, index)
    },
    removeLabel (index, label) {
      if (index < this.form.labels.length) {
        this.form.labels.splice(index, 1)
      }
      this.$refs.labelForm.close()
    },
    addLabel () {
      this.$refs.labelForm.show(null, this.form.labels.length)
    },
    onLabelSubmit (index, label) {
      for (let i = 0; i < this.form.labels.length; i++) {
        if (i !== index && this.form.labels[i].key === label.key) {
          this.$common.showNotifyError('属性key重复')
          return
        }
      }
      if (index >= this.form.labels.length) {
        this.form.labels.push(label)
      } else {
        this.$set(this.form.labels, index, label)
      }
      this.$refs.labelForm.close()
    }
  },
  created () {
    if (this.edit) {
      this.getProject()
        .then(() => this.getTemplateList())
        .then(() => this.reset())
    } else {
      this.getTemplateList()
    }
  }
}
</script>

<style lang="scss">
  @import '../../assets/common.scss';
  .projectFormDiv .projectForm {
    @include youran-form;
  }

</style>
