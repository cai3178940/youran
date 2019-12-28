<template>
  <div class="codePreview" @contextmenu.prevent="">
    <el-dialog :title="'代码预览: '+projectName" :visible.sync="visible" :fullscreen="true">
      <el-header class="codePath">
        <template v-for="(node,index) in paths">
          <span :key="node.key" class="codePathCell">
            <i v-if="index>0"  class="el-icon-arrow-right"></i>
            <span>
              <svg-icon v-if="node.icon" className=""
                        :iconClass="node.icon"></svg-icon>
              {{node.name}}
            </span>
          </span>
        </template>
      </el-header>
      <el-container ref="parent" class="codeContainer">
        <el-aside ref="aside" width="250px" v-loading="codeTreeLoading" class="codeAside">
          <el-scrollbar style="height:100%">
            <el-tree :props="treeProps"
                     :data="codeTree.tree"
                     :expand-on-click-node="false"
                     @node-expand="expandSingleNode"
                     @node-click="nodeClick">
              <span slot-scope="{ node, data }"
                    class="codeTreeNode"
                    @dblclick="codeTreeNodeDblclick(data, node)">
                <svg-icon :iconClass="getCodeTreeNodeIcon(data)"></svg-icon>
                <span style="margin-left: 3px;">{{node.label}}</span>
              </span>
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
                :name="tab.name"
                v-loading="tab.loading"
                element-loading-text="加载中..."
                element-loading-background="#313335">
                <span slot="label"
                      class="codeTabLabel"
                      @contextmenu.prevent="showTabContextMenu($event,tab)">{{tab.title}}</span>
                <vue-codemirror v-model="tab.content" :options="cmOptions"></vue-codemirror>
              </el-tab-pane>
            </el-tabs>
          </el-main>
        </el-container>
      </el-container>
    </el-dialog>
    <vue-simple-context-menu
      :options="tabContextMenuOptions"
      menuClassName="codeContextMenu"
      ref="tabContextMenu"
      @option-clicked="tabContextMenuOptionClicked"
    />
  </div>
</template>

<script>
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
import FileTypeUtil from '@/utils/file-type-util'
import projectApi from '@/api/project'
/**
 * 右键菜单组件
 * https://github.com/johndatserakis/vue-simple-context-menu
 */
import VueSimpleContextMenu from '@/components/VueSimpleContextMenu'

export default {
  name: 'code-preview',
  components: {
    'vue-codemirror': codemirror,
    'vue-simple-context-menu': VueSimpleContextMenu
  },
  data () {
    return {
      projectId: null,
      templateIndex: null,
      projectName: '',
      treeProps: {
        children: 'children',
        label: 'name'
      },
      // 后端返回的代码目录数据
      codeTree: {
        projectId: null,
        projectVersion: null,
        templateId: null,
        templateInnerVersion: null,
        tree: []
      },
      cmOptions: {
        readOnly: true,
        mode: FileTypeUtil.getCmMode('java'),
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
      visible: false,
      tabContextMenuOptions: [
        {
          name: '关闭',
          value: 'close'
        },
        {
          name: '关闭其他',
          value: 'closeOther'
        },
        {
          name: '关闭全部',
          value: 'closeAll'
        },
        {
          name: '关闭左边',
          value: 'closeLeft'
        },
        {
          name: '关闭右边',
          value: 'closeRight'
        }
      ]
    }
  },
  methods: {
    /**
     * event: 右击事件
     * item: tab节点信息
     */
    showTabContextMenu (event, item) {
      this.$refs.tabContextMenu.showMenu(event, item)
    },
    /**
     * item: tab节点信息
     * option: 菜单项
     */
    tabContextMenuOptionClicked ({ item, option }) {
      const tabName = item.name
      if (option.value === 'close') {
        this.removeTab(tabName)
      } else if (option.value === 'closeOther') {
        const otherTabs = this.codeTabs.filter(tab => tab.name !== tabName)
        otherTabs.forEach(tab => this.removeTab(tab.name))
      } else if (option.value === 'closeAll') {
        const tabNames = this.codeTabs.map(tab => tab.name)
        tabNames.forEach(name => this.removeTab(name))
      } else if (option.value === 'closeLeft') {
        const leftTabNames = []
        for (const tab of this.codeTabs) {
          if (tab.name === tabName) {
            break
          }
          leftTabNames.push(tab.name)
        }
        leftTabNames.forEach(name => this.removeTab(name))
      } else if (option.value === 'closeRight') {
        const rightTabNames = []
        let find = false
        for (const tab of this.codeTabs) {
          if (tab.name === tabName) {
            find = true
            continue
          }
          if (!find) {
            continue
          }
          rightTabNames.push(tab.name)
        }
        rightTabNames.forEach(name => this.removeTab(name))
      }
    },
    initData (projectId, projectName, templateIndex) {
      this.projectId = projectId
      this.projectName = projectName
      this.templateIndex = templateIndex
      this.codeTree.tree = []
      this.paths = []
      this.codeTabs = []
    },
    show (projectId, projectName, templateIndex) {
      this.visible = true
      this.initData(projectId, projectName, templateIndex)
      this.queryCodeTree(projectId, templateIndex)
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
    queryCodeTree (projectId, templateIndex) {
      this.codeTreeLoading = true
      return projectApi.getCodeTree(projectId, templateIndex)
        .then(data => { this.codeTree = data })
        .catch(error => this.$common.showNotifyError(error))
        .finally(() => { this.codeTreeLoading = false })
    },
    /**
     * 目录树节点图标
     */
    getCodeTreeNodeIcon (data) {
      return data.dir ? FileTypeUtil.getIcon('folder') : FileTypeUtil.getIcon(data.type)
    },
    /**
     * 双击树节点
     */
    codeTreeNodeDblclick (data, node) {
      if (!node.isLeaf) {
        const expanded = node.expanded
        node.expanded = !expanded
        if (!expanded) {
          this.expandSingleNode(data, node)
        }
      }
    },
    parsePath (data) {
      this.paths = [{
        name: this.projectName,
        key: this.projectName,
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
      this.parsePath(data)
      const tab = this.addTab(data)
      projectApi.getCodeFileContent(
        {
          'projectId': this.codeTree.projectId,
          'projectVersion': this.codeTree.projectVersion,
          'templateId': this.codeTree.templateId,
          'templateInnerVersion': this.codeTree.templateInnerVersion,
          'filePath': data.path
        })
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
  @import '../../assets/coding-panel.scss';

  .codePreview {
    @include coding-panel;
  }
</style>
