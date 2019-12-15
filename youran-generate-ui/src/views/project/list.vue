<template>
  <div class="projectList">
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item>项目管理</el-breadcrumb-item>
    </el-breadcrumb>
    <el-row type="flex" align="middle" :gutter="20">
      <el-col :span="6"  class="activeNum">
        已选择{{ activeNum }}个项目
      </el-col>
      <el-col :span="18" style="text-align: right; margin-bottom: 10px;">
        <el-button @click.native="handleAdd" type="success">创建项目</el-button>
        <el-button @click.native="handleImport" type="warning">元数据导入</el-button>
      </el-col>
    </el-row>

    <el-table ref="projectTable" :data="list" :border="true"
              row-key="projectId"
              style="min-width: 1255px;"
              v-loading="loading"
              :element-loading-text="loadingText">
      <el-table-column type="index" label="序号" width="50"></el-table-column>
      <el-table-column property="projectName" label="项目标识" width="200"></el-table-column>
      <el-table-column property="projectDesc" label="项目名称"></el-table-column>
      <el-table-column property="author" label="作者" width="120"></el-table-column>
      <el-table-column label="Git仓库" width="90px">
        <template v-slot="scope">
          <svg-icon v-if="scope.row.remote" className="table-cell-icon color-success" iconClass="check"></svg-icon>
          <svg-icon v-else className="table-cell-icon color-danger" iconClass="times"></svg-icon>
        </template>
      </el-table-column>
      <el-table-column label="代码模板" width="180">
        <template v-slot="scope">
          <template v-for="template in [getTemplate(scope.row.templateId),
                                        getTemplate(scope.row.templateId2),
                                        getTemplate(scope.row.templateId3)]">
            <el-popover v-if="template"
                        :key="template.templateId"
                        placement="left"
                        width="400"
                        trigger="click">
              <el-scrollbar style="height:100%">
                <div v-html="convertMarkdown(template.remark)"
                     style="max-height:550px" class="markdown-body"></div>
              </el-scrollbar>
              <el-button slot="reference" type="text" size="medium">
                {{ template.name }}<span class="template-version-tag">v{{ template.templateVersion }}</span>
              </el-button>
            </el-popover>
          </template>
        </template>
      </el-table-column>
      <!--代码下载进度条-->
      <el-table-column label="代码生成进度" width="110">
        <template v-slot="scope">
          <el-progress v-if="progressingProjectIds.find(id => id===scope.row.projectId)"
                       :text-inside="true" :stroke-width="20"
                       :percentage="scope.row.genCodePercent"
                       :status="scope.row.genCodeStatus"></el-progress>
        </template>
      </el-table-column>
      <el-table-column
        label="操作"
        width="205">
        <template v-slot="scope">
          <el-button @click="handleEntity(scope.row)" type="text" size="medium">实体管理</el-button>
          <el-button @click="handleConst(scope.row)" type="text" size="medium">枚举管理</el-button>
          <el-dropdown size="small" trigger="click" @command="handleCommand" style="margin-left:10px;">
            <span class="el-dropdown-link button-font">
              操作<i class="el-icon-arrow-down el-icon--right"></i>
            </span>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item :command="{method:'handleEdit',arg:scope.row}" >
                <svg-icon className="dropdown-icon color-primary" iconClass="edit"></svg-icon>
                编辑
              </el-dropdown-item>
              <el-dropdown-item :command="{method:'handleDel',arg:scope.row}" >
                <svg-icon className="dropdown-icon color-danger" iconClass="trash"></svg-icon>
                删除
              </el-dropdown-item>
              <el-dropdown-item :command="{method:'handleReverseEngineering',arg:scope.row}" divided>
                <svg-icon className="dropdown-icon color-warning" iconClass="reverse-engineering"></svg-icon>
                反向工程
              </el-dropdown-item>
              <el-dropdown-item :command="{method:'handleExport',arg:scope.row}" >
                <svg-icon className="dropdown-icon color-purple" iconClass="download"></svg-icon>
                导出元数据
              </el-dropdown-item>
              <el-dropdown-item v-for="(templateIndex, index) in getProjectTemplateIndexs(scope.row)"
                                :key="'preview_button_' + scope.row.projectId + '_' + templateIndex"
                                :command="{method:'handlePreView',arg: [ scope.row , templateIndex ]}"
                                :divided="index===0">
                <svg-icon :className="'dropdown-icon ' + iconColorClass[index]" iconClass="preview"></svg-icon>
                代码预览({{ getTemplateName(scope.row, templateIndex) }}）
              </el-dropdown-item>
              <el-dropdown-item v-for="(templateIndex, index) in getProjectTemplateIndexs(scope.row)"
                                :key="'gencode_button_' + scope.row.projectId + '_' + templateIndex"
                                :command="{method:'handleGenCode',arg: [ scope.row , templateIndex ]}"
                                :divided="index===0">
                <svg-icon :className="'dropdown-icon ' + iconColorClass[index]" iconClass="code-download"></svg-icon>
                下载代码({{ getTemplateName(scope.row, templateIndex) }}）
              </el-dropdown-item>
              <template v-if="scope.row.remote">
                <el-dropdown-item v-for="(templateIndex, index) in getProjectRemoteUrlIndexs(scope.row)"
                                  :key="'gitcommit_button_' + scope.row.projectId + '_' + templateIndex"
                                  :command="{method:'handleCommit',arg: [ scope.row , templateIndex ]}"
                                  :divided="index===0">
                  <svg-icon :className="'dropdown-icon ' + iconColorClass[index]" iconClass="git"></svg-icon>
                  提交Git({{ getTemplateName(scope.row, templateIndex) }}）
                </el-dropdown-item>
              </template>
            </el-dropdown-menu>
          </el-dropdown>
        </template>
      </el-table-column>
    </el-table>

    <!-- 元数据导入对话框 -->
    <import-project ref="importProject"
                    @success="onImportProjectSuccess"></import-project>

    <el-dialog title="反向工程" :visible.sync="reverseEngineeringFormVisible" width="60%">
      <el-form ref="reverseEngineeringForm" :model="reverseEngineeringForm" :rules="reverseEngineeringFormRules" size="small">
        <el-form-item label="脚本语言：" label-width="100px">
          <el-radio-group v-model="reverseEngineeringForm.dbType">
            <el-radio border label="mysql">MySql</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="DDL脚本：" label-width="100px" prop="ddl">
          <el-input v-model="reverseEngineeringForm.ddl" type="textarea" :autosize="{ minRows: 10, maxRows: 1000}"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="reverseEngineeringFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="handleReverseEngineeringCheck">校 验</el-button>
        <el-button type="success" @click="handleReverseEngineeringSubmit">反向生成</el-button>
      </div>
    </el-dialog>

    <code-preview ref="codePreview"></code-preview>
  </div>
</template>

<script>
import projectApi from '@/api/project'
import templateApi from '@/api/template'
import CodePreview from './codePreview'
import ImportProject from './import'
import showdown from 'showdown'

const converter = new showdown.Converter({
  emoji: 'true',
  tables: 'true'
})

export default {
  name: 'projectList',
  components: { CodePreview, ImportProject },
  data () {
    return {
      activeNum: 0,
      selectItems: [],
      list: [],
      loading: false,
      loadingText: '',
      reverseEngineeringFormVisible: false,
      reverseEngineeringForm: {
        projectId: null,
        dbType: 'mysql',
        ddl: ''
      },
      reverseEngineeringFormRules: {
        ddl: [
          { required: true, message: '请输入DDL脚本', trigger: 'blur' },
          { max: 10000, message: '长度不能超过10000个字符', trigger: 'blur' }
        ]
      },
      iconColorClass: ['color-primary', 'color-warning', 'color-success'],
      progressingProjectIds: [],
      templateList: []
    }
  },
  methods: {
    selectionChange (val) {
      this.selectItems = val
      this.activeNum = this.selectItems.length
    },
    handleDel (row) {
      this.$common.confirm('是否确认删除')
        .then(() => projectApi.deleteBatch([row.projectId]))
        .then(() => this.doQuery())
        .catch(error => this.$common.showNotifyError(error))
    },
    // 列表查询
    doQuery () {
      this.loading = true
      this.loadingText = '列表加载中'
      projectApi.getList()
        .then(data => {
          // 下载进度条数据初始化
          data.forEach(r => {
            r.genCodeStatus = null
            r.genCodePercent = 0
            r.genCodeMsg = ''
          })
          this.list = data
        })
        .catch(error => this.$common.showNotifyError(error))
        .finally(() => { this.loading = false })
    },
    /**
     * 获取当前项目的模板序号列表
     */
    getProjectTemplateIndexs (row) {
      const indexs = []
      if (row.templateId) {
        indexs.push(1)
      }
      if (row.templateId2) {
        indexs.push(2)
      }
      if (row.templateId3) {
        indexs.push(3)
      }
      return indexs
    },
    /**
     * 获取当前项目的远程git仓库序号列表
     */
    getProjectRemoteUrlIndexs (row) {
      const indexs = []
      if (row.templateId && row.remoteUrl) {
        indexs.push(1)
      }
      if (row.templateId2 && row.remoteUrl2) {
        indexs.push(2)
      }
      if (row.templateId3 && row.remoteUrl3) {
        indexs.push(3)
      }
      return indexs
    },
    getTemplateList () {
      return templateApi.getList()
        .then(data => {
          this.templateList = data
        })
        .catch(error => this.$common.showNotifyError(error))
    },
    getTemplate (templateId) {
      return this.templateList.find(t => t.templateId === templateId)
    },
    getTemplateName (row, templateIndex) {
      let tid = null
      if (templateIndex === 1) {
        tid = row.templateId
      } else if (templateIndex === 2) {
        tid = row.templateId2
      } else if (templateIndex === 3) {
        tid = row.templateId3
      }
      const template = this.getTemplate(tid)
      if (template) {
        return template.name + 'v' + template.templateVersion
      }
      return ''
    },
    handleAdd () {
      this.$router.push('/project/add')
    },
    handleImport () {
      this.$refs.importProject.show()
    },
    onImportProjectSuccess () {
      this.doQuery()
    },
    handleEntity (row) {
      this.$router.push(`/project/${row.projectId}/entity`)
    },
    handleConst (row) {
      this.$router.push(`/project/${row.projectId}/const`)
    },
    handleEdit (row) {
      this.$router.push(`/project/edit/${row.projectId}`)
    },
    handleShow (row) {
      this.$router.push(`/project/show/${row.projectId}`)
    },
    handleExport (row) {
      projectApi.exportCodeZip(row.projectId)
    },
    handlePreView ([row, templateIndex]) {
      const projectId = row.projectId
      projectApi.callCodeGenWebSocketService(
        'gen_code',
        { 'projectId': projectId, 'templateIndex': templateIndex },
        () => this.progressingProjectIds.push(projectId),
        progressVO => this.rowProgressChange(row, progressVO)
      )
        .then(progressVO => {
          if (progressVO.status === 2) {
            this.$refs.codePreview.show(row.projectId, row.projectName, templateIndex)
          } else {
            this.$common.showNotifyError(progressVO.msg)
          }
        })
        .finally(() => this.removeProgress(row))
    },
    /**
     * 行进度条改变
     * @return boolean 进度是否结束
     */
    rowProgressChange (row, progressVO) {
      let done = false
      row.genCodePercent = progressVO.percentage
      row.genCodeMsg = progressVO.msg
      row.genCodeStatus = null
      // 进度完成
      if (progressVO.status === 2) {
        row.genCodeStatus = 'success'
        done = true
      }
      // 进度异常
      if (progressVO.status === 3) {
        row.genCodeStatus = 'exception'
        done = true
      }
      return done
    },
    handleGenCode ([row, templateIndex]) {
      const projectId = row.projectId
      this.$common.confirm('是否确认下载')
        .then(() => projectApi.callCodeGenWebSocketService(
          'gen_code_and_zip',
          { 'projectId': projectId, 'templateIndex': templateIndex },
          () => this.progressingProjectIds.push(projectId),
          progressVO => this.rowProgressChange(row, progressVO)
        ))
        .then(progressVO => {
          if (progressVO.status === 2) {
            this.$common.showMsg('success', progressVO.msg)
            // 下载代码文件
            projectApi.exportCodeZipBySessionId(progressVO.sessionId)
          } else {
            this.$common.showNotifyError(progressVO.msg)
          }
        })
        .finally(() => this.removeProgress(row))
    },
    handleReverseEngineering (row) {
      this.reverseEngineeringFormVisible = true
      this.reverseEngineeringForm.projectId = row.projectId
      this.reverseEngineeringForm.ddl = ''
    },
    handleReverseEngineeringCheck () {
      let loading = null
      // 校验表单
      this.$refs.reverseEngineeringForm.validate()
        // 提交表单
        .then(() => {
          loading = this.$loading()
          return projectApi.reverseEngineeringCheck(this.reverseEngineeringForm)
        })
        .then(() => {
          this.$common.showMsg('success', '校验通过')
        })
        .catch(error => this.$common.showNotifyError(error))
        .finally(() => {
          if (loading) {
            loading.close()
          }
        })
    },
    handleReverseEngineeringSubmit () {
      let loading = null
      // 校验表单
      this.$refs.reverseEngineeringForm.validate()
        // 提交表单
        .then(() => {
          loading = this.$loading()
          return projectApi.reverseEngineeringExecute(this.reverseEngineeringForm)
        })
        .then(() => {
          this.$common.showMsg('success', '执行成功')
          this.reverseEngineeringFormVisible = false
        })
        .catch(error => this.$common.showNotifyError(error))
        .finally(() => {
          if (loading) {
            loading.close()
          }
        })
    },
    handleCommit ([row, templateIndex]) {
      const projectId = row.projectId
      projectApi.checkCommit(projectId, templateIndex)
        .then(checkCommitVO => {
          let msg = `首次提交代码到【${checkCommitVO.remoteUrl}】,是否确认？`
          if (!checkCommitVO.firstCommit) {
            const lastGen = checkCommitVO.lastGenHistory
            msg = `即将从【${checkCommitVO.remoteUrl}】拉取上次的分支【${lastGen.branch}】并在此基础上增量提交一个新分支,是否确认？`
          }
          return this.$common.confirm(msg)
        })
        .then(() => projectApi.callCodeGenWebSocketService(
          'git_commit',
          { 'projectId': projectId, 'templateIndex': templateIndex },
          () => this.progressingProjectIds.push(projectId),
          progressVO => this.rowProgressChange(row, progressVO)
        ))
        .then(progressVO => {
          if (progressVO.status === 2) {
            this.$common.showMsg('success', progressVO.msg)
          } else {
            this.$common.showNotifyError(progressVO.msg)
          }
        })
        .finally(() => this.removeProgress(row))
    },
    /**
     * 下载完成后移除进度条
     */
    removeProgress (row) {
      this.progressingProjectIds.splice(this.progressingProjectIds.findIndex(v => v === row.projectId), 1)
      row.genCodePercent = 0
    },
    handleCommand: function (command) {
      this[command.method](command.arg)
    },
    convertMarkdown (remark) {
      return converter.makeHtml(remark)
    }
  },
  activated () {
    this.getTemplateList()
      .then(() => this.doQuery())
  }
}
</script>
<style lang="scss">
  @import '../../assets/common.scss';

  .project-table-expand-column {
    visibility: hidden;
  }

  .project-table-expand {
    margin: 0px 60px;

    .el-form-item {
      margin-bottom: 5px;
    }
  }

  .projectList {

    .active-row {
      background-color: #f5f7fa;
    }

    .activeNum {
      min-width: 160px;
      text-align: left;
      padding: 0 0 0 20px;
    }

    /**
     * 调整表格行高
     */
    .el-table td {
      padding: $el-table-padding;
    }

    /**
     * 操作按钮字体
     */
    .button-font {
      font: 400 13.3333px Arial;
    }
    /**
     * 模板版本标签
     */
    .template-version-tag {
      color: #FFFFFF;
      border: 2px solid transparent;
      border-radius:4px;
      margin: 0px;
      user-select: none;
      font-size: 10px;
      padding: 1px;
      background-color: $color-warning;
    }
  }
  /**
   * 下拉菜单图标
   */
  .dropdown-icon {
    font-size: 14px;
    margin-right: 0px;
    vertical-align: -4%;
  }
</style>
