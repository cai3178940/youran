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
      <el-table-column property="projectName" label="项目标识" width="180"></el-table-column>
      <el-table-column property="projectDesc" label="项目名称"></el-table-column>
      <el-table-column property="author" label="作者" width="100"></el-table-column>
      <el-table-column label="Git仓库" width="80px">
        <template v-slot="scope">
          <svg-icon v-if="scope.row.remote" className="table-cell-icon color-success" iconClass="check"></svg-icon>
          <svg-icon v-else className="table-cell-icon color-danger" iconClass="times"></svg-icon>
        </template>
      </el-table-column>
      <el-table-column label="使用代码模板" width="200">
        <template v-slot="scope">
          <template v-for="template in [getTemplate(scope.row.templateId),
                                        getTemplate(scope.row.templateId2),
                                        getTemplate(scope.row.templateId3)]">
            <el-dropdown v-if="template"
                         :key="template.templateId"
                         size="small" trigger="click"
                         @command="handleCommand"
                         style="margin:5px;">
              <el-button type="primary" size="mini">
                {{ template.name }}v{{ template.templateVersion }}
              </el-button>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item :command="{method:'handleTemplateRemark',arg: [ scope.row , template.remark ]}">
                  <svg-icon className="dropdown-icon color-primary" iconClass="info"></svg-icon>
                  模板说明
                </el-dropdown-item>
                <el-dropdown-item :command="{method:'handlePreView',arg: [ scope.row , template.templateId ]}">
                  <svg-icon className="dropdown-icon color-success" iconClass="preview"></svg-icon>
                  代码预览
                </el-dropdown-item>
                <el-dropdown-item :command="{method:'handleGenCode',arg: [ scope.row , template.templateId ]}">
                  <svg-icon className="dropdown-icon color-primary" iconClass="code-download"></svg-icon>
                  下载代码
                </el-dropdown-item>
                <template v-if="scope.row.remote">
                  <el-dropdown-item :command="{method:'handleGitDiff',arg: [ scope.row , template.templateId ]}"
                                    divided>
                    <svg-icon className="dropdown-icon color-warning" iconClass="diff"></svg-icon>
                    增量预览
                  </el-dropdown-item>
                  <el-dropdown-item :command="{method:'handleCommit',arg: [ scope.row , template.templateId ]}">
                    <svg-icon className="dropdown-icon color-danger" iconClass="git"></svg-icon>
                    提交Git
                  </el-dropdown-item>
                </template>
              </el-dropdown-menu>
            </el-dropdown>
          </template>
        </template>
      </el-table-column>
      <!--代码下载进度条-->
      <el-table-column label="代码生成进度" width="110">
        <template v-slot="scope">
          <el-progress v-if="progressingProjectIds.includes(scope.row.projectId)"
                       :text-inside="true" :stroke-width="20"
                       :percentage="scope.row.genCodePercent"
                       :status="scope.row.genCodeStatus"></el-progress>
        </template>
      </el-table-column>
      <el-table-column
        label="操作"
        width="210">
        <template v-slot="scope">
          <el-button @click="handleEntity(scope.row)" type="text" size="medium">实体管理</el-button>
          <el-button @click="handleConst(scope.row)" type="text" size="medium">枚举管理</el-button>
          <el-dropdown size="small" trigger="click" @command="handleCommand" style="margin-left:10px;">
            <span class="el-dropdown-link button-font">
              操作<i class="el-icon-arrow-down el-icon--right"></i>
            </span>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item :command="{method:'handleChart',arg:scope.row}">
                <svg-icon className="dropdown-icon color-success" iconClass="chart"></svg-icon>
                图表管理
              </el-dropdown-item>
              <el-dropdown-item :command="{method:'handleEdit',arg:scope.row}" divided>
                <svg-icon className="dropdown-icon color-primary" iconClass="edit"></svg-icon>
                编辑
              </el-dropdown-item>
              <el-dropdown-item :command="{method:'handleDel',arg:scope.row}">
                <svg-icon className="dropdown-icon color-danger" iconClass="trash"></svg-icon>
                删除
              </el-dropdown-item>
              <el-dropdown-item v-if="isShareButtonVisible(scope.row)" :command="{method:'handleShare',arg:scope.row}">
                <svg-icon className="dropdown-icon color-primary" iconClass="share"></svg-icon>
                共享
              </el-dropdown-item>
              <el-dropdown-item :command="{method:'handleReverseEngineering',arg:scope.row}" divided>
                <svg-icon className="dropdown-icon color-warning" iconClass="reverse-engineering"></svg-icon>
                反向工程
              </el-dropdown-item>
              <el-dropdown-item :command="{method:'handleExport',arg:scope.row}">
                <svg-icon className="dropdown-icon color-purple" iconClass="download"></svg-icon>
                导出元数据
              </el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </template>
      </el-table-column>
    </el-table>

    <!-- 元数据导入对话框 -->
    <import-project ref="importProject"
                    @success="onImportProjectSuccess"></import-project>
    <!-- 项目共享对话框 -->
    <el-dialog title="项目共享" :visible.sync="shareFormVisible" width="40%">
      <el-form ref="shareForm" :model="shareForm" size="small">
        <el-form-item label="共享给：" label-width="100px">
          <el-select style="width:90%;" v-model="shareForm.teamId"
                     placeholder="请选择项目组" tabindex="90" clearable>
            <el-option :label="'无'" :value="null"></el-option>
            <el-option
              v-for="item in teamList"
              :key="item.key"
              :label="item.value"
              :value="item.key">
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="shareFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="handleShareSubmit">提 交</el-button>
      </div>
    </el-dialog>
    <!-- 反向工程对话框 -->
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

    <el-dialog title="模板说明" :visible.sync="templateRemarkVisible" width="60%">
      <div class="markdown-body" v-html="templateRemarkHtml"></div>
    </el-dialog>

    <el-dialog title="增量预览" :visible.sync="codeDiffVisible" :fullscreen="true">
      <div v-html="codeDiffHtml"></div>
    </el-dialog>

    <code-preview ref="codePreview"></code-preview>
  </div>
</template>

<script>
import projectApi from '@/api/project'
import templateApi from '@/api/template'
import projectTeamApi from '@/api/team/projectTeam'
import CodePreview from './codePreview'
import ImportProject from './import'
import showdown from 'showdown'
// 文件差异对比工具 https://github.com/rtfpessoa/diff2html
import { html } from 'diff2html'
import 'diff2html/bundles/css/diff2html.min.css'
import { mapState } from 'vuex'

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
      shareFormVisible: false,
      shareForm: {
        projectId: null,
        teamId: null
      },
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
      progressingProjectIds: [],
      templateList: [],
      teamList: [],
      templateRemarkVisible: false,
      templateRemarkHtml: '',
      codeDiffVisible: false,
      codeDiffHtml: ''
    }
  },
  computed: {
    ...mapState({
      systemUserInfo: state => state.systemUserInfo
    })
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
    loadProjectTeamList () {
      return projectTeamApi.findOptions()
        .then(data => {
          this.teamList = data
        })
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
    handleChart (row) {
      this.$router.push(`/project/${row.projectId}/chart`)
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
    handlePreView ([row, templateId]) {
      const projectId = row.projectId
      if (!this.validateProgressing(projectId)) {
        return
      }
      projectApi.callCodeGenWebSocketService(
        'gen_code',
        { 'projectId': projectId, 'templateId': templateId },
        () => this.progressingProjectIds.push(projectId),
        progressVO => this.rowProgressChange(row, progressVO)
      )
        .then(progressVO => {
          if (progressVO.status === 2) {
            this.$refs.codePreview.show(row.projectId, row.projectName, templateId)
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
    handleGenCode ([row, templateId]) {
      const projectId = row.projectId
      if (!this.validateProgressing(projectId)) {
        return
      }
      this.$common.confirm('是否确认下载')
        .then(() => projectApi.callCodeGenWebSocketService(
          'gen_code_and_zip',
          { 'projectId': projectId, 'templateId': templateId },
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
    /**
     * 是否显示共享按钮
     */
    isShareButtonVisible (row) {
      if (!this.systemUserInfo.teamEnabled) {
        return false
      }
      return row.createdBy === this.systemUserInfo.username
    },
    /**
     * 点击共享按钮后触发
     */
    handleShare (row) {
      this.shareFormVisible = true
      this.shareForm.projectId = row.projectId
      this.shareForm.teamId = row.teamId
    },
    /**
     * 点击共享提交按钮后触发
     */
    handleShareSubmit () {
      let loading = this.$loading()
      projectApi.share(this.shareForm)
        .then(() => {
          this.shareFormVisible = false
          this.$common.showMsg('success', '操作成功')
          this.doQuery()
        })
        .catch(error => this.$common.showNotifyError(error))
        .finally(() => {
          if (loading) {
            loading.close()
          }
        })
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
    handleCommit ([row, templateId]) {
      const projectId = row.projectId
      if (!this.validateProgressing(projectId)) {
        return
      }
      projectApi.checkCommit(projectId, templateId)
        .then(checkCommitVO => {
          let msg = `首次提交代码到【${checkCommitVO.remoteUrl}】,是否继续？`
          if (!checkCommitVO.firstCommit) {
            const lastGen = checkCommitVO.lastGenHistory
            msg = `即将从【${checkCommitVO.remoteUrl}】拉取分支【${lastGen.branch}】并在此基础上执行增量提交,是否继续？`
          }
          return this.$common.confirm(msg)
        })
        .then(() => projectApi.callCodeGenWebSocketService(
          'git_commit',
          { 'projectId': projectId, 'templateId': templateId },
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
    handleTemplateRemark ([row, remark]) {
      this.templateRemarkVisible = true
      this.templateRemarkHtml = converter.makeHtml(remark)
    },
    /**
     * 校验项目是否正在生成代码
     */
    validateProgressing (projectId) {
      if (this.progressingProjectIds.includes(projectId)) {
        this.$common.showNotifyError('代码生成中，请稍后再试')
        return false
      }
      return true
    },
    /**
     * 显示git代码差异
     */
    handleGitDiff ([row, templateId]) {
      const projectId = row.projectId
      if (!this.validateProgressing(projectId)) {
        return
      }
      projectApi.callCodeGenWebSocketService(
        'git_diff',
        { 'projectId': projectId, 'templateId': templateId },
        () => this.progressingProjectIds.push(projectId),
        progressVO => this.rowProgressChange(row, progressVO)
      )
        .then(progressVO => {
          if (progressVO.status === 2) {
            if (!progressVO.data) {
              this.$common.showNotifyError('代码无变动')
            } else {
              this.codeDiffHtml = html(progressVO.data, {
                inputFormat: 'diff',
                showFiles: true,
                matching: 'lines',
                outputFormat: 'line-by-line'
              })
              this.codeDiffVisible = true
            }
          } else {
            this.$common.showNotifyError(progressVO.msg)
          }
        })
        .finally(() => this.removeProgress(row))
    }
  },
  activated () {
    Promise.all([this.getTemplateList(), this.loadProjectTeamList()])
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
