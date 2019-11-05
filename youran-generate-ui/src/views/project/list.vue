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
      <el-table-column property="groupId" label="groupId" width="160"></el-table-column>
      <el-table-column property="projectName" label="项目标识" width="200"></el-table-column>
      <el-table-column property="projectDesc" label="项目名称"></el-table-column>
      <el-table-column property="author" label="作者" width="120"></el-table-column>
      <el-table-column property="packageName" label="包名" width="180"></el-table-column>
      <el-table-column label="Git仓库" width="90px">
        <template v-slot="scope">
          <i v-if="scope.row.remote==1" class="iconfont icon-check2 table-cell-icon color-success"></i>
          <i v-else class="iconfont icon-times1 table-cell-icon color-danger"></i>
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
          <el-dropdown trigger="click" @command="handleCommand" style="margin-left:10px;">
            <span class="el-dropdown-link button-font">
              操作<i class="el-icon-arrow-down el-icon--right"></i>
            </span>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item :command="{method:'handleEdit',arg:scope.row}" >
                <i class="iconfont icon-edit_small1 dropdown-icon"></i>
                编辑
              </el-dropdown-item>
              <el-dropdown-item :command="{method:'handleDel',arg:scope.row}" >
                <i class="iconfont icon-trash dropdown-icon"></i>
                删除
              </el-dropdown-item>
              <el-dropdown-item :command="{method:'handleReverseEngineering',arg:scope.row}" divided>
                <i class="iconfont icon-jushounixiangdan-moren dropdown-icon"></i>
                反向工程
              </el-dropdown-item>
              <el-dropdown-item :command="{method:'handleExport',arg:scope.row}" >
                <i class="iconfont icon-download dropdown-icon"></i>
                导出元数据
              </el-dropdown-item>
              <el-dropdown-item v-for="(item, index) in getProjectTemplateIndexs(scope.row)"
                                :key="'preview_button_' + scope.row.projectId + '_' + item"
                                :command="{method:'handlePreView',arg: [ scope.row , item ]}"
                                :divided="index===0">
                <i class="iconfont icon-preview2 dropdown-icon"></i>
                代码预览(模板{{item}}）
              </el-dropdown-item>
              <el-dropdown-item v-for="(item, index) in getProjectTemplateIndexs(scope.row)"
                                :key="'gencode_button_' + scope.row.projectId + '_' + item"
                                :command="{method:'handleGenCode',arg: [ scope.row , item ]}"
                                :divided="index===0">
                <i class="iconfont icon-code-download dropdown-icon"></i>
                下载代码(模板{{item}}）
              </el-dropdown-item>
              <template v-if="scope.row.remote==1">
                <el-dropdown-item v-for="(item, index) in getProjectRemoteUrlIndexs(scope.row)"
                                  :key="'gitcommit_button_' + scope.row.projectId + '_' + item"
                                  :command="{method:'handleCommit',arg: [ scope.row , item ]}"
                                  :divided="index===0">
                  <i class="iconfont icon-git1 dropdown-icon"></i>
                  提交Git(模板{{item}}）
                </el-dropdown-item>
              </template>
            </el-dropdown-menu>
          </el-dropdown>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog title="元数据导入" :visible.sync="importFormVisible" width="400px">
      <el-upload drag
                 :action="importUrl"
                 :on-success="onImportSuccess"
                 :on-error="onImportError"
                 :show-file-list="false"
                 accept="application/zip">
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
        <div class="el-upload__tip" slot="tip">只能上传zip格式的压缩包</div>
      </el-upload>
    </el-dialog>

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
    <!-- 文件下载专用iframe -->
    <iframe style="display:none;" :src="downloadUrl"></iframe>
  </div>
</template>

<script>
import { apiPath, wsApiPath } from '@/components/common'
/**
 * websocket前端组件
 * https://www.npmjs.com/package/webstomp-client
 */
import webstomp from 'webstomp-client'
import SockJS from 'sockjs-client'
import shortid from 'shortid'
import CodePreview from './codePreview'

export default {
  name: 'projectList',
  components: { CodePreview },
  data () {
    return {
      query: {},
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
      progressingProjectIds: [],
      downloadUrl: '',
      importFormVisible: false,
      importUrl: `/${apiPath}/meta_import`
    }
  },
  methods: {
    selectionChange (val) {
      this.selectItems = val
      this.activeNum = this.selectItems.length
    },
    handleDel (row) {
      this.$common.confirm('是否确认删除')
        .then(() => this.$ajax.put(`/${apiPath}/meta_project/deleteBatch`, [row.projectId]))
        .then(response => this.$common.checkResult(response))
        .then(() => this.doQuery())
        .catch(error => this.$common.showNotifyError(error))
    },
    // 列表查询
    doQuery () {
      this.loading = true
      this.loadingText = '列表加载中'
      this.$ajax.get(`/${apiPath}/meta_project/list`, { params: this.query })
        .then(response => this.$common.checkResult(response))
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
    handleAdd () {
      this.$router.push('/project/add')
    },
    handleImport () {
      this.importFormVisible = true
    },
    onImportSuccess (response, file, fileList) {
      this.importFormVisible = false
      this.$common.showMsg('success', '导入成功')
      this.doQuery()
    },
    onImportError (error, file, fileList) {
      this.$common.showNotifyError(JSON.parse(error.message))
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
      this.downloadUrl = `/${apiPath}/meta_export/${row.projectId}`
      // 隔2秒改成null，修复不能重复下载的bug
      setTimeout(() => {
        this.downloadUrl = null
      }, 2000)
    },
    handlePreView ([row, templateIndex]) {
      const projectId = row.projectId
      this.callCodeGenWebSocketService(
        'genCode',
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
    /**
     * 调用【代码生成】相关websocket服务
     * @param serviceName 服务名
     * @param params 请求参数
     * @param afterConnect 连接建立之后的回调
     * @param doProgress 处理进度消息回调
     */
    callCodeGenWebSocketService (serviceName, params, afterConnect, doProgress) {
      return new Promise((resolve, reject) => {
        const sock = new SockJS(`${this.$common.BASE_API_URL}/${wsApiPath}`)
        const stompClient = webstomp.over(sock)
        stompClient.connect({}, () => {
          // 生成随机sessionId
          const sessionId = shortid.generate()
          // 订阅进度变化的topic
          stompClient.subscribe(`/code_gen/${serviceName}_progress/${sessionId}`, (frame) => {
            const progressVO = JSON.parse(frame.body)
            const done = doProgress(progressVO)
            if (done) {
              // 如果进度结束，则断开websocket连接
              stompClient.disconnect()
              resolve(progressVO)
            }
          })
          afterConnect()
          // 请求生成代码
          stompClient.send(`/code_gen/${serviceName}/${sessionId}`, '', params)
        })
      })
    },
    handleGenCode ([row, templateIndex]) {
      const projectId = row.projectId
      this.$common.confirm('是否确认下载')
        .then(() => this.callCodeGenWebSocketService(
          'genCodeAndZip',
          { 'projectId': projectId, 'templateIndex': templateIndex },
          () => this.progressingProjectIds.push(projectId),
          progressVO => this.rowProgressChange(row, progressVO)
        ))
        .then(progressVO => {
          if (progressVO.status === 2) {
            // 修改iframe的地址，进行文件下载
            this.downloadUrl = `${this.$common.BASE_API_URL}/${apiPath}/code_gen/downloadCode/${progressVO.sessionId}`
            this.$common.showMsg('success', progressVO.msg)
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
          return this.$ajax.post(`/${apiPath}/reverse_engineering/check`, this.reverseEngineeringForm)
        })
      // 校验返回结果
        .then(response => this.$common.checkResult(response))
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
          return this.$ajax.post(`/${apiPath}/reverse_engineering/execute`, this.reverseEngineeringForm)
        })
        // 校验返回结果
        .then(response => this.$common.checkResult(response))
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
      this.$common.confirm('是否确认提交到远程git仓库')
        .then(() => this.callCodeGenWebSocketService(
          'gitCommit',
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
    }
  },
  activated () {
    this.doQuery()
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
     * 下拉菜单图标
     */
    .dropdown-icon {
      margin-right: 0px;
    }
  }
</style>
