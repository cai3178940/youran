<template>
  <div class="codePreview">
    <el-dialog :title="projectName" :visible.sync="visible" :fullscreen="true">
      <el-header class="codePath">
        <template v-for="(node,index) in paths">
          <span :key="node.key" style="line-height: 23px;">
            <i v-if="index>0"  class="el-icon-arrow-right"></i>
            <span><i :class="node.icon"></i> {{node.name}}</span>
          </span>
        </template>
      </el-header>
      <el-container ref="parent" class="codeContainer">
        <el-aside ref="aside" width="250px" v-loading="codeTreeLoading" class="codeAside">
          <el-scrollbar style="height:100%">
            <el-tree :props="treeProps"
                     :data="codeTree.tree"
                     :render-content="renderTreeNode"
                     :expand-on-click-node="false"
                     @node-expand="expandSingleNode"
                     @node-click="nodeClick">
            </el-tree>
          </el-scrollbar>
        </el-aside>
        <div ref="splitLine" @mousedown="splitLineMousedown" class="splitLine"></div>
        <el-container ref="main">
          <el-main class="codeMain">
            <el-tabs v-model="currentTabName" type="border-card" closable @tab-remove="removeTab">
              <el-tab-pane
                v-for="tab in codeTabs"
                :key="tab.name"
                :label="tab.title"
                :name="tab.name"
                v-loading="tab.loading"
                element-loading-text="加载中..."
                element-loading-background="#313335">
                <vue-codemirror v-model="tab.content" :options="cmOptions"></vue-codemirror>
              </el-tab-pane>
            </el-tabs>
          </el-main>
        </el-container>
      </el-container>
    </el-dialog>
  </div>
</template>

<script>
import { apiPath } from '@/components/common'
import FileTypeUtil from '@/components/file-type-util'
import '@/assets/Hawcons/style.css'
import { codemirror } from 'vue-codemirror'
import 'codemirror/lib/codemirror.css'
import 'codemirror/theme/base16-dark.css'
import 'codemirror/mode/clike/clike.js'
import 'codemirror/mode/xml/xml.js'
import 'codemirror/mode/yaml/yaml.js'
import 'codemirror/mode/properties/properties.js'
import 'codemirror/mode/sql/sql.js'
import 'codemirror/mode/markdown/markdown.js'

export default {
  name: 'code-preview',
  components: {
    'vue-codemirror': codemirror
  },
  data () {
    return {
      projectId: null,
      projectName: '',
      treeProps: {
        children: 'children',
        label: 'name'
      },
      // 后端返回的代码目录数据
      codeTree: {
        projectId: null,
        projectVersion: null,
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
      paths: [],
      fileLoading: false,
      codeTabs: [],
      currentTabName: '',
      visible: false
    }
  },
  methods: {
    initData (projectId, projectName) {
      this.projectId = projectId
      this.projectName = projectName
      this.codeTree.tree = []
      this.paths = []
      this.codeTabs = []
    },
    show (projectId, projectName) {
      this.visible = true
      this.initData(projectId, projectName)
      this.queryCodeTree(projectId)
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
    queryCodeTree (projectId) {
      this.codeTreeLoading = true
      return this.$ajax.get(`/${apiPath}/code_preview/${projectId}/code_tree`)
        .then(response => this.$common.checkResult(response))
        .then(data => { this.codeTree = data })
        .catch(error => this.$common.showNotifyError(error))
        .finally(() => { this.codeTreeLoading = false })
    },
    renderTreeNode (h, { node, data, store }) {
      const icon = data.dir ? 'hawcons-icon-94-folder' : FileTypeUtil.getIcon(data.type)
      return h('span', [
        h('i', { attrs: { class: icon } }),
        h('span', '     '),
        h('span', node.label)
      ])
    },
    parsePath (path) {
      this.paths = [{
        name: this.projectName,
        key: this.projectName,
        icon: 'hawcons-icon-94-folder'
      }]
      const paths = path.split('/').filter(p => p)
      for (let i = 0; i < paths.length; i++) {
        let p = paths[i]
        const item = {
          name: p,
          key: i + '_' + p,
          icon: 'hawcons-icon-94-folder'
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
      const oldTab = this.codeTabs.find(tab => tab.name === data.path)
      if (oldTab) {
        this.currentTabName = oldTab.name
        return
      }
      this.parsePath(data.path)
      const tab = this.addTab(data)
      this.$ajax.get(`/${apiPath}/code_preview/${this.codeTree.projectId}/file_content?projectVersion=${this.codeTree.projectId}&filePath=${encodeURIComponent(data.path)}`, { responseType: 'text' })
        .then(response => this.$common.checkResult(response))
        .then(fileData => {
          this.cmOptions.mode = FileTypeUtil.getCmMode(data.type)
          tab.content = fileData
        })
        .catch(error => this.$common.showNotifyError(error))
        .finally(() => { tab.loading = false })
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
    addTab (nodeData) {
      const tab = {
        name: nodeData.path,
        title: nodeData.name,
        loading: true,
        content: '\n\n\n\n\n\n\n\n\n\n\n\n\n\n'
      }
      this.codeTabs.push(tab)
      this.currentTabName = nodeData.path
      return tab
    },
    removeTab (tabName) {
      if (this.currentTabName === tabName) {
        this.codeTabs.forEach((tab, index) => {
          if (tab.name === tabName) {
            let nextTab = this.codeTabs[index + 1] || this.codeTabs[index - 1]
            if (nextTab) {
              this.currentTabName = nextTab.name
            }
          }
        })
      }
      this.codeTabs = this.codeTabs.filter(tab => tab.name !== tabName)
    }
  }
}
</script>

<style lang="scss">
  @import '../../assets/common.scss';
  $white :  #FFFFFF;
  $border-color : #4a4e50;
  $back-color-1 : #313335;
  $back-color-2 : #3c3f41;

  .codePreview {
    .codeDiv {
      width: 100%;
      height: 100%;
    }

    .splitLine {
      position:absolute;
      left:248px;
      z-index:100;
      height:calc(100% - 70px);
      width:2px;
      overflow:hidden;
      background:$back-color-2;
      cursor:w-resize;
    }

    .el-dialog__header {
      background-color: $back-color-2;
      padding: 10px 20px 10px;
    }

    .el-dialog__title {
      color: $white;
    }

    .el-dialog__headerbtn {
      top: 15px;
    }

    .el-dialog__close {
      color: $white;
    }
    .el-dialog__close:hover {
      color: $white;
    }

    .el-loading-mask{
      background-color: #5d6164;
    }
    .el-dialog__body {
      height: calc(100% - 55px);
      padding: 0px;
    }
    .el-tree {
      min-width: 100%;
      display:inline-block !important;
    }
    .CodeMirror {
      border: 1px solid $back-color-1;
      height: auto;
    }
    .codeContainer {
      height: calc(100% - 15px);
    }
    .codeMain {
      background-color: $back-color-1;
      padding: 0px;
    }
    .codeAside {
      background-color: $back-color-2;
    }
    .codePath {
      background-color: $back-color-2;
      border: 1px solid #5a5b5f;
      font-size: 16px;
      height: 25px !important;
    }
    .el-tree-node__content{
      background-color: $back-color-2;
    }
    .el-tree-node:focus>.el-tree-node__content {
      background-color: #0d293e;
    }
    .el-tree{
      color: #adadad;
    }
    .el-tabs--border-card {
      border: 0px;
      border-left: 1px solid $border-color !important;
    }
    .el-tabs__item {
      color: #adadad !important;

      border-right: 1px solid $border-color !important;
    }
    .el-tabs__item.is-active {
      background-color: #515658 !important;
      border-bottom: 3px solid #4a7a88;
    }
    .el-tabs__header {
      background-color: $back-color-2;
      border-bottom: 1px solid $back-color-1;
    }
    .el-tabs__content {
      padding: 0px;
    }

  }
</style>
