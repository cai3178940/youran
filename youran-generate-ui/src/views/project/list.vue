<template>
  <div class="projectList">
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item>项目管理</el-breadcrumb-item>
    </el-breadcrumb>
    <el-row type="flex" align="middle" :gutter="20">
      <el-col :span="6"  class="activeNum">
        已选择{{ activeNum }}个项目
      </el-col>
      <el-col :span="18" style="text-align: right;">
        <el-button @click.native="handleAdd" type="success">创建项目</el-button>
      </el-col>
    </el-row>

    <el-table ref="projectTable" :data="list"
              row-key="projectId"
              :expand-row-keys="expandRowKeys"
              style="min-width: 1255px;"
              v-loading="loading">
              <!--:row-class-name="activeRow"-->
              <!--@cell-mouse-enter="cellMouseEnter"-->
      <el-table-column type="expand"  width="0" class-name="project-table-expand-column">
        <template slot-scope="scope">
          <el-row>
            <el-col :span="24">
              <el-progress :text-inside="true" :stroke-width="18" :percentage="scope.row.genCodePercent" :status="scope.row.genCodeStatus"></el-progress>
            </el-col>
          </el-row>
        </template>
      </el-table-column>
      <el-table-column type="index" label="序号" width="50"></el-table-column>
      <el-table-column property="groupId" label="groupId" width="160"></el-table-column>
      <el-table-column property="projectName" label="项目标识" width="200"></el-table-column>
      <el-table-column property="projectDesc" label="项目名称" width="200"></el-table-column>
      <el-table-column property="author" label="作者" width="120"></el-table-column>
      <el-table-column property="packageName" label="包名" width="180"></el-table-column>
      <el-table-column label="Git仓库" width="90px">
        <template slot-scope="scope">
          <icon v-if="scope.row.remote==1" name="check" class="color-success"></icon>
          <icon v-if="scope.row.remote!=1" name="times" class="color-danger"></icon>
        </template>
      </el-table-column>
      <el-table-column
        label=""
        width="205">
        <template slot-scope="scope">
          <el-button @click="handleEntity(scope.row)" type="text" size="medium">实体管理</el-button>
          <el-button @click="handleConst(scope.row)" type="text" size="medium">枚举管理</el-button>
          <el-dropdown trigger="click" @command="handleCommand" style="margin-left:10px;">
            <span class="el-dropdown-link button-font">
              操作<i class="el-icon-arrow-down el-icon--right"></i>
            </span>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item :command="{method:'handleEdit',arg:scope.row}" >
                <icon name="edit" scale="0.8" ></icon> 编辑
              </el-dropdown-item>
              <el-dropdown-item :command="{method:'handleReverseEngineering',arg:scope.row}" >
                <icon name="object-group" scale="0.8" ></icon> 反向工程
              </el-dropdown-item>
              <el-dropdown-item :command="{method:'handlePreView',arg:scope.row}" >
                <icon name="eye" scale="0.8" ></icon> 代码预览
              </el-dropdown-item>
              <el-dropdown-item :command="{method:'handlePreViewSql',arg:scope.row}" >
                <icon name="file-code" scale="0.8" ></icon> sql预览
              </el-dropdown-item>
              <el-dropdown-item :command="{method:'handleGenCode',arg:scope.row}" >
                <icon name="download" scale="0.8" ></icon> 下载代码
              </el-dropdown-item>
              <el-dropdown-item v-if="scope.row.remote==1" :command="{method:'handleCommit',arg:scope.row}" >
                <icon name="brands/git" scale="0.8" ></icon> 提交Git
              </el-dropdown-item>
              <el-dropdown-item :command="{method:'handleDel',arg:scope.row}" >
                <icon name="trash-alt" scale="0.8" ></icon> 删除
              </el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog title="反向工程" :visible.sync="reverseEngineeringFormVisible" width="60%">
      <el-form ref="reverseEngineeringForm" :model="reverseEngineeringForm" :rules="reverseEngineeringFormRules">
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
      expandRowKeys: [],
      downloadUrl: ''
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
      this.$ajax.get(`/${apiPath}/meta_project/list`, { params: this.query })
        .then(response => this.$common.checkResult(response))
        .then(data => {
          // 下载进度条数据初始化
          data.forEach(r => {
            r.genCodeStatus = 'success'
            r.genCodePercent = 0
            r.genCodeMsg = ''
          })
          this.list = data
        })
        .catch(error => this.$common.showNotifyError(error))
        .finally(() => { this.loading = false })
    },
    handleAdd () {
      this.$router.push('/project/add')
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
    /*
    handleGenSql (row) {
      window.open(`${this.$common.BASE_API_URL}/${apiPath}/code_gen/genSql?projectId=${row.projectId}`)
    },
    */
    handlePreView (row) {
      this.loading = true
      this.$ajax.get(`/${apiPath}/code_gen/genCode?projectId=${row.projectId}`)
        .then(response => this.$common.checkResult(response))
        .then(() => {
          this.$refs.codePreview.show(row.projectId)
        })
        .finally(() => { this.loading = false })
    },
    handlePreViewSql (row) {
      this.loading = true
      this.$ajax.get(`/${apiPath}/code_gen/genCode?projectId=${row.projectId}`)
        .then(response => this.$common.checkResult(response))
        .then(() => {
          this.$refs.codePreview.show(row.projectId)
        })
        .finally(() => { this.loading = false })
    },
    /**
     * 行进度条改变
     * @return boolean 进度是否结束
     */
    rowProgressChange (row, progressVO) {
      let done = false
      row.genCodePercent = progressVO.percentage
      row.genCodeMsg = progressVO.msg
      row.genCodeStatus = 'text'
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
          afterConnect()
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
          // 请求生成代码
          stompClient.send(`/code_gen/${serviceName}/${sessionId}`, '', params)
        })
      })
    },
    handleGenCode (row) {
      const projectId = row.projectId
      this.$common.confirm('是否确认下载')
        .then(() => this.callCodeGenWebSocketService(
          'genCode',
          { 'projectId': projectId },
          () => this.expandRowKeys.push(projectId),
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
        .finally(() => this.expandRowKeys.splice(this.expandRowKeys.findIndex(v => v === projectId), 1))
    },
    /*
    handleGenCode (row) {
      this.$common.confirm('是否确认下载')
        .then(() => window.open(`${this.$common.BASE_API_URL}/${apiPath}/code_gen/genCodeAndDownload?projectId=${row.projectId}`))
    },
    */
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
    handleCommit (row) {
      const projectId = row.projectId
      this.$common.confirm('是否确认提交到远程git仓库')
        .then(() => this.callCodeGenWebSocketService(
          'gitCommit',
          { 'projectId': projectId },
          () => this.expandRowKeys.push(projectId),
          progressVO => this.rowProgressChange(row, progressVO)
        ))
        .then(progressVO => {
          if (progressVO.status === 2) {
            this.$common.showMsg('success', progressVO.msg)
          } else {
            this.$common.showNotifyError(progressVO.msg)
          }
        })
        .finally(() => this.expandRowKeys.splice(this.expandRowKeys.findIndex(v => v === projectId), 1))
    },
    /*
    handleCommit (row) {
      this.$common.confirm('是否确认提交到远程git仓库')
        .then(() => {
          this.loading = true
          return this.$ajax.get(`/${apiPath}/code_gen/gitCommit?projectId=${row.projectId}`)
        })
        .then(response => this.$common.checkResult(response))
        .then(result => this.$common.showMsg('success', result.message))
        .catch(error => this.$common.showNotifyError(error))
        .finally(() => { this.loading = false })
    },
    */
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

  }
</style>
