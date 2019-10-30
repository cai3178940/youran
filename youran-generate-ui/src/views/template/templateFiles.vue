<template>
  <div class="fileManage">
    <el-dialog :title="title" :visible.sync="visible" :fullscreen="true">
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

export default {
  name: 'template-files',
  components: {
    'vue-codemirror': codemirror
  },
  data () {
    return {
      templateId: null,
      title: '',
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
      paths: [],
      fileLoading: false,
      codeTabs: [],
      currentTabName: '',
      visible: false
    }
  },
  methods: {
    initData (templateId, templateName) {
      this.templateId = templateId
      this.title = '模板文件管理: ' + templateName
      this.codeTree.tree = []
      this.paths = []
      this.codeTabs = []
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
      const oldTab = this.codeTabs.find(tab => tab.name === data.path)
      if (oldTab) {
        this.currentTabName = oldTab.name
        return
      }
      this.parsePath(data.path)
      const tab = this.addTab(data)
      this.$ajax.get(`/${apiPath}/code_template/${this.codeTree.templateId}/file_content?fileId=${data.info.fileId}`, { responseType: 'text' })
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

  .fileManage {
    @include coding-panel;
  }
</style>
