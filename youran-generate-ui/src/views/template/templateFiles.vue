<template>
  <div class="fileManage" @contextmenu.prevent="">
    <el-dialog :title="'模板文件管理: ' + templateName" :visible.sync="visible" :fullscreen="true">
      <el-header class="codePath">
        <template v-for="(node,index) in paths">
          <span :key="node.key" style="line-height: 23px;">
            <i v-if="index>0"  class="el-icon-arrow-right"></i>
            <span><i :class="node.icon"></i> {{node.name}}</span>
          </span>
        </template>
      </el-header>
      <el-container ref="parent" class="codeContainer">
        <el-aside @contextmenu.native.prevent="showContextMenu" ref="aside" width="250px" v-loading="codeTreeLoading" class="codeAside">
          <el-scrollbar  style="height:100%">
            <el-tree :props="treeProps"
                     :data="codeTree.tree"
                     :render-content="renderTreeNode"
                     :expand-on-click-node="false"
                     @node-expand="expandSingleNode"
                     @node-contextmenu="showContextMenu"
                     @node-click="nodeClick">
            </el-tree>
          </el-scrollbar>
        </el-aside>
        <div ref="splitLine" @mousedown="splitLineMousedown" class="splitLine"></div>
        <el-container ref="main">
          <el-main class="codeMain" v-loading="fileLoading">
            <vue-codemirror v-model="currentFileContent" :options="cmOptions"></vue-codemirror>
          </el-main>
        </el-container>
      </el-container>
    </el-dialog>
    <vue-simple-context-menu
      :elementId="'context-menu'"
      :options="contextMenuOptions"
      ref="contextMenu"
      @option-clicked="contextMenuOptionClicked"
    />
    <el-dialog title="新建模板文件" :visible.sync="addTemplateFileFormVisible" width="550px">
      <el-form ref="addTemplateFileForm" :rules="templateFileRules" class="addTemplateForm" :model="templateFileForm" size="small">
        <el-form-item prop="fileName" label="文件名：" label-width="120px">
          <el-input style="width:300px;" v-model="templateFileForm.fileName"
                    placeholder="例如：xxxx.ftl"></el-input>
        </el-form-item>
        <el-form-item prop="fileDir" label="目录：" label-width="120px">
          <el-input style="width:300px;" v-model="templateFileForm.fileDir"
                    placeholder="例如：/aaa/bbb"></el-input>
        </el-form-item>
        <el-form-item label="上下文类型：" label-width="120px">
          <el-radio-group v-model="templateFileForm.contextType">
            <el-radio v-for="obj in contextType"
                      :key="obj.value"
                      :label="obj.value" border>{{obj.label}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="是否抽象文件：" label-width="120px">
          <el-radio-group v-model="templateFileForm.abstracted">
            <el-radio border :label="true">是</el-radio>
            <el-radio border :label="false">否</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="addTemplateFileFormVisible = false">取 消</el-button>
        <el-button type="success" @click="handleAddTemplateFile">创 建</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { apiPath } from '@/components/common'
import FileTypeUtil from '@/components/file-type-util'
/**
 * 代码编辑器
 * https://codemirror.net/
 */
import { codemirror } from 'vue-codemirror'
import 'codemirror/lib/codemirror.css'
import 'codemirror/theme/base16-dark.css'
import 'codemirror/mode/clike/clike.js'
import 'codemirror/mode/xml/xml.js'
import 'codemirror/mode/yaml/yaml.js'
import 'codemirror/mode/properties/properties.js'
import 'codemirror/mode/sql/sql.js'
import 'codemirror/mode/markdown/markdown.js'
/**
 * 右键菜单组件
 * https://github.com/johndatserakis/vue-simple-context-menu
 */
import VueSimpleContextMenu from 'vue-simple-context-menu'
import 'vue-simple-context-menu/dist/vue-simple-context-menu.css'
import { initTemplateFileFormBean, getTemplateFileRulesRules } from './model'
import options from '@/components/options'

const menuOptions1 = [
  {
    name: '新建模板文件',
    value: 'addTemplateFile'
  }
]
const menuOptions2 = [
  {
    name: '新建模板文件',
    value: 'addTemplateFile'
  },
  {
    name: '修改文件属性',
    value: 'editTemplateFile'
  }
]
export default {
  name: 'template-files',
  components: {
    'vue-codemirror': codemirror,
    'vue-simple-context-menu': VueSimpleContextMenu
  },
  data () {
    return {
      templateId: null,
      templateName: '',
      treeProps: {
        children: 'children',
        label: 'name'
      },
      // 后端返回的代码目录数据
      codeTree: {
        templateId: null,
        templateVersion: null,
        tree: []
      },
      cmOptions: {
        readOnly: true,
        mode: 'text/x-java',
        theme: 'base16-dark',
        lineNumbers: true,
        line: true
      },
      codeTreeLoading: false,
      currentNode: null,
      currentFileContent: '',
      paths: [],
      fileLoading: false,
      visible: false,
      contextMenuOptions: menuOptions1,
      // 是否显示添加模板文件表单
      addTemplateFileFormVisible: false,
      // 添加模板文件表单
      templateFileForm: initTemplateFileFormBean(),
      // 上下文类型
      contextType: options.contextType,
      // 模板文件校验规则
      templateFileRules: getTemplateFileRulesRules()
    }
  },
  methods: {
    contextMenuOptionClicked ({ item, option }) {
      if (option.value === 'addTemplateFile') {
        this.addTemplateFileFormVisible = true
      } else if (option.value === 'editTemplateFile') {
        this.addTemplateFileFormVisible = true
      }
    },
    showContextMenu (event, item) {
      this.contextMenuOptions = item ? menuOptions2 : menuOptions1
      this.$refs.contextMenu.showMenu(event, item)
    },
    initData (templateId, templateName) {
      this.templateId = templateId
      this.templateName = templateName
      this.codeTree.tree = []
      this.paths = []
    },
    show (templateId, templateName) {
      this.visible = true
      this.initData(templateId, templateName)
      this.queryCodeTree(templateId)
    },
    /**
     * 菜单自由伸缩
     */
    splitLineMousedown (e) {
      const parent = this.$refs.parent.$el
      const aside = this.$refs.aside.$el
      const main = this.$refs.main.$el
      const splitLine = this.$refs.splitLine
      const disX = e.clientX
      splitLine.left = splitLine.offsetLeft
      document.onmousemove = function (e) {
        let iT = splitLine.left + (e.clientX - disX)
        const maxT = parent.clientWight - splitLine.offsetWidth
        splitLine.style.margin = 0
        iT < 0 && (iT = 0)
        iT > maxT && (iT = maxT)
        splitLine.style.left = aside.style.width = iT + 'px'
        main.style.width = parent.clientWidth - iT + 'px'
        return false
      }
      document.onmouseup = function () {
        document.onmousemove = null
        document.onmouseup = null
        splitLine.releaseCapture && splitLine.releaseCapture()
      }
      splitLine.setCapture && splitLine.setCapture()
      return false
    },
    queryCodeTree (templateId) {
      this.codeTreeLoading = true
      return this.$ajax.get(`/${apiPath}/code_template/${templateId}/dir_tree`)
        .then(response => this.$common.checkResult(response))
        .then(data => { this.codeTree = data })
        .catch(error => this.$common.showNotifyError(error))
        .finally(() => { this.codeTreeLoading = false })
    },
    renderTreeNode (h, { node, data, store }) {
      const icon = data.dir ? FileTypeUtil.getIcon('folder') : FileTypeUtil.getIcon(data.type)
      return h('span', [
        h('i', { class: icon }),
        h('span', '     '),
        h('span', node.label)
      ])
    },
    parsePath (data) {
      this.paths = [{
        name: this.templateName,
        key: this.templateName,
        icon: FileTypeUtil.getIcon('folder')
      }]
      const paths = data.path.split('/').filter(p => p)
      for (let i = 0; i < paths.length; i++) {
        let p = paths[i]
        const icon = i < paths.length - 1 ? 'folder' : data.type
        const item = {
          name: p,
          key: i + '_' + p,
          icon: FileTypeUtil.getIcon(icon)
        }
        this.paths.push(item)
      }
    },
    nodeClick (data, node) {
      if (this.currentNode === data) {
        return
      }
      this.currentNode = data
      if (data.dir) {
        return
      }
      this.parsePath(data)
      this.fileLoading = true
      this.$ajax.get(`/${apiPath}/template_file/${data.info.fileId}`)
        .then(response => this.$common.checkResult(response))
        .then(file => {
          data.fileId = file.fileId
          data.fileName = file.fileName
          data.fileDir = file.fileDir
          data.templateId = file.templateId
          data.contextType = file.contextType
          data.abstracted = file.abstracted
          data.version = file.version
          this.cmOptions.mode = FileTypeUtil.getCmMode(data.type)
          this.currentFileContent = file.content
        })
        .catch(error => this.$common.showNotifyError(error))
        .finally(() => { this.fileLoading = false })
    },
    /**
     * 展开所有单节点下级
     */
    expandSingleNode (data, node) {
      function recursiveExpand (n) {
        if (n.childNodes.length === 1) {
          const childNode = n.childNodes[0]
          childNode.expanded = true
          recursiveExpand(childNode)
        }
      }
      recursiveExpand(node)
    },
    handleAddTemplateFile () {
      this.$refs.addTemplateFileForm.validate()
        .then(() => {
          return this.$ajax.post(`/${apiPath}/template_file`,
            Object.assign({}, this.templateFileForm, { templateId: this.templateId }))
        })
        .then(response => this.$common.checkResult(response))
        .then(() => {
          this.addTemplateFileFormVisible = false
        })
        .then(() => this.queryCodeTree(this.templateId))
        .catch(error => this.$common.showNotifyError(error))
    }
  }
}
</script>

<style lang="scss">
  @import '../../assets/common.scss';

  .fileManage {
    @include coding-panel;

    .addTemplateForm {
      padding: 20px 20px;
    }

    .el-dialog {
    }
  }
</style>
